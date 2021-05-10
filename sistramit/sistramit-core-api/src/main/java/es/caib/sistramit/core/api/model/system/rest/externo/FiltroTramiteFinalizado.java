package es.caib.sistramit.core.api.model.system.rest.externo;

import java.io.Serializable;
import java.util.Date;

/**
 * Filtros para trámite finalizado (RestApiExternaService)
 *
 * Busqueda por: - nif/fechaDesde/fechaHasta - idSesionTramitacion
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FiltroTramiteFinalizado implements Serializable {

	private String nif;
	private Date fechaDesde;
	private Date fechaHasta;
	private String idSesionTramitacion;

	public FiltroTramiteFinalizado() {
		super();
	}

	public String getNif() {
		return nif;
	}

	public void setNif(final String nif) {
		this.nif = nif;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(final Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(final Date fechaHasta) {
		this.fechaHasta = fechaHasta;
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
	 *                                idSesionTramitacion a establecer
	 */
	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

}
