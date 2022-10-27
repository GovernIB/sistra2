package es.caib.sistrages.core.api.model;

import java.util.Date;

/**
 * Seccion reutilizable.
 *
 * @author Indra
 *
 */

public final class SeccionReutilizable  extends ModelApi {

	/** Serial Version UID */
	private static final long serialVersionUID = 1L;

	/** Codigo **/
	private Long codigo;

	/** Id entidad */
	private Long idEntidad;

	/** Identificiador enidad **/
	private String identificadorEntidad;

	/** Formulario asociado **/
	private Long idFormularioAsociado;

	/** Identificador **/
	private String identificador;

	/** Descripcion **/
	private String descripcion;

	/** Bloqueado **/
	private boolean activado;

	/** Bloqueado **/
	private boolean bloqueado;

	/** Bloqueado usuario **/
	private String bloqueadoUsuario;

	/** Release **/
	private int release;

	/** Huella **/
	private String huella;

	/** Fecha **/
	private Date fecha;

	/** Disenyo formulario para la exportacion/importacion/carga **/
	private DisenyoFormulario disenyoFormulario;

	/**
	 * Crea una nueva instancia de ComponenteFormularioSeccion.
	 */
	public SeccionReutilizable() {
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
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	/**
	 * @return the idEntidad
	 */
	public Long getIdEntidad() {
		return idEntidad;
	}


	/**
	 * @param idEntidad the idEntidad to set
	 */
	public void setIdEntidad(Long idEntidad) {
		this.idEntidad = idEntidad;
	}


	/**
	 * @return the identificadorEntidad
	 */
	public String getIdentificadorEntidad() {
		return identificadorEntidad;
	}


	/**
	 * @param identificadorEntidad the identificadorEntidad to set
	 */
	public void setIdentificadorEntidad(String identificadorEntidad) {
		this.identificadorEntidad = identificadorEntidad;
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
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
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
	 * @return the bloqueado
	 */
	public boolean isBloqueado() {
		return bloqueado;
	}


	/**
	 * @param bloqueado the bloqueado to set
	 */
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}


	/**
	 * @return the bloqueadoUsuario
	 */
	public String getBloqueadoUsuario() {
		return bloqueadoUsuario;
	}


	/**
	 * @param bloqueadoUsuario the bloqueadoUsuario to set
	 */
	public void setBloqueadoUsuario(String bloqueadoUsuario) {
		this.bloqueadoUsuario = bloqueadoUsuario;
	}


	/**
	 * @return the release
	 */
	public int getRelease() {
		return release;
	}


	/**
	 * @param release the release to set
	 */
	public void setRelease(int release) {
		this.release = release;
	}


	/**
	 * @return the huella
	 */
	public String getHuella() {
		return huella;
	}


	/**
	 * @param huella the huella to set
	 */
	public void setHuella(String huella) {
		this.huella = huella;
	}


	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}


	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	/**
	 * @return the idFormularioAsociado
	 */
	public Long getIdFormularioAsociado() {
		return idFormularioAsociado;
	}


	/**
	 * @param idFormularioAsociado the idFormularioAsociado to set
	 */
	public void setIdFormularioAsociado(Long idFormularioAsociado) {
		this.idFormularioAsociado = idFormularioAsociado;
	}


	/**
	 * @return the activado
	 */
	public boolean isActivado() {
		return activado;
	}


	/**
	 * @param activado the activado to set
	 */
	public void setActivado(boolean activado) {
		this.activado = activado;
	}

	/**
	 * @return the disenyoFormulario
	 */
	public DisenyoFormulario getDisenyoFormulario() {
		return disenyoFormulario;
	}


	/**
	 * @param disenyoFormulario the disenyoFormulario to set
	 */
	public void setDisenyoFormulario(DisenyoFormulario disenyoFormulario) {
		this.disenyoFormulario = disenyoFormulario;
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
		if (!(obj instanceof SeccionReutilizable)) {
			return false;
		}
		final SeccionReutilizable other = (SeccionReutilizable) obj;
		if (identificador == null) {
			if (other.identificador != null) {
				return false;
			}
		} else if (!identificador.equals(other.identificador)) {
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
