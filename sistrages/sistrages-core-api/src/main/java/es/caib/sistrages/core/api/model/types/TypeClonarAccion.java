package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar la acción a realizar.
 *
 * @author Indra
 *
 */
public enum TypeClonarAccion {

	/** NADA. **/
	NADA("N"),
	/** CREAR. **/
	CREAR("C"),
	/** REEMPLAZAR. **/
	REEMPLAZAR("R"),
	/** REEMPLAZAR. **/
	MANTENER("M");

	/** valor. **/
	private String valor;

	/**
	 * Constructor
	 *
	 */
	private TypeClonarAccion(final String iValor) {
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
	public static TypeClonarAccion fromString(final String accion) {
		TypeClonarAccion typeAccion = null;
		if (accion != null) {
			for (final TypeClonarAccion tAccion : TypeClonarAccion.values()) {
				if (tAccion.valor.equals(accion)) {
					typeAccion = tAccion;
					break;
				}
			}
		}
		return typeAccion;
	}
}
