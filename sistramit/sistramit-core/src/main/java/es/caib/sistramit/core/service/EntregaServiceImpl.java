package es.caib.sistramit.core.service;

import es.caib.sistramit.core.api.model.comun.ResultadoProcesoProgramado;
import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;
import es.caib.sistramit.core.api.service.EntregaService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.integracion.EntregaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.EntregaTramite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación proceso de entrega.
 *
 * @author Indra
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class EntregaServiceImpl implements EntregaService {

	/** Acceso configuración. */
	@Autowired
	private ConfiguracionComponent config;

	/** Componente entrega. */
	@Autowired
	private EntregaComponent entregaComponent;

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(EntregaServiceImpl.class);

	@Override
	@NegocioInterceptor
	public ResultadoProcesoProgramado procesarEnvioRemotoFinalizadosInmediatos() {
		return procesarEntregas(entregaComponent.recuperarInmediatosPendientes());
	}

	@Override
	@NegocioInterceptor
	public ResultadoProcesoProgramado procesarEnvioRemotoFinalizadosPeriodicos() {
		// Desbloquea entregas que se hayan quedado bloqueadas
		entregaComponent.desbloquearEntregasBloqueadas();
		// Recupera trámites finalizados pendientes de entrega
		return procesarEntregas(entregaComponent.recuperarPeriodicosPendientes());
	}

	@Override
	@NegocioInterceptor
	public ResultadoProcesoProgramado procesarFuncionarioHabilitadoFinalizados() {
		// Avisa a funcionario habilitado de los trámites finalizados
		return procesarFinalizadosFH(entregaComponent.recuperarFinalizadosFHPendientes());
	}

	/**
	 * Procesa trámites finalizados para funcionario habilitado.
	 * @param tramiteFinalizados trámites finalizados
	 * @return resultado proceso
	 */
	private ResultadoProcesoProgramado procesarFinalizadosFH(List<TramiteFinalizado> tramiteFinalizados) {
		int avisadosOK = 0;
		int avisadosKO = 0;
		for (TramiteFinalizado tf : tramiteFinalizados) {
			boolean avisado = false;
			try {
				// Realiza aviso
				avisado = entregaComponent.procesarAvisoFuncionarioHabilitado(tf);
			} catch (Exception e) {
				log.error("Error procesando aviso FH trámite finalizado idSesionTramitacion=" + tf.getIdSesionTramitacion(), e);
			}
			// Actualiza contadores
			if (avisado) {
				avisadosOK++;
			} else {
				avisadosKO++;
			}
		}
		// Retorna resultado proceso
		ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();
		res.getDetalles().addPropiedad("pendientes", Integer.toString(tramiteFinalizados.size()));
		res.getDetalles().addPropiedad("avisadosOK", Integer.toString(avisadosOK));
		res.getDetalles().addPropiedad("avisadosKO", Integer.toString(avisadosKO));
		return res;

	}

	/**
	 * Procesa entradas.
	 * @param entregas entregas a procesar
	 * @return
	 */
	private ResultadoProcesoProgramado procesarEntregas(List<EntregaTramite> entregas) {
		// Realiza entrega de trámites finalizados
		int procesadosOK = 0;
		int procesadosKO = 0;
		for (EntregaTramite e : entregas) {
			boolean procesado = false;
			// Bloquea entrega
			if (entregaComponent.bloquearEntrega(e.getIdSesionTramitacion())) {
				// Procesa entrega
				procesado = entregaComponent.procesarEntrega(e);
			}
			// Actualiza contadores
			if (procesado) {
				procesadosOK++;
			} else {
				procesadosKO++;
			}
		}
		// Retorna resultado proceso
		ResultadoProcesoProgramado res = new ResultadoProcesoProgramado();
		res.getDetalles().addPropiedad("pendientes", Integer.toString(entregas.size()));
		res.getDetalles().addPropiedad("procesadasOK", Integer.toString(procesadosOK));
		res.getDetalles().addPropiedad("procesadasKO", Integer.toString(procesadosKO));
		return res;
	}

}
