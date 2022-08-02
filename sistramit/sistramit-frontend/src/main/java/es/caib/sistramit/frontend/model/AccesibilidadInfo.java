package es.caib.sistramit.frontend.model;

/**
 * Información para mostrar accesibilidad.
 *
 * @author Indra
 *
 */
public final class AccesibilidadInfo {

	/** Id sesion tramitación. */
	private String idSesionTramitacion;

	/** Idioma. */
	private String idioma;

	/** Commit. **/
	private String commit;

	/** Entorno. **/
	private String version;

	/**
	 * Método de acceso a idSesionTramitacion.
	 *
	 * @return idSesionTramitacion
	 */
	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	/**
	 * Método para establecer idSesionTramitacion.
	 *
	 * @param idSesionTramitacion
	 *                                idSesionTramitacion a establecer
	 */
	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	/**
	 * Método de acceso a idioma.
	 *
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método para establecer idioma.
	 *
	 * @param idioma
	 *                   idioma a establecer
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the commit
	 */
	public final String getCommit() {
		return commit;
	}

	/**
	 * @param commit
	 *                   the commit to set
	 */
	public final void setCommit(final String commit) {
		this.commit = commit;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *                    the version to set
	 */
	public void setVersion(final String version) {
		this.version = version;
	}

}
