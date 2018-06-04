package es.caib.sistramit.core.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.exception.NoExisteFlujoTramitacionException;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.flujo.FlujoTramitacionComponent;

@Service
@Transactional
public class FlujoTramitacionServiceImpl implements FlujoTramitacionService {

	/** Map con con los flujos de tramitacion. */
	// TODO Ver como purgar el map
	private final Map<String, FlujoTramitacionComponent> flujoTramitacionMap = new HashMap<>();

	@Override
	@NegocioInterceptor
	public String iniciarTramite(final String idTramite, final int version, final String idioma,
			final String idTramiteCatalogo, final String urlInicio, final Map<String, String> parametrosInicio) {
		// Generamos flujo de tramitacion y almacenamos en map
		final FlujoTramitacionComponent ft = (FlujoTramitacionComponent) ApplicationContextProvider
				.getApplicationContext().getBean("flujoTramitacionComponent");
		final String idSesionTramitacion = ft.iniciarTramite(idTramite, version, idioma, idTramiteCatalogo, urlInicio,
				parametrosInicio);
		flujoTramitacionMap.put(idSesionTramitacion, ft);
		return idSesionTramitacion;
	}

	@Override
	@NegocioInterceptor
	public DetalleTramite obtenerDetalleTramite(final String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		return ft.obtenerDetalleTramite();
	}

	@Override
	@NegocioInterceptor
	public ResultadoIrAPaso cargarTramite(String idSesionTramitacion) {
		// Generamos flujo de tramitacion, almacenamos en map y cargamos trámite
		final FlujoTramitacionComponent ft = (FlujoTramitacionComponent) ApplicationContextProvider
						.getApplicationContext().getBean("flujoTramitacionComponent");
		ResultadoIrAPaso res = ft.cargarTramite(idSesionTramitacion);
		flujoTramitacionMap.put(idSesionTramitacion, ft);
		return res;
	}

	@Override
	@NegocioInterceptor
	public ResultadoIrAPaso recargarTramite(String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		return ft.recargarTramite();
	}

	@Override
	@NegocioInterceptor
	public ResultadoIrAPaso irAPaso(String idSesionTramitacion, String idPaso) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		return ft.irAPaso(idPaso);
	}

	@Override
	@NegocioInterceptor
	public ResultadoAccionPaso accionPaso(String idSesionTramitacion, String idPaso, TypeAccionPaso accionPaso,
			ParametrosAccionPaso parametros) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		return ft.accionPaso(idPaso, accionPaso, parametros);
	}

	@Override
	@NegocioInterceptor
	public void cancelarTramite(String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		ft.cancelarTramite();
	}

	// -------------------------------------------------------------------------------------------
	// - Métodos especiales invocados desde el interceptor. No pasan por interceptor
	// de auditoria.
	// -------------------------------------------------------------------------------------------
	@Override
	public DetalleTramite obtenerFlujoTramitacionInfo(final String idSesionTramitacion) {
		// ATENCION: NO DEBE PASAR POR INTERCEPTOR. SE USA DESDE EL PROPIO INTERCEPTOR.
		DetalleTramite dt = null;
		final FlujoTramitacionComponent ft = flujoTramitacionMap.get(idSesionTramitacion);
		if (ft != null) {
			try {
				dt = ft.obtenerDetalleTramite();
			} catch (final Exception ex) {
				// No hacemos nada
				dt = null;
			}
		}
		return dt;
	}

	@Override
	public void invalidarFlujoTramitacion(final String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = flujoTramitacionMap.get(idSesionTramitacion);
		if (ft != null) {
			try {
				ft.invalidarFlujoTramicacion();
			} catch (final Exception ex) {
				// No hacemos nada
			}
		}
	}

	// --------------------------------------------------------------
	// ---- FUNCIONES INTERNAS
	// --------------------------------------------------------------

	/**
	 * Obtiene flujo de tramitación.
	 *
	 * @param idSesionTramitacion
	 *            id sesión tramitación
	 * @return flujo tramitación
	 */
	private FlujoTramitacionComponent obtenerFlujoTramitacion(final String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = flujoTramitacionMap.get(idSesionTramitacion);
		if (ft == null) {
			throw new NoExisteFlujoTramitacionException(idSesionTramitacion);
		}
		return ft;
	}

}
