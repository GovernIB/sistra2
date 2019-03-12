package es.caib.sistra2.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidacionesCadena {

	/**
	 * Instancia.
	 */
	private static ValidacionesCadena instance;

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Obtener instancia.
	 *
	 * @return instancia.
	 */
	public static ValidacionesCadena getInstance() {
		if (instance == null) {
			instance = new ValidacionesCadena();
		}
		return instance;
	}

	/**
	 * Constructor.
	 */
	private ValidacionesCadena() {
		super();
	}

	/**
	 * Valida si el parametro String es valido (No es nulo y tiene contenido).
	 *
	 * @param token
	 *            String a Validar
	 * @return true, si es satisfactorio true Si es una cadena vacia o nula false Si
	 *         no es una cadena vacia
	 */
	public boolean esCadenaVacia(final String token) {
		return (esNulo(token) || (token.length() == 0));
	}

	/**
	 * Valida si dos cadenas son iguales.
	 *
	 * @param cadena1
	 *            Cadena 1
	 * @param cadena2
	 *            Cadena 2
	 * @return boolean
	 */
	public boolean esIgual(final String pCadena1, final String pCadena2) {
		return StringUtils.equals(pCadena1, pCadena2);
	}

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
	public boolean esIgual(final String pCadena1, final String pCadena2, final boolean pIgnoreCase) {
		boolean res;
		if (pIgnoreCase) {
			res = StringUtils.equalsIgnoreCase(pCadena1, pCadena2);
		} else {
			res = StringUtils.equals(pCadena1, pCadena2);
		}
		return res;
	}

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
	public String lpad(final String asTexto, final int aiLongMinima, final String acRelleno) {
		return StringUtils.leftPad(asTexto, aiLongMinima, acRelleno);
	}

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
	public String rpad(final String asTexto, final int aiLongMinima, final String acRelleno) {
		return StringUtils.rightPad(asTexto, aiLongMinima, acRelleno);
	}

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
	public String replace(final String texto, final String one, final String another) {
		return StringUtils.replace(texto, one, another);
	}

	/**
	 * Valida si una expresion se encuentra dentro de un texto.
	 *
	 * @param texto
	 *            Texto que contiene la expresion
	 * @param expresion
	 *            Expresion a validar
	 * @return true, si es satisfactorio true si es valido false si no es valido
	 */
	public boolean validaExpresionRegular(final String texto, final String expresion) {
		boolean res;
		if (esCadenaVacia(texto) || esCadenaVacia(expresion)) {
			res = false;
		} else {
			res = texto.matches(expresion);
		}
		return res;
	}

	/**
	 * Valida si el objeto pasado es nulo.
	 *
	 * @param nulo
	 *            Objeto a Validar
	 * @return true, si es null true si es valido false si no es valido
	 */
	public boolean esNulo(final Object nulo) {
		return (nulo == null);
	}

	/**
	 * Elimina espacios por delante y por detrás.
	 *
	 * @param cadena
	 *            Cadena
	 * @return cadena resultado
	 */
	public String trim(final String pCadena) {
		String res = null;
		if (pCadena != null) {
			res = pCadena.trim();
		}
		return res;
	}

	/**
	 * Elimina espacios por la izquierda.
	 *
	 * @param cadena
	 *            Cadena
	 * @return cadena resultado
	 */
	public String ltrim(final String pCadena) {
		String res = null;
		if (pCadena != null) {
			final int len = pCadena.length();
			int i = 0;
			while (i < len && Character.isWhitespace(pCadena.charAt(i))) {
				i++;
			}
			if (i != 0) {
				res = pCadena.substring(i);
			} else {
				res = pCadena;
			}
		}
		return res;
	}

	/**
	 * Elimina espacios por la derecha.
	 *
	 * @param cadena
	 *            Cadena
	 * @return cadena resultado
	 */
	public String rtrim(final String pCadena) {
		String res = null;
		if (pCadena != null) {
			final int len = pCadena.length();
			int i = len - ConstantesNumero.N1;
			while (i >= 0 && Character.isWhitespace(pCadena.charAt(i))) {
				i--;
			}
			if (i < 0) {
				res = "";
			} else {
				res = pCadena.substring(0, i + ConstantesNumero.N1);
			}
		}
		return res;
	}

	/**
	 * Extrae el valor de un campo xml.
	 *
	 * @param xml
	 *            Xml
	 * @param xpath
	 *            Xpath
	 * @return Valor
	 */
	public String extraerValorXml(final String pXml, final String pXpath) {
		String respuesta = "";
		if (!StringUtils.isEmpty(pXml) && !StringUtils.isEmpty(pXpath)) {
			final SAXReader reader = new SAXReader();
			Document documento;
			try {
				documento = reader.read(new ByteArrayInputStream(pXml.getBytes("UTF-8")));
				final Node nodo = documento.selectSingleNode(pXpath);
				if (nodo != null) {
					respuesta = nodo.getText();
				}
			} catch (final UnsupportedEncodingException ex) {
				log.warn("Excepcion extrayendo valor xml: " + pXpath + ". Devolvemos cadena vacía.", ex);
			} catch (final DocumentException e) {
				log.warn("Excepcion extrayendo valor xml: " + pXpath + ". Devolvemos cadena vacía.", e);
				respuesta = "";
			}
		}
		return respuesta;
	}

	/**
	 * Convierte a Integer.
	 * 
	 * @param intStr
	 *            cadena
	 * @return Integer
	 */
	public Integer parseInt(String intStr) {
		Integer res = null;
		if (intStr != null) {
			try {
				res = Integer.parseInt(intStr);
			} catch (final NumberFormatException nfe) {
				res = null;
			}
		}
		return res;
	}

}
