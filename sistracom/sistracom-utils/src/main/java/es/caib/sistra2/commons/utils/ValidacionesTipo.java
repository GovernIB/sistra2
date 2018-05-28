package es.caib.sistra2.commons.utils;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * Validaciones de tipo.
 *
 * @author Indra
 */
public final class ValidacionesTipo {

	/** Atributo constante PATRON_TELEFONO. */
	private final Pattern patronTelefono;
	/** Atributo constante PATRON_IMPORTE. */
	private final Pattern patronImporte;
	/** Atributo constante PATRON_EMAIL. */
	private final Pattern patronEmail;
	/** Atributo patron fecha de ValidacionesTipoImpl. */
	private final Pattern patronFecha;
	/** Atributo formato fecha. */
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	/** Atributo formato fecha-hora. */
	private static final String FORMATO_FECHAHORA = "dd/MM/yyyy HH:mm:ss";
	/** Atributo LETRAS. */
	private static final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";
	/** Atributo SI n_ nif. */
	private static final String SIN_NIF = "[0-9]{0,8}[" + LETRAS + "]{1}";
	/** Atributo SI n_ cif. */
	private static final String SIN_CIF = "[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]||[ABCDEFGHIJ]){1}";
	/** Atributo SI n_ nie. */
	private static final String SIN_NIE = "[X|Y|Z][0-9]{1,8}[A-Z]{1}";
	/** Atributo SI n_ ss. */
	private static final String SIN_SS = "^\\s*[0-9]{2}[\\/|\\s\\-]?[0-9]{7,8}[\\/|\\s\\-]?[0-9]{2}\\s*$";
	/** Atributo R e_ cuenta. */
	private static final String RE_CUENTA = "^[0-9]{20}$";
	/** Instancia. */
	private static ValidacionesTipo instance;

	/** Variable codigosB para code128. */
	final String[] codigosB = new String[] { " ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".",
			"/", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ":", ";", "<", "=", ">", "?", "@", "A", "B", "C",
			"D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
			"Y", "Z", "[", "\\", "]", "^", "_", "`", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
			"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "{", "|", "}", "~" };
	/** Variable caracteres para code128. */
	final String[] caracteres = new String[] { "Â", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-",
			".", "/", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ":", ";", "<", "=", ">", "?", "@", "A", "B",
			"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z", "[", "\\", "]", "^", "_", "`", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
			"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "{", "|", "}", "~", "Ã", "Ä", "Å",
			"Æ", "Ç", "È", "É", "Ê", "Ë", "Ì", "Í", "Î" };
	/** Variable valores para code128. */
	final String[] valores = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
			"14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31",
			"32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
			"50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67",
			"68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85",
			"86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", "101", "102",
			"103", "104", "105", "na" };

	/**
	 * Constructor.
	 */
	private ValidacionesTipo() {
		super();
		patronTelefono = Pattern.compile("^\\d{9}$");
		patronImporte = Pattern.compile("^[0-9]+(,[0-9]{1,2})?$");
		patronEmail = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
		patronFecha = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((1|2)\\d\\d\\d)");
	}

	/**
	 * Obtener instancia.
	 *
	 * @return instancia.
	 */
	public static ValidacionesTipo getInstance() {
		if (instance == null) {
			instance = new ValidacionesTipo();
		}
		return instance;
	}

	/**
	 * Verifica expresión regular.
	 *
	 * @param texto
	 *            Texto a validar
	 * @param pattern
	 *            Patron que valida el texto
	 * @return boolean true Si cumple el patron false Si no cumple el patron
	 */
	private static boolean compruebaRegExp(final String texto, final Pattern pattern) {
		final Matcher m = pattern.matcher(texto);
		return (m.find());
	}

	public boolean esCP(final String cp, final String provincia) {
		final int cinco = 5;
		boolean res = true;
		if (esCadenaVacia(cp) || (longitudJava(cp, cinco) != 0) || !esNumerico(cp)) {
			res = false;
		} else {
			final String codigoProvincial = cp.substring(0, 2);
			final int iCodigoProvincial = Integer.parseInt(cp.substring(0, 2));
			if (iCodigoProvincial > ConstantesNumero.N54) {
				res = false;
			} else if ((provincia != null) && (provincia.length() > 0)) {
				res = (provincia.equalsIgnoreCase(codigoProvincial));
			}
		}
		return res;
	}

	public boolean esCP(final Long cp, final String provincia) {
		boolean res = false;
		if (isNull(cp)) {
			res = false;
		} else {
			String strCp = cp.toString();
			if (longitudJava(strCp, ConstantesNumero.N4) == 0) {
				final StringBuffer codigoPostal = new StringBuffer("0");
				codigoPostal.append(strCp);
				strCp = codigoPostal.toString();
			}
			res = esCP(strCp, provincia);
		}
		return res;
	}

