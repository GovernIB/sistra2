package es.caib.sistramit.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;

/**
 * Mapeo tabla STT_LOGINT.
 */
@Entity
@Table(name = "STT_LOGINT")
@SuppressWarnings("serial")
public class HEventoAuditoria implements IModelApi {

    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_LOGINT_SEQ")
    @SequenceGenerator(name = "STT_LOGINT_SEQ", sequenceName = "STT_LOGINT_SEQ", allocationSize = ConstantesNumero.N1)
    @Column(name = "LOG_CODIGO")
    private Long id;

    /**
     * tipo.
     */
    @Column(name = "LOG_EVETIP", nullable = false)
    private String tipo;

    /**
     * fecha.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOG_EVEFEC", nullable = false)
    private Date fecha;

    /**
     * sesion tramitacion.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOG_CODSES")
    private HSesionTramitacion sesionTramitacion;

    /**
     * descripcion.
     */
    @Column(name = "LOG_EVEDES")
    private String descripcion;

    /**
     * resultado.
     */
    @Column(name = "LOG_EVERES")
    private String resultado;

    /**
     * detalle.
     */
    @Column(name = "LOG_EVEDET")
    private String detalle;

    /**
     * codigoError.
     */
    @Column(name = "LOG_ERRCOD")
    private String codigoError;

    /**
     * traza.
     */
    @Lob
    @Column(name = "LOG_ERRDET")
    private String trazaError;

    /**
     * Instancia un nuevo h log interno de HLogInterno.
     */
    public HEventoAuditoria() {
    }

    /**
     * Para obtener el atributo id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Para establecer el atributo id.
     *
     * @param pId
     *            el nuevo valor para id
     */
    public void setId(final Long pId) {
        this.id = pId;
    }

    /**
     * Para obtener el atributo fecha.
     *
     * @return fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Para establecer el atributo fecha.
     *
     * @param pFecha
     *            el nuevo valor para fecha
     */
    public void setFecha(final Date pFecha) {
        this.fecha = pFecha;
    }

    /**
     * Para obtener el atributo descripcion.
     *
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Para establecer el atributo descripcion.
     *
     * @param pDescripcion
     *            el nuevo valor para descripcion
     */
    public void setDescripcion(final String pDescripcion) {
        this.descripcion = pDescripcion;
    }

    /**
     * Para obtener el atributo resultado.
     *
     * @return resultado
     */
    public String getResultado() {
        return resultado;
    }

    /**
     * Para establecer el atributo resultado.
     *
     * @param pResultado
     *            el nuevo valor para resultado
     */
    public void setResultado(final String pResultado) {
        this.resultado = pResultado;
    }

    /**
     * Para obtener el atributo detalle.
     *
     * @return detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * Para establecer el atributo detalle.
     *
     * @param pDetalle
     *            el nuevo valor para detalle
     */
    public void setDetalle(final String pDetalle) {
        this.detalle = pDetalle;
    }

    /**
     * Para obtener el atributo codigoError.
     *
     * @return codigoError
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * Para establecer el atributo codigoError.
     *
     * @param pCodigoError
     *            el nuevo valor para codigo error
     */
    public void setCodigoError(final String pCodigoError) {
        this.codigoError = pCodigoError;
    }

    /**
     * Para obtener el atributo traza.
     *
     * @return traza
     */
    public String getTrazaError() {
        return trazaError;
    }

    /**
     * Para establecer el atributo traza.
     *
     * @param pTraza
     *            el nuevo valor para traza
     */
    public void setTrazaError(final String pTraza) {
        this.trazaError = pTraza;
    }

    public HSesionTramitacion getSesionTramitacion() {
        return sesionTramitacion;
    }

    public void setSesionTramitacion(HSesionTramitacion sesionTramitacion) {
        this.sesionTramitacion = sesionTramitacion;
    }

    public static HEventoAuditoria fromModel(EventoAuditoria m) {

        // Pasamos propiedades a json
        String detalle = null;
        try {
            detalle = JSONUtil.toJSON(m.getPropiedadesEvento());
        } catch (final JSONUtilException e) {
            // No auditamos detalles
        }

        final HEventoAuditoria h = new HEventoAuditoria();
        h.setFecha(new Date()); // Establecemos fecha actual
        h.setTipo(m.getTipoEvento().toString());
        h.setDescripcion(StringUtils.truncate(m.getDescripcion(),
                ConstantesNumero.N1000));
        h.setResultado(m.getResultado());
        h.setDetalle(detalle); // Serializamos propiedades
        h.setCodigoError(m.getCodigoError());
        h.setTrazaError(m.getTrazaError());

        return h;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
