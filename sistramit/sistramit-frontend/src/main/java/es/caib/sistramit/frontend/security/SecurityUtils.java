package es.caib.sistramit.frontend.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Hex;
import org.springframework.security.core.context.SecurityContextHolder;

import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.model.comun.ConstantesNumero;

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
	 * Genera token CSRF.
	 *
	 * @return Token CSRF.
	 */
	public static String generateCSRFToken() {
		final int size = ConstantesNumero.N40;
		SecureRandom sr = null;
		final byte[] random = new byte[size];
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
		} catch (final NoSuchAlgorithmException e) {
			throw new ErrorFrontException("No se puede generar token CSRF: " + e.getMessage(), e);
		}
		sr.nextBytes(random);
		return new String(Hex.encodeHex(random));
	}
}
