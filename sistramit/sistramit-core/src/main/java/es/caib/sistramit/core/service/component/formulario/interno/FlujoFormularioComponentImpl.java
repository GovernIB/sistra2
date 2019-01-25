package es.caib.sistramit.core.service.component.formulario.interno;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarPagina;
import es.caib.sistramit.core.api.model.formulario.SesionFormularioInfo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;

@Component("flujoFormularioComponent")
@Scope(value = "prototype")
public class FlujoFormularioComponentImpl implements FlujoFormularioComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Datos de sesión del formulario. */
	private DatosSesionFormularioInterno datosSesion;

	/** Controlador gestor formularios interno. */
	@Autowired
	private ControladorGestorFormulariosInterno controlador;

	@Override
	public String cargarSesion(String ticket) {
		datosSesion = controlador.cargarSesion(ticket);
		// TODO id sesion formulario?? falta campo??
		return ticket;
	}

	@Override
	public PaginaFormulario cargarPaginaActual() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginaFormulario cargarPaginaAnterior() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoEvaluarCambioCampo evaluarCambioCampoPagina(String idCampo, List<ValorCampo> valoresPagina) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoGuardarPagina guardarPagina(List<ValorCampo> valoresPagina, String accionPersonalizada) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelarFormulario() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ValorCampo> deserializarValoresCampos(Map<String, String> valores) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SesionFormularioInfo obtenerInformacionFormulario() {
		// TODO Auto-generated method stub
		return null;
	}

}
