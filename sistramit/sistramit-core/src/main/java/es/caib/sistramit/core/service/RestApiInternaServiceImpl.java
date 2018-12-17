package es.caib.sistramit.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.model.system.DetallePagoAuditoria;
import es.caib.sistramit.core.api.model.system.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FicheroAuditoria;
import es.caib.sistramit.core.api.model.system.FicheroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroEventoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.FiltroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.OUTPerdidaClave;
import es.caib.sistramit.core.api.model.system.PagoAuditoria;
import es.caib.sistramit.core.api.model.system.PersistenciaAuditoria;
import es.caib.sistramit.core.api.service.RestApiInternaService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.RestApiInternaComponent;

@Service
@Transactional
public class RestApiInternaServiceImpl implements RestApiInternaService {

	/** Componente auditoria. */
	@Autowired
	private AuditoriaComponent auditoriaComponent;

	@Autowired
	private RestApiInternaComponent restApiInternaComponent;

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(RestApiInternaServiceImpl.class);

	@Override
	@NegocioInterceptor
	public List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltros) {
		return auditoriaComponent.recuperarLogSesionTramitacionArea(pFiltros);
	}

	@Override
	@NegocioInterceptor
	public List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(
			final FiltroEventoAuditoria pFiltroBusqueda, final FiltroPaginacion pFiltroPaginacion) {
		return auditoriaComponent.recuperarLogSesionTramitacionArea(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@NegocioInterceptor
	public Long contarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda) {
		return auditoriaComponent.contarLogSesionTramitacionArea(pFiltroBusqueda);
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
}
