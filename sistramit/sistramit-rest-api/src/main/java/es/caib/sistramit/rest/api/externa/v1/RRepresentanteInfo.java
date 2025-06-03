package es.caib.sistramit.rest.api.externa.v1;

import io.swagger.annotations.ApiModelProperty;

public class RRepresentanteInfo extends RPersonaInfo {

	/**
	 * Email representante.
	 */
	@ApiModelProperty(value = "Email")
	private String email;

	/**
	 * Devuelve el email.
	 * @return the email
	 */
	public final String getEmail() {
		return email;
	}

	/**
	 * Establece el email.
	 * @param email the email to set
	 */
	public final void setEmail(String email) {
		this.email = email;
	}

}
