package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * RPermisoHelpDesk.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPermisoHelpDesk", description = "Descripcion de RPermisoHelpDesk")
public class RPermisoHelpDesk {

	/** codigoDIR3 entidad. */
	@ApiModelProperty(value = "codigoDIR3Entidad")
	private String codigoDIR3Entidad;

	/** identificador area. */
	@ApiModelProperty(value = "identificadorArea")
	private String identificadorArea;

	/** Tipo: Rol (R) / Usuario (U) */
	@ApiModelProperty(value = "Tipo: Rol (R) / Usuario (U)")
	private String tipo;

	/** valor. */
	@ApiModelProperty(value = "valor")
	private String valor;

	public String getCodigoDIR3Entidad() {
		return codigoDIR3Entidad;
	}

	public void setCodigoDIR3Entidad(final String codigoDIR3Entidad) {
		this.codigoDIR3Entidad = codigoDIR3Entidad;
	}

	public String getIdentificadorArea() {
		return identificadorArea;
	}

	public void setIdentificadorArea(final String identificadorArea) {
		this.identificadorArea = identificadorArea;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(final String valor) {
		this.valor = valor;
	}

}
