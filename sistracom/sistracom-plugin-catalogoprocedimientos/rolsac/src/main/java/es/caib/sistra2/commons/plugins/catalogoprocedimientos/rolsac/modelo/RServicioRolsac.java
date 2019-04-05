package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

/**
 * Servicios de rolsac.
 *
 * @author Indra
 *
 */
public class RServicioRolsac {

	/** codigo. */
	private long codigo;

	/** Codi servicio **/
	private String codigoServicio;

	/** Codi sia **/
	private String codigoSIA;

	/** Correo **/
	private String correo;

	/** Destinatario **/
	private String destinatario;

	/** Estado sia **/
	private String estadoSIA;

	/** Fecha de actualizacion **/
	private java.util.Calendar fechaActualizacion;

	/** Fecha de despublicacion/caducidad **/
	private java.util.Calendar fechaDespublicacion;

	/** Fecha de publicacion **/
	private java.util.Calendar fechaPublicacion;

	/** Fecha de SIA **/
	private java.util.Calendar fechaSIA;

	/** codigo. */
	private long id;

	/** Nombre. **/
	private String nombre;

	/** Nombre del responsable. **/
	private String nombreResponsable;

	/** Objeto. **/
	private String objeto;

	/** Observaciones. **/
	private String observaciones;

	/** Requisitos. **/
	private String requisitos;

	/** Telefono. **/
	private String telefono;

	/** Tramite Url. **/
	private String tramiteUrl;

	/** Tasa url. **/
	private String tasaUrl;

	/** tramiteVersion. **/
	private String tramiteVersion;

	/** Validacion. **/
	private Integer validacion;

	/** Link organo componente. **/
	private RLink link_servicioResponsable;

	/** Link procedimiento. **/
	private RLink link_organoInstructor;

	/**
	 * @return the codigo
	 */
	public final long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public final void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the codigoServicio
	 */
	public final String getCodigoServicio() {
		return codigoServicio;
	}

	/**
	 * @param codigoServicio
	 *            the codigoServicio to set
	 */
	public final void setCodigoServicio(final String codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

	/**
	 * @return the codigoSIA
	 */
	public final String getCodigoSIA() {
		return codigoSIA;
	}

	/**
	 * @param codigoSIA
	 *            the codigoSIA to set
	 */
	public final void setCodigoSIA(final String codigoSIA) {
		this.codigoSIA = codigoSIA;
	}

	/**
	 * @return the correo
	 */
	public final String getCorreo() {
		return correo;
	}

	/**
	 * @param correo
	 *            the correo to set
	 */
	public final void setCorreo(final String correo) {
		this.correo = correo;
	}

	/**
	 * @return the destinatario
	 */
	public final String getDestinatario() {
		return destinatario;
	}

	/**
	 * @param destinatario
	 *            the destinatario to set
	 */
	public final void setDestinatario(final String destinatario) {
		this.destinatario = destinatario;
	}

	/**
	 * @return the estadoSIA
	 */
	public final String getEstadoSIA() {
		return estadoSIA;
	}

	/**
	 * @param estadoSIA
	 *            the estadoSIA to set
	 */
	public final void setEstadoSIA(final String estadoSIA) {
		this.estadoSIA = estadoSIA;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public final java.util.Calendar getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion
	 *            the fechaActualizacion to set
	 */
	public final void setFechaActualizacion(final java.util.Calendar fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	/**
	 * @return the fechaDespublicacion
	 */
	public final java.util.Calendar getFechaDespublicacion() {
		return fechaDespublicacion;
	}

	/**
	 * @param fechaDespublicacion
	 *            the fechaDespublicacion to set
	 */
	public final void setFechaDespublicacion(final java.util.Calendar fechaDespublicacion) {
		this.fechaDespublicacion = fechaDespublicacion;
	}

	/**
	 * @return the fechaPublicacion
	 */
	public final java.util.Calendar getFechaPublicacion() {
		return fechaPublicacion;
	}

	/**
	 * @param fechaPublicacion
	 *            the fechaPublicacion to set
	 */
	public final void setFechaPublicacion(final java.util.Calendar fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	/**
	 * @return the fechaSIA
	 */
	public final java.util.Calendar getFechaSIA() {
		return fechaSIA;
	}

	/**
	 * @param fechaSIA
	 *            the fechaSIA to set
	 */
	public final void setFechaSIA(final java.util.Calendar fechaSIA) {
		this.fechaSIA = fechaSIA;
	}

	/**
	 * @return the id
	 */
	public final long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(final long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public final String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public final void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the nombreResponsable
	 */
	public final String getNombreResponsable() {
		return nombreResponsable;
	}

	/**
	 * @param nombreResponsable
	 *            the nombreResponsable to set
	 */
	public final void setNombreResponsable(final String nombreResponsable) {
		this.nombreResponsable = nombreResponsable;
	}

	/**
	 * @return the objeto
	 */
	public final String getObjeto() {
		return objeto;
	}

	/**
	 * @param objeto
	 *            the objeto to set
	 */
	public final void setObjeto(final String objeto) {
		this.objeto = objeto;
	}

	/**
	 * @return the observaciones
	 */
	public final String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            the observaciones to set
	 */
	public final void setObservaciones(final String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the requisitos
	 */
	public final String getRequisitos() {
		return requisitos;
	}

	/**
	 * @param requisitos
	 *            the requisitos to set
	 */
	public final void setRequisitos(final String requisitos) {
		this.requisitos = requisitos;
	}

	/**
	 * @return the telefono
	 */
	public final String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public final void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the tramiteUrl
	 */
	public final String getTramiteUrl() {
		return tramiteUrl;
	}

	/**
	 * @param tramiteUrl
	 *            the tramiteUrl to set
	 */
	public final void setTramiteUrl(final String tramiteUrl) {
		this.tramiteUrl = tramiteUrl;
	}

	/**
	 * @return the tasaUrl
	 */
	public final String getTasaUrl() {
		return tasaUrl;
	}

	/**
	 * @param tasaUrl
	 *            the tasaUrl to set
	 */
	public final void setTasaUrl(final String tasaUrl) {
		this.tasaUrl = tasaUrl;
	}

	/**
	 * @return the tramiteVersion
	 */
	public final String getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion
	 *            the tramiteVersion to set
	 */
	public final void setTramiteVersion(final String tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * @return the validacion
	 */
	public final Integer getValidacion() {
		return validacion;
	}

	/**
	 * @param validacion
	 *            the validacion to set
	 */
	public final void setValidacion(final Integer validacion) {
		this.validacion = validacion;
	}

	/**
	 * @return the link_servicioResponsable
	 */
	public final RLink getLink_servicioResponsable() {
		return link_servicioResponsable;
	}

	/**
	 * @param link_servicioResponsable
	 *            the link_servicioResponsable to set
	 */
	public final void setLink_servicioResponsable(final RLink link_servicioResponsable) {
		this.link_servicioResponsable = link_servicioResponsable;
	}

	/**
	 * @return the link_organoInstructor
	 */
	public final RLink getLink_organoInstructor() {
		return link_organoInstructor;
	}

	/**
	 * @param link_organoInstructor
	 *            the link_organoInstructor to set
	 */
	public final void setLink_organoInstructor(final RLink link_organoInstructor) {
		this.link_organoInstructor = link_organoInstructor;
	}

}
