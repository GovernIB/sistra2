package es.caib.sistramit.core.api.model.flujo;

/**
 * Opción formulario de soporte.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class SoporteOpcion implements ModelApi {

    /** Código. */
    private String codigo;

    /** Título. */
    private String titulo;

    /** Descripción. */
    private String descripcion;

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
     * Método de acceso a titulo.
     *
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Método para establecer titulo.
     *
     * @param titulo
     *            titulo a establecer
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

}
