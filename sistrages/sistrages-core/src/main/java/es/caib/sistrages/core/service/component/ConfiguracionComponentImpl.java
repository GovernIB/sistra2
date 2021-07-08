package es.caib.sistrages.core.service.component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.exception.CargaConfiguracionException;
import es.caib.sistrages.core.api.exception.ErrorNoControladoException;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.service.repository.dao.ConfiguracionGlobalDao;
import es.caib.sistrages.core.service.repository.dao.PluginsDao;

@Component("configuracionComponent")
public class ConfiguracionComponentImpl implements ConfiguracionComponent {

	@Autowired
	PluginsDao pluginsDao;

	@Autowired
	ConfiguracionGlobalDao configuracionGlobalDao;

	/** Propiedades configuración especificadas en properties. */
	private Properties propiedadesLocales;

	final Logger log = LoggerFactory.getLogger(ConfiguracionComponentImpl.class);

	@PostConstruct
	public void init() {
		// Recupera propiedades configuracion especificadas en properties
		propiedadesLocales = recuperarConfiguracionProperties();
	}

	@Override
	public IPlugin obtenerPluginGlobal(final TypePlugin tipoPlugin) {
		final List<Plugin> plugins = pluginsDao.getAll(TypeAmbito.GLOBAL, null);
		return createPlugin(plugins, tipoPlugin, true);
	}

	@Override
	public IPlugin obtenerPluginEntidad(final TypePlugin tipoPlugin, final Long idEntidad) {
		final List<Plugin> plugins = pluginsDao.getAll(TypeAmbito.ENTIDAD, idEntidad);
		return createPlugin(plugins, tipoPlugin, true);
	}

	@Override
	public IPlugin obtenerPlugin(final TypePlugin tipoPlugin, final Long idEntidad) {
		List<Plugin> plugins = pluginsDao.getAll(TypeAmbito.ENTIDAD, idEntidad);
		IPlugin iplugin = createPlugin(plugins, tipoPlugin, false);
		if (iplugin == null) {
			plugins = pluginsDao.getAll(TypeAmbito.GLOBAL, null);
			iplugin = createPlugin(plugins, tipoPlugin, false);
		}

		return iplugin;
	}

	@Override
	public String replacePlaceholders(final String valor) {
		String res = replacePlaceHolders(valor, false);
		res = replacePlaceHolders(res, true);
		return res;
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
		final String pathProperties = System.getProperty("es.caib.sistrages.properties.path");
		final Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream(pathProperties);) {
			props.load(fis);
		} catch (final IOException e) {
			throw new CargaConfiguracionException(
					"Error al cargar la configuracion del properties '" + pathProperties + "' : " + e.getMessage(), e);
		}


		final String pathPropertiesSystem = System.getProperty("es.caib.sistrages.system.properties.path");
		final Properties propSystem = new Properties();
		try (FileInputStream fis = new FileInputStream(pathPropertiesSystem);) {
			propSystem.load(fis);
		} catch (final IOException e) {
			throw new CargaConfiguracionException(
					"Error al cargar la configuracion del properties '" + pathProperties + "' : " + e.getMessage(), e);
		}

