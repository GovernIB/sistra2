package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import java.util.List;

import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.ControladorPasoReferenciaImpl;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.EstadoSubestadoPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeFaseActualizacionDatosInternos;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

/**
 * Controlador para paso Anexar.
 *
 * @author Indra
 *
 */
@Component("controladorPasoAnexar")
public final class ControladorPasoAnexar extends ControladorPasoReferenciaImpl {

    // TODO PENDIENTE

    @Override
    protected void actualizarDatosInternos(DatosPaso pDatosPaso,
            DatosPersistenciaPaso pDpp, DefinicionTramiteSTG pDefinicionTramite,
            VariablesFlujo pVariablesFlujo,
            TypeFaseActualizacionDatosInternos pFaseEjecucion) {
        // TODO Auto-generated method stub

    }

    @Override
    protected EstadoSubestadoPaso evaluarEstadoPaso(DatosPaso pDatosPaso) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<DatosDocumento> obtenerDocumentosCompletadosPaso(
            DatosPaso pDatosPaso, DatosPersistenciaPaso pDpp,
            DefinicionTramiteSTG pDefinicionTramite) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected RespuestaEjecutarAccionPaso ejecutarAccionPaso(
            DatosPaso pDatosPaso, DatosPersistenciaPaso pDpp,
            TypeAccionPaso pAccionPaso, ParametrosAccionPaso pParametros,
            DefinicionTramiteSTG pDefinicionTramite,
            VariablesFlujo pVariablesFlujo) {
        // TODO Auto-generated method stub
        return null;
    }

}
