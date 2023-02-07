package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * Area
 *
 * @author Indra
 *
 */
@ApiModel(value = "RVariableArea", description = "Descripcion de RVariableArea")
public class RVariableArea {

	/** Id. */
	@ApiModelProperty(value = "codigo")
	private Long codigo;

	/** Identificador. **/
	@ApiModelProperty(value = "identificador")
	private String identificador;

	/** Descripcion. */
	@ApiModelProperty(value = "descripcion")
	private String descripcion;

	/** Valor variable. */
	@ApiModelProperty(value = "valor")
	private String valor;

	/**
	 * @return the codigo
	 */
	public final Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *                   the codigo to set
	 */
	public final void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the identificador
	 */
	public final String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *                          the identificador to set
	 */
	public final void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public final String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *                        the descripcion to set
	 */
	public final void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Método de acceso a valor.
	 * 
	 * @return valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Método para establecer valor.
	 * 
	 * @param valor
	 *                  valor a establecer
	 */
	public void setValor(final String valor) {
		this.valor = valor;
	}

}
