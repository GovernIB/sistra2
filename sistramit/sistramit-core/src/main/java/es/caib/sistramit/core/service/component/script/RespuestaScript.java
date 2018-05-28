package es.caib.sistramit.core.service.component.script;

import es.caib.sistramit.core.service.model.script.types.TypeAviso;

/**
 * Respuesta de la ejecución de un script.
 *
 * @author Indra
 *
 */
public final class RespuestaScript {

	/**
	 * Valor retornado por el script: un string o un plugin de tipo resultado (en
	 * caso de que el script retorne un valor).
	 */
	private Object resultado;

	/**
	 * Indica si en la lógica del script se ha establecido un error.
	 */
	private boolean error;

	/**
	 * En caso de que se haya marcado un error se establece el mensaje del error.
	 */
	private String mensajeError;

	/**
	 * Para los scripts de validación indica si hay que generar mensaje de aviso. No
	 * para ejecución, simplemente se muestra mensaje.
	 */
	private boolean aviso;

	/**
	 * Permite establecer un mensaje de aviso(WARNING / INFO).
	 */
	private String mensajeAviso;

	/**
	 * Permite establecer tipo mensaje de aviso: WARNING / INFO.
	 */
	private TypeAviso tipoMensajeAviso;

	/**
	 * Indica si la ejecución de un script se ha marcado con error.
	 *
	 * @return error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * Método para establecer error.
	 *
	 * @param pError
	 *            error a establecer
	 */
	public void setError(final boolean pError) {
		error = pError;
	}

	/**
	 * Método de acceso a mensajeError.
	 *
	 * @return mensajeError
	 */
	public String getMensajeError() {
		return mensajeError;
	}

	/**
	 * Método para establecer mensajeError.
	 *
	 * @param pMensajeError
	 *            mensajeError a establecer
	 */
	public void setMensajeError(final String pMensajeError) {
		mensajeError = pMensajeError;
	}

	/**
	 * Método de acceso a resultado.
	 *
	 * @return resultado
	 */
	public Object getResultado() {
		return resultado;
	}

	/**
	 * Método para establecer resultado.
	 *
	 * @param pResultado
	 *            resultado a establecer
	 */
	public void setResultado(final Object pResultado) {
		resultado = pResultado;
	}

	/**
	 * Método de acceso a aviso.
	 *
	 * @return aviso
	 */
	public boolean isAviso() {
		return aviso;
	}

	/**
	 * Método para establecer aviso.
	 *
	 * @param pAviso
	 *            aviso a establecer
	 */
	public void setAviso(final boolean pAviso) {
		aviso = pAviso;
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

	/**
	 * Método de acceso a tipoMensajeAviso.
	 *
	 * @return tipoMensajeAviso
	 */
	public TypeAviso getTipoMensajeAviso() {
		return tipoMensajeAviso;
	}

	/**
	 * Método para establecer tipoMensajeAviso.
	 *
	 * @param pTipoMensajeAviso
	 *            tipoMensajeAviso a establecer
	 */
	public void setTipoMensajeAviso(final TypeAviso pTipoMensajeAviso) {
		tipoMensajeAviso = pTipoMensajeAviso;
	}

}
