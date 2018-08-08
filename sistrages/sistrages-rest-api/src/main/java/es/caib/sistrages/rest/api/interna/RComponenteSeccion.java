package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Componente sección.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RComponenteSeccion", description = "Descripcion de RComponenteSeccion")
public class RComponenteSeccion extends RComponente {

    /** Letra sección. */
	@ApiModelProperty(value = "Letra sección")
    private String letra;

    /**
     * Método de acceso a letra.
     *
     * @return letra
     */
    public String getLetra() {
        return letra;
    }

    /**
     * Método para establecer letra.
     *
     * @param letra
     *            letra a establecer
     */
    public void setLetra(String letra) {
        this.letra = letra;
    }

}
