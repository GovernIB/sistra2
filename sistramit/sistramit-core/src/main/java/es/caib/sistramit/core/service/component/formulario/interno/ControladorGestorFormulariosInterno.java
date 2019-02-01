package es.caib.sistramit.core.service.component.formulario.interno;

import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;

/**
 * Interfaz del controlador de formularios internos.
 *
 * Contiene la lógica para iniciar y recoger resultado del gestor de formularios
 * interno.
 *
 * @author Indra
 *
 */
public interface ControladorGestorFormulariosInterno {

	/**
	 * Inicia sesión formulario.
	 *
	 * @param dis
	 *            Datos inicio sesión
	 * @return Ticket acceso formulario
	 */
	String iniciarSesion(DatosInicioSesionFormulario dis);

	/**
	 * Obtiene datos finalización formulario.
	 *
	 * @param tck
	 *            Ticket acceso a datos finalización sesión formulario.
	 * @return datos finalización formulario
	 */
	DatosFinalizacionFormulario obtenerDatosFinalizacionFormulario(String tck);

}
