package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Opción formulario soporte.
 */
@ApiModel(value = "ROpcionFormularioSoporte", description = "Descripcion de ROpcionFormularioSoporte")
public class ROpcionFormularioSoporte {

	/** Codigo. */
	@ApiModelProperty(value = "Codigo")
	private Long codigo;

	/** Tipo incidencia. */
	@ApiModelProperty(value = "Tipo incidencia")
	private RLiteral tipo;

	/** Descripcion incidencia. */
	@ApiModelProperty(value = "Descripcion incidencia")
	private RLiteral descripcion;

	/**
	 * Destinatario: responsable incidencias ("R"), Lista fija de emails ("E").
	 */
	@ApiModelProperty(value = "Destinatario: Responsable Incidencias (R), Lista fija de emails (E), Administrador Área (A)")
	private String destinatario;

	/** Lista emails (separadas por ;). */
	@ApiModelProperty(value = "Lista emails (separadas por ;)")
	private String listaEmails;

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public RLiteral getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param tipo tipo a establecer
	 */
	public void setTipo(RLiteral tipo) {
		this.tipo = tipo;
	}

	/**
	 * Método de acceso a descripcion.
	 *
	 * @return descripcion
	 */
	public RLiteral getDescripcion() {
		return descripcion;
	}

	/**
	 * Método para establecer descripcion.
	 *
	 * @param descripcion descripcion a establecer
	 */
	public void setDescripcion(RLiteral descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Método de acceso a destinatario.
	 *
	 * @return destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}

	/**
	 * Método para establecer destinatario.
	 *
	 * @param destinatario destinatario a establecer
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	/**
	 * Método de acceso a listaEmails.
	 *
	 * @return listaEmails
	 */
	public String getListaEmails() {
		return listaEmails;
	}

	/**
	 * Método para establecer listaEmails.
	 *
	 * @param listaEmails listaEmails a establecer
	 */
	public void setListaEmails(String listaEmails) {
		this.listaEmails = listaEmails;
	}

	/**
	 * Método de acceso a codigo.
	 *
	 * @return codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Método para establecer codigo.
	 *
	 * @param codigo codigo a establecer
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

}
