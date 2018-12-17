package es.caib.sistrahelp.core.service.component;

import java.util.List;

import es.caib.sistrahelp.core.api.model.FicheroAuditoria;
import es.caib.sistrahelp.core.api.model.FicheroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaPago;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.FiltroPerdidaClave;
import es.caib.sistrahelp.core.api.model.FiltroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.ResultadoAuditoriaDetallePago;
import es.caib.sistrahelp.core.api.model.ResultadoAuditoriaPago;
import es.caib.sistrahelp.core.api.model.ResultadoAuditoriaPersistencia;
import es.caib.sistrahelp.core.api.model.ResultadoEventoAuditoria;
import es.caib.sistrahelp.core.api.model.ResultadoPerdidaClave;

/**
 * Acceso a componente SISTRAGES.
 *
 * @author Indra
 *
 */
public interface SistramitApiComponent {

	/**
	 * Obtener auditoria evento.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @param pFiltroPaginacion
	 *            filtro paginacion
	 * @return lista de eventos
	 */
	public ResultadoEventoAuditoria obtenerAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Obtener auditoria tramite.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return resultado perdida clave
	 */
	public ResultadoPerdidaClave obtenerClaveTramitacion(final FiltroPerdidaClave pFiltroBusqueda);

	/**
	 * Obtener auditoria pago.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @param pFiltroPaginacion
	 *            filtro paginacion
	 * @return resultado auditoria pago
	 */
	public ResultadoAuditoriaPago obtenerAuditoriaPago(FiltroAuditoriaPago pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Obtener auditoria detalle pago.
	 *
	 * @param pIdPago
	 *            id pago
	 * @return resultado auditoria detalle pago
	 */
	public ResultadoAuditoriaDetallePago obtenerAuditoriaDetallePago(Long pIdPago);

	/**
	 * Obtener auditoria persistencia.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @param pFiltroPaginacion
	 *            filtro paginacion
	 * @return resultado auditoria persistencia
	 */
	ResultadoAuditoriaPersistencia obtenerAuditoriaPersistencia(FiltroPersistenciaAuditoria pFiltroBusqueda,
			FiltroPaginacion pFiltroPaginacion);

	/**
	 * Obtener auditoria persistencia fichero.
	 *
	 * @param pIdTramite
	 *            id tramite
	 * @return lista de ficheros
	 */
	List<FicheroPersistenciaAuditoria> obtenerAuditoriaPersistenciaFichero(Long pIdTramite);

	/**
	 * Obtener auditoria fichero.
	 *
	 * @param pIdFichero
	 *            id. fichero
	 * @param pClave
	 *            clave
	 * @return fichero
	 */
	FicheroAuditoria obtenerAuditoriaFichero(Long pIdFichero, String pClave);

}
