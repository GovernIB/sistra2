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

}
