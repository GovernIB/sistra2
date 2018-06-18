package es.caib.sistramit.frontend.model;

/**
 * Información para mostrar asistente.
 *
 * @author Indra
 *
 */
public final class AsistenteInfo {

    /** Id sesion tramitación. */
    private String idSesionTramitacion;

    /** Idioma. */
    private String idioma;

    /**
     * Método de acceso a idSesionTramitacion.
     * 
     * @return idSesionTramitacion
     */
    public String getIdSesionTramitacion() {
        return idSesionTramitacion;
    }

    /**
     * Método para establecer idSesionTramitacion.
     * 
     * @param idSesionTramitacion
     *            idSesionTramitacion a establecer
     */
    public void setIdSesionTramitacion(String idSesionTramitacion) {
        this.idSesionTramitacion = idSesionTramitacion;
    }

    /**
     * Método de acceso a idioma.
     * 
     * @return idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Método para establecer idioma.
     * 
     * @param idioma
     *            idioma a establecer
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

}
