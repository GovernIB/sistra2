package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Parámetro dominio.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RParametroDominio", description = "Descripcion de RParametroDominio")
public class RParametroDominio {

    /** Identificador parámetro. */
	@ApiModelProperty(value = "Identificador parámetro")
    private String identificador;

    /** Tipo parámetro: CONSTANTE("C"), CAMPO("M"), PARAMETRO("P"). */
	@ApiModelProperty(value = "Tipo parámetro: CONSTANTE(C), CAMPO(M), PARAMETRO(P)")
    private String tipo;

    /** Valor parámetro. */
	@ApiModelProperty(value = "Valor parámetro")
    private String valor;

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
     * Método de acceso a valor.
     *
     * @return valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * Método para establecer valor.
     *
     * @param valor
     *            valor a establecer
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

}
