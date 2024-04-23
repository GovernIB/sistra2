package es.caib.sistra2.commons.plugins.registro.mock;

import java.util.Date;
import java.util.Properties;

import es.caib.sistra2.commons.plugins.registro.api.*;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.commons.plugins.registro.api.types.TypeEstadoRegistro;

/**
 * Plugin mock registro.
 *
 * @author Indra
 */
public class EnvioRemotoMockPlugin extends AbstractPluginProperties implements IEnvioRemotoPlugin {

    /**
     * Log.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvioRemotoMockPlugin.class);

    /**
     * Prefix.
     */
    public static final String IMPLEMENTATION_BASE_PROPERTY = "mock.";

    /**
     * Nombre de propiedad para indicar cuanto tiempo se espera para registrar (en
     * segundos).
     */
    public static final String PROP_DURACION_ENVIO = "duracionEnvio";

    /**
     * Si genera error al registrar (true/false).
     */
    public static final String PROP_ENVIAR_ERROR = "enviarERROR";

    public EnvioRemotoMockPlugin(final String prefijoPropiedades, final Properties properties) {
        super(prefijoPropiedades, properties);
    }

    @Override
    public String iniciarSesionEnvio(final DestinoEnvio destinoEnvio) throws EnvioRemotoPluginException {
        // Simulamos
        return System.currentTimeMillis() + "";
    }

    @Override
    public VerificacionRegistro verificarEnvio(final DestinoEnvio destinoEnvio, final String idSesionEnvio)
            throws EnvioRemotoPluginException {
        final VerificacionRegistro res = new VerificacionRegistro();
        res.setEstado(TypeEstadoRegistro.REALIZADO);
        final ResultadoRegistro resultadoRegistro = new ResultadoRegistro();
        resultadoRegistro.setFechaRegistro(new Date());
        resultadoRegistro.setNumeroRegistro("E-" + System.currentTimeMillis());
        res.setDatosRegistro(resultadoRegistro);
        return res;
    }

    @Override
    public ResultadoRegistro realizarEnvio(final DestinoEnvio destinoEnvio, final String idSesionEnvio,
                                           final DatosTramitacion datosTramitacion, final AsientoRegistral asientoRegistral) throws EnvioRemotoPluginException {

        // Si hay que simular tiempo registro
        final String duracionRegistro = getPropiedad(PROP_DURACION_ENVIO);
        if (duracionRegistro != null) {
            final int duracionSegundos = Integer.parseInt(duracionRegistro);
            if (duracionSegundos > 0) {
                retardo(duracionSegundos);
            }
        }

        // Si hay que simular error registro
        if ("true".equals(getPropiedad(PROP_ENVIAR_ERROR))) {
            throw new EnvioRemotoPluginException("Simulación error envío");
        }

        // Devolvemos simulacion registro
        final ResultadoRegistro res = new ResultadoRegistro();
        res.setFechaRegistro(new Date());
        res.setNumeroRegistro("E-" + System.currentTimeMillis());
        return res;
    }

    /**
     * Obtiene propiedad.
     *
     * @param propiedad propiedad
     * @return valor
     * @throws RegistroPluginException
     */
    private String getPropiedad(final String propiedad) {
        final String res = getProperty(REGISTRO_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
        return res;
    }

    /**
     * Implementa retardo para testing.
     *
     * @param pTimeout timeout en segundos
     */
    private void retardo(final int pTimeout) {
        LOGGER.debug("Simulando retardo de " + pTimeout + " segundos");
        final long inicio = System.currentTimeMillis();
        while (true) {
            if ((System.currentTimeMillis() - inicio) > (pTimeout * 1000L)) {
                break;
            }
        }
        LOGGER.debug("Fin simulando retardo de " + pTimeout + " segundos");
    }

}
