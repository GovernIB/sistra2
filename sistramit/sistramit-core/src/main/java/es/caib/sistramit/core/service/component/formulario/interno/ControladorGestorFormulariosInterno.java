package es.caib.sistramit.core.service.component.formulario.interno;

import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;

/**
 * Interfaz del controlador de formularios internos.
 *
 * Contiene la lógica del gestor de formularios interno.
 *
 * @author Indra
 *
 */
public interface ControladorGestorFormulariosInterno {

    // TODO Pendiente

    String iniciarSesion(DatosInicioSesionFormulario difi);

    DatosFinalizacionFormulario obtenerDatosFinalizacionFormulario(
            String pTicket);

}
