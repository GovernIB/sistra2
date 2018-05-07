package es.caib.sistrages.core.api.model;

/**
 * Componente formulario de tipo campo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public abstract class ComponenteFormularioCampo extends ComponenteFormulario {

	/** Indica si es obligatorio. */
	private boolean obligatorio;

	/** Indica si es solo lectura. */
	private boolean soloLectura;

	/** Indica si es no modificable. */
	private boolean noModificable; // TODO VER SI ES NECESARIO

	/** Script autorrellenable. */
	private Script scriptAutorrellenable;

	/** Script solo lectura. */
	private Script scriptSoloLectura;

	/** Script validacion. */
	private Script scriptValidacion;

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(final boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public boolean isSoloLectura() {
		return soloLectura;
	}

	public void setSoloLectura(final boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

	public boolean isNoModificable() {
		return noModificable;
	}

	public void setNoModificable(final boolean noModificable) {
		this.noModificable = noModificable;
	}

	public Script getScriptAutorrellenable() {
		return scriptAutorrellenable;
	}

	public void setScriptAutorrellenable(final Script scriptAutorrellenable) {
		this.scriptAutorrellenable = scriptAutorrellenable;
	}

	public Script getScriptSoloLectura() {
		return scriptSoloLectura;
	}

	public void setScriptSoloLectura(final Script scriptSoloLectura) {
		this.scriptSoloLectura = scriptSoloLectura;
	}

	public Script getScriptValidacion() {
		return scriptValidacion;
	}

	public void setScriptValidacion(final Script scriptValidacion) {
		this.scriptValidacion = scriptValidacion;
	}

}
