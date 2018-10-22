package es.caib.sistra2.commons.plugins.registro.regweb3;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import es.caib.regweb3.ws.api.v3.DatosInteresadoWs;
import es.caib.regweb3.ws.api.v3.RegWebInfoWs;
import es.caib.regweb3.ws.api.v3.RegWebInfoWsService;


/**
 * Utilidades Regweb3.
 * @author Indra
 *
 */
public class UtilsRegweb3 {

	/**
	 * Obtiene service registro salida.
	 * @return service registro salida
	 * @throws Exception
	 */
	public static RegWebInfoWs getRegistroInfoService(String entidad, String endpoint, String user, String pass) throws Exception  {

		// Url WSDL: local o remoto segun haya proxy
		URL wsdl = obtenerUrlWsdl(endpoint, "RegWebInfo");
        RegWebInfoWsService service = new RegWebInfoWsService(wsdl);

        RegWebInfoWs api = service.getRegWebInfoWs();

        // TODO: configurar servicio

       return api;
    }

	/**
	 * Url WSDL: local o remoto segun haya proxy
	 * @param endpoint
	 * @param serviceName
	 * @return
	 * @throws MalformedURLException
	 */
	private static URL obtenerUrlWsdl(final String endpoint, String serviceName)
			throws MalformedURLException {

		// TODO: Contemplar obtencion de WSDL en entornos con proxy
		URL wsdl = new URL(endpoint + "?wsdl");

		return wsdl;
	}

}
