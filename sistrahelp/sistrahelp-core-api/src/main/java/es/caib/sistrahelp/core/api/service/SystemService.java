package es.caib.sistrahelp.core.api.service;

import es.caib.sistrahelp.core.api.model.Sesion;

/**
 * La interface SystemService.
 */
public interface SystemService {

	/**
	 * Obtiene el valor de sesion.
	 *
	 * @param userName
	 *            usuario
	 * @return el valor de sesion
	 */
	public Sesion getSesion(String pUserName);

	/**
	 * Actualiza la lista de propiedades de la sesion de usuario.
	 *
	 * @param pUserName
	 *            usuario
	 * @param pPropiedades
	 *            propiedades de la sesión
	 */
	public void updateSesionPropiedades(String pUserName, String pPropiedades);

}
