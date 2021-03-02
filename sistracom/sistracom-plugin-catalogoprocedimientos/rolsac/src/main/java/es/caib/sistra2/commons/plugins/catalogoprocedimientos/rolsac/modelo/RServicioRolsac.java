package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

import java.util.Calendar;

/**
 * Servicios de rolsac.
 *
 * @author Indra
 *
 */
public class RServicioRolsac implements RInfoLOPDIntf {

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
	private Calendar fechaActualizacion;

	/** Fecha de despublicacion/caducidad **/
	private Calendar fechaDespublicacion;

	/** Fecha de publicacion **/
	private Calendar fechaPublicacion;

	/** Fecha de SIA **/
	private Calendar fechaSIA;

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

	/** tramiteId. **/
	private String tramiteId;

	/** tramiteVersion. **/
	private String tramiteVersion;

	/** Validacion. **/
	private Integer validacion;

	/** Link organo componente. **/
	private RLink link_servicioResponsable;

	/** Link procedimiento. **/
	private RLink link_organoInstructor;

	/** Plataforma. **/
	private RPlataforma plataforma;

	/** Plataforma. **/
	private Long codigoPlataforma;

	/** Telematico. **/
	private boolean telematico;

	/** LOPD Cabecera. */
	private String lopdCabecera;

	/** LOPD Responsable. */
	private String lopdResponsable;

	/** LOPD Finalidad. */
	private String lopdFinalidad;

	/** LOPD Destinatario. */
	private String lopdDestinatario;

	/** LOPD Derechos. */
	private String lopdDerechos;

	/** LOPD Legitimacion. */
	private RLegitimacion lopdLegitimacion;

	/** Link InfoAdicional. **/
	private RLink link_lopdInfoAdicional;

	/**
	 * @return the codigo
	 */
	public final long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *                   the codigo to set
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
	 *                           the codigoServicio to set
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
	 *                      the codigoSIA to set
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
	 *                   the correo to set
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
	 *                         the destinatario to set
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
	 *                      the estadoSIA to set
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
	 *                               the fechaActualizacion to set
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
	 *                                the fechaDespublicacion to set
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
	 *                             the fechaPublicacion to set
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
	 *                     the fechaSIA to set
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
	 *               the id to set
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
	 *                   the nombre to set
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
	 *                              the nombreResponsable to set
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
	 *                   the objeto to set
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
	 *                          the observaciones to set
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
	 *                       the requisitos to set
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
	 *                     the telefono to set
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
	 *                       the tramiteUrl to set
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
	 *                    the tasaUrl to set
	 */
	public final void setTasaUrl(final String tasaUrl) {
		this.tasaUrl = tasaUrl;
	}

	/**
	 * @return the validacion
	 */
	public final Integer getValidacion() {
		return validacion;
	}

	/**
	 * @param validacion
	 *                       the validacion to set
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
	 *                                     the link_servicioResponsable to set
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
	 *                                  the link_organoInstructor to set
	 */
	public final void setLink_organoInstructor(final RLink link_organoInstructor) {
		this.link_organoInstructor = link_organoInstructor;
	}

	/**
	 * @return the plataforma
	 */
	public final RPlataforma getPlataforma() {
		return plataforma;
	}

	/**
	 * @param plataforma
	 *                       the plataforma to set
	 */
	public final void setPlataforma(final RPlataforma plataforma) {
		this.plataforma = plataforma;
	}

	/**
	 * @return the codigoPlataforma
	 */
	public final Long getCodigoPlataforma() {
		return codigoPlataforma;
	}

	/**
	 * @param codigoPlataforma
	 *                             the codigoPlataforma to set
	 */
	public final void setCodigoPlataforma(final Long codigoPlataforma) {
		this.codigoPlataforma = codigoPlataforma;
	}

	/**
	 * @return the telematico
	 */
	public final boolean isTelematico() {
		return telematico;
	}

	/**
	 * @param telematico
	 *                       the telematico to set
	 */
	public final void setTelematico(final boolean telematico) {
		this.telematico = telematico;
	}

