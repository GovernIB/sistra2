package es.caib.sistra2.commons.plugins.digitalizacion.api;

/**
 * Resultado digitalización.
 */
public class ResultadoDigitalizacion {

    /** Documento digitalizado (contenido). */
    private boolean digitalizado;

    /** Documento digitalizado (nombre fichero). */
    private String nombreFichero;

    /** Mensaje error. */
    private String mensajeError;

    /** Traza error. */
    private String trazaError;


    private byte[] documento;

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public boolean isDigitalizado() {
        return digitalizado;
    }

    public void setDigitalizado(boolean digitalizado) {
        this.digitalizado = digitalizado;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }

    public void setNombreFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    public String getTrazaError() {
        return trazaError;
    }

    public void setTrazaError(String trazaError) {
        this.trazaError = trazaError;
    }
}
