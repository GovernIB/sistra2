package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.AvisoEntidadService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.AvisoEntidadDao;

/**
 * La clase AvisoEntidadServiceImpl.
 */
@Service
@Transactional
public class AvisoEntidadServiceImpl implements AvisoEntidadService {

	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AvisoEntidadServiceImpl.class);

	/**
	 * aviso entidad dao.
	 */
	@Autowired
	AvisoEntidadDao avisoEntidadDao;

	@Override
	@NegocioInterceptor
	public AvisoEntidad getAvisoEntidad(final Long idAvisoEntidad) {
		return avisoEntidadDao.getById(idAvisoEntidad);
	}

	@Override
	@NegocioInterceptor
	public void addAvisoEntidad(final Long idEntidad, final AvisoEntidad avisoEntidad) {

		// TODO Pendiente control acceso entidad

		avisoEntidadDao.add(idEntidad, avisoEntidad);
	}

	@Override
	@NegocioInterceptor
	public void removeAvisoEntidad(final Long idAvisoEntidad) {

		// TODO Pendiente control acceso entidad

		avisoEntidadDao.remove(idAvisoEntidad);
	}

	@Override
	@NegocioInterceptor
	public void updateAvisoEntidad(final AvisoEntidad avisoEntidad) {

		// TODO Pendiente control acceso entidad

		avisoEntidadDao.update(avisoEntidad);

	}

	@Override
	@NegocioInterceptor
	public List<AvisoEntidad> listAvisoEntidad(final Long idEntidad, final TypeIdioma idioma, final String filtro) {
		return avisoEntidadDao.getAllByFiltro(idEntidad, idioma, filtro);
	}

}
