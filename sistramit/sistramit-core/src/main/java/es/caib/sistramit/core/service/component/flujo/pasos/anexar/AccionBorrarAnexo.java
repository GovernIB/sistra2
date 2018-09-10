package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

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
 * Acción que permite descargar una plantilla en el paso Anexar.
 *
 * @author Indra
 *
 */
@Component("accionAdBorrarAnexo")
public final class AccionBorrarAnexo implements AccionPaso {

    // TODO PENDIENTE

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
