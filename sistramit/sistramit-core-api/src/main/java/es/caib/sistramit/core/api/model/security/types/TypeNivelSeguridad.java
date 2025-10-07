package es.caib.sistramit.core.api.model.security.types;

/**
 * Tipos nivel seguridad.
 *
 * @author Indra
 *
 */
public enum TypeNivelSeguridad {
	/**
	 * Bajo (Código String: BAJO).
	 * - Nivel EIDAS: bajo
	 * - Métodos autenticación: Cl@ve Móvil (nivel bajo permite registro básico y avanzado) / Cl@ve Permanente (nivel bajo permite registro básico y avanzado) / Certificado (nivel bajo permite certificado SW y HW)
	 */
	BAJO("BAJO"),
	/**
	 *  Sustancial (Código String: 2).
	 *  - Nivel EIDAS: sustancial
	 *  - Métodos autenticación: Cl@ve Móvil (nivel sustancial solo permite registro avanzado) / Cl@ve Permanente (nivel sustancial solo permite registro avanzado) / Certificado (nivel sustancial permite certificado SW y HW)
	 */
	SUSTANCIAL("SUSTANCIAL"),
	/**
	 * Sustancial con certificado (Código String: 4).
	 * - Nivel EIDAS: sustancial
	 * - Métodos autenticación: Certificado (nivel sustancial permite certificado SW y HW)
	 */
	SUSTANCIAL_CERTIFICADO("SUSTANCIAL_CERTIFICADO"),
	/**
	 * Alto (Código String: 4).
	 * - Nivel EIDAS: alto
	 * - Métodos autenticación: Certificado (nivel alto solo permite certificados HW)
	 */
	ALTO("ALTO");

	/**
	 * Valor como string.
	 */
	private final String stringValueNivelSeguridad;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypeNivelSeguridad(final String value) {
		stringValueNivelSeguridad = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueNivelSeguridad;
	}

	/**
	 * Método para From string de la clase TypeAutenticacion.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type autenticacion
	 */
	public static TypeNivelSeguridad fromString(final String text) {
		TypeNivelSeguridad respuesta = null;
		if (text != null) {
			for (final TypeNivelSeguridad b : TypeNivelSeguridad.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

	/**
	 * Compara QAA para saber si es superior a otro QAA
	 *
	 * @param qaaCompare
	 *                       otro QAA
	 * @return boolean
	 */
	public boolean esSuperior(final TypeNivelSeguridad qaaCompare) {
		// Pasamos a entero para comparar
		return Integer.parseInt(this.toString()) > Integer.parseInt(qaaCompare.toString());
	}

}
