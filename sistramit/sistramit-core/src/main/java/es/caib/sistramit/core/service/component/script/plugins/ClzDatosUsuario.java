package es.caib.sistramit.core.service.component.script.plugins;

import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.service.model.script.ClzDatosUsuarioInt;

/**
 * Datos usuario.
 *
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ClzDatosUsuario implements ClzDatosUsuarioInt {

	/**
	 * Datos usuario.
	 */
	private final DatosUsuario usuario;

	/**
	 * Constructor.
	 *
	 * @param pusuario
	 *            usuario
	 */
	public ClzDatosUsuario(final DatosUsuario pusuario) {
		super();
		usuario = pusuario;
	}

	@Override
	public String getNif() {
		String res = null;
		if (usuario != null) {
			res = usuario.getNif();
		}
		return res;
	}

	@Override
	public String getNombre() {
		String res = null;
		if (usuario != null) {
			res = usuario.getNombre();
		}
		return res;
	}

	@Override
	public String getApellido1() {
		String res = null;
		if (usuario != null) {
			res = usuario.getApellido1();
		}
		return res;
	}

	@Override
	public String getApellido2() {
		String res = null;
		if (usuario != null) {
			res = usuario.getApellido2();
		}
		return res;
	}

	@Override
	public String getNombreApellidos() {
		String res = null;
		if (usuario != null) {
			res = usuario.getNombreApellidos();
		}
		return res;
	}

	@Override
	public String getApellidosNombre() {
		String res = null;
		if (usuario != null) {
			res = usuario.getApellidosNombre();
		}
		return res;
	}

	@Override
	public String getEmail() {
		String res = null;
		if (usuario != null) {
			res = usuario.getCorreo();
		}
		return res;
	}

	@Override
	public String getApellidos() {
		String res = null;
		if (usuario != null) {
			res = usuario.getApellidos();
		}
		return res;
	}

}
