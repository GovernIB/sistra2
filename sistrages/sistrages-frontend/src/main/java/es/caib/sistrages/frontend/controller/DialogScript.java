package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.extensions.event.ClipboardSuccessEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSeccionReutilizable;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.LiteralScript;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.ScriptSeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.DisenyoFormularioComponenteSimple;
import es.caib.sistrages.core.api.model.comun.DisenyoFormularioPaginaSimple;
import es.caib.sistrages.core.api.model.comun.DisenyoFormularioSimple;
import es.caib.sistrages.core.api.model.comun.ErrorValidacion;
import es.caib.sistrages.core.api.model.comun.TramiteSimple;
import es.caib.sistrages.core.api.model.comun.ValorIdentificadorCompuesto;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypePluginScript;
import es.caib.sistrages.core.api.model.types.TypeScript;
import es.caib.sistrages.core.api.model.types.TypeScriptFlujo;
import es.caib.sistrages.core.api.model.types.TypeScriptFormulario;
import es.caib.sistrages.core.api.model.types.TypeScriptSeccionReutilizable;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.ScriptService;
import es.caib.sistrages.core.api.service.SeccionReutilizableService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.core.api.util.UtilScripts;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.OpcionArbol;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogScript extends DialogControllerBase {

	private static final long serialVersionUID = 20111020L;

	/** Script service. */
	@Inject
	private ScriptService scriptService;

	/** Script service. */
	@Inject
	private SeccionReutilizableService srService;

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Formulario Interno service. */
	@Inject
	private FormularioInternoService formularioInternoService;

	/** seccionReutilizableService service. */
	@Inject
	private SeccionReutilizableService seccionReutilizableService;

	/** dominio service. */
	@Inject
	private DominioService dominioService;

	/** Id tramite version. **/
	private String idTramiteVersion;
	/** Formulario actual. **/
	private String idFormularioActual;
	/** Id. paso actual. **/
	private String idPasoActual;
	/** Id. componente. **/
	private String idComponente;
	/** Id. componente. **/
	private String nombreComponente;
	/** Id. pagina. **/
	private String idPagina;
	/** Id. formulario actual. **/
	private String idFormularioInternoActual;
	/** Dominios. */
	private List<Dominio> dominios = new ArrayList<>();
	/** Dominio seleccionado. **/
	private Dominio dominioSeleccionado;
	/** Data del script. **/
	private Script data;
	/** Tipo script. **/
	private String tipoScriptFlujo;
	/** Tipo script. **/
	private String tipoScriptFormulario;
	/** Tipo script. **/
	private String tipoScriptSeccionReutilizable;
	/** Tipo de typeScript. **/
	private TypeScript tipoScript;
	/** Identificador seccion **/
	private String identificadorSeccion;
	/** Tipo disenyo == SECCION O TRAMITE **/
	private String tipoDisenyo;
	/** booleano que indica si es tipo form y tiene sr **/
	private boolean tieneSR = false;

	/** Visible formulario. **/
	private boolean visibleFormularios = true;
	/** Visible herramientas. **/
	private boolean visibleHerramientas = false;
	/** Visible mensajes. **/
	private boolean visibleMensajes = true;
	/** Visible dominiosId. **/
	private boolean visibleDominios = true;
	/** Visible scripts sr **/
	private boolean visibleScriptSR = true;
	/** Permite editar. **/
	private boolean permiteEditar = false;
	/** Indica si hay que mostrar el editor html */
	private String literalHTML;

	/**
	 * Indicaría el texto que se añade al codeMirror pero de momento desactivado.
	 **/
	private String nuevoTexto;

	/** Indica si hay que mostrar la ayuda o no */
	private boolean mostrarLateralAyuda;

	/** Indica los plugins que hay. **/
	private List<TypePluginScript> plugins;

	/** URL del iframe del html de ayuda del plugin. **/
	private String urlIframe;

	/** Mensaje seleccionado. **/
	private LiteralScript mensajeSeleccionado;

	/** Arbol */
	private TreeNode treeFormularios;

	/** Nodo seleccionado. **/
	private TreeNode selectedNode;

	/** Text formulario. **/
	private String textFormulario;

	/** Idiomas. **/
	private List<String> idiomas;

	/** Lista secciones reut. **/
	private List<ComponenteFormularioCampoSeccionReutilizable> srL = new ArrayList<>();

	/**
	 * Constructor vacio.
	 */
	public DialogScript() {
		// Constructor vacio.
	}

	/**
	 * Init
	 */
	public void init() {

		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		final Object json = mochila.get(Constantes.CLAVE_MOCHILA_SCRIPT);
		if (json == null) {
			data = new Script();
		} else {
			data = (Script) UtilJSON.fromJSON(json.toString(), Script.class);
		}
		mostrarLateralAyuda = false;
		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_SCRIPT);

		if (TypeModoAcceso.valueOf(this.modoAcceso) == TypeModoAcceso.CONSULTA) {
			permiteEditar = false;
		} else {
			permiteEditar = true;
		}

		if (tipoScriptFormulario != null) {
			tipoScript = (TypeScriptFormulario) UtilJSON.fromJSON(tipoScriptFormulario, TypeScriptFormulario.class);
			if (permiteEditar) {
				setPlugins(UtilScripts.getPluginsScript((TypeScriptFormulario) tipoScript));
			}
		}
		if (tipoScriptFlujo != null) {
			tipoScript = (TypeScriptFlujo) UtilJSON.fromJSON(tipoScriptFlujo, TypeScriptFlujo.class);
			if (permiteEditar) {
				setPlugins(UtilScripts.getPluginsScript((TypeScriptFlujo) tipoScript));
			}
		}

		if (tipoScriptSeccionReutilizable != null) {
			tipoScript = (TypeScriptSeccionReutilizable) UtilJSON.fromJSON(tipoScriptSeccionReutilizable,
					TypeScriptSeccionReutilizable.class);
			if (permiteEditar) {
				setPlugins(UtilScripts.getPluginsScript((TypeScriptSeccionReutilizable) tipoScript));
			}
		}

		dominios = new ArrayList<>();
		if (permiteEditar) {

			if (idTramiteVersion != null) {
				dominios = tramiteService.getDominioSimpleByTramiteId(Long.valueOf(idTramiteVersion));
			} else if (tipoDisenyo != null
					&& tipoDisenyo.equals(TypeParametroVentana.PARAMETRO_DISENYO_SECCION.toString())) {
				dominios = seccionReutilizableService.getDominiosByIdentificadorSeccion(identificadorSeccion);
			}
		}

		/* inicializa arbol */
		treeFormularios = new DefaultTreeNode("Root", null);

		if (permiteEditar) {

			if (tipoScript instanceof TypeScriptFlujo) {

				final TramiteSimple tramiteSimple = tramiteService.getTramiteSimple(idTramiteVersion);
				idiomas = UtilTraducciones.getIdiomas(tramiteSimple.getIdiomasSoportados());
				final List<Long> idFormularios = UtilScripts.getFormulariosFlujo(tramiteSimple, idFormularioActual,
						idPasoActual, (TypeScriptFlujo) tipoScript, idFormularioInternoActual);

				for (final Long idFormularioInterno : idFormularios) {
					final String identificadorFormulario = formularioInternoService
							.getIdentificadorFormularioInterno(idFormularioInterno);
					final DisenyoFormularioSimple formulario = formularioInternoService.getFormularioInternoSimple(null,
							idFormularioInterno, null, null, obtenerCargarPaginasPosteriores(), false, null);

					cargarArbol(formulario, identificadorFormulario);
				}
			}

			if (tipoScript instanceof TypeScriptFormulario) {
				final DisenyoFormularioSimple disenyoFormulario;
				if (tipoDisenyo.equals(TypeParametroVentana.PARAMETRO_DISENYO_TRAMITE.toString())) {
					disenyoFormulario = this.formularioInternoService.getFormularioInternoSimple(
							Long.valueOf(idFormularioActual), null, idComponente, idPagina,
							obtenerCargarPaginasPosteriores(), false, null);
				} else {
					disenyoFormulario = this.formularioInternoService.getFormularioInternoSimple(null,
							Long.valueOf(idFormularioActual), idComponente, idPagina, obtenerCargarPaginasPosteriores(),
							true, identificadorSeccion);
				}

				if (idTramiteVersion != null) {
					idiomas = UtilTraducciones.getIdiomas(tramiteService.getIdiomasDisponibles(idTramiteVersion));
				} else {
					idiomas = new ArrayList<>();
					idiomas.add(TypeIdioma.CASTELLANO.toString());
					idiomas.add(TypeIdioma.CATALAN.toString());
				}

			}

			if (tipoScript.name().equals("SCRIPT_DATOS_INICIALES_FORMULARIO")) {
				DisenyoFormulario formulario = this.formularioInternoService
						.getFormularioInternoCompleto(Long.parseLong(idFormularioInternoActual));

				if (formulario != null) {

					// recorremos paginas
					for (final PaginaFormulario pagina : formulario.getPaginas()) {

						for (final LineaComponentesFormulario linea : pagina.getLineas()) {

							for (final ComponenteFormulario componente : linea.getComponentes()) {
								if (componente instanceof ComponenteFormularioCampoSeccionReutilizable) {
									SeccionReutilizable sr = srService.getSeccionReutilizable(
											((ComponenteFormularioCampoSeccionReutilizable) componente)
													.getIdSeccionReutilizable());
									this.tieneSR = true;
									componente.setIdComponente(sr.getIdentificador());
									srL.add((ComponenteFormularioCampoSeccionReutilizable) componente);
								}
							}
						}
					}

				}
			}

			if (tipoScript instanceof TypeScriptSeccionReutilizable) {

				final DisenyoFormularioSimple disenyoFormulario = this.formularioInternoService
						.getFormularioInternoSimple(null, Long.valueOf(idFormularioActual), idComponente, idPagina,
								obtenerCargarPaginasPosteriores(), true, identificadorSeccion);
				if (disenyoFormulario != null) {
					cargarArbol(disenyoFormulario, disenyoFormulario.getIdentificador());
				}
				idiomas = new ArrayList<>();
				idiomas.add(TypeIdioma.CATALAN.toString());
				idiomas.add(TypeIdioma.CASTELLANO.toString());

			}
		}

	}

	public ScriptSeccionReutilizable getScriptSR(ComponenteFormulario cf) {
		List<ScriptSeccionReutilizable> sSrL = srService.getScriptsByIdSeccionReutilizable(
				Long.valueOf(((ComponenteFormularioCampoSeccionReutilizable) cf).getIdSeccionReutilizable()));
		return sSrL.size() > 0 ? sSrL.get(0) : null;
	}

	public boolean scrSrNoVacio(ComponenteFormulario cf) {
		ScriptSeccionReutilizable script = getScriptSR(cf);
		return (script != null && script.getScript() != null && !script.getScript().getContenido().isEmpty());

	}

	/**
	 * Visualiza el script.
	 *
	 * @return
	 */
	public void verScript(ComponenteFormulario cf) {

		ScriptSeccionReutilizable valorSeleccionado = getScriptSR(cf);

		// Muestra dialogo
		if (valorSeleccionado != null) {
			final Map<String, String> maps = new HashMap<>();
			maps.put(TypeParametroVentana.TIPO_SCRIPT_SECCION_REUTILIZABLE.toString(),
					UtilJSON.toJSON(valorSeleccionado.getTipoScript()));
			SeccionReutilizable seccion = srService
					.getSeccionReutilizable(valorSeleccionado.getIdSeccionReutilizable());
			maps.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), seccion.getIdFormularioAsociado().toString());
			maps.put(TypeParametroVentana.SECCION_REUTILIZABLE.toString(), seccion.getIdentificador());
			maps.put(TypeParametroVentana.PARAMETRO_DISENYO.toString(),
					TypeParametroVentana.PARAMETRO_DISENYO_SECCION.toString());
			/*
			 * maps.put(TypeParametroVentana.FORM_INTERNO_ACTUAL.toString(), this.id);
			 * maps.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);
			 */
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(valorSeleccionado.getScript()));

			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.CONSULTA, maps, true, 700);
		}

	}

	public boolean isScriptSeccionReutilizable() {
		return tipoScript instanceof TypeScriptSeccionReutilizable;
	}

	/**
	 * Comprueba si puede cargar los script posteriores
	 *
	 * @return
	 */
	private boolean obtenerCargarPaginasPosteriores() {
		return tipoScript == TypeScriptFormulario.SCRIPT_NAVEGACION_PAGINA;
	}

	/**
	 * Carga el arbol.
	 *
	 * @param formulario
	 * @param identificadorFormulario
	 * @param cargarComponentesPaginasPosteriores Si está a true, las páginas que
	 *                                            sean posteriores a la actual, se
	 *                                            cargan
	 */
	private void cargarArbol(final DisenyoFormularioSimple formulario, final String identificadorFormulario) {

		final DefaultTreeNode nodoFormulario = new DefaultTreeNode(new OpcionArbol(identificadorFormulario));
		nodoFormulario.setSelectable(false);

		int i = 1;
		for (final DisenyoFormularioPaginaSimple pagina : formulario.getPaginas()) {
			String literal;
			literal = (pagina.getAlias() == null) ? UtilJSF.getLiteral("dialogDisenyoFormulario.pagina") + i
					: UtilJSF.getLiteral("dialogDisenyoFormulario.pagina") + i + " - " + pagina.getAlias();
			final DefaultTreeNode nodoPagina = new DefaultTreeNode(new OpcionArbol(literal));
			nodoPagina.setSelectable(pagina.isSeleccionable());
			for (final DisenyoFormularioComponenteSimple componente : pagina.getComponentes()) {
				final DefaultTreeNode nodoComponente = new DefaultTreeNode(
						new OpcionArbol(componente.getIdentificador(), componente.getTipo()));
				nodoPagina.getChildren().add(nodoComponente);
			}
			nodoFormulario.getChildren().add(nodoPagina);
			i++;
		}

		treeFormularios.getChildren().add(nodoFormulario);
	}

	/**
	 * Consultar dominio.
	 *
	 * @param idDominio
	 */
	public void consultarDominio(final Dominio dominio) {
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), dominio.getCodigo().toString());
		params.put(TypeParametroVentana.AMBITO.toString(), dominio.getAmbito().toString());
		UtilJSF.openDialog(DialogDominio.class, TypeModoAcceso.CONSULTA, params, true, 770, 670);
	}

	/**
	 * Ping.
	 */
	public void ping(final Long idDominio) {
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(idDominio));
		UtilJSF.openDialog(DialogDominioPing.class, TypeModoAcceso.CONSULTA, params, true, 770, 600);
	}

	/**
	 * Cuando se maximiza un panel.
	 *
	 * @param event
	 */
	public void onToggle(final ToggleEvent event) {
		if ("VISIBLE".equals(event.getVisibility().name())) {

			visibleFormularios = true;
			visibleHerramientas = true;
			visibleMensajes = true;
			visibleDominios = true;
			visibleScriptSR = true;

			if ("panelFormularios".equals(event.getComponent().getId())) {
				visibleFormularios = false;
			}

			if ("panelHerramientas".equals(event.getComponent().getId())) {
				visibleHerramientas = false;
			}

			if ("panelMensajes".equals(event.getComponent().getId())) {
				visibleMensajes = false;
			}

			if ("panelDominios".equals(event.getComponent().getId())) {
				visibleDominios = false;
			}

			if ("panelSR".equals(event.getComponent().getId())) {
				visibleScriptSR = false;
			}
		}

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		boolean comentariosWarning = UtilScripts.buscarComentariosScript(data.getContenido());
		if (comentariosWarning) {
			addMessageContext(TypeNivelGravedad.ERROR, "ERROR",
					UtilJSF.getLiteral("dialogScript.error.scriptcomentarios"));
			return;

		}
		if (isIdentificadorMensajesRepetido()) {
			addMessageContext(TypeNivelGravedad.ERROR, "ERROR",
					UtilJSF.getLiteral("dialogScript.error.identificadorDuplicado"));
			return;
		}

		if (data.getMensajes() != null && !data.getMensajes().isEmpty() && estaVacio()) {
			addMessageContext(TypeNivelGravedad.ERROR, "ERROR",
					UtilJSF.getLiteral("dialogScript.error.scriptvacioconmensajes"));
			return;
		}

		if (funcionGetValorIncorrecta()) {
			addMessageContext(TypeNivelGravedad.ERROR, "ERROR",
					UtilJSF.getLiteral("dialogScript.error.getValorErroneo"));
			return;
		}

		if (tipoDisenyo != null && tipoDisenyo.equals(TypeParametroVentana.PARAMETRO_DISENYO_TRAMITE.toString())) {
			if (!agregarDominioNoUtilizados()) {
				return;
			}

			if (!validoScript()) {
				return;
			}
		} else if (tipoDisenyo != null && tipoDisenyo.equals(TypeParametroVentana.PARAMETRO_DISENYO_SECCION)) {
			if (!revisarDominioNoarea()) {
				return;
			}

			if (!revisarDominioNoIdentificadorSimple()) {
				return;
			}

			if (!revisarDominioExiste()) {
				return;
			}
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		if (estaVacio()) {
			result.setResult(null);
		} else {
			result.setResult(this.data);
		}
		UtilJSF.closeDialog(result);
	}

	private boolean funcionGetValorIncorrecta() {
		String contenido = UtilScripts.extraerContenido(data.getContenido());
		if (contenido.contains("PLUGIN_DATOSFORMULARIO.getValor")) {

			Matcher matcher = Pattern.compile("PLUGIN_DATOSFORMULARIO.getValor\\(\\'(.*?)\\'").matcher(contenido);
			Integer totalCoincidencias = 0;
			while (matcher.find()) {
				totalCoincidencias++;
			}

			// Vamos a comprobar si alguna no tiene las comillas simples
			Matcher matcherComprobarIncorrectas = Pattern.compile("PLUGIN_DATOSFORMULARIO.getValor\\((.*?)\\)")
					.matcher(contenido);
			Integer totalCoincidenciasGenerico = 0;
			while (matcherComprobarIncorrectas.find()) {
				totalCoincidenciasGenerico++;
				// EN JDK9 o superiores, existe un macher.result.count, que es más eficiente
			}

			return totalCoincidenciasGenerico.compareTo(totalCoincidencias) != 0;
		} else {
			return false;
		}
	}

	/**
	 * Revisa que no se haya intentado utilizar un identificador simple
	 *
	 * @return
	 */
	private boolean revisarDominioNoIdentificadorSimple() {
		String contenido = UtilScripts.extraerContenido(data.getContenido());
		List<String> identificadoresDominio = new ArrayList<>();
		// Busca el patrón PLUGIN_DOMINIO.invocarDominio('{0}' siendo {0} el
		// identificador
		Matcher matcher = Pattern.compile("PLUGIN_DOMINIOS.invocarDominio\\(\\'("
				+ ValorIdentificadorCompuesto.SEPARACION_IDENTIFICADOR_COMPUESTO + "*?)\\'").matcher(contenido);
		Integer totalCoincidencias = 0;
		while (matcher.find()) {
			totalCoincidencias++;
			String identifDominio = matcher.group(1);
			if (!identificadoresDominio.contains(identifDominio)) {
				identificadoresDominio.add(identifDominio);
			}
		}

		if (!identificadoresDominio.isEmpty()) {
			// Revisamos si los identificadores están correctos
			for (String identificadorDominio : identificadoresDominio) {
				ValorIdentificadorCompuesto identificador = new ValorIdentificadorCompuesto(identificadorDominio);
				if (identificador.isError()) {
					String[] params = new String[1];
					params[0] = identificadorDominio;
					addMessageContext(TypeNivelGravedad.ERROR, "ERROR",
							UtilJSF.getLiteral("error.validacion.identificador.script", params));
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Revisa que no se haya intentado utilizar un dominio de tipo area
	 *
	 * @return
	 */
	private boolean revisarDominioExiste() {
		String contenido = UtilScripts.extraerContenido(data.getContenido());
		List<String> identificadoresDominio = new ArrayList<>();
		// Busca el patrón PLUGIN_DOMINIO.invocarDominio('{0}' siendo {0} el
		// identificador
		Matcher matcher = Pattern.compile("PLUGIN_DOMINIOS.invocarDominio\\(\\'("
				+ ValorIdentificadorCompuesto.SEPARACION_IDENTIFICADOR_COMPUESTO + "*?)\\'").matcher(contenido);
		Integer totalCoincidencias = 0;
		while (matcher.find()) {
			totalCoincidencias++;
			String identifDominio = matcher.group(1);
			if (!identificadoresDominio.contains(identifDominio)) {
				identificadoresDominio.add(identifDominio);
			}
		}

		if (!identificadoresDominio.isEmpty()) {
			// Revisamos si los identificadores están correctos
			for (String identificadorDominio : identificadoresDominio) {
				ValorIdentificadorCompuesto identificador = new ValorIdentificadorCompuesto(identificadorDominio);
				String idCompuesto = "";
				if (!identificador.getAmbito().equals(TypeAmbito.GLOBAL)) {
					if (identificador.getIdentificadorEntidad() != null
							&& !identificador.getIdentificadorEntidad().isEmpty()) {
						idCompuesto += identificador.getIdentificadorEntidad() + '.';
					}
					if (identificador.getIdentificadorArea() != null
							&& !identificador.getIdentificadorArea().isEmpty()) {
						idCompuesto += identificador.getIdentificadorArea() + '.';
					}
				} else {
					idCompuesto += "GLOBAL.";
				}
				if (dominioService
						.loadDominioByIdentificadorCompuesto(idCompuesto + identificador.getIdentificador()) == null) {
					String[] params = new String[1];
					params[0] = idCompuesto + identificador.getIdentificador();
					addMessageContext(TypeNivelGravedad.ERROR, "ERROR",
							UtilJSF.getLiteral("error.validacion.dominio.no.existe.script", params));
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Revisa que no se haya intentado utilizar un dominio de tipo area
	 *
	 * @return
	 */
	private boolean revisarDominioNoarea() {
		String contenido = UtilScripts.extraerContenido(data.getContenido());
		List<String> identificadoresDominio = new ArrayList<>();
		// Busca el patrón PLUGIN_DOMINIO.invocarDominio('{0}' siendo {0} el
		// identificador
		Matcher matcher = Pattern.compile("PLUGIN_DOMINIOS.invocarDominio\\(\\'("
				+ ValorIdentificadorCompuesto.SEPARACION_IDENTIFICADOR_COMPUESTO + "*?)\\'").matcher(contenido);
		Integer totalCoincidencias = 0;
		while (matcher.find()) {
			totalCoincidencias++;
			String identifDominio = matcher.group(1);
			if (!identificadoresDominio.contains(identifDominio)) {
				identificadoresDominio.add(identifDominio);
			}
		}

		// Vamos a comprobar si alguna no tiene las comillas simples
		Matcher matcherComprobarIncorrectas = Pattern.compile("PLUGIN_DOMINIOS.invocarDominio\\((.*?)\\)")
				.matcher(contenido);
		Integer totalCoincidenciasGenerico = 0;
		while (matcherComprobarIncorrectas.find()) {
			totalCoincidenciasGenerico++;
			// EN JDK9 o superiores, existe un macher.result.count, que es más eficiente
		}

		if (totalCoincidenciasGenerico.compareTo(totalCoincidencias) != 0) {
			addMessageContext(TypeNivelGravedad.ERROR, "ERROR",
					UtilJSF.getLiteral("dialogScript.error.invocarDominioErroneo"));
			return false;
		}

		if (!identificadoresDominio.isEmpty()) {
			// Revisamos si los identificadores están correctos
			for (String identificadorDominio : identificadoresDominio) {
				ValorIdentificadorCompuesto identificador = new ValorIdentificadorCompuesto(identificadorDominio);
				if (identificador.getAmbito() == TypeAmbito.AREA) {
					String[] params = new String[1];
					params[0] = identificador.getIdentificador();
					addMessageContext(TypeNivelGravedad.ERROR, "ERROR",
							UtilJSF.getLiteral("dialogScript.error.dominioAreaSRU", params));
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Método que se encarga de buscar los dominios que se utilizan y los agregará
	 * si no están
	 */
	private boolean agregarDominioNoUtilizados() {
		String contenido = UtilScripts.extraerContenido(data.getContenido());
		List<String> identificadoresDominio = new ArrayList<>();
		// Busca el patrón PLUGIN_DOMINIO.invocarDominio('{0}' siendo {0} el
		// identificador
		Matcher matcher = Pattern.compile("PLUGIN_DOMINIOS.invocarDominio\\(\\'("
				+ ValorIdentificadorCompuesto.SEPARACION_IDENTIFICADOR_COMPUESTO + "*?)\\'").matcher(contenido);
		Integer totalCoincidencias = 0;
		while (matcher.find()) {
			totalCoincidencias++;
			String identifDominio = matcher.group(1);
			if (!identificadoresDominio.contains(identifDominio)) {
				identificadoresDominio.add(identifDominio);
			}
		}

		// Vamos a comprobar si alguna no tiene las comillas simples
		Matcher matcherComprobarIncorrectas = Pattern.compile("PLUGIN_DOMINIOS.invocarDominio\\((.*?)\\)")
				.matcher(contenido);
		Integer totalCoincidenciasGenerico = 0;
		while (matcherComprobarIncorrectas.find()) {
			totalCoincidenciasGenerico++;
			// EN JDK9 o superiores, existe un macher.result.count, que es más eficiente
		}

		if (totalCoincidenciasGenerico.compareTo(totalCoincidencias) != 0) {
			addMessageContext(TypeNivelGravedad.ERROR, "ERROR",
					UtilJSF.getLiteral("dialogScript.error.invocarDominioErroneo"));
			return false;
		}

		if (!identificadoresDominio.isEmpty()) {
			final List<ValorIdentificadorCompuesto> dominiosDelScript = new ArrayList<>();
			TramiteVersion tramVersion = tramiteService.getTramiteVersion(Long.valueOf(this.idTramiteVersion));

			// Revisamos si los identificadores están correctos
			for (String identificadorDominio : identificadoresDominio) {

				ValorIdentificadorCompuesto identificador = new ValorIdentificadorCompuesto(identificadorDominio);
				dominiosDelScript.add(identificador);
				if (identificador.getAmbito() == TypeAmbito.ENTIDAD
						&& !UtilJSF.getIdentificadorEntidad().equals(identificador.getIdentificadorEntidad())) {
					String[] params = new String[1];
					params[0] = identificador.getIdentificador();
					addMessageContext(TypeNivelGravedad.ERROR, "ERROR",
							UtilJSF.getLiteral("dialogScript.error.identificador.otraEntidad", params));
					return false;
				}

				if (identificador.getAmbito() == TypeAmbito.AREA) {
					// Doble comprobacion
					// - C1: Primero si la entidad del area es de otra area, error
					if (!UtilJSF.getIdentificadorEntidad().equals(identificador.getIdentificadorEntidad())) {
						// Si la entidad no cuadra, es que ya está mal
						String[] params = new String[1];
						params[0] = identificador.getIdentificador();
						addMessageContext(TypeNivelGravedad.ERROR, "ERROR",
								UtilJSF.getLiteral("dialogScript.error.identificador.otraEntidad", params));
						return false;
					}

					// - C2: Comprobar el area coincide con el del tramite.
					if (!tramVersion.getIdentificadorArea().equals(identificador.getIdentificadorArea())) {

						String[] params = new String[1];
						params[0] = identificador.getIdentificador();
						addMessageContext(TypeNivelGravedad.ERROR, "ERROR",
								UtilJSF.getLiteral("dialogScript.error.identificador.otraArea", params));
						return false;
					}

				}
			}

			// Si no ha petado, actualizamos los valores
			tramiteService.actualizarDominios(tramVersion, dominiosDelScript);
			// Actualizamos los dominios
			dominios = tramiteService.getDominioSimpleByTramiteId(Long.valueOf(idTramiteVersion));

		}
		return true;
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void anyadirDominio() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), idTramiteVersion);
		TramiteVersion tramVersion = tramiteService.getTramiteVersion(Long.valueOf(this.idTramiteVersion));
		params.put(TypeParametroVentana.AREA.toString(), tramVersion.getIdArea().toString());
		UtilJSF.openDialog(DialogDefinicionVersionDominios.class, TypeModoAcceso.ALTA, params, true, 950, 550);
	}

	/**
	 * Cuando vuelve de anyadir dominio.
	 *
	 * @param event
	 */
	public void returnDialogoDominio(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			Dominio dominio = (Dominio) respuesta.getResult();
			dominios.add(dominio);
		}
	}

	public void cerrar() {
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		if (estaVacio()) {
			result.setResult(null);
		} else {
			result.setResult(this.data);
		}
		UtilJSF.closeDialog(result);
	}

	/**
	 * Para comprobar si algun identificador está repetido.
	 *
	 * @return
	 */
	private boolean isIdentificadorMensajesRepetido() {

		boolean repetido = false;
		if (data.getMensajes() != null && !data.getMensajes().isEmpty()) {
			// Map de identificadores para ver si hay alguno repetido
			final Map<String, Boolean> identificadores = new HashMap<>();
			for (final LiteralScript mensaje : data.getMensajes()) {
				if (identificadores.containsKey(mensaje.getIdentificador())) {
					repetido = true;
					break;
				}
				identificadores.put(mensaje.getIdentificador(), true);
			}
		}
		return repetido;
	}

	/**
	 * Comprueba si el contenido está vacío
	 *
	 * @return
	 */
	private boolean estaVacio() {
		boolean estaVacio;
		if (this.data == null || this.data.getContenido() == null) {
			estaVacio = true;
		} else {
			final String contenido = this.data.getContenido().replaceAll(" ", "").replaceAll("\n", "")
					.replaceAll("\t", "").trim();
			if (contenido.isEmpty()) {
				estaVacio = true;
			} else {
				estaVacio = false;
			}
		}
		return estaVacio;
	}

	/**
	 * Ayuda
	 */
	public void ayuda() {
		UtilJSF.openHelp("script");
	}

	/**
	 * Obtiene identificador SR
	 */
	public String identificadorSR(ComponenteFormulario componente) {
		SeccionReutilizable sr = srService.getSeccionReutilizable(
				((ComponenteFormularioCampoSeccionReutilizable) componente).getIdSeccionReutilizable());
		return sr.getIdentificador();
	}

	/**
	 * Aceptar.
	 */
	public void borrar() {

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(null);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Aceptar.
	 */
	public void cancelar() {

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Verifica si hay fila seleccionada de mensaje.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaMensaje() {
		boolean filaSeleccionada = true;
		if (this.mensajeSeleccionado == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Abre dialogo para editar mensaje.
	 */
	public void editarMensaje() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaMensaje())
			return;

		// Preparamos la mochila con el mensaje
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT_LITERAL, UtilJSON.toJSON(mensajeSeleccionado));

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(mensajeSeleccionado.getCodigo()));
		params.put(TypeParametroVentana.ID_SCRIPT.toString(), String.valueOf(this.data.getCodigo()));
		params.put(TypeParametroVentana.IDIOMAS.toString(), UtilJSON.toJSON(idiomas));
		params.put(TypeParametroVentana.LITERAL_HTML.toString(), literalHTML);

		UtilJSF.openDialog(DialogScriptLiteral.class, TypeModoAcceso.EDICION, params, true, 650, 200);
	}

	/**
	 * Return dialogo mensaje.
	 */
	public void returnDialogoMensaje(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final LiteralScript mensaje = (LiteralScript) respuesta.getResult();
			if (respuesta.getModoAcceso() == TypeModoAcceso.ALTA) {
				this.data.getMensajes().add(mensaje);
			}

			if (respuesta.getModoAcceso() == TypeModoAcceso.EDICION) {
				final int posicion = this.data.getMensajes().indexOf(this.mensajeSeleccionado);
				this.data.getMensajes().remove(posicion);
				this.data.getMensajes().add(posicion, mensaje);
			}

			// Indica si al guardar, se deben revisar los mensajes.
			this.data.setMensajesAlterado(true);
			this.mensajeSeleccionado = mensaje;
		}
	}

	/**
	 * revisa si el literal está a null y cambia el valor.
	 */
	public void checkLiteral() {
		if (literalHTML == null) {
			literalHTML = "";
		}
	}

	/**
	 * Abre dialogo de nueva mensaje.
	 */
	public void nuevoMensaje() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID_SCRIPT.toString(), String.valueOf(this.data.getCodigo()));
		params.put(TypeParametroVentana.IDIOMAS.toString(), UtilJSON.toJSON(idiomas));
		params.put(TypeParametroVentana.LITERAL_HTML.toString(), literalHTML);

		UtilJSF.openDialog(DialogScriptLiteral.class, TypeModoAcceso.ALTA, params, true, 650, 200);
	}

	/**
	 * Abre dialogo para eliminar mensaje.
	 */
	public void eliminarMensaje() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaMensaje()) {
			return;
		}

		this.data.getMensajes().remove(this.mensajeSeleccionado);
		// Indica si al guardar, se deben revisar los mensajes.
		this.data.setMensajesAlterado(true);
	}

	/**
	 * De momento desactivado. Añade un texto al codeMirror.
	 */
	public void anyadirTexto() {
		this.data.setContenido(this.data.getContenido() + "\n" + this.nuevoTexto);
	}

	/**
	 * Muestra el lateral de ayuda.
	 */
	public void mostrarAyuda(final TypePluginScript plugin) {
		this.urlIframe = "AyudaServlet?ts=" + System.currentTimeMillis() + "&lang=" + UtilJSF.getIdioma().toString()
				+ "&plugin=" + plugin.name();
		this.mostrarLateralAyuda = true;
	}

	/**
	 * Muestra mensaje de copy.
	 */
	public void mensajeCopy() {
		addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Marca como disabled si el nodo no está seleccionado.
	 *
	 * @return
	 */
	public boolean isDisabledBoton() {
		if (this.selectedNode != null) {
			textFormulario = this.selectedNode.getData().toString();
		}
		return this.selectedNode == null;
	}

	public void successListener(final ClipboardSuccessEvent successEvent) {
		final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
				"Component id: " + successEvent.getComponent().getId() + " Action: " + successEvent.getAction()
						+ " Text: " + successEvent.getText());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * Oculta el lateral de ayuda.
	 */
	public void ocultarAyuda() {
		this.mostrarLateralAyuda = false;
	}

	/**
	 * @return the visibleFormularios
	 */
	public boolean isVisibleFormularios() {
		return visibleFormularios;
	}

	/**
	 * @param visibleFormularios the visibleFormularios to set
	 */
	public void setVisibleFormularios(final boolean visibleFormularios) {
		this.visibleFormularios = visibleFormularios;
	}

	/**
	 * @return the visibleHerramientas
	 */
	public boolean isVisibleHerramientas() {
		return visibleHerramientas;
	}

	/**
	 * @param visibleHerramientas the visibleHerramientas to set
	 */
	public void setVisibleHerramientas(final boolean visibleHerramientas) {
		this.visibleHerramientas = visibleHerramientas;
	}

	/**
	 * @return the visibleMensajes
	 */
	public boolean isVisibleMensajes() {
		return visibleMensajes;
	}

	/**
	 * @param visibleMensajes the visibleMensajes to set
	 */
	public void setVisibleMensajes(final boolean visibleMensajes) {
		this.visibleMensajes = visibleMensajes;
	}

	/**
	 * @return the visibleDominios
	 */
	public boolean isVisibleDominios() {
		return visibleDominios;
	}

	/**
	 * @param visibleDominios the visibleDominios to set
	 */
	public void setVisibleDominios(final boolean visibleDominios) {
		this.visibleDominios = visibleDominios;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the data
	 */
	public Script getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final Script data) {
		this.data = data;
	}

	/**
	 * @return the nuevoTexto
	 */
	public String getNuevoTexto() {
		return nuevoTexto;
	}

	/**
	 * @param nuevoTexto the nuevoTexto to set
	 */
	public void setNuevoTexto(final String nuevoTexto) {
		this.nuevoTexto = nuevoTexto;
	}

	/**
	 * @return the mostrarLateralAyuda
	 */
	public boolean isMostrarLateralAyuda() {
		return mostrarLateralAyuda;
	}

	/**
	 * @param mostrarLateralAyuda the mostrarLateralAyuda to set
	 */
	public void setMostrarLateralAyuda(final boolean mostrarLateralAyuda) {
		this.mostrarLateralAyuda = mostrarLateralAyuda;
	}

	/**
	 * @return the plugins
	 */
	public List<TypePluginScript> getPlugins() {
		return plugins;
	}

	/**
	 * @param plugins the plugins to set
	 */
	public void setPlugins(final List<TypePluginScript> plugins) {
		this.plugins = plugins;
	}

	/**
	 * @return the urlIframe
	 */
	public String getUrlIframe() {
		return urlIframe;
	}

	/**
	 * @param urlIframe the urlIframe to set
	 */
	public void setUrlIframe(final String urlIframe) {
		this.urlIframe = urlIframe;
	}

	/**
	 * @return the scriptService
	 */
	public ScriptService getScriptService() {
		return scriptService;
	}

	/**
	 * @param scriptService the scriptService to set
	 */
	public void setScriptService(final ScriptService scriptService) {
		this.scriptService = scriptService;
	}

	/**
	 * @return the mensajeSeleccionado
	 */
	public LiteralScript getMensajeSeleccionado() {
		return mensajeSeleccionado;
	}

	/**
	 * @param mensajeSeleccionado the mensajeSeleccionado to set
	 */
	public void setMensajeSeleccionado(final LiteralScript mensajeSeleccionado) {
		this.mensajeSeleccionado = mensajeSeleccionado;
	}

	/**
	 * @return the permiteEditar
	 */
	public boolean isPermiteEditar() {
		return permiteEditar;
	}

	/**
	 * @param permiteEditar the permiteEditar to set
	 */
	public void setPermiteEditar(final boolean permiteEditar) {
		this.permiteEditar = permiteEditar;
	}

	/**
	 * @return the idTramiteVersion
	 */
	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	/**
	 * @param idTramiteVersion the idTramiteVersion to set
	 */
	public void setIdTramiteVersion(final String idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}

	public String getNombreComponente() {
		return nombreComponente;
	}

	public void setNombreComponente(String nombreComponente) {
		this.nombreComponente = nombreComponente;
	}

	/**
	 * @return the dominios
	 */
	public List<Dominio> getDominios() {
		return dominios;
	}

	/**
	 * @param dominios the dominios to set
	 */
	public void setDominios(final List<Dominio> dominios) {
		this.dominios = dominios;
	}

	/**
	 * @return the dominioSeleccionado
	 */
	public Dominio getDominioSeleccionado() {
		return dominioSeleccionado;
	}

	/**
	 * @param dominioSeleccionado the dominioSeleccionado to set
	 */
	public void setDominioSeleccionado(final Dominio dominioSeleccionado) {
		this.dominioSeleccionado = dominioSeleccionado;
	}

	/**
	 * @return the treeFormularios
	 */
	public TreeNode getTreeFormularios() {
		return treeFormularios;
	}

	/**
	 * @param treeFormularios the treeFormularios to set
	 */
	public void setTreeFormularios(final TreeNode treeFormularios) {
		this.treeFormularios = treeFormularios;
	}

	/**
	 * @return the tipoScript
	 */
	public TypeScript getTipoScript() {
		return tipoScript;
	}

	/**
	 * @param tipoScript the tipoScript to set
	 */
	public void setTipoScript(final TypeScript tipoScript) {
		this.tipoScript = tipoScript;
	}

	/**
	 * @return the tipoScriptFlujo
	 */
	public String getTipoScriptFlujo() {
		return tipoScriptFlujo;
	}

	/**
	 * @param tipoScriptFlujo the tipoScriptFlujo to set
	 */
	public void setTipoScriptFlujo(final String tipoScriptFlujo) {
		this.tipoScriptFlujo = tipoScriptFlujo;
	}

	/**
	 * @return the tipoScriptFormulario
	 */
	public String getTipoScriptFormulario() {
		return tipoScriptFormulario;
	}

	/**
	 * @param tipoScriptFormulario the tipoScriptFormulario to set
	 */
	public void setTipoScriptFormulario(final String tipoScriptFormulario) {
		this.tipoScriptFormulario = tipoScriptFormulario;
	}

	/**
	 * @return the selectedNode
	 */
	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	/**
	 * @param selectedNode the selectedNode to set
	 */
	public void setSelectedNode(final TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	/**
	 * @return the textFormulario
	 */
	public String getTextFormulario() {
		return textFormulario;
	}

	/**
	 * @param textFormulario the textFormulario to set
	 */
	public void setTextFormulario(final String textFormulario) {
		this.textFormulario = textFormulario;
	}

	/**
	 * @return the idPasoActual
	 */
	public String getIdPasoActual() {
		return idPasoActual;
	}

	/**
	 * @param idPasoActual the idPasoActual to set
	 */
	public void setIdPasoActual(final String idPasoActual) {
		this.idPasoActual = idPasoActual;
	}

	/**
	 * @return the idComponente
	 */
	public String getIdComponente() {
		return idComponente;
	}

	/**
	 * @param idComponente the idComponente to set
	 */
	public void setIdComponente(final String idComponente) {
		this.idComponente = idComponente;
	}

	/**
	 * @return the idPagina
	 */
	public String getIdPagina() {
		return idPagina;
	}

	/**
	 * @param idPagina the idPagina to set
	 */
	public void setIdPagina(final String idPagina) {
		this.idPagina = idPagina;
	}

	/**
	 * @return the srL
	 */
	public final List<ComponenteFormularioCampoSeccionReutilizable> getSrL() {
		return srL;
	}

	/**
	 * @param srL the srL to set
	 */
	public final void setSrL(List<ComponenteFormularioCampoSeccionReutilizable> srL) {
		this.srL = srL;
	}

	/**
	 * @return the idFormularioActual
	 */
	public String getIdFormularioActual() {
		return idFormularioActual;
	}

	/**
	 * @param idFormularioActual the idFormularioActual to set
	 */
	public void setIdFormularioActual(final String idFormularioActual) {
		this.idFormularioActual = idFormularioActual;
	}

	/**
	 * @return the idFormularioInternoActual
	 */
	public String getIdFormularioInternoActual() {
		return idFormularioInternoActual;
	}

	/**
	 * @param idFormularioInternoActual the idFormularioInternoActual to set
	 */
	public void setIdFormularioInternoActual(final String idFormularioInternoActual) {
		this.idFormularioInternoActual = idFormularioInternoActual;
	}

	/**
	 * @return the tipoScriptSeccionReutilizable
	 */
	public String getTipoScriptSeccionReutilizable() {
		return tipoScriptSeccionReutilizable;
	}

	/**
	 * @param tipoScriptSeccionReutilizable the tipoScriptSeccionReutilizable to set
	 */
	public void setTipoScriptSeccionReutilizable(String tipoScriptSeccionReutilizable) {
		this.tipoScriptSeccionReutilizable = tipoScriptSeccionReutilizable;
	}

	/**
	 * @return the idiomas
	 */
	public List<String> getIdiomas() {
		return idiomas;
	}

	/**
	 * @param idiomas the idiomas to set
	 */
	public void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

	/**
	 * @return the literalHTML
	 */
	public final String getLiteralHTML() {
		return literalHTML;
	}

	/**
	 * @param literalHTML the literalHTML to set
	 */
	public final void setLiteralHTML(String literalHTML) {
		this.literalHTML = literalHTML;
	}

	private boolean validoScript() {
		final List<ErrorValidacion> listaErrores = tramiteService.validarScript(data, dominios, idiomas,
				UtilJSF.getSessionBean().getLang());
		if (!listaErrores.isEmpty()) {
			final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

			mochilaDatos.put(Constantes.CLAVE_MOCHILA_ERRORESVALIDACION,
					listaErrores.stream().map(SerializationUtils::clone).collect(java.util.stream.Collectors.toList()));

			UtilJSF.openDialog(DialogErroresValidacion.class, TypeModoAcceso.CONSULTA, null, true, 1050, 520);
			return false;
		}

		return true;
	}

	/**
	 * @return the identificadorSeccion
	 */
	public String getIdentificadorSeccion() {
		return identificadorSeccion;
	}

	/**
	 * @return the visibleScriptSR
	 */
	public final boolean isVisibleScriptSR() {
		return visibleScriptSR;
	}

	/**
	 * @param visibleScriptSR the visibleScriptSR to set
	 */
	public final void setVisibleScriptSR(boolean visibleScriptSR) {
		this.visibleScriptSR = visibleScriptSR;
	}

	/**
	 * @param identificadorSeccion the identificadorSeccion to set
	 */
	public void setIdentificadorSeccion(String identificadorSeccion) {
		this.identificadorSeccion = identificadorSeccion;
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
	 * @return the tieneSR
	 */
	public final boolean isTieneSR() {
		return tieneSR;
	}

	/**
	 * @param tieneSR the tieneSR to set
	 */
	public final void setTieneSR(boolean tieneSR) {
		this.tieneSR = tieneSR;
	}

}
