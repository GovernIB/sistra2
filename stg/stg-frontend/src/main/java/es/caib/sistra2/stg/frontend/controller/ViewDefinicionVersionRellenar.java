package es.caib.sistra2.stg.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistra2.stg.core.api.model.Dominio;
import es.caib.sistra2.stg.frontend.model.types.TypeNivelGravedad;
import es.caib.sistra2.stg.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de dominios empleados.
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
	private Dominio datoSeleccionado;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionDominios.
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
	public Dominio getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado
	 *            el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final Dominio datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
