package es.caib.sistrahelp.core.api.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;

/**
 * La clase EventoAuditoriaTramitacion (RestApiInternaService).
 */
@SuppressWarnings("serial")
public final class Soporte implements Serializable {

	public Soporte(Long codigo, Date fechaCreacion, String sesionTramitacion, String nif, String nombre,
			String telefono, String email, String horario, String tipoProblema, String descripcionProblema,
			String idTramite, Integer version, String nombreFichero, byte[] datosFichero, String estado,
			String coment) {
		super();
		this.codigo = codigo;
		this.fechaCreacion = fechaCreacion;
		this.sesionTramitacion = sesionTramitacion;
		this.nif = nif;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.horario = horario;
		this.tipoProblema = tipoProblema;
		this.descripcionProblema = descripcionProblema;
		this.idTramite = idTramite;
		this.version = version;
		this.nombreFichero = nombreFichero;
		this.datosFichero = datosFichero;
		this.estado = estado;
		this.coment = coment;
	}

	/**
	 * Crea una nueva instancia de EventoAuditoriaTramitacion.
	 */
	public Soporte() {
		super();
	}

	/**
	 * Codigo.
	 */
	private Long codigo;

	/**
	 * Fecha de creacion
	 */
	private Date fechaCreacion;

	/**
	 * Sesion de tramitacion
	 */
	private String sesionTramitacion;

	/**
	 * NIF.
	 */
	private String nif;

	/**
	 * Nombre.
	 */
	private String nombre;

	/**
	 * Telefono.
	 */
	private String telefono;

	/**
	 * Email
	 */
	private String email;

	/**
	 * Horario
	 */
	private String horario;

	/**
	 * Tipo de problema
	 */
	private String tipoProblema;

	/**
	 * Descripion problema
	 */
	private String descripcionProblema;

	/**
	 * IdTramite
	 */
	private String idTramite;

	/**
	 * Version
	 */
	private Integer version;

	/**
	 * Nombre fichero
	 */
	private String nombreFichero;

	/**
	 * Estado
	 */
	private String estado;

	/**
	 * Comentario
	 */
	private String coment;

	/**
	 * Datos fichero"
	 */
	private byte[] datosFichero;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getSesionTramitacion() {
		return sesionTramitacion;
	}

	public void setSesionTramitacion(String sesionTramitacion) {
		this.sesionTramitacion = sesionTramitacion;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getTipoProblema() {
		return tipoProblema;
	}

	public void setTipoProblema(String tipoProblema) {
		this.tipoProblema = tipoProblema;
	}

	public String getDescripcionProblema() {
		return descripcionProblema;
	}

	public void setDescripcionProblema(String descripcionProblema) {
		this.descripcionProblema = descripcionProblema;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public byte[] getDatosFichero() {
		return datosFichero;
	}

	public void setDatosFichero(byte[] datosFichero) {
		this.datosFichero = datosFichero;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getComent() {
		return coment;
	}

	public void setComent(String coment) {
		this.coment = coment;
	}
}
