package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Datos verificación pago.
 *
 * @author Indra
 *
 */
public final class PagoVerificacion {

    /** Indica si se ha podido verificar. */
    private TypeSiNo verificado;
    /** Indica si se ha realizado. */
    private TypeSiNo realizado;
    /** Estado incorrecto. */
    private DetalleEstadoPagoIncorrecto estadoIncorrecto;

    // TODO Puede ser necesario establecer datos pago para auditar

    /**
     * Método de acceso a verificado.
     *
     * @return verificado
     */
    public TypeSiNo getVerificado() {
        return verificado;
    }

    /**
     * Método para establecer verificado.
     *
     * @param verificado
     *            verificado a establecer
     */
    public void setVerificado(TypeSiNo verificado) {
        this.verificado = verificado;
    }

    /**
     * Método de acceso a realizado.
     *
     * @return realizado
     */
    public TypeSiNo getRealizado() {
        return realizado;
    }

    /**
     * Método para establecer realizado.
     *
     * @param realizado
     *            realizado a establecer
     */
    public void setRealizado(TypeSiNo realizado) {
        this.realizado = realizado;
    }

    /**
     * Método de acceso a estadoIncorrecto.
     *
     * @return estadoIncorrecto
     */
    public DetalleEstadoPagoIncorrecto getEstadoIncorrecto() {
        return estadoIncorrecto;
    }

    /**
     * Método para establecer estadoIncorrecto.
     *
     * @param estadoIncorrecto
     *            estadoIncorrecto a establecer
     */
    public void setEstadoIncorrecto(
            DetalleEstadoPagoIncorrecto estadoIncorrecto) {
        this.estadoIncorrecto = estadoIncorrecto;
    }

}
