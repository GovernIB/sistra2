package es.caib.sistramit.core.service.model.formulario;

import java.util.Date;

import es.caib.sistramit.core.service.component.formulario.interno.FlujoFormularioComponent;

/**
 * Cacheo de flujo de formulario interno.
 *
 * @author Indra
 *
 */
public class FlujoFormularioCache {

	/** Flujo de tramitacion. */
	private FlujoFormularioComponent flujoFormulario;

	/** Ultimo acceso. */
	private Date fcUltimoAcceso;

	/**
	 * Método de acceso a flujoFormulario.
	 *
	 * @return flujoFormulario
	 */
	public FlujoFormularioComponent getFlujoFormulario() {
		return flujoFormulario;
	}

	/**
	 * Método para establecer flujoFormulario.
	 *
	 * @param flujoFormulario
	 *            flujoFormulario a establecer
	 */
	public void setFlujoFormulario(FlujoFormularioComponent flujoTramitacion) {
		this.flujoFormulario = flujoTramitacion;
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
