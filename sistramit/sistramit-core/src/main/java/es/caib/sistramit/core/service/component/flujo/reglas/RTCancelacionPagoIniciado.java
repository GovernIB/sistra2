package es.caib.sistramit.core.service.component.flujo.reglas;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeSubEstadoPasoPagar;

/**
 * Regla que establece que si el paso actual es el de pago y se ha iniciado o
 * completado un pago no se pueda cancelar el trámite.
 *
 * @author Indra
 *
 */
public final class RTCancelacionPagoIniciado implements ReglaTramitacion {
    @Override
    public boolean execute(final ContextoReglaTramitacion pCtx,
            final Object[] vars) {
        // Si hay un paso pagos y hay un pago iniciado o completado no se puede
        // cambiar de paso
        boolean res = true;
        for (final DatosPaso paso : pCtx.getListaPasos()) {
            if (paso.getTipo() == TypePaso.PAGAR && (paso
                    .getSubestado() == TypeSubEstadoPasoPagar.PAGO_INICIADO
                    || paso.getSubestado() == TypeSubEstadoPasoPagar.PAGO_COMPLETADO)) {
                res = false;
                break;
            }
        }
        return res;
    }
}
