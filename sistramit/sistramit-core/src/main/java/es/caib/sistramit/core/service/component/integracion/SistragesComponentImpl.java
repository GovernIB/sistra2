package es.caib.sistramit.core.service.component.integracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RVersionTramite;

/**
 * Implementación acceso SISTRAGES.
 *
 * @author Indra
 *
 */
@Component("sistragesComponent")
public final class SistragesComponentImpl implements SistragesComponent {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SistragesApiComponent sistragesApiComponent;

    @Override
    public RConfiguracionGlobal obtenerConfiguracionGlobal() {
        return sistragesApiComponent.obtenerConfiguracionGlobal();
    }

    @Override
    public void evictConfiguracionGlobal() {
        log.debug("evictConfiguracionGlobal");
    }

    @Override
    public RConfiguracionEntidad obtenerConfiguracionEntidad(String idEntidad) {
        return sistragesApiComponent.obtenerConfiguracionEntidad(idEntidad);
    }

    @Override
    public void evictConfiguracionEntidad(String idEntidad) {
        log.debug("evictConfiguracionEntidad: " + idEntidad);
    }

    @Override
    public RVersionTramite recuperarDefinicionTramite(String idTramite,
            int version, String idioma) {
        final RVersionTramite definicionVersion = sistragesApiComponent
                .recuperarDefinicionTramite(idTramite, version, idioma);
        return definicionVersion;
    }

    @Override
    public void evictDefinicionTramite(String idTramite, int version,
            String idioma) {
        log.debug("evictDefinicionTramite: " + idTramite + "-" + version + "-"
                + idioma);
    }

    @Override
    public RAvisosEntidad obtenerAvisosEntidad(String idEntidad) {
        return sistragesApiComponent.obtenerAvisosEntidad(idEntidad);
    }

    @Override
    public void evictAvisosEntidad(String idEntidad) {
        log.debug("evictAvisosEntidad: " + idEntidad);
    }

}
