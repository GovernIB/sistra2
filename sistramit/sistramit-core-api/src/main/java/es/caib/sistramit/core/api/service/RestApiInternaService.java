package es.caib.sistramit.core.api.service;

import java.util.List;

import es.caib.sistramit.core.api.model.system.DetallePagoAuditoria;
import es.caib.sistramit.core.api.model.system.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FicheroAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroEventoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.FiltroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.OUTPerdidaClave;
import es.caib.sistramit.core.api.model.system.PagoAuditoria;
import es.caib.sistramit.core.api.model.system.PersistenciaAuditoria;

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
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return lista de eventos
	 */
	List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda);

	/**
	 * Permite recuperar el numero de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @param pFiltroPaginacion
	 *            filtro paginacion
	 * @return lista de eventos
	 */
	List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Permite recuperar el numero de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltro
	 *            filtro busqueda
	 * @return Numero de eventos
	 */
	Long contarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda);

	/**
	 * Recuperar las claves de tramitacion por area parametrizada por unos filtros
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return resultado: resultado: 1 -> correcto -1 -> se ha excedido el tamaño
	 *         maximo de la consulta lista de las claves
	 */
	OUTPerdidaClave recuperarClaveTramitacionArea(final FiltroPerdidaClave pFiltroBusqueda);

	/**
	 * Recuperar fichero.
	 *
	 * @param pIdFichero
	 *            id fichero
	 * @param pClave
	 *            clave
	 * @return fichero
	 */
	FicheroAuditoria recuperarFichero(final Long pIdFichero, final String pClave);

	/**
	 * Contar pagos area.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return Numero de pagos
	 */
	Long contarPagosArea(FiltroPagoAuditoria pFiltroBusqueda);

	/**
	 * Recuperar pagos area.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @param pFiltroPaginacion
	 *            filtro paginacion
	 * @return lista de pagos
	 */
	List<PagoAuditoria> recuperarPagosArea(FiltroPagoAuditoria pFiltroBusqueda, FiltroPaginacion pFiltroPaginacion);

	/**
	 * Recuperar detalle pago.
	 *
	 * @param pIdPago
	 *            id pago
	 * @return detalle pago auditoria
	 */
	DetallePagoAuditoria recuperarDetallePago(Long pIdPago);

	/**
	 * Contar persistencia area.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return Numero de datos
	 */
	Long contarPersistenciaArea(FiltroPersistenciaAuditoria pFiltroBusqueda);

	/**
	 * Recuperar persistencia area.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @param pFiltroPaginacion
	 *            filtro paginacion
	 * @return lista de datos de persistencia
	 */
	List<PersistenciaAuditoria> recuperarPersistenciaArea(FiltroPersistenciaAuditoria pFiltroBusqueda,
			FiltroPaginacion pFiltroPaginacion);

}
