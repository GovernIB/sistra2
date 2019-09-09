package es.caib.sistrages.core.api.model.comun;

/**
 * Fila importar.
 *
 * @author Indra
 *
 */
public class FilaImportarResultado {

	/** Id area. */
	private Long idArea;

	/** Id tramite. **/
	private Long idTramite;

	/** Id tramite version. **/
	private Long idTramiteVersion;

	/**
	 * @return the idArea
	 */
	public final Long getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea the idArea to set
	 */
	public final void setIdArea(final Long idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the idTramite
	 */
	public final Long getIdTramite() {
		return idTramite;
	}

	/**
	 * @param idTramite the idTramite to set
	 */
	public final void setIdTramite(final Long idTramite) {
		this.idTramite = idTramite;
	}

	/**
	 * @return the idTramiteVersion
	 */
	public final Long getIdTramiteVersion() {
		return idTramiteVersion;
	}

	/**
	 * @param idTramiteVersion the idTramiteVersion to set
	 */
	public final void setIdTramiteVersion(final Long idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}

}
