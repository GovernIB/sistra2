package es.caib.sistramit.frontend.security;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(final Authentication authentication) {

		final String name = authentication.getName();
		final String password = authentication.getCredentials().toString();

		// TODO Autenticacion
		return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());

	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
