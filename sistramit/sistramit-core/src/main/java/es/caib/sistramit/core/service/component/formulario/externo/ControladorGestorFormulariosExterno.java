package es.caib.sistramit.core.service.component.formulario.externo;

import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;

/**
 * Interfaz del controlador de formularios externo.
 *
 * Contiene la lógica del gestor de formularios externo.
 *
 * @author Indra
 *
 */
public interface ControladorGestorFormulariosExterno {

	/**
	 * Inicia sesión formulario en Gestor Formularios Externo.
	 * 
	 * @param difi
	 *                 Datos inicio sesión formulario
	 * @return url redirección a Gestor Formularios Externo
	 */
	String iniciarSesion(DatosInicioSesionFormulario difi);

	/**
	 * Recupera datos del formulario finalizado del Gestor Formularios Externo a
	 * partir de un ticket de acceso.
	 * 
	 * @param pTicket
	 *                    Ticket
	 * @return datos formulario finalizado
	 */
	DatosFinalizacionFormulario obtenerDatosFinalizacionFormulario(String pTicket);

}
