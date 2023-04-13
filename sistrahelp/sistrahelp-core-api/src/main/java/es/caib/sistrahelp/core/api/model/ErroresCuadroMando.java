package es.caib.sistrahelp.core.api.model;

import java.util.List;

/**
 *
 * ErroresCuadroMando.
 *
 * @author Indra
 *
 */

public class ErroresCuadroMando extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Identificador Tramite. */
	private String identificadorTramite;

	/** Errores version. */
	private Integer version;

	/** Errores tramite. */
	private int errTram;

	/** Sesiones Ok */
	private Integer sesOk;

	/** Sesiones Ko */
	private Integer sesKo;

	/** Sesiones Inacabadas */
	private Integer sesInac;

	/** Sesiones Porcentage Error */
	private Double sesPor;

	/** Lista Errores Trámite. */
	private List<ErroresTramites> listaErr;

	/**
	 * @return the version
	 */
	public final Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public final void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the identificadorTramite
	 */
	public final String getIdentificadorTramite() {
		return identificadorTramite;
	}

	/**
	 * @param identificadorTramite the identificadorTramite to set
	 */
	public final void setIdentificadorTramite(String identificadorTramite) {
		this.identificadorTramite = identificadorTramite;
	}

	/**
	 * @return the errTram
	 */
	public final int getErrTram() {
		return errTram;
	}

	/**
	 * @param errTram the errTram to set
	 */
	public final void setErrTram(int errTram) {
		this.errTram = errTram;
	}

	/**
	 * @return the listaErr
	 */
	public final List<ErroresTramites> getListaErr() {
		return listaErr;
	}

	/**
	 * @param listaErroresTram the listaErr to set
	 */
	public final void setListaErr(List<ErroresTramites> listaErroresTram) {
		this.listaErr = listaErroresTram;
	}

	/**
	 * @return the sesOk
	 */
	public final Integer getSesOk() {
		return sesOk;
	}

	/**
	 * @param sesOk the sesOk to set
	 */
	public final void setSesOk(Integer sesOk) {
		this.sesOk = sesOk;
	}

	/**
	 * @return the sesKo
	 */
	public final Integer getSesKo() {
		return sesKo;
	}

	/**
	 * @param sesKo the sesKo to set
	 */
	public final void setSesKo(Integer sesKo) {
		this.sesKo = sesKo;
	}

	/**
	 * @return the sesInac
	 */
	public final Integer getSesInac() {
		return sesInac;
	}

	/**
	 * @param sesInac the sesInac to set
	 */
	public final void setSesInac(Integer sesInac) {
		this.sesInac = sesInac;
	}

	/**
	 * @return the sesPor
	 */
	public final Double getSesPor() {
		return sesPor;
	}

	/**
	 * @param sesPor the sesPor to set
	 */
	public final void setSesPor(Double sesPor) {
		this.sesPor = sesPor;
	}

}
