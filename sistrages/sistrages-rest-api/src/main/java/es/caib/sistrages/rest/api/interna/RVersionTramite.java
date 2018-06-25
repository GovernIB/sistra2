package es.caib.sistrages.rest.api.interna;

import java.util.List;

public class RVersionTramite {

    /** Identificador tramite. */
    private String identificador;

    /** Versión trámite. */
    private int version;

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

}
