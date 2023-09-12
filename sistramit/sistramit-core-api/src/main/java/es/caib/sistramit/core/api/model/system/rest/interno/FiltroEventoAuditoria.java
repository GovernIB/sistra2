package es.caib.sistramit.core.api.model.system.rest.interno;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.caib.sistramit.core.api.model.system.types.TypeEvento;

/**
 * Filtros para la auditoria de tramites (RestApiInternaService)
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FiltroEventoAuditoria implements Serializable {

	private List<String> listaAreas;
	private String idSesionTramitacion;
	private String nif;
	private String nombre;
	private Date fechaDesde;
	private Date fechaHasta;
	private TypeEvento evento;
	private String excepcion;
	private String codSia;
	private String estado;
	private String coment;

	private String idTramite;
	private Integer versionTramite;
	private String idProcedimientoCP;
	private String idProcedimientoSIA;

	private boolean errorPlataforma;
	private boolean soloContar;

	private String tlf;
	private String email;

	private String sortField;
	private String sortOrder;

	private String clasificacionSeleccionada;
	private String errorTipo;

	public FiltroEventoAuditoria() {
		super();
	}

	public String getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(String excepcion) {
		this.excepcion = excepcion;
	}

	public FiltroEventoAuditoria(final List<String> listaAreas) {
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
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

	public String getSortField() {
		return sortField;
	}

	public void setSortField(final String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(final String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public final String getCodSia() {
		return codSia;
	}

	public final void setCodSia(String codSia) {
		this.codSia = codSia;
	}

	public String getTlf() {
		return tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getComent() {
		return coment;
	}

	public void setComent(String coment) {
		this.coment = coment;
	}

	public String getClasificacionSeleccionada() {
		return clasificacionSeleccionada;
	}

	public void setClasificacionSeleccionada(String clasificacionSeleccionada) {
		this.clasificacionSeleccionada = clasificacionSeleccionada;
	}

	public String getErrorTipo() {
		return errorTipo;
	}

	public void setErrorTipo(String errorTipo) {
		this.errorTipo = errorTipo;
	}

}
