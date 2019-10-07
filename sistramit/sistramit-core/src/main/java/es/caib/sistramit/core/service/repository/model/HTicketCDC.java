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
 * Mapeo tabla STT_TCKCDC T.
 */

@Entity
@Table(name = "STT_TCKCDC ")
@SuppressWarnings("serial")
public final class HTicketCDC implements IModelApi {

	/** Atributo codigo. */
	@Id
	@SequenceGenerator(name = "STT_TCKCDC_SEQ", sequenceName = "STT_TCKCDC_SEQ", allocationSize = ConstantesNumero.N1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_TCKCDC_SEQ")
	@Column(name = "TCC_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N10, scale = 0)
	private Long codigo;

	/** Atributo ticket. */
	@Column(name = "TCC_TICKET")
	private String ticket;

	/** Atributo fecha inicio. */
	@Column(name = "TCC_FECINI")
	private Date fechaInicio;

	/** Atributo id sesion tramitacion. */
	@Column(name = "TCC_IDESTR")
	private String idSesionTramitacion;

	/** Informacion de autenticacion serializada (para vuelta de login). */
	@Lob
	@Column(name = "TCC_INFAUT")
	private byte[] infoAutenticacion;

	/** Atributo fecha fin. */
	@Column(name = "TCC_FECFIN")
	private Date fechaFin;

	/** Atributo ticket usado. */
	@Column(name = "TCC_TCKUSA")
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
	 *                    el campo codigo a settear
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
	 *                    el campo ticket a settear
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
	 *                         el campo fechaInicio a settear
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
	 *                                 el campo idSesionTramitacion a settear
	 */
	public void setIdSesionTramitacion(final String pIdSesionTramitacion) {
		idSesionTramitacion = pIdSesionTramitacion;
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
	 *                      el campo fechaFin a settear
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
	 *                               infoAutenticacion a establecer
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
	 *                          usadoRetorno a establecer
	 */
	public void setUsadoRetorno(final boolean pUsadoRetorno) {
		usadoRetorno = pUsadoRetorno;
	}

}
