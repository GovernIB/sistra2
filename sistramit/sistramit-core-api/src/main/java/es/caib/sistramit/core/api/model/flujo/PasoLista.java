package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 * Información de paso de tramitación en la lista de pasos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PasoLista implements Serializable {

    /**
     * Identificador paso.
     */
    private String id;

    /**
     * Indica el tipo de paso.
     */
    private TypePaso tipo;

    /**
     * Indica si se ha completado el paso.
     */
    private TypeSiNo completado = TypeSiNo.NO;

    /**
     * Indica si el paso es accesible.
     */
    private TypeSiNo accesible = TypeSiNo.NO;

    /**
     * Crea instancia.
     *
     * @return PasoLista
     */
    public static PasoLista createNewPasoLista() {
        return new PasoLista();
    }

    /**
     * Método de acceso a idPaso.
     *
     * @return idPaso
     */
    public String getId() {
        return id;
    }

    /**
     * Método para establecer idPaso.
     *
     * @param pIdPasoLista
     *            idPaso a establecer
     */
    public void setId(final String pIdPasoLista) {
        id = pIdPasoLista;
    }

    /**
     * Método de acceso a tipo.
     *
     * @return tipo
     */
    public TypePaso getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param pTipo
     *            tipo a establecer
     */
    public void setTipo(final TypePaso pTipo) {
        tipo = pTipo;
    }

    /**
     * Método de acceso a completado.
     *
     * @return completado
     */
    public TypeSiNo getCompletado() {
        return completado;
    }

    /**
     * Método para establecer completado.
     *
     * @param pCompletado
     *            completado a establecer
     */
    public void setCompletado(final TypeSiNo pCompletado) {
        completado = pCompletado;
    }

    /**
     * Método de acceso a accesible.
     *
     * @return accesible
     */
    public TypeSiNo getAccesible() {
        return accesible;
    }

    /**
     * Método para establecer accesible.
     *
     * @param pAccesible
     *            accesible a establecer
     */
    public void setAccesible(final TypeSiNo pAccesible) {
        accesible = pAccesible;
    }

}
