package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto número.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionCampoTextoNumero
        extends ConfiguracionCampoTexto {

    /**
     * Constructor.
     */
    public ConfiguracionCampoTextoNumero() {
        super();
        setContenido(TypeTexto.NUMERO);
    }

    /**
     * Número máximo de dígitos parte entera.
     */
    private OpcionesCampoTextoNumero opciones = new OpcionesCampoTextoNumero();

    /**
     * Método de acceso a opciones.
     *
     * @return opciones
     */
    public OpcionesCampoTextoNumero getOpciones() {
        return opciones;
    }

    /**
     * Método para establecer opciones.
     *
     * @param pOpciones
     *            opciones a establecer
     */
    public void setOpciones(final OpcionesCampoTextoNumero pOpciones) {
        opciones = pOpciones;
    }

}