	/**
	 * Devuelve la letra de control del NIF completo a partir de un DNI.
	 *
	 * @param dniNumerico
	 *            dni al que se quiere añadir la letra del NIF
	 * @return el atributo letra nif Letra control NIF.
	 */
	private static String getLetraNIF(final String dniNumerico) {
		final int dni = Integer.parseInt(dniNumerico, ConstantesNumero.N10);
		final int modulo = dni % ConstantesNumero.N23;
		final char letra = LETRAS.charAt(modulo);
		return String.valueOf(letra);
	}

	public boolean esNif(final String valor) {
		boolean res = false;
		try {
			if (esCadenaVacia(valor) || !Pattern.matches(SIN_NIF, valor) || valor.length() != ConstantesNumero.N9) {
				res = false;
			} else {
				final String letraNif = valor.substring(8);

				final StringBuffer sb = new StringBuffer(20);
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

	public boolean esCif(final String valor) {
		boolean res = false;
		try {
			if (esCadenaVacia(valor) || !Pattern.matches(SIN_CIF, valor) || valor.length() != ConstantesNumero.N9) {
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
				res = (codigoControl.equals(Integer.toString(suma))
						|| codigoControl.toUpperCase().equals(letraControl));
			}
		} catch (final IllegalArgumentException e) {
			res = false;
		}
		return res;
	}

	public boolean esNie(final String nie) {
		boolean res = false;
		try {
			if (isNull(nie) || !Pattern.matches(SIN_NIE, nie) || (nie.length() != ConstantesNumero.N9)) {
				res = false;
			} else {
				final String numero = nie.replaceAll("[a-zA-Z]", StringUtils.EMPTY);
				String inicio = "";
				if (nie.startsWith("Y")) {
					inicio = "1";
				} else if (nie.startsWith("Z")) {
					inicio = "2";
				}
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

	public boolean esNumeroSeguridadSocial(final String nss) {
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

	public boolean esNumeroCuentaValido(final String numeroCuenta) {
		boolean res = false;
		if (esCadenaVacia(numeroCuenta) || !Pattern.matches(RE_CUENTA, numeroCuenta)) {
			res = false;
		} else {
			final String entidad = numeroCuenta.substring(0, ConstantesNumero.N4);
			final String sucursal = numeroCuenta.substring(ConstantesNumero.N4, ConstantesNumero.N8);
			final String dc = numeroCuenta.substring(ConstantesNumero.N8, ConstantesNumero.N10);
			final String cuenta = numeroCuenta.substring(ConstantesNumero.N10);
			res = esNumeroCuentaValido(entidad, sucursal, dc, cuenta);
		}
		return res;
	}

	public boolean esNumeroCuentaValido(final String entidad, final String sucursal, final String dc,
			final String cuenta) {
		boolean res = false;
		if (esCadenaVacia(entidad) || esCadenaVacia(sucursal) || esCadenaVacia(dc) || esCadenaVacia(cuenta)) {
			res = false;
		} else {
			final String ncc = entidad.trim() + sucursal.trim() + dc.trim() + cuenta.trim();
			if (!(ncc.length() == ConstantesNumero.N20) || (!Pattern.matches(RE_CUENTA, ncc))
					|| (Integer.parseInt(entidad, ConstantesNumero.N10) < ConstantesNumero.N1)) {
				res = false;
			} else {
				res = verificaDigitoControlCCC(entidad, sucursal, dc, cuenta);
			}
		}
		return res;
	}

	public boolean esNumeroCuentaIbanValido(final String iban, final String entidad, final String sucursal,
			final String dc, final String cuenta) {
		final String account = iban + entidad + sucursal + dc + cuenta;
		return esNumeroCuentaIbanValido(account);
	}

	public boolean esNumeroCuentaIbanValido(final String pNumeroCuenta) {
		return (checkIBAN(pNumeroCuenta) && esNumeroCuentaValido(pNumeroCuenta.substring(ConstantesNumero.N4)));
	}

	/**
	 * Verifica digitos cuenta.
	 *
	 * @param entidad
	 *            entidad
	 * @param sucursal
	 *            sucursal
	 * @param dc
	 *            digito control
	 * @param cuenta
	 *            num cuenta
	 * @return true si valido
	 */
	private boolean verificaDigitoControlCCC(final String entidad, final String sucursal, final String dc,
			final String cuenta) {
		boolean res;
		int calculo0 = ConstantesNumero.N11 - (ConstantesNumero.N4
				* Integer.parseInt(entidad.substring(0, ConstantesNumero.N1), ConstantesNumero.N10)
				+ ConstantesNumero.N8 * Integer.parseInt(entidad.substring(ConstantesNumero.N1, ConstantesNumero.N2),
						ConstantesNumero.N10)
				+ ConstantesNumero.N5 * Integer.parseInt(entidad.substring(ConstantesNumero.N2, ConstantesNumero.N3),
						ConstantesNumero.N10)
				+ ConstantesNumero.N10 * Integer.parseInt(entidad.substring(ConstantesNumero.N3, ConstantesNumero.N4),
						ConstantesNumero.N10)
				+ ConstantesNumero.N9
						* Integer.parseInt(sucursal.substring(0, ConstantesNumero.N1), ConstantesNumero.N10)
				+ ConstantesNumero.N7 * Integer.parseInt(sucursal.substring(ConstantesNumero.N1, ConstantesNumero.N2),
						ConstantesNumero.N10)
				+ ConstantesNumero.N3 * Integer.parseInt(sucursal.substring(ConstantesNumero.N2, ConstantesNumero.N3),
						ConstantesNumero.N10)
				+ ConstantesNumero.N6 * Integer.parseInt(sucursal.substring(ConstantesNumero.N3, ConstantesNumero.N4),
						ConstantesNumero.N10))
				% ConstantesNumero.N11;
		int calculo1 = ConstantesNumero.N11 - (ConstantesNumero.N1
				* Integer.parseInt(cuenta.substring(0, ConstantesNumero.N1), ConstantesNumero.N10)
				+ ConstantesNumero.N2 * Integer.parseInt(cuenta.substring(ConstantesNumero.N1, ConstantesNumero.N2),
						ConstantesNumero.N10)
				+ ConstantesNumero.N4 * Integer.parseInt(cuenta.substring(ConstantesNumero.N2, ConstantesNumero.N3),
						ConstantesNumero.N10)
				+ ConstantesNumero.N8 * Integer.parseInt(cuenta.substring(ConstantesNumero.N3, ConstantesNumero.N4),
						ConstantesNumero.N10)
				+ ConstantesNumero.N5 * Integer.parseInt(cuenta.substring(ConstantesNumero.N4, ConstantesNumero.N5),
						ConstantesNumero.N10)
				+ ConstantesNumero.N10 * Integer.parseInt(cuenta.substring(ConstantesNumero.N5, ConstantesNumero.N6),
						ConstantesNumero.N10)
				+ ConstantesNumero.N9 * Integer.parseInt(cuenta.substring(ConstantesNumero.N6, ConstantesNumero.N7),
						ConstantesNumero.N10)
				+ ConstantesNumero.N7 * Integer.parseInt(cuenta.substring(ConstantesNumero.N7, ConstantesNumero.N8),
						ConstantesNumero.N10)
				+ ConstantesNumero.N3 * Integer.parseInt(cuenta.substring(ConstantesNumero.N8, ConstantesNumero.N9),
						ConstantesNumero.N10)
				+ ConstantesNumero.N6 * Integer.parseInt(cuenta.substring(ConstantesNumero.N9, ConstantesNumero.N10),
						ConstantesNumero.N10))
				% ConstantesNumero.N11;
		if (calculo0 >= ConstantesNumero.N10) {
			calculo0 = ConstantesNumero.N11 - calculo0;
		}
		if (calculo1 >= ConstantesNumero.N10) {
			calculo1 = ConstantesNumero.N11 - calculo1;
		}
		if ((calculo0 != Integer.parseInt(dc.substring(0, ConstantesNumero.N1), ConstantesNumero.N10))
				|| (calculo1 != Integer.parseInt(dc.substring(ConstantesNumero.N1, ConstantesNumero.N2),
						ConstantesNumero.N10))) {
			res = false;
		} else {
			res = true;
		}
		return res;
	}

	/**
	 * Valida si el parametro String es valido (No es nulo y tiene contenido).
	 *
	 * @param token
	 *            String a Validar
	 * @return true: si valido true, false: si cadena vacia
	 */
	private static boolean esCadenaVacia(final String token) {
		return (isNull(token) || (token.length() == 0));
	}

	public boolean esFecha(final String fecha) {
		boolean res = (!esCadenaVacia(fecha) && compruebaRegExp(fecha, patronFecha));
		if (!res) {
			res = esFecha(fecha, FORMATO_FECHA);
		}
		return res;
	}

	public boolean esFecha(final String fecha, final String formato) {
		boolean res;
		if (esCadenaVacia(fecha)) {
			res = false;
		} else {
			try {
				final SimpleDateFormat formatoFecha = new SimpleDateFormat(formato);
				formatoFecha.setLenient(false);
				final Date fechaDate = formatoFecha.parse(fecha);

				if (fechaDate == null) {
					res = false;
				} else {
					res = true;
				}
			} catch (final ParseException e) {
				res = false;
			}
		}
		return res;
	}

	public boolean esImporte(final String importe) {
		return (!esCadenaVacia(importe) && compruebaRegExp(importe, patronImporte));
	}

	/**
	 * Método para Es numerico de ValidacionesTipoImpl.
	 *
	 * @param numero
	 *            Parámetro numero
	 * @return true, si es satisfactorio
	 */
	private boolean esNumerico(final String numero) {
		return (!esCadenaVacia(numero) && numero.matches("[0-9]+"));
	}

	public boolean esEntero(final String numero) {
		boolean res = false;
		try {
			res = (!esCadenaVacia(numero) && (esNumerico(numero) || (Integer.parseInt(numero) <= 0)));
		} catch (final NumberFormatException e) {
			res = false;
		}
		return res;
	}

	public boolean esNatural(final String numero) {
		boolean res = false;
		try {
			res = (!esCadenaVacia(numero) && (Long.parseLong(numero) >= 0));
		} catch (final NumberFormatException e) {
			res = false;
		}
		return res;
	}

	public boolean validaRango(final int pvalor, final int pminimo, final int pmaximo) {
		final Integer valor = new Integer(pvalor);
		final Integer minimo = new Integer(pminimo);
		final Integer maximo = new Integer(pmaximo);
		boolean resultado = false;
		if (isNull(valor) || (isNull(maximo) && isNull(minimo))) {
			resultado = false;
		} else {
			// Verifica maximo
			resultado = isNull(maximo) || (isNotNull(maximo) && (valor.compareTo(maximo) <= 0));
			// Verifica minimo
			resultado = resultado && (isNull(minimo) || (isNotNull(minimo) && (valor.compareTo(minimo) >= 0)));
		}
		return resultado;
	}

	public boolean validaRangoF(final float pvalor, final float pminimo, final float pmaximo) {
		final Float valor = new Float(pvalor);
		final Float minimo = new Float(pminimo);
		final Float maximo = new Float(pmaximo);
		boolean resultado = false;
		if (isNull(valor) || (isNull(maximo) && isNull(minimo))) {
			resultado = false;
		} else {
			// Verifica maximo
			resultado = isNull(maximo) || (isNotNull(maximo) && (valor.compareTo(maximo) <= 0));
			// Verifica minimo
			resultado = resultado && (isNull(minimo) || (isNotNull(minimo) && (valor.compareTo(minimo) >= 0)));
		}
		return resultado;
	}

	public boolean esEmail(final String email) {
		boolean resultado = false;
		if (!esCadenaVacia(email)) {
			final Matcher m = patronEmail.matcher(email);
			resultado = m.matches();
		}
		return resultado;
	}

	public boolean esURL(final String pUrl) {
		boolean resultado = false;
		if (!esCadenaVacia(pUrl)) {
			final UrlValidator urlValidator = new UrlValidator();
			if (urlValidator.isValid(pUrl)) {
				resultado = true;
			}
		}
		return resultado;
	}

	public boolean esTelefono(final String telefono) {
		boolean respuesta = false;
		if (esTelefonoFijo(telefono) || esTelefonoMovil(telefono)) {
			respuesta = true;
		}
		return respuesta;
	}

	/**
	 * Método para Valida fecha fin de la clase ValidacionesTipoImpl.
	 *
	 * @param fechaUno
	 *            Parámetro fecha uno
	 * @param fechaDos
	 *            Parámetro fecha dos
	 * @return el int
	 */
	private int validaFechaFin(final Date fechaUno, final Date fechaDos) {
		int resultado = 0;
		if ((isNotNull(fechaUno) && isNotNull(fechaDos)) && fechaUno.after(fechaDos)) {
			resultado = ConstantesNumero.N1;
		} else {
			if (fechaUno.before(fechaDos)) {
				resultado = ConstantesNumero.N_1;
			}
		}
		return resultado;
	}

	public int validaFechaFin(final String fechaUno, final String fechaDos) throws ValidacionTipoException {
		return validaFechaFinImpl(fechaUno, fechaDos, FORMATO_FECHA);
	}

	/**
	 * Método para Valida fecha fin impl de la clase ValidacionesTipoImpl.
	 *
	 * @param fechaUno
	 *            Parámetro fecha uno
	 * @param fechaDos
	 *            Parámetro fecha dos
	 * @param formatoFecha2
	 *            Parámetro formato fecha2
	 * @return el int
	 * @throws ValidacionTipoException
	 *             Excepcion validacion
	 */
	private int validaFechaFinImpl(final String fechaUno, final String fechaDos, final String formatoFecha2)
			throws ValidacionTipoException {
		if ((fechaUno == null) || (fechaDos == null)) {
			throw new ValidacionTipoException("Se está pasando una fecha vacía.");
		}
		try {
			final SimpleDateFormat formatoFecha = new SimpleDateFormat(formatoFecha2);
			formatoFecha.setLenient(false);
			final Date fUno = formatoFecha.parse(fechaUno);
			final Date fDos = formatoFecha.parse(fechaDos);
			return validaFechaFin(fUno, fDos);
		} catch (final ParseException e) {
			throw new ValidacionTipoException("El formato de fecha no es correcta. Debe ser " + formatoFecha2 + ".", e);
		}
	}

	public int validaFechaActual(final String fecha) throws ValidacionTipoException {
		return validaFechaActualImpl(fecha, FORMATO_FECHA);
	}

	/**
	 * Valida contra fecha actual.
	 *
	 * @param fecha
	 *            Fecha
	 * @param formato
	 *            Formato fecha
	 * @return entero
	 * @throws ValidacionTipoException
	 */
	private int validaFechaActualImpl(final String fecha, final String formato) throws ValidacionTipoException {
		final SimpleDateFormat formatoFecha = new SimpleDateFormat(formato);
		formatoFecha.setLenient(false);
		if (fecha == null) {
			throw new ValidacionTipoException("Se está pasando una fecha vacía.");
		}
		Date fechaActual = new Date();
		final String fechaActualStr = formatoFecha.format(fechaActual);
		try {
			fechaActual = formatoFecha.parse(fechaActualStr);
			final Date f = formatoFecha.parse(fecha);
			return validaFechaFin(f, fechaActual);
		} catch (final ParseException e) {
			throw new ValidacionTipoException("El formato de fecha no es correcto. Debe ser " + formato + ".", e);
		}
	}

	public String formateaNombreApellidos(final String formato, final String nombre, final String apellido1,
			final String apellido2) {
		String nom = nombre;
		String ape1 = apellido1;
		String ape2 = apellido2;
		String sepApe2;
		if (esCadenaVacia(ape1)) {
			ape1 = "";
		}
		if (esCadenaVacia(ape2)) {
			ape2 = "";
			sepApe2 = "";
		} else {
			sepApe2 = " ";
		}
		if (esCadenaVacia(nom)) {
			nom = "";
		}
		final StringBuffer sb = new StringBuffer(500);
		if ("AN".equals(formato)) {
			sb.append(ape1).append(sepApe2).append(ape2).append(", ").append(nom);
		} else if ("NA".equals(formato)) {
			sb.append(nom).append(" ").append(ape1).append(sepApe2).append(ape2);
		}
		return sb.toString();
	}

	/**
	 * Valida si el objeto pasado es nulo.
	 *
	 * @param nulo
	 *            Objeto a Validar
	 * @return true, si es null true si es valido false si no es valido
	 */
	private static boolean isNull(final Object nulo) {
		return (nulo == null);
	}

	/**
	 * Valida si el objeto pasado no es nulo.
	 *
	 * @param nulo
	 *            Objeto a Validar
	 * @return true, si es not null true si es valido false si no es valido
	 */
	private static boolean isNotNull(final Object nulo) {
		return (nulo != null);
	}

	/**
	 * Comprueba longitud.
	 *
	 * @param texto
	 *            Texto
	 * @param tamanyo
	 *            Tamaño
	 * @return 1 / -1
	 */
	private static int longitudJava(final String texto, final long tamanyo) {
		int result = 0;
		if (texto.length() > tamanyo) {
			result = ConstantesNumero.N1;
		} else if (texto.length() < tamanyo) {
			result = ConstantesNumero.N_1;
		} else {
			result = 0;
		}
		return result;
	}

	public int validaFechaHoraFin(final String pFechaHoraUno, final String pFechaHoraDos) throws ValidacionTipoException {
		return validaFechaFinImpl(pFechaHoraUno, pFechaHoraDos, FORMATO_FECHAHORA);
	}

	public int validaFechaHoraActual(final String pFechaHora) throws ValidacionTipoException {
		return validaFechaActualImpl(pFechaHora, FORMATO_FECHAHORA);
	}

	public String getFechaActual() {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date());
	}

	public boolean esTelefonoFijo(final String pTelefono) {
		boolean respuesta = false;
		if (!esCadenaVacia(pTelefono) && compruebaRegExp(pTelefono, patronTelefono)) {
			if (pTelefono.startsWith("9") || pTelefono.startsWith("8")) {
				respuesta = true;
			}
		}
		return respuesta;
	}

	public boolean esTelefonoMovil(final String pTelefono) {
		boolean respuesta = false;
		if (!esCadenaVacia(pTelefono) && compruebaRegExp(pTelefono, patronTelefono)) {
			if (pTelefono.startsWith("6") || pTelefono.startsWith("7")) {
				respuesta = true;
			}
		}
		return respuesta;
	}

	public double formateaCadenaADouble(final String pNumero, final String pSeparadorMiles,
			final String pSeparadorDecimales) throws ValidacionTipoException {
		validarSeparadoresFormatearDouble(pSeparadorMiles, pSeparadorDecimales);
		String numeroFormateado = "0";
		if (!StringUtils.isEmpty(pNumero)) {
			// Quitamos espacios, separador miles y establecemos separador
			// decimal java
			numeroFormateado = StringUtils.replace(pNumero, " ", "");
			if (StringUtils.isNotEmpty(pSeparadorMiles)) {
				numeroFormateado = StringUtils.replace(numeroFormateado, pSeparadorMiles, "");
			}
			numeroFormateado = StringUtils.replace(numeroFormateado, pSeparadorDecimales, ".");
			// Validamos formato numero
			final String pattern = "^[-]?[0-9]+(\\.[0-9]+)?$";
			final Pattern p = Pattern.compile(pattern);
			if (!p.matcher(numeroFormateado).find()) {
				throw new ValidacionTipoException("Numero no valido: " + pNumero);
			}
		}
		try {
			final double res = Double.parseDouble(numeroFormateado);
			return res;
		} catch (final NumberFormatException nfe) {
			throw new ValidacionTipoException("Numero no valido: " + pNumero);
		}
	}

	public String formateaDoubleACadena(final double pNumero, final String pSeparadorMiles,
			final String pSeparadorDecimales, final int numeroDecimales) throws ValidacionTipoException {
		validarSeparadoresFormatearDouble(pSeparadorMiles, pSeparadorDecimales);
		NumberFormat nf = null;
		String pattern = "";
		if (!StringUtils.isEmpty(pSeparadorMiles)) {
			pattern = "###,###,###.##";
		} else {
			pattern = "#########.##";
		}
		if (",".equals(pSeparadorDecimales)) {
			nf = NumberFormat.getInstance(Locale.GERMAN);
		} else {
			nf = NumberFormat.getInstance(Locale.US);
		}
		final DecimalFormat formatter = (DecimalFormat) nf;
		formatter.applyPattern(pattern);
		formatter.setGroupingUsed(true);
		nf.setMaximumFractionDigits(numeroDecimales);
		nf.setMinimumFractionDigits(numeroDecimales);
		final String numero = formatter.format(pNumero);
		return numero;
	}

	/**
	 * Valida los separadores usados en la conversion de double a cadena y
	 * viceversa.
	 *
	 * @param pSeparadorMiles
	 *            Separador miles
	 * @param pSeparadorDecimales
	 *            Separador decimales
	 * @throws ValidacionTipoException
	 */
	private void validarSeparadoresFormatearDouble(final String pSeparadorMiles, final String pSeparadorDecimales)
			throws ValidacionTipoException {
		if (StringUtils.isEmpty(pSeparadorDecimales)) {
			throw new ValidacionTipoException("No se ha especificado separador de decimales");
		}
		if (!StringUtils.equals(".", pSeparadorDecimales) && !StringUtils.equals(",", pSeparadorDecimales)) {
			throw new ValidacionTipoException(
					"No se ha especificado un separador de decimales valido: '" + pSeparadorDecimales + "'");
		}
		if (StringUtils.isNotEmpty(pSeparadorMiles) && !StringUtils.equals(".", pSeparadorMiles)
				&& !StringUtils.equals(",", pSeparadorMiles)) {
			throw new ValidacionTipoException(
					"No se ha especificado un separador de miles valido: '" + pSeparadorMiles + "'");
		}
		if (StringUtils.equals(pSeparadorMiles, pSeparadorDecimales)) {
			throw new ValidacionTipoException("El separador de miles es igual al separador de decimales: '"
					+ pSeparadorMiles + "' - '" + pSeparadorDecimales + "'");
		}
	}

	public int obtenerAnyo(final String pFecha) throws ValidacionTipoException {
		try {
			final Date fecha = parseFecha(pFecha, FORMATO_FECHA);
			final Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			return cal.get(Calendar.YEAR);
		} catch (final ArrayIndexOutOfBoundsException ex) {
			throw new ValidacionTipoException("El formato de la fecha introducida es incorrecto", ex);
		}
	}

	public int obtenerMes(final String pFecha) throws ValidacionTipoException {
		try {
			final Date fecha = parseFecha(pFecha, FORMATO_FECHA);
			final Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			return cal.get(Calendar.MONTH) + ConstantesNumero.N1;
		} catch (final ArrayIndexOutOfBoundsException ex) {
			throw new ValidacionTipoException("El formato de la fecha introducida es incorrecto", ex);
		}
	}

	public int obtenerDia(final String pFecha) throws ValidacionTipoException {
		try {
			final Date fecha = parseFecha(pFecha, FORMATO_FECHA);
			final Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			return cal.get(Calendar.DAY_OF_MONTH);
		} catch (final ArrayIndexOutOfBoundsException ex) {
			throw new ValidacionTipoException("El formato de la fecha introducida es incorrecto", ex);
		}
	}

	public String sumaDias(final String pFecha, final int pDias) throws ValidacionTipoException {
		final Date fecha = parseFecha(pFecha, FORMATO_FECHA);
		final Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DAY_OF_MONTH, pDias);
		return getFecha(cal.getTime(), FORMATO_FECHA);
	}

	/**
	 * Parsea fecha de string.
	 *
	 * @param pFecha
	 *            fecha
	 * @param formato
	 *            formato
	 * @return fecha
	 * @throws ValidacionTipoException
	 */
	private Date parseFecha(final String pFecha, final String formato) throws ValidacionTipoException {
		try {
			final SimpleDateFormat df = new SimpleDateFormat(formato);
			df.setLenient(false);
			return df.parse(pFecha);
		} catch (final ParseException e) {
			throw new ValidacionTipoException("El formato de la fecha introducida es incorrecto", e);
		}
	}

	public int distanciaDias(final String pFecha1, final String pFecha2) throws ValidacionTipoException {
		return distanciaDiasImpl(pFecha1, pFecha2, false);
	}

	public int distanciaDiasHabiles(final String pFecha1, final String pFecha2) throws ValidacionTipoException {
		return distanciaDiasImpl(pFecha1, pFecha2, true);
	}

	/**
	 * Calcula distancia entre dias.
	 *
	 * @param pFecha1
	 *            Fecha inicio
	 * @param pFecha2
	 *            Fecha fin
	 * @param pHabiles
	 *            habiles
	 * @return distancia entre dias
	 * @throws ValidacionTipoException
	 */
	private int distanciaDiasImpl(final String pFecha1, final String pFecha2, final boolean pHabiles)
			throws ValidacionTipoException {
		int distancia = 0;
		final Date d1 = parseFecha(pFecha1, FORMATO_FECHA);
		if (d1 == null) {
			distancia = 0;
		} else {
			final Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(d1);

			final Date d2 = parseFecha(pFecha2, FORMATO_FECHA);
			if (d2 == null) {
				distancia = 0;
			} else {
				final Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(d2);
				int mult = 0;
				if (d2.after(d1)) {
					mult = ConstantesNumero.N1;
				} else {
					mult = ConstantesNumero.N_1;
				}
				while (!calendar1.getTime().equals(calendar2.getTime())) {
					calendar1.add(Calendar.DATE, mult);
					if (pHabiles) {
						final int dia = calendar1.get(Calendar.DAY_OF_WEEK);
						if (dia != Calendar.SATURDAY && dia != Calendar.SUNDAY) {
							distancia += mult;
						}
					} else {
						distancia += mult;
					}
				}
			}
		}
		return distancia;
	}

	/**
	 * Chequea codigo IBAN de una cuenta.
	 *
	 * @param accountNumber
	 *            Cuenta
	 * @return true si OK
	 */
	private boolean checkIBAN(final String accountNumber) {

		final int IBANNUMBER_MIN_SIZE = 15;
		final int IBANNUMBER_MAX_SIZE = 34;
		final BigInteger IBANNUMBER_MAGIC_NUMBER = new BigInteger("97");

		String newAccountNumber = accountNumber.trim();

		// Check that the total IBAN length is correct as per the country. If
		// not, the IBAN is invalid. We could also check
		// for specific length according to country, but for now we won't
		if (newAccountNumber.length() < IBANNUMBER_MIN_SIZE || newAccountNumber.length() > IBANNUMBER_MAX_SIZE) {
			return false;
		}

		// Move the four initial characters to the end of the string.
		newAccountNumber = newAccountNumber.substring(4) + newAccountNumber.substring(0, 4);

		// Replace each letter in the string with two digits, thereby expanding
		// the string, where A = 10, B = 11, ..., Z = 35.
		final StringBuilder numericAccountNumber = new StringBuilder();
		for (int i = 0; i < newAccountNumber.length(); i++) {
			numericAccountNumber.append(Character.getNumericValue(newAccountNumber.charAt(i)));
		}

		// Interpret the string as a decimal integer and compute the remainder
		// of that number on division by 97.
		final BigInteger ibanNumber = new BigInteger(numericAccountNumber.toString());
		return ibanNumber.mod(IBANNUMBER_MAGIC_NUMBER).intValue() == 1;

	}

	public String formateaBarcode128(final String pCodigo) {
		int suma = ConstantesNumero.N104;
		final char[] arr = pCodigo.toCharArray();
		int c = ConstantesNumero.N1;
		for (final char ch : arr) {
			final String caracter = Character.toString(ch);
			suma += obtenerPeso(caracter) * c++;
		}
		final int total = suma % ConstantesNumero.N103;
		final String checksum = obtenerChecksum(total);
		final String barras = "Ì" + pCodigo + checksum + "Î";
		return barras;
	}

	public String montarCadenaBarcode(final String pIdAplicacion, final String pTipoFormato,
			final String pNumeroJustificante, final String pCodigoTerritorial, final String pConcepto,
			final String pImporte, final String pNif) {
		// Si idAplicación es nulo, pondrá el valor por defecto (90)
		String idAplicacionTratada = pIdAplicacion;
		if (idAplicacionTratada == null) {
			idAplicacionTratada = "90";
		}
		// Si el tipoFormato es nulo, pondrá el valor por defecto (564)
		String tipoFormatoTratado = pTipoFormato;
		if (tipoFormatoTratado == null) {
			tipoFormatoTratado = "564";
		}
		// Tratamos el importe por si no nos llega en céntimos
		String importeCentimos = pImporte;
		final int indx = importeCentimos.indexOf(".");
		final int indx2 = importeCentimos.indexOf(",");
		if (indx > ConstantesNumero.N_1 || indx2 > ConstantesNumero.N_1) {
			String entera = "";
			String decimales = "";
			if (indx > indx2) {
				entera = importeCentimos.substring(0, indx);
				decimales = importeCentimos.substring(indx + ConstantesNumero.N1, importeCentimos.length());
			} else {
				entera = importeCentimos.substring(0, indx2);
				decimales = importeCentimos.substring(indx2 + ConstantesNumero.N1, importeCentimos.length());
			}
			decimales = StringUtils.rightPad(decimales, ConstantesNumero.N2, '0');
			entera = StringUtils.replace(entera, ".", "");
			entera = StringUtils.replace(entera, ",", "");
			importeCentimos = entera + decimales;

		}

		return StringUtils.leftPad(idAplicacionTratada, ConstantesNumero.N2, '0')
				+ StringUtils.leftPad(tipoFormatoTratado, ConstantesNumero.N3, '0')
				+ StringUtils.leftPad(pNumeroJustificante, ConstantesNumero.N13, '0') + pCodigoTerritorial
				+ StringUtils.leftPad(pConcepto, ConstantesNumero.N4, '0')
				+ StringUtils.leftPad(importeCentimos, ConstantesNumero.N8, '0')
				+ StringUtils.leftPad(pNif, ConstantesNumero.N9, '0');
	}

	/**
	 * Función para obtener peso para cálculo de barcode 128.
	 *
	 * @param caracter
	 *            caracter
	 * @return peso del caracter
	 */
	private int obtenerPeso(final String caracter) {
		int i = 0;
		for (final String valor : codigosB) {
			if (valor.equals(caracter)) {
				return Integer.parseInt(valores[i]);
			}
			i++;
		}
		throw new RuntimeException("El valor es incorrecto");
	}

	/**
	 * Obtiene el checksum para el code 128.
	 *
	 * @param valor
	 *            valor del cálculo
	 * @return el cecksum
	 */
	private String obtenerChecksum(final int valor) {
		int i = 0;
		for (final String vl : valores) {
			if (vl.equals(String.valueOf(valor))) {
				return caracteres[i];
			}
			i++;
		}
		throw new RuntimeException("El checksum es incorrecto");
	}

	private String getFecha(final Date datetime, final String formato) {
		String respuesta = "";
		if (datetime == null) {
			respuesta = "";
		} else {
			final Calendar lFecha = Calendar.getInstance(new Locale("es", "ES"));
			lFecha.setTime(datetime);

			final String lsDia = StringUtils.leftPad(Integer.toString(lFecha.get(Calendar.DAY_OF_MONTH)),
					ConstantesNumero.N2, '0');

			final String lsMes = StringUtils.leftPad(Integer.toString(ConstantesNumero.N1 + lFecha.get(Calendar.MONTH)),
					ConstantesNumero.N2, '0');

			final String lsAnyo = StringUtils.leftPad(Integer.toString(lFecha.get(Calendar.YEAR)), 4, '0');

			final String lsAnyo2 = lsAnyo.substring(2, 4);

			final String lsHora24 = StringUtils.leftPad(Integer.toString(lFecha.get(Calendar.HOUR_OF_DAY)),
					ConstantesNumero.N2, '0');

			final String lsMinuto = StringUtils.leftPad(Integer.toString(lFecha.get(Calendar.MINUTE)),
					ConstantesNumero.N2, '0');

			final String lsSegundo = StringUtils.leftPad(Integer.toString(lFecha.get(Calendar.SECOND)),
					ConstantesNumero.N2, '0');

			final String lsHora = StringUtils.leftPad(Integer.toString(lFecha.get(Calendar.HOUR)), ConstantesNumero.N2,
					'0');

			// String ls_posMeridian =
			// Integer.toString(l_fecha.get(Calendar.AM_PM));

			String lsRes = formato.toUpperCase();
			lsRes = StringUtils.replace(lsRes, "YYYY", lsAnyo);
			lsRes = StringUtils.replace(lsRes, "YY", lsAnyo2);
			lsRes = StringUtils.replace(lsRes, "MM", lsMes);
			lsRes = StringUtils.replace(lsRes, "DD", lsDia);
			lsRes = StringUtils.replace(lsRes, "HH24", lsHora24);
			lsRes = StringUtils.replace(lsRes, "HH", lsHora);
			lsRes = StringUtils.replace(lsRes, "MI", lsMinuto);
			lsRes = StringUtils.replace(lsRes, "SS", lsSegundo);
			respuesta = lsRes;
		}
		return respuesta;
	}

}
