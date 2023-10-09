package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * PlatTramitElectronica.
 *
 * @author indra
 *
 */
public class RPlatTramitElectronica {

	/**
	 * Identificador
	 */
	private String identificador;

	/** enlace. **/
    private String descripcion;

	/** codigo **/
	private Long codigo;

    /**
     * Url
     */
    private String urlAcceso;

	private RLink link_codEntidad;

	private Long codEntidad;

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
	 * @return the urlAcceso
	 */
	public String getUrlAcceso() {
		return urlAcceso;
	}

	/**
	 * @param urlAcceso the urlAcceso to set
	 */
	public void setUrlAcceso(String urlAcceso) {
		this.urlAcceso = urlAcceso;
	}

	/**
	 * @return the link_codEntidad
	 */
	public RLink getLink_codEntidad() {
		return link_codEntidad;
	}

	/**
	 * @param link_codEntidad the link_codEntidad to set
	 */
	public void setLink_codEntidad(RLink link_codEntidad) {
		this.link_codEntidad = link_codEntidad;
	}

	/**
	 * @return the codEntidad
	 */
	public Long getCodEntidad() {
		return codEntidad;
	}

	/**
	 * @param codEntidad the codEntidad to set
	 */
	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}
}
