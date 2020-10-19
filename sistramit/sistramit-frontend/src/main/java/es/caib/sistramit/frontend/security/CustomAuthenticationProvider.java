package es.caib.sistramit.frontend.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.security.SesionInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.service.SecurityService;
import es.caib.sistramit.frontend.ApplicationContextProvider;
import es.caib.sistramit.frontend.SesionHttp;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    /** Log. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(CustomAuthenticationProvider.class);

    @Override
    public Authentication authenticate(final Authentication authentication) {

        debug("Entra a autenticate");

        // Obtiene user/pass
        final String usuario = authentication.getName();
        final String passwd = authentication.getCredentials().toString();
        if (StringUtils.isBlank(usuario) || StringUtils.isBlank(passwd)) {
            throw new BadCredentialsException("Usuario no válido");
        }

        // Obtenemos security service
        final SecurityService securityService = (SecurityService) ApplicationContextProvider
                .getApplicationContext().getBean("securityService");

        // Obtenemos sesion info
        final SesionHttp sesionHttp = (SesionHttp) ApplicationContextProvider
                .getApplicationContext().getBean("sesionHttp");
        final SesionInfo sesionInfo = new SesionInfo();
        sesionInfo.setUserAgent(sesionHttp.getUserAgent());
        sesionInfo.setIdioma(sesionHttp.getIdioma());

        // Valida usuario
        UsuarioAutenticadoInfo usuarioAutenticadoInfo = null;
        try {

            if (ConstantesSeguridad.ANONIMO_USER.equals(usuario)) {
                // Usuario anonimo: autenticacion automatica carga persistencia
                LOGGER.trace("Obtener user anonimo");
                usuarioAutenticadoInfo = securityService
                        .validarUsuarioAnonimo(sesionInfo);
            } else if (ConstantesSeguridad.TICKET_USER_CARPETA
                    .equals(usuario)) {
                // Autenticacion por ticket
                debug("Autenticacion desde Carpeta: " + passwd);
                // Validamos ticket
                usuarioAutenticadoInfo = securityService
                        .validarTicketCarpetaCiudadana(sesionInfo, passwd);
            } else if (ConstantesSeguridad.TICKET_USER_CLAVE.equals(usuario)) {
                // Autenticacion por ticket
                debug("Autenticacion por Clave: " + passwd);
                // Validamos ticket
                usuarioAutenticadoInfo = securityService
                        .validarTicketAutenticacion(sesionInfo, passwd);
            } else if (ConstantesSeguridad.TICKET_USER_GF.equals(usuario)) {
                // Autenticacion por ticket
                debug("Autenticacion desde Gestor Formularios: " + passwd);
                // Validamos ticket
                usuarioAutenticadoInfo = securityService
                        .validarTicketFormularioExterno(sesionInfo, passwd);
            } else if (ConstantesSeguridad.TICKET_USER_PAGO.equals(usuario)) {
                // Autenticacion por ticket
                debug("Autenticacion desde Pasarela Pagos: " + passwd);
                // Validamos ticket
                usuarioAutenticadoInfo = securityService
                        .validarTicketPasarelaPagos(sesionInfo, passwd);
            } else {
                throw new BadCredentialsException(
                        "Usuario no valido: " + usuario);
            }
        } catch (final Exception ex) {
            // Se genera excepcion y se mostrará el mensaje de la excepción
            throw new BadCredentialsException(ex.getMessage(), ex);
        }

        debug("Retornando usuario autenticado: "
                + usuarioAutenticadoInfo.getUsername());
        final UsuarioAutenticado usu = new UsuarioAutenticado();
        usu.setUsuario(usuarioAutenticadoInfo);
        return new UsernamePasswordAuthenticationToken(usu,
                authentication.getCredentials(), usu.getAuthorities());

    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    /**
     * Traza mensaje.
     *
     * @param message
     *            Mensaje
     */
    private void debug(final String message) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(message);
        }
    }

}
