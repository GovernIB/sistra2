package es.caib.sistra2.stg.core.service.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistra2.stg.core.api.model.TestData;
import es.caib.sistra2.stg.core.service.repository.dao.TestDataDao;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TestComponentImpl implements TestComponent  {

	@Autowired
	TestDataDao testDataDao;



	/* (non-Javadoc)
	 * @see es.caib.sistra2.stg.core.service.component.TestComponent#add(es.caib.sistra2.stg.core.api.model.TestData)
	 */
	@Override
	public void add(TestData testData) {
		testDataDao.addTestData(testData);

	}




}
