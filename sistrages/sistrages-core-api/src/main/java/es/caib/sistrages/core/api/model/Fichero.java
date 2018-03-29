package es.caib.sistrages.core.api.model;

/**
 * Fichero.
 *
 * @author indra
 *
 */
@SuppressWarnings("serial")
public class Fichero extends ModelApi {

	/** Código interno. **/
	private Long id;

	/** Nombre del fichero. */
	private String nombre;

	/** Indica si es público. */
	private boolean publico;

	/** Constructor. */
	public Fichero() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public boolean isPublico() {
		return publico;
	}

	public void setPublico(final boolean publico) {
		this.publico = publico;
	}
}
