package es.caib.sistrages.core.api.model;

import java.util.Date;

import es.caib.sistrages.core.api.model.types.TypeMensajeAviso;

/**
 *
 * Mensaje aviso.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class MensajeAviso extends ModelApi {

	/** Id. */
	private Long id;

	/** Descripción. */
	private Traducciones descripcion;

	/** Tipo. */
	private TypeMensajeAviso tipo;

	/** Activo. */
	private boolean activo;

	/** Fecha inicio. */
	private Date fechaInicio;

	/** Fecha fin. */
	private Date fechaFin;

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
	 * @return the descripcion
	 */
	public Traducciones getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(Traducciones descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the tipo
	 */
	public TypeMensajeAviso getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final TypeMensajeAviso tipo) {
		this.tipo = tipo;
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
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio
	 *            the fechaInicio to set
	 */
	public void setFechaInicio(final Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 *            the fechaFin to set
	 */
	public void setFechaFin(final Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}
