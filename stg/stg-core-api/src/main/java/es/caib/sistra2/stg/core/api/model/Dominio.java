package es.caib.sistra2.stg.core.api.model;

/**
 *
 * Dominio.
 *
 * @author Indra
 *
 */
public class Dominio {

	/**
	 * Id.
	 */
	private Long id;

	/**
	 * Codigo.
	 */
	private String codigo;

	/**
	 * Descripcion.
	 */
	private String descripcion;

	/**
	 * Tipo.
	 */
	private String tipo;

	/**
	 * ambito.
	 */
	private String ambito;

	/**
	 * Crea una nueva instancia de Dominio.
	 */
	public Dominio() {
		super();
	}

	/**
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id
	 *            el nuevo valor de id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor de descripcion.
	 *
	 * @return el valor de descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor de descripcion.
	 *
	 * @param descripcion
	 *            el nuevo valor de descripcion
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor de tipo.
	 *
	 * @return el valor de tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Establece el valor de tipo.
	 *
	 * @param tipo
	 *            el nuevo valor de tipo
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene el valor de ambito.
	 *
	 * @return el valor de ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * Establece el valor de ambito.
	 *
	 * @param ambito
	 *            el nuevo valor de ambito
	 */
	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

}
