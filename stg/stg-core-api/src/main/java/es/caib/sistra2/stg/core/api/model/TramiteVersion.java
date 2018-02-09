package es.caib.sistra2.stg.core.api.model;

/**
 *
 * Trámite versión.
 *
 * @author Indra
 *
 */
public class TramiteVersion {

	/**
	 * Id.
	 */
	private Long id;

	/**
	 * Version.
	 */
	private String version;

	/**
	 * Descripcion.
	 */
	private String descripcion;

	/**
	 * Flujo
	 */
	private String flujo;

	/**
	 * Activo
	 */
	private Boolean activa;

	/**
	 * Release
	 */
	private String release;

	/**
	 * Bloqueado por
	 */
	private String bloqueado;

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
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(final String version) {
		this.version = version;
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
	 * @return the flujo
	 */
	public String getFlujo() {
		return flujo;
	}

	/**
	 * @param flujo
	 *            the flujo to set
	 */
	public void setFlujo(final String flujo) {
		this.flujo = flujo;
	}

	/**
	 * @return the activa
	 */
	public Boolean getActiva() {
		return activa;
	}

	/**
	 * @param activa
	 *            the activa to set
	 */
	public void setActiva(final Boolean activa) {
		this.activa = activa;
	}

	/**
	 * @return the release
	 */
	public String getRelease() {
		return release;
	}

	/**
	 * @param release
	 *            the release to set
	 */
	public void setRelease(final String release) {
		this.release = release;
	}

	/**
	 * @return the bloqueado
	 */
	public String getBloqueado() {
		return bloqueado;
	}

	/**
	 * @param bloqueado
	 *            the bloqueado to set
	 */
	public void setBloqueado(final String bloqueado) {
		this.bloqueado = bloqueado;
	}

}
