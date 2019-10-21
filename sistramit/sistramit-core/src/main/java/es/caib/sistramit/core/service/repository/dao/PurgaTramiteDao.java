package es.caib.sistramit.core.service.repository.dao;

import java.util.Date;
import java.util.List;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;

/**
 * Interface PurgaTramiteDao.
 *
 * @author Indra
 */
public interface PurgaTramiteDao {

	/**
	 * Borra sesiones formulario si su fecha de de fin esta por debajo de la fecha
	 * limite.
	 *
	 * @param fechaLimite
	 *                        Fecha limite
	 * @return Número de sesiones borradas
	 */
	int purgarSesionesFormularioFinalizadas(final Date fechaLimite);

	/**
	 * Borra sesiones formulario si su fecha de fin es nula y la fecha de inicio
	 * esta por debajo de la fecha limite.
	 *
	 * @param fechaLimite
	 *                        Fecha limite
	 * @return Número de sesiones borradas
	 */
	int purgarSesionesFormularioNoFinalizadas(final Date fechaLimite);

	/**
	 * Purga ficheros marcados para borrar.
	 *
	 * @return Número de ficheros borrados
	 */
	int purgarFicherosHuerfanos();

	/**
	 * Marca para purgar los trámites que se quedan en este estado.
	 *
	 * @param pFinalizadosHasta
	 *                                              Tramites finalizados con fecha
	 *                                              de finalizacion anterior a la
	 *                                              fecha indicada
	 * @param pSinFinalizarHasta
	 *                                              Tramites sin finalizar con fecha
	 *                                              de acceso anterior a la fecha
	 *                                              indicada.
	 * @param pCaducadosHasta
	 *                                              Tramites persistentes caducados
	 *                                              con fecha de caducidad anterior
	 *                                              a la fecha indicada
	 * @param pPendientePurgaPagoRealizadoHasta
	 *                                              Tramites pendientes de purga por
	 *                                              pago realizado con fecha ultima
	 *                                              de acceso anterior a la fecha
	 *                                              indicada
	 * @return Lista con las propiedades: "MARCADOS" - Número de trámites marcados
	 *         para purgar en este proceso / "PENDIENTES" - Número de trámites
	 *         pendientes de purgar tras el proceso / "PENDIENTEPAGOREALIZADO" -
	 *         Número de trámites marcados como pendientes de purga por pago
	 *         realizado / "MARCADOSPAGOREALIZADO" - Número de trámites para purgar
	 *         que estaban pendiente de purga por pago realizado
	 *
	 */
	ListaPropiedades marcarPurgarTramites(Date pFinalizadosHasta, Date pSinCaducidadHasta,
			Date pSinFinalizarHasta, Date pCaducadosHasta, Date pPendientePurgaPagoRealizadoHasta);

	/**
	 * Realiza la purga de los trámites marcados para purgar.
	 *
	 * @return Número de trámites purgados
	 */
	int realizarPurgaTramitesMarcadosParaPurgar();

	/**
	 *
	 * Devuelve los trámites que cumplen ser persistentes con fecha de caducidad
	 * inferior a los días indicados.
	 *
	 * @param numDias
	 *                    Número de días antes de caducar que se avisará.
	 *
	 * @return Trámites pendientes de caducar
	 */
	List<DatosPersistenciaTramite> getTramitesPersistentesPendientesCaducar(int numDias);

	/**
	 * Método que elimina definitivamente los trámites purgados.
	 *
	 * @param pFechaLimitePurga
	 *                              Fecha limite purga
	 *
	 * @return número de trámites purgados eliminados
	 */
	int eliminarTramitesPurgados(Date pFechaLimitePurga);

}