package es.caib.sistramit.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * La clase RDetallePagoAuditoria.
 */
@ApiModel(value = "RDetallePagoAuditoria", description = "Detalle Pago")
public class RDetallePagoAuditoria {

	/**
	 * datos.
	 */
	@ApiModelProperty(value = "Datos sesion pago")
	private RDatosSesionPago datos;

	/**
	 * verificacion.
	 */
	@ApiModelProperty(value = "Datos verificacion pago")
	private RVerificacionPago verificacion;

	/**
	 * Obtiene el valor de datos.
	 *
	 * @return el valor de datos
	 */
	public RDatosSesionPago getDatos() {
		return datos;
	}

	/**
	 * Establece el valor de datos.
	 *
	 * @param datos
	 *            el nuevo valor de datos
	 */
	public void setDatos(final RDatosSesionPago datos) {
		this.datos = datos;
	}

	/**
	 * Obtiene el valor de verificacion.
	 *
	 * @return el valor de verificacion
	 */
	public RVerificacionPago getVerificacion() {
		return verificacion;
	}

	/**
	 * Establece el valor de verificacion.
	 *
	 * @param verificacion
	 *            el nuevo valor de verificacion
	 */
	public void setVerificacion(final RVerificacionPago verificacion) {
		this.verificacion = verificacion;
	}
}
