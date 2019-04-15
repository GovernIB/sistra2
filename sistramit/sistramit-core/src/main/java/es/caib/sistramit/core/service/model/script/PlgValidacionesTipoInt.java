package es.caib.sistramit.core.service.model.script;

import es.caib.sistra2.commons.utils.ValidacionTipoException;

public interface PlgValidacionesTipoInt {

	/**
	 * Validamos si el Codigo Postal (CP) facilitado es Valido. Además, si facilitan
	 * el codigo de la provincia, se valida que coincidan. Las validaciones son:
	 * <br />
	 * que tenga 5 caracteres que sea numerico que el indice provincial no sea mayor
	 * de 54 (Melilla) que corresponda a la provincia si se facilita
	 *
	 * @param cp
	 *            Codigo Postal
	 * @param provincia
	 *            Codigo Provincial
	 * @return true, si es satisfactorio true Si es un CP valido false Si no es un
	 *         CP valido
	 */
	boolean esCP(final String cp, final String provincia);

	/**
	 * Valida si el parametro es un NIF o no. Valida que: <br />
	 * -- El parametro tenga 9 caracteres. <br />
	 * -- El parametro termina en una letra. <br />
	 * -- La letra cumple la validación.
	 *
	 * @param valor
	 *            nif que se quiere validar
	 * @return true, si es satisfactorio true Si es un NIF valido false Si no es un
	 *         NIF valido
	 */
	boolean esDni(final String valor);

	/**
	 * Valida si el parametro es un NIE o no. Valida que:<br />
	 * -- El parametro tenga 9 caracteres. <br />
	 * -- El parametro empieza por X/Y/Z <br />
	 * -- Contiene 7 dígitos <br />
	 * -- Finaliza con una la letra del digito de control
	 *
	 * @param nie
	 *            NIE que se quiere validar
	 * @return true, si es satisfactorio true Si es un NIE valido false Si no es un
	 *         NIE valido
	 */
	boolean esNie(final String nie);

	/**
	 * Valida si el parametro es un NIF Otros o no (es un nif para menores). Valida
	 * que:<br />
	 * -- El parametro tenga 9 caracteres. <br />
	 * -- El parametro empieza por J/K/L <br />
	 * -- Contiene 7 dígitos <br />
	 * -- Finaliza con una la letra del digito de control
	 *
	 * @param valor
	 *            nif que se quiere validar
	 * @return true, si es satisfactorio true Si es un NIF valido false Si no es un
	 *         NIF valido
	 */
	boolean esNifOtros(final String valor);

	/**
	 * Valida si el parametro es un NIF o no. Valida que: <br />
	 * -- El parametro tenga 9 caracteres. <br />
	 * -- El parametro termina en una letra. <br />
	 * -- La letra cumple la validación.
	 *
	 * @param valor
	 *            nif que se quiere validar
	 * @return true, si es satisfactorio true Si es un NIF valido false Si no es un
	 *         NIF valido
	 */
	boolean esNifPersonaJuridica(final String valor);

	/**
	 * Valida si es un nif válido de persona física.
	 * 
	 * @param valor
	 *            nif
	 * @return indica si es válido
	 */
	boolean esNifPersonaFisica(final String valor);

	/**
	 * Valida si es un nif válido (de persona física o jurídica).
	 * 
	 * @param valor
	 *            nif
	 * @return indica si es válido
	 */
	boolean esNif(final String valor);

	/**
	 * Valida si el parametro es un Numero de la Seguridad Social (NSS) o no. Valida
	 * que:<br />
	 * -- El formato inicial del parametro <br />
	 * -- El digito de control
	 *
	 * @param nss
	 *            NSS que se quiere validar
	 * @return true, si es satisfactorio true Si es un NSS valido false Si no es un
	 *         NSS valido
	 */
	boolean esNumeroSeguridadSocial(final String nss);

	/**
	 * Valida un número de cuenta (NCC) que viene junto en formato
	 * [entidad][sucursal][dc][cuenta]. No admite ningún tipo de separador, tan sólo
	 * las cifras del número de cuenta
	 *
	 * @param numeroCuenta
	 *            NCC qa validar
	 * @return true, si es satisfactorio true Si es un NCC valido false Si no es un
	 *         NCC valido
	 */
	boolean esNumeroCuentaValido(final String numeroCuenta);

