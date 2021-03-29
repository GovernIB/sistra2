package es.caib.sistra2.commons.plugins.formulario.rest;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import es.caib.sistra2.commons.plugins.formulario.api.DatosInicioFormulario;
import es.caib.sistra2.commons.plugins.formulario.api.DatosRetornoFormulario;
import es.caib.sistra2.commons.plugins.formulario.api.FormularioPluginException;
import es.caib.sistra2.commons.plugins.formulario.api.IFormularioPlugin;
import es.caib.sistra2.commons.plugins.formulario.api.ParametroFormulario;

/**
 * Plugin formulario REST.
 *
 * @author Indra
 *
 */
public class FormularioPluginRest extends AbstractPluginProperties implements IFormularioPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "rest.";

	/** Propiedades. */
	private static final String BASE_PROPERTY_USER = ".USER";
	private static final String BASE_PROPERTY_PASS = ".PASS";

	/** Operaciones REST. */
	private static final String REST_OPERACION_INVOCAR_FORMULARIO = "/formulario";
	private static final String REST_OPERACION_RESULTADO_FORMULARIO = "/resultado";

	public FormularioPluginRest(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public String invocarFormulario(final String idGestorFormulario, final String urlGestor,
			final DatosInicioFormulario datosInicio) throws FormularioPluginException {

		final String user = getPropiedad(idGestorFormulario, BASE_PROPERTY_USER);
		final String pass = getPropiedad(idGestorFormulario, BASE_PROPERTY_PASS);

		final RestTemplate restTemplate = new RestTemplate();

		if (user != null && pass != null) {
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(user, pass));
		}

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		final RDatosInicioFormulario rdi = convertRDatosInicioFormulario(datosInicio);

		final HttpEntity<RDatosInicioFormulario> request = new HttpEntity<>(rdi, headers);

		final ResponseEntity<String> response = restTemplate
				.postForEntity(urlGestor + REST_OPERACION_INVOCAR_FORMULARIO, request, String.class);

		if (response == null) {
			throw new FormularioPluginException("Respuesta vacía.");
		}

		return response.getBody();

	}

	@Override
	public DatosRetornoFormulario obtenerResultadoFormulario(final String idGestorFormulario, final String urlGestor,
			final String ticket) throws FormularioPluginException {

		final String user = getPropiedad(idGestorFormulario, BASE_PROPERTY_USER);
		final String pass = getPropiedad(idGestorFormulario, BASE_PROPERTY_PASS);

		final RestTemplate restTemplate = new RestTemplate();

		if (user != null && pass != null) {
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(user, pass));
		}

		/*
		 * // Si se pasara por post final MultiValueMap<String, String> map = new
		 * LinkedMultiValueMap<>(); map.add("ticket", ticket); final HttpHeaders headers
		 * = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); final
		 * HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map,
		 * headers); final ResponseEntity<RDatosRetornoFormulario> response =
		 * restTemplate .postForEntity(urlGestor + REST_OPERACION_RESULTADO_FORMULARIO +
		 * "/" + ticket, request, RDatosRetornoFormulario.class);
		 */

		final RDatosRetornoFormulario response = restTemplate.getForObject(
				urlGestor + REST_OPERACION_RESULTADO_FORMULARIO + "/" + ticket, RDatosRetornoFormulario.class);

		if (response == null) {
			throw new FormularioPluginException("Respuesta vacía.");
		}

		return convertDatosRetornoFormulario(response);

	}

	/**
	 * Convierte retorno formulario.
	 *
	 * @param rdr
	 *                datos retornados por rest
	 * @return retorno formulario
	 */
	private DatosRetornoFormulario convertDatosRetornoFormulario(final RDatosRetornoFormulario rdr) {
		final DatosRetornoFormulario res = new DatosRetornoFormulario();
		res.setIdSesionFormulario(rdr.getIdSesionFormulario());
		res.setCancelado(rdr.isCancelado());
		res.setXml(Base64.getDecoder().decode(rdr.getXml()));
		res.setPdf(Base64.getDecoder().decode(rdr.getPdf()));
		return res;
	}

	/**
	 * Obtiene propiedad.
	 *
	 * @param propiedad
	 *                      propiedad
	 * @return valor
	 * @throws AutenticacionPluginException
	 */
	private String getPropiedad(final String idGestorFormulario, final String propiedad) {
		String valor = null;
		final String login = getProperty(FORMULARIO_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + "GESTORFORMULARIO."
				+ idGestorFormulario + ".LOGIN");
		if (login != null) {
			valor = getProperty(FORMULARIO_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + "LOGIN." + login + propiedad);
		}
		return valor;
	}

	/**
	 * Convierte a modelo REST.
	 *
	 * @param datosInicio
	 *                        Datos inicio
	 * @return RDatosInicioFormulario
	 */
	protected RDatosInicioFormulario convertRDatosInicioFormulario(final DatosInicioFormulario datosInicio) {
		final List<RParametroFormulario> rpa = new ArrayList<>();
		if (datosInicio.getParametrosApertura() != null) {
			for (final ParametroFormulario p : datosInicio.getParametrosApertura()) {
				final RParametroFormulario rp = new RParametroFormulario();
				rp.setCodigo(p.getCodigo());
				rp.setValor(p.getValor());
				rpa.add(rp);
			}
		}

		RUsuarioInfo rus = null;
		if (datosInicio.getUsuario() != null) {
			rus = new RUsuarioInfo();
			rus.setNif(datosInicio.getUsuario().getNif());
			rus.setNombre(datosInicio.getUsuario().getNombre());
			rus.setApellido1(datosInicio.getUsuario().getApellido1());
			rus.setApellido2(datosInicio.getUsuario().getApellido2());
		}

		final RDatosInicioFormulario rdi = new RDatosInicioFormulario();
		rdi.setEntidad(datosInicio.getEntidad());
		rdi.setIdFormularioExterno(datosInicio.getIdFormularioExterno());
		rdi.setIdioma(datosInicio.getIdioma());
		rdi.setIdSesionFormulario(datosInicio.getIdSesionFormulario());
		rdi.setParametrosApertura(rpa);
		rdi.setUrlCallback(datosInicio.getUrlCallback());
		rdi.setUsuario(rus);
		rdi.setXmlDatosActuales(Base64.getEncoder().encodeToString(datosInicio.getXmlDatosActuales()));

		return rdi;
	}
}
