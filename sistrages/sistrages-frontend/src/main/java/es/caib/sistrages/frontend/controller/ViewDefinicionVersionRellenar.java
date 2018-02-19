package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Formulario;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de rellenar.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionRellenar extends ViewControllerBase {

	/**
	 * Dato seleccionado en la lista.
	 */
	private Formulario datoSeleccionado;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionRellenar.
	 */
	public ViewDefinicionVersionRellenar() {
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
	public Formulario getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado
	 *            el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final Formulario datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
