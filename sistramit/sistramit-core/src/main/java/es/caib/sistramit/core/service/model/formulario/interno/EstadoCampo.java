package es.caib.sistramit.core.service.model.formulario.interno;

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
	private TypeSiNo soloLectura = TypeSiNo.NO;;

	/**
	 * Indica si el campo es visible.
	 */
	private TypeSiNo oculto = TypeSiNo.NO;

	/**
	 * Indica si el campo es obligatorio.
	 */
	private TypeSiNo obligatorio = TypeSiNo.NO;

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
	 * Obtiene visible.
	 * @return visible
	 */
	public TypeSiNo getOculto() {return oculto;}

	/**
	 * Establece visible.
	 * @param visible visible
	 */
	public void setOculto(TypeSiNo visible) {this.oculto = visible;}

	/**
	 * Obtiene obligatorio.
	 * @return obligatorio
	 */
	public TypeSiNo getObligatorio() {return obligatorio;}

	/**
	 * Establece obligatorio.
	 * @param obligatorio obligatorio
	 */
	public void setObligatorio(TypeSiNo obligatorio) {this.obligatorio = obligatorio;}

}
