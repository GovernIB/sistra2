package es.caib.sistrahelp.core.api.model;

import java.util.List;

/**
 *
 * ErroresCuadroMando.
 *
 * @author Indra
 *
 */

public class ErroresTramites extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Identificador Tramite. */
	private String identificadorError;

	/** Traza */
	private String traza;

	/** Errores tramite. */
	private int errNum;

	/**
	 * @return the identificadorError
	 */
	public final String getIdentificadorError() {
		return identificadorError;
	}

	/**
	 * @param identificadorError the identificadorError to set
	 */
	public final void setIdentificadorError(String identificadorError) {
		this.identificadorError = identificadorError;
	}

	/**
	 * @return the errNum
	 */
	public final int getErrNum() {
		return errNum;
	}

	/**
	 * @param errNum the errNum to set
	 */
	public final void setErrNum(int errNum) {
		this.errNum = errNum;
	}

	/**
	 * @return the traza
	 */
	public final String getTraza() {
		return traza;
	}

	/**
	 * @param traza the traza to set
	 */
	public final void setTraza(String traza) {
		this.traza = traza;
	}

}
