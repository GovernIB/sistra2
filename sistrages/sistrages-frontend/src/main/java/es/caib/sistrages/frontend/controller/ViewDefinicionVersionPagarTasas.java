package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de pagar tasas.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionPagarTasas extends ViewControllerBase {

	/**
	 * Dato seleccionado en la lista.
	 */
	private Tasa datoSeleccionado;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionPagarTasas.
	 */
	public ViewDefinicionVersionPagarTasas() {
		super();
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void nuevo() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public Tasa getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado
	 *            el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final Tasa datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
