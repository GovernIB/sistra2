package es.caib.sistramit.core.service.repository.model;

import es.caib.sistra2.commons.utils.ConstantesNumero;

import javax.persistence.*;
import java.util.Date;

/**
 * Mapeo tabla STT_TRAFIN.
 */
@Entity
@Table(name = "STT_TRAETG")
@SuppressWarnings("serial")
public final class HTramiteEntrega implements IModelApi {

    /**
     * Atributo codigo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_TRAETG_SEQ")
    @SequenceGenerator(name = "STT_TRAETG_SEQ", sequenceName = "STT_TRAETG_SEQ", allocationSize = ConstantesNumero.N1)
    @Column(name = "ETG_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N10, scale = 0)
    private Long codigo;

    /**
     * Atributo id sesion tramitacion.
     */
    @Column(name = "ETG_IDESTR")
    private String idSesionTramitacion;

    /**
     * Atributo fecha fin trámite.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ETG_FCFITR")
    private Date fechaFinalizacion;

    /**
     * Atributo id entidad.
     */
    @Column(name = "ETG_ENTIDAD")
    private String idEntidad;

    /**
     * Atributo asiento.
     */
    @Lob
    @Column(name = "ETG_ASIENT")
    private String asiento;

    /**
     * Atributo estado: X: no entregar / N: pendiente entregar / E: error entrega / S: entregado.
     */
    @Column(name = "ETG_ESTADO")
    private String estado;

    /**
     * Atributo  inmediato: si debe realizarse entrega inmediata.
     */
    @Column(name = "ETG_INMEDI")
    private boolean inmediato;

    /**
     * Atributo id sesión envío.
     */
    @Column(name = "ETG_IDENV")
    private String idSesionEnvio;

    /**
     * Atributo fecha entrega.
     */
    @Column(name = "ETG_FCETG")
    private Date fechaEntrega;

    /**
     * Atributo descripción error.
     */
    @Column(name = "ETG_ERRMSG")
    private String mensajeError;

    /**
     * Atributo fecha bloqueo.
     */
    @Column(name = "ETG_FCBLOQ")
    private Date fechaBloqueo;


    /**
     * Devuelve el código.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece el código.
     *
     * @param codigo el nuevo código
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve el idSesionTramitacion.
     *
     * @return idSesionTramitacion
     */
    public String getIdSesionTramitacion() {
        return idSesionTramitacion;
    }

    /**
     * Establece el idSesionTramitacion.
     *
     * @param idSesionTramitacion el nuevo idSesionTramitacion
     */
    public void setIdSesionTramitacion(String idSesionTramitacion) {
        this.idSesionTramitacion = idSesionTramitacion;
    }

    /**
     * Devuelve la fechaFinalizacion.
     *
     * @return fechaFinalizacion
     */
    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    /**
     * Establece la fechaFinalizacion.
     *
     * @param fechaFinalizacion la nueva fechaFinalizacion
     */
    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    /**
     * Devuelve el idEntidad.
     *
     * @return idEntidad
     */
    public String getIdEntidad() {
        return idEntidad;
    }

    /**
     * Establece el idEntidad.
     *
     * @param idEntidad el nuevo idEntidad
     */
    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }

    /**
     * Devuelve el asiento.
     *
     * @return asiento
     */
    public String getAsiento() {
        return asiento;
    }

    /**
     * Establece el asiento.
     *
     * @param asiento el nuevo asiento
     */
    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    /**
     * Devuelve el estado.
     *
     * @return estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado.
     *
     * @param estado el nuevo estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Devuelve el estado inmediato.
     *
     * @return inmediato
     */
    public boolean isInmediato() {
        return inmediato;
    }

    /**
     * Establece el estado inmediato.
     *
     * @param inmediato el nuevo estado inmediato
     */
    public void setInmediato(boolean inmediato) {
        this.inmediato = inmediato;
    }

    /**
     * Devuelve el idSesionEnvio.
     *
     * @return idSesionEnvio
     */
    public String getIdSesionEnvio() {
        return idSesionEnvio;
    }

    /**
     * Establece el idSesionEnvio.
     *
     * @param idSesionEnvio el nuevo idSesionEnvio
     */
    public void setIdSesionEnvio(String idSesionEnvio) {
        this.idSesionEnvio = idSesionEnvio;
    }

    /**
     * Devuelve la fechaEntrega.
     *
     * @return fechaEntrega
     */
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * Establece la fechaEntrega.
     *
     * @param fechaEntrega la nueva fechaEntrega
     */
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * Devuelve el mensajeError.
     *
     * @return mensajeError
     */
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * Establece el mensajeError.
     *
     * @param mensajeError el nuevo mensajeError
     */
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    /**
     * Devuelve la fechaBloqueo.
     *
     * @return fechaBloqueo
     */
    public Date getFechaBloqueo() {
        return fechaBloqueo;
    }

    /**
     * Establece la fechaBloqueo.
     *
     * @param fechaBloqueo la nueva fechaBloqueo
     */
    public void setFechaBloqueo(Date fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }
}
