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

	private Integer id;

	private Integer grupo;

	private String opLogicoAND_OR;

	private boolean opLogicoNOT;

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

	/**
	 * @return the grupo
	 */
	public Integer getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return the opLogicoAND_OR
	 */
	public String getOpLogicoAND_OR() {
		return opLogicoAND_OR;
	}

	/**
	 * @param opLogicoAND_OR the opLogicoAND_OR to set
	 */
	public void setOpLogicoAND_OR(String opLogicoAND_OR) {
		this.opLogicoAND_OR = opLogicoAND_OR;
	}

	/**
	 * @return the opLogicoNOT
	 */
	public boolean isOpLogicoNOT() {
		return opLogicoNOT;
	}

	/**
	 * @param opLogicoNOT the opLogicoNOT to set
	 */
	public void setOpLogicoNOT(boolean opLogicoNOT) {
		this.opLogicoNOT = opLogicoNOT;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}
