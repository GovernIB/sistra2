package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Anexar.
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoAnexar implements TypeAccionPaso {

    /**
     * Anexar documento. Parámetros entrada: idAnexo, nombreFichero,
     * presentacion (TypePresentacion), datosFichero (byte[]), titulo.
     * Parámetros salida: conversionPDF (true/false)
     */
    ANEXAR_DOCUMENTO,
    /**
     * Borra un anexo. Parámetros entrada: idAnexo, instancia. Parámetros
     * salida: no tiene
     */
    BORRAR_ANEXO,
    /**
     * Descargar plantilla. Parámetros entrada: idAnexo. Parámetros salida:
     * nombreFichero, datosFichero (byte[])
     */
    DESCARGAR_PLANTILLA(false),
    /**
     * Descargar anexo. Parámetros entrada: idAnexo, instancia. Parámetros
     * salida: nombreFichero, datosFichero (byte[])
     */
    DESCARGAR_ANEXO(false),
    // TODO BORRAR
    /**
     * Parámetros entrada: idAnexo, instancia. Parámetros salida: url
     */
    TEST_FIRMAR(false),
    /**
     * Parámetros entrada: idAnexo, instancia. Parámetros salida:
     */
    TEST_RETORNO_FIRMA(false),
    /**
     * Parámetros entrada: idAnexo, instancia. Parámetros salida: nombreFichero,
     * datosFichero (byte[])
     */
    TEST_DESCARGAR_FIRMA(false);

    /**
     * Indica si la acción modifica datos del paso.
     */
    private boolean modificaPasoAnexar = true;

    /**
     * Constructor.
     *
     * @param pmodificaPaso
     *            Indica si modifica el paso.
     */
    private TypeAccionPasoAnexar(final boolean pmodificaPaso) {
        modificaPasoAnexar = pmodificaPaso;
    }

    /**
     * Constructor.
     */
    private TypeAccionPasoAnexar() {
    }

    @Override
    public boolean isModificaPaso() {
        return modificaPasoAnexar;
    }

}
