package es.caib.sistramit.core.service.component.flujo.pasos.pagar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;
import es.caib.sistramit.core.api.model.flujo.DetalleEstadoPagoIncorrecto;
import es.caib.sistramit.core.api.model.flujo.DetallePasoPagar;
import es.caib.sistramit.core.api.model.flujo.Pago;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.RetornoPago;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoPagoIncorrecto;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.PagoComponent;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoPagar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.integracion.PagoComponentRedireccion;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.repository.dao.PagoExternoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 * Acción que permite iniciar pago electrónico en el paso Pagar.
 *
 * @author Indra
 *
 */
@Component("accionPtIniciarPago")
public final class AccionIniciarPago implements AccionPaso {

    /** Dao paso. */
    @Autowired
    private FlujoPasoDao dao;
    /** Dao ticket pago. */
    @Autowired
    private PagoExternoDao pagoExternoDao;
    /** Literales negocio. */
    @Autowired
    private Literales literales;
    /** Atributo pago component. */
    @Autowired
    private PagoComponent pagoExternoComponent;
    /** Configuracion. */
    @Autowired
    private ConfiguracionComponent configuracionComponent;

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
        TypeSiNo reiniciar = (TypeSiNo) UtilsFlujo
                .recuperaParametroAccionPaso(pParametros, "reiniciar", false);
        if (reiniciar == null) {
            reiniciar = TypeSiNo.NO;
        }

        // Obtenemos datos internos del paso
        final DatosInternosPasoPagar pDipa = (DatosInternosPasoPagar) pDatosPaso
                .internalData();

        // Obtenemos info de detalle para el pago
        final Pago pago = ((DetallePasoPagar) pDipa.getDetallePaso())
                .getPago(idPago);

        // Validaciones previas a iniciar pago
        validacionesPago(pDipa, pago, reiniciar);

        // Generamos url para iniciar el pago
        final String urlInicioPago = abrirPagoPasarela(pDipa, pDpp, pago,
                pDefinicionTramite, pVariablesFlujo);

        // Devolvemos respuesta
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        rp.addParametroRetorno("url", urlInicioPago);
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
     * @param pReiniciar
     *            Indica si reinicia el pago
     */
    private void validacionesPago(final DatosInternosPasoPagar pDipa,
            final Pago pPago, final TypeSiNo pReiniciar) {

        // - El pago debe estar en estado pendiente de realizar (si no se
        // reinicia)
        if (pPago.getRellenado() != TypeEstadoDocumento.SIN_RELLENAR
                && pReiniciar == TypeSiNo.NO) {
            throw new AccionPasoNoPermitidaException(
                    "El pago ya ha sido iniciado");
        }

        // - Verificamos si se permite presentacion electronica
        if (!pPago.getPresentacionesPermitidas()
                .contains(TypePresentacion.ELECTRONICA)) {
            throw new ErrorConfiguracionException(
                    "El pago se realizado mediante una forma de presentación que no está permitida");
        }

    }

    /**
     * Inicia pago en la pasarela.
     *
     *
     * @param pDipa
     *            Datos internos paso
     * @param pDpp
     *            Datos persistencia paso
     * @param pDetallePago
     *            Detalle del pago
     * @param pDefinicionTramite
     *            Definición trámite
     * @param pVariablesFlujo
     *            debug
     * @return Url inicio pago
     */
    private String abrirPagoPasarela(final DatosInternosPasoPagar pDipa,
            final DatosPersistenciaPaso pDpp, final Pago pDetallePago,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo) {

        // Crea datos sesion pago
        final DatosSesionPago sesionPago = ControladorPasoPagarHelper
                .getInstance()
                .crearSesionPagoPasarela(TypePresentacion.ELECTRONICA,
                        pVariablesFlujo.getIdSesionTramitacion(), pDipa,
                        pDetallePago.getId(), pVariablesFlujo.getIdioma(),
                        pDefinicionTramite);

        // Generamos url callback
        // - Creamos ticket
        final RetornoPago retornoPago = new RetornoPago();
        retornoPago.setIdSesionTramitacion(
                pVariablesFlujo.getIdSesionTramitacion());
        retornoPago.setIdPaso(pDipa.getIdPaso());
        retornoPago.setIdPago(pDetallePago.getId());
        retornoPago.setUsuario(pVariablesFlujo.getUsuarioAutenticado());
        final String ticket = pagoExternoDao.generarTicketPago(retornoPago);
        // - Construimos url
        final String urlCallback = configuracionComponent
                .obtenerPropiedadConfiguracion(
                        TypePropiedadConfiguracion.SISTRAMIT_URL)
                + ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_PAGO_EXTERNO
                + "?" + ConstantesSeguridad.TICKET_USER_PAGO + "=" + ticket;

        // Inicia sesion de pago en pasarela externa
        final PagoComponentRedireccion urlInicioPago = pagoExternoComponent
                .iniciarPagoElectronico(sesionPago, urlCallback,
                        pVariablesFlujo.isDebugEnabled());
        sesionPago.setIdentificadorPago(urlInicioPago.getIdentificador());

        // Marcamos pago como incorrecto para indicar que se va a intentar pagar
        // - Actualizamos detalle
        actualizarDetallePago(pDipa, pDetallePago.getId(), pVariablesFlujo);
        // - Actualizamos persistencia
        actualizarPersistencia(pDipa, pDpp, pDetallePago.getId(), sesionPago,
                pVariablesFlujo.getIdSesionTramitacion());

        // Cacheamos datos de sesion de pago en datos internos paso
        pDipa.addSesionPago(pDetallePago.getId(), sesionPago);

        // Devolvemos respuesta
        return urlInicioPago.getUrl();
    }

