package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteCP;

/**
 * Variables accesibles desde un paso de tramitación. Permiten el acceso desde
 * el paso a los elementos compartidos (información de sesión, formularios,
 * pagos, ...)
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class VariablesFlujo implements Serializable {

    /**
     * Entorno.
     */
    private TypeEntorno entorno;

    /**
     * Identificador de sesión de tramitación.
     */
    private String idSesionTramitacion;

    /**
     * Nivel autenticación del usuario actualmente autenticado.
     */
    private TypeAutenticacion nivelAutenticacion;

    /**
     * Metodo autenticación iniciador.
     */
    private TypeMetodoAutenticacion metodoAutenticacion;

    /**
     * Datos del usuario que esta actualmente autenticado.
     */
    private DatosUsuario usuario;

    /**
     * Idioma de tramitación.
     */
    private String idioma;

    /**
     * Url inicio tramite.
     */
    private String urlInicioTramite;

    /**
     * Id tramite.
     */
    private String idTramite;

    /**
     * Versión tramite.
     */
    private int versionTramite;

    /**
     * Titulo del trámite.
     */
    private String tituloTramite;

    /**
     * Parámetros de inicio.
     */
    private Map<String, String> parametrosInicio;

    /**
     * Datos provenientes de catálogo de procedimientos.
     */
    private DefinicionTramiteCP datosTramiteCP;

    /**
     * Documentos accesibles desde el paso pertenecientes a pasos anteriores.
     */
    private List<DatosDocumento> documentos = new ArrayList<>();

    /**
     * Inicio plazo del trámite.
     */
    private Date fechaInicioPlazo;

    /**
     * Fin plazo del tramite.
     */
    private Date fechaFinPlazo;

    /**
     * Indica si se debe debugear el trámite.
     */
    private boolean debugEnabled;

    public String getIdSesionTramitacion() {
        return idSesionTramitacion;
    }

    public void setIdSesionTramitacion(final String idSesionTramitacion) {
        this.idSesionTramitacion = idSesionTramitacion;
    }

    public TypeAutenticacion getNivelAutenticacion() {
        return nivelAutenticacion;
    }

    public void setNivelAutenticacion(
            final TypeAutenticacion nivelAutenticacion) {
        this.nivelAutenticacion = nivelAutenticacion;
    }

    public TypeMetodoAutenticacion getMetodoAutenticacion() {
        return metodoAutenticacion;
    }

    public void setMetodoAutenticacion(
            final TypeMetodoAutenticacion metodoAutenticacion) {
        this.metodoAutenticacion = metodoAutenticacion;
    }

    public DatosUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(final DatosUsuario usuario) {
        this.usuario = usuario;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(final String idioma) {
        this.idioma = idioma;
    }

    public String getUrlInicioTramite() {
        return urlInicioTramite;
    }

    public void setUrlInicioTramite(final String urlInicioTramite) {
        this.urlInicioTramite = urlInicioTramite;
    }

    public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(final String idTramite) {
        this.idTramite = idTramite;
    }

    public int getVersionTramite() {
        return versionTramite;
    }

    public void setVersionTramite(final int versionTramite) {
        this.versionTramite = versionTramite;
    }

    public String getTituloTramite() {
        return tituloTramite;
    }

    public void setTituloTramite(final String tituloTramite) {
        this.tituloTramite = tituloTramite;
    }

    public Map<String, String> getParametrosInicio() {
        return parametrosInicio;
    }

    public void setParametrosInicio(
            final Map<String, String> parametrosInicio) {
        this.parametrosInicio = parametrosInicio;
    }

    public DefinicionTramiteCP getDatosTramiteCP() {
        return datosTramiteCP;
    }

    public void setDatosTramiteCP(final DefinicionTramiteCP datosTramiteCP) {
        this.datosTramiteCP = datosTramiteCP;
    }

    public List<DatosDocumento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(final List<DatosDocumento> documentos) {
        this.documentos = documentos;
    }

    public Date getFechaInicioPlazo() {
        return fechaInicioPlazo;
    }

    public void setFechaInicioPlazo(final Date fechaInicioPlazo) {
        this.fechaInicioPlazo = fechaInicioPlazo;
    }

    public Date getFechaFinPlazo() {
        return fechaFinPlazo;
    }

    public void setFechaFinPlazo(final Date fechaFinPlazo) {
        this.fechaFinPlazo = fechaFinPlazo;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(final boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    /**
     * Método de acceso a entorno.
     * 
     * @return entorno
     */
    public TypeEntorno getEntorno() {
        return entorno;
    }

    /**
     * Método para establecer entorno.
     * 
     * @param entorno
     *            entorno a establecer
     */
    public void setEntorno(TypeEntorno entorno) {
        this.entorno = entorno;
    }

}
