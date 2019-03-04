package es.caib.sistramit.frontend.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

/**
 * Cache para almacenar todas las peticiones en proceso login. Por alguna razón
 * desconocida cuando se habilita CSRF solo se almacenan las peticiones que
 * pasan por el CSRF.
 *
 * @author Indra
 *
 */
public class LoginRequestCache extends HttpSessionRequestCache {

	/** Nombre con el que se almacena el saved request. */
	public static final String SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";

	@Override
	public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
		final DefaultSavedRequest savedRequest = new DefaultSavedRequest(request, new PortResolverImpl());
		request.getSession().setAttribute(SAVED_REQUEST, savedRequest);
	}
}