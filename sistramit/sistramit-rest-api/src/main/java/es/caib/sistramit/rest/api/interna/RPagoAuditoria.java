package es.caib.sistramit.rest.api.interna;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Evento Auditoria.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPagoAuditoria", description = "Pago Auditoria")
public class RPagoAuditoria {
	/**
	 * clave de tramitacion
	 */
	@ApiModelProperty(value = "id.Sesion Tramitacion")
	private String idSesionTramitacion;

	/**
	 * fecha.
	 */
	@ApiModelProperty(value = "Fecha")
	private Date fecha;

	/**
	 * id tramite.
	 */
	@ApiModelProperty(value = "Identificador tramite")
	private String idTramite;

	/**
	 * version tramite.
	 */
	@ApiModelProperty(value = "Version tramite")
	private Integer versionTramite;

	/**
	 * id procedimiento CP.
	 */
	@ApiModelProperty(value = "Codigo procedimiento")
	private String idProcedimientoCP;

	@ApiModelProperty(value = "Identificador fichero")
	private Long fichero;

	@ApiModelProperty(value = "Clave fichero")
	private String ficheroClave;

	@ApiModelProperty(value = "Identificador pago")
	private Long codigoPago;

	@ApiModelProperty(value = "Estado")
	private String estado;

	@ApiModelProperty(value = "Identificador")
	private String identificador;

	@ApiModelProperty(value = "Presentacion pago")
	private String presentacion;

	@ApiModelProperty(value = "Identificador pasarela")
	private String pasarelaId;

	@ApiModelProperty(value = "Importe")
	private int importe;

	@ApiModelProperty(value = "Codigo tasa")
	private String tasaId;

	@ApiModelProperty(value = "Identificador pago pasarela")
	private String localizador;

	@ApiModelProperty(value = "Fecha pago")
	private Date fechaPago;

	@ApiModelProperty(value = "Estado Pago")
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
