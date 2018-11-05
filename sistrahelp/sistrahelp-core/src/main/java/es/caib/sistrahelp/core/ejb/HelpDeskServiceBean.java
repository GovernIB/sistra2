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
import es.caib.sistrahelp.core.api.model.FiltrosAuditoriaTramitacion;
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
	HelpDeskService auditoriaService;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK })
	public List<EventoAuditoriaTramitacion> obtenerAuditoriaEvento(final FiltrosAuditoriaTramitacion pFiltros) {
		return auditoriaService.obtenerAuditoriaEvento(pFiltros);
	}

}
