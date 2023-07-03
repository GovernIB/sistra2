package es.caib.sistramit.core.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.model.formulario.Captcha;
import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoBuscadorDinamico;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarElemento;
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
	public ResultadoGuardarPagina cancelarFormulario(final String idSesionFormulario) {
		return flujoFormularioInternoService.cancelarFormulario(idSesionFormulario);
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

	@Override
	public ResultadoBuscadorDinamico buscadorDinamico(final String idSesionFormulario, final String idCampo,
			final String textoCampo, final List<ValorCampo> valores) {
		return flujoFormularioInternoService.buscadorDinamico(idSesionFormulario, idCampo, textoCampo, valores);
	}

	@Override
	public Captcha generarImagenCaptcha(final String idSesionFormulario, final String idCampo) {
		return flujoFormularioInternoService.generarImagenCaptcha(idSesionFormulario, idCampo);
	}

	@Override
	public Captcha generarSonidoCaptcha(final String idSesionFormulario, final String idCampo) {
		return flujoFormularioInternoService.generarSonidoCaptcha(idSesionFormulario, idCampo);
	}

	@Override
	public void regenerarCaptcha(final String idSesionFormulario, final String idCampo) {
		flujoFormularioInternoService.regenerarCaptcha(idSesionFormulario, idCampo);
	}

	@Override
	public PaginaFormulario anyadirElemento(final String idSesionFormulario, final String idCampoListaElementos,
			final List<ValorCampo> valoresPagina) {
		return flujoFormularioInternoService.anyadirElemento(idSesionFormulario, idCampoListaElementos, valoresPagina);
	}

	@Override
	public PaginaFormulario modificarElemento(final String idSesionFormulario, final String idCampoListaElementos,
			final int indiceElemento, final List<ValorCampo> valoresPagina) {
		return flujoFormularioInternoService.modificarElemento(idSesionFormulario, idCampoListaElementos,
				indiceElemento, valoresPagina);
	}

	@Override
	public PaginaFormulario consultarElemento(final String idSesionFormulario, final String idCampoListaElementos,
			final int indiceElemento, final List<ValorCampo> valoresPagina) {
		return flujoFormularioInternoService.consultarElemento(idSesionFormulario, idCampoListaElementos,
				indiceElemento, valoresPagina);
	}

	@Override
	public ResultadoEvaluarCambioCampo evaluarCambioCampoElemento(final String idSesionFormulario,
			final String idCampoListaElementos, final String idCampo, final List<ValorCampo> valoresPagina) {
		return flujoFormularioInternoService.evaluarCambioCampoElemento(idSesionFormulario, idCampoListaElementos,
				idCampo, valoresPagina);
	}

	@Override
	public ResultadoGuardarElemento guardarElemento(final String idSesionFormulario, final String idCampoListaElementos,
			final List<ValorCampo> valoresElemento) {
		return flujoFormularioInternoService.guardarElemento(idSesionFormulario, idCampoListaElementos,
				valoresElemento);
	}

}
