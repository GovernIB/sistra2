package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto teléfono.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionCampoTextoTelefono
        extends ConfiguracionCampoTexto {

    /**
     * Constructor.
     */
    public ConfiguracionCampoTextoTelefono() {
        super();
        setContenido(TypeTexto.TELEFONO);
    }

    /**
     * Opciones particularizadas.
     */
    private OpcionesCampoTextoTelefono opciones = new OpcionesCampoTextoTelefono();

    /**
     * Método de acceso a opciones.
     *
     * @return opciones
     */
    public OpcionesCampoTextoTelefono getOpciones() {
        return opciones;
    }

    /**
     * Método para establecer opciones.
     *
     * @param pOpciones
     *            opciones a establecer
     */
    public void setOpciones(final OpcionesCampoTextoTelefono pOpciones) {
        opciones = pOpciones;
    }

}
