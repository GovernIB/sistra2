package es.caib.sistramit.core.service.component.flujo.reglas;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.util.UtilsSTG;

import java.util.List;

/**
 * Regla que verifica si hay algo anómalo en la definición de pasos.
 * 
 * @author Indra
 * 
 */
public final class RTDefinicionPasosTramite implements ReglaTramitacion {

    @Override
    public boolean execute(final ContextoReglaTramitacion pCtx,
            final Object[] vars) {

        // Recuperamos pasos de tipo pagar
        final List<DatosPaso> ldpp = pCtx.getPasos(TypePaso.PAGAR);

        // Verificar que los pasos cargados en persistencia existen en la definición del trámite
        for (DatosPaso dp : ldpp) {
            // Verificamos si existe ese paso en la definición
            if (UtilsSTG.devuelveDefinicionPaso(dp.getIdPaso(), pCtx.getDefinicionTramite()) == null) {
                throw new ErrorConfiguracionException(
                        "No existeix el pas " + dp.getIdPaso() + " en la definició del tràmit");
            }
        }

        // Controlamos de que solo puede existir un paso pagar
        if (ldpp.size() > ConstantesNumero.N1) {
            throw new ErrorConfiguracionException(
                    "No hi pot haver més d'un pas pagar");
        }

        return true;
    }
}
