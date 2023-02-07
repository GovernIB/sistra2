package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 * Dominio.
 *
 * @author Indra
 *
 */

public class EnvioRemoto extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Id. */
	private Long codigo;

	/** Ámbito dominio (E: Entidad / A: Área) */
	private TypeAmbito ambito;

	/** Identificador. **/
	private String identificador;

	/** Identificador. **/
	private String identificadorCompuesto;

	/** Descripcion. */
	private String descripcion;

	/**
	 * Para tipo Remoto indica URL de acceso al servicio remoto de consulta de
	 * dominio.
	 */
	private String url;

	/** Areas. **/
	private Area area;

	/** Id Area. **/
	private String idArea;

	/** Si el ambito es entidad **/
	private Entidad entidad;

	/** Timeout **/
	private Long timeout;

	/** Configuracion Autenticacion **/
	private ConfiguracionAutenticacion configuracionAutenticacion;

	/**
	 * Crea una nueva instancia de Dominio.
	 */
	public EnvioRemoto() {
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

	/**
	 * @return the entidad
	 */
	public Entidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public void setEntidad(final Entidad entidad) {
		this.entidad = entidad;
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

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     ***/
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "EnvioRemoto. ");
           texto.append(tabulacion +"\t Codi:" + getCodigo() + "\n");
           texto.append(tabulacion +"\t Ambito:" + ambito + "\n");
           texto.append(tabulacion +"\t Identificador:" + identificador + "\n");
           texto.append(tabulacion +"\t IdentificadorCompost:" + identificadorCompuesto + "\n");
           texto.append(tabulacion +"\t Descripcio:" + descripcion + "\n");
           return texto.toString();
     }

}
