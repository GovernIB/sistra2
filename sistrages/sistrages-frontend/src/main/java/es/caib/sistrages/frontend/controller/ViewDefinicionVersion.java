package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.ScriptService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.OpcionArbol;
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

	/** Tramite service. */
	@Inject
	private ScriptService scriptService;

	/** Security service. */
	@Inject
	private SecurityService securityService;

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Tramite service. */
	@Inject
	private DominioService dominioService;

	/** id. */
	private Long id;

	/** Area del tramite. **/
	private Area area;

	/** Tramite. **/
	private Tramite tramite;

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

	/** Documento seleccionado. **/
	private Documento documentoSeleccionado;

	/** Tasa seleccionado. **/
	private Tasa tasaSeleccionado;

	/** Dato seleccionado en la lista. */
	private Dominio dominioSeleccionado;

	/**
	 * Crea una nueva instancia de view definicion version.
	 */
	public ViewDefinicionVersion() {
		super();
	}

	/**
	 * Inicializacion.
	 */
	public void init() {
		/* titulo pantalla */
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		// Control acceso
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidad(UtilJSF.getIdEntidad());

		/** Inicializamos los datos. **/
		recuperarDatos();

		/* inicializa breadcrum y lo creamos */
		breadCrumbRoot = new DefaultMenuModel();

		DefaultMenuItem item = null;

		item = new DefaultMenuItem(area.getCodigo());
		item.setUrl("/secure/app/viewTramites.xhtml?MODO_ACCESO=" + modoAcceso + "&area=" + area.getId());
		breadCrumbRoot.addElement(item);

		item = new DefaultMenuItem(tramite.getDescripcion());
		item.setUrl("/secure/app/viewTramitesVersion.xhtml?MODO_ACCESO=" + modoAcceso + "&ID=" + tramite.getId());
		breadCrumbRoot.addElement(item);

		item = new DefaultMenuItem("Version " + tramiteVersion.getNumeroVersion());
		item.setUrl("#");
		breadCrumbRoot.addElement(item);
		breadCrumbRoot.generateUniqueIds();
		breadCrumb = copyMenuModel(breadCrumbRoot);

		/* Inicializamos el arbol. */
		inicializarArbol();
	}

	/**
	 * Abre un di&aacute;logo para editar los datos.
	 */
	public void consultarDisenyo() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), "1");
		UtilJSF.openDialog(DialogDisenyoFormulario.class, TypeModoAcceso.CONSULTA, params, true, 1200, 720);
	}

	/**
	 * Vuelve a recuperar los datos.
	 */
	private void recuperarDatos() {
		/* recuperamos los datos */
		tramiteVersion = tramiteService.getTramiteVersion(id);
		area = tramiteService.getAreaTramite(Long.valueOf(tramiteVersion.getIdTramite()));
		tramite = tramiteService.getTramite(tramiteVersion.getIdTramite());

		/* obtenemos los pasos del trámite. */
		final List<TramitePaso> pasos = tramiteService.getTramitePasos(id);
		tramiteVersion.setListaPasos(pasos);
		final List<Dominio> dominios = tramiteService.getTramiteDominios(id);
		tramiteVersion.setListaDominios(dominios);
	}

	/**
	 * Consultar Script.
	 *
	 * @param iScript
	 */
	public void consultarScript(final Long idScript) {

		// Muestra dialogo
		final Map<String, String> map = new HashMap<>();
		if (idScript == null) {
			map.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(new Script()));
		} else {
			final Script script = this.scriptService.getScript(idScript);
			map.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(script));
		}
		map.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.CONSULTA.toString());
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.CONSULTA, map, true, 950, 700);

	}

	/**
	 * Consultar Script.
	 */
	public void consultarTraduccion(final Literal literal) {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.CONSULTA, literal, null, null);
	}

	/**
	 * Edita el paso seleccionado. Dependiendo del tipo de instancia, abre un tipo o
	 * no (no todos los pasos necesitan editarse).
	 */
	public void editarPaso() {
		if (this.selectedNode != null) {
			final TramitePaso paso = ((OpcionArbol) this.selectedNode.getData()).getTramitePaso();
			final Map<String, String> map = new HashMap<>();
			map.put(TypeParametroVentana.ID.toString(), paso.getId().toString());

			if (paso instanceof TramitePasoDebeSaber) {

				UtilJSF.openDialog(DialogDefinicionVersionDebeSaber.class, TypeModoAcceso.EDICION, map, true, 950, 500);

			} else if (paso instanceof TramitePasoTasa) {

				UtilJSF.openDialog(DialogDefinicionVersionTasa.class, TypeModoAcceso.EDICION, map, true, 950, 700);

			}

		}
	}

	/**
	 * Retorno dialogo de un Paso Tramite.
	 *
	 * @param event
	 *            respuesta dialogo
	 ***/
	public void returnDialogoPT(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {
			case EDICION:

				// Recuperamos el tramite paso y lo actualizamos y damos el mensaje
				final TramitePaso tramitePasoMod = (TramitePaso) respuesta.getResult();
				tramiteService.updateTramitePaso(tramitePasoMod);
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.modificado.ok"));

				// Podriamos llamar la BBDD o actualizarlo a mano.
				recuperarDatos();
				/*
				 * int posicion = 0; for (TramitePaso tramitePaso :
				 * tramiteVersion.getListaPasos()) { if
				 * (tramitePaso.getId().compareTo(tramitePasoMod.getId()) == 0) { break; }
				 * posicion++; }
				 *
				 * if (posicion <= tramiteVersion.getListaPasos().size()) {
				 * tramiteVersion.getListaPasos().remove(posicion);
				 * tramiteVersion.getListaPasos().add(posicion, tramitePasoMod); }
				 */

				// Refrescamos el arbol
				inicializarArbol();

				break;
			case ALTA:
				// Se da por hecho que de alta de momento no existe (ya que los pasos no pueden
				// ser personalizados)
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

	}

	// ------- VIEW DE EDITAR PROPIEDADES DE TRAMITE VERSION

	/**
	 * Abre la ventana para editar propiedades.
	 */
	public void editarPropiedades() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), id.toString());
		UtilJSF.openDialog(DialogDefinicionVersionPropiedades.class, TypeModoAcceso.EDICION, params, true, 950, 520);
	}

	/**
	 * Cuando vuelve de editar el control de acceso.
	 *
	 * @param event
	 */
	public void returnDialogoRefrescarTramite(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			tramiteVersion = tramiteService.getTramiteVersion(id);
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.modificado.ok"));
		}
	}

	// ------- VIEW DE EDITAR CONTROL DE ACCESO DE TRAMITE VERSION

	/**
	 * Abre la ventana para editar propiedades.
	 */
	public void editarControlAcceso() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), id.toString());
		UtilJSF.openDialog(DialogDefinicionVersionControlAcceso.class, TypeModoAcceso.EDICION, params, true, 950, 520);
	}

	// ------- VIEW DE DOMINIOS

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void anyadirDominio() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.tramiteVersion.getId().toString());
		params.put(TypeParametroVentana.AREA.toString(), this.area.getId().toString());
		UtilJSF.openDialog(DialogDefinicionVersionDominios.class, TypeModoAcceso.ALTA, params, true, 950, 450);
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void quitarDominio() {

		if (!verificarFilaSeleccionada())
			return;

		if (dominioService.tieneTramiteVersion(this.dominioSeleccionado.getId(), Long.valueOf(this.id))) {

			dominioService.removeTramiteVersion(this.dominioSeleccionado.getId(), this.tramiteVersion.getId());
			final List<Dominio> dominios = tramiteService.getTramiteDominios(id);
			tramiteVersion.setListaDominios(dominios);

		} else {

			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.relacionInexistente"));
		}

	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void consultarDominio() {

		if (!verificarFilaSeleccionada())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), dominioSeleccionado.getId().toString());
		params.put(TypeParametroVentana.AMBITO.toString(), TypeAmbito.ENTIDAD.toString());
		UtilJSF.openDialog(DialogDominio.class, TypeModoAcceso.CONSULTA, params, true, 950, 450);
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoDominio(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled() && respuesta.getModoAcceso() == TypeModoAcceso.ALTA) {
			final Dominio dominio = (Dominio) respuesta.getResult();
			dominioService.addTramiteVersion(dominio.getId(), tramiteVersion.getId());

			final List<Dominio> dominios = tramiteService.getTramiteDominios(id);
			tramiteVersion.setListaDominios(dominios);

			message = UtilJSF.getLiteral("info.alta.ok");

		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.dominioSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	// ------- VIEW DE PASO DE RELLENAR ------------------------------

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void nuevoFormulario() {
		UtilJSF.openDialog(DialogDefinicionVersionRellenar.class, TypeModoAcceso.ALTA, null, true, 600, 200);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editarFormulario() {
		// Verifica si no hay fila seleccionada
		if (!verificarFormularioSeleccionado())
			return;

		abrirDialogFormulario(this.formularioSeleccionado);
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
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(formulario.getId()));
		UtilJSF.openDialog(DialogDefinicionVersionFormulario.class, TypeModoAcceso.EDICION, params, true, 1100, 500);
	}

	/**
	 * Eliminar.
	 */
	public void eliminarFormulario() {
		// Verifica si no hay fila seleccionada
		if (!verificarFormularioSeleccionado())
			return;

		tramiteService.removeFormulario(this.getTramitePasoRELLSeleccionado().getId(),
				this.formularioSeleccionado.getId());

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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Sube el formulario.
	 */
	public void subirFormulario() {
		if (!verificarFormularioSeleccionado())
			return;

		final TramitePasoRellenar tramitePasoRellenar = this.getTramitePasoRELLSeleccionado();
		final int posicion = posicionFormulario(this.formularioSeleccionado);

		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final FormularioTramite formulario = tramitePasoRellenar.getFormulariosTramite().remove(posicion);
		tramitePasoRellenar.getFormulariosTramite().add(posicion - 1, formulario);

		for (int i = 0; i < tramitePasoRellenar.getFormulariosTramite().size(); i++) {
			tramitePasoRellenar.getFormulariosTramite().get(i).setOrden(i + 1);
		}

		tramiteService.updateTramitePaso(tramitePasoRellenar);

	}

	/**
	 * Baja el formulario.
	 */
	public void bajarFormulario() {
		if (!verificarFormularioSeleccionado())
			return;

		final TramitePasoRellenar tramitePasoRellenar = this.getTramitePasoRELLSeleccionado();

		final int posicion = posicionFormulario(this.formularioSeleccionado);

		if (posicion >= tramitePasoRellenar.getFormulariosTramite().size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final FormularioTramite formulario = tramitePasoRellenar.getFormulariosTramite().remove(posicion);
		tramitePasoRellenar.getFormulariosTramite().add(posicion + 1, formulario);

		for (int i = 0; i < tramitePasoRellenar.getFormulariosTramite().size(); i++) {
			tramitePasoRellenar.getFormulariosTramite().get(i).setOrden(i + 1);
		}

		tramiteService.updateTramitePaso(tramitePasoRellenar);
	}

	/**
	 *
	 * @param formulario
	 * @return
	 */
	private int posicionFormulario(final FormularioTramite formulario) {
		int posicion = 0;

		for (final FormularioTramite formTram : this.getTramitePasoRELLSeleccionado().getFormulariosTramite()) {
			if (formTram.getId().compareTo(formulario.getId()) == 0) {
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
	public void returnDialogoFormulario(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			final FormularioTramite formulario = (FormularioTramite) respuesta.getResult();
			switch (respuesta.getModoAcceso()) {

			case ALTA:

				tramiteService.addFormularioTramite(formulario, this.getTramitePasoRELLSeleccionado().getId());

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				tramiteService.updateFormularioTramite(formulario);

				// Mensaje
				message = UtilJSF.getLiteral("info.modificado.ok");
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Si el mensaje está relleno es que ha habido algún cambio
			recuperarDatos();
			inicializarArbol();
		}

	}

	// ------- VIEW DE PASO DE RELLENAR ------------------------------

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void nuevoDocumento() {
		UtilJSF.openDialog(DialogDefinicionVersionAnexarDocumentos.class, TypeModoAcceso.ALTA, null, true, 600, 200);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editarDocumento() {
		// Verifica si no hay fila seleccionada
		if (!verificarDocumentoSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.documentoSeleccionado.getId()));
		UtilJSF.openDialog(DialogDefinicionVersionAnexo.class, TypeModoAcceso.EDICION, params, true, 950, 575);
	}

	/**
	 * Eliminar.
	 */
	public void eliminarDocumento() {
		// Verifica si no hay fila seleccionada
		if (!verificarDocumentoSeleccionada())
			return;

		tramiteService.removeDocumento(this.getTramitePasoANEXSeleccionado().getId(),
				this.documentoSeleccionado.getId());

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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Sube la propiedad de posición.
	 */
	public void subirDocumento() {
		if (!verificarDocumentoSeleccionada())
			return;

		final TramitePasoAnexar tramitePasoAnexar = this.getTramitePasoANEXSeleccionado();
		final int posicion = tramitePasoAnexar.getDocumentos().indexOf(this.documentoSeleccionado);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final Documento documento = tramitePasoAnexar.getDocumentos().remove(posicion);
		tramitePasoAnexar.getDocumentos().add(posicion - 1, documento);

		for (int i = 0; i < tramitePasoAnexar.getDocumentos().size(); i++) {
			tramitePasoAnexar.getDocumentos().get(i).setOrden(i + 1);
		}

		tramiteService.updateTramitePaso(tramitePasoAnexar);
	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajarDocumento() {
		if (!verificarDocumentoSeleccionada())
			return;

		final TramitePasoAnexar tramitePasoAnexar = this.getTramitePasoANEXSeleccionado();
		final int posicion = tramitePasoAnexar.getDocumentos().indexOf(this.documentoSeleccionado);
		if (posicion >= tramitePasoAnexar.getDocumentos().size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final Documento documento = tramitePasoAnexar.getDocumentos().remove(posicion);
		tramitePasoAnexar.getDocumentos().add(posicion + 1, documento);

		for (int i = 0; i < tramitePasoAnexar.getDocumentos().size(); i++) {
			tramitePasoAnexar.getDocumentos().get(i).setOrden(i + 1);
		}

		tramiteService.updateTramitePaso(tramitePasoAnexar);

	}

	/**
	 * Retorno dialogo de un Paso Tramite.
	 *
	 * @param event
	 *            respuesta dialogo
	 ***/
	public void returnDialogoDocumento(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			final Documento documento = (Documento) respuesta.getResult();
			switch (respuesta.getModoAcceso()) {

			case ALTA:
				tramiteService.addDocumentoTramite(documento, this.getTramitePasoANEXSeleccionado().getId());

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				tramiteService.updateDocumentoTramite(documento);

				// Mensaje
				message = UtilJSF.getLiteral("info.modificado.ok");
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Si el mensaje está relleno es que ha habido algún cambio
			recuperarDatos();
			inicializarArbol();
		}

	}

	// ------- VIEW DE PASO DE RELLENAR ------------------------------

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void nuevaTasa() {
		UtilJSF.openDialog(DialogDefinicionVersionPagarTasas.class, TypeModoAcceso.ALTA, null, true, 600, 300);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editarTasa() {
		// Verifica si no hay fila seleccionada
		if (!verificarTasaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.tasaSeleccionado.getId()));
		UtilJSF.openDialog(DialogDefinicionVersionTasa.class, TypeModoAcceso.EDICION, params, true, 600, 300);

	}

	/**
	 * Eliminar.
	 */
	public void eliminarTasa() {
		// Verifica si no hay fila seleccionada
		if (!verificarTasaSeleccionada())
			return;

		tramiteService.removeTasa(this.getTramitePasoTSSeleccionado().getId(), this.tasaSeleccionado.getId());

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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Sube la propiedad de posición.
	 */
	public void subirTasa() {
		if (!verificarTasaSeleccionada())
			return;

		final TramitePasoTasa tramitePasoAnexar = this.getTramitePasoTSSeleccionado();
		final int posicion = tramitePasoAnexar.getTasas().indexOf(this.tasaSeleccionado);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final Tasa tasa = tramitePasoAnexar.getTasas().remove(posicion);
		tramitePasoAnexar.getTasas().add(posicion - 1, tasa);

		for (int i = 0; i < tramitePasoAnexar.getTasas().size(); i++) {
			tramitePasoAnexar.getTasas().get(i).setOrden(i + 1);
		}

		tramiteService.updateTramitePaso(tramitePasoAnexar);

	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajarTasa() {
		if (!verificarTasaSeleccionada())
			return;

		final TramitePasoTasa tramitePasoAnexar = this.getTramitePasoTSSeleccionado();
		final int posicion = tramitePasoAnexar.getTasas().indexOf(this.tasaSeleccionado);
		if (posicion >= tramitePasoAnexar.getTasas().size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final Tasa tasa = tramitePasoAnexar.getTasas().remove(posicion);
		tramitePasoAnexar.getTasas().add(posicion + 1, tasa);

		for (int i = 0; i < tramitePasoAnexar.getTasas().size(); i++) {
			tramitePasoAnexar.getTasas().get(i).setOrden(i + 1);
		}

		tramiteService.updateTramitePaso(tramitePasoAnexar);

	}

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoTasa(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			final Tasa tasa = (Tasa) respuesta.getResult();
			switch (respuesta.getModoAcceso()) {

			case ALTA:
				tramiteService.addTasaTramite(tasa, this.getTramitePasoTSSeleccionado().getId());

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				tramiteService.updateTasaTramite(tasa);

				// Mensaje
				message = UtilJSF.getLiteral("info.modificado.ok");
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}
	}

	// ------- FUNCIONES PRIVADAS ------------------------------

	private Long getIdSelectNode() {
		Long idSelectNode = null;
		if (this.selectedNode != null && this.selectedNode.getData() != null) {
			final OpcionArbol opcionArbol = (OpcionArbol) this.selectedNode.getData();
			if (opcionArbol.getTramitePaso() != null) {
				idSelectNode = opcionArbol.getTramitePaso().getId();
			} else if (opcionArbol.getDocumento() != null) {
				idSelectNode = opcionArbol.getDocumento().getId();
			} else if (opcionArbol.getFormulario() != null) {
				idSelectNode = opcionArbol.getFormulario().getId();
			} else if (opcionArbol.getTasa() != null) {
				idSelectNode = opcionArbol.getTasa().getId();
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
		OpcionArbol nodoSeleccionado = null;
		if (this.selectedNode != null && this.selectedNode.getData() != null) {
			nodoSeleccionado = (OpcionArbol) this.selectedNode.getData();
		}
		this.selectedNode = null;

		/* inicializa arbol */
		root = new DefaultTreeNode("Root", null);

		/** Nodo Propiedades. **/
		final DefaultTreeNode nodoPropiedades = new DefaultTreeNode(
				new OpcionArbol(UtilJSF.getLiteral("viewDefinicionVersion.indice.propiedades"),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionPropiedades")));
		marcarNodoComoSeleccionado(nodoPropiedades, null, "viewDefinicionVersionPropiedades", nodoSeleccionado,
				idNodoSeleccionado);
		root.getChildren().add(nodoPropiedades);

		/** Nodo Control acceso. **/
		final DefaultTreeNode nodoControlAcceso = new DefaultTreeNode(
				new OpcionArbol(UtilJSF.getLiteral("viewDefinicionVersion.indice.controlAcceso"),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionControlAcceso")));
		marcarNodoComoSeleccionado(nodoControlAcceso, null, "viewDefinicionVersionControlAcceso", nodoSeleccionado,
				idNodoSeleccionado);
		root.getChildren().add(nodoControlAcceso);

		/** Nodo Dominios. **/
		final DefaultTreeNode nodoDominios = new DefaultTreeNode(
				new OpcionArbol(UtilJSF.getLiteral("viewDefinicionVersion.indice.dominiosEmpleados"),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionDominios")));
		marcarNodoComoSeleccionado(nodoDominios, null, "viewDefinicionVersionDominios", nodoSeleccionado,
				idNodoSeleccionado);
		root.getChildren().add(nodoDominios);

		final TreeNode nodePasosTramitacion = new DefaultTreeNode(
				new OpcionArbol(UtilJSF.getLiteral("viewDefinicionVersion.indice.pasosTramitacion"),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionPasos")),
				root);

		for (final TramitePaso tramitePaso : this.tramiteVersion.getListaPasos()) {

			/** Nodo paso. **/
			final Long idTramite = tramitePaso.getId();
			final String textoTramite = String.valueOf(tramitePaso.getOrden()) + ". "
					+ tramitePaso.getDescripcion().getTraduccion(this.getSesion().getLang());

			final String url = UtilJSF.getUrlTramitePaso(tramitePaso);
			final DefaultTreeNode nodo = new DefaultTreeNode(
					new OpcionArbol(textoTramite, UtilJSF.getUrlArbolDefinicionVersion(url, idTramite), tramitePaso));
			marcarNodoComoSeleccionado(nodo, idTramite, url, nodoSeleccionado, idNodoSeleccionado);

			if (tramitePaso instanceof TramitePasoRellenar
					&& ((TramitePasoRellenar) tramitePaso).getFormulariosTramite() != null
					&& !((TramitePasoRellenar) tramitePaso).getFormulariosTramite().isEmpty()) {

				for (final FormularioTramite formulario : ((TramitePasoRellenar) tramitePaso).getFormulariosTramite()) {

					/** Nodo formularios. **/
					final DefaultTreeNode nodoFormulario = new DefaultTreeNode(new OpcionArbol(formulario.getCodigo(),
							UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionFormulario", formulario.getId()),
							formulario, tramitePaso));
					marcarNodoComoSeleccionado(nodoFormulario, formulario.getId(), "viewDefinicionVersionFormulario",
							nodoSeleccionado, idNodoSeleccionado);
					nodo.getChildren().add(nodoFormulario);

				}

			} else if (tramitePaso instanceof TramitePasoAnexar
					&& ((TramitePasoAnexar) tramitePaso).getDocumentos() != null
					&& !((TramitePasoAnexar) tramitePaso).getDocumentos().isEmpty()) {

				for (final Documento documento : ((TramitePasoAnexar) tramitePaso).getDocumentos()) {

					/** Nodo documento. **/
					final DefaultTreeNode nodoDocumento = new DefaultTreeNode(new OpcionArbol(documento.getCodigo(),
							UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionAnexo", documento.getId()),
							documento, tramitePaso));
					marcarNodoComoSeleccionado(nodoDocumento, documento.getId(), "viewDefinicionVersionAnexo",
							nodoSeleccionado, idNodoSeleccionado);
					nodo.getChildren().add(nodoDocumento);

				}

			} else if (tramitePaso instanceof TramitePasoTasa && ((TramitePasoTasa) tramitePaso).getTasas() != null
					&& !((TramitePasoTasa) tramitePaso).getTasas().isEmpty()) {

				for (final Tasa tasa : ((TramitePasoTasa) tramitePaso).getTasas()) {

					/** Nodo Tasa. **/
					final DefaultTreeNode nodoTasa = new DefaultTreeNode(new OpcionArbol(tasa.getCodigo(),
							UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionTasa", tasa.getId()), tasa,
							tramitePaso));
					marcarNodoComoSeleccionado(nodoTasa, tasa.getId(), "viewDefinicionVersionTasa", nodoSeleccionado,
							idNodoSeleccionado);
					nodo.getChildren().add(nodoTasa);

				}

			}

			nodePasosTramitacion.getChildren().add(nodo);

		}

		setExpandedRecursively(root, true);
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
	 * @return
	 */
	private void marcarNodoComoSeleccionado(final DefaultTreeNode nodoArbol, final Long idNodoArbol,
			final String urlNodoArbol, final OpcionArbol nodoSeleccionado, final Long idNodoSeleccionado) {
		// Si el nodo no tiene arbol, hay que ver si por url.
		if (idNodoArbol == null) {

			if (nodoSeleccionado != null && urlNodoArbol != null && nodoSeleccionado != null
					&& nodoSeleccionado.getUrl().contains(urlNodoArbol)) {
				this.selectedNode = nodoArbol;
				nodoArbol.setSelected(true);
			}

		} else { // Si tiene id, se tienen que comparar por id

			if (idNodoSeleccionado != null && idNodoSeleccionado.compareTo(idNodoArbol) == 0) {
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
	 * M&eacute;todo que se ejecuta cuando se selecciona un nodo del arbol.
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

	public Documento getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(final Documento documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}

	public Tasa getTasaSeleccionado() {
		return tasaSeleccionado;
	}

	public void setTasaSeleccionado(final Tasa tasaSeleccionado) {
		this.tasaSeleccionado = tasaSeleccionado;
	}
}
