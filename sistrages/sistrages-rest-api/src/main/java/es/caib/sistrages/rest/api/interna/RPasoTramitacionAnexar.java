package es.caib.sistrages.rest.api.interna;

import java.util.List;

/**
 * Paso anexar.
 *
 * @author Indra
 *
 */
public class RPasoTramitacionAnexar extends RPasoTramitacion {

    /** Anexos. */
    private List<RAnexoTramite> anexos;

    /** Script anexos dinámicos. */
    private RScript scriptAnexosDinamicos;

    /**
     * Método de acceso a anexos.
     *
     * @return anexos
     */
    public List<RAnexoTramite> getAnexos() {
        return anexos;
    }

    /**
     * Método para establecer anexos.
     *
     * @param anexos
     *            anexos a establecer
     */
    public void setAnexos(List<RAnexoTramite> anexos) {
        this.anexos = anexos;
    }

    /**
     * Método de acceso a scriptAnexosDinamicos.
     *
     * @return scriptAnexosDinamicos
     */
    public RScript getScriptAnexosDinamicos() {
        return scriptAnexosDinamicos;
    }

    /**
     * Método para establecer scriptAnexosDinamicos.
     *
     * @param scriptAnexosDinamicos
     *            scriptAnexosDinamicos a establecer
     */
    public void setScriptAnexosDinamicos(RScript scriptAnexosDinamicos) {
        this.scriptAnexosDinamicos = scriptAnexosDinamicos;
    }

}
