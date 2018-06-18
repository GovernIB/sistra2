package es.caib.sistrages.rest.api;

import java.util.List;

/**
 * Avisos entidad.
 *
 * @author Indra
 *
 */
public class AvisosEntidad {

    /** Avisos. */
    private List<Aviso> avisos;

    /**
     * Método de acceso a avisos.
     * 
     * @return avisos
     */
    public List<Aviso> getAvisos() {
        return avisos;
    }

    /**
     * Método para establecer avisos.
     * 
     * @param avisos
     *            avisos a establecer
     */
    public void setAvisos(List<Aviso> avisos) {
        this.avisos = avisos;
    }

}
