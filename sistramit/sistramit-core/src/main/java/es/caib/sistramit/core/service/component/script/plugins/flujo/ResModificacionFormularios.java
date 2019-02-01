package es.caib.sistramit.core.service.component.script.plugins.flujo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorResetCampos;
import es.caib.sistramit.core.service.component.script.ScriptUtils;
import es.caib.sistramit.core.service.component.script.plugins.ClzValorCampoMultiple;
import es.caib.sistramit.core.service.model.script.ClzValorCampoMultipleInt;
import es.caib.sistramit.core.service.model.script.ResModificacionFormulariosInt;
import es.caib.sistramit.core.service.util.UtilsFormulario;

/**
 *
 * Modificación de los formularios en el script de post guardar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResModificacionFormularios
        implements ResModificacionFormulariosInt {

    /**
     * Lista de datos modificados de los formularios.
     */
    private final Map<String, List<ValorCampo>> datosModificadosFormularios = new HashMap<>();

    /**
     * Lista de formularios modificados.
     */
    private final List<String> formulariosModificados = new ArrayList<>();

    /**
     * Lista de formularios marcados como incorrectos.
     */
    private final List<String> formulariosIncorrectos = new ArrayList<>();

    @Override
    public String getPluginId() {
        return ID;
    }

    @Override
    public void setFormularioIncorrecto(final String idFormulario) {
        formulariosIncorrectos.add(idFormulario);
    }

    @Override
    public void setValor(final String idFormulario, final String campo,
            final String valor) throws ScriptException {
        // Almacenamos modificación (se aplicará en el paso).
        final ValorCampoSimple vs = new ValorCampoSimple();
        vs.setId(campo);
        vs.setValor(valor);
        addValorModificado(idFormulario, vs);
    }

    @Override
    public void setValorCompuesto(final String idFormulario, final String campo,
            final String codigo, final String descripcion)
            throws ScriptException {
        // Almacenamos modificación (se aplicará en el paso).
        final ValorCampoIndexado vc = new ValorCampoIndexado();
        vc.setId(campo);
        vc.setValor(new ValorIndexado(codigo, descripcion));
        addValorModificado(idFormulario, vc);
    }

    @Override
    public void setValorMultiple(final String idFormulario, final String campo,
            final ClzValorCampoMultipleInt valorMultiple)
            throws ScriptException {
        // Almacenamos modificación (se aplicará en el paso).
        final ValorCampoListaIndexados vci = ScriptUtils
                .crearValorListaIndexados(campo, valorMultiple);
        addValorModificado(idFormulario, vci);
    }

    @Override
    public ClzValorCampoMultipleInt crearValorMultiple() {
        return new ClzValorCampoMultiple();
    }

    @Override
    public void resetValores(final String pIdFormulario)
            throws ScriptException {
        // Añadimos valor especial para resetear valores (se aplicará en el
        // paso).
        final ValorResetCampos vs = new ValorResetCampos();
        addValorModificado(pIdFormulario, vs);
    }

    /**
     * Método de acceso a formulariosModificados.
     *
     * @return formulariosModificados
     */
    public List<String> getFormulariosModificados() {
        return formulariosModificados;
    }

    /**
     * Método de acceso a formulariosIncorrectos.
     *
     * @return formulariosIncorrectos
     */
    public List<String> getFormulariosIncorrectos() {
        return formulariosIncorrectos;
    }

    /**
     * Método para obtener los datos modificados para un formulario.
     *
     * @param idFormulario
     *            Id formulario
     * @return Map con el id campo y valor del campo
     */
    public List<ValorCampo> getDatosModificadosFormulario(
            final String idFormulario) {
        return this.datosModificadosFormularios.get(idFormulario);
    }

    /**
     * Añade una modificación sobre un campo de un formulario.
     *
     * @param idFormulario
     *            Id formulario
     * @param valor
     *            Valor
     * @throws ScriptException
     *             Exception
     */
    private void addValorModificado(final String idFormulario,
            final ValorCampo valor) throws ScriptException {
        if (!UtilsFormulario.comprobarCaracteresPermitidos(valor)) {
            throw new ScriptException(
                    "Valor campo contiene caracteres no permitidos: "
                            + valor.print());
        }
        if (!this.datosModificadosFormularios.containsKey(idFormulario)) {
            this.formulariosModificados.add(idFormulario);
            this.datosModificadosFormularios.put(idFormulario,
                    new ArrayList<ValorCampo>());
        }
        this.datosModificadosFormularios.get(idFormulario).add(valor);
    }

}
