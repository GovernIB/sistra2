package es.caib.sistramit.core.service.component.flujo.reglas;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeSubEstadoPasoPagar;

/**
 * Regla que establece la accesibilidad de los pasos para el flujo normalizado:
 * los pasos completados anteriores y los pasos siguientes completados o ultimo
 * pendiente.
 *
 * @author Indra
 *
 */
public final class RTNavegacionAccesibilidadNormalizado
        implements ReglaTramitacion {

    @Override
    public boolean execute(final ContextoReglaTramitacion pCtx,
            final Object[] vars) {

        // Obtiene paso actual
        final DatosPaso datosPasoActual = pCtx.getPaso(pCtx.getIdPasoActual());

        // No podemos movernos del paso actual si estamos:
        // - en el paso final
        // - el paso actual esta enviado a la bandeja
        // - el paso de pagar no tiene un pago iniciado
        // - el paso de registro no esta pendiente de reintentar
        boolean evaluar = true;
        if (datosPasoActual.isPasoFinal()) {
            evaluar = false;
        }
        if (datosPasoActual.getTipo() == TypePaso.PAGAR && datosPasoActual
                .getSubestado() == TypeSubEstadoPasoPagar.PAGO_INICIADO) {
            evaluar = false;
        }

        // Recorremos pasos estableciendo accesibilidad pasos
        boolean posteriorActual = false;
        boolean completadoAnterior = false;
        boolean esAccesible = false;
        for (final DatosPaso dp : pCtx.getListaPasos()) {

            // Inicializamos como no accesible
            esAccesible = false;

            // Calculamos accesibilidad del paso
            if (evaluar) {
                // Si es posterior al actual, depende de si el paso anterior
                // esta completado
                if (posteriorActual) {
                    if (completadoAnterior) {
                        esAccesible = true;
                    }
                } else {
                    // Si es previo al actual o es el actual, es accesible
                    esAccesible = true;
                    // Marcamos que hemos llegado al actual
                    if (datosPasoActual.getIdPaso().equals(dp.getIdPaso())) {
                        posteriorActual = true;
                    }
                }

                // Guardamos si anterior paso esta completado para siguiente
                // iteracion
                completadoAnterior = (dp
                        .getEstado() == TypeEstadoPaso.COMPLETADO);
            }

            // Establecemos accesibilidad del paso
            pCtx.setAccesibilidadPaso(dp.getIdPaso(), esAccesible);
        }

        return true;
    }
}
