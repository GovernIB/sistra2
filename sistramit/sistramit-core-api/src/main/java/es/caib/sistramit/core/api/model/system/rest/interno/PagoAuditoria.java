package es.caib.sistramit.core.api.model.system.rest.interno;

import java.io.Serializable;
import java.util.Date;

/**
 * La clase PagoAuditoria. (RestApiInternaService)
 */
@SuppressWarnings("serial")
public final class PagoAuditoria implements Serializable {
	public PagoAuditoria() {
		super();
	}

	public PagoAuditoria(final String idSesionTramitacion, final Date fecha, final String idTramite,
			final Integer versionTramite, final String idProcedimientoCP, final Long fichero, final String ficheroClave,
			final Long codigoPago, final String estado, final String pagoEstadoIncorrecto) {
		super();
		this.idSesionTramitacion = idSesionTramitacion;
		this.fecha = fecha;
		this.idTramite = idTramite;
		this.versionTramite = versionTramite;
		this.idProcedimientoCP = idProcedimientoCP;
		this.fichero = fichero;
		this.ficheroClave = ficheroClave;
		this.codigoPago = codigoPago;
		this.estado = estado;
		this.pagoEstadoIncorrecto = pagoEstadoIncorrecto;
	}

	/**
	 * clave de tramitacion
	 */
	private String idSesionTramitacion;

	/**
	 * fecha.
	 */
	private Date fecha;

	/**
	 * id tramite.
	 */
	private String idTramite;

	/**
	 * version tramite.
	 */
	private Integer versionTramite;

	/**
	 * id procedimiento CP.
	 */
	private String idProcedimientoCP;

	private Long fichero;

	private String ficheroClave;

	private Long codigoPago;

	private String estado;

	private String identificador;
	private String presentacion;
	private String pasarelaId;
	private int importe;
	private String tasaId;
	private String localizador;
	private Date fechaPago;
	private String pagoEstadoIncorrecto;

	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
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

	public String getFicheroClave() {
		return ficheroClave;
	}

	public void setFicheroClave(final String ficheroClave) {
		this.ficheroClave = ficheroClave;
	}

	public Long getCodigoPago() {
		return codigoPago;
	}

	public void setCodigoPago(final Long codigoPago) {
		this.codigoPago = codigoPago;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(final String estado) {
		this.estado = estado;
	}

	public Long getFichero() {
		return fichero;
	}

	public void setFichero(final Long fichero) {
		this.fichero = fichero;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(final String presentacion) {
		this.presentacion = presentacion;
	}

	public String getPasarelaId() {
		return pasarelaId;
	}

	public void setPasarelaId(final String pasarelaId) {
		this.pasarelaId = pasarelaId;
	}

	public int getImporte() {
		return importe;
	}

	public void setImporte(final int importe) {
		this.importe = importe;
	}

	public String getTasaId() {
		return tasaId;
	}

	public void setTasaId(final String tasaId) {
		this.tasaId = tasaId;
	}

	public String getLocalizador() {
		return localizador;
	}

	public void setLocalizador(final String localizador) {
		this.localizador = localizador;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(final Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getPagoEstadoIncorrecto() {
		return pagoEstadoIncorrecto;
	}

	public void setPagoEstadoIncorrecto(final String pagoEstadoIncorrecto) {
		this.pagoEstadoIncorrecto = pagoEstadoIncorrecto;
	}

}
