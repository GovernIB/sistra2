package es.caib.sistrages.core.api.model.comun;

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;

/**
 * Fila formateador importar.
 *
 * @author Indra
 *
 */
public class FilaImportarFormateador extends FilaImportar {

	/** FormateadorFormulario . **/
	private FormateadorFormulario formateadorFormulario;

	/** FormateadorFormulario actual. **/
	private FormateadorFormulario formateadorFormularioActual;

	/** Constructor básico. **/
	public FilaImportarFormateador() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportarFormateador(final FormateadorFormulario iFormateador,
			final FormateadorFormulario iFormateadorActual, final TypeImportarAccion iAccion,
			final TypeImportarEstado iEstado, final TypeImportarResultado iTypeAccion, final String iMensaje) {
		super();
		this.setFormateadorFormulario(iFormateador);
		this.setFormateadorFormularioActual(iFormateadorActual);
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
		this.mensaje = iMensaje;
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

	/**
	 * @return the formateadorFormularioActual
	 */
	public FormateadorFormulario getFormateadorFormularioActual() {
		return formateadorFormularioActual;
	}

	/**
	 * @param formateadorFormularioActual
	 *            the formateadorFormularioActual to set
	 */
	public void setFormateadorFormularioActual(final FormateadorFormulario formateadorFormularioActual) {
		this.formateadorFormularioActual = formateadorFormularioActual;
	}

}
