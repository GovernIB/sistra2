package es.caib.sistramit.core.service.model.system;

import java.util.Date;

/**
 * Cacheo de flujo de tramitación.
 *
 * @author Indra
 *
 */
public class FlujoTramitacionCache {

	/** Flujo de tramitacion. */
	private FlujoTramitacionCacheIntf flujoTramitacion;

	/** Ultimo acceso. */
	private Date fcUltimoAcceso;

	/**
	 * Método de acceso a flujoTramitacion.
	 *
	 * @return flujoTramitacion
	 */
	public FlujoTramitacionCacheIntf getFlujoTramitacion() {
		return flujoTramitacion;
	}

	/**
	 * Método para establecer flujoTramitacion.
	 *
	 * @param flujoTramitacion
	 *            flujoTramitacion a establecer
	 */
	public void setFlujoTramitacion(FlujoTramitacionCacheIntf flujoTramitacion) {
		this.flujoTramitacion = flujoTramitacion;
	}

	/**
	 * Método de acceso a fcUltimoAcceso.
	 *
	 * @return fcUltimoAcceso
	 */
	public Date getFcUltimoAcceso() {
		return fcUltimoAcceso;
	}

	/**
	 * Método para establecer fcUltimoAcceso.
	 *
	 * @param fcUltimoAcceso
	 *            fcUltimoAcceso a establecer
	 */
	public void setFcUltimoAcceso(Date fcUltimoAcceso) {
		this.fcUltimoAcceso = fcUltimoAcceso;
	}

}
