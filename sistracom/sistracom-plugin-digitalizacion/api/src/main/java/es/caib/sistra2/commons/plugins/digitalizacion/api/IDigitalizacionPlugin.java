package es.caib.sistra2.commons.plugins.digitalizacion.api;

import org.fundaciobit.pluginsib.core.IPlugin;

/**
 * Interface digitalización plugin.
 *
 * @author Indra
 *
 */
public interface IDigitalizacionPlugin extends IPlugin {

	/** Prefix. */
	public static final String DIGITALIZACION_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES + "digitalizacion.";

	/**
	 * Permite generar una sesión de digitalización.
	 *
	 * @param infoSesionDigitalizacion
	 *                            Datos relativos a la sesión de digitalización
	 * @return identificador de la sesión de digitalizacion
	 * @throws DigitalizacionPluginException
	 */
	String generarSesionDigitalizacion(InfoSesionDigitalizacion infoSesionDigitalizacion) throws DigitalizacionPluginException;


	/**
	 * Permite iniciar la operación de digitalización.
	 *
	 * @param idSesionDigitalizacion
	 *                          identificador de la sesión de digitalizacion
	 * @param urlCallBack
	 *                          URL de callback de la aplicación invocante a la que
	 *                          la pasarela de digitalización deberá invocar una vez
	 *                          finalizada la realización de la digitalizacion.
	 * @return urlPasarelaDigitalizacion : URL a la que se deberá redirigir el navegador del
	 *         usuario para ir la pasarela de digitalizacion
	 *
	 */
	String iniciarSesionDigitalizacion(String idSesionDigitalizacion, String urlCallBack) throws DigitalizacionPluginException;

	/**
	 * Permite obtener el resultado de la sesión de digitalizacion
	 *
	 * @param idSesionDigitalizacion
	 *                          identificador de la sesión de digitalizacion
	 * @return resultado de la sesión de digitalizacion
	 */
	ResultadoDigitalizacion obtenerResultadoDigitalizacion(String idSesionDigitalizacion) throws DigitalizacionPluginException;

	boolean isIframe() throws DigitalizacionPluginException;
}
