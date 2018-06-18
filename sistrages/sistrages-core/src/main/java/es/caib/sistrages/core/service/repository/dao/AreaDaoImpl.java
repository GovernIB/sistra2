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

	private static final String FALTA_ENTIDAD = "Falta la entidad";
	private static final String FALTA_AREA = "Falta el area";
	private static final String NO_EXISTE_EL_AREA = "No existe el area: ";
	private static final String FALTA_IDENTIFICADOR = "Falta el identificador";
	private static final String NO_EXISTE_LA_ENTIDAD = "No existe la entidad: ";
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
	public Area getById(final Long pId) {
		Area area = null;

		if (pId == null) {
			throw new FaltanDatosException(FALTA_IDENTIFICADOR);
		}

		final JArea jarea = entityManager.find(JArea.class, pId);

		if (jarea == null) {
			throw new NoExisteDato(NO_EXISTE_EL_AREA + pId);
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
	public List<Area> getAllByFiltro(final Long pIdEntidad, final String pFiltro) {
		if (pIdEntidad == null) {
			throw new FaltanDatosException(FALTA_ENTIDAD);
		}

		return listarAreas(pIdEntidad, pFiltro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.AreaDao#getAll(java.lang.Long)
	 */
	@Override
	public List<Area> getAll(final Long pIdEntidad) {
		if (pIdEntidad == null) {
			throw new FaltanDatosException(FALTA_ENTIDAD);
		}

		return listarAreas(pIdEntidad, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.AreaDao#add(java.lang.Long,
	 * es.caib.sistrages.core.api.model.Area)
	 */
	@Override
	public void add(final Long pIdEntidad, final Area pArea) {
		if (pArea == null) {
			throw new FaltanDatosException(FALTA_AREA);
		}

		if (pIdEntidad == null) {
			throw new FaltanDatosException(FALTA_ENTIDAD);
		}

		final JEntidad jEntidad = entityManager.find(JEntidad.class, pIdEntidad);
		if (jEntidad == null) {
			throw new FaltanDatosException(NO_EXISTE_LA_ENTIDAD + pIdEntidad);
		}

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
	public void remove(final Long pId) {
		if (pId == null) {
			throw new FaltanDatosException(FALTA_IDENTIFICADOR);
		}

		final JArea jArea = entityManager.find(JArea.class, pId);
		if (jArea == null) {
			throw new NoExisteDato(NO_EXISTE_EL_AREA + pId);
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
			throw new FaltanDatosException(FALTA_AREA);
		}
		final JArea jArea = entityManager.find(JArea.class, pArea.getCodigo());
		if (jArea == null) {
			throw new NoExisteDato(NO_EXISTE_EL_AREA + pArea.getCodigo());
		}

		// Mergeamos datos
		final JArea jAreaNew = JArea.fromModel(pArea);
		jAreaNew.setCodigo(jArea.getCodigo());
		jAreaNew.setEntidad(jArea.getEntidad());
		jAreaNew.setDominios(jArea.getDominios());
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

		String sql = "Select a From JArea a where a.entidad.codigo = :idEntidad";

		if (StringUtils.isNotBlank(pFiltro)) {
			sql += " AND a in (select t.area from JTramite t  where upper(t.identificador) like :filtro or upper(t.descripcion) like :filtro  )";
		}
		sql += " ORDER BY a.codigo";

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

	@Override
	public Area getAreaByIdentificador(final String identificador, final Long idEntidad) {

		final String sql = "Select t From JArea t where t.entidad.codigo = :idEntidad and t.identificador = :identificador";
		final Query query = entityManager.createQuery(sql);

		query.setParameter("idEntidad", idEntidad);
		query.setParameter("identificador", identificador);

		final JArea jarea = (JArea) query.getSingleResult();
		if (jarea == null) {
			return null;
		} else {
			return jarea.toModel();
		}

	}
}
