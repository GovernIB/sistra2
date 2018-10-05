package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RProcedimientoRolsac {

    /** codigo **/
    private long codigo;

    /**  **/
    private String destinatarios;

    /**  **/
    private java.util.Calendar fechaActualizacion;

    /**  **/
    private java.util.Calendar fechaCaducidad;

    /**  **/
    private java.util.Calendar fechaPublicacion;

    /**  **/
    private boolean indicador;

    /**  **/
    private String dirElectronica;

    /**  **/
    private String lugar;

    /**  **/
    private String nombre;

    /**  **/
    private String notificacion;

    /**  **/
    private String observaciones;

    /**  **/
    private String plazos;

    /**  **/
    private String recursos;

    /**  **/
    private String requisitos;

    /**  **/
    private String resolucion;

    /**  **/
    private String responsable;

    /**  **/
    private String resumen;

    /**  **/
    private String signatura;

    /**  **/
    private boolean taxa;

    /**  **/
    private String url;

    /**  **/
    private Integer validacion;

    /**  **/
    private String codigoSIA;

    /**  **/
    private String estadoSIA;

    /**  **/
    private java.util.Calendar fechaSIA;

    private String tramite;

    private Long version;

    /*
     * private java.lang.String resultat; private boolean ventanillaUnica;
     */

    // -- LINKS--//
    // -- se duplican las entidades para poder generar la clase link en funcion
    // de
    // la propiedad principal (sin "link_")
    /** servicioResponsable **/
    private RLink link_servicioResponsable;

    /** unidadAdministrativa **/
    private RLink link_unidadAdministrativa;

    /** organResolutori **/
    private RLink link_organResolutori;

    /** familia **/
    private RLink link_familia;

    /*
     * ////// CASOS ESPECIALES, SE RELLENA LA SUBENTIDAD. //Silencio
     *
     * @ApiModelProperty(value = "silencio", required = false) private Silencis
     * silencio;
     *
     *
     * //Iniciacion
     *
     * @ApiModelProperty(value = "iniciacion", required = false) private
     * Iniciacions iniciacion;
     */

    /**
     * @return the codigo
     */
    public long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     *            the codigo to set
     */
    public void setCodigo(final long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the destinatarios
     */
    public String getDestinatarios() {
        return destinatarios;
    }

    /**
     * @param destinatarios
     *            the destinatarios to set
     */
    public void setDestinatarios(final String destinatarios) {
        this.destinatarios = destinatarios;
    }

    /**
     * @return the fechaActualizacion
     */
    public java.util.Calendar getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * @param fechaActualizacion
     *            the fechaActualizacion to set
     */
    public void setFechaActualizacion(
            final java.util.Calendar fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * @return the fechaCaducidad
     */
    public java.util.Calendar getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * @param fechaCaducidad
     *            the fechaCaducidad to set
     */
    public void setFechaCaducidad(final java.util.Calendar fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    /**
     * @return the fechaPublicacion
     */
    public java.util.Calendar getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * @param fechaPublicacion
     *            the fechaPublicacion to set
     */
    public void setFechaPublicacion(final java.util.Calendar fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * @return the indicador
     */
    public boolean isIndicador() {
        return indicador;
    }

    /**
     * @param indicador
     *            the indicador to set
     */
    public void setIndicador(final boolean indicador) {
        this.indicador = indicador;
    }

    /**
     * @return the dirElectronica
     */
    public String getDirElectronica() {
        return dirElectronica;
    }

    /**
     * @param dirElectronica
     *            the dirElectronica to set
     */
    public void setDirElectronica(final String dirElectronica) {
        this.dirElectronica = dirElectronica;
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
     * @return the notificacion
     */
    public String getNotificacion() {
        return notificacion;
    }

    /**
     * @param notificacion
     *            the notificacion to set
     */
    public void setNotificacion(final String notificacion) {
        this.notificacion = notificacion;
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
     * @return the recursos
     */
    public String getRecursos() {
        return recursos;
    }

    /**
     * @param recursos
     *            the recursos to set
     */
    public void setRecursos(final String recursos) {
        this.recursos = recursos;
    }

    /**
     * @return the requisitos
     */
    public String getRequisitos() {
        return requisitos;
    }

    /**
     * @param requisitos
     *            the requisitos to set
     */
    public void setRequisitos(final String requisitos) {
        this.requisitos = requisitos;
    }

    /**
     * @return the resolucion
     */
    public String getResolucion() {
        return resolucion;
    }

    /**
     * @param resolucion
     *            the resolucion to set
     */
    public void setResolucion(final String resolucion) {
        this.resolucion = resolucion;
    }

    /**
     * @return the responsable
     */
    public String getResponsable() {
        return responsable;
    }

    /**
     * @param responsable
     *            the responsable to set
     */
    public void setResponsable(final String responsable) {
        this.responsable = responsable;
    }

    /**
     * @return the resumen
     */
    public String getResumen() {
        return resumen;
    }

    /**
     * @param resumen
     *            the resumen to set
     */
    public void setResumen(final String resumen) {
        this.resumen = resumen;
    }

    /**
     * @return the signatura
     */
    public String getSignatura() {
        return signatura;
    }

    /**
     * @param signatura
     *            the signatura to set
     */
    public void setSignatura(final String signatura) {
        this.signatura = signatura;
    }

    /**
     * @return the taxa
     */
    public boolean isTaxa() {
        return taxa;
    }

    /**
     * @param taxa
     *            the taxa to set
     */
    public void setTaxa(final boolean taxa) {
        this.taxa = taxa;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * @return the validacion
     */
    public Integer getValidacion() {
        return validacion;
    }

    /**
     * @param validacion
     *            the validacion to set
     */
    public void setValidacion(final Integer validacion) {
        this.validacion = validacion;
    }

    /**
     * @return the codigoSIA
     */
    public String getCodigoSIA() {
        return codigoSIA;
    }

    /**
     * @param codigoSIA
     *            the codigoSIA to set
     */
    public void setCodigoSIA(final String codigoSIA) {
        this.codigoSIA = codigoSIA;
    }

    /**
     * @return the estadoSIA
     */
    public String getEstadoSIA() {
        return estadoSIA;
    }

    /**
     * @param estadoSIA
     *            the estadoSIA to set
     */
    public void setEstadoSIA(final String estadoSIA) {
        this.estadoSIA = estadoSIA;
    }

    /**
     * @return the fechaSIA
     */
    public java.util.Calendar getFechaSIA() {
        return fechaSIA;
    }

    /**
     * @param fechaSIA
     *            the fechaSIA to set
     */
    public void setFechaSIA(final java.util.Calendar fechaSIA) {
        this.fechaSIA = fechaSIA;
    }

    /**
     * @return the tramite
     */
    public String getTramite() {
        return tramite;
    }

    /**
     * @param tramite
     *            the tramite to set
     */
    public void setTramite(final String tramite) {
        this.tramite = tramite;
    }

    /**
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(final Long version) {
        this.version = version;
    }

    /**
     * @return the link_servicioResponsable
     */
    public RLink getLink_servicioResponsable() {
        return link_servicioResponsable;
    }

    /**
     * @param link_servicioResponsable
     *            the link_servicioResponsable to set
     */
    public void setLink_servicioResponsable(
            final RLink link_servicioResponsable) {
        this.link_servicioResponsable = link_servicioResponsable;
    }

    /**
     * @return the link_unidadAdministrativa
     */
    public RLink getLink_unidadAdministrativa() {
        return link_unidadAdministrativa;
    }

    /**
     * @param link_unidadAdministrativa
     *            the link_unidadAdministrativa to set
     */
    public void setLink_unidadAdministrativa(
            final RLink link_unidadAdministrativa) {
        this.link_unidadAdministrativa = link_unidadAdministrativa;
    }

    /**
     * @return the link_organResolutori
     */
    public RLink getLink_organResolutori() {
        return link_organResolutori;
    }

    /**
     * @param link_organResolutori
     *            the link_organResolutori to set
     */
    public void setLink_organResolutori(final RLink link_organResolutori) {
        this.link_organResolutori = link_organResolutori;
    }

    /**
     * @return the link_familia
     */
    public RLink getLink_familia() {
        return link_familia;
    }

    /**
     * @param link_familia
     *            the link_familia to set
     */
    public void setLink_familia(final RLink link_familia) {
        this.link_familia = link_familia;
    }

}
