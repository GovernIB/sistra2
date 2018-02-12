package es.caib.sistrages.core.service.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.service.repository.dao.AreaDao;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AreaComponentImpl implements AreaComponent {

	@Autowired
	AreaDao areaDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.component.TestComponent#add(es.caib.sistrages.core.api.model.TestData)
	 */
	@Override
	public void add(final Area area) {
		areaDao.addArea(area);
	}

}
