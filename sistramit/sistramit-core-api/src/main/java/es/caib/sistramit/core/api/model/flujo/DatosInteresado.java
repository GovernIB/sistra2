package es.caib.sistramit.core.api.model.flujo;

/**
 * Datos de usuario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosInteresado extends DatosUsuario {

	/**
	 * Datos contacto.
	 */
	private DatosContacto contacto;

	/**
	 * Método de acceso a contacto.
	 *
	 * @return contacto
	 */
	public DatosContacto getContacto() {
		return contacto;
	}

	/**
	 * Método para establecer contacto.
	 *
	 * @param contacto
	 *                     contacto a establecer
	 */
	public void setContacto(final DatosContacto contacto) {
		this.contacto = contacto;
	}

	/**
	 * Constructor
	 */
	public DatosInteresado() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param pNif
	 *                       Nif
	 * @param pNombre
	 *                       Nombre
	 * @param pApellido1
	 *                       Apellido 1
	 * @param pApellido2
	 *                       Apellido 2
	 */
	public DatosInteresado(final String pNif, final String pNombre, final String pApellido1, final String pApellido2) {
		super(pNif, pNombre, pApellido1, pApellido2);
	}

	/**
	 * Constructor.
	 *
	 * @param pNif
	 *                       Nif
	 * @param pNombre
	 *                       Nombre
	 * @param pApellido1
	 *                       Apellido 1
	 * @param pApellido2
	 *                       Apellido 2
	 * @param pContacto
	 *                       Contacto
	 */
	public DatosInteresado(final String pNif, final String pNombre, final String pApellido1, final String pApellido2,
			final DatosContacto pContacto) {
		super(pNif, pNombre, pApellido1, pApellido2);
		this.contacto = pContacto;
	}

	@Override
	public String print() {
		String res = super.print();
		if (contacto != null) {
			res += " " + contacto.print();
		}
		return res;
	}

}
