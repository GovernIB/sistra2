package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormateador;
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

	@Override
	@NegocioInterceptor
	public FormateadorFormulario getFormateadorFormulario(final String codigo) {
		return fmtDao.getByCodigo(codigo);
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
	public void addFormateadorFormulario(final Long idEntidad, final FormateadorFormulario fmt) {
		fmtDao.add(idEntidad, fmt);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormateadorFormularioService#
	 * removeFormateadorFormulario(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void removeFormateadorFormulario(final Long idFmt) {
		fmtDao.remove(idFmt);
	}

	@Override
	@NegocioInterceptor
	public void updateFormateadorFormulario(final FormateadorFormulario rol, final Long idEntidad) {
		fmtDao.update(rol, idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormateadorFormularioService#
	 * listFormateadorFormulario(java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<FormateadorFormulario> listFormateadorFormulario(final Long idEntidad, final String filtro,
			final Boolean bloqueado) {
		return fmtDao.getAllByFiltro(idEntidad, filtro, bloqueado);
	}

	@Override
	@NegocioInterceptor
	public boolean tieneRelacionesFormateadorFormulario(final Long idFmt) {
		return fmtDao.tieneRelacionesFormateadorFormulario(idFmt);
	}

	@Override
	@NegocioInterceptor
	public List<PlantillaFormateador> getListaPlantillasFormateador(final Long idFormateador) {
		return fmtDao.getListaPlantillasFormateador(idFormateador);
	}

	@Override
	@NegocioInterceptor
	public FormateadorFormulario getFormateadorPorDefecto(final Long idEntidad) {
		return fmtDao.getFormateadorPorDefecto(idEntidad, null);
	}

}
