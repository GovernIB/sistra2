package es.caib.sistramit.core.service.component.formulario.externo;

import org.springframework.stereotype.Component;

import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;

/**
 * Interfaz del controlador de formularios externo.
 *
 * Contiene la lógica del gestor de formularios externo..
 *
 * @author Indra
 */
@Component("controladorGestorFormulariosExterno")
public final class ControladorGestorFormulariosExternoImpl
        implements ControladorGestorFormulariosExterno {

    // TODO Pendiente

    @Override
    public String iniciarSesion(DatosInicioSesionFormulario difi) {
        // TODO Auto-generated method stub
        throw new RuntimeException("PENDIENTE IMPLEMENTAR");
    }

    @Override
    public DatosFinalizacionFormulario obtenerDatosFinalizacionFormulario(
            String pTicket) {
        // TODO Auto-generated method stub
        throw new RuntimeException("PENDIENTE IMPLEMENTAR");
    }

}
