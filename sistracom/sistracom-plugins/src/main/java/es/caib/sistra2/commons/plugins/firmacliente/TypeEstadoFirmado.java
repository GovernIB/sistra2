package es.caib.sistra2.commons.plugins.firmacliente;

/**
 * Estado de la sesión de firma. Segun la doc. oficial:
 * <ul>
 * <li>FirmaSimpleStatus.STATUS_INITIALIZING: = 0;</li>
 * <li>FirmaSimpleStatus.STATUS_IN_PROGRESS: = 1;</li>
 * <li>FirmaSimpleStatus.STATUS_FINAL_ERROR: = -1;</li>
 * <li>FirmaSimpleStatus.STATUS_CANCELLED: = -2;</li>
 * <li>FirmaSimpleStatus.STATUS_FINAL_OK: = 2;</li>
 * </ul>
 *
 * @author Indra
 *
 */
public enum TypeEstadoFirmado {

	/** Inicializado el firmado. **/
	INICIALIZADO(0),
	/** En progreso. **/
	EN_PROGRESO(1),
	/** Finalizado con error. */
	FINALIZADO_CON_ERROR(-1),
	/** Cancelado. **/
	CANCELADO(-2),
	/** Finalizado Ok. **/
	FINALIZADO_OK(2);

	/**
	 * Valor como entero.
	 */
	private final int valor;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeEstadoFirmado(final int value) {
		valor = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(valor);
	}

	/**
	 * Método para From string de la clase TypeEstadoFirmado.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type estado firmado
	 */
	public static TypeEstadoFirmado fromString(final String text) {
		TypeEstadoFirmado respuesta = null;
		if (text != null) {
			for (final TypeEstadoFirmado b : TypeEstadoFirmado.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

	/**
	 * Método para From string de la clase TypeEstadoFirmado.
	 *
	 * @param text
	 *            Parámetro entero
	 * @return el type estado firmado
	 */
	public static TypeEstadoFirmado fromInt(final int iValor) {
		TypeEstadoFirmado respuesta = null;
		for (final TypeEstadoFirmado b : TypeEstadoFirmado.values()) {
			if (iValor == b.valor) {
				respuesta = b;
				break;
			}
		}

		return respuesta;
	}
}
