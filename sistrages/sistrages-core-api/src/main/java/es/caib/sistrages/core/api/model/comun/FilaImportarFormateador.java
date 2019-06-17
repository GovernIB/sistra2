package es.caib.sistrages.core.api.model.comun;

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarExiste;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;

/**
 * Fila formateador importar.
 *
 * @author Indra
 *
 */
public class FilaImportarFormateador extends FilaImportarBase {

	/** FormateadorFormulario . **/
	private FormateadorFormulario formateadorFormulario;

	/** FormateadorFormulario actual. **/
	private FormateadorFormulario formateadorFormularioActual;

	/**
	 * Indica si está correcto el formateador. Se utiliza principalmente porque en
	 * Importar Trámite, son todos de tipo info y hay que saber cual se tiene que
	 * quitar.
	 **/
	private boolean correcto;

	/** Constructor básico. **/
	public FilaImportarFormateador() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportarFormateador(final FormateadorFormulario iFormateador,
			final FormateadorFormulario iFormateadorActual, final TypeImportarAccion iAccion,
			final TypeImportarExiste iExiste, final TypeImportarEstado iEstado, final TypeImportarResultado iTypeAccion,
			final String iMensaje, final boolean correcto) {
		super();
		this.setFormateadorFormulario(iFormateador);
		this.setFormateadorFormularioActual(iFormateadorActual);
		this.accion = iAccion;
		this.estado = iEstado;
		this.existe = iExiste;
		this.resultado = iTypeAccion;
		this.mensaje = iMensaje;
		this.setCorrecto(correcto);
	}

	/**
	 * @return the formateadorFormulario
	 */
	public FormateadorFormulario getFormateadorFormulario() {
		return formateadorFormulario;
	}

	/**
	 * @param formateadorFormulario the formateadorFormulario to set
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
	 * @param formateadorFormularioActual the formateadorFormularioActual to set
	 */
	public void setFormateadorFormularioActual(final FormateadorFormulario formateadorFormularioActual) {
		this.formateadorFormularioActual = formateadorFormularioActual;
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

	/**
	 * Crea un elemento FilaImportarFormateador de tipo IT (Importar Tramite) cuando
	 * NO existe el formateador o si existe pero está desactivado la
	 * personalizacion.
	 *
	 * @param formateador
	 * @param formateadorActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarFormateador crearITformateadorError(final FormateadorFormulario formateador,
			final FormateadorFormulario formateadorActual, final String mensaje) {

		TypeImportarExiste existe;
		if (formateadorActual == null) {
			existe = TypeImportarExiste.NO_EXISTE;
		} else {
			existe = TypeImportarExiste.EXISTE;
		}

		return new FilaImportarFormateador(formateador, formateadorActual, TypeImportarAccion.NADA, existe,
				TypeImportarEstado.ERROR, TypeImportarResultado.INFO, mensaje, false);

	}

	/**
	 * Crea un elemento FilaImportarFormateador de tipo IT (Importar Tramite) cuando
	 * existe el formateador
	 *
	 * @param formateador
	 * @param formateadorActual
	 * @param literal
	 * @return
	 */
	public static FilaImportarFormateador creaITformateadorExiste(final FormateadorFormulario formateador,
			final FormateadorFormulario formateadorActual) {
		return new FilaImportarFormateador(formateador, formateadorActual, TypeImportarAccion.REEMPLAZAR,
				TypeImportarExiste.EXISTE, TypeImportarEstado.REVISADO, TypeImportarResultado.INFO, null, true);
	}

	/**
	 * Crea un elemento FilaImportarFormateador de tipo CC (Cuaderno Cargar) cuando
	 * NO existe el formateador o si existe pero está desactivado la
	 * personalizacion.
	 *
	 * @param formateador
	 * @param formateadorActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarFormateador crearCCformateadorError(final FormateadorFormulario formateador,
			final FormateadorFormulario formateadorActual, final String mensaje) {

		TypeImportarExiste existe;
		if (formateadorActual == null) {
			existe = TypeImportarExiste.NO_EXISTE;
		} else {
			existe = TypeImportarExiste.EXISTE;
		}

		return new FilaImportarFormateador(formateador, formateadorActual, TypeImportarAccion.NADA, existe,
				TypeImportarEstado.ERROR, TypeImportarResultado.ERROR, mensaje, false);

	}

	/**
	 * Crea un elemento FilaImportarFormateador de tipo CC (Cuaderno Cargar) cuando
	 * existe el formateador
	 *
	 * @param formateador
	 * @param formateadorActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarFormateador crearCCformateadorOk(final FormateadorFormulario formateador,
			final FormateadorFormulario formateadorActual) {
		return new FilaImportarFormateador(formateador, formateadorActual, TypeImportarAccion.REEMPLAZAR,
				TypeImportarExiste.EXISTE, TypeImportarEstado.REVISADO, TypeImportarResultado.INFO, null, true);
	}

}
