package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistra2.commons.plugins.pago.api.PagoPluginException;
import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;
import es.caib.sistramit.core.service.model.integracion.PagoComponentRedireccion;
import es.caib.sistramit.core.service.model.integracion.PagoComponentVerificacion;

/**
 * Acceso a componente pagos.
 *
 * @author Indra
 *
 */
public interface PagoComponent {

    /**
     * Obtiene importe tasa.
     *
     * @param idEntidad
     *            id entidad
     * @param idPasarela
     *            id pasarela
     * @param idTasa
     *            id tasa
     * @param debugEnabled
     *            indica si se habilita debug
     * @return importe (en cents)
     *
     * @throws PagoPluginException
     */
    int consultaTasa(final String idEntidad, final String idPasarela,
            String idTasa, boolean debugEnabled);

    /**
     * Inicio pago electrónico.
     *
     * @param sesionPago
     *            datos sesion pago
     * @param urlCallback
     *            url callback
     * @param debugEnabled
     *            indica si se habilita debug
     * @return redirección pago
     */
    PagoComponentRedireccion iniciarPagoElectronico(DatosSesionPago sesionPago,
            String urlCallback, boolean debugEnabled);

    /**
     * Verificación pago electrónico.
     *
     * @param sesionPago
     *            sesión pago
     * @param debugEnabled
     *            indica si se habilita debug
     * @return Estado pago
     */
    PagoComponentVerificacion verificarPagoElectronico(
            DatosSesionPago sesionPago, boolean debugEnabled);

    /**
     * Obtiene carta de pago presencial (PDF).
     *
     * @param datosPago
     *            Datos pago
     * @param debugEnabled
     *            indica si se habilita debug
     * @return carta de pago presencial
     */
    byte[] obtenerCartaPagoPresencial(DatosSesionPago sesionPago,
            boolean debugEnabled);

}
