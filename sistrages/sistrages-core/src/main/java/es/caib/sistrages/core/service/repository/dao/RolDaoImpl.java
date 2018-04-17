package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JRolArea;

/**
 * La clase RolDaoImpl.
 */
@Repository("rolDao")
public class RolDaoImpl implements RolDao {

	private static final String NO_EXISTE_ROL = "No existe rol";
	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de RolDaoImpl.
	 */
	public RolDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.RolDao#getById(java.lang.Long)
	 */
	@Override
	public Rol getById(final Long id) {
		Rol rol = null;

		if (id == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JRolArea jRol = entityManager.find(JRolArea.class, id);

		if (jRol == null) {
			throw new NoExisteDato(NO_EXISTE_ROL + ": " + id);
		} else {
			rol = jRol.toModel();
			if (jRol.getArea() != null) {
				rol.setArea(jRol.getArea().toModel());
			}
		}

		return rol;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.RolDao#add(es.caib.sistrages.
	 * core.api.model.Rol)
	 */
	@Override
	public void add(final Rol rol) {
		if (rol == null) {
			throw new FaltanDatosException("Falta el rol");
		}

		final JRolArea jRol = JRolArea.fromModel(rol);

		if (rol.getArea() != null) {
			final JArea jArea = entityManager.find(JArea.class, rol.getArea().getId());
			jRol.setArea(jArea);
		}

		entityManager.persist(jRol);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.RolDao#remove(java.lang.Long)
	 */
	@Override
	public void remove(final Long id) {

		if (id == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JRolArea jRol = entityManager.find(JRolArea.class, id);
		if (jRol == null) {
			throw new NoExisteDato("No existe rol: " + id);
		}
		entityManager.remove(jRol);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.RolDao#update(es.caib.sistrages
	 * .core.api.model.Rol)
	 */
	@Override
	public void update(final Rol rol) {
		if (rol == null) {
			throw new FaltanDatosException("Falta el rol");
		}

		final JRolArea jRol = entityManager.find(JRolArea.class, rol.getId());

		if (jRol == null) {
			throw new NoExisteDato("No existe rol: " + rol.getId());
		}

		// Mergeamos datos
		final JRolArea jRolNew = JRolArea.fromModel(rol);
		jRolNew.setCodigo(jRol.getCodigo());

		if (rol.getArea() != null) {
			final JArea jArea = entityManager.find(JArea.class, rol.getArea().getId());
			jRolNew.setArea(jArea);
		}

		entityManager.merge(jRolNew);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.RolDao#getAllByFiltro(java.lang
	 * .Long, java.lang.String)
	 */
	@Override
	public List<Rol> getAllByFiltro(final Long idEntidad, final String filtro) {
		return listarRoles(idEntidad, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.RolDao#getAll(java.lang.Long)
	 */
	@Override
	public List<Rol> getAll(final Long idEntidad) {
		return listarRoles(idEntidad, null);
	}

	@Override
	public List<Rol> getAllByArea(final Long idArea) {
		final List<Rol> listaRoles = new ArrayList<>();
		final String sql = "select r from JRolArea as r where r.area.codigo = :idArea";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idArea", idArea);
		final List<JRolArea> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JRolArea jRol : results) {
				final Rol rol = jRol.toModel();
				if (jRol.getArea() != null) {
					rol.setArea(jRol.getArea().toModel());
				}
				listaRoles.add(rol);
			}
		}
		return listaRoles;
	}

	/**
	 * Listar roles.
	 *
	 * @param idEntidad
	 *            Id entidad
	 * @param filtro
	 *            filtro
	 * @return Listado de roles
	 */
	@SuppressWarnings("unchecked")
	private List<Rol> listarRoles(final Long idEntidad, final String filtro) {
		final List<Rol> listaRoles = new ArrayList<>();
		String sql = "select r from JRolArea as r JOIN r.area a where a.entidad.codigo = :idEntidad";

		if (StringUtils.isNotBlank(filtro)) {
			sql += " AND (LOWER(r.descripcion) LIKE :filtro OR LOWER(r.valor) LIKE :filtro OR LOWER(a.identificador) LIKE :filtro)";
		}
		sql += " order by r.valor";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idEntidad", idEntidad);
		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		final List<JRolArea> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JRolArea jRol : results) {
				final Rol rol = jRol.toModel();
				if (jRol.getArea() != null) {
					rol.setArea(jRol.getArea().toModel());
				}
				listaRoles.add(rol);
			}
		}
		return listaRoles;
	}

	@Override
	public void removeByArea(final Long idArea) {
		final String sql = "delete from JRolArea as a where a.area.codigo = :idArea";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idArea", idArea);
		query.executeUpdate();
	}

}
