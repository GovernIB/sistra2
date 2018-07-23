package es.caib.sistramit.core.service.component.flujo.pasos.rellenar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.exception.AccionPasoNoExisteException;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoRellenar;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.ControladorPasoReferenciaImpl;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRellenar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.EstadoSubestadoPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeFaseActualizacionDatosInternos;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

/**
 * Controlador para paso Rellenar.
 *
 * @author Indra
 *
 */
@Component("controladorPasoRellenar")
public final class ControladorPasoRellenar
        extends ControladorPasoReferenciaImpl {

    /** Accion abrir formulario. */
    @Autowired
    private AccionAbrirFormulario accionAbrirFormulario;
    /** Accion guardar formulario. */
    @Autowired
    private AccionGuardarFormulario accionGuardarFormulario;
    /** Accion descargar formulario. */
    @Autowired
    private AccionDescargarFormulario accionDescargarFormulario;
    /** Accion descargar xml formulario para debug. */
    @Autowired
    private AccionDescargarXmlFormulario accionDescargarXmlFormulario;

    // TODO PENDIENTE

    @Override
    protected void actualizarDatosInternos(DatosPaso pDatosPaso,
            DatosPersistenciaPaso pDpp, DefinicionTramiteSTG pDefinicionTramite,
            VariablesFlujo pVariablesFlujo,
            TypeFaseActualizacionDatosInternos pFaseEjecucion) {

        // Obtenemos datos internos paso rellenar
        final DatosInternosPasoRellenar dipa = (DatosInternosPasoRellenar) pDatosPaso
                .internalData();
        // Regeneramos datos a partir de persistencia
        regenerarDatos(dipa, pDpp, pDefinicionTramite, pVariablesFlujo);

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

        RespuestaEjecutarAccionPaso rp = null;

        AccionPaso accionPaso = null;
        switch ((TypeAccionPasoRellenar) pAccionPaso) {
        case ABRIR_FORMULARIO:
            accionPaso = accionAbrirFormulario;
            break;
        case GUARDAR_FORMULARIO:
            accionPaso = accionGuardarFormulario;
            break;
        case DESCARGAR_FORMULARIO:
            accionPaso = accionDescargarFormulario;
            break;
        case DESCARGAR_XML:
            accionPaso = accionDescargarXmlFormulario;
            break;
        default:
            throw new AccionPasoNoExisteException("No existe acción "
                    + pAccionPaso + " en el paso Debe Rellenar");
        }

        rp = accionPaso.ejecutarAccionPaso(pDatosPaso, pDpp, pAccionPaso,
                pParametros, pDefinicionTramite, pVariablesFlujo);
        return rp;
    }

    // ----------------------------------------------------------------

    /**
     * Regenera datos paso en funcion de los datos de persistencia.
     *
     * @param pDipa
     *            Datos internos paso
     * @param pDpp
     *            Datos persistencia
     * @param pDefinicionTramite
     *            Definición trámite
     * @param pVariablesFlujo
     *            Variables flujo
     *
     */
    private void regenerarDatos(DatosInternosPasoRellenar pDipa,
            DatosPersistenciaPaso pDpp, DefinicionTramiteSTG pDefinicionTramite,
            VariablesFlujo pVariablesFlujo) {

        // TODO Pendiente

    }

}
