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

}
