package es.caib.sistra2.commons.plugins.autenticacion.loginib;

import es.caib.loginib.rest.api.v1.RDatosAutenticacion;
import es.caib.loginib.rest.api.v1.RLoginParams;
import es.caib.loginib.rest.api.v1.RLogoutParams;
import es.caib.sistra2.commons.plugins.autenticacion.api.*;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Properties;

/**
 * Plugin componente autenticación con loginib.
 *
 * @author Indra
 *
 */
public class ComponenteAutenticacionPluginLoginib extends AbstractPluginProperties
		implements IComponenteAutenticacionPlugin {

	public ComponenteAutenticacionPluginLoginib(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public String iniciarSesionAutenticacion(final String codigoEntidad, final String idioma,
											 final List<TipoAutenticacion> metodos, final String qaa, final String callback)
			throws AutenticacionPluginException {

		String metodo = "";
		if (metodos.contains(TipoAutenticacion.ANONIMO)) {
			metodo += "ANONIMO;";
		}
		if (metodos.contains(TipoAutenticacion.AUTENTICADO)) {
			metodo += "CLAVE_CERTIFICADO;CLAVE_PIN;CLAVE_PERMANENTE";
		}
		if (metodo.endsWith(";")) {
			metodo = metodo.substring(0, metodo.length() - 1);
		}

		final RLoginParams param = new RLoginParams();
		param.setAplicacion(getIdAplicacion());
		param.setEntidad(codigoEntidad);
		param.setUrlCallback(callback);
		param.setIdioma(idioma);
		param.setForzarAutenticacion(false);
		param.setQaa(Integer.parseInt(qaa));
		param.setMetodosAutenticacion(metodo);
		param.setAplicacion("SISTRA2");
		final RestTemplate restTemplate = new RestTemplate();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		final HttpEntity<RLoginParams> request = new HttpEntity<>(param, headers);
		final ResponseEntity<String> responseTramite = restTemplate.postForEntity(getUrl() + "/login", request,
				String.class);

		return responseTramite.getBody();
	}

	@Override
	public DatosUsuario validarTicketAutenticacion(final String pTicket) throws AutenticacionPluginException {

		final RestTemplate restTemplate = new RestTemplate();

		final RDatosAutenticacion datosUtenticacion = restTemplate.getForObject(getUrl() + "/ticket/" + pTicket,
				RDatosAutenticacion.class);

		final DatosUsuario datos = new DatosUsuario();
		datos.setMetodoAutenticacion(TipoMetodoAutenticacion.fromString(datosUtenticacion.getMetodoAutenticacion()));
		datos.setNif(datosUtenticacion.getNif());
		datos.setNombre(datosUtenticacion.getNombre());
		datos.setApellido1(datosUtenticacion.getApellido1());
		datos.setApellido2(datosUtenticacion.getApellido2());
		datos.setMetodoAutenticacion(TipoMetodoAutenticacion.fromString(datosUtenticacion.getMetodoAutenticacion()));
		if ("ANONIMO".equals(datosUtenticacion.getMetodoAutenticacion())) {
			datos.setAutenticacion(TipoAutenticacion.ANONIMO);
		} else {
			datos.setAutenticacion(TipoAutenticacion.AUTENTICADO);
		}
		return datos;
	}

	@Override
	public String iniciarSesionLogout(final String codigoEntidad, final String pIdioma, final String pCallback)
			throws AutenticacionPluginException {

		final RestTemplate restTemplate = new RestTemplate();

		final RLogoutParams param = new RLogoutParams();
		param.setAplicacion(getIdAplicacion());
		param.setEntidad(codigoEntidad);
		param.setUrlCallback(pCallback);
		param.setIdioma(pIdioma);
		param.setAplicacion("SISTRA2");
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		final HttpEntity<RLogoutParams> request = new HttpEntity<>(param, headers);
		final ResponseEntity<String> responseTramite = restTemplate.postForEntity(getUrl() + "/logout", request,
				String.class);

		return responseTramite.getBody();

	}

	/**
	 * Obtiene url de propiedades.
	 *
	 * @return url propiedades
	 * @throws AutenticacionPluginException
	 */
	private String getUrl() throws AutenticacionPluginException {
		final String url = this.getProperty("url");
		if (url == null) {
			throw new AutenticacionPluginException("No se ha especificado parametro url en propiedades");
		}
		return url;
	}

	/**
	 * Obtiene id aplicacion de propiedades.
	 *
	 * @return
	 * @throws AutenticacionPluginException
	 */
	protected String getIdAplicacion() throws AutenticacionPluginException {
		final String idApp = this.getProperty("idAplicacion");
		if (idApp == null) {
			throw new AutenticacionPluginException("No se ha especificado parametro idAplicacion en propiedades");
		}
		return idApp;
	}

}
