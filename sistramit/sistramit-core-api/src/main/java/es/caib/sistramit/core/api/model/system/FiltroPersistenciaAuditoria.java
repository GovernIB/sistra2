package es.caib.sistramit.core.api.model.system;

import java.util.Date;
import java.util.List;

import es.caib.sistramit.core.api.model.system.types.TypeTramitePersistencia;

/**
 * Filtros para la persistencia
 *
 * @author Indra
 *
 */
public class FiltroPersistenciaAuditoria {

	private List<String> listaAreas;
	private String idSesionTramitacion;
	private String nif;
	private Date fechaDesde;
	private Date fechaHasta;
	private TypeTramitePersistencia tipoTramitePersistencia;

	private String idTramite;
	private Integer versionTramite;
	private String idProcedimientoCP;
	private String idProcedimientoSIA;

	private boolean soloContar;

	public FiltroPersistenciaAuditoria() {
		super();
	}

	public FiltroPersistenciaAuditoria(final List<String> listaAreas) {
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

	public TypeTramitePersistencia getTipoTramitePersistencia() {
		return tipoTramitePersistencia;
	}

	public void setTipoTramitePersistencia(final TypeTramitePersistencia tipoTramitePersistencia) {
		this.tipoTramitePersistencia = tipoTramitePersistencia;
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

	public boolean isSoloContar() {
		return soloContar;
	}

	public void setSoloContar(final boolean soloContar) {
		this.soloContar = soloContar;
	}

}
