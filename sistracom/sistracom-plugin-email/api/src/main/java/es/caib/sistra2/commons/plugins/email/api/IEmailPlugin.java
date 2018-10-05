package es.caib.sistra2.commons.plugins.email.api;

import org.fundaciobit.pluginsib.core.IPlugin;

import java.util.List;

/**
 * Interface email plugin.
 *
 * @author Indra
 *
 */
public interface IEmailPlugin extends IPlugin {

	/** Prefix. */
	public static final String AUTENTICACION_BASE_PROPERTY = IPLUGIN_BASE_PROPERTIES + "email.";

	/**
	 * Realiza envio email.
	 *
	 * @param destinatarios
	 *            Destinatarios
	 * @param asunto
	 *            Asunto
	 * @param mensaje
	 *            Mensaje
	 * @param anexos
	 *            anexos
	 * @return boolean
	 */
	boolean envioEmail(List<String> destinatarios, String asunto, String mensaje, List<AnexoEmail> anexos)
			throws EmailPluginException;

}
