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

	private TypeAlineacionTexto(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeAlineacionTexto fromString(final String text) {
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

	@Override
	public String toString() {
		return valor;
	}

}
