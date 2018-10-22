package es.caib.sistramit.core.service.repository.model;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistramit.core.api.exception.ErrorJsonException;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;

/**
 * Mapeo tabla STT_TRAPER.
 */
@Entity
@Table(name = "STT_TRAPER")
@SuppressWarnings("serial")
public final class HTramite implements IModelApi {

    /** Atributo codigo. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_TRAPER_SEQ")
    @SequenceGenerator(name = "STT_TRAPER_SEQ", sequenceName = "STT_TRAPER_SEQ", allocationSize = ConstantesNumero.N1)
    @Column(name = "TRP_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N10, scale = 0)
    private Long codigo;

    /**
     * Sesion tramitacion.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TRP_CODSTR", nullable = false)
    private HSesionTramitacion sesionTramitacion;

    /** Atributo id tramite. */
    @Column(name = "TRP_IDETRA")
    private String idTramite;

    /** Atributo version tramite. */
    @Column(name = "TRP_VERTRA")
    private int versionTramite;

    /** Atributo trámite Id Area. */
    @Column(name = "TRP_IDEARE")
    private String idArea;

    /** Atributo trámite Catálogo Procedimientos. */
    @Column(name = "TRP_IDETCP")
    private String idTramiteCP;

    /** Atributo procedimiento Catálogo Procedimientos. */
    @Column(name = "TRP_IDEPCP")
    private String idProcedimientoCP;

    /** Atributo procedimiento SIA. */
    @Column(name = "TRP_PROSIA")
    private String idProcedimientoSIA;

    /** Atributo descripcion tramite. */
    @Column(name = "TRP_DESTRA")
    private String descripcionTramite;

    /** Atributo estado. */
    @Column(name = "TRP_ESTADO")
    private String estado;

    /** Atributo autenticacion. */
    @Column(name = "TRP_NIVAUT")
    private String autenticacion;

    /** Atributo metodo autenticacion inicio tramite. */
    @Column(name = "TRP_METAUT")
    private String metodoAutenticacion;

    /** Atributo nif iniciador. */
    @Column(name = "TRP_NIFINI")
    private String nifIniciador;

    /** Atributo nombre iniciador. */
    @Column(name = "TRP_NOMINI")
    private String nombreIniciador;

    /** Atributo apellido1 iniciador. */
    @Column(name = "TRP_APE1INI")
    private String apellido1Iniciador;

    /** Atributo apellido2 iniciador. */
    @Column(name = "TRP_APE2INI")
    private String apellido2Iniciador;

    /** Atributo timestamp. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRP_TSFLUJO")
    private Date timestamp;

    /** Atributo idioma. */
    @Column(name = "TRP_IDIOMA")
    private String idioma;

    /** Atributo parametros iniciales. */
    @Column(name = "TRP_PARINI")
    private String parametrosInicio;

    /** Atributo persistente. */
    @Column(name = "TRP_PERSIS")
    private boolean persistente;

    /** Atributo plazo dinamico. */
    @Column(name = "TRP_PLZDIN")
    private boolean plazoDinamico;

    /** Atributo fecha inicio. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRP_FECINI")
    private Date fechaInicio;

    /** Atributo fecha ultimo acceso. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRP_FECACC")
    private Date fechaUltimoAcceso;

    /** Atributo fecha caducidad. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRP_FECCAD")
    private Date fechaCaducidad;

    /** Atributo fecha fin. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRP_FECFIN")
    private Date fechaFin;

    /** Atributo cancelado. */
    @Column(name = "TRP_BORRAD")
    private boolean cancelado;

    /** Atributo nif presentador. */
    @Column(name = "TRP_NIFFIN")
    private String nifPresentador;

    /** Atributo nombre presentador. */
    @Column(name = "TRP_NOMFIN")
    private String nombrePresentador;

    /** Indica si esta purgado. */
    @Column(name = "TRP_PURGA")
    private boolean purgado;

