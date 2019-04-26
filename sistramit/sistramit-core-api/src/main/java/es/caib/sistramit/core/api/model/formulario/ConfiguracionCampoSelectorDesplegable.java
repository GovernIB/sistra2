package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;

/**
 * Configuración de un campo del formulario de tipo selector de tipo
 * desplegable.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ConfiguracionCampoSelectorDesplegable extends ConfiguracionCampoSelector {

	/** Opciones. */
	private OpcionesSelectorDesplegable opciones;

	/**
	 * Constructor
	 */
	public ConfiguracionCampoSelectorDesplegable() {
		super();
		setContenido(TypeSelector.LISTA);
	}

	/**
	 * Método de acceso a opciones.
	 *
	 * @return opciones
	 */
	public OpcionesSelectorDesplegable getOpciones() {
		return opciones;
	}

	/**
	 * Método para establecer opciones.
	 *
	 * @param opciones
	 *            opciones a establecer
	 */
	public void setOpciones(final OpcionesSelectorDesplegable opciones) {
		this.opciones = opciones;
	}

}
