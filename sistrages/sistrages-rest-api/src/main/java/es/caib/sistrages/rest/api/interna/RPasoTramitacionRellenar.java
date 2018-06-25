package es.caib.sistrages.rest.api.interna;

import java.util.List;

public class RPasoTramitacionRellenar extends RPasoTramitacion {

    /** Formularios. */
    private List<RFormularioTramite> formularios;

    /**
     * Método de acceso a formularios.
     *
     * @return formularios
     */
    public List<RFormularioTramite> getFormularios() {
        return formularios;
    }

    /**
     * Método para establecer formularios.
     *
     * @param formularios
     *            formularios a establecer
     */
    public void setFormularios(List<RFormularioTramite> formularios) {
        this.formularios = formularios;
    }

}
