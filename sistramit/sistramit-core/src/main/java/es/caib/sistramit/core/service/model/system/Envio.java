package es.caib.sistramit.core.service.model.system;

import es.caib.sistramit.core.service.model.system.types.TypeEnvio;

/**
 * Envio.
 *
 * @author Indra
 *
 */
public class Envio {

	/** Tipo envío. */
	private TypeEnvio tipo;

	/** Destino (según tipo envío). */
	private String destino;

	/** Título. */
	private String titulo;

	/** Mensaje. */
	private String mensaje;

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public TypeEnvio getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param tipo
	 *                 tipo a establecer
	 */
	public void setTipo(final TypeEnvio tipo) {
		this.tipo = tipo;
	}

	/**
	 * Método de acceso a destino.
	 *
	 * @return destino
	 */
	public String getDestino() {
		return destino;
	}

	/**
	 * Método para establecer destino.
	 *
	 * @param destino
	 *                    destino a establecer
	 */
	public void setDestino(final String destino) {
		this.destino = destino;
	}

	/**
	 * Método de acceso a titulo.
	 *
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Método para establecer titulo.
	 *
	 * @param titulo
	 *                   titulo a establecer
	 */
	public void setTitulo(final String titulo) {
		this.titulo = titulo;
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
	 * @param mensaje
	 *                    mensaje a establecer
	 */
	public void setMensaje(final String mensaje) {
		this.mensaje = mensaje;
	}

}
