package es.caib.sistrages.rest.api;

/**
 * Componente textbox.
 *
 * @author Indra
 *
 */
public class ComponenteTextbox extends Componente {

    /** Propiedades campo. */
    private PropiedadesCampo propiedadesCampo;

    /**
     * Tipo texto: NORMAL, NUMERO, EMAIL, ID, CP, TELEFONO, FECHA, HORA,
     * EXPRESION, IMPORTE.
     */
    private String tipoTexto;

    /** Propiedades texto normal. */
    private PropiedadesTextoNormal textoNormal;

    /** Propiedades texto numero. */
    private PropiedadesTextoNumero textoNumero;

    /** Propiedades texto identificacion. */
    private PropiedadesTextoIdentificacion textoIdentificacion;

    /** Propiedades texto expresión regular. */
    private PropiedadesTextoExpRegular textoExpRegular;

    /**
     * Método de acceso a propiedadesCampo.
     *
     * @return propiedadesCampo
     */
    public PropiedadesCampo getPropiedadesCampo() {
        return propiedadesCampo;
    }

    /**
     * Método para establecer propiedadesCampo.
     *
     * @param propiedadesCampo
     *            propiedadesCampo a establecer
     */
    public void setPropiedadesCampo(PropiedadesCampo propiedadesCampo) {
        this.propiedadesCampo = propiedadesCampo;
    }

    /**
     * Método de acceso a tipoTexto.
     *
     * @return tipoTexto
     */
    public String getTipoTexto() {
        return tipoTexto;
    }

    /**
     * Método para establecer tipoTexto.
     *
     * @param tipoTexto
     *            tipoTexto a establecer
     */
    public void setTipoTexto(String tipoTexto) {
        this.tipoTexto = tipoTexto;
    }

    /**
     * Método de acceso a textoNormal.
     *
     * @return textoNormal
     */
    public PropiedadesTextoNormal getTextoNormal() {
        return textoNormal;
    }

    /**
     * Método para establecer textoNormal.
     *
     * @param textoNormal
     *            textoNormal a establecer
     */
    public void setTextoNormal(PropiedadesTextoNormal textoNormal) {
        this.textoNormal = textoNormal;
    }

    /**
     * Método de acceso a textoNumero.
     *
     * @return textoNumero
     */
    public PropiedadesTextoNumero getTextoNumero() {
        return textoNumero;
    }

    /**
     * Método para establecer textoNumero.
     *
     * @param textoNumero
     *            textoNumero a establecer
     */
    public void setTextoNumero(PropiedadesTextoNumero textoNumero) {
        this.textoNumero = textoNumero;
    }

    /**
     * Método de acceso a textoIdentificacion.
     *
     * @return textoIdentificacion
     */
    public PropiedadesTextoIdentificacion getTextoIdentificacion() {
        return textoIdentificacion;
    }

    /**
     * Método para establecer textoIdentificacion.
     *
     * @param textoIdentificacion
     *            textoIdentificacion a establecer
     */
    public void setTextoIdentificacion(
            PropiedadesTextoIdentificacion textoIdentificacion) {
        this.textoIdentificacion = textoIdentificacion;
    }

    /**
     * Método de acceso a textoExpRegular.
     *
     * @return textoExpRegular
     */
    public PropiedadesTextoExpRegular getTextoExpRegular() {
        return textoExpRegular;
    }

    /**
     * Método para establecer textoExpRegular.
     *
     * @param textoExpRegular
     *            textoExpRegular a establecer
     */
    public void setTextoExpRegular(PropiedadesTextoExpRegular textoExpRegular) {
        this.textoExpRegular = textoExpRegular;
    }

}
