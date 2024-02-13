package es.caib.sistrages.rest.api.interna;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Logo entidad.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RLogoEntidad", description = "Descripcion de RLogoEntidad")
public class RLogoEntidad {

    /** Timestamp recuperacion. */
	@ApiModelProperty(value = "Timestamp recuperacion")
    private String timestamp;

    /** Avisos. */
	@ApiModelProperty(value = "logo")
    private byte[] logo;

    /**
	 * @return the logo
	 */
	public byte[] getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	/**
     * Método de acceso a timestamp.
     *
     * @return timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Método para establecer timestamp.
     *
     * @param timestamp
     *            timestamp a establecer
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
