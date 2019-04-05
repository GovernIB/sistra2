package es.caib.sistrages.core.api.service;

import es.caib.sistrages.core.api.model.Sesion;

/**
 * La interface SystemService.
 */
public interface SystemService {

	/**
	 * Purgar ficheros.
	 */
	public void purgarFicheros();

	/**
	 * Obtener propiedad configuracion.
	 *
	 * @param propiedad
	 *            propiedad
	 * @return the string
	 */
	public String obtenerPropiedadConfiguracion(String propiedad);

	/**
	 * Verificar maestro.
	 *
	 * @param appId
	 *            app id
	 * @return true, if successful
	 */
	boolean verificarMaestro(String appId);

	/**
	 * Obtiene el valor de sesion.
	 *
	 * @param userName
	 *            usuario
	 * @return el valor de sesion
	 */
	public Sesion getSesion(String pUserName);

	/**
	 * Actualiza el perfil de la sesion de usuario.
	 *
	 * @param pUserName
	 *            usuario
	 * @param pPerfil
	 *            perfil
	 */
	public void updateSesionPerfil(String pUserName, String pPerfil);

	/**
	 * Actualiza el idioma de la sesion de usuario..
	 *
	 * @param pUserName
	 *            usuario
	 * @param pIdioma
	 *            idioma
	 */
	public void updateSesionIdioma(String pUserName, String pIdioma);

	/**
	 * Actualiza la entidad de la sesion de usuario.
	 *
	 * @param pUserName
	 *            usuario
	 * @param idEntidad
	 *            idEntidad de la entidad
	 */
	public void updateSesionEntidad(String pUserName, Long pIdEntidad);

}
