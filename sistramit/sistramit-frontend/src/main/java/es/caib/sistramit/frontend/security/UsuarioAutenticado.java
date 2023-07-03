package es.caib.sistramit.frontend.security;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

/**
 * Usuario autenticado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class UsuarioAutenticado implements UserDetails {

	/**
	 * Info usuario autenticado.
	 */
	private UsuarioAutenticadoInfo usuario;

	/**
	 * Roles del usuario.
	 *
	 */
	private Collection<GrantedAuthority> authorities;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities
	 * ()
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	public String getPassword() {
		return "";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		return usuario.getUsername();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired
	 * ()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked
	 * ()
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Método para establecer authorities.
	 *
	 * @param pAuthorities
	 *            authorities a establecer
	 */
	public void setAuthorities(final Collection<GrantedAuthority> pAuthorities) {
		authorities = pAuthorities;
	}

	public UsuarioAutenticadoInfo getUsuario() {
		return usuario;
	}

	public void setUsuario(final UsuarioAutenticadoInfo usuario) {
		this.usuario = usuario;
	}

}
