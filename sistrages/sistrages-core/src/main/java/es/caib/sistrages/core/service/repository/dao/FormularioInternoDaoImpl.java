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
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.types.TypeListaValores;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.service.repository.model.JCampoFormulario;
import es.caib.sistrages.core.service.repository.model.JCampoFormularioCasillaVerificacion;
import es.caib.sistrages.core.service.repository.model.JCampoFormularioIndexado;
import es.caib.sistrages.core.service.repository.model.JCampoFormularioTexto;
import es.caib.sistrages.core.service.repository.model.JDominio;
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
	 * getFormularioPaginasById(java.lang.Long)
	 */
	@Override
	public FormularioInterno getFormularioPaginasById(final Long pId) {
		final JFormulario jForm = getJFormularioById(pId);

		jForm.getPaginas();

		final FormularioInterno formInt = jForm.toModel();

		for (final JPaginaFormulario jPagina : jForm.getPaginas()) {
			formInt.getPaginas().add(jPagina.toModel());
		}

		// ordenamos lista de paginas por campo orden
		if (!formInt.getPaginas().isEmpty() && formInt.getPaginas().size() > 1) {
			Collections.sort(formInt.getPaginas(),
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
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
	public FormularioInterno getFormularioCompletoById(final Long pId) {
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
	public ObjetoFormulario addComponente(final TypeObjetoFormulario pTipoObjeto, final Long pIdPagina,
			final Long pIdLinea, final Integer pOrden, final String pPosicion) {
		JLineaFormulario jLineaSeleccionada = null;
		ObjetoFormulario objetoResultado = null;

		if (pIdPagina != null) {
			final JPaginaFormulario jPagina = getJPaginaById(pIdPagina);

			if (pIdLinea != null) {
				jLineaSeleccionada = getJLineaById(pIdLinea);
			}

			switch (pTipoObjeto) {
			case LINEA:
				// si el orden es inferior o igual al tamaño de la lista de lineas hay que
				// hacer hueco
				creaHuecoEnLineas(jPagina, pOrden);
				final JLineaFormulario jLineaCreada = JLineaFormulario.createDefault(pOrden, jPagina);
				entityManager.persist(jLineaCreada);
				entityManager.merge(jPagina);
				objetoResultado = jLineaCreada.toModel();
				break;
			case CAMPO_TEXTO:
				creaHuecoEnComponentes(jLineaSeleccionada, pOrden);
				final JCampoFormularioTexto jObjFormCampoTexto = JCampoFormularioTexto.createDefault(pOrden,
						jLineaSeleccionada);
				entityManager.persist(jObjFormCampoTexto);
				entityManager.merge(jLineaSeleccionada);
				objetoResultado = jObjFormCampoTexto.toModel();
				break;
			case CHECKBOX:
				creaHuecoEnComponentes(jLineaSeleccionada, pOrden);
				final JCampoFormularioCasillaVerificacion jObjFormCasillaVerificacion = JCampoFormularioCasillaVerificacion
						.createDefault(pOrden, jLineaSeleccionada);
				entityManager.persist(jObjFormCasillaVerificacion);
				entityManager.merge(jLineaSeleccionada);
				objetoResultado = jObjFormCasillaVerificacion.toModel();
				break;
			case SELECTOR:
				creaHuecoEnComponentes(jLineaSeleccionada, pOrden);
				final JCampoFormularioIndexado jObjFormSelector = JCampoFormularioIndexado.createDefault(pOrden,
						jLineaSeleccionada);
				entityManager.persist(jObjFormSelector);
				entityManager.merge(jLineaSeleccionada);
				objetoResultado = jObjFormSelector.toModel();
				break;
			case SECCION:
				creaHuecoEnLineas(jPagina, pOrden);
				final JLineaFormulario jLineaBloqueCreadaSeccion = JLineaFormulario.createDefault(pOrden, jPagina);
				if (jLineaBloqueCreadaSeccion != null) {
					final JSeccionFormulario jObjFormSeccion = JSeccionFormulario.createDefault(1,
							jLineaBloqueCreadaSeccion);

					entityManager.persist(jLineaBloqueCreadaSeccion);
					entityManager.persist(jObjFormSeccion);
					entityManager.merge(jPagina);

					objetoResultado = jLineaBloqueCreadaSeccion.toModel();
					((LineaComponentesFormulario) objetoResultado).getComponentes().add(jObjFormSeccion.toModel());
				}
				break;
			case ETIQUETA:
				creaHuecoEnLineas(jPagina, pOrden);
				final JLineaFormulario jLineaBloqueCreadaEtiqueta = JLineaFormulario.createDefault(pOrden, jPagina);
				if (jLineaBloqueCreadaEtiqueta != null) {
					final JEtiquetaFormulario jObjFormEtiqueta = JEtiquetaFormulario.createDefault(1,
							jLineaBloqueCreadaEtiqueta);

					entityManager.persist(jLineaBloqueCreadaEtiqueta);
					entityManager.persist(jObjFormEtiqueta);
					entityManager.merge(jPagina);

					objetoResultado = jLineaBloqueCreadaEtiqueta.toModel();
					((LineaComponentesFormulario) objetoResultado).getComponentes().add(jObjFormEtiqueta.toModel());
				}
				break;
			}

		}

		return objetoResultado;

	}

	@Override
	public void removeLineaFormulario(final Long pId) {
		final JLineaFormulario jLinea = getJLineaById(pId);
		final JPaginaFormulario jPagina = jLinea.getPaginaFormulario();

		if (jLinea.getPaginaFormulario().getLineasFormulario().size() > 1
				&& jLinea.getOrden() < jLinea.getPaginaFormulario().getLineasFormulario().size()) {
			// hay que reordenar lineas
			quitaHuecoEnLineas(jLinea.getPaginaFormulario(), jLinea.getOrden() + 1);
		}

		jPagina.removeLinea(jLinea);

		entityManager.merge(jPagina);
	}

	@Override
	public void removeComponenteFormulario(final Long pId) {
		final JElementoFormulario jElemento = getJElementoById(pId);
		final JLineaFormulario jLineaFormulario = jElemento.getLineaFormulario();

		if (jLineaFormulario.getElementoFormulario().size() > 1
				&& jElemento.getOrden() < jLineaFormulario.getElementoFormulario().size()) {
			// hay que reordenar componentes
			quitaHuecoEnComponentes(jLineaFormulario, jElemento.getOrden() + 1);
		}

		jLineaFormulario.removeElemento(jElemento);

		entityManager.merge(jLineaFormulario);
	}

	@Override
	public ObjetoFormulario updateComponente(final ComponenteFormulario pComponente) {
		// TODO
		ObjetoFormulario objetoResultado = null;

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
				objetoResultado = jSeccion.toModel();
				break;
			case IMAGEN:
				final JImagenFormulario jImagen = jElemento.getImagenFormulario();
				final ComponenteFormularioImagen imagen = (ComponenteFormularioImagen) pComponente;
				entityManager.merge(jImagen);
				objetoResultado = jImagen.toModel();
				break;
			case ETIQUETA:
				final JEtiquetaFormulario jEtiqueta = jElemento.getEtiquetaFormulario();
				final ComponenteFormularioEtiqueta etiqueta = (ComponenteFormularioEtiqueta) pComponente;
				jEtiqueta.setTipo(etiqueta.getTipoEtiqueta().toString());
				entityManager.merge(jEtiqueta);
				objetoResultado = jEtiqueta.toModel();
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
					jCampoTexto.setNormalExpresionRegular(campoTexto.getExpresionRegular());
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
				objetoResultado = jCampoTexto.toModel();
				break;
			case CHECKBOX:
				final JCampoFormularioCasillaVerificacion jCampoCheckbox = jElemento.getCampoFormulario()
						.getCampoFormularioCasillaVerificacion();
				final ComponenteFormularioCampoCheckbox campoCheckbox = (ComponenteFormularioCampoCheckbox) pComponente;

				jCampoCheckbox.setValorChecked(campoCheckbox.getValorChecked());
				jCampoCheckbox.setValorNoChecked(campoCheckbox.getValorNoChecked());

				entityManager.merge(jCampoCheckbox);
				objetoResultado = jCampoCheckbox.toModel();
				break;
			case SELECTOR:
				JCampoFormularioIndexado jCampoIndexado = jElemento.getCampoFormulario().getCampoFormularioIndexado();
				final ComponenteFormularioCampoSelector campoIndexado = (ComponenteFormularioCampoSelector) pComponente;

				jCampoIndexado.setTipoCampoIndexado(campoIndexado.getTipoCampoIndexado().name());
				jCampoIndexado.setTipoListaValores(campoIndexado.getTipoListaValores().toString());

				if (TypeListaValores.FIJA.equals(campoIndexado.getTipoListaValores())) {
					jCampoIndexado = JCampoFormularioIndexado.mergeListaValoresFijaModel(jCampoIndexado, campoIndexado);
				} else if (!campoIndexado.getListaValorListaFija().isEmpty()) {
					// no es fija y hay datos por lo que hay que eliminarlos
					campoIndexado.getListaValorListaFija().clear();
					jCampoIndexado = JCampoFormularioIndexado.mergeListaValoresFijaModel(jCampoIndexado, campoIndexado);
				}

				if (TypeListaValores.DOMINIO.equals(campoIndexado.getTipoListaValores())) {
					jCampoIndexado.setDominio(JDominio.fromModelStatic(campoIndexado.getDominio()));
					jCampoIndexado.setCampoDominioCodigo(campoIndexado.getCampoDominioCodigo());
					jCampoIndexado.setCampoDominioDescripcion(campoIndexado.getCampoDominioDescripcion());
					jCampoIndexado = JCampoFormularioIndexado.mergeListaParametrosDominioModel(jCampoIndexado,
							campoIndexado);
				} else {
					jCampoIndexado.setDominio(null);
					jCampoIndexado.setCampoDominioCodigo(null);
					jCampoIndexado.setCampoDominioDescripcion(null);
					campoIndexado.getListaParametrosDominio().clear();
					jCampoIndexado = JCampoFormularioIndexado.mergeListaParametrosDominioModel(jCampoIndexado,
							campoIndexado);
				}

				entityManager.merge(jCampoIndexado);
				objetoResultado = jCampoIndexado.toModel();
				break;
			}

		}

		return objetoResultado;

	}

	@Override
	public void updateOrdenComponente(final Long pId, final Integer pOrden) {
		final JElementoFormulario jElemento = getJElementoById(pId);
		jElemento.setOrden(pOrden);
		entityManager.merge(jElemento);
	}

	@Override
	public void updateOrdenLinea(final Long pId, final Integer pOrden) {
		final JLineaFormulario jLinea = getJLineaById(pId);
		jLinea.setOrden(pOrden);
		entityManager.merge(jLinea);
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

}
