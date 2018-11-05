package es.caib.sistramit.core.service.component.flujo.pasos.pagar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;
import es.caib.sistramit.core.api.model.flujo.DetalleEstadoPagoIncorrecto;
import es.caib.sistramit.core.api.model.flujo.DetallePasoPagar;
import es.caib.sistramit.core.api.model.flujo.Pago;
import es.caib.sistramit.core.api.model.flujo.PagoVerificacion;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoPagoIncorrecto;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.PagoComponent;
import es.caib.sistramit.core.service.component.literales.Literales;
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
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Acción que verifica si se ha realizado un paso en la pasarela.
 *
 * @author Indra
 *
 */
@Component("accionPtVerificarPagoPasarela")
public final class AccionVerificarPagoPasarela implements AccionPaso {

    /** Atributo pago component. */
    @Autowired
    private PagoComponent pagoExternoComponent;
    /** Dao paso. */
    @Autowired
    private FlujoPasoDao dao;
    /** Literales negocio. */
    @Autowired
    private Literales literales;

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
        final DatosSesionPago sesionPago = pDipa.recuperarSesionPago(idPago);

        // Validaciones previas a iniciar pago
        validacionesPago(pDipa, pago);

        // Verificar si el pago se ha realizado
        final PagoComponentVerificacion dvp = pagoExternoComponent
                .verificarPagoElectronico(sesionPago,
                        UtilsSTG.isDebugEnabled(pDefinicionTramite));

        // En funcion del resultado de la validacion actualizamos estado paso y
        // persistencia
        // - Actualizamos detalle
        actualizarDetallePago(pDipa, idPago, dvp, pVariablesFlujo);
        // - Actualizamos persistencia
        actualizarPersistencia(pDipa, pDpp, idPago, dvp,
                pVariablesFlujo.getIdSesionTramitacion());

