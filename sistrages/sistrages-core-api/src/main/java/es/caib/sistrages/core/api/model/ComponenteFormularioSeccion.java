package es.caib.sistrages.core.api.model;

/**
 * Componente formulario de tipo seccion.
 *
 * @author Indra
 *
 */

public final class ComponenteFormularioSeccion extends ComponenteFormulario {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	private String letra;

	/**
	 * Crea una nueva instancia de ComponenteFormularioSeccion.
	 */
	public ComponenteFormularioSeccion() {
		super();
	}

	/**
	 * Obtiene el valor de letra.
	 *
	 * @return el valor de letra
	 */
	public String getLetra() {
		return letra;
	}

	/**
	 * Establece el valor de letra.
	 *
	 * @param letra
	 *            el nuevo valor de letra
	 */
	public void setLetra(final String letra) {
		this.letra = letra;
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
           StringBuilder texto = new StringBuilder(tabulacion + "ComponenteFormularioSeccion. ");
           if (this.getIdComponente() != null) { texto.append(tabulacion +"\t IdComponente:" + this.getIdComponente() + "\n"); }
           if (this.getTipo() != null) { texto.append(tabulacion +"\t Tipus:" + this.getTipo() + "\n");}
           texto.append(tabulacion +"\t Ordre:" + this.getOrden() + "\n");
           texto.append(tabulacion +"\t NumColumnas:" + this.getNumColumnas() + "\n");
           texto.append(tabulacion +"\t NoMostrarText:" + this.isNoMostrarTexto() + "\n");
           if (this.getAlineacionTexto() != null) { texto.append(tabulacion +"\t AlineacioText:" + this.getAlineacionTexto() + "\n");}
           texto.append(tabulacion +"\t TipoSeccionReutilizable:" + this.isTipoSeccionReutilizable() + "\n");
           if (this.getIdFormSeccion() != null) { texto.append(tabulacion +"\t IdFormSeccion:" + this.getIdFormSeccion() + "\n");}

           if (this.getTexto() != null) {
        	   texto.append(tabulacion +"\t Text: \n");
        	   texto.append(this.getTexto().toString(tabulacion, idioma) + "\n");
           }
           if (this.getAyuda() != null) {
        	   texto.append(tabulacion +"\t Ajuda: \n");
        	   texto.append(this.getAyuda().toString(tabulacion, idioma) + "\n");
           }
           if (this.getLetra () != null) { texto.append(tabulacion +"\t Letra:" + this.getLetra() + "\n"); }
           return texto.toString();
     }

}
