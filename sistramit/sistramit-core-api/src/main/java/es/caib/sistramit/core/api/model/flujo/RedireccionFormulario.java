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
public abstract class RedireccionFormulario implements Serializable {

    /**
     * Indica si es externo o interno.
     */
    private TypeFormulario tipo;

    /**
     * Método de acceso a tipo.
     *
     * @return tipo
     */
    public final TypeFormulario getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param pTipo
     *            tipo a establecer
     */
    protected final void setTipo(final TypeFormulario pTipo) {
        tipo = pTipo;
    }

}
