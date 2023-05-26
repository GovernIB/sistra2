package es.caib.sistramit.core.service.repository.dao;

import java.util.Date;
import java.util.List;

import es.caib.sistramit.core.api.model.flujo.TramiteIniciado;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.DatosFormularioSoporte;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;
import es.caib.sistramit.core.service.model.flujo.EstadoPersistenciaPasoTramite;

/**
 * Interfaz de acceso a base de datos para los datos generales del flujo de
 * tramite. Los datos de cada paso se gestionarán con otro dao.
 *
 * @author Indra
 *
 */
public interface FlujoTramiteDao {

	/**
	 * Crea sesion de tramitacion.
	 *
	 * @return identificador sesion
	 */
	String crearSesionTramitacion();

	/**
	 * Crea un trámite en persistencia (sin los pasos).
	 *
	 * @param dpt
	 *                Datos trámite persistencia.
	 */
	void crearTramitePersistencia(DatosPersistenciaTramite dpt);

	/**
	 * Obtiene datos del trámite en persistencia.
	 *
	 * @param idSesionTramitacion
	 *                                Parámetro id sesion tramitacion
	 * @return Datos de persistencia del trámite (sin los pasos).
	 */
	DatosPersistenciaTramite obtenerTramitePersistencia(String idSesionTramitacion);

	/**
	 * Registra el acceso a un trámite (inicio o carga). Renueva el timestamp de
	 * acceso.
	 *
	 * @param idSesionTramitacion
	 *                                Id sesión tramitación
	 * @param fechaCaducidad
	 *                                Fecha de caducidad del trámite renovada tras
	 *                                el acceso.
	 * @return el date
	 */
	Date registraAccesoTramite(String idSesionTramitacion, Date fechaCaducidad);

	/**
	 * Verifica que no se haya cargado el trámite en otra sesión de tramitación.
	 *
	 * @param idSesionTramitacion
	 *                                Id Sesión Tramitación
	 * @param timestampFlujo
	 *                                Timestamp flujo
	 * @return true si coincide el flujo
	 */
	boolean verificaTimestampFlujo(String idSesionTramitacion, Date timestampFlujo);

	/**
	 * Cambia el estado de un trámite.
	 *
	 * @param idSesionTramitacion
	 *                                Id sesión tramitación
	 * @param estado
	 *                                Estado trámite
	 *
	 */
	void cambiaEstadoTramite(String idSesionTramitacion, TypeEstadoTramite estado);

	/**
	 * Crea un paso asociado al trámite, dejandolo como pendiente de inicializar.
	 *
	 * @param idSesionTramitacion
	 *                                Id sesión tramitación
	 * @param idPaso
	 *                                Id del paso.
	 * @param tipoPaso
	 *                                Tipo de paso.
	 * @param orden
	 *                                Parámetro orden
	 */
	void crearPaso(String idSesionTramitacion, String idPaso, TypePaso tipoPaso, int orden);

	/**
	 * Elimina paso asociado al trámite.
	 *
	 * @param idSesionTramitacion
	 *                                Parámetro id sesion tramitacion
	 * @param idPaso
	 *                                Id del paso.
	 */
	void eliminarPaso(String idSesionTramitacion, String idPaso);

	/**
	 * Elimina pasos asociados al trámite.
	 *
	 * @param idSesionTramitacion
	 *                                Parámetro id sesion tramitacion
	 */
	void eliminarPasos(String idSesionTramitacion);

	/**
	 * Obtiene la lista de pasos del trámite.
	 *
	 * @param idSesionTramitacion
	 *                                Parámetro id sesion tramitacion
	 * @return Lista de pasos con la info: id/tipo/estado
	 */
	List<EstadoPersistenciaPasoTramite> obtenerListaPasos(String idSesionTramitacion);

	/**
	 * Purgar trámite. Elimina los pasos de tramitación y marca como purgado el
	 * trámite.
	 *
	 * @param idSesionTramitacion
	 *                                Parámetro id sesion tramitacion
	 */
	void purgarTramite(String idSesionTramitacion);

	/**
	 * Cancela trámite. Marca el trámite como cancelado.
	 *
	 * @param idSesionTramitacion
	 *                                Parámetro id sesion tramitacion
	 */
	void cancelarTramite(String idSesionTramitacion);

	/**
	 * Obtiene el número de inicios tramitación en el intervalo.
	 *
	 * @param idTramite
	 *                            Id tramite
	 * @param version
	 *                            Version tramite
	 * @param limiteIntervalo
	 *                            Intervalo (minutos)
	 * @param finIntervalo
	 *                            Fin intervalo
	 * @return número de inicios
	 */
	Long contadorLimiteTramitacion(final String idTramite, final int version, int limiteIntervalo,
			final Date finIntervalo);

	/**
	 * Registra formulario soporte.
	 *
	 * @param idSesionTramitacion
	 *                                   Id sesión tramitación
	 * @param datosFormularioSoporte
	 *                                   Datos formulario soporte
	 */
	void registraFormularioSoporte(final String idSesionTramitacion, DatosFormularioSoporte datosFormularioSoporte);

	/**
	 * Obtiene tramitaciones iniciadas del usuario.
	 * @param nif nif
	 * @param tramite Trámite
	 * @param version Versión
	 * @param idTramiteCatalogo Id Trámite Catálogo
	 * @param servicioCatalogo Indica si es servicio
	 * @return Lista tramitaciones iniciadas
	 */
	List<TramiteIniciado> obtenerTramitacionesIniciadas(String nif, String tramite, int version, String idTramiteCatalogo, boolean servicioCatalogo);

}
