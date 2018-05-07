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
	 * Título trámite.
	 */
	private String titulo;

	/**
	 * Usuario autenticado.
	 */
	private UsuarioAutenticadoInfo usuario;

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

}
