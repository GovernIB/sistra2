package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Propiedades campo formulario.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPropiedadesCampo", description = "Descripcion de RPropiedadesCampo")
public class RPropiedadesCampo {

	/** Obligatorio. */
	@ApiModelProperty(value = "Obligatorio")
	private boolean obligatorio;

	/** Solo lectura. */
	@ApiModelProperty(value = "Solo lectura")
	private boolean soloLectura;

	/** No modificable (no permite modificar su valor inicial). */
	@ApiModelProperty(value = "No modificable (no permite modificar su valor inicial)")
	private boolean noModificable;

	/** Script autorrellable. */
	@ApiModelProperty(value = "Script autorrellable")
	private RScript scriptAutorrellenable;

	/** Script estado. */
	@ApiModelProperty(value = "Script estado")
	private RScript scriptEstado;

	/** Script validacion. */
	@ApiModelProperty(value = "Script validacion")
	private RScript scriptValidacion;

	/**
	 * Método de acceso a obligatorio.
	 *
	 * @return obligatorio
	 */
	public boolean isObligatorio() {
		return obligatorio;
	}

	/**
	 * Método para establecer obligatorio.
	 *
	 * @param obligatorio obligatorio a establecer
	 */
	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	/**
	 * Método de acceso a soloLectura.
	 *
	 * @return soloLectura
	 */
	public boolean isSoloLectura() {
		return soloLectura;
	}

	/**
	 * Método para establecer soloLectura.
	 *
	 * @param soloLectura soloLectura a establecer
	 */
	public void setSoloLectura(boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

	/**
	 * Método de acceso a noModificable.
	 *
	 * @return noModificable
	 */
	public boolean isNoModificable() {
		return noModificable;
	}

	/**
	 * Método para establecer noModificable.
	 *
	 * @param noModificable noModificable a establecer
	 */
	public void setNoModificable(boolean noModificable) {
		this.noModificable = noModificable;
	}

	/**
	 * Método de acceso a scriptAutorrellenable.
	 *
	 * @return scriptAutorrellenable
	 */
	public RScript getScriptAutorrellenable() {
		return scriptAutorrellenable;
	}

	/**
	 * Método para establecer scriptAutorrellenable.
	 *
	 * @param scriptAutorrellenable scriptAutorrellenable a establecer
	 */
	public void setScriptAutorrellenable(RScript scriptAutorrellenable) {
		this.scriptAutorrellenable = scriptAutorrellenable;
	}

	/**
	 * Método de acceso a scriptEstado.
	 *
	 * @return scriptEstado
	 */
	public RScript getScriptEstado() {
		return scriptEstado;
	}

	/**
	 * Método para establecer scriptEstado.
	 *
	 * @param scriptEstado scriptEstado a establecer
	 */
	public void setScriptEstado(RScript scriptEstado) {
		this.scriptEstado = scriptEstado;
	}

	/**
	 * Método de acceso a scriptValidacion.
	 *
	 * @return scriptValidacion
	 */
	public RScript getScriptValidacion() {
		return scriptValidacion;
	}

	/**
	 * Método para establecer scriptValidacion.
	 *
	 * @param scriptValidacion scriptValidacion a establecer
	 */
	public void setScriptValidacion(RScript scriptValidacion) {
		this.scriptValidacion = scriptValidacion;
	}

}
