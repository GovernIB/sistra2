package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Disenyo.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDisenyo extends ViewControllerBase {

	/** url de la opcion del arbol seleccionada **/
	private String opcionUrl;

	/** Tagname. **/
	private String tagname;

	/** id. */
	private String nodeId;

	/**
	 * Crea una nueva instancia de view definicion version.
	 */
	public ViewDisenyo() {
		super();
	}

	/**
	 * Editar.
	 */
	public void editar() {

		if (tagname == null) {
			opcionUrl = null;
			return;
		}

		if ("input".equals(tagname)) {
			opcionUrl = "/secure/app/viewDisenyoTextbox.xhtml";
		} else if ("select".equals(tagname)) {
			opcionUrl = "/secure/app/viewDisenyoSelect.xhtml";
		} else {
			opcionUrl = null;
		}

	}

	/**
	 * Inicializacion.
	 */
	public void init() {
		/* titulo pantalla */
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		// opcionUrl = "/secure/app/viewDisenyoTextbox.xhtml";

	}

	/**
	 * Sin implementar.
	 */
	public void sinImplementar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
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

	/**
	 * @return the tagname
	 */
	public String getTagname() {
		return tagname;
	}

	/**
	 * @param tagname
	 *            the tagname to set
	 */
	public void setTagname(final String tagname) {
		this.tagname = tagname;
	}

	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId
	 *            the nodeId to set
	 */
	public void setNodeId(final String nodeId) {
		this.nodeId = nodeId;
	}

}
