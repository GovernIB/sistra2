package es.caib.sistrages.core.api.model.comun;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarExiste;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;

/**
 * Fila importar tramite.
 *
 * @author Indra
 *
 */
public class FilaImportarTramite extends FilaImportarBase {

	/** Tramite. **/
	private Tramite tramite;

	/** Tramite actual. **/
	private Tramite tramiteActual;

	/** Indica el valor de la descripción del tramite. **/
	private String tramiteResultado;

	/** Indica el valor de la descripción del tramite. **/
	private String identificador;

	/** Indica el valor de la descripción del tramite. **/
	private String descripcion;

	/** Trámite origen: el identificador y descripcion. **/
	private String origenIdentificador;
	private String origenDescripcion;

	/** Trámite destino: el identificador y descripcion. **/
	private String destinoIdentificador;
	private String destinoDescripcion;

	/** Constructor básico. **/
	public FilaImportarTramite() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportarTramite(final TypeImportarAccion iAccion, final TypeImportarExiste iExiste,
			final TypeImportarEstado iEstado, final TypeImportarResultado iTypeAccion, final Tramite tramite,
			final Tramite tramiteActual) {
		this.accion = iAccion;
		this.estado = iEstado;
		this.existe = iExiste;
		this.resultado = iTypeAccion;
		this.tramite = tramite;
		this.tramiteActual = tramiteActual;
		this.tramiteResultado = tramite.getDescripcion();
		this.identificador = tramite.getIdentificador();
		this.descripcion = tramite.getDescripcion();
		this.origenIdentificador = tramite.getIdentificador();
		this.origenDescripcion = tramite.getDescripcion();
		if (tramiteActual != null) {
			this.destinoIdentificador = tramiteActual.getIdentificador();
			this.destinoDescripcion = tramiteActual.getDescripcion();
		}
	}

	/**
	 * @return the tramite
	 */
	public Tramite getTramite() {
		return tramite;
	}

	/**
	 * @param tramite the tramite to set
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
	 * @param tramiteActual the tramiteActual to set
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
	 * @param tramiteResultado the tramiteResultado to set
	 */
	public void setTramiteResultado(final String tramiteResultado) {
		this.tramiteResultado = tramiteResultado;
	}

	/**
	 * @return the identificador
	 */
	public final String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public final void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public final String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public final void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the origenIdentificador
	 */
	public final String getOrigenIdentificador() {
		return origenIdentificador;
	}

	/**
	 * @param origenIdentificador the origenIdentificador to set
	 */
	public final void setOrigenIdentificador(final String origenIdentificador) {
		this.origenIdentificador = origenIdentificador;
	}

	/**
	 * @return the origenDescripcion
	 */
	public final String getOrigenDescripcion() {
		return origenDescripcion;
	}

	/**
	 * @param origenDescripcion the origenDescripcion to set
	 */
	public final void setOrigenDescripcion(final String origenDescripcion) {
		this.origenDescripcion = origenDescripcion;
	}

	/**
	 * @return the destinoIdentificador
	 */
	public final String getDestinoIdentificador() {
		return destinoIdentificador;
	}

	/**
	 * @param destinoIdentificador the destinoIdentificador to set
	 */
	public final void setDestinoIdentificador(final String destinoIdentificador) {
		this.destinoIdentificador = destinoIdentificador;
	}

	/**
	 * @return the destinoDescripcion
	 */
	public final String getDestinoDescripcion() {
		return destinoDescripcion;
	}

	/**
	 * @param destinoDescripcion the destinoDescripcion to set
	 */
	public final void setDestinoDescripcion(final String destinoDescripcion) {
		this.destinoDescripcion = destinoDescripcion;
	}

