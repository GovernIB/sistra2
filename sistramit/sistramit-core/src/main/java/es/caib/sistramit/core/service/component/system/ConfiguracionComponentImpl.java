package es.caib.sistramit.core.service.component.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.ConfiguracionGlobal;
import es.caib.sistrages.rest.api.ValorParametro;
import es.caib.sistramit.core.api.exception.CargaConfiguracionException;
import es.caib.sistramit.core.api.model.comun.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.integracion.SistragesComponent;

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
        final ConfiguracionGlobal configuracionGlobal = sistragesComponent
                .obtenerConfiguracionGlobal();
        if (configuracionGlobal != null
                && configuracionGlobal.getPropiedades() != null
                && configuracionGlobal.getPropiedades()
                        .getParametros() != null) {
            for (final ValorParametro vp : configuracionGlobal.getPropiedades()
                    .getParametros()) {
                if (propiedad.toString().equals(vp.getCodigo())) {
                    res = vp.getValor();
                    break;
                }
            }
        }
        return res;
    }

}
