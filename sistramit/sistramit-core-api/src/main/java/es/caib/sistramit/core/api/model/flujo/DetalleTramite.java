package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

/**
 * Detalle tramite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DetalleTramite implements Serializable {

	/**
	 * Id trámite.
	 */
	private String idTramite;

	/**
	 * Título trámite.
	 */
	private String titulo;

	/**
	 * Usuario autenticado.
	 */
	private UsuarioAutenticadoInfo usuario;

	/** Debug habilitado. */
	private boolean debug;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	public UsuarioAutenticadoInfo getUsuario() {
		return usuario;
	}

	public void setUsuario(final UsuarioAutenticadoInfo usuario) {
		this.usuario = usuario;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

}
