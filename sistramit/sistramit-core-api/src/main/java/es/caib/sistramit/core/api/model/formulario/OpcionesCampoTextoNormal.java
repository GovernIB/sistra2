package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

/**
 * Opciones de configuración de un campo del formulario de tipo texto normañ.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesCampoTextoNormal implements Serializable {

    /**
     * Tamaño máximo (carácteres).
     */
    private int tamanyo;

    /**
     * Multilinea (1:no multilinea / >1:multilinea).
     */
    private int lineas;

    /**
     * Método de acceso a tamanyo.
     *
     * @return tamanyo
     */
    public int getTamanyo() {
        return tamanyo;
    }

    /**
     * Método para establecer tamanyo.
     *
     * @param pTamanyo
     *            tamanyo a establecer
     */
    public void setTamanyo(final int pTamanyo) {
        tamanyo = pTamanyo;
    }

    /**
     * Método de acceso a lineas.
     *
     * @return lineas
     */
    public int getLineas() {
        return lineas;
    }

    /**
     * Método para establecer lineas.
     *
     * @param pLineas
     *            lineas a establecer
     */
    public void setLineas(final int pLineas) {
        lineas = pLineas;
    }

}
