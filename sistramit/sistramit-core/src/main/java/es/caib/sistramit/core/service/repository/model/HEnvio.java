package es.caib.sistramit.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.service.model.system.Envio;
import es.caib.sistramit.core.service.model.system.types.TypeEnvio;

/**
 * Mapeo tabla STT_AVISOS.
 */

@Entity
@Table(name = "STT_AVISOS")
@SuppressWarnings("serial")
public final class HEnvio implements IModelApi {

	/** Atributo codigo. */
	@Id
	@SequenceGenerator(name = "STT_AVISOS_SEQ", sequenceName = "STT_AVISOS_SEQ", allocationSize = ConstantesNumero.N1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_AVISOS_SEQ")
	@Column(name = "AVI_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N20, scale = 0)
	private Long codigo;

	/** Atributo fecha. */
	@Column(name = "AVI_FECAVI")
	private Date fechaCreacion;

	/** Atributo tipo. */
	@Column(name = "AVI_TIPO", length = 1)
	private String tipo;

	/** Atributo destino. */
	@Column(name = "AVI_DESTIN", length = 1000)
	private String destino;

	/** Atributo titulo. */
	@Column(name = "AVI_TITUL", length = 1000)
	private String titulo;

	/** Atributo mensaje. */
	@Column(name = "AVI_MENSA", length = 4000)
	private String mensaje;

	/** Atributo fecha. */
	@Column(name = "AVI_FECENV")
	private Date fechaEnvio;

	/** Atributo fecha de reintento. */
	@Column(name = "AVI_FECREI")
	private Date fechaReintento;

	/** Atributo mensaje de error. */
	@Column(name = "AVI_ERROR", length = 4000)
	private String mensajeError;

	/**
	 * Método de acceso a codigo.
	 *
	 * @return codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Método para establecer codigo.
	 *
	 * @param codigo codigo a establecer
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método de acceso a fechaCreacion.
	 *
	 * @return fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Método para establecer fechaCreacion.
	 *
	 * @param fechaCreacion fechaCreacion a establecer
	 */
	public void setFechaCreacion(final Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param tipo tipo a establecer
	 */
	public void setTipo(final String tipo) {
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
	 * Método de acceso a fechaEnvio.
	 *
	 * @return fechaEnvio
	 */
	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	/**
	 * Método para establecer fechaEnvio.
	 *
	 * @param fechaEnvio fechaEnvio a establecer
	 */
	public void setFechaEnvio(final Date fechaEnvio) {
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

	/**
	 * From model
	 *
	 * @param m
	 * @return
	 */
	public static HEnvio fromModel(final Envio m) {
		final HEnvio h = new HEnvio();
		h.setCodigo(m.getCodigo());
		h.setFechaCreacion(m.getFechaCreacion());
		h.setTipo(m.getTipo().toString());
		h.setDestino(m.getDestino());
		h.setTitulo(m.getTitulo());
		h.setMensaje(m.getMensaje());
		return h;
	}

	public Envio toModel() {
		final Envio m = new Envio();
		m.setCodigo(this.getCodigo());
		m.setFechaCreacion(this.getFechaCreacion());
		m.setTipo(TypeEnvio.fromString(this.getTipo()));
		m.setDestino(this.getDestino());
		m.setTitulo(this.getTitulo());
		m.setMensaje(this.getMensaje());
		return m;
	}
}
