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
	 * Si el campo pertenece a una página de lista de elementos indica si se debe
	 * mostrar o no.
	 */
	@ApiModelProperty(value = "Si el campo pertenece a una página de lista de elementos indica si se debe mostrar o no.")
	private boolean listaElementosMostrar;

	/**
	 * Si el campo pertenece a una página de lista de elementos indica la anchura al
	 * mostrarse.
	 */
	@ApiModelProperty(value = "Si el campo pertenece a una página de lista de elementos indica la anchura al mostrarse.")
	private Integer listaElementosAnchura;

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
	 * @param obligatorio
	 *                        obligatorio a establecer
	 */
	public void setObligatorio(final boolean obligatorio) {
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
	 * @param soloLectura
	 *                        soloLectura a establecer
	 */
	public void setSoloLectura(final boolean soloLectura) {
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
	 * @param noModificable
	 *                          noModificable a establecer
	 */
	public void setNoModificable(final boolean noModificable) {
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
	 * @param scriptAutorrellenable
	 *                                  scriptAutorrellenable a establecer
	 */
	public void setScriptAutorrellenable(final RScript scriptAutorrellenable) {
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
	 * @param scriptEstado
	 *                         scriptEstado a establecer
	 */
	public void setScriptEstado(final RScript scriptEstado) {
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
	 * @param scriptValidacion
	 *                             scriptValidacion a establecer
	 */
	public void setScriptValidacion(final RScript scriptValidacion) {
		this.scriptValidacion = scriptValidacion;
	}

	/**
	 * Método de acceso a listaElementosAnchura.
	 *
	 * @return listaElementosAnchura
	 */
	public Integer getListaElementosAnchura() {
		return listaElementosAnchura;
	}

	/**
	 * Método para establecer listaElementosAnchura.
	 *
	 * @param listaElementosAnchura
	 *                                  listaElementosAnchura a establecer
	 */
	public void setListaElementosAnchura(final Integer listaElementosAnchura) {
		this.listaElementosAnchura = listaElementosAnchura;
	}

	/**
	 * Método de acceso a listaElementosMostrar.
	 * 
	 * @return listaElementosMostrar
	 */
	public boolean isListaElementosMostrar() {
		return listaElementosMostrar;
	}

	/**
	 * Método para establecer listaElementosMostrar.
	 * 
	 * @param listaElementosMostrar
	 *                                  listaElementosMostrar a establecer
	 */
	public void setListaElementosMostrar(final boolean listaElementosMostrar) {
		this.listaElementosMostrar = listaElementosMostrar;
	}

}
