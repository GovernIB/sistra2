package es.caib.sistramit.core.service.component.flujo.reglas;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeSubEstadoPasoRegistrar;
import org.springframework.stereotype.Component;

/**
 * Regla que establece que si el paso actual es el de registro y esta en estado
 * de reintentar registro no se pueda cancelar el trámite.
 * 
 * @author Indra
 * 
 */
public final class RTCancelacionRegistroPendienteReintento implements
        ReglaTramitacion {
    @Override
    public boolean execute(final ContextoReglaTramitacion pCtx,
            final Object[] vars) {
        boolean res = true;
        if (pCtx.getIdPasoActual() != null
                && pCtx.getPaso(pCtx.getIdPasoActual()).getTipo() == TypePaso.REGISTRAR
                && pCtx.getPaso(pCtx.getIdPasoActual()).getSubestado() == TypeSubEstadoPasoRegistrar.REINTENTAR_REGISTRO) {
            res = false;
        }
        return res;
    }
}
