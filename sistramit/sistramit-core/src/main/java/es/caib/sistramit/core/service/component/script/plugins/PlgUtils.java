package es.caib.sistramit.core.service.component.script.plugins;

import es.caib.sistra2.commons.utils.ValidacionTipoException;
import es.caib.sistra2.commons.utils.ValidacionesCadena;
import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistramit.core.service.model.script.PlgUtilsInt;

/**
 * Plugin que permite realizar diversas validaciones.
 *
 * @author Indra
 */
@SuppressWarnings("serial")
public final class PlgUtils implements PlgUtilsInt {

	/**
	 * Indica si esta habilitado debug.
	 */
	private final boolean debugEnabled;

	/**
	 * Constructor.
	 *
	 * @param pDebugEnabled
	 *            Debug enabled
	 */
	public PlgUtils(final boolean pDebugEnabled) {
		super();
		debugEnabled = pDebugEnabled;
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public boolean esCP(final String cp, final String provincia) {
		return ValidacionesTipo.getInstance().esCP(cp, provincia);
	}

	@Override
	public boolean esNif(final String valor) {
		return ValidacionesTipo.getInstance().esNif(valor);
	}

	@Override
	public boolean esCif(final String valor) {
		return ValidacionesTipo.getInstance().esCif(valor);
	}

	@Override
	public boolean esNie(final String nie) {
		return ValidacionesTipo.getInstance().esNie(nie);
	}

	@Override
	public boolean esNumeroSeguridadSocial(final String nss) {
		return ValidacionesTipo.getInstance().esNumeroSeguridadSocial(nss);
	}

	@Override
	public boolean esNumeroCuentaValido(final String numeroCuenta) {
		return ValidacionesTipo.getInstance().esNumeroCuentaValido(numeroCuenta);
	}

	@Override
	public boolean esNumeroCuentaIbanValido(final String numeroCuenta) {
		return ValidacionesTipo.getInstance().esNumeroCuentaIbanValido(numeroCuenta);
	}

	@Override
	public boolean esNumeroCuentaValido(final String entidad, final String sucursal, final String dc,
			final String cuenta) {
		return ValidacionesTipo.getInstance().esNumeroCuentaValido(entidad, sucursal, dc, cuenta);
	}

	@Override
	public boolean esNumeroCuentaIbanValido(final String iban, final String entidad, final String sucursal,
			final String dc, final String cuenta) {
		return ValidacionesTipo.getInstance().esNumeroCuentaIbanValido(iban, entidad, sucursal, dc, cuenta);
	}

	@Override
	public boolean esFecha(final String fecha) {
		return ValidacionesTipo.getInstance().esFecha(fecha, ValidacionesTipo.FORMATO_FECHA_INTERNACIONAL);
	}

	@Override
	public boolean esFecha(final String fecha, final String formato) {
		return ValidacionesTipo.getInstance().esFecha(fecha, formato);
	}

	@Override
	public boolean esImporte(final String importe) {
		return ValidacionesTipo.getInstance().esImporte(importe);
	}

	@Override
	public boolean esEntero(final String numero) {
		return ValidacionesTipo.getInstance().esEntero(numero);
	}

	@Override
	public boolean esNatural(final String numero) {
		return ValidacionesTipo.getInstance().esNatural(numero);
	}

	@Override
	public boolean validaRango(final int valor, final int minimo, final int maximo) {
		return ValidacionesTipo.getInstance().validaRango(valor, minimo, maximo);
	}

	@Override
	public boolean esEmail(final String email) {
		return ValidacionesTipo.getInstance().esEmail(email);
	}

	@Override
	public boolean esURL(final String url) {
		return ValidacionesTipo.getInstance().esURL(url);
	}

	@Override
	public boolean esTelefono(final String telefono) {
		return ValidacionesTipo.getInstance().esTelefono(telefono);
	}

	@Override
	public boolean esTelefonoFijo(final String telefono) {
		return ValidacionesTipo.getInstance().esTelefonoFijo(telefono);
	}

	@Override
	public boolean esTelefonoMovil(final String telefono) {
		return ValidacionesTipo.getInstance().esTelefonoMovil(telefono);
	}

	@Override
	public int validaFechaFin(final String fechaUno, final String fechaDos) throws ValidacionTipoException {
		return ValidacionesTipo.getInstance().validaFechaFin(fechaUno, fechaDos);
	}

	@Override
	public int validaFechaActual(final String fecha) throws ValidacionTipoException {
		return ValidacionesTipo.getInstance().validaFechaActual(fecha);
	}

	@Override
	public int validaFechaHoraFin(final String fechaHoraUno, final String fechaHoraDos) throws ValidacionTipoException {
		return ValidacionesTipo.getInstance().validaFechaHoraFin(fechaHoraUno, fechaHoraDos,
				ValidacionesTipo.FORMATO_FECHA_INTERNACIONAL);
	}

	@Override
	public String formateaNombreApellidos(final String formato, final String nombre, final String apellido1,
			final String apellido2) {
		return ValidacionesTipo.getInstance().formateaNombreApellidos(formato, nombre, apellido1, apellido2);
	}

	@Override
	public String getFechaActual() {
		return ValidacionesTipo.getInstance().getFechaActual(ValidacionesTipo.FORMATO_FECHA_INTERNACIONAL);
	}

	@Override
	public double formateaCadenaADouble(final String numero, final String separadorMiles,
			final String separadorDecimales) throws ValidacionTipoException {
		return ValidacionesTipo.getInstance().formateaCadenaADouble(numero, separadorMiles, separadorDecimales);
	}

	@Override
	public String formateaDoubleACadena(final double numero, final String separadorMiles,
			final String separadorDecimales, final int numeroDecimales) throws ValidacionTipoException {
		return ValidacionesTipo.getInstance().formateaDoubleACadena(numero, separadorMiles, separadorDecimales,
				numeroDecimales);
	}

	@Override
	public int obtenerAnyo(final String fecha) throws ValidacionTipoException {
		return ValidacionesTipo.getInstance().obtenerAnyo(fecha, ValidacionesTipo.FORMATO_FECHA_INTERNACIONAL);
	}

	@Override
	public int obtenerMes(final String fecha) throws ValidacionTipoException {
		return ValidacionesTipo.getInstance().obtenerMes(fecha, ValidacionesTipo.FORMATO_FECHA_INTERNACIONAL);
	}

	@Override
	public int obtenerDia(final String fecha) throws ValidacionTipoException {
		return ValidacionesTipo.getInstance().obtenerDia(fecha, ValidacionesTipo.FORMATO_FECHA_INTERNACIONAL);
	}

	@Override
	public String sumaDias(final String fecha, final int dias) throws ValidacionTipoException {
		return ValidacionesTipo.getInstance().sumaDias(fecha, dias, ValidacionesTipo.FORMATO_FECHA_INTERNACIONAL);
	}

	@Override
	public int distanciaDias(final String fecha1, final String fecha2) throws ValidacionTipoException {
		return ValidacionesTipo.getInstance().distanciaDias(fecha1, fecha2,
				ValidacionesTipo.FORMATO_FECHA_INTERNACIONAL);
	}

	@Override
	public int distanciaDiasHabiles(final String fecha1, final String fecha2) throws ValidacionTipoException {
		return ValidacionesTipo.getInstance().distanciaDiasHabiles(fecha1, fecha2,
				ValidacionesTipo.FORMATO_FECHA_INTERNACIONAL);
	}

	@Override
	public boolean validaRangoF(final float valor, final float minimo, final float maximo) {
		return ValidacionesTipo.getInstance().validaRangoF(valor, minimo, maximo);
	}

	@Override
	public boolean esCadenaVacia(final String token) {
		return ValidacionesCadena.getInstance().esCadenaVacia(token);
	}

	@Override
	public boolean esIgual(final String cadena1, final String cadena2) {
		return ValidacionesCadena.getInstance().esIgual(cadena1, cadena2);
	}

	@Override
	public boolean esIgual(final String cadena1, final String cadena2, final boolean ignoreCase) {
		return ValidacionesCadena.getInstance().esIgual(cadena1, cadena2, ignoreCase);
	}

	@Override
	public String lpad(final String asTexto, final int aiLongMinima, final String acRelleno) {
		return ValidacionesCadena.getInstance().lpad(asTexto, aiLongMinima, acRelleno);
	}

	@Override
	public String rpad(final String asTexto, final int aiLongMinima, final String acRelleno) {
		return ValidacionesCadena.getInstance().rpad(asTexto, aiLongMinima, acRelleno);
	}

	@Override
	public String replace(final String texto, final String one, final String another) {
		return ValidacionesCadena.getInstance().replace(texto, one, another);
	}

	@Override
	public boolean validaExpresionRegular(final String texto, final String expresion) {
		return ValidacionesCadena.getInstance().validaExpresionRegular(texto, expresion);
	}

	@Override
	public boolean esNulo(final Object nulo) {
		return ValidacionesCadena.getInstance().esNulo(nulo);
	}

	@Override
	public String trim(final String cadena) {
		return ValidacionesCadena.getInstance().trim(cadena);
	}

	@Override
	public String ltrim(final String cadena) {
		return ValidacionesCadena.getInstance().ltrim(cadena);
	}

	@Override
	public String rtrim(final String cadena) {
		return ValidacionesCadena.getInstance().rtrim(cadena);
	}

	@Override
	public String extraerValorXml(final String xml, final String xpath) {
		return ValidacionesCadena.getInstance().extraerValorXml(xml, xpath);
	}

}
