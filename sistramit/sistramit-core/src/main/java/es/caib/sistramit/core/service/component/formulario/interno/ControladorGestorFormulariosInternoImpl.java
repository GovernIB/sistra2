package es.caib.sistramit.core.service.component.formulario.interno;

import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;
import es.caib.sistramit.core.service.repository.dao.FormularioDao;

/**
 * Controlador de formularios internos.
 *
 * Contiene la lógica del gestor de formularios interno.
 *
 * @author Indra
 */
@Component("controladorGestorFormulariosInterno")
public final class ControladorGestorFormulariosInternoImpl implements ControladorGestorFormulariosInterno {

	// TODO BORRAR
	public final static boolean SIMULAR = true;

	/** Dao para acceso a bbdd. */
	@Autowired
	private FormularioDao dao;

	@Override
	public String iniciarSesion(DatosInicioSesionFormulario difi) {
		// Almacena sesion en BBDD generando ticket
		final String ticket = dao.crearSesionGestorFormularios(difi);
		return ticket;
	}

	@Override
	public DatosFinalizacionFormulario obtenerDatosFinalizacionFormulario(String ticket) {

		// TODO BORRAR
		if (SIMULAR) {
			try {
				final DatosFinalizacionFormulario dff = new DatosFinalizacionFormulario();
				dff.setFechaFinalizacion(new Date());
				dff.setCancelado(false);
				dff.setXml(SimulacionFormularioMock.getDatosSimulados().getBytes("UTF-8"));
				dff.setPdf(IOUtils.toByteArray(this.getClass().getClassLoader().getResourceAsStream("formulario.pdf")));

				return dff;
			} catch (final Exception ex) {
				throw new RuntimeException(ex);
			}
		}

		// Recupera datos finalizacion
		final DatosFinalizacionFormulario dff = dao.obtenerDatosFinSesionGestorFormularios(ticket);
		return dff;
	}

}
