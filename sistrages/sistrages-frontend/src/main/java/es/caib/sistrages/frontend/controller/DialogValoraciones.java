package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.IncidenciaValoracion;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogValoraciones extends DialogControllerBase {

	@Inject
	private EntidadService entidadService;

	/** Datos elemento. */
	private List<IncidenciaValoracion> listaDatos;

	/** Data Seleccionada. **/
	private IncidenciaValoracion datoSeleccionado;

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
			addMessageContext(TypeNivelGravedad.INFO, message);
			// Refrescamos datos
			buscar();
		}

	}

	/**
	 * Crea nueva Valoracion.
	 */
	public void nuevaValoracion() {
		UtilJSF.openDialog(DialogValoracion.class, TypeModoAcceso.ALTA, null, true, 600, 150);

	}

	/**
	 * Edita una Valoracion.
	 */
	public void editarValoracion() {

		if (!verificarFilaSeleccionada()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(datoSeleccionado.getCodigo()));
		UtilJSF.openDialog(DialogValoracion.class, TypeModoAcceso.EDICION, params, true, 600, 250);

	}

	/**
	 * Quita un Valoracion.
	 */
	public void quitarValoracion() {
		if (!verificarFilaSeleccionada())
			return;

		// Eliminamos
		entidadService.removeValoracion(datoSeleccionado.getCodigo());

		// Refrescamos datos
		buscar();

		// Mostramos mensaje
		addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));

	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.datoSeleccionado == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Buscar datos.
	 */
	private void buscar() {
		// lista
		listaDatos = entidadService.getValoraciones(UtilJSF.getIdEntidad());
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
	 * Abrir ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("valoraciones");
	}

	/**
	 * @return the data
	 */
	public List<IncidenciaValoracion> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setListaDatos(final List<IncidenciaValoracion> data) {
		this.listaDatos = data;
	}

	/**
	 * @return the dataSeleccionada
	 */
	public IncidenciaValoracion getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param dataSeleccionada
	 *            the dataSeleccionada to set
	 */
	public void setDatoSeleccionado(final IncidenciaValoracion datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
