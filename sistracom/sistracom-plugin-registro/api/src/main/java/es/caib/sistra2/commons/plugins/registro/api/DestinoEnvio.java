package es.caib.sistra2.commons.plugins.registro.api;

import java.io.Serializable;

/**
 * Datos destino envío.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DestinoEnvio implements Serializable {

	/** Id entidad. */
	private String idEntidad;

	/** Id envio remoto. */
	private String idEnvioRemoto;

	/** Url. */
	private String url;

	/** Usuario. */
	private String usuario;

	/** Password. */
	private String password;

	/** Timeout (segs). */
	private int timeoutSecs;

	/**
	 * Método de acceso a url.
	 *
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Método para establecer url.
	 *
	 * @param url
	 *                url a establecer
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * Método de acceso a usuario.
	 *
	 * @return usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Método para establecer usuario.
	 *
	 * @param usuario
	 *                    usuario a establecer
	 */
	public void setUsuario(final String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Método de acceso a password.
	 *
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Método para establecer password.
	 *
	 * @param password
	 *                     password a establecer
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Método de acceso a timeoutSecs.
	 *
	 * @return timeoutSecs
	 */
	public int getTimeoutSecs() {
		return timeoutSecs;
	}

	/**
	 * Método para establecer timeoutSecs.
	 *
	 * @param timeoutSecs
	 *                        timeoutSecs a establecer
	 */
	public void setTimeoutSecs(final int timeoutSecs) {
		this.timeoutSecs = timeoutSecs;
	}

	/**
	 * Método de acceso a idEntidad.
	 * 
	 * @return idEntidad
	 */
	public String getIdEntidad() {
		return idEntidad;
	}

	/**
	 * Método para establecer idEntidad.
	 * 
	 * @param idEntidad
	 *                      idEntidad a establecer
	 */
	public void setIdEntidad(final String idEntidad) {
		this.idEntidad = idEntidad;
	}

	/**
	 * Método de acceso a idEnvioRemoto.
	 * 
	 * @return idEnvioRemoto
	 */
	public String getIdEnvioRemoto() {
		return idEnvioRemoto;
	}

	/**
	 * Método para establecer idEnvioRemoto.
	 * 
	 * @param idEnvioRemoto
	 *                          idEnvioRemoto a establecer
	 */
	public void setIdEnvioRemoto(final String idEnvioRemoto) {
		this.idEnvioRemoto = idEnvioRemoto;
	}

}
