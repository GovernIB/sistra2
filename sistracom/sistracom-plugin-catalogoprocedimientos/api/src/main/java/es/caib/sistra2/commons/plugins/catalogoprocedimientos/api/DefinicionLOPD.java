package es.caib.sistra2.commons.plugins.catalogoprocedimientos.api;

import java.io.Serializable;
import java.util.List;

/**
 * Datos LOPD.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DefinicionLOPD implements Serializable {

	/** Texto cabecera. */
	private String textoCabecera;

	/** Campos LOPD. */
	private List<CampoLOPD> campos;

	/**
	 * Método de acceso a textoCabecera.
	 *
	 * @return textoCabecera
	 */
	public String getTextoCabecera() {
		return textoCabecera;
	}

	/**
	 * Método para establecer textoCabecera.
	 *
	 * @param textoCabecera
	 *                          textoCabecera a establecer
	 */
	public void setTextoCabecera(final String textoCabecera) {
		this.textoCabecera = textoCabecera;
	}

	/**
	 * Método de acceso a campos.
	 *
	 * @return campos
	 */
	public List<CampoLOPD> getCampos() {
		return campos;
	}

	/**
	 * Método para establecer campos.
	 *
	 * @param campos
	 *                   campos a establecer
	 */
	public void setCampos(final List<CampoLOPD> campos) {
		this.campos = campos;
	}

}
