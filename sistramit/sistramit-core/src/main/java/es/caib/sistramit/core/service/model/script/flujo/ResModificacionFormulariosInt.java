package es.caib.sistramit.core.service.model.script.flujo;

import javax.script.ScriptException;

import es.caib.sistramit.core.service.model.script.ClzValorCampoMultipleInt;
import es.caib.sistramit.core.service.model.script.PluginScriptRes;

/**
 * Modificación de los formularios en el script de post guardar.
 *
 * @author Indra
 *
 */
public interface ResModificacionFormulariosInt extends PluginScriptRes {

    /**
     * Id plugin.
     */
    String ID = "DATOS_FORMULARIOS";

    /**
     * Marca un formulario que estaba completado para que tenga que ser
     * revisado.
     *
     * @param idFormulario
     *            Id formulario
     */
    void setFormularioIncorrecto(final String idFormulario);

    /**
     * Establece el valor de un campo simple. Modificar un formulario implica
     * que se marcará con estado incorrecto para forzar su revisión. Solo se
     * podrán modificar formularios posteriores al formulario actual.
     *
     * @param idFormulario
     *            Id formulario a modificar
     * @param campo
     *            Id campo a modificar
     * @param valor
     *            Valor formulario
     * @throws ScriptException
     *             Se lanzará una excepción si no se puede modificar el
     *             formulario en este script.
     */
    void setValor(final String idFormulario, final String campo,
            final String valor) throws ScriptException;

    /**
     * Establece el valor de un campo compuesto. Modificar un formulario implica
     * que se marcará con estado incorrecto para forzar su revisión. Solo se
     * podrán modificar formularios posteriores al formulario actual.
     *
     * @param idFormulario
     *            id formulario a modificar
     * @param campo
     *            id campo a modificar
     * @param codigo
     *            Valor a establecer (codigo)
     * @param descripcion
     *            Valor a establecer (descripcion)
     * @throws ScriptException
     *             Exception
     */
    void setValorCompuesto(final String idFormulario, final String campo,
            final String codigo, final String descripcion)
            throws ScriptException;

    /**
     * Establece un valor a la lista de valores de un campo de selección
     * múltiple. Modificar un formulario implica que se marcará con estado
     * incorrecto para forzar su revisión. Solo se podrán modificar formularios
     * posteriores al formulario actual.
     *
     * @param idFormulario
     *            Id formulario a modificar.
     * @param campo
     *            Id campo a modificar.
     * @param valorMultiple
     *            Valor múltiple a establecer.
     * @throws ScriptException
     *             Exception
     */
    void setValorMultiple(final String idFormulario, final String campo,
            final ClzValorCampoMultipleInt valorMultiple)
            throws ScriptException;

    /**
     * Crea valor múltiple para alimentarlo y después poder establecerlo
     * mediante setValorMultiple.
     *
     * @return Valor múltiple para ser alimentado.
     */
    ClzValorCampoMultipleInt crearValorMultiple();

    /**
     * Resetea a vacío los valores establecidos en el formulario.
     *
     * @param idFormulario
     *            Id formulario
     * @throws ScriptException
     *             Exception
     */
    void resetValores(final String idFormulario) throws ScriptException;

}
