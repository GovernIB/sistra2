package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar la acción a realizar.
 *
 * @author Indra
 *
 */
public enum TypeImportarResultado {

	/** OK **/
	OK("fa fa-check fa-2x", "0, 204, 0"),
	/** INFO **/
	INFO("fa fa-info-circle fa-2x", "69, 69, 255"),
	/** WARNING. **/
	WARNING("fa fa-warning fa-2x", "214, 214, 0"),
	/** ERROR. **/
	ERROR("fa fa-times fa-2x", "255, 19, 19");

	/** Css **/
	private String css;

	/** Icon. **/
	private String icon;

	/**
	 * Constructor
	 *
	 * @param iCss
	 * @param iIcon
	 */
	private TypeImportarResultado(final String iIcon, final String iCss) {
		this.css = iCss;
		this.icon = iIcon;
	}

	/**
	 * Get css.
	 *
	 * @return
	 */
	public String getCss() {
		return "color:rgb(" + css + ")";
	}

	/**
	 * Get icon.
	 *
	 * @return
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Is warning.
	 *
	 * @return
	 */
	public boolean isWarning() {
		return this == TypeImportarResultado.WARNING;
	}
}
