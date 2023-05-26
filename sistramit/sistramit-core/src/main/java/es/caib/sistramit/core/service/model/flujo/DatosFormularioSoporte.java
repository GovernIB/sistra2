package es.caib.sistramit.core.service.model.flujo;

import java.util.Date;

import es.caib.sistramit.core.api.model.flujo.types.TypeSoporteEstado;

public class DatosFormularioSoporte {

	/** Fecha creación. */
	private Date fechaCreacion;

	/** Nif. */
	private String nif;

	/** Nombre. */
	private String nombre;

	/** Teléfono. */
	private String telefono;

	/** Email. */
	private String email;

	/** Tipo problema. */
	private String problemaTipo;

	/** Tipo problema (descripción). */
	private String problemaTipoDesc;

	/** Descripción problema. */
	private String problemaDesc;

	/** Horario soporte. */
	private String horarioContacto;

	/** Nombre fichero. */
	private String nombreFichero;

	/** Datos fichero. */
	private byte[] datosFichero;

	/** Estado. */
	private TypeSoporteEstado estado;

	/** Comentarios. */
	private String comentarios;

	/**
	 * Método de acceso a nif.
	 *
	 * @return nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Método para establecer nif.
	 *
	 * @param nif
	 *                nif a establecer
	 */
	public void setNif(final String nif) {
		this.nif = nif;
	}

	/**
	 * Método de acceso a nombre.
	 *
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método para establecer nombre.
	 *
	 * @param nombre
	 *                   nombre a establecer
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método de acceso a telefono.
	 *
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Método para establecer telefono.
	 *
	 * @param telefono
	 *                     telefono a establecer
	 */
	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Método de acceso a email.
	 *
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Método para establecer email.
	 *
	 * @param email
	 *                  email a establecer
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Método de acceso a problemaTipo.
	 *
	 * @return problemaTipo
	 */
	public String getProblemaTipo() {
		return problemaTipo;
	}

	/**
	 * Método para establecer problemaTipo.
	 *
	 * @param problemaTipo
	 *                         problemaTipo a establecer
	 */
	public void setProblemaTipo(final String problemaTipo) {
		this.problemaTipo = problemaTipo;
	}

	/**
	 * Método de acceso a problemaDesc.
	 *
	 * @return problemaDesc
	 */
	public String getProblemaDesc() {
		return problemaDesc;
	}

	/**
	 * Método para establecer problemaDesc.
	 *
	 * @param problemaDesc
	 *                         problemaDesc a establecer
	 */
	public void setProblemaDesc(final String problemaDesc) {
		this.problemaDesc = problemaDesc;
	}

	/**
	 * Método de acceso a horarioContacto.
	 *
	 * @return horarioContacto
	 */
	public String getHorarioContacto() {
		return horarioContacto;
	}

	/**
	 * Método para establecer horarioContacto.
	 *
	 * @param horarioContacto
	 *                            horarioContacto a establecer
	 */
	public void setHorarioContacto(final String horarioContacto) {
		this.horarioContacto = horarioContacto;
	}

	/**
	 * Método de acceso a fechaCreacion.
	 *
	 * @return fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Método para establecer fechaCreacion.
	 *
	 * @param fechaCreacion
	 *                          fechaCreacion a establecer
	 */
	public void setFechaCreacion(final Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Método de acceso a nombreFichero.
	 *
	 * @return nombreFichero
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}

	/**
	 * Método para establecer nombreFichero.
	 *
	 * @param nombreFichero
	 *                          nombreFichero a establecer
	 */
	public void setNombreFichero(final String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	/**
	 * Método de acceso a datosFichero.
	 *
	 * @return datosFichero
	 */
	public byte[] getDatosFichero() {
		return datosFichero;
	}

	/**
	 * Método para establecer datosFichero.
	 *
	 * @param datosFichero
	 *                         datosFichero a establecer
	 */
	public void setDatosFichero(final byte[] datosFichero) {
		this.datosFichero = datosFichero;
	}

	public TypeSoporteEstado getEstado() {
		return estado;
	}

	public void setEstado(final TypeSoporteEstado estado) {
		this.estado = estado;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(final String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * Método de acceso a problemaTipoDesc.
	 * 
	 * @return problemaTipoDesc
	 */
	public String getProblemaTipoDesc() {
		return problemaTipoDesc;
	}

	/**
	 * Método para establecer problemaTipoDesc.
	 * 
	 * @param problemaTipoDesc
	 *                             problemaTipoDesc a establecer
	 */
	public void setProblemaTipoDesc(final String problemaTipoDesc) {
		this.problemaTipoDesc = problemaTipoDesc;
	}

}
