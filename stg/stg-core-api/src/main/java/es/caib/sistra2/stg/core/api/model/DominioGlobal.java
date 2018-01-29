package es.caib.sistra2.stg.core.api.model;

/**
 *
 * Dominio global.
 *
 * @author Indra
 *
 */
public class DominioGlobal {

	/**
	 * Codigo.
	 */
	private Long codigo;

	/**
	 * Descripcion.
	 */
	private String descripcion;


	/**
	 * Tipo.
	 */
	private String tipo;

	/**
	 *  Cacheable.
	 */
	private boolean cacheable;

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the cacheable
	 */
	public boolean isCacheable() {
		return cacheable;
	}

	/**
	 * @param cacheable the cacheable to set
	 */
	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}



}
