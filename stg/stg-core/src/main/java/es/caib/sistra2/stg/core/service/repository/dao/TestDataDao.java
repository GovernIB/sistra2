package es.caib.sistra2.stg.core.service.repository.dao;

import java.util.List;

import es.caib.sistra2.stg.core.api.model.TestData;

public interface TestDataDao {
	TestData getTestDataById(final String pCodigo);
	List<TestData> getAllTestDataByFiltroDescripcion(final String pFiltro);
	List<TestData> getAllTestData();
	void addTestData(final TestData pTestData);
	void removeTestData(final String pCodigo);
	void updateTestData(final TestData pTestData);
}