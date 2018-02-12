package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Disenyo.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDisenyoTextbox extends ViewControllerBase {

	/** url de la opcion del arbol seleccionada **/
	private String opcionUrl;

	/**
	 * Crea una nueva instancia de view definicion version.
	 */
	public ViewDisenyoTextbox() {
		super();
	}

	/**
	 * Inicializacion.
	 */
	public void init() {
		/* titulo pantalla */
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		opcionUrl = "/secure/app/viewDisenyoTextbox.xhtml";

	}

	/**
	 * Obtiene el valor de opcionUrl.
	 *
	 * @return el valor de opcionUrl
	 */
	public String getOpcionUrl() {
		return opcionUrl;
	}

	/**
	 * Set opcion url.
	 * 
	 * @param opcion
	 */
	public void setOpcionUrl(final String opcion) {
		this.opcionUrl = opcion;
	}

}
