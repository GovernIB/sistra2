package es.caib.sistrahelp.frontend;

/**
 * @author Indra
 *
 */
public final class ModuleConfig {

	/** Entorno. **/
	private String entorno;

	/** Version. **/
	private String version;

	/** Commit svn. **/
	private String commitSvn;

	/** Commit git. **/
	private String commitGit;

	/**
	 * @return the entorno
	 */
	public String getEntorno() {
		return entorno;
	}

	/**
	 * @param entorno
	 *                    the entorno to set
	 */
	public void setEntorno(final String entorno) {
		this.entorno = entorno;
	}

	/**
	 * @return the entorno
	 */
	public String getEntornoUpper() {
		if (entorno == null) {
			return "";
		} else {
			return entorno.toUpperCase();
		}
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCommitSvn() {
		return commitSvn;
	}

	public void setCommitSvn(String commitSvn) {
		this.commitSvn = commitSvn;
	}

	public String getCommitGit() {
		return commitGit;
	}

	public void setCommitGit(String commitGit) {
		this.commitGit = commitGit;
	}

}
