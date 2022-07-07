package es.caib.sistramit.core.service.model.system;

import java.util.Date;

import es.caib.sistramit.core.service.model.system.types.TypeEnvioAviso;

/**
 * Envio.
 *
 * @author Indra
 *
 */
public class EnvioAviso {

	/** Codigo. **/
	private Long codigo;

	/** Atributo fecha. */
	private Date fechaCreacion;

	/** Tipo envío. */
	private TypeEnvioAviso tipo;

	/** Destino (según tipo envío). */
	private String destino;

	/** Título. */
	private String titulo;

	/** Mensaje. */
	private String mensaje;

	/** Fecha de envio. */
	private Date fechaEnvio;

	/** Fecha de reintento. */
	private Date fechaReintento;

	/** Mensaje de error. */
	private String mensajeError;

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public TypeEnvioAviso getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param tipo tipo a establecer
	 */
	public void setTipo(final TypeEnvioAviso tipo) {
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
	 * @param destino destino a establecer
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
	 * @param titulo titulo a establecer
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
	 * @param mensaje mensaje a establecer
	 */
	public void setMensaje(final String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the codigo
	 */
	public final Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public final void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the fechaCreacion
	 */
	public final Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public final void setFechaCreacion(final Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the fechaEnvio
	 */
	public final Date getFechaEnvio() {
		return fechaEnvio;
	}

	/**
	 * @param fechaEnvio the fechaEnvio to set
	 */
	public final void setFechaEnvio(final Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	/**
	 * @return the fechaReintento
	 */
	public final Date getFechaReintento() {
		return fechaReintento;
	}

	/**
	 * @param fechaReintento the fechaReintento to set
	 */
	public final void setFechaReintento(final Date fechaReintento) {
		this.fechaReintento = fechaReintento;
	}

	/**
	 * @return the mensajeError
	 */
	public final String getMensajeError() {
		return mensajeError;
	}

	/**
	 * @param mensajeError the mensajeError to set
	 */
	public final void setMensajeError(final String mensajeError) {
		this.mensajeError = mensajeError;
	}

}
