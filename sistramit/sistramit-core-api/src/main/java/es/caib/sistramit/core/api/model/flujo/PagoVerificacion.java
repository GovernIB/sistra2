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
    /** Indica metodo de pago. */
    private String metodoPago;
    /** Indica localizador de pago que se ha intentado verificar. */
    private String localizador;
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

    /**
     * Método de acceso a metodoPago.
     *
     * @return metodoPago
     */
    public String getMetodoPago() {
        return metodoPago;
    }

    /**
     * Método para establecer metodoPago.
     *
     * @param metodoPago
     *            metodoPago a establecer
     */
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    /**
     * Método de acceso a localizador.
     *
     * @return localizador
     */
    public String getLocalizador() {
        return localizador;
    }

    /**
     * Método para establecer localizador.
     *
     * @param localizador
     *                        localizador a establecer
     */
    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

}
