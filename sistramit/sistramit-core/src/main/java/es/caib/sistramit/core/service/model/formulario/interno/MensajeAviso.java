package es.caib.sistramit.core.service.model.formulario.interno;

import es.caib.sistramit.core.api.model.comun.types.TypeAviso;

/**
 *
 * Mensaje de aviso.
 *
 * @author Indra
 *
 */
public final class MensajeAviso {

	/**
	 * Tipo aviso.
	 */
	private TypeAviso tipoAviso;

	/**
	 * Mensaje aviso.
	 */
	private String mensajeAviso;

	/**
	 * Constructor.
	 */
	public MensajeAviso() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param pTipoAviso
	 *            Tipo aviso
	 * @param pMensajeAviso
	 *            Mensaje aviso
	 */
	public MensajeAviso(final TypeAviso pTipoAviso, final String pMensajeAviso) {
		super();
		tipoAviso = pTipoAviso;
		mensajeAviso = pMensajeAviso;
	}

	/**
	 * Método de acceso a tipoAviso.
	 *
	 * @return tipoAviso
	 */
	public TypeAviso getTipoAviso() {
		return tipoAviso;
	}

	/**
	 * Método para establecer tipoAviso.
	 *
	 * @param pTipoAviso
	 *            tipoAviso a establecer
	 */
	public void setTipoAviso(final TypeAviso pTipoAviso) {
		tipoAviso = pTipoAviso;
	}

	/**
	 * Método de acceso a mensajeAviso.
	 *
	 * @return mensajeAviso
	 */
	public String getMensajeAviso() {
		return mensajeAviso;
	}

	/**
	 * Método para establecer mensajeAviso.
	 *
	 * @param pMensajeAviso
	 *            mensajeAviso a establecer
	 */
	public void setMensajeAviso(final String pMensajeAviso) {
		mensajeAviso = pMensajeAviso;
	}

}
