/**
 *
 */
package es.caib.sistrages.frontend.model.comun;

/**
 * @author Indra
 *
 */
public final class ModuleConfig {

	/** Entorno. **/
	private String entorno;

	/** Version. **/
	private String version;

	/** Commit. **/
	private String commit;

	/**
	 * @return the entorno
	 */
	public String getEntorno() {
		return entorno;
	}

	/**
	 * @param entorno
	 *            the entorno to set
	 */
	public void setEntorno(final String entorno) {
		this.entorno = entorno;
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
	 * @return the commit
	 */
	public String getCommit() {
		return commit;
	}

	/**
	 * @param commit
	 *            the commit to set
	 */
	public void setCommit(final String commit) {
		this.commit = commit;
	}

}
