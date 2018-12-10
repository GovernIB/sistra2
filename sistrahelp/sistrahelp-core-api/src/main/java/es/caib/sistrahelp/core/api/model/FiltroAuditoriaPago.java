package es.caib.sistrahelp.core.api.model;

import java.util.Date;
import java.util.List;

import es.caib.sistrahelp.core.api.model.types.TypeAutenticacion;

/**
 * Filtros para la auditoria de tramites
 *
 * @author Indra
 *
 */
public class FiltroAuditoriaPago extends ModelApi {
	private static final long serialVersionUID = 1L;
	private List<String> listaAreas;
	private TypeAutenticacion tipoAcceso;
	private String idSesionTramitacion;
	private String nif;
	private Date fechaDesde;
	private Date fechaHasta;

	private boolean soloContar;

	public FiltroAuditoriaPago() {
		super();
	}

	public FiltroAuditoriaPago(final List<String> listaAreas) {
		super();
		this.listaAreas = listaAreas;
	}

	public FiltroAuditoriaPago(final FiltroAuditoriaPago pFiltroAuditoriaPago) {
		super();
		if (pFiltroAuditoriaPago != null) {
			this.listaAreas = pFiltroAuditoriaPago.listaAreas;
			this.tipoAcceso = pFiltroAuditoriaPago.tipoAcceso;
			this.idSesionTramitacion = pFiltroAuditoriaPago.idSesionTramitacion;
			this.nif = pFiltroAuditoriaPago.nif;
			this.fechaDesde = pFiltroAuditoriaPago.fechaDesde;
			this.fechaHasta = pFiltroAuditoriaPago.fechaHasta;
			this.soloContar = pFiltroAuditoriaPago.soloContar;
		}
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

	public boolean isSoloContar() {
		return soloContar;
	}

	public void setSoloContar(final boolean soloContar) {
		this.soloContar = soloContar;
	}

	public TypeAutenticacion getTipoAcceso() {
		return tipoAcceso;
	}

	public void setTipoAcceso(final TypeAutenticacion tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

}
