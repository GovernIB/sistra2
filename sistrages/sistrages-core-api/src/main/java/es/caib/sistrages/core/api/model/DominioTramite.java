package es.caib.sistrages.core.api.model;

/**
 * Dominio tramite.
 *
 * @author slromero
 */
public class DominioTramite {

	/** Id tramite version. **/
	private Long idTramiteVersion;

	/** Tramite. **/
	private String tramite;

	/** Area. **/
	private String area;

	/** Num version. */
	private int numVersion;

	/** Release. **/
	private Integer release;

	/** Entidad. **/
	private Literal entidad;

	/**
	 * @return the idTramiteVersion
	 */
	public String getTramiteVersion() {
		if (idTramiteVersion == null) {
			return "";
		} else {
			return idTramiteVersion.toString();
		}
	}

	/**
	 * @param tramiteVersion
	 *            the idTramiteVersion to set
	 */
	public void setTramiteVersion(final String tramiteVersion) {
		if (tramiteVersion == null || tramiteVersion.isEmpty()) {
			this.idTramiteVersion = null;
		} else {
			this.idTramiteVersion = Long.valueOf(tramiteVersion);
		}
	}

	/**
	 * @return the idTramiteVersion
	 */
	public Long getIdTramiteVersion() {
		return idTramiteVersion;
	}

	/**
	 * @param idTramiteVersion
	 *            the idTramiteVersion to set
	 */
	public void setIdTramiteVersion(final Long idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}

	/**
	 * @return the tramite
	 */
	public String getTramite() {
		return tramite;
	}

	/**
	 * @param tramite
	 *            the tramite to set
	 */
	public void setTramite(final String tramite) {
		this.tramite = tramite;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(final String area) {
		this.area = area;
	}

	/**
	 * @return the numVersion
	 */
	public int getNumVersion() {
		return numVersion;
	}

	/**
	 * @param numVersion
	 *            the numVersion to set
	 */
	public void setNumVersion(final int numVersion) {
		this.numVersion = numVersion;
	}

	/**
	 * @return the release
	 */
	public Integer getRelease() {
		return release;
	}

	/**
	 * @param release
	 *            the release to set
	 */
	public void setRelease(final Integer release) {
		this.release = release;
	}

	/**
	 * @return the entidad
	 */
	public Literal getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public void setEntidad(final Literal entidad) {
		this.entidad = entidad;
	}

}
