package es.caib.sistrages.core.api.model;

/**
 * La clase ComponenteFormularioCampo.
 */

public abstract class ComponenteFormularioCampo extends ComponenteFormulario {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * obligatorio.
	 */
	private boolean obligatorio;

	/**
	 * solo lectura.
	 */
	private boolean soloLectura;

	/**
	 * no modificable.
	 */
	private boolean noModificable;

	/**
	 * script autorrellenable.
	 */
	private Script scriptAutorrellenable;

	/**
	 * script solo lectura.
	 */
	private Script scriptSoloLectura;

	/**
	 * script validacion.
	 */
	private Script scriptValidacion;

	/**
	 * Verifica si es obligatorio.
	 *
	 * @return true, si es obligatorio
	 */
	public boolean isObligatorio() {
		return obligatorio;
	}

	/**
	 * Establece el valor de obligatorio.
	 *
	 * @param obligatorio
	 *            el nuevo valor de obligatorio
	 */
	public void setObligatorio(final boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	/**
	 * Verifica si es solo lectura.
	 *
	 * @return true, si es solo lectura
	 */
	public boolean isSoloLectura() {
		return soloLectura;
	}

	/**
	 * Establece el valor de soloLectura.
	 *
	 * @param soloLectura
	 *            el nuevo valor de soloLectura
	 */
	public void setSoloLectura(final boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

	/**
	 * Verifica si es no modificable.
	 *
	 * @return true, si es no modificable
	 */
	public boolean isNoModificable() {
		return noModificable;
	}

	/**
	 * Establece el valor de noModificable.
	 *
	 * @param noModificable
	 *            el nuevo valor de noModificable
	 */
	public void setNoModificable(final boolean noModificable) {
		this.noModificable = noModificable;
	}

	/**
	 * Obtiene el valor de scriptAutorrellenable.
	 *
	 * @return el valor de scriptAutorrellenable
	 */
	public Script getScriptAutorrellenable() {
		return scriptAutorrellenable;
	}

	/**
	 * Establece el valor de scriptAutorrellenable.
	 *
	 * @param scriptAutorrellenable
	 *            el nuevo valor de scriptAutorrellenable
	 */
	public void setScriptAutorrellenable(final Script scriptAutorrellenable) {
		this.scriptAutorrellenable = scriptAutorrellenable;
	}

	/**
	 * Obtiene el valor de scriptSoloLectura.
	 *
	 * @return el valor de scriptSoloLectura
	 */
	public Script getScriptSoloLectura() {
		return scriptSoloLectura;
	}

	/**
	 * Establece el valor de scriptSoloLectura.
	 *
	 * @param scriptSoloLectura
	 *            el nuevo valor de scriptSoloLectura
	 */
	public void setScriptSoloLectura(final Script scriptSoloLectura) {
		this.scriptSoloLectura = scriptSoloLectura;
	}

	/**
	 * Obtiene el valor de scriptValidacion.
	 *
	 * @return el valor de scriptValidacion
	 */
	public Script getScriptValidacion() {
		return scriptValidacion;
	}

	/**
	 * Establece el valor de scriptValidacion.
	 *
	 * @param scriptValidacion
	 *            el nuevo valor de scriptValidacion
	 */
	public void setScriptValidacion(final Script scriptValidacion) {
		this.scriptValidacion = scriptValidacion;
	}

}
