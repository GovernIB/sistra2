package es.caib.sistrages.rest.api.interna;

/**
 * Propiedades campo texto normal.
 *
 * @author Indra
 *
 */
public class RPropiedadesTextoNormal {

    /** Texto normal: tamaño máximo. */
    private int tamanyoMax;

    /** Texto normal: multilinea. */
    private boolean multilinea;

    /**
     * Método de acceso a tamanyoMax.
     *
     * @return tamanyoMax
     */
    public int getTamanyoMax() {
        return tamanyoMax;
    }

    /**
     * Método para establecer tamanyoMax.
     *
     * @param tamanyoMax
     *            tamanyoMax a establecer
     */
    public void setTamanyoMax(int tamanyoMax) {
        this.tamanyoMax = tamanyoMax;
    }

    /**
     * Método de acceso a multilinea.
     *
     * @return multilinea
     */
    public boolean isMultilinea() {
        return multilinea;
    }

    /**
     * Método para establecer multilinea.
     *
     * @param multilinea
     *            multilinea a establecer
     */
    public void setMultilinea(boolean multilinea) {
        this.multilinea = multilinea;
    }

}
