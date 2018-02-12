package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.service.repository.model.HArea;

@Repository("areaDao")
public class AreaDaoImpl implements AreaDao {

	/** EntityManager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Area getAreaById(final String pCodigo) {
		Area testdata = null;

		final HArea htestdata = getHAreaById(pCodigo);

		if (htestdata != null) {
			// Establecemos datos
			testdata = new Area();
			testdata.setCodigo(htestdata.getCodigo());
			testdata.setDescripcion(htestdata.getDescripcion());
		}

		return testdata;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getAllAreaByFiltroDescripcion(final String pFiltro) {
		Area testdata = null;
		final List<Area> resultado = new ArrayList<>();

		final Query query = entityManager.createQuery("Select t From HArea t Where upper(t.descripcion) like :filtro");
		query.setParameter("filtro", "%" + pFiltro.toUpperCase() + "%");

		final List<HArea> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<HArea> iterator = results.iterator(); iterator.hasNext();) {
				final HArea htestdata = iterator.next();

				// Establecemos datos
				testdata = new Area();
				testdata.setCodigo(htestdata.getCodigo());
				testdata.setDescripcion(htestdata.getDescripcion());

				resultado.add(testdata);
			}
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getAllArea() {
		Area testdata = null;
		final List<Area> resultado = new ArrayList<>();

		final Query query = entityManager.createQuery("Select t From HArea t");

		final List<HArea> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<HArea> iterator = results.iterator(); iterator.hasNext();) {
				final HArea htestdata = iterator.next();

				// Establecemos datos
				testdata = new Area();
				testdata.setCodigo(htestdata.getCodigo());
				testdata.setDescripcion(htestdata.getDescripcion());

				resultado.add(testdata);
			}
		}

		return resultado;
	}

	@Override
	public void addArea(final Area pArea) {
		final HArea htestdata = new HArea();
		htestdata.setCodigo(pArea.getCodigo());
		htestdata.setDescripcion(pArea.getDescripcion());

		entityManager.persist(htestdata);
		entityManager.flush();
	}

	@Override
	public void removeArea(final String pCodigo) {

		final HArea htestdata = getHAreaById(pCodigo);
		if (htestdata != null) {
			entityManager.remove(htestdata);
		}

	}

	@SuppressWarnings("unchecked")
	private HArea getHAreaById(final String pCodigo) {
		HArea htestdata = null;

		final Query query = entityManager.createQuery("Select t From HArea t Where t.codigo = :codigo");
		query.setParameter("codigo", pCodigo);

		final List<HArea> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			htestdata = results.get(0);
		}

		return htestdata;
	}

	@Override
	public void updateArea(final Area pArea) {

		final HArea htestdata = getHAreaById(pArea.getCodigo());
		if (htestdata != null) {
			htestdata.setDescripcion(pArea.getDescripcion());
			entityManager.merge(htestdata);
			entityManager.flush();
		}
	}
}
