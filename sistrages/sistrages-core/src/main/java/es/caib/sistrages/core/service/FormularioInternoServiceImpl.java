package es.caib.sistrages.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;

@Service
@Transactional
public class FormularioInternoServiceImpl implements FormularioInternoService {

	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(FormularioInternoServiceImpl.class);

	@Autowired
	FormularioInternoDao formIntDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * getFormularioInterno(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public FormularioInterno getFormularioInterno(final Long pId) {
		return formIntDao.getById(pId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * updateFormularioInterno(es.caib.sistrages.core.api.model.FormularioInterno)
	 */
	@Override
	@NegocioInterceptor
	public void updateFormularioInterno(final FormularioInterno pFormInt) {
		formIntDao.update(pFormInt);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * getFormularioInternoPaginas(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public FormularioInterno getFormularioInternoPaginas(final Long pId) {
		return formIntDao.getFormPagById(pId);
	}
}
