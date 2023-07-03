package es.caib.sistramit.core.api.model.formulario.types;

/**
 * Palabras reservadas que no se pueden usar como ids de campos.
 *
 * @author Indra
 *
 */
public enum TypePalabraReservada {

	/**
	 * Id campo (Código String: idCampo).
	 */
	ID_CAMPO("idCampo"),
	/**
	 * Accion para acciones formulario (Código String: accion).
	 */
	ACCION("accion"),
	/**
	 * Texto campo para buscador dinámico (Código String: textoCampo).
	 */
	TEXTO_CAMPO("textoCampo"),
	/**
	 * Id campo lista elementos (Código String: idCampoListaElementos).
	 */
	ID_CAMPO_LISTA_ELEMENTOS("idCampoListaElementos"),
	/**
	 * Indice elemento (Código String: indiceElemento).
	 */
	INDICE_ELEMENTO("indiceElemento");

	/**
	 * Valor como string.
	 */
	private final String stringValueValor;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypePalabraReservada(final String value) {
		stringValueValor = value;
	}

	@Override
	public String toString() {
		return stringValueValor;
	}

	/**
	 * Método para From string de la clase TypeValor.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type paso
	 */
	public static TypePalabraReservada fromString(final String text) {
		TypePalabraReservada respuesta = null;
		if (text != null) {
			for (final TypePalabraReservada b : TypePalabraReservada.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
