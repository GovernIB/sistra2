/**
 *
 */
package es.caib.sistramit.frontend.model.comun;

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
	 * @param entorno the entorno to set
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
	 * @param version the version to set
	 */
	public void setVersion(final String version) {
		this.version = version;
	}

	/**
	 * @return the commit
	 */
	public String getCommitSvn() {
		return commitSvn;
	}

	/**
	 * @param commit the commit to set
	 */
	public void setCommitSvn(final String commit_svn) {
		this.commitSvn = commit_svn;
	}

	public String getCommitGit() {
		return commitGit;
	}

	public void setCommitGit(final String commit_git) {
		this.commitGit = commit_git;
	}

}
