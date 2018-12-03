package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;

/**
 * Configuración de un campo del formulario.
 *
 * @author Indra
 *
 */
public abstract class ConfiguracionCampo implements Serializable {
    /**
     * Id del campo.
     */
    private String id;
    /**
     * Tipo de campo.
     */
    private TypeCampo tipo;
    /**
     * Obligatorio.
     */
    private TypeSiNo obligatorio = TypeSiNo.NO;
    /**
     * Indica si el campo es solo lectura.
     */
    private TypeSiNo soloLectura = TypeSiNo.NO;
    /**
     * Indica si el campo es modificable (si el campo puede cambiar de valor
     * respecto a su valor inicial).
     */
    private TypeSiNo modificable = TypeSiNo.SI;
    /**
     * Ayuda online.
     */
    private String ayuda;
    /**
     * Indica si el campo debe evaluarse en servidor al modificar (existen
     * dependencias en script de otros campos).
     */
    private TypeSiNo evaluar = TypeSiNo.NO;

    /**
     * Método de acceso a tipo.
     *
     * @return tipo
     */
    public final TypeCampo getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param pTipo
     *            tipo a establecer
     */
    protected final void setTipo(final TypeCampo pTipo) {
        tipo = pTipo;
    }

    /**
     * Método de acceso a obligatorio.
     *
     * @return obligatorio
     */
    public final TypeSiNo getObligatorio() {
        return obligatorio;
    }

    /**
     * Método para establecer obligatorio.
     *
     * @param pObligatorio
     *            obligatorio a establecer
     */
    public final void setObligatorio(final TypeSiNo pObligatorio) {
        obligatorio = pObligatorio;
    }

    /**
     * Método de acceso a ayuda.
     *
     * @return ayuda
     */
    public final String getAyuda() {
        return ayuda;
    }

    /**
     * Método para establecer ayuda.
     *
     * @param pAyuda
     *            ayuda a establecer
     */
    public final void setAyuda(final String pAyuda) {
        ayuda = pAyuda;
    }

    /**
     * Indica id del campo.
     *
     * @return id del campo
     */
    public final String getId() {
        return id;
    }

    /**
     * Indica id del campo.
     *
     * @param pId
     *            id del campo
     */
    public final void setId(final String pId) {
        id = pId;
    }

    /**
     * Indica si el campo es solo lectura.
     *
     * @return indica si el campo es solo lectura
     */
    public final TypeSiNo getSoloLectura() {
        return soloLectura;
    }

    /**
     * Indica si el campo es solo lectura.
     *
     * @param pSoloLectura
     *            indica si el campo es solo lectura
     */
    public final void setSoloLectura(final TypeSiNo pSoloLectura) {
        soloLectura = pSoloLectura;
    }

    /**
     * Indica si el campo debe evaluarse en servidor al modificar (existen
     * dependencias en script de otros campos).
     *
     * @return indica si el campo debe evaluarse en servidor al modificar
     *         (existen dependencias en script de otros campos)
     */
    public final TypeSiNo getEvaluar() {
        return evaluar;
    }

    /**
     * Indica si el campo debe evaluarse en servidor al modificar (existen
     * dependencias en script de otros campos).
     *
     * @param pEvaluar
     *            indica si el campo debe evaluarse en servidor al modificar
     *            (existen dependencias en script de otros campos)
     */
    public final void setEvaluar(final TypeSiNo pEvaluar) {
        evaluar = pEvaluar;
    }

    /**
     * Método de acceso a modificable.
     *
     * @return modificable
     */
    public final TypeSiNo getModificable() {
        return modificable;
    }

    /**
     * Método para establecer modificable.
     *
     * @param pModificable
     *            modificable a establecer
     */
    public final void setModificable(final TypeSiNo pModificable) {
        modificable = pModificable;
    }

}
