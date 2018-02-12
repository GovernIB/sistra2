package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.exception.TestException;
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

	@Override
	@NegocioInterceptor
	public List<Area> list(final String filtro) {
		List<Area> result = null;
		if (filtro == null || filtro.trim().length() == 0) {
			result = areaDataDao.getAllArea();
		} else {
			result = areaDataDao.getAllAreaByFiltroDescripcion(filtro);
		}

		return result;
	}

	@Override
	@NegocioInterceptor
	public Area load(final String id) {
		Area result = null;
		result = areaDataDao.getAreaById(id);
		return result;
	}

	@Override
	@NegocioInterceptor
	public void add(final Area testData) {
		if (load(testData.getCodigo()) != null) {
			throw new TestException("Dato repetido");
		}
		areaDataDao.addArea(testData);
		log.debug("add");
	}

	@Override
	@NegocioInterceptor
	public void remove(final String id) {
		areaDataDao.removeArea(id);
	}

	@Override
	@NegocioInterceptor
	public void update(final Area area) {
		areaDataDao.updateArea(area);
	}

}
