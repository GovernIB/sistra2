package es.caib.sistra2.commons.plugins.mock.autenticacion;

import java.util.List;

import es.caib.sistra2.commons.plugins.autenticacion.DatosUsuario;
import es.caib.sistra2.commons.plugins.autenticacion.IComponenteAutenticacionPlugin;
import es.caib.sistra2.commons.plugins.autenticacion.TipoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.TipoMetodoAutenticacion;

/**
 * Plugin mock componente autenticación.
 *
 * @author Indra
 *
 */
public class ComponenteAutenticacionPluginMock
        implements IComponenteAutenticacionPlugin {

    /**
     * Punto de entrada retorno componente autenticacion.
     */
    private static final String PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN = "/asistente/retornoAutenticacion.html";

    @Override
    public String iniciarSesionAutenticacion(String codigoEntidad,
            String idioma, List<TipoAutenticacion> metodos, String qaa,
            String callback) {
        // Simulamos directamente retorno componente autenticacion. Ponemos la
        // primera letra del ticket el primer tipo de autenticacion para simular
        // ese metodo.
        String prefix;
        prefix = metodos.get(0).toString();
        return PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN + "?ticket=" + prefix
                + "12345";
    }

    @Override
    public DatosUsuario validarTicketAutenticacion(String pTicket) {
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
    public String iniciarSesionLogout(String codigoEntidad, String pIdioma,
            String pCallback) {
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
     * @param sesionInfo
     *            sesion info
     * @return usuario anonimo
     */
    private DatosUsuario generarUsuarioAnonimo() {
        final DatosUsuario ui = new DatosUsuario();
        ui.setAutenticacion(TipoAutenticacion.ANONIMO);
        ui.setMetodoAutenticacion(TipoMetodoAutenticacion.ANONIMO);
        return ui;
    }

}
