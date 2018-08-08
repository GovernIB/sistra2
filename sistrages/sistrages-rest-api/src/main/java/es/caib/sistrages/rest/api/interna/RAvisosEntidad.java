package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Avisos entidad.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RAvisosEntidad", description = "Descripcion de RAvisosEntidad")
public class RAvisosEntidad {

    /** Timestamp recuperacion. */
	@ApiModelProperty(value = "Timestamp recuperacion")
    private String timestamp;

    /** Avisos. */
	@ApiModelProperty(value = "Avisos")
    private List<RAviso> avisos;

    /**
     * Método de acceso a avisos.
     *
     * @return avisos
     */
    public List<RAviso> getAvisos() {
        return avisos;
    }

    /**
     * Método para establecer avisos.
     *
     * @param avisos
     *            avisos a establecer
     */
    public void setAvisos(List<RAviso> avisos) {
        this.avisos = avisos;
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
