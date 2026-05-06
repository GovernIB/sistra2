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

    /** totalCount. **/
    private Long totalCount;

    /** Constructor. **/
    public RRespuestaBasicaRolsac(final String status, final String mensaje,
            final Long totalCount) {
        super();
        this.status = status;
        this.mensaje = mensaje;
        this.totalCount = totalCount;
    }

    /** Constructor. **/
    public RRespuestaBasicaRolsac() {
        this.status = null;
        this.mensaje = null;
        this.totalCount = null;
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
     * @return the totalCount
     */
    public Long getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount
     *            the totalCount to set
     */
    public void setTotalCount(final Long totalCount) {
        this.totalCount = totalCount;
    }

}
