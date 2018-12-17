package es.caib.sistramit.frontend.model;

import java.io.Serializable;
import java.util.Properties;

import es.caib.sistramit.core.api.model.comun.ResultadoProcesoProgramado;

/**
 * Informacion debug sesion tramitación.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DebugPurga implements Serializable {

	/** Idioma. **/
	private String idioma;

	/** Literales. **/
	private Properties literales;

	/** Resultado. **/
	private ResultadoProcesoProgramado resultado;

	/**
	 * @return the resultado
	 */
	public ResultadoProcesoProgramado getResultado() {
		return resultado;
	}

	/**
	 * @param resultado
	 *            the resultado to set
	 */
	public void setResultado(final ResultadoProcesoProgramado resultado) {
		this.resultado = resultado;
	}

	/**
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma
	 *            the idioma to set
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the literales
	 */
	public Properties getLiterales() {
		return literales;
	}

	/**
	 * @param literales
	 *            the literales to set
	 */
	public void setLiterales(final Properties literales) {
		this.literales = literales;
	}

	@Override
	public String toString() {
		final StringBuilder resultado = new StringBuilder();
		if (this.resultado == null || this.resultado.getDetalles() == null
				|| this.resultado.getDetalles().getPropiedades() == null) {
			resultado.append("Resultats, detalls o propietats a nul");
		} else {
			resultado.append("\n Detalls total : \n");
			for (final String key : this.resultado.getDetalles().getPropiedades().keySet()) {
				resultado.append(key);
				resultado.append(" - ");
				resultado.append(this.resultado.getDetalles().getPropiedad(key));
				resultado.append("  \n  ");
			}

		}
		return resultado.toString();
	}

}
