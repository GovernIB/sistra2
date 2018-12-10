package es.caib.sistramit.core.api.model.system;

import java.io.Serializable;
import java.util.Date;

/**
 * Evento de persistencia.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PersistenciaAuditoria implements Serializable {

	public PersistenciaAuditoria(final Long id, final String idSesionTramitacion, final String idTramite,
			final int versionTramite, final String idProcedimientoCP, final String nif, final String nombre,
			final String apellido1, final String apellido2, final Date fechaInicio, final String estado,
			final boolean cancelado, final Date fechaCaducidad, final boolean purgar, final Date fechaPurgado,
			final boolean purgado) {
		super();
		this.id = id;
		this.idSesionTramitacion = idSesionTramitacion;
		this.idTramite = idTramite;
		this.versionTramite = versionTramite;
		this.idProcedimientoCP = idProcedimientoCP;
		this.nif = nif;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.fechaInicio = fechaInicio;
		this.estado = estado;
		this.cancelado = cancelado;
		this.fechaCaducidad = fechaCaducidad;
		this.purgar = purgar;
		this.fechaPurgado = fechaPurgado;
		this.purgado = purgado;
	}

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
	private String estado;
	private boolean cancelado;
	private Date fechaCaducidad;
	private boolean purgar;
	private Date fechaPurgado;
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
