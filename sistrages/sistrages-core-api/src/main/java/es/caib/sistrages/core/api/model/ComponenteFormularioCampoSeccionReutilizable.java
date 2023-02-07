package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La clase ComponenteFormularioCampoCaptcha.
 */

public final class ComponenteFormularioCampoSeccionReutilizable extends ComponenteFormularioCampo {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Letra **/
	private String letra;

	/** Id seccion reutilizable **/
	private Long idSeccionReutilizable;

	/** Identificador seccion reutilizable **/
	private String identificadorSeccionReutilizable;

	/**
	 * Crea una nueva instancia de ComponenteFormularioCampoCaptcha.
	 */
	public ComponenteFormularioCampoSeccionReutilizable() {
		this.setTipo(TypeObjetoFormulario.SECCION_REUTILIZABLE);
	}

	/**
	 * @return the letra
	 */
	public String getLetra() {
		return letra;
	}

	/**
	 * @param letra the letra to set
	 */
	public void setLetra(String letra) {
		this.letra = letra;
	}

	/**
	 * @return the idSeccionReutilizable
	 */
	public Long getIdSeccionReutilizable() {
		return idSeccionReutilizable;
	}

	/**
	 * @param idSeccionReutilizable the idSeccionReutilizable to set
	 */
	public void setIdSeccionReutilizable(Long idSeccionReutilizable) {
		this.idSeccionReutilizable = idSeccionReutilizable;
	}

	/**
	 * @return the identificadorSeccionReutilizable
	 */
	public String getIdentificadorSeccionReutilizable() {
		return identificadorSeccionReutilizable;
	}

	/**
	 * @param identificadorSeccionReutilizable the identificadorSeccionReutilizable to set
	 */
	public void setIdentificadorSeccionReutilizable(String identificadorSeccionReutilizable) {
		this.identificadorSeccionReutilizable = identificadorSeccionReutilizable;
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
           StringBuilder texto = new StringBuilder(tabulacion + "ComponenteFormularioSeccionReutilizable. ");
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

           if (this.letra != null) { texto.append(tabulacion +"\t Letra:" + letra + "\n");}
           if (this.idSeccionReutilizable != null) { texto.append(tabulacion +"\t IdSeccioReutilitzable:" + idSeccionReutilizable + "\n");}
           if (this.identificadorSeccionReutilizable != null) { texto.append(tabulacion +"\t IdentificadorSeccioReutilitzable:" + this.identificadorSeccionReutilizable + "\n");}
           return texto.toString();
     }

}
