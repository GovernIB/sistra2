package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;
import java.util.List;

/**
 * Valores posibles de un campo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValoresPosiblesCampo implements Serializable {

	/**
	 * Id campo.
	 */
	private String id;

	/**
	 * Valores posibles.
	 */
	private List<ValorIndexado> valores;

	/**
	 * Método de acceso a idCampo.
	 *
	 * @return idCampo
	 */
	public String getId() {
		return id;
	}

	/**
	 * Método para establecer idCampo.
	 *
	 * @param pIdCampo
	 *                     idCampo a establecer
	 */
	public void setId(final String pIdCampo) {
		id = pIdCampo;
	}

	/**
	 * Método de acceso a valores.
	 *
	 * @return valores
	 */
	public List<ValorIndexado> getValores() {
		return valores;
	}

	/**
	 * Método para establecer valores.
	 *
	 * @param pValores
	 *                     valores a establecer
	 */
	public void setValores(final List<ValorIndexado> pValores) {
		valores = pValores;
	}

	/**
	 * Crea nueva instancia.
	 *
	 * @return ValoresPosiblesCampo
	 */
	public static ValoresPosiblesCampo createNewValoresPosiblesCampo() {
		return new ValoresPosiblesCampo();
	}

}
