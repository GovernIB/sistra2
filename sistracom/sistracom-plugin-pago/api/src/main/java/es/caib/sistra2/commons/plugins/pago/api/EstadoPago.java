package es.caib.sistra2.commons.plugins.pago.api;

import java.util.Date;

public class EstadoPago {

    /** Estado pago. */
    private TypeEstadoPago estado;

    /** Fecha pago, opcional, en caso de estar pagado. */
    private Date fechaPago;

    /** Localizador pago, en caso de estar pagado. */
    private String localizador;

    /** Código error pasarela, opcional en caso de estado desconocido. */
    private String codigoErrorPasarela;

    /** Mensaje error pasarela, opcional en caso de estado desconocido. */
    private String mensajeErrorPasarela;

    /**
     * Método de acceso a estado.
     *
     * @return estado
     */
    public TypeEstadoPago getEstado() {
        return estado;
    }

    /**
     * Método para establecer estado.
     *
     * @param estado
     *            estado a establecer
     */
    public void setEstado(TypeEstadoPago estado) {
        this.estado = estado;
    }

    /**
     * Método de acceso a fechaPago.
     *
     * @return fechaPago
     */
    public Date getFechaPago() {
        return fechaPago;
    }

    /**
     * Método para establecer fechaPago.
     *
     * @param fechaPago
     *            fechaPago a establecer
     */
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    /**
     * Método de acceso a codigoErrorPasarela.
     *
     * @return codigoErrorPasarela
     */
    public String getCodigoErrorPasarela() {
        return codigoErrorPasarela;
    }

    /**
     * Método para establecer codigoErrorPasarela.
     *
     * @param codigoErrorPasarela
     *            codigoErrorPasarela a establecer
     */
    public void setCodigoErrorPasarela(String codigoErrorPasarela) {
        this.codigoErrorPasarela = codigoErrorPasarela;
    }

    /**
     * Método de acceso a mensajeErrorPasarela.
     *
     * @return mensajeErrorPasarela
     */
    public String getMensajeErrorPasarela() {
        return mensajeErrorPasarela;
    }

    /**
     * Método para establecer mensajeErrorPasarela.
     *
     * @param mensajeErrorPasarela
     *            mensajeErrorPasarela a establecer
     */
    public void setMensajeErrorPasarela(String mensajeErrorPasarela) {
        this.mensajeErrorPasarela = mensajeErrorPasarela;
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
     *            localizador a establecer
     */
    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

}
