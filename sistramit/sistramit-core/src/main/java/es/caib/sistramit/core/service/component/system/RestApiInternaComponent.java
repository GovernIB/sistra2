package es.caib.sistramit.core.service.component.system;

import java.util.List;

import es.caib.sistramit.core.api.model.system.DetallePagoAuditoria;
import es.caib.sistramit.core.api.model.system.FicheroAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.OUTPerdidaClave;
import es.caib.sistramit.core.api.model.system.PagoAuditoria;

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
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return OUTPerdidaClave
	 */
	public OUTPerdidaClave recuperarClaveTramitacionArea(final FiltroPerdidaClave pFiltroBusqueda);

	/**
	 * Recuperar fichero.
	 *
	 * @param pIdFichero
	 *            id fichero
	 * @param pClave
	 *            clave
	 * @return fichero
	 */
	public FicheroAuditoria recuperarFichero(final Long pIdFichero, final String pClave);

	/**
	 * Recuperar pagos area.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @param pFiltroPaginacion
	 *            the filtro paginacion
	 * @return lista de pagos
	 */
	List<PagoAuditoria> recuperarPagosArea(final FiltroPagoAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Contar pagos area.
	 *
	 * @param pFiltroBusqueda
	 *            filtro busqueda
	 * @return numero de pagos
	 */
	Long contarPagosArea(final FiltroPagoAuditoria pFiltroBusqueda);

	/**
	 * Recuperar detalle pago.
	 *
	 * @param pIdPago
	 *            id pago
	 * @return detalle pago auditoria
	 */
	DetallePagoAuditoria recuperarDetallePago(final Long pIdPago);
}