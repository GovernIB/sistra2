package es.caib.sistramit.core.service.model.script;

/**
 * Clase de acceso a un valor indexado desde los plugins.
 *
 * @author Indra
 *
 */
public interface ClzValorCampoCompuestoInt extends PluginClass {

    /**
     * Método de acceso a codigo.
     *
     * @return codigo
     */
    String getCodigo();

    /**
     * Método para establecer codigo.
     *
     * @param pCodigo
     *            codigo a establecer
     */
    void setCodigo(final String pCodigo);

    /**
     * Método de acceso a descripcion.
     *
     * @return descripcion
     */
    String getDescripcion();

    /**
     * Método para establecer descripcion.
     *
     * @param pDescripcion
     *            descripcion a establecer
     */
    void setDescripcion(final String pDescripcion);

}
