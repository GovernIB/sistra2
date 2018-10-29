package es.caib.sistramit.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistra2.commons.utils.ConstantesNumero;

/**
 * Mapeo tabla STT_PAGEXT.
 */

@Entity
@Table(name = "STT_PAGEXT")
@SuppressWarnings("serial")
public final class HPagoExterno implements IModelApi {

    /** Atributo codigo. */
    @Id
    @SequenceGenerator(name = "STT_PAGEXT_SEQ", sequenceName = "STT_PAGEXT_SEQ", allocationSize = ConstantesNumero.N1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_PAGEXT_SEQ")
    @Column(name = "PAE_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N10, scale = 0)
    private Long codigo;

    /** Atributo ticket. */
    @Column(name = "PAE_TICKET")
    private String ticket;

    /** Atributo fecha inicio. */
    @Column(name = "PAE_FECINI")
    private Date fechaInicio;

    /** Atributo id sesion tramitacion. */
    @Column(name = "PAE_IDESTR")
    private String idSesionTramitacion;

    /** Atributo id paso. */
    @Column(name = "PAE_IDPASO")
    private String idPaso;

    /** Atributo id formulario. */
    @Column(name = "PAE_IDPAGO")
    private String idPago;

    /** Informacion de autenticacion serializada (para vuelta de pagos). */
    @Lob
    @Column(name = "PAE_INFAUT")
    private byte[] infoAutenticacion;

    /** Atributo fecha fin. */
    @Column(name = "PAE_FECFIN")
    private Date fechaFin;

    /** Atributo ticket usado. */
    @Column(name = "PAE_TCKUSA")
    private boolean usadoRetorno;

    /**
     * Método para obtener el campo codigo.
     *
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Método para settear el campo codigo.
     *
     * @param pCodigo
     *            el campo codigo a settear
     */
    public void setCodigo(final Long pCodigo) {
        codigo = pCodigo;
    }

    /**
     * Método para obtener el campo ticket.
     *
     * @return the ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Método para settear el campo ticket.
     *
     * @param pTicket
     *            el campo ticket a settear
     */
    public void setTicket(final String pTicket) {
        ticket = pTicket;
    }

    /**
     * Método para obtener el campo fechaInicio.
     *
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método para settear el campo fechaInicio.
     *
     * @param pFechaInicio
     *            el campo fechaInicio a settear
     */
    public void setFechaInicio(final Date pFechaInicio) {
        fechaInicio = pFechaInicio;
    }

    /**
     * Método para obtener el campo idSesionTramitacion.
     *
     * @return the idSesionTramitacion
     */
    public String getIdSesionTramitacion() {
        return idSesionTramitacion;
    }

    /**
     * Método para settear el campo idSesionTramitacion.
     *
     * @param pIdSesionTramitacion
     *            el campo idSesionTramitacion a settear
     */
    public void setIdSesionTramitacion(final String pIdSesionTramitacion) {
        idSesionTramitacion = pIdSesionTramitacion;
    }

    /**
     * Método para obtener el campo idPaso.
     *
     * @return the idPaso
     */
    public String getIdPaso() {
        return idPaso;
    }

    /**
     * Método para settear el campo idPaso.
     *
     * @param pIdPaso
     *            el campo idPaso a settear
     */
    public void setIdPaso(final String pIdPaso) {
        idPaso = pIdPaso;
    }

    /**
     * Método para obtener el campo idPago.
     *
     * @return the idPago
     */
    public String getIdPago() {
        return idPago;
    }

    /**
     * Método para settear el campo idPago.
     *
     * @param pIdPago
     *            el campo idPago a settear
     */
    public void setIdPago(final String pIdPago) {
        idPago = pIdPago;
    }

    /**
     * Método para obtener el campo fechaFin.
     *
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * Método para settear el campo fechaFin.
     *
     * @param pFechaFin
     *            el campo fechaFin a settear
     */
    public void setFechaFin(final Date pFechaFin) {
        fechaFin = pFechaFin;
    }

    /**
     * Método de acceso a infoAutenticacion.
     *
     * @return infoAutenticacion
     */
    public byte[] getInfoAutenticacion() {
        return infoAutenticacion;
    }

    /**
     * Método para establecer infoAutenticacion.
     *
     * @param pInfoAutenticacion
     *            infoAutenticacion a establecer
     */
    public void setInfoAutenticacion(final byte[] pInfoAutenticacion) {
        infoAutenticacion = pInfoAutenticacion;
    }

    /**
     * Método de acceso a usadoRetorno.
     *
     * @return usadoRetorno
     */
    public boolean isUsadoRetorno() {
        return usadoRetorno;
    }

    /**
     * Método para establecer usadoRetorno.
     *
     * @param pUsadoRetorno
     *            usadoRetorno a establecer
     */
    public void setUsadoRetorno(final boolean pUsadoRetorno) {
        usadoRetorno = pUsadoRetorno;
    }

}
