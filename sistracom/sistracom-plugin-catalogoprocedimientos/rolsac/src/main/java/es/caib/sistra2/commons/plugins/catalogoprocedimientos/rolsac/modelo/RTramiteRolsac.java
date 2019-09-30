package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RTramiteRolsac {

	/** codigo. */
	private long codigo;

	/** Codi vuds **/
	private String codiVuds;

	/** Fecha de actualizacion **/
	private java.util.Calendar dataActualitzacio;

	/** Fecha de actualizacion vuds **/
	private String dataActualitzacioVuds;

	/** Fecha de caducidad **/
	private java.util.Calendar dataCaducitat;

	/** Fecha de publicacion **/
	private java.util.Calendar dataPublicacio;

	/** Fecha de inicio **/
	private java.util.Calendar dataInici;

	/** Fecha de cierre **/
	private java.util.Calendar dataTancament;

	/** Desc. codigo vuds. **/
	private String descCodiVuds;

	/** Documentacion. **/
	private String documentacion;

	/** Fase. **/
	private Integer fase;

	/** Id. tramite telematico. **/
	private String idTraTel;

	/** Lugar. **/
	private String lugar;

	/** Nombre. **/
	private String nombre;

	/** Observaciones. **/
	private String observaciones;

	/** Orden. **/
	private Long orden;

	/** Plazos. **/
	private String plazos;

	/** Requisitos. **/
	private String requisits;

	/** Url externa. **/
	private String urlExterna;

	/** Validacion. **/
	private Long validacio;

	/** Version. **/
	private Integer versio;

	/** Tasas. **/
	private RTasaRolsac[] tasas;

	/** Presencial. **/
	private boolean presencial;

	/** Telemático. **/
	private boolean telematico;

	/** Link organo componente. **/
	private RLink link_organCompetent;

	/** Link procedimiento. **/
	private RLink link_procedimiento;

	/** Organo componente. **/
	private java.lang.Long organCompetent;

	/** Procedimiento. **/
	private java.lang.Long procedimiento;

	/** Plataforma. **/
	private RPlataforma plataforma;

	/**
	 * @return the codigo
	 */
	public long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the codiVuds
	 */
	public String getCodiVuds() {
		return codiVuds;
	}

	/**
	 * @param codiVuds the codiVuds to set
	 */
	public void setCodiVuds(final String codiVuds) {
		this.codiVuds = codiVuds;
	}

	/**
	 * @return the dataActualitzacio
	 */
	public java.util.Calendar getDataActualitzacio() {
		return dataActualitzacio;
	}

	/**
	 * @param dataActualitzacio the dataActualitzacio to set
	 */
	public void setDataActualitzacio(final java.util.Calendar dataActualitzacio) {
		this.dataActualitzacio = dataActualitzacio;
	}

	/**
	 * @return the dataActualitzacioVuds
	 */
	public String getDataActualitzacioVuds() {
		return dataActualitzacioVuds;
	}

	/**
	 * @param dataActualitzacioVuds the dataActualitzacioVuds to set
	 */
	public void setDataActualitzacioVuds(final String dataActualitzacioVuds) {
		this.dataActualitzacioVuds = dataActualitzacioVuds;
	}

	/**
	 * @return the dataCaducitat
	 */
	public java.util.Calendar getDataCaducitat() {
		return dataCaducitat;
	}

	/**
	 * @param dataCaducitat the dataCaducitat to set
	 */
	public void setDataCaducitat(final java.util.Calendar dataCaducitat) {
		this.dataCaducitat = dataCaducitat;
	}

	/**
	 * @return the dataPublicacio
	 */
	public java.util.Calendar getDataPublicacio() {
		return dataPublicacio;
	}

	/**
	 * @param dataPublicacio the dataPublicacio to set
	 */
	public void setDataPublicacio(final java.util.Calendar dataPublicacio) {
		this.dataPublicacio = dataPublicacio;
	}

	/**
	 * @return the dataInici
	 */
	public java.util.Calendar getDataInici() {
		return dataInici;
	}

	/**
	 * @param dataInici the dataInici to set
	 */
	public void setDataInici(final java.util.Calendar dataInici) {
		this.dataInici = dataInici;
	}

	/**
	 * @return the dataTancament
	 */
	public java.util.Calendar getDataTancament() {
		return dataTancament;
	}

	/**
	 * @param dataTancament the dataTancament to set
	 */
	public void setDataTancament(final java.util.Calendar dataTancament) {
		this.dataTancament = dataTancament;
	}

	/**
	 * @return the descCodiVuds
	 */
	public String getDescCodiVuds() {
		return descCodiVuds;
	}

	/**
	 * @param descCodiVuds the descCodiVuds to set
	 */
	public void setDescCodiVuds(final String descCodiVuds) {
		this.descCodiVuds = descCodiVuds;
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
	public void setDocumentacion(final String documentacion) {
		this.documentacion = documentacion;
	}

	/**
	 * @return the fase
	 */
	public Integer getFase() {
		return fase;
	}

	/**
	 * @param fase the fase to set
	 */
	public void setFase(final Integer fase) {
		this.fase = fase;
	}

	/**
	 * @return the idTraTel
	 */
	public String getIdTraTel() {
		return idTraTel;
	}

	/**
	 * @param idTraTel the idTraTel to set
	 */
	public void setIdTraTel(final String idTraTel) {
		this.idTraTel = idTraTel;
	}

	/**
	 * @return the lugar
	 */
	public String getLugar() {
		return lugar;
	}

	/**
	 * @param lugar the lugar to set
	 */
	public void setLugar(final String lugar) {
		this.lugar = lugar;
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
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(final String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the orden
	 */
	public Long getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(final Long orden) {
		this.orden = orden;
	}

	/**
	 * @return the plazos
	 */
	public String getPlazos() {
		return plazos;
	}

	/**
	 * @param plazos the plazos to set
	 */
	public void setPlazos(final String plazos) {
		this.plazos = plazos;
	}

	/**
	 * @return the requisits
	 */
	public String getRequisits() {
		return requisits;
	}

	/**
	 * @param requisits the requisits to set
	 */
	public void setRequisits(final String requisits) {
		this.requisits = requisits;
	}

	/**
	 * @return the urlExterna
	 */
	public String getUrlExterna() {
		return urlExterna;
	}

	/**
	 * @param urlExterna the urlExterna to set
	 */
	public void setUrlExterna(final String urlExterna) {
		this.urlExterna = urlExterna;
	}

	/**
	 * @return the validacio
	 */
	public Long getValidacio() {
		return validacio;
	}

	/**
	 * @param validacio the validacio to set
	 */
	public void setValidacio(final Long validacio) {
		this.validacio = validacio;
	}

	/**
	 * @return the versio
	 */
	public Integer getVersio() {
		return versio;
	}

	/**
	 * @param versio the versio to set
	 */
	public void setVersio(final Integer versio) {
		this.versio = versio;
	}

	/**
	 * @return the tasas
	 */
	public RTasaRolsac[] getTasas() {
		return tasas;
	}

	/**
	 * @param tasas the tasas to set
	 */
	public void setTasas(final RTasaRolsac[] tasas) {
		this.tasas = tasas;
	}

	/**
	 * @return the link_organCompetent
	 */
	public RLink getLink_organCompetent() {
		return link_organCompetent;
	}

	/**
	 * @param link_organCompetent the link_organCompetent to set
	 */
	public void setLink_organCompetent(final RLink link_organCompetent) {
		this.link_organCompetent = link_organCompetent;
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
	public void setLink_procedimiento(final RLink link_procedimiento) {
		this.link_procedimiento = link_procedimiento;
	}

	/**
	 * @return the presencial
	 */
	public boolean isPresencial() {
		return presencial;
	}

	/**
	 * @param presencial the presencial to set
	 */
	public void setPresencial(final boolean presencial) {
		this.presencial = presencial;
	}

	/**
	 * @return the telematico
	 */
	public boolean isTelematico() {
		return telematico;
	}

	/**
	 * @param telematico the telematico to set
	 */
	public void setTelematico(final boolean telematico) {
		this.telematico = telematico;
	}

	/**
	 * @return the organCompetent
	 */
	public java.lang.Long getOrganCompetent() {
		return organCompetent;
	}

	/**
	 * @param organCompetent the organCompetent to set
	 */
	public void setOrganCompetent(final java.lang.Long organCompetent) {
		this.organCompetent = organCompetent;
	}

	/**
	 * @return the procedimiento
	 */
	public java.lang.Long getProcedimiento() {
		return procedimiento;
	}

	/**
	 * @param procedimiento the procedimiento to set
	 */
	public void setProcedimiento(final java.lang.Long procedimiento) {
		this.procedimiento = procedimiento;
	}

	/**
	 * @return the plataforma
	 */
	public final RPlataforma getPlataforma() {
		return plataforma;
	}

	/**
	 * @param plataforma the plataforma to set
	 */
	public final void setPlataforma(final RPlataforma plataforma) {
		this.plataforma = plataforma;
	}

}
