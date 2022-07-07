package es.caib.sistrages.core.api.model;

import java.util.List;

import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeCache;
import es.caib.sistrages.core.api.model.types.TypeDominio;

/**
 * Dominio.
 *
 * @author Indra
 *
 */

public class Dominio extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Id. */
	private Long codigo;

	/** Ámbito dominio (G : Global / E: Entidad / A: Área) */
	private TypeAmbito ambito;

	/** Identificador. **/
	private String identificador;

	/** Identificador. **/
	private String identificadorCompuesto;

	/** Descripcion. */
	private String descripcion;

	/** Indica si se realiza cacheo dominio. */
	private TypeCache cache;

	/**
	 * Tipo dominio (B: Base datos / F: Fuente datos / L: Lista fija datos / R:
	 * Remota)
	 */
	private TypeDominio tipo;

	/** Para tipo Base datos indica JNDI Datasource. */
	private String jndi;

	/** Para tipo Base datos indica SQL */
	private String sql;

	private String sqlDecoded;

	/** Para tipo Fuente de datos indica el ID de la Fuente de datos */
	private Long idFuenteDatos;
	private String identificadorFD;

	/**
	 * JSON con la lista de valores (identificador - valor)
	 */
	private List<Propiedad> listaFija;

	/**
	 * Para tipo Remoto indica URL de acceso al servicio remoto de consulta de
	 * dominio.
	 */
	private String url;

	/** JSON con la lista de parametros (identificador - valor). */
	private List<Propiedad> parametros;

	/** Areas. **/
	private Area area;

	/** Id Area. **/
	private String idArea;

	/** Si el ambito es entidad **/
	private Long entidad;

	/** Timeout **/
	private Long timeout;

	/** Configuracion Autenticacion **/
	private ConfiguracionAutenticacion configuracionAutenticacion;

	/**
	 * Crea una nueva instancia de Dominio.
	 */
	public Dominio() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the ambito
	 */
	public TypeAmbito getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito the ambito to set
	 */
	public void setAmbito(final TypeAmbito ambito) {
		this.ambito = ambito;
	}

	/**
	 * @return the codigo
	 */
	public String getIdString() {
		if (codigo == null) {
			return null;
		} else {
			return String.valueOf(codigo);
		}
	}

	/**
	 * @param idString the codigo to set
	 */
	public void setIdString(final String idString) {
		if (idString == null) {
			this.codigo = null;
		} else {
			this.codigo = Long.valueOf(idString);
		}
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(final String codigo) {
		this.identificador = codigo;
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
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the tipo
	 */
	public TypeDominio getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
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
	 * @param jndi the jndi to set
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
	 * @param sql the sql to set
	 */
	public void setSql(final String sql) {
		this.sql = sql;
	}

	/**
	 * @return the idFuenteDatos
	 */
	public Long getIdFuenteDatos() {
		return idFuenteDatos;
	}

	/**
	 * @param idFuenteDatos the idFuenteDatos to set
	 */
	public void setIdFuenteDatos(final Long fuenteDatos) {
		this.idFuenteDatos = fuenteDatos;
	}

	/**
	 * @return the listaFija
	 */
	public List<Propiedad> getListaFija() {
		return listaFija;
	}

	/**
	 * @param listaFija the listaFija to set
	 */
	public void setListaFija(final List<Propiedad> listaFija) {
		this.listaFija = listaFija;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the parametros
	 */
	public List<Propiedad> getParametros() {
		return parametros;
	}

	/**
	 * @param parametros the parametros to set
	 */
	public void setParametros(final List<Propiedad> parametros) {
		this.parametros = parametros;
	}

	/**
	 * @return the areas
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param areas the areas to set
	 */
	public void setArea(final Area areas) {
		this.area = areas;
	}

	public String getSqlDecoded() {
		return sqlDecoded;
	}

	public void setSqlDecoded(final String sqlDecoded) {
		this.sqlDecoded = sqlDecoded;
	}

	/**
	 * @return the entidad
	 */
	public Long getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public void setEntidad(final Long entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the cache
	 */
	public TypeCache getCache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(final TypeCache cache) {
		this.cache = cache;
	}

	/**
	 * @return the timeout
	 */
	public Long getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the configuracionAutenticacion
	 */
	public ConfiguracionAutenticacion getConfiguracionAutenticacion() {
		return configuracionAutenticacion;
	}

	/**
	 * @param configuracionAutenticacion the configuracionAutenticacion to set
	 */
	public void setConfiguracionAutenticacion(ConfiguracionAutenticacion configuracionAutenticacion) {
		this.configuracionAutenticacion = configuracionAutenticacion;
	}

	/**
	 * @return the identificadorCompuesto
	 */
	public String getIdentificadorCompuesto() {
		return identificadorCompuesto;
	}

	/**
	 * @param identificadorCompuesto the identificadorCompuesto to set
	 */
	public void setIdentificadorCompuesto(String identificadorCompuesto) {
		this.identificadorCompuesto = identificadorCompuesto;
	}

	/**
	 * @return the idArea
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea the idArea to set
	 */
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the identificadorFD
	 */
	public String getIdentificadorFD() {
		return identificadorFD;
	}

	/**
	 * @param identificadorFD the identificadorFD to set
	 */
	public void setIdentificadorFD(String identificadorFD) {
		this.identificadorFD = identificadorFD;
	}

}
