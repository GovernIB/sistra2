package es.caib.sistramit.core.service.component.system;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.UserAgentUtil;
import es.caib.sistramit.core.api.exception.ServiceException;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.*;
import es.caib.sistramit.core.api.model.flujo.types.*;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.api.model.system.types.TypeParametroEvento;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
			final Object[] pArgumentos, final boolean debugEnabled) {
		List<EventoAuditoria> eventosFlujoTramitacion = null;
		// Auditamos invocaciones si debug esta habilitado
		eventosFlujoTramitacion = eventoFlujoTramitacionInvocacion(idSesionTramitacion, pMetodo, pArgumentos,
				debugEnabled);
		return eventosFlujoTramitacion;
	}

	@Override
	public List<EventoAuditoria> interceptaExcepcion(final String idSesionTramitacion, final String pMetodo,
													 final Object[] pArgumentos, final ServiceException pExcepcion,
													 boolean debugEnabled) {
		List<EventoAuditoria> eventosFlujoTramitacion = new ArrayList<>();
		if (pExcepcion.isAuditarExcepcion()) {
			// Auditamos invocaciones si debug esta habilitado
			eventosFlujoTramitacion = eventoFlujoTramitacionExcepcion(idSesionTramitacion, pMetodo, pArgumentos, pExcepcion,
					debugEnabled);
		}
		return eventosFlujoTramitacion;
	}

	@Override
	public List<EventoAuditoria> interceptaRetorno(final String idSesionTramitacion, final String pMetodo,
			final Object[] pArgumentos, final Object pResult, final boolean debugEnabled) {
		// Auditamos eventos de tramitacion
		final List<EventoAuditoria> eventosFlujoTramitacion = eventoFlujoTramitacionRetorno(idSesionTramitacion,
				pMetodo, pArgumentos, pResult);
		return eventosFlujoTramitacion;
	}

	// ----------------------------------------------------------------------------
	// FUNCIONES UTILIDAD
	// ----------------------------------------------------------------------------

	/**
	 * Crea eventos invocacion.
	 *
	 * @param idSesionTramitacion
	 *                                idSesionTramitacion
	 * @param pMetodo
	 *                                Metodo
	 * @param pArgumentos
	 *                                Argumentos
	 * @param debugEnabled
	 *                                Indica si debug está habilitadp
	 * @return Eventos
	 */
	private List<EventoAuditoria> eventoFlujoTramitacionInvocacion(final String idSesionTramitacion,
			final String pMetodo, final Object[] pArgumentos, final boolean debugEnabled) {

		final List<EventoAuditoria> eventosFlujo = new ArrayList<>();

		// Debug pasos tramite (solo si debug habilitado)
		if (debugEnabled) {
			// Evento ir a paso
			if ("irAPaso".equals(pMetodo)) {
				final EventoAuditoria evento = crearEvento(TypeEvento.DEBUG_FLUJO, idSesionTramitacion);
				evento.setDescripcion("irAPaso: " + TypePaso.fromString((String) pArgumentos[1]).name());
				eventosFlujo.add(evento);
			}

			// Evento ir a paso actual
			if ("irAPasoActual".equals(pMetodo)) {
				final EventoAuditoria evento = crearEvento(TypeEvento.DEBUG_FLUJO, idSesionTramitacion);
				evento.setDescripcion("irAPasoActual");
				eventosFlujo.add(evento);
			}

			// Evento accion paso
			if ("accionPaso".equals(pMetodo)) {
				final EventoAuditoria evento = crearEvento(TypeEvento.DEBUG_FLUJO, idSesionTramitacion);
				evento.setDescripcion("accionPaso: " + TypePaso.fromString((String) pArgumentos[1]).name() + " / "
						+ ((TypeAccionPaso) pArgumentos[2]).name());
				eventosFlujo.add(evento);
			}
		}

		// Eventos accion paso
		if ("accionPaso".equals(pMetodo)) {
			final TypeAccionPaso accionPaso = (TypeAccionPaso) pArgumentos[ConstantesNumero.N2];
			final ParametrosAccionPaso parametrosPaso = (ParametrosAccionPaso) pArgumentos[ConstantesNumero.N3];

			// Evento inicio pago
			if (accionPaso instanceof TypeAccionPasoPagar
					&& ((TypeAccionPasoPagar) accionPaso) == TypeAccionPasoPagar.INICIAR_PAGO) {
				final EventoAuditoria eventoFormulario = crearEvento(TypeEvento.PAGO_ELECTRONICO_INICIO,
						idSesionTramitacion);
				eventosFlujo.add(eventoFormulario);
			}

			// Evento inicio registro
			if (accionPaso instanceof TypeAccionPasoRegistrar
					&& ((TypeAccionPasoRegistrar) accionPaso) == TypeAccionPasoRegistrar.INICIAR_SESION_REGISTRO) {
				final EventoAuditoria eventoFormulario = crearEvento(TypeEvento.REGISTRAR_TRAMITE_INICIO,
						idSesionTramitacion);
				eventosFlujo.add(eventoFormulario);
			}

		}

		return eventosFlujo;

	}

	/**
	 * Crea evento.
	 *
	 * @param tipoEvento
	 *                                Tipo evento
	 * @param idSesionTramitacion
	 *                                idSesionTramitacion
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
	 *                                idSesionTramitacion
	 * @param pMetodo
	 *                                Metodo
	 * @param pArgumentos
	 *                                Argumentos
	 * @param pResult
	 *                                Resultado
	 * @return Eventos
	 */
	private List<EventoAuditoria> eventoFlujoTramitacionRetorno(final String idSesionTramitacion, final String pMetodo,
			final Object[] pArgumentos, final Object pResult) {
		final List<EventoAuditoria> eventosFlujo = new ArrayList<>();

		// Evento iniciar tramite
		if ("iniciarTramite".equals(pMetodo)) {
			// La sesion se crea al finalizar el metodo
			final EventoAuditoria evento = crearEvento(TypeEvento.INICIAR_TRAMITE, (String) pResult);
			final ListaPropiedades propiedadesEvento = new ListaPropiedades();
			final UsuarioAutenticadoInfo user = (UsuarioAutenticadoInfo) pArgumentos[0];
			propiedadesEvento.addPropiedad(TypeParametroEvento.URL_INICIO.toString(),
					(String) pArgumentos[ConstantesNumero.N6]);
			addPropsAutenticacion(propiedadesEvento, user);
			addPropsUserAgent(propiedadesEvento, user.getSesionInfo().getUserAgent());
			evento.setPropiedadesEvento(propiedadesEvento);
			eventosFlujo.add(evento);
		}

		// Evento cargar tramite
		if ("cargarTramite".equals(pMetodo)) {
			final EventoAuditoria evento = crearEvento(TypeEvento.CARGAR_TRAMITE, idSesionTramitacion);
			final ListaPropiedades propiedadesEvento = new ListaPropiedades();
			final UsuarioAutenticadoInfo user = (UsuarioAutenticadoInfo) pArgumentos[1];
			addPropsAutenticacion(propiedadesEvento, user);
			addPropsUserAgent(propiedadesEvento, user.getSesionInfo().getUserAgent());
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
	 * Intercepta excepciones producidas en el flujo de tramitacion.
	 * @param idSesionTramitacion Id sesion tramitación
	 * @param pMetodo Metodo
	 * @param pArgumentos Argumentos
	 * @param pExcepcion Excepcion generada
	 * @param debugEnabled Si debug habilitado
	 * @return Eventos generados
	 */
	private List<EventoAuditoria> eventoFlujoTramitacionExcepcion(String idSesionTramitacion, String pMetodo, Object[] pArgumentos, ServiceException pExcepcion, boolean debugEnabled) {

		final List<EventoAuditoria> eventosFlujo = new ArrayList<>();

		/*
			-----  QUITAMOS EVENTOS ESPECIFICOS Y DEJAMOS SOLO ERROR -----

		// Evento de problema de conexion a firma cliente
		if (pExcepcion instanceof SesionFirmaClienteConnectException) {
			EventoAuditoria e = crearEvento(TypeEvento.FIRMA_ERROR_CONEXION, idSesionTramitacion);
			e.setDescripcion(pExcepcion.getMessage());
			eventosFlujo.add(e);
		}

		// Evento de verificación erronea de firmante para anexo firmado
		if (pExcepcion instanceof AnexarFirmadoFirmaIncorrectaException) {
			EventoAuditoria e = crearEvento(TypeEvento.FIRMA_ANEXOFIRMADO_KO, idSesionTramitacion);
			e.setDescripcion(pExcepcion.getMessage());
			eventosFlujo.add(e);
		}

		 */

		return eventosFlujo;

	}


	/**
	 * Añade propiedades autenticación.
	 *
	 * @param propiedadesEvento
	 *                              Propiedades evento
	 * @param user
	 *                              User
	 */
	protected void addPropsAutenticacion(final ListaPropiedades propiedadesEvento, final UsuarioAutenticadoInfo user) {
		if (user != null) {
			// Metodo auth
			if (user.getMetodoAutenticacion() != null) {
				propiedadesEvento.addPropiedad(TypeParametroEvento.AUTENTICACION.toString(),
						user.getMetodoAutenticacion().toString());
			}
			// QAA
			if (user.getQaa() != null) {
				propiedadesEvento.addPropiedad(TypeParametroEvento.AUTENTICACION_QAA.toString(),
						user.getQaa().toString());
			}
			// Representante
			if (user.getRepresentante() != null) {
				propiedadesEvento.addPropiedad(TypeParametroEvento.AUTENTICACION_RPTE.toString(),
						user.getRepresentante().getNif() + " - " + user.getRepresentante().getNombreApellidos());
			}
		}
	}

	/**
	 * Añade propiedades relativas al user agent.
	 *
	 * @param propiedadesEvento
	 *                              propiedades evento
	 * @param userAgent
	 *                              user agent
	 */
	private void addPropsUserAgent(final ListaPropiedades propiedadesEvento, final String userAgent) {
		propiedadesEvento.addPropiedad(TypeParametroEvento.SISTEMA_OPERATIVO.toString(),
				UserAgentUtil.getSistemaOperativo(userAgent));
		propiedadesEvento.addPropiedad(TypeParametroEvento.VERSION_SISTEMA_OPERATIVO.toString(),
				UserAgentUtil.getVersionSistemaOperativo(userAgent));
		propiedadesEvento.addPropiedad(TypeParametroEvento.NAVEGADOR.toString(), UserAgentUtil.getNavegador(userAgent));
		propiedadesEvento.addPropiedad(TypeParametroEvento.VERSION_NAVEGADOR.toString(),
				UserAgentUtil.getVersionNavegador(userAgent));
	}

	/**
	 * Eventos producidos tras ejecutar un paso.
	 *
	 * @param idSesionTramitacion
	 *                                idSesionTramitacion
	 * @param pArgumentos
	 *                                Argumentos
	 * @param pResult
	 *                                Resultado
	 * @return Eventos
	 */
	private List<EventoAuditoria> eventoFlujoTramitacionAccionPaso(final String idSesionTramitacion,
			final Object[] pArgumentos, final Object pResult) {

		final List<EventoAuditoria> eventos = new ArrayList<>();

		final TypeAccionPaso accionPaso = (TypeAccionPaso) pArgumentos[ConstantesNumero.N2];
		final ParametrosAccionPaso parametrosPaso = (ParametrosAccionPaso) pArgumentos[ConstantesNumero.N3];
		final ResultadoAccionPaso respuestaAccionPaso = (ResultadoAccionPaso) pResult;

		// Eventos formulario
		if (accionPaso instanceof TypeAccionPasoRellenar) {

			final TypeAccionPasoRellenar accionPasoRellenar = (TypeAccionPasoRellenar) accionPaso;

			// Abrir / Guardar formulario
			if (accionPasoRellenar == TypeAccionPasoRellenar.ABRIR_FORMULARIO
					|| accionPasoRellenar == TypeAccionPasoRellenar.GUARDAR_FORMULARIO) {
				final TypeEvento tipoEventoForm = (accionPasoRellenar == TypeAccionPasoRellenar.ABRIR_FORMULARIO
						? TypeEvento.FORMULARIO_INICIO
						: TypeEvento.FORMULARIO_FIN);
				final String idFormulario = (String) UtilsFlujo.recuperaParametroAccionPaso(parametrosPaso,
						"idFormulario", true);
				final EventoAuditoria eventoFormulario = crearEvento(tipoEventoForm, idSesionTramitacion);
				final ListaPropiedades propiedadesEvento = new ListaPropiedades();
				eventoFormulario.setPropiedadesEvento(propiedadesEvento);
				propiedadesEvento.addPropiedad(TypeParametroEvento.DOCUMENTO_ID.toString(), idFormulario);
				eventos.add(eventoFormulario);
			}
		}

		// Eventos paso registro tramite
		if (accionPaso instanceof TypeAccionPasoRegistrar) {

			final TypeAccionPasoRegistrar accionPasoRegistrar = (TypeAccionPasoRegistrar) accionPaso;

			// Inicio / fin firma
			if (accionPasoRegistrar == TypeAccionPasoRegistrar.INICIAR_FIRMA_DOCUMENTO
					|| accionPasoRegistrar == TypeAccionPasoRegistrar.VERIFICAR_FIRMA_DOCUMENTO) {
				final String idDocumento = (String) UtilsFlujo.recuperaParametroAccionPaso(parametrosPaso,
						"idDocumento", true);
				final String instanciaStr = (String) UtilsFlujo.recuperaParametroAccionPaso(parametrosPaso, "instancia",
						false);
				final String nifFirmante = (String) UtilsFlujo.recuperaParametroAccionPaso(parametrosPaso, "firmante",
						false);

				final ListaPropiedades propiedadesEvento = new ListaPropiedades();
				propiedadesEvento.addPropiedad(TypeParametroEvento.DOCUMENTO_ID.toString(), idDocumento);
				propiedadesEvento.addPropiedad(TypeParametroEvento.DOCUMENTO_INSTANCIA.toString(), instanciaStr);

				// TODO FIRMA - HAY QUE VER COMO ESPECIFICAR FIRMANTE CUANDO NO SE VERIFICA FIRMA (SOLO SE PODRA EN VERIFICARFIRMA)
				if (StringUtils.isNotBlank(nifFirmante)) {
					propiedadesEvento.addPropiedad(TypeParametroEvento.NIF.toString(), nifFirmante);
				}

				TypeEvento typeEvento = null;

				switch (accionPasoRegistrar) {
				case INICIAR_FIRMA_DOCUMENTO:
					propiedadesEvento.addPropiedad(TypeParametroEvento.FIRMA_SESION.toString(), (String) respuestaAccionPaso.getParametroRetorno("idSesionFirma"));
					typeEvento = TypeEvento.FIRMA_INICIO;
					break;
				case VERIFICAR_FIRMA_DOCUMENTO:
					final FirmaVerificacion fv = ((FirmaVerificacion) respuestaAccionPaso
							.getParametroRetorno("resultado"));
					boolean resultadoFirma = (fv.getRealizada() == TypeSiNo.SI && fv.getVerificada() == TypeSiNo.SI);
					if (StringUtils.isNotBlank(fv.getDetalleError())) {
						propiedadesEvento.addPropiedad(TypeParametroEvento.FIRMA_ERROR.toString(), fv.getDetalleError());
					}
					if (StringUtils.isNotBlank(fv.getCodigoError())) {
						propiedadesEvento.addPropiedad(TypeParametroEvento.FIRMA_ERROR_CODIGO.toString(), fv.getCodigoError());
					}
					propiedadesEvento.addPropiedad(TypeParametroEvento.FIRMA_SESION.toString(), fv.getSesionFirma());
					typeEvento = resultadoFirma ? TypeEvento.FIRMA_FIN_OK : TypeEvento.FIRMA_FIN_KO;
					if (StringUtils.isNotBlank(fv.getMetodoFirma())) {
						propiedadesEvento.addPropiedad(TypeParametroEvento.FIRMA_METODO.toString(), fv.getMetodoFirma());
					}
					break;
				}

				if (typeEvento != null) {
					EventoAuditoria eventoFirma = crearEvento(typeEvento, idSesionTramitacion);
					eventoFirma.setPropiedadesEvento(propiedadesEvento);
					eventos.add(eventoFirma);
				}

			}

			// Registro tramite
			if (accionPasoRegistrar == TypeAccionPasoRegistrar.FINALIZAR_REGISTRO) {
				// Comprobamos si se ha conseguido registrar
				final ResultadoRegistrar resReg = ((ResultadoRegistrar) parametrosPaso.getParametroEntrada("resultadoRegistrar"));
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
		}

		// Evento pago
		if (accionPaso instanceof TypeAccionPasoPagar) {
			// Pago electronico
			if (((TypeAccionPasoPagar) accionPaso) == TypeAccionPasoPagar.VERIFICAR_PAGO_PASARELA) {
				final PagoVerificacion pv = (PagoVerificacion) respuestaAccionPaso.getParametroRetorno("verificacion");
				final DatosSesionPago sp = (DatosSesionPago) respuestaAccionPaso.getParametroRetorno("sesionPago");
				TypeEvento eventoPago = null;
				if (pv.getRealizado() == TypeSiNo.SI) {
					eventoPago = TypeEvento.PAGO_ELECTRONICO_VERIFICADO;
				} else {
					eventoPago = TypeEvento.PAGO_ELECTRONICO_NO_VERIFICADO;
				}
				final EventoAuditoria eventoPagoTramite = crearEventoPago(eventoPago, idSesionTramitacion, sp);
				// Para no realizado añadimos localizador y metodo pago
				if (pv.getRealizado() == TypeSiNo.NO ) {
					if (StringUtils.isNotBlank(pv.getMetodoPago())) {
						eventoPagoTramite.getPropiedadesEvento().addPropiedad(TypeParametroEvento.PAGO_METODO.toString(),
								pv.getMetodoPago());
					}
					if (StringUtils.isNotBlank(pv.getLocalizador())) {
						eventoPagoTramite.getPropiedadesEvento().addPropiedad(TypeParametroEvento.PAGO_LOCALIZADOR.toString(),
								pv.getLocalizador());
					}
				}
				// Añade mensaje error pasarela
				if (pv.getEstadoIncorrecto() != null) {
					eventoPagoTramite.getPropiedadesEvento().addPropiedad(TypeParametroEvento.PAGO_ERROR.toString(), pv.getEstadoIncorrecto().getCodigoErrorPasarela() + "-" + pv.getEstadoIncorrecto().getMensajeErrorPasarela());
				}
				eventos.add(eventoPagoTramite);
			}
			// Pago presencial
			if (((TypeAccionPasoPagar) accionPaso) == TypeAccionPasoPagar.CARTA_PAGO_PRESENCIAL) {
				final DatosSesionPago sp = (DatosSesionPago) respuestaAccionPaso.getParametroRetorno("sesionPago");
				final EventoAuditoria eventoPagoTramite = crearEventoPago(TypeEvento.PAGO_PRESENCIAL,
						idSesionTramitacion, sp);
				eventos.add(eventoPagoTramite);
			}
			// Cancelar pago
			if (((TypeAccionPasoPagar) accionPaso) == TypeAccionPasoPagar.CANCELAR_PAGO_INICIADO) {
				final DatosSesionPago sp = (DatosSesionPago) respuestaAccionPaso.getParametroRetorno("sesionPago");
				final PagoVerificacion pv = (PagoVerificacion) respuestaAccionPaso.getParametroRetorno("verificacion");
				final EventoAuditoria eventoPagoTramite = crearEventoPago(TypeEvento.PAGO_CANCELADO,
						idSesionTramitacion, sp);
				if (pv != null && pv.getRealizado() == TypeSiNo.NO ) {
					if (StringUtils.isNotBlank(pv.getMetodoPago())) {
						eventoPagoTramite.getPropiedadesEvento().addPropiedad(TypeParametroEvento.PAGO_METODO.toString(),
								pv.getMetodoPago());
					}
					if (StringUtils.isNotBlank(pv.getLocalizador())) {
						eventoPagoTramite.getPropiedadesEvento().addPropiedad(TypeParametroEvento.PAGO_LOCALIZADOR.toString(),
								pv.getLocalizador());
					}
				}
				eventos.add(eventoPagoTramite);
			}
		}

		// Evento fin tramite (tras accion paso)
		if (respuestaAccionPaso.isFinalizadoTrasAccion()) {
			eventos.add(crearEvento(TypeEvento.FIN_TRAMITE, idSesionTramitacion));
		}

		return eventos;
	}

	protected EventoAuditoria crearEventoPago(final TypeEvento eventoPago, final String idSesionTramitacion,
			final DatosSesionPago sp) {
		final EventoAuditoria eventoPagoTramite = crearEvento(eventoPago, idSesionTramitacion);
		final ListaPropiedades propiedadesEvento = new ListaPropiedades();
		eventoPagoTramite.setPropiedadesEvento(propiedadesEvento);
		propiedadesEvento.addPropiedad(TypeParametroEvento.PAGO_ID_SESION.toString(), sp.getIdentificadorPago());
		propiedadesEvento.addPropiedad(TypeParametroEvento.PAGO_PASARELA.toString(), sp.getPasarelaId());
		propiedadesEvento.addPropiedad(TypeParametroEvento.PAGO_IMPORTE.toString(), String.format(Locale.US, "%.2f", sp.getImporte() / 100.0));
		if (sp.getMultiplicador() > 1) {
			propiedadesEvento.addPropiedad(TypeParametroEvento.PAGO_MULTIPLICADOR.toString(), Integer.toString(sp.getMultiplicador()));
		}
		// Metodo pago y localizador solo se guardan en sesion pago cuando se verifica pago
		if (StringUtils.isNotBlank(sp.getMetodoPagoSeleccionado())) {
			propiedadesEvento.addPropiedad(TypeParametroEvento.PAGO_METODO.toString(), sp.getMetodoPagoSeleccionado());
		}
		if (StringUtils.isNotBlank(sp.getLocalizador())) {
			propiedadesEvento.addPropiedad(TypeParametroEvento.PAGO_LOCALIZADOR.toString(), sp.getLocalizador());
		}
		return eventoPagoTramite;
	}

}
