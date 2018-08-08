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

    /** Identificador. */
	@ApiModelProperty(value = "Identificador")
    private String identificador;

    /** Cachear. */
	@ApiModelProperty(value = "Cachear")
    private boolean cachear;

    /**
     * Tipo dominio: Lista fija (L),Consulta BD (B), Consulta remota (R) y
     * Fuente datos (F).
     */
	@ApiModelProperty(value = "Tipo dominio: Lista fija (L),Consulta BD (B), Consulta remota (R) y Fuente datos (F)")
    private String tipo;

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
     * @param identificador
     *            identificador a establecer
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Método de acceso a cachear.
     *
     * @return cachear
     */
    public boolean isCachear() {
        return cachear;
    }

    /**
     * Método para establecer cachear.
     *
     * @param cachear
     *            cachear a establecer
     */
    public void setCachear(boolean cachear) {
        this.cachear = cachear;
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
     *            tipo a establecer
     */
    public void setTipo(String tipo) {
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
     *            uri a establecer
     */
    public void setUri(String uri) {
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
     *            sql a establecer
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

}
