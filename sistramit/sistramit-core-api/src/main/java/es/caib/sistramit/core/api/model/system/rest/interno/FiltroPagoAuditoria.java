package es.caib.sistramit.core.api.model.system.rest.interno;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;

/**
 * Filtros para la auditoria de pagos (RestApiInternaService)
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FiltroPagoAuditoria implements Serializable {

	private List<String> listaAreas;
	private String idSesionTramitacion;
	private String nif;
	private Date fechaDesde;
	private Date fechaHasta;
	private TypeAutenticacion acceso;

	private boolean soloContar;

	public FiltroPagoAuditoria() {
		super();
	}

	public FiltroPagoAuditoria(final List<String> listaAreas) {
		super();
		this.listaAreas = listaAreas;
	}

	public List<String> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(final List<String> listaAreas) {
		this.listaAreas = listaAreas;
	}

	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
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

	public TypeAutenticacion getAcceso() {
		return acceso;
	}

	public void setAcceso(final TypeAutenticacion acceso) {
		this.acceso = acceso;
	}

	public boolean isSoloContar() {
		return soloContar;
	}

	public void setSoloContar(final boolean soloContar) {
		this.soloContar = soloContar;
	}

}
