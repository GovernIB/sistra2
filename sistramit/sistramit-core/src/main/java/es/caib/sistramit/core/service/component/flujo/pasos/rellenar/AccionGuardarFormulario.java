package es.caib.sistramit.core.service.component.flujo.pasos.rellenar;

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
 * Acción que permite guardar un formulario en el paso Rellenar. También permite
 * borrar un formulario opcional.
 *
 * @author Indra
 *
 */
@Component("accionRfGuardarFormulario")
public final class AccionGuardarFormulario implements AccionPaso {

    @Override
    public RespuestaEjecutarAccionPaso ejecutarAccionPaso(DatosPaso pDatosPaso,
            DatosPersistenciaPaso pDpp, TypeAccionPaso pAccionPaso,
            ParametrosAccionPaso pParametros,
            DefinicionTramiteSTG pDefinicionTramite,
            VariablesFlujo pVariablesFlujo) {
        // TODO Auto-generated method stub
        return null;
    }

}
