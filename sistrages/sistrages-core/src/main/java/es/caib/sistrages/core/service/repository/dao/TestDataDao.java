package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.TestData;

public interface TestDataDao {
	TestData getTestDataById(final String pCodigo);
	List<TestData> getAllTestDataByFiltroDescripcion(final String pFiltro);
	List<TestData> getAllTestData();
	void addTestData(final TestData pTestData);
	void removeTestData(final String pCodigo);
	void updateTestData(final TestData pTestData);
}