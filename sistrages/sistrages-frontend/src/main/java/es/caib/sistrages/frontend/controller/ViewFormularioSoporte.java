package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class ViewFormularioSoporte extends DialogControllerBase {

	@Inject
	private EntidadService entidadService;

	/** Datos elemento. */
	private List<FormularioSoporte> listaDatos;

	/** Data Seleccionada. **/
	private FormularioSoporte datoSeleccionado;

	/**
	 * Inicialización.
	 *
	 */
	public void init() {
		buscar();
	}

	/**
	 * Retorno dialogo de los botones de propiedades.
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
			// Refrescamos datos
			buscar();
		}

	}

	/**
	 * Crea nueva propiedad.
	 */
	public void nuevoFormulario() {
		UtilJSF.openDialog(DialogFormularioSoporte.class, TypeModoAcceso.ALTA, null, true, 600, 250);
	}

	/**
	 * Edita una propiedad.
	 */
	public void editarFormulario() {

		if (!verificarFilaSeleccionada())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(datoSeleccionado.getCodigo()));
		UtilJSF.openDialog(DialogFormularioSoporte.class, TypeModoAcceso.EDICION, params, true, 600, 250);
	}

	/**
	 * Quita un formulario.
	 */
	public void quitarFormulario() {
		if (!verificarFilaSeleccionada())
			return;

		// Verificamos que no sea la última y que este habilitado en la entidad el
		// formulario de soporte
		final Entidad entidad = entidadService.loadEntidad(UtilJSF.getIdEntidad());
		if (entidad.isFormularioIncidenciasHabilitado() && listaDatos.size() == 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogFormularioSoporte.error.noOpciones"));
			return;
		}

		// Eliminamos
		if (entidadService.removeOpcionFormularioSoporte(datoSeleccionado.getCodigo())) {
			// Refrescamos datos
			buscar();
			// Mostramos mensaje
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}

	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.datoSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Buscar datos.
	 */
	private void buscar() {
		// lista
		listaDatos = entidadService.listOpcionesFormularioSoporte(UtilJSF.getIdEntidad());
		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	/**
	 * Cerrar.
	 */
	public void cerrar() {
		UtilJSF.closeDialog(null);
	}

	/**
	 * @return the data
	 */
	public List<FormularioSoporte> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setListaDatos(final List<FormularioSoporte> data) {
		this.listaDatos = data;
	}

	/**
	 * @return the dataSeleccionada
	 */
	public FormularioSoporte getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param dataSeleccionada
	 *            the dataSeleccionada to set
	 */
	public void setDatoSeleccionado(final FormularioSoporte datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
