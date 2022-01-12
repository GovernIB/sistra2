package es.caib.sistramit.core.service.component.flujo.reglas;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeSubEstadoPasoRegistrar;

/**
 * Regla que establece que si el paso actual es el de registro no se puede
 * cambiar de paso si esta en estado de reintentar registro.
 *
 * @author Indra
 *
 */
public final class RTNavegacionRegistroPendienteReintento implements ReglaTramitacion {
	@Override
	public boolean execute(final ContextoReglaTramitacion pCtx, final Object[] vars) {
		boolean res = true;
		// Si el paso actual es de registro y esta pendiente reintentar no se puede
		// cambiar de paso
		if (pCtx.getIdPasoActual() != null && pCtx.getPaso(pCtx.getIdPasoActual()).getTipo() == TypePaso.REGISTRAR
				&& pCtx.getPaso(pCtx.getIdPasoActual())
						.getSubestado() == TypeSubEstadoPasoRegistrar.REINTENTAR_REGISTRO) {
			res = false;
		}
		return res;
	}
}
