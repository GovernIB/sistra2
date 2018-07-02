package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JFormularioSoporte;
import es.caib.sistrages.core.service.repository.model.JLiteral;

/**
 * La clase FormularioSoporteDaoImpl.
 */
@Repository("formularioSoporteDao")
public class FormularioSoporteDaoImpl implements FormularioSoporteDao {

	private static final String FALTA_ENTIDAD = "Falta la entidad";
	private static final String FALTA_ID = "Falta el identificador";
	private static final String FALTA_FST = "Falta el formulario de soporte";
	private static final String NO_EXISTE_FST = "No existe el formulario de soporte";
	private static final String NO_EXISTE_ENTIDAD = "No existe la entidad";
	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de FormularioSoporteDaoImpl.
	 */
	public FormularioSoporteDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioSoporteDao#getById(
	 * java.lang.Long)
	 */
	@Override
	public FormularioSoporte getById(final Long id) {
		FormularioSoporte fst = null;

		if (id == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JFormularioSoporte jFst = entityManager.find(JFormularioSoporte.class, id);

		if (jFst == null) {
			throw new NoExisteDato(NO_EXISTE_FST + ": " + id);
		} else {
			fst = jFst.toModel();
		}

		return fst;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioSoporteDao#add(java.
	 * lang.Long, es.caib.sistrages.core.api.model.FormularioSoporte)
	 */
	@Override
	public void add(final Long idEntidad, final FormularioSoporte fst) {
		if (idEntidad == null) {
			throw new FaltanDatosException(FALTA_ENTIDAD);
		}
		if (fst == null) {
			throw new FaltanDatosException(FALTA_FST);
		}

		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato(NO_EXISTE_ENTIDAD + ": " + idEntidad);
		}

		final JFormularioSoporte jFmt = JFormularioSoporte.fromModel(fst);
		jFmt.setEntidad(jEntidad);
		entityManager.persist(jFmt);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioSoporteDao#remove(
	 * java.lang.Long)
	 */
	@Override
	public void remove(final Long id) {

		if (id == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JFormularioSoporte jFmt = entityManager.find(JFormularioSoporte.class, id);
		if (jFmt == null) {
			throw new NoExisteDato(NO_EXISTE_FST + ": " + id);
		}
		entityManager.remove(jFmt);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioSoporteDao#update(es.
	 * caib.sistrages.core.api.model.FormularioSoporte)
	 */
	@Override
	public void update(final FormularioSoporte fmt) {
		if (fmt == null) {
			throw new FaltanDatosException(FALTA_FST);
		}

		final JFormularioSoporte jFst = entityManager.find(JFormularioSoporte.class, fmt.getCodigo());

		if (jFst == null) {
			throw new NoExisteDato(NO_EXISTE_FST + ": " + fmt.getCodigo());
		}

		// Mergeamos datos
		final JFormularioSoporte jFstNew = JFormularioSoporte.fromModel(fmt);
		jFstNew.setCodigo(jFst.getCodigo());
		jFstNew.setEntidad(jFst.getEntidad());
		jFstNew.setTipoIncidencia(JLiteral.mergeModel(jFst.getTipoIncidencia(), fmt.getTipoIncidencia()));
		jFstNew.setDescripcion(JLiteral.mergeModel(jFst.getDescripcion(), fmt.getDescripcion()));
		entityManager.merge(jFstNew);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormularioSoporte> getAll(final Long idEntidad) {
		final List<FormularioSoporte> listaFst = new ArrayList<>();
		final String sql = "select fst from JFormularioSoporte fst where fst.entidad.codigo = :idEntidad";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idEntidad", idEntidad);

		final List<JFormularioSoporte> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JFormularioSoporte jFst : results) {
				listaFst.add(jFst.toModel());
			}
		}
		return listaFst;
	}

}
