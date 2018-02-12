package es.caib.sistrages.core.api.model;

/**
 *
 * Entidad.
 *
 * @author Indra
 *
 */
public class Entidad {

	/**
	 * Codigo.
	 */
	private Long codigo;

	/**
	 * Descripcion.
	 */
	private String descripcion;


	/**
	 * Logo.
	 */
	private String logo;

	/**
	 * Rol.
	 */
	private String rol;

	/**
	 *  Activo.
	 */
	private boolean activo;



	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public boolean getActivo() {
		return activo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}


}
