package es.caib.sistramit.core.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

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
import es.caib.sistramit.core.api.model.system.rest.interno.OUTPerdidaClave;
import es.caib.sistramit.core.api.model.system.rest.interno.PagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.PersistenciaAuditoria;
import es.caib.sistramit.core.api.service.RestApiInternaService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class RestApiInternaServiceBean implements RestApiInternaService {

	@Autowired
	private RestApiInternaService restApiInternaService;

	@Override
	public List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(
			final FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiInternaService.recuperarLogSesionTramitacionArea(pFiltroBusqueda);
	}

	@Override
	public List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(
			final FiltroEventoAuditoria pFiltroBusqueda, final FiltroPaginacion pFiltroPaginacion) {
		return restApiInternaService.recuperarLogSesionTramitacionArea(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	public Long contarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiInternaService.contarLogSesionTramitacionArea(pFiltroBusqueda);
	}

	@Override
	public OUTPerdidaClave recuperarClaveTramitacionArea(final FiltroPerdidaClave pFiltroBusqueda) {
		return restApiInternaService.recuperarClaveTramitacionArea(pFiltroBusqueda);
	}

	@Override
	public FicheroAuditoria recuperarFichero(final Long pIdFichero, final String pClave) {
		return restApiInternaService.recuperarFichero(pIdFichero, pClave);
	}

	@Override
	public Long contarPagosArea(final FiltroPagoAuditoria pFiltroBusqueda) {
		return restApiInternaService.contarPagosArea(pFiltroBusqueda);
	}

	@Override
	public List<PagoAuditoria> recuperarPagosArea(final FiltroPagoAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		return restApiInternaService.recuperarPagosArea(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	public DetallePagoAuditoria recuperarDetallePago(final Long pIdPago) {
		return restApiInternaService.recuperarDetallePago(pIdPago);
	}

	@Override
	public Long contarPersistenciaArea(final FiltroPersistenciaAuditoria pFiltroBusqueda) {
		return restApiInternaService.contarPersistenciaArea(pFiltroBusqueda);
	}

	@Override
	public List<PersistenciaAuditoria> recuperarPersistenciaArea(final FiltroPersistenciaAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		return restApiInternaService.recuperarPersistenciaArea(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	public List<FicheroPersistenciaAuditoria> recuperarPersistenciaFicheros(final Long pIdTramite) {
		return restApiInternaService.recuperarPersistenciaFicheros(pIdTramite);
	}

	@Override
	public List<EventoCM> recuperarEventosCM(FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiInternaService.recuperarEventosCM(pFiltroBusqueda);
	}

	@Override
	public List<ErroresPorTramiteCM> recuperarErroresPorTramiteCM(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		return restApiInternaService.recuperarErroresPorTramiteCM(pFiltroBusqueda, filtroPaginacion);
	}

	@Override
	public Long contarErroresPorTramiteCM(FiltroEventoAuditoria filtroBusqueda) {
		return restApiInternaService.contarErroresPorTramiteCM(filtroBusqueda);
	}

	@Override
	public List<EventoCM> recuperarErroresPorTramiteCMExpansion(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		return restApiInternaService.recuperarErroresPorTramiteCMExpansion(pFiltroBusqueda, filtroPaginacion);
	}

	@Override
	public Long contarErroresPorTramiteExpansionCM(FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiInternaService.contarErroresPorTramiteExpansionCM(pFiltroBusqueda);
	}

	@Override
	public List<EventoCM> recuperarErroresPlataformaCM(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		return restApiInternaService.recuperarErroresPlataformaCM(pFiltroBusqueda, filtroPaginacion);
	}

	@Override
	public Long contarErroresPlataformaCM(FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiInternaService.contarErroresPlataformaCM(pFiltroBusqueda);
	}
}
