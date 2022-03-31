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
@ApiModel(value = "RArea", description = "Descripcion de RArea")
public class RArea {

	/** Mensaje aviso. */
	@ApiModelProperty(value = "Id")
	private String id;

	/** Emails */
	@ApiModelProperty(value = "Emails asociados al área (separados por ; )")
	private String emails;

	/**
	 * @return the emails
	 */
	public final String getEmails() {
		return emails;
	}

	/**
	 * @param emails the emails to set
	 */
	public final void setEmails(String emails) {
		this.emails = emails;
	}

	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(String idCompuesto) {
		this.id = idCompuesto;
	}

}
