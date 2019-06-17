package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar la acción a realizar.
 *
 * @author Indra
 *
 */
public enum TypeImportarExiste {

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
	private TypeImportarExiste(final String iValor) {
		this.valor = iValor;
	}

	@Override
	public String toString() {
		return this.valor;
	}
}
