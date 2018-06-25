package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypeFormulario;

/**
 * Respuesta a la acción de abrir un formulario externo. Devolverá una url para
 * redirigir el navegador.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class RedireccionFormularioExterno extends RedireccionFormulario {

    /**
     *
     * Constructor.
     */
    public RedireccionFormularioExterno() {
        super();
        setTipo(TypeFormulario.EXTERNO);
    }

    /**
     * Url de redirección al gestor de formularios externo.
     */
    private String url;

    /**
     * Indica url de redirección al gestor de formularios externo.
     *
     * @return url de redirección al gestor de formularios externo
     */
    public String getUrl() {
        return url;
    }

    /**
     * Indica url de redirección al gestor de formularios externo.
     *
     * @param pUrl
     *            url de redirección al gestor de formularios externo
     */
    public void setUrl(final String pUrl) {
        url = pUrl;
    }

}
