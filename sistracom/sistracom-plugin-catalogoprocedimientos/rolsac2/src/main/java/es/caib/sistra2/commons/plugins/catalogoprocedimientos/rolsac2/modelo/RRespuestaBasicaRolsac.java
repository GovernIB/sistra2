package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RRespuestaBasicaRolsac {

    /** Status a retornar. **/
    private String status;

    /** Mensaje de error. **/
    private String mensaje;

    /** Numero de Elementos. **/
    private Long numeroElementos;

    /** Constructor. **/
    public RRespuestaBasicaRolsac(final String status, final String mensaje,
            final Long numeroElementos) {
        super();
        this.status = status;
        this.mensaje = mensaje;
        this.numeroElementos = numeroElementos;
    }

    /** Constructor. **/
    public RRespuestaBasicaRolsac() {
        this.status = null;
        this.mensaje = null;
        this.numeroElementos = null;
    };

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * @return the mensajeError
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensajeError
     *            the mensajeError to set
     */
    public void setMensaje(final String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the numeroElementos
     */
    public Long getNumeroElementos() {
        return numeroElementos;
    }

    /**
     * @param numeroElementos
     *            the numeroElementos to set
     */
    public void setNumeroElementos(final Long numeroElementos) {
        this.numeroElementos = numeroElementos;
    }

}
