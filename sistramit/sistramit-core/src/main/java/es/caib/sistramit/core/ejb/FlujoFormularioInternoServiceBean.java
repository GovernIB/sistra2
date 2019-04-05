package es.caib.sistramit.core.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarPagina;
import es.caib.sistramit.core.api.model.formulario.SesionFormularioInfo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.service.FlujoFormularioInternoService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class FlujoFormularioInternoServiceBean implements FlujoFormularioInternoService {

	@Autowired
	private FlujoFormularioInternoService flujoFormularioInternoService;

	@Override
	public String cargarSesion(String ticket) {
		return flujoFormularioInternoService.cargarSesion(ticket);
	}

	@Override
	public void inicializarSesion(String idSesionFormulario) {
		flujoFormularioInternoService.inicializarSesion(idSesionFormulario);
	}

	@Override
	public PaginaFormulario cargarPaginaActual(String idSesionFormulario) {
		return flujoFormularioInternoService.cargarPaginaActual(idSesionFormulario);
	}

	@Override
	public ResultadoEvaluarCambioCampo evaluarCambioCampoPagina(String idSesionFormulario, String idCampo,
			List<ValorCampo> valoresPagina) {
		return flujoFormularioInternoService.evaluarCambioCampoPagina(idSesionFormulario, idCampo, valoresPagina);
	}

	@Override
	public ResultadoGuardarPagina guardarPagina(String idSesionFormulario, List<ValorCampo> valoresPagina,
			String accionPersonalizada) {
		return flujoFormularioInternoService.guardarPagina(idSesionFormulario, valoresPagina, accionPersonalizada);
	}

	@Override
	public void cancelarFormulario(String idSesionFormulario) {
		flujoFormularioInternoService.cancelarFormulario(idSesionFormulario);
	}

	@Override
	public List<ValorCampo> deserializarValoresCampos(String idSesionFormulario, Map<String, String> valores) {
		return flujoFormularioInternoService.deserializarValoresCampos(idSesionFormulario, valores);
	}

	@Override
	public PaginaFormulario cargarPaginaAnterior(String idSesionFormulario) {
		return flujoFormularioInternoService.cargarPaginaAnterior(idSesionFormulario);
	}

	@Override
	public SesionFormularioInfo obtenerInformacionFormulario(String idSesionFormulario) {
		return flujoFormularioInternoService.obtenerInformacionFormulario(idSesionFormulario);
	}

}
