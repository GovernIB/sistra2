package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;

/**
 * Configuración de un campo del formulario de tipo lista elementos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionCampoListaElementos extends ConfiguracionCampo {

	/** Opciones. */
	private OpcionesCampoListaElementos opciones;

	/**
	 * Constructor.
	 */
	public ConfiguracionCampoListaElementos() {
		super();
		setTipo(TypeCampo.LISTA_ELEMENTOS);
	}

	/**
	 * Método de acceso a opciones.
	 *
	 * @return opciones
	 */
	public OpcionesCampoListaElementos getOpciones() {
		return opciones;
	}

	/**
	 * Método para establecer opciones.
	 *
	 * @param opciones
	 *                     opciones a establecer
	 */
	public void setOpciones(final OpcionesCampoListaElementos opciones) {
		this.opciones = opciones;
	}

}
