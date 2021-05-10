package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.ConfiguracionAutenticacionDao;

/**
 * La clase ConfiguracionAutenticacionServiceImpl.
 */
@Service
@Transactional
public class ConfiguracionAutenticacionServiceImpl implements ConfiguracionAutenticacionService {

	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ConfiguracionAutenticacionServiceImpl.class);

	/**
	 * Configuracion Autenticacion dao.
	 */
	@Autowired
	ConfiguracionAutenticacionDao configuracionAutenticacionDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioExternoService#
	 * getFormularioExterno(java. lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public ConfiguracionAutenticacion getConfiguracionAutenticacion(final Long idConfiguracionAutenticacion) {
		return configuracionAutenticacionDao.getById(idConfiguracionAutenticacion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioExternoService#
	 * addFormularioExterno(java. lang.Long,
	 * es.caib.sistrages.core.api.model.FormularioExterno)
	 */
	@Override
	@NegocioInterceptor
	public Long addConfiguracionAutenticacion(final Long idArea, final ConfiguracionAutenticacion confAutenticacion) {
		return configuracionAutenticacionDao.add(idArea, confAutenticacion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioExternoService#
	 * removeFormularioExterno( java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeConfiguracionAutenticacion(final Long idConfAutenticacion) {
		configuracionAutenticacionDao.remove(idConfAutenticacion);
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioExternoService#
	 * updateFormularioExterno(es. caib.sistrages.core.api.model.FormularioExterno)
	 */
	@Override
	@NegocioInterceptor
	public void updateConfiguracionAutenticacion(final ConfiguracionAutenticacion confAutenticacion) {
		configuracionAutenticacionDao.update(confAutenticacion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioExternoService#
	 * listFormularioExterno(java. lang.Long,
	 * es.caib.sistrages.core.api.model.types.TypeIdioma, java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<ConfiguracionAutenticacion> listConfiguracionAutenticacion(final Long idArea, final TypeIdioma idioma,
			final String filtro) {
		return configuracionAutenticacionDao.getAllByFiltro(idArea, idioma, filtro);
	}

	@Override
	@NegocioInterceptor
	public boolean existeConfiguracionAutenticacion(final String identificador, final Long idCodigo) {
		return configuracionAutenticacionDao.existeConfiguracionAutenticacion(identificador, idCodigo);
	}

	@Override
	@NegocioInterceptor
	public ConfiguracionAutenticacion getConfiguracionAutenticacion(String identificador, Long codigo) {
		return configuracionAutenticacionDao.getConfiguracionAutenticacion(identificador, codigo);
	}

}
