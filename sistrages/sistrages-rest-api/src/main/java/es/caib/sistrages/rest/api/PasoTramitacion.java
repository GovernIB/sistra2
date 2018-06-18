package es.caib.sistrages.rest.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Paso tramitación.
 *
 * @author Indra
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = PasoTramitacionDebeSaber.class, name = "PasoTramitacionDebeSaber"),
        @Type(value = PasoTramitacionRellenar.class, name = "PasoTramitacionRellenar"),
        @Type(value = PasoTramitacionAnexar.class, name = "PasoTramitacionAnexar"),
        @Type(value = PasoTramitacionPagar.class, name = "PasoTramitacionPagar"),
        @Type(value = PasoTramitacionRegistrar.class, name = "PasoTramitacionRegistrar")})
public abstract class PasoTramitacion {

    /** Identificador. */
    private String identificador;

    /** Tipo de paso. */
    private String tipo;

    /**
     * Método de acceso a id.
     *
     * @return id
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método para establecer id.
     *
     * @param id
     *            id a establecer
     */
    public void setIdentificador(String id) {
        this.identificador = id;
    }

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

}
