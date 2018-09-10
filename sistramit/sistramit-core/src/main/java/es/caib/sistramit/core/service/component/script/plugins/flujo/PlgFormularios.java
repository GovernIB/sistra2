package es.caib.sistramit.core.service.component.script.plugins.flujo;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.types.TypeValor;
import es.caib.sistramit.core.service.component.script.ScriptUtils;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoFormulario;
import es.caib.sistramit.core.service.model.script.ClzValorCampoCompuestoInt;
import es.caib.sistramit.core.service.model.script.flujo.PlgFormulariosInt;

/**
 * Plugin de acceso a datos de formularios.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlgFormularios implements PlgFormulariosInt {

    /** Lista de datos de los formularios rellenados. */
    private final Map<String, DatosDocumentoFormulario> datosFormularios = new HashMap<>();

    /**
     * Constructor.
     *
     * @param forms
     *            datos formularios
     */
    public PlgFormularios(final List<DatosDocumentoFormulario> forms) {
        super();
        if (forms != null) {
            for (final DatosDocumentoFormulario df : forms) {
                datosFormularios.put(df.getId(), df);
            }
        }
    }

    @Override
    public String getPluginId() {
        return ID;
    }

    @Override
    public boolean isFormularioRellenado(final String idFormulario) {
        return datosFormularios.containsKey(idFormulario);
    }

    @Override
    public String getValor(final String idFormulario, final String campo)
            throws ScriptException {
        String res = "";
        final ValorCampoSimple vc = (ValorCampoSimple) getValorCampo(
                idFormulario, campo, TypeValor.SIMPLE);
        if (vc != null) {
            res = vc.getValor();
        }
        return res;
    }

    @Override
    public ClzValorCampoCompuestoInt getValorCompuesto(
            final String idFormulario, final String campo)
            throws ScriptException {
        final ValorCampoIndexado vci = (ValorCampoIndexado) getValorCampo(
                idFormulario, campo, TypeValor.INDEXADO);
        final ClzValorCampoCompuestoInt res;
        if (vci != null) {
            res = ScriptUtils.crearValorCompuesto(vci.getValor());
        } else {
            res = ScriptUtils.crearValorCompuesto(null);
        }
        return res;
    }

    @Override
    public ClzValorCampoCompuestoInt getValorMultiple(final String idFormulario,
            final String campo, final int indice) throws ScriptException {
        ClzValorCampoCompuestoInt res = null;
        final ValorCampoListaIndexados vl = (ValorCampoListaIndexados) getValorCampo(
                idFormulario, campo, TypeValor.LISTA_INDEXADOS);
        if (vl != null) {
            final ValorIndexado vi = vl.getValor().get(indice);
            res = ScriptUtils.crearValorCompuesto(vi);
        } else {
            res = ScriptUtils.crearValorCompuesto(null);
        }
        return res;
    }

    @Override
    public int getNumeroValoresValorMultiple(final String idFormulario,
            final String campo) throws ScriptException {
        int res = 0;
        final ValorCampoListaIndexados vl = (ValorCampoListaIndexados) getValorCampo(
                idFormulario, campo, TypeValor.LISTA_INDEXADOS);
        if (vl != null && vl.getValor() != null) {
            res = vl.getValor().size();
        }
        return res;
    }

    @Override
    public String getAccionPersonalizada(final String idFormulario)
            throws ScriptException {
        final DatosDocumentoFormulario df = getDatosFormulario(idFormulario);
        String res = df.getCampos().getAccionPersonalizada();
        if (res == null) {
            res = "";
        }
        return res;
    }

    @Override
    public String getXml(final String idFormulario) throws ScriptException {
        final DatosDocumentoFormulario df = getDatosFormulario(idFormulario);
        String res;
        try {
            res = new String(df.getCampos().getXml(), Constantes.UTF8);
        } catch (final UnsupportedEncodingException e) {
            throw new ScriptException(e);
        }
        return res;
    }

    // ---------------------------------------------------------------------------------------
    // FUNCIONES UTILIDAD.
    // ---------------------------------------------------------------------------------------

    /**
     * Obtiene valor del campo.
     *
     * @param idFormulario
     *            idFormulario
     * @param campo
     *            idCampo
     * @param tipoValor
     *            Tipo valor
     * @return Valor del campo (depende del tipo)
     * @throws ScriptException
     *             Si formulario no existe o campo no es del tipo.
     */
    private ValorCampo getValorCampo(final String idFormulario,
            final String campo, final TypeValor tipoValor)
            throws ScriptException {

        // Obtiene formulario
        final DatosDocumentoFormulario df = getDatosFormulario(idFormulario);

        // Obtiene valor campo
        final ValorCampo res = df.getCampos().getValorCampo(campo);
        if (res != null) {
            // Verificamos tipo
            if (res.getTipo() != tipoValor) {
                throw new ScriptException("El campo " + campo
                        + " no es de tipo " + tipoValor.name());
            }
        }

        return res;
    }

    /**
     * Obtiene datos formulario.
     *
     * @param idFormulario
     *            idFormulario
     * @return datos formulario
     * @throws ScriptException
     *             Si formulario no existe.
     */
    private DatosDocumentoFormulario getDatosFormulario(
            final String idFormulario) throws ScriptException {
        final DatosDocumentoFormulario df = this.datosFormularios
                .get(idFormulario);
        if (df == null) {
            throw new ScriptException("El formulario " + idFormulario
                    + " no existe, no esta rellenado o no es accesible desde este script");
        }
        return df;
    }

}
