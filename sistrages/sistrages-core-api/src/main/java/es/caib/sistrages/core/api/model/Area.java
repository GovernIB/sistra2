package es.caib.sistrages.core.api.model;

/**
 *
 * Área.
 *
 * @author Indra
 *
 */

public class Area extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. */
	private Long codigo;

	/** Identificador. */
	private String identificador;

	/** Identificador. */
	private String identificadorCompuesto;

	/** Descripción. */
	private String descripcion;

	/** Email */
	private String email;

	/** Tiene permiso **/
	private Boolean tienePermiso = true;

	/**
	 * codigo DIR 3.
	 */
	private String codigoDIR3Entidad;

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
	 * Obtiene el valor de codigoDIR3Entidad.
	 *
	 * @return el valor de codigoDIR3Entidad
	 */
	public String getCodigoDIR3Entidad() {
		return codigoDIR3Entidad;
	}

	/**
	 * @return the email
	 */
	public final String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public final void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Establece el valor de codigoDIR3Entidad.
	 *
	 * @param codigoDIR3Entidad el nuevo valor de codigoDIR3Entidad
	 */
	public void setCodigoDIR3Entidad(final String codigoDIR3Entidad) {
		this.codigoDIR3Entidad = codigoDIR3Entidad;
	}

	public Boolean getTienePermiso() {
		return tienePermiso;
	}

	public void setTienePermiso(Boolean tienePermiso) {
		this.tienePermiso = tienePermiso;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identificador == null) ? 0 : identificador.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
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
		if (!(obj instanceof Area)) {
			return false;
		}
		final Area other = (Area) obj;
		if (identificador == null) {
			if (other.identificador != null) {
				return false;
			}
		} else if (!identificador.equals(other.identificador)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email))
			return false;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		}
		if (descripcion == null) {
			if (other.descripcion != null) {
				return false;
			}
		} else if (!descripcion.equals(other.descripcion))
			return false;
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
