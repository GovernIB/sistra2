package es.caib.sistrages.rest.api;

/**
 * Opción formulario soporte.
 */
public class OpcionFormularioSoporte {

    /** Tipo incidencia. */
    private Literal tipo;

    /** Descripcion incidencia. */
    private Literal descripcion;

    /**
     * Destinatario: responsable incidencias ("R"), Lista fija de emails ("E").
     */
    private String destinatario;

    /** Lista emails (separadas por ;). */
    private String listaEmails;

    /**
     * Método de acceso a tipo.
     * 
     * @return tipo
     */
    public Literal getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     * 
     * @param tipo
     *            tipo a establecer
     */
    public void setTipo(Literal tipo) {
        this.tipo = tipo;
    }

    /**
     * Método de acceso a descripcion.
     * 
     * @return descripcion
     */
    public Literal getDescripcion() {
        return descripcion;
    }

    /**
     * Método para establecer descripcion.
     * 
     * @param descripcion
     *            descripcion a establecer
     */
    public void setDescripcion(Literal descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Método de acceso a destinatario.
     * 
     * @return destinatario
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * Método para establecer destinatario.
     * 
     * @param destinatario
     *            destinatario a establecer
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * Método de acceso a listaEmails.
     * 
     * @return listaEmails
     */
    public String getListaEmails() {
        return listaEmails;
    }

    /**
     * Método para establecer listaEmails.
     * 
     * @param listaEmails
     *            listaEmails a establecer
     */
    public void setListaEmails(String listaEmails) {
        this.listaEmails = listaEmails;
    }

}
