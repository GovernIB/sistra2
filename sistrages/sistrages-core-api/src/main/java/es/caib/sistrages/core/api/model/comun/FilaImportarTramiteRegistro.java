package es.caib.sistrages.core.api.model.comun;

import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarExiste;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;

/**
 * Fila importar tramite registro.
 *
 * @author Indra
 *
 */
public class FilaImportarTramiteRegistro extends FilaImportarBase {

	/** TramiteVersion. **/
	private TramiteVersion tramiteVersion;

	/** Paso registro. **/
	private TramitePaso pasoRegistro;

	/** Indica el codigo de oficina. **/
	private String oficina;

	/** Indica el texto de oficina. **/
	private String oficinaText;

	/** Indica el codigo de libro. **/
	private String libro;

	/** Indica el texto de libro. **/
	private String libroText;

	/** Indica el codigo de tipo. **/
	private String tipo;

	/** Indica el texto de tipo. **/
	private String tipoText;

	/** Mostrar registro. **/
	private boolean mostrarRegistro;

	/** Constructor básico. **/
	public FilaImportarTramiteRegistro() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportarTramiteRegistro(final TypeImportarAccion iAccion, final TypeImportarExiste iExiste,
			final TypeImportarEstado iEstado, final TypeImportarResultado iTypeAccion,
			final TramiteVersion tramiteVersion, final TramitePaso pasoRegistro, final boolean mostrarRegistro) {
		this.accion = iAccion;
		this.existe = iExiste;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
		this.tramiteVersion = tramiteVersion;
		this.pasoRegistro = pasoRegistro;
		this.libro = tramiteVersion.getPasoRegistrar().getCodigoLibroRegistro();
		this.oficina = tramiteVersion.getPasoRegistrar().getCodigoOficinaRegistro();
		this.tipo = tramiteVersion.getPasoRegistrar().getCodigoTipoAsunto();
		this.mostrarRegistro = mostrarRegistro;
	}

	/**
	 * @return the tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion the tramiteVersion to set
	 */
	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * @return the tramiteVersionResultadoOficina
	 */
	public String getOficina() {
		return oficina;
	}

	/**
	 * @param oficina the oficina to set
	 */
	public void setOficina(final String oficina) {
		this.oficina = oficina;
	}

	/**
	 * @return the libro
	 */
	public String getLibro() {
		return libro;
	}

	/**
	 * @param libro the libro to set
	 */
	public void setLibro(final String libro) {
		this.libro = libro;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the oficinaText
	 */
	public String getOficinaText() {
		return oficinaText;
	}

	/**
	 * @param oficinaText the oficinaText to set
	 */
	public void setOficinaText(final String oficinaText) {
		this.oficinaText = oficinaText;
	}

	/**
	 * @return the libroText
	 */
	public String getLibroText() {
		return libroText;
	}

	/**
	 * @param libroText the libroText to set
	 */
	public void setLibroText(final String libroText) {
		this.libroText = libroText;
	}

	/**
	 * @return the tipoText
	 */
	public String getTipoText() {
		return tipoText;
	}

	/**
	 * @return the pasoRegistro
	 */
	public TramitePaso getPasoRegistro() {
		return pasoRegistro;
	}

	/**
	 * @param pasoRegistro the pasoRegistro to set
	 */
	public void setPasoRegistro(final TramitePaso pasoRegistro) {
		this.pasoRegistro = pasoRegistro;
	}

	/**
	 * @return the mostrarRegistro
	 */
	public boolean isMostrarRegistro() {
		return mostrarRegistro;
	}

	/**
	 * @param mostrarRegistro the mostrarRegistro to set
	 */
	public void setMostrarRegistro(final boolean mostrarRegistro) {
		this.mostrarRegistro = mostrarRegistro;
	}

	/**
	 * @param tipoText the tipoText to set
	 */
	public void setTipoText(final String tipoText) {
		this.tipoText = tipoText;
	}

	/**
	 * Crea un elemento FilaImportarTramiteRegistro de tipo IM (Importar Tramite)
	 * cuando NO tiene una acción el trámite.
	 *
	 *
	 * @param tramiteVersion
	 * @param literal
	 * @return
	 */
	public static FilaImportarTramiteRegistro crearITerrorTramiteSinSeleccionar(final TramiteVersion tramiteVersion,
			final String literal) {
		final FilaImportarTramiteRegistro fila = new FilaImportarTramiteRegistro(TypeImportarAccion.NADA,
				TypeImportarExiste.EXISTE, TypeImportarEstado.ERROR, TypeImportarResultado.ERROR, tramiteVersion, null,
				true);
		fila.setMensaje(literal);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarTramiteRegistro de tipo IT (Importar Tramite)
	 * cuando existe un paso de tipo registro
	 *
	 * @param tramiteVersion
	 * @param pasoRegistro
	 * @return
	 ***/
	public static FilaImportarTramiteRegistro creaITconPasoRegistro(final TramiteVersion tramiteVersion,
			final TramitePaso pasoRegistro) {
		return new FilaImportarTramiteRegistro(TypeImportarAccion.REEMPLAZAR, TypeImportarExiste.EXISTE,
				TypeImportarEstado.PENDIENTE, TypeImportarResultado.WARNING, tramiteVersion, pasoRegistro, true);
	}

	/**
	 * Crea un elemento FilaImportarTramiteRegistro de tipo IT (Importar Tramite)
	 * cuando NO existe un paso de tipo registro
	 *
	 * @param tramiteVersion
	 * @return
	 */
	public static FilaImportarTramiteRegistro creaITsinPasoRegistro(final TramiteVersion tramiteVersion) {
		return new FilaImportarTramiteRegistro(TypeImportarAccion.NADA, TypeImportarExiste.EXISTE,
				TypeImportarEstado.REVISADO, TypeImportarResultado.INFO, tramiteVersion, null, false);
	}

	/**
	 * Crea un elemento FilaImportarTramiteRegistro de tipo CC (Cuaderno Carga)
	 * cuando existe un paso de tipo registro
	 *
	 * @param tramiteVersion
	 * @param pasoRegistro
	 * @return
	 ***/
	public static FilaImportarTramiteRegistro creaCCconPasoRegistro(final TramiteVersion tramiteVersion,
			final TramitePaso pasoRegistro) {
		return new FilaImportarTramiteRegistro(TypeImportarAccion.REEMPLAZAR, TypeImportarExiste.EXISTE,
				TypeImportarEstado.PENDIENTE, TypeImportarResultado.WARNING, tramiteVersion, pasoRegistro, true);
	}

	/**
	 * Crea un elemento FilaImportarTramiteRegistro de tipo CC (Cuaderno Carga)
	 * cuando NO existe un paso de tipo registro
	 *
	 * @param tramiteVersion
	 * @return
	 */
	public static FilaImportarTramiteRegistro creaCCsinPasoRegistro(final TramiteVersion tramiteVersion) {
		return new FilaImportarTramiteRegistro(TypeImportarAccion.NADA, TypeImportarExiste.EXISTE,
				TypeImportarEstado.ERROR, TypeImportarResultado.ERROR, tramiteVersion, null, false);
	}

}
