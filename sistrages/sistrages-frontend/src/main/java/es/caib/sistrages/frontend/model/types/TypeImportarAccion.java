package es.caib.sistrages.frontend.model.types;

import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Tipo para indicar la acción a realizar.
 *
 * @author Indra
 *
 */
public enum TypeImportarAccion {

	/** NADA. **/
	NADA("N"),
	/** CREAR. **/
	CREAR("C"),
	/** REVISADO. */
	REVISADO("V"),
	/** REEMPLAZAR. **/
	REEMPLAZAR("R"),
	/** REEMPLAZAR. **/
	MANTENER("M"),
	/** ERROR **/
	ERROR("E"),
	/** Pendiente. **/
	PENDIENTE("P"),;

	/** valor. **/
	private String valor;

	/**
	 * Constructor
	 *
	 */
	private TypeImportarAccion(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Literal.
	 */
	public String getLiteral() {
		return UtilJSF.getLiteral("typeImportar.accion." + this.valor);
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
	public static TypeImportarAccion fromString(final String accion) {
		TypeImportarAccion typeAccion = null;
		if (accion != null) {
			for (final TypeImportarAccion tAccion : TypeImportarAccion.values()) {
				if (tAccion.valor.equals(accion)) {
					typeAccion = tAccion;
					break;
				}
			}
		}
		return typeAccion;
	}
}
