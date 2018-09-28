package es.caib.sistra2.commons.plugins.mock.email;

import java.util.List;
import java.util.Properties;

import org.fundaciobit.plugins.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.commons.plugins.email.AnexoEmail;
import es.caib.sistra2.commons.plugins.email.EmailPluginException;
import es.caib.sistra2.commons.plugins.email.IEmailPlugin;

/**
 * Plugin mock email.
 *
 * @author Indra
 *
 */
public class EmailPluginMock extends AbstractPluginProperties
        implements IEmailPlugin {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public EmailPluginMock() {
    }

    public EmailPluginMock(final String prefijoPropiedades,
            final Properties properties) {
    }

    @Override
    public boolean envioEmail(List<String> destinatarios, String asunto,
            String mensaje, List<AnexoEmail> anexos)
            throws EmailPluginException {
        String dest = "";
        for (final String d : destinatarios) {
            dest += d + " ";
        }
        log.info("Simular envio mail. \nDestinatarios:" + dest + "\nAsunto: "
                + asunto + "\nCuerpo:" + mensaje);

        return true;
    }

}
