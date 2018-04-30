package es.caib.sistramit.core.api.model.comun.types;

/**
 * Nivel excepción. Indica si es FATAL (invalida el flujo) o CONTINUABLE
 * (permite continuar el flujo).
 *
 * @author Indra
 *
 */
public enum TypeNivelExcepcion {
	/**
	 * Error fatal. No deja continuar el flujo. (Código String: f).
	 */
	FATAL("f"),
	/**
	 * Alerta. Permite continuar el flujo (Código String: w).
	 */
	WARNING("w"),
	/**
	 * Error. Permite continuar el flujo (Código String: e).
	 */
	ERROR("e");

	/**
	 * Valor como string.
	 */
	private final String stringValueNivelExcepcion;

	/**
	 * Constructor.
	 *
	 * @param valueSiNo
	 *            Valor como string.
	 */
	private TypeNivelExcepcion(final String valueSiNo) {
		stringValueNivelExcepcion = valueSiNo;
	}

	@Override
	public String toString() {
		return stringValueNivelExcepcion;
	}

}
