package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

/**
 * Respuesta al ejecutar una accion paso desde el controlador de referencia.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class RespuestaEjecutarAccionPaso implements Serializable {

	/**
	 * Respuesta de la acción del paso.
	 */
	private RespuestaAccionPaso respuestaAccionPaso;

	/**
	 * Indica si se debe recalcular los datos internos desde persistencia.
	 */
	private boolean recalcularDatosInternos;

	/**
	 * Método de acceso a respuestaAccionPaso.
	 *
	 * @return respuestaAccionPaso
	 */
	public RespuestaAccionPaso getRespuestaAccionPaso() {
		return respuestaAccionPaso;
	}

	/**
	 * Método para establecer respuestaAccionPaso.
	 *
	 * @param pRespuestaAccionPaso
	 *            respuestaAccionPaso a establecer
	 */
	public void setRespuestaAccionPaso(final RespuestaAccionPaso pRespuestaAccionPaso) {
		respuestaAccionPaso = pRespuestaAccionPaso;
	}

	/**
	 * Método de acceso a recalcularDatosInternos.
	 *
	 * @return recalcularDatosInternos
	 */
	public boolean isRecalcularDatosInternos() {
		return recalcularDatosInternos;
	}

	/**
	 * Método para establecer recalcularDatosInternos.
	 *
	 * @param pRecalcularDatosInternos
	 *            recalcularDatosInternos a establecer
	 */
	public void setRecalcularDatosInternos(final boolean pRecalcularDatosInternos) {
		recalcularDatosInternos = pRecalcularDatosInternos;
	}

}
