package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.PluginService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.PluginsDao;

/**
 * PluginService implementación.
 */
@Service
@Transactional
public class PluginServiceImpl implements PluginService {

	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PluginServiceImpl.class);

	/**
	 * aviso entidad dao.
	 */
	@Autowired
	PluginsDao pluginDao;

	@Override
	@NegocioInterceptor
	public Plugin getPlugin(final Long id) {
		return pluginDao.getById(id);
	}

	@Override
	@NegocioInterceptor
	public void addPlugin(final Plugin plugin, final Long idEntidad) {
		pluginDao.add(plugin, idEntidad);
	}

	@Override
	@NegocioInterceptor
	public boolean removePlugin(final Long id) {
		pluginDao.remove(id);
		return true;
	}

	@Override
	@NegocioInterceptor
	public void updatePlugin(final Plugin plugin) {
		pluginDao.update(plugin);
	}

	@Override
	@NegocioInterceptor
	public List<Plugin> listPlugin(final TypeAmbito ambito, final Long idEntidad, final String filtro) {
		return pluginDao.getAllByFiltro(ambito, idEntidad, filtro);
	}

}
