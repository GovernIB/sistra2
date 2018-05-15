package es.caib.sistrages.core.service.repository.dao;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampo;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoCheckbox;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
import es.caib.sistrages.core.api.model.ComponenteFormularioEtiqueta;
import es.caib.sistrages.core.api.model.ComponenteFormularioImagen;
import es.caib.sistrages.core.api.model.ComponenteFormularioSeccion;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.service.repository.model.IModelApi;
import es.caib.sistrages.core.service.repository.model.JCampoFormulario;
import es.caib.sistrages.core.service.repository.model.JCampoFormularioCasillaVerificacion;
import es.caib.sistrages.core.service.repository.model.JCampoFormularioIndexado;
import es.caib.sistrages.core.service.repository.model.JCampoFormularioTexto;
import es.caib.sistrages.core.service.repository.model.JElementoFormulario;
import es.caib.sistrages.core.service.repository.model.JEtiquetaFormulario;
import es.caib.sistrages.core.service.repository.model.JFormulario;
import es.caib.sistrages.core.service.repository.model.JImagenFormulario;
import es.caib.sistrages.core.service.repository.model.JLineaFormulario;
import es.caib.sistrages.core.service.repository.model.JLiteral;
import es.caib.sistrages.core.service.repository.model.JPaginaFormulario;
import es.caib.sistrages.core.service.repository.model.JScript;
import es.caib.sistrages.core.service.repository.model.JSeccionFormulario;

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
	private static final String NO_EXISTE_LINEA = "No existe la linea: ";
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
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getFormularioById(java.lang.Long)
	 */
	@Override
	public FormularioInterno getFormularioById(final Long pId) {
		return getJFormularioById(pId).toModel();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getFormPagById(java.lang.Long)
	 */
	@Override
	public FormularioInterno getFormularioPaginasById(final Long pId) {
		final JFormulario jForm = getJFormularioById(pId);

		final FormularioInterno formInt = jForm.toModel();

		for (final JPaginaFormulario jPagina : jForm.getPaginas()) {
			formInt.getPaginas().add(getContenidoPaginaById(jPagina.getCodigo()));
		}

		// ordenamos lista de paginas por campo orden
		if (!formInt.getPaginas().isEmpty() && formInt.getPaginas().size() > 1) {
			Collections.sort(formInt.getPaginas(),
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
		}

		return formInt;
	}

	@Override
	public Long addFormulario(final FormularioTramite pFormTra) {
		if (pFormTra == null) {
			throw new FaltanDatosException(FALTA_FORMDIS);
		}

		final JFormulario jForm = JFormulario.createDefault(JLiteral.fromModel(pFormTra.getDescripcion()));

		final JPaginaFormulario jPagina = JPaginaFormulario.createDefault(jForm);

		final Set<JPaginaFormulario> paginas = new HashSet<>();
		paginas.add(jPagina);
		jForm.setPaginas(paginas);

		entityManager.persist(jForm);

		return jForm.getCodigo();
	}

	@Override
	public void updateFormulario(final FormularioInterno pFormInt) {
		// TODO: revisar

		JFormulario jForm = getJFormularioById(pFormInt.getId());

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
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getPaginaById(java.lang.Long)
	 */
	@Override
	public PaginaFormulario getPaginaById(final Long pId) {
		return getJPaginaById(pId).toModel();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getContenidoPaginaById(java.lang.Long)
	 */
	@Override
	public PaginaFormulario getContenidoPaginaById(final Long pId) {
		final JPaginaFormulario jPagina = getJPaginaById(pId);

		final PaginaFormulario pagina = jPagina.toModel();

		for (final JLineaFormulario jLinea : jPagina.getLineasFormulario()) {
			final LineaComponentesFormulario linea = jLinea.toModel();

			for (final JElementoFormulario jComponente : jLinea.getElementoFormulario()) {
				final ComponenteFormulario componente = componenteFormularioToModel(jComponente);
				if (componente != null) {
					linea.getComponentes().add(componente);
				}
			}
			// ordenamos lista de componentes por campo orden
			if (linea != null && !linea.getComponentes().isEmpty() && linea.getComponentes().size() > 1) {
				Collections.sort(linea.getComponentes(),
						(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
			}
			pagina.getLineas().add(linea);
		}

		// ordenamos lista de lineas por campo orden
		if (!pagina.getLineas().isEmpty() && pagina.getLineas().size() > 1) {
			Collections.sort(pagina.getLineas(),
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
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
		return componenteFormularioToModel(getJElementoById(pId));
	}

	@Override
	public Long addComponente(final TypeObjetoFormulario pTipoObjeto, final Long pIdPagina, final Long pIdLinea,
			final Integer pOrden, final String pPosicion) {
		IModelApi jObjetoFormulario = null;
		JLineaFormulario jLineaSeleccionada = null;
		JLineaFormulario jLinea = null;
		Long idComponente = null;

		if (pIdPagina != null) {
			final JPaginaFormulario jPagina = getJPaginaById(pIdPagina);

			if (pIdLinea != null) {
				jLineaSeleccionada = getJLineaById(pIdLinea);
			}

			switch (pTipoObjeto) {
			case LINEA:
				final Integer ordenLinea = ordenInsercionLinea(jPagina, jLineaSeleccionada, pPosicion);
				// si el orden es inferior o igual al tamaño de la lista de lineas hay que
				// hacer hueco
				creaHuecoEnLineas(jPagina, ordenLinea);
				jObjetoFormulario = JLineaFormulario.createDefault(ordenLinea, jPagina);
				break;
			case CAMPO_TEXTO:
				jLinea = lineaComponentes(jPagina, jLineaSeleccionada, pPosicion);
				if (jLinea != null) {
					final Integer ordenComponente = ordenInsercionComponente(jLinea, pOrden, pPosicion);
					jObjetoFormulario = JCampoFormularioTexto.createDefault(ordenComponente, jLinea);
				}
				break;
			case CHECKBOX:
				jLinea = lineaComponentes(jPagina, jLineaSeleccionada, pPosicion);
				if (jLinea != null) {
					final Integer ordenComponente = ordenInsercionComponente(jLinea, pOrden, pPosicion);
					jObjetoFormulario = JCampoFormularioCasillaVerificacion.createDefault(ordenComponente, jLinea);
				}
				break;
			case SELECTOR:
				jLinea = lineaComponentes(jPagina, jLineaSeleccionada, pPosicion);
				if (jLinea != null) {
					final Integer ordenComponente = ordenInsercionComponente(jLinea, pOrden, pPosicion);
					jObjetoFormulario = JCampoFormularioIndexado.createDefault(ordenComponente, jLinea);
				}
				break;
			case ETIQUETA:
				jLinea = lineaComponentes(jPagina, jLineaSeleccionada, pPosicion);
				if (jLinea != null) {
					final Integer ordenComponente = ordenInsercionComponente(jLinea, pOrden, pPosicion);
					jObjetoFormulario = JEtiquetaFormulario.createDefault(ordenComponente, jLinea);
				}
				break;
			case IMAGEN:
				jLinea = lineaComponentes(jPagina, jLineaSeleccionada, pPosicion);
				if (jLinea != null) {
					final Integer ordenComponente = ordenInsercionComponente(jLinea, pOrden, pPosicion);
					jObjetoFormulario = JImagenFormulario.createDefault(ordenComponente, jLinea);
				}
				break;
			case SECCION:
				jLinea = lineaBloque(jPagina, jLineaSeleccionada, pPosicion);
				if (jLinea != null) {
					jObjetoFormulario = JSeccionFormulario.createDefault(1, jLinea);
				}
				break;
			}

			if (jLinea != null && jLinea.getCodigo() == null) {
				entityManager.persist(jLinea);
			}
			if (jObjetoFormulario != null) {
				entityManager.persist(jObjetoFormulario);

				if (jObjetoFormulario instanceof JLineaFormulario) {
					idComponente = ((JLineaFormulario) jObjetoFormulario).getCodigo();
				} else if (jObjetoFormulario instanceof JCampoFormularioTexto) {
					idComponente = ((JCampoFormularioTexto) jObjetoFormulario).getCodigo();
				} else if (jObjetoFormulario instanceof JCampoFormularioCasillaVerificacion) {
					idComponente = ((JCampoFormularioCasillaVerificacion) jObjetoFormulario).getCodigo();
				} else if (jObjetoFormulario instanceof JCampoFormularioIndexado) {
					idComponente = ((JCampoFormularioIndexado) jObjetoFormulario).getCodigo();
				} else if (jObjetoFormulario instanceof JEtiquetaFormulario) {
					idComponente = ((JEtiquetaFormulario) jObjetoFormulario).getCodigo();
				} else if (jObjetoFormulario instanceof JImagenFormulario) {
					idComponente = ((JImagenFormulario) jObjetoFormulario).getCodigo();
				} else if (jObjetoFormulario instanceof JSeccionFormulario) {
					idComponente = ((JSeccionFormulario) jObjetoFormulario).getCodigo();
				}
			}
		}

		return idComponente;
	}

	@Override
	public void removeLineaFormulario(final Long pId) {
		// TODO:revisar si se borran los elemento en cascada o no
		final JLineaFormulario jLinea = getJLineaById(pId);
		if (jLinea.getPaginaFormulario().getLineasFormulario().size() > 1
				&& jLinea.getOrden() < jLinea.getPaginaFormulario().getLineasFormulario().size()) {
			// hay que reordenar lineas
			quitaHuecoEnLineas(jLinea.getPaginaFormulario(), jLinea.getOrden() + 1);
		}
		entityManager.remove(jLinea);
	}

	@Override
	public void removeComponenteFormulario(final Long pId) {
		final JElementoFormulario jElemento = getJElementoById(pId);

		if (jElemento.getLineaFormulario().getElementoFormulario().size() > 1
				&& jElemento.getOrden() < jElemento.getLineaFormulario().getElementoFormulario().size()) {
			// hay que reordenar componentes
			quitaHuecoEnComponentes(jElemento.getLineaFormulario(), jElemento.getOrden() + 1);
		}
		entityManager.remove(jElemento);

	}

	@Override
	public void updateComponente(final ComponenteFormulario pComponente) {
		// TODO
		if (pComponente != null) {
			final JElementoFormulario jElemento = getJElementoById(pComponente.getId());

			jElemento.setAyuda(JLiteral.mergeModel(jElemento.getAyuda(), pComponente.getAyuda()));
			jElemento.setTexto(JLiteral.mergeModel(jElemento.getTexto(), pComponente.getTexto()));
			jElemento.setIdentificador(pComponente.getIdComponente());
			jElemento.setNumeroColumnas(pComponente.getNumColumnas());
			jElemento.setNoMostrarTexto(pComponente.isNoMostrarTexto());
			jElemento.setAlineacionTexto(pComponente.getAlineacionTexto().toString());

			// hacemos la parte de campo
			if (TypeObjetoFormulario.CAMPO_TEXTO.equals(pComponente.getTipo())
					|| TypeObjetoFormulario.CHECKBOX.equals(pComponente.getTipo())
					|| TypeObjetoFormulario.SELECTOR.equals(pComponente.getTipo())) {
				final JCampoFormulario jCampo = jElemento.getCampoFormulario();
				final ComponenteFormularioCampo campo = (ComponenteFormularioCampo) pComponente;

				jCampo.setScriptAutocalculado(JScript.fromModel(campo.getScriptAutorrellenable()));
				jCampo.setScriptSoloLectura(JScript.fromModel(campo.getScriptSoloLectura()));
				jCampo.setScriptValidaciones(JScript.fromModel(campo.getScriptValidacion()));
				jCampo.setObligatorio(campo.isObligatorio());
				jCampo.setSoloLectura(campo.isSoloLectura());
				jCampo.setNoModificable(campo.isNoModificable());
			}

			switch (pComponente.getTipo()) {
			case SECCION:
				final JSeccionFormulario jSeccion = jElemento.getSeccionFormulario();
				final ComponenteFormularioSeccion seccion = (ComponenteFormularioSeccion) pComponente;
				jSeccion.setLetra(seccion.getLetra());
				entityManager.merge(jSeccion);
				break;
			case IMAGEN:
				final JImagenFormulario jImagen = jElemento.getImagenFormulario();
				final ComponenteFormularioImagen imagen = (ComponenteFormularioImagen) pComponente;
				entityManager.merge(jImagen);
				break;
			case ETIQUETA:
				final JEtiquetaFormulario jEtiqueta = jElemento.getEtiquetaFormulario();
				final ComponenteFormularioEtiqueta etiqueta = (ComponenteFormularioEtiqueta) pComponente;
				jEtiqueta.setTipo(etiqueta.getTipoEtiqueta().toString());
				entityManager.merge(jEtiqueta);
				break;
			case CAMPO_TEXTO:
				final JCampoFormularioTexto jCampoTexto = jElemento.getCampoFormulario().getCampoFormularioTexto();
				final ComponenteFormularioCampoTexto campoTexto = (ComponenteFormularioCampoTexto) pComponente;

				jCampoTexto.setOculto(campoTexto.isOculto());
				jCampoTexto.setTipo(campoTexto.getTipoCampoTexto().name());

				switch (campoTexto.getTipoCampoTexto()) {
				case NORMAL:
					jCampoTexto.setNormalTamanyo(campoTexto.getNormalTamanyo());
					jCampoTexto.setNormalMultilinea(campoTexto.isNormalMultilinea());
					jCampoTexto.setNormalNumeroLineas(campoTexto.getNormalNumeroLineas());
					jCampoTexto.setNormalExpresionRegular(campoTexto.getNormalExpresionRegular());
					break;
				case NUMERO:
					jCampoTexto.setNumeroDigitosEnteros(campoTexto.getNumeroDigitosEnteros());
					jCampoTexto.setNumeroDigitosDecimales(campoTexto.getNumeroDigitosDecimales());
					jCampoTexto.setNumeroSeparador(campoTexto.getNumeroSeparador().toString());
					jCampoTexto.setNumeroRangoMinimo(campoTexto.getNumeroRangoMinimo());
					jCampoTexto.setNumeroRangoMaximo(campoTexto.getNumeroRangoMaximo());
					jCampoTexto.setNumeroConSigno(campoTexto.isNumeroConSigno());
					break;
				case ID:
					jCampoTexto.setIdentNif(campoTexto.isIdentNif());
					jCampoTexto.setIdentCif(campoTexto.isIdentCif());
					jCampoTexto.setIdentNie(campoTexto.isIdentNie());
					jCampoTexto.setIdentNss(campoTexto.isIdentNss());
					break;
				case TELEFONO:
					jCampoTexto.setTelefonoFijo(campoTexto.isTelefonoFijo());
					jCampoTexto.setTelefonoMovil(campoTexto.isTelefonoMovil());
				}

				jCampoTexto.setPermiteRango(campoTexto.isPermiteRango());
				entityManager.merge(jCampoTexto);
				break;
			case CHECKBOX:
				final JCampoFormularioCasillaVerificacion jCampoCheckbox = jElemento.getCampoFormulario()
						.getCampoFormularioCasillaVerificacion();
				final ComponenteFormularioCampoCheckbox campoCheckbox = (ComponenteFormularioCampoCheckbox) pComponente;

				jCampoCheckbox.setValorChecked(campoCheckbox.getValorChecked());
				jCampoCheckbox.setValorNoChecked(campoCheckbox.getValorNoChecked());

				entityManager.merge(jCampoCheckbox);
				break;
			case SELECTOR:
				final JCampoFormularioIndexado jCampoIndexado = jElemento.getCampoFormulario()
						.getCampoFormularioIndexado();
				final ComponenteFormularioCampoSelector campoIndexado = (ComponenteFormularioCampoSelector) pComponente;
				break;
			}

		}

	}

	private JFormulario getJFormularioById(final Long pId) {
		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JFormulario jForm = entityManager.find(JFormulario.class, pId);

		if (jForm == null) {
			throw new NoExisteDato(NO_EXISTE_FORMINT + pId);
		}
		return jForm;
	}

	private JPaginaFormulario getJPaginaById(final Long pId) {

		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JPaginaFormulario jPagina = entityManager.find(JPaginaFormulario.class, pId);

		if (jPagina == null) {
			throw new NoExisteDato(NO_EXISTE_PAGINA + pId);
		}

		return jPagina;
	}

	private JLineaFormulario getJLineaById(final Long pId) {

		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JLineaFormulario jLinea = entityManager.find(JLineaFormulario.class, pId);

		if (jLinea == null) {
			throw new NoExisteDato(NO_EXISTE_LINEA + pId);
		}

		return jLinea;
	}

	private JElementoFormulario getJElementoById(final Long pId) {

		if (pId == null) {
			throw new FaltanDatosException(FALTA_COMPONENTE);
		}

		final JElementoFormulario jElemento = entityManager.find(JElementoFormulario.class, pId);

		if (jElemento == null) {
			throw new NoExisteDato(NO_EXISTE_COMPONENTE + ": " + pId);
		}

		return jElemento;
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

	/**
	 * Crea un hueco en la lista de lineas.
	 *
	 * @param pJPagina
	 *            pagina que tiene la lista de lineas
	 * @param pInicio
	 *            inicio para el hueco
	 * @return true, si tiene exito
	 */
	private boolean creaHuecoEnLineas(final JPaginaFormulario pJPagina, final Integer pInicio) {
		return actualizaEntreLineas(pJPagina, pInicio, 1);
	}

	private boolean quitaHuecoEnLineas(final JPaginaFormulario pJPagina, final Integer pInicio) {
		return actualizaEntreLineas(pJPagina, pInicio, -1);
	}

	private boolean actualizaEntreLineas(final JPaginaFormulario pJPagina, final Integer pInicio,
			final Integer pFactor) {
		boolean resultado = false;

		// creamos hueco si es necesario
		if (!pJPagina.getLineasFormulario().isEmpty() && pInicio <= pJPagina.getLineasFormulario().size()) {
			final String sql = "UPDATE JLineaFormulario p SET p.orden = p.orden + :factor WHERE p.paginaFormulario.codigo = :idPagina and p.orden >= :inicio";
			final Query query = entityManager.createQuery(sql);
			query.setParameter("factor", pFactor);
			query.setParameter("idPagina", pJPagina.getCodigo());
			query.setParameter("inicio", pInicio);
			final int update = query.executeUpdate();
			resultado = (update > 0);
		}

		return resultado;
	}

	/**
	 * Crea un hueco en la lista de componentes.
	 *
	 * @param pJLineaSeleccionada
	 *            linea seleccionada
	 * @param pInicio
	 *            inicio
	 * @return true, si tiene exito
	 */
	private boolean actualizaEntreComponentes(final JLineaFormulario pJLineaSeleccionada, final Integer pInicio,
			final Integer pFactor) {
		boolean resultado = false;

		// creamos hueco si es necesario
		if (!pJLineaSeleccionada.getElementoFormulario().isEmpty()
				&& pInicio <= pJLineaSeleccionada.getElementoFormulario().size()) {
			final String sql = "UPDATE JElementoFormulario p SET p.orden = p.orden + :factor WHERE p.lineaFormulario.codigo = :idLinea and p.orden >= :inicio";
			final Query query = entityManager.createQuery(sql);
			query.setParameter("factor", pFactor);
			query.setParameter("idLinea", pJLineaSeleccionada.getCodigo());
			query.setParameter("inicio", pInicio);
			final int update = query.executeUpdate();
			resultado = (update > 0);
		}
		return resultado;
	}

	private boolean creaHuecoEnComponentes(final JLineaFormulario pJLineaSeleccionada, final Integer pInicio) {
		return actualizaEntreComponentes(pJLineaSeleccionada, pInicio, 1);
	}

	private boolean quitaHuecoEnComponentes(final JLineaFormulario pJLineaSeleccionada, final Integer pInicio) {
		return actualizaEntreComponentes(pJLineaSeleccionada, pInicio, -1);
	}

	/**
	 * Obtiene el valor de una linea en base al orden.
	 *
	 * @param jPagina
	 *            pagina
	 * @param pOrden
	 *            orden
	 * @return el valor de la linea
	 */
	private JLineaFormulario getLineaByOrden(final JPaginaFormulario jPagina, final Integer pOrden) {

		final String sql = "SELECT d FROM JLineaFormulario d where d.paginaFormulario.codigo = :idPagina and d.orden = :orden";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idPagina", jPagina.getCodigo());
		query.setParameter("orden", pOrden);

		return (JLineaFormulario) query.getResultList().get(0);
	}

	/**
	 * Orden de la linea para la insercion .
	 *
	 * @param pJPagina
	 *            pagina
	 * @param pJLineaSeleccionada
	 *            linea seleccionada
	 * @param pPosicion
	 *            posicion
	 * @return orden
	 */
	private Integer ordenInsercionLinea(final JPaginaFormulario pJPagina, final JLineaFormulario pJLineaSeleccionada,
			final String pPosicion) {
		Integer orden = 1;

		if (pJLineaSeleccionada == null) {
			if (!pJPagina.getLineasFormulario().isEmpty()) {
				orden += pJPagina.getLineasFormulario().size();
			}
		} else {
			orden = pJLineaSeleccionada.getOrden();
			if ("D".equals(pPosicion)) {
				orden++;
			}
		}
		return orden;
	}

	/**
	 * gestiona las lineas para los compontentes que van ellos solos en una linea.
	 *
	 * @param pJPagina
	 *            pagina
	 * @param pJLineaSeleccionada
	 *            linea seleccionada
	 * @param pPosicion
	 *            posicion
	 * @return linea formulario sobre la que insertar el compontente
	 */
	private JLineaFormulario lineaBloque(final JPaginaFormulario pJPagina, final JLineaFormulario pJLineaSeleccionada,
			final String pPosicion) {
		JLineaFormulario jLinea = null;
		JLineaFormulario jUltimaLinea = null;
		JLineaFormulario jAnteriorLinea = null;

		// pagina vacia, insertamos un linea
		if (pJPagina.getLineasFormulario().isEmpty()) {
			jLinea = JLineaFormulario.createDefault(1, pJPagina);
		} else if (pJLineaSeleccionada == null) {
			// no hemos seleccionado linea, miramos la ultima
			if (pJPagina.getLineasFormulario().size() == 1) {
				jUltimaLinea = (JLineaFormulario) pJPagina.getLineasFormulario().toArray()[0];
			} else {
				jUltimaLinea = Collections.max(pJPagina.getLineasFormulario(),
						(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
			}

			// miramos si esta vacia de componentes
			if (jUltimaLinea.getElementoFormulario().isEmpty()) {
				jLinea = jUltimaLinea;
			} else {
				jLinea = JLineaFormulario.createDefault(jUltimaLinea.getOrden() + 1, pJPagina);
			}
		} else {
			if (pJLineaSeleccionada.getElementoFormulario().isEmpty() && "D".equals(pPosicion)) {
				jLinea = pJLineaSeleccionada;
			} else if (!pJLineaSeleccionada.getElementoFormulario().isEmpty() && "D".equals(pPosicion)) {
				creaHuecoEnLineas(pJPagina, pJLineaSeleccionada.getOrden() + 1);
				jLinea = JLineaFormulario.createDefault(pJLineaSeleccionada.getOrden() + 1, pJPagina);
			} else if ("A".equals(pPosicion)) {
				if (pJPagina.getLineasFormulario().size() == 1 || pJLineaSeleccionada.getOrden() == 1) {
					creaHuecoEnLineas(pJPagina, 1);
					jLinea = JLineaFormulario.createDefault(1, pJPagina);
				} else {
					jAnteriorLinea = getLineaByOrden(pJPagina, pJLineaSeleccionada.getOrden() - 1);

					if (jAnteriorLinea != null) {
						if (jAnteriorLinea.getElementoFormulario().isEmpty()) {
							jLinea = jAnteriorLinea;
						} else {
							creaHuecoEnLineas(pJPagina, pJLineaSeleccionada.getOrden());
							jLinea = JLineaFormulario.createDefault(pJLineaSeleccionada.getOrden(), pJPagina);
						}
					}
				}
			}
		}
		return jLinea;

	}

	/**
	 * gestiona la Linea para insertar componentes.
	 *
	 * @param pJPagina
	 *            pagina
	 * @param pJLineaSeleccionada
	 *            linea seleccionada
	 * @param pPosicion
	 *            posicion
	 * @return la linea
	 */
	private JLineaFormulario lineaComponentes(final JPaginaFormulario pJPagina,
			final JLineaFormulario pJLineaSeleccionada, final String pPosicion) {
		JLineaFormulario jLinea = null;
		JLineaFormulario jUltimaLinea = null;

		// pagina vacia, insertamos un linea
		if (pJPagina.getLineasFormulario().isEmpty()) {
			jLinea = JLineaFormulario.createDefault(1, pJPagina);
		} else if (pJLineaSeleccionada == null) {
			// no hemos seleccionado linea, miramos la ultima
			if (pJPagina.getLineasFormulario().size() == 1) {
				jUltimaLinea = (JLineaFormulario) pJPagina.getLineasFormulario().toArray()[0];
			} else {
				jUltimaLinea = Collections.max(pJPagina.getLineasFormulario(),
						(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
			}

			if (!jUltimaLinea.completa()) {
				jLinea = jUltimaLinea;
			} else {
				jLinea = JLineaFormulario.createDefault(jUltimaLinea.getOrden() + 1, pJPagina);
			}
		} else if (!pJLineaSeleccionada.completa()) {
			jLinea = pJLineaSeleccionada;
		}
		return jLinea;
	}

	/**
	 * Orden del componente para la insercion.
	 *
	 * @param pJLineaSeleccionada
	 *            linea seleccionada
	 * @param pOrden
	 *            orden
	 * @param pPosicion
	 *            posicion
	 * @return orden
	 */
	private Integer ordenInsercionComponente(final JLineaFormulario pJLineaSeleccionada, final Integer pOrden,
			final String pPosicion) {
		Integer orden = null;
		if (pJLineaSeleccionada != null) {
			if (pJLineaSeleccionada.getElementoFormulario().isEmpty()) {
				orden = 1;
			} else if (pJLineaSeleccionada.getElementoFormulario().size() < 6) {
				if (pOrden == null) {
					orden = pJLineaSeleccionada.getElementoFormulario().size() + 1;
				} else {
					if ("A".equals(pPosicion)) {
						orden = pOrden;
					} else {
						orden = pOrden + 1;
					}

					creaHuecoEnComponentes(pJLineaSeleccionada, orden);
				}
			}
		}
		return orden;
	}

}
