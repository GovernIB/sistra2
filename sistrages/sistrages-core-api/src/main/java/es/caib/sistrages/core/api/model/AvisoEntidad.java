package es.caib.sistrages.core.api.model;

import java.util.Date;

import es.caib.sistrages.core.api.model.types.TypeAvisoEntidad;

/**
 *
 * Mensaje aviso.
 *
 * @author Indra
 *
 */

public class AvisoEntidad extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Id. */
	private Long codigo;

	/** Descripción. */
	private Literal mensaje;

	/** Tipo. */
	private TypeAvisoEntidad tipo;

	/** Activo. */
	private boolean bloqueado;

	/** Fecha inicio. */
	private Date fechaInicio;

	/** Fecha fin. */
	private Date fechaFin;

	/** Lista serializada con códigos de trámites */
	private String listaSerializadaTramites;

	public AvisoEntidad() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descripcion
	 */
	public Literal getMensaje() {
		return mensaje;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setMensaje(final Literal descripcion) {
		this.mensaje = descripcion;
	}

	/**
	 * @return the tipo
	 */
	public TypeAvisoEntidad getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final TypeAvisoEntidad tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the activo
	 */
	public boolean isBloqueado() {
		return bloqueado;
	}

	/**
	 * @param activo
	 *            the activo to set
	 */
	public void setBloqueado(final boolean activo) {
		this.bloqueado = activo;
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

	/**
	 * Obtiene el valor de listaSerializadaTramites.
	 *
	 * @return el valor de listaSerializadaTramites
	 */
	public String getListaSerializadaTramites() {
		return listaSerializadaTramites;
	}

	/**
	 * Establece el valor de listaSerializadaTramites.
	 *
	 * @param listaSerializadaTramites
	 *            el nuevo valor de listaSerializadaTramites
	 */
	public void setListaSerializadaTramites(final String listaSerializadaTramites) {
		this.listaSerializadaTramites = listaSerializadaTramites;
	}

}