	/**
	 * Valida un número de cuenta IBAN (NCC) que viene junto en formato
	 * [IBAN][entidad][sucursal][dc][cuenta]. No admite ningún tipo de separador,
	 * tan sólo las cifras del número de cuenta
	 *
	 * @param numeroCuenta
	 *            NCC qa validar
	 * @return true, si es satisfactorio true Si es un NCC valido false Si no es un
	 *         NCC valido
	 */
	boolean esNumeroCuentaIbanValido(final String numeroCuenta);

	/**
	 * Valida un número de cuenta (NCC) a partir de la entidad, sucursal, dc y
	 * cuenta.
	 *
	 * @param entidad
	 *            Codigo de 4 digitos de la entidad
	 * @param sucursal
	 *            Codigo de 4 digitos correspondiente a la sucursal
	 * @param dc
	 *            Codigo de 2 digitos correspondiente a los digitos de control
	 * @param cuenta
	 *            codigo de n digitos que identifica la cuenta
	 * @return true, si es satisfactorio true Si es un NCC valido false Si no es un
	 *         NCC valido
	 */
	boolean esNumeroCuentaValido(final String entidad, final String sucursal, final String dc, final String cuenta);

	/**
	 * Valida un número de cuenta iban (NCC) a partir de la entidad, sucursal, dc y
	 * cuenta.
	 *
	 * @param iban
	 *            Codigo de 4 digitos del IBAN
	 * @param entidad
	 *            Codigo de 4 digitos de la entidad
	 * @param sucursal
	 *            Codigo de 4 digitos correspondiente a la sucursal
	 * @param dc
	 *            Codigo de 2 digitos correspondiente a los digitos de control
	 * @param cuenta
	 *            codigo de n digitos que identifica la cuenta
	 * @return true, si es satisfactorio true Si es un NCC valido false Si no es un
	 *         NCC valido
	 */
	boolean esNumeroCuentaIbanValido(final String iban, final String entidad, final String sucursal, final String dc,
			final String cuenta);

	/**
	 * Verifica que el String sea tranformable a fecha (formato yyyy-MM-yyyy).
	 *
	 * @param fecha
	 *            Fecha a Validar
	 * @return true, si es satisfactorio true Si es una fecha valida false Si no es
	 *         una fecha valida
	 */
	boolean esFecha(final String fecha);

	/**
	 * Verifica que el String sea tranformable a fecha.
	 *
	 * @param fecha
	 *            Fecha a Validar
	 * @param formato
	 *            Formato fecha a Validar
	 * @return true, si es satisfactorio true Si es una fecha valida false Si no es
	 *         una fecha valida
	 */
	boolean esFecha(final String fecha, String formato);

	/**
	 * Verifica que el String sea un importe valido.
	 *
	 * @param importe
	 *            Importe a Validar
	 * @return true, si es satisfactorio true Si es un importe valido false Si no es
	 *         un importe valido
	 */
	boolean esImporte(final String importe);

	/**
	 * Valida si el numero es entero. Permite indicar el signo para los negativos
	 * (-).
	 *
	 * @param numero
	 *            Numero a validar
	 * @return true, si es satisfactorio true Si el numero es entero false Si el
	 *         numero no fuera entero
	 */
	boolean esEntero(final String numero);

	/**
	 * Valida si el numero es natural.
	 *
	 * @param numero
	 *            Numero a validar
	 * @return true, si es satisfactorio true Si el numero es entero false Si el
	 *         numero no fuera entero
	 */
	boolean esNatural(final String numero);

	/**
	 * Compara si el parámetro valor está dentro de un rango marcado. Si el valor
	 * introducido es un nulo, devuelve false. Si el rango es un nulo, comparará que
	 * sea mayor que el mínimo Si el rango mínimo es un nulo, comparará que sea
	 * menor que el máximo Si ambos límites son nulos, devolverá false Si los rangos
	 * no son correctos, devolvera false
	 *
	 * @param valor
	 *            Valor a validar dentro del rango
	 * @param minimo
	 *            Límite inferior del rango
	 * @param maximo
	 *            Límite superior del rango
	 * @return true, si es satisfactorio true Si está dentro del rango marcado false
	 *         Si no está dentro del rango marcado
	 */
	boolean validaRango(final int valor, final int minimo, final int maximo);

