package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La clase ComponenteFormularioCampoCaptcha.
 */

public final class ComponenteFormularioListaElementos extends ComponenteFormularioCampo {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo id formulario **/
	private Long idFormulario;

	/**
	 * Crea una nueva instancia de ComponenteFormularioCampoCaptcha.
	 */
	public ComponenteFormularioListaElementos() {
		this.setTipo(TypeObjetoFormulario.LISTA_ELEMENTOS);
	}

	/**
	 * @return the idFormulario
	 */
	public Long getIdFormulario() {
		return idFormulario;
	}


	/**
	 * @param idFormulario the idFormulario to set
	 */
	public void setIdFormulario(Long idFormulario) {
		this.idFormulario = idFormulario;
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
           StringBuilder texto = new StringBuilder(tabulacion + "ComponenteFormularioListaElementos. ");
           texto.append(tabulacion +"\t IdComponente:" + this.getIdComponente() + "\n");
           texto.append(tabulacion +"\t Tipus:" + this.getTipo() + "\n");
           texto.append(tabulacion +"\t Ordre:" + this.getOrden() + "\n");
           texto.append(tabulacion +"\t NumColumnas:" + this.getNumColumnas() + "\n");
           texto.append(tabulacion +"\t NoMostrarText:" + this.isNoMostrarTexto() + "\n");
           texto.append(tabulacion +"\t AlineacioText:" + this.getAlineacionTexto() + "\n");
           texto.append(tabulacion +"\t TipoSeccionReutilizable:" + this.isTipoSeccionReutilizable() + "\n");
           texto.append(tabulacion +"\t IdFormSeccion:" + this.getIdFormSeccion() + "\n");

           if (this.getTexto() != null) {
        	   texto.append(tabulacion +"\t Text: \n");
        	   texto.append(this.getTexto().toString(tabulacion, idioma) + "\n");
           }
           if (this.getAyuda() != null) {
        	   texto.append(tabulacion +"\t Ajuda: \n");
        	   texto.append(this.getAyuda().toString(tabulacion, idioma) + "\n");
           }
           return texto.toString();
     }

}
