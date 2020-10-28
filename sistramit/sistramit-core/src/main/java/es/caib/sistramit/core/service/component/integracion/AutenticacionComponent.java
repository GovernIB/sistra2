package es.caib.sistramit.core.service.component.integracion;

import java.util.List;

import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeQAA;
import es.caib.sistramit.core.service.model.integracion.DatosAutenticacionUsuario;

/**
 * Acceso a Componente Autenticación.
 *
 * @author Indra
 *
 */
public interface AutenticacionComponent {

	/**
	 * Inicia sesion autenticacion.
	 *
	 * @param codigoEntidad
	 *                          codigo entidad
	 * @param idioma
	 *                          Idioma
	 * @param metodos
	 *                          metodos auth
	 * @param qaa
	 *                          qaa
	 * @param callback
	 *                          callback
	 * @param callbackError
	 *                          callback error
	 * @param pDebugEnabled
	 *                          debug
	 * @return url inicio sesion
	 */
	String iniciarSesionAutenticacion(String codigoEntidad, String idioma, List<TypeMetodoAutenticacion> metodos,
			TypeQAA qaa, String callback, final String callbackError, final boolean pDebugEnabled);

	/**
	 * Validar ticket.
	 *
	 * @param pTicket
	 *                    ticket
	 * @return datos usuario
	 */
	DatosAutenticacionUsuario validarTicketAutenticacion(String pTicket);

	/**
	 * Iniciar sesión de logout.
	 *
	 * @param codigoEntidad
	 *                          codigo entidad
	 * @param pIdioma
	 *                          idioma
	 * @param pCallback
	 *                          url callback
	 * @param pDebugEnabled
	 *                          debug
	 * @return
	 */
	String iniciarSesionLogout(String codigoEntidad, String pIdioma, String pCallback, boolean pDebugEnabled);

}
