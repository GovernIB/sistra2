package es.caib.sistramit.core.service.component.flujo.pasos.pagar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;
import es.caib.sistramit.core.api.model.flujo.DetallePasoPagar;
import es.caib.sistramit.core.api.model.flujo.Pago;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.PagoComponent;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoPagar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.integracion.PagoComponentVerificacion;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 * Acción que permite cancelar pago iniciado en el paso Pagar.
 *
 * @author Indra
 *
 */
@Component("accionPtCancelarPagoIniciado")
public final class AccionCancelarPagoIniciado implements AccionPaso {

    /** Atributo pago component. */
    @Autowired
    private PagoComponent pagoExternoComponent;
    /** Dao paso. */
    @Autowired
    private FlujoPasoDao dao;

    @Override
    public RespuestaEjecutarAccionPaso ejecutarAccionPaso(
            final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
            final TypeAccionPaso pAccionPaso,
            final ParametrosAccionPaso pParametros,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo) {

        // Recogemos parametros
        final String idPago = (String) UtilsFlujo
                .recuperaParametroAccionPaso(pParametros, "idPago", true);

        // Obtenemos datos internos del paso
        final DatosInternosPasoPagar pDipa = (DatosInternosPasoPagar) pDatosPaso
                .internalData();

        // Obtenemos info de detalle para el pago
        final Pago pago = ((DetallePasoPagar) pDipa.getDetallePaso())
                .getPago(idPago);

        // Validaciones previas a iniciar pago
        validacionesPago(pDipa, pago, pVariablesFlujo.isDebugEnabled());

        // Actualiza detalle
        actualizarDetallePago(pDipa, idPago);

        // Actualiza persistencia
        actualizarPersistencia(pDipa, pDpp, idPago);

        // Devolvemos respuesta
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rep.setRespuestaAccionPaso(rp);
        return rep;

    }

    /**
     * Realiza validaciones previas al pago.
     *
     * @param pDipa
     *            Datos interno paso pago
     * @param pPago
     *            Datos pago
     * @param debugEnabled
     *            Debug enabled
     * @param pReiniciar
     */
    private void validacionesPago(final DatosInternosPasoPagar pDipa,
            final Pago pPago, boolean debugEnabled) {

        // - Verifica que el pago este en estado iniciado
        if (pPago
                .getRellenado() != TypeEstadoDocumento.RELLENADO_INCORRECTAMENTE) {
            throw new AccionPasoNoPermitidaException(
                    "El pago no esta en estado iniciado");
        }

        // - Verificamos si el pago se ha realizado contra la pasarela
        final DatosSesionPago sesionPago = pDipa
                .recuperarSesionPago(pPago.getId());
        final PagoComponentVerificacion dvp = pagoExternoComponent
                .verificarPagoElectronico(sesionPago, debugEnabled);
        if (dvp.isVerificado() && dvp.isPagado()) {
            throw new AccionPasoNoPermitidaException("El pago esta completado");
        }

    }

    /**
     * Actualiza detalle de pago.
     *
     * @param pDipa
     *            Datos internos paso
     * @param idPago
     *            idPago
     */
    private void actualizarDetallePago(final DatosInternosPasoPagar pDipa,
            final String idPago) {
        final Pago detallePago = ((DetallePasoPagar) pDipa.getDetallePaso())
                .getPago(idPago);
        detallePago.setRellenado(TypeEstadoDocumento.SIN_RELLENAR);
        detallePago.setEstadoIncorrecto(null);
    }

    /**
     * Actualiza persistencia.
     *
     * @param pDipa
     *            Datos internos paso
     * @param pDpp
     *            Datos persistencia
     * @param idPago
     *            Id pago
     */
    private void actualizarPersistencia(final DatosInternosPasoPagar pDipa,
            final DatosPersistenciaPaso pDpp, final String idPago) {

        final DocumentoPasoPersistencia documentoPersistencia = pDpp
                .getDocumentoPasoPersistencia(idPago, ConstantesNumero.N1);

        // - Marcamos para borrar los ficheros anteriores
        final List<ReferenciaFichero> ficsPersistenciaBorrar = new ArrayList<>();
        ficsPersistenciaBorrar
                .addAll(documentoPersistencia.obtenerReferenciasFicherosPago());

        // - Marcamos este documento como rellenado incorrectamente para indicar
        // que voy a intentar pagar
        documentoPersistencia.setEstado(TypeEstadoDocumento.SIN_RELLENAR);
        documentoPersistencia.setPagoEstadoIncorrecto(null);
        documentoPersistencia.setPagoErrorPasarela(null);
        documentoPersistencia.setPagoMensajeErrorPasarela(null);
        documentoPersistencia.setPagoNifSujetoPasivo(null);
        documentoPersistencia.setPagoIdentificador(null);
        documentoPersistencia.setFichero(null);

        dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(),
                pDipa.getIdPaso(), documentoPersistencia);

        // - Eliminamos ficheros anteriores
        for (final ReferenciaFichero refFic : ficsPersistenciaBorrar) {
            dao.eliminarFicheroPersistencia(refFic);
        }

    }

}
