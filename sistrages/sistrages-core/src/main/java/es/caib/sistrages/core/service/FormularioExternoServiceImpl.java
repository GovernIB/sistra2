package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.FormularioExternoService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.FormularioExternoDao;

/**
 * La clase FormularioExternoServiceImpl.
 */
@Service
@Transactional
public class FormularioExternoServiceImpl implements FormularioExternoService {

	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(FormularioExternoServiceImpl.class);

	/**
	 * aviso entidad dao.
	 */
	@Autowired
	FormularioExternoDao formularioExternoDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioExternoService#
	 * getFormularioExterno(java. lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public GestorExternoFormularios getFormularioExterno(final Long idFormularioExterno) {
		return formularioExternoDao.getById(idFormularioExterno);
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
	public void addFormularioExterno(final Long idEntidad, final GestorExternoFormularios FormularioExterno) {
		formularioExternoDao.add(idEntidad, FormularioExterno);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioExternoService#
	 * removeFormularioExterno( java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeFormularioExterno(final Long idFormularioExterno) {
		formularioExternoDao.remove(idFormularioExterno);
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
	public void updateFormularioExterno(final GestorExternoFormularios FormularioExterno) {
		formularioExternoDao.update(FormularioExterno);
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
	public List<GestorExternoFormularios> listFormularioExterno(final Long idEntidad, final TypeIdioma idioma,
			final String filtro) {
		return formularioExternoDao.getAllByFiltro(idEntidad, idioma, filtro);
	}

}
