package es.caib.sistramit.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Fichero
 *
 * @author Indra
 *
 */
@ApiModel(value = "RFichero", description = "Fichero")
public class RFichero {

	/**
	 * Nombre.
	 */
	@ApiModelProperty(value = "Nombre del fichero")
	private String nombre;

	/**
	 * Contenido
	 */
	@ApiModelProperty(value = "Contenido del fichero en base 64")
	private String contenido;

	public RFichero() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(final String contenido) {
		this.contenido = contenido;
	}

}
