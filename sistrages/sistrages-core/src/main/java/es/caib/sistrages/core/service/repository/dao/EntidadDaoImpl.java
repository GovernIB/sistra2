package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JFichero;
import es.caib.sistrages.core.service.repository.model.JLiteral;

@Repository("entidadDao")
public class EntidadDaoImpl implements EntidadDao {

	/** EntityManager. */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de EntidadDaoImpl.
	 */
	public EntidadDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#getById(java.lang.
	 * Long)
	 */
	@Override
	public Entidad getById(final Long idEntidad) {
		Entidad entidad = null;
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad != null) {
			// Establecemos datos
			entidad = jEntidad.toModel();
		}
		return entidad;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.EntidadDao#add(es.caib.
	 * sistrages.core.api.model.Entidad)
	 */
	@Override
	public void add(final Entidad entidad) {
		// Añade entidad por superadministrador estableciendo datos minimos
		final JEntidad jEntidad = new JEntidad();
		jEntidad.setCodigoDir3(entidad.getCodigoDIR3());
		jEntidad.setNombre(JLiteral.fromModel(entidad.getNombre()));
		jEntidad.setActiva(entidad.isActivo());
		jEntidad.setRoleAdministrador(entidad.getRol());
		entityManager.persist(jEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#remove(java.lang.
	 * Long)
	 */
	@Override
	public void remove(final Long idEntidad) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}
		entityManager.remove(jEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.EntidadDao#
	 * updateSuperAdministrador(es.caib.sistrages.core.api.model.Entidad)
	 */
	@Override
	public void updateSuperAdministrador(final Entidad entidad) {
		// Update entidad por superadministrador estableciendo datos minimos
		final JEntidad jEntidad = entityManager.find(JEntidad.class, entidad.getId());
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + entidad.getId());
		}
		jEntidad.setCodigoDir3(entidad.getCodigoDIR3());
		jEntidad.setNombre(JLiteral.mergeModel(jEntidad.getNombre(), entidad.getNombre()));
		jEntidad.setActiva(entidad.isActivo());
		jEntidad.setRoleAdministrador(entidad.getRol());
		entityManager.merge(jEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.EntidadDao#
	 * updateAdministradorEntidad(es.caib.sistrages.core.api.model.Entidad)
	 */
	@Override
	public void updateAdministradorEntidad(final Entidad entidad) {

		final JEntidad jEntidad = entityManager.find(JEntidad.class, entidad.getId());
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + entidad.getId());
		}

		jEntidad.setPiePaginaAsistenteTramitacion(
				JLiteral.mergeModel(jEntidad.getPiePaginaAsistenteTramitacion(), entidad.getPie()));

		jEntidad.setEmail(entidad.getEmail());
		jEntidad.setContactoEmail(entidad.isEmailHabilitado());
		jEntidad.setContactoTelefono(entidad.isTelefonoHabilitado());
		jEntidad.setTelefono(entidad.getTelefono());
		jEntidad.setContactoUrl(entidad.isUrlSoporteHabilitado());
		jEntidad.setUrlSoporte(entidad.getUrlSoporte());
		jEntidad.setContactoFormularioIncidencias(entidad.isFormularioIncidenciasHabilitado());
		entityManager.merge(jEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#getAllByFiltro(es.
	 * caib.sistrages.core.api.model.types.TypeIdioma, java.lang.String)
	 */
	@Override
	public List<Entidad> getAllByFiltro(final TypeIdioma idioma, final String filtro) {
		return listarEntidades(idioma, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.EntidadDao#getAll()
	 */
	@Override
	public List<Entidad> getAll() {
		return listarEntidades(null, null);
	}

	/**
	 * Listar entidades.
	 *
	 * @param idioma
	 *            idioma
	 * @param filtro
	 *            filtro
	 * @return lista de entidades
	 */
	@SuppressWarnings("unchecked")
	private List<Entidad> listarEntidades(final TypeIdioma idioma, final String filtro) {
		final List<Entidad> entidades = new ArrayList<>();

		String sql = "SELECT DISTINCT e FROM JEntidad e ";
		if (StringUtils.isNotBlank(filtro)) {
			sql += " LEFT JOIN e.nombre.traduccionLiterales t WHERE LOWER(e.codigoDir3) LIKE :filtro OR (t.idioma.identificador = :idioma AND LOWER(t.literal) LIKE :filtro) OR LOWER(e.roleAdministrador) LIKE :filtro";
		}
		sql += " ORDER BY e.codigoDir3";

		final Query query = entityManager.createQuery(sql);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("idioma", idioma.toString());
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		final List<JEntidad> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final JEntidad jentidad : results) {
				final Entidad entidad = jentidad.toModel();
				entidades.add(entidad);
			}
		}

		return entidades;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#removeLogoGestor(
	 * java.lang.Long)
	 */
	@Override
	public void removeLogoGestor(final Long idEntidad) {

		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}

		final JFichero jFichero = jEntidad.getLogoGestorTramites();
		if (jFichero != null) {
			jEntidad.setLogoGestorTramites(null);
			entityManager.merge(jEntidad);
			entityManager.remove(jFichero);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#removeLogoAsistente(
	 * java.lang.Long)
	 */
	@Override
	public void removeLogoAsistente(final Long idEntidad) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}

		final JFichero jFichero = jEntidad.getLogoAsistenteTramitacion();
		if (jFichero != null) {
			jEntidad.setLogoAsistenteTramitacion(null);
			entityManager.merge(jEntidad);
			entityManager.remove(jFichero);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#removeCssAsistente(
	 * java.lang.Long)
	 */
	@Override
	public void removeCssAsistente(final Long idEntidad) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}

		final JFichero jFichero = jEntidad.getCssAsistenteTramitacion();
		if (jFichero != null) {
			jEntidad.setCssAsistenteTramitacion(null);
			entityManager.merge(jEntidad);
			entityManager.remove(jFichero);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#uploadLogoGestor(
	 * java.lang.Long, es.caib.sistrages.core.api.model.Fichero)
	 */
	@Override
	public Fichero uploadLogoGestor(final Long idEntidad, final Fichero fichero) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}

		jEntidad.setLogoGestorTramites(JFichero.fromModel(fichero));
		entityManager.merge(jEntidad);

		return jEntidad.getLogoGestorTramites().toModel();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#uploadLogoAsistente(
	 * java.lang.Long, es.caib.sistrages.core.api.model.Fichero)
	 */
	@Override
	public Fichero uploadLogoAsistente(final Long idEntidad, final Fichero fichero) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}

		jEntidad.setLogoAsistenteTramitacion(JFichero.fromModel(fichero));
		entityManager.merge(jEntidad);

		return jEntidad.getLogoAsistenteTramitacion().toModel();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.EntidadDao#uploadCssAsistente(
	 * java.lang.Long, es.caib.sistrages.core.api.model.Fichero)
	 */
	@Override
	public Fichero uploadCssAsistente(final Long idEntidad, final Fichero fichero) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}

		jEntidad.setCssAsistenteTramitacion(JFichero.fromModel(fichero));
		entityManager.merge(jEntidad);

		return jEntidad.getCssAsistenteTramitacion().toModel();
	}
}
