package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.service.PluginService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.PluginsDao;

/**
 * La clase PluginServiceImpl.
 */
@Service
@Transactional
public class PluginServiceImpl implements PluginService {

	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PluginServiceImpl.class);

	/**
	 * plugin dao.
	 */
	@Autowired
	PluginsDao pluginDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.PluginService#getPlugin(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Plugin getPlugin(final Long id) {
		return pluginDao.getById(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.PluginService#addPlugin(es.caib.sistrages.
	 * core.api.model.Plugin, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void addPlugin(final Plugin plugin, final Long idEntidad) {
		pluginDao.add(plugin, idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.PluginService#removePlugin(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removePlugin(final Long id) {
		pluginDao.remove(id);
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.PluginService#updatePlugin(es.caib.
	 * sistrages.core.api.model.Plugin)
	 */
	@Override
	@NegocioInterceptor
	public void updatePlugin(final Plugin plugin) {
		pluginDao.update(plugin);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.PluginService#listPlugin(es.caib.sistrages
	 * .core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<Plugin> listPlugin(final TypeAmbito ambito, final Long idEntidad, final String filtro) {
		return pluginDao.getAllByFiltro(ambito, idEntidad, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.PluginService#listPlugin(es.caib.sistrages
	 * .core.api.model.types.TypeAmbito, java.lang.Long, TypePlugin)
	 */
	@Override
	@NegocioInterceptor
	public List<Plugin> listPlugin(final TypeAmbito ambito, final Long idEntidad, final TypePlugin tipo) {
		return pluginDao.getAllByFiltro(ambito, idEntidad, tipo);
	}

}
