package es.caib.sistramit.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.model.comun.LogEvento;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.comun.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;

@Service
@Transactional
public class SystemServiceImpl implements SystemService {

	// TODO Pendiente leer de propiedades e ir consultando periodicamente STG. Ver
	// cuales deben ir a properties y cuales a STG.
	private Map<TypePropiedadConfiguracion, String> propiedades;

	@PostConstruct
	public void init() {
		propiedades = new HashMap<>();
		propiedades.put(TypePropiedadConfiguracion.ENTORNO, TypeEntorno.DESARROLLO.toString());
		propiedades.put(TypePropiedadConfiguracion.URL_SISTRAMIT, "http://localhost:8080/sistramitfront");
		propiedades.put(TypePropiedadConfiguracion.IDIOMAS_SOPORTADOS, "es,ca,en");
	}

	@Override
	@NegocioInterceptor
	public String obtenerPropiedadConfiguracion(final TypePropiedadConfiguracion propiedad) {
		return propiedades.get(propiedad);
	}

	@Override
	public void auditarErrorFront(final String idSesionTramitacion, final ErrorFrontException error) {
		// TODO PENDIENTE
	}

	@Override
	public List<LogEvento> recuperarLogSesionTramitacion(final String idSesionTramitacion, final Date fechaDesde,
			final Date fechaHasta, final boolean ordenAsc) {
		// TODO PENDIENTE
		return new ArrayList<>();
	}

}
