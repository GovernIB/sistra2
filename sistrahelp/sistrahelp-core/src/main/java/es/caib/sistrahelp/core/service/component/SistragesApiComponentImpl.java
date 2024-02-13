package es.caib.sistrahelp.core.service.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.digester.plugins.PluginException;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RLiteralIdioma;
import es.caib.sistrages.rest.api.interna.RLogoEntidad;
import es.caib.sistrages.rest.api.interna.RPermisoHelpDesk;
import es.caib.sistrages.rest.api.interna.RPlugin;
import es.caib.sistrages.rest.api.interna.RTramitesPorArea;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RVersionesPorTramite;
import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.Literal;
import es.caib.sistrahelp.core.api.model.Traduccion;
import es.caib.sistrahelp.core.api.model.types.TypePluginGlobal;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;

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

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	public List<RPermisoHelpDesk> obtenerPermisosHelpdesk() {
		List<RPermisoHelpDesk> resultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final RPermisoHelpDesk[] listaPermisos = restTemplate.getForObject(getUrl() + "/permisosHelpdesk",
				RPermisoHelpDesk[].class);

		if (listaPermisos != null) {
			resultado = Arrays.asList(listaPermisos);
		}

		return resultado;
	}

	@Override
	public Entidad obtenerDatosEntidad(final String idEntidad) {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		final RConfiguracionEntidad configuracionEntidad = restTemplate.getForObject(getUrl() + "/entidad/" + idEntidad,
				RConfiguracionEntidad.class);

		Entidad entidad = null;

		if (configuracionEntidad != null) {
			entidad = new Entidad();

			entidad.setCodigoDIR3(configuracionEntidad.getIdentificador());

			if (configuracionEntidad.getDescripcion() != null) {
				final Literal literal = new Literal();

				for (final RLiteralIdioma rLiteral : configuracionEntidad.getDescripcion().getLiterales()) {
					final Traduccion trad = new Traduccion(rLiteral.getIdioma(), rLiteral.getDescripcion());
					literal.add(trad);
				}

				entidad.setNombre(literal);
			}

			entidad.setLogoGestor(configuracionEntidad.getLogoGestor());

		}
		return entidad;

	}


	@Override
	public byte[] urlLogoEntidad(String idEntidad) {
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		final RLogoEntidad configuracionEntidad = restTemplate.getForObject(getUrl() + "/entidad/" + idEntidad + "/logo",
				RLogoEntidad.class);
		return configuracionEntidad.getLogo();
	}

	@Override
	public Entidad obtenerDatosEntidadByArea(final String identificador) {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		final RConfiguracionEntidad configuracionEntidad = restTemplate.getForObject(
				getUrl() + "/entidadByArea/" + identificador.split("\\.")[0] + "/" + identificador.split("\\.")[1],
				RConfiguracionEntidad.class);

		Entidad entidad = null;

		if (configuracionEntidad != null) {
			entidad = new Entidad();

			entidad.setCodigoDIR3(configuracionEntidad.getIdentificador());

			if (configuracionEntidad.getDescripcion() != null) {
				final Literal literal = new Literal();

				for (final RLiteralIdioma rLiteral : configuracionEntidad.getDescripcion().getLiterales()) {
					final Traduccion trad = new Traduccion(rLiteral.getIdioma(), rLiteral.getDescripcion());
					literal.add(trad);
				}

				entidad.setNombre(literal);
			}

			entidad.setLogoGestor(configuracionEntidad.getLogoGestor());
			entidad.setLogoAsistente(configuracionEntidad.getLogo());

		}
		return entidad;

	}

	@Override
	public IPlugin obtenerPluginGlobal(final TypePluginGlobal tipoPlugin) throws PluginException {
		final RConfiguracionGlobal confGlobal = obtenerConfiguracionGlobal();
		return createPlugin(confGlobal.getPlugins(), tipoPlugin.toString());
	}

	public RConfiguracionGlobal obtenerConfiguracionGlobal() {
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		return restTemplate.getForObject(getUrl() + "/configuracionGlobal", RConfiguracionGlobal.class);
	}

	private IPlugin createPlugin(final List<RPlugin> plugins, final String plgTipo) throws PluginException {

		String prefijoGlobal = this.getPropiedadGlobal(TypePropiedadConfiguracion.PLUGINS_PREFIJO);
		if (prefijoGlobal == null) {
			throw new PluginException("No s'ha definit propietat global per prefix global per plugins: "
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
				throw new PluginException("No existeix plugin de tipus " + plgTipo);
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
				throw new PluginException(
						"No s'ha pogut instanciar plugin de tipus " + plgTipo + " , PluginManager retorna nulo.");
			}

			return plg;

		} catch (final Exception e) {
			throw new PluginException("Error al instanciar plugin " + plgTipo + " amb classname " + classname, e);
		}
	}

	/**
	 * Obtiene valor propiedad de configuracion global.
	 *
	 * @param propiedad propiedad
	 * @return valor
	 */
	private String getPropiedadGlobal(final TypePropiedadConfiguracion propiedad) {
		String res = null;
		final RConfiguracionGlobal configuracionGlobal = obtenerConfiguracionGlobal();
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

	private String getPassword() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_PWD);
	}

	private String getUser() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_USR);
	}

	private String getUrl() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_URL);
	}

	@Override
	public List<String> obtenerTramitesPorArea(String idArea) {
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		final RTramitesPorArea tramites = restTemplate.getForObject(getUrl() + "/tramitesPorArea/" + idArea.split("\\.")[0] + "/" + idArea.split("\\.")[1],
				RTramitesPorArea.class);

		List<String> tramitesAux = new ArrayList<String>();

		if (tramitesAux != null) {
			tramitesAux = tramites.getListaTramites();
		}
		return tramitesAux;
	}

	@Override
	public List<Integer> obtenerVersionTramite(String identificador, String tramite) {
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		final RVersionesPorTramite versiones = restTemplate.getForObject(getUrl() + "/versionesPorTramite/" + identificador.split("\\.")[0] + "/" + identificador.split("\\.")[1] + "/" + tramite,
				RVersionesPorTramite.class);

		List<Integer> versionesAux = new ArrayList<Integer>();

		if (versionesAux != null) {
			versionesAux = versiones.getListaVersiones();
		}
		return versionesAux;
	}


}
