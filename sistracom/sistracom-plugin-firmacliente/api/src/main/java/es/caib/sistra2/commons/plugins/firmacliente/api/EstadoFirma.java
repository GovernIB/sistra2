package es.caib.sistra2.commons.plugins.firmacliente.api;

/**
 * Estado de la sesión de firma.
 */
public class EstadoFirma {

    /** Estado de la firma. */
    private TypeEstadoFirmado estadoFirmado;

    /** Mensaje de error. */
    private String mensajeError;

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
}
