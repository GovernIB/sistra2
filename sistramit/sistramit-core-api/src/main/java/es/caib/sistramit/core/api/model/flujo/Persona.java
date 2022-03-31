package es.caib.sistramit.core.api.model.flujo;

/**
 *
 * Persona: nif y nombre completo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class Persona implements ModelApi {

	/**
	 * Constructor.
	 */
	public Persona() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param pNif
	 *                    Nif
	 * @param pNombre
	 *                    Nombre completo
	 */
	public Persona(final String pNif, final String pNombre) {
		super();
		nif = pNif;
		nombre = pNombre;
	}

	/**
	 * Nif.
	 */
	private String nif;

	/**
	 * Nombre.
	 */
	private String nombre;

	/**
	 * Método de acceso a nif.
	 *
	 * @return nif
	 */
	public final String getNif() {
		return nif;
	}

	/**
	 * Método para establecer nif.
	 *
	 * @param pNif
	 *                 nif a establecer
	 */
	public final void setNif(final String pNif) {
		nif = pNif;
	}

	/**
	 * Método de acceso a nombre.
	 *
	 * @return nombre
	 */
	public final String getNombre() {
		return nombre;
	}

	/**
	 * Método para establecer nombre.
	 *
	 * @param pNombre
	 *                    nombre a establecer
	 */
	public final void setNombre(final String pNombre) {
		nombre = pNombre;
	}

	/**
	 * Método para Crea new persona de la clase Persona.
	 *
	 * @return el persona
	 */
	public static Persona createNewPersona() {
		return new Persona();
	}

	/**
	 * Método para mostrar el contenido de la clase Persona.
	 *
	 * @return el string
	 */
	public String print() {
		return "Persona [nif=" + nif + ", nombre=" + nombre + "]";
	}

}
