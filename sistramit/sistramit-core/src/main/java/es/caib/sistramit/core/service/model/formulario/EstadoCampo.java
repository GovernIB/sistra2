package es.caib.sistramit.core.service.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Estado de un campo que puede variar al evaluar un campo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class EstadoCampo implements Serializable {

	/**
	 * Id campo.
	 */
	private String idCampo;

	/**
	 * Indica si el campo esta como solo lectura.
	 */
	private TypeSiNo soloLectura;

	/**
	 * Constructor.
	 *
	 * @param pIdCampo
	 *            Id campo
	 * @param pSoloLectura
	 *            Solo lectura
	 */
	public EstadoCampo(final String pIdCampo, final TypeSiNo pSoloLectura) {
		super();
		idCampo = pIdCampo;
		soloLectura = pSoloLectura;
	}

	/**
	 * Constructor.
	 */
	public EstadoCampo() {
		super();
	}

	/**
	 * Método de acceso a idCampo.
	 *
	 * @return idCampo
	 */
	public String getIdCampo() {
		return idCampo;
	}

	/**
	 * Método para establecer idCampo.
	 *
	 * @param pIdCampo
	 *            idCampo a establecer
	 */
	public void setIdCampo(final String pIdCampo) {
		idCampo = pIdCampo;
	}

	/**
	 * Método de acceso a solo lectura.
	 *
	 * @return estado
	 */
	public TypeSiNo getSoloLectura() {
		return soloLectura;
	}

	/**
	 * Método para establecer estado.
	 *
	 * @param pSoloLectura
	 *            indica si es solo lectura
	 */
	public void setSoloLectura(final TypeSiNo pSoloLectura) {
		soloLectura = pSoloLectura;
	}

	/**
	 * Crea nueva instancia.
	 *
	 * @param pIdCampo
	 *            Id campo
	 * @param pSoloLectura
	 *            Solo lectura
	 * @return Estado campo
	 */
	public static EstadoCampo createNewEstadoCampo(final String pIdCampo, final TypeSiNo pSoloLectura) {
		return new EstadoCampo(pIdCampo, pSoloLectura);
	}

}
