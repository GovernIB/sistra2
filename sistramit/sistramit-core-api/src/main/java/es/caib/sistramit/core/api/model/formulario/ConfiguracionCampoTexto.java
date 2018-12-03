package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public abstract class ConfiguracionCampoTexto extends ConfiguracionCampo {

    /**
     * Constructor.
     */
    public ConfiguracionCampoTexto() {
        super();
        setTipo(TypeCampo.TEXTO);
    }

    /**
     * Tipo de texto.
     */
    private TypeTexto contenido;

    /**
     * Método de acceso a tipoTexto.
     *
     * @return tipoTexto
     */
    public final TypeTexto getContenido() {
        return contenido;
    }

    /**
     * Método para establecer tipoTexto.
     *
     * @param pTipoTexto
     *            tipoTexto a establecer
     */
    protected final void setContenido(final TypeTexto pTipoTexto) {
        contenido = pTipoTexto;
    }

}
