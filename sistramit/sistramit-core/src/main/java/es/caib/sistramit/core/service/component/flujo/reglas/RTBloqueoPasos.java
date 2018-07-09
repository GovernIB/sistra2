package es.caib.sistramit.core.service.component.flujo.reglas;

import java.util.List;

import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeSubEstadoPasoPagar;

/**
 * Regla que establece como solo lectura los pasos de captura, rellenar y anexar
 * si hay pagos iniciados o completados.
 *
 * @author Indra
 *
 */
@Component("reglaBloqueoPasos")
public final class RTBloqueoPasos implements ReglaTramitacion {

    @Override
    public boolean execute(final ContextoReglaTramitacion pCtx,
            final Object[] vars) {

        // Recuperamos pasos de tipo pagar
        final List<DatosPaso> ldpp = pCtx.getPasos(TypePaso.PAGAR);

        // Controlamos de que solo puede existir uno
        if (ldpp.size() > ConstantesNumero.N1) {
            throw new ErrorConfiguracionException(
                    "No pueden existir más de un paso pagar");
        }

        // Comprobamos si debemos bloquear
        boolean bloquear = false;
        if (ldpp.size() == ConstantesNumero.N1) {
            if (ldpp.get(0)
                    .getSubestado() == TypeSubEstadoPasoPagar.PAGO_INICIADO
                    || ldpp.get(0)
                            .getSubestado() == TypeSubEstadoPasoPagar.PAGO_COMPLETADO) {
                bloquear = true;
            }
        }

        // Bloqueamos pasos de tipo rellenar, capturar y anexar hasta paso pagar
        for (final DatosPaso dp : pCtx.getListaPasos()) {
            if (dp.getTipo() == TypePaso.PAGAR) {
                break;
            } else if (dp.getTipo() == TypePaso.RELLENAR
                    || dp.getTipo() == TypePaso.CAPTURAR
                    || dp.getTipo() == TypePaso.ANEXAR) {
                if (dp.getEstado() != TypeEstadoPaso.NO_INICIALIZADO
                        && dp.getEstado() != TypeEstadoPaso.REVISAR) {
                    pCtx.setSoloLecturaPaso(dp.getIdPaso(), bloquear);
                }
            }
        }

        return true;
    }
}
