package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;

// TODO: Auto-generated Javadoc
/**
 * La clase ConfiguracionGlobalServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ConfiguracionGlobalServiceBean implements ConfiguracionGlobalService {

	/**
	 * configuracion global service.
	 */
	@Autowired
	ConfiguracionGlobalService configuracionGlobalService;

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.ConfiguracionGlobalService#
	 * getConfiguracionGlobal(java.lang.Long)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.SUPER_ADMIN)
	public ConfiguracionGlobal getConfiguracionGlobal(final Long idConfiguracionGlobal) {
		return configuracionGlobalService.getConfiguracionGlobal(idConfiguracionGlobal);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.ConfiguracionGlobalService#
	 * getConfiguracionGlobal(java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public ConfiguracionGlobal getConfiguracionGlobal(final String propiedad) {
		return configuracionGlobalService.getConfiguracionGlobal(propiedad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.ConfiguracionGlobalService#
	 * addConfiguracionGlobal(es.caib.sistrages.core.api.model.ConfiguracionGlobal)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.SUPER_ADMIN)
	public void addConfiguracionGlobal(final ConfiguracionGlobal configuracionGlobal) {
		configuracionGlobalService.addConfiguracionGlobal(configuracionGlobal);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.ConfiguracionGlobalService#
	 * removeConfiguracionGlobal(java.lang.Long)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.SUPER_ADMIN)
	public void removeConfiguracionGlobal(final Long idConfiguracionGlobal) {
		configuracionGlobalService.removeConfiguracionGlobal(idConfiguracionGlobal);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.ConfiguracionGlobalService#
	 * updateConfiguracionGlobal(es.caib.sistrages.core.api.model.
	 * ConfiguracionGlobal)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.SUPER_ADMIN)
	public void updateConfiguracionGlobal(final ConfiguracionGlobal configuracionGlobal) {
		configuracionGlobalService.updateConfiguracionGlobal(configuracionGlobal);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.ConfiguracionGlobalService#
	 * listConfiguracionGlobal(java.lang.String)
	 */
	@Override
	@RolesAllowed(ConstantesRolesAcceso.SUPER_ADMIN)
	public List<ConfiguracionGlobal> listConfiguracionGlobal(final String filtro) {
		return configuracionGlobalService.listConfiguracionGlobal(filtro);
	}

	@Override
	@RolesAllowed(ConstantesRolesAcceso.SUPER_ADMIN)
	public IPlugin obtenerPluginGlobal(final TypePlugin tipoPlugin) {
		return configuracionGlobalService.obtenerPluginGlobal(tipoPlugin);
	}

	@Override
	@RolesAllowed(ConstantesRolesAcceso.SUPER_ADMIN)
	public IPlugin obtenerPluginEntidad(final TypePlugin tipoPlugin, final Long idEntidad) {
		return configuracionGlobalService.obtenerPluginEntidad(tipoPlugin, idEntidad);
	}

}