    /**
     * Actualiza detalle de pago.
     *
     * @param pDipa
     *            Datos internos paso
     * @param idPago
     *            idPago
     * @param pVariablesFlujo
     *            Variables flujo
     */
    private void actualizarDetallePago(final DatosInternosPasoPagar pDipa,
            final String idPago, final VariablesFlujo pVariablesFlujo) {
        // - Marco este documento como rellenado incorrectamente para indicar
        // que voy a intentar pagar
        final String msgError = ControladorPasoPagarHelper.getInstance()
                .generarMensajeEstadoIncorrecto(literales,
                        pVariablesFlujo.getIdioma(),
                        TypeEstadoPagoIncorrecto.PAGO_INICIADO, null, null);
        final DetallePasoPagar detallePasoPagar = (DetallePasoPagar) pDipa
                .getDetallePaso();
        final Pago detallePago = detallePasoPagar.getPago(idPago);
        detallePago.setRellenado(TypeEstadoDocumento.RELLENADO_INCORRECTAMENTE);
        detallePago.setPresentacion(TypePresentacion.ELECTRONICA);
        detallePago.setEstadoIncorrecto(new DetalleEstadoPagoIncorrecto(
                TypeEstadoPagoIncorrecto.PAGO_INICIADO, msgError, null, null));
    }

    /**
     * Actualiza persistencia.
     *
     * @param pDipa
     *            Datos internos paso
     * @param pDpp
     *            Datos persistencia
     * @param pSesionPago
     *            Sesion pago
     * @param pIdSesionTramitacion
     *            Id sesion tramitacion
     */
    private void actualizarPersistencia(final DatosInternosPasoPagar pDipa,
            final DatosPersistenciaPaso pDpp, final String idPago,
            final DatosSesionPago pSesionPago,
            final String pIdSesionTramitacion) {
        final DocumentoPasoPersistencia documentoPersistencia = pDpp
                .getDocumentoPasoPersistencia(idPago, ConstantesNumero.N1);
        // - Marcamos este documento como rellenado incorrectamente para indicar
        // que voy a intentar pagar
        documentoPersistencia
                .setEstado(TypeEstadoDocumento.RELLENADO_INCORRECTAMENTE);
        documentoPersistencia.setPagoEstadoIncorrecto(
                TypeEstadoPagoIncorrecto.PAGO_INICIADO);
        documentoPersistencia.setPagoErrorPasarela(null);
        documentoPersistencia.setPagoMensajeErrorPasarela(null);
        documentoPersistencia
                .setPagoNifSujetoPasivo(pSesionPago.getSujetoPasivo().getNif());
        documentoPersistencia
                .setPagoIdentificador(pSesionPago.getIdentificadorPago());

        // - Marcamos para borrar los ficheros anteriores
        final List<ReferenciaFichero> ficsPersistenciaBorrar = new ArrayList<>();
        ficsPersistenciaBorrar
                .addAll(documentoPersistencia.obtenerReferenciasFicherosPago());
        // - Insertamos nuevos ficheros
        final byte[] xmlPago = ControladorPasoPagarHelper.getInstance()
                .toXML(pSesionPago);
        final ReferenciaFichero referenciaDocumentoXML = dao
                .insertarFicheroPersistencia(idPago + ".xml", xmlPago,
                        pIdSesionTramitacion);
        // - Actualizamos en persistencia el documento
        documentoPersistencia.setFichero(referenciaDocumentoXML);
        dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(),
                pDipa.getIdPaso(), documentoPersistencia);

        // - Eliminamos ficheros anteriores
        for (final ReferenciaFichero refFic : ficsPersistenciaBorrar) {
            dao.eliminarFicheroPersistencia(refFic);
        }
    }

}
