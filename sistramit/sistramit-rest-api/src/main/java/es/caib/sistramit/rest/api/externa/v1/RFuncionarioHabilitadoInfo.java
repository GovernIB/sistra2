package es.caib.sistramit.rest.api.externa.v1;

import io.swagger.annotations.ApiModelProperty;

/**
 * Datos funcionario habilitado.
 */
public class RFuncionarioHabilitadoInfo extends RPersonaInfo{

	/**
	 * Username.
	 */
	@ApiModelProperty(value = "Username")
	private String username;

	/**
	 * DIR3 asociado al FH.
	 */
	@ApiModelProperty(value = "dir3")
	private String dir3;

	/**
	 * Devuelve el username.
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Establece el username.
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Devuelve el DIR3.
	 * @return dir3
	 */
	public String getDir3() {
		return dir3;
	}

	/**
	 * Establece el DIR3.
	 * @param dir3
	 */
	public void setDir3(String dir3) {
		this.dir3 = dir3;
	}
}
