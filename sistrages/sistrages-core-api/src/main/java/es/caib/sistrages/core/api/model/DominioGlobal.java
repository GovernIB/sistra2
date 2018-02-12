package es.caib.sistrages.core.api.model;

import java.util.List;

import es.caib.sistrages.core.api.model.comun.Propiedad;

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
	 * Cacheable.
	 */
	private boolean cacheable;

	/**
	 * JDNI.
	 */
	private String jdni;

	/**
	 * Query.
	 */
	private String query;

	/**
	 * Propiedades
	 */
	private List<Propiedad> propiedades;

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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the cacheable
	 */
	public boolean isCacheable() {
		return cacheable;
	}

	/**
	 * @param cacheable
	 *            the cacheable to set
	 */
	public void setCacheable(final boolean cacheable) {
		this.cacheable = cacheable;
	}

	/**
	 * @return the jdni
	 */
	public String getJdni() {
		return jdni;
	}

	/**
	 * @param jdni
	 *            the jdni to set
	 */
	public void setJdni(final String jdni) {
		this.jdni = jdni;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            the query to set
	 */
	public void setQuery(final String query) {
		this.query = query;
	}

	/**
	 * @return the propiedades
	 */
	public List<Propiedad> getPropiedades() {
		return propiedades;
	}

	/**
	 * @param propiedades
	 *            the propiedades to set
	 */
	public void setPropiedades(final List<Propiedad> propiedades) {
		this.propiedades = propiedades;
	}

}
