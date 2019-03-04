package es.caib.sistrages.core.api.model.comun;

/**
 * Fila entidad importar.
 *
 * @author Indra
 *
 */
public class FilaImportarEntidad extends FilaImportar {

	/** Código dir3. **/
	private String dir3;

	/** Código dir3 actual. **/
	private String dir3Actual;

	/** Mensaje. **/
	private String mensaje;

	/**
	 * Constructor basico.
	 */
	public FilaImportarEntidad() {
		super();
	}

	/**
	 * @return the dir3
	 */
	public final String getDir3() {
		return dir3;
	}

	/**
	 * @param dir3
	 *            the dir3 to set
	 */
	public final void setDir3(final String dir3) {
		this.dir3 = dir3;
	}

	/**
	 * @return the dir3Actual
	 */
	public final String getDir3Actual() {
		return dir3Actual;
	}

	/**
	 * @param dir3Actual
	 *            the dir3Actual to set
	 */
	public final void setDir3Actual(final String dir3Actual) {
		this.dir3Actual = dir3Actual;
	}

	/**
	 * @return the mensaje
	 */
	public final String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public final void setMensaje(final String mensaje) {
		this.mensaje = mensaje;
	}

}
