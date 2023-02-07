package es.caib.sistrages.rest.api.interna;

import java.util.List;

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

	/** Tipos dominio. */
	public static final String TIPO_LISTA_LISTA = "L";
	public static final String TIPO_CONSULTA_BD = "B";
	public static final String TIPO_CONSULTA_REMOTA = "R";
	public static final String TIPO_FUENTE_DATOS = "F";

	/** Timestamp recuperacion. */
	@ApiModelProperty(value = "Timestamp recuperacion")
	private String timestamp;

	/** Identificador. */
	@ApiModelProperty(value = "Identificador")
	private String identificador;

	/** Ámbito: GLOBAL("G"), ENTIDAD("E"), AREA("A"). */
	@ApiModelProperty(value = "Ámmbito")
	private String ambito;

	/** Identificador entidad (nulo si global). */
	@ApiModelProperty(value = "Identificador entidad (Código DIR3). Será nulo si ámbito global")
	private String identificadorEntidad;

	/** Identificador área (nulo si global). */
	@ApiModelProperty(value = "Identificador área. Será nulo si ámbito global")
	private String identificadorArea;

	/** Cacheo implicito. */
	@ApiModelProperty(value = "Tipo cacheo: explícito - 24 h (E) / implícito - 1 min (I) / no cache (N)")
	private String tipoCache;

	/**
	 * Tipo dominio: Lista fija (L),Consulta BD (B), Consulta remota (R) y Fuente
	 * datos (F).
	 */
	@ApiModelProperty(value = "Tipo dominio: Lista fija (L),Consulta BD (B), Consulta remota (R) y Fuente datos (F)")
	private String tipo;

	/** JNDI BD / URL para BD / Consulta remota. */
	@ApiModelProperty(value = "JNDI BD / URL para BD / Consulta remota")
	private String uri;

	/** Query para BD. */
	@ApiModelProperty(value = "Query para BD")
	private String sql;

	/** Configuracion autenticacion. */
	@ApiModelProperty(value = "Configuracion autenticacion")
	private String identificadorConfAutenticacion;

	/** Timeout (para dominios remotos). */
	@ApiModelProperty(value = "Timeout en segundos (para dominios remotos)")
	private Long timeout;

	/** Lista parámetros. */
	@ApiModelProperty(value = "Lista de parámetros")
	private List<String> parametros;

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
	 * @param identificador
	 *                          identificador a establecer
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
	 * @param tipo
	 *                 tipo a establecer
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
	 * @param uri
	 *                uri a establecer
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
	 * @param sql
	 *                sql a establecer
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
	 * @param timestamp
	 *                      the timestamp to set
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
	 * @param tipoCache
	 *                      tipoCache a establecer
	 */
	public void setTipoCache(final String tipoCache) {
		this.tipoCache = tipoCache;
	}

	/**
	 * Método de acceso a timeout.
	 *
	 * @return timeout
	 */
	public Long getTimeout() {
		return timeout;
	}

	/**
	 * Método para establecer timeout.
	 *
	 * @param timeout
	 *                    timeout a establecer
	 */
	public void setTimeout(final Long timeout) {
		this.timeout = timeout;
	}

	/**
	 * Método de acceso a idConfiguracionAutenticacion.
	 *
	 * @return idConfiguracionAutenticacion
	 */
	public String getIdentificadorConfAutenticacion() {
		return identificadorConfAutenticacion;
	}

	/**
	 * Método para establecer idConfiguracionAutenticacion.
	 *
	 * @param idConfiguracionAutenticacion
	 *                                         idConfiguracionAutenticacion a
	 *                                         establecer
	 */
	public void setIdentificadorConfAutenticacion(final String idConfiguracionAutenticacion) {
		this.identificadorConfAutenticacion = idConfiguracionAutenticacion;
	}

	/**
	 * Método de acceso a identificadorEntidad.
	 *
	 * @return identificadorEntidad
	 */
	public String getIdentificadorEntidad() {
		return identificadorEntidad;
	}

	/**
	 * Método para establecer identificadorEntidad.
	 *
	 * @param identificadorEntidad
	 *                                 identificadorEntidad a establecer
	 */
	public void setIdentificadorEntidad(final String identificadorEntidad) {
		this.identificadorEntidad = identificadorEntidad;
	}

	/**
	 * Método de acceso a parametros.
	 *
	 * @return parametros
	 */
	public List<String> getParametros() {
		return parametros;
	}

	/**
	 * Método para establecer parametros.
	 *
	 * @param parametros
	 *                       parametros a establecer
	 */
	public void setParametros(final List<String> parametros) {
		this.parametros = parametros;
	}

	/**
	 * Método de acceso a ambito.
	 *
	 * @return ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * Método para establecer ambito.
	 *
	 * @param ambito
	 *                   ambito a establecer
	 */
	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

	/**
	 * Método de acceso a identificadorArea.
	 * 
	 * @return identificadorArea
	 */
	public String getIdentificadorArea() {
		return identificadorArea;
	}

	/**
	 * Método para establecer identificadorArea.
	 * 
	 * @param identificadorArea
	 *                              identificadorArea a establecer
	 */
	public void setIdentificadorArea(final String identificadorArea) {
		this.identificadorArea = identificadorArea;
	}

}
