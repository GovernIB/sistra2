package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Dominio.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RDominio", description = "Descripcion de RDominio")
public class RDominio {

	/** Timestamp recuperacion. */
	@ApiModelProperty(value = "Timestamp recuperacion")
	private String timestamp;

	/** Identificador. */
	@ApiModelProperty(value = "Identificador")
	private String identificador;

	/** Cacheo implicito. */
	@ApiModelProperty(value = "Tipo cacheo: explícito - 24 h (E) / implícito - 1 min (I) / no cache (N)")
	private String tipoCache;

	/**
	 * Tipo dominio: Lista fija (L),Consulta BD (B), Consulta remota (R) y Fuente
	 * datos (F).
	 */
	@ApiModelProperty(value = "Tipo dominio: Lista fija (L),Consulta BD (B), Consulta remota (R) y Fuente datos (F)")
	private String tipo;

	public static final String TIPO_LISTA_LISTA = "L";
	public static final String TIPO_CONSULTA_BD = "B";
	public static final String TIPO_CONSULTA_REMOTA = "R";
	public static final String TIPO_FUENTE_DATOS = "F";

	/** JNDI BD / URL para BD / Consulta remota. */
	@ApiModelProperty(value = "JNDI BD / URL para BD / Consulta remota")
	private String uri;

	/** Query para BD. */
	@ApiModelProperty(value = "Query para BD")
	private String sql;

	/**
	 * Método de acceso a identificador.
	 *
	 * @return identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Método para establecer identificador.
	 *
	 * @param identificador identificador a establecer
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param tipo tipo a establecer
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Método de acceso a uri.
	 *
	 * @return uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Método para establecer uri.
	 *
	 * @param uri uri a establecer
	 */
	public void setUri(final String uri) {
		this.uri = uri;
	}

	/**
	 * Método de acceso a sql.
	 *
	 * @return sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * Método para establecer sql.
	 *
	 * @param sql sql a establecer
	 */
	public void setSql(final String sql) {
		this.sql = sql;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(final String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Método de acceso a tipoCache.
	 *
	 * @return tipoCache
	 */
	public String getTipoCache() {
		return tipoCache;
	}

	/**
	 * Método para establecer tipoCache.
	 *
	 * @param tipoCache tipoCache a establecer
	 */
	public void setTipoCache(final String tipoCache) {
		this.tipoCache = tipoCache;
	}

}
