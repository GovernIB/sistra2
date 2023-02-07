package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La clase ComponenteFormularioCampoCheckbox.
 */

public final class ComponenteFormularioCampoCheckbox extends ComponenteFormularioCampo {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * valor checked.
	 */
	private String valorChecked;

	/**
	 * valor no checked.
	 */
	private String valorNoChecked;

	/**
	 * Crea una nueva instancia de ComponenteFormularioCampoCheckbox.
	 */
	public ComponenteFormularioCampoCheckbox() {
		this.setTipo(TypeObjetoFormulario.CHECKBOX);
	}

	/**
	 * Obtiene el valor de valorChecked.
	 *
	 * @return el valor de valorChecked
	 */
	public String getValorChecked() {
		return valorChecked;
	}

	/**
	 * Establece el valor de valorChecked.
	 *
	 * @param valorChecked
	 *            el nuevo valor de valorChecked
	 */
	public void setValorChecked(final String valorChecked) {
		this.valorChecked = valorChecked;
	}

	/**
	 * Obtiene el valor de valorNoChecked.
	 *
	 * @return el valor de valorNoChecked
	 */
	public String getValorNoChecked() {
		return valorNoChecked;
	}

	/**
	 * Establece el valor de valorNoChecked.
	 *
	 * @param valorNoChecked
	 *            el nuevo valor de valorNoChecked
	 */
	public void setValorNoChecked(final String valorNoChecked) {
		this.valorNoChecked = valorNoChecked;
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
           StringBuilder texto = new StringBuilder(tabulacion + "ComponenteFormularioSelector. ");
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

           if (this.valorChecked != null) { texto.append(tabulacion +"\t ValorChecked:" + this.valorChecked + "\n");}
           if (this.valorNoChecked != null) { texto.append(tabulacion +"\t ValorNoChecked:" + this.valorNoChecked + "\n");}
           return texto.toString();
     }

}
