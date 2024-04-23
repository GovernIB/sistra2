package es.caib.sistramit.core.service.repository.dao;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;
import es.caib.sistramit.core.service.model.flujo.EntregaTramite;
import es.caib.sistramit.core.service.model.flujo.types.TypeEntregaEstado;

import java.util.Date;
import java.util.List;

/**
 * Interface de acceso a base de datos para lógica entrega trámite.
 */
public interface EntregaTramiteDao {

	/**
	 * Crea entrega trámite.
	 *
	 * @param idSesionTramitacion Identificador de la sesión de tramitación.
	 * @param fechaRegistro
	 * @param idEntidad           Identificador de la entidad.
	 * @param asiento             Asiento registral.
	 * @param inmediato           Indica si es inmediato.
	 */
	void crearEntrega(String idSesionTramitacion, Date fechaRegistro, String idEntidad, AsientoRegistral asiento, boolean inmediato);

	/**
	 * Recupera asiento registral para poder realizar entrega.
	 * @param idSesionTramitacion Identificador de la sesión de tramitación.
	 * @return Asiento registral.
	 */
	AsientoRegistral recuperarAsiento(String idSesionTramitacion);

	/**
	 * Recupera entregas inmediatas pendientes.
	 * @return Lista de entregas inmediatas pendientes.
	 */
	List<EntregaTramite> recuperarEntregaInmediatosPendientes();

	/**
	 * Recupera entregas periódicas pendientes.
	 * @return Lista de entregas periódicas pendientes.
	 */
	List<EntregaTramite> recuperarEntregaPeriodicosPendientes();

	/**
	 * Actualiza estado entrega.
	 * @param idSesionTramitacion Identificador de la sesión de tramitación.
	 * @param idSesionEnvio Identificador de la sesión de envío.
	 * @param estado Estado de la entrega.
	 * @param mensajeError Mensaje de error.
	 */
	void actualizarEstadoEntrega(String idSesionTramitacion, String idSesionEnvio, TypeEntregaEstado estado, String mensajeError);

	/**
	 * Bloquea entrega antes de procesarla.
	 * @param idSesionTramitacion Identificador de la sesión de tramitación.
	 * @return true si se puede bloquear.
	 */
	boolean bloquearEntrega(String idSesionTramitacion);

	/**
	 * Desbloquea entregas.
	 */
	void desbloquearEntregas();

	/**
	 * Purga entregas trámite.
	 * @return Número de entregas purgadas.
	 */
    int purgarEntregasTramites();

	/**
	 * Recupera info trámite finalizado.
	 * @param idSesionTramitacion Identificador de la sesión de tramitación.
	 * @return Trámite finalizado.
	 */
    TramiteFinalizado recuperarTramiteFinalizado(String idSesionTramitacion);
}
