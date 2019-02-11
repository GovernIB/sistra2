package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeAviso;

/**
 *
 * Mensaje de aviso.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class MensajeAviso implements Serializable {

	/**
	 * Tipo aviso.
	 */
	private TypeAviso tipo;

	/**
	 * Mensaje aviso.
	 */
	private String texto;

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
		tipo = pTipoAviso;
		texto = pMensajeAviso;
	}

	/**
	 * Método de acceso a tipoAviso.
	 *
	 * @return tipoAviso
	 */
	public TypeAviso getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipoAviso.
	 *
	 * @param pTipoAviso
	 *            tipoAviso a establecer
	 */
	public void setTipo(final TypeAviso pTipoAviso) {
		tipo = pTipoAviso;
	}

	/**
	 * Método de acceso a mensajeAviso.
	 *
	 * @return mensajeAviso
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * Método para establecer mensajeAviso.
	 *
	 * @param pMensajeAviso
	 *            mensajeAviso a establecer
	 */
	public void setTexto(final String pMensajeAviso) {
		texto = pMensajeAviso;
	}

}
