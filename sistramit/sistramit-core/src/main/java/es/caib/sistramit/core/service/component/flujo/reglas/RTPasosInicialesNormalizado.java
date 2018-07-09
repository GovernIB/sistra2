package es.caib.sistramit.core.service.component.flujo.reglas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RPasoTramitacion;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionAnexar;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionPagar;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRellenar;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.component.flujo.ConstantesFlujo;

/**
 * Regla que crea los pasos iniciales para el flujo normalizado.
 *
 * @author Indra
 *
 */
@Component("reglaPasosInicialesNormalizado")
public final class RTPasosInicialesNormalizado implements ReglaTramitacion {
    @Override
    public boolean execute(final ContextoReglaTramitacion pCtx,
            final Object[] vars) {

        // Recorremos definición trámite para ver que pasos son necesarios
        final List<RPasoTramitacion> listaPasos = pCtx.getDefinicionTramite()
                .getDefinicionVersion().getPasos();
        final Map<TypePaso, String> idsPasos = calcularIdsPasos(listaPasos);
        final String idPasoDebeSaber = idsPasos.get(TypePaso.DEBESABER);
        final String idPasoRellenar = idsPasos.get(TypePaso.RELLENAR);
        final String idPasoAnexar = idsPasos.get(TypePaso.ANEXAR);
        final String idPasoPagar = idsPasos.get(TypePaso.PAGAR);
        final String idPasoRegistrar = idsPasos.get(TypePaso.REGISTRAR);

        // Establecemos lista de pasos
        // - DEBE SABER: Este es un paso virtual que siempre debe estar
        pCtx.addPaso(idPasoDebeSaber, TypePaso.DEBESABER);
        // - RELLENAR: Si existe algún formulario
        if (idPasoRellenar != null) {
            pCtx.addPaso(idPasoRellenar, TypePaso.RELLENAR);
        }
        // - ANEXAR: Si existe algún anexo
        if (idPasoAnexar != null) {
            pCtx.addPaso(idPasoAnexar, TypePaso.ANEXAR);
        }
        // - PAGAR: Si existe algún pago
        if (idPasoPagar != null) {
            pCtx.addPaso(idPasoPagar, TypePaso.PAGAR);
        }
        // - REGISTRAR: siempre debe estar
        pCtx.addPaso(idPasoRegistrar, TypePaso.REGISTRAR);
        // - PASO GUARDAR: Este es un paso virtual que siempre debe estar
        pCtx.addPaso(ConstantesFlujo.ID_PASO_GUARDAR, TypePaso.GUARDAR);

        return true;
    }

    /**
     * Calcula id pasos necesarios.
     *
     * @param listaPasos
     *            Parámetro lista pasos
     * @return map con id pasos necesarios
     */
    private Map<TypePaso, String> calcularIdsPasos(
            final List<RPasoTramitacion> listaPasos) {
        final Map<TypePaso, String> idsPasos = new HashMap<>();
        for (final RPasoTramitacion paso : listaPasos) {

            final TypePaso tipoPaso = TypePaso.fromString(paso.getTipo());

            // Verificamos si hay algun paso que no va a estar
            boolean saltar = false;
            switch (tipoPaso) {
            case RELLENAR:
                // Si no tiene formularios
                final RPasoTramitacionRellenar pr = (RPasoTramitacionRellenar) paso;
                saltar = (pr.getFormularios() == null
                        || pr.getFormularios().isEmpty());
                break;
            case ANEXAR:
                // Si no tiene anexos ni script dinamico
                final RPasoTramitacionAnexar pa = (RPasoTramitacionAnexar) paso;
                saltar = (pa.getAnexos() == null || pa.getAnexos().isEmpty())
                        && (pa.getScriptAnexosDinamicos() == null || StringUtils
                                .isEmpty(pa.getScriptAnexosDinamicos()
                                        .getScript()));
                break;
            case PAGAR:
                // Si no tiene pagos
                final RPasoTramitacionPagar pt = (RPasoTramitacionPagar) paso;
                saltar = (pt.getPagos() == null || pt.getPagos().isEmpty());
                break;
            default:
                saltar = false;
            }

            if (!saltar) {
                idsPasos.put(tipoPaso, paso.getIdentificador());
            }

        }
        return idsPasos;
    }
}
