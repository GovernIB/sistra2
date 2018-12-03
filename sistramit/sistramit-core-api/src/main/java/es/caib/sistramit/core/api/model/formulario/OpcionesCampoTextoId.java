package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Opciones particularizadas de configuración de un campo del formulario de tipo
 * texto identificador.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesCampoTextoId implements Serializable {

    /**
     * Indica si admite nif.
     */
    private TypeSiNo nif = TypeSiNo.NO;

    /**
     * Indica si admite cif.
     */
    private TypeSiNo cif = TypeSiNo.NO;

    /**
     * Indica si admite nie.
     */
    private TypeSiNo nie = TypeSiNo.NO;

    /**
     * Indica si admite número seguridad social.
     */
    private TypeSiNo nss = TypeSiNo.NO;

    /**
     * Método de acceso a nif.
     *
     * @return nif
     */
    public TypeSiNo getNif() {
        return nif;
    }

    /**
     * Método para establecer nif.
     *
     * @param pNif
     *            nif a establecer
     */
    public void setNif(final TypeSiNo pNif) {
        nif = pNif;
    }

    /**
     * Método de acceso a cif.
     *
     * @return cif
     */
    public TypeSiNo getCif() {
        return cif;
    }

    /**
     * Método para establecer cif.
     *
     * @param pCif
     *            cif a establecer
     */
    public void setCif(final TypeSiNo pCif) {
        cif = pCif;
    }

    /**
     * Método de acceso a nie.
     *
     * @return nie
     */
    public TypeSiNo getNie() {
        return nie;
    }

    /**
     * Método para establecer nie.
     *
     * @param pNie
     *            nie a establecer
     */
    public void setNie(final TypeSiNo pNie) {
        nie = pNie;
    }

    /**
     * Método de acceso a nss.
     *
     * @return nss
     */
    public TypeSiNo getNss() {
        return nss;
    }

    /**
     * Método para establecer nss.
     *
     * @param pNss
     *            nss a establecer
     */
    public void setNss(final TypeSiNo pNss) {
        nss = pNss;
    }

}
