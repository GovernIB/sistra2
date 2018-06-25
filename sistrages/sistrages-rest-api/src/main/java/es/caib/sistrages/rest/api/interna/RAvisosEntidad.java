package es.caib.sistrages.rest.api.interna;

import java.util.List;

/**
 * Avisos entidad.
 *
 * @author Indra
 *
 */
public class RAvisosEntidad {

    /** Avisos. */
    private List<RAviso> avisos;

    /**
     * Método de acceso a avisos.
     *
     * @return avisos
     */
    public List<RAviso> getAvisos() {
        return avisos;
    }

    /**
     * Método para establecer avisos.
     *
     * @param avisos
     *            avisos a establecer
     */
    public void setAvisos(List<RAviso> avisos) {
        this.avisos = avisos;
    }

}
