package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RPaginaFormulario", description = "Descripcion de RPaginaFormulario")
public class RPaginaFormulario {

	/** Identificador página (necesario si se requiere navegación entre páginas). */
	@ApiModelProperty(value = "Identificador página (necesario si se requiere navegación entre páginas)")
	private String identificador;

	/** HTML página (B64). */
	@ApiModelProperty(value = "HTML página (B64)")
	private String htmlB64;

	/** Indica si es página final. */
	@ApiModelProperty(value = "Indica si es página final")
	private boolean paginaFinal;

	/** Script validación página. */
	@ApiModelProperty(value = "Script validación página")
	private RScript scriptValidacion;

	/** Script validación página. */
	@ApiModelProperty(value = "Script navegación entre páginas")
	private RScript scriptNavegacion;

	/** Líneas de componentes. */
	@ApiModelProperty(value = "Líneas de componentes")
	private List<RLineaComponentes> lineas;

	/**
	 * Método de acceso a htmlB64.
	 *
	 * @return htmlB64
	 */
	public String getHtmlB64() {
		return htmlB64;
	}

	/**
	 * Método para establecer htmlB64.
	 *
	 * @param htmlB64
	 *                    htmlB64 a establecer
	 */
	public void setHtmlB64(final String htmlB64) {
		this.htmlB64 = htmlB64;
	}

	/**
	 * Método de acceso a paginaFinal.
	 *
	 * @return paginaFinal
	 */
	public boolean isPaginaFinal() {
		return paginaFinal;
	}

	/**
	 * Método para establecer paginaFinal.
	 *
	 * @param paginaFinal
	 *                        paginaFinal a establecer
	 */
	public void setPaginaFinal(final boolean paginaFinal) {
		this.paginaFinal = paginaFinal;
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
	 * @return the scriptNavegacion
	 */
	public final RScript getScriptNavegacion() {
		return scriptNavegacion;
	}

	/**
	 * @param scriptNavegacion
	 *                             the scriptNavegacion to set
	 */
	public final void setScriptNavegacion(final RScript scriptNavegacion) {
		this.scriptNavegacion = scriptNavegacion;
	}

	/**
	 * Método de acceso a lineas.
	 *
	 * @return lineas
	 */
	public List<RLineaComponentes> getLineas() {
		return lineas;
	}

	/**
	 * Método para establecer lineas.
	 *
	 * @param lineas
	 *                   lineas a establecer
	 */
	public void setLineas(final List<RLineaComponentes> lineas) {
		this.lineas = lineas;
	}

	/**
	 * Método de acceso a identificador.
	 * 
	 * @return identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Método para establecer identificador.
	 * 
	 * @param identificador
	 *                          identificador a establecer
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

}
