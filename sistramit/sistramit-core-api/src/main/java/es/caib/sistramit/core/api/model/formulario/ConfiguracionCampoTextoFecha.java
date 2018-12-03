package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto fecha.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ConfiguracionCampoTextoFecha extends ConfiguracionCampoTexto {

    /**
     * Constructor.
     */
    public ConfiguracionCampoTextoFecha() {
        super();
        setContenido(TypeTexto.FECHA);
    }

}
