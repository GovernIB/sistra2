package es.caib.sistrages.core.api.model.types;

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
	/** REEMPLAZAR. **/
	REEMPLAZAR("R"),
	/** REEMPLAZAR. **/
	MANTENER("M"),
	/** Incrementar release (sólo para la versión de trámite) */
	INCREMENTAR("I"),
	/** Importar, cuando no existe el trámite (se comporta como el reemplazar) **/
	IMPORTAR("X"),
	/** Seleccionar. **/
	SELECCIONAR("S");

	/** valor. **/
	private String valor;

	/**
	 * Constructor
	 *
	 */
	private TypeImportarAccion(final String iValor) {
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
