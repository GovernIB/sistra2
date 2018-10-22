package es.caib.sistramit.core.service.component.flujo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.FlujoTramitacionCache;

@Component("flujoTramitacionCacheComponent")
public class FlujoTramitacionCacheComponentImpl
        implements FlujoTramitacionCacheComponent {

    /** Configuracion. */
    @Autowired
    private ConfiguracionComponent configuracionComponent;

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /** Map con las sesiones de tramitación activas. */
    private final Map<String, FlujoTramitacionCache> flujoTramitacionMap = new HashMap<>();

    @Override
    public void put(final String idSesionTramitacion,
            final FlujoTramitacionComponent flujoTramitacionComponent) {
        synchronized (flujoTramitacionMap) {
            final FlujoTramitacionCache fc = new FlujoTramitacionCache();
            fc.setFcUltimoAcceso(new Date());
            fc.setFlujoTramitacion(flujoTramitacionComponent);
            flujoTramitacionMap.put(idSesionTramitacion, fc);
        }
    }

    @Override
    public FlujoTramitacionComponent get(final String idSesionTramitacion) {
        FlujoTramitacionComponent res = null;
        synchronized (flujoTramitacionMap) {
            final FlujoTramitacionCache fc = flujoTramitacionMap
                    .get(idSesionTramitacion);
            if (fc != null && !isTimeout(fc.getFcUltimoAcceso())) {
                res = fc.getFlujoTramitacion();
                fc.setFcUltimoAcceso(new Date());
            }
        }
        return res;
    }

    @Override
    public void purgar() {
        synchronized (flujoTramitacionMap) {
            for (final String idSesionTramitacion : flujoTramitacionMap
                    .keySet()) {
                final FlujoTramitacionCache fc = flujoTramitacionMap
                        .get(idSesionTramitacion);
                if (fc != null && isTimeout(fc.getFcUltimoAcceso())) {
                    log.debug("Purga flujo " + idSesionTramitacion);
                    flujoTramitacionMap.remove(idSesionTramitacion);
                }
            }
        }

    }

    private boolean isTimeout(final Date fecha) {
        final Date ahora = new Date();
        String timeoutProp = configuracionComponent
                .obtenerPropiedadConfiguracion(
                        TypePropiedadConfiguracion.TIMEOUT_SESION_WEB);
        if (StringUtils.isBlank(timeoutProp)) {
            log.warn("No se ha especificado propiedad "
                    + TypePropiedadConfiguracion.TIMEOUT_SESION_WEB
                    + " en fichero propiedades");
            timeoutProp = "30";
        }
        final int timeout = Integer.parseInt(timeoutProp)
                + ConstantesNumero.N10;
        final long min = (ahora.getTime() - fecha.getTime())
                / (ConstantesNumero.N1000 * ConstantesNumero.N60);
        return (min > timeout);
    }

}
