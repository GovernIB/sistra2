package es.caib.sistramit.core.service.component.flujo.reglas;

import es.caib.sistramit.core.service.model.flujo.DatosPaso;

/**
 * Regla que establece que si el paso actual es el final no se puede cambiar de
 * paso.
 *
 * @author Indra
 *
 */
public final class RTNavegacionPasoFinal implements ReglaTramitacion {
	@Override
	public boolean execute(final ContextoReglaTramitacion pCtx, final Object[] vars) {
		boolean resultado;
		// Comprobamos si todavia no se ha establecido el paso actual (inicio /
		// carga tramite)
		if (pCtx.getIdPasoActual() == null) {
			resultado = true;
		} else {
			// Recuperamos paso actual
			final DatosPaso dp = pCtx.getPaso(pCtx.getIdPasoActual());
			// Si el paso actual es de pagos y hay un pago iniciado no se puede
			// cambiar de paso
			if (dp.isPasoFinal()) {
				resultado = false;
			} else {
				resultado = true;
			}
		}
		return resultado;
	}
}
