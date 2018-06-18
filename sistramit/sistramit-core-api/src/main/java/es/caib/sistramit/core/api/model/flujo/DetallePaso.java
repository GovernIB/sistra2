package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 * Detalle de un paso del trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public abstract class DetallePaso implements Serializable {

    /**
     * Identificador paso.
     */
    private String id;

    /**
     * Tipo de paso.
     */
    private TypePaso tipo;

    /**
     * Titulo del paso.
     */
    private String titulo; // TODO Ver si eliminar

    /**
     * Info del paso. Información inicial del paso (contiene HTML).
     */
    private String info; // TODO Ver si eliminar

    /**
     * Indica si el paso esta completado.
     */
    private TypeSiNo completado = TypeSiNo.NO;

    /**
     * Indica que los elementos del paso no se pueden modificar (formularios,
     * anexos, etc.).
     */
    private TypeSiNo soloLectura = TypeSiNo.NO;

    /**
     * Indica indica si el paso esta completado.
     *
     * @return indica si el paso esta completado
     */
    public final TypeSiNo getCompletado() {
        return completado;
    }

    /**
     * Indica indica si el paso esta completado.
     *
     * @param pEstado
     *            indica si el paso esta completado
     */
    public final void setCompletado(final TypeSiNo pEstado) {
        completado = pEstado;
    }

    /**
     * Indica identificador paso.
     *
     * @return identificador paso
     */
    public final String getId() {
        return id;
    }

    /**
     * Indica identificador paso.
     *
     * @param pId
     *            identificador paso
     */
    public final void setId(final String pId) {
        id = pId;
    }

    /**
     * Indica titulo del paso.
     *
     * @return titulo del paso
     */
    public final String getTitulo() {
        return titulo;
    }

    /**
     * Indica titulo del paso.
     *
     * @param pTitulo
     *            titulo del paso
     */
    public final void setTitulo(final String pTitulo) {
        titulo = pTitulo;
    }

    /**
     * Indica indica que los elementos del paso no se pueden modificar
     * (formularios, anexos, etc.
     *
     * @return indica que los elementos del paso no se pueden modificar
     *         (formularios, anexos, etc
     */
    public final TypeSiNo getSoloLectura() {
        return soloLectura;
    }

    /**
     * Indica tipo de paso.
     *
     * @return tipo de paso
     */
    public final TypePaso getTipo() {
        return tipo;
    }

    /**
     * Indica tipo de paso.
     *
     * @param pTipo
     *            tipo de paso
     */
    protected final void setTipo(final TypePaso pTipo) {
        tipo = pTipo;
    }

    /**
     * Indica indica que los elementos del paso no se pueden modificar
     * (formularios, anexos, etc.
     *
     * @param pSoloLectura
     *            indica que los elementos del paso no se pueden modificar
     *            (formularios, anexos, etc
     */
    public final void setSoloLectura(final TypeSiNo pSoloLectura) {
        soloLectura = pSoloLectura;
    }

    /**
     * Método para Prints the de la clase DetallePaso.
     *
     * @return el string
     */
    public abstract String print();

    /**
     * Indica informacion del paso.
     *
     * @return info del paso
     */
    public final String getInfo() {
        return info;
    }

    /**
     * Indica informacion del paso.
     *
     * @param pInfo
     *            info del paso
     */
    public final void setInfo(final String pInfo) {
        info = pInfo;
    }

}
