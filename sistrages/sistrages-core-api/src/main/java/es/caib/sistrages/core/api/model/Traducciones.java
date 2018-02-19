package es.caib.sistrages.core.api.model;

import java.util.List;

/**
 * Traducciones. Esta clase no se mapea con la BBDD sino que se utiliza como
 * intermediaria para poder trabajar con la lista de traducción en el xhtml.
 *
 * @author indra
 *
 */
public class Traducciones {

	/** Idioma. **/
	private List<Traduccion> trads;

	/**
	 * Para obtener una traducción
	 *
	 * @return
	 */
	public String getTraduccion() {

		if (trads == null) {
			return "";
		}

		for (final Traduccion traduccion : trads) {
			if ("ca".equals(traduccion.getIdioma())) {
				return traduccion.getTexto();
			}
		}

		return trads.get(0).getTexto();
	}

	/**
	 * Para obtener una traducción
	 *
	 * @return
	 */
	public String getTraduccion(final String idioma) {

		if (trads == null || idioma == null) {
			return "";
		}

		for (final Traduccion traduccion : trads) {
			if (idioma.equals(traduccion.getIdioma())) {
				return traduccion.getTexto();
			}
		}

		return "";
	}

	/**
	 * @return the traducciones
	 */
	public List<Traduccion> getTraducciones() {
		return trads;
	}

	/**
	 * @param traducciones
	 *            the traducciones to set
	 */
	public void setTraducciones(final List<Traduccion> traducciones) {
		this.trads = traducciones;
	}

}
