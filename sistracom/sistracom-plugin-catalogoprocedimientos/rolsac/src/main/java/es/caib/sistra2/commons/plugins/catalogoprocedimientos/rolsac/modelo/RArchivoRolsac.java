package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

/**
 * Archivo Rolsac.
 *
 * @author Indra
 *
 */
public class RArchivoRolsac {

	/** Identificador **/
	private Long codigo;

	/** Descripcion **/
	private String nombre;

	/** Mime **/
	private String mime;

	/** Peso **/
	private Long peso;

	/**
	 * Método de acceso a codigo.
	 * 
	 * @return codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Método para establecer codigo.
	 * 
	 * @param codigo
	 *                   codigo a establecer
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
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
	 * Método de acceso a mime.
	 * 
	 * @return mime
	 */
	public String getMime() {
		return mime;
	}

	/**
	 * Método para establecer mime.
	 * 
	 * @param mime
	 *                 mime a establecer
	 */
	public void setMime(final String mime) {
		this.mime = mime;
	}

	/**
	 * Método de acceso a peso.
	 * 
	 * @return peso
	 */
	public Long getPeso() {
		return peso;
	}

	/**
	 * Método para establecer peso.
	 * 
	 * @param peso
	 *                 peso a establecer
	 */
	public void setPeso(final Long peso) {
		this.peso = peso;
	}

}
