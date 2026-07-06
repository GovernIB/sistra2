package es.caib.sistramit.frontend.controller.asistente.pasos;

import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.frontend.procesos.EntregaProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * Control concurrencia al registrar.
 *
 */
@Component
public class RegistrarConcurrencyLimiter {

    /** Limite concurrencia. */
    private volatile int limit = 20;

    /** Contador de ejecuciones. */
    private final AtomicInteger executing = new AtomicInteger();

    /** Marca de tiempo del último chequeo. */
    private volatile long lastCheck = 0;

    /** Intervalo de tiempo para refrescar el límite de concurrencia (en milisegundos). */
    private static final long CHECK_INTERVAL_MS = 60_000;

    /** Log. */
    private static Logger log = LoggerFactory.getLogger(EntregaProcess.class);


    /** Servicio del sistema. */
    @Autowired
    private SystemService systemService;

    /** Inicializa el límite de concurrencia al iniciar el componente. */
    /* PROBLEMA AL METER JAVAX NOTATION EN EL FRONT
    @PostConstruct
    public void init() {
        refrescaLimiteConcurrencia();
    }
     */

    /**
     * Intenta adquirir un permiso para ejecutar la operación de registro. Si se alcanza el límite de concurrencia, devuelve null.
     * @return Un objeto Permit si se adquirió el permiso, o null si se alcanzó el límite de concurrencia.
     */
    public Permit tryAcquire() {

        refrescaLimiteConcurrencia();

        while (true) {
            int current = executing.get();

            if (current >= limit) {
                return null;
            }

            if (executing.compareAndSet(current, current + 1)) {
                return new Permit();
            }
        }
    }

    /**
     * Refresca el límite de concurrencia si ha pasado el intervalo de tiempo definido.
     * Consulta la configuración del sistema cada minuto para obtener el nuevo límite y lo actualiza si es diferente al actual.
     */
    private void refrescaLimiteConcurrencia() {

        long now = System.currentTimeMillis();

        if (now - lastCheck >= CHECK_INTERVAL_MS) {

            lastCheck = now;

            String limiteConcurrenciaStr = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.REGISTRO_CONTROL_CONCURRENCIA);
            int newLimit = limiteConcurrenciaStr != null ? Integer.parseInt(limiteConcurrenciaStr) : 20;

            if (newLimit != limit) {
                limit = newLimit;
                log.info("Limit de concurrencia per registrar actualitzat a: {}", limit);
            }
        }
    }

    /**
     * Clase Permit que representa un permiso adquirido para ejecutar la operación de registro. Implementa AutoCloseable para liberar el permiso automáticamente al cerrar.
     */
    public class Permit implements AutoCloseable {

        /** Indica si el permiso ha sido cerrado. */
        private boolean closed;

        @Override
        public void close() {
            if (!closed) {
                closed = true;
                executing.decrementAndGet();
            }
        }
    }
}