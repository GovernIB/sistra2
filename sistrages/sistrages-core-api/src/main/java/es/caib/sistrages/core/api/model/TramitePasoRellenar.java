package es.caib.sistrages.core.api.model;

import java.util.List;

/**
 *
 * Tramite Paso Rellenar.
 *
 * @author Indra
 *
 */

public class TramitePasoRellenar extends TramitePaso {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Formulario tramite. **/
	private List<FormularioTramite> formulariosTramite;

	/**
	 * @return the formulariosTramite
	 */
	public List<FormularioTramite> getFormulariosTramite() {
		return formulariosTramite;
	}

	/**
	 * @param formulariosTramite
	 *            the formulariosTramite to set
	 */
	public void setFormulariosTramite(final List<FormularioTramite> formulariosTramite) {
		this.formulariosTramite = formulariosTramite;
	}

	/**
	 * Método que reordena los formularios tramite
	 */
	public void reordenar() {
		int ordenFormularios = 1;
		if (this.formulariosTramite != null) {
			for (final FormularioTramite documento : this.formulariosTramite) {
				documento.setOrden(ordenFormularios);
				ordenFormularios++;
			}
		}
	}

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     ***/
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "TramitPasRellenar. ");
           texto.append(tabulacion +"\t Codi:" + getCodigo() + "\n");
           texto.append(tabulacion +"\t Ordre:" + getOrden() + "\n");
           texto.append(tabulacion +"\t PasFinal:" + isPasoFinal() + "\n");
           texto.append(tabulacion +"\t IdPasoRelacio:" + getIdPasoRelacion() + "\n");
           texto.append(tabulacion +"\t IdPasoTramitacio:" + getIdPasoTramitacion() + "\n");
           if (getDescripcion() != null) {
        	   texto.append(tabulacion +"\t Descripció: \n");
        	   texto.append(getDescripcion().toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (getScriptNavegacion() != null) {
        	   texto.append(tabulacion +"\t ScriptNavegacio: \n");
        	   texto.append(getScriptNavegacion().toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (getScriptVariables() != null) {
        	   texto.append(tabulacion +"\t ScriptVariables: \n");
        	   texto.append(getScriptVariables().toString(tabulacion+"\t", idioma)+ "\n");
           }

           if (formulariosTramite != null && !formulariosTramite.isEmpty()) {
        	   texto.append(tabulacion +"\t FormularisTramit: \n");
        	   for(FormularioTramite form : formulariosTramite) {
        		   texto.append(form.toString(tabulacion+"\t", idioma)+ "\n");
        	   }
           }
           return texto.toString();
     }

}
