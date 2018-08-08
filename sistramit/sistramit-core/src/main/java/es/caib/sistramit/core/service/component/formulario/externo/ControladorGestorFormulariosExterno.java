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

    // TODO Pendiente

    String iniciarSesion(DatosInicioSesionFormulario difi);

    DatosFinalizacionFormulario obtenerDatosFinalizacionFormulario(
            String pTicket);

}
