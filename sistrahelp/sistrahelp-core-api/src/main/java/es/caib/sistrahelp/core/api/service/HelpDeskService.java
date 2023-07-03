package es.caib.sistrahelp.core.api.service;

import java.util.List;
import java.util.Map;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FicheroAuditoria;
import es.caib.sistrahelp.core.api.model.FicheroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaPago;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.FiltroPerdidaClave;
import es.caib.sistrahelp.core.api.model.FiltroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.PagoAuditoria;
import es.caib.sistrahelp.core.api.model.PersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.ResultadoAuditoriaDetallePago;
import es.caib.sistrahelp.core.api.model.ResultadoErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.ResultadoEventoCM;
import es.caib.sistrahelp.core.api.model.ResultadoPerdidaClave;
import es.caib.sistrahelp.core.api.model.ResultadoSoporte;
import es.caib.sistrahelp.core.api.model.Soporte;

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
	 * @param pFiltroBusqueda filtro busqueda
	 * @return numero de elementos
	 */
	public Long countAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda);

	/**
	 * Obtener auditoria tramite.
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return resultado perdida clave
	 */
	public ResultadoPerdidaClave obtenerClaveTramitacion(FiltroPerdidaClave pFiltroBusqueda);

	/**
	 * Obtener auditoria pago.
	 *
	 * @param pFiltroBusqueda   filtro busqueda
	 * @param pFiltroPaginacion filtro paginacion
	 * @return lista de Pagos
	 */
	public List<PagoAuditoria> obtenerAuditoriaPago(FiltroAuditoriaPago pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Obtener numero de elementos de auditoria pago.
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return numero de elementos
	 */
	public Long countAuditoriaPago(FiltroAuditoriaPago pFiltroBusqueda);

	/**
	 * Recupera el número de concurrencias de los eventos pago, registro, rellenar
	 * formulario y firma
	 *
	 * @param pFiltro filtro busqueda
	 * @return Numero de eventos
	 */
	public ResultadoEventoCM obtenerCountEventoCM(final FiltroAuditoriaTramitacion pFiltroBusqueda);

	/**
	 * Obtener auditoria detalle pago.
	 *
	 * @param pIdPago id pago
	 * @return resultado auditoria detalle pago
	 */
	public ResultadoAuditoriaDetallePago obtenerAuditoriaDetallePago(Long pIdPago);

	/**
	 * Contar persistencia area.
	 *
	 * @param pFiltroBusqueda filtro busqueda
	 * @return numero de elementos
	 */
	public Long countAuditoriaPersistencia(final FiltroPersistenciaAuditoria pFiltroBusqueda);

	/**
	 * Recuperar persistencia area.
	 *
	 * @param pFiltroBusqueda   filtro busqueda
	 * @param pFiltroPaginacion filtro paginacion
	 * @return lista de persistencia
	 */
	public List<PersistenciaAuditoria> obtenerAuditoriaPersistencia(final FiltroPersistenciaAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion);

	/**
	 * Obtener auditoria persistencia fichero.
	 *
	 * @param pIdTramite id tramite
	 * @return lista de ficheros
	 */
	public List<FicheroPersistenciaAuditoria> obtenerAuditoriaPersistenciaFichero(final Long pIdTramite);

	/**
	 * Obtener auditoria fichero.
	 *
	 * @param pIdFichero id. fichero
	 * @param pClave     clave
	 * @return fichero
	 */
	public FicheroAuditoria obtenerAuditoriaFichero(Long pIdFichero, String pClave);

	ResultadoErroresPorTramiteCM obtenerErroresPorTramiteCM(FiltroAuditoriaTramitacion pFiltroBusqueda,
			FiltroPaginacion pFiltroPaginacion);

	ResultadoEventoCM obtenerErroresPorTramiteCMExpansion(FiltroAuditoriaTramitacion pFiltroBusqueda,
			FiltroPaginacion pFiltroPaginacion);

	ResultadoEventoCM obtenerErroresPlataformaCM(FiltroAuditoriaTramitacion pFiltroBusqueda,
			FiltroPaginacion pFiltroPaginacion);

	ResultadoSoporte obtenerFormularioSoporte(FiltroAuditoriaTramitacion pFiltroBusqueda,
			FiltroPaginacion pFiltroPaginacion);

	void updateFormularioSoporte(Soporte soporte);

	String urlLogoEntidad(String codDir3);

}
