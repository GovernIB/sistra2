package es.caib.sistramit.core.api.model.flujo;

import java.util.List;

/**
 * Datos LOPD (formato tabla).
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class LopdTabla implements ModelApi {

	/** Texto cabecera. */
	private String cabecera;

	/** Campos LOPD. */
	private List<LopdCampo> campos;

	/**
	 * Método de acceso a textoCabecera.
	 *
	 * @return textoCabecera
	 */
	public String getCabecera() {
		return cabecera;
	}

	/**
	 * Método para establecer textoCabecera.
	 *
	 * @param textoCabecera
	 *                          textoCabecera a establecer
	 */
	public void setCabecera(final String textoCabecera) {
		this.cabecera = textoCabecera;
	}

	/**
	 * Método de acceso a campos.
	 *
	 * @return campos
	 */
	public List<LopdCampo> getCampos() {
		return campos;
	}

	/**
	 * Método para establecer campos.
	 * 
	 * @param campos
	 *                   campos a establecer
	 */
	public void setCampos(final List<LopdCampo> campos) {
		this.campos = campos;
	}

}
