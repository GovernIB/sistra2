package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeValidacion;

/**
 *
 * Mensaje de aviso.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class MensajeValidacion implements Serializable {

	/**
	 * Tipo aviso.
	 */
	private TypeValidacion estado;

	/**
	 * Mensaje aviso.
	 */
	private String mensaje;

	/**
	 * Campo asociado a la validación (opcional).
	 */
	private String campo;

	/**
	 * Constructor.
	 */
	public MensajeValidacion() {
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
	public MensajeValidacion(final TypeValidacion pTipoAviso, final String pMensajeAviso) {
		super();
		estado = pTipoAviso;
		mensaje = pMensajeAviso;

	}

	/**
	 * Constructor.
	 *
	 * @param pCodigoElemento
	 *            Elemento asociado a la validación (campo formulario)
	 * @param pTipoAviso
	 *            Tipo aviso
	 * @param pMensajeAviso
	 *            Mensaje aviso
	 */
	public MensajeValidacion(final String pCodigoElemento, final TypeValidacion pTipoAviso,
			final String pMensajeAviso) {
		super();
		estado = pTipoAviso;
		mensaje = pMensajeAviso;
		campo = pCodigoElemento;
	}

	/**
	 * Método de acceso a tipoAviso.
	 *
	 * @return tipoAviso
	 */
	public TypeValidacion getEstado() {
		return estado;
	}

	/**
	 * Método para establecer tipoAviso.
	 *
	 * @param pTipoAviso
	 *            tipoAviso a establecer
	 */
	public void setEstado(final TypeValidacion pTipoAviso) {
		estado = pTipoAviso;
	}

	/**
	 * Método de acceso a mensajeAviso.
	 *
	 * @return mensajeAviso
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Método para establecer mensajeAviso.
	 *
	 * @param pMensajeAviso
	 *            mensajeAviso a establecer
	 */
	public void setMensaje(final String pMensajeAviso) {
		mensaje = pMensajeAviso;
	}

	/**
	 * Método de acceso a campo.
	 *
	 * @return campo
	 */
	public String getCampo() {
		return campo;
	}

	/**
	 * Método para establecer campo.
	 *
	 * @param campo
	 *            campo a establecer
	 */
	public void setCampo(String campo) {
		this.campo = campo;
	}

}
