package es.caib.sistramit.core.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.model.flujo.types.TypeSoporteEstado;
import es.caib.sistramit.core.api.model.system.rest.interno.DetallePagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.ErroresPorTramiteCM;
import es.caib.sistramit.core.api.model.system.rest.interno.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.rest.interno.EventoCM;
import es.caib.sistramit.core.api.model.system.rest.interno.FicheroAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FicheroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroEventoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FormularioSoporte;
import es.caib.sistramit.core.api.model.system.rest.interno.OUTPerdidaClave;
import es.caib.sistramit.core.api.model.system.rest.interno.PagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.PersistenciaAuditoria;
import es.caib.sistramit.core.api.service.RestApiInternaService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.system.RestApiInternaComponent;

@Service
@Transactional
public class RestApiInternaServiceImpl implements RestApiInternaService {

	@Autowired
	private RestApiInternaComponent restApiInternaComponent;

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(RestApiInternaServiceImpl.class);

	@Override
	@NegocioInterceptor
	public List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltros) {
		return restApiInternaComponent.recuperarLogSesionTramitacionArea(pFiltros);
	}

	@Override
	@NegocioInterceptor
	public List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(
			final FiltroEventoAuditoria pFiltroBusqueda, final FiltroPaginacion pFiltroPaginacion) {
		return restApiInternaComponent.recuperarLogSesionTramitacionArea(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@NegocioInterceptor
	public Long contarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiInternaComponent.contarLogSesionTramitacionArea(pFiltroBusqueda);
	}

	@Override
	@NegocioInterceptor
	public OUTPerdidaClave recuperarClaveTramitacionArea(final FiltroPerdidaClave pFiltroBusqueda) {
		return restApiInternaComponent.recuperarClaveTramitacionArea(pFiltroBusqueda);
	}

	@Override
	@NegocioInterceptor
	public FicheroAuditoria recuperarFichero(final Long pIdFichero, final String pClave) {
		return restApiInternaComponent.recuperarFichero(pIdFichero, pClave);
	}

	@Override
	@NegocioInterceptor
	public Long contarPagosArea(final FiltroPagoAuditoria pFiltroBusqueda) {
		return restApiInternaComponent.contarPagosArea(pFiltroBusqueda);
	}

	@Override
	@NegocioInterceptor
	public List<PagoAuditoria> recuperarPagosArea(final FiltroPagoAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		return restApiInternaComponent.recuperarPagosArea(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@NegocioInterceptor
	public DetallePagoAuditoria recuperarDetallePago(final Long pIdPago) {
		return restApiInternaComponent.recuperarDetallePago(pIdPago);
	}

	@Override
	@NegocioInterceptor
	public Long contarPersistenciaArea(final FiltroPersistenciaAuditoria pFiltroBusqueda) {
		return restApiInternaComponent.contarPersistenciaArea(pFiltroBusqueda);
	}

	@Override
	@NegocioInterceptor
	public List<PersistenciaAuditoria> recuperarPersistenciaArea(final FiltroPersistenciaAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		return restApiInternaComponent.recuperarPersistenciaArea(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@NegocioInterceptor
	public List<FicheroPersistenciaAuditoria> recuperarPersistenciaFicheros(final Long pIdTramite) {
		return restApiInternaComponent.recuperarPersistenciaFicheros(pIdTramite);

	}

	@Override
	@NegocioInterceptor
	public List<EventoCM> recuperarEventosCM(FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiInternaComponent.recuperarEventosCM(pFiltroBusqueda);
	}

	@Override
	@NegocioInterceptor
	public List<ErroresPorTramiteCM> recuperarErroresPorTramiteCM(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		return restApiInternaComponent.recuperarErroresPorTramiteCM(pFiltroBusqueda, filtroPaginacion);
	}

	@Override
	@NegocioInterceptor
	public Long contarErroresPorTramiteCM(FiltroEventoAuditoria filtroBusqueda) {
		return restApiInternaComponent.contarErroresPorTramiteCM(filtroBusqueda);
	}

	@Override
	public List<EventoCM> recuperarErroresPorTramiteCMExpansion(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		return restApiInternaComponent.recuperarErroresPorTramiteCMExpansion(pFiltroBusqueda, filtroPaginacion);
	}

	@Override
	public Long contarErroresPorTramiteExpansionCM(FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiInternaComponent.contarErroresPorTramiteExpansionCM(pFiltroBusqueda);
	}

	@Override
	public List<EventoCM> recuperarTramitesPorErrorCM(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		return restApiInternaComponent.recuperarTramitesPorErrorCM(pFiltroBusqueda, filtroPaginacion);
	}

	@Override
	public Long contarTramitesPorErrorCM(FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiInternaComponent.contarTramitesPorErrorCM(pFiltroBusqueda);
	}

	@Override
	@NegocioInterceptor
	public List<ErroresPorTramiteCM> recuperarTramitesPorErrorCMExpansion(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		return restApiInternaComponent.recuperarTramitesPorErrorCMExpansion(pFiltroBusqueda, filtroPaginacion);
	}

	@Override
	@NegocioInterceptor
	public Long contarTramitesPorErrorExpansionCM(FiltroEventoAuditoria filtroBusqueda) {
		return restApiInternaComponent.contarTramitesPorErrorExpansionCM(filtroBusqueda);
	}

	@Override
	public List<EventoCM> recuperarErroresPlataformaCM(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		return restApiInternaComponent.recuperarErroresPlataformaCM(pFiltroBusqueda, filtroPaginacion);
	}

	@Override
	public Long contarErroresPlataformaCM(FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiInternaComponent.contarErroresPlataformaCM(pFiltroBusqueda);
	}

	@Override
	public List<FormularioSoporte> recuperarFormularioSoporte(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		return restApiInternaComponent.recuperarFormularioSoporte(pFiltroBusqueda, filtroPaginacion);
	}

	@Override
	public Long contarFormularioSoporte(FiltroEventoAuditoria filtroBusqueda) {
		return restApiInternaComponent.contarFormularioSoporte(filtroBusqueda);
	}

	@Override
	public List<String> listarTiposProblema() {
		return restApiInternaComponent.listarTiposProblema();
	}

	@Override
	public void updateEstadoIncidencia(Long idSoporte, TypeSoporteEstado estado, String comentarios) {
		restApiInternaComponent.updateEstadoIncidencia(idSoporte, estado, comentarios);
	}
}