    /** Atributo fecha purgado. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRP_FCPURG")
    private Date fechaPurgado;

    /** Atributo marcado para purgar. */
    @Column(name = "TRP_PURCHK")
    private boolean purgar;

    /** Indica si no se ha podido purgar por tener pagos realizados. */
    @Column(name = "TRP_PURPAG")
    private boolean purgaPendientePorPagoRealizado;

    /** Atributo url inicio tramite. */
    @Column(name = "TRP_URLINI")
    private String urlInicio;

    /**
     * Obtiene el atributo codigo de HTramite.
     *
     * @return el atributo codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Asigna el atributo codigo de HTramite.
     *
     * @param pCodigo
     *            el nuevo valor para codigo
     */
    public void setCodigo(final Long pCodigo) {
        codigo = pCodigo;
    }

    /**
     * Obtiene el atributo id tramite de HTramite.
     *
     * @return el atributo id tramite
     */
    public String getIdTramite() {
        return idTramite;
    }

    /**
     * Asigna el atributo id tramite de HTramite.
     *
     * @param pIdTramite
     *            el nuevo valor para id tramite
     */
    public void setIdTramite(final String pIdTramite) {
        idTramite = pIdTramite;
    }

    /**
     * Obtiene el atributo version tramite de HTramite.
     *
     * @return el atributo version tramite
     */
    public int getVersionTramite() {
        return versionTramite;
    }

    /**
     * Asigna el atributo version tramite de HTramite.
     *
     * @param pVersionTramite
     *            el nuevo valor para version tramite
     */
    public void setVersionTramite(final int pVersionTramite) {
        versionTramite = pVersionTramite;
    }

    /**
     * Obtiene el atributo descripcion tramite de HTramite.
     *
     * @return el atributo descripcion tramite
     */
    public String getDescripcionTramite() {
        return descripcionTramite;
    }

    /**
     * Asigna el atributo descripcion tramite de HTramite.
     *
     * @param pDescripcionTramite
     *            el nuevo valor para descripcion tramite
     */
    public void setDescripcionTramite(final String pDescripcionTramite) {
        descripcionTramite = pDescripcionTramite;
    }

    /**
     * Obtiene el atributo estado de HTramite.
     *
     * @return el atributo estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Asigna el atributo estado de HTramite.
     *
     * @param pEstado
     *            el nuevo valor para estado
     */
    public void setEstado(final String pEstado) {
        estado = pEstado;
    }

    /**
     * Método para obtener el campo autenticacion.
     *
     * @return the autenticacion
     */
    public String getAutenticacion() {
        return autenticacion;
    }

    /**
     * Método para settear el campo autenticacion.
     *
     * @param pAutenticacion
     *            el campo autenticacion a settear
     */
    public void setAutenticacion(final String pAutenticacion) {
        autenticacion = pAutenticacion;
    }

    /**
     * Obtiene el atributo nif iniciador de HTramite.
     *
     * @return el atributo nif iniciador
     */
    public String getNifIniciador() {
        return nifIniciador;
    }

    /**
     * Asigna el atributo nif iniciador de HTramite.
     *
     * @param pNifIniciador
     *            el nuevo valor para nif iniciador
     */
    public void setNifIniciador(final String pNifIniciador) {
        nifIniciador = pNifIniciador;
    }

    /**
     * Obtiene el atributo nombre iniciador de HTramite.
     *
     * @return el atributo nombre iniciador
     */
    public String getNombreIniciador() {
        return nombreIniciador;
    }

    /**
     * Asigna el atributo nombre iniciador de HTramite.
     *
     * @param pNombreIniciador
     *            el nuevo valor para nombre iniciador
     */
    public void setNombreIniciador(final String pNombreIniciador) {
        nombreIniciador = pNombreIniciador;
    }

    /**
     * Obtiene el atributo timestamp de HTramite.
     *
     * @return el atributo timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Asigna el atributo timestamp de HTramite.
     *
     * @param pTimestamp
     *            el nuevo valor para timestamp
     */
    public void setTimestamp(final Date pTimestamp) {
        timestamp = pTimestamp;
    }

