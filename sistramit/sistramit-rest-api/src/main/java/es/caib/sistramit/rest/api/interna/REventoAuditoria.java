package es.caib.sistramit.rest.api.interna;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "REventoAuditoria", description = "Evento Auditoria")
public class REventoAuditoria {

	/**
	 * id.
	 */
	@ApiModelProperty(value = "Identificador")
	private Long id;

	/**
	 * tipoEvento.
	 */
	@ApiModelProperty(value = "Tipo de evento")
	private String tipoEvento;

	/**
	 * fecha.
	 */
	@ApiModelProperty(value = "fecha del evento")
	private Date fecha;

	/**
	 * descripcion.
	 */
	@ApiModelProperty(value = "Descripcion")
	private String descripcion;

	/**
	 * resultado.
	 */
	@ApiModelProperty(value = "Resultado")
	private String resultado;

	/**
	 * id sesion.
	 */
	@ApiModelProperty(value = "Identificador de la sesión")
	private String idSesionTramitacion;

	/**
	 * nif.
	 */
	@ApiModelProperty(value = "Nif")
	private String nif;

	/**
	 * id tramite.
	 */
	@ApiModelProperty(value = "Identificador del trámite")
	private String idTramite;

	/**
	 * version tramite.
	 */
	@ApiModelProperty(value = "Versión del trámite")
	private Integer versionTramite;

	/**
	 * cod procedimiento.
	 */
	@ApiModelProperty(value = "Código del procedimiento")
	private String idProcedimientoCP;

	/**
	 * id procedimiento SIA.
	 */
	@ApiModelProperty(value = "Código del procedimiento SIA")
	private String idProcedimientoSIA;

	/**
	 * codigoError.
	 */
	@ApiModelProperty(value = "Código Error")
	private String codigoError;

	/**
	 * traza.
	 */
	@ApiModelProperty(value = "Traza Error")
	private String trazaError;

	@ApiModelProperty(value = "detalle")
	private String detalle;

	/**
	 * Instancia un nuevo evento
	 */
	public REventoAuditoria() {
	}

	/**
	 * Para obtener el atributo id.
	 *
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Para establecer el atributo id.
	 *
	 * @param pId
	 *            el nuevo valor para id
	 */
	public void setId(final Long pId) {
		this.id = pId;
	}

	/**
	 * Para obtener el atributo fecha.
	 *
	 * @return fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Para establecer el atributo fecha.
	 *
	 * @param pFecha
	 *            el nuevo valor para fecha
	 */
	public void setFecha(final Date pFecha) {
		this.fecha = pFecha;
	}

	/**
	 * Para obtener el atributo descripcion.
	 *
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Para establecer el atributo descripcion.
	 *
	 * @param pDescripcion
	 *            el nuevo valor para descripcion
	 */
	public void setDescripcion(final String pDescripcion) {
		this.descripcion = pDescripcion;
	}

	/**
	 * Para obtener el atributo resultado.
	 *
	 * @return resultado
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * Para establecer el atributo resultado.
	 *
	 * @param pResultado
	 *            el nuevo valor para resultado
	 */
	public void setResultado(final String pResultado) {
		this.resultado = pResultado;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(final String tipo) {
		this.tipoEvento = tipo;
	}

	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	public void setIdSesionTramitacion(final String idSesion) {
		this.idSesionTramitacion = idSesion;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(final String nif) {
		this.nif = nif;
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

	public void setIdProcedimientoCP(final String codProcedimiento) {
		this.idProcedimientoCP = codProcedimiento;
	}

	public String getIdProcedimientoSIA() {
		return idProcedimientoSIA;
	}

	public void setIdProcedimientoSIA(final String idProcedimientoSIA) {
		this.idProcedimientoSIA = idProcedimientoSIA;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(final String codigoError) {
		this.codigoError = codigoError;
	}

	public String getTrazaError() {
		return trazaError;
	}

	public void setTrazaError(final String trazaError) {
		this.trazaError = trazaError;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(final String detalle) {
		this.detalle = detalle;
	}

}
