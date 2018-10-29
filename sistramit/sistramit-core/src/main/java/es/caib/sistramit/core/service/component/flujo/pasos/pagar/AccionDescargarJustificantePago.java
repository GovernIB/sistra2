package es.caib.sistramit.core.service.component.flujo.pasos.pagar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.model.flujo.DetallePasoPagar;
import es.caib.sistramit.core.api.model.flujo.Pago;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoPagar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 * Acción que permite descargar el justificante de pago en el paso Pagar.
 *
 * @author Indra
 *
 */
@Component("accionPtDescargarJustificantePago")
public final class AccionDescargarJustificantePago implements AccionPaso {

    /** Atributo dao. */
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

        // Validaciones
        validarDescargarJustificante(pDipa, idPago);

        // Obtenemos justificante
        final byte[] justificante = obtenerJustificantePago(pDipa, pDpp,
                idPago);

        // Devolvemos la respuesta
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        rp.addParametroRetorno("datos", justificante);
        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rep.setRespuestaAccionPaso(rp);
        return rep;

    }

    /**
     * Obtiene justificante pago de persistencia.
     *
     * @param pDipa
     *            Datos internos paso
     * @param pDpp
     *            Datos persistencia pago
     * @param pIdPago
     *            Id pago
     * @return Justificante pago
     */
    private byte[] obtenerJustificantePago(final DatosInternosPasoPagar pDipa,
            final DatosPersistenciaPaso pDpp, final String pIdPago) {

        byte[] justificante = null;

        final DocumentoPasoPersistencia dpp = pDpp
                .getDocumentoPasoPersistencia(pIdPago, ConstantesNumero.N1);

        final ReferenciaFichero referenciaJustificantePago = dpp
                .getPagoJustificantePdf();
        final DatosFicheroPersistencia dfp = dao
                .recuperarFicheroPersistencia(referenciaJustificantePago);
        justificante = dfp.getContenido();

        return justificante;
    }

    /**
     * Valida si se puede descargar el justificante de pago.
     *
     * @param pDipa
     *            Datos internos pago
     * @param pIdPago
     *            Id pago
     */
    private void validarDescargarJustificante(
            final DatosInternosPasoPagar pDipa, final String pIdPago) {
        final Pago detallePago = ((DetallePasoPagar) pDipa.getDetallePaso())
                .getPago(pIdPago);
        // - Debe estar realizado
        if (detallePago
                .getRellenado() != TypeEstadoDocumento.RELLENADO_CORRECTAMENTE) {
            throw new AccionPasoNoPermitidaException(
                    "El pago no esta realizado");
        }
    }

}
