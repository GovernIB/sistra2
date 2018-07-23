package es.caib.sistramit.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistra2.commons.utils.ConstantesNumero;

/**
 * Mapeo tabla STT_FICPTR.
 */

@Entity
@Table(name = "STT_FICPTR")
@SuppressWarnings("serial")
public final class HFichero implements IModelApi {

	/** Atributo codigo. */
	@Id
	@SequenceGenerator(name = "STT_FICPTR_SEQ", sequenceName = "STT_FICPTR_SEQ", allocationSize = ConstantesNumero.N1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_FICPTR_SEQ")
	@Column(name = "FIC_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N10, scale = 0)
	private Long codigo;

	/** Atributo clave. */
	@Column(name = "FIC_CLAVE")
	private String clave;

	/** Atributo nombre. */
	@Column(name = "FIC_NOMFIC")
	private String nombre;

	/** Atributo contenido. */
	@Lob
	@Column(name = "FIC_DATFIC")
	private byte[] contenido;

	/** Atributo borrar. */
	@Column(name = "FIC_BORRAR")
	private boolean borrar;

	/** Atributo codigo tramite (enlace directo para optimizar purga). */
	@Column(name = "FIC_CODTRP")
	private Long codigoTramite;

	/** Atributo codigo tramite (enlace directo para optimizar purga). */
	@Column(name = "FIC_FECHA ")
	private Date fechaCreacion;

	/** Atributo codigo tramite (enlace directo para optimizar purga). */
	@Column(name = "FIC_TAMANYO")
	private Long tamanyo;

	/**
	 * Obtiene el atributo codigo de HFichero.
	 *
	 * @return el atributo codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Asigna el atributo codigo de HFichero.
	 *
	 * @param pCodigo
	 *            el nuevo valor para codigo
	 */
	public void setCodigo(final Long pCodigo) {
		codigo = pCodigo;
	}

	/**
	 * Obtiene el atributo clave de HFichero.
	 *
	 * @return el atributo clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * Asigna el atributo clave de HFichero.
	 *
	 * @param pClave
	 *            el nuevo valor para clave
	 */
	public void setClave(final String pClave) {
		clave = pClave;
	}

	/**
	 * Obtiene el atributo nombre de HFichero.
	 *
	 * @return el atributo nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna el atributo nombre de HFichero.
	 *
	 * @param pNombre
	 *            el nuevo valor para nombre
	 */
	public void setNombre(final String pNombre) {
		nombre = pNombre;
	}

	/**
	 * Obtiene el atributo contenido de HFichero.
	 *
	 * @return el atributo contenido
	 */
	public byte[] getContenido() {
		return contenido;
	}

	/**
	 * Asigna el atributo contenido de HFichero.
	 *
	 * @param pContenido
	 *            el nuevo valor para contenido
	 */
	public void setContenido(final byte[] pContenido) {
		contenido = pContenido;
	}

	/**
	 * Método de acceso a borrar.
	 *
	 * @return borrar
	 */
	public boolean isBorrar() {
		return borrar;
	}

	/**
	 * Método para establecer borrar.
	 *
	 * @param pBorrar
	 *            borrar a establecer
	 */
	public void setBorrar(final boolean pBorrar) {
		borrar = pBorrar;
	}

	/**
	 * Método de acceso a codigoTramite.
	 *
	 * @return codigoTramite
	 */
	public Long getCodigoTramite() {
		return codigoTramite;
	}

	/**
	 * Método para establecer codigoTramite.
	 *
	 * @param pCodigoTramite
	 *            codigoTramite a establecer
	 */
	public void setCodigoTramite(final Long pCodigoTramite) {
		codigoTramite = pCodigoTramite;
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
	 * @param pFechaCreacion
	 *            fechaCreacion a establecer
	 */
	public void setFechaCreacion(final Date pFechaCreacion) {
		fechaCreacion = pFechaCreacion;
	}

	/**
	 * Método de acceso a tamanyo.
	 *
	 * @return el tamanyo
	 */
	public Long getTamanyo() {
		return tamanyo;
	}

	/**
	 * Método para settear el campo tamanyo.
	 *
	 * @param tamanyo
	 *            el tamanyo a settear
	 */
	public void setTamanyo(final Long tamanyo) {
		this.tamanyo = tamanyo;
	}

}
