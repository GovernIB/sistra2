package es.caib.sistramit.core.service.component.flujo.reglas;

import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoRegistrar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;

/**
 * Reglas de navegacion automatica tras realizar una accion sobre un paso.
 * Navegaciones automáticas: tras registrar pasa al siguiente paso (guardar).
 *
 *
 *
 * @author Indra
 *
 */
public final class RTAccionNavegacionAutoNormalizado implements ReglaTramitacion {

	@Override
	public boolean execute(final ContextoReglaTramitacion pCtx, final Object[] vars) {

		// Recuperamos paso actual
		final DatosPaso dp = pCtx.getPaso(pCtx.getIdPasoActual());

		// Obtenemos accion a ejecutar
		final TypeAccionPaso accion = (TypeAccionPaso) vars[0];

		// Si se ha registrado pasamos automaticamente a paso de guardar
		if (accion instanceof TypeAccionPasoRegistrar && dp.getEstado() == TypeEstadoPaso.COMPLETADO) {
			pCtx.setPasarSiguientePaso(true);
		}

		return true;
	}
}
