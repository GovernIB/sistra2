package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Valoracion.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RValoracion", description = "Descripcion de RValoracion")
public class RIncidenciaValoracion {

	/** Identificador. */
	@ApiModelProperty(value = "Identificador")
	private String identificador;

	/** Descripción. */
	@ApiModelProperty(value = "Descripción")
	private RLiteral descripcion;

	/**
	 * @return the identificador
	 */
	public final String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *            the identificador to set
	 */
	public final void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public final RLiteral getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public final void setDescripcion(final RLiteral descripcion) {
		this.descripcion = descripcion;
	}

}
