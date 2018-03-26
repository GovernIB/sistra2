package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JEntidad;

@Repository("areaDao")
public class AreaDaoImpl implements AreaDao {

	/** EntityManager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Area getById(final Long pCodigo) {
		Area area = null;

		final JArea jarea = getJAreaById(pCodigo);
		if (jarea != null) {
			area = jarea.toModel();
		}

		return area;
	}

	@Override
	public List<Area> getAllByFiltro(final String pFiltro) {
		return listarAreas(pFiltro);
	}

	@Override
	public List<Area> getAll() {
		return listarAreas(null);
	}

	@Override
	public void add(final Long idEntidad, final Area pArea) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		final JArea jArea = JArea.fromModel(pArea);
		jArea.setEntidad(jEntidad);
		entityManager.persist(jArea);
	}

	@Override
	public void remove(final Long pCodigo) {
		final JArea htestdata = getJAreaById(pCodigo);
		if (htestdata == null) {
			throw new NoExisteDato("No existe area: " + pCodigo);
		}
		entityManager.remove(htestdata);
	}

	@SuppressWarnings("unchecked")
	private JArea getJAreaById(final Long pCodigo) {
		JArea htestdata = null;

		final Query query = entityManager.createQuery("Select t From JArea t Where t.codigo = :codigo");
		query.setParameter("codigo", pCodigo);

		final List<JArea> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			htestdata = results.get(0);
		}

		return htestdata;
	}

	@Override
	public void update(final Area pArea) {
		final JArea jarea = getJAreaById(pArea.getId());
		if (jarea == null) {
			throw new NoExisteDato("No existe area: " + pArea.getId());
		}
		jarea.fromModel(pArea);
		entityManager.merge(jarea);
	}

	private List<Area> listarAreas(final String pFiltro) {
		final List<Area> resultado = new ArrayList<>();

		String sql = "Select t From JArea t";
		if (StringUtils.isNotBlank(pFiltro)) {
			sql += "Where upper(t.descripcion) like :filtro";
		}
		sql += " ORDER BY t.codigo";

		final Query query = entityManager.createQuery(sql);

		if (StringUtils.isNotBlank(pFiltro)) {
			query.setParameter("filtro", "%" + pFiltro.toUpperCase() + "%");
		}

		final List<JArea> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<JArea> iterator = results.iterator(); iterator.hasNext();) {
				final JArea jarea = iterator.next();

				resultado.add(jarea.toModel());
			}
		}

		return resultado;
	}
}
