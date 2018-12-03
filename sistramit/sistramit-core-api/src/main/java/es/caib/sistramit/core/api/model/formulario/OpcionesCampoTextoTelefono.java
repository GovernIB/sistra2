package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Configuración de un campo del formulario de tipo texto teléfono.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesCampoTextoTelefono implements Serializable {

    /**
     * Indica si admite fijos.
     */
    private TypeSiNo fijo = TypeSiNo.NO;

    /**
     * Indica si admite móviles.
     */
    private TypeSiNo movil = TypeSiNo.NO;

    /**
     * Método de acceso a fijo.
     *
     * @return fijo
     */
    public TypeSiNo getFijo() {
        return fijo;
    }

    /**
     * Método para establecer fijo.
     *
     * @param pFijo
     *            fijo a establecer
     */
    public void setFijo(final TypeSiNo pFijo) {
        fijo = pFijo;
    }

    /**
     * Método de acceso a movil.
     *
     * @return movil
     */
    public TypeSiNo getMovil() {
        return movil;
    }

    /**
     * Método para establecer movil.
     *
     * @param pMovil
     *            movil a establecer
     */
    public void setMovil(final TypeSiNo pMovil) {
        movil = pMovil;
    }

}
