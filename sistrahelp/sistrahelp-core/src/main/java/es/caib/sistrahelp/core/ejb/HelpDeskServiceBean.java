package es.caib.sistrahelp.core.ejb;

import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
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
import es.caib.sistrahelp.core.api.model.ResultadoErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.ResultadoEventoCM;
import es.caib.sistrahelp.core.api.model.ResultadoPerdidaClave;
import es.caib.sistrahelp.core.api.model.ResultadoSoporte;
import es.caib.sistrahelp.core.api.model.Soporte;
import es.caib.sistrahelp.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.core.interceptor.NegocioInterceptor;

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
	@PermitAll
	public List<EventoAuditoriaTramitacion> obtenerAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		return helpdeskService.obtenerAuditoriaEvento(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@PermitAll
	public Long countAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda) {
		return helpdeskService.countAuditoriaEvento(pFiltroBusqueda);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public ResultadoPerdidaClave obtenerClaveTramitacion(final FiltroPerdidaClave pFiltroBusqueda) {
		return helpdeskService.obtenerClaveTramitacion(pFiltroBusqueda);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public List<PagoAuditoria> obtenerAuditoriaPago(final FiltroAuditoriaPago pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		return helpdeskService.obtenerAuditoriaPago(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public Long countAuditoriaPago(final FiltroAuditoriaPago pFiltroBusqueda) {
		return helpdeskService.countAuditoriaPago(pFiltroBusqueda);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public ResultadoAuditoriaDetallePago obtenerAuditoriaDetallePago(final Long pIdPago) {
		return helpdeskService.obtenerAuditoriaDetallePago(pIdPago);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public Long countAuditoriaPersistencia(final FiltroPersistenciaAuditoria pFiltroBusqueda) {
		return helpdeskService.countAuditoriaPersistencia(pFiltroBusqueda);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public List<PersistenciaAuditoria> obtenerAuditoriaPersistencia(final FiltroPersistenciaAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		return helpdeskService.obtenerAuditoriaPersistencia(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public List<FicheroPersistenciaAuditoria> obtenerAuditoriaPersistenciaFichero(final Long pIdTramite) {
		return helpdeskService.obtenerAuditoriaPersistenciaFichero(pIdTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public FicheroAuditoria obtenerAuditoriaFichero(final Long pIdFichero, final String pClave) {
		return helpdeskService.obtenerAuditoriaFichero(pIdFichero, pClave);
	}

	@Override
	@PermitAll
	public ResultadoEventoCM obtenerCountEventoCM(FiltroAuditoriaTramitacion pFiltroBusqueda) {
		return helpdeskService.obtenerCountEventoCM(pFiltroBusqueda);
	}

	@Override
	@PermitAll
	public ResultadoErroresPorTramiteCM obtenerErroresPorTramiteCM(FiltroAuditoriaTramitacion pFiltroBusqueda,
			FiltroPaginacion pFiltroPaginacion) {
		return helpdeskService.obtenerErroresPorTramiteCM(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public ResultadoEventoCM obtenerErroresPorTramiteCMExpansion(FiltroAuditoriaTramitacion pFiltroBusqueda,
			FiltroPaginacion pFiltroPaginacion) {
		return helpdeskService.obtenerErroresPorTramiteCMExpansion(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@PermitAll
	public ResultadoEventoCM obtenerTramitesPorErrorCM(FiltroAuditoriaTramitacion pFiltroBusqueda,
			FiltroPaginacion pFiltroPaginacion) {
		return helpdeskService.obtenerTramitesPorErrorCM(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public ResultadoErroresPorTramiteCM obtenerTramitesPorErrorCMExpansion(FiltroAuditoriaTramitacion pFiltroBusqueda,
			FiltroPaginacion pFiltroPaginacion) {
		return helpdeskService.obtenerTramitesPorErrorCMExpansion(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@PermitAll
	public ResultadoEventoCM obtenerErroresPlataformaCM(FiltroAuditoriaTramitacion pFiltroBusqueda,
			FiltroPaginacion pFiltroPaginacion) {
		return helpdeskService.obtenerErroresPlataformaCM(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public ResultadoSoporte obtenerFormularioSoporte(FiltroAuditoriaTramitacion pFiltroBusqueda,
			FiltroPaginacion pFiltroPaginacion) {
		return helpdeskService.obtenerFormularioSoporte(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public void updateFormularioSoporte(Soporte soporte) {
		helpdeskService.updateFormularioSoporte(soporte);
	}

	@Override
	@PermitAll
	public String urlLogoEntidad(String codDir3) {
		return helpdeskService.urlLogoEntidad(codDir3);
	}

}
