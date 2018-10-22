package es.caib.sistramit.core.service.component.flujo.pasos.pagar;

import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

/**
 * Acción que verifica si se ha realizado un paso en la pasarela.
 *
 * @author Indra
 *
 */
@Component("accionPtVerificarPagoPasarela")
public final class AccionVerificarPagoPasarela implements AccionPaso {

    @Override
    public RespuestaEjecutarAccionPaso ejecutarAccionPaso(
            final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
            final TypeAccionPaso pAccionPaso,
            final ParametrosAccionPaso pParametros,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo) {
        throw new RuntimeException("Pendiente implementar");
    }
}
