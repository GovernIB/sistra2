package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.ConfiguracionGlobalDao;

/**
 * La clase ConfiguracionGlobalServiceImpl.
 */
@Service
@Transactional
public class ConfiguracionGlobalServiceImpl implements ConfiguracionGlobalService {

	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ConfiguracionGlobalServiceImpl.class);

	/**
	 * configuracion global dao.
	 */
	@Autowired
	ConfiguracionGlobalDao configuracionGlobalDao;

	@Override
	@NegocioInterceptor
	public ConfiguracionGlobal getConfiguracionGlobal(final Long idConfiguracionGlobal) {
		return configuracionGlobalDao.getById(idConfiguracionGlobal);
	}

	@Override
	@NegocioInterceptor
	public ConfiguracionGlobal getConfiguracionGlobal(final String propiedad) {
		return configuracionGlobalDao.getByPropiedad(propiedad);
	}

	@Override
	@NegocioInterceptor
	public void addConfiguracionGlobal(final ConfiguracionGlobal configuracionGlobal) {
		configuracionGlobalDao.add(configuracionGlobal);
	}

	@Override
	@NegocioInterceptor
	public void removeConfiguracionGlobal(final Long idConfiguracionGlobal) {
		configuracionGlobalDao.remove(idConfiguracionGlobal);
	}

	@Override
	@NegocioInterceptor
	public void updateConfiguracionGlobal(final ConfiguracionGlobal configuracionGlobal) {
		configuracionGlobalDao.update(configuracionGlobal);

	}

	@Override
	@NegocioInterceptor
	public List<ConfiguracionGlobal> listConfiguracionGlobal(final String filtro) {
		return configuracionGlobalDao.getAllByFiltro(filtro);
	}

}
