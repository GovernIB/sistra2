package es.caib.sistrages.rest.api;

/**
 * Destino registro.
 *
 * @author Indra
 *
 */
public class DestinoRegistro {

    /** Oficina registro. */
    private String oficinaRegistro;

    /** Libro registro. */
    private String libroRegistro;

    /** Tipo asunto. */
    private String tipoAsunto;

    /**
     * Método de acceso a oficinaRegistro.
     * 
     * @return oficinaRegistro
     */
    public String getOficinaRegistro() {
        return oficinaRegistro;
    }

    /**
     * Método para establecer oficinaRegistro.
     * 
     * @param oficinaRegistro
     *            oficinaRegistro a establecer
     */
    public void setOficinaRegistro(String oficinaRegistro) {
        this.oficinaRegistro = oficinaRegistro;
    }

    /**
     * Método de acceso a libroRegistro.
     * 
     * @return libroRegistro
     */
    public String getLibroRegistro() {
        return libroRegistro;
    }

    /**
     * Método para establecer libroRegistro.
     * 
     * @param libroRegistro
     *            libroRegistro a establecer
     */
    public void setLibroRegistro(String libroRegistro) {
        this.libroRegistro = libroRegistro;
    }

    /**
     * Método de acceso a tipoAsunto.
     * 
     * @return tipoAsunto
     */
    public String getTipoAsunto() {
        return tipoAsunto;
    }

    /**
     * Método para establecer tipoAsunto.
     * 
     * @param tipoAsunto
     *            tipoAsunto a establecer
     */
    public void setTipoAsunto(String tipoAsunto) {
        this.tipoAsunto = tipoAsunto;
    }

}
