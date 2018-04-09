package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.service.FormateadorFormularioService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao;

/**
 * La clase FormateadorFormulario.
 */
@Service
@Transactional
public class FormateadorFormularioServiceImpl implements FormateadorFormularioService {

	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(FormateadorFormularioServiceImpl.class);

	/**
	 * FormateadorFormulario
	 */
	@Autowired
	FormateadorFormularioDao fmtDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormateadorFormularioService#
	 * getFormateadorFormulario(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public FormateadorFormulario getFormateadorFormulario(final Long idFmt) {
		return fmtDao.getById(idFmt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.caib.sistrages.core.api.service.FormateadorFormularioService#
	 * addFormateadorFormulario(es.caib.sistrages.core.api.model.
	 * FormateadorFormulario)
	 */
	@Override
	@NegocioInterceptor
	public void addFormateadorFormulario(final FormateadorFormulario fmt) {

		// TODO Pendiente control acceso entidad

		fmtDao.add(fmt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.caib.sistrages.core.api.service.FormateadorFormularioService#
	 * removeFormateadorFormulario(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeFormateadorFormulario(final Long idFmt) {

		// TODO Pendiente control acceso entidad
		fmtDao.remove(idFmt);
		return true;
	}

	@Override
	@NegocioInterceptor
	public void updateFormateadorFormulario(final FormateadorFormulario rol) {

		// TODO Pendiente control acceso entidad
		fmtDao.update(rol);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.caib.sistrages.core.api.service.FormateadorFormularioService#
	 * listFormateadorFormulario(java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<FormateadorFormulario> listFormateadorFormulario(final String filtro) {
		return fmtDao.getAllByFiltro(filtro);
	}

}
