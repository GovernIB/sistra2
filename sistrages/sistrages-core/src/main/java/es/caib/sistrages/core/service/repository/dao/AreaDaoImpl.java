package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JEntidad;

/**
 * La clase AreaDaoImpl.
 */
@Repository("areaDao")
public class AreaDaoImpl implements AreaDao {

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de AreaDaoImpl.
	 */
	public AreaDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.AreaDao#getById(java.lang.Long)
	 */
	@Override
	public Area getById(final Long pCodigo) {
		Area area = null;

		if (pCodigo == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JArea jarea = entityManager.find(JArea.class, pCodigo);

		if (jarea == null) {
			throw new NoExisteDato("No existe el area: " + pCodigo);
		} else {
			area = jarea.toModel();
		}

		return area;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.AreaDao#getAllByFiltro(java.
	 * lang.Long, java.lang.String)
	 */
	@Override
	public List<Area> getAllByFiltro(final Long idEntidad, final String pFiltro) {
		return listarAreas(idEntidad, pFiltro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.AreaDao#getAll(java.lang.Long)
	 */
	@Override
	public List<Area> getAll(final Long idEntidad) {
		return listarAreas(idEntidad, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.AreaDao#add(java.lang.Long,
	 * es.caib.sistrages.core.api.model.Area)
	 */
	@Override
	public void add(final Long idEntidad, final Area pArea) {
		if (pArea == null) {
			throw new FaltanDatosException("Falta el area");
		}

		if (idEntidad == null) {
			throw new FaltanDatosException("Falta la entidad");
		}

		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		final JArea jArea = JArea.fromModel(pArea);
		jArea.setEntidad(jEntidad);
		entityManager.persist(jArea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.AreaDao#remove(java.lang.Long)
	 */
	@Override
	public void remove(final Long pCodigo) {
		if (pCodigo == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JArea jArea = entityManager.find(JArea.class, pCodigo);
		if (jArea == null) {
			throw new NoExisteDato("No existe area: " + pCodigo);
		}
		entityManager.remove(jArea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.AreaDao#update(es.caib.
	 * sistrages.core.api.model.Area)
	 */
	@Override
	public void update(final Area pArea) {
		if (pArea == null) {
			throw new FaltanDatosException("Falta el area");
		}
		final JArea jArea = entityManager.find(JArea.class, pArea.getId());
		if (jArea == null) {
			throw new NoExisteDato("No existe area: " + pArea.getId());
		}

		// Mergeamos datos
		final JArea jAreaNew = JArea.fromModel(pArea);
		jAreaNew.setCodigo(jArea.getCodigo());
		jAreaNew.setEntidad(jArea.getEntidad());

		entityManager.merge(jAreaNew);
	}

	/**
	 * Listar areas.
	 *
	 * @param idEntidad
	 *            Id entidad
	 * @param pFiltro
	 *            the filtro
	 * @return lista de areas
	 */
	@SuppressWarnings("unchecked")
	private List<Area> listarAreas(final Long idEntidad, final String pFiltro) {
		final List<Area> resultado = new ArrayList<>();

		String sql = "Select t From JArea t where t.entidad.codigo = :idEntidad";

		if (StringUtils.isNotBlank(pFiltro)) {
			sql += " AND upper(t.descripcion) like :filtro";
		}
		sql += " ORDER BY t.codigo";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idEntidad", idEntidad);
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
