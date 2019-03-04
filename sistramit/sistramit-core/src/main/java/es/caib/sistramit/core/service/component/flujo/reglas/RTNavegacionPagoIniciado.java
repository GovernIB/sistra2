package es.caib.sistramit.core.service.component.flujo.reglas;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeSubEstadoPasoPagar;

/**
 * Regla que establece que si el paso actual es el de pago no se puede cambiar
 * de paso si hay un pago a mitad.
 *
 * @author Indra
 *
 */
public final class RTNavegacionPagoIniciado implements ReglaTramitacion {

	@Override
	public boolean execute(final ContextoReglaTramitacion pCtx, final Object[] vars) {

		boolean res = true;

		// Comprobamos si todavia no se ha establecido el paso actual (inicio /
		// carga tramite)
		if (pCtx.getIdPasoActual() != null) {

			// Recuperamos paso actual
			final DatosPaso dp = pCtx.getPaso(pCtx.getIdPasoActual());

			// Si el paso actual es de pagos y hay un pago iniciado no se puede
			// cambiar de paso
			if (dp.getTipo() == TypePaso.PAGAR && dp.getSubestado() == TypeSubEstadoPasoPagar.PAGO_INICIADO) {
				res = false;
			}
		}

		return res;
	}
}