	/**
	 * Método para Es email de Validaciones.
	 *
	 * @param email
	 *            Parámetro email
	 * @return true, si es satisfactorio
	 */
	boolean esEmail(final String email);

	/**
	 * Valida si la cadena pasada por parametro es una URL. En caso que el texto no
	 * fuese una URL, se produciria una MalformedURLException
	 *
	 * @param url
	 *            Cadena de texto a validar
	 * @return true, si es satisfactorio true Si la cadena es valida false Si la
	 *         cadena no fuera valida
	 */
	boolean esURL(final String url);

	/**
	 * Comprueba si el telefono entregado pertenece a un teléfono fijo o móvil.
	 * válido
	 *
	 * @param telefono
	 *            Telefono a validar
	 * @return true, si es satisfactorio true Si el telefono es valido false Si el
	 *         telefono no fuera valido
	 */
	boolean esTelefono(final String telefono);

	/**
	 * Comprueba si el telefono entregado es numérico simple (numero natural), tiene
	 * un tamaño de 9 dígitos y que comienza por 9 u 8 (fijo).
	 *
	 * @param telefono
	 *            Telefono a validar
	 * @return true, si es satisfactorio true Si el telefono es valido false Si el
	 *         telefono no fuera valido
	 */
	boolean esTelefonoFijo(final String telefono);

	/**
	 * Comprueba si el telefono entregado es numérico simple (numero natural), tiene
	 * un tamaño de 9 dígitos y que comienza por 6 o por 7 (movil).
	 *
	 * @param telefono
	 *            Telefono a validar
	 * @return true, si es satisfactorio true Si el telefono es valido false Si el
	 *         telefono no fuera valido
	 */
	boolean esTelefonoMovil(final String telefono);

	/**
	 * Compara dos fechas.
	 *
	 * @param fechaUno
	 *            Parámetro fecha uno (formato fecha: yyyy-MM-yyyy)
	 * @param fechaDos
	 *            Parámetro fecha dos (formato fecha: yyyy-MM-yyyy)
	 * @return Devuelve (0) en caso de que la fecha sea igual, (1) en el caso de que
	 *         la fecha sea superior y (-1) si la fecha es inferior
	 * @throws ValidacionTipoException
	 */
	int validaFechaFin(final String fechaUno, final String fechaDos) throws ValidacionTipoException;

	/**
	 * Compara fecha actual con la pasada como parametro.
	 *
	 * @param fecha
	 *            Parámetro fecha (formato fecha: yyyy-MM-yyyy)
	 * @return Devuelve (0) en caso de que la fecha sea igual, (1) en el caso de que
	 *         la fecha sea superior a la actual y (-1) si la fecha es inferior a la
	 *         actual
	 * @throws ValidacionTipoException
	 */
	int validaFechaActual(final String fecha) throws ValidacionTipoException;

	/**
	 * Compara dos fechas (incluida hora).
	 *
	 * @param fechaHoraUno
	 *            Parámetro fecha uno (formato fecha: yyyy-MM-yyyy)
	 * @param fechaHoraDos
	 *            Parámetro fecha dos (formato fecha: yyyy-MM-yyyy)
	 * @return Devuelve (0) en caso de que la fecha sea igual, (1) en el caso de que
	 *         la fecha sea superior y (-1) si la fecha es inferior
	 * @throws ValidacionTipoException
	 */
	int validaFechaHoraFin(final String fechaHoraUno, final String fechaHoraDos) throws ValidacionTipoException;

	/**
	 * Método para Formatea nombre apellidos de la clase Validaciones.
	 *
	 * @param formato
	 *            Parámetro formato
	 * @param nombre
	 *            Parámetro nombre
	 * @param apellido1
	 *            Parámetro apellido1
	 * @param apellido2
	 *            Parámetro apellido2
	 * @return el string
	 */
	String formateaNombreApellidos(final String formato, final String nombre, final String apellido1,
			final String apellido2);

