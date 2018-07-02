package es.caib.sistrages.core.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.service.repository.dao.ConfiguracionGlobalDao;
import es.caib.sistrages.core.service.repository.dao.PluginsDao;


@Service
@Transactional
public class RestApiInternaServiceImpl implements RestApiInternaService {



	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(RestApiInternaServiceImpl.class);

	/**
	 * configuracion global dao.
	 */
	@Autowired
	ConfiguracionGlobalDao configuracionGlobalDao;

	/**
	 * plugin dao.
	 */
	@Autowired
	PluginsDao pluginDao;



    @Override
    @NegocioInterceptor
    public String test(String echo) {
        return "Echo: " + echo;
    }


	@Override
	@NegocioInterceptor
	public List<ConfiguracionGlobal> listConfiguracionGlobal(final String filtro) {
		return configuracionGlobalDao.getAllByFiltro(filtro);
	}


	@Override
	@NegocioInterceptor
	public List<Plugin> listPlugin(final TypeAmbito ambito, final Long idEntidad, final String filtro) {
		return pluginDao.getAllByFiltro(ambito, idEntidad, filtro);
	}


}
