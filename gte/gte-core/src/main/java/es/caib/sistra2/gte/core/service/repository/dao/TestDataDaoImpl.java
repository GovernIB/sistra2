package es.caib.sistra2.gte.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.caib.sistra2.gte.core.api.model.TestData;
import es.caib.sistra2.gte.core.service.repository.model.*;

@Repository("testDataDao")
public class TestDataDaoImpl implements TestDataDao {

	/** EntityManager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public TestData getTestDataById(final String pCodigo) {
		TestData testdata = null;

		HTestData htestdata = getHTestDataById(pCodigo);

		if (htestdata != null) {
			// Establecemos datos
			testdata = new TestData();
			testdata.setCodigo(htestdata.getCodigo());
			testdata.setDescripcion(htestdata.getDescripcion());
		}

		return testdata;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestData> getAllTestDataByFiltroDescripcion(final String pFiltro) {
		TestData testdata = null;
		List<TestData> resultado = new ArrayList<>();

		final Query query = entityManager.createQuery("Select t From HTestData t Where upper(t.descripcion) like :filtro");
		query.setParameter("filtro", "%"+pFiltro.toUpperCase()+"%");

		final List<HTestData> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (Iterator<HTestData> iterator = results.iterator(); iterator.hasNext();) {
				HTestData htestdata = iterator.next();

				// Establecemos datos
				testdata = new TestData();
				testdata.setCodigo(htestdata.getCodigo());
				testdata.setDescripcion(htestdata.getDescripcion());

				resultado.add(testdata);
			}
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestData> getAllTestData() {
		TestData testdata = null;
		List<TestData> resultado = new ArrayList<>();

		final Query query = entityManager.createQuery("Select t From HTestData t");

		final List<HTestData> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (Iterator<HTestData> iterator = results.iterator(); iterator.hasNext();) {
				HTestData htestdata = iterator.next();

				// Establecemos datos
				testdata = new TestData();
				testdata.setCodigo(htestdata.getCodigo());
				testdata.setDescripcion(htestdata.getDescripcion());

				resultado.add(testdata);
			}
		}

		return resultado;
	}

	@Override
	public void addTestData(TestData pTestData) {
		HTestData htestdata = new HTestData();
		htestdata.setCodigo(pTestData.getCodigo());
		htestdata.setDescripcion(pTestData.getDescripcion());

		entityManager.persist(htestdata);
		entityManager.flush();
	}

	@Override
	public void removeTestData(String pCodigo) {

		HTestData htestdata = getHTestDataById(pCodigo);
		if (htestdata != null) {
			entityManager.remove(htestdata);
		}

	}

	@SuppressWarnings("unchecked")
	private HTestData getHTestDataById(final String pCodigo) {
		HTestData htestdata = null;

		final Query query = entityManager.createQuery("Select t From HTestData t Where t.codigo = :codigo");
		query.setParameter("codigo", pCodigo);

		final List<HTestData> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			htestdata = results.get(0);
		}

		return htestdata;
	}

	@Override
	public void updateTestData(TestData pTestData) {

		HTestData htestdata = getHTestDataById(pTestData.getCodigo());
		if (htestdata != null) {
			htestdata.setDescripcion(pTestData.getDescripcion());
			entityManager.merge(htestdata);
			entityManager.flush();
		}
	}
}
