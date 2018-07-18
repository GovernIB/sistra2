package es.caib.sistramit.core.service.component.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.plugins.IPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RPlugin;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistramit.core.api.exception.CargaConfiguracionException;
import es.caib.sistramit.core.api.exception.PluginErrorException;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.integracion.SistragesComponent;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

@Component("configuracionComponent")
public class ConfiguracionComponentImpl implements ConfiguracionComponent {

    /** Propiedades configuración especificadas en properties. */
    private Properties propiedadesLocales;

    /** Componente STG. */
    @Autowired
    private SistragesComponent sistragesComponent;

    @PostConstruct
    public void init() {
        // Recupera propiedades configuracion especificadas en properties
        propiedadesLocales = recuperarConfiguracionProperties();
    }

    @Override
    public String obtenerPropiedadConfiguracion(
            TypePropiedadConfiguracion propiedad) {
        // Busca primero en propiedades locales
        String prop = propiedadesLocales.getProperty(propiedad.toString());
        if (StringUtils.isBlank(prop)) {
            // Si no, busca en propiedades globales
            prop = getPropiedadGlobal(propiedad);
        }
        return prop;
    }

    @Override
    public DefinicionTramiteSTG recuperarDefinicionTramite(String idTramite,
            int version, String idioma) {
        final RVersionTramite definicionVersion = sistragesComponent
                .recuperarDefinicionTramite(idTramite, version, idioma);
        final DefinicionTramiteSTG dt = new DefinicionTramiteSTG(new Date(),
                definicionVersion);
        return dt;
    }

    @Override
    public RConfiguracionEntidad obtenerConfiguracionEntidad(String idEntidad) {
        return sistragesComponent.obtenerConfiguracionEntidad(idEntidad);
    }

    @Override
    public RAvisosEntidad obtenerAvisosEntidad(String idEntidad) {
        return sistragesComponent.obtenerAvisosEntidad(idEntidad);
    }

    @Override
    public IPlugin obtenerPluginGlobal(TypePluginGlobal tipoPlugin) {
        final RConfiguracionGlobal confGlobal = sistragesComponent
                .obtenerConfiguracionGlobal();
        return createPlugin(confGlobal.getPlugins(), tipoPlugin.toString());
    }

    @Override
    public IPlugin obtenerPluginEntidad(TypePluginEntidad tipoPlugin,
            String idEntidad) {
        final RConfiguracionEntidad confEntidad = sistragesComponent
                .obtenerConfiguracionEntidad(idEntidad);
        return createPlugin(confEntidad.getPlugins(), tipoPlugin.toString());
    }

    // ----------------------------------------------------------------------
    // FUNCIONES PRIVADAS
    // ----------------------------------------------------------------------
    /**
     * Carga propiedades locales de fichero de properties.
     *
     * @return properties
     */
    private Properties recuperarConfiguracionProperties() {
        final String pathProperties = System
                .getProperty("es.caib.sistramit.properties.path");
        try (FileInputStream fis = new FileInputStream(pathProperties);) {
            final Properties props = new Properties();
            props.load(fis);
            return props;
        } catch (final IOException e) {
            throw new CargaConfiguracionException(
                    "Error al cargar la configuracion del properties '"
                            + pathProperties + "' : " + e.getMessage(),
                    e);
        }
    }

    /**
     * Obtiene valor propiedad de configuracion global.
     *
     * @param propiedad
     *            propiedad
     * @return valor
     */
    private String getPropiedadGlobal(TypePropiedadConfiguracion propiedad) {
        String res = null;
        final RConfiguracionGlobal configuracionGlobal = sistragesComponent
                .obtenerConfiguracionGlobal();
        if (configuracionGlobal != null
                && configuracionGlobal.getPropiedades() != null
                && configuracionGlobal.getPropiedades()
                        .getParametros() != null) {
            for (final RValorParametro vp : configuracionGlobal.getPropiedades()
                    .getParametros()) {
                if (propiedad.toString().equals(vp.getCodigo())) {
                    res = vp.getValor();
                    break;
                }
            }
        }
        return res;
    }

    private IPlugin createPlugin(final List<RPlugin> plugins,
            final String plgTipo) {
        IPlugin plg = null;
        String className = null;
        try {
            for (final RPlugin p : plugins) {
                if (p.getTipo().equals(plgTipo)) {
                    className = p.getClassname();
                    break;
                }
            }

            if (className == null) {
                throw new PluginErrorException(
                        "No existe plugin de tipo " + plgTipo);
            }

            // TODO Revisar creación y establecer props
            plg = (IPlugin) Class.forName(className).newInstance();

        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException e) {
            throw new PluginErrorException("Error al instanciar plugin "
                    + plgTipo + " con classname " + className, e);
        }
        return plg;
    }
}
