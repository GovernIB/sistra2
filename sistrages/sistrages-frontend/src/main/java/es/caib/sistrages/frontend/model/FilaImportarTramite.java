package es.caib.sistrages.frontend.model;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.frontend.model.types.TypeImportarAccion;
import es.caib.sistrages.frontend.model.types.TypeImportarEstado;
import es.caib.sistrages.frontend.model.types.TypeImportarResultado;

/**
 * Fila importar tramite.
 *
 * @author Indra
 *
 */
public class FilaImportarTramite extends FilaImportar {

	/** Tramite. **/
	private Tramite tramite;

	/** Tramite actual. **/
	private Tramite tramiteActual;

	/** Indica el valor de la descripción del tramite. **/
	private String tramiteResultado;

	/** Constructor básico. **/
	public FilaImportarTramite() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportarTramite(final TypeImportarAccion iAccion, final TypeImportarEstado iEstado,
			final TypeImportarResultado iTypeAccion, final Tramite tramite, final Tramite tramiteActual) {
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
		this.tramite = tramite;
		this.tramiteActual = tramiteActual;
		this.tramiteResultado = tramite.getDescripcion();
	}

	/**
	 * @return the tramite
	 */
	public Tramite getTramite() {
		return tramite;
	}

	/**
	 * @param tramite
	 *            the tramite to set
	 */
	public void setTramite(final Tramite tramite) {
		this.tramite = tramite;
	}

	/**
	 * @return the tramiteActual
	 */
	public Tramite getTramiteActual() {
		return tramiteActual;
	}

	/**
	 * @param tramiteActual
	 *            the tramiteActual to set
	 */
	public void setTramiteActual(final Tramite tramiteActual) {
		this.tramiteActual = tramiteActual;
	}

	/**
	 * @return the tramiteResultado
	 */
	public String getTramiteResultado() {
		return tramiteResultado;
	}

	/**
	 * @param tramiteResultado
	 *            the tramiteResultado to set
	 */
	public void setTramiteResultado(final String tramiteResultado) {
		this.tramiteResultado = tramiteResultado;
	}

}
