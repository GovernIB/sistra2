package es.caib.sistra2.commons.plugins.registro.envio;

public class REnvioRemoto {

    public REnvioRemoto() {
        // Constructor vacio
    }

    /**
     * Id de sesion de envio
     */
    private String idEnvio;

    /**
     * Datos tramitación.
     */
    private RDatosTramitacion datosTramitacion;

    /**
     * Asiento registral
     */
    private RAsientoRegistral asiento;

    /**
     * Obtiene el id de la sesión de envío
     *
     * @return idEnvio
     */
    public String getIdEnvio() {
        return idEnvio;
    }

    /**
     * Establece el id de la sesión de envío
     *
     * @param idEnvio el id de la sesión de envío
     */
    public void setIdEnvio(final String idEnvio) {
        this.idEnvio = idEnvio;
    }

    /**
     * Obtiene el asiento registral
     *
     * @return asiento
     */
    public final RAsientoRegistral getAsiento() {
        return asiento;
    }

    /**
     * Establece el asiento registral
     *
     * @param asiento el asiento registral
     */
    public final void setAsiento(RAsientoRegistral asiento) {
        this.asiento = asiento;
    }

    /**
     * Obtiene los datos de tramitación
     *
     * @return datosTramitacion
     */
    public RDatosTramitacion getDatosTramitacion() {
        return datosTramitacion;
    }

    /**
     * Establece los datos de tramitación
     *
     * @param datosTramitacion los datos de tramitación
     */
    public void setDatosTramitacion(RDatosTramitacion datosTramitacion) {
        this.datosTramitacion = datosTramitacion;
    }
}
