package es.caib.sistramit.core.service.model.flujo;

import es.caib.sistramit.core.service.model.flujo.types.TypeEntregaEstado;

import java.io.Serializable;

/**
 * Entrega trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class EntregaTramite implements Serializable {

    /** Id sesion tramitacion. */
    private String idSesionTramitacion;

    /** Id entidad. */
    private String idEntidad;

    /** Estado entrega */
    private TypeEntregaEstado estado;

    /** Id sesión envío. */
    private String idSesionEnvio;

    /**
     * Obtiene el id de la sesión de tramitación.
     * @return idSesionTramitacion
     */
    public String getIdSesionTramitacion() {
        return idSesionTramitacion;
    }

    /**
     * Establece el id de la sesión de tramitación.
     * @param idSesionTramitacion idSesionTramitacion
     */
    public void setIdSesionTramitacion(String idSesionTramitacion) {
        this.idSesionTramitacion = idSesionTramitacion;
    }

    /**
     * Obtiene el id de la entidad.
     * @return idEntidad
     */
    public String getIdEntidad() {
        return idEntidad;
    }

    /**
     * Establece el id de la entidad.
     * @param idEntidad idEntidad
     */
    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }

    /**
     * Obtiene el estado de la entrega.
     * @return estado
     */
    public TypeEntregaEstado getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la entrega.
     * @param estado estado
     */
    public void setEstado(TypeEntregaEstado estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el id de la sesión de envío.
     * @return idSesionEnvio
     */
    public String getIdSesionEnvio() {
        return idSesionEnvio;
    }

    /**
     * Establece el id de la sesión de envío.
     * @param idSesionEnvio idSesionEnvio
     */
    public void setIdSesionEnvio(String idSesionEnvio) {
        this.idSesionEnvio = idSesionEnvio;
    }

}
