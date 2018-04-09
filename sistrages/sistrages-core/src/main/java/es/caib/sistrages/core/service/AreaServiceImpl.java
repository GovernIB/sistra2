package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.service.AreaService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.AreaComponent;
import es.caib.sistrages.core.service.repository.dao.AreaDao;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {

	private final Logger log = LoggerFactory.getLogger(AreaServiceImpl.class);

	@Autowired
	AreaDao areaDataDao;

	@Autowired
	AreaComponent areaComponent;

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.AreaService#listArea(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<Area> listArea(final Long idEntidad, final String filtro) {
		return areaDataDao.getAllByFiltro(idEntidad, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.AreaService#getArea(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Area getArea(final Long id) {
		return areaDataDao.getById(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.AreaService#addArea(java.lang.Long,
	 * es.caib.sistrages.core.api.model.Area)
	 */
	@Override
	@NegocioInterceptor
	public void addArea(final Long idEntidad, final Area area) {
		areaDataDao.add(idEntidad, area);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.AreaService#removeArea(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeArea(final Long id) {
		areaDataDao.remove(id);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.caib.sistrages.core.api.service.AreaService#updateArea(es.caib.sistrages.
	 * core.api.model.Area)
	 */
	@Override
	@NegocioInterceptor
	public void updateArea(final Area area) {
		areaDataDao.update(area);
	}

}
