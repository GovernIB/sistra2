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

	// TODO SACAR CONTENIDO APARTE
	/** Contenido. **/
	private byte[] contenido;

	/**
	 * Constructor.
	 *
	 * @param iId
	 * @param iNombre
	 */
	public Fichero(final Long iId, final String iNombre) {
		this.setId(iId);
		this.nombre = iNombre;
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

	/**
	 * @return the contenido
	 */
	public byte[] getContenido() {
		return contenido;
	}

	/**
	 * @param contenido
	 *            the contenido to set
	 */
	public void setContenido(final byte[] contenido) {
		this.contenido = contenido;
	}
}
