package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Literal.
 *
 * @author indra
 *
 */

public class Literal extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Idioma. **/
	private List<Traduccion> trads;

	/** Codigo. **/
	private Long codigo;

	/**
	 * Crea una nueva instancia de Traducciones.
	 */
	public Literal() {
		super();
	}

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
	 *            idioma
	 * @return el valor de traduccion
	 */
	public String getTraduccion(final String idioma) {

		if (trads == null || idioma == null) {
			return null;
		}

		for (final Traduccion traduccion : trads) {
			if (idioma.equals(traduccion.getIdioma())) {
				return traduccion.getLiteral();
			}
		}

		return null;
	}

	/**
	 * Obtiene el valor de traduccion (con algún valor por defecto).
	 *
	 * @param idioma
	 *            idioma
	 * @return el valor de traduccion
	 */
	public String getTraduccion(final String idioma, final List<String> idiomas) {

		// Si no hay traducciones devolver vacío
		if (trads == null || idioma == null || trads.isEmpty()) {
			return null;
		}

		// Solo se buscará si el idioma que se pida está en la lista de idiomas
		if (idiomas.contains(idioma)) {
			// Recorrer las traducciones buscando el idioma que se pide.
			for (final Traduccion traduccion : trads) {
				if (idioma.equals(traduccion.getIdioma())) {
					return traduccion.getLiteral();
				}
			}
		}

		// Recorrer las traducciones buscando el idioma según lo definido en idiomas
		// (que viene por configuracion global)
		for (final String idi : idiomas) {
			final String trad = this.getTraduccion(idi);
			if (trad != null) {
				return trad;
			}
		}

		// Sino devolver cualquier traduccion.
		return trads.get(0).getLiteral();

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
	 * Obtiene el valor de idiomas.
	 *
	 * @return el valor de idiomas
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
	 * Obtiene el valor de traducciones.
	 *
	 * @return el valor de traducciones
	 */
	public List<Traduccion> getTraducciones() {
		return trads;
	}

	/**
	 * Establece el valor de traducciones.
	 *
	 * @param traducciones
	 *            el nuevo valor de traducciones
	 */
	public void setTraducciones(final List<Traduccion> traducciones) {
		this.trads = traducciones;
	}

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     */
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion +"\t Literal. ");
           texto.append(tabulacion +"\t Codi:" + codigo + "\n");
           if (getTraduccion(idioma) != null) {
        	   texto.append(tabulacion +"\t Text:" + getTraduccion(idioma).replaceAll("\n", "\n\t\t"+tabulacion));
           }
           return texto.toString();
     }

	public void limpiarIds() {
		this.setCodigo(null);
		if (this.getTraducciones() != null && !this.getTraducciones().isEmpty()) {
			for (final Traduccion trad : this.getTraducciones()) {
				trad.limpiarIds();
			}
		}
	}

}