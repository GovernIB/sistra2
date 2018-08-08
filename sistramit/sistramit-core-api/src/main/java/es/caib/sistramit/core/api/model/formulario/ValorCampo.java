package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.formulario.types.TypeValor;

/**
 * Valor de un campo de un formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public abstract class ValorCampo implements Serializable {

    /**
     * Id campo.
     */
    private String id;

    /**
     * Tipo de valor.
     */
    private TypeValor tipo;

    /**
     * Método de acceso a tipo.
     *
     * @return tipo
     */
    public final TypeValor getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param pTipo
     *            tipo a establecer
     */
    protected final void setTipo(final TypeValor pTipo) {
        tipo = pTipo;
    }

    /**
     * Método de acceso a idCampo.
     *
     * @return idCampo
     */
    public final String getId() {
        return id;
    }

    /**
     * Método para establecer idCampo.
     *
     * @param pIdCampo
     *            idCampo a establecer
     */
    public final void setId(final String pIdCampo) {
        id = pIdCampo;
    }

    /**
     * Indica si el valor es vacío.
     *
     * @return boolean
     */
    public abstract boolean esVacio();

    /**
     * Indica si el valor es igual al valor de otro campo.
     *
     * @param valorCampo
     *            Valor de otro campo con el que se comparara
     * @return boolean indicando si los valores de los campos son iguales
     */
    public abstract boolean esValorIgual(ValorCampo valorCampo);

    /**
     * Ofrece una vision en String del campo.
     *
     * @return String
     */
    public abstract String print();

    /**
     * Crea otro valor campo igual.
     *
     * @return ValorCampo
     */
    public abstract ValorCampo duplicar();
}
