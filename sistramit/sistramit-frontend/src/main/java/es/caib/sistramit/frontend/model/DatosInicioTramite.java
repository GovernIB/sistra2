package es.caib.sistramit.frontend.model;

import es.caib.sistramit.core.api.model.flujo.PersonaDesglosado;

/**
 * Datos inicio trámite.
 */
public class DatosInicioTramite {

    /** Código trámite. */
    private String tramite;

    /** Versión trámite. */
    private int version;

    /** Idioma. */
    private String idioma;

    /** Id trámite catálogo. */
    private String idTramiteCatalogo;

    /** Servicio catálogo. */
    private boolean servicioCatalogo;

    /** Parámetros inicio trámite. */
    private String parametros;

    /** Indica si fuerza nuevo trámite. */
    private boolean forzarNuevo;

    public DatosInicioTramite(String tramite, int version, String idioma, String idTramiteCatalogo, boolean servicioCatalogo, String parametros, boolean forzarNuevo) {
        this.tramite = tramite;
        this.version = version;
        this.idioma = idioma;
        this.idTramiteCatalogo = idTramiteCatalogo;
        this.servicioCatalogo = servicioCatalogo;
        this.parametros = parametros;
        this.forzarNuevo = forzarNuevo;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getIdTramiteCatalogo() {
        return idTramiteCatalogo;
    }

    public void setIdTramiteCatalogo(String idTramiteCatalogo) {
        this.idTramiteCatalogo = idTramiteCatalogo;
    }

    public boolean isServicioCatalogo() {
        return servicioCatalogo;
    }

    public void setServicioCatalogo(boolean servicioCatalogo) {
        this.servicioCatalogo = servicioCatalogo;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public boolean isForzarNuevo() {
        return forzarNuevo;
    }

    public void setForzarNuevo(boolean forzarNuevo) {
        this.forzarNuevo = forzarNuevo;
    }
}
