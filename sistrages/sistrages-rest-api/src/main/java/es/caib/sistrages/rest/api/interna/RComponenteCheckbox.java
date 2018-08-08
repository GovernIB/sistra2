package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Componente checkbox.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RComponenteCheckbox", description = "Descripcion de RComponenteCheckbox")
public class RComponenteCheckbox extends RComponente {

    /** Propiedades campo. */
	@ApiModelProperty(value = "Propiedades campo")
    private RPropiedadesCampo propiedadesCampo;

    /** Valor checked. */
	@ApiModelProperty(value = "Valor checked")
    private String valorChecked;

    /** Valor no checked. */
	@ApiModelProperty(value = "Valor no checked")
    private String valorNoChecked;

    /**
     * Método de acceso a propiedadesCampo.
     *
     * @return propiedadesCampo
     */
    public RPropiedadesCampo getPropiedadesCampo() {
        return propiedadesCampo;
    }

    /**
     * Método para establecer propiedadesCampo.
     *
     * @param propiedadesCampo
     *            propiedadesCampo a establecer
     */
    public void setPropiedadesCampo(RPropiedadesCampo propiedadesCampo) {
        this.propiedadesCampo = propiedadesCampo;
    }

    /**
     * Método de acceso a valorChecked.
     *
     * @return valorChecked
     */
    public String getValorChecked() {
        return valorChecked;
    }

    /**
     * Método para establecer valorChecked.
     *
     * @param valorChecked
     *            valorChecked a establecer
     */
    public void setValorChecked(String valorChecked) {
        this.valorChecked = valorChecked;
    }

    /**
     * Método de acceso a valorNoChecked.
     *
     * @return valorNoChecked
     */
    public String getValorNoChecked() {
        return valorNoChecked;
    }

    /**
     * Método para establecer valorNoChecked.
     *
     * @param valorNoChecked
     *            valorNoChecked a establecer
     */
    public void setValorNoChecked(String valorNoChecked) {
        this.valorNoChecked = valorNoChecked;
    }
}
