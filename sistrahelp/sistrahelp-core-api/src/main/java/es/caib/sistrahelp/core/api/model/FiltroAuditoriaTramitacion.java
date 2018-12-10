package es.caib.sistrahelp.core.api.model;

import java.util.Date;
import java.util.List;

import es.caib.sistrahelp.core.api.model.types.TypeEvento;

/**
 * Filtros para la auditoria de tramites
 *
 * @author Indra
 *
 */
public class FiltroAuditoriaTramitacion extends ModelApi {
	private static final long serialVersionUID = 1L;
	private List<String> listaAreas;
	private String idSesionTramitacion;
	private String nif;
	private Date fechaDesde;
	private Date fechaHasta;
	private TypeEvento evento;

	private String idTramite;
	private Integer versionTramite;
	private String idProcedimientoCP;
	private String idProcedimientoSIA;

	private boolean errorPlataforma;
	private boolean soloContar;

	public FiltroAuditoriaTramitacion() {
		super();
	}

	public FiltroAuditoriaTramitacion(final List<String> listaAreas) {
		super();
		this.listaAreas = listaAreas;
	}

	public FiltroAuditoriaTramitacion(final List<String> listaAreas, final boolean errorPlataforma) {
		super();
		this.listaAreas = listaAreas;
		this.errorPlataforma = errorPlataforma;
	}

	public FiltroAuditoriaTramitacion(final FiltroAuditoriaTramitacion pFiltroAuditoriaTramitacion) {
		super();
		if (pFiltroAuditoriaTramitacion != null) {
			this.listaAreas = pFiltroAuditoriaTramitacion.listaAreas;
			this.idSesionTramitacion = pFiltroAuditoriaTramitacion.idSesionTramitacion;
			this.nif = pFiltroAuditoriaTramitacion.nif;
			this.fechaDesde = pFiltroAuditoriaTramitacion.fechaDesde;
			this.fechaHasta = pFiltroAuditoriaTramitacion.fechaHasta;
			this.evento = pFiltroAuditoriaTramitacion.evento;
			this.idTramite = pFiltroAuditoriaTramitacion.idTramite;
			this.versionTramite = pFiltroAuditoriaTramitacion.versionTramite;
			this.idProcedimientoCP = pFiltroAuditoriaTramitacion.idProcedimientoCP;
			this.idProcedimientoSIA = pFiltroAuditoriaTramitacion.idProcedimientoSIA;
			this.errorPlataforma = pFiltroAuditoriaTramitacion.errorPlataforma;
			this.soloContar = pFiltroAuditoriaTramitacion.soloContar;
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

	public TypeEvento getEvento() {
		return evento;
	}

	public void setEvento(final TypeEvento evento) {
		this.evento = evento;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	public Integer getVersionTramite() {
		return versionTramite;
	}

	public void setVersionTramite(final Integer versionTramite) {
		this.versionTramite = versionTramite;
	}

	public String getIdProcedimientoCP() {
		return idProcedimientoCP;
	}

	public void setIdProcedimientoCP(final String idProcedimientoCP) {
		this.idProcedimientoCP = idProcedimientoCP;
	}

	public String getIdProcedimientoSIA() {
		return idProcedimientoSIA;
	}

	public void setIdProcedimientoSIA(final String idProcedimientoSIA) {
		this.idProcedimientoSIA = idProcedimientoSIA;
	}

	public boolean isErrorPlataforma() {
		return errorPlataforma;
	}

	public void setErrorPlataforma(final boolean errorPlataforma) {
		this.errorPlataforma = errorPlataforma;
	}

	public boolean isSoloContar() {
		return soloContar;
	}

	public void setSoloContar(final boolean soloContar) {
		this.soloContar = soloContar;
	}

}
