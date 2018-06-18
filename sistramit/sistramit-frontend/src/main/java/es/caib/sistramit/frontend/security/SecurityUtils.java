package es.caib.sistramit.frontend.security;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utilidades seguridad.
 *
 * @author Indra
 *
 */
public final class SecurityUtils {

    /**
     * Constructor.
     */
    private SecurityUtils() {
        super();
    }

    /**
     * Obtiene usuario autenticado.
     *
     * @return Usuario autenticado
     */
    public static UsuarioAutenticado obtenerUsuarioAutenticado() {
        final UsuarioAutenticado user = (UsuarioAutenticado) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return user;
    }

    /**
     * Realiza logout.
     */
    public static void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
