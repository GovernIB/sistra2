/**
 *
 */
package es.caib.sistrages.frontend;

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

	/** promocionDesa2Pro **/
	private String promocionSe2Pro;

	/** admetreServeis **/
	private String admetreServeis;

	/**
	 * @return the admetreServeis
	 */
	public final String getAdmetreServeis() {
		return admetreServeis;
	}

	/**
	 * @param admetreServeis the admetreServeis to set
	 */
	public final void setAdmetreServeis(String admetreServeis) {
		this.admetreServeis = admetreServeis;
	}

	/**
	 * @return the entorno
	 */
	public String getEntorno() {
		return entorno;
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

	/**
	 * @return the promocionSe2Pro
	 */
	public String getPromocionSe2Pro() {
		return promocionSe2Pro;
	}

	/**
	 * @param promocionDesa2Pro the promocionSe2Pro to set
	 */
	public void setPromocionSe2Pro(String promocionSe2Pro) {
		this.promocionSe2Pro = promocionSe2Pro;
	}

}
