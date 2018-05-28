package es.caib.sistra2.commons.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Utilidades Nif Cif.
 *
 * @author Indra
 *
 */
public final class NifCif {

	/**
	 * Normalizar nif/cif pasando a mayusculas, rellenando con 0 hasta tamaño 9 y
	 * quitando espacios,/,\ y -.
	 *
	 * @param nif
	 *            nif/cif
	 * @return nif/cif normalizado
	 */
	public static String normalizarNif(final String nif) {
		String doc = null;
		if (nif != null) {
			// Quitamos espacios y otros caracteres
			doc = nif.toUpperCase();
			doc = doc.replaceAll("[\\/\\s\\-]", "");
			// Rellenamos con 0
			if (doc.length() > 1) {
				final String primerCaracter = doc.substring(0, 1);
				if (Pattern.matches("[^A-Z]", primerCaracter)) {
					// Es nif
					doc = StringUtils.leftPad(doc, ConstantesNumero.N9, '0');
				} else {
					// es cif o nie
					final String letraInicio = doc.substring(0, ConstantesNumero.N1);
					final String resto = doc.substring(ConstantesNumero.N1);

					doc = letraInicio + StringUtils.leftPad(resto, ConstantesNumero.N8, '0');

				}
			} else {
				doc = nif;
			}

		}
		return doc;
	}

}