    /**
     * Obtiene el atributo idioma de HTramite.
     *
     * @return el atributo idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Asigna el atributo idioma de HTramite.
     *
     * @param pIdioma
     *            el nuevo valor para idioma
     */
    public void setIdioma(final String pIdioma) {
        idioma = pIdioma;
    }

    /**
     * Obtiene el atributo parametros iniciales de HTramite.
     *
     * @return el atributo parametros iniciales
     */
    public String getParametrosInicio() {
        return parametrosInicio;
    }

    /**
     * Asigna el atributo parametros iniciales de HTramite.
     *
     * @param pParametrosIniciales
     *            el nuevo valor para parametros iniciales
     */
    public void setParametrosInicio(final String pParametrosIniciales) {
        parametrosInicio = pParametrosIniciales;
    }

    /**
     * Comprueba si es true persistente de HTramite.
     *
     * @return true, si es persistente
     */
    public boolean isPersistente() {
        return persistente;
    }

    /**
     * Asigna el atributo persistente de HTramite.
     *
     * @param pPersistente
     *            el nuevo valor para persistente
     */
    public void setPersistente(final boolean pPersistente) {
        persistente = pPersistente;
    }

    /**
     * Obtiene el atributo fecha inicio de HTramite.
     *
     * @return el atributo fecha inicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Asigna el atributo fecha inicio de HTramite.
     *
     * @param pFechaInicio
     *            el nuevo valor para fecha inicio
     */
    public void setFechaInicio(final Date pFechaInicio) {
        fechaInicio = pFechaInicio;
    }

    /**
     * Obtiene el atributo fecha ultimo acceso de HTramite.
     *
     * @return el atributo fecha ultimo acceso
     */
    public Date getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    /**
     * Asigna el atributo fecha ultimo acceso de HTramite.
     *
     * @param pFechaUltimoAcceso
     *            el nuevo valor para fecha ultimo acceso
     */
    public void setFechaUltimoAcceso(final Date pFechaUltimoAcceso) {
        fechaUltimoAcceso = pFechaUltimoAcceso;
    }

    /**
     * Obtiene el atributo fecha caducidad de HTramite.
     *
     * @return el atributo fecha caducidad
     */
    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * Asigna el atributo fecha caducidad de HTramite.
     *
     * @param pFechaCaducidad
     *            el nuevo valor para fecha caducidad
     */
    public void setFechaCaducidad(final Date pFechaCaducidad) {
        fechaCaducidad = pFechaCaducidad;
    }

    /**
     * Obtiene el atributo fecha fin de HTramite.
     *
     * @return el atributo fecha fin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * Asigna el atributo fecha fin de HTramite.
     *
     * @param pFechaFin
     *            el nuevo valor para fecha fin
     */
    public void setFechaFin(final Date pFechaFin) {
        fechaFin = pFechaFin;
    }

    /**
     * Comprueba si es true cancelado de HTramite.
     *
     * @return true, si es cancelado
     */
    public boolean isCancelado() {
        return cancelado;
    }

    /**
     * Asigna el atributo cancelado de HTramite.
     *
     * @param pCancelado
     *            el nuevo valor para cancelado
     */
    public void setCancelado(final boolean pCancelado) {
        cancelado = pCancelado;
    }

    /**
     * Obtiene el atributo nif presentador de HTramite.
     *
     * @return el atributo nif presentador
     */
    public String getNifPresentador() {
        return nifPresentador;
    }

    /**
     * Asigna el atributo nif presentador de HTramite.
     *
     * @param pNifPresentador
     *            el nuevo valor para nif presentador
     */
    public void setNifPresentador(final String pNifPresentador) {
        nifPresentador = pNifPresentador;
    }

    /**
     * Obtiene el atributo nombre presentador de HTramite.
     *
     * @return el atributo nombre presentador
     */
    public String getNombrePresentador() {
        return nombrePresentador;
    }

