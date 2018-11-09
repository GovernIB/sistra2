package es.caib.sistramit.core.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.model.system.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FiltroAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FiltroPaginacion;
import es.caib.sistramit.core.api.service.RestApiInternaService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class RestApiInternaServiceBean implements RestApiInternaService {

	@Autowired
	private RestApiInternaService restApiInternaService;

	@Override
	public List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(
			final FiltroAuditoriaTramitacion pFiltroBusqueda) {
		return restApiInternaService.recuperarLogSesionTramitacionArea(pFiltroBusqueda);
	}

	@Override
	public List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(
			final FiltroAuditoriaTramitacion pFiltroBusqueda, final FiltroPaginacion pFiltroPaginacion) {
		return restApiInternaService.recuperarLogSesionTramitacionArea(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	public Long recuperarLogSesionTramitacionAreaCount(final FiltroAuditoriaTramitacion pFiltroBusqueda) {
		return restApiInternaService.recuperarLogSesionTramitacionAreaCount(pFiltroBusqueda);
	}

}
