package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo expresión regular.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionCampoTextoExpReg
        extends ConfiguracionCampoTexto {
    /**
     * Constructor.
     */
    public ConfiguracionCampoTextoExpReg() {
        super();
        setContenido(TypeTexto.EXPRESION_REGULAR);
    }

    /**
     * Opciones particularizadas.
     */
    private OpcionesCampoTextoExpReg opciones = new OpcionesCampoTextoExpReg();

    /**
     * Método de acceso a opciones.
     *
     * @return opciones
     */
    public OpcionesCampoTextoExpReg getOpciones() {
        return opciones;
    }

    /**
     * Método para establecer opciones.
     *
     * @param pOpciones
     *            opciones a establecer
     */
    public void setOpciones(final OpcionesCampoTextoExpReg pOpciones) {
        opciones = pOpciones;
    }

}
