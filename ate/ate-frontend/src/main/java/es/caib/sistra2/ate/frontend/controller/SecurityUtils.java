package es.caib.sistra2.ate.frontend.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Utilidad para acceder al usuario y roles. Podría meterse en un Spring Bean en la parte web.
 * 
 * @author Indra
 *
 */
public class SecurityUtils {

	public static String getUserName() {
		final ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes();
		final HttpServletRequest request = attr.getRequest();
		return request.getUserPrincipal().getName();
	}
	
	public static boolean hasRole(String role) {
		final ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes();
		final HttpServletRequest request = attr.getRequest();
		return request.isUserInRole(role);
	}
	
}
