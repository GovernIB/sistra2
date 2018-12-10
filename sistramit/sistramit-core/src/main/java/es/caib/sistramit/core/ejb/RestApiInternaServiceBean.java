package es.caib.sistramit.core.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.model.system.DetallePagoAuditoria;
import es.caib.sistramit.core.api.model.system.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FicheroAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroEventoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.FiltroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.OUTPerdidaClave;
import es.caib.sistramit.core.api.model.system.PagoAuditoria;
import es.caib.sistramit.core.api.model.system.PersistenciaAuditoria;
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

}
