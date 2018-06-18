package es.caib.sistrages.rest.api;

import java.util.List;

public class VersionTramite {

    /** Identificador tramite. */
    private String identificador;

    /** Versión trámite. */
    private int version;

    /** Lista dominios utilizados. */
    private List<Dominio> dominios;

    /** Propiedades. */
    private VersionTramitePropiedades propiedades;

    /** Control acceso. */
    private VersionTramiteControlAcceso controlAcceso;

    /** Pasos tramitación. */
    private List<PasoTramitacion> pasos;

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
    public List<PasoTramitacion> getPasos() {
        return pasos;
    }

    /**
     * Método para establecer pasos.
     *
     * @param pasos
     *            pasos a establecer
     */
    public void setPasos(List<PasoTramitacion> pasos) {
        this.pasos = pasos;
    }

    /**
     * Método de acceso a propiedades.
     *
     * @return propiedades
     */
    public VersionTramitePropiedades getPropiedades() {
        return propiedades;
    }

    /**
     * Método para establecer propiedades.
     *
     * @param propiedades
     *            propiedades a establecer
     */
    public void setPropiedades(VersionTramitePropiedades propiedades) {
        this.propiedades = propiedades;
    }

    /**
     * Método de acceso a controlAcceso.
     *
     * @return controlAcceso
     */
    public VersionTramiteControlAcceso getControlAcceso() {
        return controlAcceso;
    }

    /**
     * Método para establecer controlAcceso.
     *
     * @param controlAcceso
     *            controlAcceso a establecer
     */
    public void setControlAcceso(VersionTramiteControlAcceso controlAcceso) {
        this.controlAcceso = controlAcceso;
    }

    /**
     * Método de acceso a dominios.
     * 
     * @return dominios
     */
    public List<Dominio> getDominios() {
        return dominios;
    }

    /**
     * Método para establecer dominios.
     * 
     * @param dominios
     *            dominios a establecer
     */
    public void setDominios(List<Dominio> dominios) {
        this.dominios = dominios;
    }

}
