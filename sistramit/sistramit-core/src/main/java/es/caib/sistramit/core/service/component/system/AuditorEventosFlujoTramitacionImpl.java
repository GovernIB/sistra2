package es.caib.sistramit.core.service.component.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.UserAgentUtil;
import es.caib.sistramit.core.api.exception.ServiceException;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeResultadoRegistro;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.api.model.system.types.TypeParametroEvento;

/**
 * Permite establecer logica personalizada para auditar eventos en la invocacion
 * de funciones.
 *
 * @author Indra
 *
 */
@Component("auditorEventosFlujoTramitacionComponent")
public final class AuditorEventosFlujoTramitacionImpl implements AuditorEventosFlujoTramitacion {

	@Override
	public List<EventoAuditoria> interceptaInvocacion(final String idSesionTramitacion, final String pMetodo,
			final Object[] pArgumentos) {
		// No se requiere
		return new ArrayList<>();
	}

	@Override
	public List<EventoAuditoria> interceptaExcepcion(final String idSesionTramitacion, final String pMetodo,
			final Object[] pArgumentos, final ServiceException pExcepcion) {
		// No se requiere
		return new ArrayList<>();
	}

	@Override
	public List<EventoAuditoria> interceptaRetorno(final String idSesionTramitacion, final String pMetodo,
			final Object[] pArgumentos, final Object pResult) {
		// Auditamos eventos de tramitacion
		final List<EventoAuditoria> eventosFlujoTramitacion = eventoFlujoTramitacion(idSesionTramitacion, pMetodo,
				pArgumentos, pResult);
		return eventosFlujoTramitacion;
	}

	// ----------------------------------------------------------------------------
	// FUNCIONES UTILIDAD
	// ----------------------------------------------------------------------------

	/**
	 * Crea evento.
	 *
	 * @param tipoEvento
	 *            Tipo evento
	 * @param idSesionTramitacion
	 *            idSesionTramitacion
	 * @return evento tramitacion
	 */
	private EventoAuditoria crearEvento(final TypeEvento tipoEvento, final String idSesionTramitacion) {
		final EventoAuditoria evento = new EventoAuditoria();
		evento.setTipoEvento(tipoEvento);
		evento.setFecha(new Date());
		evento.setIdSesionTramitacion(idSesionTramitacion);
		return evento;
	}

	/**
	 * Controla eventos producidos al realizar una operacion sobre el flujo de
	 * tramitacion.
	 *
	 * @param idSesionTramitacion
	 *            idSesionTramitacion
	 * @param pMetodo
	 *            Metodo
	 * @param pArgumentos
	 *            Argumentos
	 * @param pResult
	 *            Resultado
	 * @return Eventos
	 */
	private List<EventoAuditoria> eventoFlujoTramitacion(final String idSesionTramitacion, final String pMetodo,
			final Object[] pArgumentos, final Object pResult) {
		final List<EventoAuditoria> eventosFlujo = new ArrayList<>();

		// Evento iniciar tramite
		if ("iniciarTramite".equals(pMetodo)) {
			// La sesion se crea al finalizar el metodo
			final EventoAuditoria evento = crearEvento(TypeEvento.INICIAR_TRAMITE, (String) pResult);
			final ListaPropiedades propiedadesEvento = new ListaPropiedades();
			final UsuarioAutenticadoInfo user = (UsuarioAutenticadoInfo) pArgumentos[0];
			propiedadesEvento.addPropiedad(TypeParametroEvento.URL_INICIO.toString(), (String) pArgumentos[5]);
			propiedadesEvento.addPropiedad(TypeParametroEvento.SISTEMA_OPERATIVO.toString(),
					UserAgentUtil.getSistemaOperativo(user.getSesionInfo().getUserAgent()));
			propiedadesEvento.addPropiedad(TypeParametroEvento.VERSION_SISTEMA_OPERATIVO.toString(),
					UserAgentUtil.getVersionSistemaOperativo(user.getSesionInfo().getUserAgent()));
			propiedadesEvento.addPropiedad(TypeParametroEvento.NAVEGADOR.toString(),
					UserAgentUtil.getNavegador(user.getSesionInfo().getUserAgent()));
			propiedadesEvento.addPropiedad(TypeParametroEvento.VERSION_NAVEGADOR.toString(),
					UserAgentUtil.getVersionNavegador(user.getSesionInfo().getUserAgent()));
			evento.setPropiedadesEvento(propiedadesEvento);
			eventosFlujo.add(evento);
		}

		// Evento cargar tramite
		if ("cargarTramite".equals(pMetodo)) {
			final EventoAuditoria evento = crearEvento(TypeEvento.CARGAR_TRAMITE, idSesionTramitacion);
			final ListaPropiedades propiedadesEvento = new ListaPropiedades();
			final UsuarioAutenticadoInfo user = (UsuarioAutenticadoInfo) pArgumentos[1];
			propiedadesEvento.addPropiedad(TypeParametroEvento.SISTEMA_OPERATIVO.toString(),
					UserAgentUtil.getSistemaOperativo(user.getSesionInfo().getUserAgent()));
			propiedadesEvento.addPropiedad(TypeParametroEvento.VERSION_SISTEMA_OPERATIVO.toString(),
					UserAgentUtil.getSistemaOperativo(user.getSesionInfo().getUserAgent()));
			propiedadesEvento.addPropiedad(TypeParametroEvento.NAVEGADOR.toString(),
					UserAgentUtil.getSistemaOperativo(user.getSesionInfo().getUserAgent()));
			propiedadesEvento.addPropiedad(TypeParametroEvento.VERSION_NAVEGADOR.toString(),
					UserAgentUtil.getSistemaOperativo(user.getSesionInfo().getUserAgent()));
			evento.setPropiedadesEvento(propiedadesEvento);
			eventosFlujo.add(evento);
		}

		// Evento cancelar tramite
		if ("cancelarTramite".equals(pMetodo)) {
			eventosFlujo.add(crearEvento(TypeEvento.BORRAR_TRAMITE, idSesionTramitacion));
		}

		// Evento producido tras ejecutar un paso
		if ("accionPaso".equals(pMetodo)) {
			final List<EventoAuditoria> eventosAccionPaso = eventoFlujoTramitacionAccionPaso(idSesionTramitacion,
					pArgumentos, pResult);
			eventosFlujo.addAll(eventosAccionPaso);
		}

		// Evento fin tramite (tras ir a paso)
		if ("irAPaso".equals(pMetodo)) {
			// Verificamos si tramite finaliza tras ir a este paso
			if (((ResultadoIrAPaso) pResult).isFinalizadoTrasIrAPaso()) {
				eventosFlujo.add(crearEvento(TypeEvento.FIN_TRAMITE, idSesionTramitacion));
			}
		}

		return eventosFlujo;
	}

