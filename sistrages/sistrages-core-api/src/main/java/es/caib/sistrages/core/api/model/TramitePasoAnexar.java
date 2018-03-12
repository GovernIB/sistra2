package es.caib.sistrages.core.api.model;

import java.util.List;

/**
 *
 * Tramite Paso Anexar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class TramitePasoAnexar extends TramitePaso {

	/** Documentos. **/
	private List<Documento> documentos;

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
}