	/**
	 * Devuelve la fecha actual en formato yyyy-MM-yyyy.
	 *
	 * @return cadena resultado
	 */
	String getFechaActual();

	/**
	 * Método para formatear una cadena a tipo double.
	 *
	 * @param numero
	 *            Parámetro numero
	 * @param separadorMiles
	 *            Parámetro separador miles (. o ,). Opcional.
	 * @param separadorDecimales
	 *            Parámetro separador decimales (. o ,). Obligatorio.
	 * @return el double
	 * @throws ValidacionTipoException
	 */
	double formateaCadenaADouble(String numero, String separadorMiles, String separadorDecimales)
			throws ValidacionTipoException;

	/**
	 * Método para formatear un número de tipo double a cadena.
	 *
	 * @param numero
	 *            Parámetro numero
	 * @param separadorMiles
	 *            Parámetro separador miles (. o ,). Opcional.
	 * @param separadorDecimales
	 *            Parámetro separador decimales (. 0 ,). Obligatorio.
	 * @param numeroDecimales
	 *            Parámetro numero decimales
	 * @return el string
	 * @throws ValidacionTipoException
	 */
	String formateaDoubleACadena(double numero, String separadorMiles, String separadorDecimales, int numeroDecimales)
			throws ValidacionTipoException;

	/**
	 * Método para Obtener el año de una fecha (yyyy-MM-yyyy).
	 *
	 * @param fecha
	 *            Parámetro fecha
	 * @return el int
	 * @throws ValidacionTipoException
	 */
	int obtenerAnyo(String fecha) throws ValidacionTipoException;

	/**
	 * Método para Obtener el mes de una fecha (yyyy-MM-yyyy).
	 *
	 * @param fecha
	 *            Parámetro fecha
	 * @return el int
	 * @throws ValidacionTipoException
	 */
	int obtenerMes(String fecha) throws ValidacionTipoException;

	/**
	 * Método para Obtener el día de una fecha (yyyy-MM-yyyy).
	 *
	 * @param fecha
	 *            Parámetro fecha
	 * @return el int
	 * @throws ValidacionTipoException
	 */
	int obtenerDia(String fecha) throws ValidacionTipoException;

	/**
	 * Método para sumar días a una fecha (yyyy-MM-yyyy).
	 *
	 * @param fecha
	 *            Parámetro fecha
	 * @param dias
	 *            Parámetro dias
	 * @return el String
	 * @throws ValidacionTipoException
	 */
	String sumaDias(String fecha, int dias) throws ValidacionTipoException;

	/**
	 * Método para sacar la diferencia de días entre 2 fechas
	 *
	 * @param fecha1
	 *            Parámetro fecha1
	 * @param fecha2
	 *            Parámetro fecha2
	 * @return el int
	 * @throws ValidacionTipoException
	 */
	int distanciaDias(String fecha1, String fecha2) throws ValidacionTipoException;

	/**
	 * Método para sacar la diferencia de días entre 2 fechas (sin contar fines de
	 * semana)
	 *
	 * @param fecha1
	 *            Parámetro fecha1
	 * @param fecha2
	 *            Parámetro fecha2
	 * @return el int
	 * @throws ValidacionTipoException
	 */
	int distanciaDiasHabiles(String fecha1, String fecha2) throws ValidacionTipoException;

	/**
	 * Compara si el parámetro valor está dentro de un rango marcado. Si el valor
	 * introducido es un nulo, devuelve false. Si el rango es un nulo, comparará que
	 * sea mayor que el mínimo Si el rango mínimo es un nulo, comparará que sea
	 * menor que el máximo Si ambos límites son nulos, devolverá false Si los rangos
	 * no son correctos, devolvera false
	 *
	 * @param valor
	 *            Valor a validar dentro del rango
	 * @param minimo
	 *            Límite inferior del rango
	 * @param maximo
	 *            Límite superior del rango
	 * @return true, si es satisfactorio true Si está dentro del rango marcado false
	 *         Si no está dentro del rango marcado
	 */
	boolean validaRangoF(float valor, float minimo, float maximo);

}
