package es.caib.sistramit.core.service.repository.dao;

import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;

/**
 * Interfaz de acceso a base de datos para los datos de sesión de formulario
 * (interno y externo).
 *
 * @author Indra
 *
 */
public interface FormularioDao {

	/**
	 * Crea sesión de formulario.
	 *
	 * @param datosInicioSesion
	 *                              Datos inicio de sesión.
	 * @return Devuelve ticket de sesión.
	 */
	String crearSesionGestorFormularios(DatosInicioSesionFormulario datosInicioSesion);

	/**
	 * Obtiene los datos pasados en el inicio de sesión.
	 *
	 * @param ticket
	 *                    Ticket
	 * @param interno
	 *                    indica si busca por ticket interno o externo
	 * @return Datos inicio sesión
	 */
	DatosInicioSesionFormulario obtenerDatosInicioSesionGestorFormularios(String ticket, boolean interno);

	/**
	 * Finaliza sesión de formulario en el gestor.
	 *
	 * @param ticket
	 *                           Ticket de sesión
	 * @param datosFinSesion
	 *                           Datos de fin de sesión
	 */
	void finalizarSesionGestorFormularios(String ticket, DatosFinalizacionFormulario datosFinSesion);

	/**
	 * Obtiene los datos pasados al finalizar la sesión.
	 *
	 * @param ticket
	 *                    Ticket
	 * @param interno
	 *                    indica si es interno
	 * @return Datos fin sesión
	 */
	DatosFinalizacionFormulario obtenerDatosFinSesionGestorFormularios(String ticket, boolean interno);

}
