package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.api.service.RolService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.RolDao;

/**
 * La clase RolServiceImpl.
 */
@Service
@Transactional
public class RolServiceImpl implements RolService {

	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(RolServiceImpl.class);

	/**
	 * aviso entidad dao.
	 */
	@Autowired
	RolDao rolDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.RolService#getRol(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Rol getRol(final Long idRol) {
		return rolDao.getById(idRol);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.RolService#addRol(es.caib.sistrages.core.
	 * api.model.Rol)
	 */
	@Override
	@NegocioInterceptor
	public void addRol(final Rol rol) {

		// TODO Pendiente control acceso entidad

		rolDao.add(rol);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.RolService#removeRol(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeRol(final Long idRol) {

		// TODO Pendiente control acceso entidad
		rolDao.remove(idRol);
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.RolService#updateRol(es.caib.sistrages.
	 * core.api.model.Rol)
	 */
	@Override
	@NegocioInterceptor
	public void updateRol(final Rol rol) {

		// TODO Pendiente control acceso entidad
		rolDao.update(rol);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.RolService#listRol(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<Rol> listRol(final Long idEntidad, final String filtro) {
		return rolDao.getAllByFiltro(idEntidad, filtro);
	}

}
