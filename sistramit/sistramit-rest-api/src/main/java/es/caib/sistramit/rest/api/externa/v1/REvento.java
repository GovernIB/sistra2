package es.caib.sistramit.rest.api.externa.v1;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Evento.
 *
 * @author Indra
 *
 */
@ApiModel(value = "REvento", description = "Evento")
public class REvento {

	/**
	 * tipo de evento.
	 */
	@ApiModelProperty(value = "Tipo de evento")
	private String tipoEvento;

	/**
	 * Fecha del evento.
	 */
	@ApiModelProperty(value = "Fecha del evento")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private Date fecha;

	/**
	 * Identificador de la sesión.
	 */
	@ApiModelProperty(value = "Identificador de la sesión")
	private String idSesionTramitacion;

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
	 * Id procedimiento SIA.
	 */
	@ApiModelProperty(value = "Código del procedimiento SIA")
	private String idProcedimientoSIA;

	/**
	 * Propiedades particulares evento.
	 */
	@ApiModelProperty(value = "Propiedades particulares evento")
	private Map<String, String> propiedadesEvento;

	/**
	 * Instancia un nuevo evento
	 */
	public REvento() {
		super();
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
	 *                   el nuevo valor para fecha del evento
	 */
	public void setFecha(final Date pFecha) {
		this.fecha = pFecha;
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
	 *                 el nuevo valor de tipo evento
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
	 *                     el nuevo valor de Identificador de la sesión
	 */
	public void setIdSesionTramitacion(final String idSesion) {
		this.idSesionTramitacion = idSesion;
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
	 *                      el nuevo valor de id. tramite
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
	 *                           el nuevo valor de version tramite
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
	 *                             el nuevo valor de id. Procedimiento CP
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
	 *                               el nuevo valor de id. Procedimiento SIA
	 */
	public void setIdProcedimientoSIA(final String idProcedimientoSIA) {
		this.idProcedimientoSIA = idProcedimientoSIA;
	}

	/**
	 * Método de acceso a propiedadesEvento.
	 *
	 * @return propiedadesEvento
	 */
	public Map<String, String> getPropiedadesEvento() {
		return propiedadesEvento;
	}

	/**
	 * Método para establecer propiedadesEvento.
	 *
	 * @param propiedadesEvento
	 *                              propiedadesEvento a establecer
	 */
	public void setPropiedadesEvento(final Map<String, String> propiedadesEvento) {
		this.propiedadesEvento = propiedadesEvento;
	}

}
