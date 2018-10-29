package es.caib.sistramit.core.service.component.script.plugins.flujo;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.NifCif;
import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.service.model.flujo.DatosCalculoPago;
import es.caib.sistramit.core.service.model.script.ResPagoInt;

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
    public void setContribuyente(final String nif, final String nombre)
            throws ScriptException {
        final String nifNormalizado = NifCif.normalizarNif(nif);
        validarDatosPersona(nifNormalizado, nombre);
        datosPago.setContribuyente(new Persona(nifNormalizado, nombre));
    }

    @Override
    public void setDetallePago(final String modelo, final String concepto,
            final String tasa, final int importe) throws ScriptException {

        validarDatosPago(tasa, modelo, concepto);

        datosPago.setModelo(modelo);
        datosPago.setConcepto(concepto);
        datosPago.setTasa(tasa);
        datosPago.setImporte(importe);
    }

    @Override
    public void setPasarela(String pasarelaId) {
        datosPago.setPasarelaId(pasarelaId);
    }

    @Override
    public void setOrganismo(String organismoId) {
        datosPago.setOrganismo(organismoId);
    }

    /**
     * Valida datos persona.
     *
     * @param nifNormalizado
     *            Nif normalizado
     * @param pNombre
     *            Nombre
     * @throws ScriptException
     *             Excepcion
     */
    private void validarDatosPersona(final String nifNormalizado,
            final String pNombre) throws ScriptException {
        if (!ValidacionesTipo.getInstance().esNif(nifNormalizado)
                && !ValidacionesTipo.getInstance().esCif(nifNormalizado)
                && !ValidacionesTipo.getInstance().esNie(nifNormalizado)) {
            throw new ScriptException(
                    "El dato proporcionado como nif persona no es nif/nie/cif: "
                            + nifNormalizado);
        }
        if (StringUtils.isEmpty(pNombre) || !XssFilter.filtroXss(pNombre)) {
            throw new ScriptException(
                    "El dato proporcionado como nombre persona esta vacio o contiene caraceteres no permitidos");
        }
    }

    /**
     * Valida datos pago.
     *
     * @param codigo
     *            Codigo
     * @param modelo
     *            Modelo
     * @param concepto
     *            Concepto
     * @param importe
     *            importe en cents
     * @throws ScriptException
     *             Exception
     */
    private void validarDatosPago(final String codigo, final String modelo,
            final String concepto) throws ScriptException {
        if (!XssFilter.filtroXss(codigo)) {
            throw new ScriptException(
                    "El codigo contiene caracteres no permitidos");
        }
        if (!XssFilter.filtroXss(modelo)) {
            throw new ScriptException(
                    "El modelo contiene caracteres no permitidos");
        }
        if (!XssFilter.filtroXss(concepto)) {
            throw new ScriptException(
                    "El concepto contiene caracteres no permitidos");
        }
    }

}
