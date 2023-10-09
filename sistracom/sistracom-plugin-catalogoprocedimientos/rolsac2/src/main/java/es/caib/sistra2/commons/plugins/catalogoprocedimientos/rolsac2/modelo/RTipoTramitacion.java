package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * TipoTramitacion.
 *
 * @author indra
 *
 */
public class RTipoTramitacion {

	/**
	 * Identificador
	 */
	private String tramiteId;

	/** enlace. **/
    private String descripcion;

	/** codigo **/
	private Long codigo;

    /**
     * Tramitación presencial
     */
    private boolean tramitPresencial;

    /**
     * Tramitación electrónica
     */
    private boolean tramitElectronica;

    /**
     * Tramitacion telefonica
     */
    private boolean tramitTelefonica;

    /**
     * Url
     */
    private String url;

    /**
     * Fase procedimiento
     */
    private Integer faseProc;

    /**
     * URL tramitación
     */
    private String urlTramitacion;

    /**
     * Código plataforma tramitación
     */
	private RLink link_codPlatTramitacion;

	private Long codPlatTramitacion;

    /**
     * Trámite Versión
     */
    private Integer tramiteVersion;

    /**
     * Trámite parámetros
     */
    private String tramiteParametros;

    /**
     * Indica si es una plantilla
     **/
    private boolean plantilla;

	private RLink link_entidad;

	private Long entidad;

	/**
	 * @return the tramiteId
	 */
	public String getTramiteId() {
		return tramiteId;
	}

	/**
	 * @param tramiteId the tramiteId to set
	 */
	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
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
	 * @return the tramitPresencial
	 */
	public boolean isTramitPresencial() {
		return tramitPresencial;
	}

	/**
	 * @param tramitPresencial the tramitPresencial to set
	 */
	public void setTramitPresencial(boolean tramitPresencial) {
		this.tramitPresencial = tramitPresencial;
	}

	/**
	 * @return the tramitElectronica
	 */
	public boolean isTramitElectronica() {
		return tramitElectronica;
	}

	/**
	 * @param tramitElectronica the tramitElectronica to set
	 */
	public void setTramitElectronica(boolean tramitElectronica) {
		this.tramitElectronica = tramitElectronica;
	}

	/**
	 * @return the tramitTelefonica
	 */
	public boolean isTramitTelefonica() {
		return tramitTelefonica;
	}

	/**
	 * @param tramitTelefonica the tramitTelefonica to set
	 */
	public void setTramitTelefonica(boolean tramitTelefonica) {
		this.tramitTelefonica = tramitTelefonica;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the faseProc
	 */
	public Integer getFaseProc() {
		return faseProc;
	}

	/**
	 * @param faseProc the faseProc to set
	 */
	public void setFaseProc(Integer faseProc) {
		this.faseProc = faseProc;
	}

	/**
	 * @return the urlTramitacion
	 */
	public String getUrlTramitacion() {
		return urlTramitacion;
	}

	/**
	 * @param urlTramitacion the urlTramitacion to set
	 */
	public void setUrlTramitacion(String urlTramitacion) {
		this.urlTramitacion = urlTramitacion;
	}

	/**
	 * @return the link_codPlatTramitacion
	 */
	public RLink getLink_codPlatTramitacion() {
		return link_codPlatTramitacion;
	}

	/**
	 * @param link_codPlatTramitacion the link_codPlatTramitacion to set
	 */
	public void setLink_codPlatTramitacion(RLink link_codPlatTramitacion) {
		this.link_codPlatTramitacion = link_codPlatTramitacion;
	}

	/**
	 * @return the codPlatTramitacion
	 */
	public Long getCodPlatTramitacion() {
		return codPlatTramitacion;
	}

	/**
	 * @param codPlatTramitacion the codPlatTramitacion to set
	 */
	public void setCodPlatTramitacion(Long codPlatTramitacion) {
		this.codPlatTramitacion = codPlatTramitacion;
	}

	/**
	 * @return the tramiteVersion
	 */
	public Integer getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion the tramiteVersion to set
	 */
	public void setTramiteVersion(Integer tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * @return the tramiteParametros
	 */
	public String getTramiteParametros() {
		return tramiteParametros;
	}

	/**
	 * @param tramiteParametros the tramiteParametros to set
	 */
	public void setTramiteParametros(String tramiteParametros) {
		this.tramiteParametros = tramiteParametros;
	}

	/**
	 * @return the plantilla
	 */
	public boolean isPlantilla() {
		return plantilla;
	}

	/**
	 * @param plantilla the plantilla to set
	 */
	public void setPlantilla(boolean plantilla) {
		this.plantilla = plantilla;
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
