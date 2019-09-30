package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

/**
 * Plataforma de rolsac.
 *
 * @author Indra
 *
 */
public class RPlataforma {

	/** codigo. */
	private long codigo;

	/** Descripcion **/
	private String descripcion;

	/** Identificador **/
	private String identificador;

	/** Url **/
	private String url;

	/**
	 * @return the codigo
	 */
	public long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descripcion
	 */
	public final String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public final void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the identificador
	 */
	public final String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public final void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the url
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public final void setUrl(final String url) {
		this.url = url;
	}

}
