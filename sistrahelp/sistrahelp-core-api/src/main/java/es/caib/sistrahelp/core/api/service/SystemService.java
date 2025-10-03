package es.caib.sistrahelp.core.api.service;

import java.util.List;

import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.model.Sesion;

/**
 * La interface SystemService.
 */
public interface SystemService {

	/**
	 * Obtiene el valor de sesion.
	 *
	 * @param pUserName
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
	 void updateSesionPropiedades(String pUserName, String pPropiedades);

	/**
	 * Verifica si es maestro
	 *
	 * @param instancia instancia
	 * @return si es maestro
	 */
	boolean verificarMaestro(String instancia);

	public List<Alerta> calcularAlertasEjecucion();

	/**
	 * Actualizar fecha acceso del usuario.
	 * @param pUserName
	 */
    void actualizarFechaAcceso(String pUserName);
}
