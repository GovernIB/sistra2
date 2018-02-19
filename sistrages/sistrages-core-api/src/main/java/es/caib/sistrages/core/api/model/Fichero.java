package es.caib.sistrages.core.api.model;

/**
 * Fichero.
 *
 * @author indra
 *
 */
public class Fichero {

	/** Código interno. **/
	private Long codigo;

	/** Nombre del fichero. */
	private String nombre;

	/**
	 * Constructor.
	 * 
	 * @param iCodigo
	 * @param iNombre
	 */
	public Fichero(final Long iCodigo, final String iNombre) {
		this.codigo = iCodigo;
		this.nombre = iNombre;
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
}
