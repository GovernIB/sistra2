package es.caib.sistramit.core.service.component.script;

import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.service.component.script.plugins.ClzValorCampoCompuesto;
import es.caib.sistramit.core.service.model.script.ClzValorCampoCompuestoInt;
import es.caib.sistramit.core.service.model.script.ClzValorCampoMultipleInt;

/**
 * Utilidades para scripts.
 *
 * @author Indra
 *
 */
public final class ScriptUtils {

    /**
     * Constructor.
     */
    private ScriptUtils() {
        super();
    }

    /**
     * Calcula mensaje de error.
     *
     * @param pCodigosError
     *            Códigos de error y sus mensajes asociados
     * @param pCodigoMensajeError
     *            Código mensaje error a calcular
     * @param pParametrosMensajeError
     *            Parámetros mensaje error
     * @return Mensaje de error
     */
    public static String calculaMensajeError(
            final Map<String, String> pCodigosError,
            final String pCodigoMensajeError,
            final List<String> pParametrosMensajeError) {
        String res = pCodigosError.get(pCodigoMensajeError);
        if (res == null) {
            res = "";
        } else {
            if (pParametrosMensajeError != null
                    && pParametrosMensajeError.size() > 0) {
                for (int i = 0; i < pParametrosMensajeError.size(); i++) {
                    res = StringUtils.replace(res, "{" + i + "}",
                            pParametrosMensajeError.get(i));
                }
            }
        }
        return res;
    }

    /**
     * Valida datos persona.
     *
     * @param nifNormalizado
     *            Nif normalizado
     * @param pNombre
     *            Nombre
     * @param pApellido1
     *            Apellido 1
     * @param pApellido2
     *            Apellido 2
     * @throws ScriptException
     *             Genera excepcion si no pasa validacion
     */
    public static void validarDatosPersona(final String nifNormalizado,
            final String pNombre, final String pApellido1,
            final String pApellido2) throws ScriptException {

        if (!ValidacionesTipo.getInstance().esNif(nifNormalizado)
                && !ValidacionesTipo.getInstance().esCif(nifNormalizado)
                && !ValidacionesTipo.getInstance().esNie(nifNormalizado)) {
            throw new ScriptException(
                    "El dato proporcionado como nif persona no es nif/nie/cif: "
                            + nifNormalizado);
        }
        if (StringUtils.isEmpty(pNombre) || !XssFilter.filtroXss(pNombre)) {
            throw new ScriptException(
                    "El dato proporcionado como nombre persona esta vacio o contiene caracteres no permitidos");
        }

        if (!StringUtils.isBlank(pApellido1)) {
            if (!XssFilter.filtroXss(pApellido1)) {
                throw new ScriptException(
                        "El dato proporcionado como apellido 1 persona contiene caracetres no permitidos");
            }
        }
        if (!StringUtils.isBlank(pApellido2)) {
            if (!XssFilter.filtroXss(pApellido2)) {
                throw new ScriptException(
                        "El dato proporcionado como apellido 2 persona contiene caracteres no permitidos");
            }
        }
    }

    /**
     * Crea valor lista indexados a partir de un valor multiple.
     *
     * @param campo
     *            Id campo
     * @param valorMultiple
     *            Valor multiple
     * @return Valor lista indexados
     */
    public static ValorCampoListaIndexados crearValorListaIndexados(
            final String campo, final ClzValorCampoMultipleInt valorMultiple) {
        final ValorCampoListaIndexados vci = new ValorCampoListaIndexados();
        vci.setId(campo);
        if (valorMultiple != null) {
            for (int i = 0; i < valorMultiple.getNumeroValores(); i++) {
                final ClzValorCampoCompuestoInt vcc = valorMultiple.getValor(i);
                vci.addValorIndexado(vcc.getCodigo(), vcc.getDescripcion());
            }
        }
        return vci;
    }

    /**
     * Genera valor compuesto a partir de un valor indexado.
     *
     * @param vi
     *            Valor indexado
     * @return valor compuesto
     */
    public static ClzValorCampoCompuestoInt crearValorCompuesto(
            final ValorIndexado vi) {
        ClzValorCampoCompuestoInt res;
        if (vi != null) {
            res = new ClzValorCampoCompuesto(vi.getValor(),
                    vi.getDescripcion());
        } else {
            res = new ClzValorCampoCompuesto("", "");
        }
        return res;
    }

}
