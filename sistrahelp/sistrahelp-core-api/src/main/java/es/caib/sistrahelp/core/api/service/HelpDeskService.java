package es.caib.sistrahelp.core.api.service;

import java.util.List;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaPago;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.FiltroPerdidaClave;
import es.caib.sistrahelp.core.api.model.FiltroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.PagoAuditoria;
import es.caib.sistrahelp.core.api.model.PersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.ResultadoAuditoriaDetallePago;
import es.caib.sistrahelp.core.api.model.ResultadoPerdidaClave;

/**
 * Servicio para auditoria de tramites.
 *
 * @author Indra
 *
 */
public interface HelpDeskService {

	/**
	 * Obtener auditoria evento.
	 *
	 * @return lista de eventos
	 */
	public List<EventoAuditoriaTramitacion> obtenerAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Obtener numero de elementos de auditoria evento.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return numero de elementos
	 */
	public Long countAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda);

	/**
	 * Obtener auditoria tramite.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return resultado perdida clave
	 */
	public ResultadoPerdidaClave obtenerClaveTramitacion(FiltroPerdidaClave pFiltroBusqueda);

	/**
	 * Obtener auditoria pago.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @param pFiltroPaginacion
	 *            filtro paginacion
	 * @return lista de Pagos
	 */
	public List<PagoAuditoria> obtenerAuditoriaPago(FiltroAuditoriaPago pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Obtener numero de elementos de auditoria pago.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return numero de elementos
	 */
	public Long countAuditoriaPago(FiltroAuditoriaPago pFiltroBusqueda);

	/**
	 * Obtener auditoria detalle pago.
	 *
	 * @param pIdPago
	 *            id pago
	 * @return resultado auditoria detalle pago
	 */
	public ResultadoAuditoriaDetallePago obtenerAuditoriaDetallePago(Long pIdPago);

	/**
	 * Contar persistencia area.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return numero de elementos
	 */
	public Long contarPersistenciaArea(final FiltroPersistenciaAuditoria pFiltroBusqueda);

	/**
	 * Recuperar persistencia area.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @param pFiltroPaginacion
	 *            filtro paginacion
	 * @return lista de persistencia
	 */
	public List<PersistenciaAuditoria> recuperarPersistenciaArea(final FiltroPersistenciaAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);
}
