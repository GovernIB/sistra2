package es.caib.sistrages.rest.api;

import java.util.List;

public class PasoTramitacionRellenar extends PasoTramitacion {

    /** Formularios. */
    private List<FormularioTramite> formularios;

    /**
     * Método de acceso a formularios.
     * 
     * @return formularios
     */
    public List<FormularioTramite> getFormularios() {
        return formularios;
    }

    /**
     * Método para establecer formularios.
     * 
     * @param formularios
     *            formularios a establecer
     */
    public void setFormularios(List<FormularioTramite> formularios) {
        this.formularios = formularios;
    }

}
