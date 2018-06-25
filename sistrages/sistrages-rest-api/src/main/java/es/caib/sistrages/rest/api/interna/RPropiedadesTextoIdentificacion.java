package es.caib.sistrages.rest.api.interna;

/**
 * Propiedades campo texto identificacion.
 *
 * @author Indra
 *
 */
public class RPropiedadesTextoIdentificacion {

    /** Texto identificación: nif. */
    private boolean nif;

    /** Texto identificación: cif. */
    private boolean cif;

    /** Texto identificación: nie. */
    private boolean nie;

    /** Texto identificación: nss. */
    private boolean nss;

    /**
     * Método de acceso a nif.
     *
     * @return nif
     */
    public boolean isNif() {
        return nif;
    }

    /**
     * Método para establecer nif.
     *
     * @param nif
     *            nif a establecer
     */
    public void setNif(boolean nif) {
        this.nif = nif;
    }

    /**
     * Método de acceso a cif.
     *
     * @return cif
     */
    public boolean isCif() {
        return cif;
    }

    /**
     * Método para establecer cif.
     *
     * @param cif
     *            cif a establecer
     */
    public void setCif(boolean cif) {
        this.cif = cif;
    }

    /**
     * Método de acceso a nie.
     *
     * @return nie
     */
    public boolean isNie() {
        return nie;
    }

    /**
     * Método para establecer nie.
     *
     * @param nie
     *            nie a establecer
     */
    public void setNie(boolean nie) {
        this.nie = nie;
    }

    /**
     * Método de acceso a nss.
     *
     * @return nss
     */
    public boolean isNss() {
        return nss;
    }

    /**
     * Método para establecer nss.
     *
     * @param nss
     *            nss a establecer
     */
    public void setNss(boolean nss) {
        this.nss = nss;
    }

}
