package es.caib.sistramit.core.service.model.script.flujo;

import javax.script.ScriptException;

import es.caib.sistramit.core.service.model.script.PluginScriptPlg;

/**
 * Plugin que ofrece utilidades para pagos.
 *
 * @author Indra
 *
 */
public interface PlgPagoInt extends PluginScriptPlg {

    /**
     * Id plugin.
     */
    String ID = "PLUGIN_PAGO";

    /**
     * Consulta importe tasa.
     * 
     * @param pasarelaId
     *            id pasarela
     * @param tasaId
     *            id tasa
     * @return importe tasa
     */
    int consultarTasa(final String pasarelaId, final String tasaId)
            throws ScriptException;

}
