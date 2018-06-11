package es.caib.sistrages.frontend.model.types;

import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Tipo para indicar la acción a realizar.
 *
 * @author Indra
 *
 */
public enum TypeImportarEstado {

	/** EXISTE. **/
	EXISTE("E"),
	/** NO EXISTE. **/
	NO_EXISTE("N");

	/** valor. **/
	private String valor;

	/**
	 * Constructor
	 *
	 */
	private TypeImportarEstado(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Literal.
	 */
	public String getLiteral() {
		return UtilJSF.getLiteral("typeImportar.estado." + this.valor);
	}

	@Override
	public String toString() {
		return this.valor;
	}
}
