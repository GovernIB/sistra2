package es.caib.sistrages.core.ejb;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.service.ComponenteService;

/**
 * La clase PluginServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ComponenteServiceBean implements ComponenteService {

	/**
	 * componente service.
	 */
	@Autowired
	ComponenteService componenteService;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public IPlugin obtenerPluginGlobal(final TypePlugin tipoPlugin) {
		return componenteService.obtenerPluginGlobal(tipoPlugin);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public IPlugin obtenerPluginEntidad(final TypePlugin tipoPlugin, final Long idEntidad) {
		return componenteService.obtenerPluginEntidad(tipoPlugin, idEntidad);
	}

}
