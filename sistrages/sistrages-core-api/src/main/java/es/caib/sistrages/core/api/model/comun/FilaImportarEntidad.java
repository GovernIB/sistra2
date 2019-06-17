package es.caib.sistrages.core.api.model.comun;

import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarExiste;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;

/**
 * Fila entidad importar.
 *
 * @author Indra
 *
 */
public class FilaImportarEntidad extends FilaImportarBase {

	/** Código dir3. **/
	private String dir3;

	/** Código dir3 actual. **/
	private String dir3Actual;

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
	 * @param dir3 the dir3 to set
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
	 * @param dir3Actual the dir3Actual to set
	 */
	public final void setDir3Actual(final String dir3Actual) {
		this.dir3Actual = dir3Actual;
	}

	/**
	 * Crea un elemento FilaImportarEntidad de tipo IT (Importar Tramite). No puede
	 * salir mal, ya se encarga el área de dar el error si el área pertenece a otra
	 * entidad.
	 *
	 * @param dir3zip
	 * @param dir3actual
	 * @param existeDir3ZIP
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarEntidad crearIT(final String dir3zip, final String dir3actual,
			final boolean existeDir3ZIP, final String mensaje) {
		final FilaImportarEntidad fila = new FilaImportarEntidad();
		fila.setAccion(TypeImportarAccion.NADA);
		fila.setDir3(dir3zip);
		fila.setDir3Actual(dir3actual);
		if (existeDir3ZIP) {
			fila.setExiste(TypeImportarExiste.EXISTE);
		} else {
			fila.setExiste(TypeImportarExiste.NO_EXISTE);
		}
		fila.setEstado(TypeImportarEstado.REVISADO);
		fila.setResultado(TypeImportarResultado.INFO);
		fila.setMensaje(mensaje);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarEntidad de tipo CC (Cuaderno Carga). Si es
	 * correcto o no se decide si son iguales los 2 DIR3
	 *
	 * @param dir3zip
	 * @param dir3actual
	 * @param existeDir3ZIP
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarEntidad crearCC(final String dir3zip, final String dir3actual,
			final boolean existeDir3ZIP, final String mensaje) {
		final FilaImportarEntidad fila = new FilaImportarEntidad();
		fila.setDir3(dir3zip);
		fila.setDir3Actual(dir3actual);
		if (existeDir3ZIP) {
			fila.setExiste(TypeImportarExiste.EXISTE);
		} else {
			fila.setExiste(TypeImportarExiste.NO_EXISTE);
		}
		if (dir3zip == null || dir3actual == null || !dir3zip.equals(dir3actual)) {
			fila.setAccion(TypeImportarAccion.NADA);
			fila.setEstado(TypeImportarEstado.ERROR);
			fila.setResultado(TypeImportarResultado.ERROR);
		} else {
			fila.setAccion(TypeImportarAccion.NADA);
			fila.setEstado(TypeImportarEstado.REVISADO);
			fila.setResultado(TypeImportarResultado.INFO);
		}
		fila.setMensaje(mensaje);
		return fila;
	}

}
