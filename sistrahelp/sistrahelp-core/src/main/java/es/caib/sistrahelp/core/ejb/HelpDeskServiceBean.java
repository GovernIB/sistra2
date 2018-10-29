package es.caib.sistrahelp.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrahelp.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistramit.rest.api.interna.REventoAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltrosAuditoria;

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
	public List<REventoAuditoria> obtenerAuditoriaEvento(final RFiltrosAuditoria pFiltros) {
		return auditoriaService.obtenerAuditoriaEvento(pFiltros);
	}

}
