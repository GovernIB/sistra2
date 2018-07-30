package es.caib.sistrages.frontend.model;

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.frontend.model.types.TypeImportarAccion;
import es.caib.sistrages.frontend.model.types.TypeImportarEstado;
import es.caib.sistrages.frontend.model.types.TypeImportarResultado;

/**
 * Fila formateador importar.
 *
 * @author Indra
 *
 */
public class FilaImportarFormateador extends FilaImportar {

	/** FormateadorFormulario . **/
	private FormateadorFormulario formateadorFormulario;

	/** Constructor básico. **/
	public FilaImportarFormateador() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportarFormateador(final FormateadorFormulario iFormateador, final TypeImportarAccion iAccion,
			final TypeImportarEstado iEstado, final TypeImportarResultado iTypeAccion) {
		super();
		this.setFormateadorFormulario(iFormateador);
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
	}

	/**
	 * @return the formateadorFormulario
	 */
	public FormateadorFormulario getFormateadorFormulario() {
		return formateadorFormulario;
	}

	/**
	 * @param formateadorFormulario
	 *            the formateadorFormulario to set
	 */
	public void setFormateadorFormulario(final FormateadorFormulario formateadorFormulario) {
		this.formateadorFormulario = formateadorFormulario;
	}
}
