package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Componente aviso.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RComponenteAviso", description = "Descripcion de RComponenteAviso")
public class RComponenteAviso extends RComponente {

    /** Tipo aviso. */
	@ApiModelProperty(value = "Tipo aviso")
    private String tipoAviso;

    /**
     * Método de acceso a tipoAviso.
     *
     * @return tipoAviso
     */
    public String getTipoAviso() {
        return tipoAviso;
    }

    /**
     * Método para establecer tipoAviso.
     *
     * @param tipoAviso
     *            tipoAviso a establecer
     */
    public void setTipoAviso(String tipoAviso) {
        this.tipoAviso = tipoAviso;
    }

}
