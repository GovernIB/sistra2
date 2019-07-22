package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;

/**
 * Configuración de un campo del formulario de tipo oculto.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionCampoOculto extends ConfiguracionCampo {

	/**
	 * Constructor.
	 */
	public ConfiguracionCampoOculto() {
		super();
		setTipo(TypeCampo.OCULTO);
	}

}
