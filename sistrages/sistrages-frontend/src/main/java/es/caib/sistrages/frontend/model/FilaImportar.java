package es.caib.sistrages.frontend.model;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.frontend.model.types.TypeImportarAccion;
import es.caib.sistrages.frontend.model.types.TypeImportarEstado;
import es.caib.sistrages.frontend.model.types.TypeImportarResultado;

/**
 * Fila dominio importar.
 *
 * @author slromero
 *
 */
public class FilaImportar {

	/** Dominio. **/
	private Dominio dominio;

	/** FormateadorFormulario . **/
	private FormateadorFormulario formateadorFormulario;

	/** Accion. **/
	private TypeImportarAccion accion;

	/** Estado. **/
	private TypeImportarEstado estado;

	/** TypeAccion. **/
	private TypeImportarResultado resultado;

	/**
	 * Constructor basico.
	 */
	public FilaImportar() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportar(final Dominio iDominio, final TypeImportarAccion iAccion, final TypeImportarEstado iEstado,
			final TypeImportarResultado iTypeAccion) {
		this.dominio = iDominio;
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
	}

	/**
	 * Constructor.
	 */
	public FilaImportar(final FormateadorFormulario iFormateador, final TypeImportarAccion iAccion,
			final TypeImportarEstado iEstado, final TypeImportarResultado iTypeAccion) {
		this.setFormateadorFormulario(iFormateador);
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
	}

	/**
	 * Constructor.
	 */
	public FilaImportar(final TypeImportarAccion iAccion, final TypeImportarEstado iEstado,
			final TypeImportarResultado iTypeAccion) {
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
	}

	/**
	 * @return the dominio
	 */
	public Dominio getDominio() {
		return dominio;
	}

	/**
	 * @param dominio
	 *            the dominio to set
	 */
	public void setDominio(final Dominio dominio) {
		this.dominio = dominio;
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
