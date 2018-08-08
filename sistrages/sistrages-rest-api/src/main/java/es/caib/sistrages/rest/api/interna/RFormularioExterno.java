package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Formulario externo.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RFormularioExterno", description = "Descripcion de RFormularioExterno")
public class RFormularioExterno {

    /** Identificador gestor formularios externo. */
	@ApiModelProperty(value = "Identificador gestor formularios externo")
    private String identificadorGestorFormularios;

    /** Identificador formulario externo. */
	@ApiModelProperty(value = "Identificador formulario externo")
    private String identificadorFormulario;

    /**
     * Método de acceso a identificadorGestorFormularios.
     *
     * @return identificadorGestorFormularios
     */
    public String getIdentificadorGestorFormularios() {
        return identificadorGestorFormularios;
    }

    /**
     * Método para establecer identificadorGestorFormularios.
     *
     * @param identificadorGestorFormularios
     *            identificadorGestorFormularios a establecer
     */
    public void setIdentificadorGestorFormularios(
            String identificadorGestorFormularios) {
        this.identificadorGestorFormularios = identificadorGestorFormularios;
    }

    /**
     * Método de acceso a identificadorFormulario.
     *
     * @return identificadorFormulario
     */
    public String getIdentificadorFormulario() {
        return identificadorFormulario;
    }

    /**
     * Método para establecer identificadorFormulario.
     *
     * @param identificadorFormulario
     *            identificadorFormulario a establecer
     */
    public void setIdentificadorFormulario(String identificadorFormulario) {
        this.identificadorFormulario = identificadorFormulario;
    }

}
