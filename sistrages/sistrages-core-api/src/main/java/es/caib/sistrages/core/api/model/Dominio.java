package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;

/**
 * Dominio.
 *
 * @author Indra
 *
 */
public class Dominio {

	/** Id. */
	private Long id;

	/** Ámbito dominio (G : Global / E: Entidad / A: Área) */
	private String ambito;

	/** Ámbito dominio (G : Global / E: Entidad / A: Área) */
	private TypeAmbito typeAmbito;

	/** Identificador. **/
	private String codigo;

	/** Descripcion. */
	private String descripcion;

	/** Indica si se realiza cacheo dominio. */
	private boolean cacheable;

	/**
	 * Tipo dominio (B: Base datos / F: Fuente datos / L: Lista fija datos / R:
	 * Remota)
	 */
	private TypeDominio tipo;

	/** Para tipo Base datos indica JNDI Datasource. */
	private String jndi;

	/** Para tipo Base datos indica SQL */
	private String sql;

	/** Para tipo Fuente de datos indica el ID de la Fuente de datos */
	private Fuente fuenteDatos;

	/** Query para la fuente de datos. **/
	private String fuenteDatosQuery;

	/**
	 * JSON con la lista de valores (codigo - valor)
	 */
	private String listaFija;

	/**
	 * Para tipo Remoto indica URL de acceso al servicio remoto de consulta de
	 * dominio.
	 */
	private String url;

	/** JSON con la lista de parametros (codigo - valor). */
	private String parametros;

	/**
	 * Crea una nueva instancia de Dominio.
	 */
	public Dominio() {
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
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito
	 *            the ambito to set
	 */
	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

	/**
	 * @return the typeAmbito
	 */
	public TypeAmbito getTypeAmbito() {
		return typeAmbito;
	}

	/**
	 * @param typeAmbito
	 *            the typeAmbito to set
	 */
	public void setTypeAmbito(final TypeAmbito typeAmbito) {
		this.typeAmbito = typeAmbito;
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
	 * @return the tipo
	 */
	public TypeDominio getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final TypeDominio tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the jndi
	 */
	public String getJndi() {
		return jndi;
	}

	/**
	 * @param jndi
	 *            the jndi to set
	 */
	public void setJndi(final String jndi) {
		this.jndi = jndi;
	}

	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sql
	 *            the sql to set
	 */
	public void setSql(final String sql) {
		this.sql = sql;
	}

	/**
	 * @return the fuenteDatos
	 */
	public Fuente getFuenteDatos() {
		return fuenteDatos;
	}

	/**
	 * @param fuenteDatos
	 *            the fuenteDatos to set
	 */
	public void setFuenteDatos(final Fuente fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	/**
	 * @return the fuenteDatosQuery
	 */
	public String getFuenteDatosQuery() {
		return fuenteDatosQuery;
	}

	/**
	 * @param fuenteDatosQuery
	 *            the fuenteDatosQuery to set
	 */
	public void setFuenteDatosQuery(final String fuenteDatosQuery) {
		this.fuenteDatosQuery = fuenteDatosQuery;
	}

	/**
	 * @return the listaFija
	 */
	public String getListaFija() {
		return listaFija;
	}

	/**
	 * @param listaFija
	 *            the listaFija to set
	 */
	public void setListaFija(final String listaFija) {
		this.listaFija = listaFija;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the parametros
	 */
	public String getParametros() {
		return parametros;
	}

	/**
	 * @param parametros
	 *            the parametros to set
	 */
	public void setParametros(final String parametros) {
		this.parametros = parametros;
	}

}
