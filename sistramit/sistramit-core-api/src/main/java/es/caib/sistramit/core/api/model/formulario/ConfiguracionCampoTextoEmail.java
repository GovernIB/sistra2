package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto email.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ConfiguracionCampoTextoEmail extends ConfiguracionCampoTexto {

    /**
     * Constructor.
     */
    public ConfiguracionCampoTextoEmail() {
        super();
        setContenido(TypeTexto.EMAIL);
    }

}
