package es.caib.sistra2.commons.ws.utils;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WsClientUtil {

	/** Log. */
	private final static Logger log = LoggerFactory.getLogger(WsClientUtil.class);

	public static void configurePort(final BindingProvider port, final String url, final String user, final String pass,
			final String auth, final Long timeout, final boolean logCalls) throws Exception {

		final Client client = ClientProxy.getClient(port);
		final HTTPConduit conduit = (HTTPConduit) client.getConduit();
		final HTTPClientPolicy httpClientPolicy = conduit.getClient();

		// Timeout
		if (timeout != null) {
			httpClientPolicy.setConnectionTimeout(timeout);
		}

		// Log calls
		if (logCalls) {
			client.getInInterceptors().add(new LoggingInInterceptor());
			client.getOutInterceptors().add(new LoggingOutInterceptor());
		}

		// Autenticación
		if (user != null) {
			if ("BASIC".equals(auth)) {
				port.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, user);
				port.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, pass);
			} else {
				throw new Exception("Tipo de autenticacion ws no soportada: " + auth);
			}
		} else {
			log.debug("No se establece autenticacion ya que el usuario es nulo");
		}

		// Proxy
		final String proxyHost = System.getProperty("http.proxyHost");
		if (proxyHost != null && !"".equals(proxyHost)) {
			if (!validateNonProxyHosts(url)) {

				log.debug("Estableciendo autenticacion para proxy");

				httpClientPolicy.setProxyServer(proxyHost);
				httpClientPolicy.setProxyServerPort(Integer.parseInt(System.getProperty("http.proxyPort")));

				conduit.getProxyAuthorization().setUserName(System.getProperty("http.proxyUser"));
				conduit.getProxyAuthorization().setPassword(System.getProperty("http.proxyPassword"));
			}
		}

	}

	/**
	 * Busca els host de la url indicada dentro de la propiedad http.nonProxyHosts
	 * de la JVM
	 *
	 * @param url
	 *                Endpoint del ws
	 * @return true si el host esta dentro de la propiedad, fals en caso contrario
	 */
	private static boolean validateNonProxyHosts(final String url) throws Exception {
		final String nonProxyHosts = System.getProperty("http.nonProxyHosts");
		boolean existe = false;
		URL urlURL;
		try {
			if (nonProxyHosts != null && !"".equals(nonProxyHosts)) {
				urlURL = new URL(url);
				final String[] nonProxyHostsArray = nonProxyHosts.split("\\|");
				for (int i = 0; i < nonProxyHostsArray.length; i++) {
					final String a = nonProxyHostsArray[i].replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
					;
					if (urlURL.getHost().matches(a)) {
						existe = true;
						break;
					}
				}
			}
		} catch (final MalformedURLException e) {
			log.error("Error al validar los nonProxyHost " + e.getCause(), e);
			throw e;
		}
		return existe;
	}

}
