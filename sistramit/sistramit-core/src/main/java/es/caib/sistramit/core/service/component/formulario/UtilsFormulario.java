package es.caib.sistramit.core.service.component.formulario;

import java.util.List;

import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.service.model.formulario.ValorCampo;
import es.caib.sistramit.core.service.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.service.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.service.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.service.model.formulario.ValorIndexado;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.model.formulario.types.TypeValor;

/**
 * Clase de utilidades para formularios.
 *
 * @author Indra
 *
 */
public class UtilsFormulario {

    /**
     * Crea valor vacío según tipo de campo.
     *
     * @param idCampo
     *            Id campo
     * @param tipo
     *            Tipo de campo
     * @return Valor campo vacio
     */
    public static ValorCampo crearValorVacio(final String idCampo,
            final TypeValor tipo) {
        ValorCampo res = null;
        switch (tipo) {
        case SIMPLE:
            res = ValorCampoSimple.createValorVacio(idCampo);
            break;
        case INDEXADO:
            res = ValorCampoIndexado.createValorVacio(idCampo);
            break;
        case LISTA_INDEXADOS:
            res = ValorCampoListaIndexados.createValorVacio(idCampo);
            break;
        default:
            throw new TipoNoControladoException(
                    msgTipoValorCampoNoControlado(tipo.name()));
        }
        return res;
    }

    /**
     * Interpreta xml de formulario y devuelve lista de valores de los campos.
     *
     * @param pXml
     *            Xml datos formulario
     * @return Lista de valores de los campos.
     */
    public static XmlFormulario xmlToValores(final byte[] pXml) {

        // TODO PENDIENTE PARSEO FORMULARIOS

        return null;

    }

    public static byte[] valoresToXml(XmlFormulario xmlFormulario) {

        // TODO PENDIENTE PARSEO FORMULARIOS

        return null;
    }

    /**
     * Comprueba si el campo tiene caracteres permitidos.
     *
     * @param campo
     *            Campo
     * @return boolean
     */
    public static boolean comprobarCaracteresPermitidos(
            final ValorCampo campo) {
        boolean res = true;
        if (!campo.esVacio()) {
            switch (campo.getTipo()) {
            case SIMPLE:
                final String v1 = ((ValorCampoSimple) campo).getValor();
                res = validarCaracteresPermitidos(v1);
                break;
            case INDEXADO:
                final ValorIndexado vi1 = ((ValorCampoIndexado) campo)
                        .getValor();
                res = validarCaracteresPermitidos(vi1.getValor());
                if (res) {
                    res = validarCaracteresPermitidos(vi1.getDescripcion());
                }
                break;
            case LISTA_INDEXADOS:
                final List<ValorIndexado> vli = ((ValorCampoListaIndexados) campo)
                        .getValor();
                for (final ValorIndexado vi : vli) {
                    res = validarCaracteresPermitidos(vi.getValor());
                    if (res) {
                        res = validarCaracteresPermitidos(vi.getDescripcion());
                    }
                    if (!res) {
                        break;
                    }
                }
                break;
            default:
                throw new TipoNoControladoException(
                        msgTipoValorCampoNoControlado(campo.getTipo().name()));
            }
        }

        return res;
    }

    /**
     * Método para Msg tipo valor campo no controlado de la clase
     * UtilsFormulario.
     *
     * @param campo
     *            Parámetro campo
     * @return el string
     */
    private static String msgTipoValorCampoNoControlado(final String campo) {
        return "Tipo de valor campo " + campo + " no controlado";
    }

    /**
     * Validacion estandar de los caracteres permitidos. Todos excepto "<" y
     * ">".
     *
     * @param valor
     *            Valor
     * @return boolean
     */
    private static boolean validarCaracteresPermitidos(final String valor) {
        return XssFilter.filtroXss(valor);
    }
}
