package es.caib.sistramit.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Invalidación de caché.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RInvalidacion", description = "Invalidación caché")
public class RInvalidacion {

    /**
     * Tipo invalidación.
     */
    @ApiModelProperty(value = "Tipo: versión trámite (T) / datos dominio (D) / configuración entidad (E) / configuración global (C)")
    private String tipo;

    /**
     * Identificador.
     */
    @ApiModelProperty(value = "Identificador según el tipo de invalidación: versión trámite (idtramite-version) / datos dominio (iddominio) / configuración entidad (identidad) / configuración global (no aplica)")
    private String identificador;

    /**
     * Método de acceso a tipo.
     *
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param tipo
     *            tipo a establecer
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

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
     *            identificador a establecer
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

}
