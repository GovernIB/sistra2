package es.caib.sistra2.commons.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Utilidades a las identificaciones NIF.
 *
 * @author Indra
 * @link <a href=
 *       "https://es.wikipedia.org/wiki/N%C3%BAmero_de_identificaci%C3%B3n_fiscal">https://es.wikipedia.org/wiki/N%C3%BAmero_de_identificaci%C3%B3n_fiscal</a>
 *
 */
public final class NifUtils {

	/** Atributo LETRAS. */
	private static final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";

	/** Atributo SI n_ dni. */
	private static final String SIN_DNI = "[0-9]{0,8}[" + LETRAS + "]{1}";

	/** Atributo SI n_ nif. */
	private static final String SIN_NIF_PERSONA_JURIDICA = "[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]||[ABCDEFGHIJ]){1}";

	/** Atributo SI n_ nie. */
	private static final String SIN_NIE = "[X|Y|Z][0-9]{1,8}[A-Z]{1}";

	/** Atributo SI n_ nif_otros. */
	private static final String SIN_NIF_OTROS = "[K|L|M][0-9]{1,8}[A-Z]{1}";

	/** Atributo SI n_ ss. */
	private static final String SIN_SS = "^\\s*[0-9]{2}[\\/|\\s\\-]?[0-9]{7,8}[\\/|\\s\\-]?[0-9]{2}\\s*$";

	/** Indica que NIF PJ deben tener letra como DC. */
	private static final String NIF_PJ_DC_LETRA = "N,P,Q,R,S,W";

	/** Constructor. **/
	private NifUtils() {
		// Constructor vacio
	}

	/**
	 * Método que comprueba si la identificación pertenece a alguna de las
	 * identificaciones posibles.
	 *
	 * @param identificacion
	 * @param esDni
	 * @param esNie
	 * @param esOtrosNif
	 * @param esNif
	 * @param esNss
	 * @return
	 */
	public static boolean esIdentificacion(final String identificacion, final boolean esDni, final boolean esNie,
			final boolean esOtrosNif, final boolean esNif, final boolean esNss) {
		boolean resultado = false;
		if (esDni) {
			resultado = esDni(identificacion);
		}
		if (!resultado && esNie) {
			resultado = esNie(identificacion);
		}
		if (!resultado && esOtrosNif) {
			resultado = esNifOtros(identificacion);
		}
		if (!resultado && esNif) {
			resultado = esNifPersonaJuridica(identificacion);
		}
		if (!resultado && esNss) {
			resultado = esNSS(identificacion);
		}
		return resultado;
	}

	/**
	 * Es persona jurídica. Es decir, la identificación es de alguno de los
	 * siguientes tipos: <br />
	 * - Dni <br />
	 * - Nie <br />
	 * - Otros nif <br />
	 *
	 * @param identificacion
	 * @return
	 */
	public static boolean esNifPersonaFisica(final String identificacion) {
		return esDni(identificacion) || esNie(identificacion) || esNifOtros(identificacion);
	}

	/**
	 * Comprueba si es un DNI. Un dni es de tamaño 9 formado por 8 números y 1 letra
	 * de control.
	 *
	 * @param valor
	 * @return
	 */
	public static boolean esDni(final String valor) {
		boolean res = false;
		try {
			if (esCadenaVacia(valor) || !Pattern.matches(SIN_DNI, valor) || valor.length() != ConstantesNumero.N9) {
				res = false;
			} else {
				/***
				 * Pasos: <br />
				 * 1. Extraemos letra <br />
				 * 2. Cogemos todos los digitos <br />
				 * 3. Calculamos la letra de control a partir de los dígitos<br />
				 * 4. Si son iguales, es dni correcto (true), sino false.
				 */
				final String letraNif = valor.substring(8);

				final StringBuilder sb = new StringBuilder(20);
				for (int i = 0; i < valor.length(); i++) {
					final char c = valor.substring(i, i + ConstantesNumero.N1).toCharArray()[0];
					if (Character.isDigit(c)) {
						sb.append(c);
					}
				}

				final String digitos = sb.toString();

				final String letra = getLetraNIF(digitos);
				if (letra == null) {
					res = false;
				} else {
					res = letraNif.equals(letra);
				}

			}
		} catch (final IllegalArgumentException e) {
			res = false;
		}
		return res;
	}

