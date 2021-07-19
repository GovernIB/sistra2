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
	public String cargarSesion(final String ticket) {
		return flujoFormularioInternoService.cargarSesion(ticket);
	}

	@Override
	public void inicializarSesion(final String idSesionFormulario) {
		flujoFormularioInternoService.inicializarSesion(idSesionFormulario);
	}

	@Override
	public PaginaFormulario cargarPaginaActual(final String idSesionFormulario) {
		return flujoFormularioInternoService.cargarPaginaActual(idSesionFormulario);
	}

	@Override
	public ResultadoEvaluarCambioCampo evaluarCambioCampoPagina(final String idSesionFormulario, final String idCampo,
			final List<ValorCampo> valoresPagina) {
		return flujoFormularioInternoService.evaluarCambioCampoPagina(idSesionFormulario, idCampo, valoresPagina);
	}

	@Override
	public ResultadoGuardarPagina guardarPagina(final String idSesionFormulario, final List<ValorCampo> valoresPagina,
			final String accionPersonalizada) {
		return flujoFormularioInternoService.guardarPagina(idSesionFormulario, valoresPagina, accionPersonalizada);
	}

	@Override
	public ResultadoGuardarPagina guardarSalirPagina(final String idSesionFormulario,
			final List<ValorCampo> valoresPagina) {
		return flujoFormularioInternoService.guardarSalirPagina(idSesionFormulario, valoresPagina);
	}

	@Override
	public void cancelarFormulario(final String idSesionFormulario) {
		flujoFormularioInternoService.cancelarFormulario(idSesionFormulario);
	}

	@Override
	public List<ValorCampo> deserializarValoresCampos(final String idSesionFormulario,
			final Map<String, String> valores) {
		return flujoFormularioInternoService.deserializarValoresCampos(idSesionFormulario, valores);
	}

	@Override
	public PaginaFormulario cargarPaginaAnterior(final String idSesionFormulario, final List<ValorCampo> valores) {
		return flujoFormularioInternoService.cargarPaginaAnterior(idSesionFormulario, valores);
	}

	@Override
	public SesionFormularioInfo obtenerInformacionFormulario(final String idSesionFormulario) {
		return flujoFormularioInternoService.obtenerInformacionFormulario(idSesionFormulario);
	}

}
