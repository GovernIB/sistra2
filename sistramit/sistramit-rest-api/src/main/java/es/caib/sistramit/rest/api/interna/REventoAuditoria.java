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
@ApiModel(value = "REventoAuditoria", description = "Evento Auditoria")
public class REventoAuditoria {

	/**
	 * Identificador.
	 */
	@ApiModelProperty(value = "Identificador")
	private Long id;

	/**
	 * tipo de evento.
	 */
	@ApiModelProperty(value = "Tipo de evento")
	private String tipoEvento;

	/**
	 * Fecha del evento.
	 */
	@ApiModelProperty(value = "Fecha del evento")
	private Date fecha;

	/**
	 * Descripcion.
	 */
	@ApiModelProperty(value = "Descripcion")
	private String descripcion;

	/**
	 * Resultado.
	 */
	@ApiModelProperty(value = "Resultado")
	private String resultado;

	/**
	 * Identificador de la sesión.
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
	 * Código del procedimiento.
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

	/**
	 * detalle.
	 */
	@ApiModelProperty(value = "detalle")
	private String detalle;

	/**
	 * Instancia un nuevo evento
	 */
	public REventoAuditoria() {
		super();
	}

	/**
	 * Para obtener el Identificador.
	 *
	 * @return Identificador
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Para establecer el Identificador.
	 *
	 * @param pId
	 *            el nuevo valor para Identificador
	 */
	public void setId(final Long pId) {
		this.id = pId;
	}

	/**
	 * Para obtener la fecha del evento.
	 *
	 * @return fecha del evento
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Para establecer la fecha del evento.
	 *
	 * @param pFecha
	 *            el nuevo valor para fecha del evento
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

	/**
	 * Obtiene el valor de tipo evento.
	 *
	 * @return el valor de tipo evento
	 */
	public String getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * Establece el valor de tipo evento.
	 *
	 * @param tipo
	 *            el nuevo valor de tipo evento
	 */
	public void setTipoEvento(final String tipo) {
		this.tipoEvento = tipo;
	}

	/**
	 * Obtiene el valor de Identificador de la sesión.
	 *
	 * @return el valor de Identificador de la sesión
	 */
	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	/**
	 * Establece el valor de Identificador de la sesión.
	 *
	 * @param idSesion
	 *            el nuevo valor de Identificador de la sesión
	 */
	public void setIdSesionTramitacion(final String idSesion) {
		this.idSesionTramitacion = idSesion;
	}

	/**
	 * Obtiene el valor de nif.
	 *
	 * @return el valor de nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Establece el valor de nif.
	 *
	 * @param nif
	 *            el nuevo valor de nif
	 */
	public void setNif(final String nif) {
		this.nif = nif;
	}

	/**
	 * Obtiene el valor de id. Tramite.
	 *
	 * @return el valor de id. Tramite
	 */
	public String getIdTramite() {
		return idTramite;
	}

	/**
	 * Establece el valor de id. tramite.
	 *
	 * @param idTramite
	 *            el nuevo valor de id. tramite
	 */
	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	/**
	 * Obtiene el valor de version tramite.
	 *
	 * @return el valor de version tramite
	 */
	public Integer getVersionTramite() {
		return versionTramite;
	}

	/**
	 * Establece el valor de version tramite.
	 *
	 * @param versionTramite
	 *            el nuevo valor de version tramite
	 */
	public void setVersionTramite(final Integer versionTramite) {
		this.versionTramite = versionTramite;
	}

	/**
	 * Obtiene el valor de id. Procedimiento CP.
	 *
	 * @return el valor de id. Procedimiento CP
	 */
	public String getIdProcedimientoCP() {
		return idProcedimientoCP;
	}

	/**
	 * Establece el valor de id. Procedimiento CP.
	 *
	 * @param codProcedimiento
	 *            el nuevo valor de id. Procedimiento CP
	 */
	public void setIdProcedimientoCP(final String codProcedimiento) {
		this.idProcedimientoCP = codProcedimiento;
	}

	/**
	 * Obtiene el valor de id. Procedimiento SIA.
	 *
	 * @return el valor de id. Procedimiento SIA
	 */
	public String getIdProcedimientoSIA() {
		return idProcedimientoSIA;
	}

	/**
	 * Establece el valor de id. Procedimiento SIA.
	 *
	 * @param idProcedimientoSIA
	 *            el nuevo valor de id. Procedimiento SIA
	 */
	public void setIdProcedimientoSIA(final String idProcedimientoSIA) {
		this.idProcedimientoSIA = idProcedimientoSIA;
	}

	/**
	 * Obtiene el valor de codigo error.
	 *
	 * @return el valor de codigo error
	 */
	public String getCodigoError() {
		return codigoError;
	}

	/**
	 * Establece el valor de codigo error.
	 *
	 * @param codigoError
	 *            el nuevo valor de codigo error
	 */
	public void setCodigoError(final String codigoError) {
		this.codigoError = codigoError;
	}

	/**
	 * Obtiene el valor de traza error.
	 *
	 * @return el valor de traza error
	 */
	public String getTrazaError() {
		return trazaError;
	}

	/**
	 * Establece el valor de traza error.
	 *
	 * @param trazaError
	 *            el nuevo valor de traza error
	 */
	public void setTrazaError(final String trazaError) {
		this.trazaError = trazaError;
	}

	/**
	 * Obtiene el valor de detalle.
	 *
	 * @return el valor de detalle
	 */
	public String getDetalle() {
		return detalle;
	}

	/**
	 * Establece el valor de detalle.
	 *
	 * @param detalle
	 *            el nuevo valor de detalle
	 */
	public void setDetalle(final String detalle) {
		this.detalle = detalle;
	}

}
