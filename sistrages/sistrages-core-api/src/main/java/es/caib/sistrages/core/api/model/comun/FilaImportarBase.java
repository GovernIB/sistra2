package es.caib.sistrages.core.api.model.comun;

import java.util.List;

import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarExiste;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;

/**
 * Fila importar base.
 *
 * @author Indra
 *
 */
public class FilaImportarBase {

	/** Accion. **/
	protected TypeImportarAccion accion;

	/** Acciones que se pueden realizar. **/
	protected List<TypeImportarAccion> acciones;

	/** Estado. **/
	protected TypeImportarEstado estado;

	/** Estado. **/
	protected TypeImportarExiste existe;

	/** TypeAccion. **/
	protected TypeImportarResultado resultado;

	/** Mensaje. **/
	protected String mensaje;

	/** Constructor básico. **/
	public FilaImportarBase() {
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
	public TypeImportarExiste getExiste() {
		return existe;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setExiste(final TypeImportarExiste existe) {
		this.existe = existe;
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

	/**
	 * @return the acciones
	 */
	public List<TypeImportarAccion> getAcciones() {
		return acciones;
	}

	/**
	 * @param acciones
	 *            the acciones to set
	 */
	public void setAcciones(final List<TypeImportarAccion> acciones) {
		this.acciones = acciones;
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
