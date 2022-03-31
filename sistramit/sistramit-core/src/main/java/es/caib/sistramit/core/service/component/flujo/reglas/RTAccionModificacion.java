package es.caib.sistramit.core.service.component.flujo.reglas;

import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;

/**
 * Regla que valida si se puede realizar una acción que modifica datos en caso
 * de que el paso este como solo lectura e invalida los pasos siguientes.
 *
 * @author Indra
 *
 */
public final class RTAccionModificacion implements ReglaTramitacion {

    @Override
    public boolean execute(final ContextoReglaTramitacion pCtx,
            final Object[] vars) {

        // Obtenemos accion a ejecutar
        if (vars == null || vars.length == 0
                || !(vars[0] instanceof TypeAccionPaso)) {
            throw new ErrorConfiguracionException(
                    "No se està establint la acció com variable de la regla");
        }
        final TypeAccionPaso accion = (TypeAccionPaso) vars[0];

        // Recuperamos paso actual
        final DatosPaso dp = pCtx.getPaso(pCtx.getIdPasoActual());

        // Si la accion modifica el paso
        if (accion.isModificaPaso()) {
            // Si el paso esta solo lectura, no permitimos accion
            if (dp.isSoloLectura()) {
                throw new AccionPasoNoPermitidaException(
                        "La passa " + dp.getIdPaso() + " està només lectura");
            }
            // Invalidamos pasos posteriores
            boolean post = false;
            for (final DatosPaso dpi : pCtx.getListaPasos()) {

                if (dpi.getIdPaso().equals(dp.getIdPaso())) {
                    post = true;
                    continue;
                }

                // Invalidamos paso si ya se ha accedido a el
                if (post) {
                    if (dpi.getEstado() == TypeEstadoPaso.PENDIENTE
                            || dpi.getEstado() == TypeEstadoPaso.COMPLETADO) {
                        pCtx.invalidarPaso(dpi.getIdPaso());
                    }
                }
            }
        }

        return true;
    }
}
