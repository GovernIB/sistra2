package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeRolPermiso;

/**
 *
 * Rol.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class Rol extends ModelApi {

	/** Id. */
	private Long id;

	/** Tipo: R (Role) / U (Usuario) */
	private TypeRolPermiso tipo;

	/** Para Role el código de Role, para Usuario el NIF del usuario. */
	private String codigo;

	/** Descripción. */
	private String descripcion;

	/** Area */
	private Area area;

	/** Permiso alta-baja trámites. **/
	private boolean alta;

	/** Permiso modificacion trámites. **/
	private boolean modificacion;

	/** Permiso consulta trámites. **/
	private boolean consulta;

	/** Permiso helpdesk trámites. **/
	private boolean helpdesk;

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
	 * @return the tipo
	 */
	public TypeRolPermiso getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final TypeRolPermiso tipo) {
		this.tipo = tipo;
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
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(final Area area) {
		this.area = area;
	}

	/**
	 * @return the alta
	 */
	public boolean isAlta() {
		return alta;
	}

	/**
	 * @param alta
	 *            the alta to set
	 */
	public void setAlta(final boolean alta) {
		this.alta = alta;
	}

	/**
	 * @return the modificacion
	 */
	public boolean isModificacion() {
		return modificacion;
	}

	/**
	 * @param modificacion
	 *            the modificacion to set
	 */
	public void setModificacion(final boolean modificacion) {
		this.modificacion = modificacion;
	}

	/**
	 * @return the consulta
	 */
	public boolean isConsulta() {
		return consulta;
	}

	/**
	 * @param consulta
	 *            the consulta to set
	 */
	public void setConsulta(final boolean consulta) {
		this.consulta = consulta;
	}

	/**
	 * @return the helpdesk
	 */
	public boolean isHelpdesk() {
		return helpdesk;
	}

	/**
	 * @param helpdesk
	 *            the helpdesk to set
	 */
	public void setHelpdesk(final boolean helpdesk) {
		this.helpdesk = helpdesk;
	}

}
