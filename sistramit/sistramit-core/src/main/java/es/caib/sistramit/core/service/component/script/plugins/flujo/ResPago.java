package es.caib.sistramit.core.service.component.script.plugins.flujo;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.NifUtils;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.service.model.flujo.DatosCalculoPago;
import es.caib.sistramit.core.service.model.script.flujo.ResPagoInt;

/**
 * Datos que se pueden establecer en un pago.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResPago implements ResPagoInt {

	/**
	 * Datos pago pasarela.
	 */
	private final DatosCalculoPago datosPago;

	@Override
	public String getPluginId() {
		return ID;
	}

	/**
	 * Constructor.
	 */
	public ResPago() {
		super();
		datosPago = new DatosCalculoPago();
	}

	@Override
	public DatosCalculoPago getDatosPago() {
		return datosPago;
	}

	@Override
	public void setContribuyente(final String nif, final String nombre) throws ScriptException {
		final String nifNormalizado = NifUtils.normalizarNif(nif);
		if (!NifUtils.esNifPersonaFisica(nifNormalizado) && !NifUtils.esNifPersonaJuridica(nifNormalizado)) {
			throw new ScriptException("La dada proporcionada com nif persona no és un nif vàlid: " + nifNormalizado);
		}
		validarDatosPersona(nifNormalizado, nombre);
		datosPago.setContribuyente(new Persona(nifNormalizado, nombre));
	}

	@Override
	public void setDetallePago(final String modelo, final String concepto, final String tasa, final int importe)
			throws ScriptException {
			setDetallePago(modelo, concepto, tasa, 1, importe);
	}

	@Override
	public void setDetallePago(final String modelo, final String concepto, final String tasa, final int unidades, final int importeUnidad)
			throws ScriptException {

		validarDatosPago(tasa, modelo, concepto, importeUnidad, unidades);

		datosPago.setModelo(modelo);
		datosPago.setConcepto(concepto);
		datosPago.setTasa(tasa);
		datosPago.setImporte(importeUnidad * unidades);
		datosPago.setMultiplicador(unidades);
	}

	@Override
	public void setPasarela(final String pasarelaId) {
		datosPago.setPasarelaId(pasarelaId);
	}

	@Override
	public void setOrganismo(final String organismoId) {
		datosPago.setOrganismo(organismoId);
	}

	@Override
	public void setMetodosPago(final String metodos) {
		datosPago.setMetodosPago(metodos);
	}

	/**
	 * Valida datos persona.
	 *
	 * @param nifNormalizado
	 *                           Nif normalizado
	 * @param pNombre
	 *                           Nombre
	 * @throws ScriptException
	 *                             Excepcion
	 */
	private void validarDatosPersona(final String nifNormalizado, final String pNombre) throws ScriptException {
		if (!NifUtils.esNifPersonaFisica(nifNormalizado) && !NifUtils.esNifPersonaJuridica(nifNormalizado)) {
			throw new ScriptException("La dada proporcionada no és un nif vàlid: " + nifNormalizado);
		}
		if (StringUtils.isEmpty(pNombre) || !XssFilter.filtroXss(pNombre)) {
			throw new ScriptException("La dada proporcionada com nom persona està buit o conté caràceters no permesos");
		}
	}

	/**
	 * Valida datos pago.
	 *
	 * @param codigo   Codigo
	 * @param modelo   Modelo
	 * @param concepto Concepto
	 * @param importe  importe en cents
	 * @param unidades
	 * @throws ScriptException Exception
	 */
	private void validarDatosPago(final String codigo, final String modelo, final String concepto, int importe, int unidades)
			throws ScriptException {
		if (!XssFilter.filtroXss(codigo)) {
			throw new ScriptException("El codi conté caràcters no permesos");
		}
		if (!XssFilter.filtroXss(modelo)) {
			throw new ScriptException("El model conté caràcters no permesos");
		}
		if (!XssFilter.filtroXss(concepto)) {
			throw new ScriptException("El concepte conté caràcters no permesos");
		}
		if (importe < 0) {
			throw new ScriptException("Import no válid, ha de ser major o igual a 0");
		}
		if (unidades < 1 || unidades > 999) {
			throw new ScriptException("Unitats no vàlides, ha de ser major que 0 i menor que 1000");
		}
	}

}
