package es.caib.sistramit.core.service.component.integracion;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistrages.rest.api.util.XTestJson;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;

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
        // TODO Pendiente
        return recuperarConfiguracionGlobalFromSTG();
    }

    @Override
    public RConfiguracionEntidad obtenerConfiguracionEntidad(String idEntidad) {
        // TODO Pendiente recuperar STG
        final RConfiguracionEntidad c = XTestJson.crearEntidad();
        return c;
    }

    @Override
    public RVersionTramite recuperarDefinicionTramite(String idTramite,
            int version, String idioma) {
        // TODO Pendiente recuperar STG
        final RVersionTramite definicionVersion = XTestJson
                .crearVersionTramite();
        return definicionVersion;
    }

    @Override
    public RAvisosEntidad obtenerAvisosEntidad(String idEntidad) {
        final RAvisosEntidad avisos = XTestJson.crearAvisos();
        return avisos;
    }

    // ------------- FUNCIONES PRIVADAS ---------------------------------

    /**
     * Invoca al STG para recuperar configuración global.
     *
     * @return propiedades
     */
    private RConfiguracionGlobal recuperarConfiguracionGlobalFromSTG() {

        // TODO PENDIENTE RECUPERAR SISTRAGES
        final RConfiguracionGlobal configuracionGlobal = new RConfiguracionGlobal();

        final List<RValorParametro> parametros = new ArrayList<>();
        RValorParametro vp;
        vp = new RValorParametro();
        vp.setCodigo(TypePropiedadConfiguracion.URL_SISTRAMIT.toString());
        vp.setValor("http://localhost:8080/sistramitfront");
        parametros.add(vp);
        vp = new RValorParametro();
        vp.setCodigo(TypePropiedadConfiguracion.IDIOMAS_SOPORTADOS.toString());
        vp.setValor("es,ca,en");
        parametros.add(vp);
        vp = new RValorParametro();
        vp.setCodigo(TypePropiedadConfiguracion.TIMEOUT_CACHE_FLUJO.toString());
        vp.setValor("3600");
        parametros.add(vp);

        final RListaParametros propiedades = new RListaParametros();
        propiedades.setParametros(parametros);
        configuracionGlobal.setPropiedades(propiedades);

        return configuracionGlobal;

    }

}
