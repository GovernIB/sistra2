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
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.service.repository.model.JFormateadorFormulario;

/**
 * La clase FormateadorFormularioDaoImpl.
 */
@Repository("formateadorFormularioDao")
public class FormateadorFormularioDaoImpl implements FormateadorFormularioDao {

	private static final String NO_EXISTE_FMT = "No existe formateador de formularios";
	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de FormateadorFormularioDaoImpl.
	 */
	public FormateadorFormularioDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao#
	 * getById(java.lang.Long)
	 */
	@Override
	public FormateadorFormulario getById(final Long id) {
		FormateadorFormulario fmt = null;

		if (id == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JFormateadorFormulario jFmt = entityManager.find(JFormateadorFormulario.class, id);

		if (jFmt == null) {
			throw new NoExisteDato(NO_EXISTE_FMT + ": " + id);
		} else {
			fmt = jFmt.toModel();
		}

		return fmt;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao#add(es
	 * .caib.sistrages.core.api.model.FormateadorFormulario)
	 */
	@Override
	public void add(final FormateadorFormulario fmt) {
		if (fmt == null) {
			throw new FaltanDatosException("Falta el formateador de formulario");
		}

		final JFormateadorFormulario jFmt = JFormateadorFormulario.fromModel(fmt);

		entityManager.persist(jFmt);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao#remove
	 * (java.lang.Long)
	 */
	@Override
	public void remove(final Long id) {

		if (id == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JFormateadorFormulario jFmt = entityManager.find(JFormateadorFormulario.class, id);
		if (jFmt == null) {
			throw new NoExisteDato(NO_EXISTE_FMT + ": " + id);
		}
		entityManager.remove(jFmt);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao#update
	 * (es.caib.sistrages.core.api.model.FormateadorFormulario)
	 */
	@Override
	public void update(final FormateadorFormulario fmt) {
		if (fmt == null) {
			throw new FaltanDatosException("Falta el formateador de formulario");
		}

		final JFormateadorFormulario jFmt = entityManager.find(JFormateadorFormulario.class, fmt.getId());

		if (jFmt == null) {
			throw new NoExisteDato(NO_EXISTE_FMT + ": " + fmt.getId());
		}

		// Mergeamos datos
		final JFormateadorFormulario jFmtNew = JFormateadorFormulario.fromModel(fmt);
		jFmtNew.setCodigo(jFmt.getCodigo());

		entityManager.merge(jFmtNew);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao#
	 * getAllByFiltro(java.lang.String)
	 */
	@Override
	public List<FormateadorFormulario> getAllByFiltro(final String filtro) {
		return listarFmt(filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao#getAll
	 * ()
	 */
	@Override
	public List<FormateadorFormulario> getAll() {
		return listarFmt(null);
	}

	/**
	 * Listar formateador de Formulario.
	 *
	 * @param filtro
	 *            filtro
	 * @return Listado de formateadores de Formulario
	 */
	@SuppressWarnings("unchecked")
	private List<FormateadorFormulario> listarFmt(final String filtro) {
		final List<FormateadorFormulario> listaFmt = new ArrayList<>();
		String sql = "select f from JFormateadorFormulario f";

		if (StringUtils.isNotBlank(filtro)) {
			sql += " where (LOWER(f.descripcion) LIKE :filtro OR LOWER(f.classname) LIKE :filtro)";
		}
		sql += " order by f.classname";

		final Query query = entityManager.createQuery(sql);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%".concat(filtro.toLowerCase()).concat("%"));
		}
		
		final List<JFormateadorFormulario> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JFormateadorFormulario jRol : results) {
				final FormateadorFormulario fmt = jRol.toModel();

				listaFmt.add(fmt);
			}
		}
		return listaFmt;
	}

}
