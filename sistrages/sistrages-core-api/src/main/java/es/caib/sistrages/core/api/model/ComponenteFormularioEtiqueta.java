package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeEtiqueta;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La clase ComponenteFormularioEtiqueta.
 */

public final class ComponenteFormularioEtiqueta extends ComponenteFormulario {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * tipo etiqueta.
	 */
	private TypeEtiqueta tipoEtiqueta = TypeEtiqueta.INFO;

	/**
	 * Crea una nueva instancia de ComponenteFormularioEtiqueta.
	 */
	public ComponenteFormularioEtiqueta() {
		this.setTipo(TypeObjetoFormulario.ETIQUETA);
	}

	/**
	 * Obtiene el valor de tipoEtiqueta.
	 *
	 * @return el valor de tipoEtiqueta
	 */
	public TypeEtiqueta getTipoEtiqueta() {
		return tipoEtiqueta;
	}

	/**
	 * Establece el valor de tipoEtiqueta.
	 *
	 * @param tipoEtiqueta
	 *            el nuevo valor de tipoEtiqueta
	 */
	public void setTipoEtiqueta(final TypeEtiqueta tipoEtiqueta) {
		this.tipoEtiqueta = tipoEtiqueta;
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
           StringBuilder texto = new StringBuilder(tabulacion + "ComponenteFormularioEtiqueta. ");
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
           return texto.toString();
     }

}
