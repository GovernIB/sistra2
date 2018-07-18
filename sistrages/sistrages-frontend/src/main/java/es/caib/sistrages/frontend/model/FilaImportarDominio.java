package es.caib.sistrages.frontend.model;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.frontend.model.types.TypeImportarAccion;
import es.caib.sistrages.frontend.model.types.TypeImportarEstado;
import es.caib.sistrages.frontend.model.types.TypeImportarResultado;

/**
 * Fila dominio importar.
 *
 * @author slromero
 *
 */
public class FilaImportarDominio extends FilaImportar {

	/** Dominio. **/
	private Dominio dominio;

	/** Dominio. **/
	private Dominio dominioActual;

	/** Indica si es visibleBoton o no el boton. **/
	private boolean visibleBoton;

	/**
	 * Constructor basico.
	 */
	public FilaImportarDominio() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportarDominio(final Dominio iDominio, final Dominio iDominioActual, final TypeImportarAccion iAccion,
			final TypeImportarEstado iEstado, final TypeImportarResultado iTypeAccion, final boolean visible) {
		super();
		this.dominio = iDominio;
		this.dominioActual = iDominioActual;
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
		this.visibleBoton = visible;
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
	 * @return the visibleBoton
	 */
	public boolean isVisibleBoton() {
		return visibleBoton;
	}

	/**
	 * @return the visibleBoton
	 */
	public boolean getVisibleBoton() {
		return isVisibleBoton();
	}

	/**
	 * @param visibleBoton
	 *            the visibleBoton to set
	 */
	public void setVisibleBoton(final boolean visibleBoton) {
		this.visibleBoton = visibleBoton;
	}

	/**
	 * @return the dominioActual
	 */
	public Dominio getDominioActual() {
		return dominioActual;
	}

	/**
	 * @param dominioActual
	 *            the dominioActual to set
	 */
	public void setDominioActual(final Dominio dominioActual) {
		this.dominioActual = dominioActual;
	}
}
