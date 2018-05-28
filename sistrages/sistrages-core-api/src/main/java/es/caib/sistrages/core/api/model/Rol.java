package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeRoleUser;

/**
 * La clase Rol.
 */
@SuppressWarnings("serial")
public class Rol extends ModelApi {

	/** id. */
	private Long id;

	/** tipo. */
	private TypeRoleUser tipo;

	/** codigo. */
	private String codigo;

	/** descripcion. */
	private String descripcion;

	/** area. */
	private Area area;

	/** alta. */
	private boolean alta;

	/** modificacion. */
	private boolean modificacion;

	/** consulta. */
	private boolean consulta;

	/** helpdesk. */
	private boolean helpdesk;

	/** promocionar. */
	private boolean promocionar;

	/**
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id
	 *            el nuevo valor de id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de tipo.
	 *
	 * @return el valor de tipo
	 */
	public TypeRoleUser getTipo() {
		return tipo;
	}

	/**
	 * Establece el valor de tipo.
	 *
	 * @param tipo
	 *            el nuevo valor de tipo
	 */
	public void setTipo(final TypeRoleUser tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor de descripcion.
	 *
	 * @return el valor de descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor de descripcion.
	 *
	 * @param descripcion
	 *            el nuevo valor de descripcion
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor de area.
	 *
	 * @return el valor de area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * Establece el valor de area.
	 *
	 * @param area
	 *            el nuevo valor de area
	 */
	public void setArea(final Area area) {
		this.area = area;
	}

	/**
	 * Verifica si es alta.
	 *
	 * @return true, si es alta
	 */
	public boolean isAlta() {
		return alta;
	}

	/**
	 * Establece el valor de alta.
	 *
	 * @param alta
	 *            el nuevo valor de alta
	 */
	public void setAlta(final boolean alta) {
		this.alta = alta;
	}

	/**
	 * Verifica si es modificacion.
	 *
	 * @return true, si es modificacion
	 */
	public boolean isModificacion() {
		return modificacion;
	}

	/**
	 * Establece el valor de modificacion.
	 *
	 * @param modificacion
	 *            el nuevo valor de modificacion
	 */
	public void setModificacion(final boolean modificacion) {
		this.modificacion = modificacion;
	}

	/**
	 * Verifica si es consulta.
	 *
	 * @return true, si es consulta
	 */
	public boolean isConsulta() {
		return consulta;
	}

	/**
	 * Establece el valor de consulta.
	 *
	 * @param consulta
	 *            el nuevo valor de consulta
	 */
	public void setConsulta(final boolean consulta) {
		this.consulta = consulta;
	}

	/**
	 * Verifica si es helpdesk.
	 *
	 * @return true, si es helpdesk
	 */
	public boolean isHelpdesk() {
		return helpdesk;
	}

	/**
	 * Establece el valor de helpdesk.
	 *
	 * @param helpdesk
	 *            el nuevo valor de helpdesk
	 */
	public void setHelpdesk(final boolean helpdesk) {
		this.helpdesk = helpdesk;
	}

	/**
	 * @return the promocionar
	 */
	public boolean isPromocionar() {
		return promocionar;
	}

	/**
	 * @param promocionar
	 *            the promocionar to set
	 */
	public void setPromocionar(final boolean promocionar) {
		this.promocionar = promocionar;
	}
}
