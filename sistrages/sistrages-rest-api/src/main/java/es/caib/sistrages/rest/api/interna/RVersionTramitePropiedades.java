package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Propiedades versión trámite.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RVersionTramitePropiedades", description = "Descripcion de RVersionTramitePropiedades")
public class RVersionTramitePropiedades {

    /** Autenticado. */
	@ApiModelProperty(value = "Autenticado")
    private boolean autenticado;

    /** Nivel QAA de autenticación. */
	@ApiModelProperty(value = "Nivel QAA de autenticación")
    private int nivelQAA;

    /** Autenticado. */
	@ApiModelProperty(value = "Autenticado")
    private boolean noAutenticado;

    /** Idiomas. */
	@ApiModelProperty(value = "Idiomas")
    private List<String> idiomas;

    /** Persistente. */
	@ApiModelProperty(value = "Persistente")
    private boolean persistente;

    /** En caso de ser persistente indica días persistencia (si 0 infinita). */
	@ApiModelProperty(value = "En caso de ser persistente indica días persistencia (si 0 infinita)")
    private int diasPersistencia;

    /** Script personalizacion. */
	@ApiModelProperty(value = "Script personalizacion")
    private RScript scriptPersonalizacion;

    /** Script parametros iniciales. */
	@ApiModelProperty(value = "Script parametros iniciales")
    private RScript scriptParametrosIniciales;

    /**
     * Método de acceso a autenticado.
     *
     * @return autenticado
     */
    public boolean isAutenticado() {
        return autenticado;
    }

    /**
     * Método para establecer autenticado.
     *
     * @param autenticado
     *            autenticado a establecer
     */
    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    /**
     * Método de acceso a noAutenticado.
     *
     * @return noAutenticado
     */
    public boolean isNoAutenticado() {
        return noAutenticado;
    }

    /**
     * Método para establecer noAutenticado.
     *
     * @param noAutenticado
     *            noAutenticado a establecer
     */
    public void setNoAutenticado(boolean noAutenticado) {
        this.noAutenticado = noAutenticado;
    }

    /**
     * Método de acceso a idiomas.
     *
     * @return idiomas
     */
    public List<String> getIdiomas() {
        return idiomas;
    }

    /**
     * Método para establecer idiomas.
     *
     * @param idiomas
     *            idiomas a establecer
     */
    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    /**
     * Método de acceso a persistente.
     *
     * @return persistente
     */
    public boolean isPersistente() {
        return persistente;
    }

    /**
     * Método para establecer persistente.
     *
     * @param persistente
     *            persistente a establecer
     */
    public void setPersistente(boolean persistente) {
        this.persistente = persistente;
    }

    /**
     * Método de acceso a diasPersistencia.
     *
     * @return diasPersistencia
     */
    public int getDiasPersistencia() {
        return diasPersistencia;
    }

    /**
     * Método para establecer diasPersistencia.
     *
     * @param diasPersistencia
     *            diasPersistencia a establecer
     */
    public void setDiasPersistencia(int diasPersistencia) {
        this.diasPersistencia = diasPersistencia;
    }

    /**
     * Método de acceso a scriptPersonalizacion.
     *
     * @return scriptPersonalizacion
     */
    public RScript getScriptPersonalizacion() {
        return scriptPersonalizacion;
    }

    /**
     * Método para establecer scriptPersonalizacion.
     *
     * @param scriptPersonalizacion
     *            scriptPersonalizacion a establecer
     */
    public void setScriptPersonalizacion(RScript scriptPersonalizacion) {
        this.scriptPersonalizacion = scriptPersonalizacion;
    }

    /**
     * Método de acceso a scriptParametrosIniciales.
     *
     * @return scriptParametrosIniciales
     */
    public RScript getScriptParametrosIniciales() {
        return scriptParametrosIniciales;
    }

    /**
     * Método para establecer scriptParametrosIniciales.
     *
     * @param scriptParametrosIniciales
     *            scriptParametrosIniciales a establecer
     */
    public void setScriptParametrosIniciales(RScript scriptParametrosIniciales) {
        this.scriptParametrosIniciales = scriptParametrosIniciales;
    }

    /**
     * Método de acceso a nivelQAA.
     *
     * @return nivelQAA
     */
    public int getNivelQAA() {
        return nivelQAA;
    }

    /**
     * Método para establecer nivelQAA.
     *
     * @param nivelQAA
     *            nivelQAA a establecer
     */
    public void setNivelQAA(int nivelQAA) {
        this.nivelQAA = nivelQAA;
    }

}
