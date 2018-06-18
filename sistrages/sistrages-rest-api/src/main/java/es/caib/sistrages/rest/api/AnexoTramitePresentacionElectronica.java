package es.caib.sistrages.rest.api;

import java.util.List;

/**
 * Presentación electrónica anexo trámite.
 *
 * @author Indra
 *
 */
public class AnexoTramitePresentacionElectronica {

    /** Número instancias. */
    private int instancias;

    /** Lista extensiones permitidas. Si vacía se permiten todas. */
    private List<String> extensiones;

    /** Tamaño máximo (0 sin restricción). */
    private int tamanyoMax;

    /** Tamaño unidad: KB / MB. */
    private String tamanyoUnidad;

    /** Convertir a PDF. */
    private boolean convertirPDF;

    /** Anexar firmado. */
    private boolean anexarFirmado;

    /** Firmar electrónicamente. */
    private boolean firmar;

    /** Script firmantes. */
    private Script scriptFirmantes;

    /** Script validacion. */
    private Script scriptValidacion;

    /**
     * Método de acceso a instancias.
     * 
     * @return instancias
     */
    public int getInstancias() {
        return instancias;
    }

    /**
     * Método para establecer instancias.
     * 
     * @param instancias
     *            instancias a establecer
     */
    public void setInstancias(int instancias) {
        this.instancias = instancias;
    }

    /**
     * Método de acceso a extensiones.
     * 
     * @return extensiones
     */
    public List<String> getExtensiones() {
        return extensiones;
    }

    /**
     * Método para establecer extensiones.
     * 
     * @param extensiones
     *            extensiones a establecer
     */
    public void setExtensiones(List<String> extensiones) {
        this.extensiones = extensiones;
    }

    /**
     * Método de acceso a tamanyoMax.
     * 
     * @return tamanyoMax
     */
    public int getTamanyoMax() {
        return tamanyoMax;
    }

    /**
     * Método para establecer tamanyoMax.
     * 
     * @param tamanyoMax
     *            tamanyoMax a establecer
     */
    public void setTamanyoMax(int tamanyoMax) {
        this.tamanyoMax = tamanyoMax;
    }

    /**
     * Método de acceso a tamanyoUnidad.
     * 
     * @return tamanyoUnidad
     */
    public String getTamanyoUnidad() {
        return tamanyoUnidad;
    }

    /**
     * Método para establecer tamanyoUnidad.
     * 
     * @param tamanyoUnidad
     *            tamanyoUnidad a establecer
     */
    public void setTamanyoUnidad(String tamanyoUnidad) {
        this.tamanyoUnidad = tamanyoUnidad;
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
     * @param convertirPDF
     *            convertirPDF a establecer
     */
    public void setConvertirPDF(boolean convertirPDF) {
        this.convertirPDF = convertirPDF;
    }

    /**
     * Método de acceso a anexarFirmado.
     * 
     * @return anexarFirmado
     */
    public boolean isAnexarFirmado() {
        return anexarFirmado;
    }

    /**
     * Método para establecer anexarFirmado.
     * 
     * @param anexarFirmado
     *            anexarFirmado a establecer
     */
    public void setAnexarFirmado(boolean anexarFirmado) {
        this.anexarFirmado = anexarFirmado;
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
     * @param firmar
     *            firmar a establecer
     */
    public void setFirmar(boolean firmar) {
        this.firmar = firmar;
    }

    /**
     * Método de acceso a scriptFirmantes.
     * 
     * @return scriptFirmantes
     */
    public Script getScriptFirmantes() {
        return scriptFirmantes;
    }

    /**
     * Método para establecer scriptFirmantes.
     * 
     * @param scriptFirmantes
     *            scriptFirmantes a establecer
     */
    public void setScriptFirmantes(Script scriptFirmantes) {
        this.scriptFirmantes = scriptFirmantes;
    }

    /**
     * Método de acceso a scriptValidacion.
     * 
     * @return scriptValidacion
     */
    public Script getScriptValidacion() {
        return scriptValidacion;
    }

    /**
     * Método para establecer scriptValidacion.
     * 
     * @param scriptValidacion
     *            scriptValidacion a establecer
     */
    public void setScriptValidacion(Script scriptValidacion) {
        this.scriptValidacion = scriptValidacion;
    }

}
