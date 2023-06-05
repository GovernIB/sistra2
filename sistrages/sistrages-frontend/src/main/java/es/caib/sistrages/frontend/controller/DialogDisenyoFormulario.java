package es.caib.sistrages.frontend.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;

import es.caib.sistrages.core.api.exception.ErrorNoControladoException;
import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampo;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoCaptcha;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoCheckbox;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
import es.caib.sistrages.core.api.model.ComponenteFormularioSeccion;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.ParametroDominio;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.ValorListaFija;
import es.caib.sistrages.core.api.model.comun.ConstantesDisenyo;
import es.caib.sistrages.core.api.model.types.TypeAccionFormulario;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeCampoIndexado;
import es.caib.sistrages.core.api.model.types.TypeCampoTexto;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeListaValores;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.model.types.TypeScriptFormulario;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.SeccionReutilizableService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.core.api.util.UtilDisenyo;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Disenyo formulario.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogDisenyoFormulario extends DialogControllerBase {

	@Inject
	FormularioInternoService formIntService;

	@Inject
	TramiteService tramiteService;

	@Inject
	SeccionReutilizableService seccionService;

	@Inject
	DominioService dominioService;

	@Inject
	SeccionReutilizableService seccionReutilizableService;

	/** Id formulario **/
	private String id;

	/** Id. formulario paso. **/
	private String idFormulario;

	/** Id. seccion **/
	private String idSeccion;
	private String identificadorSeccion;

	/** id tramite. */
	private String idTramiteVersion;

	/** Tipo de diseño: trámite o seccion **/
	private String tipoDisenyo;

	/** Formulario. **/
	private DisenyoFormulario formulario;

	/** Formulario. **/
	private Map<Long, DisenyoFormulario> formularioSeccion;

	/** Pag. actual. **/
	private int paginaActual;

	private Boolean cambios = false;

	/** Posicionamiento. **/
	private String posicionamiento;

	/** Componente editado (copia original). **/
	private ObjetoFormulario objetoFormularioEdit;

	/** Traducciones editado (cuando se llama a editar traducciones). **/
	private Literal traduccionesEdit;

	/** Url ventana propiedades **/
	private String panelPropiedadesUrl;

	/** Url detalle componente. **/
	private String detalleComponenteUrl;

	/** Url iframe. **/
	private String urlIframe;

	private Literal traduccionesI;

	/** Indica si esta 'colapsado' el panel de propiedades (a true colapsado). **/
	private boolean visiblePropiedades = false;

	/** Indica si esta 'colapsado' el panel de scripts (a true colapsado). **/
	private boolean visibleScripts = true;

	/** Indica si esta 'colapsado' el panel de estilos (a true colapsado). **/
	private boolean visibleEstilos = true;

	/** Codigo componente destino al pedir confirmacion de cambios. **/
	private String codigoObjFormularioDestino = null;

	/** Columnas. **/
	private List<Integer> ncolumnas;

	private TramiteVersion tramiteVersion;

	/** Idiomas. **/
	private List<String> idiomas;
	private String idioma;

	/** Los objetos a copiar. **/
	private Long idObjetoCopy = null;
	private ObjetoFormulario objetoCopy = null;
	private TypeObjetoFormulario tipoObjetoCopy = null;
	private Integer idPaginaCopy = null;
	private Long idLineaCopy = null;
	private boolean copy = false;
	private boolean cut = false;

	/** Url de componente del formulario vacio. **/
	private static final String URL_FORMULARIO_VACIO = "/secure/app/dialogDisenyoFormularioVacio.xhtml";
	/**
	 * Indica la acción que se iba a realizar antes de que salga el dialog de
	 * confirmar los cambios del componente que has tocado.
	 **/
	private TypeAccionFormulario accionPendiente;

	private boolean mostrarOcultos;
	/** Altura **/
	private String height;

	public boolean isTipoTramite() {
		return tipoDisenyo != null && tipoDisenyo.equals(TypeParametroVentana.PARAMETRO_DISENYO_TRAMITE.toString());
	}

	public boolean isTipoSeccion() {
		return tipoDisenyo != null && tipoDisenyo.equals(TypeParametroVentana.PARAMETRO_DISENYO_SECCION.toString());
	}

	private String titulo;

	private String portapapeles;

	private String errorCopiar;

	private boolean esIframe = false;

	/**
	 * Inicializacion.
	 **/
	public void init() {

		if (isTipoSeccion()) {
			titulo = UtilJSF.getLiteral("dialogDisenyoFormulario.titulo.seccion");
		} else {
			titulo = UtilJSF.getLiteral("dialogDisenyoFormulario.titulo.tramite");
		}

		// Recuperacion formulario
		recuperarFormulario(id);

		// recupera tramite version
		if (isTipoTramite() && StringUtils.isNotEmpty(idTramiteVersion)) {
			tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
			idiomas = UtilTraducciones.getIdiomasSoportados(tramiteVersion);
			if (!idiomas.isEmpty()) {
				if (idiomas.contains(UtilJSF.getIdioma().toString())) {
					idioma = UtilJSF.getIdioma().toString();
				} else {
					idioma = idiomas.get(0);
				}
			}
		}

		if (isTipoSeccion()) {
			idiomas = new ArrayList<>();
			idiomas.add(TypeIdioma.CASTELLANO.toString());
			idiomas.add(TypeIdioma.CATALAN.toString());
			idiomas.add(TypeIdioma.INGLES.toString());
			if (idiomas.contains(UtilJSF.getIdioma().toString())) {
				idioma = UtilJSF.getIdioma().toString();
			} else {
				idioma = idiomas.get(0);
			}
		}

		panelPropiedadesUrl = URL_FORMULARIO_VACIO;
		urlIframe = "FormRenderServlet?ts=" + System.currentTimeMillis();
		paginaActual = 1;
		posicionamiento = "D";
		height = UtilJSF.getSessionBean().getHeight() - 165 + "px";
	}

	/**
	 * Evento tras pulsar el botón de descargar cambios. Se tiene que dejar el
	 * objeto como estaba al principio antes de editarse.
	 **/
	public void descartarCambios() {

		/*
		 * ObjetoFormulario ofOriginal = null; if (objetoFormularioEdit instanceof
		 * LineaComponentesFormulario) { ofOriginal =
		 * formulario.getPaginas().get(paginaActual -
		 * 1).getLinea(objetoFormularioEdit.getCodigo()); } else if
		 * (objetoFormularioEdit instanceof ComponenteFormulario) { ofOriginal =
		 * formulario.getPaginas().get(paginaActual -
		 * 1).getComponente(objetoFormularioEdit.getCodigo()); }
		 *
		 * if (ofOriginal != null) { objetoFormularioEdit = ofOriginal; }
		 */

		recuperarFormulario(id);
		// Continuamos con la accion que se iba a realizar
		continuarAccion();
	}

	/**
	 * Confirmacion cambios.
	 **/
	public void confirmacionCambios() {

		if (this.isTipoSeccion()) {
			final String identificador = ((ComponenteFormulario) objetoFormularioEdit).getIdComponente();
			if (!identificador.startsWith("SRE_" + this.getIdentificadorSeccion())) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("error.validacion.identificador.formatoSRE") + " " + "SRE_"
								+ this.getIdentificadorSeccion(),
						true, ID_TEXT_IDENTIFICADOR);
				return;
			}
		}

		// Guarda cambios
		aplicarCambios();

		// Continuamos con la accion que se iba a realizar
		continuarAccion();
	}

	/**
	 * Tras venir del evento de descartar o confirmar cambios, hay que continuar con
	 * la acción que quería realizar el usuario.
	 */
	public void continuarAccion() {
		switch (accionPendiente) {
		case PROPIEDADES_FORMULARIO:
			abrirPropiedadesFormulario(false);
			break;
		case ADD_LINEA:
			insertaLineaComponenteBloque(TypeObjetoFormulario.LINEA, null);
			break;
		case ADD_SECCION:
			insertaLineaComponenteBloque(TypeObjetoFormulario.SECCION, null);
			break;
		case ADD_ETIQUETA:
			insertaLineaComponenteBloque(TypeObjetoFormulario.ETIQUETA, null);
			break;
		case ADD_TEXTO:
			insertaCampo(TypeObjetoFormulario.CAMPO_TEXTO);
			break;
		case ADD_SELECTOR:
			insertaCampo(TypeObjetoFormulario.SELECTOR);
			break;
		case ADD_CHECKBOX:
			insertaCampo(TypeObjetoFormulario.CHECKBOX);
			break;
		case ADD_CAPTCHA:
			insertaLineaComponenteBloque(TypeObjetoFormulario.CAPTCHA, null);
			// insertaCampo(TypeObjetoFormulario.CAPTCHA);
			break;
		case ADD_SECCION_REUTILIZABLE:
			final Map<String, String> params = new HashMap<>();
			UtilJSF.openDialog(DialogDisenyoSeccionReutilizable.class, TypeModoAcceso.valueOf(modoAcceso), params, true,
					700, 200);
			break;
		case COPIAR:
			copy(false);
			break;
		case CORTAR:
			cut(false);
			break;
		case PEGAR:
			paste(false);
			break;
		case MOVER_IZQ:
			moverObjetoIzq(false);
			break;
		case MOVER_DER:
			moverObjetoDer(false);
			break;
		case PAG_ANT:
			moverPaginaIzq(false);
			break;
		case PAG_NEXT:
			moverPaginaDer(false);
			break;
		case VER_ESTRUCTURA:
			abrirDialogoEstructura(false);
			break;
		case PREVISUALIZAR:
			previsualizar(false);
			break;
		case CERRAR:
			cancelar(false);
			break;
		case SELECCIONAR_OTRO_COMPONENTE:
			cambiarEdicionComponente(codigoObjFormularioDestino, false, null, null);
			codigoObjFormularioDestino = null;
			break;
		default:
			break;
		}

	}

	/**
	 * Abre un di&aacute;logo para previsualizar tramite.
	 */
	public void previsualizar() {
		previsualizar(true);
	}

	/**
	 * Abre un di&aacute;logo para previsualizar tramite.
	 *
	 * @param check Indica si tiene que comprobar si has modificado el componente
	 *              anterior.
	 */
	public void previsualizar(final boolean check) {

		if (check && isModificadoSinGuardar(TypeAccionFormulario.PREVISUALIZAR)) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(tramiteVersion.getCodigo()));
		if (this.modoAcceso.equals(TypeModoAcceso.EDICION.toString())) {
			UtilJSF.openDialog(DialogTramiteVersionPrevisualizar.class, TypeModoAcceso.EDICION, params, true, 830, 400);
		} else {
			UtilJSF.openDialog(DialogTramiteVersionPrevisualizar.class, TypeModoAcceso.CONSULTA, params, true, 830,
					400);
		}
	}

	/**
	 * Editar componente.
	 **/
	public void editarComponente() {
		editarComponente(true);
	}

	/**
	 * Editar componente.
	 *
	 * @param check Indica si tiene que comprobar si has modificado el componente
	 *              anterior.
	 */
	public void editarComponente(final boolean check) {

		if (check && isModificadoSinGuardar(TypeAccionFormulario.SELECCIONAR_OTRO_COMPONENTE)) {
			return;
		}

		final FacesContext context = FacesContext.getCurrentInstance();
		final Map<String, String> map = context.getExternalContext().getRequestParameterMap();

		String idComponente = null;

		if (StringUtils.isNotEmpty(map.get("id"))) {
			idComponente = map.get("id");
		}
		boolean isTipoSeccion = map.get("tipoSeccion") != null && ("S".equals(map.get("tipoSeccion")));
		Long seccionID = null;
		Long seccionFormID = null;
		if (isTipoSeccion) {
			if (map.get("seccionId") != null) {
				seccionID = Long.valueOf(map.get("seccionId"));
			}
			if (map.get("seccionFormId") != null) {
				seccionFormID = Long.valueOf(map.get("seccionFormId"));
			}
		}

		// Cambiamos a nuevo componente
		cambiarEdicionComponente(idComponente, isTipoSeccion, seccionID, seccionFormID);

	}

	/**
	 * Cambiar edicion componente.
	 *
	 * @param idComponente the id componente
	 */
	private void cambiarEdicionComponente(final String idComponente, boolean isTipoSeccion, Long seccionID,
			Long seccionFormID) {
		limpiaSeleccion();

		if (idComponente != null) {
			ObjetoFormulario cf = null;
			if (isTipoSeccion) {
				cf = getComponenteSeccionReutilizable(seccionID, seccionFormID, idComponente);
			} else {
				if (idComponente.contains("L")) {
					cf = formulario.getPaginas().get(paginaActual - 1)
							.getLinea(Long.parseLong(idComponente.substring(1)));
				} else {
					cf = formulario.getPaginas().get(paginaActual - 1).getComponente(Long.parseLong(idComponente));
				}
			}

			// Buscamos nuevo componente
			if (cf != null) {
				objetoFormularioEdit = (ObjetoFormulario) UtilCoreApi.cloneModelApi(cf);
				// Verificamos que no haya problemas en la serialización
				if (!UtilCoreApi.equalsModelApi(cf, objetoFormularioEdit)) {
					throw new FrontException("Componente clonado no coincide");
				}
			}
		}

		String pagina = URL_FORMULARIO_VACIO;
		detalleComponenteUrl = null;
		if (objetoFormularioEdit != null) {
			if (objetoFormularioEdit instanceof ComponenteFormulario) {
				pagina = getPaginaEdicionComponente(((ComponenteFormulario) objetoFormularioEdit).getTipo());

				detalleComponenteUrl = "/secure/app/dialogDisenyoFormularioComponente"
						+ ((ComponenteFormulario) objetoFormularioEdit).getTipo() + ".xhtml";
			} else if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				pagina = "/secure/app/dialogDisenyoFormularioLinea.xhtml";
			}
		}

		panelPropiedadesUrl = pagina;
		generaNumColumnas();
		calcularVisibilidadSeleccion();
	}

	/** Las variables booleanas para mostrar o desactivar botones **/
	private boolean desactivarAplicarCambios = false;
	private boolean habilitadoObjetoMovible = true;

	public boolean getHabilitadoBtnObjCopy() {
		return habilitadoObjetoMovible;
	}

	public boolean getHabilitadoBtnObjPaste() {
		return habilitadoObjetoMovible;
	}

	public boolean getHabilitadoBtnObjCut() {
		return habilitadoObjetoMovible;
	}

	public boolean getHabilitadoBtnObjDel() {
		return habilitadoObjetoMovible;
	}

	private boolean habilitadoObjetoAccion = true;

	public boolean getHabilitadoBtnLN() {
		return habilitadoObjetoAccion;
	}

	public boolean getHabilitadoBtnSC() {
		return habilitadoObjetoAccion;
	}

	public boolean getHabilitadoBtnET() {
		return habilitadoObjetoAccion;
	}

	public boolean getHabilitadoBtnCT() {
		return habilitadoObjetoAccion;
	}

	public boolean getHabilitadoBtnSE() {
		return habilitadoObjetoAccion;
	}

	public boolean getHabilitadoBtnCK() {
		return habilitadoObjetoAccion;
	}

	public boolean getHabilitadoBtnOC() {
		return habilitadoObjetoAccion;
	}

	public boolean getHabilitadoBtnCP() {
		return habilitadoObjetoAccion;
	}

	public boolean getHabilitadoBtnSR() {
		return habilitadoObjetoAccion;
	}

	/**
	 * Respecto a objetoFormularioEdit , calcula la visibilidad, si se puede tocar
	 * ciertos botones o tienen que no ser visibles.
	 */
	private void calcularVisibilidadSeleccion() {

		// Desactivamos el boton de aplicar cambios
		this.desactivarAplicarCambios = objetoFormularioEdit == null
				|| (objetoFormularioEdit instanceof ComponenteFormulario
						&& ((ComponenteFormulario) objetoFormularioEdit).isTipoSeccionReutilizable());

		// Variable que indica si es movible.
		if (isTipoSeccion()) {
			// Solo esta habilitado si el orden es mayor que 1 (es decir, no es la seccion
			// principal)
			this.habilitadoObjetoMovible = objetoFormularioEdit != null;
		} else {
			if (objetoFormularioEdit == null) {
				this.habilitadoObjetoMovible = false;
			} else {
				this.habilitadoObjetoMovible = !(objetoFormularioEdit instanceof ComponenteFormulario)
						|| (objetoFormularioEdit instanceof ComponenteFormulario
								&& !((ComponenteFormulario) objetoFormularioEdit).isTipoSeccionReutilizable());
			}
		}

		// Variable que indica si los botones de añadir componentes están activos.
		if (objetoFormularioEdit == null) {
			habilitadoObjetoAccion = true;
		}

		if (isTipoSeccion()) {
			habilitadoObjetoAccion = true;
		} else {
			habilitadoObjetoAccion = !(objetoFormularioEdit instanceof ComponenteFormulario)
					|| (objetoFormularioEdit instanceof ComponenteFormulario
							&& !((ComponenteFormulario) objetoFormularioEdit).isTipoSeccionReutilizable());
		}
	}

	private ObjetoFormulario getComponenteSeccionReutilizable(Long seccionID, Long seccionFormID, String idComponente) {
		if (formularioSeccion == null) {
			formularioSeccion = new HashMap<>();
		}
		if (!formularioSeccion.containsKey(seccionFormID)) {
			DisenyoFormulario disenyo = seccionReutilizableService.getFormularioInternoCompleto(seccionFormID);
			formularioSeccion.put(seccionFormID, disenyo);
		}
		if (idComponente.contains("L")) {
			return formularioSeccion.get(seccionFormID).getPaginas().get(0)
					.getLinea(Long.parseLong(idComponente.substring(1)));
		} else {
			return formularioSeccion.get(seccionFormID).getPaginas().get(0).getComponente(Long.parseLong(idComponente));
		}
	}

	/**
	 * Obtiene la pagina que tiene que cargar en la derecha en propiedades.
	 *
	 * @param tipo
	 * @return
	 */
	private String getPaginaEdicionComponente(final TypeObjetoFormulario tipo) {
		String pagina;
		switch (tipo) {
		case CAMPO_TEXTO:
			pagina = "/secure/app/dialogDisenyoFormularioComponente.xhtml";
			break;
		case SELECTOR:
			pagina = "/secure/app/dialogDisenyoFormularioComponente.xhtml";
			break;
		case ETIQUETA:
			pagina = "/secure/app/dialogDisenyoFormularioEtiqueta.xhtml";
			break;
		case SECCION:
			pagina = "/secure/app/dialogDisenyoFormularioSeccion.xhtml";
			break;
		case SECCION_REUTILIZABLE:
			pagina = "/secure/app/dialogDisenyoFormularioSeccionReutilizable.xhtml";
			break;
		case CHECKBOX:
			pagina = "/secure/app/dialogDisenyoFormularioComponente.xhtml";
			break;
		case CAMPO_OCULTO:
			pagina = "/secure/app/dialogDisenyoFormularioComponenteOC.xhtml";
			break;
		case CAPTCHA:
			pagina = "/secure/app/dialogDisenyoFormularioComponenteCP.xhtml";
			break;
		// TODO PENDIENTE
		default:
			pagina = null;
			break;
		}
		return pagina;
	}

	/**
	 * Indica el toogle.
	 *
	 * @param event
	 */
	public void onToggle(final ToggleEvent event) {
		if ("VISIBLE".equals(event.getVisibility().name())) {

			if ("panelPropietats".equals(event.getComponent().getId())) {
				setVisiblePropiedades(false);
				setVisibleScripts(true);
				setVisibleEstilos(true);
			}

			if ("panelScripts".equals(event.getComponent().getId())) {
				setVisiblePropiedades(true);
				setVisibleScripts(false);
				setVisibleEstilos(true);
			}

			if ("panelEstilos".equals(event.getComponent().getId())) {
				setVisiblePropiedades(true);
				setVisibleScripts(true);
				setVisibleEstilos(false);
			}
		}

	}

	private static final String ID_TEXT_IDENTIFICADOR = "dialogDisenyoFormulario:formDisenyoTextbox:inputIdComponente";

	private boolean sonValidosRequerimientos(final PaginaFormulario pPagina) {

		if (objetoFormularioEdit != null) {
			final LineaComponentesFormulario linea = pPagina.getLineaComponente(objetoFormularioEdit.getCodigo());

			if (objetoFormularioEdit instanceof ComponenteFormulario) {

				// Se envia directamente al JSF para que se vea directamente en el input (si se
				// quiere, pasar al dialogBase)
				// No puede ser el identificador vacío
				final String identificador = ((ComponenteFormulario) objetoFormularioEdit).getIdComponente();
				if (identificador == null || identificador.trim().isEmpty()) {
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
							UtilJSF.getLiteral("error.validacion.identificador.vacio"), true, ID_TEXT_IDENTIFICADOR);
					return false;
				}

				if (identificador.contains("IBAN")) {
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
							UtilJSF.getLiteral("error.validacion.identificador.contieneIban"), true, ID_TEXT_IDENTIFICADOR);
					return false;
				}

				if (this.isTipoSeccion() && !identificador.startsWith("SRE_" + this.getIdentificadorSeccion())) {
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
							UtilJSF.getLiteral("error.validacion.identificador.formatoSRE") + " " + "SRE_"
									+ this.getIdentificadorSeccion(),
							true, ID_TEXT_IDENTIFICADOR);
					return false;
				}

				if (!this.isTipoSeccion() && identificador.startsWith("SRE_")) {
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
							UtilJSF.getLiteral("error.validacion.identificador.formatoExclusivoParaSRE"), true,
							ID_TEXT_IDENTIFICADOR);
					return false;
				}

				// Tiene que formar un tipo de regExp
				if (!identificador.matches("^[a-zA-Z0-9_-]{1,50}")) {
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
							UtilJSF.getLiteral("error.validacion.identificador.formato"), true, ID_TEXT_IDENTIFICADOR);
					return false;
				}

				if (!linea.cabenComponentes((ComponenteFormulario) objetoFormularioEdit, false)) {
					if (objetoFormularioEdit instanceof ComponenteFormularioCampoTexto && TypeCampoTexto.IBAN
							.equals(((ComponenteFormularioCampoTexto) objetoFormularioEdit).getTipoCampoTexto())) {
						addMessageContext(TypeNivelGravedad.ERROR,
								UtilJSF.getLiteral("dialogDisenyoFormulario.iban.errorNumColumnas"));
					} else {
						addMessageContext(TypeNivelGravedad.WARNING,
								UtilJSF.getLiteral("warning.componente.sinespacio"), true);
					}
					return false;
				}

				if (objetoFormularioEdit instanceof ComponenteFormularioCampo) {
					final ComponenteFormularioCampo campo = (ComponenteFormularioCampo) objetoFormularioEdit;

					if (campo.isNoModificable() && !campo.isSoloLectura()) {
						addMessageContext(TypeNivelGravedad.WARNING,
								UtilJSF.getLiteral("warning.componente.soloLectura.unchecked"), true);
						return false;
					}
				}

				if (objetoFormularioEdit instanceof ComponenteFormularioCampoTexto) {
					final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;

					if ((TypeCampoTexto.NORMAL.equals(campo.getTipoCampoTexto())
							|| TypeCampoTexto.EMAIL.equals(campo.getTipoCampoTexto()))
							&& (campo.getNormalTamanyo() == null || campo.getNormalTamanyo() <= 0)) {
						addMessageContext(TypeNivelGravedad.WARNING,
								UtilJSF.getLiteral("warning.componente.normal.tamaño"), true);
						return false;
					}

					if (TypeCampoTexto.ID.equals(campo.getTipoCampoTexto()) && !campo.isIdentDni()
							&& !campo.isIdentNie() && !campo.isIdentNifOtros() && !campo.isIdentNif()
							&& !campo.isIdentNss()) {
						addMessageContext(TypeNivelGravedad.WARNING,
								UtilJSF.getLiteral("warning.componente.identificacion"), true);
						return false;
					}

					if (TypeCampoTexto.NUMERO.equals(campo.getTipoCampoTexto())
							&& (campo.getNumeroDigitosEnteros() == null || campo.getNumeroDigitosEnteros() <= 0)
							&& (campo.getNumeroDigitosDecimales() == null || campo.getNumeroDigitosDecimales() <= 0)) {
						addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.numero"),
								true);
						return false;
					}

					if (TypeCampoTexto.TELEFONO.equals(campo.getTipoCampoTexto()) && !campo.isTelefonoFijo()
							&& !campo.isTelefonoMovil()) {
						addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.telefono"),
								true);
						return false;
					}

					if (TypeCampoTexto.IBAN.equals(campo.getTipoCampoTexto()) && campo.getNumColumnas() <= 2) {
						addMessageContext(TypeNivelGravedad.ERROR,
								UtilJSF.getLiteral("dialogDisenyoFormulario.iban.errorNumColumnas"));
						return false;
					}

					if (TypeCampoTexto.EXPRESION.equals(campo.getTipoCampoTexto())
							&& StringUtils.isEmpty(campo.getExpresionRegular())) {
						addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.expresion"),
								true);
						return false;
					}

					if (TypeCampoTexto.EXPRESION.equals(campo.getTipoCampoTexto())) {
						// El tipo de expresion tiene que ser correcto
						try {
							Pattern.compile(campo.getExpresionRegular());
						} catch (final PatternSyntaxException e) {
							UtilJSF.loggearErrorFront("Error con la epxresion del campo " + campo.getIdComponente(), e);
							addMessageContext(TypeNivelGravedad.WARNING,
									UtilJSF.getLiteral("warning.componente.expresion.incorrecta"), true);
							return false;
						}
					}
				}

				if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
					final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
					// Restricción, si es de tipo selector, se debe marcar como
					if (campo.getTipoCampoIndexado() == TypeCampoIndexado.UNICA && !campo.isObligatorio()) {
						addMessageContext(TypeNivelGravedad.WARNING,
								UtilJSF.getLiteral("warning.componente.selectorunico.obligatorio"), true);
						return false;
					}

					// Restricción si es tipo dominio, tiene que tener asignado un dominio entre
					// otras restricciones
					if (TypeListaValores.DOMINIO.equals(campo.getTipoListaValores()) && campo.getCodDominio() == null) {
						addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.dominio"), true);
						return false;
					} else if (TypeListaValores.DOMINIO.equals(campo.getTipoListaValores())
							&& campo.getCodDominio() != null) {
						final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());
						if (!TypeDominio.LISTA_FIJA.equals(dominio.getTipo())) {
							if (StringUtils.isEmpty(campo.getCampoDominioCodigo())) {
								addMessageContext(TypeNivelGravedad.WARNING,
										UtilJSF.getLiteral("warning.dominio.codigo"), true);
								return false;
							} else if (StringUtils.isEmpty(campo.getCampoDominioDescripcion())) {
								addMessageContext(TypeNivelGravedad.WARNING,
										UtilJSF.getLiteral("warning.dominio.descripcion"), true);
								return false;
							} else if (!dominio.getParametros().isEmpty()
									&& campo.getListaParametrosDominio().isEmpty()) {
								addMessageContext(TypeNivelGravedad.WARNING,
										UtilJSF.getLiteral("warning.dominio.parametros"), true);
								return false;
							}
						}
					}

				}

				if (objetoFormularioEdit instanceof ComponenteFormularioCampoCheckbox) {
					final ComponenteFormularioCampoCheckbox campo = (ComponenteFormularioCampoCheckbox) objetoFormularioEdit;

					if (campo.getValorChecked() == null || campo.getValorChecked().isEmpty()) {
						UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
								UtilJSF.getLiteral("error.validacion.valorChecked.vacio"), true);
						return false;
					}

					if (campo.getValorNoChecked() == null || campo.getValorNoChecked().isEmpty()) {
						UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
								UtilJSF.getLiteral("error.validacion.valorNoChecked.vacio"), true);
						return false;
					}
				}

				final boolean isDuplicado = formIntService.isIdElementoFormularioDuplicated(Long.valueOf(id),
						objetoFormularioEdit.getCodigo(),
						((ComponenteFormulario) objetoFormularioEdit).getIdComponente());
				if (isDuplicado) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.identificador.duplicado"),
							true);
					return false;
				}
			}

		}

		return true;
	}

	/**
	 * Abre la página.
	 */
	public void editarPagina() {
		final Map<String, String> params = new HashMap<>();
		UtilJSF.getSessionBean().limpiaMochilaDatos();

		final PaginaFormulario paginaOriginal = formulario.getPaginas().get(paginaActual - 1);
		final PaginaFormulario pagina = PaginaFormulario.castSimple(paginaOriginal);
		UtilJSF.getSessionBean().getMochilaDatos().put(Constantes.CLAVE_MOCHILA_FORMULARIO, UtilJSON.toJSON(pagina));

		params.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), this.idFormulario);
		params.put(TypeParametroVentana.FORM_INTERNO_ACTUAL.toString(), this.id);
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);

		UtilJSF.openDialog(DialogPaginaFormulario.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 430, 190);
	}

	public void returnDialogoPagina(final SelectEvent event) {
		PaginaFormulario pagina = null;
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				Object[] obj = (Object[]) respuesta.getResult();
				pagina = (PaginaFormulario) obj[0];
				formIntService.guardarPagina(pagina);
				formulario.getPaginas().get(paginaActual - 1).setIdentificador(pagina.getIdentificador());
				formulario.getPaginas().get(paginaActual - 1).setCodigo(pagina.getCodigo());
				formulario.getPaginas().get(paginaActual - 1).setPaginaFinal(pagina.isPaginaFinal());
				formulario.getPaginas().get(paginaActual - 1).setScriptNavegacion(pagina.getScriptNavegacion());
				formulario.getPaginas().get(paginaActual - 1).setScriptNavegacion(pagina.getScriptValidacion());
				if ((boolean) obj[1]) {
					cambios = (boolean) obj[1];
				}
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}
	}

	/**
	 * Aplicar cambios.
	 **/
	public void aplicarCambios() {
		FacesContext.getCurrentInstance().isValidationFailed();
		if (objetoFormularioEdit != null && !desactivarAplicarCambios) {

			final PaginaFormulario pagina;
			if (objetoFormularioEdit instanceof ComponenteFormulario
					&& ((ComponenteFormulario) objetoFormularioEdit).isTipoSeccionReutilizable()) {
				pagina = formularioSeccion.get(((ComponenteFormulario) objetoFormularioEdit).getIdFormSeccion())
						.getPaginas().get(paginaActual - 1);
			} else {
				pagina = formulario.getPaginas().get(paginaActual - 1);
			}

			if (!sonValidosRequerimientos(pagina)) {
				return;
			}

			final ComponenteFormulario cfOriginal = pagina.getComponente(objetoFormularioEdit.getCodigo());

			// TODO SI ES UNA LINEA NO SE GUARDA
			if (cfOriginal != null) {

				try {
					BeanUtils.copyProperties(cfOriginal, objetoFormularioEdit);
				} catch (final Exception e) {
					UtilJSF.loggearErrorFront("Error no controlado ", e);
					throw new ErrorNoControladoException(e);
				}

				final ComponenteFormulario cfUpdate = (ComponenteFormulario) formIntService
						.updateComponenteFormulario(cfOriginal);

				// si es campo selector con dominio damos de alta el dominio si
				// en dominios
				// empleados no lo está ya
				if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
					final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
					if (this.isTipoTramite() && TypeListaValores.DOMINIO.equals(campo.getTipoListaValores())
							&& campo.getCodDominio() != null
							&& !dominioService.tieneTramiteVersion(campo.getCodDominio(), tramiteVersion.getCodigo())) {
						dominioService.addTramiteVersion(campo.getCodDominio(), tramiteVersion.getCodigo());
					}
				}

				try {
					BeanUtils.copyProperties(objetoFormularioEdit, cfUpdate);
					BeanUtils.copyProperties(cfOriginal, cfUpdate);
				} catch (final Exception e) {
					UtilJSF.loggearErrorFront("Error no controlado al copiar propiedades del componente", e);
					throw new ErrorNoControladoException(e);
				}
				if (this.cambios) {
					if (isTipoSeccion()) {
						seccionService.actualizarFechaSeccion(Long.parseLong(idSeccion),
								UtilJSF.getSessionBean().getUserName(),
								UtilJSF.getLiteral("info.modificado.formulario"));
					} else {
						tramiteService.actualizarFechaTramiteVersion(Long.parseLong(idTramiteVersion),
								UtilJSF.getSessionBean().getUserName(),
								UtilJSF.getLiteral("info.modificado.formulario"));
					}
				}
				this.cambios = false;
				addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.modificado.ok"));

				// Refresca iframe formulario
				// TODO Pasarle componente destino para posicionarse
				urlIframe = "FormRenderServlet?ts=" + System.currentTimeMillis();

			}
		}
	}

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event respuesta dialogo
	 */
	private void returnDialogoTraducciones(final SelectEvent event) {
		try {
			final DialogResult respuesta = (DialogResult) event.getObject();
			// Solo tiene sentido cambios para edicion
			if (!respuesta.isCanceled() && respuesta.getModoAcceso() == TypeModoAcceso.EDICION) {
				final Literal traduccionesMod = (Literal) respuesta.getResult();
				if (this.isCambioLiterales(traduccionesI, traduccionesMod)) {
					cambios = true;
				}
				if (traduccionesEdit == null) {
					traduccionesEdit = new Literal();
				}
				if (traduccionesMod == null) {
					traduccionesEdit = null;
				} else {
					BeanUtils.copyProperties(traduccionesEdit, traduccionesMod);
				}
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			// UtilJSF.loggearErrorFront("Error establecimiendo traducciones", e);
			throw new FrontException("Error estableciendo traducciones", e);
		}

	}

	/**
	 * Retorno dialogo de los botones de traducciones texto.
	 *
	 * @param event
	 */
	public void returnDialogoTraduccionesTexto(final SelectEvent event) {
		returnDialogoTraducciones(event);
		if (traduccionesEdit != null && ((ComponenteFormulario) objetoFormularioEdit).getTexto() == null) {
			((ComponenteFormulario) objetoFormularioEdit).setTexto(traduccionesEdit);
		}

	}

	/**
	 * Editar texto componente.
	 */
	public void editarTraduccionesTexto() {
		traduccionesEdit = ((ComponenteFormulario) objetoFormularioEdit).getTexto();
		traduccionesI = formIntService.getComponenteFormulario(objetoFormularioEdit.getCodigo()).getTexto();
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.valueOf(modoAcceso),
				((ComponenteFormulario) objetoFormularioEdit).getTexto(), idiomas, idiomas);
	}

	/**
	 * Editar texto componente.
	 */
	public void consultarTraduccionesTexto() {
		traduccionesEdit = ((ComponenteFormulario) objetoFormularioEdit).getTexto();
		traduccionesI = formIntService.getComponenteFormulario(objetoFormularioEdit.getCodigo()).getTexto();
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.CONSULTA,
				((ComponenteFormulario) objetoFormularioEdit).getTexto(), idiomas, idiomas);
	}

	/**
	 * Editar texto componente.
	 */
	public void editarTraduccionesTextoOpcional() {
		traduccionesEdit = ((ComponenteFormulario) objetoFormularioEdit).getTexto();
		traduccionesI = formIntService.getComponenteFormulario(objetoFormularioEdit.getCodigo()).getTexto();
		UtilTraducciones.openDialogTraduccionOpcional(TypeModoAcceso.valueOf(modoAcceso),
				((ComponenteFormulario) objetoFormularioEdit).getTexto(), idiomas, idiomas);
	}

	/**
	 * Consultar texto componente.
	 */
	public void consultarTraduccionesTextoOpcional() {
		traduccionesEdit = ((ComponenteFormulario) objetoFormularioEdit).getTexto();
		traduccionesI = formIntService.getComponenteFormulario(objetoFormularioEdit.getCodigo()).getTexto();
		UtilTraducciones.openDialogTraduccionOpcional(TypeModoAcceso.CONSULTA,
				((ComponenteFormulario) objetoFormularioEdit).getTexto(), idiomas, idiomas);
	}

	/**
	 * Editar texto componente.
	 */
	public void editarTraduccionesHTML() {
		traduccionesEdit = ((ComponenteFormulario) objetoFormularioEdit).getTexto();
		traduccionesI = formIntService.getComponenteFormulario(objetoFormularioEdit.getCodigo()).getTexto();
		UtilTraducciones.openDialogTraduccionHTML(TypeModoAcceso.valueOf(modoAcceso),
				((ComponenteFormulario) objetoFormularioEdit).getTexto(), idiomas, idiomas, false);
	}

	/**
	 * Editar texto componente.
	 */
	public void editarTraduccionesHTMLOpcional() {
		traduccionesEdit = ((ComponenteFormulario) objetoFormularioEdit).getTexto();
		traduccionesI = formIntService.getComponenteFormulario(objetoFormularioEdit.getCodigo()).getTexto();
		UtilTraducciones.openDialogTraduccionHTML(TypeModoAcceso.valueOf(modoAcceso),
				((ComponenteFormulario) objetoFormularioEdit).getTexto(), idiomas, idiomas, true);
	}

	/**
	 * Editar ayuda componente.
	 */
	public void editarTraduccionesAyuda() {
		traduccionesEdit = ((ComponenteFormulario) objetoFormularioEdit).getAyuda();
		traduccionesI = formIntService.getComponenteFormulario(objetoFormularioEdit.getCodigo()).getAyuda();
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.valueOf(modoAcceso), traduccionesEdit, idiomas, idiomas,
				true, codigoObjFormularioDestino, codigoObjFormularioDestino);
	}

	/**
	 * Editar ayuda componente.
	 */
	public void consultarTraduccionesAyuda() {
		traduccionesEdit = ((ComponenteFormulario) objetoFormularioEdit).getAyuda();
		traduccionesI = formIntService.getComponenteFormulario(objetoFormularioEdit.getCodigo()).getAyuda();
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.CONSULTA, traduccionesEdit, idiomas, idiomas, true,
				codigoObjFormularioDestino, codigoObjFormularioDestino);
	}

	/**
	 * Retorno dialog del botón de traducciones de ayuda
	 *
	 * @param event
	 */
	public void returnDialogoTraduccionesAyuda(final SelectEvent event) {
		returnDialogoTraducciones(event);
		((ComponenteFormulario) objetoFormularioEdit).setAyuda(traduccionesEdit);
	}

	/**
	 * Cierra la página
	 **/
	public void cancelar() {
		cancelar(true);
	}

	/**
	 * Cierra la página
	 *
	 * @param check Indica si tiene que comprobar si has modificado el componente
	 *              anterior.
	 */
	public void cancelar(final boolean check) {

		if (check && isModificadoSinGuardar(TypeAccionFormulario.CERRAR)) {
			return;
		}

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Comprueba si se ha modificado y se intenta realizar una acción sin guardar.
	 **/
	private boolean isModificadoSinGuardar(final TypeAccionFormulario accion) {
		accionPendiente = accion;
		final FacesContext context = FacesContext.getCurrentInstance();
		final Map<String, String> map = context.getExternalContext().getRequestParameterMap();

		String idComponente = null;

		if (StringUtils.isNotEmpty(map.get("id"))) {
			idComponente = map.get("id");
		}

		ObjetoFormulario ofOriginal = null;

		if (objetoFormularioEdit != null && objetoFormularioEdit instanceof ComponenteFormulario
				&& ((ComponenteFormulario) objetoFormularioEdit).isTipoSeccionReutilizable()) {
			ofOriginal = formularioSeccion.get(((ComponenteFormulario) objetoFormularioEdit).getIdFormSeccion())
					.getPaginas().get(0).getComponente(objetoFormularioEdit.getCodigo());
		} else {
			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				ofOriginal = formulario.getPaginas().get(paginaActual - 1).getLinea(objetoFormularioEdit.getCodigo());
			} else if (objetoFormularioEdit instanceof ComponenteFormulario) {
				ofOriginal = formulario.getPaginas().get(paginaActual - 1)
						.getComponente(objetoFormularioEdit.getCodigo());
			}
		}

		if (!UtilCoreApi.equalsModelApi(ofOriginal, objetoFormularioEdit) || this.cambios) {
			// Guardamos componente destino
			codigoObjFormularioDestino = idComponente;
			// Invocamos a boton para que dispare ventana de confirmacion
			this.cambios = false;
			final RequestContext contextReq = RequestContext.getCurrentInstance();
			contextReq.execute("PF('confirmationButton').jq.click();");
			UtilJSF.doValidationFailed();
			return true;
		}

		return false;
	}

	/**
	 * Abre dialogo para propiedades formulario.
	 **/
	public void abrirPropiedadesFormulario() {
		abrirPropiedadesFormulario(true);
	}

	/**
	 * Abre dialogo para propiedades formulario.
	 *
	 * @param check Indica si tiene que comprobar si has modificado el componente
	 *              anterior.
	 *
	 **/
	public void abrirPropiedadesFormulario(final boolean check) {

		if (check && isModificadoSinGuardar(TypeAccionFormulario.PROPIEDADES_FORMULARIO)) {
			return;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), id);
		params.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), this.idFormulario);
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), this.idTramiteVersion);

		Map<String, Object> mochilaDatos = null;

		// limpiamos mochila
		UtilJSF.getSessionBean().limpiaMochilaDatos();

		// metemos datos en la mochila
		mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO, idiomas);

		UtilJSF.openDialog(DialogPropiedadesFormulario.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 950,
				520);
	}

	/**
	 * Método copiar
	 */
	public void copy() {
		copy(true);
	}

	/**
	 * Método copiar
	 *
	 * @param check Indica si tiene que comprobar si has modificado el componente
	 *              anterior.
	 */
	public void copy(final boolean check) {
		if (check && isModificadoSinGuardar(TypeAccionFormulario.COPIAR)) {
			return;
		}

		if (objetoFormularioEdit != null && esSeleccionableCopyCut()) {
			copy = true;
			cut = false;
			calcularElementosACopiar();
		}
	}

	/**
	 * Prepara las variables sobre el objeto copy/paste:
	 * <ul>
	 * <li>El objeto a copiar</li>
	 * <li>Su id</li>
	 * <li>El tipo de objeto</li>
	 * <li>La linea donde se encuentra</li>
	 * <li>La página donde se encuentra</li>
	 * </ul>
	 */
	private void calcularElementosACopiar() {
		// Obtenemos los datos del objeto
		idObjetoCopy = objetoFormularioEdit.getCodigo();
		objetoCopy = (objetoFormularioEdit);
		if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
			tipoObjetoCopy = TypeObjetoFormulario.LINEA;
		} else {
			tipoObjetoCopy = ((ComponenteFormulario) objetoFormularioEdit).getTipo();
		}
		// Obtenemos la pagina y la linea actual
		idPaginaCopy = paginaActual;
		if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
			idLineaCopy = ((LineaComponentesFormulario) objetoFormularioEdit).getCodigo();
		} else {
			final ComponenteFormulario campo = (ComponenteFormulario) objetoFormularioEdit;
			final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
			idLineaCopy = (pagina.getLineaComponente(campo.getCodigo())).getCodigo();
		}
	}

	/**
	 * Método cortar
	 */
	public void cut() {
		cut(true);
	}

	/**
	 * Método cortar
	 *
	 * @param check Indica si tiene que comprobar si has modificado el componente
	 *              anterior.
	 */
	public void cut(final boolean check) {
		if (check && isModificadoSinGuardar(TypeAccionFormulario.CORTAR)) {
			return;
		}

		if (objetoFormularioEdit != null && esSeleccionableCopyCut()) {
			copy = false;
			cut = true;
			calcularElementosACopiar();
		}
	}

	/**
	 * Indica si se ha implementado la acción de copy/paste en el componente.
	 *
	 * @return
	 */
	private boolean esSeleccionableCopyCut() {
		boolean seleccionable;
		if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
			tipoObjetoCopy = TypeObjetoFormulario.LINEA;
		} else {
			tipoObjetoCopy = ((ComponenteFormulario) objetoFormularioEdit).getTipo();
		}
		if (tipoObjetoCopy == TypeObjetoFormulario.CAMPO_TEXTO || tipoObjetoCopy == TypeObjetoFormulario.CAMPO_OCULTO
				|| tipoObjetoCopy == TypeObjetoFormulario.CHECKBOX || tipoObjetoCopy == TypeObjetoFormulario.SELECTOR
				|| tipoObjetoCopy == TypeObjetoFormulario.SECCION || tipoObjetoCopy == TypeObjetoFormulario.ETIQUETA
				|| tipoObjetoCopy == TypeObjetoFormulario.LINEA || tipoObjetoCopy == TypeObjetoFormulario.CAPTCHA) {
			seleccionable = true;
		} else {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.copypaste.noimplementado"), true);
			tipoObjetoCopy = null;
			seleccionable = false;
		}

		if (seleccionable && this.isTipoSeccion()) {
			String identificador = null;

			if (objetoFormularioEdit instanceof ComponenteFormulario) {
				identificador = ((ComponenteFormulario) objetoFormularioEdit).getIdComponente();
			}

			if (identificador != null && !identificador.startsWith("SRE_" + this.getIdentificadorSeccion())) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("error.validacion.identificador.formatoSRE") + " " + "SRE_"
								+ this.getIdentificadorSeccion(),
						true, ID_TEXT_IDENTIFICADOR);
				seleccionable = false;
			}
		}
		return seleccionable;
	}

	/**
	 * Método paste.
	 */
	public void paste() {
		paste(true);
	}

	/**
	 * Método paste.
	 *
	 * @param check Indica si tiene que comprobar si has modificado el componente
	 *              anterior.
	 */
	public void paste(final boolean check) {

		if (check && isModificadoSinGuardar(TypeAccionFormulario.PEGAR)) {
			return;
		}

		if ((!copy && !cut) || objetoFormularioEdit == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.copypaste.primerocopy"), true);
			return;
		}

		if (cut && objetoFormularioEdit.getCodigo().compareTo(idObjetoCopy) == 0) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.copypaste.mismoelemento"), true);
			return;
		}

		if (tipoObjetoCopy == TypeObjetoFormulario.CAMPO_TEXTO || tipoObjetoCopy == TypeObjetoFormulario.CAMPO_OCULTO
				|| tipoObjetoCopy == TypeObjetoFormulario.CHECKBOX || tipoObjetoCopy == TypeObjetoFormulario.SELECTOR
				|| tipoObjetoCopy == TypeObjetoFormulario.CAPTCHA) {

			/**
			 * Si es de tipo texto, checkbox o selector, los pasos a seguir son:
			 * <ul>
			 * <li>Buscar la linea seleccionada.</li>
			 * <li>La posición, si es de tipo linea el elemento seleccionado se pondrá al
			 * final sino donde haya seleccionado.</li>
			 * <li>Si se selecciona posterior/anterior, cambiará la posición
			 * seleccionada.</li>
			 * <li>Se comprueba si cabe</li>
			 * <li>Si cabe, se realiza la acción</li>
			 * </ul>
			 */
			final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
			final Long idPagina = pagina.getCodigo();
			LineaComponentesFormulario linea = null;
			Integer ordenSeleccionado = null;
			Integer orden = null;
			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				linea = (LineaComponentesFormulario) objetoFormularioEdit;
			} else {
				final ComponenteFormulario campo = (ComponenteFormulario) objetoFormularioEdit;
				linea = pagina.getLineaComponente(campo.getCodigo());
				ordenSeleccionado = campo.getOrden();
			}

			if (!linea.cabenComponentes((ComponenteFormulario) objetoCopy, true)) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.sinespacio"));
				return;
			}

			if (TypeObjetoFormulario.CAMPO_OCULTO.equals(tipoObjetoCopy)) {
				orden = UtilDisenyo.ordenInsercionComponenteOculto(linea, ordenSeleccionado, posicionamiento);
			} else {
				orden = UtilDisenyo.ordenInsercionComponente(linea, ordenSeleccionado, posicionamiento);
			}
			if (orden == null) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.sinespacio"));
				return;
			} else {

				// ya está insertado el componente
				final ObjetoFormulario componente = formIntService.copyCutComponenteFormulario(idPagina,
						linea.getCodigo(), orden, posicionamiento, this.idObjetoCopy, cut);

				// borramos el elemento original
				if (cut) {
					ComponenteFormulario objetoBorrar = null;
					for (final ComponenteFormulario comp : formulario.getPaginas().get(idPaginaCopy - 1)
							.getLinea(idLineaCopy).getComponentes()) {
						if (comp.getCodigo().compareTo(idObjetoCopy) == 0) {
							objetoBorrar = comp;
							break;
						}
					}

					if (objetoBorrar != null) {
						formulario.getPaginas().get(idPaginaCopy - 1).getLinea(idLineaCopy).getComponentes()
								.remove(objetoBorrar);
					}
				}

				// actualizamos modelo
				pagina.getLineas().get(linea.getOrden() - 1).addComponente((ComponenteFormulario) componente);

				// lo seleccionamos
				seleccionaComponente(componente);
			}
		} else if (tipoObjetoCopy == TypeObjetoFormulario.LINEA || tipoObjetoCopy == TypeObjetoFormulario.SECCION
				|| tipoObjetoCopy == TypeObjetoFormulario.ETIQUETA) {
			/**
			 * Si es de tipo linea, secciono o etiqueta hay que tener en cuenta, que se
			 * copia toda la linea, los pasos a seguir son:
			 * <ul>
			 * <li>Si es tipo etiqueta o sección, se selecciona la linea</li>
			 * <li>Si la linea es vacía, no se copia.</li>
			 * <li>Buscamos la posición de la linea donde va a ir (caber seguro que
			 * cabe)</li>
			 * <li>Realizamos la acción de copiado sobre la linea original.</li>
			 * </ul>
			 *
			 **/

			final PaginaFormulario paginaCopy = formulario.getPaginas().get(idPaginaCopy - 1);

			final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
			final Long idPagina = pagina.getCodigo();
			LineaComponentesFormulario lineaDestino = null;
			LineaComponentesFormulario lineaOriginal = null;

			// Seleccionamos la linea original que vamos a copiar
			if (objetoCopy instanceof LineaComponentesFormulario) {
				lineaOriginal = (LineaComponentesFormulario) objetoCopy;
			} else {
				final ComponenteFormulario campo = (ComponenteFormulario) objetoCopy;
				lineaOriginal = paginaCopy.getLineaComponente(campo.getCodigo());
			}

			//
			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				lineaDestino = (LineaComponentesFormulario) objetoFormularioEdit;
			} else {
				final ComponenteFormulario campo = (ComponenteFormulario) objetoFormularioEdit;
				lineaDestino = pagina.getLineaComponente(campo.getCodigo());
			}
			final Integer orden = UtilDisenyo.ordenInsercionLinea(pagina, lineaDestino, posicionamiento);

			if (orden == null) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.sinespacio"));
				return;
			} else {

				final ObjetoFormulario componente = formIntService.copyCutLineaFormulario(idPagina,
						lineaOriginal.getCodigo(), orden, posicionamiento, cut);

				if (cut) {
					LineaComponentesFormulario objetoBorrar = null;
					for (final LineaComponentesFormulario comp : formulario.getPaginas().get(idPaginaCopy - 1)
							.getLineas()) {
						if (comp.getCodigo().compareTo(idLineaCopy) == 0) {
							objetoBorrar = comp;
							break;
						}
					}

					if (objetoBorrar != null) {
						formulario.getPaginas().get(idPaginaCopy - 1).getLineas().remove(objetoBorrar);
					}

				}

				// actualizamos modelo (si habia saltos en el orden de linea puede que el orden
				// inicial no sea el que toca)
				pagina.addLinea((LineaComponentesFormulario) componente);

				switch (tipoObjetoCopy) {
				case SECCION:
				case ETIQUETA:
					seleccionaComponente(((LineaComponentesFormulario) componente).getComponentes().get(0));
					break;
				case LINEA:
					seleccionaComponente(componente);
					break;
				default:
					break;
				}

			}
		} else {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.copypaste.noimplementado"), true);

			return;
		}

		idObjetoCopy = null;
		copy = false;
		cut = false;

		// Actualizar el formulario
		RequestContext.getCurrentInstance().update("dialogDisenyoFormulario:formulario-panel");
	}

	/**
	 * Mover página a la izquierda.
	 */
	public void moverPaginaIzq() {
		moverPaginaIzq(true);
	}

	/**
	 * Mover página a la izquierda
	 *
	 * @param check Indica si tiene que comprobar si has modificado el componente
	 *              anterior.
	 */
	public void moverPaginaIzq(final boolean check) {
		if (check && isModificadoSinGuardar(TypeAccionFormulario.MOVER_IZQ)) {
			return;
		}

		paginaActual--;
		if (paginaActual < 1) {
			paginaActual = 1;
		}

		limpiaSeleccion();

		actualizarInterfazMoverPagina();
	}

	/** Actualizar la interfaz tras moverse en una pagina. **/
	private void actualizarInterfazMoverPagina() {
		// Actualizar el formulario y los botones de mover
		RequestContext.getCurrentInstance().update("dialogDisenyoFormulario:formulario-panel");
		RequestContext.getCurrentInstance().update("dialogDisenyoFormulario:componente-panel");
		RequestContext.getCurrentInstance().update("dialogDisenyoFormulario:bh-mover");
		RequestContext.getCurrentInstance().update("dialogDisenyoFormulario:botonera");
		RequestContext.getCurrentInstance().update("dialogDisenyoFormulario:bh-herramientas");
	}

	/**
	 * Mover página a la derecha.
	 */
	public void moverPaginaDer() {
		moverPaginaDer(true);
	}

	/**
	 * Mover página a la derecha.
	 *
	 * @param check Indica si tiene que comprobar si has modificado el componente
	 *              anterior.
	 */
	public void moverPaginaDer(final boolean check) {
		if (check && isModificadoSinGuardar(TypeAccionFormulario.MOVER_DER)) {
			return;
		}

		paginaActual++;
		if (paginaActual > getNumeroPaginas()) {
			paginaActual = getNumeroPaginas();
		}
		limpiaSeleccion();

		actualizarInterfazMoverPagina();
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			String message = null;
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral("info.alta.ok");
			} else {
				message = UtilJSF.getLiteral("info.modificado.ok");
				limpiaSeleccion();
				// objetoFormularioEdit = null;
			}
			addMessageContext(TypeNivelGravedad.INFO, message);

			Object[] obj = (Object[]) respuesta.getResult();

			final DisenyoFormulario formularioNuevo = (DisenyoFormulario) obj[0];

			recuperarFormulario(id);

			if ((boolean) obj[1]) {
				cambios = true;
			}

			// formulario.setMostrarCabecera(formularioNuevo.isMostrarCabecera());
			// formulario.setTextoCabecera(formularioNuevo.getTextoCabecera());
			paginaActual = 1;
		} else {
			UtilJSF.doValidationFailed();
		}

	}

	/** Inserta un componente de tipo texto. **/
	public void insertaCampoTexto() {

		if (isModificadoSinGuardar(TypeAccionFormulario.ADD_TEXTO)) {
			return;
		}
		insertaCampo(TypeObjetoFormulario.CAMPO_TEXTO);
	}

	/** Inserta un componente de tipo campo oculto. **/
	public void insertaCampoOculto() {

		if (isModificadoSinGuardar(TypeAccionFormulario.ADD_OCULTO)) {
			return;
		}
		insertaCampo(TypeObjetoFormulario.CAMPO_OCULTO);
		mostrarOcultos = true;
	}

	/** Inserta un componente de tipo checkbox. **/
	public void insertaCheckBox() {

		if (isModificadoSinGuardar(TypeAccionFormulario.ADD_CHECKBOX)) {
			return;
		}
		insertaCampo(TypeObjetoFormulario.CHECKBOX);
	}

	/** Inserta un componente de tipo selector. **/
	public void insertaSelector() {

		if (isModificadoSinGuardar(TypeAccionFormulario.ADD_SELECTOR)) {
			return;
		}
		insertaCampo(TypeObjetoFormulario.SELECTOR);
	}

	/**
	 * Inserta campo.
	 *
	 * @param tipoCampo
	 */
	private void insertaCampo(final TypeObjetoFormulario tipoCampo) {
		final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
		final Long idPagina = pagina.getCodigo();
		LineaComponentesFormulario linea = null;
		Integer ordenSeleccionado = null;
		Integer orden = null;

		if (objetoFormularioEdit == null) {
			// mostrar aviso, hay que seleccionar una linea o componente
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.seleccionado"), true);
		} else {
			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				linea = (LineaComponentesFormulario) objetoFormularioEdit;
			} else {
				final ComponenteFormulario campo = (ComponenteFormulario) objetoFormularioEdit;
				linea = pagina.getLineaComponente(campo.getCodigo());
				ordenSeleccionado = campo.getOrden();
			}

			if (TypeObjetoFormulario.CAMPO_OCULTO.equals(tipoCampo)) {
				orden = UtilDisenyo.ordenInsercionComponenteOculto(linea, ordenSeleccionado, posicionamiento);
			} else {
				orden = UtilDisenyo.ordenInsercionComponente(linea, ordenSeleccionado, posicionamiento);
			}

			if (orden != null) {
				final ObjetoFormulario componente = formIntService.addComponenteFormulario(tipoCampo, idPagina,
						linea.getCodigo(), orden, posicionamiento, null, this.isTipoSeccion(), identificadorSeccion,
						this.idTramiteVersion);

				// actualizamos modelo
				pagina.getLineas().get(linea.getOrden() - 1).addComponente((ComponenteFormulario) componente);
				seleccionaComponente(componente);
				generaNumColumnas();
			} else {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.sinespacio"), true);
			}

		}
	}

	/** Inserta un componente de tipo seccion. **/
	public void insertaSeccion() {
		if (isTipoSeccion() && tieneYaSeccion()) {
			addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogDisenyoFormulario.tipoSeccion.errorSecciones"), true);
			return;
		}

		if (isModificadoSinGuardar(TypeAccionFormulario.ADD_SECCION)) {
			return;
		}
		insertaLineaComponenteBloque(TypeObjetoFormulario.SECCION, null);
	}

	/**
	 * Busca si tiene alguna seccion.
	 *
	 * @return
	 */
	private boolean tieneYaSeccion() {
		if (formulario != null && formulario.getPaginas() != null && !formulario.getPaginas().isEmpty()
				&& formulario.getPaginas().get(0).getLineas() != null
				&& !formulario.getPaginas().get(0).getLineas().isEmpty()) {
			for (LineaComponentesFormulario linea : formulario.getPaginas().get(0).getLineas()) {
				if (linea.getComponentes() != null) {
					for (ComponenteFormulario componente : linea.getComponentes()) {
						if (componente.getTipo() == TypeObjetoFormulario.SECCION) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/** Inserta un componente de tipo seccion. **/
	public void insertaCaptcha() {
		if (isModificadoSinGuardar(TypeAccionFormulario.ADD_CAPTCHA)) {
			return;
		}
		insertaLineaComponenteBloque(TypeObjetoFormulario.CAPTCHA, null);
	}

	public void insertaSeccionReutilizable() {

		if (isModificadoSinGuardar(TypeAccionFormulario.ADD_SECCION_REUTILIZABLE)) {
			return;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		UtilJSF.openDialog(DialogDisenyoSeccionReutilizable.class, TypeModoAcceso.valueOf(modoAcceso), params, true,
				700, 200);
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoSeccionReutilizable(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			SeccionReutilizable seccionR = (SeccionReutilizable) respuesta.getResult();
			insertaLineaComponenteBloque(TypeObjetoFormulario.SECCION_REUTILIZABLE, seccionR);
		}
	}

	/** Inserta un componente de tipo etiqueta. **/
	public void insertaEtiqueta() {
		if (isModificadoSinGuardar(TypeAccionFormulario.ADD_ETIQUETA)) {
			return;
		}
		insertaLineaComponenteBloque(TypeObjetoFormulario.ETIQUETA, null);
	}

	/** Inserta un componente de tipo linea. **/
	public void insertaLinea() {
		if (isModificadoSinGuardar(TypeAccionFormulario.ADD_LINEA)) {
			return;
		}
		insertaLineaComponenteBloque(TypeObjetoFormulario.LINEA, null);
	}

	/**
	 * Inserta linea componente
	 *
	 * @param tipoCampo
	 * @param objeto    Se pasa una componente de tipo objeto genérico (de momento
	 *                  es seccion reutilizable) por si se quiere pasar algún otro
	 *                  tipo de componente para ser añadido al utilizarse.
	 */
	private void insertaLineaComponenteBloque(final TypeObjetoFormulario tipoCampo, final Object objeto) {
		final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
		LineaComponentesFormulario lineaSeleccionada = null;
		Integer ordenLinea = null;
		Long idLineaSeleccionada = null;

		if (objetoFormularioEdit == null && !pagina.getLineas().isEmpty()) {
			// mostrar aviso, hay que seleccionar una linea o componente
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.seleccionado"), true);
		} else {
			// hay algo seleccionado
			if (objetoFormularioEdit != null) {

				if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
					lineaSeleccionada = (LineaComponentesFormulario) objetoFormularioEdit;
				} else {
					lineaSeleccionada = pagina.getLineaComponente(objetoFormularioEdit.getCodigo());
				}

				idLineaSeleccionada = lineaSeleccionada.getCodigo();

			}

			ordenLinea = UtilDisenyo.ordenInsercionLinea(pagina, lineaSeleccionada, posicionamiento);

			if (isTipoSeccion() && lineaSeleccionada != null && lineaSeleccionada.getOrden() <= 1) {
				// Nunca se puede poner en la primera línea.
				ordenLinea = 2;
			}

			final ObjetoFormulario componente = formIntService.addComponenteFormulario(tipoCampo, pagina.getCodigo(),
					idLineaSeleccionada, ordenLinea, posicionamiento, objeto, isTipoSeccion(), identificadorSeccion,
					this.idTramiteVersion);

			// actualizamos modelo (si habia saltos en el orden de linea puede que el orden
			// inicial no sea el que toca)
			pagina.addLinea((LineaComponentesFormulario) componente);

			switch (tipoCampo) {
			case SECCION:
			case ETIQUETA:
				seleccionaComponente(((LineaComponentesFormulario) componente).getComponentes().get(0));
				break;
			case LINEA:
				seleccionaComponente(componente);
				break;
			case CAPTCHA:
				seleccionaComponente(componente);
				break;
			case SECCION_REUTILIZABLE:
				seleccionaComponente(componente);
				break;
			default:
				break;
			}
		}
	}

	public void validarEliminarObjetoFormulario() {
		if (objetoFormularioEdit != null) {
			final RequestContext contextReq = RequestContext.getCurrentInstance();
			contextReq.execute("PF('confirmarEliminar').jq.click();");
		} else {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.seleccionado"));
		}
	}

	public void eliminarObjetoFormulario() {
		if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
			formIntService.removeLineaFormulario(objetoFormularioEdit.getCodigo());

			// actualizamos modelo
			final List<LineaComponentesFormulario> lineasPagina = formulario.getPaginas().get(paginaActual - 1)
					.getLineas();

			final LineaComponentesFormulario lineaSeleccionada = (LineaComponentesFormulario) objetoFormularioEdit;

			lineasPagina.removeIf(

					linea -> (linea.getCodigo().compareTo(lineaSeleccionada.getCodigo()) == 0));

			for (

					int i = 1; i <= lineasPagina.size(); i++) {
				lineasPagina.get(i - 1).setOrden(i);
			}

		} else if (objetoFormularioEdit instanceof ComponenteFormulario) {
			formIntService.removeComponenteFormulario(objetoFormularioEdit.getCodigo());

			// actualizamos modelo
			final ComponenteFormulario componenteSeleccionado = (ComponenteFormulario) objetoFormularioEdit;

			final LineaComponentesFormulario linea = formulario.getPaginas().get(paginaActual - 1)
					.getLineaComponente(componenteSeleccionado.getCodigo());

			linea.getComponentes().removeIf(

					componente -> (componente.getCodigo().compareTo(componenteSeleccionado.getCodigo()) == 0));

			for (

					int i = 1; i <= linea.getComponentes().size(); i++) {
				linea.getComponentes().get(i - 1).setOrden(i);
			}

			if (TypeObjetoFormulario.SECCION.equals(componenteSeleccionado.getTipo())
					|| TypeObjetoFormulario.ETIQUETA.equals(componenteSeleccionado.getTipo())) {
				objetoFormularioEdit = linea;
				eliminarObjetoFormulario();
			}
		}

		limpiaSeleccion();
	}

	public void cambiarCampoTextoMultilinea() {
		setCambios();
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		if (campo.isNormalMultilinea()) {
			final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
			final LineaComponentesFormulario linea = pagina.getLineaComponente(objetoFormularioEdit.getCodigo());
			final ComponenteFormularioCampoTexto campoNuevo = new ComponenteFormularioCampoTexto();
			try {
				BeanUtils.copyProperties(campoNuevo, campo);
			} catch (IllegalAccessException | InvocationTargetException e) {
				UtilJSF.loggearErrorFront("Error copiando propiedades", e);
			}
			campoNuevo.setNumColumnas(ConstantesDisenyo.TAM_MIN_COMPONENTE_MULTILINEA);

			if (linea.cabenComponentes(campoNuevo, false)) {
				campo.setNumColumnas(ConstantesDisenyo.TAM_MIN_COMPONENTE_MULTILINEA);
				campo.setNormalNumeroLineas(1);
			} else {
				campo.setNormalMultilinea(false);
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.sinespacio"));
			}

		} else {
			campo.setNormalNumeroLineas(null);
		}
	}

	private void generaNumColumnas() {
		if (objetoFormularioEdit == null) {
			setNcolumnas(null);
		} else {
			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				setNcolumnas(null);
			} else if (objetoFormularioEdit instanceof ComponenteFormulario) {

				final PaginaFormulario pagina;
				if (((ComponenteFormulario) objetoFormularioEdit).isTipoSeccionReutilizable()) {
					pagina = formularioSeccion.get(((ComponenteFormulario) objetoFormularioEdit).getIdFormSeccion())
							.getPaginas().get(0);
				} else {
					pagina = formulario.getPaginas().get(paginaActual - 1);
				}
				int inicio = 1;
				int fin = ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA
						- pagina.getLineaComponente(objetoFormularioEdit.getCodigo()).columnasComponentes()
						+ ((ComponenteFormulario) objetoFormularioEdit).getNumColumnas();

				if (objetoFormularioEdit instanceof ComponenteFormularioSeccion) {
					inicio = ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA;
					fin = ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA;
				} else if (objetoFormularioEdit instanceof ComponenteFormularioCampoCaptcha) {
					inicio = ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA;
					fin = ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA;
				} else if (objetoFormularioEdit instanceof ComponenteFormularioCampoTexto) {
					if (((ComponenteFormularioCampoTexto) objetoFormularioEdit).isNormalMultilinea()) {
						inicio = ConstantesDisenyo.TAM_MIN_COMPONENTE_MULTILINEA;
					} else {
						inicio = 1;
					}

				}

				setNcolumnas(generaListaNumColumnas(inicio, fin));
			}

		}
	}

	private List<Integer> generaListaNumColumnas(final int min, final int max) {
		setNcolumnas(new ArrayList<>());
		for (int i = min; i <= max; i++) {
			getNcolumnas().add(i);
		}
		return getNcolumnas();
	}

	private void limpiaSeleccion() {
		panelPropiedadesUrl = URL_FORMULARIO_VACIO;
		objetoFormularioEdit = null;
	}

	private void seleccionaComponente(final ObjetoFormulario pComponente) {
		objetoFormularioEdit = pComponente;

		String pagina = URL_FORMULARIO_VACIO;
		detalleComponenteUrl = null;
		if (objetoFormularioEdit != null) {
			if (objetoFormularioEdit instanceof ComponenteFormulario) {
				switch (((ComponenteFormulario) objetoFormularioEdit).getTipo()) {
				case CAMPO_TEXTO:
					pagina = "/secure/app/dialogDisenyoFormularioComponente.xhtml";
					break;
				case SELECTOR:
					pagina = "/secure/app/dialogDisenyoFormularioComponente.xhtml";
					break;
				case ETIQUETA:
					pagina = "/secure/app/dialogDisenyoFormularioEtiqueta.xhtml";
					break;
				case SECCION:
					pagina = "/secure/app/dialogDisenyoFormularioSeccion.xhtml";
					break;
				case CHECKBOX:
					pagina = "/secure/app/dialogDisenyoFormularioComponente.xhtml";
					break;
				case CAMPO_OCULTO:
					pagina = "/secure/app/dialogDisenyoFormularioComponenteOC.xhtml";
					break;
				default:
					break;
				}

				detalleComponenteUrl = "/secure/app/dialogDisenyoFormularioComponente"
						+ ((ComponenteFormulario) objetoFormularioEdit).getTipo() + ".xhtml";
			} else if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				pagina = "/secure/app/dialogDisenyoFormularioLinea.xhtml";
			}
		}

		panelPropiedadesUrl = pagina;
	}

	/**
	 * Mover componente a la izquierda
	 */
	public void moverObjetoIzq() {
		moverObjetoIzq(true);
	}

	/**
	 * Mover componente a la izquierda.
	 *
	 * @param check Indica si tiene que comprobar si has modificado el componente
	 *              anterior.
	 */
	public void moverObjetoIzq(final boolean check) {
		if (check && isModificadoSinGuardar(TypeAccionFormulario.MOVER_IZQ)) {
			return;
		}

		if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
			moverLinea(ConstantesDisenyo.POSICIONAMIENTO_PREVIO);
		} else {
			moverComponente(ConstantesDisenyo.POSICIONAMIENTO_PREVIO);
		}

		actualizarInterfazMoverComponente();

	}

	/** Actualizar interfaz mover componente. **/
	private void actualizarInterfazMoverComponente() {
		// Actualizar el formulario y los botones de mover
		RequestContext.getCurrentInstance().update("dialogDisenyoFormulario:formulario-panel");
		RequestContext.getCurrentInstance().update("dialogDisenyoFormulario:componente-panel");
		RequestContext.getCurrentInstance().update("dialogDisenyoFormulario:bh-mover");
	}

	/**
	 * Mover componente a la derecha.
	 */
	public void moverObjetoDer() {
		moverObjetoDer(true);
	}

	/**
	 * Mover componente a la derecha
	 *
	 * @param check Indica si tiene que comprobar si has modificado el componente
	 *              anterior.
	 */
	public void moverObjetoDer(final boolean check) {
		if (check && isModificadoSinGuardar(TypeAccionFormulario.MOVER_DER)) {
			return;
		}
		if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
			moverLinea(ConstantesDisenyo.POSICIONAMIENTO_POSTERIOR);
		} else {
			moverComponente(ConstantesDisenyo.POSICIONAMIENTO_POSTERIOR);
		}

		actualizarInterfazMoverComponente();
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		if (isTipoTramite()) {
			UtilJSF.openHelp("disenyoFormularioDialog");
		} else {
			UtilJSF.openHelp("disenyoFormularioDialogSeccion");
		}

	}

	/**
	 * Mueve la linea a una posición en concreta.
	 *
	 * @param pPosicion
	 */
	private void moverLinea(final String pPosicion) {
		final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
		Integer ordenSeleccionado = null;
		Integer orden = null;

		if (objetoFormularioEdit == null) {
			// mostrar aviso, hay que seleccionar una linea o componente
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.seleccionado"), true);
		} else {
			final LineaComponentesFormulario linea = (LineaComponentesFormulario) objetoFormularioEdit;
			ordenSeleccionado = linea.getOrden();

			orden = UtilDisenyo.ordenMoverLinea(pagina, ordenSeleccionado, pPosicion);

			if (orden != null) {
				final LineaComponentesFormulario lineaSeleccionado = pagina.getLineas().get(ordenSeleccionado - 1);
				final LineaComponentesFormulario lineaCambiable = pagina.getLineas().get(orden - 1);

				formIntService.updateOrdenLineaFormulario(lineaSeleccionado.getCodigo(), orden);
				formIntService.updateOrdenLineaFormulario(lineaCambiable.getCodigo(), ordenSeleccionado);

				// actualizamos modelo
				pagina.getLineas().remove(lineaSeleccionado);
				pagina.getLineas().add(orden - 1, lineaSeleccionado);
				lineaSeleccionado.setOrden(orden);
				lineaCambiable.setOrden(ordenSeleccionado);
				linea.setOrden(orden);
			}
		}
	}

	private void moverComponente(final String pPosicion) {
		final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
		LineaComponentesFormulario linea = null;
		Integer ordenSeleccionado = null;
		Integer orden = null;

		if (objetoFormularioEdit == null) {
			// mostrar aviso, hay que seleccionar una linea o componente
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.seleccionado"), true);
		} else {
			final ComponenteFormulario campo = (ComponenteFormulario) objetoFormularioEdit;
			linea = pagina.getLineaComponente(campo.getCodigo());
			ordenSeleccionado = campo.getOrden();

			orden = UtilDisenyo.ordenMoverComponente(linea, ordenSeleccionado, pPosicion);

			if (orden != null) {
				final ComponenteFormulario componenteSeleccionado = linea.getComponentes().get(ordenSeleccionado - 1);
				final ComponenteFormulario componenteCambiable = linea.getComponentes().get(orden - 1);

				formIntService.updateOrdenComponenteFormulario(componenteSeleccionado.getCodigo(), orden);
				formIntService.updateOrdenComponenteFormulario(componenteCambiable.getCodigo(), ordenSeleccionado);

				// actualizamos modelo
				linea.getComponentes().remove(componenteSeleccionado);
				linea.getComponentes().add(orden - 1, componenteSeleccionado);
				componenteSeleccionado.setOrden(orden);
				componenteCambiable.setOrden(ordenSeleccionado);
				campo.setOrden(orden);

			} else {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.sinespacio"), true);
			}

		}
	}

	public void abrirListaValoresFijaCIN() {
		if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
			// Muestra dialogo
			final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
			Map<String, Object> mochilaDatos = null;

			// limpiamos mochila
			UtilJSF.getSessionBean().limpiaMochilaDatos();

			// metemos datos en la mochila
			mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
			mochilaDatos.put(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO, idiomas);
			if (!campo.getListaValorListaFija().isEmpty()) {
				mochilaDatos.put(Constantes.CLAVE_MOCHILA_LVFCIN, campo.getListaValorListaFija().stream()
						.map(SerializationUtils::clone).collect(java.util.stream.Collectors.toList()));
			}

			UtilJSF.openDialog(DialogListaValoresFijaCIN.class, TypeModoAcceso.valueOf(modoAcceso), null, true, 800,
					350);
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	@SuppressWarnings("unchecked")
	public void returnDialogoListaValoresFijaCIN(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
				// Muestra dialogo
				final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;

				if (respuesta.getResult() == null) {
					campo.getListaValorListaFija().clear();
				} else {
					campo.setListaValorListaFija((List<ValorListaFija>) respuesta.getResult());
				}
			}
		} else {
			UtilJSF.doValidationFailed();
		}

	}

	public void abrirBusquedaDominio() {
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		if (this.isTipoTramite()) {
			params.put(TypeParametroVentana.PARAMETRO_DISENYO.toString(),
					TypeParametroVentana.PARAMETRO_DISENYO_TRAMITE.toString());
			params.put(TypeParametroVentana.TRAMITE.toString(), String.valueOf(tramiteVersion.getIdTramite()));
		} else {
			params.put(TypeParametroVentana.PARAMETRO_DISENYO.toString(),
					TypeParametroVentana.PARAMETRO_DISENYO_SECCION.toString());
			params.put(TypeParametroVentana.SECCION_REUTILIZABLE.toString(), String.valueOf(this.getIdSeccion()));
		}
		UtilJSF.openDialog(DialogBusquedaDominio.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 1200, 420);
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoBusquedaDominio(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
			campo.setCodDominio(((Dominio) respuesta.getResult()).getCodigo());

			campo.setCampoDominioCodigo(null);
			campo.setCampoDominioDescripcion(null);
			if (!campo.getListaParametrosDominio().isEmpty()) {
				campo.getListaParametrosDominio().clear();
			}

			if (this.isTipoTramite()
					&& !dominioService.tieneTramiteVersion(campo.getCodDominio(), tramiteVersion.getCodigo())) {
				addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.alta.dominio.empleado"));
			}
		} else {
			UtilJSF.doValidationFailed();
		}

	}

	public void consultaDominio() {
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;

		final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(campo.getCodDominio()));
		params.put(TypeParametroVentana.AMBITO.toString(), dominio.getAmbito().toString());
		if (TypeAmbito.AREA.equals(dominio.getAmbito())) {
			final Area area = tramiteService.getAreaTramite(tramiteVersion.getIdTramite());
			params.put(TypeParametroVentana.AREA.toString(), area.getCodigo().toString());
		}
		UtilJSF.openDialog(DialogDominio.class, TypeModoAcceso.CONSULTA, params, true, 770, 680);
	}

	public void pingDominio() {
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
		final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(campo.getCodDominio()));
		params.put(TypeParametroVentana.AMBITO.toString(), dominio.getAmbito().toString());
		UtilJSF.openDialog(DialogDominioPing.class, TypeModoAcceso.CONSULTA, params, true, 770, 600);
	}

	private String getIdComponente() {
		String idComponente = null;
		if (objetoFormularioEdit != null) {
			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				idComponente = "L" + objetoFormularioEdit.getCodigo();
			} else if (objetoFormularioEdit instanceof ComponenteFormulario) {
				idComponente = ((ComponenteFormulario) objetoFormularioEdit).getIdComponente();
			}
		}
		return idComponente;
	}

	public void abrirParametrosDominioCIN() {
		if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
			// Muestra dialogo
			final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
			Map<String, Object> mochilaDatos = null;

			// limpiamos mochila
			UtilJSF.getSessionBean().limpiaMochilaDatos();

			final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());

			mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
			mochilaDatos.put(Constantes.CLAVE_MOCHILA_PARAMDOM, dominio.getParametros().stream()
					.map(SerializationUtils::clone).collect(java.util.stream.Collectors.toList()));

			// metemos datos en la mochila
			if (!campo.getListaParametrosDominio().isEmpty()) {
				mochilaDatos.put(Constantes.CLAVE_MOCHILA_PRDCIN, campo.getListaParametrosDominio().stream()
						.map(SerializationUtils::clone).collect(java.util.stream.Collectors.toList()));
			}

			UtilJSF.openDialog(DialogParametrosDominioCIN.class, TypeModoAcceso.valueOf(modoAcceso), null, true, 800,
					350);
		}
	}

	@SuppressWarnings("unchecked")
	public void returnDialogoParametrosDominioCIN(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
				// Muestra dialogo
				final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;

				if (respuesta.getResult() == null) {
					campo.getListaParametrosDominio().clear();
				} else {
					campo.setListaParametrosDominio((List<ParametroDominio>) respuesta.getResult());
				}
			}
		} else {
			UtilJSF.doValidationFailed();
		}

	}

	/**
	 * Editar script
	 */
	public void consultarDialogScript(final String tipoScript, final Script script) {
		final Map<String, String> maps = new HashMap<>();
		maps.put(TypeParametroVentana.TIPO_SCRIPT_FORMULARIO.toString(),
				UtilJSON.toJSON(TypeScriptFormulario.fromString(tipoScript)));

		if (this.isTipoSeccion()) {
			/*
			 * maps.put(TypeParametroVentana.TIPO_SCRIPT_SECCION_REUTILIZABLE.toString(),
			 * UtilJSON.toJSON(valorSeleccionado.getTipoScript()));
			 */
			SeccionReutilizable seccion = seccionReutilizableService.getSeccionReutilizable(Long.valueOf(idSeccion));
			maps.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), seccion.getIdFormularioAsociado().toString());
			maps.put(TypeParametroVentana.SECCION_REUTILIZABLE.toString(), seccion.getIdentificador());
			maps.put(TypeParametroVentana.PARAMETRO_DISENYO.toString(),
					TypeParametroVentana.PARAMETRO_DISENYO_SECCION.toString());
		} else {
			maps.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), this.idFormulario);
			maps.put(TypeParametroVentana.FORM_INTERNO_ACTUAL.toString(), this.id);
			maps.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);
			maps.put(TypeParametroVentana.PARAMETRO_DISENYO.toString(),
					TypeParametroVentana.PARAMETRO_DISENYO_TRAMITE.toString());
		}
		maps.put(TypeParametroVentana.COMPONENTE.toString(), this.objetoFormularioEdit.getCodigo().toString());
		maps.put(TypeParametroVentana.COMPONENTE_NOMBRE.toString(), getIdComponente());

		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.CONSULTA, maps, true, 700);

	}

	/**
	 * Editar script
	 */
	public void editarDialogScript(final String tipoScript, final Script script) {
		final Map<String, String> maps = new HashMap<>();
		maps.put(TypeParametroVentana.TIPO_SCRIPT_FORMULARIO.toString(),
				UtilJSON.toJSON(TypeScriptFormulario.fromString(tipoScript)));

		if (this.isTipoSeccion()) {
			/*
			 * maps.put(TypeParametroVentana.TIPO_SCRIPT_SECCION_REUTILIZABLE.toString(),
			 * UtilJSON.toJSON(valorSeleccionado.getTipoScript()));
			 */
			SeccionReutilizable seccion = seccionReutilizableService.getSeccionReutilizable(Long.valueOf(idSeccion));
			maps.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), seccion.getIdFormularioAsociado().toString());
			maps.put(TypeParametroVentana.SECCION_REUTILIZABLE.toString(), seccion.getIdentificador());
			maps.put(TypeParametroVentana.PARAMETRO_DISENYO.toString(),
					TypeParametroVentana.PARAMETRO_DISENYO_SECCION.toString());
		} else {
			maps.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), this.idFormulario);
			maps.put(TypeParametroVentana.FORM_INTERNO_ACTUAL.toString(), this.id);
			maps.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);
			maps.put(TypeParametroVentana.PARAMETRO_DISENYO.toString(),
					TypeParametroVentana.PARAMETRO_DISENYO_TRAMITE.toString());
		}
		maps.put(TypeParametroVentana.COMPONENTE.toString(), this.objetoFormularioEdit.getCodigo().toString());
		maps.put(TypeParametroVentana.COMPONENTE_NOMBRE.toString(), getIdComponente());

		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.valueOf(modoAcceso), maps, true, 700);

	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptAutorrelleno(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				if (objetoFormularioEdit instanceof ComponenteFormularioCampo) {
					((ComponenteFormularioCampo) objetoFormularioEdit)
							.setScriptAutorrellenable((Script) respuesta.getResult());
					ComponenteFormularioCampo objI = (ComponenteFormularioCampo) formIntService
							.getComponenteFormulario(objetoFormularioEdit.getCodigo());
					if (objI != null && objetoFormularioEdit != null) {
						if (this.isCambioScripts(
								((ComponenteFormularioCampo) objetoFormularioEdit).getScriptAutorrellenable(),
								objI.getScriptAutorrellenable())) {
							cambios = true;
						}
					} else if (objI == null) {
						if (objetoFormularioEdit != null) {
							cambios = true;
						}
					} else {
						if (objI != null) {
							cambios = true;
						}
					}

				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptSoloLectura(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				if (objetoFormularioEdit instanceof ComponenteFormularioCampo) {
					((ComponenteFormularioCampo) objetoFormularioEdit)
							.setScriptSoloLectura((Script) respuesta.getResult());
					ComponenteFormularioCampo objI = (ComponenteFormularioCampo) formIntService
							.getComponenteFormulario(objetoFormularioEdit.getCodigo());
					if (objI != null && objetoFormularioEdit != null) {
						if (this.isCambioScripts(
								((ComponenteFormularioCampo) objetoFormularioEdit).getScriptSoloLectura(),
								objI.getScriptSoloLectura())) {
							cambios = true;
						}
					} else if (objI == null) {
						if (objetoFormularioEdit != null) {
							cambios = true;
						}
					} else {
						if (objI != null) {
							cambios = true;
						}
					}
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptValidacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				if (objetoFormularioEdit instanceof ComponenteFormularioCampo) {
					((ComponenteFormularioCampo) objetoFormularioEdit)
							.setScriptValidacion((Script) respuesta.getResult());
					ComponenteFormularioCampo objI = (ComponenteFormularioCampo) formIntService
							.getComponenteFormulario(objetoFormularioEdit.getCodigo());
					if (objI != null && objetoFormularioEdit != null) {
						if (this.isCambioScripts(
								((ComponenteFormularioCampo) objetoFormularioEdit).getScriptValidacion(),
								objI.getScriptValidacion())) {
							cambios = true;
						}
					} else if (objI == null) {
						if (objetoFormularioEdit != null) {
							cambios = true;
						}
					} else {
						if (objI != null) {
							cambios = true;
						}
					}
				}
				break;
			default:
				break;
			}
		}
	}

	/** Revierte los cambios al indicar que no se quiere guardar **/
	public void revertirEnFrontal() {
		objetoFormularioEdit = formIntService.getComponenteFormulario(objetoFormularioEdit.getCodigo());
	}

	/*
	 * Retorno dialogo del script de valores posibles.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptValores(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
					((ComponenteFormularioCampoSelector) objetoFormularioEdit)
							.setScriptValoresPosibles((Script) respuesta.getResult());
					ComponenteFormularioCampoSelector objI = (ComponenteFormularioCampoSelector) formIntService
							.getComponenteFormulario(objetoFormularioEdit.getCodigo());
					if (objI != null && objetoFormularioEdit != null) {
						if (this.isCambioScripts(
								((ComponenteFormularioCampoSelector) objetoFormularioEdit).getScriptValoresPosibles(),
								objI.getScriptValoresPosibles())) {
							cambios = true;
						}
					} else if (objI == null) {
						if (objetoFormularioEdit != null) {
							cambios = true;
						}
					} else {
						if (objI != null) {
							cambios = true;
						}
					}
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Abre el dialog de ver estructura.
	 */
	public void abrirDialogoEstructura() {
		abrirDialogoEstructura(true);
	}

	/**
	 * Abre el dialog de ver estructura.
	 *
	 * @param check Indica si tiene que comprobar si has modificado el componente
	 *              anterior.
	 */
	public void abrirDialogoEstructura(final boolean check) {

		if (check && isModificadoSinGuardar(TypeAccionFormulario.VER_ESTRUCTURA)) {
			return;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(formulario.getCodigo()));

		UtilJSF.openDialog(DialogEstructuraFormulario.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 450,
				460);
	}

	public void changedNoModificable() {
		setCambios();
		if (objetoFormularioEdit instanceof ComponenteFormularioCampo
				&& ((ComponenteFormularioCampo) objetoFormularioEdit).isNoModificable()) {
			((ComponenteFormularioCampo) objetoFormularioEdit).setSoloLectura(true);
		}
	}

	public boolean isListaDesplegable() {
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
		return TypeCampoIndexado.DESPLEGABLE.equals(campo.getTipoCampoIndexado());
	}

	public boolean isListaSeleccionUnica() {
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
		return TypeCampoIndexado.UNICA.equals(campo.getTipoCampoIndexado());
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {

		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	/**
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * @param errorCopiar the errorCopiar to set
	 */
	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewTramites.copiar"));
	}

	// -- Getters / Setters

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * Obtiene el valor de opcionUrl.
	 *
	 * @return el valor de opcionUrl
	 */
	public String getPanelPropiedadesUrl() {
		return panelPropiedadesUrl;
	}

	/**
	 * Set opcion url.
	 *
	 * @param opcion
	 */
	public void setPanelPropiedadesUrl(final String opcion) {
		this.panelPropiedadesUrl = opcion;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getUrlIframe() {
		final String idComponente = getIdComponente();

		return urlIframe + "&id=" + id + "&page=" + paginaActual + "&lang=" + idioma + "&showHidden="
				+ isMostrarOcultos() + (idComponente != null ? "&idComponente=" + idComponente : "");
	}

	public void setUrlIframe(final String urlIframe) {
		this.urlIframe = urlIframe;
	}

	public DisenyoFormulario getFormulario() {
		return formulario;
	}

	public void setFormulario(final DisenyoFormulario formulario) {
		this.formulario = formulario;
	}

	public ObjetoFormulario getObjetoFormularioEdit() {
		return objetoFormularioEdit;
	}

	public void setComponentEdit(final ObjetoFormulario objetoFormularioEdit) {
		this.objetoFormularioEdit = objetoFormularioEdit;
	}

	/**
	 * @return the visiblePropiedades
	 */
	public boolean isVisiblePropiedades() {
		return visiblePropiedades;
	}

	/**
	 * @param visiblePropiedades the visiblePropiedades to set
	 */
	public void setVisiblePropiedades(final boolean visiblePropiedades) {
		this.visiblePropiedades = visiblePropiedades;
	}

	/**
	 * @return the visibleScripts
	 */
	public boolean isVisibleScripts() {
		return visibleScripts;
	}

	/**
	 * @param visibleScripts the visibleScripts to set
	 */
	public void setVisibleScripts(final boolean visibleScripts) {
		this.visibleScripts = visibleScripts;
	}

	/**
	 * @return the visibleEstilos
	 */
	public boolean isVisibleEstilos() {
		return visibleEstilos;
	}

	/**
	 * @param visibleEstilos the visibleEstilos to set
	 */
	public void setVisibleEstilos(final boolean visibleEstilos) {
		this.visibleEstilos = visibleEstilos;
	}

	public String getDetalleComponenteUrl() {
		return detalleComponenteUrl;
	}

	public void setDetalleComponenteUrl(final String detalleComponenteUrl) {
		this.detalleComponenteUrl = detalleComponenteUrl;
	}

	public Literal getTraduccionesEdit() {
		return traduccionesEdit;
	}

	public void setTraduccionesEdit(final Literal traduccionesEdit) {
		this.traduccionesEdit = traduccionesEdit;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(final int paginaActual) {
		this.paginaActual = paginaActual;
	}

	public String getPosicionamiento() {
		return posicionamiento;
	}

	public void setPosicionamiento(final String posicionamiento) {
		this.posicionamiento = posicionamiento;
	}

	public List<Integer> getNcolumnas() {
		return ncolumnas;
	}

	public void setNcolumnas(final List<Integer> ncolumnas) {
		this.ncolumnas = ncolumnas;
	}

	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	public void setIdTramiteVersion(final String idTramite) {
		this.idTramiteVersion = idTramite;
	}

	public List<String> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the idFormulario
	 */
	public String getIdFormulario() {
		return idFormulario;
	}

	/**
	 * @param idFormulario the idFormulario to set
	 */
	public void setIdFormulario(final String idFormulario) {
		this.idFormulario = idFormulario;
	}

	/**
	 * Recupera formulario
	 *
	 * @param idForm
	 **/
	private void recuperarFormulario(final String idForm) {
		formulario = formIntService.getFormularioInternoCompleto(Long.parseLong(idForm));
	}

	/**
	 * Comprueba si es un componente de tipo campo
	 *
	 * @return
	 */
	public boolean isComponenteCampo() {
		return objetoFormularioEdit instanceof ComponenteFormularioCampo;
	}

	/**
	 * Comprueba si es un componente de tipo texto
	 *
	 * @return
	 */
	public boolean isCampoTextoNormal() {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		return TypeCampoTexto.NORMAL.equals(campo.getTipoCampoTexto());
	}

	/**
	 * Comprueba si es un componente de tipo texto
	 *
	 * @return
	 */
	public boolean isCampoTextoEmail() {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		return TypeCampoTexto.EMAIL.equals(campo.getTipoCampoTexto());
	}

	/**
	 * Comprueba si es un componente de tipo texto
	 *
	 * @return
	 */
	public boolean isCampoTextoIBAN() {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		return TypeCampoTexto.IBAN.equals(campo.getTipoCampoTexto());
	}

	/**
	 * Comprueba si es un tipo de campo texto de tipo expresion.
	 *
	 * @return
	 */
	public boolean isCampoExprRegular() {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		return TypeCampoTexto.EXPRESION.equals(campo.getTipoCampoTexto());
	}

	/**
	 * Comprueba si es un tipo de campo texto de tipo numero.
	 *
	 * @return
	 */
	public boolean isCampoNumero() {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		return TypeCampoTexto.NUMERO.equals(campo.getTipoCampoTexto());
	}

	/**
	 * Comprueba si es un tipo de campo texto de tipo identificacion.
	 *
	 * @return
	 */
	public boolean isCampoIdentificacion() {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		return TypeCampoTexto.ID.equals(campo.getTipoCampoTexto());
	}

	/**
	 * Comprueba si es un tipo de campo texto de tipo teléfono.
	 *
	 * @return
	 */
	public boolean isCampoTelefono() {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		return TypeCampoTexto.TELEFONO.equals(campo.getTipoCampoTexto());
	}

	/**
	 * Comprueba si es un tipo de campo texto de tipo Lista Variable Fija
	 *
	 * @return
	 */
	public boolean isLVFija() {
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
		return TypeListaValores.FIJA.equals(campo.getTipoListaValores());
	}

	/**
	 * Comprueba si es un tipo de campo texto de tipo Lista variable Dominio
	 *
	 * @return
	 */
	public boolean isLVDominio() {
		boolean resultado = false;

		if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
			final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
			resultado = TypeListaValores.DOMINIO.equals(campo.getTipoListaValores());
		}

		return resultado;
	}

	/**
	 * Comprueba si es un tipo de campo texto de tipo Lista Variable Script
	 *
	 * @return
	 */
	public boolean isLVScript() {
		boolean res = false;
		if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
			final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
			res = TypeListaValores.SCRIPT.equals(campo.getTipoListaValores());
		}
		return res;
	}

	/**
	 * Comprueba si es un tipo de campo texto de tipo Lista Fija
	 *
	 * @return
	 */
	public boolean isDominioLF() {
		boolean resultado = true;
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
		if (campo.getCodDominio() != null) {
			final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());
			resultado = TypeDominio.LISTA_FIJA.equals(dominio.getTipo());
		}

		return resultado;
	}

	/**
	 * Comprueba si tiene dominio asociado el componente.
	 *
	 * @return
	 */
	public boolean isDominioTieneParametros() {
		boolean resultado = false;
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
		if (campo.getCodDominio() != null) {
			final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());
			resultado = !dominio.getParametros().isEmpty();
		}

		return resultado;
	}

	/**
	 * Obtiene el identificador del dominio asociado al componente
	 *
	 * @return
	 */
	public String getIdentificadorDominio() {
		String resultado = null;

		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;

		if (campo.getCodDominio() != null) {
			final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());
			resultado = dominio.getIdentificador();
		}

		return resultado;
	}

	/**
	 * Obtiene el numero de paginas del formulario
	 *
	 * @return
	 */
	public int getNumeroPaginas() {
		return formulario.getPaginas().size();
	}

	/**
	 * Debe estar habilitado el botón de mover izquierda página.
	 *
	 * @return
	 */
	public boolean getHabilitadoBtnPagIzq() {
		return paginaActual > 1;
	}

	/**
	 * Debe estar habilitado el botón de mover derecha página.
	 *
	 * @return
	 */
	public boolean getHabilitadoBtnPagDer() {
		return paginaActual < getNumeroPaginas();
	}

	/**
	 * Debe estar habilitado el botón de mover componente a la izquierda
	 *
	 * @return
	 */
	public boolean getHabilitadoBtnObjIzq() {
		final int orden = getOrden();
		return orden != 0 ? orden > 1 : false;
		/*
		 * if (isTipoTramite()) { return orden != 0 ? orden > 1 : false; } else { //No
		 * puedes desplazarte a la izquierda de la seccion principal return orden > 2; }
		 */
	}

	/**
	 * Debe estar habilitado el botón de mover componente a la derecha.
	 *
	 * @return
	 */
	public boolean getHabilitadoBtnObjDer() {
		final int orden = getOrden();
		boolean resultado = false;
		if (objetoFormularioEdit != null) {
			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				resultado = orden != 0 ? orden < numObjetosLinea() : false;
			} else if (objetoFormularioEdit instanceof ComponenteFormulario) {
				resultado = orden != 0 ? orden < numObjetosLinea() : false;
			}
		}
		return resultado;
	}

	/**
	 * Obtiene el orden de un componente o linea.
	 *
	 * @return
	 */
	private int getOrden() {
		int orden = 0;
		if (objetoFormularioEdit != null) {
			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				orden = ((LineaComponentesFormulario) objetoFormularioEdit).getOrden();
			} else if (objetoFormularioEdit instanceof ComponenteFormulario) {
				orden = ((ComponenteFormulario) objetoFormularioEdit).getOrden();
			}
		}
		return orden;
	}

	/**
	 * Obtiene el numero de líneas de una página o de num de objetos de una linea.
	 *
	 * @return
	 */
	private int numObjetosLinea() {
		int numObj = 0;
		if (objetoFormularioEdit != null) {
			final PaginaFormulario pagina;

			if (objetoFormularioEdit instanceof ComponenteFormulario
					&& ((ComponenteFormulario) objetoFormularioEdit).isTipoSeccionReutilizable()) {
				pagina = formularioSeccion.get(((ComponenteFormulario) objetoFormularioEdit).getIdFormSeccion())
						.getPaginas().get(0);
			} else {
				pagina = formulario.getPaginas().get(paginaActual - 1);
			}

			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				numObj = pagina.getLineas().size();
			} else if (objetoFormularioEdit instanceof ComponenteFormulario) {
				numObj = pagina.getLineaComponente(objetoFormularioEdit.getCodigo()).getComponentes().size();
			}

		}
		return numObj;
	}

	public boolean isMostrarOcultos() {
		return mostrarOcultos;
	}

	public void setMostrarOcultos(final boolean mostrarOcultos) {
		this.mostrarOcultos = mostrarOcultos;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setCambios() {
		this.cambios = true;
	}

	public void setCambiosTipoTexto() {
		this.cambios = true;
		if (this.objetoFormularioEdit != null && this.objetoFormularioEdit instanceof ComponenteFormulario
				&& isCampoTextoIBAN()
				&& ((ComponenteFormularioCampoTexto) objetoFormularioEdit).getNumColumnas() <= 2) {
			((ComponenteFormularioCampoTexto) objetoFormularioEdit).setNumColumnas(3);
		}
	}

	public void setearCambios() {
		this.cambios = true;
		if (this.objetoFormularioEdit != null && this.objetoFormularioEdit instanceof ComponenteFormulario
				&& ((ComponenteFormulario) this.objetoFormularioEdit).getIdComponente() != null) {
			((ComponenteFormulario) this.objetoFormularioEdit).setIdComponente(
					((ComponenteFormulario) this.objetoFormularioEdit).getIdComponente().toUpperCase());
		}
	}

	/**
	 * @return the tipoDisenyo
	 */
	public String getTipoDisenyo() {
		return tipoDisenyo;
	}

	/**
	 * @param tipoDisenyo the tipoDisenyo to set
	 */
	public void setTipoDisenyo(String tipoDisenyo) {
		this.tipoDisenyo = tipoDisenyo;
	}

	/**
	 * @return the idSeccion
	 */
	public String getIdSeccion() {
		return idSeccion;
	}

	/**
	 * @param idSeccion the idSeccion to set
	 */
	public void setIdSeccion(String idSeccion) {
		this.idSeccion = idSeccion;
	}

	/**
	 * @return the identificadorSeccion
	 */
	public String getIdentificadorSeccion() {
		return identificadorSeccion;
	}

	/**
	 * @param identificadorSeccion the identificadorSeccion to set
	 */
	public void setIdentificadorSeccion(String identificadorSeccion) {
		this.identificadorSeccion = identificadorSeccion;
	}

	/**
	 * @return the desactivarAplicarCambios
	 */
	public boolean isDesactivarAplicarCambios() {
		return desactivarAplicarCambios;
	}

	/**
	 * @param desactivarAplicarCambios the desactivarAplicarCambios to set
	 */
	public void setDesactivarAplicarCambios(boolean desactivarAplicarCambios) {
		this.desactivarAplicarCambios = desactivarAplicarCambios;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isEsIframe() {
		return esIframe;
	}

	public void setEsIframe(boolean esIframe) {
		this.esIframe = esIframe;
	}

}