		props.putAll(propSystem);
		return props;
	}

	/**
	 * Obtiene valor propiedad de configuracion global.
	 *
	 * @param propiedad
	 *                      propiedad
	 * @return valor
	 */
	private String getPropiedadGlobal(final TypePropiedadConfiguracion propiedad) {
		return getPropiedadGlobal(propiedad.toString());
	}

	/**
	 * Obtiene valor propiedad de configuracion global.
	 *
	 * @param propiedad
	 *                      propiedad
	 * @return valor
	 */
	private String getPropiedadGlobal(final String propiedad) {
		String res = null;
		final ConfiguracionGlobal conf = configuracionGlobalDao.getByPropiedad(propiedad.toString());
		if (conf != null) {
			res = conf.getValor();
		}
		return res;
	}

	/**
	 * Crea plugin.
	 *
	 * @param plugins
	 *                            Lista configuración plugins
	 * @param plgTipo
	 *                            Tipo plugin
	 * @param lanzarExcepcion
	 *                            Si lanza excepción
	 * @return plugin
	 */
	private IPlugin createPlugin(final List<Plugin> plugins, final TypePlugin plgTipo, final boolean lanzarExcepcion) {
		String prefijoGlobal = this.getPropiedadGlobal(TypePropiedadConfiguracion.PLUGINS_PREFIJO);
		if (prefijoGlobal != null && !prefijoGlobal.endsWith(".")) {
			prefijoGlobal = prefijoGlobal + ".";
		}

		IPlugin plg = null;
		Plugin rplg = null;
		String classname = null;
		try {
			for (final Plugin p : plugins) {
				if (p.getTipo().equals(plgTipo)) {
					rplg = p;
					break;
				}
			}

			if (rplg == null) {
				if (lanzarExcepcion) {
					throw new CargaConfiguracionException("No existe plugin de tipo " + plgTipo);
				} else {
					return null;
				}
			}

			classname = rplg.getClassname();

			Properties prop = null;
			if (rplg.getPrefijoPropiedades() != null && rplg.getPropiedades() != null
					&& !rplg.getPropiedades().isEmpty()) {
				prop = new Properties();
				for (final Propiedad propiedad : rplg.getPropiedades()) {

					// Comprobamos si la propiedad hay que cargarla de system
					final String valorProp = replacePlaceholders(propiedad.getValor());

					prop.put(prefijoGlobal + rplg.getPrefijoPropiedades() + propiedad.getCodigo(), valorProp);
				}
			}

			plg = (IPlugin) PluginsManager.instancePluginByClassName(classname, prefijoGlobal, prop);

			if (plg == null) {
				throw new CargaConfiguracionException(
						"No se ha podido instanciar plugin de tipo " + plgTipo + " , PluginManager devuelve nulo.");
			}

			return plg;

		} catch (final Exception e) {
			throw new CargaConfiguracionException(
					"Error al instanciar plugin " + plgTipo + " con classname " + classname, e);
		}
	}

	/**
	 * Reemplaza placeholders: sistema (${system.propiedad}) o configuración
	 * (${config.propiedad).
	 *
	 * @param valor
	 *                   Valor
	 * @param system
	 *                   Indica si es de sistema
	 * @return Valor reemplazado.
	 */
	private String replacePlaceHolders(final String valor, final boolean system) {
		String placeholder;

		if (system) {
			placeholder = "${system.";
		} else {
			placeholder = "${config.";
		}

		String res = valor;
		if (res != null) {
			int pos = valor.indexOf(placeholder);
			while (pos >= 0) {
				final int pos2 = res.indexOf("}", pos + 1);
				if (pos2 >= 0) {
					final String propPlaceholder = res.substring(pos + placeholder.length(), pos2);
					String valuePlaceholder = "";
					if (system) {
						valuePlaceholder = System.getProperty(propPlaceholder);
					} else {
						valuePlaceholder = readPropiedad(propPlaceholder, false);
					}
					valuePlaceholder = StringUtils.defaultString(valuePlaceholder);
					if (valuePlaceholder.indexOf(placeholder) >= 0) {
						throw new ErrorNoControladoException(
								"Valor no válido para propiedad " + propPlaceholder + ": " + valuePlaceholder);
					}
					if (StringUtils.isBlank(valuePlaceholder)) {
						log.warn("Placeholder " + propPlaceholder + " tiene valor vacío");
					}
					res = StringUtils.replace(res, placeholder + propPlaceholder + "}", valuePlaceholder);
				}
				pos = res.indexOf(placeholder);
			}
		}
		return res;
	}

	/**
	 * Lee propiedad.
	 *
	 * @param propiedad
	 *                       propiedad
	 * @param forceLocal
	 *                       si fuerza solo a buscar en el properties local y no
	 *                       buscar en la configuración global del STG
	 * @return valor propiedad (nulo si no existe)
	 */
	private String readPropiedad(final String propiedad, final boolean forceLocal) {
		// Busca primero en propiedades locales
		String prop = propiedadesLocales.getProperty(propiedad);
		// Si no, busca en propiedades globales
		if (StringUtils.isBlank(prop) && !forceLocal) {
			prop = getPropiedadGlobal(propiedad);
		}
		return StringUtils.trim(prop);
	}

}
