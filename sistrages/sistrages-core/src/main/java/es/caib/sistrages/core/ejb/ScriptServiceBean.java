package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.LiteralScript;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.service.ScriptService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ScriptServiceBean implements ScriptService {

	@Autowired
	ScriptService scriptService;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Script getScript(final Long idScript) {
		return scriptService.getScript(idScript);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<LiteralScript> getLiterales(final Long idScript) {
		return scriptService.getLiterales(idScript);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public LiteralScript getLiteralScript(final Long idLiteralScript) {
		return scriptService.getLiteralScript(idLiteralScript);
	}

}
