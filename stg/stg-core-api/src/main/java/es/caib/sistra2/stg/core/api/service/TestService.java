package es.caib.sistra2.stg.core.api.service;

import java.util.List;

import es.caib.sistra2.stg.core.api.model.TestData;

/**
 * Test Service.
 * @author Indra.
 *
 */
public interface TestService {

	public List<TestData> list(String filtro);

	public TestData load(String id);

	public void add(TestData testData);

	public void remove(String id);

	public void update(TestData testData);

	void testTransactionNew();

}
