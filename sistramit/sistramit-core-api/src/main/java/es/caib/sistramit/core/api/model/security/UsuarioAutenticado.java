package es.caib.sistramit.core.api.model.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;

/**
 * Usuario autenticado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class UsuarioAutenticado implements UserDetails {

	/**
	 * Tipo autenticacion.
	 */
	private TypeAutenticacion autenticacion;

	/**
	 * Metodo Autenticacion.
	 */
	private TypeMetodoAutenticacion metodoAutenticacion;

	/**
	 * Código usuario.
	 */
	private String username;

	/**
	 * Nombre usuario / Razon social si es una empresa.
	 */
	private String nombre;

	/**
	 * Apellido 1 usuario.
	 */
	private String apellido1;

	/**
	 * Apellido 2 usuario.
	 */
	private String apellido2;

	/**
	 * Nif usuario.
	 */
	private String nif;

	/**
	 * Email usuario.
	 */
	private String email;

	/**
	 * Roles del usuario.
	 *
	 */
	private Collection<GrantedAuthority> authorities;

	/** Información sesión web. */
	private SesionInfo sesionInfo;

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
		// TODO PASSWD NULL?
		return "";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		return username;
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
	 * Método de acceso a nif usuario autenticado.
	 *
	 * @return nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Método para establecer nif.
	 *
	 * @param pNif
	 *            nif a establecer
	 */
	public void setNif(final String pNif) {
		nif = pNif;
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

	/**
	 * Método para establecer userName.
	 *
	 * @param pUserName
	 *            userName a establecer
	 */
	public void setUsername(final String pUserName) {
		username = pUserName;
	}

	/**
	 * Método de acceso a autenticacion.
	 *
	 * @return autenticacion
	 */
	public TypeAutenticacion getAutenticacion() {
		return autenticacion;
	}

	/**
	 * Método para establecer autenticacion.
	 *
	 * @param pAutenticacion
	 *            autenticacion a establecer
	 */
	public void setAutenticacion(final TypeAutenticacion pAutenticacion) {
		autenticacion = pAutenticacion;
	}

	/**
	 * Método de acceso a nombre.
	 *
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método para establecer nombre.
	 *
	 * @param pNombre
	 *            nombre a establecer
	 */
	public void setNombre(final String pNombre) {
		nombre = pNombre;
	}

	/**
	 * Método de acceso a apellido1.
	 *
	 * @return apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * Método para establecer apellido1.
	 *
	 * @param pApellido1
	 *            apellido1 a establecer
	 */
	public void setApellido1(final String pApellido1) {
		apellido1 = pApellido1;
	}

	/**
	 * Método de acceso a apellido2.
	 *
	 * @return apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * Método para establecer apellido2.
	 *
	 * @param pApellido2
	 *            apellido2 a establecer
	 */
	public void setApellido2(final String pApellido2) {
		apellido2 = pApellido2;
	}

	/**
	 * Método de acceso a email.
	 *
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Método para establecer email.
	 *
	 * @param pEmail
	 *            email a establecer
	 */
	public void setEmail(final String pEmail) {
		email = pEmail;
	}

	/**
	 * Obtiene nombre y apellidos.
	 *
	 * @return nombre y apellidos
	 */
	public String getNombreApellidos() {
		final StringBuffer res = new StringBuffer(100);
		if (this.getNombre() != null) {
			res.append(this.getNombre());
		}
		if (this.getApellido1() != null) {
			res.append(" ").append(this.getApellido1());
		}
		if (this.getApellido2() != null) {
			res.append(" ").append(this.getApellido2());
		}
		return res.toString();
	}

	/**
	 * Obtiene apellidos, nombre.
	 *
	 * @return apellidos, nombre
	 */
	public String getApellidosNombre() {
		final StringBuffer res = new StringBuffer(100);
		if (this.getApellido1() == null) {
			res.append(this.getNombre());
		} else {
			res.append(this.getApellido1());
			if (this.getApellido2() != null) {
				res.append(" ").append(this.getApellido2());
			}
			res.append(", ").append(this.getNombre());
		}
		return res.toString();
	}

	/**
	 * Método de acceso a metodoAutenticacion.
	 *
	 * @return metodoAutenticacion
	 */
	public TypeMetodoAutenticacion getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	/**
	 * Método para establecer metodoAutenticacion.
	 *
	 * @param pMetodoAutenticacion
	 *            metodoAutenticacion a establecer
	 */
	public void setMetodoAutenticacion(final TypeMetodoAutenticacion pMetodoAutenticacion) {
		metodoAutenticacion = pMetodoAutenticacion;
	}

	public SesionInfo getSesionInfo() {
		return sesionInfo;
	}

	public void setSesionInfo(final SesionInfo sesionInfo) {
		this.sesionInfo = sesionInfo;
	}

}
