package es.caib.sistramit.core.api.model.flujo;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * Persona: nif, nombre y apellidos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class PersonaDesglosado implements ModelApi {

	/** Nombre. */
	private String nombre;

	/** Apellido 1. */
	private String apellido1;

	/** Apellido 2.*/
	private String apellido2;

	/** Nif. */
	private String nif;

	/**
	 * Constructor.
	 */
	public PersonaDesglosado() {
		super();
	}

	public PersonaDesglosado(String nif, String nombre, String apellido1, String apellido2) {
		this.nif = nif;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
	}

	/**
	 * @return the nombre
	 */
	public final String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellido1
	 */
	public final String getApellido1() {
		return apellido1;
	}

	/**
	 * @param apellido1 the apellido1 to set
	 */
	public final void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * @return the apellido2
	 */
	public final String getApellido2() {
		return apellido2;
	}

	/**
	 * @param apellido2 the apellido2 to set
	 */
	public final void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * @return the nif
	 */
	public final String getNif() {
		return nif;
	}

	/**
	 * @param nif the nif to set
	 */
	public final void setNif(String nif) {
		this.nif = nif;
	}

	public final String getNombreApellidos() {
		final StringBuffer res = new StringBuffer(100);
		if (this.getNombre() != null) {
			res.append(this.getNombre());
		}
		if (StringUtils.isNotBlank(this.getApellido1())) {
			res.append(" ").append(this.getApellido1());
		}
		if (StringUtils.isNotBlank(this.getApellido2())) {
			res.append(" ").append(this.getApellido2());
		}
		return res.toString();
	}

	/**
	 * Método para mostrar el contenido de la clase Persona.
	 *
	 * @return el string
	 */
	public String print() {
		return "Persona [nif=" + nif + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + "]";
	}

}
