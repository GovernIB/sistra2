package es.caib.sistrages.core.api.model.comun;

import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;

/**
 * Fila importar básico.
 *
 * @author Indra
 *
 */
public class FilaImportarTramiteVersion extends FilaImportar {

	/** TramiteVersion. **/
	private TramiteVersion tramiteVersion;

	/** TramiteVersion actual. **/
	private TramiteVersion tramiteVersionActual;

	/** Indica el codigo de oficina. **/
	private String tramiteVersionResultadoOficina;

	/** Indica el codigo de oficina. **/
	private String tramiteVersionResultadoOficinaText;

	/** Indica el codigo de libro. **/
	private String tramiteVersionResultadoLibro;

	/** Indica el codigo de libro. **/
	private String tramiteVersionResultadoLibroText;

	/** Indica el codigo de tipo. **/
	private String tramiteVersionResultadoTipo;

	/** Indica el codigo de tipo. **/
	private String tramiteVersionResultadoTipoText;

	/** Constructor básico. **/
	public FilaImportarTramiteVersion() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportarTramiteVersion(final TypeImportarAccion iAccion, final TypeImportarEstado iEstado,
			final TypeImportarResultado iTypeAccion, final TramiteVersion tramiteVersion,
			final TramiteVersion tramiteVersionActual) {
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
		this.tramiteVersion = tramiteVersion;
		this.tramiteVersionActual = tramiteVersionActual;
		this.tramiteVersionResultadoLibro = tramiteVersion.getPasoRegistrar().getCodigoLibroRegistro();
		this.tramiteVersionResultadoOficina = tramiteVersion.getPasoRegistrar().getCodigoOficinaRegistro();
		this.tramiteVersionResultadoTipo = tramiteVersion.getPasoRegistrar().getCodigoTipoAsunto();
	}

	/**
	 * @return the tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion
	 *            the tramiteVersion to set
	 */
	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * @return the tramiteVersionActual
	 */
	public TramiteVersion getTramiteVersionActual() {
		return tramiteVersionActual;
	}

	/**
	 * @param tramiteVersionActual
	 *            the tramiteVersionActual to set
	 */
	public void setTramiteVersionActual(final TramiteVersion tramiteVersionActual) {
		this.tramiteVersionActual = tramiteVersionActual;
	}

	/**
	 * @return the tramiteVersionResultadoOficina
	 */
	public String getTramiteVersionResultadoOficina() {
		return tramiteVersionResultadoOficina;
	}

	/**
	 * @param tramiteVersionResultadoOficina
	 *            the tramiteVersionResultadoOficina to set
	 */
	public void setTramiteVersionResultadoOficina(final String tramiteVersionResultadoOficina) {
		this.tramiteVersionResultadoOficina = tramiteVersionResultadoOficina;
	}

	/**
	 * @return the tramiteVersionResultadoLibro
	 */
	public String getTramiteVersionResultadoLibro() {
		return tramiteVersionResultadoLibro;
	}

	/**
	 * @param tramiteVersionResultadoLibro
	 *            the tramiteVersionResultadoLibro to set
	 */
	public void setTramiteVersionResultadoLibro(final String tramiteVersionResultadoLibro) {
		this.tramiteVersionResultadoLibro = tramiteVersionResultadoLibro;
	}

	/**
	 * @return the tramiteVersionResultadoTipo
	 */
	public String getTramiteVersionResultadoTipo() {
		return tramiteVersionResultadoTipo;
	}

	/**
	 * @param tramiteVersionResultadoTipo
	 *            the tramiteVersionResultadoTipo to set
	 */
	public void setTramiteVersionResultadoTipo(final String tramiteVersionResultadoTipo) {
		this.tramiteVersionResultadoTipo = tramiteVersionResultadoTipo;
	}

	/**
	 * @return the tramiteVersionResultadoOficinaText
	 */
	public String getTramiteVersionResultadoOficinaText() {
		return tramiteVersionResultadoOficinaText;
	}

	/**
	 * @param tramiteVersionResultadoOficinaText
	 *            the tramiteVersionResultadoOficinaText to set
	 */
	public void setTramiteVersionResultadoOficinaText(final String tramiteVersionResultadoOficinaText) {
		this.tramiteVersionResultadoOficinaText = tramiteVersionResultadoOficinaText;
	}

	/**
	 * @return the tramiteVersionResultadoLibroText
	 */
	public String getTramiteVersionResultadoLibroText() {
		return tramiteVersionResultadoLibroText;
	}

	/**
	 * @param tramiteVersionResultadoLibroText
	 *            the tramiteVersionResultadoLibroText to set
	 */
	public void setTramiteVersionResultadoLibroText(final String tramiteVersionResultadoLibroText) {
		this.tramiteVersionResultadoLibroText = tramiteVersionResultadoLibroText;
	}

	/**
	 * @return the tramiteVersionResultadoTipoText
	 */
	public String getTramiteVersionResultadoTipoText() {
		return tramiteVersionResultadoTipoText;
	}

	/**
	 * @param tramiteVersionResultadoTipoText
	 *            the tramiteVersionResultadoTipoText to set
	 */
	public void setTramiteVersionResultadoTipoText(final String tramiteVersionResultadoTipoText) {
		this.tramiteVersionResultadoTipoText = tramiteVersionResultadoTipoText;
	}

}
