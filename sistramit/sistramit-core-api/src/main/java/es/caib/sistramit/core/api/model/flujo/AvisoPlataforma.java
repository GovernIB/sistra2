package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;

/**
 * Aviso plataforma.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AvisoPlataforma implements Serializable {

	/**
	 * Mensaje.
	 */
	private String mensaje;

	/**
	 * Bloquear acceso.
	 */
	private boolean bloquearAcceso;

	/**
	 * Constructor.
	 *
	 * @param pMensaje
	 *            Mensaje
	 * @param pBloquearAcceso
	 *            Bloquear acceso
	 */
	public AvisoPlataforma(final String pMensaje, final boolean pBloquearAcceso) {
		super();
		mensaje = pMensaje;
		bloquearAcceso = pBloquearAcceso;
	}

	/**
	 * Constructor.
	 *
	 * @param pMensaje
	 *            Mensaje
	 * @param pBloquearAcceso
	 *            Bloquear acceso trámite
	 * @return Aviso plataforma
	 */
	public static AvisoPlataforma createNewAvisoPlataforma(final String pMensaje, final boolean pBloquearAcceso) {
		return new AvisoPlataforma(pMensaje, pBloquearAcceso);
	}

	/**
	 * Constructor.
	 */
	public AvisoPlataforma() {
		super();
	}

	/**
	 * Método de acceso a mensaje.
	 *
	 * @return mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Método para establecer mensaje.
	 *
	 * @param pMensaje
	 *            mensaje a establecer
	 */
	public void setMensaje(final String pMensaje) {
		mensaje = pMensaje;
	}

	/**
	 * Método de acceso a bloquearAcceso.
	 *
	 * @return bloquearAcceso
	 */
	public boolean isBloquearAcceso() {
		return bloquearAcceso;
	}

	/**
	 * Método para establecer bloquearAcceso.
	 *
	 * @param pBloquearAcceso
	 *            bloquearAcceso a establecer
	 */
	public void setBloquearAcceso(final boolean pBloquearAcceso) {
		bloquearAcceso = pBloquearAcceso;
	}

}