	/**
	 * @return the tramiteId
	 */
	public String getTramiteId() {
		return tramiteId;
	}

	/**
	 * @param tramiteId
	 *                      the tramiteId to set
	 */
	public void setTramiteId(final String tramiteId) {
		this.tramiteId = tramiteId;
	}

	/**
	 * @return the tramiteVersion
	 */
	public String getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion
	 *                           the tramiteVersion to set
	 */
	public void setTramiteVersion(final String tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * Método de acceso a lopdCabecera.
	 *
	 * @return lopdCabecera
	 */
	@Override
	public String getLopdCabecera() {
		return lopdCabecera;
	}

	/**
	 * Método para establecer lopdCabecera.
	 *
	 * @param lopdCabecera
	 *                         lopdCabecera a establecer
	 */
	public void setLopdCabecera(final String lopdCabecera) {
		this.lopdCabecera = lopdCabecera;
	}

	/**
	 * Método de acceso a lopdResponsable.
	 *
	 * @return lopdResponsable
	 */
	@Override
	public String getLopdResponsable() {
		return lopdResponsable;
	}

	/**
	 * Método para establecer lopdResponsable.
	 *
	 * @param lopdResponsable
	 *                            lopdResponsable a establecer
	 */
	public void setLopdResponsable(final String lopdResponsable) {
		this.lopdResponsable = lopdResponsable;
	}

	/**
	 * Método de acceso a lopdFinalidad.
	 *
	 * @return lopdFinalidad
	 */
	@Override
	public String getLopdFinalidad() {
		return lopdFinalidad;
	}

	/**
	 * Método para establecer lopdFinalidad.
	 *
	 * @param lopdFinalidad
	 *                          lopdFinalidad a establecer
	 */
	public void setLopdFinalidad(final String lopdFinalidad) {
		this.lopdFinalidad = lopdFinalidad;
	}

	/**
	 * Método de acceso a lopdDestinatario.
	 *
	 * @return lopdDestinatario
	 */
	@Override
	public String getLopdDestinatario() {
		return lopdDestinatario;
	}

	/**
	 * Método para establecer lopdDestinatario.
	 *
	 * @param lopdDestinatario
	 *                             lopdDestinatario a establecer
	 */
	public void setLopdDestinatario(final String lopdDestinatario) {
		this.lopdDestinatario = lopdDestinatario;
	}

	/**
	 * Método de acceso a lopdDerechos.
	 *
	 * @return lopdDerechos
	 */
	@Override
	public String getLopdDerechos() {
		return lopdDerechos;
	}

	/**
	 * Método para establecer lopdDerechos.
	 *
	 * @param lopdDerechos
	 *                         lopdDerechos a establecer
	 */
	public void setLopdDerechos(final String lopdDerechos) {
		this.lopdDerechos = lopdDerechos;
	}

	/**
	 * Método de acceso a lopdLegitimacion.
	 *
	 * @return lopdLegitimacion
	 */
	@Override
	public RLegitimacion getLopdLegitimacion() {
		return lopdLegitimacion;
	}

	/**
	 * Método para establecer lopdLegitimacion.
	 *
	 * @param lopdLegitimacion
	 *                             lopdLegitimacion a establecer
	 */
	public void setLopdLegitimacion(final RLegitimacion lopdLegitimacion) {
		this.lopdLegitimacion = lopdLegitimacion;
	}

	/**
	 * Método de acceso a link_lopdInfoAdicional.
	 *
	 * @return link_lopdInfoAdicional
	 */
	@Override
	public RLink getLink_lopdInfoAdicional() {
		return link_lopdInfoAdicional;
	}

	/**
	 * Método para establecer link_lopdInfoAdicional.
	 *
	 * @param link_lopdInfoAdicional
	 *                                   link_lopdInfoAdicional a establecer
	 */
	public void setLink_lopdInfoAdicional(final RLink link_lopdInfoAdicional) {
		this.link_lopdInfoAdicional = link_lopdInfoAdicional;
	}

}