    /**
     * Asigna el atributo nombre presentador de HTramite.
     *
     * @param pNombrePresentador
     *            el nuevo valor para nombre presentador
     */
    public void setNombrePresentador(final String pNombrePresentador) {
        nombrePresentador = pNombrePresentador;
    }

    /**
     * Método para obtener el campo apellido1Iniciador.
     *
     * @return the apellido1Iniciador
     */
    public String getApellido1Iniciador() {
        return apellido1Iniciador;
    }

    /**
     * Método para settear el campo apellido1Iniciador.
     *
     * @param pApellido1Iniciador
     *            el campo apellido1Iniciador a settear
     */
    public void setApellido1Iniciador(final String pApellido1Iniciador) {
        apellido1Iniciador = pApellido1Iniciador;
    }

    /**
     * Método para obtener el campo apellido2Iniciador.
     *
     * @return the apellido2Iniciador
     */
    public String getApellido2Iniciador() {
        return apellido2Iniciador;
    }

    /**
     * Método para settear el campo apellido2Iniciador.
     *
     * @param pApellido2Iniciador
     *            el campo apellido2Iniciador a settear
     */
    public void setApellido2Iniciador(final String pApellido2Iniciador) {
        apellido2Iniciador = pApellido2Iniciador;
    }

    /**
     * Método para obtener el campo plazoDinamico.
     *
     * @return the plazoDinamico
     */
    public boolean isPlazoDinamico() {
        return plazoDinamico;
    }

    /**
     * Método para settear el campo plazoDinamico.
     *
     * @param pPlazoDinamico
     *            el campo plazoDinamico a settear
     */
    public void setPlazoDinamico(final boolean pPlazoDinamico) {
        plazoDinamico = pPlazoDinamico;
    }

    /**
     * Método de acceso a purgado.
     *
     * @return purgado
     */
    public boolean isPurgado() {
        return purgado;
    }

    /**
     * Método para establecer purgado.
     *
     * @param pPurgado
     *            purgado a establecer
     */
    public void setPurgado(final boolean pPurgado) {
        purgado = pPurgado;
    }

    /**
     * Método de acceso a fechaPurgado.
     *
     * @return fechaPurgado
     */
    public Date getFechaPurgado() {
        return fechaPurgado;
    }

    /**
     * Método para establecer fechaPurgado.
     *
     * @param pFechaPurgado
     *            fechaPurgado a establecer
     */
    public void setFechaPurgado(final Date pFechaPurgado) {
        fechaPurgado = pFechaPurgado;
    }

    /**
     * Método de acceso a urlInicio.
     *
     * @return urlInicio
     */
    public String getUrlInicio() {
        return urlInicio;
    }

    /**
     * Método para establecer urlInicio.
     *
     * @param pUrlInicio
     *            urlInicio a establecer
     */
    public void setUrlInicio(final String pUrlInicio) {
        urlInicio = pUrlInicio;
    }

    /**
     * Método de acceso a sesionTramitacion.
     *
     * @return sesionTramitacion
     */
    public HSesionTramitacion getSesionTramitacion() {
        return sesionTramitacion;
    }

    /**
     * Método para establecer sesionTramitacion.
     *
     * @param pSesionTramitacion
     *            sesionTramitacion a establecer
     */
    public void setSesionTramitacion(
            final HSesionTramitacion pSesionTramitacion) {
        sesionTramitacion = pSesionTramitacion;
    }

    /**
     * Método de acceso a purgar.
     *
     * @return purgar
     */
    public boolean isPurgar() {
        return purgar;
    }

    /**
     * Método para establecer purgar.
     *
     * @param pPurgar
     *            purgar a establecer
     */
    public void setPurgar(final boolean pPurgar) {
        purgar = pPurgar;
    }

    /**
     * Método de acceso a purgaPendientePorPagoRealizado.
     *
     * @return purgaPendientePorPagoRealizado
     */
    public boolean isPurgaPendientePorPagoRealizado() {
        return purgaPendientePorPagoRealizado;
    }

