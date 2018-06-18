package es.caib.sistrages.rest.api;

/**
 * Formulario trámite.
 *
 * @author Indra
 *
 */
public class FormularioTramite {

    /** Identificador. */
    private String identificador;

    /** Descripción. */
    private String descripcion;

    /** Obligatoriedad: Si (S) / No (N) / Depende (D). */
    private String obligatoriedad;

    /** Script dependencia. */
    private Script scriptDependencia;

    /**
     * Indica si hay que firmar electrónicamente (para presentación
     * electrónica).
     */
    private boolean firmar;

    /** Script firmantes. */
    private Script scriptFirmantes;

    /**
     * Indica si hay que presentar en preregistro (para presentación
     * presencial).
     */
    private boolean presentarPreregistro;

    /** Script parámetros apertura. */
    private Script scriptParametrosApertura;

    /** Script datos iniciales. */
    private Script scriptDatosIniciales;

    /** Script post guardar. */
    private Script scriptPostguardar;

    /** Indica si es interno (si no externo). */
    private boolean interno;

    /** Si es interno, se indica el diseño del formulario. */
    private FormularioInterno formularioInterno;

    /** Si es externo, se indica el formulario externo. */
    private FormularioExterno formularioExterno;

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
     * Método de acceso a presentarPreregistro.
     *
     * @return presentarPreregistro
     */
    public boolean isPresentarPreregistro() {
        return presentarPreregistro;
    }

    /**
     * Método para establecer presentarPreregistro.
     *
     * @param presentarPreregistro
     *            presentarPreregistro a establecer
     */
    public void setPresentarPreregistro(boolean presentarPreregistro) {
        this.presentarPreregistro = presentarPreregistro;
    }

    /**
     * Método de acceso a scriptParametrosApertura.
     *
     * @return scriptParametrosApertura
     */
    public Script getScriptParametrosApertura() {
        return scriptParametrosApertura;
    }

    /**
     * Método para establecer scriptParametrosApertura.
     *
     * @param scriptParametrosApertura
     *            scriptParametrosApertura a establecer
     */
    public void setScriptParametrosApertura(Script scriptParametrosApertura) {
        this.scriptParametrosApertura = scriptParametrosApertura;
    }

    /**
     * Método de acceso a scriptDatosIniciales.
     *
     * @return scriptDatosIniciales
     */
    public Script getScriptDatosIniciales() {
        return scriptDatosIniciales;
    }

    /**
     * Método para establecer scriptDatosIniciales.
     *
     * @param scriptDatosIniciales
     *            scriptDatosIniciales a establecer
     */
    public void setScriptDatosIniciales(Script scriptDatosIniciales) {
        this.scriptDatosIniciales = scriptDatosIniciales;
    }

    /**
     * Método de acceso a scriptPostguardar.
     *
     * @return scriptPostguardar
     */
    public Script getScriptPostguardar() {
        return scriptPostguardar;
    }

    /**
     * Método para establecer scriptPostguardar.
     *
     * @param scriptPostguardar
     *            scriptPostguardar a establecer
     */
    public void setScriptPostguardar(Script scriptPostguardar) {
        this.scriptPostguardar = scriptPostguardar;
    }

    /**
     * Método de acceso a interno.
     *
     * @return interno
     */
    public boolean isInterno() {
        return interno;
    }

    /**
     * Método para establecer interno.
     *
     * @param interno
     *            interno a establecer
     */
    public void setInterno(boolean interno) {
        this.interno = interno;
    }

    /**
     * Método de acceso a formularioInterno.
     *
     * @return formularioInterno
     */
    public FormularioInterno getFormularioInterno() {
        return formularioInterno;
    }

    /**
     * Método para establecer formularioInterno.
     *
     * @param formularioInterno
     *            formularioInterno a establecer
     */
    public void setFormularioInterno(FormularioInterno formularioInterno) {
        this.formularioInterno = formularioInterno;
    }

    /**
     * Método de acceso a formularioExterno.
     *
     * @return formularioExterno
     */
    public FormularioExterno getFormularioExterno() {
        return formularioExterno;
    }

    /**
     * Método para establecer formularioExterno.
     *
     * @param formularioExterno
     *            formularioExterno a establecer
     */
    public void setFormularioExterno(FormularioExterno formularioExterno) {
        this.formularioExterno = formularioExterno;
    }

}
