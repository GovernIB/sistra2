package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.types.TypeFormulario;

/**
 *
 * Respuesta a la acción de abrir un formulario. En caso de ser un formulario
 * interno devolverá el html del formulario y los datos del formulario. En caso
 * de ser un formulario externo devolverá una url para redirigir el navegador.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AbrirFormulario implements Serializable {

    /**
     * Indica si es externo o interno.
     */
    private TypeFormulario tipo;

    /**
     * Url redireccion formulario.
     */
    private String url;

    /**
     * Método de acceso a tipo.
     *
     * @return tipo
     */
    public TypeFormulario getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param pTipo
     *            tipo a establecer
     */
    public void setTipo(final TypeFormulario pTipo) {
        tipo = pTipo;
    }

    /**
     * Método de acceso a url.
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Método para establecer url.
     *
     * @param url
     *            url a establecer
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
