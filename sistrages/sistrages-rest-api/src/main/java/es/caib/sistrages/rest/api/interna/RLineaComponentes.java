package es.caib.sistrages.rest.api.interna;

import java.util.List;

/**
 * Línea de componentes.
 *
 * @author Indra
 *
 */
public class RLineaComponentes {

    /** Componentes. */
    private List<RComponente> componentes;

    /**
     * Método de acceso a componentes.
     *
     * @return componentes
     */
    public List<RComponente> getComponentes() {
        return componentes;
    }

    /**
     * Método para establecer componentes.
     *
     * @param componentes
     *            componentes a establecer
     */
    public void setComponentes(List<RComponente> componentes) {
        this.componentes = componentes;
    }

}
