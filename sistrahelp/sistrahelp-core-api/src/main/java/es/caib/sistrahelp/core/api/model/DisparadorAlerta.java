package es.caib.sistrahelp.core.api.model;

import java.util.Date;

import es.caib.sistrahelp.core.api.model.types.TypeEvento;

/**
 * Dominio.
 *
 * @author Indra
 *
 */

public class DisparadorAlerta extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	private TypeEvento ev;

	private String operador;

	private int concurrencias;

	/**
	 * @return the ev
	 */
	public final TypeEvento getEv() {
		return ev;
	}

	/**
	 * @param ev the ev to set
	 */
	public final void setEv(TypeEvento ev) {
		this.ev = ev;
	}

	/**
	 * @return the operador
	 */
	public final String getOperador() {
		return operador;
	}

	/**
	 * @param operador the operador to set
	 */
	public final void setOperador(String operador) {
		this.operador = operador;
	}

	/**
	 * @return the concurrencias
	 */
	public final int getConcurrencias() {
		return concurrencias;
	}

	/**
	 * @param concurrencias the concurrencias to set
	 */
	public final void setConcurrencias(int concurrencias) {
		this.concurrencias = concurrencias;
	}

}
