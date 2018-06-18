package es.caib.sistrages.rest.api;

/**
 * Plugin.
 *
 * @author Indra
 *
 */
public class Plugin {

    /** Tipo. */
    private String tipo;

    /** Classname. */
    private String classname;

    /** Propiedades. */
    private ListaParametros parametros;

    /**
     * Método de acceso a tipo.
     * 
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     * 
     * @param tipo
     *            tipo a establecer
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Método de acceso a classname.
     * 
     * @return classname
     */
    public String getClassname() {
        return classname;
    }

    /**
     * Método para establecer classname.
     * 
     * @param classname
     *            classname a establecer
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    /**
     * Método de acceso a parametros.
     * 
     * @return parametros
     */
    public ListaParametros getParametros() {
        return parametros;
    }

    /**
     * Método para establecer parametros.
     * 
     * @param parametros
     *            parametros a establecer
     */
    public void setParametros(ListaParametros parametros) {
        this.parametros = parametros;
    }

}
