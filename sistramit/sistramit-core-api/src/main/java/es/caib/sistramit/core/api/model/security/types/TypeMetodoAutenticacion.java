package es.caib.sistramit.core.api.model.security.types;

/**
 * Método de autenticación.
 *
 * @author Indra
 *
 */
public enum TypeMetodoAutenticacion {
	/**
	 * ANONIMO.
	 */
	ANONIMO("ANONIMO"),
	/**
	 * CERTIFICADO_CLAVE.
	 */
	CLAVE_CERTIFICADO("CLAVE_CERTIFICADO"),
	/**
	 * CLAVE_PIN.
	 */
	CLAVE_PIN("CLAVE_PIN"),
	/**
	 * CLAVE_PERMANENTE.
	 */
	CLAVE_PERMANENTE("CLAVE_PERMANENTE");

	/**
	 * Valor como string.
	 */
	private final String stringValueMetodoAuth;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeMetodoAutenticacion(final String value) {
		stringValueMetodoAuth = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueMetodoAuth;
	}

	/**
	 * Convierte string a TypeValor.
	 *
	 * @param text
	 *            string
	 * @return TypeValor
	 */
	public static TypeMetodoAutenticacion fromString(final String text) {
		TypeMetodoAutenticacion respuesta = null;
		if (text != null) {
			for (final TypeMetodoAutenticacion b : TypeMetodoAutenticacion.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

	/**
	 * Indica si es un metodo de Cl@ve.
	 *
	 * @return true si es un metodo de Cl@ve.
	 */
	public boolean isClave() {
		return (this == CLAVE_CERTIFICADO || this == CLAVE_PERMANENTE || this == CLAVE_PIN);
	}

}
