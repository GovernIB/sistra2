package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Pagar.
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoPagar implements TypeAccionPaso {

    /**
     * Iniciar pago. Parámetros entrada: idPago, reiniciar (opcional).
     * Parámetros salida: url inicio pago.
     */
    INICIAR_PAGO,
    /**
     * Cancelar pago iniciado. Parámetros entrada: idPago. Parámetros salida: no
     * tiene.
     */
    CANCELAR_PAGO_INICIADO,
    /**
     * Realiza la verificación del pago de la pasarela. Parámetros entrada:
     * idPago. Parámetros salida: verificacion (PagoVerificacion).
     */
    VERIFICAR_PAGO_PASARELA,
    /**
     * Descarga justificante de pago. Parámetros entrada: idPago. Parámetros
     * salida: datos (byte[])
     */
    DESCARGAR_JUSTIFICANTE(false);

    /**
     * Indica si la acción modifica datos del paso.
     */
    private boolean modificaPasoPagar = true;

    /**
     * Constructor.
     *
     * @param pmodificaPaso
     *            Indica si modifica el paso.
     */
    private TypeAccionPasoPagar(final boolean pmodificaPaso) {
        modificaPasoPagar = pmodificaPaso;
    }

    /**
     * Constructor.
     */
    private TypeAccionPasoPagar() {
    }

    @Override
    public boolean isModificaPaso() {
        return modificaPasoPagar;
    }
}
