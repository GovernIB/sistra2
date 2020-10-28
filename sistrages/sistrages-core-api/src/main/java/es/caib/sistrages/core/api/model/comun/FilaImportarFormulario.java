package es.caib.sistrages.core.api.model.comun;

/**
 * Fila formulario importar.
 *
 * @author Indra
 *
 */
public class FilaImportarFormulario extends FilaImportarBase {

	/**
	 * Indica si está correcto el formulario. <br />
	 * No está correcto si es de tipo externo y el formulario gestor externo no
	 * existe.
	 **/
	private boolean correcto;

	/** Constructor básico. **/
	public FilaImportarFormulario() {
		super();
	}

	/**
	 * @return the correcto
	 */
	public boolean isCorrecto() {
		return correcto;
	}

	/**
	 * @param correcto the correcto to set
	 */
	public void setCorrecto(final boolean correcto) {
		this.correcto = correcto;
	}

}
