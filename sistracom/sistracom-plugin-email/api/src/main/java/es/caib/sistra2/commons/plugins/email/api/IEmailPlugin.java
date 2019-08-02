package es.caib.sistra2.commons.plugins.email.api;

import java.util.List;

import org.fundaciobit.pluginsib.core.IPlugin;

/**
 * Interface email plugin.
 *
 * @author Indra
 *
 */
public interface IEmailPlugin extends IPlugin {

    /** Prefix. */
    public static final String EMAIL_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES;

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
    boolean envioEmail(List<String> destinatarios, String asunto,
            String mensaje, List<AnexoEmail> anexos)
            throws EmailPluginException;

}
