package es.caib.sistramit.rest.api.interna;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Evento Auditoria.
 *
 * @author Indra
 *
 */
@ApiModel(value = "REventoAuditoria", description = "Evento Auditoria")
public class RSoporte {

	/**
	 * Codigo.
	 */
	@ApiModelProperty(value = "Codigo")
	private Long codigo;

	/**
	 * IdTramite.
	 */
	@ApiModelProperty(value = "Id tramite")
	private String idTramite;

	/**
	 * Version.
	 */
	@ApiModelProperty(value = "Version")
	private Integer version;

	/**
	 * Fecha de creacion
	 */
	@ApiModelProperty(value = "Fecha de creacion")
	private Date fechaCreacion;

	/**
	 * Sesion de tramitacion
	 */
	@ApiModelProperty(value = "Sesion de tramitacion")
	private String sesionTramitacion;

	/**
	 * NIF.
	 */
	@ApiModelProperty(value = "NIF")
	private String nif;

	/**
	 * Nombre.
	 */
	@ApiModelProperty(value = "Nombre")
	private String nombre;

	/**
	 * Telefono.
	 */
	@ApiModelProperty(value = "Telefono")
	private String telefono;

	/**
	 * Email
	 */
	@ApiModelProperty(value = "Email")
	private String email;

	/**
	 * Horario
	 */
	@ApiModelProperty(value = "Horario")
	private String horario;

	/**
	 * Tipo de problema
	 */
	@ApiModelProperty(value = "Tipo de problema")
	private String tipoProblema;

	/**
	 * Descripion problema
	 */
	@ApiModelProperty(value = "Descripion problema")
	private String descripcionProblema;

	/**
	 * Nombre fichero
	 */
	@ApiModelProperty(value = "Nombre fichero")
	private String nombreFichero;

	/**
	 * Datos fichero
	 */
	@ApiModelProperty(value = "Datos fichero")
	private byte[] datosFichero;

	/**
	 * Estado
	 */
	@ApiModelProperty(value = "Estado")
	private String estado;

	/**
	 * Comentario
	 */
	@ApiModelProperty(value = "Comentario")
	private String comentario;

	/**
	 * Instancia un nuevo evento
	 */
	public RSoporte() {
		super();
	}

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

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
