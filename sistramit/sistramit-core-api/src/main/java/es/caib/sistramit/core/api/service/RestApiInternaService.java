package es.caib.sistramit.core.api.service;

import java.util.List;

import es.caib.sistramit.core.api.model.system.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FiltrosAuditoriaTramitacion;

/**
 * Servicio funcionalidades Rest Api Interna.
 *
 * @author Indra
 *
 */
public interface RestApiInternaService {

	/**
	 * Permite recuperar la lista de eventos internos por areas parametrizada por
	 * unos filtros
	 *
	 * @param pFiltros
	 *            filtros
	 * @return Lista de eventos asociados a la sesión.
	 */
	List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(final FiltrosAuditoriaTramitacion pFiltros);

}
