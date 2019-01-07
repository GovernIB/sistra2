package es.caib.sistramit.core.service.model.script;

public interface PlgValidacionesCadenaInt {

	/**
	 * Valida si el parametro String es valido (No es nulo y tiene contenido).
	 *
	 * @param token
	 *            String a Validar
	 * @return true, si es satisfactorio true Si es una cadena vacia o nula false Si
	 *         no es una cadena vacia
	 */
	boolean esCadenaVacia(final String token);

	/**
	 * Valida si dos cadenas son iguales.
	 *
	 * @param cadena1
	 *            Cadena 1
	 * @param cadena2
	 *            Cadena 2
	 * @return boolean
	 */
	boolean esIgual(final String cadena1, final String cadena2);

	/**
	 * Valida si dos cadenas son iguales permitiendo establecer la opción de si
	 * distingue mayusculas y minusculas.
	 *
	 * @param cadena1
	 *            Cadena 1
	 * @param cadena2
	 *            Cadena 2
	 * @param ignoreCase
	 *            Si true indica que no se distingue mayusculas y minusculas.
	 * @return boolean
	 */
	boolean esIgual(final String cadena1, final String cadena2, boolean ignoreCase);

	/**
	 * LPAD.
	 *
	 * @param asTexto
	 *            Parámetro as texto
	 * @param aiLongMinima
	 *            Parámetro ai long minima
	 * @param acRelleno
	 *            Parámetro ac relleno
	 * @return el string String que contiene la cadena ampliada a la longitud
	 *         determinada
	 */
	String lpad(final String asTexto, final int aiLongMinima, final String acRelleno);

	/**
	 * Rellena una cadena por la derecha hasta alcanzar una longitud determinada con
	 * un caracter especificado.
	 *
	 * @param asTexto
	 *            Parámetro as texto
	 * @param aiLongMinima
	 *            Parámetro ai long minima
	 * @param acRelleno
	 *            Parámetro ac relleno
	 * @return el string String que contiene la cadena ampliada a la longitud
	 *         determinada
	 */
	String rpad(final String asTexto, final int aiLongMinima, final String acRelleno);

	/**
	 * Método usada para reemplazar todas las ocurrencias de determinada cadena de
	 * texto por otra cadena de texto. En el String sustituye 'one' por 'another'.
	 *
	 * @param texto
	 *            String Texto origen
	 * @param one
	 *            String Fragmento de texto a reemplazar
	 * @param another
	 *            String Fragmento de texto con el que se reemplaza
	 * @return el string
	 */
	String replace(final String texto, final String one, final String another);

	/**
	 * Valida si una expresion se encuentra dentro de un texto.
	 *
	 * @param texto
	 *            Texto que contiene la expresion
	 * @param expresion
	 *            Expresion a validar
	 * @return true, si es satisfactorio true si es valido false si no es valido
	 */
	boolean validaExpresionRegular(final String texto, final String expresion);

	/**
	 * Valida si el objeto pasado es nulo.
	 *
	 * @param nulo
	 *            Objeto a Validar
	 * @return true, si es null true si es valido false si no es valido
	 */
	boolean esNulo(final Object nulo);

	/**
	 * Elimina espacios por delante y por detrás.
	 *
	 * @param cadena
	 *            Cadena
	 * @return cadena resultado
	 */
	String trim(String cadena);

	/**
	 * Elimina espacios por la izquierda.
	 *
	 * @param cadena
	 *            Cadena
	 * @return cadena resultado
	 */
	String ltrim(String cadena);

	/**
	 * Elimina espacios por la derecha.
	 *
	 * @param cadena
	 *            Cadena
	 * @return cadena resultado
	 */
	String rtrim(String cadena);

	/**
	 * Extrae el valor de un campo xml.
	 *
	 * @param xml
	 *            Xml
	 * @param xpath
	 *            Xpath
	 * @return Valor
	 */
	String extraerValorXml(String xml, String xpath);
}
