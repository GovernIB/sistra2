package es.caib.sistrages.core.api.model;

import java.util.List;

/**
 *
 * Tramite Paso Rellenar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class TramitePasoRellenar extends TramitePaso {

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

}
