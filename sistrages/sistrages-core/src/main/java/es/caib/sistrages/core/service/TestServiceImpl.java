package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.exception.TestException;
import es.caib.sistrages.core.api.model.TestData;
import es.caib.sistrages.core.api.service.TestService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.TestComponent;
import es.caib.sistrages.core.service.repository.dao.TestDataDao;

@Service
@Transactional
public class TestServiceImpl implements TestService {

	private final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

	@Autowired
	TestDataDao testDataDao;

	@Autowired
	TestComponent testComponent;

	@Override
	@NegocioInterceptor
	public List<TestData> list(String filtro) {
		List<TestData> result = null;
		if (filtro == null || filtro.trim().length() == 0) {
			result = testDataDao.getAllTestData();
		} else {
			result = testDataDao.getAllTestDataByFiltroDescripcion(filtro);
		}

		return result;
	}

	@Override
	@NegocioInterceptor
	public TestData load(String id) {
		TestData result = null;
		result = testDataDao.getTestDataById(id);
		return result;
	}

	@Override
	@NegocioInterceptor
	public void add(TestData testData) {
		if (load(testData.getCodigo()) != null) {
			throw new TestException();
		}
		testDataDao.addTestData(testData);
		log.debug("add");
	}

	@Override
	@NegocioInterceptor
	public void remove(String id) {
		testDataDao.removeTestData(id);
	}

	@Override
	@NegocioInterceptor
	public void update(TestData testData) {
		testDataDao.updateTestData(testData);
	}

	@Override
	@NegocioInterceptor
	public void testTransactionNew() {
		TestData testData = new TestData();
		testData.setCodigo(System.currentTimeMillis() +"");
		testData.setDescripcion("testTransactionNew");
		testComponent.add(testData);
		throw new TestException("Prueba para generar un error dentro de una transaccion y ver que la otra termina.");
	}
}
