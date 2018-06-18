package es.caib.sistrages.rest.api;

import java.util.List;

/**
 * Paso anexar.
 *
 * @author Indra
 *
 */
public class PasoTramitacionAnexar extends PasoTramitacion {

    /** Anexos. */
    private List<AnexoTramite> anexos;

    /** Script anexos dinámicos. */
    private Script scriptAnexosDinamicos;

    /**
     * Método de acceso a anexos.
     * 
     * @return anexos
     */
    public List<AnexoTramite> getAnexos() {
        return anexos;
    }

    /**
     * Método para establecer anexos.
     * 
     * @param anexos
     *            anexos a establecer
     */
    public void setAnexos(List<AnexoTramite> anexos) {
        this.anexos = anexos;
    }

    /**
     * Método de acceso a scriptAnexosDinamicos.
     * 
     * @return scriptAnexosDinamicos
     */
    public Script getScriptAnexosDinamicos() {
        return scriptAnexosDinamicos;
    }

    /**
     * Método para establecer scriptAnexosDinamicos.
     * 
     * @param scriptAnexosDinamicos
     *            scriptAnexosDinamicos a establecer
     */
    public void setScriptAnexosDinamicos(Script scriptAnexosDinamicos) {
        this.scriptAnexosDinamicos = scriptAnexosDinamicos;
    }

}
