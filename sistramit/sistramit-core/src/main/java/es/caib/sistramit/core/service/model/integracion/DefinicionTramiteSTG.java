package es.caib.sistramit.core.service.model.integracion;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistrages.rest.api.interna.RVersionTramite;

/**
 * Definición trámite proveniente del STG.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DefinicionTramiteSTG implements Serializable {

    /** Fecha recuperacion. */
    private Date fechaRecuperacion;

    /** Definicion. */
    private RVersionTramite definicionVersion;

    /**
     * Constructor.
     *
     * @param fechaRecuperacion
     *            Fecha recuperacion.
     * @param definicionVersion
     *            Definicion version
     */
    public DefinicionTramiteSTG(Date fechaRecuperacion,
            RVersionTramite definicionVersion) {
        super();
        this.fechaRecuperacion = fechaRecuperacion;
        this.definicionVersion = definicionVersion;
    }

    /**
     * Método de acceso a fechaRecuperacion.
     *
     * @return fechaRecuperacion
     */
    public Date getFechaRecuperacion() {
        return fechaRecuperacion;
    }

    /**
     * Método para establecer fechaRecuperacion.
     *
     * @param fechaRecuperacion
     *            fechaRecuperacion a establecer
     */
    public void setFechaRecuperacion(Date fechaRecuperacion) {
        this.fechaRecuperacion = fechaRecuperacion;
    }

    /**
     * Método de acceso a definicionVersion.
     *
     * @return definicionVersion
     */
    public RVersionTramite getDefinicionVersion() {
        return definicionVersion;
    }

    /**
     * Método para establecer definicionVersion.
     *
     * @param definicionVersion
     *            definicionVersion a establecer
     */
    public void setDefinicionVersion(RVersionTramite definicionVersion) {
        this.definicionVersion = definicionVersion;
    }

}
