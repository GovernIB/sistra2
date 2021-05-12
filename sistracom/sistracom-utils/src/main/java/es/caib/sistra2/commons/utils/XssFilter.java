package es.caib.sistra2.commons.utils;

import java.text.Normalizer;

import org.apache.commons.lang3.StringUtils;

/**
 * Detecta si se usan valores no permitidos en los literales.
 */
public final class XssFilter {

	/**
	 * Lista de valores no permitidos en literales para prevenir xss.
	 */
	private static final String[] BLACK_LIST_LITERAL = { "<", ">" };

	/**
	 * Lista de valores no permitidos en literales para prevenir xss.
	 */
	private static final String[] BLACK_LIST_HTML = { "script", "javascript", "onabort", "onBlur", "onchange",
			"onclick", "onbblclick", "ondragdrop", "onerror", "onFocus", "onKeyDown", "onKeyPress", "onKeyUp", "onLoad",
			"onMouseDown", "onMouseMove", "onMouseOut", "onMouseOver", "onMouseUp", "onMove", "onReset", "onResize",
			"onSelect", "onSubmit", "onUnload", "applet", "<embed", "<object" };

	/**
	 * Instancia un nuevo util de Util.
	 */
	private XssFilter() {

	}

	/**
	 * Filtrado de Xss para un literal que no admite html.
	 *
	 * @param valor
	 *            Valor a filtrar
	 * @return Indica si pasa el filtro.
	 */
	public static boolean filtroXss(final String valor) {
		return filtroXss(valor, false);
	}

	/**
	 * Filtrado de Xss.
	 *
	 * @param valor
	 *            Valor a filtrar
	 * @param html
	 *            Indica si admite html
	 * @return Indica si pasa el filtro.
	 */
	public static boolean filtroXss(final String valor, final boolean html) {
		boolean res;
		if (html) {
			res = buscarValorNoPermitido(valor, BLACK_LIST_HTML);
		} else {
			res = buscarValorNoPermitido(valor, BLACK_LIST_LITERAL);
		}
		return res;
	}

	/**
	 * Normaliza nombre fichero.
	 *
	 * @param nombreFichero
	 *            Nombre fichero
	 * @return
	 */
	public static String normalizarFilename(final String nombreFichero) {
		// Quitamos acentos y caracteres problematicos
		String nombreFicheroNormalizado = Normalizer.normalize(nombreFichero, Normalizer.Form.NFD)
				.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").replaceAll(" ", "_").replaceAll("%", "_")
				.replaceAll("'", "´");
		// Eliminamos chars no permitidos en RW3
		final String[] charsNoPermitidos = { "\\+",">", "%", "\\*", "&", ":", ";", "¿", "\\?", "/", "\\|", "!", "<", "¡",
				"\"" };
		for (final String cnp : charsNoPermitidos) {
			nombreFicheroNormalizado = nombreFicheroNormalizado.replaceAll(cnp, "_");
		}
		return nombreFicheroNormalizado;
	}

	/**
	 * Busca si existe algun valor no permitido en la cadena.
	 *
	 * @param cadena
	 *            Cadena
	 * @param blackList
	 *            Lista de valores no permitidos
	 * @return true si no encuentra ninguno
	 */
	private static boolean buscarValorNoPermitido(final String cadena, final String[] blackList) {
		boolean res = true;
		if (cadena != null) {
			String tmp = cadena.toLowerCase();
			tmp = StringUtils.deleteWhitespace(tmp);
			for (final String key : blackList) {
				if (tmp.indexOf(key.toLowerCase()) >= 0) {
					res = false;
					break;
				}
			}
		}
		return res;
	}

}
