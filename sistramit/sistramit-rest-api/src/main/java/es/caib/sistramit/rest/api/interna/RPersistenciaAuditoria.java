package es.caib.sistramit.rest.api.interna;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Evento de persistencia.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPersistenciaAuditoria", description = "Persistencia Auditoria")
public final class RPersistenciaAuditoria {

	@ApiModelProperty(value = "Id")
	private Long id;
	@ApiModelProperty(value = "Id. Sesion Tramitacion")
	private String idSesionTramitacion;
	@ApiModelProperty(value = "Id. Tramite")
	private String idTramite;
	@ApiModelProperty(value = "Version Tramite")
	private int versionTramite;
	@ApiModelProperty(value = "Id. Procedimiento CP")
	private String idProcedimientoCP;
	@ApiModelProperty(value = "Nif")
	private String nif;
	@ApiModelProperty(value = "Nombre")
	private String nombre;
	@ApiModelProperty(value = "Apellido 1")
	private String apellido1;
	@ApiModelProperty(value = "Apellido 2")
	private String apellido2;
	@ApiModelProperty(value = "Fecha Inicio")
	private Date fechaInicio;
	@ApiModelProperty(value = "Estado")
	private String estado;
	@ApiModelProperty(value = "Cancelado")
	private boolean cancelado;
	@ApiModelProperty(value = "Fecha Caducidad")
	private Date fechaCaducidad;
	@ApiModelProperty(value = "Purgar")
	private boolean purgar;
	@ApiModelProperty(value = "Fecha Purgado")
	private Date fechaPurgado;
	@ApiModelProperty(value = "Purgado")
	private boolean purgado;

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

	public String getEstado() {
		return estado;
	}

	public void setEstado(final String estado) {
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

}
