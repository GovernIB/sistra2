package es.caib.sistrages.rest.api;

import java.util.List;

/**
 * Línea de componentes.
 *
 * @author Indra
 *
 */
public class LineaComponentes {

    /** Componentes. */
    private List<Componente> componentes;

    /**
     * Método de acceso a componentes.
     *
     * @return componentes
     */
    public List<Componente> getComponentes() {
        return componentes;
    }

    /**
     * Método para establecer componentes.
     *
     * @param componentes
     *            componentes a establecer
     */
    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }

}
