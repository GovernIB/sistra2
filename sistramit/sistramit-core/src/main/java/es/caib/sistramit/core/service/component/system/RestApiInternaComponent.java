package es.caib.sistramit.core.service.component.system;

import java.util.List;

import es.caib.sistramit.core.api.model.system.rest.interno.DetallePagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FicheroAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FicheroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPersistenciaAuditoria;
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

	/**
	 * Contar persistencia area.
	 *
	 * @param pFiltroBusqueda
	 *            the filtro busqueda
	 * @return the long
	 */
	public Long contarPersistenciaArea(FiltroPersistenciaAuditoria pFiltroBusqueda);

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

	/**
	 * Recuperar persistencia ficheros.
	 *
	 * @param pIdTramite
	 *            id. tramite
	 * @return lista de ficheros
	 */
	public List<FicheroPersistenciaAuditoria> recuperarPersistenciaFicheros(final Long pIdTramite);

}