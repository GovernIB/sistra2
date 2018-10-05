package es.caib.sistra2.commons.plugins.firmacliente.api;

import org.fundaciobit.pluginsib.core.IPlugin;

/**
 * Interface firma plugin.
 *
 * @author Indra
 *
 */
public interface IFirmaPlugin extends IPlugin {

	/** Prefix. */
	public static final String AUTENTICACION_BASE_PROPERTY = IPLUGIN_BASE_PROPERTIES + "firmacliente.";

	/**
	 * Permite generar una sesión de firma.
	 *
	 * @param infoSesionFirma
	 *            Datos relativos a la sesión de firma, como son el idioma, el
	 *            código de entidad, el NIF del firmante, nombre de usuario
	 *            firmante, código SIA del procedimiento,
	 * @return identificador de la sesión de firma
	 * @throws FirmaPluginException
	 */
	String generarSesionFirma(InfoSesionFirma infoSesionFirma) throws FirmaPluginException;

	/**
	 * Permite añadir un fichero para firmar
	 *
	 * @param ficheroAFirmar
	 *            datos del fichero a añadir para firma
	 * @throws FirmaPluginException
	 */
	void ficheroAFirmar(FicheroAFirmar ficheroAFirmar) throws FirmaPluginException;

	/**
	 * Permite iniciar la operación de firma
	 *
	 * @param idSesionFirma
	 *            identificador de la sesión de firma
	 * @param urlCallBack
	 *            URL de callback de la aplicación invocante a la que la pasarela de
	 *            firma deberá invocar una vez finalizada la realización de la
	 *            firma.
	 * @param paramAdic
	 *            parámetros adicionales según pasarela de firma
	 * @return urlPasarelaFirma : URL a la que se deberá redirigir el navegador del
	 *         usuario para ir la pasarela de firma
	 *
	 */
	String iniciarSesionFirma(String idSesionFirma, String urlCallBack, String paramAdic) throws FirmaPluginException;

	/**
	 * Permite obtener el estado de la sesión de firma
	 *
	 * @param idSesionFirma
	 *            identificador de la sesión de firma
	 * @return estado de la sesión de firma
	 */
	TypeEstadoFirmado obtenerEstadoSesionFirma(String idSesionFirma) throws FirmaPluginException;

	/**
	 * Permite obtener la firma de un fichero firmado.
	 *
	 * @param idSesionFirma
	 *            identificador de la sesión de firma
	 * @param idFicheroFirma
	 *            identificador de fichero a firmar
	 * @return
	 */
	FicheroFirmado obtenerFirmaFichero(String idSesionFirma, String idFicheroFirma) throws FirmaPluginException;

	/**
	 * Permite cerrar una sesión de firma
	 *
	 * @param idSesionFirma
	 *            identificador de la sesión de firma
	 */
	void cerrarSesionFirma(String idSesionFirma) throws FirmaPluginException;
}
