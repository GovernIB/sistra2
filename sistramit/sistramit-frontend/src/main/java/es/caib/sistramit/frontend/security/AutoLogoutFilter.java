package es.caib.sistramit.frontend.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;

/**
 * Filtro que fuerza logout automatico si se accede a los puntos de entrada de
 * la aplicación.
 *
 */
public final class AutoLogoutFilter implements Filter {

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		// Controlamos si es un punto de entrada
		final String url = ((HttpServletRequest) request).getRequestURI();
		if (url.contains(ConstantesSeguridad.PUNTOENTRADA_CARGAR_TRAMITE)
				|| url.contains(ConstantesSeguridad.PUNTOENTRADA_INICIAR_TRAMITE)
				|| url.contains(ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_FORMULARIO_EXTERNO)
				|| url.contains(ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_PAGO_EXTERNO)) {

			// Forzamos logout eliminando la sesion
			final HttpSession session = ((HttpServletRequest) request).getSession(false);
			if (session != null) {
				// Comprobamos si venimos del login
				if (session.getAttribute(ConstantesSeguridad.AUTOLOGOUT_FROMLOGIN) != null) {
					// Eliminamos atributo para futuros accesos
					session.removeAttribute(ConstantesSeguridad.AUTOLOGOUT_FROMLOGIN);
				} else {
					// Si no venimos del login cerramos sesion para forzar login
					session.invalidate();
				}
			}
		}

		// Dejamos continuar
		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {
		// No hacemos nada
	}

	@Override
	public void init(final FilterConfig arg0) throws ServletException {
		// No hacemos nada
	}

}
