package es.caib.sistrages.core.service.component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.exception.CargaConfiguracionException;
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

	@Override
	public IPlugin obtenerPluginGlobal(final TypePlugin tipoPlugin) {
		final List<Plugin> plugins = pluginsDao.getAll(TypeAmbito.GLOBAL, null);
		return createPlugin(plugins, tipoPlugin);
	}

	@Override
	public IPlugin obtenerPluginEntidad(final TypePlugin tipoPlugin, final Long idEntidad) {
		final List<Plugin> plugins = pluginsDao.getAll(TypeAmbito.ENTIDAD, idEntidad);
		return createPlugin(plugins, tipoPlugin);
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
		final String pathProperties = System.getProperty("es.caib.sistramit.properties.path");
		try (FileInputStream fis = new FileInputStream(pathProperties);) {
			final Properties props = new Properties();
			props.load(fis);
			return props;
		} catch (final IOException e) {
			throw new CargaConfiguracionException(
					"Error al cargar la configuracion del properties '" + pathProperties + "' : " + e.getMessage(), e);
		}
	}

	/**
	 * Obtiene valor propiedad de configuracion global.
	 *
	 * @param propiedad
	 *            propiedad
	 * @return valor
	 */
	private String getPropiedadGlobal(final TypePropiedadConfiguracion propiedad) {
		String res = null;
		final ConfiguracionGlobal conf = configuracionGlobalDao.getByPropiedad(propiedad.toString());
		if (conf != null) {
			res = conf.getValor();
		}
		return res;
	}

	private IPlugin createPlugin(final List<Plugin> plugins, final TypePlugin plgTipo) {
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
				throw new CargaConfiguracionException("No existe plugin de tipo " + plgTipo);
			}

			classname = rplg.getClassname();

			Properties prop = null;
			if (rplg.getPrefijoPropiedades() != null && rplg.getPropiedades() != null
					&& !rplg.getPropiedades().isEmpty()) {
				prop = new Properties();
				for (final Propiedad propiedad : rplg.getPropiedades()) {
					prop.put(prefijoGlobal + rplg.getPrefijoPropiedades() + propiedad.getCodigo(),
							propiedad.getValor());
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
}
