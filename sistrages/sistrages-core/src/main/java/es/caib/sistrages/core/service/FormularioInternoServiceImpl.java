package es.caib.sistrages.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
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
		return formIntDao.getFormularioById(pId);
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
		formIntDao.updateFormulario(pFormInt);
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
		return formIntDao.getFormularioPaginasById(pId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * getPaginaFormulario(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public PaginaFormulario getPaginaFormulario(final Long pId) {
		return formIntDao.getPaginaById(pId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * getContenidoPaginaFormulario(java.lang.Long)
	 */
	@Override
	public PaginaFormulario getContenidoPaginaFormulario(final Long pId) {
		return formIntDao.getContenidoPaginaById(pId);
	}

	@Override
	public ObjetoFormulario addComponenteFormulario(final TypeObjetoFormulario pTipoObjeto, final Long pIdPagina,
			final Long pIdLinea, final Integer pOrden, final String pPosicion) {
		return formIntDao.addComponente(pTipoObjeto, pIdPagina, pIdLinea, pOrden, pPosicion);
	}

	@Override
	public void updateComponenteFormulario(final ComponenteFormulario pComponente) {
		formIntDao.updateComponente(pComponente);

	}

	@Override
	public ComponenteFormulario getComponenteFormulario(final Long pId) {
		return formIntDao.getComponenteById(pId);
	}

	@Override
	public void removeComponenteFormulario(final Long pId) {
		formIntDao.removeComponenteFormulario(pId);
	}

	@Override
	public void removeLineaFormulario(final Long pId) {
		formIntDao.removeLineaFormulario(pId);
	}

	@Override
	public void updateOrdenComponenteFormulario(final Long pId, final Integer pOrden) {
		formIntDao.updateOrdenComponente(pId, pOrden);
	}

	@Override
	public void updateOrdenLineaFormulario(final Long pId, final Integer pOrden) {
		formIntDao.updateOrdenLinea(pId, pOrden);

	}
}
