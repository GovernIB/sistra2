package es.caib.sistramit.core.api.service;

import es.caib.sistramit.core.api.model.security.InfoLoginTramite;

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

}
