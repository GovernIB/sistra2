package es.caib.sistrahelp.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FicheroAuditoria;
import es.caib.sistrahelp.core.api.model.FicheroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaPago;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.FiltroPerdidaClave;
import es.caib.sistrahelp.core.api.model.FiltroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.PagoAuditoria;
import es.caib.sistrahelp.core.api.model.PersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.ResultadoAuditoriaDetallePago;
import es.caib.sistrahelp.core.api.model.ResultadoPerdidaClave;
import es.caib.sistrahelp.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrahelp.core.api.service.HelpDeskService;

/**
 * Servicio para verificar accesos de seguridad.
 *
 * @author Indra
 *
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class HelpDeskServiceBean implements HelpDeskService {

	/** Security service. */
	@Autowired
	HelpDeskService helpdeskService;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK })
	public List<EventoAuditoriaTramitacion> obtenerAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		return helpdeskService.obtenerAuditoriaEvento(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK })
	public Long countAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda) {
		return helpdeskService.countAuditoriaEvento(pFiltroBusqueda);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK })
	public ResultadoPerdidaClave obtenerClaveTramitacion(final FiltroPerdidaClave pFiltroBusqueda) {
		return helpdeskService.obtenerClaveTramitacion(pFiltroBusqueda);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK })
	public List<PagoAuditoria> obtenerAuditoriaPago(final FiltroAuditoriaPago pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		return helpdeskService.obtenerAuditoriaPago(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK })
	public Long countAuditoriaPago(final FiltroAuditoriaPago pFiltroBusqueda) {
		return helpdeskService.countAuditoriaPago(pFiltroBusqueda);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK })
	public ResultadoAuditoriaDetallePago obtenerAuditoriaDetallePago(final Long pIdPago) {
		return helpdeskService.obtenerAuditoriaDetallePago(pIdPago);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK })
	public Long countAuditoriaPersistencia(final FiltroPersistenciaAuditoria pFiltroBusqueda) {
		return helpdeskService.countAuditoriaPersistencia(pFiltroBusqueda);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK })
	public List<PersistenciaAuditoria> obtenerAuditoriaPersistencia(final FiltroPersistenciaAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		return helpdeskService.obtenerAuditoriaPersistencia(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK })
	public List<FicheroPersistenciaAuditoria> obtenerAuditoriaPersistenciaFichero(final Long pIdTramite) {
		return helpdeskService.obtenerAuditoriaPersistenciaFichero(pIdTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK })
	public FicheroAuditoria obtenerAuditoriaFichero(final Long pIdFichero, final String pClave) {
		return helpdeskService.obtenerAuditoriaFichero(pIdFichero, pClave);
	}

}
