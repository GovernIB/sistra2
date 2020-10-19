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
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JGestorExternoFormularios;

/**
 * La clase FormularioExternoDaoImpl.
 */
@Repository("FormularioExternoDao")
public class FormularioExternoDaoImpl implements FormularioExternoDao {

	private static final String FALTA_IDENTIFICADOR = "Falta el identificador";
	private static final String NO_EXISTE_EL_AVISO = "No existe el Formulario Externo: ";
	private static final String FALTA_ENTIDAD = "Falta la entidad";
	private static final String FALTA_AVISO = "Falta el formulario Externo";
	private static final String NO_EXISTE_LA_ENTIDAD = "No existe la entidad: ";

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de FormularioExternoDaoImpl.
	 */
	public FormularioExternoDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioExternoDao#getById(
	 * java. lang.Long)
	 */
	@Override
	public GestorExternoFormularios getById(final Long pId) {
		GestorExternoFormularios FormularioExterno = null;

		if (pId == null) {
			throw new FaltanDatosException(FALTA_IDENTIFICADOR);
		}

		final JGestorExternoFormularios jGestorExternoFormularios = entityManager.find(JGestorExternoFormularios.class,
				pId);

		if (jGestorExternoFormularios == null) {
			throw new NoExisteDato(NO_EXISTE_EL_AVISO + pId);
		} else {
			FormularioExterno = jGestorExternoFormularios.toModel();
		}

		return FormularioExterno;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioExternoDao#add(long,
	 * es.caib.sistrages.core.api.model.FormularioExterno)
	 */
	@Override
	public void add(final Long pIdEntidad, final GestorExternoFormularios pFormularioExterno) {
		if (pFormularioExterno == null) {
			throw new FaltanDatosException(FALTA_AVISO);
		}

		if (pIdEntidad == null) {
			throw new FaltanDatosException(FALTA_ENTIDAD);
		}

		final JEntidad jEntidad = entityManager.find(JEntidad.class, pIdEntidad);
		if (jEntidad == null) {
			throw new FaltanDatosException(NO_EXISTE_LA_ENTIDAD + pIdEntidad);
		}

		final JGestorExternoFormularios jGestorExternoFormularios = JGestorExternoFormularios
				.fromModel(pFormularioExterno);
		jGestorExternoFormularios.setEntidad(jEntidad);
		entityManager.persist(jGestorExternoFormularios);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioExternoDao#remove(
	 * java. lang.Long)
	 */
	@Override
	public void remove(final Long pId) {
		final JGestorExternoFormularios jGestorExternoFormularios = entityManager.find(JGestorExternoFormularios.class,
				pId);
		if (jGestorExternoFormularios == null) {
			throw new NoExisteDato(NO_EXISTE_EL_AVISO + pId);
		}
		entityManager.remove(jGestorExternoFormularios);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioExternoDao#update(es.
	 * caib. sistrages.core.api.model.FormularioExterno)
	 */
	@Override
	public void update(final GestorExternoFormularios pFormularioExterno) {
		if (pFormularioExterno == null) {
			throw new FaltanDatosException(FALTA_AVISO);
		}

		final JGestorExternoFormularios jGestorExternoFormularios = entityManager.find(JGestorExternoFormularios.class,
				pFormularioExterno.getCodigo());
		if (jGestorExternoFormularios == null) {
			throw new NoExisteDato(NO_EXISTE_EL_AVISO + pFormularioExterno.getCodigo());
		}
		// Mergeamos datos
		jGestorExternoFormularios.merge(pFormularioExterno);
		entityManager.merge(jGestorExternoFormularios);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioExternoDao#
	 * getAllByFiltro( java.lang.Long,
	 * es.caib.sistrages.core.api.model.types.TypeIdioma, java.lang.String)
	 */
	@Override
	public List<GestorExternoFormularios> getAllByFiltro(final Long pIdEntidad, final TypeIdioma pIdioma,
			final String pFiltro) {
		if (pIdEntidad == null) {
			throw new FaltanDatosException(FALTA_ENTIDAD);
		}
		return listarAvisos(pIdEntidad, pIdioma, pFiltro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioExternoDao#getAll(
	 * java. lang.Long)
	 */
	@Override
	public List<GestorExternoFormularios> getAll(final Long pIdEntidad) {
		if (pIdEntidad == null) {
			throw new FaltanDatosException(FALTA_ENTIDAD);
		}
		return listarAvisos(pIdEntidad, null, null);
	}

	/**
	 * Listar avisos.
	 *
	 * @param pIdEntidad idEntidad
	 * @param pIdioma    idioma
	 * @param pFiltro    filtro
	 * @return Listado de avisos
	 */
	@SuppressWarnings("unchecked")
	private List<GestorExternoFormularios> listarAvisos(final Long pIdEntidad, final TypeIdioma pIdioma,
			final String pFiltro) {
		final List<GestorExternoFormularios> listaFormularioExterno = new ArrayList<>();
		String sql = "select a from JGestorExternoFormularios as a";

		sql += " where a.entidad.codigo = :idEntidad ";
		if (StringUtils.isNotBlank(pFiltro)) {
			sql += "  AND ( a.identificador like :filtro OR a.descripcion like :filtro OR a.url like :filtro) ";
		}
		sql += "  order by a.identificador, a.codigo";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idEntidad", pIdEntidad);

		if (StringUtils.isNotBlank(pFiltro)) {
			query.setParameter("filtro", "%" + pFiltro.toLowerCase() + "%");
		}

		final List<JGestorExternoFormularios> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JGestorExternoFormularios jGestorExternoFormularios : results) {
				listaFormularioExterno.add(jGestorExternoFormularios.toModel());
			}
		}
		return listaFormularioExterno;
	}

	@Override
	public void removeByEntidad(final Long pIdEntidad) {

		if (pIdEntidad == null) {
			throw new FaltanDatosException(FALTA_ENTIDAD);
		}

		final String sql = "delete from JGestorExternoFormularios a where a.entidad.codigo = :idEntidad";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idEntidad", pIdEntidad);
		query.executeUpdate();
	}

}
