package es.caib.sistra2.commons.plugins.autenticacion;

import java.util.List;

import org.fundaciobit.plugins.IPlugin;

/**
 * Interface componente autenticación.
 *
 * @author Indra
 *
 */
public interface IComponenteAutenticacionPlugin extends IPlugin {

    /**
     * Inicia sesion autenticacion.
     *
     * @param codigoEntidad
     *            codigo entidad
     * @param idioma
     *            Idioma
     * @param metodos
     *            metodos auth
     * @param qaa
     *            qaa
     * @param urlCallback
     *            callback
     * @return url inicio sesion
     */
    String iniciarSesionAutenticacion(String codigoEntidad, String idioma,
            List<TipoAutenticacion> metodos, String qaa, String urlCallback);

    /**
     * Validar ticket.
     *
     * @param ticket
     *            ticket
     * @return datos usuario
     */
    DatosUsuario validarTicketAutenticacion(String ticket);

    /**
     * Iniciar sesión de logout.
     *
     * @param codigoEntidad
     *            codigo entidad
     * @param idioma
     *            idioma
     * @param urlCallback
     *            url callback
     * @param pDebugEnabled
     *            debug
     * @return
     */
    String iniciarSesionLogout(String codigoEntidad, String idioma,
            String urlCallback);

}
