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

	/** Atributo formato fecha. */
	public static final String FORMATO_FECHA = "dd/MM/yyyy";

	/** Atributo formato fecha-hora. */
	public static final String FORMATO_FECHAHORA = "dd/MM/yyyy HH:mm:ss";

	/** Atributo formato fecha. */
	public static final String FORMATO_FECHA_INTERNACIONAL = "yyyy-MM-dd";

	/** Atributo formato fecha-hora. */
	public static final String FORMATO_FECHAHORA_INTERNACIONAL = "yyyy-MM-dd HH:mm:ss";

	/** Atributo constante PATRON_TELEFONO. */
	private final Pattern patronTelefono;

	/** Atributo constante PATRON_IMPORTE. */
	private final Pattern patronImporte;

	/** Atributo constante PATRON_EMAIL. */
	private final Pattern patronEmail;

	/** Atributo patron hora de ValidacionesTipoImpl. */
	private final Pattern patronHora;

	/** Atributo R e_ cuenta. */
	private static final String RE_CUENTA = "^[0-9]{20}$";

	/** Literal de error. **/
	private static final String FORMATO_FECHA_INCORRECTO = "El formato de la fecha introducida es incorrecto";

	/** Instancia. */
	private static ValidacionesTipo instance;

	public static final int DOCUMENTO_NO_VALIDO = -1;

	public static final int TIPO_DOCUMENTO_DNI = 1;
	public static final int TIPO_DOCUMENTO_NIE = 2;
	public static final int TIPO_DOCUMENTO_NIF_OTROS = 3;
	public static final int TIPO_DOCUMENTO_NIF_PJ = 4;
	public static final int TIPO_DOCUMENTO_NSS = 5;

	/** Variable codigosB para code128. */
	static final String[] codigosB = new String[] { " ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",",
			"-", ".", "/", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ":", ";", "<", "=", ">", "?", "@", "A",
			"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "[", "\\", "]", "^", "_", "`", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
			"l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "{", "|", "}", "~" };

	/** Variable caracteres para code128. */
	static final String[] caracteres = new String[] { "Â", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",",
			"-", ".", "/", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ":", ";", "<", "=", ">", "?", "@", "A",
			"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "[", "\\", "]", "^", "_", "`", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
			"l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "{", "|", "}", "~", "Ã", "Ä",
			"Å", "Æ", "Ç", "È", "É", "Ê", "Ë", "Ì", "Í", "Î" };

	/** Variable valores para code128. */
	static final String[] valores = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
			"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
			"31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48",
			"49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66",
			"67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84",
			"85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", "101",
			"102", "103", "104", "105", "na" };

	/** Constantes para el IBAN. **/
	private static final int IBANNUMBER_MIN_SIZE = 15;
	private static final int IBANNUMBER_MAX_SIZE = 34;
	private static final BigInteger IBANNUMBER_MAGIC_NUMBER = BigInteger.valueOf(new Long("97"));

	/**
	 * Constructor.
	 */
	private ValidacionesTipo() {
		super();
		patronTelefono = Pattern.compile("^\\d{9}$");
		patronImporte = Pattern.compile("^[0-9]+(,[0-9]{1,2})?$");
		patronEmail = Pattern.compile(
				"^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,4}$");
		patronHora = Pattern.compile("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
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
	 *                    Texto a validar
	 * @param pattern
	 *                    Patron que valida el texto
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

	public boolean esIdentificacion(final String identificacion, final boolean esDni, final boolean esNie,
			final boolean esOtrosNif, final boolean esNif, final boolean esNss) {
		return NifUtils.esIdentificacion(identificacion, esDni, esNie, esOtrosNif, esNif, esNss);
	}

	public boolean esDni(final String valor) {
		return NifUtils.esDni(valor);
	}

	public boolean esNie(final String valor) {
		return NifUtils.esNie(valor);
	}

	public boolean esNifOtros(final String valor) {
		return NifUtils.esNifOtros(valor);
	}

	public boolean esNifPersonaJuridica(final String valor) {
		return NifUtils.esNifPersonaJuridica(valor);
	}

	public boolean esNifPersonaFisica(final String valor) {
		return NifUtils.esNifPersonaFisica(valor);
	}

	public boolean esNif(final String valor) {
		return NifUtils.esNifPersonaFisica(valor) || NifUtils.esNifPersonaJuridica(valor);
	}

	public boolean esNumeroSeguridadSocial(final String nss) {
		return NifUtils.esNSS(nss);
	}

	public int validaDocumento(final String valor) {
		if (esDni(valor))
			return TIPO_DOCUMENTO_DNI;
		if (esNie(valor))
			return TIPO_DOCUMENTO_NIE;
		if (esNifOtros(valor))
			return TIPO_DOCUMENTO_NIF_OTROS;
		if (esNifPersonaJuridica(valor))
			return TIPO_DOCUMENTO_NIF_PJ;
		if (esNumeroSeguridadSocial(valor))
			return TIPO_DOCUMENTO_NSS;
		return DOCUMENTO_NO_VALIDO;
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
			if (ncc.length() != ConstantesNumero.N20 || (!Pattern.matches(RE_CUENTA, ncc))
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
	 * Comprueba si el codigo SWIFT valido.
	 *
	 * @param codigo
	 *                   Codigo SWIFT
	 * @return
	 */
	public boolean esNumeroSwiftValido(final String codigo) {
		boolean valido;
		if (codigo == null || (codigo.length() != 8 && codigo.length() != 11)) {
			valido = false;
		} else {
			final String banco = codigo.substring(0, 4);
			final String pais = codigo.substring(4, 6);
			final String localidad = codigo.substring(6, 8);
			String sucursal = null;
			if (codigo.length() == 11) {
				sucursal = codigo.substring(8, 11);
			}
			valido = esNumeroSwiftValido(banco, pais, localidad, sucursal);
		}
		return valido;
	}

	/**
	 * Comprueba si el codigo SWIFT valido.
	 *
	 * @param banco
	 *                      Codigo Banco
	 * @param pais
	 *                      Codigo pais
	 * @param localidad
	 *                      Codigo localidad
	 * @param sucursal
	 *                      Codigo sucursal (no obligatorio)
	 * @return
	 */
	public boolean esNumeroSwiftValido(final String banco, final String pais, final String localidad,
			final String sucursal) {
		final boolean codigoBancoCorrecto = banco != null && banco.length() == 4 && StringUtils.isAllUpperCase(banco);
		final boolean codigoPaisCorrecto = pais != null && pais.length() == 2 && StringUtils.isAllUpperCase(pais);
		final boolean codigoLocalidadCorrecto = localidad != null && localidad.length() == 2
				&& (StringUtils.isNumeric(localidad) || StringUtils.isAllUpperCase(localidad));
		final boolean codigoSucursalCorrecto = esNumeroSwiftValidoSucursal(sucursal);

		return codigoBancoCorrecto && codigoPaisCorrecto && codigoLocalidadCorrecto && codigoSucursalCorrecto;
	}

	/**
	 * Comprueba si el num. sucursal es valido (es opcional el dato). Tienen que ser
	 * o mayúsculas o numérico (mezclado).
	 *
	 * @param sucursal
	 * @return
	 */
	private boolean esNumeroSwiftValidoSucursal(final String sucursal) {
		boolean validoSucursal;
		if (sucursal == null) {
			validoSucursal = true;
		} else {
			validoSucursal = true;
			for (final char caracter : sucursal.toCharArray()) {
				if (!StringUtils.isAllUpperCase(String.valueOf(caracter))
						&& !StringUtils.isNumeric(String.valueOf(caracter))) {
					validoSucursal = false;
					break;
				}
			}
		}
		return validoSucursal;
	}

	/**
	 * Verifica digitos cuenta.
	 *
	 * @param entidad
	 *                     entidad
	 * @param sucursal
	 *                     sucursal
	 * @param dc
	 *                     digito control
	 * @param cuenta
	 *                     num cuenta
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
	 *                  String a Validar
	 * @return true: si valido true, false: si cadena vacia
	 */
	private static boolean esCadenaVacia(final String token) {
		return (isNull(token) || (token.length() == 0));
	}

	public boolean esHora(final String hora) {
		return (!esCadenaVacia(hora) && compruebaRegExp(hora, patronHora));
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
	 *                   Parámetro numero
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
		final Integer valor = Integer.valueOf(pvalor);
		final Integer minimo = Integer.valueOf(pminimo);
		final Integer maximo = Integer.valueOf(pmaximo);
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
		final Float valor = Float.valueOf(pvalor);
		final Float minimo = Float.valueOf(pminimo);
		final Float maximo = Float.valueOf(pmaximo);
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

	public boolean esURL(final String url) {
		boolean resultado = false;
		if (!esCadenaVacia(url)) {
			final UrlValidator urlValidator = new UrlValidator();
			if (urlValidator.isValid(url)) {
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
	 *                     Parámetro fecha uno
	 * @param fechaDos
	 *                     Parámetro fecha dos
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

	/**
	 * Método para Valida fecha fin impl de la clase ValidacionesTipoImpl.
	 *
	 * @param fechaUno
	 *                          Parámetro fecha uno
	 * @param fechaDos
	 *                          Parámetro fecha dos
	 * @param iformatoFecha
	 *                          Parámetro formato fecha
	 * @return el int
	 * @throws ValidacionTipoException
	 *                                     Excepcion validacion
	 */
	public int validaFechaFin(final String fechaUno, final String fechaDos, final String iformatoFecha)
			throws ValidacionTipoException {
		if (fechaUno == null || fechaDos == null) {
			throw new ValidacionTipoException("Se está pasando una fecha vacía.");
		}
		try {
			final SimpleDateFormat formatoFecha = new SimpleDateFormat(iformatoFecha);
			formatoFecha.setLenient(false);
			final Date fUno = formatoFecha.parse(fechaUno);
			final Date fDos = formatoFecha.parse(fechaDos);
			return validaFechaFin(fUno, fDos);
		} catch (final ParseException e) {
			throw new ValidacionTipoException("El formato de fecha no es correcta. Debe ser " + iformatoFecha + ".", e);
		}
	}

	/**
	 * Valida contra fecha actual.
	 *
	 * @param fecha
	 *                    Fecha
	 * @param formato
	 *                    Formato fecha
	 * @return entero
	 * @throws ValidacionTipoException
	 */
	public int validaFechaActual(final String fecha, final String formato) throws ValidacionTipoException {
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
		final StringBuilder sb = new StringBuilder(500);
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
	 *                 Objeto a Validar
	 * @return true, si es null true si es valido false si no es valido
	 */
	private static boolean isNull(final Object nulo) {
		return (nulo == null);
	}

	/**
	 * Valida si el objeto pasado no es nulo.
	 *
	 * @param nulo
	 *                 Objeto a Validar
	 * @return true, si es not null true si es valido false si no es valido
	 */
	private static boolean isNotNull(final Object nulo) {
		return (nulo != null);
	}

	/**
	 * Comprueba longitud.
	 *
	 * @param texto
	 *                    Texto
	 * @param tamanyo
	 *                    Tamaño
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

	public int validaFechaHoraFin(final String pFechaHoraUno, final String pFechaHoraDos, final String formato)
			throws ValidacionTipoException {
		return validaFechaFin(pFechaHoraUno, pFechaHoraDos, formato);
	}

	public String getFechaActual(final String formato) {
		final SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return sdf.format(new Date());
	}

	public boolean esTelefonoFijo(final String pTelefono) {
		boolean respuesta = false;
		if (!esCadenaVacia(pTelefono) && compruebaRegExp(pTelefono, patronTelefono)
				&& (pTelefono.startsWith("9") || pTelefono.startsWith("8"))) {
			respuesta = true;
		}
		return respuesta;
	}

	public boolean esTelefonoMovil(final String pTelefono) {
		boolean respuesta = false;
		if (!esCadenaVacia(pTelefono) && compruebaRegExp(pTelefono, patronTelefono)
				&& (pTelefono.startsWith("6") || pTelefono.startsWith("7"))) {
			respuesta = true;
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
			return Double.parseDouble(numeroFormateado);
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
		return formatter.format(pNumero);
	}

	/**
	 * Valida los separadores usados en la conversion de double a cadena y
	 * viceversa.
	 *
	 * @param pSeparadorMiles
	 *                                Separador miles
	 * @param pSeparadorDecimales
	 *                                Separador decimales
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

	public int obtenerAnyo(final String pFecha, final String formato) throws ValidacionTipoException {
		try {
			final Date fecha = parseFecha(pFecha, formato);
			final Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			return cal.get(Calendar.YEAR);
		} catch (final ArrayIndexOutOfBoundsException ex) {
			throw new ValidacionTipoException(FORMATO_FECHA_INCORRECTO, ex);
		}
	}

	public int obtenerMes(final String pFecha, final String formato) throws ValidacionTipoException {
		try {
			final Date fecha = parseFecha(pFecha, formato);
			final Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			return cal.get(Calendar.MONTH) + ConstantesNumero.N1;
		} catch (final ArrayIndexOutOfBoundsException ex) {
			throw new ValidacionTipoException(FORMATO_FECHA_INCORRECTO, ex);
		}
	}

	public int obtenerDia(final String pFecha, final String formato) throws ValidacionTipoException {
		try {
			final Date fecha = parseFecha(pFecha, formato);
			final Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			return cal.get(Calendar.DAY_OF_MONTH);
		} catch (final ArrayIndexOutOfBoundsException ex) {
			throw new ValidacionTipoException(FORMATO_FECHA_INCORRECTO, ex);
		}
	}

	/**
	 * Suma dias a una fecha
	 *
	 * @param fecha
	 * @param dias
	 * @param formato
	 * @return
	 * @throws ValidacionTipoException
	 */
	public String sumaDias(final String fecha, final int dias, final String formato) throws ValidacionTipoException {
		return suma(fecha, dias, formato, Calendar.DAY_OF_MONTH);
	}

	/**
	 * Suma meses a una fecha
	 *
	 * @param fecha
	 * @param meses
	 * @param formato
	 * @return
	 * @throws ValidacionTipoException
	 */
	public String sumaMeses(final String fecha, final int meses, final String formato) throws ValidacionTipoException {
		return suma(fecha, meses, formato, Calendar.MONTH);
	}

	/**
	 * Suma anyos a una fecha
	 *
	 * @param fecha
	 * @param anyos
	 * @param formato
	 * @return
	 * @throws ValidacionTipoException
	 */
	public String sumaAnyos(final String fecha, final int anyos, final String formato) throws ValidacionTipoException {
		return suma(fecha, anyos, formato, Calendar.YEAR);
	}

	/**
	 * Suma dias/meses/años según el tipo a una fecha.
	 *
	 * @param fecha
	 * @param valor
	 * @param formato
	 * @param tipo
	 * @return
	 * @throws ValidacionTipoException
	 */
	private String suma(final String fecha, final int valor, final String formato, final int tipo)
			throws ValidacionTipoException {
		final Date dfecha = parseFecha(fecha, formato);
		final Calendar cal = Calendar.getInstance();
		cal.setTime(dfecha);
		cal.add(tipo, valor);
		return getFecha(cal.getTime(), formato);
	}

	/**
	 * Parsea fecha de string. Si es nulo, coge el formato por defecto.
	 *
	 * @param fecha
	 *                    fecha
	 * @param formato
	 *                    formato
	 * @return fecha
	 * @throws ValidacionTipoException
	 */
	public Date parseFecha(final String fecha, final String formato) throws ValidacionTipoException {
		Date res = null;
		try {
			if (fecha != null) {
				String formatoFecha;
				if (formato == null || formato.isEmpty()) {
					formatoFecha = ValidacionesTipo.FORMATO_FECHA;
				} else {
					formatoFecha = formato;
				}
				final SimpleDateFormat df = new SimpleDateFormat(formatoFecha);
				df.setLenient(false);
				res = df.parse(fecha);
			}
			return res;
		} catch (final ParseException e) {
			throw new ValidacionTipoException(FORMATO_FECHA_INCORRECTO, e);
		}
	}

	/**
	 * Parsea fecha de string.
	 *
	 * @param fecha
	 *                    fecha
	 * @param formato
	 *                    formato
	 * @return fecha
	 * @throws ValidacionTipoException
	 */
	public String formateaFecha(final Date fecha, final String formato) {
		String res = null;
		if (fecha != null) {
			final SimpleDateFormat df = new SimpleDateFormat(formato);
			df.setLenient(false);
			res = df.format(fecha);
		}
		return res;
	}

	/**
	 * Parsea fecha de string.
	 *
	 * @param fecha
	 *                    fecha
	 * @param formato
	 *                    formato
	 * @return fecha
	 * @throws ValidacionTipoException
	 */
	public String convierteFormatoFecha(final String fecha, final String formatoInicio, final String formatoFin)
			throws ValidacionTipoException {
		try {
			String res = null;
			if (fecha != null) {
				final SimpleDateFormat dfi = new SimpleDateFormat(formatoInicio);
				final Date fechaDate = dfi.parse(fecha);
				final SimpleDateFormat dff = new SimpleDateFormat(formatoFin);
				dff.setLenient(false);
				res = dff.format(fechaDate);
			}
			return res;
		} catch (final ParseException e) {
			throw new ValidacionTipoException("El formato de fecha no es correcta", e);
		}
	}

	/**
	 * Calcula distancia dias.
	 * 
	 * @param pFecha1
	 *                    Fecha inicio
	 * @param pFecha2
	 *                    Fecha fin
	 * @param formato
	 *                    Formato
	 * @return
	 * @throws ValidacionTipoException
	 */
	public int distanciaDias(final String pFecha1, final String pFecha2, final String formato)
			throws ValidacionTipoException {
		return distanciaDiasImpl(pFecha1, pFecha2, false, formato);
	}

	public int distanciaDiasHabiles(final String pFecha1, final String pFecha2, final String formato)
			throws ValidacionTipoException {
		return distanciaDiasImpl(pFecha1, pFecha2, true, formato);
	}

	/**
	 * Calcula distancia entre dias.
	 *
	 * @param pFecha1
	 *                     Fecha inicio
	 * @param pFecha2
	 *                     Fecha fin
	 * @param pHabiles
	 *                     habiles
	 * @return distancia entre dias
	 * @throws ValidacionTipoException
	 */
	private int distanciaDiasImpl(final String pFecha1, final String pFecha2, final boolean pHabiles,
			final String formato) throws ValidacionTipoException {
		int distancia = 0;
		final Date d1 = parseFecha(pFecha1, formato);
		if (d1 == null) {
			distancia = 0;
		} else {
			final Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(d1);

			final Date d2 = parseFecha(pFecha2, formato);
			if (d2 == null) {
				distancia = 0;
			} else {
				distancia = getDistanciaDias(d1, d2, calendar1, pHabiles);
			}
		}
		return distancia;
	}

	/**
	 * Metodo principal para calcular distancia de dias, que se quejaba el
	 * checkstyle por ser demasiado complejo el método.
	 **/
	private int getDistanciaDias(final Date d1, final Date d2, final Calendar calendar1, final boolean pHabiles) {
		int distancia = 0;
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
		return distancia;
	}

	/**
	 * Chequea codigo IBAN de una cuenta.
	 *
	 * @param accountNumber
	 *                          Cuenta
	 * @return true si OK
	 */
	private boolean checkIBAN(final String accountNumber) {

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

	/**
	 * Compara dos fechas y ve si la distancia entre dos fechas (en formato texto)
	 * concuerda con la distancia que se pasa como parámetro de entrada.
	 *
	 * @param fechaIni
	 * @param fechaFin
	 * @param formatoFecha
	 * @param numAnyos
	 * @return
	 * @throws ValidacionTipoException
	 */
	public boolean hayDistanciaAnyos(final String fechaIni, final String fechaFin, final String formatoFecha,
			final int numAnyos) throws ValidacionTipoException {

		Date iniDate;
		Date finDate;

		final SimpleDateFormat sdfFormatoFecha;
		if (formatoFecha == null || formatoFecha.isEmpty()) {
			sdfFormatoFecha = new SimpleDateFormat(ValidacionesTipo.FORMATO_FECHA);
		} else {
			sdfFormatoFecha = new SimpleDateFormat(formatoFecha);
		}
		try {
			iniDate = sdfFormatoFecha.parse(fechaIni);
			finDate = sdfFormatoFecha.parse(fechaFin);
		} catch (final Exception e) {
			throw new ValidacionTipoException("No se puede convertir correctamente las fechas.");
		}

		return hayDistanciaAnyos(iniDate, finDate, numAnyos);
	}

	/**
	 * Compara la distancia entre dos fechas y ve si la distancia concuerda con la
	 * que se pasa por entrada.
	 *
	 * @param fecha1
	 * @param fecha2
	 * @param numAnyos
	 * @return
	 */
	public boolean hayDistanciaAnyos(final Date fecha1, final Date fecha2, final int numAnyos) {
		boolean resultado = false;
		Calendar iniCalendar;
		Calendar finCalendar;

		if (fecha1 == null || fecha2 == null || numAnyos < 0) // No se proporciona una fecha válida o un numero de años
																// positivo
		{
			return resultado; // Salida de la función.
		}

		iniCalendar = Calendar.getInstance();
		finCalendar = Calendar.getInstance();
		iniCalendar.setTime(fecha1);
		finCalendar.setTime(fecha2);

		if (finCalendar.before(iniCalendar)) // La fecha menor de la comparación no es válida.
		{
			return resultado; // Salida de la función.
		}

		// Evaluación de la condición.
		iniCalendar.add(Calendar.YEAR, numAnyos); // Actualización de la fecha de comparación.
		resultado = finCalendar.before(iniCalendar); // Comparación de fechas.
		return !resultado; // Salida de la función.

	}
}
