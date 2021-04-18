package es.caib.sistramit.core.api.model.formulario.types;

/**
 * Tipos de acción formulario normalizado.
 *
 * @author Indra
 *
 */
public enum TypeAccionFormularioNormalizado implements TypeAccionFormulario {

	/**
	 * Acción finalizar (Código String: finalizar).
	 */
	FINALIZAR("finalizar"),
	/**
	 * Acción anterior (Código String: anterior).
	 */
	ANTERIOR("anterior"),
	/**
	 * Acción siguiente (Código String: siguiente).
	 */
	SIGUIENTE("siguiente");

	/**
	 * Valor como string.
	 */
	private final String stringValueCampo;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypeAccionFormularioNormalizado(final String value) {
		stringValueCampo = value;
	}

	@Override
	public String toString() {
		return stringValueCampo;
	}

}
