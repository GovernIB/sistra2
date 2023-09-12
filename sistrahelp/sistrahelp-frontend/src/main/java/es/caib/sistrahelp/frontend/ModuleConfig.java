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

	/** Filtro inicial. **/
	private String filtroInicial;

	/** Hora desde. **/
	private String horaDesde;

	/** Minutos refresco. **/
	private String minutosRefresco;

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

	public final String getFiltroInicial() {
		return filtroInicial;
	}

	public final void setFiltroInicial(String filtroInicial) {
		this.filtroInicial = filtroInicial;
	}

	/**
	 * @return the horaDesde
	 */
	public String getHoraDesde() {
		return horaDesde;
	}

	/**
	 * @param horaDesde the horaDesde to set
	 */
	public void setHoraDesde(String horaDesde) {
		this.horaDesde = horaDesde;
	}

	/**
	 * @return the minutosRefresco
	 */
	public String getMinutosRefresco() {
		return minutosRefresco;
	}

	/**
	 * @param minutosRefresco the minutosRefresco to set
	 */
	public void setMinutosRefresco(String minutosRefresco) {
		this.minutosRefresco = minutosRefresco;
	}

}
