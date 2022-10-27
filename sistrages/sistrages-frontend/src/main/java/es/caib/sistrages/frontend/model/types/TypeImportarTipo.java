package es.caib.sistrages.frontend.model.types;

import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Tipo para indicar el tipo: dominio o tramite.
 *
 * @author Indra
 *
 */
public enum TypeImportarTipo {

	/** DOMINIO. **/
	DOMINIO("N"),
	/** TRAMITE. **/
	TRAMITE("T"),
	/** SECCION REUTILIZABLE **/
	SECCION_REUTILIZABLE("S");

	/** valor. **/
	private String valor;

	/**
	 * Constructor
	 *
	 */
	private TypeImportarTipo(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Literal.
	 */
	public String getLiteral() {
		return UtilJSF.getLiteral("typeImportar.tipo." + this.valor);
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
	public static TypeImportarTipo fromString(final String accion) {
		TypeImportarTipo typeAccion = null;
		if (accion != null) {
			for (final TypeImportarTipo tAccion : TypeImportarTipo.values()) {
				if (tAccion.valor.equals(accion)) {
					typeAccion = tAccion;
					break;
				}
			}
		}
		return typeAccion;
	}
}
