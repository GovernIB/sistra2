package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosCampo;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
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

	@Override
	@NegocioInterceptor
	public Dominio loadDominio(final String codigoDominio) {
		Dominio result = null;
		result = dominioDao.getByCodigo(codigoDominio);
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
		return fuenteDatoDao.getById(idFuenteDato);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadFuenteDato(java.lang.
	 * Long)
	 */
	@Override
	public FuenteDatosValores loadFuenteDatoValores(final Long idFuenteDato) {
		return fuenteDatoDao.getValoresById(idFuenteDato);
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
	public boolean removeFuenteDato(final Long idFuenteDato) {
		boolean borrar;
		if (!dominioDao.getAllByFuenteDatos(idFuenteDato).isEmpty()) {
			borrar = false;
		} else {
			borrar = true;
			fuenteDatoDao.remove(idFuenteDato);
		}
		return borrar;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#removeFuenteDato(java.lang.
	 * Long)
	 */
	@Override
	public void addFuenteDatoCampo(final FuenteDatosCampo fuenteDatoCampo, final Long idFuente) {
		fuenteDatoDao.addFuenteDatoCampo(fuenteDatoCampo, idFuente);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#removeFuenteDato(java.lang.
	 * Long)
	 */
	@Override
	public void updateFuenteDatoCampo(final FuenteDatosCampo fuenteDatoCampo) {
		fuenteDatoDao.updateFuenteDatoCampo(fuenteDatoCampo);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#removeFuenteDato(java.lang.
	 * Long)
	 */
	@Override
	public void removeFuenteDatoCampo(final Long idFuenteDatoCampo) {
		fuenteDatoDao.removeFuenteDatoCampo(idFuenteDatoCampo);
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

	@Override
	public FuenteFila loadFuenteDatoFila(final Long idFuenteDatoFila) {
		return fuenteDatoDao.loadFuenteDatoFila(idFuenteDatoFila);
	}

	@Override
	public void addFuenteDatoFila(final FuenteFila fila, final Long idFuente) {
		fuenteDatoDao.addFuenteDatoFila(fila, idFuente);
	}

	@Override
	public void updateFuenteDatoFila(final FuenteFila fila) {
		fuenteDatoDao.updateFuenteDatoFila(fila);
	}

	@Override
	public void removeFuenteFila(final Long idFila) {
		fuenteDatoDao.removeFuenteFila(idFila);
	}

	@Override
	public boolean isCorrectoPK(final FuenteFila fuenteFila, final Long idFuenteDato) {
		return fuenteDatoDao.isCorrectoPK(fuenteFila, idFuenteDato);
	}

}
