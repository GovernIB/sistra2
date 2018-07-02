package es.caib.sistrages.rest.api.interna;

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
        @Type(value = RPasoTramitacionDebeSaber.class, name = "RPasoTramitacionDebeSaber"),
        @Type(value = RPasoTramitacionRellenar.class, name = "RPasoTramitacionRellenar"),
        @Type(value = RPasoTramitacionAnexar.class, name = "RPasoTramitacionAnexar"),
        @Type(value = RPasoTramitacionPagar.class, name = "RPasoTramitacionPagar"),
        @Type(value = RPasoTramitacionRegistrar.class, name = "RPasoTramitacionRegistrar")})
public abstract class RPasoTramitacion {

    /** Identificador. */
    private String identificador;

    /** Tipo de paso. */
    private String tipo;

    /** Indica si es paso final. */
    private boolean pasoFinal;

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

    /**
     * Método de acceso a pasoFinal.
     * 
     * @return pasoFinal
     */
    public boolean isPasoFinal() {
        return pasoFinal;
    }

    /**
     * Método para establecer pasoFinal.
     * 
     * @param pasoFinal
     *            pasoFinal a establecer
     */
    public void setPasoFinal(boolean pasoFinal) {
        this.pasoFinal = pasoFinal;
    }

}
