package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto password.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionCampoTextoPassword
        extends ConfiguracionCampoTexto {

    /**
     * Opciones tipo password.
     */
    private OpcionesCampoTextoPassword opciones = new OpcionesCampoTextoPassword();

    /**
     * Constructor.
     */
    public ConfiguracionCampoTextoPassword() {
        super();
        setContenido(TypeTexto.PASSWORD);
    }

    /**
     * Método de acceso a opciones.
     *
     * @return opciones
     */
    public OpcionesCampoTextoPassword getOpciones() {
        return opciones;
    }

    /**
     * Método para establecer opciones.
     *
     * @param pOpciones
     *            opciones a establecer
     */
    public void setOpciones(final OpcionesCampoTextoPassword pOpciones) {
        opciones = pOpciones;
    }

}
