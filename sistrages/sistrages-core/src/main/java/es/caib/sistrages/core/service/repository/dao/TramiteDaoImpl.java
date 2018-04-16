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
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JTramite;

/**
 * La clase TramiteDaoImpl.
 */
@Repository("tramiteDao")
public class TramiteDaoImpl implements TramiteDao {

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de TramiteDaoImpl.
	 */
	public TramiteDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#getById(java.lang.
	 * Long)
	 */
	@Override
	public Tramite getById(final Long id) {
		Tramite tramite = null;

		if (id == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JTramite jTramite = entityManager.find(JTramite.class, id);

		if (jTramite == null) {
			throw new NoExisteDato("No existe el tramite: " + id);
		} else {
			tramite = jTramite.toModel();
		}

		return tramite;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#getAllByFiltro(java.
	 * lang.Long, java.lang.String)
	 */
	@Override
	public List<Tramite> getAllByFiltro(final Long idArea, final String pFiltro) {
		return listarTramites(idArea, pFiltro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#getAll(java.lang.
	 * Long)
	 */
	@Override
	public List<Tramite> getAll(final Long idArea) {
		return listarTramites(idArea, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#add(java.lang.Long,
	 * es.caib.sistrages.core.api.model.Tramite)
	 */
	@Override
	public void add(final Long idArea, final Tramite pTramite) {
		if (idArea == null) {
			throw new FaltanDatosException("Falta el area");
		}

		if (pTramite == null) {
			throw new FaltanDatosException("Falta el tramite");
		}

		final JArea jArea = entityManager.find(JArea.class, idArea);
		final JTramite jTramite = JTramite.fromModel(pTramite);
		jTramite.setArea(jArea);
		entityManager.persist(jTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.TramiteDao#remove(java.lang.
	 * Long)
	 */
	@Override
	public void remove(final Long id) {
		if (id == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JTramite jTramite = entityManager.find(JTramite.class, id);
		if (jTramite == null) {
			throw new NoExisteDato("No existe el tramite: " + id);
		}
		entityManager.remove(jTramite);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.TramiteDao#update(es.caib.
	 * sistrages.core.api.model.Tramite)
	 */
	@Override
	public void update(final Tramite pTramite) {
		if (pTramite == null) {
			throw new FaltanDatosException("Falta el tramite");
		}
		final JTramite jTramite = entityManager.find(JTramite.class, pTramite.getId());
		if (jTramite == null) {
			throw new NoExisteDato("No existe el tramite: " + pTramite.getId());
		}

		// Mergeamos datos
		final JTramite jTramiteNew = JTramite.fromModel(pTramite);
		jTramiteNew.setCodigo(jTramite.getCodigo());
		jTramiteNew.setArea(jTramite.getArea());
		jTramiteNew.setVersionTramite(jTramite.getVersionTramite());
		entityManager.merge(jTramiteNew);
	}

	/**
	 * Listar tramite.
	 *
	 * @param idArea
	 *            Id area
	 * @param pFiltro
	 *            the filtro
	 * @return lista de tramites
	 */
	@SuppressWarnings("unchecked")
	private List<Tramite> listarTramites(final Long idArea, final String pFiltro) {
		final List<Tramite> resultado = new ArrayList<>();

		String sql = "Select t From JTramite t where t.area.codigo = :idArea";

		if (StringUtils.isNotBlank(pFiltro)) {
			sql += " AND (upper(t.descripcion) like :filtro OR upper(t.identificador) like :filtro)";
		}
		sql += " ORDER BY t.codigo";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idArea", idArea);
		if (StringUtils.isNotBlank(pFiltro)) {
			query.setParameter("filtro", "%" + pFiltro.toUpperCase() + "%");
		}

		final List<JTramite> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<JTramite> iterator = results.iterator(); iterator.hasNext();) {
				final JTramite jTramite = iterator.next();
				resultado.add(jTramite.toModel());
			}
		}

		return resultado;
	}
}
