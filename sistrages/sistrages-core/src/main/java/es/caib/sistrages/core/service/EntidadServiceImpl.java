package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.EntidadDao;

@Service
@Transactional
public class EntidadServiceImpl implements EntidadService {

	private final Logger log = LoggerFactory.getLogger(EntidadServiceImpl.class);

	@Autowired
	EntidadDao entidadDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#loadEntidad(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Entidad loadEntidad(final Long idEntidad) {
		Entidad result = null;
		result = entidadDao.getById(idEntidad);
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#addEntidad(es.caib.
	 * sistrages.core.api.model.Entidad)
	 */
	@Override
	@NegocioInterceptor
	public void addEntidad(final Entidad entidad) {
		entidadDao.add(entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#removeEntidad(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeEntidad(final Long idEntidad) {

		// TODO Verificar dependencias
		// TODO Gestion borrado ficheros

		entidadDao.remove(idEntidad);

		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#
	 * updateEntidadSuperAdministrador(es.caib.sistrages.core.api.model.Entidad)
	 */
	@Override
	@NegocioInterceptor
	public void updateEntidadSuperAdministrador(final Entidad entidad) {
		entidadDao.updateSuperAdministrador(entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#listEntidad(es.caib.
	 * sistrages.core.api.model.types.TypeIdioma, java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<Entidad> listEntidad(final TypeIdioma idioma, final String filtro) {
		return entidadDao.getAllByFiltro(idioma, filtro);
	}
}
