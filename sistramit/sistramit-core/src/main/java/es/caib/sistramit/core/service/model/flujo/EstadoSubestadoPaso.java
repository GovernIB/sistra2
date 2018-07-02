package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeSubestadoPaso;

/**
 * Agrupa estado y subestado paso.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class EstadoSubestadoPaso implements Serializable {

    /**
     * Constructor.
     */
    public EstadoSubestadoPaso() {
        super();
    }

    /**
     * Constructor.
     *
     * @param pEstado
     *            Estado paso
     * @param pSubestado
     *            Subestado paso
     */
    public EstadoSubestadoPaso(final TypeEstadoPaso pEstado,
            final TypeSubestadoPaso pSubestado) {
        super();
        estado = pEstado;
        subestado = pSubestado;
    }

    /**
     * Estado paso.
     */
    private TypeEstadoPaso estado;

    /**
     * Subestado paso.
     */
    private TypeSubestadoPaso subestado;

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
     * Método de acceso a subestado.
     *
     * @return subestado
     */
    public TypeSubestadoPaso getSubestado() {
        return subestado;
    }

    /**
     * Método para establecer subestado.
     *
     * @param pSubestado
     *            subestado a establecer
     */
    public void setSubestado(final TypeSubestadoPaso pSubestado) {
        subestado = pSubestado;
    }

}
