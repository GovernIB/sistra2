package es.caib.sistramit.core.api.model.flujo;

/**
 *
 * Funcionario habilitado.
 *
 * @author Indra
 *
 */
public class FuncionarioHabilitado extends PersonaDesglosado {

	// TODO -- REVISAR: VER SI ES NECESARIO PASAR MAS DATOS (P.E. DIR3)

	/** Username. */
	private String userName;

	/** DIR3 asociado al FH. */
	private String dir3;

	/**
	 * Constructor.
	 */
	public FuncionarioHabilitado() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FuncionarioHabilitado(String userName, String nif, String nombre, String apellido1, String apellido2, String dir3) {
		super(nif, nombre, apellido1, apellido2);
		this.userName = userName;
		this.dir3 = dir3;
	}

	/**
	 * Devuelve userName.
	 * @return the userName
	 */
	public final String getUserName() {
		return userName;
	}

	/**
	 * Establece userName.
	 * @param userName the userName to set
	 */
	public final void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Devuelve dir3.
	 * @return the dir3
	 */
	public final String getDir3() {
		return dir3;
	}

	/**
	 * Establece dir3.
	 * @param dir3 the dir3 to set
	 */
	public final void setDir3(String dir3) {
		this.dir3 = dir3;
	}

	/**
	 * Método para mostrar el contenido de la clase FH.
	 *
	 * @return el string
	 */
	public String print() {
		return "FH [username = " + userName + ", nif = " + getNif() + ", nombre = " + getNombre() + ", apellido1 = " + getApellido1() + ", apellido2 = " + getApellido2() + ", dir3 = " + dir3 + "]";
	}

}
