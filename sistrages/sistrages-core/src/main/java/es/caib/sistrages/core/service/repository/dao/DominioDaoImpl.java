package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JDominio;
import es.caib.sistrages.core.service.repository.model.JEntidad;

/**
 * La clase DominioDaoImpl.
 */
@Repository("dominioDao")
public class DominioDaoImpl implements DominioDao {

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#getById(java.lang.
	 * Long)
	 */
	@Override
	public Dominio getById(final Long idDominio) {
		Dominio dominio = null;
		final JDominio hdominio = entityManager.find(JDominio.class, idDominio);
		if (hdominio != null) {
			// Establecemos datos
			dominio = hdominio.toModel();
		}
		return dominio;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.DominioDao#add(es.caib.
	 * sistrages.core.api.model.Dominio, java.lang.Long, java.lang.Long)
	 */
	@Override
	public void add(final Dominio dominio, final Long idEntidad, final Long idArea) {
		// Añade dominio por superadministrador estableciendo datos minimos
		final JDominio jDominio = new JDominio();
		jDominio.fromModel(dominio);
		if (idEntidad != null) {
			final JEntidad jentidad = entityManager.find(JEntidad.class, idEntidad);
			if (jDominio.getEntidades() == null) {
				jDominio.setEntidades(new HashSet<>());
			}
			jDominio.getEntidades().add(jentidad);
		}
		if (idArea != null) {
			final JArea jarea = entityManager.find(JArea.class, idArea);
			if (jDominio.getAreas() == null) {
				jDominio.setAreas(new HashSet<>());
			}
			jDominio.getAreas().add(jarea);
		}
		entityManager.persist(jDominio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#remove(java.lang.
	 * Long)
	 */
	@Override
	public void remove(final Long idDominio) {
		final JDominio hdominio = entityManager.find(JDominio.class, idDominio);
		if (hdominio != null) {
			entityManager.remove(hdominio);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#getAllByFiltro(es.
	 * caib.sistrages.core.api.model.types.TypeAmbito, java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public List<Dominio> getAllByFiltro(final TypeAmbito ambito, final Long id, final String filtro) {
		return listarDominios(ambito, id, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.DominioDao#getAll(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long)
	 */
	@Override
	public List<Dominio> getAll(final TypeAmbito ambito, final Long id) {
		return listarDominios(ambito, id, null);
	}

	/**
	 * Listar dominios.
	 *
	 * @param ambito
	 *            ambito
	 * @param id
	 *            id
	 * @param filtro
	 *            filtro
	 * @return lista de dominios
	 */
	private List<Dominio> listarDominios(final TypeAmbito ambito, final Long id, final String filtro) {
		final List<Dominio> dominioes = new ArrayList<>();

		String sql = "SELECT DISTINCT d FROM JDominio d ";

		if (ambito == TypeAmbito.AREA) {
			sql += " JOIN d.areas area WHERE area.id = :id AND d.ambito = '" + TypeAmbito.AREA + "'";
		} else if (ambito == TypeAmbito.ENTIDAD) {
			sql += " JOIN d.entidades ent WHERE ent.id = :id AND d.ambito = '" + TypeAmbito.ENTIDAD + "'";
		} else if (ambito == TypeAmbito.GLOBAL) {
			sql += " WHERE d.ambito = '" + TypeAmbito.GLOBAL + "'";
		}

		if (StringUtils.isNotBlank(filtro)) {
			sql += " AND (LOWER(d.descripcion) LIKE :filtro OR LOWER(d.identificador) LIKE :filtro)";
		}

		sql += " ORDER BY d.identificador";

		final Query query = entityManager.createQuery(sql);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		if (id != null) {
			query.setParameter("id", id);
		}

		final List<JDominio> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final JDominio jdominio : results) {
				final Dominio dominio = jdominio.toModel();
				dominioes.add(dominio);
			}
		}

		return dominioes;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#updateDominio(es.
	 * caib.sistrages.core.api.model.Dominio)
	 */
	@Override
	public void updateDominio(final Dominio dominio) {
		final JDominio jdominio = entityManager.find(JDominio.class, dominio.getId());
		jdominio.fromModel(dominio);
		entityManager.merge(jdominio);
	}
}
