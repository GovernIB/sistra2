package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Opciones particularizadas de configuración de un campo del formulario de tipo
 * texto identificador.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesCampoTextoId implements Serializable {

	/**
	 * Indica si admite dni.
	 */
	private TypeSiNo dni = TypeSiNo.NO;

	/**
	 * Indica si admite nie.
	 */
	private TypeSiNo nie = TypeSiNo.NO;

	/**
	 * Indica si admite nif.
	 */
	private TypeSiNo nifOtros = TypeSiNo.NO;

	/**
	 * Indica si admite nif de personas jurídicas.
	 */
	private TypeSiNo nifPJ = TypeSiNo.NO;

	/**
	 * Indica si admite número seguridad social.
	 */
	private TypeSiNo nss = TypeSiNo.NO;

	/**
	 * Prevenir pegar.
	 */
	private TypeSiNo pegar = TypeSiNo.SI;

	/**
	 * Método de acceso a prevenirPegar.
	 *
	 * @return prevenirPegar
	 */
	public TypeSiNo getPegar() {
		return pegar;
	}

	/**
	 * Método para establecer prevenirPegar.
	 *
	 * @param prevenirPegar
	 *                          prevenirPegar a establecer
	 */
	public void setPegar(final TypeSiNo prevenirPegar) {
		this.pegar = prevenirPegar;
	}

	/**
	 * Método de acceso a nie.
	 *
	 * @return nie
	 */
	public TypeSiNo getNie() {
		return nie;
	}

	/**
	 * Método para establecer nie.
	 *
	 * @param pNie
	 *                 nie a establecer
	 */
	public void setNie(final TypeSiNo pNie) {
		nie = pNie;
	}

	/**
	 * Método de acceso a nss.
	 *
	 * @return nss
	 */
	public TypeSiNo getNss() {
		return nss;
	}

	/**
	 * Método para establecer nss.
	 *
	 * @param pNss
	 *                 nss a establecer
	 */
	public void setNss(final TypeSiNo pNss) {
		nss = pNss;
	}

	/**
	 * @return the dni
	 */
	public final TypeSiNo getDni() {
		return dni;
	}

	/**
	 * @param dni
	 *                the dni to set
	 */
	public final void setDni(final TypeSiNo dni) {
		this.dni = dni;
	}

	/**
	 * @return the nifOtros
	 */
	public final TypeSiNo getNifOtros() {
		return nifOtros;
	}

	/**
	 * @param nifOtros
	 *                     the nifOtros to set
	 */
	public final void setNifOtros(final TypeSiNo nifOtros) {
		this.nifOtros = nifOtros;
	}

	/**
	 * @return the nifPJ
	 */
	public TypeSiNo getNifPJ() {
		return nifPJ;
	}

	/**
	 * @param nifPJ
	 *                  the nifPJ to set
	 */
	public void setNifPJ(final TypeSiNo nifPJ) {
		this.nifPJ = nifPJ;
	}

}
