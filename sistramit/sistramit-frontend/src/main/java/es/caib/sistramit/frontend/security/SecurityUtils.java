package es.caib.sistramit.frontend.security;

import java.net.InetAddress;

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
		final UsuarioAutenticado user = (UsuarioAutenticado) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return user;
	}

	/**
	 * Realiza logout.
	 */
	public static void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	/**
	 * Obtiene instancia jboss.
	 * 
	 * @return nombre nodo JBoss.
	 */
	public static String getJbossNodeName() {
		String jbossNodeName = "";
		try {
			// jbossNodeName = System.getProperty("jboss.node.name");
			// jbossNodeName = (jbossNodeName != null ? jbossNodeName :
			// InetAddress.getLocalHost().getHostName());
			jbossNodeName = InetAddress.getLocalHost().getHostAddress();
			if (jbossNodeName != null && jbossNodeName.lastIndexOf(".") > 0) {
				jbossNodeName = jbossNodeName.substring(jbossNodeName.lastIndexOf(".") + 1);
			}
		} catch (final Exception e) {
			jbossNodeName = "";
		}
		return jbossNodeName;
	}

}
