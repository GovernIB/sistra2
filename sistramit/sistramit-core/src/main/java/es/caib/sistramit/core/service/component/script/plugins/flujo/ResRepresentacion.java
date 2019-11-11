package es.caib.sistramit.core.service.component.script.plugins.flujo;

import es.caib.sistramit.core.service.model.script.flujo.ResPersonaInt;
import es.caib.sistramit.core.service.model.script.flujo.ResRepresentacionInt;

/**
 * Datos representación: representante y representado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResRepresentacion implements ResRepresentacionInt {

	/** Indica si se activa la representación. */
	private boolean activarRepresentacion = true;

	/** Representante. */
	private ResPersona representante = null;

	/** Representado. */
	private ResPersona representado = null;

	@Override
	public String getPluginId() {
		return ID;
	}

	/**
	 * Método de acceso a representante.
	 *
	 * @return representante
	 */
	public ResPersona getRepresentante() {
		return representante;
	}

	/**
	 * Método de acceso a representado.
	 *
	 * @return representado
	 */
	public ResPersona getRepresentado() {
		return representado;
	}

	@Override
	public ResPersonaInt crearRepresentante() {
		representante = new ResPersona();
		return representante;
	}

	@Override
	public ResPersonaInt crearRepresentado() {
		representado = new ResPersona();
		return representado;
	}

	@Override
	public void setActivarRepresentacion(final boolean activar) {
		activarRepresentacion = activar;
	}

	/**
	 * Método de acceso a activarRepresentacion.
	 * 
	 * @return activarRepresentacion
	 */
	public boolean isActivarRepresentacion() {
		return activarRepresentacion;
	}

}
