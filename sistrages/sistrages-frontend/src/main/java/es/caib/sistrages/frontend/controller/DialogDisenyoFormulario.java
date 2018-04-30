package es.caib.sistrages.frontend.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;

import es.caib.sistrages.core.api.exception.ErrorNoControladoException;
import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampo;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.FormularioMockBD;
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

	/** Id formulario **/
	private String id;

	/** Formulario. **/
	private FormularioInterno formulario;

	private int paginaActual;

	private String posicionamiento;

	/** quitar: Código Componente seleccionado. */
	private String componentSelectedCodigo;

	/** Componente editado (copia original). **/
	private ComponenteFormulario componentEdit;

	/** Traducciones editado (cuando se llama a editar traducciones). **/
	private Literal traduccionesEdit;

	/** Url ventana propiedades **/
	private String panelPropiedadesUrl;

	/** Url detalle componente. **/
	private String detalleComponenteUrl;

	/** Url iframe. **/
	private String urlIframe;

	/**
	 * Indica si esta 'colapsado' el panel de propiedades (a true colapsado).
	 **/
	private boolean visiblePropiedades = false;

	/**
	 * Indica si esta 'colapsado' el panel de scripts (a true colapsado).
	 **/
	private boolean visibleScripts = true;

	/**
	 * Indica si esta 'colapsado' el panel de estilos (a true colapsado).
	 **/
	private boolean visibleEstilos = true;

	/**
	 * Codigo componente destino al pedir confirmacion de cambios.
	 **/
	private Long codigoComponenteDestino = null;

	/**
	 * Inicializacion.
	 **/
	public void init() {

		// TITULO ??
		// setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		// TODO
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			// ...
		} else {
			// ...
		}

		// TODO Recuperacion formulario
		recuperarFormulario(id);

		// TODO ¿que campo se selecciona? ninguno?
		panelPropiedadesUrl = "/secure/app/dialogDisenyoFormularioVacio.xhtml";
		urlIframe = "FormRenderServlet?ts=" + System.currentTimeMillis();
		paginaActual = 1;
		posicionamiento = "D";
	}

	/**
	 * Recupera formulario
	 *
	 * @param idForm
	 **/
	private void recuperarFormulario(final String idForm) {
		// TODO Test
		// formulario = FormularioMockBD.recuperar(Long.parseLong(idForm));
		formulario = formIntService.getFormularioInternoPaginas(Long.parseLong(idForm));
	}

	/**
	 * Editar.
	 **/
	public void editarComponente() {

		final FacesContext context = FacesContext.getCurrentInstance();
		final Map<String, String> map = context.getExternalContext().getRequestParameterMap();

		Long idComponente = null;
		if (map.get("id") != null) {
			idComponente = new Long(map.get("id"));
		}

		// Verificamos si ha habido cambios
		if (componentEdit != null) {
			final ComponenteFormulario cfOriginal = formulario.getPaginas().get(0).getComponente(componentEdit.getId());
			if (!UtilCoreApi.equalsModelApi(cfOriginal, componentEdit)) {
				// TODO Pedir confirmacion cambios
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "Componente cambiado");
				// Guardamos componente destino
				codigoComponenteDestino = idComponente;
				// Invocamos a boton para que dispare ventana de confirmacion
				final RequestContext contextReq = RequestContext.getCurrentInstance();
				contextReq.execute("PF('confirmationButton').jq.click();");
				return;
			}
		}

		// Cambiamos a nuevo componente
		cambiarEdicionComponente(idComponente);

	}

	/**
	 * Cambiar edicion componente.
	 *
	 * @param idComponente
	 *            the id componente
	 */
	private void cambiarEdicionComponente(final Long idComponente) {
		componentEdit = null;

		if (idComponente != null) {
			final ComponenteFormulario cf = formulario.getPaginas().get(0).getComponente(idComponente);

			// Buscamos nuevo componente
			if (cf != null) {
				componentEdit = (ComponenteFormulario) UtilCoreApi.cloneModelApi(cf);
				// TODO PARA QUITAR
				if (!UtilCoreApi.equalsModelApi(cf, componentEdit)) {
					UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "Componente clonado no coincide");
				}
			}
		}

		// TODO Gestion pagina actual,...
		String pagina = "/secure/app/dialogDisenyoFormularioVacio.xhtml";
		detalleComponenteUrl = null;
		if (componentEdit != null) {
			switch (componentEdit.getTipo()) {
			case CAMPO_TEXTO:
				pagina = "/secure/app/dialogDisenyoFormularioComponente.xhtml";
				break;
			case SELECTOR:
				pagina = "/secure/app/dialogDisenyoFormularioComponente.xhtml";
				break;
			case ETIQUETA:
				pagina = "/secure/app/dialogDisenyoFormularioComponente.xhtml";
				break;
			// TODO PENDIENTE
			default:
				break;
			}
			detalleComponenteUrl = "/secure/app/dialogDisenyoFormularioComponente" + componentEdit.getTipo() + ".xhtml";
		}

		panelPropiedadesUrl = pagina;
	}

	/**
	 * Sin implementar.
	 */
	public void sinImplementar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
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

	/**
	 * Confirmacion cambios.
	 **/
	public void confirmacionCambios() {
		// Guarda cambios
		aplicarCambios();
		// Cambia panel propiedades a nuevo componente seleccionado
		cambiarEdicionComponente(codigoComponenteDestino);
		codigoComponenteDestino = null;
	}

	/**
	 * Aplicar cambios.
	 **/
	public void aplicarCambios() {

		if (componentEdit != null) {

			final ComponenteFormulario cfOriginal = formulario.getPaginas().get(0).getComponente(componentEdit.getId());

			// TODO PENDIENTE GUARDAR (ver como hacerlo, ¿beanutils?¿metodos particulares
			// por tipo componente?) De momento no dejamos cambiar codigo para permitir
			// dejar seleccionando
			try {
				BeanUtils.copyProperties(cfOriginal, componentEdit);
			} catch (final Exception e) {
				throw new ErrorNoControladoException(e);
			}
			FormularioMockBD.guardar(formulario);

			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "Confirmacion cambios pendiente. Se simula cambio");

			// Refresca iframe formulario
			// TODO Pasarle componente destino para posicionarse
			urlIframe = "FormRenderServlet?ts=" + System.currentTimeMillis();
		}
	}

	/**
	 * Descartar cambios.
	 **/
	public void descartarCambios() {
		// Pasamos a componente destino
		cambiarEdicionComponente(codigoComponenteDestino);
		codigoComponenteDestino = null;

	}

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoTraducciones(final SelectEvent event) {
		try {
			final DialogResult respuesta = (DialogResult) event.getObject();
			// Solo tiene sentido cambios para edicion
			if (!respuesta.isCanceled() && respuesta.getModoAcceso() == TypeModoAcceso.EDICION) {
				final Literal traduccionesMod = (Literal) respuesta.getResult();
				BeanUtils.copyProperties(traduccionesEdit, traduccionesMod);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new FrontException("Error estableciendo traducciones", e);
		}

	}

	/**
	 * Editar texto componente.
	 */
	public void editarTraduccionesTexto() {
		// TODO COGER IDIOMAS TRAMITE Y VER OBLIGATORIOS
		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		if (componentEdit.getTexto() == null) {
			componentEdit.setTexto(UtilTraducciones.getTraduccionesPorDefecto());
		}
		traduccionesEdit = componentEdit.getTexto();
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, traduccionesEdit, idiomas, idiomas);
	}

	/**
	 * Editar ayuda componente.
	 */
	public void editarTraduccionesAyuda() {
		// TODO COGER IDIOMAS TRAMITE Y VER OBLIGATORIOS
		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		if (componentEdit.getAyuda() == null) {
			componentEdit.setAyuda(UtilTraducciones.getTraduccionesPorDefecto());
		}
		traduccionesEdit = componentEdit.getAyuda();
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, traduccionesEdit, idiomas, idiomas);
	}

	public boolean isComponenteCampo() {
		return componentEdit instanceof ComponenteFormularioCampo;
	}

	/**
	 * Cancelar.
	 **/
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Abre dialogo para propiedades formulario.
	 **/
	public void abrirPropiedadesFormulario() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();

		params.put(TypeParametroVentana.ID.toString(), String.valueOf(formulario.getId()));
		UtilJSF.openDialog(DialogPropiedadesFormulario.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 800,
				340);
	}

	public int getNumeroPaginas() {
		return formulario.getPaginas().size();
	}

	public void moverPaginaIzq() {
		paginaActual--;
		if (paginaActual < 1)
			paginaActual = 1;
	}

	public void moverPaginaDer() {
		paginaActual++;
		if (paginaActual > getNumeroPaginas())
			paginaActual = getNumeroPaginas();
	}

	public boolean getHabilitadoBtnPagIzq() {
		return paginaActual > 1;
	}

	public boolean getHabilitadoBtnPagDer() {
		return paginaActual < getNumeroPaginas();
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
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
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			formulario = (FormularioInterno) respuesta.getResult();
			paginaActual = 1;
		}

	}
	// -- Getters / Setters

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

	/**
	 * @return the nodeId
	 */
	public String getComponentSelectedCodigo() {
		return componentSelectedCodigo;
	}

	/**
	 * @param nodeId
	 *            the nodeId to set
	 */
	public void setComponentSelectedCodigo(final String nodeId) {
		this.componentSelectedCodigo = nodeId;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getUrlIframe() {
		return urlIframe + "&id=" + id + "&page=" + paginaActual;
	}

	public void setUrlIframe(final String urlIframe) {
		this.urlIframe = urlIframe;
	}

	public FormularioInterno getFormulario() {
		return formulario;
	}

	public void setFormulario(final FormularioInterno formulario) {
		this.formulario = formulario;
	}

	public ComponenteFormulario getComponentEdit() {
		return componentEdit;
	}

	public void setComponentEdit(final ComponenteFormulario componentEdit) {
		this.componentEdit = componentEdit;
	}

	/**
	 * @return the visiblePropiedades
	 */
	public boolean isVisiblePropiedades() {
		return visiblePropiedades;
	}

	/**
	 * @param visiblePropiedades
	 *            the visiblePropiedades to set
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
	 * @param visibleScripts
	 *            the visibleScripts to set
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
	 * @param visibleEstilos
	 *            the visibleEstilos to set
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
}
