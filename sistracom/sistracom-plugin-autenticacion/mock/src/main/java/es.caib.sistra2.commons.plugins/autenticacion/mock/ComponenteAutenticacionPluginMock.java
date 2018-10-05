package es.caib.sistra2.commons.plugins.autenticacion.mock;

import es.caib.sistra2.commons.plugins.autenticacion.api.*;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import java.util.List;
import java.util.Properties;

/**
 * Plugin mock componente autenticación.
 *
 * @author Indra
 *
 */
public class ComponenteAutenticacionPluginMock extends AbstractPluginProperties
        implements IComponenteAutenticacionPlugin {

    /**
     * Punto de entrada retorno componente es.caib.sistra2.commons.plugins.autenticacion.
     */
    private static final String PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN = "/asistente/retornoAutenticacion.html";

    public ComponenteAutenticacionPluginMock() {
    }

    public ComponenteAutenticacionPluginMock(final String prefijoPropiedades,
                                             final Properties properties) {
    }

    @Override
    public String iniciarSesionAutenticacion(final String codigoEntidad,
            final String idioma, final List<TipoAutenticacion> metodos,
            final String qaa, final String callback)
            throws AutenticacionPluginException {
        // Simulamos directamente retorno componente es.caib.sistra2.commons.plugins.autenticacion. Ponemos la
        // primera letra del ticket el primer tipo de es.caib.sistra2.commons.plugins.autenticacion para simular
        // ese metodo.
        String prefix;
        prefix = metodos.get(0).toString();
        return PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN + "?ticket=" + prefix
                + "12345";
    }

    @Override
    public DatosUsuario validarTicketAutenticacion(final String pTicket)
            throws AutenticacionPluginException {
        // Simulamos segun primera letra del ticket
        DatosUsuario u;
        if (pTicket.startsWith(TipoAutenticacion.AUTENTICADO.toString())) {
            u = generarUserMock();
        } else {
            u = generarUsuarioAnonimo();
        }
        return u;
    }

    @Override
    public String iniciarSesionLogout(final String codigoEntidad,
            final String pIdioma, final String pCallback)
            throws AutenticacionPluginException {
        // TODO PENDIENTE IMPLEMENTAR
        return null;
    }

    /**
     * Genera usuario mock autenticado.
     *
     * @return usuario mock autenticado
     */
    private DatosUsuario generarUserMock() {
        final DatosUsuario ui = new DatosUsuario();
        ui.setAutenticacion(TipoAutenticacion.AUTENTICADO);
        ui.setMetodoAutenticacion(TipoMetodoAutenticacion.CLAVE_CERTIFICADO);
        ui.setNif("11111111H");
        ui.setNombre("José");
        ui.setApellido1("García");
        ui.setApellido2("Gutierrez");
        ui.setEmail("correo@email.es");
        return ui;
    }

    /**
     * Genera usuario anonimo.
     *
     * @return usuario anonimo
     */
    private DatosUsuario generarUsuarioAnonimo() {
        final DatosUsuario ui = new DatosUsuario();
        ui.setAutenticacion(TipoAutenticacion.ANONIMO);
        ui.setMetodoAutenticacion(TipoMetodoAutenticacion.ANONIMO);
        return ui;
    }

}
