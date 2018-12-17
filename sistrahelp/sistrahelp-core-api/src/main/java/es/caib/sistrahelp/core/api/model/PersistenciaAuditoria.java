package es.caib.sistrahelp.core.api.model;

import java.util.Date;

import es.caib.sistrahelp.core.api.model.types.TypeEstadoTramite;

/**
 * Evento de persistencia.
 *
 * @author Indra
 *
 */
public final class PersistenciaAuditoria extends ModelApi {

	private static final long serialVersionUID = 1L;

	public PersistenciaAuditoria() {
		super();
	}

	private Long id;
	private String idSesionTramitacion;
	private String idTramite;
	private int versionTramite;
	private String idProcedimientoCP;
	private String nif;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private Date fechaInicio;
	private TypeEstadoTramite estado;
	private boolean cancelado;
	private Date fechaCaducidad;
	private boolean purgar;
	private Date fechaPurgado;
	private boolean purgado;
	private String descripcionTramite;
	private Date fechaUltimoAcceso;
	private Date fechaFin;
	private boolean persistente;
	private String urlInicio;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	public int getVersionTramite() {
		return versionTramite;
	}

	public void setVersionTramite(final int versionTramite) {
		this.versionTramite = versionTramite;
	}

	public String getIdProcedimientoCP() {
		return idProcedimientoCP;
	}

	public void setIdProcedimientoCP(final String idProcedimientoCP) {
		this.idProcedimientoCP = idProcedimientoCP;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(final String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(final String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(final String apellido2) {
		this.apellido2 = apellido2;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(final Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public TypeEstadoTramite getEstado() {
		return estado;
	}

	public void setEstado(final TypeEstadoTramite estado) {
		this.estado = estado;
	}

	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(final boolean cancelado) {
		this.cancelado = cancelado;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(final Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public boolean isPurgar() {
		return purgar;
	}

	public void setPurgar(final boolean purgar) {
		this.purgar = purgar;
	}

	public Date getFechaPurgado() {
		return fechaPurgado;
	}

	public void setFechaPurgado(final Date fechaPurgado) {
		this.fechaPurgado = fechaPurgado;
	}

	public boolean isPurgado() {
		return purgado;
	}

	public void setPurgado(final boolean purgado) {
		this.purgado = purgado;
	}

	public String getDescripcionTramite() {
		return descripcionTramite;
	}

	public void setDescripcionTramite(final String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}

	public Date getFechaUltimoAcceso() {
		return fechaUltimoAcceso;
	}

	public void setFechaUltimoAcceso(final Date fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(final Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public boolean isPersistente() {
		return persistente;
	}

	public void setPersistente(final boolean persistente) {
		this.persistente = persistente;
	}

	public String getUrlInicio() {
		return urlInicio;
	}

	public void setUrlInicio(final String urlInicio) {
		this.urlInicio = urlInicio;
	}

}
