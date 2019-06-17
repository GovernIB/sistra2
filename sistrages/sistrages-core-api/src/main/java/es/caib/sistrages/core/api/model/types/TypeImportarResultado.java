package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar la acción a realizar.
 *
 * @author Indra
 *
 */
public enum TypeImportarResultado {

	/** OK **/
	OK("fa fa-check fa-2x", "0, 204, 0", "typeImportar.resultado.OK"),
	/** INFO **/
	INFO("fa fa-info-circle fa-2x", "0, 0, 214", "typeImportar.resultado.INFO"),
	/** WARNING. **/
	WARNING("fa fa-warning fa-2x", "214, 214, 0", "typeImportar.resultado.WARNING"),
	/** ERROR. **/
	ERROR("fa fa-times fa-2x", "255, 19, 19", "typeImportar.resultado.ERROR");

	/** Css **/
	private String css;

	/** Icon. **/
	private String icon;

	/** Literal. **/
	private String literal;

	/**
	 * Constructor
	 *
	 * @param iCss
	 * @param iIcon
	 * @param iLiteral
	 */
	private TypeImportarResultado(final String iIcon, final String iCss, final String iLiteral) {
		this.css = iCss;
		this.icon = iIcon;
		this.literal = iLiteral;
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
	 * @return the literal
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * @param literal the literal to set
	 */
	public void setLiteral(final String literal) {
		this.literal = literal;
	}

	/**
	 * Is warning.
	 *
	 * @return
	 */
	public boolean isWarning() {
		return this == TypeImportarResultado.WARNING;
	}

	/**
	 * Comprueba si es error o warning
	 *
	 * @return
	 */
	public boolean isErrorOrWarning() {
		return this == TypeImportarResultado.ERROR || this == TypeImportarResultado.WARNING;
	}

	/**
	 * Comprueba si es ok o warning
	 *
	 * @return
	 */
	public boolean isWarningOrOk() {
		return this == TypeImportarResultado.OK || this == TypeImportarResultado.WARNING;
	}

	/**
	 * Comprueba si es error
	 *
	 * @return
	 */
	public boolean isError() {
		return this == TypeImportarResultado.ERROR;
	}

}
