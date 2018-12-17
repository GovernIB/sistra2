package es.caib.sistramit.core.api.model.flujo;

import org.apache.commons.lang3.StringUtils;

/**
 * Datos de usuario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosUsuario implements ModelApi {

	// TODO Meter tipo de identificacion

	/**
	 * Nombre.
	 */
	private String nombre;

	/**
	 * Nif.
	 */
	private String nif;

	/**
	 * Apellido 1.
	 */
	private String apellido1;

	/**
	 * Apellido 2.
	 */
	private String apellido2;

	/**
	 * Email.
	 */
	private String correo;

	/**
	 * Constructor.
	 *
	 * @param pNif
	 *            Nif
	 * @param pNombre
	 *            Nombre
	 * @param pApellido1
	 *            Apellido 1
	 * @param pApellido2
	 *            Apellido 2
	 */
	public DatosUsuario(final String pNif, final String pNombre, final String pApellido1, final String pApellido2) {
		super();
		nif = pNif;
		nombre = pNombre;
		apellido1 = pApellido1;
		apellido2 = pApellido2;
	}

	/**
	 * Constructor.
	 */
	public DatosUsuario() {
		super();
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

	public String getNif() {
		return nif;
	}

	public String getNombre() {
		return nombre;
	}

	/**
	 * Método para establecer nombre usuario.
	 *
	 * @param pNombre
	 *            nombre a establecer
	 */
	public void setNombre(final String pNombre) {
		nombre = pNombre;
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

	public String getNombreApellidos() {
		final StringBuffer res = new StringBuffer(100);
		if (this.getNombre() != null) {
			res.append(this.getNombre());
		}
		if (StringUtils.isNotBlank(this.getApellido1())) {
			res.append(" ").append(this.getApellido1());
		}
		if (StringUtils.isNotBlank(this.getApellido2())) {
			res.append(" ").append(this.getApellido2());
		}
		return res.toString();
	}

	public String getApellidosNombre() {
		final StringBuffer res = new StringBuffer(100);
		if (this.getApellido1() != null) {
			res.append(this.getApellido1());
			if (this.getApellido2() != null) {
				res.append(" ").append(this.getApellido2());
			}
			res.append(", ").append(this.getNombre());
		} else {
			res.append(this.getNombre());
		}
		return res.toString();
	}

	public String getApellidos() {
		final StringBuffer res = new StringBuffer(100);
		if (StringUtils.isNotBlank(this.getApellido1())) {
			res.append(this.getApellido1());
		}
		if (StringUtils.isNotBlank(this.getApellido2())) {
			res.append(" ").append(this.getApellido2());
		}
		return res.toString();
	}

	/**
	 * Método de acceso a email.
	 *
	 * @return email
	 */
	public String getCorreo() {
		return correo;
	}

	public String getApellido1() {
		return apellido1;
	}

	/**
	 * Método para establecer email.
	 *
	 * @param pEmail
	 *            email a establecer
	 */
	public void setCorreo(final String pEmail) {
		correo = pEmail;
	}

	public String print() {
		return getNif() + " - " + getNombreApellidos();
	}

}