	/**
	 * Eventos producidos tras ejecutar un paso.
	 *
	 * @param idSesionTramitacion
	 *            idSesionTramitacion
	 * @param pArgumentos
	 *            Argumentos
	 * @param pResult
	 *            Resultado
	 * @return Eventos
	 */
	private List<EventoAuditoria> eventoFlujoTramitacionAccionPaso(String idSesionTramitacion,
			final Object[] pArgumentos, final Object pResult) {

		final List<EventoAuditoria> eventos = new ArrayList<>();

		final TypeAccionPaso accionPaso = (TypeAccionPaso) pArgumentos[ConstantesNumero.N2];
		final ResultadoAccionPaso respuestaAccionPaso = (ResultadoAccionPaso) pResult;

		// Evento registro tramite
		if (accionPaso instanceof TypeAccionPasoRegistrar
				&& ((TypeAccionPasoRegistrar) accionPaso) == TypeAccionPasoRegistrar.REGISTRAR_TRAMITE) {
			// Comprobamos si se ha conseguido registrar
			final ResultadoRegistrar resReg = ((ResultadoRegistrar) respuestaAccionPaso
					.getParametroRetorno("resultado"));

			if (resReg.getResultado() == TypeResultadoRegistro.CORRECTO) {
				final EventoAuditoria eventoRegistrarTramite = crearEvento(TypeEvento.REGISTRAR_TRAMITE,
						idSesionTramitacion);
				final ListaPropiedades propiedadesEvento = new ListaPropiedades();
				eventoRegistrarTramite.setPropiedadesEvento(propiedadesEvento);
				propiedadesEvento.addPropiedad(TypeParametroEvento.NUMERO_REGISTRO.toString(),
						resReg.getNumeroRegistro());
				eventos.add(eventoRegistrarTramite);
			}
		}

		// Evento fin tramite (tras accion paso)
		if (respuestaAccionPaso.isFinalizadoTrasAccion()) {
			eventos.add(crearEvento(TypeEvento.FIN_TRAMITE, idSesionTramitacion));
		}

		return eventos;
	}

	/**
	 * Obtiene informacion excepcion origen.
	 *
	 * @param pExcepcion
	 *            excepcion origen
	 * @return informacion excepcion origen.
	 */
	private String getInfoExcepcionOrigen(final ServiceException pExcepcion) {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		final String fechaStr = sdf.format(pExcepcion.getFechaExcepcion());
		return fechaStr + " - " + pExcepcion.getClass().getSimpleName();
	}
}
