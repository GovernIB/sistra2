package es.caib.sistrahelp.core.api.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Filtros para la perdida de clave
 *
 * @author Indra
 *
 */
public class FiltroPerdidaClave extends ModelApi {

	private static final long serialVersionUID = -257813826202155209L;
	private List<String> listaAreas;
	private String datoFormulario;
	private Date fechaDesde;
	private Date fechaHasta;
	private String idTramite;
	private Integer versionTramite;
	private String idProcedimientoCP;

	public FiltroPerdidaClave() {
		super();
		this.fechaDesde = getToday();
	}

	public FiltroPerdidaClave(final List<String> listaAreas) {
		super();
		this.listaAreas = listaAreas;
		this.fechaDesde = getToday();
	}

	public List<String> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(final List<String> listaAreas) {
		this.listaAreas = listaAreas;
	}

	public String getDatoFormulario() {
		return datoFormulario;
	}

	public void setDatoFormulario(final String datoFormulario) {
		this.datoFormulario = datoFormulario;
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

	private Date getToday() {
		final Calendar calendar = Calendar.getInstance();
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 0, 0, 0);
		return calendar.getTime();
	}

}
