package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.service.model.flujo.EntregaTramite;

import java.util.Date;
import java.util.List;

/**
 * Acceso a componente Entrega (CES2).
 *
 * @author Indra
 *
 */
public interface EntregaComponent {

	/**
	 * Recupera entregas inmediatas pendientes de procesar.
	 * @return entregas
	 */
	List<EntregaTramite> recuperarInmediatosPendientes();

	/**
	 * Recupera entregas periódicas pendientes de procesar.
	 * @return entregas
	 */
	List<EntregaTramite> recuperarPeriodicosPendientes();

	/**
	 * Procesa entrega de trámite finalizado.
	 * @param tramEntrega trámite a entregar
	 * @return resultado
	 */
	boolean procesarEntrega(EntregaTramite tramEntrega);

	/**
	 * Bloquea entrega antes de procesarla.
	 * @param idSesionTramitacion id de la sesión de tramitación
	 * @return resultado
	 */
	boolean bloquearEntrega(String idSesionTramitacion);

	/**
	 * Desbloquea entregas bloqueadas.
	 */
	void desbloquearEntregasBloqueadas();

}
