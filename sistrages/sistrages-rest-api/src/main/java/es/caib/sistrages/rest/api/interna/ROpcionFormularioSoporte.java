package es.caib.sistrages.rest.api.interna;

/**
 * Opción formulario soporte.
 */
public class ROpcionFormularioSoporte {

    /** Tipo incidencia. */
    private RLiteral tipo;

    /** Descripcion incidencia. */
    private RLiteral descripcion;

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
    public RLiteral getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param tipo
     *            tipo a establecer
     */
    public void setTipo(RLiteral tipo) {
        this.tipo = tipo;
    }

    /**
     * Método de acceso a descripcion.
     *
     * @return descripcion
     */
    public RLiteral getDescripcion() {
        return descripcion;
    }

    /**
     * Método para establecer descripcion.
     *
     * @param descripcion
     *            descripcion a establecer
     */
    public void setDescripcion(RLiteral descripcion) {
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
