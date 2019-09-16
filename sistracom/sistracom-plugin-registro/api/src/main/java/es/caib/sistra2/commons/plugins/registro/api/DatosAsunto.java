package es.caib.sistra2.commons.plugins.registro.api;

import java.io.Serializable;
import java.util.Date;

/**
 * Datos del asunto del asiento
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DatosAsunto implements Serializable {

	private Date fechaAsunto;
	private String idiomaAsunto;
	private String extractoAsunto;
	private String codigoOrganoDestino;
	private String codigoSiaProcedimiento;
	private String numeroExpediente;
	private String textoExpone;
	private String textoSolicita;

	public Date getFechaAsunto() {
		return fechaAsunto;
	}

	public void setFechaAsunto(final Date fechaAsunto) {
		this.fechaAsunto = fechaAsunto;
	}

	public String getIdiomaAsunto() {
		return idiomaAsunto;
	}

	public void setIdiomaAsunto(final String idiomaAsunto) {
		this.idiomaAsunto = idiomaAsunto;
	}

	public String getExtractoAsunto() {
		return extractoAsunto;
	}

	public void setExtractoAsunto(final String extractoAsunto) {
		this.extractoAsunto = extractoAsunto;
	}

	public String getCodigoOrganoDestino() {
		return codigoOrganoDestino;
	}

	public void setCodigoOrganoDestino(final String codigoOrganoDestino) {
		this.codigoOrganoDestino = codigoOrganoDestino;
	}

	/**
	 * Método de acceso a codigoSiaProcedimiento.
	 *
	 * @return codigoSiaProcedimiento
	 */
	public String getCodigoSiaProcedimiento() {
		return codigoSiaProcedimiento;
	}

	/**
	 * Método para establecer codigoSiaProcedimiento.
	 *
	 * @param codigoSiaProcedimiento
	 *                                   codigoSiaProcedimiento a establecer
	 */
	public void setCodigoSiaProcedimiento(final String codigoSiaProcedimiento) {
		this.codigoSiaProcedimiento = codigoSiaProcedimiento;
	}

	/**
	 * Método de acceso a numeroExpediente.
	 *
	 * @return numeroExpediente
	 */
	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	/**
	 * Método para establecer numeroExpediente.
	 *
	 * @param numeroExpediente
	 *                             numeroExpediente a establecer
	 */
	public void setNumeroExpediente(final String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	/**
	 * Método de acceso a textoExpone.
	 *
	 * @return textoExpone
	 */
	public String getTextoExpone() {
		return textoExpone;
	}

	/**
	 * Método para establecer textoExpone.
	 *
	 * @param textoExpone
	 *                        textoExpone a establecer
	 */
	public void setTextoExpone(final String textoExpone) {
		this.textoExpone = textoExpone;
	}

	/**
	 * Método de acceso a textoSolicita.
	 *
	 * @return textoSolicita
	 */
	public String getTextoSolicita() {
		return textoSolicita;
	}

	/**
	 * Método para establecer textoSolicita.
	 *
	 * @param textoSolicita
	 *                          textoSolicita a establecer
	 */
	public void setTextoSolicita(final String textoSolicita) {
		this.textoSolicita = textoSolicita;
	}

}
