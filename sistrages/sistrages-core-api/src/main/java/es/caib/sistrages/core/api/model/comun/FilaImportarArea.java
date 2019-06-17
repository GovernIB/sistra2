package es.caib.sistrages.core.api.model.comun;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarExiste;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;

/**
 * Fila importar area.
 *
 * @author Indra
 *
 */
public class FilaImportarArea extends FilaImportarBase {

	/** Area. **/
	private Area area;

	/** Area actual. **/
	private Area areaActual;

	/** Indica el valor de la descripción del area. **/
	private String areaResultado;

	/** Identificador cuando se crea un area. */
	private String identificador;

	/** Descripcion cuando se crea un area. */
	private String descripcion;

	/** Área origen: el identificador y descripcion. **/
	private String origenIdentificador;
	private String origenDescripcion;

	/** Área destino: el identificador y descripcion. **/
	private String destinoIdentificador;
	private String destinoDescripcion;

	/** Constructor básico. **/
	public FilaImportarArea() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportarArea(final TypeImportarAccion iAccion, final TypeImportarExiste iExiste,
			final TypeImportarEstado iEstado, final TypeImportarResultado iTypeAccion, final Area area,
			final Area areaActual) {
		this.accion = iAccion;
		this.estado = iEstado;
		this.existe = iExiste;
		this.resultado = iTypeAccion;
		this.area = area;
		this.areaActual = areaActual;
		this.areaResultado = area.getDescripcion();
		this.identificador = area.getIdentificador();
		this.descripcion = area.getDescripcion();
		this.origenIdentificador = area.getIdentificador();
		this.origenDescripcion = area.getDescripcion();
		if (areaActual != null) {
			this.destinoIdentificador = areaActual.getIdentificador();
			this.destinoDescripcion = areaActual.getDescripcion();
		}
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(final Area area) {
		this.area = area;
	}

	/**
	 * @return the areaActual
	 */
	public Area getAreaActual() {
		return areaActual;
	}

	/**
	 * @param areaActual the areaActual to set
	 */
	public void setAreaActual(final Area areaActual) {
		this.areaActual = areaActual;
	}

	/**
	 * @return the areaResultado
	 */
	public String getAreaResultado() {
		return areaResultado;
	}

	/**
	 * @param areaResultado the areaResultado to set
	 */
	public void setAreaResultado(final String areaResultado) {
		this.areaResultado = areaResultado;
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(final String descripcion) {
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
	 * Crea un elemento FilaImportarArea de tipo IT (Importar Tramite) cuando no
	 * existe el área.
	 *
	 * @param area
	 * @param areaActual
	 * @return
	 */
	public static FilaImportarArea crearITnoExiste(final Area area, final Area areaActual) {
		return new FilaImportarArea(null, TypeImportarExiste.NO_EXISTE, TypeImportarEstado.PENDIENTE,
				TypeImportarResultado.WARNING, area, areaActual);
	}

	/**
	 * Crea un elemento FilaImportarArea de tipo IT (Importar Tramite) cuando existe
	 * pero es WARNING porque no coincide las entidades del usuario y del área.
	 *
	 * @param area
	 * @param areaActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarArea crearITerrorEntidadIncorrecta(final Area area, final Area areaActual,
			final String mensaje) {
		final FilaImportarArea fila = new FilaImportarArea(null, TypeImportarExiste.EXISTE,
				TypeImportarEstado.PENDIENTE, TypeImportarResultado.WARNING, area, areaActual);
		fila.setMensaje(mensaje);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarArea de tipo CC (Cuaderno Carga) cuando existe
	 * pero es errónea porque no coincide las entidades del usuario y del área.
	 *
	 * @param area
	 * @param areaActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarArea crearCCerrorEntidadIncorrecta(final Area area, final Area areaActual,
			final String mensaje) {
		final FilaImportarArea fila = new FilaImportarArea(null, TypeImportarExiste.EXISTE, TypeImportarEstado.ERROR,
				TypeImportarResultado.ERROR, area, areaActual);
		fila.setMensaje(mensaje);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarArea de tipo IT (Importar Tramite) cuando existe
	 * y la selección es correcta.
	 *
	 * @param area
	 * @param areaActual
	 * @return
	 */
	public static FilaImportarArea crearITseleccionOK(final Area area, final Area areaActual) {
		return new FilaImportarArea(TypeImportarAccion.SELECCIONAR, TypeImportarExiste.EXISTE,
				TypeImportarEstado.REVISADO, TypeImportarResultado.WARNING, area, areaActual);
	}

	/**
	 * Crea un elemento FilaImportarArea de tipo IT (Importar Tramite) cuando existe
	 * y la selección es correcta.
	 *
	 * @param area
	 * @param areaActual
	 * @return
	 */
	public static FilaImportarArea crearCCseleccionOK(final Area area, final Area areaActual) {

		FilaImportarArea fila;
		if (area.getDescripcion().equals(areaActual.getDescripcion())) {
			fila = new FilaImportarArea(TypeImportarAccion.SELECCIONAR, TypeImportarExiste.EXISTE,
					TypeImportarEstado.REVISADO, TypeImportarResultado.INFO, area, areaActual);
		} else {
			fila = new FilaImportarArea(TypeImportarAccion.REEMPLAZAR, TypeImportarExiste.EXISTE,
					TypeImportarEstado.PENDIENTE, TypeImportarResultado.WARNING, area, areaActual);
		}
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarArea de tipo IT (Importar Tramite) cuando existe
	 * pero es errónea porque no tienes permisos de edición sobre el área.
	 *
	 * @param area
	 * @param areaActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarArea crearITerrorSinPermisos(final Area area, final Area areaActual,
			final String mensaje) {
		return crearerrorSinPermisos(area, areaActual, mensaje);
	}

	/**
	 * Crea un elemento FilaImportarArea de tipo CC (Cuaderno Carga) cuando existe
	 * pero es errónea porque no tienes permisos de edición sobre el área.
	 *
	 * @param area
	 * @param areaActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarArea crearCCerrorSinPermisos(final Area area, final Area areaActual,
			final String mensaje) {
		return crearerrorSinPermisos(area, areaActual, mensaje);
	}

	private static FilaImportarArea crearerrorSinPermisos(final Area area, final Area areaActual,
			final String mensaje) {
		final FilaImportarArea filaArea = new FilaImportarArea(null, TypeImportarExiste.EXISTE,
				TypeImportarEstado.ERROR, TypeImportarResultado.ERROR, area, areaActual);
		filaArea.setMensaje(mensaje);
		return filaArea;
	}

	/**
	 * Crea un elemento FilaImportarArea de tipo CC (Cuaderno Carga) cuando no
	 * existe el área.
	 *
	 * @param area
	 * @param areaActual
	 * @return
	 */
	public static FilaImportarArea crearCCnoExisteAdmEntidad(final Area area, final Area areaActual) {
		return new FilaImportarArea(TypeImportarAccion.CREAR, TypeImportarExiste.NO_EXISTE,
				TypeImportarEstado.PENDIENTE, TypeImportarResultado.WARNING, area, areaActual);
	}

	/**
	 * Crea un elemento FilaImportarArea de tipo CC (Cuaderno Carga) cuando no
	 * existe el área.
	 *
	 * @param area
	 * @param areaActual
	 * @return
	 */
	public static FilaImportarArea crearCCerrorNoAdmEntidad(final Area area, final Area areaActual,
			final String mensaje) {
		final FilaImportarArea fila = new FilaImportarArea(null, TypeImportarExiste.NO_EXISTE, TypeImportarEstado.ERROR,
				TypeImportarResultado.ERROR, area, areaActual);
		fila.setMensaje(mensaje);
		return fila;
	}

}
