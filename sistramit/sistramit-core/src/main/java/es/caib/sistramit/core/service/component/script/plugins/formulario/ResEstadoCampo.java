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
	public ResEstadoCampo(String pIdElemento) {
		super();
		estadoCampo = new EstadoCampo();
		estadoCampo.setIdCampo(pIdElemento);
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	/**
	 * Método de acceso a estadoCampo.
	 *
	 * @return estadoCampo
	 */
	public EstadoCampo getEstadoCampo() {
		return estadoCampo;
	}

	@Override
	public void setSoloLectura(final boolean readOnly) {
		estadoCampo.setSoloLectura(TypeSiNo.fromBoolean(readOnly));
	}

	@Override
	public void setVisible(final boolean visible) {
		estadoCampo.setOculto(TypeSiNo.fromBoolean(!visible));
	}

	@Override
	public void setObligatorio(boolean obligatorio) {
		estadoCampo.setObligatorio(TypeSiNo.fromBoolean(obligatorio));
	}
}