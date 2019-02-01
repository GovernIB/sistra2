package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto hora.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ConfiguracionCampoTextoHora extends ConfiguracionCampoTexto {

	/**
	 * Constructor.
	 */
	public ConfiguracionCampoTextoHora() {
		super();
		setContenido(TypeTexto.HORA);
	}

}
