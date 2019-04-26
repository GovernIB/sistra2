package es.caib.sistra2.commons.plugins.registro.regweb3;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import javax.xml.ws.BindingProvider;

import es.caib.regweb3.ws.api.v3.AsientoRegistralWs;
import es.caib.regweb3.ws.api.v3.DatosInteresadoWs;
import es.caib.regweb3.ws.api.v3.RegWebInfoWs;
import es.caib.regweb3.ws.api.v3.RegWebInfoWsService;
import es.caib.regweb3.ws.api.v3.RegWebRegistroEntradaWs;
import es.caib.regweb3.ws.api.v3.RegWebRegistroEntradaWsService;
import es.caib.regweb3.ws.api.v3.RegWebRegistroSalidaWs;
import es.caib.regweb3.ws.api.v3.RegWebRegistroSalidaWsService;
import es.caib.regweb3.ws.api.v3.RegWebAsientoRegistralWs;
import es.caib.regweb3.ws.api.v3.RegWebAsientoRegistralWsService;
import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.Interesado;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeDocumentoIdentificacion;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeInteresado;
import es.caib.sistra2.commons.ws.utils.WsClientUtil;

import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistra2.commons.utils.NifUtils;


/**
 * Utilidades Regweb3.
 * @author Indra
 *
 */
public class UtilsRegweb3 {


	/**
	 * Obtiene service registro entrada.
	 * @return service registro entrada
	 * @throws Exception
	 */
	public static RegWebRegistroEntradaWs getRegistroEntradaService(String entidad, String endpoint, String wsdlDir,
			String user, String pass, boolean logCalls) throws Exception  {

		URL wsdl = obtenerUrlWsdl(endpoint, wsdlDir, "RegWebRegistroEntrada");
        RegWebRegistroEntradaWsService service = new RegWebRegistroEntradaWsService(wsdl);

        RegWebRegistroEntradaWs api = service.getRegWebRegistroEntradaWs();

        configurarService((BindingProvider) api, endpoint, user, pass, logCalls);

       return api;
    }

	/**
	 * Obtiene service registro salida.
	 * @return service registro salida
	 * @throws Exception
	 */
	public static RegWebRegistroSalidaWs getRegistroSalidaService(String entidad, String endpoint, String wsdlDir,
			String user, String pass, boolean logCalls) throws Exception  {

        URL wsdl = obtenerUrlWsdl(endpoint, wsdlDir, "RegWebRegistroSalida");
        RegWebRegistroSalidaWsService service = new RegWebRegistroSalidaWsService(wsdl);

        RegWebRegistroSalidaWs api = service.getRegWebRegistroSalidaWs();

        configurarService((BindingProvider) api, endpoint, user, pass, logCalls);

       return api;
    }

	/**
	 * Obtiene service asiento registral.
	 * @return service asiento registral
	 * @throws Exception
	 */
	public static RegWebAsientoRegistralWs getAsientoRegistralService(String entidad, String endpoint, String wsdlDir,
			String user, String pass, boolean logCalls) throws Exception  {

        URL wsdl = obtenerUrlWsdl(endpoint, wsdlDir, "RegWebAsientoRegistral");
        RegWebAsientoRegistralWsService service = new RegWebAsientoRegistralWsService(wsdl);

        RegWebAsientoRegistralWs api = service.getRegWebAsientoRegistralWs();

        configurarService((BindingProvider) api, endpoint, user, pass, logCalls);

       return api;
    }


	/**
	 * Obtiene service registro info.
	 * @return service registro info
	 * @throws Exception
	 */
	public static RegWebInfoWs getRegistroInfoService(String entidad, String endpoint, String wsdlDir,
			String user, String pass, boolean logCalls) throws Exception  {

		// Url WSDL: local o remoto segun haya proxy
		URL wsdl = obtenerUrlWsdl(endpoint, wsdlDir, "RegWebInfo");
        RegWebInfoWsService service = new RegWebInfoWsService(wsdl);

        RegWebInfoWs api = service.getRegWebInfoWs();

        configurarService((BindingProvider) api, endpoint, user, pass, logCalls);

       return api;
    }

	/**
	 * Url WSDL: local o remoto segun haya proxy
	 * @param endpoint
	 * @param serviceName
	 * @return
	 * @throws MalformedURLException
	 */
	private static URL obtenerUrlWsdl(final String endpoint, String wsdlDir, String serviceName)
			throws MalformedURLException {

		URL wsdl = null;
		if (StringUtils.isNotBlank(System.getProperty("http.proxyHost"))) {
			wsdl = new URL("file://" + wsdlDir  + "/" + serviceName + ".wsdl");
		} else {
			wsdl = new URL(endpoint + "?wsdl");
		}
		return wsdl;
	}