    /**
     * Método para establecer purgaPendientePorPagoRealizado.
     *
     * @param pPurgaPendientePorPagoRealizado
     *            purgaPendientePorPagoRealizado a establecer
     */
    public void setPurgaPendientePorPagoRealizado(
            final boolean pPurgaPendientePorPagoRealizado) {
        purgaPendientePorPagoRealizado = pPurgaPendientePorPagoRealizado;
    }

    /**
     * Método de acceso a metodoAutenticacion.
     *
     * @return metodoAutenticacion
     */
    public String getMetodoAutenticacion() {
        return metodoAutenticacion;
    }

    /**
     * Método para establecer metodoAutenticacion.
     *
     * @param pMetodoAutenticacion
     *            metodoAutenticacion a establecer
     */
    public void setMetodoAutenticacion(final String pMetodoAutenticacion) {
        metodoAutenticacion = pMetodoAutenticacion;
    }

    public String getIdTramiteCP() {
        return idTramiteCP;
    }

    public void setIdTramiteCP(String idTramiteCP) {
        this.idTramiteCP = idTramiteCP;
    }

    /**
     * Método de acceso a idProcedimientoCP.
     *
     * @return idProcedimientoCP
     */
    public String getIdProcedimientoCP() {
        return idProcedimientoCP;
    }

    /**
     * Método para establecer idProcedimientoCP.
     *
     * @param idProcedimientoCP
     *            idProcedimientoCP a establecer
     */
    public void setIdProcedimientoCP(String idProcedimientoCP) {
        this.idProcedimientoCP = idProcedimientoCP;
    }

    /**
     * Método de acceso a idProcedimientoSIA.
     *
     * @return idProcedimientoSIA
     */
    public String getIdProcedimientoSIA() {
        return idProcedimientoSIA;
    }

    /**
     * Método para establecer idProcedimientoSIA.
     *
     * @param idProcedimientoSIA
     *            idProcedimientoSIA a establecer
     */
    public void setIdProcedimientoSIA(String idProcedimientoSIA) {
        this.idProcedimientoSIA = idProcedimientoSIA;
    }

    /**
     * Convierte objeto de la capa negocio a la capa de repositorio.
     *
     * @param m
     *            objeto modelo
     * @return objeto repositorio
     */
    public static HTramite fromModel(DatosPersistenciaTramite m) {
        final HTramite hTramite = new HTramite();
        hTramite.setAutenticacion(m.getAutenticacion().toString());
        hTramite.setMetodoAutenticacion(
                m.getMetodoAutenticacionInicio().toString());
        hTramite.setNifIniciador(m.getNifIniciador());
        hTramite.setNombreIniciador(m.getNombreIniciador());
        hTramite.setApellido1Iniciador(m.getApellido1Iniciador());
        hTramite.setApellido2Iniciador(m.getApellido2Iniciador());

        hTramite.setEstado(m.getEstado().toString());
        hTramite.setFechaCaducidad(m.getFechaCaducidad());
        hTramite.setFechaInicio(m.getFechaInicio());
        hTramite.setFechaFin(m.getFechaFin());
        hTramite.setFechaUltimoAcceso(m.getFechaUltimoAcceso());
        hTramite.setTimestamp(new Date(m.getTimestamp()));
        hTramite.setCancelado(m.isCancelado());

        hTramite.setIdTramite(m.getIdTramite());
        hTramite.setVersionTramite(m.getVersionTramite());
        hTramite.setDescripcionTramite(m.getDescripcionTramite());
        hTramite.setIdArea(m.getIdArea());
        hTramite.setIdTramiteCP(m.getIdTramiteCP());
        hTramite.setIdProcedimientoCP(m.getIdProcedimientoCP());
        hTramite.setIdProcedimientoSIA(m.getIdProcedimientoSIA());

        hTramite.setIdioma(m.getIdioma());
        hTramite.setUrlInicio(m.getUrlInicio());
        try {
            hTramite.setParametrosInicio(
                    JSONUtil.toJSON(m.getParametrosInicio()));
        } catch (final JSONUtilException e) {
            throw new ErrorJsonException(e);
        }
        hTramite.setPersistente(m.isPersistente());
        hTramite.setPlazoDinamico(m.isPlazoDinamico());

        hTramite.setNifPresentador(m.getNifPresentador());
        hTramite.setNombrePresentador(m.getNombreCompletoPresentador());

        hTramite.setPurgar(m.isMarcadoPurgar());
        hTramite.setPurgado(m.isPurgado());
        hTramite.setFechaPurgado(m.getFechaPurgado());
        hTramite.setPurgaPendientePorPagoRealizado(
                m.isPurgaPendientePorPagoRealizado());

        return hTramite;
    }

