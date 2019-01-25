package es.caib.sistramit.core.service.component.script.plugins.formulario;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.service.model.formulario.interno.EstadoCampo;
import es.caib.sistramit.core.service.model.script.formulario.ResEstadoCampoInt;

/**
 *
 * Establecimiento de estado en script estado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResEstadoCampo implements ResEstadoCampoInt {

	/**
	 * Estado del campo.
	 */
	private final EstadoCampo estadoCampo;

	/**
	 * Constructor.
	 */
	public ResEstadoCampo() {
		super();
		estadoCampo = new EstadoCampo();
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public void setSoloLectura(final boolean readOnly) {
		if (readOnly) {
			estadoCampo.setSoloLectura(TypeSiNo.SI);
		} else {
			estadoCampo.setSoloLectura(TypeSiNo.NO);
		}
	}

	/**
	 * Método de acceso a estadoCampo.
	 *
	 * @return estadoCampo
	 */
	public EstadoCampo getEstadoCampo() {
		return estadoCampo;
	}

}
