package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.DatosTramitacion;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;
import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;
import es.caib.sistramit.core.service.model.flujo.EntregaTramite;
import es.caib.sistramit.core.service.model.flujo.types.TypeEntregaEstado;
import es.caib.sistramit.core.service.repository.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación entrega component (CES2).
 *
 * @author Indra
 *
 */
@Component("entregaComponent")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public final class EntregaComponentImpl implements EntregaComponent {

	/** Entrega DAO. */
	@Autowired
	private EntregaTramiteDao entregaTramiteDaoDao;

	/** Envio remoto component. */
	@Autowired
	private EnvioRemotoComponent envioRemotoComponent;

	/** Debug. */
	// TODO CES2 HACER QUE SEA CONFIGURABLE POR PROPS?
	private boolean debug = true;

	/** Id envío remoto. */
	private final String idEnvioRemoto = "CES2";

	@Override
	public List<EntregaTramite> recuperarInmediatosPendientes() {
		return entregaTramiteDaoDao.recuperarEntregaInmediatosPendientes();
	}

	@Override
	public List<EntregaTramite> recuperarPeriodicosPendientes() {
		return entregaTramiteDaoDao.recuperarEntregaPeriodicosPendientes();
	}

	@Override
	public boolean procesarEntrega(EntregaTramite tramEntrega) {
		boolean entregado = false;
		// Verifica si hay que reintentar
		boolean reintentar = (tramEntrega.getEstado() == TypeEntregaEstado.REINTENTAR_ENTREGA);
		// Revisa si el trámite ya ha sido entregado
		if (tramEntrega.getEstado() == TypeEntregaEstado.ENTREGADO) {
			// No debería llegar aquí, ya que no se debería procesar un trámite ya entregado
			entregado = true;
		}
		// Envío
		if (!entregado && !reintentar) {
			entregado = procesarEntregaEnvio(tramEntrega);
		}
		// Reintento
		if (!entregado && reintentar) {
			entregado = procesarEntregaReintento(tramEntrega);
		}
		// Indica si se ha entregado
		return entregado;
	}

	@Override
	public boolean bloquearEntrega(String idSesionTramitacion) {
		return entregaTramiteDaoDao.bloquearEntrega(idSesionTramitacion);
	}

	@Override
	public void desbloquearEntregasBloqueadas() {
		entregaTramiteDaoDao.desbloquearEntregas();
	}

	/**
	 * Procesa envío de entrega de un trámite.
	 *
	 * @param tramEntrega
	 *            entrega de trámite
	 * @return true si se ha entregado
	 */
	private boolean procesarEntregaEnvio(EntregaTramite tramEntrega) {
		String idSesionRegistro = null;
		AsientoRegistral asiento = null;
		TramiteFinalizado tramiteFinalizado = null;
		TypeEntregaEstado estado = null;
		boolean error = false;
		String errorMsg = null;

		// Recuperar asiento y datos tramite finalizado
		try {
			// Recuperamos asiento
			asiento = entregaTramiteDaoDao.recuperarAsiento(tramEntrega.getIdSesionTramitacion());
			// Recuperamos datos tramite finalizado
			tramiteFinalizado = entregaTramiteDaoDao.recuperarTramiteFinalizado(tramEntrega.getIdSesionTramitacion());
			if (tramiteFinalizado == null) {
				estado = TypeEntregaEstado.ERROR_ENTREGA;
				error = true;
				errorMsg = "Error al recuperar datos trámite finalizado " + tramEntrega.getIdSesionTramitacion() + " : no se ha encontrado";
			}
			// Si es tipo registro, informamos en el asiento el numero/fecha de registro
			if (tramiteFinalizado.getNumeroRegistro() != null) {
				asiento.getDatosOrigen().setNumeroRegistro(tramiteFinalizado.getNumeroRegistro());
				asiento.getDatosOrigen().setFechaEntradaRegistro(tramiteFinalizado.getFechaRegistro());
			}
		} catch (Exception e) {
			estado = TypeEntregaEstado.ERROR_ENTREGA;
			error = true;
			errorMsg = "Error al recuperar entrega " + tramEntrega.getIdSesionTramitacion() + " : " + e.getMessage();
		}


		// Iniciar sesion
		if (!error) {
			try {
				idSesionRegistro = envioRemotoComponent.iniciarSesionEnvio(tramEntrega.getIdEntidad(),
						idEnvioRemoto,
						debug);
			} catch (Exception e) {
				estado = TypeEntregaEstado.ERROR_ENTREGA;
				error = true;
				errorMsg = "Error al iniciar sesión envío " + tramEntrega.getIdSesionTramitacion() + " : " + e.getMessage();
			}
		}
		// Envio
		if (!error) {
			try {
				DatosTramitacion datosTramitacion = new DatosTramitacion();
				datosTramitacion.setIdSesionTramitacion(tramEntrega.getIdSesionTramitacion());
				datosTramitacion.setIdTramite(tramiteFinalizado.getIdTramite());
				datosTramitacion.setVersionTramite(tramiteFinalizado.getVersionTramite());
				datosTramitacion.setIdProcedimiento(tramiteFinalizado.getIdProcedimientoCP());
				datosTramitacion.setIdProcedimientoSIA(tramiteFinalizado.getIdProcedimientoSIA());
				ResultadoRegistrar resReg = envioRemotoComponent.realizarEnvio(tramEntrega.getIdEntidad(),
						idEnvioRemoto,
						tramEntrega.getIdSesionTramitacion(),
						idSesionRegistro,
						datosTramitacion,
						asiento,
						debug);
				switch (resReg.getResultado()) {
					case CORRECTO:
						estado = TypeEntregaEstado.ENTREGADO;
						break;
					case REINTENTAR:
						estado = TypeEntregaEstado.REINTENTAR_ENTREGA;
						error = true;
						errorMsg = "Error al realizar envío " + tramEntrega.getIdSesionTramitacion() + " : se debe reintentar";
						break;
					case ERROR:
						estado = TypeEntregaEstado.ERROR_ENTREGA;
						error = true;
						errorMsg = "Error al realizar envío " + tramEntrega.getIdSesionTramitacion() + " : se debe enviar de nuevo.";
						break;
				}
			} catch (Exception e) {
				estado = TypeEntregaEstado.REINTENTAR_ENTREGA;
				error = true;
				errorMsg = "Error al realizar envío " + tramEntrega.getIdSesionTramitacion() + " : " + e.getMessage();
			}
		}
		// Actualiza estado
		entregaTramiteDaoDao.actualizarEstadoEntrega(tramEntrega.getIdSesionTramitacion(), idSesionRegistro, estado, errorMsg);
		// Indica si se ha entregado
		return (estado == TypeEntregaEstado.ENTREGADO);
	}


	/**
	 * Procesa reintento de entrega de un trámite.
	 *
	 * @param tramEntrega
	 *            entrega de trámite
	 * @return true si se ha entregado
	 */
	private boolean procesarEntregaReintento(EntregaTramite tramEntrega) {
		TypeEntregaEstado estado = null;
		String errorMsg = null;
		try {
			// Reintento
			ResultadoRegistrar resReg = envioRemotoComponent.reintentarEnvio(tramEntrega.getIdEntidad(),
					idEnvioRemoto,
					tramEntrega.getIdSesionEnvio(),
					debug);
			// Evalua estado
			switch (resReg.getResultado()) {
				case CORRECTO:
					estado = TypeEntregaEstado.ENTREGADO;
					break;
				case REINTENTAR:
					estado = TypeEntregaEstado.REINTENTAR_ENTREGA;
					errorMsg = "Error al reintentar envío " + tramEntrega.getIdSesionTramitacion() + " : se debe reintentar de nuevo";
					break;
				case ERROR:
					estado = TypeEntregaEstado.ERROR_ENTREGA;
					errorMsg = "No se ha realizado envío " + tramEntrega.getIdSesionTramitacion() + " : se debe enviar de nuevo";
					break;
			}
		} catch (Exception e) {
			estado = TypeEntregaEstado.REINTENTAR_ENTREGA;
			errorMsg = "Error al reintentar envío " + tramEntrega.getIdSesionTramitacion() + " : " + e.getMessage();
		}
		// Actualiza estado
		entregaTramiteDaoDao.actualizarEstadoEntrega(tramEntrega.getIdSesionTramitacion(), tramEntrega.getIdSesionEnvio(), estado, errorMsg);
		// Indica si se ha entregado
		return (estado == TypeEntregaEstado.ENTREGADO);
	}

}