	/**
	 * Crea un elemento FilaImportarTramite de tipo IT (Importar Tramite) cuando no
	 * se ha seleccionado aun un área.
	 *
	 * @param tramite
	 * @param tramiteActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarTramite crearITerrorAreaSinSeleccionar(final Tramite tramite, final Tramite tramiteActual,
			final String mensaje) {
		final FilaImportarTramite filaTramite = new FilaImportarTramite(null, TypeImportarExiste.EXISTE,
				TypeImportarEstado.ERROR, TypeImportarResultado.ERROR, tramite, tramiteActual);

		filaTramite.setMensaje(mensaje);
		return filaTramite;
	}

	/**
	 * Crea un elemento FilaImportarTramite de tipo IT (Importar Tramite) cuando no
	 * se ha confirmado la acción del area.
	 *
	 * @param tramite
	 * @param tramiteActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarTramite crearITerrorAreaSinConfirmar(final Tramite tramite, final Tramite tramiteActual,
			final String mensaje) {
		final FilaImportarTramite filaTramite = new FilaImportarTramite(null, TypeImportarExiste.EXISTE,
				TypeImportarEstado.ERROR, TypeImportarResultado.ERROR, tramite, tramiteActual);
		filaTramite.setMensaje(mensaje);
		return filaTramite;
	}

	/**
	 * Crea un elemento FilaImportarTramite de tipo IT (Importar Tramite) cuando no
	 * existe el tramite actual, y, por tanto, está ok.
	 *
	 * @param tramite
	 * @param tramiteActual
	 * @return
	 */
	public static FilaImportarTramite crearITtramiteNoExiste(final Tramite tramite, final Tramite tramiteActual,
			final FilaImportarArea filaArea) {
		final FilaImportarTramite fila = new FilaImportarTramite(TypeImportarAccion.CREAR, TypeImportarExiste.NO_EXISTE,
				TypeImportarEstado.REVISADO, TypeImportarResultado.INFO, tramite, tramiteActual);
		final List<TypeImportarAccion> acciones = new ArrayList<>();
		acciones.add(TypeImportarAccion.CREAR);
		if (filaArea != null && filaArea.getAccion() != null
				&& filaArea.getAccion() == TypeImportarAccion.SELECCIONAR) {
			acciones.add(TypeImportarAccion.SELECCIONAR);
		}
		fila.setAcciones(acciones);
		fila.setAccion(TypeImportarAccion.CREAR);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarTramite de tipo IT (Importar Tramite) cuando
	 * existe el tramite actual, y, por tanto, está ok.
	 *
	 * @param tramite
	 * @param tramiteActual
	 * @return
	 */
	public static FilaImportarTramite crearITtramiteExiste(final Tramite tramite, final Tramite tramiteActual,
			final FilaImportarArea filaArea) {
		final FilaImportarTramite fila = new FilaImportarTramite(TypeImportarAccion.SELECCIONAR,
				TypeImportarExiste.EXISTE, TypeImportarEstado.PENDIENTE, TypeImportarResultado.WARNING, tramite,
				tramiteActual);
		final List<TypeImportarAccion> acciones = new ArrayList<>();
		acciones.add(TypeImportarAccion.CREAR);
		if (filaArea != null && filaArea.getAccion() != null
				&& filaArea.getAccion() == TypeImportarAccion.SELECCIONAR) {
			acciones.add(TypeImportarAccion.SELECCIONAR);
		}
		fila.setAcciones(acciones);
		fila.setAccion(TypeImportarAccion.SELECCIONAR);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarTramite de tipo CC (Cuaderno Carga) cuando el
	 * area actual y el tramite actual no tienen el mismo area. Es un error.
	 *
	 * @param tramite
	 * @param tramiteActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarTramite crearCCerrorDistintaArea(final Tramite tramite, final Tramite tramiteActual,
			final String mensaje) {
		final FilaImportarTramite fila = new FilaImportarTramite(TypeImportarAccion.NADA, TypeImportarExiste.EXISTE,
				TypeImportarEstado.ERROR, TypeImportarResultado.ERROR, tramite, tramiteActual);
		fila.setMensaje(mensaje);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarTramite de tipo CC (Cuaderno Carga) cuando el
	 * area actual no existe pero el trámite existe y está asociado a otra área.
	 *
	 * @param tramite
	 * @param tramiteActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarTramite crearCCerrorTramiteAreaIncorrecta(final Tramite tramite,
			final Tramite tramiteActual, final String mensaje) {
		final FilaImportarTramite fila = new FilaImportarTramite(TypeImportarAccion.NADA, TypeImportarExiste.EXISTE,
				TypeImportarEstado.ERROR, TypeImportarResultado.ERROR, tramite, tramiteActual);
		fila.setMensaje(mensaje);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarTramite de tipo CC (Cuaderno Carga) cuando no
	 * existe el trámite pero tienes permisos.
	 *
	 * @param tramite
	 * @param tramiteActual
	 * @return
	 **/
	public static FilaImportarTramite crearCCnoExiste(final Tramite tramite, final Tramite tramiteActual) {
		return new FilaImportarTramite(TypeImportarAccion.CREAR, TypeImportarExiste.NO_EXISTE,
				TypeImportarEstado.REVISADO, TypeImportarResultado.INFO, tramite, tramiteActual);
	}

	/**
	 * Crea un elemento FilaImportarTramite de tipo CC (Cuaderno Carga) existe el
	 * trámite. Si coincide la descripcion, se reemplazará y marcará como revisado
	 * mientras que si cambia, se pondrá en warning .
	 *
	 * @param tramite
	 * @param tramiteActual
	 * @return
	 **/
	public static FilaImportarTramite crearCCexiste(final Tramite tramite, final Tramite tramiteActual) {

		final FilaImportarTramite fila;
		if (tramite.getDescripcion().equals(tramiteActual.getDescripcion())) {
			fila = new FilaImportarTramite(TypeImportarAccion.REEMPLAZAR, TypeImportarExiste.EXISTE,
					TypeImportarEstado.REVISADO, TypeImportarResultado.INFO, tramite, tramiteActual);
		} else {
			fila = new FilaImportarTramite(TypeImportarAccion.REEMPLAZAR, TypeImportarExiste.EXISTE,
					TypeImportarEstado.PENDIENTE, TypeImportarResultado.WARNING, tramite, tramiteActual);
		}
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarTramite de tipo CC (Cuaderno Carga) cuando el
	 * tramite no existe y no se tiene permisos.
	 *
	 * @param tramite
	 * @param tramiteActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarTramite crearCCnoExisteSinPermisos(final Tramite tramite, final Tramite tramiteActual,
			final String mensaje) {
		final FilaImportarTramite fila = new FilaImportarTramite(TypeImportarAccion.NADA, TypeImportarExiste.NO_EXISTE,
				TypeImportarEstado.ERROR, TypeImportarResultado.ERROR, tramite, tramiteActual);
		fila.setMensaje(mensaje);
		return fila;
	}

}
