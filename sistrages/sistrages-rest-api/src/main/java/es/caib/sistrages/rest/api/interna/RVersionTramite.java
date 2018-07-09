package es.caib.sistrages.rest.api.interna;

import java.util.List;

public class RVersionTramite {

    /** Timestamp recuperacion. */
    private String timestamp;

    /** Identificador tramite. */
    private String identificador;

    /** Versión trámite. */
    private int version;

    /** Id entidad. */
    private String idEntidad;

    /** Idioma. */
    private String idioma;

    /** Tipo flujo: Normalizado (N) / Personalizado (P). */
    private String tipoFlujo;

    /** Lista dominios utilizados. */
    private List<RDominio> dominios;

    /** Propiedades. */
    private RVersionTramitePropiedades propiedades;

    /** Control acceso. */
    private RVersionTramiteControlAcceso controlAcceso;

    /** Pasos tramitación. */
    private List<RPasoTramitacion> pasos;

    /**
     * Método de acceso a idTramite.
     *
     * @return idTramite
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método para establecer idTramite.
     *
     * @param idTramite
     *            idTramite a establecer
     */
    public void setIdentificador(String idTramite) {
        this.identificador = idTramite;
    }

    /**
     * Método de acceso a version.
     *
     * @return version
     */
    public int getVersion() {
        return version;
    }

    /**
     * Método para establecer version.
     *
     * @param version
     *            version a establecer
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Método de acceso a pasos.
     *
     * @return pasos
     */
    public List<RPasoTramitacion> getPasos() {
        return pasos;
    }

    /**
     * Método para establecer pasos.
     *
     * @param pasos
     *            pasos a establecer
     */
    public void setPasos(List<RPasoTramitacion> pasos) {
        this.pasos = pasos;
    }

    /**
     * Método de acceso a propiedades.
     *
     * @return propiedades
     */
    public RVersionTramitePropiedades getPropiedades() {
        return propiedades;
    }

    /**
     * Método para establecer propiedades.
     *
     * @param propiedades
     *            propiedades a establecer
     */
    public void setPropiedades(RVersionTramitePropiedades propiedades) {
        this.propiedades = propiedades;
    }

    /**
     * Método de acceso a controlAcceso.
     *
     * @return controlAcceso
     */
    public RVersionTramiteControlAcceso getControlAcceso() {
        return controlAcceso;
    }

    /**
     * Método para establecer controlAcceso.
     *
     * @param controlAcceso
     *            controlAcceso a establecer
     */
    public void setControlAcceso(RVersionTramiteControlAcceso controlAcceso) {
        this.controlAcceso = controlAcceso;
    }

    /**
     * Método de acceso a dominios.
     *
     * @return dominios
     */
    public List<RDominio> getDominios() {
        return dominios;
    }

    /**
     * Método para establecer dominios.
     *
     * @param dominios
     *            dominios a establecer
     */
    public void setDominios(List<RDominio> dominios) {
        this.dominios = dominios;
    }

    /**
     * Método de acceso a idEntidad.
     *
     * @return idEntidad
     */
    public String getIdEntidad() {
        return idEntidad;
    }

    /**
     * Método para establecer idEntidad.
     *
     * @param idEntidad
     *            idEntidad a establecer
     */
    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
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

    /**
     * Método de acceso a tipoFlujo.
     *
     * @return tipoFlujo
     */
    public String getTipoFlujo() {
        return tipoFlujo;
    }

    /**
     * Método para establecer tipoFlujo.
     *
     * @param tipoFlujo
     *            tipoFlujo a establecer
     */
    public void setTipoFlujo(String tipoFlujo) {
        this.tipoFlujo = tipoFlujo;
    }

    /**
     * Método de acceso a timestamp.
     * 
     * @return timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Método para establecer timestamp.
     * 
     * @param timestamp
     *            timestamp a establecer
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
