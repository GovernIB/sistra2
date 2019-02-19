package es.caib.sistramit.core.service.component.script.plugins.flujo;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.service.component.script.ScriptUtils;
import es.caib.sistramit.core.service.component.script.plugins.ClzValorCampoMultiple;
import es.caib.sistramit.core.service.model.script.ClzValorCampoMultipleInt;
import es.caib.sistramit.core.service.model.script.flujo.ResDatosInicialesFormularioInt;
import es.caib.sistramit.core.service.util.UtilsFormulario;

/**
 *
 * Permite establecer datos iniciales en un formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResDatosInicialesFormulario
        implements ResDatosInicialesFormularioInt {
    /**
     * Lista de datos inciales del formulario.
     */
    private final List<ValorCampo> datosIniciales = new ArrayList<>();

    @Override
    public String getPluginId() {
        return ID;
    }

    @Override
    public void setValor(final String campo, final String valor)
            throws ScriptException {
        final ValorCampoSimple vs = new ValorCampoSimple(campo, valor);
        validarCampo(vs);
        datosIniciales.add(vs);
    }

    @Override
    public void setValorCompuesto(final String campo, final String codigo,
            final String descripcion) throws ScriptException {
        final ValorCampoIndexado vc = new ValorCampoIndexado(campo, codigo,
                descripcion);
        validarCampo(vc);
        datosIniciales.add(vc);
    }

    @Override
    public void setValorMultiple(final String campo,
            final ClzValorCampoMultipleInt valores) throws ScriptException {
        final ValorCampoListaIndexados valorInicial = ScriptUtils
                .crearValorListaIndexados(campo, valores);
        validarCampo(valorInicial);
        datosIniciales.add(valorInicial);
    }

    @Override
    public ClzValorCampoMultipleInt crearValorMultiple() {
        return new ClzValorCampoMultiple();
    }

    /**
     * Método de acceso a datosIniciales.
     *
     * @return datosIniciales
     */
    public List<ValorCampo> getDatosIniciales() {
        return datosIniciales;
    }

    /**
     * Valida si campo tiene valor valido.
     *
     * @param vs
     *            Valor campo
     * @throws ScriptException
     *             Excepcion
     */
    private void validarCampo(final ValorCampo vs) throws ScriptException {
        if (!UtilsFormulario.comprobarCaracteresPermitidos(vs)) {
            throw new ScriptException(
                    "Valor campo contiene caracteres no permitidos: "
                            + vs.print());
        }
    }
}
