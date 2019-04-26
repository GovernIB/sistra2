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
	private String tipoAsunto;
	private String extractoAsunto;
	private String codigoOrganoDestino;
	private String codigoSiaProcedimiento;
	private String numeroExpediente;
	private String textoExpone;
	private String textoSolicita;

	public Date getFechaAsunto() {
		return fechaAsunto;
	}

	public void setFechaAsunto(Date fechaAsunto) {
		this.fechaAsunto = fechaAsunto;
	}

	public String getIdiomaAsunto() {
		return idiomaAsunto;
	}

	public void setIdiomaAsunto(String idiomaAsunto) {
		this.idiomaAsunto = idiomaAsunto;
	}

	public String getTipoAsunto() {
		return tipoAsunto;
	}

	public void setTipoAsunto(String tipoAsunto) {
		this.tipoAsunto = tipoAsunto;
	}

	public String getExtractoAsunto() {
		return extractoAsunto;
	}

	public void setExtractoAsunto(String extractoAsunto) {
		this.extractoAsunto = extractoAsunto;
	}

	public String getCodigoOrganoDestino() {
		return codigoOrganoDestino;
	}

	public void setCodigoOrganoDestino(String codigoOrganoDestino) {
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
	 *            codigoSiaProcedimiento a establecer
	 */
	public void setCodigoSiaProcedimiento(String codigoSiaProcedimiento) {
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
	 *            numeroExpediente a establecer
	 */
	public void setNumeroExpediente(String numeroExpediente) {
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
	 *            textoExpone a establecer
	 */
	public void setTextoExpone(String textoExpone) {
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
	 *            textoSolicita a establecer
	 */
	public void setTextoSolicita(String textoSolicita) {
		this.textoSolicita = textoSolicita;
	}

}
