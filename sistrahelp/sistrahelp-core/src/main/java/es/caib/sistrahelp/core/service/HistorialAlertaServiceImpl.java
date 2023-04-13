package es.caib.sistrahelp.core.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrahelp.core.api.model.HistorialAlerta;
import es.caib.sistrahelp.core.api.service.HistorialAlertaService;
import es.caib.sistrahelp.core.interceptor.NegocioInterceptor;
import es.caib.sistrahelp.core.service.repository.dao.HistorialAlertaDao;

/**
 * La clase DominioServiceImpl.
 */

@Service
@Transactional
public class HistorialAlertaServiceImpl implements HistorialAlertaService {

	/**
	 * log.
	 */
	private final Logger log = LoggerFactory.getLogger(HistorialAlertaServiceImpl.class);

	/**
	 * HistorialAlerta dao.
	 */
	@Autowired
	HistorialAlertaDao historialAlertaDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadDominio(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public HistorialAlerta loadHistorialAlerta(final Long codAl) {
		HistorialAlerta result = null;
		result = historialAlertaDao.getByCodigo(codAl);
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
	public Long addHistorialAlerta(final HistorialAlerta al) {
		return historialAlertaDao.add(al);
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
	public boolean removeHistorialAlerta(final Long idAl) {
		return historialAlertaDao.remove(idAl);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#updateDominio(es.caib.
	 * sistrages.core.api.model.Dominio)
	 */
	@Override
	@NegocioInterceptor
	public void updateHistorialAlerta(final HistorialAlerta al) {
		historialAlertaDao.updateHistorialAlerta(al);
	}

	@Override
	@NegocioInterceptor
	public List<HistorialAlerta> listHistorialAlerta(Date desde, Date hasta) {
		return historialAlertaDao.getAllByFiltro(desde, hasta);
	}

	@Override
	@NegocioInterceptor
	public HistorialAlerta loadHistorialAlertaByAlerta(Long codAlerta) {
		return historialAlertaDao.getHistorialAlertaByAlerta(codAlerta);
	}
}