        // Devolvemos respuesta
        final PagoVerificacion pv = new PagoVerificacion();
        pv.setVerificado(TypeSiNo.fromBoolean(dvp.isVerificado()));
        pv.setRealizado(TypeSiNo.fromBoolean(dvp.isPagado()));
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        rp.addParametroRetorno("verificacion", pv);
        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rep.setRespuestaAccionPaso(rp);
        return rep;

    }

    /**
     * Actualiza detalle de pago.
     *
     * @param pDipa
     *            Datos internos paso
     * @param idPago
     *            idPago
     * @param pDvp
     * @param pVariablesFlujo
     *            Variables flujo
     */
    private void actualizarDetallePago(final DatosInternosPasoPagar pDipa,
            final String idPago, final PagoComponentVerificacion pDvp,
            final VariablesFlujo pVariablesFlujo) {

        final Pago detallePago = ((DetallePasoPagar) pDipa.getDetallePaso())
                .getPago(idPago);

        // Si no se ha podido verificar, lo seguimos dejando pendiente y
        // ajustamos mensaje error
        if (!pDvp.isVerificado()) {
            final String mensajeError = ControladorPasoPagarHelper.getInstance()
                    .generarMensajeEstadoIncorrecto(literales,
                            pVariablesFlujo.getIdioma(),
                            TypeEstadoPagoIncorrecto.PAGO_INICIADO,
                            pDvp.getCodigoError(), pDvp.getMensajeError());
            detallePago.setEstadoIncorrecto(new DetalleEstadoPagoIncorrecto(
                    TypeEstadoPagoIncorrecto.PAGO_INICIADO, mensajeError,
                    pDvp.getCodigoError(), pDvp.getMensajeError()));
        } else {
            // Si ha podido verificarse, comprobamos si esta pagado o no
            if (pDvp.isPagado()) {
                detallePago.setRellenado(
                        TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
                detallePago.setEstadoIncorrecto(null);
            } else {
                // Si no se ha pagado, volvemos a estado inicial para iniciar
                // proceso de pago
                detallePago.setRellenado(TypeEstadoDocumento.SIN_RELLENAR);
                detallePago.setEstadoIncorrecto(null);
            }
        }
    }

    /**
     * Actualiza persistencia paso pagar.
     *
     * @param pDipa
     *            Datos internos paso pagar
     * @param pDpp
     *            Datos persistencia paso pagar
     * @param pIdPago
     *            Id pago
     * @param pResPasarela
     *            Resultado pasarela
     * @param pIdSesionTramitacion
     *            Id sesion tramitacion
     */
    private void actualizarPersistencia(final DatosInternosPasoPagar pDipa,
            final DatosPersistenciaPaso pDpp, final String pIdPago,
            final PagoComponentVerificacion pResPasarela,
            final String pIdSesionTramitacion) {
        // - Obtenemos id sesion pago, datos pago y fichero de autorizacion de
        // pago
        final DatosSesionPago datosSesionPago = pDipa
                .recuperarSesionPago(pIdPago);
        final DocumentoPasoPersistencia docPagoPersistencia = pDpp
                .getDocumentoPasoPersistencia(pIdPago, ConstantesNumero.N1);

        // Actualizamos sesion de pago
        // - Si no se ha verificado, actualizamos estado pago incorrecto
        // (pendiente verificacion)
        if (!pResPasarela.isVerificado()) {
            // Documento pago persistencia
            docPagoPersistencia
                    .setEstado(TypeEstadoDocumento.RELLENADO_INCORRECTAMENTE);
            docPagoPersistencia.setPagoEstadoIncorrecto(
                    TypeEstadoPagoIncorrecto.PAGO_INICIADO);
            docPagoPersistencia
                    .setPagoErrorPasarela(pResPasarela.getCodigoError());
            docPagoPersistencia.setPagoMensajeErrorPasarela(
                    pResPasarela.getMensajeError());
        } else {
            // Comprobamos si el pago se ha realizado
            if (pResPasarela.isPagado()) {
                // Actualizamos sesion pago con datos del pago realizado
                datosSesionPago.setFechaPago(pResPasarela.getFechaPago());
                datosSesionPago.setLocalizador(pResPasarela.getLocalizador());

                // Documento pago persistencia
                docPagoPersistencia
                        .setEstado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
                docPagoPersistencia.setPagoEstadoIncorrecto(null);
                docPagoPersistencia.setPagoErrorPasarela(null);
                docPagoPersistencia.setPagoMensajeErrorPasarela(null);
                docPagoPersistencia.setPagoIdentificador(
                        datosSesionPago.getIdentificadorPago());
                // Ficheros asociados
                // - Datos pago
                final byte[] xmlPago = ControladorPasoPagarHelper.getInstance()
                        .toXML(datosSesionPago);
                dao.actualizarFicheroPersistencia(
                        docPagoPersistencia.getFichero(), pIdPago + ".xml",
                        xmlPago);
                // - Justificante de pago
                final ReferenciaFichero refJustificante = dao
                        .insertarFicheroPersistencia(pIdPago + ".pdf",
                                pResPasarela.getJustificantePDF(),
                                pIdSesionTramitacion);
                docPagoPersistencia.setPagoJustificantePdf(refJustificante);
            } else {
                // No pagado, reseteamos para inicio de nuevo
                docPagoPersistencia.setEstado(TypeEstadoDocumento.SIN_RELLENAR);
                docPagoPersistencia.setPagoEstadoIncorrecto(null);
                docPagoPersistencia.setPagoErrorPasarela(null);
                docPagoPersistencia.setPagoMensajeErrorPasarela(null);
            }
        }

        // Actualizamos persistencia
        dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(),
                pDipa.getIdPaso(), docPagoPersistencia);
    }

    /**
     * Realiza validaciones previas al pago.
     *
     * @param pDipa
     *            Datos interno paso pago
     * @param pPago
     *            Datos pago
     * @param pXmlRespuesta
     *            Xml respuesta recibida
     * @param pSesionPago
     */
    private void validacionesPago(final DatosInternosPasoPagar pDipa,
            final Pago pPago) {
        // - El pago debe estar en estado iniciado
        if (pPago
                .getRellenado() != TypeEstadoDocumento.RELLENADO_INCORRECTAMENTE) {
            throw new AccionPasoNoPermitidaException(
                    "El pago no esta en estado iniciado");
        }
    }

}
