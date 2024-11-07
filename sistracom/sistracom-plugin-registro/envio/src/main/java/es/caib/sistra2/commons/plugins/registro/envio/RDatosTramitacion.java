package es.caib.sistra2.commons.plugins.registro.envio;

/**
 * Datos tramitacion realizada.
 */

public class RDatosTramitacion {

    /**
     * Id sesión tramitación.
     */
    private String idSesionTramitacion;

    /**
     * Id trámite.
     */
    private String idTramite;

    /**
     * Version trámite.
     */
    private int versionTramite;

    /**
     * Identificador procedimiento.
     */
    private String idProcedimiento;

    /**
     * Identificador procedimiento.
     */
    private String idProcedimientoSIA;

    /**
     *  Obtiene el id de la sesión de tramitación
     * @return
     */
    public String getIdSesionTramitacion() {
        return idSesionTramitacion;
    }

    /**
     * Establece el id de la sesión de tramitación
     * @param idSesionTramitacion
     */
    public void setIdSesionTramitacion(String idSesionTramitacion) {
        this.idSesionTramitacion = idSesionTramitacion;
    }

    /**
     * Obtiene el id del trámite
     *
     * @return idTramite
     */
    public String getIdTramite() {
        return idTramite;
    }

    /**
     * Establece el id del trámite
     *
     * @param idTramite el id del trámite
     */
    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    /**
     * Obtiene la versión del trámite
     *
     * @return versionTramite
     */
    public int getVersionTramite() {
        return versionTramite;
    }

    /**
     * Establece la versión del trámite
     *
     * @param versionTramite la versión del trámite
     */
    public void setVersionTramite(int versionTramite) {
        this.versionTramite = versionTramite;
    }

    /**
     * Obtiene el identificador del procedimiento
     *
     * @return idProcedimiento
     */
    public String getIdProcedimiento() {
        return idProcedimiento;
    }

    /**
     * Establece el identificador del procedimiento
     *
     * @param idProcedimiento el identificador del procedimiento
     */
    public void setIdProcedimiento(String idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }

	/**
	 * Obtiene el id procedimiento codigo sia
	 * @return the idProcedimientoSIA
	 */
	public String getIdProcedimientoSIA() {
		return idProcedimientoSIA;
	}

	/**
	 * Establece el id procedimiento codigo sia
	 *
	 * @param idProcedimientoSIA the idProcedimientoSIA to set
	 */
	public void setIdProcedimientoSIA(String idProcedimientoSIA) {
		this.idProcedimientoSIA = idProcedimientoSIA;
	}


}
