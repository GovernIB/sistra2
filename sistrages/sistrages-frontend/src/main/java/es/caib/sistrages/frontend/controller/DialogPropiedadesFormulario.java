package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.ModelApi;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.types.TypeScriptFormulario;
import es.caib.sistrages.core.api.service.FormateadorFormularioService;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogPropiedadesFormulario extends DialogControllerBase {

	/** id */
	private String id;

	/** Id. formulario tramite. **/
	private String idFormularioTramite;

	/** Id. tramite version. **/
	private String idTramiteVersion;

	/**
	 * Datos elemento.
	 */
	private DisenyoFormulario data;

	private PaginaFormulario paginaSeleccionada;

	private String literal;

	private PlantillaFormulario plantillaSeleccionada;

	private List<String> idiomas;

	@Inject
	FormularioInternoService formIntService;

	@Inject
	private FormateadorFormularioService fmtService;

	/**
	 * Inicialización.
	 */
	@SuppressWarnings("unchecked")
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

		if (!mochilaDatos.isEmpty()) {
			idiomas = (List<String>) mochilaDatos.get(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO);
		}

		if (id == null) {
			data = new DisenyoFormulario();
		} else {
			data = formIntService.getFormularioInternoPaginas(Long.valueOf(id));
			literal = data.getTextoCabecera() != null
					? data.getTextoCabecera().getTraduccion(UtilJSF.getSessionBean().getLang(), idiomas)
					: null;
		}

	}

	/**
	 * Guardar.
	 */
	public void guardar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
		case EDICION:
			if (!contienePlantillaPorDefecto()) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.planilla.porDefecto"));
				return;
			}
			if (!contienePaginaFinal()) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.pagina.sinFinal"));
				return;
			}
			formIntService.updateFormularioInterno(data);

			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);

		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO);
	}

	private boolean contienePlantillaPorDefecto() {
		boolean contiene;
		if (data.getPlantillas() == null || data.getPlantillas().isEmpty()) {
			contiene = true;
		} else {
			contiene = false;
			for (final PlantillaFormulario plantilla : data.getPlantillas()) {
				if (plantilla != null && plantilla.isPorDefecto()) {
					contiene = true;
					break;
				}
			}
		}
		return contiene;
	}

	private boolean contienePaginaFinal() {
		boolean contiene;
		if (data.getPaginas() == null || data.getPaginas().isEmpty()) {
			contiene = true;
		} else {
			contiene = false;
			for ( PaginaFormulario pagina : data.getPaginas()) {
				if (pagina != null && pagina.isPaginaFinal()) {
					contiene = true;
					break;
				}
			}
		}
		return contiene;
	}


	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);

		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO);
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("propiedadesFormularioDialog");
	}

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setTextoCabecera(literales);
			setLiteral(literales.getTraduccion(UtilJSF.getSessionBean().getLang(), idiomas));
		}
	}

	/**
	 * Editar texto de cabecera.
	 *
	 *
	 */
	public void editarTextoCabecera() {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.valueOf(modoAcceso), data.getTextoCabecera(), getIdiomas(),
				getIdiomas());
	}

	/**
	 * Editar script
	 */
	public void editarDialogScriptPlantilla(final String tipoScript, final Script script) {
		final Map<String, String> maps = new HashMap<>();
		maps.put(TypeParametroVentana.TIPO_SCRIPT_FORMULARIO.toString(),
				UtilJSON.toJSON(TypeScriptFormulario.fromString(tipoScript)));
		maps.put(TypeParametroVentana.FORM_INTERNO_ACTUAL.toString(), this.id);
		maps.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), this.idFormularioTramite);
		maps.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);

		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));

		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.valueOf(modoAcceso), maps, true, 700);

	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptPlantilla(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptPlantilla((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Baja la pagina de posición.
	 */
	public void bajarPagina() {
		if (!verificarFilaSeleccionada(paginaSeleccionada))
			return;

		final int posicion = this.data.getPaginas().indexOf(this.paginaSeleccionada);
		if (posicion >= this.data.getPaginas().size() - 1) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final PaginaFormulario pagina = this.data.getPaginas().remove(posicion);
		this.data.getPaginas().add(posicion + 1, pagina);
	}

	/**
	 * Sube la pagina de posición.
	 */
	public void subirPagina() {
		if (!verificarFilaSeleccionada(paginaSeleccionada))
			return;

		final int posicion = this.data.getPaginas().indexOf(this.paginaSeleccionada);
		if (posicion <= 0) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final PaginaFormulario pagina = this.data.getPaginas().remove(posicion);
		this.data.getPaginas().add(posicion - 1, pagina);
	}

	public void nuevaPagina() {
		final PaginaFormulario pagina = new PaginaFormulario();
		pagina.setOrden(data.getPaginas().size() + 1);
		pagina.setIdentificador("P" + pagina.getOrden());
		this.data.getPaginas().add(pagina);
	}

	/** Consultar pagina. **/
	public void consultarPagina() {

		abrirPagina(TypeModoAcceso.CONSULTA);
	}

	/** Editar pagina. **/
	public void editarPagina() {
		abrirPagina(TypeModoAcceso.valueOf(modoAcceso));
	}

	/** Abrir dialog pagina formulario. **/
	public void abrirPagina(final TypeModoAcceso modo) {

		if (!verificarFilaSeleccionada(paginaSeleccionada)) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		UtilJSF.getSessionBean().getMochilaDatos().put(Constantes.CLAVE_MOCHILA_FORMULARIO,
				UtilJSON.toJSON(this.paginaSeleccionada));

		params.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), this.idFormularioTramite);
		params.put(TypeParametroVentana.FORM_INTERNO_ACTUAL.toString(), this.id);
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);

		UtilJSF.openDialog(DialogPaginaFormulario.class, modo, params, true, 430, 190);
	}

	/**
	 * Return dialogo pagina.
	 *
	 * @param event the event
	 */
	public void returnDialogoPagina(final SelectEvent event) {
		PaginaFormulario pagina = null;
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				pagina = (PaginaFormulario) respuesta.getResult();

				// Muestra dialogo
				final int posicion = this.data.getPaginas().indexOf(this.paginaSeleccionada);
				this.data.getPaginas().get(posicion).setPaginaFinal(pagina.isPaginaFinal());
				this.data.getPaginas().get(posicion).setScriptValidacion(pagina.getScriptValidacion());
				paginaSeleccionada.setIdentificador(pagina.getIdentificador());
				paginaSeleccionada.setPaginaFinal(pagina.isPaginaFinal());
				paginaSeleccionada.setScriptNavegacion(pagina.getScriptNavegacion());
				paginaSeleccionada.setScriptValidacion(pagina.getScriptValidacion());

				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}
	}

	/**
	 * elimina la pagina de posición.
	 */
	public void eliminarPagina() {
		if (!verificarFilaSeleccionada(paginaSeleccionada))
			return;

		// siempre tiene que haber una pagina
		if (data.getPaginas().size() == 1) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.numeroPaginasMinimo"));
			return;
		}

		final int posicion = this.data.getPaginas().indexOf(this.paginaSeleccionada);

		this.data.getPaginas().remove(posicion);
		paginaSeleccionada = null;
	}

	public void nuevaPlantilla() {
		UtilJSF.openDialog(DialogPlantillaFormulario.class, TypeModoAcceso.ALTA, null, true, 430, 230);
	}

	/**
	 * Return dialogo plantilla.
	 *
	 * @param event the event
	 */
	public void returnDialogoPlantilla(final SelectEvent event) {
		PlantillaFormulario plantilla = null;
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				plantilla = (PlantillaFormulario) respuesta.getResult();

				if (formateadorDuplicado(plantilla)) {
					addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.valor.duplicated"));
					return;
				}

				if (plantilla.isPorDefecto()) {
					plantillaLimpiarPorDefecto();
				}

				this.data.getPlantillas().add(plantilla);
				break;
			case EDICION:
				plantilla = (PlantillaFormulario) respuesta.getResult();

				if (formateadorDuplicado(plantilla)) {
					addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.valor.duplicated"));
					return;
				}

				if (!plantillaSeleccionada.isPorDefecto() && plantilla.isPorDefecto()) {
					plantillaLimpiarPorDefecto();
				}

				// Muestra dialogo
				final int posicion = this.data.getPlantillas().indexOf(this.plantillaSeleccionada);
				this.data.getPlantillas().remove(posicion);
				this.data.getPlantillas().add(posicion, plantilla);
				this.plantillaSeleccionada = plantilla;
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}
	}

	/** Consultar plantilla. **/
	public void consultarPlantilla() {
		abrirPlantilla(TypeModoAcceso.CONSULTA);
	}

	/** Editar plantilla. **/
	public void editarPlantilla() {
		abrirPlantilla(TypeModoAcceso.valueOf(modoAcceso));
	}

	/** Abrir dialog de plantilla formulario. **/
	private void abrirPlantilla(final TypeModoAcceso modo) {

		if (!verificarFilaSeleccionada(plantillaSeleccionada)) {
			return;
		}

		// Abrimos seleccion ficheros
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.plantillaSeleccionada));
		UtilJSF.openDialog(DialogPlantillaFormulario.class, modo, params, true, 430, 230);
	}

	/**
	 *
	 * elimina la plantilla
	 */
	public void eliminarPlantilla() {
		if (!verificarFilaSeleccionada(plantillaSeleccionada))
			return;

		final int posicion = this.data.getPlantillas().indexOf(this.plantillaSeleccionada);

		this.data.getPlantillas().remove(posicion);
		plantillaSeleccionada = null;
	}

	/**
	 * Limpia la propiedad de por defecto.
	 */
	private void plantillaLimpiarPorDefecto() {

		for (final PlantillaFormulario plantilla : data.getPlantillas()) {
			plantilla.setPorDefecto(false);
		}

	}

	private boolean formateadorDuplicado(final PlantillaFormulario pPlantillaEditada) {
		boolean duplicado = false;

		for (final PlantillaFormulario plantilla : data.getPlantillas()) {
			if (plantilla.getIdentificador().equals(pPlantillaEditada.getIdentificador())
					&& !plantilla.getCodigo().equals(pPlantillaEditada.getCodigo())) {
				duplicado = true;
				break;
			}
		}

		return duplicado;
	}

	/**
	 * Deshabilitar script plantilla.
	 *
	 * @return true, if successful
	 */
	public boolean getDeshabilitarScriptPlantilla() {
		return data.getPlantillas() != null ? this.data.getPlantillas().size() <= 1 : true;
	}

	public String getDescripcionFormateador(final Long pIdFmt) {
		return fmtService.getFormateadorFormulario(pIdFmt).getIdentificador();
	}

	public void editarFicheroPlantilla() {

		if (!verificarFilaSeleccionada(plantillaSeleccionada)) {
			return;
		}

		// En caso de la plantilla este sin guardar, guardamos (se ha pedido
		// confirmacion)
		if (plantillaSeleccionada.getCodigo() <= 0) {
			formIntService.updateFormularioInterno(data);
			// Refrescamos datos
			data = formIntService.getFormularioInternoPaginas(Long.valueOf(id));
			// Seleccionamos plantilla
			for (final PlantillaFormulario p : data.getPlantillas()) {
				if (p.getIdentificador().equals(plantillaSeleccionada.getIdentificador())) {
					plantillaSeleccionada = p;
				}
			}
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(plantillaSeleccionada.getCodigo()));
		UtilJSF.openDialog(DialogPlantillaIdiomaFormulario.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 430,
				170);
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada(final ModelApi filaseleccionada) {
		boolean filaSeleccionada = true;

		if (filaseleccionada == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Sin implementar.
	 */
	public void sinImplementar() {
		addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param pId el nuevo valor de id
	 */
	public void setId(final String pId) {
		this.id = pId;
	}

	/**
	 * Obtiene el valor de data.
	 *
	 * @return el valor de data
	 */
	public DisenyoFormulario getData() {
		return data;
	}

	/**
	 * Establece el valor de data.
	 *
	 * @param data el nuevo valor de data
	 */
	public void setData(final DisenyoFormulario data) {
		this.data = data;
	}

	/**
	 * Obtiene el valor de literal.
	 *
	 * @return el valor de literal
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Establece el valor de literal.
	 *
	 * @param literal el nuevo valor de literal
	 */
	public void setLiteral(final String literal) {
		this.literal = literal;
	}

	public PaginaFormulario getPaginaSeleccionada() {
		return paginaSeleccionada;
	}

	public void setPaginaSeleccionada(final PaginaFormulario paginaSeleccionada) {
		this.paginaSeleccionada = paginaSeleccionada;
	}

	public PlantillaFormulario getPlantillaSeleccionada() {
		return plantillaSeleccionada;
	}

	public void setPlantillaSeleccionada(final PlantillaFormulario plantillaSeleccionada) {
		this.plantillaSeleccionada = plantillaSeleccionada;
	}

	public List<String> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

	public FormateadorFormularioService getFmtService() {
		return fmtService;
	}

	public void setFmtService(final FormateadorFormularioService fmtService) {
		this.fmtService = fmtService;
	}

	/**
	 * @return the idFormularioTramite
	 */
	public String getIdFormularioTramite() {
		return idFormularioTramite;
	}

	/**
	 * @param idFormularioTramite the idFormularioTramite to set
	 */
	public void setIdFormularioTramite(final String idFormularioTramite) {
		this.idFormularioTramite = idFormularioTramite;
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

}
