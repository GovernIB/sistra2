package es.caib.sistrages.core.service.component;

import java.util.List;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

	// ----------------------------------------------------------------------
	// FUNCIONES PRIVADAS
	// ----------------------------------------------------------------------

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
					final String valorProp = reemplazarPropsSystem(propiedad.getValor());

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
	 * Reemplaza propiedades con valor ${system.propiedad}
	 *
	 * @param valor
	 *            valores propiedades
	 * @return valor propiedad
	 */
	private String reemplazarPropsSystem(final String valor) {
		final String placeholder = "${system.";
		String res = valor;
		if (res != null) {
			int pos = valor.indexOf(placeholder);
			while (pos >= 0) {
				final int pos2 = res.indexOf("}", pos + 1);
				if (pos2 >= 0) {
					final String propSystem = res.substring(pos + placeholder.length(), pos2);
					final String valueSystem = System.getProperty(propSystem);
					if (valueSystem.indexOf(placeholder) >= 0) {
						throw new ErrorNoControladoException(
								"Valor no válido para propiedad " + propSystem + ": " + valueSystem);
					}
					res = StringUtils.replace(res, placeholder + propSystem + "}", valueSystem);
				}
				pos = res.indexOf(placeholder);
			}
		}
		return res;
	}

}
