package es.caib.sistramit.core.api.service;

import java.util.List;
import java.util.Map;

import es.caib.sistramit.core.api.model.flujo.types.TypeSoporteEstado;
import es.caib.sistramit.core.api.model.system.rest.interno.DetallePagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.ErroresPorTramiteCM;
import es.caib.sistramit.core.api.model.system.rest.interno.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.rest.interno.EventoCM;
import es.caib.sistramit.core.api.model.system.rest.interno.FicheroAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FicheroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroEventoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FormularioSoporte;
import es.caib.sistramit.core.api.model.system.rest.interno.OUTPerdidaClave;
import es.caib.sistramit.core.api.model.system.rest.interno.PagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.PersistenciaAuditoria;

/**
 * Servicio funcionalidades Rest Api Interna.
 *
 * @author Indra
 *
 */
public interface RestApiInternaService {

	/**
	 * Permite recuperar el numero de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return lista de eventos
	 */
	List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda);

	/**
	 * Permite recuperar el numero de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda   filtro busqueda
	 * @param pFiltroPaginacion filtro paginacion
	 * @return lista de eventos
	 */
	List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Permite recuperar el numero de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltro filtro busqueda
	 * @return Numero de eventos
	 */
	Long contarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda);

	/**
	 * Recupera el número de concurrencias de los eventos pago, registro, rellenar
	 * formulario y firma
	 *
	 * @param pFiltro filtro busqueda
	 * @return Numero de eventos
	 */
	List<EventoCM> recuperarEventosCM(final FiltroEventoAuditoria pFiltroBusqueda);

	/**
	 * Recuperar las claves de tramitacion por area parametrizada por unos filtros
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return resultado: resultado: 1 -> correcto -1 -> se ha excedido el tamaño
	 *         maximo de la consulta lista de las claves
	 */
	OUTPerdidaClave recuperarClaveTramitacionArea(final FiltroPerdidaClave pFiltroBusqueda);

	/**
	 * Recuperar fichero.
	 *
	 * @param pIdFichero id fichero
	 * @param pClave     clave
	 * @return fichero
	 */
	FicheroAuditoria recuperarFichero(final Long pIdFichero, final String pClave);

	/**
	 * Contar pagos area.
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return Numero de pagos
	 */
	Long contarPagosArea(FiltroPagoAuditoria pFiltroBusqueda);

	/**
	 * Recuperar pagos area.
	 *
	 * @param pFiltroBusqueda   filtro busqueda
	 * @param pFiltroPaginacion filtro paginacion
	 * @return lista de pagos
	 */
	List<PagoAuditoria> recuperarPagosArea(FiltroPagoAuditoria pFiltroBusqueda, FiltroPaginacion pFiltroPaginacion);

	/**
	 * Recuperar detalle pago.
	 *
	 * @param pIdPago id pago
	 * @return detalle pago auditoria
	 */
	DetallePagoAuditoria recuperarDetallePago(Long pIdPago);

	/**
	 * Contar persistencia area.
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return Numero de datos
	 */
	Long contarPersistenciaArea(FiltroPersistenciaAuditoria pFiltroBusqueda);

	/**
	 * Recuperar persistencia area.
	 *
	 * @param pFiltroBusqueda   filtro busqueda
	 * @param pFiltroPaginacion filtro paginacion
	 * @return lista de datos de persistencia
	 */
	List<PersistenciaAuditoria> recuperarPersistenciaArea(FiltroPersistenciaAuditoria pFiltroBusqueda,
			FiltroPaginacion pFiltroPaginacion);

	/**
	 * Recuperar persistencia ficheros.
	 *
	 * @param pIdTramite id. del tramite
	 * @return lista de ficheros
	 */
	List<FicheroPersistenciaAuditoria> recuperarPersistenciaFicheros(Long pIdTramite);

	List<ErroresPorTramiteCM> recuperarErroresPorTramiteCM(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion);

	Long contarErroresPorTramiteCM(FiltroEventoAuditoria filtroBusqueda);

	Long contarErroresPorTramiteExpansionCM(FiltroEventoAuditoria pFiltroBusqueda);

	List<EventoCM> recuperarErroresPorTramiteCMExpansion(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion);

	List<FormularioSoporte> recuperarFormularioSoporte(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion);

	Long contarFormularioSoporte(FiltroEventoAuditoria filtroBusqueda);

	List<EventoCM> recuperarErroresPlataformaCM(FiltroEventoAuditoria filtroBusqueda,
			FiltroPaginacion filtroPaginacion);

	Long contarErroresPlataformaCM(FiltroEventoAuditoria filtroBusqueda);

	void updateEstadoIncidencia(Long idSoporte, TypeSoporteEstado estado, String comentarios);

}
