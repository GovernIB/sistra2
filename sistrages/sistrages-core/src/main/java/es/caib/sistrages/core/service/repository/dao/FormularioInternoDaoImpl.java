package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.service.repository.model.JFormulario;
import es.caib.sistrages.core.service.repository.model.JLiteral;
import es.caib.sistrages.core.service.repository.model.JPaginaFormulario;
import es.caib.sistrages.core.service.repository.model.JScript;

/**
 * La clase FormularioSoporteDaoImpl.
 */
@Repository("formularioInternoDao")
public class FormularioInternoDaoImpl implements FormularioInternoDao {

	private static final String FALTA_ID = "Falta el identificador";
	private static final String FALTA_FORMDIS = "Falta el formulario";
	private static final String NO_EXISTE_FORMINT = "No existe el formulario: ";

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de FormularioInternoDaoImpl.
	 */
	public FormularioInternoDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#getById(
	 * java.lang .Long)
	 */
	@Override
	public FormularioInterno getById(final Long pId) {
		FormularioInterno formInt = null;

		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JFormulario jForm = entityManager.find(JFormulario.class, pId);

		if (jForm == null) {
			throw new NoExisteDato(NO_EXISTE_FORMINT + pId);
		} else {
			formInt = jForm.toModel();
		}

		return formInt;
	}

	@Override
	@SuppressWarnings("unchecked")
	public FormularioInterno getFormPagById(final Long pId) {
		FormularioInterno formInt = null;

		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final String sql = "select form from JFormulario form where form.id = :id";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("id", pId);

		final List<JFormulario> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			formInt = results.get(0).toModel();

			for (final JPaginaFormulario jPagina : results.get(0).getPaginas()) {
				formInt.getPaginas().add(jPagina.toModel());
			}

			// ordenamos lista de paginas por campo orden
			if (!formInt.getPaginas().isEmpty()) {
				Collections.sort(formInt.getPaginas(),
						(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
			}
		}

		return formInt;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#add(es.
	 * caib. sistrages.core.api.model.FormularioDisenyo)
	 */
	@Override
	public void add(final FormularioInterno pFormInt) {
		// TODO
		if (pFormInt == null) {
			throw new FaltanDatosException(FALTA_FORMDIS);
		}

		final JFormulario jForm = JFormulario.fromModel(pFormInt);

		entityManager.persist(jForm);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#remove(
	 * java.lang. Long)
	 */
	@Override
	public void remove(final Long pId) {
		// TODO
		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JFormulario jFmt = entityManager.find(JFormulario.class, pId);
		if (jFmt == null) {
			throw new NoExisteDato(NO_EXISTE_FORMINT + ": " + pId);
		}
		entityManager.remove(jFmt);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#update(es.
	 * caib. sistrages.core.api.model.FormularioDisenyo)
	 */
	@Override
	public void update(final FormularioInterno pFormInt) {
		if (pFormInt == null) {
			throw new FaltanDatosException(FALTA_FORMDIS);
		}

		JFormulario jForm = entityManager.find(JFormulario.class, pFormInt.getId());

		if (jForm == null) {
			throw new NoExisteDato(NO_EXISTE_FORMINT + ": " + pFormInt.getId());
		}

		// Mergeamos datos
		jForm.setPermitirAccionesPersonalizadas(pFormInt.isPermitirAccionesPersonalizadas());
		if (pFormInt.getScriptPlantilla() == null) {
			jForm.setScriptPlantilla(null);
		} else {
			jForm.setScriptPlantilla(JScript.fromModel(pFormInt.getScriptPlantilla()));
		}

		jForm.setCabeceraFormulario(pFormInt.isCabeceraFormulario());
		if (pFormInt.isCabeceraFormulario()) {
			jForm.setTextoCabecera(JLiteral.mergeModel(jForm.getTextoCabecera(), pFormInt.getTextoCabecera()));
		} else {
			jForm.setTextoCabecera(null);
		}

		jForm = JFormulario.mergePaginasModel(jForm, pFormInt);

		entityManager.merge(jForm);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#getAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FormularioInterno> getAll() {
		// TODO
		final List<FormularioInterno> listaFormDis = new ArrayList<>();
		final String sql = "select form from JFormulario form";

		final Query query = entityManager.createQuery(sql);

		final List<JFormulario> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JFormulario jFst : results) {
				listaFormDis.add(jFst.toModel());
			}
		}
		return listaFormDis;
	}

}
