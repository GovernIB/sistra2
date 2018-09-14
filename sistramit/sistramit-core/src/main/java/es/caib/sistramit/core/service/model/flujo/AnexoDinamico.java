package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

/**
 * Información de un anexo definido de forma dinámica.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AnexoDinamico implements Serializable {

    /**
     * Identificador anexo dinamico.
     */
    private String identificador;

    /**
     * Descripcion.
     */
    private String descripcion;

    /**
     * Extensiones (lista separadas por coma).
     */
    private String extensiones;

    /**
     * Tamaño máximo (con sufijo KB o MB).
     */
    private String tamanyoMaximo;

    /**
     * Url plantilla.
     */
    private String urlPlantilla;

    /**
     * Obligatorio.
     */
    private boolean obligatorio;

    /**
     * Convertir a PDF.
     */
    private boolean convertirPDF;

    /**
     * Firmar.
     */
    private boolean firmar;

    /**
     * Método de acceso a identificador anexo dinamico.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método para establecer identificador anexo dinamico.
     *
     * @param pIdentificador
     *            identificador a establecer
     */
    public void setIdentificador(final String pIdentificador) {
        identificador = pIdentificador;
    }

    /**
     * Método de acceso a descripcion anexo dinamico.
     *
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método para establecer descripcion anexo dinamico.
     *
     * @param pDescripcion
     *            descripcion a establecer
     */
    public void setDescripcion(final String pDescripcion) {
        descripcion = pDescripcion;
    }

    /**
     * Indica una url externa como plantilla.
     *
     * @return urlPlantilla
     */
    public String getUrlPlantilla() {
        return urlPlantilla;
    }

    /**
     * Indica una url externa como plantilla.
     *
     * @param pUrlPlantilla
     *            urlPlantilla a establecer
     */
    public void setUrlPlantilla(final String pUrlPlantilla) {
        urlPlantilla = pUrlPlantilla;
    }

    /**
     * Indica si el anexo dinamico es obligatorio.
     *
     * @return obligatorio
     */
    public boolean isObligatorio() {
        return obligatorio;
    }

    /**
     * Indica si el anexo dinamico es obligatorio.
     *
     * @param pObligatorio
     *            obligatorio a establecer
     */
    public void setObligatorio(final boolean pObligatorio) {
        obligatorio = pObligatorio;
    }

    /**
     * Indica las extensiones permitidas para el anexo dinamico.
     *
     * @return extensiones
     */
    public String getExtensiones() {
        return extensiones;
    }

    /**
     * Indica las extensiones permitidas para el anexo dinamico.
     *
     * @param pExtensiones
     *            extensiones a establecer
     */
    public void setExtensiones(final String pExtensiones) {
        extensiones = pExtensiones;
    }

    /**
     * Indica el tamaño máximo para el anexo dinamico.
     *
     * @return tamanyoMaximo
     */
    public String getTamanyoMaximo() {
        return tamanyoMaximo;
    }

    /**
     * Indica el tamaño máximo para el anexo dinamico.
     *
     * @param pTamanyoMaximo
     *            tamanyoMaximo a establecer
     */
    public void setTamanyoMaximo(final String pTamanyoMaximo) {
        tamanyoMaximo = pTamanyoMaximo;
    }

    /**
     * Método de acceso a convertirPDF.
     *
     * @return convertirPDF
     */
    public boolean isConvertirPDF() {
        return convertirPDF;
    }

    /**
     * Método para establecer convertirPDF.
     *
     * @param pConvertirPDF
     *            convertirPDF a establecer
     */
    public void setConvertirPDF(final boolean pConvertirPDF) {
        convertirPDF = pConvertirPDF;
    }

    /**
     * Método de acceso a firmar.
     *
     * @return firmar
     */
    public boolean isFirmar() {
        return firmar;
    }

    /**
     * Método para establecer firmar.
     *
     * @param pFirmar
     *            firmar a establecer
     */
    public void setFirmar(final boolean pFirmar) {
        firmar = pFirmar;
    }

}
