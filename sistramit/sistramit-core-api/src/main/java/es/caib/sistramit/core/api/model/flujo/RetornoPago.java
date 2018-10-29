package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

/**
 * Datos necesarios para retorno pago.
 *
 * @author Indra
 *
 */
public class RetornoPago implements ModelApi {

    /** Id sesion tramitacion. */
    private String idSesionTramitacion;

    /** Id paso. */
    private String idPaso;

    /** Id pago. */
    private String idPago;

    /** Usuario. */
    private UsuarioAutenticadoInfo usuario;

    /**
     * Método de acceso a idSesionTramitacion.
     * 
     * @return idSesionTramitacion
     */
    public String getIdSesionTramitacion() {
        return idSesionTramitacion;
    }

    /**
     * Método para establecer idSesionTramitacion.
     * 
     * @param idSesionTramitacion
     *            idSesionTramitacion a establecer
     */
    public void setIdSesionTramitacion(String idSesionTramitacion) {
        this.idSesionTramitacion = idSesionTramitacion;
    }

    /**
     * Método de acceso a idPaso.
     * 
     * @return idPaso
     */
    public String getIdPaso() {
        return idPaso;
    }

    /**
     * Método para establecer idPaso.
     * 
     * @param idPaso
     *            idPaso a establecer
     */
    public void setIdPaso(String idPaso) {
        this.idPaso = idPaso;
    }

    /**
     * Método de acceso a idPago.
     * 
     * @return idPago
     */
    public String getIdPago() {
        return idPago;
    }

    /**
     * Método para establecer idPago.
     * 
     * @param idPago
     *            idPago a establecer
     */
    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    /**
     * Método de acceso a usuario.
     * 
     * @return usuario
     */
    public UsuarioAutenticadoInfo getUsuario() {
        return usuario;
    }

    /**
     * Método para establecer usuario.
     * 
     * @param usuario
     *            usuario a establecer
     */
    public void setUsuario(UsuarioAutenticadoInfo usuario) {
        this.usuario = usuario;
    }

}
