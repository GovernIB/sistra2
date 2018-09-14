package es.caib.sistramit.core.service.model.script;

import javax.script.ScriptException;

/**
 * * Datos para establecer los anexos de forma dinámica indicando la siguiente
 * información por anexo: identificador, descripcion, extensiones, tamaño
 * maximo, url plantilla, obligatorio, convertir pdf y firmar.
 *
 * @author Indra
 *
 */
public interface ResAnexosDinamicosInt extends PluginScriptRes {

    /**
     * Id plugin.
     */
    String ID = "DATOS_ANEXOS_DINAMICOS";

    /**
     * Añade un nuevo anexo.
     *
     * @param identificador
     *            Identificador
     * @param descripcion
     *            Descripcion
     * @param extensiones
     *            Extensiones (lista separado por comas).
     * @param tamanyoMax
     *            Tamaño máximo (con sufijo MB / KB). Opcional.
     * @param urlPlantilla
     *            Url plantilla. Opcional.
     * @param obligatorio
     *            Obligatorio
     * @param convertirPDF
     *            Indica si se debe convertir a PDF
     * @param firmar
     *            Indica si se debe firmar
     * @throws ScriptException
     *             Excepcion
     */
    void addAnexo(final String identificador, final String descripcion,
            final String extensiones, final String tamanyoMax,
            final String urlPlantilla, final boolean obligatorio,
            final boolean pConvertirPDF, final boolean pFirmar)
            throws ScriptException;

}
