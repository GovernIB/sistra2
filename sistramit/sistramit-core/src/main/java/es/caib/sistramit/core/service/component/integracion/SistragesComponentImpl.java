package es.caib.sistramit.core.service.component.integracion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.ConfiguracionEntidad;
import es.caib.sistrages.rest.api.ConfiguracionGlobal;
import es.caib.sistrages.rest.api.ListaParametros;
import es.caib.sistrages.rest.api.ValorParametro;
import es.caib.sistrages.rest.api.VersionTramite;
import es.caib.sistrages.rest.api.util.XTestJson;
import es.caib.sistramit.core.api.model.comun.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

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

    @Override
    public ConfiguracionGlobal obtenerConfiguracionGlobal() {
        // TODO Pendiente
        return recuperarConfiguracionGlobalFromSTG();
    }

    @Override
    public void evictConfiguracionGlobal() {
        log.debug("evictConfiguracionGlobal");
    }

    @Override
    public ConfiguracionEntidad obtenerConfiguracionEntidad(String idEntidad) {
        // TODO Pendiente recuperar STG
        final ConfiguracionEntidad c = XTestJson.crearEntidad();
        return c;
    }

    @Override
    public void evictConfiguracionEntidad(String idEntidad) {
        log.debug("evictConfiguracionEntidad: " + idEntidad);
    }

    @Override
    public DefinicionTramiteSTG recuperarDefinicionTramite(String idTramite,
            int version, String idioma) {
        // TODO Pendiente recuperar STG
        final VersionTramite definicionVersion = XTestJson
                .crearVersionTramite();
        final DefinicionTramiteSTG dt = new DefinicionTramiteSTG(new Date(),
                definicionVersion);
        return dt;
    }

    @Override
    public void evictDefinicionTramite(String idTramite, int version,
            String idioma) {
        log.debug("evictDefinicionTramite: " + idTramite + "-" + version + "-"
                + idioma);

    }

    // ------------- FUNCIONES PRIVADAS ---------------------------------

    /**
     * Invoca al STG para recuperar configuración global.
     *
     * @return propiedades
     */
    private ConfiguracionGlobal recuperarConfiguracionGlobalFromSTG() {

        // TODO PENDIENTE RECUPERAR SISTRAGES
        final ConfiguracionGlobal configuracionGlobal = new ConfiguracionGlobal();

        final List<ValorParametro> parametros = new ArrayList<>();
        ValorParametro vp;
        vp = new ValorParametro();
        vp.setCodigo(TypePropiedadConfiguracion.URL_SISTRAMIT.toString());
        vp.setValor("http://localhost:8080/sistramitfront");
        parametros.add(vp);
        vp = new ValorParametro();
        vp.setCodigo(TypePropiedadConfiguracion.IDIOMAS_SOPORTADOS.toString());
        vp.setValor("es,ca,en");
        parametros.add(vp);

        final ListaParametros propiedades = new ListaParametros();
        propiedades.setParametros(parametros);
        configuracionGlobal.setPropiedades(propiedades);

        return configuracionGlobal;

    }

}
