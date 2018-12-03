package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.types.TypeSeparador;

/**
 * Opciones de configuración de un campo del formulario de tipo texto número.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesCampoTextoNumero implements Serializable {

    /**
     * Número máximo de dígitos parte entera.
     */
    private int enteros;

    /**
     * Número de decimales.
     */
    private int decimales;

    /**
     * Tipo de separación de decimales/miles. Nulo sin separador.
     */
    private TypeSeparador separador;

    /**
     * Permite negativos.
     */
    private TypeSiNo negativo = TypeSiNo.NO;

    /**
     * Rango minimo. Si no existe null.
     */
    private Integer rangoMin;

    /**
     * Rango máximo. Si no existe null.
     */
    private Integer rangoMax;

    /**
     * Método de acceso a enteros.
     *
     * @return enteros
     */
    public int getEnteros() {
        return enteros;
    }

    /**
     * Método para establecer enteros.
     *
     * @param pEnteros
     *            enteros a establecer
     */
    public void setEnteros(final int pEnteros) {
        enteros = pEnteros;
    }

    /**
     * Método de acceso a decimales.
     *
     * @return decimales
     */
    public int getDecimales() {
        return decimales;
    }

    /**
     * Método para establecer decimales.
     *
     * @param pDecimales
     *            decimales a establecer
     */
    public void setDecimales(final int pDecimales) {
        decimales = pDecimales;
    }

    /**
     * Método de acceso a separador.
     *
     * @return separador
     */
    public TypeSeparador getSeparador() {
        return separador;
    }

    /**
     * Método para establecer separador.
     *
     * @param pSeparador
     *            separador a establecer
     */
    public void setSeparador(final TypeSeparador pSeparador) {
        separador = pSeparador;
    }

    /**
     * Método de acceso a negativos.
     *
     * @return negativos
     */
    public TypeSiNo getNegativo() {
        return negativo;
    }

    /**
     * Método para establecer negativos.
     *
     * @param pNegativos
     *            negativos a establecer
     */
    public void setNegativo(final TypeSiNo pNegativos) {
        negativo = pNegativos;
    }

    /**
     * Método de acceso a rangoMin.
     *
     * @return rangoMin
     */
    public Integer getRangoMin() {
        return rangoMin;
    }

    /**
     * Método para establecer rangoMin.
     *
     * @param pRangoMin
     *            rangoMin a establecer
     */
    public void setRangoMin(final Integer pRangoMin) {
        rangoMin = pRangoMin;
    }

    /**
     * Método de acceso a rangoMax.
     *
     * @return rangoMax
     */
    public Integer getRangoMax() {
        return rangoMax;
    }

    /**
     * Método para establecer rangoMax.
     *
     * @param pRangoMax
     *            rangoMax a establecer
     */
    public void setRangoMax(final Integer pRangoMax) {
        rangoMax = pRangoMax;
    }

}
