package es.caib.sistramit.core.service.model.script;

import javax.script.ScriptException;

import es.caib.sistramit.core.service.model.flujo.DatosCalculoPago;

/**
 * Datos que se pueden establecer en un pago.
 *
 * @author Indra
 *
 */
public interface ResPagoInt extends PluginScriptRes {

    /**
     * Id plugin.
     */
    String ID = "DATOS_PAGO";

    /**
     * Establece id pasarela.
     *
     * @param pasarelaId
     *            pasarelaId
     */
    void setPasarela(String pasarelaId);

    /**
     * Establece id organismo (opcional, depende del plugin).
     *
     * @param organismoId
     *            organismoId
     */
    void setOrganismo(String organismoId);

    /**
     * Establece contribuyente.
     *
     * @param nif
     *            nif
     * @param nombre
     *            nombre
     * @throws ScriptException
     */
    void setContribuyente(String nif, String nombre) throws ScriptException;

    /**
     * Establece detalle pago.
     *
     * @param tasa
     *            codigo
     * @param modelo
     *            modelo
     * @param concepto
     *            concepto
     * @param importe
     *            importe en cents
     * @throws ScriptException
     */
    void setDetallePago(String modelo, String concepto, String tasa,
            int importe) throws ScriptException;

    /**
     * Método de acceso a datosPago.
     *
     * @return datosPago
     */
    DatosCalculoPago getDatosPago();

}
