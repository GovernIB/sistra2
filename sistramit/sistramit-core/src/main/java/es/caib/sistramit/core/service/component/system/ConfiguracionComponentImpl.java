package es.caib.sistramit.core.service.component.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionAutenticacion;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistrages.rest.api.interna.RPlugin;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistramit.core.api.exception.CargaConfiguracionException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.PluginErrorException;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
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
	public String obtenerPropiedadConfiguracion(final TypePropiedadConfiguracion propiedad, final boolean forceLocal) {
		return readPropiedad(propiedad, forceLocal);
	}

	@Override
	public String obtenerPropiedadConfiguracion(final TypePropiedadConfiguracion propiedad) {
		return readPropiedad(propiedad, false);
	}

	@Override
	public DefinicionTramiteSTG recuperarDefinicionTramite(final String idTramite, final int version,
			final String idioma) {
		RVersionTramite definicionVersion = null;
		final TypeEntorno entorno = TypeEntorno.fromString(readPropiedad(TypePropiedadConfiguracion.ENTORNO, false));
		if (entorno == TypeEntorno.DESARROLLO) {
			definicionVersion = sistragesComponent.recuperarDefinicionTramiteNoCache(idTramite, version, idioma);
		} else {
			definicionVersion = sistragesComponent.recuperarDefinicionTramite(idTramite, version, idioma);
		}
		if (definicionVersion == null) {
			throw new ErrorConfiguracionException("Error al recuperar definició tràmit");
		}
		return new DefinicionTramiteSTG(new Date(), definicionVersion);
	}

	@Override
	public RDominio recuperarDefinicionDominio(final String idDominio) {
		return sistragesComponent.recuperarDefinicionDominio(idDominio);
	}

	@Override
	public RConfiguracionEntidad obtenerConfiguracionEntidad(final String idEntidad) {
		return sistragesComponent.obtenerConfiguracionEntidad(idEntidad);
	}

	@Override
	public RAvisosEntidad obtenerAvisosEntidad(final String idEntidad) {
		return sistragesComponent.obtenerAvisosEntidad(idEntidad);
	}

	@Override
	public IPlugin obtenerPluginGlobal(final TypePluginGlobal tipoPlugin) {
		final RConfiguracionGlobal confGlobal = sistragesComponent.obtenerConfiguracionGlobal();
		return createPlugin(confGlobal.getPlugins(), tipoPlugin.toString());
	}

	@Override
	public IPlugin obtenerPluginEntidad(final TypePluginEntidad tipoPlugin, final String idEntidad) {
		final RConfiguracionEntidad confEntidad = sistragesComponent.obtenerConfiguracionEntidad(idEntidad);
		return createPlugin(confEntidad.getPlugins(), tipoPlugin.toString());
	}

	@Override
	public String obtenerUrlResources() {
		final String urlResources = readPropiedad(TypePropiedadConfiguracion.SISTRAMIT_URL, false) + "/resources";
		return urlResources;
	}

	@Override
	public RConfiguracionAutenticacion obtenerConfiguracionAutenticacion(final String idConfAut,
			final String idEntidad) {
		RConfiguracionAutenticacion confAut = null;
		List<RConfiguracionAutenticacion> configuracionesAutenticacion = null;
		if (idEntidad != null) {
			final RConfiguracionEntidad confEntidad = sistragesComponent.obtenerConfiguracionEntidad(idEntidad);
			configuracionesAutenticacion = confEntidad.getConfiguracionesAutenticacion();

		} else {
			final RConfiguracionGlobal confGlobal = sistragesComponent.obtenerConfiguracionGlobal();
			configuracionesAutenticacion = confGlobal.getConfiguracionesAutenticacion();
		}

		if (configuracionesAutenticacion != null) {
			for (final RConfiguracionAutenticacion rca : configuracionesAutenticacion) {
				if (rca.getIdentificador().equals(idConfAut)) {
					confAut = rca;
					break;
				}
			}
		}

		if (confAut == null) {
			throw new ErrorConfiguracionException("No es troba id conf autenticació: " + idConfAut);
		}

		return confAut;
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
					"Error al carregar la configuració del properties '" + pathProperties + "' : " + e.getMessage(), e);
		}
	}

	/**
	 * Obtiene valor propiedad de configuracion global.
	 *
	 * @param propiedad
	 *                      propiedad
	 * @return valor
	 */
	private String getPropiedadGlobal(final TypePropiedadConfiguracion propiedad) {
		String res = null;
		final RConfiguracionGlobal configuracionGlobal = sistragesComponent.obtenerConfiguracionGlobal();
		if (configuracionGlobal != null && configuracionGlobal.getPropiedades() != null
				&& configuracionGlobal.getPropiedades().getParametros() != null) {
			for (final RValorParametro vp : configuracionGlobal.getPropiedades().getParametros()) {
				if (propiedad.toString().equals(vp.getCodigo())) {
					res = vp.getValor();
					break;
				}
			}
		}
		return res;
	}

	private IPlugin createPlugin(final List<RPlugin> plugins, final String plgTipo) {

		String prefijoGlobal = this.getPropiedadGlobal(TypePropiedadConfiguracion.PLUGINS_PREFIJO);
		if (prefijoGlobal == null) {
			throw new PluginErrorException("No s'ha definit propietat global per prefix global per plugins: "
					+ TypePropiedadConfiguracion.PLUGINS_PREFIJO);
		}
		if (!prefijoGlobal.endsWith(".")) {
			prefijoGlobal = prefijoGlobal + ".";
		}

		IPlugin plg = null;
		RPlugin rplg = null;
		String classname = null;
		try {
			for (final RPlugin p : plugins) {
				if (p.getTipo().equals(plgTipo)) {
					rplg = p;
					break;
				}
			}

			if (rplg == null) {
				throw new PluginErrorException("No existeix plugin de tipus " + plgTipo);
			}

			classname = rplg.getClassname();

			Properties prop = null;
			if (rplg.getPrefijoPropiedades() != null && rplg.getPropiedades() != null
					&& rplg.getPropiedades().getParametros() != null
					&& !rplg.getPropiedades().getParametros().isEmpty()) {
				prop = new Properties();
				for (final RValorParametro parametro : rplg.getPropiedades().getParametros()) {

					// SE RESUELVE EN STG
					// Comprobamos si la propiedad hay que cargarla de system
					// final String valorProp = reemplazarPropsSystem(parametro.getValor());
					final String valorProp = parametro.getValor();

					prop.put(prefijoGlobal + rplg.getPrefijoPropiedades() + parametro.getCodigo(), valorProp);
				}
			}

			plg = (IPlugin) PluginsManager.instancePluginByClassName(classname, prefijoGlobal, prop);

			if (plg == null) {
				throw new PluginErrorException(
						"No s'ha pogut instanciar plugin de tipus " + plgTipo + " , PluginManager retorna nulo.");
			}

			return plg;

		} catch (final Exception e) {
			throw new PluginErrorException("Error al instanciar plugin " + plgTipo + " amb classname " + classname, e);
		}
	}

	/**
	 * Reemplaza propiedades con valor ${system.propiedad}
	 *
	 * @param valor
	 *                  valores propiedades
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
						throw new ErrorConfiguracionException(
								"Valor no vàlido per propietat " + propSystem + ": " + valueSystem);
					}
					res = StringUtils.replace(res, placeholder + propSystem + "}", valueSystem);
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
	private String readPropiedad(final TypePropiedadConfiguracion propiedad, final boolean forceLocal) {
		// Busca primero en propiedades locales
		String prop = propiedadesLocales.getProperty(propiedad.toString());
		// Si no, busca en propiedades globales
		if (StringUtils.isBlank(prop) && !forceLocal) {
			prop = getPropiedadGlobal(propiedad);
		}
		return StringUtils.trim(prop);
	}

}
