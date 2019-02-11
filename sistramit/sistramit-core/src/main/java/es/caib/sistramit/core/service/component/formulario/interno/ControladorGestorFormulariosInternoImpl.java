package es.caib.sistramit.core.service.component.formulario.interno;

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
		// Recupera datos finalizacion
		final DatosFinalizacionFormulario dff = dao.obtenerDatosFinSesionGestorFormularios(ticket);
		return dff;
	}

}
