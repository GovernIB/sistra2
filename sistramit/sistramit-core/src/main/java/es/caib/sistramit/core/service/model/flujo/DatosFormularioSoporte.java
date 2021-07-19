package es.caib.sistramit.core.service.model.flujo;

public class DatosFormularioSoporte {

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

	/** Descripción problema. */
	private String problemaDesc;

	/** Horario soporte. */
	private String horarioContacto;

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

}
