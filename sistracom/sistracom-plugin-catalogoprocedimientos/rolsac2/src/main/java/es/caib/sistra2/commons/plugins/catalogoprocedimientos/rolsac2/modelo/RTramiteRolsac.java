package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

import java.util.Date;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RTramiteRolsac {

    private Integer fase;

    private Boolean tasaAsociada;

    private String requisitos;

    private String nombre;

    private String documentacion;

    private String observacion;

    private String terminoMaximo;

    private Date fechaPublicacion;

    private Date fechaInicio;

    private Date fechaCierre;

    /**
     * Tipos de presentacion: telematica, presencial o telefonica.
     **/
    private boolean tramitPresencial;

    private boolean tramitElectronica;

    private boolean tramitTelefonica;

	/** codigo **/
	private Long codigo;

	private RLink link_unidadAdministrativa;

	private Long unidadAdministrativa;

	private RLink link_procedimiento;

	private Long procedimiento;

	private RLink link_tipoTramitacion;

	private Long tipoTramitacion;

	private RLink link_plantillaSel;

	private Long plantillaSel;

	/**
	 * @return the fase
	 */
	public Integer getFase() {
		return fase;
	}

	/**
	 * @param fase the fase to set
	 */
	public void setFase(Integer fase) {
		this.fase = fase;
	}

	/**
	 * @return the tasaAsociada
	 */
	public Boolean getTasaAsociada() {
		return tasaAsociada;
	}

	/**
	 * @param tasaAsociada the tasaAsociada to set
	 */
	public void setTasaAsociada(Boolean tasaAsociada) {
		this.tasaAsociada = tasaAsociada;
	}

	/**
	 * @return the requisitos
	 */
	public String getRequisitos() {
		return requisitos;
	}

	/**
	 * @param requisitos the requisitos to set
	 */
	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the documentacion
	 */
	public String getDocumentacion() {
		return documentacion;
	}

	/**
	 * @param documentacion the documentacion to set
	 */
	public void setDocumentacion(String documentacion) {
		this.documentacion = documentacion;
	}

	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	/**
	 * @return the terminoMaximo
	 */
	public String getTerminoMaximo() {
		return terminoMaximo;
	}

	/**
	 * @param terminoMaximo the terminoMaximo to set
	 */
	public void setTerminoMaximo(String terminoMaximo) {
		this.terminoMaximo = terminoMaximo;
	}

	/**
	 * @return the fechaPublicacion
	 */
	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	/**
	 * @param fechaPublicacion the fechaPublicacion to set
	 */
	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaCierre
	 */
	public Date getFechaCierre() {
		return fechaCierre;
	}

	/**
	 * @param fechaCierre the fechaCierre to set
	 */
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
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
	 * @return the link_unidadAdministrativa
	 */
	public RLink getLink_unidadAdministrativa() {
		return link_unidadAdministrativa;
	}

	/**
	 * @param link_unidadAdministrativa the link_unidadAdministrativa to set
	 */
	public void setLink_unidadAdministrativa(RLink link_unidadAdministrativa) {
		this.link_unidadAdministrativa = link_unidadAdministrativa;
	}

	/**
	 * @return the unidadAdministrativa
	 */
	public Long getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	/**
	 * @param unidadAdministrativa the unidadAdministrativa to set
	 */
	public void setUnidadAdministrativa(Long unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

	/**
	 * @return the link_procedimiento
	 */
	public RLink getLink_procedimiento() {
		return link_procedimiento;
	}

	/**
	 * @param link_procedimiento the link_procedimiento to set
	 */
	public void setLink_procedimiento(RLink link_procedimiento) {
		this.link_procedimiento = link_procedimiento;
	}

	/**
	 * @return the procedimiento
	 */
	public Long getProcedimiento() {
		return procedimiento;
	}

	/**
	 * @param procedimiento the procedimiento to set
	 */
	public void setProcedimiento(Long procedimiento) {
		this.procedimiento = procedimiento;
	}

	/**
	 * @return the link_tipoTramitacion
	 */
	public RLink getLink_tipoTramitacion() {
		return link_tipoTramitacion;
	}

	/**
	 * @param link_tipoTramitacion the link_tipoTramitacion to set
	 */
	public void setLink_tipoTramitacion(RLink link_tipoTramitacion) {
		this.link_tipoTramitacion = link_tipoTramitacion;
	}

	/**
	 * @return the tipoTramitacion
	 */
	public Long getTipoTramitacion() {
		return tipoTramitacion;
	}

	/**
	 * @param tipoTramitacion the tipoTramitacion to set
	 */
	public void setTipoTramitacion(Long tipoTramitacion) {
		this.tipoTramitacion = tipoTramitacion;
	}

	/**
	 * @return the link_plantillaSel
	 */
	public RLink getLink_plantillaSel() {
		return link_plantillaSel;
	}

	/**
	 * @param link_plantillaSel the link_plantillaSel to set
	 */
	public void setLink_plantillaSel(RLink link_plantillaSel) {
		this.link_plantillaSel = link_plantillaSel;
	}

	/**
	 * @return the plantillaSel
	 */
	public Long getPlantillaSel() {
		return plantillaSel;
	}

	/**
	 * @param plantillaSel the plantillaSel to set
	 */
	public void setPlantillaSel(Long plantillaSel) {
		this.plantillaSel = plantillaSel;
	}
}
