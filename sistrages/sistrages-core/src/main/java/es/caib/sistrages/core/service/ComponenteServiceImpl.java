package es.caib.sistrages.core.service;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.service.ComponenteService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.ConfiguracionComponent;

/**
 * La clase ComponenteServiceImpl.
 */
@Service
@Transactional
public class ComponenteServiceImpl implements ComponenteService {

	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ComponenteServiceImpl.class);

	/**
	 * plugin dao.
	 */
	@Autowired
	ConfiguracionComponent configuracionComponente;

	@Override
	@NegocioInterceptor
	public IPlugin obtenerPluginGlobal(final TypePlugin tipoPlugin) {
		return configuracionComponente.obtenerPluginGlobal(tipoPlugin);
	}

	@Override
	@NegocioInterceptor
	public IPlugin obtenerPluginEntidad(final TypePlugin tipoPlugin, final Long idEntidad) {
		return configuracionComponente.obtenerPluginEntidad(tipoPlugin, idEntidad);

	}

}
