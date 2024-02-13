package es.caib.sistramit.core.service.component.system;

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
 * Componente para generar auditoria.
 *
 * @author Indra
 *
 */
public interface RestApiInternaComponent {

	/**
	 * Recuperar clave tramitacion area.
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return OUTPerdidaClave
	 */
	public OUTPerdidaClave recuperarClaveTramitacionArea(final FiltroPerdidaClave pFiltroBusqueda);

	/**
	 * Recuperar fichero.
	 *
	 * @param pIdFichero id fichero
	 * @param pClave     clave
	 * @return fichero
	 */
	public FicheroAuditoria recuperarFichero(final Long pIdFichero, final String pClave);

	/**
	 * Recuperar pagos area.
	 *
	 * @param pFiltroBusqueda   filtro busqueda
	 * @param pFiltroPaginacion the filtro paginacion
	 * @return lista de pagos
	 */
	List<PagoAuditoria> recuperarPagosArea(final FiltroPagoAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Contar pagos area.
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return numero de pagos
	 */
	Long contarPagosArea(final FiltroPagoAuditoria pFiltroBusqueda);

	/**
	 * Recuperar detalle pago.
	 *
	 * @param pIdPago id pago
	 * @return detalle pago auditoria
	 */
	DetallePagoAuditoria recuperarDetallePago(final Long pIdPago);

	/**
	 * Contar persistencia area.
	 *
	 * @param pFiltroBusqueda the filtro busqueda
	 * @return the long
	 */
	public Long contarPersistenciaArea(FiltroPersistenciaAuditoria pFiltroBusqueda);

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
	 * @param pIdTramite id. tramite
	 * @return lista de ficheros
	 */
	public List<FicheroPersistenciaAuditoria> recuperarPersistenciaFicheros(final Long pIdTramite);

	/**
	 * Permite recuperar la lista de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return lista de eventos
	 */
	List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda);

	/**
	 * Permite recuperar la lista de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda   filtro busqueda
	 * @param pFiltroPaginacion filtro paginacion
	 * @return lista de eventos
	 */
	List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Recupera el número de concurrencias de los eventos pago, registro, rellenar
	 * formulario y firma
	 *
	 * @param pFiltro filtro busqueda
	 * @return Numero de eventos
	 */
	List<EventoCM> recuperarEventosCM(final FiltroEventoAuditoria pFiltroBusqueda);

	/**
	 * Permite recuperar el numero de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return numero de eventos
	 */
	Long contarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda);

	Long contarFormularioSoporte(final FiltroEventoAuditoria pFiltroBusqueda);

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

	public List<EventoCM> recuperarErroresPlataformaCM(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion);

	public Long contarErroresPlataformaCM(FiltroEventoAuditoria pFiltroBusqueda);

	List<FormularioSoporte> recuperarFormularioSoporte(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion);

	List<String> listarTiposProblema();

	void updateEstadoIncidencia(Long idSoporte, TypeSoporteEstado estado, String comentarios);

}