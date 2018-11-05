package es.caib.sistrages.core.service.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.service.repository.dao.AreaDao;

@Component
public class AreaComponentImpl implements AreaComponent {

	@Autowired
	AreaDao areaDao;

	@Override
	public void add(final Long idEntidad, final Area area) {
		areaDao.add(idEntidad, area);
	}
}
