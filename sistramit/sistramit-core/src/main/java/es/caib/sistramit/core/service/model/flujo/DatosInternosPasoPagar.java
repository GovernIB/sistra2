package es.caib.sistramit.core.service.model.flujo;

import java.util.HashMap;
import java.util.Map;

import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 *
 * Datos internos paso Pagar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosInternosPasoPagar extends DatosInternosPasoReferencia {

    /**
     * Cacheo de los datos de cálculo de los pagos. Estos datos provendrán de
     * los scripts.
     */
    private final Map<String, DatosCalculoPago> datosPagos = new HashMap<>();

    /**
     * Cacheo de los datos de las sesiones de pago. Estos datos provendrán de
     * las acciones de pagar.
     */
    private final Map<String, DatosSesionPago> sesionesPagos = new HashMap<>();

    /**
     * Constructor.
     *
     * @param idSesionTramitacion
     *            Parámetro id sesion tramitacion
     * @param idPaso
     *            Parámetro id paso
     */
    public DatosInternosPasoPagar(final String idSesionTramitacion,
            final String idPaso) {
        this.setTipo(TypePaso.PAGAR);
        this.setIdSesionTramitacion(idSesionTramitacion);
        this.setIdPaso(idPaso);
    }

    /**
     * Establecece el cálculo de datos para un pago.
     *
     * @param idPago
     *            Id pago
     * @param datosPago
     *            Cálculo del pago
     */
    public void addDatosPago(final String idPago,
            final DatosCalculoPago datosPago) {
        datosPagos.put(idPago, datosPago);
    }

    /**
     * Recupera el cálculo de datos para un pago.
     *
     * @param idPago
     *            Id pago
     * @return Cálculo del pago
     */
    public DatosCalculoPago recuperarCalculoPago(final String idPago) {
        return datosPagos.get(idPago);
    }

    /**
     * Establece datos de la sesión de pago.
     *
     * @param idPago
     *            Id pago
     * @param sesionPago
     *            Datos sesión de pago
     */
    public void addSesionPago(final String idPago,
            final DatosSesionPago sesionPago) {
        sesionesPagos.put(idPago, sesionPago);
    }

    /**
     * Recupera datos de la sesión de pago.
     *
     * @param idPago
     *            Parámetro id pago
     * @return Sesión pago
     */
    public DatosSesionPago recuperarSesionPago(final String idPago) {
        return sesionesPagos.get(idPago);
    }

    /**
     * Método para eliminar un id de sesion de pagos.
     *
     * @param idPago
     *            Parámetro id pago
     */
    public void eliminarSesionPago(final String idPago) {
        if (sesionesPagos.containsKey(idPago)) {
            sesionesPagos.remove(idPago);
        }
    }
}
