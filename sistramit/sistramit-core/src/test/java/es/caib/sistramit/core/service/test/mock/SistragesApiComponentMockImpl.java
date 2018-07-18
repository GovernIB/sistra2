package es.caib.sistramit.core.service.test.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistramit.core.service.component.integracion.SistragesApiComponent;
import es.caib.sistramit.core.service.component.integracion.SistragesMock;

/**
 * Simula acceso SISTRAGES.
 *
 * @author Indra
 *
 */
@Component("sistragesApiComponentMock")
@Primary
public final class SistragesApiComponentMockImpl
        implements SistragesApiComponent {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public RConfiguracionGlobal obtenerConfiguracionGlobal() {
        return SistragesMock.crearConfiguracionGlobal();
    }

    @Override
    public RConfiguracionEntidad obtenerConfiguracionEntidad(String idEntidad) {
        final RConfiguracionEntidad c = SistragesMock.crearEntidad();
        return c;
    }

    @Override
    public RVersionTramite recuperarDefinicionTramite(String idTramite,
            int version, String idioma) {
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
