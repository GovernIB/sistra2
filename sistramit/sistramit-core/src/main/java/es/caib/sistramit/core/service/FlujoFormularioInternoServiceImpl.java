package es.caib.sistramit.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.exception.NoExisteFlujoFormularioException;
import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarPagina;
import es.caib.sistramit.core.api.model.formulario.SesionFormularioInfo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.service.FlujoFormularioInternoService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.formulario.interno.FlujoFormularioComponent;
import es.caib.sistramit.core.service.component.system.FlujoTramitacionCacheComponent;

@Service
@Transactional
public class FlujoFormularioInternoServiceImpl implements FlujoFormularioInternoService {

	/** Caché con con los flujos de tramitacion. */
	@Autowired
	private FlujoTramitacionCacheComponent flujoTramitacionCache;

	@Override
	@NegocioInterceptor
	public String cargarSesion(final String ticket) {
		// Generamos flujo de tramitacion
		final FlujoFormularioComponent ff = (FlujoFormularioComponent) ApplicationContextProvider
				.getApplicationContext().getBean("flujoFormularioComponent");
		// Iniciamos formulario
		final String idSesionFormulario = ff.cargarSesion(ticket);
		// Almacenamos en map
		flujoTramitacionCache.put(idSesionFormulario, ff);
		// Retornamos id tramitacion
		return idSesionFormulario;
	}

	@Override
	@NegocioInterceptor
	public void inicializarSesion(final String idSesionFormulario) {
		final FlujoFormularioComponent ff = obtenerFlujoFormulario(idSesionFormulario);
		ff.inicializarSesion();
	}

	@Override
	@NegocioInterceptor
	public PaginaFormulario cargarPaginaActual(final String idSesionFormulario) {
		final FlujoFormularioComponent ff = obtenerFlujoFormulario(idSesionFormulario);
		return ff.cargarPaginaActual();
	}

	@Override
	@NegocioInterceptor
	public PaginaFormulario cargarPaginaAnterior(final String idSesionFormulario, final List<ValorCampo> valores) {
		final FlujoFormularioComponent ff = obtenerFlujoFormulario(idSesionFormulario);
		return ff.cargarPaginaAnterior(valores);
	}

	@Override
	@NegocioInterceptor
	public ResultadoEvaluarCambioCampo evaluarCambioCampoPagina(final String idSesionFormulario, final String idCampo,
			final List<ValorCampo> valoresPagina) {
		final FlujoFormularioComponent ff = obtenerFlujoFormulario(idSesionFormulario);
		return ff.evaluarCambioCampoPagina(idCampo, valoresPagina);
	}

	@Override
	@NegocioInterceptor
	public ResultadoGuardarPagina guardarPagina(final String idSesionFormulario, final List<ValorCampo> valoresPagina,
			final String accionPersonalizada) {
		final FlujoFormularioComponent ff = obtenerFlujoFormulario(idSesionFormulario);
		return ff.guardarPagina(valoresPagina, accionPersonalizada);
	}

	@Override
	@NegocioInterceptor
	public void cancelarFormulario(final String idSesionFormulario) {
		final FlujoFormularioComponent ff = obtenerFlujoFormulario(idSesionFormulario);
		ff.cancelarFormulario();
	}

	@Override
	@NegocioInterceptor
	public List<ValorCampo> deserializarValoresCampos(final String idSesionFormulario,
			final Map<String, String> valores) {
		final FlujoFormularioComponent ff = obtenerFlujoFormulario(idSesionFormulario);
		return ff.deserializarValoresCampos(valores);
	}

	// -------------------------------------------------------------------------------------------
	// - Métodos especiales invocados desde el interceptor. No pasan por
	// interceptor de auditoria.
	// -------------------------------------------------------------------------------------------
	@Override
	public SesionFormularioInfo obtenerInformacionFormulario(final String idSesionFormulario) {
		// ATENCION: NO DEBE PASAR POR INTERCEPTOR. SE USA DESDE EL PROPIO
		// INTERCEPTOR.
		SesionFormularioInfo dt = null;
		final FlujoFormularioComponent ft = (FlujoFormularioComponent) flujoTramitacionCache.get(idSesionFormulario);
		if (ft != null) {
			try {
				dt = ft.obtenerInformacionFormulario();
			} catch (final Exception ex) {
				// No hacemos nada
				dt = null;
			}
		}
		return dt;
	}

	// --------------------------------------------------------------
	// ---- FUNCIONES INTERNAS
	// --------------------------------------------------------------

	/**
	 * Obtiene flujo de formulario.
	 *
	 * @param idSesionFormulario
	 *                               id sesión formulario
	 * @return flujo formulario
	 */
	private FlujoFormularioComponent obtenerFlujoFormulario(final String idSesionFormulario) {
		final FlujoFormularioComponent ft = (FlujoFormularioComponent) flujoTramitacionCache.get(idSesionFormulario);
		if (ft == null) {
			throw new NoExisteFlujoFormularioException(idSesionFormulario);
		}
		return ft;
	}

}
