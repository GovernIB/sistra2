package es.caib.sistramit.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Fichero
 *
 * @author Indra
 *
 */
@ApiModel(value = "RFicheroPersistenciaAuditoria", description = "Fichero Persistencia Auditoria")
public class RFicheroPersistenciaAuditoria {

	@ApiModelProperty(value = "Identificador Paso")
	private String identificadorPaso;
	@ApiModelProperty(value = "Tipo Paso")
	private String tipoPaso;
	@ApiModelProperty(value = "Nombre")
	private String nombre;
	@ApiModelProperty(value = "Codigo")
	private Long codigo;
	@ApiModelProperty(value = "clave")
	private String clave;
	@ApiModelProperty(value = "Tipo")
	private String tipo;

	public RFicheroPersistenciaAuditoria() {
		super();
	}

	public String getIdentificadorPaso() {
		return identificadorPaso;
	}

	public void setIdentificadorPaso(final String identificadorPaso) {
		this.identificadorPaso = identificadorPaso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(final String clave) {
		this.clave = clave;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public String getTipoPaso() {
		return tipoPaso;
	}

	public void setTipoPaso(final String tipoPaso) {
		this.tipoPaso = tipoPaso;
	}

}
