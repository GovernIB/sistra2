package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de dominios empleados.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionDominios extends ViewControllerBase {

	/**
	 * Dato seleccionado en la lista.
	 */
	private Dominio datoSeleccionado;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionDominios.
	 */
	public ViewDefinicionVersionDominios() {
		super();
	}

	public void init() {
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void anyadir() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf("1"));
		UtilJSF.openDialog(DialogDefinicionVersionDominios.class, TypeModoAcceso.EDICION, params, true, 950, 450);
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
