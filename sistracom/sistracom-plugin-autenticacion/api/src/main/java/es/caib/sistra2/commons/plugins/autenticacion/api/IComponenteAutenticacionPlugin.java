package es.caib.sistra2.commons.plugins.autenticacion.api;

import java.util.List;

import org.fundaciobit.pluginsib.core.IPlugin;

/**
 * Interface componente autenticación.
 *
 * @author Indra
 *
 */
public interface IComponenteAutenticacionPlugin extends IPlugin {

	/** Prefix. */
	public static final String AUTENTICACION_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES + "login.";

	/**
	 * Inicia sesion es.caib.sistra2.commons.plugins.autenticacion.
	 *
	 * @param codigoEntidad
	 *            codigo entidad
	 * @param idioma
	 *            Idioma
	 * @param metodos
	 *            metodos auth
	 * @param qaa
	 *            qaa
	 * @param urlCallback
	 *            url callback en caso de autenticación correcta (se pasará
	 *            parametro 'ticket' para recuperar los datos de autenticación)
	 * @param urlCallbackError
	 *            url callback en caso de autenticación incorrecta (no se pasará
	 *            ningún parámetro)
	 * @return url inicio sesion
	 */
	String iniciarSesionAutenticacion(String codigoEntidad, String idioma, List<TipoAutenticacion> metodos, String qaa,
			String urlCallback, String urlCallbackError) throws AutenticacionPluginException;

	/**
	 * Validar ticket.
	 *
	 * @param ticket
	 *            ticket
	 * @return datos usuario
	 */
	DatosUsuario validarTicketAutenticacion(String ticket) throws AutenticacionPluginException;

	/**
	 * Iniciar sesión de logout.
	 *
	 * @param codigoEntidad
	 *            codigo entidad
	 * @param idioma
	 *            idioma
	 * @param urlCallback
	 *            url callback
	 * @return
	 */
	String iniciarSesionLogout(String codigoEntidad, String idioma, String urlCallback)
			throws AutenticacionPluginException;

}
