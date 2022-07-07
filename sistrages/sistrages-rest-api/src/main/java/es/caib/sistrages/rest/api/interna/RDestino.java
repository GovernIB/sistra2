package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Destino registro.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RDestinoRegistro", description = "Datos destino")
public class RDestino {

	/** Para registro: Oficina registro. */
	@ApiModelProperty(value = "Para registro: Oficina registro.")
	private String oficinaRegistro;

	/** Para registro: Libro registro. */
	@ApiModelProperty(value = "Para registro: Libro registro.")
	private String libroRegistro;

	/** Para envío: indica identificador envío remoto. */
	@ApiModelProperty(value = "Para envío: indica identificador envío remoto.")
	private String identificadorEnvioRemoto;

	/**
	 * Método de acceso a oficinaRegistro.
	 *
	 * @return oficinaRegistro
	 */
	public String getOficinaRegistro() {
		return oficinaRegistro;
	}

	/**
	 * Método para establecer oficinaRegistro.
	 *
	 * @param oficinaRegistro
	 *                            oficinaRegistro a establecer
	 */
	public void setOficinaRegistro(final String oficinaRegistro) {
		this.oficinaRegistro = oficinaRegistro;
	}

	/**
	 * Método de acceso a libroRegistro.
	 *
	 * @return libroRegistro
	 */
	public String getLibroRegistro() {
		return libroRegistro;
	}

	/**
	 * Método para establecer libroRegistro.
	 *
	 * @param libroRegistro
	 *                          libroRegistro a establecer
	 */
	public void setLibroRegistro(final String libroRegistro) {
		this.libroRegistro = libroRegistro;
	}

	/**
	 * Método de acceso a identificadorEnvioRemoto.
	 *
	 * @return identificadorEnvioRemoto
	 */
	public String getIdentificadorEnvioRemoto() {
		return identificadorEnvioRemoto;
	}

	/**
	 * Método para establecer identificadorEnvioRemoto.
	 *
	 * @param identificadorEnvioRemoto
	 *                                     identificadorEnvioRemoto a establecer
	 */
	public void setIdentificadorEnvioRemoto(final String identificadorEnvioRemoto) {
		this.identificadorEnvioRemoto = identificadorEnvioRemoto;
	}

}
