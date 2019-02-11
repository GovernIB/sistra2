package es.caib.sistramit.core.api.model.formulario.types;

/**
 * Tipo estado validación.
 *
 * @author Indra
 *
 */
public enum TypeValidacionEstado {

	// TODO REPASAR ESTADOS VALIDACION

	CORRECTO("correcto"), ERROR("error"), ATENCION("atencion");

	/**
	 * Valor como string.
	 */
	private final String stringValueEstado;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeValidacionEstado(final String value) {
		stringValueEstado = value;
	}

	@Override
	public String toString() {
		return stringValueEstado;
	}

}
