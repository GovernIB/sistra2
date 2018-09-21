package es.caib.sistramit.core.service.component.formulario.interno;

import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;

/**
 * Controlador de formularios internos.
 *
 * Contiene la lógica del gestor de formularios interno.
 *
 * @author Indra
 */
@Component("controladorGestorFormulariosInterno")
public final class ControladorGestorFormulariosInternoImpl
        implements ControladorGestorFormulariosInterno {

    // TODO Pendiente

    @Override
    public String iniciarSesion(DatosInicioSesionFormulario difi) {
        // TODO Simulacion rellenar
        return "/sistramitfront/asistente/rf/simularRellenarFormulario.html?idPaso="
                + difi.getIdPaso() + "&idFormulario=" + difi.getIdFormulario();
    }

    @Override
    public DatosFinalizacionFormulario obtenerDatosFinalizacionFormulario(
            String pTicket) {
        // TODO Auto-generated method stub
        try {
            final DatosFinalizacionFormulario dff = new DatosFinalizacionFormulario();
            dff.setFechaFinalizacion(new Date());
            dff.setUsadoRetorno(true);
            dff.setCancelado(false);
            dff.setXml(SimulacionFormularioMock.getDatosSimulados()
                    .getBytes("UTF-8"));
            dff.setPdf(IOUtils.toByteArray(this.getClass().getClassLoader()
                    .getResourceAsStream("formulario.pdf")));

            return dff;
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
