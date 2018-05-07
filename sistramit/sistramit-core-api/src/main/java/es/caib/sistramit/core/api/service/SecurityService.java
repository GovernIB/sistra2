package es.caib.sistramit.core.api.service;

import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.SesionInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

/**
 * Servicio para verificar accesos de seguridad.
 *
 * @author Indra
 *
 */
public interface SecurityService {

	/**
	 * Obtiene información para mostrar el login.
	 *
	 * @param codigoTramite
	 *            código trámite
	 * @param versionTramite
	 *            versión trámite
	 * @param idTramiteCatalogo
	 *            id trámite en catálogo trámites
	 * @param idioma
	 *            idioma
	 * @param urlInicioTramite
	 *            url inicio trámite
	 * @return información para mostrar login
	 */
	InfoLoginTramite obtenerInfoLoginTramite(String codigoTramite, int versionTramite, String idTramiteCatalogo,
			String idioma, String urlInicioTramite);

	/**
	 * Inicio sesión en Clave.
	 *
	 * @param lang
	 *            idioma
	 * @param urlCallback
	 *            url callback asistente
	 * @return url redireccion a Clave
	 */
	String iniciarSesionClave(String lang, String urlCallback);

	/**
	 * Cierre sesión en Clave.
	 *
	 * @param lang
	 *            idioma
	 * @param urlCallback
	 *            url callback asistente
	 * @return url redireccion a Clave
	 */
	String iniciarLogoutSesionClave(String lang, String urlCallback);

	/**
	 * Valida usuario anónimo.
	 *
	 * @param sesionInfo
	 *            Info sesión
	 * @return info usuario
	 */
	UsuarioAutenticadoInfo validarUsuarioAnonimo(SesionInfo sesionInfo);

	/**
	 * Valida acceso usuario desde Carpeta Ciudadana.
	 *
	 * @param sesionInfo
	 *            Info sesión
	 * @param ticket
	 *            Ticket
	 * @return info usuario
	 */
	UsuarioAutenticadoInfo validarTicketCarpetaCiudadana(SesionInfo sesionInfo, String ticket);

	/**
	 * Valida acceso usuario desde Clave.
	 *
	 * @param sesionInfo
	 *            Info sesión
	 * @param ticket
	 *            Ticket
	 * @return info usuario
	 */
	UsuarioAutenticadoInfo validarTicketClave(SesionInfo sesionInfo, String ticket);

	/**
	 * Valida acceso usuario desde Gestor Formularios.
	 *
	 * @param sesionInfo
	 *            Info sesión
	 * @param ticket
	 *            Ticket
	 * @return info usuario
	 */
	UsuarioAutenticadoInfo validarTicketGestorFormularios(SesionInfo sesionInfo, String ticket);

	/**
	 * Valida acceso usuario desde Pasarela de Pagos.
	 *
	 * @param sesionInfo
	 *            Info sesión
	 * @param ticket
	 *            Ticket
	 * @return info usuario
	 */
	UsuarioAutenticadoInfo validarTicketPasarelaPagos(SesionInfo sesionInfo, String ticket);

}
