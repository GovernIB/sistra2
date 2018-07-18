package es.caib.sistrages.frontend.model;

import es.caib.sistrages.frontend.model.types.TypeImportarAccion;
import es.caib.sistrages.frontend.model.types.TypeImportarEstado;
import es.caib.sistrages.frontend.model.types.TypeImportarResultado;

/**
 * Fila importar básico.
 *
 * @author slromero
 *
 */
public class FilaImportar {

	/** Accion. **/
	protected TypeImportarAccion accion;

	/** Estado. **/
	protected TypeImportarEstado estado;

	/** TypeAccion. **/
	protected TypeImportarResultado resultado;

	/** Constructor básico. **/
	public FilaImportar() {
		super();
	}

	/**
	 * @return the accion
	 */
	public TypeImportarAccion getAccion() {
		return accion;
	}

	/**
	 * @param accion
	 *            the accion to set
	 */
	public void setAccion(final TypeImportarAccion accion) {
		this.accion = accion;
	}

	/**
	 * @return the estado
	 */
	public TypeImportarEstado getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(final TypeImportarEstado estado) {
		this.estado = estado;
	}

	/**
	 * @return the resultado
	 */
	public TypeImportarResultado getResultado() {
		return resultado;
	}

	/**
	 * @param resultado
	 *            the resultado to set
	 */
	public void setResultado(final TypeImportarResultado typeAccion) {
		this.resultado = typeAccion;
	}

}
