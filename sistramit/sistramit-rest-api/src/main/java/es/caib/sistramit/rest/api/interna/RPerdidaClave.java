package es.caib.sistramit.rest.api.interna;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Perdida de clave
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPerdidaClave", description = "Perdida clave")
public class RPerdidaClave {

	/**
	 * tipo de evento.
	 */
	@ApiModelProperty(value = "Clave Tramitacion")
	private String claveTramitacion;

	/**
	 * Fecha del evento.
	 */
	@ApiModelProperty(value = "Fecha del evento")
	private Date fecha;

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
	 * Instancia un nuevo evento
	 */
	public RPerdidaClave() {
	}

	/**
	 * Obtiene el valor de clave de tramitacion.
	 *
	 * @return el valor de clave de tramitacion
	 */
	public String getClaveTramitacion() {
		return claveTramitacion;
	}

	/**
	 * Establece el valor de clave de tramitacion.
	 *
	 * @param claveTramitacion
	 *            el nuevo valor de clave de tramitacion
	 */
	public void setClaveTramitacion(final String claveTramitacion) {
		this.claveTramitacion = claveTramitacion;
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

}
