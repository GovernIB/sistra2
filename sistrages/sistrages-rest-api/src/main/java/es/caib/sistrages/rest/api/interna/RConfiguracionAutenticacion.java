package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Configuracion autenticacion.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RConfiguracionAutenticacion", description = "Configuracion autenticacion")
public class RConfiguracionAutenticacion {

	/** Identificador. */
	@ApiModelProperty(value = "Identificador")
	private String identificador;

	/** Usuario. */
	@ApiModelProperty(value = "usuario")
	private String usuario;

	/** Pasword **/
	@ApiModelProperty(value = "usuario")
	private String password;

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

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


}
