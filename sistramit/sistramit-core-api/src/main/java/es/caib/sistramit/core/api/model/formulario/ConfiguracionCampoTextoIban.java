package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto IBAN.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionCampoTextoIban extends ConfiguracionCampoTexto {

	/**
	 * Constructor.
	 */
	public ConfiguracionCampoTextoIban() {
		super();
		setContenido(TypeTexto.IBAN);
	}

}
