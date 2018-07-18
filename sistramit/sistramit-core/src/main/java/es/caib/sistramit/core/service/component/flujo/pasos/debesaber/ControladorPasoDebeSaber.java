package es.caib.sistramit.core.service.component.flujo.pasos.debesaber;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RPasoTramitacion;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionDebeSaber;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.ConfiguracionModificadaException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DescripcionPaso;
import es.caib.sistramit.core.api.model.flujo.DetallePasoDebeSaber;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.component.flujo.pasos.ControladorPasoReferenciaImpl;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoDebeSaber;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.EstadoSubestadoPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeFaseActualizacionDatosInternos;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Controlador para paso Accion.
 *
 * @author Indra
 *
 */
@Component("controladorPasoDebeSaber")
public final class ControladorPasoDebeSaber
        extends ControladorPasoReferenciaImpl {

    @Override
    protected void actualizarDatosInternos(DatosPaso pDatosPaso,
            DatosPersistenciaPaso pDpp, DefinicionTramiteSTG pDefinicionTramite,
            VariablesFlujo pVariablesFlujo,
            TypeFaseActualizacionDatosInternos pFaseEjecucion) {

        // Obtenemos datos internos del paso
        final DatosInternosPasoDebeSaber dipa = (DatosInternosPasoDebeSaber) pDatosPaso
                .internalData();

        // Regenera datos debe saber
        regenerarDatosDebeSaber(dipa, pDefinicionTramite);

    }

    @Override
    protected EstadoSubestadoPaso evaluarEstadoPaso(DatosPaso pDatosPaso) {
        return new EstadoSubestadoPaso(TypeEstadoPaso.COMPLETADO, null);
    }

    @Override
    protected List<DatosDocumento> obtenerDocumentosCompletadosPaso(
            DatosPaso pDatosPaso, DatosPersistenciaPaso pDpp,
            DefinicionTramiteSTG pDefinicionTramite) {
        // Este paso no tiene documentos
        return new ArrayList<>();
    }

    @Override
    protected RespuestaEjecutarAccionPaso ejecutarAccionPaso(
            DatosPaso pDatosPaso, DatosPersistenciaPaso pDpp,
            TypeAccionPaso pAccionPaso, ParametrosAccionPaso pParametros,
            DefinicionTramiteSTG pDefinicionTramite,
            VariablesFlujo pVariablesFlujo) {
        // Este paso no tiene acciones
        throw new AccionPasoNoPermitidaException(
                "Paso Debe Saber no tiene acciones");
    }

    // ---------------------------------------------------------------------------
    // Metodos privados
    // ---------------------------------------------------------------------------

    /**
     * Regenera datos debe saber.
     *
     * @param dipa
     *            datos internos paso
     * @param definicionTramite
     *            Parámetro definicion tramite
     */
    private void regenerarDatosDebeSaber(DatosInternosPasoDebeSaber dipa,
            DefinicionTramiteSTG definicionTramite) {
        final DetallePasoDebeSaber dpds = calcularDetallePaso(dipa,
                definicionTramite);
        dipa.setDetallePaso(dpds);
    }

    /**
     * Crea detalle del paso Debe Saber visualizable en el front a partir de la
     * definición del trámite.
     *
     * @param dipa
     *            datos internos paso
     * @param definicionTramite
     *            Parámetro definicion tramite
     * @return Detalle del paso
     */
    private DetallePasoDebeSaber calcularDetallePaso(
            DatosInternosPasoDebeSaber dipa,
            DefinicionTramiteSTG definicionTramite) {

        final RPasoTramitacionDebeSaber defPaso = (RPasoTramitacionDebeSaber) UtilsSTG
                .devuelveDefinicionPaso(dipa.getIdPaso(), definicionTramite);

        if (defPaso == null) {
            throw new ConfiguracionModificadaException(
                    "No existe paso con id " + dipa.getIdPaso());
        }

        final List<DescripcionPaso> descripcionesPaso = new ArrayList<>();
        for (final RPasoTramitacion paso : definicionTramite
                .getDefinicionVersion().getPasos()) {
            final TypePaso tipoPaso = TypePaso.fromString(paso.getTipo());
            if (tipoPaso == null) {
                throw new TipoNoControladoException(
                        "Tipo paso no controlado: " + paso.getTipo());
            }
            final DescripcionPaso d = new DescripcionPaso();
            d.setId(paso.getIdentificador());
            d.setTipo(tipoPaso);
            descripcionesPaso.add(d);
        }

        final RConfiguracionEntidad entidadInfo = getConfig()
                .obtenerConfiguracionEntidad(definicionTramite
                        .getDefinicionVersion().getIdEntidad());

        final DetallePasoDebeSaber dpds = new DetallePasoDebeSaber();
        dpds.setId(dipa.getIdPaso());
        dpds.setCompletado(TypeSiNo.SI);
        dpds.setInstrucciones(defPaso.getInstruccionesInicio());
        dpds.setInstrucciones(
                UtilsSTG.obtenerLiteral(entidadInfo.getInfoLopdHTML(),
                        definicionTramite.getDefinicionVersion().getIdioma()));
        dpds.setPasos(descripcionesPaso);
        return dpds;
    }

}
