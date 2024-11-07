package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto teléfono internacional.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionCampoTextoTelefonoInternacional
        extends ConfiguracionCampoTexto {

    /**
     * Constructor.
     */
    public ConfiguracionCampoTextoTelefonoInternacional() {
        super();
        setContenido(TypeTexto.TELEFONO_INTERNACIONAL);
    }

    /**
     * Opciones particularizadas.
     */
    private OpcionesCampoTextoTelefonoInternacional opciones = new OpcionesCampoTextoTelefonoInternacional();

    /**
     * Método de acceso a opciones.
     *
     * @return opciones
     */
    public OpcionesCampoTextoTelefonoInternacional getOpciones() {
        return opciones;
    }

    /**
     * Método para establecer opciones.
     *
     * @param pOpciones
     *            opciones a establecer
     */
    public void setOpciones(final OpcionesCampoTextoTelefonoInternacional pOpciones) {
        opciones = pOpciones;
    }

}