    /**
     * Convierte a objeto de modelo.
     *
     * @param h
     *            Objeto repositorio
     * @return Objeto modelo
     */
    public static DatosPersistenciaTramite toModel(HTramite h) {
        DatosPersistenciaTramite m = null;
        if (h != null) {
            m = new DatosPersistenciaTramite();

            m.setIdSesionTramitacion(
                    h.getSesionTramitacion().getIdSesionTramitacion());

            m.setAutenticacion(
                    TypeAutenticacion.fromString(h.getAutenticacion()));
            m.setMetodoAutenticacionInicio(TypeMetodoAutenticacion
                    .fromString(h.getMetodoAutenticacion()));
            m.setNifIniciador(h.getNifIniciador());
            m.setNombreIniciador(h.getNombreIniciador());
            m.setApellido1Iniciador(h.getApellido1Iniciador());
            m.setApellido2Iniciador(h.getApellido2Iniciador());

            m.setEstado(TypeEstadoTramite.fromString(h.getEstado()));
            m.setFechaCaducidad(h.getFechaCaducidad());
            m.setFechaInicio(h.getFechaInicio());
            m.setFechaFin(h.getFechaFin());
            m.setFechaUltimoAcceso(h.getFechaUltimoAcceso());
            m.setCancelado(h.isCancelado());
            if (h.getTimestamp() != null) {
                m.setTimestamp(h.getTimestamp().getTime());
            } else {
                m.setTimestamp(null);
            }

            m.setIdTramite(h.getIdTramite());
            m.setVersionTramite(h.getVersionTramite());
            m.setDescripcionTramite(h.getDescripcionTramite());
            m.setIdArea(h.getIdArea());
            m.setIdTramiteCP(h.getIdTramiteCP());
            m.setIdProcedimientoCP(h.getIdProcedimientoCP());
            m.setIdProcedimientoSIA(h.getIdProcedimientoSIA());

            m.setIdioma(h.getIdioma());
            m.setUrlInicio(h.getUrlInicio());
            try {
                m.setParametrosInicio((Map<String, String>) JSONUtil
                        .fromJSON(h.getParametrosInicio(), Map.class));
            } catch (final JSONUtilException e) {
                throw new ErrorJsonException(e);
            }
            m.setPersistente(h.isPersistente());
            m.setPlazoDinamico(h.isPlazoDinamico());

            m.setNifPresentador(h.getNifPresentador());
            m.setNombreCompletoPresentador(h.getNombrePresentador());

            m.setMarcadoPurgar(h.isPurgar());
            m.setPurgado(h.isPurgado());
            m.setFechaPurgado(h.getFechaPurgado());
            m.setPurgaPendientePorPagoRealizado(
                    h.isPurgaPendientePorPagoRealizado());

        }
        return m;
    }

    /**
     * Método de acceso a idArea.
     *
     * @return idArea
     */
    public String getIdArea() {
        return idArea;
    }

    /**
     * Método para establecer idArea.
     *
     * @param idArea
     *            idArea a establecer
     */
    public void setIdArea(String idArea) {
        this.idArea = idArea;
    }

}
