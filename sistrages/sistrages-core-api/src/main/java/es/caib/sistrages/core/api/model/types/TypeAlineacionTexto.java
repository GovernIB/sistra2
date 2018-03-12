package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar alineacion texto.
 *
 * @author Indra
 *
 */
public enum TypeAlineacionTexto {

	/**
	 * Derecha.
	 */
	DERECHA("D"),
	/**
	 * Centro.
	 */
	CENTRO("C"),
	/**
	 * Izquierda.
	 */
	IZQUIERDA("I");

	private String valor;

	TypeAlineacionTexto(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static String fromEnum(final TypeAlineacionTexto tipo) {
		return tipo.valor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public String fromEnum() {
		return this.valor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public TypeAlineacionTexto toEnum(final String text) {
		TypeAlineacionTexto respuesta = null;
		if (text != null) {
			for (final TypeAlineacionTexto b : TypeAlineacionTexto.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return valor;
	}

}
