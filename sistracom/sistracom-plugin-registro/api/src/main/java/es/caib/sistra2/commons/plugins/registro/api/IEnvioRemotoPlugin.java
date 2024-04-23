package es.caib.sistra2.commons.plugins.registro.api;

import org.fundaciobit.pluginsib.core.IPlugin;

/**
 * Interface envío remoto.
 *
 * @author Indra
 *
 */
public interface IEnvioRemotoPlugin extends IPlugin {

	/** Prefix. */
	public static final String REGISTRO_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES + "envio.";

	/**
	 * Inicia sesión de envío. Mediante la sesión de envío se permitirá tener la
	 * trazabilidad posterior del resultado del envío en caso de que haya habido
	 * problemas.
	 *
	 * @param destinoEnvio
	 *                         destino envio
	 *
	 * @return Id sesión envío
	 *
	 * @throws EnvioRemotoPluginException
	 */
	String iniciarSesionEnvio(DestinoEnvio destinoEnvio) throws EnvioRemotoPluginException;

	/**
	 * Realiza un envío.
	 *
	 * @param destinoEnvio
	 *                             destino envio
	 *
	 * @param idSesionEnvio
	 *                             Identificador sesión envío
	 *
	 * @param asientoRegistral
	 *                             Asiento con los datos requeridos para el envío
	 * @throws EnvioRemotoPluginException
	 */
	ResultadoRegistro realizarEnvio(DestinoEnvio destinoEnvio, String idSesionEnvio, DatosTramitacion datosTramitacion, AsientoRegistral asientoRegistral)
			throws EnvioRemotoPluginException;

	/**
	 * Verifica si se ha realizado envío.
	 *
	 * @param destinoEnvio
	 *                          destino envio
	 *
	 * @param idSesionEnvio
	 *                          id sesión registro
	 * @return verificación envío
	 *
	 * @throws EnvioRemotoPluginException
	 */
	VerificacionRegistro verificarEnvio(DestinoEnvio destinoEnvio, String idSesionEnvio)
			throws EnvioRemotoPluginException;

}
