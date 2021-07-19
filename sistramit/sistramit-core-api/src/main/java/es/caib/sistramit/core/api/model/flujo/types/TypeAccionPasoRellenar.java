package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Rellenar.
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoRellenar implements TypeAccionPaso {

    /**
     * Abrir formulario. Parámetros entrada: idFormulario. Parámetros salida:
     * referencia (AbrirFormulario).
     */
    ABRIR_FORMULARIO,

    /**
     * Guardar formulario. Parámetros entrada: idFormulario / ticket /
     * borrarOpcional (TypeSiNo). Parámetros salida: no tiene.
     */
    GUARDAR_FORMULARIO,

    /**
     * Descargar formulario. Parámetros entrada: idFormulario Parámetros salida:
     * nombreFichero, datosFichero (byte[])
     */
    DESCARGAR_FORMULARIO(false),

    /**
     * Descargar xml para debug. Parámetros entrada: idFormulario Parámetros
     * salida: xml (byte[])
     */
    DESCARGAR_XML(false);

    /**
     * Indica si la acción modifica datos del paso.
     */
    private boolean modificaPasoRelenar = true;

    /**
     * Constructor.
     *
     * @param pmodificaPaso
     *            Indica si modifica el paso.
     */
    private TypeAccionPasoRellenar(final boolean pmodificaPaso) {
        modificaPasoRelenar = pmodificaPaso;
    }

    /**
     * Constructor.
     */
    private TypeAccionPasoRellenar() {
    }

    @Override
    public boolean isModificaPaso() {
        return modificaPasoRelenar;
    }
}
