package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 *
 * Configuracion Autenticacion.
 *
 * @author Indra
 *
 */
public class ConfiguracionAutenticacion extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Id. */
	private Long codigo;

	/** Ámbito dominio (G : Global / E: Entidad / A: Área) */
	private TypeAmbito ambito;

	/** Si el ambito es entidad **/
	private Long entidad;

	/** Si el ambito es entidad **/
	private Long area;

	/**
	 * Id que no se mapea, es sólo para saber que es en la creación de la
	 * importacion
	 **/
	private Long codigoImportacion;

	/** Identificador **/
	private String identificador;

	/** Identificador compuesto**/
	private String identificadorCompuesto;

	/** Descripcion **/
	private String descripcion;

	/** Usuario **/
	private String usuario;

	/** Pasword **/
	private String password;

	public ConfiguracionAutenticacion() {
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
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the codigoImportacion
	 */
	public Long getCodigoImportacion() {
		return codigoImportacion;
	}

	/**
	 * @param codigoImportacion the codigoImportacion to set
	 */
	public void setCodigoImportacion(Long codigoImportacion) {
		this.codigoImportacion = codigoImportacion;
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
	public void setAmbito(TypeAmbito ambito) {
		this.ambito = ambito;
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
	public void setEntidad(Long entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the area
	 */
	public final Long getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public final void setArea(Long area) {
		this.area = area;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ConfiguracionAutenticacion)) {
			return false;
		}
		final ConfiguracionAutenticacion other = (ConfiguracionAutenticacion) obj;
		if (identificador == null) {
			if (other.identificador != null) {
				return false;
			}
		} else if (!identificador.equals(other.identificador)) {
			return false;
		}
		if (descripcion == null) {
			if (other.descripcion != null) {
				return false;
			}
		} else if (!descripcion.equals(other.descripcion)) {
			return false;
		}
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} else if (!codigo.equals(other.codigo)) {
			return false;
		}
		return true;
	}
}
