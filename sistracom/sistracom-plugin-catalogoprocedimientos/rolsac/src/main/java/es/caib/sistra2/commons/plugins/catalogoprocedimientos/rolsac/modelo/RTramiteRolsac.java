package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RTramiteRolsac {

    /** codigo. */
    private String codigo;

    /** Codigo vuds. **/
    private String codiVuds;

    /** Fecha de actualizacion. **/
    private String dataActualitzacio;

    /** Fecha de actualizacion vuds. **/
    private String dataActualitzacioVuds;

    /** Fecha de caducidad. **/
    private String dataCaducitat;

    /** Fecha de publicacion. **/
    private String dataPublicacio;

    /** Fecha de inicio. **/
    private String dataInici;

    /** Fecha de cierre. **/
    private String dataTancament;

    /** Desc. codigo vuds. **/
    private String descCodiVuds;

    /** Documentacion. **/
    private String documentacion;

    /** Fase. **/
    private String fase;

    /** Id. tramite telematico. **/
    private String idTraTel;

    /** Lugar. **/
    private String lugar;

    /** Nombre. **/
    private String nombre;

    /** Observaciones. **/
    private String observaciones;

    /** Orden. **/
    private String orden;

    /** Plazos. **/
    private String plazos;

    /** Requisitos. **/
    private String requisits;

    /** Url externa. **/
    private String urlExterna;

    /** Validacion. **/
    private String validacio;

    /** Version. **/
    private String versio;

    private RTasaRolsac[] tasas;

    private RLink link_organCompetent;

    private RLink link_procedimiento;

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
     * @return the codiVuds
     */
    public String getCodiVuds() {
        return codiVuds;
    }

    /**
     * @param codiVuds
     *            the codiVuds to set
     */
    public void setCodiVuds(final String codiVuds) {
        this.codiVuds = codiVuds;
    }

    /**
     * @return the dataActualitzacio
     */
    public String getDataActualitzacio() {
        return dataActualitzacio;
    }

    /**
     * @param dataActualitzacio
     *            the dataActualitzacio to set
     */
    public void setDataActualitzacio(final String dataActualitzacio) {
        this.dataActualitzacio = dataActualitzacio;
    }

    /**
     * @return the dataActualitzacioVuds
     */
    public String getDataActualitzacioVuds() {
        return dataActualitzacioVuds;
    }

    /**
     * @param dataActualitzacioVuds
     *            the dataActualitzacioVuds to set
     */
    public void setDataActualitzacioVuds(final String dataActualitzacioVuds) {
        this.dataActualitzacioVuds = dataActualitzacioVuds;
    }

    /**
     * @return the dataCaducitat
     */
    public String getDataCaducitat() {
        return dataCaducitat;
    }

    /**
     * @param dataCaducitat
     *            the dataCaducitat to set
     */
    public void setDataCaducitat(final String dataCaducitat) {
        this.dataCaducitat = dataCaducitat;
    }

    /**
     * @return the dataPublicacio
     */
    public String getDataPublicacio() {
        return dataPublicacio;
    }

    /**
     * @param dataPublicacio
     *            the dataPublicacio to set
     */
    public void setDataPublicacio(final String dataPublicacio) {
        this.dataPublicacio = dataPublicacio;
    }

    /**
     * @return the dataInici
     */
    public String getDataInici() {
        return dataInici;
    }

    /**
     * @param dataInici
     *            the dataInici to set
     */
    public void setDataInici(final String dataInici) {
        this.dataInici = dataInici;
    }

    /**
     * @return the dataTancament
     */
    public String getDataTancament() {
        return dataTancament;
    }

    /**
     * @param dataTancament
     *            the dataTancament to set
     */
    public void setDataTancament(final String dataTancament) {
        this.dataTancament = dataTancament;
    }

    /**
     * @return the descCodiVuds
     */
    public String getDescCodiVuds() {
        return descCodiVuds;
    }

    /**
     * @param descCodiVuds
     *            the descCodiVuds to set
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
     * @param documentacion
     *            the documentacion to set
     */
    public void setDocumentacion(final String documentacion) {
        this.documentacion = documentacion;
    }

    /**
     * @return the fase
     */
    public String getFase() {
        return fase;
    }

    /**
     * @param fase
     *            the fase to set
     */
    public void setFase(final String fase) {
        this.fase = fase;
    }

    /**
     * @return the idTraTel
     */
    public String getIdTraTel() {
        return idTraTel;
    }

    /**
     * @param idTraTel
     *            the idTraTel to set
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
     * @param lugar
     *            the lugar to set
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
     * @param nombre
     *            the nombre to set
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
     * @param observaciones
     *            the observaciones to set
     */
    public void setObservaciones(final String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the orden
     */
    public String getOrden() {
        return orden;
    }

    /**
     * @param orden
     *            the orden to set
     */
    public void setOrden(final String orden) {
        this.orden = orden;
    }

    /**
     * @return the plazos
     */
    public String getPlazos() {
        return plazos;
    }

    /**
     * @param plazos
     *            the plazos to set
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
     * @param requisits
     *            the requisits to set
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
     * @param urlExterna
     *            the urlExterna to set
     */
    public void setUrlExterna(final String urlExterna) {
        this.urlExterna = urlExterna;
    }

    /**
     * @return the validacio
     */
    public String getValidacio() {
        return validacio;
    }

    /**
     * @param validacio
     *            the validacio to set
     */
    public void setValidacio(final String validacio) {
        this.validacio = validacio;
    }

    /**
     * @return the versio
     */
    public String getVersio() {
        return versio;
    }

    /**
     * @param versio
     *            the versio to set
     */
    public void setVersio(final String versio) {
        this.versio = versio;
    }

    /**
     * @return the tasas
     */
    public RTasaRolsac[] getTasas() {
        return tasas;
    }

    /**
     * @param tasas
     *            the tasas to set
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
     * @param link_organCompetent
     *            the link_organCompetent to set
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
     * @param link_procedimiento
     *            the link_procedimiento to set
     */
    public void setLink_procedimiento(final RLink link_procedimiento) {
        this.link_procedimiento = link_procedimiento;
    }

}
