package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * Bulletins.
 *
 * @author indra
 *
 */
public class RTipoProcedimiento {

	/**
	 * Identificador
	 */
	private String identificador;

	/** enlace. **/
	private String descripcion;

	/** codigo **/
	private Long codigo;

	private RLink link_entidad;

	private Long entidad;

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the link_entidad
	 */
	public RLink getLink_entidad() {
		return link_entidad;
	}

	/**
	 * @param link_entidad the link_entidad to set
	 */
	public void setLink_entidad(RLink link_entidad) {
		this.link_entidad = link_entidad;
	}

	/**
	 * @return the entidad
	 */
	public Long getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public void setEntidad(Long entidad) {
		this.entidad = entidad;
	}
}
