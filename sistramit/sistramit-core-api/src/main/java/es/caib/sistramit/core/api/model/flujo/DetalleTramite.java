package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypeFlujoTramitacion;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

/**
 * Detalle tramite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DetalleTramite implements ModelApi {

    /** Entorno. */
    private TypeEntorno entorno;

    /** Id trámite. */
    private String idSesionTramitacion;

    /** Id trámite. */
    private String idTramite;

    /** Versión trámite. */
    private int version;

    /** Fecha recuperacion definicion STG (dd/MM/yyyy hh:mm). */
    private String fechaDefinicion;

    /** Idioma. */
    private String idioma;

    /** Título trámite. */
    private String titulo;

    /** Tipo flujo tramitación. */
    private TypeFlujoTramitacion tipoFlujo;

    /** Usuario autenticado. */
    private UsuarioAutenticadoInfo usuario;

    /** Indica si es persistente. */
    private TypeSiNo persistente = TypeSiNo.NO;

    /** Dias persistencia. Si 0 persistencia infinita. */
    private int diasPersistencia;

    /** Debug habilitado. */
    private TypeSiNo debug = TypeSiNo.NO;

    /** Info entidad. */
    private Entidad entidad;

    /** Paso actual. */
    private String idPasoActual;

    /**
     * Imprime detalle tramite.
     *
     * @return Detalle tramite
     */
    public String print() {
        final StringBuffer strb = new StringBuffer(
                ConstantesNumero.N1000 * ConstantesNumero.N8);
        strb.append("\nDETALLE TRAMITE\n");
        strb.append("===============\n");
        strb.append("Entorno:" + getEntorno() + "\n");
        strb.append("Usuario:" + getUsuario().print() + "\n");
        strb.append("Idioma:" + getIdioma() + "\n");
        strb.append("Titulo:" + getTitulo() + "\n");
        strb.append("Tipo flujo:" + getTipoFlujo() + "\n");
        strb.append("Id paso actual:\n" + getIdPasoActual() + "\n");
        strb.append("===============\n");
        strb.append("FIN DETALLE TRAMITE\n");
        return strb.toString();
    }

    /**
     * Método de acceso a idTramite.
     *
     * @return idTramite
     */
    public String getIdTramite() {
        return idTramite;
    }

    /**
     * Método para establecer idTramite.
     *
     * @param idTramite
     *            idTramite a establecer
     */
    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
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
     * Método de acceso a fechaDefinicion.
     *
     * @return fechaDefinicion
     */
    public String getFechaDefinicion() {
        return fechaDefinicion;
    }

    /**
     * Método para establecer fechaDefinicion.
     *
     * @param fechaDefinicion
     *            fechaDefinicion a establecer
     */
    public void setFechaDefinicion(String fechaDefinicion) {
        this.fechaDefinicion = fechaDefinicion;
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
     * Método de acceso a titulo.
     *
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Método para establecer titulo.
     *
     * @param titulo
     *            titulo a establecer
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Método de acceso a tipoFlujo.
     *
     * @return tipoFlujo
     */
    public TypeFlujoTramitacion getTipoFlujo() {
        return tipoFlujo;
    }

    /**
     * Método para establecer tipoFlujo.
     *
     * @param tipoFlujo
     *            tipoFlujo a establecer
     */
    public void setTipoFlujo(TypeFlujoTramitacion tipoFlujo) {
        this.tipoFlujo = tipoFlujo;
    }

    /**
     * Método de acceso a usuario.
     *
     * @return usuario
     */
    public UsuarioAutenticadoInfo getUsuario() {
        return usuario;
    }

    /**
     * Método para establecer usuario.
     *
     * @param usuario
     *            usuario a establecer
     */
    public void setUsuario(UsuarioAutenticadoInfo usuario) {
        this.usuario = usuario;
    }

    /**
     * Método de acceso a persistente.
     *
     * @return persistente
     */
    public TypeSiNo getPersistente() {
        return persistente;
    }

    /**
     * Método para establecer persistente.
     *
     * @param persistente
     *            persistente a establecer
     */
    public void setPersistente(TypeSiNo persistente) {
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
     * Método de acceso a debug.
     *
     * @return debug
     */
    public TypeSiNo getDebug() {
        return debug;
    }

    /**
     * Método para establecer debug.
     *
     * @param debug
     *            debug a establecer
     */
    public void setDebug(TypeSiNo debug) {
        this.debug = debug;
    }

    /**
     * Método de acceso a entidad.
     *
     * @return entidad
     */
    public Entidad getEntidad() {
        return entidad;
    }

    /**
     * Método para establecer entidad.
     *
     * @param entidad
     *            entidad a establecer
     */
    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

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
     * Método de acceso a idPasoActual.
     *
     * @return idPasoActual
     */
    public String getIdPasoActual() {
        return idPasoActual;
    }

    /**
     * Método para establecer idPasoActual.
     *
     * @param idPasoActual
     *            idPasoActual a establecer
     */
    public void setIdPasoActual(String idPasoActual) {
        this.idPasoActual = idPasoActual;
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
