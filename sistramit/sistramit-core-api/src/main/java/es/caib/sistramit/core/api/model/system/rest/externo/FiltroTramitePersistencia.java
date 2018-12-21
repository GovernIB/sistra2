package es.caib.sistramit.core.api.model.system.rest.externo;

import java.io.Serializable;
import java.util.Date;

/**
 * Filtros para la persistencia (RestApiExternaService)
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FiltroTramitePersistencia implements Serializable {

	private String nif;
	private Date fechaDesde;
	private Date fechaHasta;

	public FiltroTramitePersistencia() {
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

}
