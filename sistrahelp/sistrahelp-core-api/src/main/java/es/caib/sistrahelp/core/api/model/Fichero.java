package es.caib.sistrahelp.core.api.model;

/**
 * Fichero.
 *
 * @author indra
 *
 */

public class Fichero extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Código interno. **/
	private Long codigo;

	/** Nombre del fichero. */
	private String nombre;

	/** Indica si es público. */
	private boolean publico;

	/** Constructor. */
	public Fichero() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
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
