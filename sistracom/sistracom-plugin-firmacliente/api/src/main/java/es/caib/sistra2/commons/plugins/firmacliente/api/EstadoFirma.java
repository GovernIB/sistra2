package es.caib.sistra2.commons.plugins.firmacliente.api;

/**
 * Estado de la sesión de firma.
 */
public class EstadoFirma {

    /** Estado de la firma. */
    private TypeEstadoFirmado estadoFirmado;

    /** Código de error. */
    private String codigoError;

    /** Mensaje de error. */
    private String mensajeError;

    /** Metodo de firma. */
    private String metodoFirma;

    /**
     * Método de acceso a estadoFirmado.
     *
     * @return estadoFirmado
     */
    public TypeEstadoFirmado getEstadoFirmado() {
        return estadoFirmado;
    }

    /**
     * Método para establecer estadoFirmado.
     *
     * @param estadoFirmado
     *            estadoFirmado a establecer
     */
    public void setEstadoFirmado(final TypeEstadoFirmado estadoFirmado) {
        this.estadoFirmado = estadoFirmado;
    }

    /**
     * Método de acceso a mensajeError.
     *
     * @return mensajeError
     */
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * Método para establecer mensajeError.
     *
     * @param mensajeError
     *            mensajeError a establecer
     */
    public void setMensajeError(final String mensajeError) {
        this.mensajeError = mensajeError;
    }

    /**
     * Método de acceso a metodoFirma.
     *
     * @return metodoFirma
     */
    public String getMetodoFirma() {
        return metodoFirma;
    }

    /**
     * Método para establecer metodoFirma.
     *
     * @param metodoFirma
     *            metodoFirma a establecer
     */
    public void setMetodoFirma(final String metodoFirma) {
        this.metodoFirma = metodoFirma;
    }

    /**
     * Método de acceso a codigoError.
     * @return codigoError
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * Método para establecer codigoError.
     * @param codigoError codigoError a establecer
     */
    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }
}
