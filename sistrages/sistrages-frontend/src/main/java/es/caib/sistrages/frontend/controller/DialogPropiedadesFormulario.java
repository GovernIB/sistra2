package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.ModelApi;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogPropiedadesFormulario extends DialogControllerBase {

	/**
	 * id
	 */
	private String id;

	/**
	 * Datos elemento.
	 */
	private FormularioInterno data;

	private PaginaFormulario paginaSeleccionada;

	private String literal;

	private PlantillaFormulario plantillaSeleccionada;

	@Inject
	FormularioInternoService formIntService;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		if (id == null) {
			data = new FormularioInterno();
		} else {
			data = formIntService.getFormularioInternoPaginas(Long.valueOf(id));
			literal = data.getTextoCabecera() != null
					? data.getTextoCabecera().getTraduccion(UtilJSF.getSessionBean().getLang())
					: null;
		}

	}

	/**
	 * guardar.
	 */
	public void guardar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
		case EDICION:
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
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setTextoCabecera(literales);
			setLiteral(literales.getTraduccion(UtilJSF.getSessionBean().getLang()));
		}
	}

	/**
	 * Editar texto de cabecera.
	 *
	 *
	 */
	public void editarTextoCabecera() {
		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.valueOf(modoAcceso), data.getTextoCabecera(), idiomas,
				idiomas);
	}

	/**
	 * Método que se encarga de cargar el dialog de carga dependiendo de si existe
	 * ya el script o no.
	 *
	 * @param id
	 */
	public void editarDialogScriptPlantilla() {

		if (id == null) {
			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.ALTA, null, true, 950, 700);
		} else {
			final Map<String, String> params = new HashMap<>();
			if (data.getScriptPlantilla() == null) {
				params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(new Script()));
			} else {
				params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(data.getScriptPlantilla()));
			}
			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 950, 700);
		}
	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event
	 *            respuesta dialogo
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final PaginaFormulario pagina = this.data.getPaginas().remove(posicion);
		this.data.getPaginas().add(posicion - 1, pagina);
	}

	public void nuevaPagina() {
		final PaginaFormulario pagina = new PaginaFormulario();
		pagina.setOrden(data.getPaginas().size() + 1);
		this.data.getPaginas().add(pagina);
	}

	/**
	 * elimina la pagina de posición.
	 */
	public void eliminarPagina() {
		if (!verificarFilaSeleccionada(paginaSeleccionada))
			return;

		// siempre tiene que haber una pagina
		if (data.getPaginas().size() == 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.numeroPaginasMinimo"));
			return;
		}

		final int posicion = this.data.getPaginas().indexOf(this.paginaSeleccionada);

		this.data.getPaginas().remove(posicion);
		paginaSeleccionada = null;
	}

	public void nuevaPlantilla() {
		final PlantillaFormulario plantilla = new PlantillaFormulario();
		this.data.getPlantillas().add(plantilla);
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
	 * Deshabilitar script plantilla.
	 *
	 * @return true, if successful
	 */
	public boolean getDeshabilitarScriptPlantilla() {
		return data.getPlantillas() != null ? this.data.getPlantillas().size() <= 1 : true;
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada(final ModelApi filaseleccionada) {
		boolean filaSeleccionada = true;

		if (filaseleccionada == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Sin implementar.
	 */
	public void sinImplementar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
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
	 * @param pId
	 *            el nuevo valor de id
	 */
	public void setId(final String pId) {
		this.id = pId;
	}

	/**
	 * Obtiene el valor de data.
	 *
	 * @return el valor de data
	 */
	public FormularioInterno getData() {
		return data;
	}

	/**
	 * Establece el valor de data.
	 *
	 * @param data
	 *            el nuevo valor de data
	 */
	public void setData(final FormularioInterno data) {
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
	 * @param literal
	 *            el nuevo valor de literal
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

}
