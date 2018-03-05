package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Traducciones. Esta clase no se mapea con la BBDD sino que se utiliza como
 * intermediaria para poder trabajar con la lista de traducción en el xhtml.
 *
 * @author indra
 *
 */
public class Traducciones {

	/**
	 * Crea una nueva instancia de Traducciones.
	 */
	public Traducciones() {
		super();
	}

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
				return traduccion.getLiteral();
			}
		}

		return trads.get(0).getLiteral();
	}

	/**
	 * Obtiene el valor de traduccion.
	 *
	 * @param idioma
	 *            the idioma
	 * @return el valor de traduccion
	 */
	public String getTraduccion(final String idioma) {

		if (trads == null || idioma == null) {
			return "";
		}

		for (final Traduccion traduccion : trads) {
			if (idioma.equals(traduccion.getIdioma())) {
				return traduccion.getLiteral();
			}
		}

		return null;
	}

	/**
	 * Incluye una traducción, si la traducción:
	 * <ul>
	 * <li>No existe, añade la traducción.</li>
	 * <li>Sí existe, actualiza el texto.</li>
	 * </ul>
	 *
	 * @param traduccion
	 *            traduccion
	 */
	public void add(final Traduccion traduccion) {
		boolean encontrado = false;
		if (trads == null) {
			trads = new ArrayList<>();
		}
		for (final Traduccion trad : trads) {
			if (trad.getIdioma().equals(traduccion.getIdioma())) {
				encontrado = true;
				trad.setLiteral(traduccion.getLiteral());
			}
		}

		if (!encontrado) {
			trads.add(traduccion);
		}
	}

	/**
	 * Comprueba si esta un idioma en concreto.
	 *
	 * @param idioma
	 *            idioma
	 * @return true si existe
	 */
	public boolean contains(final String idioma) {
		boolean existe = false;
		for (final Traduccion traduccion : trads) {
			if (idioma.equals(traduccion.getIdioma())) {
				existe = true;
			}
		}

		return existe;
	}

	/**
	 * Devuelve los idiomas que tiene este traducciones.
	 *
	 * @return
	 */
	public List<String> getIdiomas() {
		final List<String> idiomas = new ArrayList<>();
		if (trads != null) {
			for (final Traduccion traduccion : trads) {
				idiomas.add(traduccion.getIdioma());
			}
		}
		return idiomas;
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