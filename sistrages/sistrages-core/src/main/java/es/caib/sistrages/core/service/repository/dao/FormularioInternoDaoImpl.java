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
import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.service.repository.model.JElementoFormulario;
import es.caib.sistrages.core.service.repository.model.JFormulario;
import es.caib.sistrages.core.service.repository.model.JLineaFormulario;
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
	private static final String FALTA_COMPONENTE = "Falta el componente";
	private static final String NO_EXISTE_FORMINT = "No existe el formulario: ";
	private static final String NO_EXISTE_PAGINA = "No existe la pagina: ";
	private static final String NO_EXISTE_COMPONENTE = "No existe el componente: ";

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

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getFormPagById(java.lang.Long)
	 */
	@Override
	public FormularioInterno getFormularioPaginasById(final Long pId) {
		FormularioInterno formInt = null;

		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JFormulario jForm = entityManager.find(JFormulario.class, pId);

		if (jForm == null) {
			throw new NoExisteDato(NO_EXISTE_FORMINT + pId);
		} else {
			formInt = jForm.toModel();

			for (final JPaginaFormulario jPagina : jForm.getPaginas()) {
				formInt.getPaginas().add(getContenidoPaginaById(jPagina.getCodigo()));
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

		jForm.setMostrarCabecera(pFormInt.isMostrarCabecera());
		if (pFormInt.isMostrarCabecera()) {
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

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getPaginaById(java.lang.Long)
	 */
	@Override
	public PaginaFormulario getPaginaById(final Long pId) {

		PaginaFormulario pagina = null;

		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JPaginaFormulario jPagina = entityManager.find(JPaginaFormulario.class, pId);

		if (jPagina == null) {
			throw new NoExisteDato(NO_EXISTE_PAGINA + pId);
		} else {
			pagina = jPagina.toModel();
		}

		return pagina;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getContenidoPaginaById(java.lang.Long)
	 */
	@Override
	public PaginaFormulario getContenidoPaginaById(final Long pId) {
		PaginaFormulario pagina = null;

		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JPaginaFormulario jPagina = entityManager.find(JPaginaFormulario.class, pId);

		if (jPagina == null) {
			throw new NoExisteDato(NO_EXISTE_PAGINA + pId);
		} else {
			pagina = jPagina.toModel();

			for (final JLineaFormulario jLinea : jPagina.getLineasFormulario()) {
				final LineaComponentesFormulario linea = jLinea.toModel();

				for (final JElementoFormulario jComponente : jLinea.getElementoFormulario()) {
					final ComponenteFormulario componente = componenteFormularioToModel(jComponente);
					if (componente != null) {
						linea.getComponentes().add(componente);
					}
				}
				// ordenamos lista de componentes por campo orden
				if (linea != null && !linea.getComponentes().isEmpty()) {
					Collections.sort(linea.getComponentes(),
							(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
				}
				pagina.getLineas().add(linea);
			}

			// ordenamos lista de lineas por campo orden
			if (!pagina.getLineas().isEmpty()) {
				Collections.sort(pagina.getLineas(),
						(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
			}
		}

		return pagina;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getComponenteById(java.lang.Long)
	 */
	@Override
	public ComponenteFormulario getComponenteById(final Long pId) {
		ComponenteFormulario componente = null;
		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JElementoFormulario jElemento = entityManager.find(JElementoFormulario.class, pId);

		if (jElemento == null) {
			throw new NoExisteDato(NO_EXISTE_COMPONENTE + ": " + pId);
		}

		componente = componenteFormularioToModel(jElemento);

		return componente;
	}

	@Override
	public void addComponente(final FormularioInterno pFormInt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateComponente(final ComponenteFormulario pComponente) {
		if (pComponente == null) {
			throw new FaltanDatosException(FALTA_COMPONENTE);
		}

		final JElementoFormulario jElemento = entityManager.find(JElementoFormulario.class, pComponente.getId());

		if (jElemento == null) {
			throw new NoExisteDato(NO_EXISTE_COMPONENTE + ": " + pComponente.getId());
		}

	}

	/**
	 * pasa un componente de BD a modelo.
	 *
	 * @param jComponente
	 *            componente de bd
	 * @return componente de modelo
	 */
	private ComponenteFormulario componenteFormularioToModel(final JElementoFormulario jComponente) {
		ComponenteFormulario componente = null;
		// miramos si es seccion
		if (jComponente.getSeccionFormulario() != null) {
			componente = jComponente.getSeccionFormulario().toModel();
		}

		// miramos si es imagen
		if (jComponente.getImagenFormulario() != null) {
			componente = jComponente.getImagenFormulario().toModel();
		}

		// miramos si es etiqueta
		if (jComponente.getEtiquetaFormulario() != null) {
			componente = jComponente.getEtiquetaFormulario().toModel();
		}

		// miramos si es campo de texto
		if (jComponente.getCampoFormulario() != null
				&& jComponente.getCampoFormulario().getCampoFormularioTexto() != null) {
			componente = jComponente.getCampoFormulario().getCampoFormularioTexto().toModel();
		}

		// miramos si es campo de checkbox
		if (jComponente.getCampoFormulario() != null
				&& jComponente.getCampoFormulario().getCampoFormularioCasillaVerificacion() != null) {
			componente = jComponente.getCampoFormulario().getCampoFormularioCasillaVerificacion().toModel();
		}

		// miramos si es campo selector
		if (jComponente.getCampoFormulario() != null
				&& jComponente.getCampoFormulario().getCampoFormularioIndexado() != null) {
			componente = jComponente.getCampoFormulario().getCampoFormularioIndexado().toModel();
		}

		return componente;
	}

}
