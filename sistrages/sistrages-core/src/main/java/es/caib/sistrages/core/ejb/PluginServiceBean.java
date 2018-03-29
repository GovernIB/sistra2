package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.PluginService;

/**
 * La clase PluginServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class PluginServiceBean implements PluginService {

	/**
	 * plugin service.
	 */
	@Autowired
	PluginService pluginService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.caib.sistrages.core.api.service.PluginService#getPlugin(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public Plugin getPlugin(final Long id) {
		return pluginService.getPlugin(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.caib.sistrages.core.api.service.PluginService#addPlugin(es.caib.sistrages.
	 * core.api.model.Plugin, java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public void addPlugin(final Plugin plugin, final Long idEntidad) {
		pluginService.addPlugin(plugin, idEntidad);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.caib.sistrages.core.api.service.PluginService#removePlugin(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public boolean removePlugin(final Long id) {
		return pluginService.removePlugin(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.caib.sistrages.core.api.service.PluginService#updatePlugin(es.caib.
	 * sistrages.core.api.model.Plugin)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public void updatePlugin(final Plugin plugin) {
		pluginService.updatePlugin(plugin);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.caib.sistrages.core.api.service.PluginService#listPlugin(es.caib.sistrages
	 * .core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public List<Plugin> listPlugin(final TypeAmbito ambito, final Long idEntidad, final String filtro) {
		return pluginService.listPlugin(ambito, idEntidad, filtro);
	}

}
