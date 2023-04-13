package es.caib.sistrahelp.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.service.AlertaService;
import es.caib.sistrahelp.core.interceptor.NegocioInterceptor;
import es.caib.sistrahelp.core.service.repository.dao.AlertaDao;

/**
 * La clase DominioServiceImpl.
 */

@Service
@Transactional
public class AlertaServiceImpl implements AlertaService {

	/**
	 * log.
	 */
	private final Logger log = LoggerFactory.getLogger(AlertaServiceImpl.class);

	/**
	 * Alerta dao.
	 */
	@Autowired
	AlertaDao alertaDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadDominio(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Alerta loadAlerta(final Long codAl) {
		Alerta result = null;
		result = alertaDao.getByCodigo(codAl);
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
	public Long addAlerta(final Alerta al) {
		return alertaDao.add(al);
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
	public boolean removeAlerta(final Long idAl) {
		return alertaDao.remove(idAl);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#updateDominio(es.caib.
	 * sistrages.core.api.model.Dominio)
	 */
	@Override
	@NegocioInterceptor
	public void updateAlerta(final Alerta al) {
		alertaDao.updateAlerta(al);
	}

	@Override
	@NegocioInterceptor
	public List<Alerta> listAlerta(final String filtro) {
		return alertaDao.getAllByFiltro(filtro);
	}

	@Override
	@NegocioInterceptor
	public List<Alerta> listAlertaActivo(final String filtro, final boolean activo) {
		return alertaDao.listarAlertaActivo(filtro, activo);
	}

	@Override
	@NegocioInterceptor
	public Alerta loadAlertaByNombre(String nombre) {
		return alertaDao.getAlertaByNombre(nombre);
	}
}
