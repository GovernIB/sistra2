package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;

/**
 * La clase ConfiguracionAutenticacionServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ConfiguracionAutenticacionServiceBean implements ConfiguracionAutenticacionService {

	/**
	 * aviso entidad service.
	 */
	@Autowired
	ConfiguracionAutenticacionService configuracionAutenticacionService;

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService#
	 * getConfiguracionAutenticacion(java. lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ConfiguracionAutenticacion getConfiguracionAutenticacion(final Long id) {
		return configuracionAutenticacionService.getConfiguracionAutenticacion(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService#
	 * addConfiguracionAutenticacion(java. lang.Long,
	 * es.caib.sistrages.core.api.model.ConfiguracionAutenticacion)
	 */
	@Override
	@RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Long addConfiguracionAutenticacion(final Long idEntidad, final ConfiguracionAutenticacion configuracionAutenticacion) {
		return configuracionAutenticacionService.addConfiguracionAutenticacion(idEntidad, configuracionAutenticacion);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService#
	 * removeConfiguracionAutenticacion( java.lang.Long)
	 */
	@Override
	@RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean removeConfiguracionAutenticacion(final Long id) {
		return configuracionAutenticacionService.removeConfiguracionAutenticacion(id);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService#
	 * updateConfiguracionAutenticacion(es. caib.sistrages.core.api.model.ConfiguracionAutenticacion)
	 */
	@Override
	@RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateConfiguracionAutenticacion(final ConfiguracionAutenticacion configuracionAutenticacion) {
		configuracionAutenticacionService.updateConfiguracionAutenticacion(configuracionAutenticacion);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService#
	 * listConfiguracionAutenticacion(java. lang.Long,
	 * es.caib.sistrages.core.api.model.types.TypeIdioma, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<ConfiguracionAutenticacion> listConfiguracionAutenticacion(final Long idEntidad, final TypeIdioma idioma,
			final String filtro) {
		return configuracionAutenticacionService.listConfiguracionAutenticacion(idEntidad, idioma, filtro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean existeConfiguracionAutenticacion(final String identificador, final Long codigo) {
		return configuracionAutenticacionService.existeConfiguracionAutenticacion(identificador, codigo);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ConfiguracionAutenticacion getConfiguracionAutenticacion(String identificador, Long codigo) {
		return configuracionAutenticacionService.getConfiguracionAutenticacion(identificador, codigo);
	}

}
