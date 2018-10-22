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
     * @param codigo
     *            codigo
     * @param modelo
     *            modelo
     * @param concepto
     *            concepto
     * @param importe
     *            importe
     * @throws ScriptException
     */
    void setDetallePago(String codigo, String modelo, String concepto,
            String importe) throws ScriptException;

    /**
     * Método de acceso a datosPago.
     * 
     * @return datosPago
     */
    DatosCalculoPago getDatosPago();

}
