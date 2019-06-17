package es.caib.sistrages.core.api.model.comun;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarExiste;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;

/**
 * Fila importar básico.
 *
 * @author Indra
 *
 */
public class FilaImportarTramiteVersion extends FilaImportarBase {

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

	/** Indica el numero de version cuando se selecciona crear. **/
	private String numVersion;

	/** Versión origen: el identificador y release. **/
	private String origenVersion;
	private String origenRelease;

	/** Versión destino: el identificador y release **/
	private String destinoVersion;
	private String destinoRelease;

	/** Constructor básico. **/
	public FilaImportarTramiteVersion() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportarTramiteVersion(final TypeImportarAccion iAccion, final TypeImportarExiste iExiste,
			final TypeImportarEstado iEstado, final TypeImportarResultado iTypeAccion,
			final TramiteVersion tramiteVersion, final TramiteVersion tramiteVersionActual) {
		this.accion = iAccion;
		this.existe = iExiste;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
		this.tramiteVersion = tramiteVersion;
		this.tramiteVersionActual = tramiteVersionActual;
		this.tramiteVersionResultadoLibro = tramiteVersion.getPasoRegistrar().getCodigoLibroRegistro();
		this.tramiteVersionResultadoOficina = tramiteVersion.getPasoRegistrar().getCodigoOficinaRegistro();
		this.tramiteVersionResultadoTipo = tramiteVersion.getPasoRegistrar().getCodigoTipoAsunto();
		this.numVersion = String.valueOf(tramiteVersion.getNumeroVersion());
		this.origenVersion = String.valueOf(tramiteVersion.getNumeroVersion());
		this.origenRelease = String.valueOf(tramiteVersion.getRelease());
		if (tramiteVersionActual != null) {
			this.destinoVersion = String.valueOf(tramiteVersionActual.getNumeroVersion());
			this.destinoRelease = String.valueOf(tramiteVersionActual.getRelease() + 1);
		}
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
	 * @return the tramiteVersionActual
	 */
	public TramiteVersion getTramiteVersionActual() {
		return tramiteVersionActual;
	}

	/**
	 * @param tramiteVersionActual the tramiteVersionActual to set
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
	 * @param tramiteVersionResultadoOficina the tramiteVersionResultadoOficina to
	 *                                       set
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
	 * @param tramiteVersionResultadoLibro the tramiteVersionResultadoLibro to set
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
	 * @param tramiteVersionResultadoTipo the tramiteVersionResultadoTipo to set
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
	 * @param tramiteVersionResultadoOficinaText the
	 *                                           tramiteVersionResultadoOficinaText
	 *                                           to set
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
	 * @param tramiteVersionResultadoLibroText the tramiteVersionResultadoLibroText
	 *                                         to set
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
	 * @param tramiteVersionResultadoTipoText the tramiteVersionResultadoTipoText to
	 *                                        set
	 */
	public void setTramiteVersionResultadoTipoText(final String tramiteVersionResultadoTipoText) {
		this.tramiteVersionResultadoTipoText = tramiteVersionResultadoTipoText;
	}

	/**
	 * @return the numVersion
	 */
	public String getNumVersion() {
		return numVersion;
	}

	/**
	 * @param numVersion the numVersion to set
	 */
	public void setNumVersion(final String numVersion) {
		this.numVersion = numVersion;
	}

	/**
	 * Indica si se tiene que mostrar el botón (sólo en modo WARNING o OK).
	 *
	 * @return
	 */
	public boolean isMostrarBoton() {
		return this.resultado == TypeImportarResultado.WARNING || this.resultado == TypeImportarResultado.OK;
	}

	/**
	 * @return the origenVersion
	 */
	public final String getOrigenVersion() {
		return origenVersion;
	}

	/**
	 * @param origenVersion the origenVersion to set
	 */
	public final void setOrigenVersion(final String origenVersion) {
		this.origenVersion = origenVersion;
	}

	/**
	 * @return the origenRelease
	 */
	public final String getOrigenRelease() {
		return origenRelease;
	}

	/**
	 * @param origenRelease the origenRelease to set
	 */
	public final void setOrigenRelease(final String origenRelease) {
		this.origenRelease = origenRelease;
	}

	/**
	 * @return the destinoVersion
	 */
	public final String getDestinoVersion() {
		return destinoVersion;
	}

	/**
	 * @param destinoVersion the destinoVersion to set
	 */
	public final void setDestinoVersion(final String destinoVersion) {
		this.destinoVersion = destinoVersion;
	}

	/**
	 * @return the destinoRelease
	 */
	public final String getDestinoRelease() {
		return destinoRelease;
	}

	/**
	 * @param destinoRelease the destinoRelease to set
	 */
	public final void setDestinoRelease(final String destinoRelease) {
		this.destinoRelease = destinoRelease;
	}

	/**
	 * Crea un elemento FilaImportarTramiteVersion de tipo CC (Cuaderno Carga)
	 * cuando no existe la versión
	 *
	 * @param tramiteVersion
	 * @param tramiteVersionActual
	 * @return
	 */
	public static FilaImportarTramiteVersion creaCCversionNoExiste(final TramiteVersion tramiteVersion,
			final TramiteVersion tramiteVersionActual) {
		return new FilaImportarTramiteVersion(TypeImportarAccion.CREAR, TypeImportarExiste.NO_EXISTE,
				TypeImportarEstado.REVISADO, TypeImportarResultado.INFO, tramiteVersion, tramiteVersionActual);
	}

	/**
	 * Crea un elemento FilaImportarTramiteVersion de tipo CC (Cuaderno Carga)
	 * cuando no existe la versión
	 *
	 * @param tramiteVersion
	 * @param tramiteVersionActual
	 * @return
	 */
	public static FilaImportarTramiteVersion creaCCversionExisteReemplazar(final TramiteVersion tramiteVersion,
			final TramiteVersion tramiteVersionActual) {
		return new FilaImportarTramiteVersion(TypeImportarAccion.REEMPLAZAR, TypeImportarExiste.EXISTE,
				TypeImportarEstado.REVISADO, TypeImportarResultado.INFO, tramiteVersion, tramiteVersionActual);
	}

	/**
	 * Crea un elemento FilaImportarTramiteVersion de tipo IT (Importar Tramite)
	 * cuando no ha seleccionado correctamente un trámite.
	 *
	 * @param tramiteVersion
	 * @param tramiteVersionActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarTramiteVersion crearITerrorTramiteSinSeleccionar(final TramiteVersion tramiteVersion,
			final TramiteVersion tramiteVersionActual, final String mensaje) {
		final FilaImportarTramiteVersion fila = new FilaImportarTramiteVersion(TypeImportarAccion.NADA,
				TypeImportarExiste.NO_EXISTE, TypeImportarEstado.ERROR, TypeImportarResultado.ERROR, tramiteVersion,
				tramiteVersionActual);
		fila.setMensaje(mensaje);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarTramiteVersion de tipo IT (Importar Tramite)
	 * cuando se ha seleccionado crear un trámite.
	 *
	 * @param tramiteVersion
	 * @param tramiteVersionActual
	 * @return
	 */
	public static FilaImportarTramiteVersion crearITtramiteNuevo(final TramiteVersion tramiteVersion,
			final TramiteVersion tramiteVersionActual) {
		final FilaImportarTramiteVersion fila = new FilaImportarTramiteVersion(TypeImportarAccion.INCREMENTAR,
				TypeImportarExiste.NO_EXISTE, TypeImportarEstado.REVISADO, TypeImportarResultado.INFO, tramiteVersion,
				tramiteVersionActual);
		final List<TypeImportarAccion> acciones = new ArrayList<>();
		acciones.add(TypeImportarAccion.INCREMENTAR);
		fila.setAccion(TypeImportarAccion.INCREMENTAR);
		fila.setAcciones(acciones);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarTramiteVersion de tipo CC (Cuaderno Carga)
	 * cuando no existe la versión.
	 *
	 * @param tramiteVersion
	 * @param tramiteVersionActual
	 * @return
	 */
	public static FilaImportarTramiteVersion crearCCcrearVersion(final TramiteVersion tramiteVersion,
			final TramiteVersion tramiteVersionActual) {
		final FilaImportarTramiteVersion fila = new FilaImportarTramiteVersion(TypeImportarAccion.CREAR,
				TypeImportarExiste.NO_EXISTE, TypeImportarEstado.REVISADO, TypeImportarResultado.INFO, tramiteVersion,
				tramiteVersionActual);
		fila.setNumVersion(String.valueOf(tramiteVersion.getNumeroVersion()));
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarTramiteVersion de tipo IT (Importar Tramite)
	 * cuando se ha seleccionado seleccionar un trámite.
	 *
	 * @param tramiteVersion
	 * @param tramiteVersionActual
	 * @return
	 */
	public static FilaImportarTramiteVersion crearITtramiteSeleccionado(final TramiteVersion tramiteVersion,
			final TramiteVersion tramiteVersionActual, final List<TramiteVersion> versiones) {
		final FilaImportarTramiteVersion fila = new FilaImportarTramiteVersion(TypeImportarAccion.INCREMENTAR,
				TypeImportarExiste.EXISTE, TypeImportarEstado.REVISADO, TypeImportarResultado.WARNING, tramiteVersion,
				tramiteVersionActual);
		final List<TypeImportarAccion> acciones = new ArrayList<>();
		acciones.add(TypeImportarAccion.INCREMENTAR);
		if (versiones != null && !versiones.isEmpty()) {
			acciones.add(TypeImportarAccion.REEMPLAZAR);
		}
		fila.setAccion(TypeImportarAccion.INCREMENTAR);
		fila.setAcciones(acciones);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarTramiteVersion de tipo CC (Cuaderno Carga)
	 * cuando la versión a importar es demasiado antigua.
	 *
	 * @param tramiteVersion
	 * @param tramiteVersionActual
	 * @param literal
	 * @return
	 */
	public static FilaImportarTramiteVersion crearCCerrorVersionAntiguo(final TramiteVersion tramiteVersion,
			final TramiteVersion tramiteVersionActual, final String literal) {
		final FilaImportarTramiteVersion fila = new FilaImportarTramiteVersion(TypeImportarAccion.REEMPLAZAR,
				TypeImportarExiste.EXISTE, TypeImportarEstado.ERROR, TypeImportarResultado.ERROR, tramiteVersion,
				tramiteVersionActual);
		fila.setMensaje(literal);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarTramiteVersion de tipo CC (Cuaderno Carga)
	 * cuando la versión a importar es más nueva que la actual.
	 *
	 * @param tramiteVersion
	 * @param tramiteVersionActual
	 * @return
	 */
	public static FilaImportarTramiteVersion crearCCversionReemplazar(final TramiteVersion tramiteVersion,
			final TramiteVersion tramiteVersionActual) {
		final FilaImportarTramiteVersion fila = new FilaImportarTramiteVersion(TypeImportarAccion.REEMPLAZAR,
				TypeImportarExiste.EXISTE, TypeImportarEstado.REVISADO, TypeImportarResultado.INFO, tramiteVersion,
				tramiteVersionActual);
		fila.setNumVersion(String.valueOf(tramiteVersion.getNumeroVersion()));
		return fila;
	}

}
