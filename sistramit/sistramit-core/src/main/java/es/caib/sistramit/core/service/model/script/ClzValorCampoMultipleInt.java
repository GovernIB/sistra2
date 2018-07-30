package es.caib.sistramit.core.service.model.script;

/**
 * Clase de acceso a un valor de un campo lista indexados desde los plugins.
 *
 * @author Indra
 *
 */
public interface ClzValorCampoMultipleInt extends PluginClass {

    /**
     * Añade valor campo.
     *
     * @param codigo
     *            Código valor
     * @param descripcion
     *            Descripción valor
     */
    void addValor(final String codigo, final String descripcion);

    /**
     * Devuelve numero de valores del campo.
     *
     * @return Número de valores
     */
    int getNumeroValores();

    /**
     * Devuelve un valor del campo.
     *
     * @param index
     *            Indice del campo
     * @return Valor del campo
     */
    ClzValorCampoCompuestoInt getValor(final int index);

}
