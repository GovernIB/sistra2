package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;

/**
 * Información del paso accesible desde el trámite: id, tipo y estado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class EstadoPersistenciaPasoTramite implements Serializable {
    /**
     * Id paso.
     */
    private String id;
    /**
     * Tipo paso.
     */
    private TypePaso tipo;
    /**
     * Estado.
     */
    private TypeEstadoPaso estado;

    /**
     * Atributo orden.
     */
    private int orden;

    /**
     * Método de acceso a id.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Método para establecer id.
     *
     * @param pId
     *            id a establecer
     */
    public void setId(final String pId) {
        id = pId;
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
     * Método de acceso a estado.
     *
     * @return estado
     */
    public TypeEstadoPaso getEstado() {
        return estado;
    }

    /**
     * Método para establecer estado.
     *
     * @param pEstado
     *            estado a establecer
     */
    public void setEstado(final TypeEstadoPaso pEstado) {
        estado = pEstado;
    }

    /**
     * Crea nueva instancia EstadoPersistenciaPasoTramite.
     *
     * @return EstadoPersistenciaPasoTramite
     */
    public static EstadoPersistenciaPasoTramite createNewEstadoPersistenciaPasoTramite() {
        return new EstadoPersistenciaPasoTramite();
    }

    /**
     * Obtiene el atributo orden de EstadoPersistenciaPasoTramite.
     *
     * @return el atributo orden
     */
    public int getOrden() {
        return orden;
    }

    /**
     * Asigna el atributo orden de EstadoPersistenciaPasoTramite.
     *
     * @param pOrden
     *            el nuevo valor para orden
     */
    public void setOrden(final int pOrden) {
        this.orden = pOrden;
    }
}
