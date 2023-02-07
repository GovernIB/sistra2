package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.VariableArea;

import es.caib.sistrages.core.api.service.VariablesAreaService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.FuenteDatosComponent;
import es.caib.sistrages.core.service.repository.dao.AreaDao;
import es.caib.sistrages.core.service.repository.dao.ConfiguracionAutenticacionDao;
import es.caib.sistrages.core.service.repository.dao.VariableAreaDao;

/**
 * La clase DominioServiceImpl.
 */

@Service
@Transactional
public class VariablesAreaServiceImpl implements VariablesAreaService {

	/**
	 * log.
	 */
	private final Logger log = LoggerFactory.getLogger(VariablesAreaServiceImpl.class);

	@Autowired
	AreaDao areaDao;

	/**
	 * dominio dao.
	 */
	@Autowired
	VariableAreaDao vaDao;

	/**
	 * config aute dao.
	 */
	@Autowired
	ConfiguracionAutenticacionDao configAutDao;

	/**
	 * dominio dao.
	 */
	@Autowired
	FuenteDatosComponent fuenteDatosComponent;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadDominio(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public VariableArea loadVariableArea(final Long codVa) {
		VariableArea result = null;
		result = vaDao.getByCodigo(codVa);
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#addDominio(es.caib.
	 * sistrages.core.api.model.Dominio, java.lang.Long, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Long addVariableArea(final VariableArea va) {
		return vaDao.add(va);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#removeDominio(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeVariableArea(final Long idVa) {
		return vaDao.remove(idVa);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#updateDominio(es.caib.
	 * sistrages.core.api.model.Dominio)
	 */
	@Override
	@NegocioInterceptor
	public void updateVariableArea(final VariableArea va) {
		vaDao.updateVariableArea(va);
	}

	@Override
	@NegocioInterceptor
	public List<VariableArea> listVariableArea(final Long idVa, final String filtro) {
		return vaDao.getAllByFiltro(idVa, filtro);
	}

	@Override
	@NegocioInterceptor
	public VariableArea loadVariableAreaByIdentificador(String identificador, Long codigoArea) {
		return vaDao.getVariableAreaByIdentificador(identificador, codigoArea);
	}

	@Override
	@NegocioInterceptor
	public List<Dominio> dominioByVariable(VariableArea va) {
		return vaDao.dominioByVariable(va);
	}

	@Override
	@NegocioInterceptor
	public List<GestorExternoFormularios> gfeByVariable(VariableArea va) {
		return vaDao.gfeByVariable(va);
	}

	@Override
	@NegocioInterceptor
	public List<EnvioRemoto> envioRemotoByVariable(VariableArea va) {
		return vaDao.envioRemotoByVariable(va);
	}

}
