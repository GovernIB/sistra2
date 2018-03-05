package es.caib.sistrages.core.api.model;

/**
 *
 * Rol.
 *
 * @author Indra
 *
 */
public class Rol {

	/** Id. */
	private Long id;

	/** Código. */
	private String codigo;

	/** Descripción. */
	private String descripcion;

	/** Area */
	private Area area;

	/** Alta. **/
	private boolean alta;

	/** Modificacion. **/
	private boolean modificacion;

	/** Consulta. **/
	private boolean consulta;

	/** Helpdesk. **/
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
