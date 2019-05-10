package es.caib.sistrages.frontend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistra2.commons.plugins.registro.api.IRegistroPlugin;
import es.caib.sistra2.commons.plugins.registro.api.LibroOficina;
import es.caib.sistra2.commons.plugins.registro.api.OficinaRegistro;
import es.caib.sistra2.commons.plugins.registro.api.RegistroPluginException;
import es.caib.sistra2.commons.plugins.registro.api.TipoAsunto;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;
import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeExtension;
import es.caib.sistrages.core.api.model.types.TypePaso;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.model.types.TypeScriptFlujo;
import es.caib.sistrages.core.api.service.ComponenteService;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.ScriptService;
import es.caib.sistrages.core.api.service.SecurityService;
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

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de tr&aacute;mites.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersion extends ViewControllerBase {

	/** Service. */
	@Inject
	private EntidadService entidadService;

	/** Service. */
	@Inject
	private ScriptService scriptService;

	/** Security service. */
	@Inject
	private SecurityService securityService;

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Dominio service. */
	@Inject
	private DominioService dominioService;

	/** Componente service. */
	@Inject
	private ComponenteService componenteService;

	/** id. */
	private Long id;

	/** Area del tramite. **/
	private Area area;

	/** Tramite. **/
	private Tramite tramite;

	/** Dominios. */
	private List<Dominio> dominios = new ArrayList<>();

	/** Arbol */
	private TreeNode root;

	/** nodo seleccionado. */
	private TreeNode selectedNode;

	/** miga de pan */
	private MenuModel breadCrumb;

	/** raiz de la miga de pan */
	private MenuModel breadCrumbRoot;

	/** url de la opcion del arbol seleccionada */
	private String opcionUrl;

	/** tramite version. */
	private TramiteVersion tramiteVersion;

	/** Formulario seleccionado. **/
	private FormularioTramite formularioSeleccionado;

	/** Formulario alta. **/
	private FormularioTramite formularioAlta;

	/** Documento seleccionado. **/
	private Documento documentoSeleccionado;

	/** Documento akta. **/
	private Documento documentoAlta;

	/** Tasa seleccionado. **/
	private Tasa tasaSeleccionado;

	/** Tasa alta. **/
	private Tasa tasaAlta;

	/** Dato seleccionado en la lista. */
	private Dominio dominioSeleccionado;

	/** Literal error mover arriba. **/
	private static final String LITERAL_ERROR_MOVERARRIBA = "error.moverarriba";
	/** Literal error mover abajo. **/
	private static final String LITERAL_ERROR_MOVERABAJO = "error.moverabajo";

	/** Literal error fila no seleccionada. **/
	private static final String LITERAL_ERROR_NOSELECCIONADOFILA = "error.noseleccionadofila";

	/** Texto tipo de asunto. **/
	private String textoTipoAsunto;

	/** Texto libro registro. **/
	private String textoLibroRegistro;

	/** Texto oficina registro. **/
	private String textoOficinaRegistro;

	/** Permite editar. **/
	boolean permiteEditar = false;
	/** Permite consultar. **/
	boolean permiteConsultar = false;
	/** Permite bloquear. **/
	private boolean permiteBloquear = false;

	private boolean permiteRefrescar;

	/** Idiomas del tramite version. **/
	private List<String> idiomas;

	/** Entidad. **/
	private Entidad entidad;

	/**
	 * Crea una nueva instancia de view definicion version.
	 */
	public ViewDefinicionVersion() {
		super();
		if (UtilJSF.getEntorno().equals(TypeEntorno.DESARROLLO.toString())) {
			setPermiteRefrescar(false);
		} else {
			setPermiteRefrescar(true);
		}
	}

	/**
	 * Inicializacion.
	 */
	public void init() {

		/* titulo pantalla */
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		/** Cargamos la entidad. **/
		entidad = entidadService.loadEntidad(UtilJSF.getIdEntidad());

		// Control acceso
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByEntidad(UtilJSF.getIdEntidad());

		/** Inicializamos los datos. **/
		recuperarDatos();

		// Verficamos que puedes ver el area
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByArea(area.getCodigo());

		/** Capa extra de seguridad. */

		/* inicializa breadcrum y lo creamos */
		breadCrumbRoot = new DefaultMenuModel();

		DefaultMenuItem item = null;

		item = new DefaultMenuItem(area.getIdentificador());
		item.setUrl("/secure/app/viewTramites.xhtml?area=" + area.getCodigo());
		breadCrumbRoot.addElement(item);

		item = new DefaultMenuItem(tramite.getIdentificador());
		item.setUrl("/secure/app/viewTramites.xhtml?area=" + area.getCodigo() + "&tramite=" + tramite.getCodigo());
		breadCrumbRoot.addElement(item);

		item = new DefaultMenuItem("Version " + tramiteVersion.getNumeroVersion());
		item.setUrl("/secure/app/viewTramites.xhtml?area=" + area.getCodigo() + "&tramite=" + tramite.getCodigo()
				+ "&tramite_version=" + tramiteVersion.getCodigo());
		breadCrumbRoot.addElement(item);
		breadCrumbRoot.generateUniqueIds();
		breadCrumb = copyMenuModel(breadCrumbRoot);

		/* Inicializamos el arbol. */
		inicializarArbol();

		/** Marcamos las propiedades, que es el primer hijo. **/
		this.opcionUrl = UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionPropiedades");

		checkPermiteEditar();
		checkPermiteConsultar();
		checkPermiteBloquear();
	}

	/**
	 * Comprueba si se puede bloquear
	 */
	private void checkPermiteBloquear() {
		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			permiteBloquear = false;
			return;
		}

		if (this.tramiteVersion.getBloqueada()) {
			permiteBloquear = false;
			return;
		}

		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {

			final Area areaSeleccionada = tramiteService.getArea(tramite.getIdArea());
			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(areaSeleccionada.getCodigo());
			// Si no puedes editar, no puedes bloquear
			if (!permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)
					&& !permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {
				permiteBloquear = false;
				return;
			}
		}

		permiteBloquear = true;

	}

	/**
	 * Comprueba si se puede bloquear
	 *
	 * @throws IOException
	 */
	public void bloquear() throws IOException {

		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.entorno.noDesarrollo"));
			return;
		}

		if (this.tramiteVersion.getBloqueada()) {
			// Si está bloqueada, entonces no puedes bloquearlo.
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.bloquear.errorYaBloqueado"));
			return;
		}

		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {

			final Area areaSeleccionada = tramiteService.getArea(tramite.getIdArea());
			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(areaSeleccionada.getCodigo());
			// Si no puedes editar, no puedes bloquear
			if (!permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)
					&& !permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.permisos.insuficientes"));
				return;
			}
		}

		tramiteService.bloquearTramiteVersion(this.tramiteVersion.getCodigo(), UtilJSF.getSessionBean().getUserName());

		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI() + "?ID=" + this.id);
	}

	/**
	 * Check Permite editar.
	 */
	private void checkPermiteEditar() {

		// Sólo se puede editar en desarrollo y si está bloqueado
		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO) || !this.tramiteVersion.getBloqueada()) {
			permiteEditar = false;
			return;
		} else {
			// Sólo el usuario que lo haya bloqueado podrá editarlo
			permiteEditar = this.tramiteVersion.getDatosUsuarioBloqueo().equals(UtilJSF.getSessionBean().getUserName());
		}
	}

	/**
	 * Check Permite editar.
	 */
	public boolean permiteEditarControlAcceso() {

		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {

			return true;

		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {

			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(this.area.getCodigo());
			if (UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {

				return permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
						|| permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA);

			} else {

				return permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA);

			}

		} else {

			return false;
		}
	}

	public void rcDobleClickTree() {

		if (this.selectedNode != null) {
			final OpcionArbol opc = (OpcionArbol) this.selectedNode.getData();

			// Editar propiedades, paso, formulario, anexo o tasa
			if (permiteEditar()) {

				if (opc.getUrl() != null && opc.getUrl()
						.equals(UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionPropiedades"))) {
					editarPropiedades();
				}

				if (opc.getTramitePaso() != null) {
					editarPaso();
				}

				if (opc.getFormulario() != null) {
					editarFormularioSeleccionado();
				}

				if (opc.getDocumento() != null) {
					editarDocumentoTramite();
				}

				if (opc.getTasa() != null) {
					editarTasaTramite();
				}

			}

		}

	}

	/**
	 * Check Permite consultar.
	 */
	private void checkPermiteConsultar() {
		permiteConsultar = !permiteEditar;
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean permiteEditar() {

		return permiteEditar;
	}

	/**
	 * Obtiene el valor de permiteConsultar.
	 *
	 * @return el valor de permiteConsultar
	 */
	public boolean permiteConsultar() {

		return permiteConsultar;
	}

	/**
	 * Abre un di&aacute;logo para editar los datos.
	 */
	public void consultarDisenyo() {
		final Map<String, String> params = new HashMap<>();
		if (this.getFormularioTramiteSeleccionado() == null
				|| this.getFormularioTramiteSeleccionado().getIdFormularioInterno() == null) {
			throw new FrontException("No existe diseño formulario");
		}
		params.put(TypeParametroVentana.ID.toString(),
				this.getFormularioTramiteSeleccionado().getIdFormularioInterno().toString());

		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), String.valueOf(tramiteVersion.getCodigo()));
		params.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(),
				((OpcionArbol) this.selectedNode.getData()).getFormulario().getCodigo().toString());
		UtilJSF.openDialog(DialogDisenyoFormulario.class, TypeModoAcceso.CONSULTA, params, true, 1200, 680);
	}

	/**
	 * Abre un di&aacute;logo para previsualizar tramite.
	 */
	public void previsualizar() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(tramiteVersion.getCodigo()));

		UtilJSF.openDialog(DialogTramiteVersionPrevisualizar.class, TypeModoAcceso.CONSULTA, params, true, 750, 400);
	}

	/**
	 * Vuelve a recuperar los datos.
	 */
	private void recuperarDatos() {

		/* recuperamos los datos */
		tramiteVersion = tramiteService.getTramiteVersion(id);
		idiomas = UtilTraducciones.getIdiomas(tramiteVersion.getIdiomasSoportados());
		area = tramiteService.getAreaTramite(tramiteVersion.getIdTramite());
		tramite = tramiteService.getTramite(tramiteVersion.getIdTramite());

		/* obtenemos los pasos del trámite. */
		final List<TramitePaso> pasos = tramiteService.getTramitePasos(id);
		tramiteVersion.setListaPasos(pasos);
		dominios = tramiteService.getDominioSimpleByTramiteId(id);

		preparamosRegistro();
	}

	/**
	 * Método que se encarga de rellenar los datos de registro (libro, oficina y
	 * tipo), porque los literales hay que guardarlos.
	 */
	private void preparamosRegistro() {

		this.textoLibroRegistro = "";
		this.textoOficinaRegistro = "";
		this.textoTipoAsunto = "";

		if (tramiteVersion.getListaPasos() != null) {

			for (final TramitePaso paso : tramiteVersion.getListaPasos()) {

				if (paso instanceof TramitePasoRegistrar) {
					final TramitePasoRegistrar pasoRegistrar = (TramitePasoRegistrar) paso;
					final IRegistroPlugin iplugin = (IRegistroPlugin) componenteService
							.obtenerPluginEntidad(TypePlugin.REGISTRO, UtilJSF.getIdEntidad());
					try {

						// Buscamos la oficina de registro
						if (pasoRegistrar.getCodigoOficinaRegistro() != null
								&& !pasoRegistrar.getCodigoOficinaRegistro().isEmpty()) {

							final List<OficinaRegistro> oficinas = iplugin
									.obtenerOficinasRegistro(entidad.getCodigoDIR3(), TypeRegistro.REGISTRO_ENTRADA);

							for (final OficinaRegistro oficina : oficinas) {
								if (pasoRegistrar.getCodigoOficinaRegistro().equals(oficina.getCodigo())) {
									this.textoOficinaRegistro = oficina.getNombre();

									// Buscamos el libro
									if (pasoRegistrar.getCodigoLibroRegistro() != null
											&& !pasoRegistrar.getCodigoLibroRegistro().isEmpty()) {
										final List<LibroOficina> libros = iplugin.obtenerLibrosOficina(
												entidad.getCodigoDIR3(), oficina.getCodigo(),
												TypeRegistro.REGISTRO_ENTRADA);
										for (final LibroOficina libro : libros) {
											if (libro.getCodigo().equals(pasoRegistrar.getCodigoLibroRegistro())) {
												this.textoLibroRegistro = libro.getNombre();
												break;
											}
										}
									}
									break;
								}
							}

						}

						// Buscamos el tipo
						if (pasoRegistrar.getCodigoTipoAsunto() != null
								&& !pasoRegistrar.getCodigoTipoAsunto().isEmpty()) {
							final List<TipoAsunto> tipos = iplugin.obtenerTiposAsunto(entidad.getCodigoDIR3());
							for (final TipoAsunto tipo : tipos) {
								if (pasoRegistrar.getCodigoTipoAsunto().equals(tipo.getCodigo())) {
									this.textoTipoAsunto = tipo.getNombre();
									break;
								}
							}

						}

					} catch (final RegistroPluginException e) {
						UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
								UtilJSF.getLiteral("dialogDefinicionVersionRegistrarTramite.registro.error"));
					}

					break;

				}
			}

		}
	}

	/**
	 * Consultar Script.
	 *
	 * @param iScript
	 */
	public void consultarScript(final String tipoScript, final Long idScript) {

		// Muestra dialogo
		final Map<String, String> map = new HashMap<>();
		map.put(TypeParametroVentana.TIPO_SCRIPT_FLUJO.toString(),
				UtilJSON.toJSON(TypeScriptFlujo.fromString(tipoScript)));
		if (idScript != null) {

			final Script script = this.scriptService.getScript(idScript);
			UtilJSF.getSessionBean().limpiaMochilaDatos();
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));

		}

		map.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.CONSULTA.toString());
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.CONSULTA, map, true, 700);

	}

	/**
	 * Consultar traduccion HTML.
	 */
	public void consultarTraduccionHTML(final Literal literal) {
		UtilTraducciones.openDialogTraduccionHTML(TypeModoAcceso.CONSULTA, literal, idiomas, idiomas, false);
	}

	/**
	 * Consultar traduccion HTML.
	 */
	public void consultarTraduccion(final Literal literal) {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.CONSULTA, literal, tramiteVersion);
	}

	/**
	 * Edita el paso seleccionado. Dependiendo del tipo de instancia, abre un tipo o
	 * no (no todos los pasos necesitan editarse).
	 */
	public void editarPaso() {
		if (this.selectedNode != null) {

			// Pasamos a la mochila los dominios y los ids de formularios
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_TRAMITE,
					UtilJSON.toJSON(UtilScripts.getTramiteSimple(this.tramiteVersion)));

			final TramitePaso paso = ((OpcionArbol) this.selectedNode.getData()).getTramitePaso();
			final Map<String, String> map = new HashMap<>();
			map.put(TypeParametroVentana.ID.toString(), paso.getCodigo().toString());
			map.put(TypeParametroVentana.TRAMITEVERSION.toString(), tramiteVersion.getCodigo().toString());

			if (paso instanceof TramitePasoDebeSaber) {

				UtilJSF.openDialog(DialogDefinicionVersionDebeSaber.class, TypeModoAcceso.EDICION, map, true, 950, 500);

			} else if (paso instanceof TramitePasoRegistrar) {

				UtilJSF.openDialog(DialogDefinicionVersionRegistrarTramite.class, TypeModoAcceso.EDICION, map, true,
						950, 450);

			}

		}
	}

	// ------- VIEW DE EDITAR PROPIEDADES DE TRAMITE VERSION

	/**
	 * Abre la ventana para editar propiedades.
	 */
	public void editarPropiedades() {

		// Pasamos a la mochila los dominios y los ids de formularios
		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_TRAMITE,
				UtilJSON.toJSON(UtilScripts.getTramiteSimple(this.tramiteVersion)));

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), id.toString());
		UtilJSF.openDialog(DialogDefinicionVersionPropiedades.class, TypeModoAcceso.EDICION, params, true, 950, 470);
	}

	/**
	 * Cuando vuelve de editar el control de acceso.
	 *
	 * @param event
	 */
	public void returnDialogoRefrescarTramite(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {

			recuperarDatos();

			// Refrescamos el arbol
			inicializarArbol();

			if (TypeModoAcceso.ALTA.equals(respuesta.getModoAcceso())) {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral(Constantes.LITERAL_INFO_ALTA_OK));
			} else if (TypeModoAcceso.EDICION.equals(respuesta.getModoAcceso())) {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
						UtilJSF.getLiteral(Constantes.LITERAL_INFO_MODIFICADO_OK));
			}
		}

	}

	/**
	 * Cuando vuelve de editar el control de acceso.
	 *
	 * @param event
	 */
	public void returnDialogoRefrescarTramiteRegistro(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {

			recuperarDatos();

			// Refrescamos el arbol
			inicializarArbol();

			final Entidad entidad = this.entidadService.loadEntidad(UtilJSF.getIdEntidad());
			if (!entidad.isPermiteSubsanarRegistrar()
					&& ((TramitePasoRegistrar) respuesta.getResult()).isPermiteSubsanar()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("viewDefinicionVersionRegistrarTramite.warning.entidadSinSubsanacion"));
			} else if (TypeModoAcceso.ALTA.equals(respuesta.getModoAcceso())) {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral(Constantes.LITERAL_INFO_ALTA_OK));
			} else if (TypeModoAcceso.EDICION.equals(respuesta.getModoAcceso())) {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
						UtilJSF.getLiteral(Constantes.LITERAL_INFO_MODIFICADO_OK));
			}
		}

	}

	// ------- VIEW DE DOMINIOS

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void anyadirDominio() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.tramiteVersion.getCodigo().toString());
		params.put(TypeParametroVentana.AREA.toString(), this.area.getCodigo().toString());
		UtilJSF.openDialog(DialogDefinicionVersionDominios.class, TypeModoAcceso.ALTA, params, true, 950, 550);
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void quitarDominio() {

		if (!verificarFilaSeleccionada()) {
			return;
		}

		if (dominioService.tieneTramiteVersion(this.dominioSeleccionado.getCodigo(), this.id)) {

			dominioService.removeTramiteVersion(this.dominioSeleccionado.getCodigo(), this.tramiteVersion.getCodigo());

			dominios = tramiteService.getDominioSimpleByTramiteId(id);

			dominioSeleccionado = null;
		} else {

			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.relacionInexistente"));
		}

	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void consultarDominio() {

		if (!verificarFilaSeleccionada()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), dominioSeleccionado.getCodigo().toString());
		params.put(TypeParametroVentana.AMBITO.toString(), TypeAmbito.ENTIDAD.toString());
		UtilJSF.openDialog(DialogDominio.class, TypeModoAcceso.CONSULTA, params, true, 770, 680);

	}

	/**
	 * Ayuda dominios empleados.
	 */
	public void ayudaDominio() {
		UtilJSF.openHelp("definicionVersionDominios");
	}

	/**
	 * Ayuda paso Rellenar formulario.
	 */
	public void ayudaRellenar() {
		UtilJSF.openHelp("definicionVersionRellenar");
	}

	/**
	 * Ayuda paso Anexar documento.
	 */
	public void ayudaAnexos() {
		UtilJSF.openHelp("definicionVersionAnexarDocumentos");
	}

	/**
	 * Ayuda paso Pagar tasa.
	 */
	public void ayudaTasas() {
		UtilJSF.openHelp("definicionVersionPagarTasas");
	}

	/**
	 * Ayuda.
	 */
	public void ayudaPropiedades() {
		UtilJSF.openHelp("definicionVersionPropiedadesDialog");
	}

	/**
	 * Ayuda.
	 */
	public void ayudaDebeSaber() {
		UtilJSF.openHelp("definicionVersionDebeSaberDialog");
	}

	/**
	 * Ayuda.
	 */
	public void ayudaFormulario() {
		UtilJSF.openHelp("definicionVersionFormularioDialog");
	}

	/**
	 * Ayuda.
	 */
	public void ayudaAnexo() {
		UtilJSF.openHelp("definicionVersionAnexoDialog");
	}

	/**
	 * Ayuda.
	 */
	public void ayudaRegistrar() {
		UtilJSF.openHelp("definicionVersionRegistrarTramiteDialog");
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.dominioSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_NOSELECCIONADOFILA));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	// ------- VIEW DE PASO DE RELLENAR ------------------------------

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void nuevoFormulario() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), tramiteVersion.getCodigo().toString());
		params.put(TypeParametroVentana.TRAMITEPASO.toString(),
				getTramitePasoRELLSeleccionado().getCodigo().toString());
		UtilJSF.openDialog(DialogDefinicionVersionRellenar.class, TypeModoAcceso.ALTA, params, true, 600, 200);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editarFormulario() {
		// Verifica si no hay fila seleccionada
		if (!verificarFormularioSeleccionado()) {
			return;
		}

		abrirDialogFormulario(this.formularioSeleccionado);
	}

	/**
	 * Edita formulario a través del doble click.
	 */
	public void editarFormularioDblClick() {
		if (permiteEditar()) {
			this.editarFormulario();
		}
	}

	/**
	 * Abre dialogo para editar dato (desde el arbol).
	 */
	public void editarFormularioSeleccionado() {

		abrirDialogFormulario(this.getFormularioTramiteSeleccionado());
	}

	/**
	 * Método privado para abrir un formulario.
	 *
	 * @param formulario
	 */
	private void abrirDialogFormulario(final FormularioTramite formulario) {

		// Ponemos en la mochila los dominios y formularios
		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_TRAMITE,
				UtilJSON.toJSON(UtilScripts.getTramiteSimple(this.tramiteVersion)));

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(formulario.getCodigo()));
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), tramiteVersion.getCodigo().toString());
		params.put(TypeParametroVentana.TRAMITEPASO.toString(),
				((OpcionArbol) this.selectedNode.getData()).getTramitePaso().getCodigo().toString());
		UtilJSF.openDialog(DialogDefinicionVersionFormulario.class, TypeModoAcceso.EDICION, params, true, 1100, 420);
	}

	/**
	 * Eliminar.
	 */
	public void eliminarFormulario() {
		// Verifica si no hay fila seleccionada
		if (!verificarFormularioSeleccionado()) {
			return;
		}

		tramiteService.removeFormulario(this.getTramitePasoRELLSeleccionado().getCodigo(),
				this.formularioSeleccionado.getCodigo());

		formularioSeleccionado = null;
		// Actualizamos la info
		recuperarDatos();
		inicializarArbol();
	}

	/**
	 * Verifica si hay formulario seleccionado.
	 *
	 * @return
	 */
	private boolean verificarFormularioSeleccionado() {
		boolean filaSeleccionada = true;
		if (this.formularioSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_NOSELECCIONADOFILA));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Sube el formulario.
	 */
	public void subirFormulario() {
		if (!verificarFormularioSeleccionado()) {
			return;
		}

		final int posicion = posicionFormulario(this.formularioSeleccionado);

		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_MOVERARRIBA));
			return;
		}

		tramiteService.intercambiarFormularios(this.formularioSeleccionado.getCodigo(),
				this.getTramitePasoRELLSeleccionado().getFormulariosTramite().get(posicion - 1).getCodigo());

		// Actualizamos la info
		recuperarDatos();
		inicializarArbol();
	}

	/**
	 * Baja el formulario.
	 */
	public void bajarFormulario() {
		if (!verificarFormularioSeleccionado()) {
			return;
		}

		final int posicion = posicionFormulario(this.formularioSeleccionado);

		if (posicion >= this.getTramitePasoRELLSeleccionado().getFormulariosTramite().size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_MOVERABAJO));
			return;
		}

		tramiteService.intercambiarFormularios(this.formularioSeleccionado.getCodigo(),
				this.getTramitePasoRELLSeleccionado().getFormulariosTramite().get(posicion + 1).getCodigo());

		// Actualizamos la info
		recuperarDatos();
		inicializarArbol();
	}

	/**
	 *
	 * @param formulario
	 * @return
	 */
	private int posicionFormulario(final FormularioTramite formulario) {
		int posicion = 0;

		for (final FormularioTramite formTram : this.getTramitePasoRELLSeleccionado().getFormulariosTramite()) {
			if (formTram.getCodigo().compareTo(formulario.getCodigo()) == 0) {
				break;
			}
			posicion++;
		}
		return posicion;
	}

	/**
	 * Retorno dialogo de un Paso Tramite.
	 *
	 * @param event
	 *            respuesta dialogo
	 ***/
	public void returnDialogoFormularioAlta(final SelectEvent event) {
		returnDialogoRefrescarTramite(event);
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			this.formularioAlta = (FormularioTramite) respuesta.getResult();
		}
	}

	/** Evento para editar formulario después de crear un alta. */
	public void editarFormularioAlta() {

		if (formularioAlta != null) {
			this.formularioSeleccionado = formularioAlta;
			this.editarFormulario();
			formularioAlta = null;
		}
	}

	// ------- VIEW DE PASO DE DOCUMENTO ------------------------------

	/**
	 * Consultar Script.
	 *
	 * @param iScript
	 */
	public void editarScriptListaDinamica() {

		final Script script = this.getTramitePasoANEXSeleccionado().getScriptAnexosDinamicos();
		// Muestra dialogo
		final Map<String, String> map = new HashMap<>();
		map.put(TypeParametroVentana.TIPO_SCRIPT_FLUJO.toString(),
				UtilJSON.toJSON(TypeScriptFlujo.SCRIPT_LISTA_DINAMICA_ANEXOS));
		map.put(TypeParametroVentana.TRAMITEPASO.toString(),
				((OpcionArbol) this.selectedNode.getData()).getTramitePaso().getCodigo().toString());

		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();

		if (script != null) {
			UtilJSF.getSessionBean().limpiaMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));
		}

		map.put(TypeParametroVentana.TRAMITEVERSION.toString(), id.toString());
		if (this.permiteEditar()) {
			map.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.EDICION.toString());
			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, map, true, 700);
		} else {
			map.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.CONSULTA.toString());
			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.CONSULTA, map, true, 700);
		}

	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoScriptListaDinamica(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Script script = (Script) respuesta.getResult();
			this.getTramitePasoANEXSeleccionado().setScriptAnexosDinamicos(script);
			tramiteService.updateTramitePaso(this.getTramitePasoANEXSeleccionado());

			message = UtilJSF.getLiteral(Constantes.LITERAL_INFO_MODIFICADO_OK);

		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}

		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_TRAMITE);

	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void nuevoDocumento() {

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), tramiteVersion.getCodigo().toString());
		params.put(TypeParametroVentana.TRAMITEPASO.toString(),
				getTramitePasoANEXSeleccionado().getCodigo().toString());
		UtilJSF.openDialog(DialogDefinicionVersionAnexarDocumentos.class, TypeModoAcceso.ALTA, params, true, 600, 200);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editarDocumento() {
		// Verifica si no hay fila seleccionada
		if (!verificarDocumentoSeleccionada()) {
			return;
		}

		this.editarDocumentoDialog(this.getDocumentoSeleccionado().getCodigo());
	}

	/** Edita documento a través del doble click. **/
	public void editarDocumentoDblClick() {
		if (permiteEditar()) {
			this.editarDocumento();
		}
	}

	/**
	 * Abre el dialog para editar dato.
	 */
	public void editarDocumentoTramite() {
		this.editarDocumentoDialog(this.getDocumentoTramiteSeleccionado().getCodigo());
	}

	/**
	 * El encargado de abrir el dialog.
	 *
	 * @param idDocumento
	 */
	private void editarDocumentoDialog(final Long idDocumento) {

		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_TRAMITE,
				UtilJSON.toJSON(UtilScripts.getTramiteSimple(this.tramiteVersion)));

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(idDocumento));
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), tramiteVersion.getCodigo().toString());
		params.put(TypeParametroVentana.TRAMITEPASO.toString(),
				((OpcionArbol) this.selectedNode.getData()).getTramitePaso().getCodigo().toString());
		params.put(TypeParametroVentana.ENTIDAD.toString(),
				entidadService.loadEntidadByArea(area.getCodigo()).getCodigo().toString());
		UtilJSF.openDialog(DialogDefinicionVersionAnexo.class, TypeModoAcceso.EDICION, params, true, 950, 645);
	}

	/**
	 * Eliminar.
	 */
	public void eliminarDocumento() {
		// Verifica si no hay fila seleccionada
		if (!verificarDocumentoSeleccionada()) {
			return;
		}

		tramiteService.removeDocumento(this.getTramitePasoANEXSeleccionado().getCodigo(),
				this.documentoSeleccionado.getCodigo());

		documentoSeleccionado = null;
		// Actualizamos la info
		recuperarDatos();
		inicializarArbol();
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarDocumentoSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.documentoSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_NOSELECCIONADOFILA));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Sube la propiedad de posición.
	 */
	public void subirDocumento() {
		if (!verificarDocumentoSeleccionada()) {
			return;
		}

		final TramitePasoAnexar tramitePasoAnexar = this.getTramitePasoANEXSeleccionado();
		final int posicion = tramitePasoAnexar.getDocumentos().indexOf(this.documentoSeleccionado);

		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_MOVERARRIBA));
			return;
		}

		final Documento documento = tramitePasoAnexar.getDocumentos().remove(posicion);
		tramitePasoAnexar.getDocumentos().add(posicion - 1, documento);

		for (int i = 0; i < tramitePasoAnexar.getDocumentos().size(); i++) {
			tramitePasoAnexar.getDocumentos().get(i).setOrden(i + 1);
		}

		tramiteService.updateTramitePaso(tramitePasoAnexar);
		// Actualizamos la info
		recuperarDatos();
		inicializarArbol();

		documentoSeleccionado = null;
	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajarDocumento() {
		if (!verificarDocumentoSeleccionada()) {
			return;
		}

		final TramitePasoAnexar tramitePasoAnexar = this.getTramitePasoANEXSeleccionado();
		final int posicion = tramitePasoAnexar.getDocumentos().indexOf(this.documentoSeleccionado);

		if (posicion >= tramitePasoAnexar.getDocumentos().size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_MOVERABAJO));
			return;
		}

		final Documento documento = tramitePasoAnexar.getDocumentos().remove(posicion);
		tramitePasoAnexar.getDocumentos().add(posicion + 1, documento);

		for (int i = 0; i < tramitePasoAnexar.getDocumentos().size(); i++) {
			tramitePasoAnexar.getDocumentos().get(i).setOrden(i + 1);
		}

		tramiteService.updateTramitePaso(tramitePasoAnexar);

		// Actualizamos la info
		recuperarDatos();
		inicializarArbol();

		documentoSeleccionado = null;
	}

	/**
	 * Retorno dialogo de un Paso Tramite.
	 *
	 * @param event
	 *            respuesta dialogo
	 ***/
	public void returnDialogoDocumentoAlta(final SelectEvent event) {
		returnDialogoRefrescarTramite(event);
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			this.documentoAlta = (Documento) respuesta.getResult();
		}
	}

	/** Evento para editar documento después de crear un alta. */
	public void editarDocumentoAlta() {

		if (documentoAlta != null) {
			this.documentoSeleccionado = documentoAlta;
			this.editarDocumento();
			documentoAlta = null;
		}
	}

	/**
	 * Activa la subsanación del paso anexar documento.
	 *
	 * @param idPaso
	 *            Id del paso.
	 * @param tipoPaso
	 *            Tipo de paso
	 **/
	public void activarSubsanacion(final Long idPaso, final String tipoPaso) {

		tramiteService.permiteSubsanacion(idPaso, true);

		final Entidad entidad = this.entidadService.loadEntidad(UtilJSF.getIdEntidad());
		final TypePaso typePaso = TypePaso.fromString(tipoPaso);
		switch (typePaso) {
		case ANEXAR:
			if (!entidad.isPermiteSubsanarAnexar()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("viewDefinicionVersionAnexarDocumentos.warning.entidadSinSubsanacion"));
			}
			this.getTramitePasoANEXSeleccionado().setPermiteSubsanar(true);
			break;
		case REGISTRAR:
			if (!entidad.isPermiteSubsanarRegistrar()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("viewDefinicionVersionRegistrarTramite.warning.entidadSinSubsanacion"));
			}
			this.getTramitePasoREGSeleccionado().setPermiteSubsanar(true);
			break;
		case PAGAR:
			if (!entidad.isPermiteSubsanarPagar()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("viewDefinicionVersionTasa.warning.entidadSinSubsanacion"));
			}
			this.getTramitePasoTSSeleccionado().setPermiteSubsanar(true);
			break;
		default:
			break;

		}

	}

	/**
	 * Desactiva la subsanación del paso anexar documento.
	 *
	 * @param idPaso
	 *            Id del paso.
	 * @param tipoPaso
	 *            Tipo de paso
	 */
	public void desactivarSubsanacion(final Long idPaso, final String tipoPaso) {

		tramiteService.permiteSubsanacion(idPaso, false);

		final TypePaso typePaso = TypePaso.fromString(tipoPaso);
		switch (typePaso) {
		case ANEXAR:
			this.getTramitePasoANEXSeleccionado().setPermiteSubsanar(false);
			break;
		case REGISTRAR:
			this.getTramitePasoREGSeleccionado().setPermiteSubsanar(false);
			break;
		case PAGAR:
			this.getTramitePasoTSSeleccionado().setPermiteSubsanar(false);
			break;
		default:
			break;
		}

	}

	// ------- VIEW DE PASO DE TASA ------------------------------

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void nuevaTasa() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), tramiteVersion.getCodigo().toString());
		params.put(TypeParametroVentana.TRAMITEPASO.toString(), getTramitePasoTSSeleccionado().getCodigo().toString());
		UtilJSF.openDialog(DialogDefinicionVersionPagarTasas.class, TypeModoAcceso.ALTA, params, true, 600, 150);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editarTasa() {
		// Verifica si no hay fila seleccionada
		if (!verificarTasaSeleccionada()) {
			return;
		}

		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_TRAMITE,
				UtilJSON.toJSON(UtilScripts.getTramiteSimple(this.tramiteVersion)));

		this.editarTasaDialog(this.tasaSeleccionado.getCodigo());

	}

	/** Edita tasa a través del doble click. **/
	public void editarTasaDblClick() {
		if (permiteEditar()) {
			this.editarTasa();
		}
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editarTasaTramite() {
		this.editarTasaDialog(this.getTasaTramiteSeleccionado().getCodigo());
	}

	public void editarTasaDialog(final Long idTasa) {
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(idTasa));
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), tramiteVersion.getCodigo().toString());
		params.put(TypeParametroVentana.TRAMITEPASO.toString(),
				((OpcionArbol) this.selectedNode.getData()).getTramitePaso().getCodigo().toString());
		UtilJSF.openDialog(DialogDefinicionVersionTasa.class, TypeModoAcceso.EDICION, params, true, 700, 450);
	}

	/**
	 * Eliminar.
	 */
	public void eliminarTasa() {
		// Verifica si no hay fila seleccionada
		if (!verificarTasaSeleccionada()) {
			return;
		}

		tramiteService.removeTasa(this.getTramitePasoTSSeleccionado().getCodigo(), this.tasaSeleccionado.getCodigo());
		tasaSeleccionado = null;
		// Actualizamos la info
		recuperarDatos();
		inicializarArbol();
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarTasaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.tasaSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_NOSELECCIONADOFILA));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Sube la propiedad de posición.
	 */
	public void subirTasa() {
		if (!verificarTasaSeleccionada()) {
			return;
		}

		final TramitePasoTasa tramitePasoAnexar = this.getTramitePasoTSSeleccionado();
		final int posicion = tramitePasoAnexar.getTasas().indexOf(this.tasaSeleccionado);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_MOVERARRIBA));
			return;
		}

		final Tasa tasa = tramitePasoAnexar.getTasas().remove(posicion);
		tramitePasoAnexar.getTasas().add(posicion - 1, tasa);

		for (int i = 0; i < tramitePasoAnexar.getTasas().size(); i++) {
			tramitePasoAnexar.getTasas().get(i).setOrden(i + 1);
		}

		tramiteService.updateTramitePaso(tramitePasoAnexar);
		tasaSeleccionado = null;

		// Actualizamos la info
		recuperarDatos();
		inicializarArbol();
	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajarTasa() {
		if (!verificarTasaSeleccionada()) {
			return;
		}

		final TramitePasoTasa tramitePasoAnexar = this.getTramitePasoTSSeleccionado();
		final int posicion = tramitePasoAnexar.getTasas().indexOf(this.tasaSeleccionado);
		if (posicion >= tramitePasoAnexar.getTasas().size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_MOVERABAJO));
			return;
		}

		final Tasa tasa = tramitePasoAnexar.getTasas().remove(posicion);
		tramitePasoAnexar.getTasas().add(posicion + 1, tasa);

		for (int i = 0; i < tramitePasoAnexar.getTasas().size(); i++) {
			tramitePasoAnexar.getTasas().get(i).setOrden(i + 1);
		}

		tramiteService.updateTramitePaso(tramitePasoAnexar);

		tasaSeleccionado = null;
		// Actualizamos la info
		recuperarDatos();
		inicializarArbol();
	}

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoTasaAlta(final SelectEvent event) {
		returnDialogoRefrescarTramite(event);
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			this.tasaAlta = (Tasa) respuesta.getResult();
		}
	}

	/** Evento para editar alta después de crear un alta. */
	public void editarTasaAlta() {

		if (tasaAlta != null) {
			this.tasaSeleccionado = tasaAlta;
			this.editarTasa();
			tasaAlta = null;
		}
	}

	// ------- FUNCIONES PRIVADAS ------------------------------

	private Long getIdSelectNode() {
		Long idSelectNode = null;
		if (this.selectedNode != null && this.selectedNode.getData() != null) {
			final OpcionArbol opcionArbol = (OpcionArbol) this.selectedNode.getData();
			if (opcionArbol.getDocumento() != null) {
				idSelectNode = opcionArbol.getDocumento().getCodigo();
			} else if (opcionArbol.getFormulario() != null) {
				idSelectNode = opcionArbol.getFormulario().getCodigo();
			} else if (opcionArbol.getTasa() != null) {
				idSelectNode = opcionArbol.getTasa().getCodigo();
			} else if (opcionArbol.getTramitePaso() != null) {
				idSelectNode = opcionArbol.getTramitePaso().getCodigo();
			}
		}

		return idSelectNode;
	}

	/**
	 * Método que se encarga de cargar de nuevo el arbol.
	 */
	private void inicializarArbol() {

		// Nos guardamos el nodo seleccionado si hiciese falta y se marca a nulo
		final Long idNodoSeleccionado = this.getIdSelectNode();
		String tipoNodoSeleccionado = null;
		OpcionArbol nodoSeleccionado = null;
		if (this.selectedNode != null && this.selectedNode.getData() != null) {
			nodoSeleccionado = (OpcionArbol) this.selectedNode.getData();
			tipoNodoSeleccionado = getTipo((OpcionArbol) this.selectedNode.getData());
		}
		this.selectedNode = null;

		/* inicializa arbol */
		root = new DefaultTreeNode("Root", null);

		/** Nodo Propiedades. **/
		final DefaultTreeNode nodoPropiedades = new DefaultTreeNode(
				new OpcionArbol(UtilJSF.getLiteral("viewDefinicionVersion.indice.propiedades"),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionPropiedades")));
		marcarNodoComoSeleccionado(nodoPropiedades, null, "viewDefinicionVersionPropiedades", nodoSeleccionado,
				idNodoSeleccionado, tipoNodoSeleccionado, "VPR");
		root.getChildren().add(nodoPropiedades);

		/** Nodo Dominios. **/
		final DefaultTreeNode nodoDominios = new DefaultTreeNode(
				new OpcionArbol(UtilJSF.getLiteral("viewDefinicionVersion.indice.dominiosEmpleados"),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionDominios")));
		marcarNodoComoSeleccionado(nodoDominios, null, "viewDefinicionVersionDominios", nodoSeleccionado,
				idNodoSeleccionado, tipoNodoSeleccionado, "VDM");
		root.getChildren().add(nodoDominios);

		final TreeNode nodePasosTramitacion = new DefaultTreeNode(
				new OpcionArbol(UtilJSF.getLiteral("viewDefinicionVersion.indice.pasosTramitacion"),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionPasos")),
				root);

		for (final TramitePaso tramitePaso : this.tramiteVersion.getListaPasos()) {

			/** Nodo paso. **/
			final Long idTramite = tramitePaso.getCodigo();
			final String textoTramite = String.valueOf(tramitePaso.getOrden()) + ". "
					+ tramitePaso.getDescripcion().getTraduccion(this.getSesion().getLang());

			final String url = UtilJSF.getUrlTramitePaso(tramitePaso);
			final DefaultTreeNode nodo = new DefaultTreeNode(
					new OpcionArbol(textoTramite, UtilJSF.getUrlArbolDefinicionVersion(url), tramitePaso));
			marcarNodoComoSeleccionado(nodo, idTramite, url, nodoSeleccionado, idNodoSeleccionado, tipoNodoSeleccionado,
					getTipo(tramitePaso));

			if (tramitePaso instanceof TramitePasoRellenar
					&& ((TramitePasoRellenar) tramitePaso).getFormulariosTramite() != null
					&& !((TramitePasoRellenar) tramitePaso).getFormulariosTramite().isEmpty()) {

				for (final FormularioTramite formulario : ((TramitePasoRellenar) tramitePaso).getFormulariosTramite()) {

					/** Nodo formularios. **/
					final DefaultTreeNode nodoFormulario = new DefaultTreeNode(
							new OpcionArbol(formulario.getIdentificador(),
									UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionFormulario"), formulario,
									tramitePaso));
					marcarNodoComoSeleccionado(nodoFormulario, formulario.getCodigo(),
							"viewDefinicionVersionFormulario", nodoSeleccionado, idNodoSeleccionado,
							tipoNodoSeleccionado, "FRM");
					nodo.getChildren().add(nodoFormulario);

				}

			} else if (tramitePaso instanceof TramitePasoAnexar
					&& ((TramitePasoAnexar) tramitePaso).getDocumentos() != null
					&& !((TramitePasoAnexar) tramitePaso).getDocumentos().isEmpty()) {

				for (final Documento documento : ((TramitePasoAnexar) tramitePaso).getDocumentos()) {

					/** Nodo documento. **/
					final DefaultTreeNode nodoDocumento = new DefaultTreeNode(
							new OpcionArbol(documento.getIdentificador(),
									UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionAnexo"), documento,
									tramitePaso));
					marcarNodoComoSeleccionado(nodoDocumento, documento.getCodigo(), "viewDefinicionVersionAnexo",
							nodoSeleccionado, idNodoSeleccionado, tipoNodoSeleccionado, "DOC");
					nodo.getChildren().add(nodoDocumento);

				}

			} else if (tramitePaso instanceof TramitePasoTasa && ((TramitePasoTasa) tramitePaso).getTasas() != null
					&& !((TramitePasoTasa) tramitePaso).getTasas().isEmpty()) {

				for (final Tasa tasa : ((TramitePasoTasa) tramitePaso).getTasas()) {

					/** Nodo Tasa. **/
					final DefaultTreeNode nodoTasa = new DefaultTreeNode(new OpcionArbol(tasa.getIdentificador(),
							UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionTasa"), tasa, tramitePaso));
					marcarNodoComoSeleccionado(nodoTasa, tasa.getCodigo(), "viewDefinicionVersionTasa",
							nodoSeleccionado, idNodoSeleccionado, tipoNodoSeleccionado, "TAX");
					nodo.getChildren().add(nodoTasa);

				}

			}

			nodePasosTramitacion.getChildren().add(nodo);

		}

		setExpandedRecursively(root, true);
	}

	/**
	 * Devuelve el tipo de opcion arbol.
	 *
	 * @param opcionArbol
	 * @return
	 */
	private String getTipo(final OpcionArbol opcionArbol) {
		String tipo;
		if (opcionArbol.getDocumento() != null) {
			tipo = "DOC";
		} else if (opcionArbol.getFormulario() != null) {
			tipo = "FRM";
		} else if (opcionArbol.getTasa() != null) {
			tipo = "TAX";
		} else if (opcionArbol.getTramitePaso() != null) {
			tipo = getTipo(opcionArbol.getTramitePaso());
		} else if ("viewDefinicionVersionPropiedades".equals(opcionArbol.getUrl())) {
			tipo = "VPR";
		} else if ("viewDefinicionVersionDominios".equals(opcionArbol.getUrl())) {
			tipo = "VDM";
		} else if ("viewDefinicionVersionPasos".equals(opcionArbol.getUrl())) {
			tipo = "VPS";
		} else {
			tipo = "";
		}
		return tipo;
	}

	/**
	 * Devuelve el tipo de paso que es un paso.
	 *
	 * @param data
	 * @return
	 */
	private String getTipo(final TramitePaso data) {
		String tipo;
		if (data instanceof TramitePasoRellenar) {
			tipo = "PRL";
		} else if (data instanceof TramitePasoRegistrar) {
			tipo = "PRG";
		} else if (data instanceof TramitePasoDebeSaber) {
			tipo = "PDS";
		} else if (data instanceof TramitePasoAnexar) {
			tipo = "PAX";
		} else if (data instanceof TramitePasoTasa) {
			tipo = "PTS";
		} else {
			tipo = "";
		}
		return tipo;
	}

	/**
	 * Se encarga de marcar un nodo como seleccionado si era el antiguo nodo
	 * seleccionado.
	 *
	 * @param nodoArbol
	 * @param idNodoArbol
	 * @param urlNodoArbol
	 * @param nodoSeleccionado
	 * @param idNodoSeleccionado
	 * @param tipoNodoSeleccionado
	 * @param tipo
	 * @return
	 */
	private void marcarNodoComoSeleccionado(final DefaultTreeNode nodoArbol, final Long idNodoArbol,
			final String urlNodoArbol, final OpcionArbol nodoSeleccionado, final Long idNodoSeleccionado,
			final String tipoNodoSeleccionado, final String tipo) {

		if (tipo == null || tipoNodoSeleccionado == null) {
			return;
		}

		// Si el nodo no tiene arbol, hay que ver si por url.
		if (idNodoArbol == null) {

			if (nodoSeleccionado != null && urlNodoArbol != null && nodoSeleccionado.getUrl() != null
					&& nodoSeleccionado.getUrl().contains(urlNodoArbol) && tipoNodoSeleccionado.equals(tipo)) {
				this.selectedNode = nodoArbol;
				nodoArbol.setSelected(true);
			}

		} else { // Si tiene id, se tienen que comparar por id

			if (idNodoSeleccionado != null && idNodoSeleccionado.compareTo(idNodoArbol) == 0
					&& tipoNodoSeleccionado.equals(tipo)) {
				this.selectedNode = nodoArbol;
				nodoArbol.setSelected(true);
			}
		}
	}

	/**
	 * Establece la propiedad expandida en un arbol recursivamente.
	 *
	 * @param node
	 *            nodo del arbol
	 * @param expanded
	 *            si se expande
	 */
	private void setExpandedRecursively(final TreeNode node, final boolean expanded) {
		if (node != null) {
			for (final TreeNode child : node.getChildren()) {
				setExpandedRecursively(child, expanded);
			}
			node.setExpanded(expanded);
		}
	}
	// ------- GETTERS / SETTERS --------------------------------

	/**
	 * Establece el valor de selectedNode.
	 *
	 * @param selectedNode
	 *            el nuevo valor de selectedNode
	 */
	public void setSelectedNode(final TreeNode selectedNode) {
		this.selectedNode = selectedNode;

		// anexar documento tiene un check de extensiones que es calculado en base al
		// campo extensiones
		if (selectedNode != null && selectedNode.getData() instanceof OpcionArbol
				&& ((OpcionArbol) this.selectedNode.getData()).getDocumento() != null) {
			final Documento data = ((OpcionArbol) this.selectedNode.getData()).getDocumento();
			if (data.getExtensiones().equals(Constantes.EXTENSIONES_TODAS)) {
				data.setExtensionSeleccion(TypeExtension.TODAS);
			} else {
				data.setExtensionSeleccion(TypeExtension.PERSONALIZADAS);
			}
		}
	}

	/**
	 * Tramite paso seleccionado.
	 *
	 * @return
	 */
	public TramitePaso getTramitePasoSeleccionado() {
		if (this.selectedNode == null) {
			return null;
		} else {
			return ((OpcionArbol) this.selectedNode.getData()).getTramitePaso();
		}
	}

	/**
	 * Formulario trámite paso seleccionado.
	 *
	 * @return
	 */
	public FormularioTramite getFormularioTramiteSeleccionado() {
		if (this.selectedNode == null) {
			return null;
		} else {
			return ((OpcionArbol) this.selectedNode.getData()).getFormulario();
		}
	}

	/**
	 * Tramite paso debe saber seleccionado.
	 *
	 * @return
	 */
	public TramitePasoDebeSaber getTramitePasoDSSeleccionado() {
		if (this.selectedNode == null) {
			return null;
		} else {
			return (TramitePasoDebeSaber) ((OpcionArbol) this.selectedNode.getData()).getTramitePaso();
		}
	}

	/**
	 * Tramite paso rellenar seleccionado.
	 *
	 * @return
	 */
	public TramitePasoRellenar getTramitePasoRELLSeleccionado() {
		if (this.selectedNode == null) {
			return null;
		} else {
			return (TramitePasoRellenar) ((OpcionArbol) this.selectedNode.getData()).getTramitePaso();
		}
	}

	/**
	 * Tramite paso anexar seleccionado.
	 *
	 * @return
	 */
	public TramitePasoAnexar getTramitePasoANEXSeleccionado() {
		if (this.selectedNode == null) {
			return null;
		} else {
			return (TramitePasoAnexar) ((OpcionArbol) this.selectedNode.getData()).getTramitePaso();
		}
	}

	/**
	 * Tramite paso tasa seleccionado.
	 *
	 * @return
	 */
	public TramitePasoTasa getTramitePasoTSSeleccionado() {
		if (this.selectedNode == null) {
			return null;
		} else {
			return (TramitePasoTasa) ((OpcionArbol) this.selectedNode.getData()).getTramitePaso();
		}
	}

	/**
	 * Tramite paso registrar seleccionado.
	 *
	 * @return
	 */
	public TramitePasoRegistrar getTramitePasoREGSeleccionado() {
		if (this.selectedNode == null) {
			return null;
		} else {
			return (TramitePasoRegistrar) ((OpcionArbol) this.selectedNode.getData()).getTramitePaso();
		}
	}

	/**
	 * Metodo que se ejecuta cuando se selecciona un nodo del arbol.
	 *
	 * @param event
	 *            evento que se ha producido
	 */
	public void onNodeSelect(final NodeSelectEvent event) {
		if (event != null) {
			final OpcionArbol opcionArbol = (OpcionArbol) event.getTreeNode().getData();
			if (opcionArbol.getUrl() != null) {
				opcionUrl = opcionArbol.getUrl();

				breadCrumb = copyMenuModel(breadCrumbRoot);

				if (breadCrumb != null) {
					creaRutaArbolBreadCrumb(breadCrumb, event.getTreeNode());
					breadCrumb.generateUniqueIds();
				}
			}

		}
	}

	/**
	 * Crea la ruta del arbol para el breadcrumb.
	 *
	 * @param miga
	 *            breadcrumb
	 * @param arbol
	 *            arbol
	 */
	private void creaRutaArbolBreadCrumb(final MenuModel miga, final TreeNode arbol) {
		if (miga != null && arbol != null) {
			if (arbol.getParent() != null) {
				creaRutaArbolBreadCrumb(miga, arbol.getParent());
			}
			if (!"root".equals(arbol.getRowKey())) {
				final OpcionArbol opcionArbol = (OpcionArbol) arbol.getData();
				final DefaultMenuItem item = new DefaultMenuItem(opcionArbol.getName());
				item.setUrl("#");
				breadCrumb.addElement(item);
			}
		}
	}

	/**
	 * Crea un nuevo MenuModel a partir del que se pasa por par&aacute;metro.
	 *
	 * @param menumodel
	 *            MenuModel a copiar
	 * @return nuevo MenuModel
	 */
	private MenuModel copyMenuModel(final MenuModel menumodel) {
		MenuModel res = null;
		if (menumodel != null && !menumodel.getElements().isEmpty()) {
			res = new DefaultMenuModel();
			for (final MenuElement item : menumodel.getElements()) {
				res.addElement(item);
			}
		}
		return res;
	}

	/**
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id
	 *            el nuevo valor de id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de tramiteVersion.
	 *
	 * @return el valor de tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * Establece el valor de tramiteVersion.
	 *
	 * @param tramiteVersion
	 *            el nuevo valor de tramiteVersion
	 */
	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * Obtiene el valor de opcionUrl.
	 *
	 * @return el valor de opcionUrl
	 */
	public String getOpcionUrl() {
		return opcionUrl;
	}

	/**
	 * Establece el valor de opcionUrl.
	 *
	 * @param opcion
	 *            el nuevo valor de opcionUrl
	 */
	public void setOpcionUrl(final String opcion) {
		this.opcionUrl = opcion;
	}

	/**
	 * Recupera el valor de root.
	 *
	 * @return el valor de root
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * Obtiene el valor de breadCrumb.
	 *
	 * @return el valor de breadCrumb
	 */
	public MenuModel getBreadCrumModel() {
		return breadCrumb;
	}

	/**
	 * Obtiene el valor de selectedNode.
	 *
	 * @return el valor de selectedNode
	 */
	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	/**
	 * @return the formularioSeleccionado
	 */
	public FormularioTramite getFormularioSeleccionado() {
		return formularioSeleccionado;
	}

	/**
	 * @param formularioSeleccionado
	 *            the formularioSeleccionado to set
	 */
	public void setFormularioSeleccionado(final FormularioTramite formularioSeleccionado) {
		this.formularioSeleccionado = formularioSeleccionado;
	}

	/**
	 * @return the dominioSeleccionado
	 */
	public Dominio getDominioSeleccionado() {
		return dominioSeleccionado;
	}

	/**
	 * @param dominioSeleccionado
	 *            the dominioSeleccionado to set
	 */
	public void setDominioSeleccionado(final Dominio dominioSeleccionado) {
		this.dominioSeleccionado = dominioSeleccionado;
	}

	/**
	 * Documento seleccionado.
	 *
	 * @return
	 */
	public Documento getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	/**
	 * Tramite paso anexar seleccionado.
	 *
	 * @return
	 */
	public Documento getDocumentoTramiteSeleccionado() {
		if (this.selectedNode == null) {
			return null;
		} else {
			return ((OpcionArbol) this.selectedNode.getData()).getDocumento();
		}
	}

	public void setDocumentoSeleccionado(final Documento documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}

	public Tasa getTasaSeleccionado() {
		return tasaSeleccionado;
	}

	/**
	 * Tramite paso anexar seleccionado.
	 *
	 * @return
	 */
	public Tasa getTasaTramiteSeleccionado() {
		if (this.selectedNode == null) {
			return null;
		} else {
			return ((OpcionArbol) this.selectedNode.getData()).getTasa();
		}
	}

	public void setTasaSeleccionado(final Tasa tasaSeleccionado) {
		this.tasaSeleccionado = tasaSeleccionado;
	}

	/**
	 * @return the dominios
	 */
	public List<Dominio> getDominios() {
		return dominios;
	}

	/**
	 * @param dominios
	 *            the dominios to set
	 */
	public void setDominios(final List<Dominio> dominios) {
		this.dominios = dominios;
	}

	/**
	 * @return the tramite
	 */
	public Tramite getTramite() {
		return tramite;
	}

	/**
	 * @param tramite
	 *            the tramite to set
	 */
	public void setTramite(final Tramite tramite) {
		this.tramite = tramite;
	}

	/**
	 * @return the textoTipoAsunto
	 */
	public String getTextoTipoAsunto() {
		return textoTipoAsunto;
	}

	/**
	 * @param textoTipoAsunto
	 *            the textoTipoAsunto to set
	 */
	public void setTextoTipoAsunto(final String textoTipoAsunto) {
		this.textoTipoAsunto = textoTipoAsunto;
	}

	/**
	 * @return the textoLibroRegistro
	 */
	public String getTextoLibroRegistro() {
		return textoLibroRegistro;
	}

	/**
	 * @param textoLibroRegistro
	 *            the textoLibroRegistro to set
	 */
	public void setTextoLibroRegistro(final String textoLibroRegistro) {
		this.textoLibroRegistro = textoLibroRegistro;
	}

	/**
	 * @return the textoOficinaRegistro
	 */
	public String getTextoOficinaRegistro() {
		return textoOficinaRegistro;
	}

	/**
	 * @param textoOficinaRegistro
	 *            the textoOficinaRegistro to set
	 */
	public void setTextoOficinaRegistro(final String textoOficinaRegistro) {
		this.textoOficinaRegistro = textoOficinaRegistro;
	}

	public boolean isPermiteRefrescar() {
		return permiteRefrescar;
	}

	public void setPermiteRefrescar(final boolean permiteRefrescar) {
		this.permiteRefrescar = permiteRefrescar;
	}

	/**
	 * @return the idiomas
	 */
	public List<String> getIdiomas() {
		return idiomas;
	}

	/**
	 * @param idiomas
	 *            the idiomas to set
	 */
	public void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

	/**
	 * @return the permiteBloquear
	 */
	public boolean isPermiteBloquear() {
		return permiteBloquear;
	}

	/**
	 * @param permiteBloquear
	 *            the permiteBloquear to set
	 */
	public void setPermiteBloquear(final boolean permiteBloquear) {
		this.permiteBloquear = permiteBloquear;
	}

	/**
	 * @return the entidad
	 */
	public final Entidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public final void setEntidad(final Entidad entidad) {
		this.entidad = entidad;
	}
}
