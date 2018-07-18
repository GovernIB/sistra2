package es.caib.sistramit.core.service.component.integracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Component("sistragesApiComponent")
public final class SistragesApiComponentImpl implements SistragesApiComponent {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public RConfiguracionGlobal obtenerConfiguracionGlobal() {
        // TODO Pendiente recuperar STG
        return SistragesMock.crearConfiguracionGlobal();
    }

    @Override
    public RConfiguracionEntidad obtenerConfiguracionEntidad(String idEntidad) {
        // TODO Pendiente recuperar STG
        final RConfiguracionEntidad c = SistragesMock.crearEntidad();
        return c;
    }

    @Override
    public RVersionTramite recuperarDefinicionTramite(String idTramite,
            int version, String idioma) {
        // TODO Pendiente recuperar STG
        final RVersionTramite definicionVersion = SistragesMock
                .crearVersionTramite();
        return definicionVersion;
    }

    @Override
    public RAvisosEntidad obtenerAvisosEntidad(String idEntidad) {
        final RAvisosEntidad avisos = SistragesMock.crearAvisos();
        return avisos;
    }

}
