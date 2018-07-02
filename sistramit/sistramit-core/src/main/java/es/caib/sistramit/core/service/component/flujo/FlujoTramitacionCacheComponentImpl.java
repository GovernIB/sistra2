package es.caib.sistramit.core.service.component.flujo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.service.model.flujo.FlujoTramitacionCache;

@Component("flujoTramitacionCacheComponent")
public class FlujoTramitacionCacheComponentImpl
        implements FlujoTramitacionCacheComponent {

    /** Caché timeout: 60 min. */
    private final long TIMEOUT_SECONDS = 3600L;

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Map<String, FlujoTramitacionCache> flujoTramitacionMap = new HashMap<>();

    @Override
    public void put(String idSesionTramitacion,
            FlujoTramitacionComponent flujoTramitacionComponent) {
        synchronized (flujoTramitacionMap) {
            final FlujoTramitacionCache fc = new FlujoTramitacionCache();
            fc.setFcUltimoAcceso(new Date());
            fc.setFlujoTramitacion(flujoTramitacionComponent);
            flujoTramitacionMap.put(idSesionTramitacion, fc);
        }
    }

    @Override
    public FlujoTramitacionComponent get(String idSesionTramitacion) {
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

    private boolean isTimeout(Date fecha) {
        final Date ahora = new Date();
        final long secs = (ahora.getTime() - fecha.getTime())
                / (ConstantesNumero.N60 * ConstantesNumero.N1000);
        return (secs > TIMEOUT_SECONDS);
    }

}
