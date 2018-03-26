package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class ViewFormularioSoporte extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento. */
	private List<FormularioSoporte> data;

	/** Dato elemento en formato JSON. **/
	private String iData;

	/** Data Seleccionada. **/
	private FormularioSoporte dataSeleccionado;

	/**
	 * Inicialización.
	 *
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new ArrayList<>();

		} else {
			data = (List<FormularioSoporte>) UtilJSON.fromListJSON(iData, FormularioSoporte.class);
		}

	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				// Refrescamos datos
				final FormularioSoporte formulario = (FormularioSoporte) respuesta.getResult();
				this.data.add(formulario);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				// Actualizamos fila actual
				final FormularioSoporte propiedadEdicion = (FormularioSoporte) respuesta.getResult();
				// Muestra dialogo
				final int posicion = this.data.indexOf(this.dataSeleccionado);

				this.data.remove(posicion);
				this.data.add(posicion, propiedadEdicion);
				this.dataSeleccionado = propiedadEdicion;

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

	/**
	 * Crea nueva propiedad.
	 */
	public void nuevoFormulario() {
		UtilJSF.openDialog(DialogFormularioSoporte.class, TypeModoAcceso.ALTA, null, true, 590, 250);
	}

	/**
	 * Edita una propiedad.
	 */
	public void editarFormulario() {

		if (!verificarFilaSeleccionada())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.dataSeleccionado));

		UtilJSF.openDialog(DialogFormularioSoporte.class, TypeModoAcceso.EDICION, params, true, 590, 250);
	}

	/**
	 * Quita un formulario.
	 */
	public void quitarFormulario() {
		if (!verificarFilaSeleccionada())
			return;

		this.data.remove(this.dataSeleccionado);

	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.dataSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			/*
			 * if (dominioGlobalService.load(data.getCodigo()) != null) {
			 * UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
			 * "Ya existe dato con ese codigo"); return; } dominioGlobalService.add(data);
			 */

			break;
		case EDICION:
			// dominioGlobalService.update(data);
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public List<FormularioSoporte> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final List<FormularioSoporte> data) {
		this.data = data;
	}

	/**
	 * @return the dataSeleccionada
	 */
	public FormularioSoporte getDataSeleccionada() {
		return dataSeleccionado;
	}

	/**
	 * @param dataSeleccionada
	 *            the dataSeleccionada to set
	 */
	public void setDataSeleccionada(final FormularioSoporte dataSeleccionada) {
		this.dataSeleccionado = dataSeleccionada;
	}

	/**
	 * @return the iData
	 */
	public String getiData() {
		return iData;
	}

	/**
	 * @param iData
	 *            the iData to set
	 */
	public void setiData(final String iData) {
		this.iData = iData;
	}

}
