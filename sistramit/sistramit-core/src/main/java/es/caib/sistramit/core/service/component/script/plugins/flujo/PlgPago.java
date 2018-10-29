package es.caib.sistramit.core.service.component.script.plugins.flujo;

import javax.script.ScriptException;

import es.caib.sistramit.core.service.component.integracion.PagoComponent;
import es.caib.sistramit.core.service.model.script.flujo.PlgPagoInt;

/**
 * Plugin de acceso a funciones pago.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlgPago implements PlgPagoInt {

    /** Componente pago. */
    private final PagoComponent pagoComponent;

    /** Id entidad. */
    private final String idEntidad;

    /**
     * Constructor.
     *
     * @param idEntidad.
     */
    public PlgPago(PagoComponent pc, String ie) {
        super();
        pagoComponent = pc;
        idEntidad = ie;
    }

    @Override
    public String getPluginId() {
        return ID;
    }

    @Override
    public int consultarTasa(String pasarelaId, String tasaId)
            throws ScriptException {

        final int importe = pagoComponent.consultaTasa(idEntidad, pasarelaId,
                tasaId, false);

        return importe;
    }

}
