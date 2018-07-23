package es.caib.sistra2.commons.plugins.email;

import java.util.List;

import org.fundaciobit.plugins.IPlugin;

/**
 * Interface email plugin.
 *
 * @author Indra
 *
 */
public interface IEmailPlugin extends IPlugin {

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
            String mensaje, List<AnexoEmail> anexos);

}
