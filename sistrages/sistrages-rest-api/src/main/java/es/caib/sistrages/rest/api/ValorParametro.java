package es.caib.sistrages.rest.api;

/**
 * Valor parámetro.
 *
 * @author Indra
 *
 */
public class ValorParametro {

    /**
     * Código.
     */
    private String codigo;

    /**
     * Valor.
     */
    private String valor;

    /**
     * Método de acceso a codigo.
     * 
     * @return codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Método para establecer codigo.
     * 
     * @param codigo
     *            codigo a establecer
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
