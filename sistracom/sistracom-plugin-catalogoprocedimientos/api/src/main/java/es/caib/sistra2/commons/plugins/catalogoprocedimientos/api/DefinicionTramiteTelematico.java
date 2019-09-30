package es.caib.sistra2.commons.plugins.catalogoprocedimientos.api;

public class DefinicionTramiteTelematico {

	/** Identificador del tramite. **/
	private String tramiteIdentificador;

	/** Identificador de la version. **/
	private Integer tramiteVersion;

	/**
	 * @return the tramiteIdentificador
	 */
	public String getTramiteIdentificador() {
		return tramiteIdentificador;
	}

	/**
	 * @param tramiteIdentificador the tramiteIdentificador to set
	 */
	public void setTramiteIdentificador(final String tramiteIdentificador) {
		this.tramiteIdentificador = tramiteIdentificador;
	}

	/**
	 * @return the tramiteVersion
	 */
	public Integer getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion the tramiteVersion to set
	 */
	public void setTramiteVersion(final Integer tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}
}
