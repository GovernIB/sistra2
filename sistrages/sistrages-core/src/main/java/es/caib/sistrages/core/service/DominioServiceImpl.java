package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.FuenteDatoDao;

/**
 * La clase DominioServiceImpl.
 */
@Service
@Transactional
public class DominioServiceImpl implements DominioService {

	/**
	 * log.
	 */
	private final Logger log = LoggerFactory.getLogger(DominioServiceImpl.class);

	/**
	 * dominio dao.
	 */
	@Autowired
	DominioDao dominioDao;

	/**
	 * fuente dato dao.
	 */
	@Autowired
	FuenteDatoDao fuenteDatoDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadDominio(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Dominio loadDominio(final Long idDominio) {
		Dominio result = null;
		result = dominioDao.getById(idDominio);
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
	public void addDominio(final Dominio entidad, final Long idEntidad, final Long idArea) {
		dominioDao.add(entidad, idEntidad, idArea);
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
	public boolean removeDominio(final Long idDominio) {
		dominioDao.remove(idDominio);
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#listDominio(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<Dominio> listDominio(final TypeAmbito ambito, final Long id, final String filtro) {
		return dominioDao.getAllByFiltro(ambito, id, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#updateDominio(es.caib.
	 * sistrages.core.api.model.Dominio)
	 */
	@Override
	public void updateDominio(final Dominio dominio) {
		dominioDao.updateDominio(dominio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadFuenteDato(java.lang.
	 * Long)
	 */
	@Override
	public FuenteDatos loadFuenteDato(final Long idFuenteDato) {
		FuenteDatos result = null;
		result = fuenteDatoDao.getById(idFuenteDato);
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#addFuenteDato(es.caib.
	 * sistrages.core.api.model.FuenteDatos, java.lang.Long)
	 */
	@Override
	public void addFuenteDato(final FuenteDatos fuenteDato, final Long id) {
		fuenteDatoDao.add(fuenteDato, id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#updateFuenteDato(es.caib.
	 * sistrages.core.api.model.FuenteDatos)
	 */
	@Override
	public void updateFuenteDato(final FuenteDatos fuenteDato) {
		fuenteDatoDao.updateFuenteDato(fuenteDato);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#removeFuenteDato(java.lang.
	 * Long)
	 */
	@Override
	public void removeFuenteDato(final Long idFuenteDato) {
		fuenteDatoDao.remove(idFuenteDato);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#listFuenteDato(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	public List<FuenteDatos> listFuenteDato(final TypeAmbito ambito, final Long id, final String filtro) {
		return fuenteDatoDao.getAllByFiltro(ambito, id, filtro);
	}

}
