package es.caib.sistramit.core.service.repository.dao;

import java.util.List;
import java.util.Map;

import es.caib.sistramit.core.api.model.flujo.types.TypeSoporteEstado;
import es.caib.sistramit.core.api.model.system.rest.externo.Evento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroEvento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramitePersistencia;
import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.TramitePersistencia;
import es.caib.sistramit.core.api.model.system.rest.interno.ErroresPorTramiteCM;
import es.caib.sistramit.core.api.model.system.rest.interno.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.rest.interno.EventoCM;
import es.caib.sistramit.core.api.model.system.rest.interno.FicheroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroEventoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FormularioSoporte;
import es.caib.sistramit.core.api.model.system.rest.interno.PagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.PersistenciaAuditoria;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosFormularioSoporte;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.system.PerdidaClaveFichero;

/**
 * Interfaz de acceso a base de datos para los rest de api (interna / extena).
 *
 * @author Indra
 *
 */
public interface RestApiDao {

	/**
	 * Obtener tramites perdida clave.
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return lista de tramites
	 */
	List<PerdidaClaveFichero> obtenerTramitesPerdidaClave(FiltroPerdidaClave pFiltroBusqueda);

	/**
	 * Obtener numero de tramites perdida clave.
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return Numero de tramites
	 */
	Long countTramitesPerdidaClave(FiltroPerdidaClave pFiltroBusqueda);

	/**
	 * Count pagos.
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return Numero de pagos
	 */
	Long countPagos(FiltroPagoAuditoria pFiltroBusqueda);

	/**
	 * Obtener pagos.
	 *
	 * @param pFiltroBusqueda  filtro busqueda
	 * @param filtroPaginacion filtro paginacion
	 * @return lista de pagos
	 */
	List<PagoAuditoria> obtenerPagos(FiltroPagoAuditoria pFiltroBusqueda, FiltroPaginacion filtroPaginacion);

	/**
	 * Count tramites persistencia.
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return Numero de tramites
	 */
	Long countTramitesPersistencia(FiltroPersistenciaAuditoria pFiltroBusqueda);

	/**
	 * Obtener tramites persistencia.
	 *
	 * @param pFiltroBusqueda  filtro busqueda
	 * @param filtroPaginacion filtro paginacion
	 * @return lista de persistencia
	 */
	List<PersistenciaAuditoria> obtenerTramitesPersistencia(FiltroPersistenciaAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion);

	/**
	 * Recuperar persistencia ficheros.
	 *
	 * @param pIdTramite id tramite
	 * @return lista de ficheros
	 */
	List<FicheroPersistenciaAuditoria> recuperarPersistenciaFicheros(Long pIdTramite);

	/**
	 * Recuperar tramites persistencia.
	 *
	 * @param pFiltro filtro
	 * @return lista de tramites
	 */
	List<TramitePersistencia> recuperarTramitesPersistencia(FiltroTramitePersistencia pFiltro);

	/**
	 * Recuperar tramites finalizados.
	 *
	 * @param pFiltro filtro
	 * @return lista de tramites
	 */
	List<TramiteFinalizado> recuperarTramitesFinalizados(FiltroTramiteFinalizado pFiltro);

	/**
	 * Recupera un fichero almacenado en persistencia.
	 *
	 * @param refFic Parámetro ref fic
	 * @return DatosFicheroPersistencia Datos del fichero.
	 */
	DatosFicheroPersistencia recuperarFicheroPersistencia(ReferenciaFichero pRefFic);

	/**
	 * Recupera un fichero almacenado en persistencia no borrado.
	 *
	 * @param refFic Parámetro ref fic
	 * @return DatosFicheroPersistencia Datos del fichero.
	 */
	DatosFicheroPersistencia recuperarFicheroPersistenciaNoBorrado(ReferenciaFichero pRefFic);

	/**
	 * Obtener documento.
	 *
	 * @param pIdDoc id doc
	 * @return documento paso persistencia
	 */
	DocumentoPasoPersistencia obtenerDocumento(Long pIdDoc);

	/**
	 * Recuperar eventos.
	 *
	 * @param pFiltro filtro
	 * @return lista de eventos
	 */
	List<Evento> recuperarEventos(FiltroEvento pFiltro);

	/**
	 * Permite recuperar la lista de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return lista de eventos
	 */
	List<EventoAuditoriaTramitacion> retrieveByAreas(final FiltroEventoAuditoria pFiltroBusqueda);

	/**
	 * Permite recuperar la lista de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda   filtro busqueda
	 * @param pFiltroPaginacion filtro paginacion
	 * @return lista de eventos
	 */
	List<EventoAuditoriaTramitacion> retrieveByAreas(final FiltroEventoAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Permite recuperar el numero de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return numero de eventos
	 */
	Long countByAreas(final FiltroEventoAuditoria pFiltroBusqueda);

	/**
	 * Recupera el número de concurrencias de los eventos pago, registro, rellenar
	 * formulario y firma
	 *
	 * @param pFiltro filtro busqueda
	 * @return Numero de eventos
	 */
	List<EventoCM> recuperarEventosCM(final FiltroEventoAuditoria pFiltroBusqueda);

	List<ErroresPorTramiteCM> recuperarErroresPorTramiteCM(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion);

	Long contarErroresPorTramiteCM(FiltroEventoAuditoria filtroBusqueda);

	List<EventoCM> recuperarErroresPorTramiteCMExpansion(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion);

	Long contarErroresPorTramiteExpansionCM(FiltroEventoAuditoria pFiltroBusqueda);

	List<EventoCM> recuperarTramitesPorErrorCM(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion);

	Long contarTramitesPorErrorCM(FiltroEventoAuditoria pFiltroBusqueda);

	List<ErroresPorTramiteCM> recuperarTramitesPorErrorCMExpansion(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion);

	Long contarTramitesPorErrorExpansionCM(FiltroEventoAuditoria filtroBusqueda);

	List<EventoCM> recuperarErroresPlataformaCM(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion);

	Long contarErroresPlataformaCM(FiltroEventoAuditoria pFiltroBusqueda);

	/**
	 * Recupera formularios de soporte
	 *
	 * @param pFiltro filtro busqueda
	 * @return Numero de eventos
	 */

	List<FormularioSoporte> recuperarFormularioSoporte(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion);

	Long contarFormularioSoporte(FiltroEventoAuditoria pFiltroBusqueda);

	List<String> listarTiposProblema();

	void updateEstadoIncidencia(Long idSoporte, TypeSoporteEstado estado, String comentarios);

}
