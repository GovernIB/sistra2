package es.caib.sistrages.core.api.model;

import java.util.List;

/**
 *
 * Tramite Paso Anexar.
 *
 * @author Indra
 *
 */

public class TramitePasoAnexar extends TramitePaso {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Documentos. **/
	private List<Documento> documentos;

	/** Script anexos dinámicos. **/
	private Script scriptAnexosDinamicos;

	/** Indica si se habilita subsanación. **/
	private boolean permiteSubsanar;

	/**
	 * @return the documentos
	 */
	public List<Documento> getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos
	 *            the documentos to set
	 */
	public void setDocumentos(final List<Documento> documentos) {
		this.documentos = documentos;
	}

	/**
	 * @return the scriptAnexosDinamicos
	 */
	public Script getScriptAnexosDinamicos() {
		return scriptAnexosDinamicos;
	}

	/**
	 * @param scriptAnexosDinamicos
	 *            the scriptAnexosDinamicos to set
	 */
	public void setScriptAnexosDinamicos(final Script scriptAnexosDinamicos) {
		this.scriptAnexosDinamicos = scriptAnexosDinamicos;
	}

	/**
	 * Método que fuerza a reordenar la lista de documentos.
	 */
	public void reordenar() {
		int ordenDocumento = 1;
		if (this.documentos != null) {
			for (final Documento documento : this.documentos) {
				documento.setOrden(ordenDocumento);
				ordenDocumento++;
			}
		}
	}

	/**
	 * @return the permiteSubsanar
	 */
	public boolean isPermiteSubsanar() {
		return permiteSubsanar;
	}

	/**
	 * @param permiteSubsanar the permiteSubsanar to set
	 */
	public void setPermiteSubsanar(boolean permiteSubsanar) {
		this.permiteSubsanar = permiteSubsanar;
	}

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     **/
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "TramitPasAnexar. ");
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
           if (scriptAnexosDinamicos != null) {
        	   texto.append(tabulacion +"\t ScriptAnexosDinamicos: \n");
        	   texto.append(scriptAnexosDinamicos.toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (documentos != null && !documentos.isEmpty()) {
        	   texto.append(tabulacion +"\t Documents: \n");
        	   for(Documento documento : documentos) {
        		   texto.append(documento.toString(tabulacion+"\t", idioma)+ "\n");
        	   }
           }
           texto.append(tabulacion +"\t PermetSubsanar:" + permiteSubsanar + "\n");
           return texto.toString();
     }

}