	/**
	 * Comprueba si es un NIE (Documento de Identidad para residentes Extranjeros).
	 * Un nie es de tamaño 9 formado por el carácter X/Y/Z, 7 dígitos y una letra de
	 * control.
	 *
	 * @param nie
	 * @return
	 */
	public static boolean esNie(final String nie) {
		boolean res = false;
		try {
			if (esCadenaVacia(nie) || !Pattern.matches(SIN_NIE, nie) || nie.length() != ConstantesNumero.N9) {
				res = false;
			} else {
				/***
				 * Pasos: <br />
				 * 1. Extraemos los números (sustituyendo caracteres por vacío) <br />
				 * 2. La primera letra se tiene que convertir por dígito (se concatena al resto
				 * de dígitos). <br />
				 * 3. Extraemos la letra de control. 4. Calculamos la letra de control a partir
				 * de los dígitos. <br />
				 * 5. Si son iguales, es dni correcto (true), sino false.
				 */
				final String numero = nie.replaceAll("[a-zA-Z]", StringUtils.EMPTY);
				final String inicio = getInicioNie(nie);

				final String letra = nie.substring(ConstantesNumero.N1).replaceAll("[^a-z^A-Z]", StringUtils.EMPTY);
				if (!letra.equals(getLetraNIF(inicio + numero))) {
					res = false;
				} else {
					res = true;
				}

			}
		} catch (final IllegalArgumentException exc) {
			res = false;
		}
		return res;
	}

	/**
	 * Asigna un valor dependiendo de si empieza por X, Y o Z (el pattern SIN_NIE
	 * obliga a que tenga esos caracteres), sabiendo que: <br />
	 * - X: No tiene dígito equivalente. <br />
	 * - Y: Su dígito equivalente es 1. <br />
	 * - Z: Su dígito equivalente es 2. <br />
	 * El dígito se concatena al principio de los otros dígitos
	 *
	 * @param nie
	 * @return
	 */
	private static String getInicioNie(final String nie) {
		String inicio;
		if (nie.startsWith("Y")) {
			inicio = "1";
		} else if (nie.startsWith("Z")) {
			inicio = "2";
		} else { // Letra X
			inicio = "";
		}
		return inicio;
	}

	/**
	 * Comprueba si es de tipo NIF otros (letras K, L, M), leer enlace wikipedia
	 * adjunto para saber a que se refiere. Un nif otros es de tamaño 9 formado por
	 * el carácter K/L/M, 7 dígitos y una letra de control. El primer carácter K/L/M
	 * no se utiliza para nada.
	 *
	 * @param nif
	 * @return
	 */
	public static boolean esNifOtros(final String nif) {
		boolean res = false;
		try {
			if (esCadenaVacia(nif) || !Pattern.matches(SIN_NIF_OTROS, nif) || nif.length() != ConstantesNumero.N9) {
				res = false;
			} else {
				/***
				 * Pasos: <br />
				 * 1. Cogemos todos los digitos <br />
				 * 2. Extraemos la letra de control <br />
				 * 3. Calculamos la letra de control a partir de los dígitos<br />
				 * 4. Si son iguales, es dni correcto (true), sino false.
				 */
				final String numero = nif.replaceAll("[a-zA-Z]", StringUtils.EMPTY);
				final String letra = nif.substring(ConstantesNumero.N1).replaceAll("[^a-z^A-Z]", StringUtils.EMPTY);
				if (!letra.equals(getLetraNIF(numero))) {
					res = false;
				} else {
					res = true;
				}
			}
		} catch (final IllegalArgumentException e) {
			res = false;
		}
		return res;
	}

