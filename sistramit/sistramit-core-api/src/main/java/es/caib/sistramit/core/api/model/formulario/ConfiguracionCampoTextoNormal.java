package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto normal.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionCampoTextoNormal
        extends ConfiguracionCampoTexto {

    /**
     * Opciones campo normal.
     */
    private OpcionesCampoTextoNormal opciones = new OpcionesCampoTextoNormal();

    /**
     * Constructor.
     */
    public ConfiguracionCampoTextoNormal() {
        super();
        setContenido(TypeTexto.NORMAL);
    }

    /**
     * Método de acceso a opciones.
     *
     * @return opciones
     */
    public OpcionesCampoTextoNormal getOpciones() {
        return opciones;
    }

    /**
     * Método para establecer opciones.
     *
     * @param pOpciones
     *            opciones a establecer
     */
    public void setOpciones(final OpcionesCampoTextoNormal pOpciones) {
        opciones = pOpciones;
    }

}