	/**
	 * Configura service.
	 * @param bp Binding Provider
	 * @param endpoint Endpoint ws
	 * @param user usuario
	 * @param pass password
	 */
	private static void configurarService(BindingProvider bp, String endpoint,
			String user, String pass, boolean logCalls) throws Exception {

		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);

		WsClientUtil.configurePort(bp, endpoint, user, pass, "BASIC", logCalls);

	}

	/**
	 * Obtiene tipo interesado.
	 * @param documentoIdentificacion documento identicacion
	 * @return tipo interesado
	 */
	public static String getTipoInteresado(String documentoIdentificacion) {
		String result = null;

		if (NifUtils.esNifPersonaFisica(documentoIdentificacion)) {
			result = ConstantesRegweb3.TIPO_INTERESADO_PERSONA_FISICA;
		}else if (NifUtils.esNifPersonaFisica(documentoIdentificacion)) {
			result = ConstantesRegweb3.TIPO_INTERESADO_PERSONA_JURIDICA;
		}

		return result;
	}

	/**
	 * Obtiene tipo documento identificacion.
	 * @param documentoIdentificacion documento identicacion
	 * @return tipo documento identificacion.
	 */
	public static String getTipoDocumentoIdentificacion(String documentoIdentificacion) {
		String result = null;

		if (NifUtils.esDni(documentoIdentificacion) || NifUtils.esNifOtros(documentoIdentificacion)) {
			result = ConstantesRegweb3.TIPO_DOCID_NIF;
		} else if (NifUtils.esNie(documentoIdentificacion)) {
			result = ConstantesRegweb3.TIPO_DOCID_NIE;
		} else if (NifUtils.esNifPersonaJuridica(documentoIdentificacion)) {
			result = ConstantesRegweb3.TIPO_DOCID_CIF;
		}

		return result;
	}

	/**
	 * Obtener datos interesado asiento.
	 * @param asiento asiento
	 * @param tipoInteresado tipo interesado (RPT/RPD).
	 *
	 * @return interesado
	 */
	public static Interesado obtenerDatosInteresadoAsiento(
			AsientoRegistral asiento, TypeInteresado tipoInteresado) {
		Interesado result = null;
		for (Iterator it = asiento.getInteresados().iterator(); it.hasNext();) {
			Interesado datosInteresado = (Interesado) it.next();
			if (datosInteresado.getActuaComo() == tipoInteresado) {
				result = datosInteresado;
				break;
			}
		}
		return result;
	}

	/**
	 * Crear interesado a partir datos asiento.
	 *
	 * @param interesadoAsiento
	 * @return
	 * @throws Exception
	 */
	public static DatosInteresadoWs crearInteresado(
			Interesado interesadoAsiento) {
		DatosInteresadoWs interesado = new DatosInteresadoWs();

		TypeDocumentoIdentificacion tipoDocumento = interesadoAsiento.getTipoDocumento();

		if (StringUtils.isNotBlank(interesadoAsiento.getDocIdentificacion())) {
			interesado.setTipoInteresado(new Long(UtilsRegweb3
					.getTipoInteresado(interesadoAsiento
							.getDocIdentificacion())));
			interesado
					.setDocumento(interesadoAsiento.getDocIdentificacion());
			interesado.setTipoDocumentoIdentificacion(tipoDocumento.toString());
		} else {
			interesado.setTipoInteresado(new Long(
					ConstantesRegweb3.TIPO_INTERESADO_PERSONA_FISICA));
		}

		if (interesado.getTipoInteresado().longValue() == Long
				.parseLong(ConstantesRegweb3.TIPO_INTERESADO_PERSONA_JURIDICA)) {
			interesado.setRazonSocial(interesadoAsiento.getRazonSocial());
		} else {
			interesado.setNombre(interesadoAsiento.getNombre());
			interesado.setApellido1(interesadoAsiento.getApellido1());
			interesado.setApellido2(interesadoAsiento.getApellido2());
		}

		interesado.setPais(interesadoAsiento.getPais());
		interesado.setProvincia(interesadoAsiento.getProvincia());
		interesado.setLocalidad(interesadoAsiento.getMunicipio());
		interesado.setDireccion(interesadoAsiento.getDireccion());
		interesado.setCp(interesadoAsiento.getCodigoPostal());
		interesado.setEmail(interesadoAsiento.getEmail());
		interesado.setTelefono(interesadoAsiento.getTelefono());
		interesado.setDireccionElectronica(interesadoAsiento.getDireccionElectronica());
		if (interesadoAsiento.getCanal() != null) {
			interesado.setCanal(Long.parseLong(interesadoAsiento.getCanal().toString()));
		}

		interesado.setObservaciones(interesadoAsiento.getObservaciones());

		return interesado;
	}

}
