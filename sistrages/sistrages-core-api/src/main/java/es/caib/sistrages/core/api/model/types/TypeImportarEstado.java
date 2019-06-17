package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar la acción a realizar.
 *
 * @author Indra
 *
 */
public enum TypeImportarEstado {

	/** REVISADO. */
	REVISADO("V"),
	/** ERROR **/
	ERROR("E"),
	/** Pendiente. **/
	PENDIENTE("P");

	/** valor. **/
	private String valor;

	/**
	 * Constructor
	 *
	 */
	private TypeImportarEstado(final String iValor) {
		this.valor = iValor;
	}

	@Override
	public String toString() {
		return this.valor;
	}

	/**
	 * Obtiene el type a partir del string.
	 *
	 * @param accion
	 * @return
	 */
	public static TypeImportarEstado fromString(final String accion) {
		TypeImportarEstado typeAccion = null;
		if (accion != null) {
			for (final TypeImportarEstado tAccion : TypeImportarEstado.values()) {
				if (tAccion.valor.equals(accion)) {
					typeAccion = tAccion;
					break;
				}
			}
		}
		return typeAccion;
	}
}
