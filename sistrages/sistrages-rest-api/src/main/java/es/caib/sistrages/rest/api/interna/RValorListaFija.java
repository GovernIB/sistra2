package es.caib.sistrages.rest.api.interna;

/**
 * Valor lista fija.
 *
 * @author Indra
 *
 */
public class RValorListaFija {

    /** Código. */
    private String codigo;

    /** Descripción. */
    private String descripcion;

    /** Defecto. */
    private boolean porDefecto;

    /**
     * Método de acceso a codigo.
     *
     * @return codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Método para establecer codigo.
     *
     * @param codigo
     *            codigo a establecer
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Método de acceso a descripcion.
     *
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método para establecer descripcion.
     *
     * @param descripcion
     *            descripcion a establecer
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Método de acceso a porDefecto.
     *
     * @return porDefecto
     */
    public boolean isPorDefecto() {
        return porDefecto;
    }

    /**
     * Método para establecer porDefecto.
     *
     * @param porDefecto
     *            porDefecto a establecer
     */
    public void setPorDefecto(boolean porDefecto) {
        this.porDefecto = porDefecto;
    }

}
