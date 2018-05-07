package es.caib.sistramit.core.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.exception.NoExisteFlujoTramitacionException;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.FlujoTramitacionComponent;

@Service
@Transactional
public class FlujoTramitacionServiceImpl implements FlujoTramitacionService {

	/** Map con con los flujos de tramitacion. */
	// TODO Ver como purgar el map
	private final Map<String, FlujoTramitacionComponent> flujoTramitacionMap = new HashMap<>();

	@Override
	@NegocioInterceptor
	public String iniciarTramite(final String idTramite, final int version, final String idioma,
			final String idProcedimiento, final String urlInicio, final Map<String, String> parametrosInicio) {
		// Generamos flujo de tramitacion y almacenamos en map
		final FlujoTramitacionComponent ft = (FlujoTramitacionComponent) ApplicationContextProvider
				.getApplicationContext().getBean("flujoTramitacionComponent");
		final String idSesionTramitacion = ft.iniciarTramite(idTramite, version, idioma, idProcedimiento, urlInicio,
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
