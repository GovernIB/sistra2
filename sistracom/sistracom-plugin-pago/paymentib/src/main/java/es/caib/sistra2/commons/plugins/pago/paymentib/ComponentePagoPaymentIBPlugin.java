package es.caib.sistra2.commons.plugins.pago.paymentib;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import es.caib.sistra2.commons.plugins.pago.api.DatosPago;
import es.caib.sistra2.commons.plugins.pago.api.IComponentePagoPlugin;
import es.caib.sistra2.commons.plugins.pago.api.PagoPluginException;
import es.caib.sistra2.commons.plugins.pago.api.RedireccionPago;
import es.caib.sistra2.commons.plugins.pago.api.TypeEstadoPago;

/**
 * Clase componente pago de paymentib.
 *
 * @author Indra
 *
 */
public class ComponentePagoPaymentIBPlugin extends AbstractPluginProperties implements IComponentePagoPlugin {

	/**
	 * Inicia pago electrónico.
	 *
	 * @param datosPago
	 *            Datos pago.
	 * @param urlCallback
	 *            Url callback.
	 * @return Redirección al pago (identificador pago + url)
	 */
	@Override
	public RedireccionPago iniciarPagoElectronico(final DatosPago datosPago, final String urlCallback)
			throws PagoPluginException {

		final RestTemplate restTemplate = new RestTemplate();

		// restTemplate.getInterceptors().add(new
		// BasicAuthorizationInterceptor(getUsr(), getPwd()));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("datosPago", "{\"aplicacionId\": \"" + datosPago.getAplicacionId() + "\",  \"concepto\": \""
				+ datosPago.getConcepto() + "\",  " + "\"detallePago\": \"" + datosPago.getDetallePago()
				+ "\",  \"entidadId\": \"" + datosPago.getEntidadId() + "\",  " + "\"idioma\": \""
				+ datosPago.getIdioma() + "\",	 \"importe\": " + datosPago.getImporte() + ",   " + "\"modelo\": \""
				+ datosPago.getModelo() + "\", \"organismoId\": \"" + datosPago.getOrganismoId() + "\",    "
				+ "\"pasarelaId\": \"" + datosPago.getPasarelaId() + "\",    \"sujetoPasivoNif\": \""
				+ datosPago.getSujetoPasivoNif() + "\"," + "\"sujetoPasivoNombre\": \""
				+ datosPago.getSujetoPasivoNombre() + "\", \"tasaId\": \"" + datosPago.getTasaId() + "\"  } ");
		map.add("urlCallback", urlCallback);

		// Obtener procedimiento.
		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);
		final ResponseEntity<RedireccionPago> response = restTemplate
				.postForEntity(getUrl() + "/iniciarPagoElectronico/", requestProc, RedireccionPago.class);

		return response.getBody();
	}

	/**
	 * Verifica estado pago contra pasarela de pago.
	 *
	 * @param identificador
	 *            identificador pago
	 * @return estado pago
	 */
	@Override
	public TypeEstadoPago verificarPagoElectronico(final String identificador) throws PagoPluginException {

		final RestTemplate restTemplate = new RestTemplate();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		// Obtener procedimiento.
		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);
		final ResponseEntity<String> response = restTemplate
				.postForEntity(getUrl() + "/verificarPagoElectronico/" + identificador, requestProc, String.class);

		return TypeEstadoPago.fromString(response.getBody());

	}

	/**
	 * Obtiene justificante de pago
	 *
	 * @param identificador
	 *            identificador pago
	 * @return Justificante de pago (nulo si la pasarela no genera justificante).
	 */
	@Override
	public byte[] obtenerJustificantePagoElectronico(final String identificador) throws PagoPluginException {

		final RestTemplate restTemplate = new RestTemplate();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		// Obtener procedimiento.
		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);
		final ResponseEntity<byte[]> response = restTemplate.postForEntity(
				getUrl() + "/obtenerJustificantePagoElectronico/" + identificador, requestProc, byte[].class);

		return response.getBody();

	}

	/**
	 * Obtiene importe tasa.
	 *
	 * @param idTasa
	 *            id tasa
	 * @return importe (en cents)
	 * @throws PagoPluginException
	 */
	@Override
	public int consultaTasa(final String idPasarela, final String idTasa) throws PagoPluginException {

		final RestTemplate restTemplate = new RestTemplate();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		// Obtener procedimiento.
		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);
		final ResponseEntity<Integer> response = restTemplate.postForEntity(
				getUrl() + "/consultaTasa/" + idPasarela + "/" + idTasa + "/", requestProc, Integer.class);

		return response.getBody();
	}

	/**
	 * Obtiene carta de pago presencial (PDF).
	 *
	 * @param datosPago
	 *            Datos pago
	 * @return carta de pago presencial
	 */
	@Override
	public byte[] obtenerCartaPagoPresencial(final DatosPago datosPago) throws PagoPluginException {
		final RestTemplate restTemplate = new RestTemplate();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("aplicacionId", datosPago.getAplicacionId());
		map.add("concepto", datosPago.getConcepto());
		map.add("detallePago", datosPago.getDetallePago());
		map.add("entidadId", datosPago.getEntidadId());
		map.add("idioma", datosPago.getIdioma());
		map.add("importe", String.valueOf(datosPago.getImporte()));
		map.add("modelo", datosPago.getModelo());
		map.add("organismoId", datosPago.getOrganismoId());
		map.add("pasarelaId", datosPago.getPasarelaId());
		map.add("sujetoPasivoNif", datosPago.getSujetoPasivoNif());
		map.add("sujetoPasivoNombre", datosPago.getSujetoPasivoNombre());
		map.add("tasaId", datosPago.getTasaId());

		// Obtener procedimiento.
		final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(map, headers);
		final ResponseEntity<byte[]> response = restTemplate.postForEntity(getUrl() + "/obtenerCartaPagoPresencial/",
				requestProc, byte[].class);

		return response.getBody();
	}

	/**
	 * Obtiene url de propiedades.
	 *
	 * @return url propiedades
	 * @throws AutenticacionPluginException
	 */
	private String getUrl() throws PagoPluginException {
		final String url = this.getProperty("url");
		if (url == null) {
			throw new PagoPluginException("No se ha especificado parametro url en propiedades");
		}
		return url;
	}

	/**
	 * Obtiene usuario de propiedades.
	 *
	 * @return url propiedades
	 * @throws AutenticacionPluginException
	 */
	private String getUsr() throws PagoPluginException {
		final String usr = this.getProperty("usr");
		if (usr == null) {
			throw new PagoPluginException("No se ha especificado parametro usuario en propiedades");
		}
		return usr;
	}

	/**
	 * Obtiene password de propiedades.
	 *
	 * @return url propiedades
	 * @throws AutenticacionPluginException
	 */
	private String getPwd() throws PagoPluginException {
		final String pwd = this.getProperty("pwd");
		if (pwd == null) {
			throw new PagoPluginException("No se ha especificado parametro password en propiedades");
		}
		return pwd;
	}
}
