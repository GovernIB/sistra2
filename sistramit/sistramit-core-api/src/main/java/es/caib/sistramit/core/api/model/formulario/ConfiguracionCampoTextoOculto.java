package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto oculto (hidden).
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ConfiguracionCampoTextoOculto extends ConfiguracionCampoTexto {

    /**
     * Constructor.
     */
    public ConfiguracionCampoTextoOculto() {
        super();
        setContenido(TypeTexto.OCULTO);
    }

}
