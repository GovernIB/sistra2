package es.caib.sistrages.core.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.exception.CargaConfiguracionException;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.ConfiguracionGlobalDao;
import es.caib.sistrages.core.service.repository.dao.FicheroExternoDao;
import es.caib.sistrages.core.service.repository.dao.ProcesoDao;

@Service
@Transactional
public class SystemServiceImpl implements SystemService {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

    /** DAO Fichero externo. */
    @Autowired
    FicheroExternoDao ficheroExternoDAO;

    /** DAO Procesos. */
    @Autowired
    ProcesoDao procesosDAO;

    /** DAO configuracion global. */
    @Autowired
    ConfiguracionGlobalDao configuracionGlobalDao;

    /** Propiedades configuración especificadas en properties. */
    private Properties propiedadesLocales;

    @PostConstruct
    public void init() {
        // Recupera propiedades configuracion especificadas en properties
        propiedadesLocales = recuperarConfiguracionProperties();
    }

    @Override
    @NegocioInterceptor
    public void purgarFicheros() {
        ficheroExternoDAO.purgarFicheros();
    }

    @Override
    @NegocioInterceptor
    public boolean verificarMaestro(final String appId) {
        return procesosDAO.verificarMaestro(appId);

    }

    @Override
    public String obtenerPropiedadConfiguracion(String propiedad) {
        // Busca primero en propiedades locales
        String prop = propiedadesLocales.getProperty(propiedad.toString());
        // Si no, busca en propiedades globales
        if (StringUtils.isBlank(prop)) {
            final ConfiguracionGlobal cg = configuracionGlobalDao
                    .getByPropiedad(propiedad);
            if (cg != null) {
                prop = cg.getValor();
            }
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
                .getProperty("es.caib.sistrages.properties.path");
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

}
