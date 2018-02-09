package es.caib.sistra2.stg.core.api.model;

import java.util.Date;

/**
 *
 * Trámite.
 *
 * @author Indra
 *
 */
public class Tramite {

	/**
	 * Id.
	 */
	private Long id;

	/**
	 * Codigo.
	 */
	private String codigo;

	/**
	 * Descripcion.
	 */
	private String descripcion;

	/**
	 * Activo
	 */
	private boolean activo;

	/**
	 * Bloqueada
	 */
	private boolean bloqueada;

	/**
	 * Modificacion
	 */
	private Date modificacion;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo
	 *            the activo to set
	 */
	public void setActivo(final boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the bloqueada
	 */
	public boolean isBloqueada() {
		return bloqueada;
	}

	/**
	 * @param bloqueada
	 *            the bloqueada to set
	 */
	public void setBloqueada(final boolean bloqueada) {
		this.bloqueada = bloqueada;
	}

	/**
	 * @return the modificacion
	 */
	public Date getModificacion() {
		return modificacion;
	}

	/**
	 * @param modificacion
	 *            the modificacion to set
	 */
	public void setModificacion(final Date modificacion) {
		this.modificacion = modificacion;
	}

}
