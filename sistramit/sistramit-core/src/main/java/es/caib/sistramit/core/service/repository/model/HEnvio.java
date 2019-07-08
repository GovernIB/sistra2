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
	@Column(name = "AVI_TIPO")
	private String tipo;

	/** Atributo destino. */
	@Column(name = "AVI_DESTIN")
	private String destino;

	/** Atributo titulo. */
	@Column(name = "AVI_TITUL")
	private String titulo;

	/** Atributo mensaje. */
	@Column(name = "AVI_MENSA")
	private String mensaje;

	/** Atributo fecha. */
	@Column(name = "AVI_FECENV")
	private Date fechaEnvio;

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
	 * @param codigo
	 *                   codigo a establecer
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
	 * @param fechaCreacion
	 *                          fechaCreacion a establecer
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
	 * @param tipo
	 *                 tipo a establecer
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
	 * @param fechaEnvio
	 *                       fechaEnvio a establecer
	 */
	public void setFechaEnvio(final Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public static HEnvio fromModel(final Envio m) {
		final HEnvio h = new HEnvio();
		h.setFechaCreacion(new Date());
		h.setTipo(m.getTipo().toString());
		h.setDestino(m.getDestino());
		h.setTitulo(m.getTitulo());
		h.setMensaje(m.getMensaje());
		return h;
	}

}
