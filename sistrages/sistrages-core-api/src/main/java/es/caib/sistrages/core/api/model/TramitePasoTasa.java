package es.caib.sistrages.core.api.model;

import java.util.List;

/**
 *
 * Tramite Paso Tasa.
 *
 * @author Indra
 *
 */

public class TramitePasoTasa extends TramitePaso {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Tasas. **/
	private List<Tasa> tasas;

	/** Permite subsanación paso registrar. **/
	private boolean permiteSubsanar;

	/**
	 * @return the tasas
	 */
	public List<Tasa> getTasas() {
		return tasas;
	}

	/**
	 * @param tasas
	 *            the tasas to set
	 */
	public void setTasas(final List<Tasa> tasas) {
		this.tasas = tasas;
	}

	/**
	 * @return the permiteSubsanar
	 */
	public boolean isPermiteSubsanar() {
		return permiteSubsanar;
	}

	/**
	 * @param permiteSubsanar
	 *            the permiteSubsanar to set
	 */
	public void setPermiteSubsanar(final boolean permiteSubsanar) {
		this.permiteSubsanar = permiteSubsanar;
	}

	/**
	 * Método que fuerza a reordenar la lista de tasas.
	 */
	public void reordenar() {
		int ordenTasa = 1;
		if (this.tasas != null) {
			for (final Tasa tasa : this.tasas) {
				tasa.setOrden(ordenTasa);
				ordenTasa++;
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
     */
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "TramitPasTasa. ");
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
           texto.append(tabulacion +"\t PermetSubsanar:" + permiteSubsanar + "\n");
           if (getTasas() != null) {
        	   texto.append(tabulacion +"\t Tasas: \n");
        	   for(Tasa tasa : tasas) {
        		   texto.append(tasa.toString(tabulacion+"\t", idioma)+ "\n");
        	   }
           }

           return texto.toString();
     }
}
