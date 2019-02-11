package es.caib.sistrages.core.api.model.comun;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de tramite simple.
 *
 * @author slromero
 *
 */
public class TramiteSimple {

	/** Lista de pasos simples **/
	private List<TramiteSimplePaso> pasos;

	public TramiteSimple() {
		setPasos(new ArrayList<>());
	}

	/**
	 * @return the pasos
	 */
	public List<TramiteSimplePaso> getPasos() {
		return pasos;
	}

	/**
	 * @param pasos
	 *            the pasos to set
	 */
	public void setPasos(final List<TramiteSimplePaso> pasos) {
		this.pasos = pasos;
	}
}
