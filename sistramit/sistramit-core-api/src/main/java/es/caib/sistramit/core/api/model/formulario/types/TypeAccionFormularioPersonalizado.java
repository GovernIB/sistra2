package es.caib.sistramit.core.api.model.formulario.types;

/**
 * Tipos de acción formulario personalizado.
 *
 * @author Indra
 *
 */
public enum TypeAccionFormularioPersonalizado implements TypeAccionFormulario {
	/**
	 * Acción personalizada (Código String: personalizada).
	 */
	PERSONALIZADA("personalizada");

	/**
	 * Valor como string.
	 */
	private final String stringValueCampo;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeAccionFormularioPersonalizado(final String value) {
		stringValueCampo = value;
	}

	@Override
	public String toString() {
		return stringValueCampo;
	}

}
