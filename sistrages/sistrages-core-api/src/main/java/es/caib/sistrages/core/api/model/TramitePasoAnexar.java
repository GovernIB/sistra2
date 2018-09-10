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

}
