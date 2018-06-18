package es.caib.sistrages.rest.api;

/**
 * Anexo trámite.
 *
 * @author Indra
 *
 */
public class AnexoTramite {

    /** Identificador. */
    private String identificador;

    /** Descripción. */
    private String descripcion;

    /** Obligatoriedad: Si (S) / No (N) / Depende (D). */
    private String obligatoriedad;

    /** Script dependencia. */
    private Script scriptDependencia;

    /** Ayuda anexo. */
    private AnexoTramiteAyuda ayuda;

    /** Tipo presentacion: Electrónica (E) / Presencial (P). */
    private String presentacion;

    /** Propiedades para presentación electrónica. */
    private AnexoTramitePresentacionElectronica presentacionElectronica;

    /**
     * Método de acceso a identificador.
     * 
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método para establecer identificador.
     * 
     * @param identificador
     *            identificador a establecer
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Método de acceso a descripcion.
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método para establecer descripcion.
     * 
     * @param descripcion
     *            descripcion a establecer
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Método de acceso a obligatoriedad.
     * 
     * @return obligatoriedad
     */
    public String getObligatoriedad() {
        return obligatoriedad;
    }

    /**
     * Método para establecer obligatoriedad.
     * 
     * @param obligatoriedad
     *            obligatoriedad a establecer
     */
    public void setObligatoriedad(String obligatoriedad) {
        this.obligatoriedad = obligatoriedad;
    }

    /**
     * Método de acceso a scriptDependencia.
     * 
     * @return scriptDependencia
     */
    public Script getScriptDependencia() {
        return scriptDependencia;
    }

    /**
     * Método para establecer scriptDependencia.
     * 
     * @param scriptDependencia
     *            scriptDependencia a establecer
     */
    public void setScriptDependencia(Script scriptDependencia) {
        this.scriptDependencia = scriptDependencia;
    }

    /**
     * Método de acceso a ayuda.
     * 
     * @return ayuda
     */
    public AnexoTramiteAyuda getAyuda() {
        return ayuda;
    }

    /**
     * Método para establecer ayuda.
     * 
     * @param ayuda
     *            ayuda a establecer
     */
    public void setAyuda(AnexoTramiteAyuda ayuda) {
        this.ayuda = ayuda;
    }

    /**
     * Método de acceso a presentacion.
     * 
     * @return presentacion
     */
    public String getPresentacion() {
        return presentacion;
    }

    /**
     * Método para establecer presentacion.
     * 
     * @param presentacion
     *            presentacion a establecer
     */
    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    /**
     * Método de acceso a presentacionElectronica.
     * 
     * @return presentacionElectronica
     */
    public AnexoTramitePresentacionElectronica getPresentacionElectronica() {
        return presentacionElectronica;
    }

    /**
     * Método para establecer presentacionElectronica.
     * 
     * @param presentacionElectronica
     *            presentacionElectronica a establecer
     */
    public void setPresentacionElectronica(
            AnexoTramitePresentacionElectronica presentacionElectronica) {
        this.presentacionElectronica = presentacionElectronica;
    }

}
