package es.caib.sistrages.frontend.model;

/**
 * Tramite para el datagrid de mensaje aviso.
 *
 * @author Indra
 *
 */
public class TramiteMensajeAviso {

	/** Tramite. **/
	private String tramite;

	/** Tramite. **/
	private String identificador;

	/** Version tramite. **/
	private String versionTramite;

	/** Area tramite. **/
	private String areaTramite;

	/** Constructor. **/
	public TramiteMensajeAviso(final String iTramite, final String iIdentificador, final String iVersionTramite, final String iAreaTramite) {
		this.tramite = iTramite;
		this.identificador = iIdentificador;
		this.versionTramite = iVersionTramite;
		this.areaTramite = iAreaTramite;
	}

	/**
	 * @return the areaTramite
	 */
	public String getAreaTramite() {
		return areaTramite;
	}

	/**
	 * @param areaTramite
	 *            the areaTramite to set
	 */
	public void setAreaTramite(final String areaTramite) {
		this.areaTramite = areaTramite;
	}

	/**
	 * @return the versionTramite
	 */
	public String getVersionTramite() {
		return versionTramite;
	}

	/**
	 * @param versionTramite
	 *            the versionTramite to set
	 */
	public void setVersionTramite(final String versionTramite) {
		this.versionTramite = versionTramite;
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
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *            the identificador to set
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * Comprueba si los parametros pasados coinciden con los propios.
	 *
	 * @param tramiteSeleccionado
	 * @param versionSeleccionado
	 * @return
	 */
	public boolean contains(final String tramiteSeleccionado, final String versionSeleccionado) {
		boolean coincide;
		if (tramiteSeleccionado.equals(this.tramite) && versionSeleccionado.equals(this.versionTramite)) {
			coincide = true;
		} else {
			coincide = false;
		}
		return coincide;
	}

}