	/**
	 * Comprueba si es de tipo NIF Persona jurídica. El formato consta de una letra
	 * de naturaleza jurídica, 7 números y un código de control (que según la
	 * naturaleza jurídica, es un número o letra).
	 *
	 * @param valor
	 * @return
	 */
	public static boolean esNifPersonaJuridica(final String valor) {
		boolean res = false;
		try {
			if (esCadenaVacia(valor) || !Pattern.matches(SIN_NIF_PERSONA_JURIDICA, valor)
					|| valor.length() != ConstantesNumero.N9) {
				res = false;
			} else {
				final String codigoControl = valor.substring(valor.length() - ConstantesNumero.N1, valor.length());
				final int[] v1 = { 0, ConstantesNumero.N2, ConstantesNumero.N4, ConstantesNumero.N6,
						ConstantesNumero.N8, ConstantesNumero.N1, ConstantesNumero.N3, ConstantesNumero.N5,
						ConstantesNumero.N7, ConstantesNumero.N9 };
				final String[] v2 = { "J", "A", "B", "C", "D", "E", "F", "G", "H", "I" };
				int suma = 0;
				for (int i = ConstantesNumero.N2; i <= ConstantesNumero.N6; i += ConstantesNumero.N2) {
					suma += v1[Integer.parseInt(valor.substring(i - ConstantesNumero.N1, i))];
					suma += Integer.parseInt(valor.substring(i, i + ConstantesNumero.N1));
				}
				suma += v1[Integer.parseInt(valor.substring(ConstantesNumero.N7, ConstantesNumero.N8))];
				suma = (ConstantesNumero.N10 - (suma % ConstantesNumero.N10));
				if (suma == ConstantesNumero.N10) {
					suma = 0;
				}

				final String letraControl = v2[suma];
				res = (codigoControl.equals(Integer.toString(suma)) || codigoControl.equalsIgnoreCase(letraControl));

				// Verificamos si DC debe ser una letra o numero
				if (res) {
					final boolean esEntero = ValidacionesTipo.getInstance().esEntero(codigoControl);
					final String letraInicial = valor.substring(0, 1);
					res = (NIF_PJ_DC_LETRA.indexOf(letraInicial) != -1 && !esEntero)
							|| (NIF_PJ_DC_LETRA.indexOf(letraInicial) == -1 && esEntero);
				}

			}
		} catch (final IllegalArgumentException e) {
			res = false;
		}
		return res;
	}

	/**
	 * Comprueba si es un número de la seguridad social (NSS).
	 *
	 * @param nss
	 * @return
	 */
	public static boolean esNSS(final String nss) {
		boolean result = false;
		try {
			if (esCadenaVacia(nss) || !Pattern.matches(SIN_SS, nss)) {
				result = false;
			} else {
				final String numeross = nss.replaceAll("[\\/\\s\\-]", StringUtils.EMPTY);
				if (!Pattern.matches("^[0-9]{11,12}$", numeross)) {
					result = false;
				} else {
					final String part1 = numeross.substring(0, ConstantesNumero.N2);
					String part2 = numeross.substring(ConstantesNumero.N2, numeross.length() - ConstantesNumero.N2);
					final String part3 = numeross.substring(numeross.length() - ConstantesNumero.N2);
					if ((part2.length() == ConstantesNumero.N8) && (part2.charAt(0) == '0')) {
						part2 = part2.substring(ConstantesNumero.N1);
					}
					final String numero = part1 + part2;
					final String dc = part3;
					final long iNumero = Long.parseLong(numero, ConstantesNumero.N10);
					final long iDc = Long.parseLong(dc, ConstantesNumero.N10);

					if (iNumero % ConstantesNumero.N97 != iDc) {
						result = false;
					} else {
						result = true;
					}
				}
			}
		} catch (final IllegalArgumentException exc) {
			result = false;
		}
		return result;
	}

	/**
	 * Normalizar nif/cif pasando a mayusculas, rellenando con 0 hasta tamaño 9 y
	 * quitando espacios,/,\ y -.
	 *
	 * @param nif
	 *                nif/cif
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

	/**
	 * Devuelve la letra de control del NIF completo a partir de un DNI.
	 *
	 * @param dniNumerico
	 *                        dni al que se quiere añadir la letra del NIF
	 * @return el atributo letra nif Letra control NIF.
	 */
	private static String getLetraNIF(final String dniNumerico) {
		final int dni = Integer.parseInt(dniNumerico, ConstantesNumero.N10);
		final int modulo = dni % ConstantesNumero.N23;
		final char letra = LETRAS.charAt(modulo);
		return String.valueOf(letra);
	}

	/**
	 * Valida si el parametro String es valido (No es nulo y tiene contenido).
	 *
	 * @param token
	 *                  String a Validar
	 * @return true: si valido true, false: si cadena vacia
	 */
	private static boolean esCadenaVacia(final String token) {
		return (token == null || token.length() == 0);
	}

	public static void main(final String args[]) {

	}
}
