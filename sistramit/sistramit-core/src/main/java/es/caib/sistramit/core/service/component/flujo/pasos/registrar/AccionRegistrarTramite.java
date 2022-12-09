package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.DatosAsunto;
import es.caib.sistra2.commons.plugins.registro.api.DatosOrigen;
import es.caib.sistra2.commons.plugins.registro.api.DocumentoAsiento;
import es.caib.sistra2.commons.plugins.registro.api.Interesado;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeDocumental;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeDocumentoIdentificacion;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeFirmaAsiento;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeInteresado;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeOrigenDocumento;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeValidez;
import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistra2.commons.utils.NifUtils;
import es.caib.sistra2.commons.utils.ValidacionTipoException;
import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RPlantillaEntidad;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DatosInteresado;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.DocumentoRegistro;
import es.caib.sistramit.core.api.model.flujo.Entidad;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeDestino;
import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeFirmaDigital;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.flujo.types.TypeResultadoRegistro;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.api.model.system.types.TypeParametroEvento;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.flujo.ConstantesFlujo;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.EnvioAvisoComponent;
import es.caib.sistramit.core.service.component.integracion.EnvioRemotoComponent;
import es.caib.sistramit.core.service.component.integracion.RegistroComponent;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoAnexo;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoFormulario;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoPago;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRegistrar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.FirmaDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ParametrosRegistro;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.ResultadoRegistro;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.system.EnvioAviso;
import es.caib.sistramit.core.service.model.system.types.TypeEnvioAviso;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Acción que permite registrar tramite en el paso Registrar.
 *
 * @author Indra
 *
 */
@Component("accionRtRegistrarTramite")
public final class AccionRegistrarTramite implements AccionPaso {

	// TODO PENDIENTE PREREGISTRO (ENTREGA PRESENCIAL). VER QUE PASA CON
	// JUSTIFICANTE PARA REGISTRO (NO NECESARIO?) Y PARA PRESENCIAL (NECESARIO?).

	// TODO VERIFICAR SI DOCS FIRMADOS.

	/** DAO acceso BBDD. */
	@Autowired
	private FlujoPasoDao dao;
	/** Literales negocio. */
	@Autowired
	private Literales literales;
	/** Componente de registro. */
	@Autowired
	private RegistroComponent registroComponent;
	/** Componente de envio remoto. */
	@Autowired
	private EnvioRemotoComponent envioRemotoComponent;
	/** Configuración. */
	@Autowired
	private ConfiguracionComponent configuracion;
	/** Auditoria. */
	@Autowired
	private AuditoriaComponent auditoriaComponent;
	/** Envio aviso. */
	@Autowired
	private EnvioAvisoComponent envioAvisoComponent;

	@Override
	public RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final TypeAccionPaso pAccionPasoObtenerAnexo, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Recuperamos parametros
		final TypeSiNo reintentarStr = (TypeSiNo) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "reintentar",
				false);
		boolean reintentar = false;
		if (reintentarStr == TypeSiNo.SI) {
			reintentar = true;
		}

		// Obtenemos datos internos del paso
		final DatosInternosPasoRegistrar pDipa = (DatosInternosPasoRegistrar) pDatosPaso.internalData();

		// Valida si se puede registrar el tramite
		validacionesRegistrar(pDipa, pDpp, pVariablesFlujo, pDefinicionTramite);

		// Realizamos proceso de registro
		final ResultadoRegistrar resReg = registrarTramite(pDipa, pDpp, pVariablesFlujo, pDefinicionTramite,
				reintentar);

		// Actualizamos persistencia
		actualizarPersistencia(pVariablesFlujo.getIdSesionTramitacion(), pDipa, pDpp, resReg);

		// Actualizamos detalle
		actualizarDetalleRegistrar(pDipa, pVariablesFlujo, pDpp, resReg);

		// Generamos aviso finalización
		generarAvisoFinalizacion(pDipa, pVariablesFlujo, resReg, pDefinicionTramite);

		// Devolvemos respuesta indicando resultado registro
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		rp.addParametroRetorno("resultado", resReg);
		final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
		rep.setRespuestaAccionPaso(rp);
		return rep;
	}

	/**
	 * Genera aviso finalización trámite.
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param resReg
	 *                               Resultado registro
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 */
	private void generarAvisoFinalizacion(final DatosInternosPasoRegistrar pDipa, final VariablesFlujo pVariablesFlujo,
			final ResultadoRegistrar resReg, final DefinicionTramiteSTG pDefinicionTramite) {

		final DetallePasoRegistrar detallePasoRegistrar = (DetallePasoRegistrar) pDipa.getDetallePaso();

		// TODO TRAMITE-SERVICIO: VER SI PARA SERVICIO ENVIAMOS CORREO FINALIZACION

		if (resReg != null && resReg.getResultado() == TypeResultadoRegistro.CORRECTO
				&& detallePasoRegistrar.getAvisoFinalizar().getAvisar() == TypeSiNo.SI
				&& pVariablesFlujo.getTipoDestino() == TypeDestino.REGISTRO) {

			final String idEntidad = pDefinicionTramite.getDefinicionVersion().getIdEntidad();
			final RConfiguracionEntidad entidad = configuracion.obtenerConfiguracionEntidad(idEntidad);

			final Entidad detalleEntidad = UtilsFlujo.detalleTramiteEntidad(entidad, pVariablesFlujo.getIdioma(),
					configuracion);
			final String urlCarpeta = detalleEntidad.getUrlCarpeta();
			final String urlSede = detalleEntidad.getUrlSede();
			final String urlLogo = detalleEntidad.getLogo();

			String titulo = this.literales.getLiteral(Literales.PASO_REGISTRAR, "mailFinalizacion.titulo",
					pVariablesFlujo.getIdioma());
			String mensaje = this.literales.getLiteral(Literales.PASO_REGISTRAR, "mailFinalizacion.texto",
					new String[] { resReg.getNumeroRegistro(), urlCarpeta, urlSede }, pVariablesFlujo.getIdioma());

			String plantilla = null;

			// Buscamos plantilla customizada por entidad
			plantilla = obtenerPlantillaMailFinalizacion(entidad.getPlantillas(), pVariablesFlujo.getIdioma());
			if (plantilla != null) {
				// Sustituir placeholders
				plantilla = StringUtils.replace(plantilla, "${NUMERO_REGISTRO}", resReg.getNumeroRegistro());
				try {
					plantilla = StringUtils.replace(plantilla, "${NUMERO_REGISTRO_B64URLSAFE}",
							ValidacionesTipo.getInstance().convierteBase64UrlSafe(resReg.getNumeroRegistro()));
				} catch (final ValidacionTipoException e) {
					throw new AccionPasoNoPermitidaException(
							"No s'ha pogut convertir número registre a B64: " + resReg.getNumeroRegistro());
				}
				plantilla = StringUtils.replace(plantilla, "${URL_SEDE}", urlSede);
				plantilla = StringUtils.replace(plantilla, "${URL_CARPETA}", urlCarpeta);
				plantilla = StringUtils.replace(plantilla, "${URL_LOGO}", urlLogo);
				mensaje = plantilla;
			} else {
				// Si no hay plantilla customizada por entidad, usamos generica
				plantilla = UtilsPasoRegistrar.getInstance().cargarPlantillaMailFinalizacion();
				if (plantilla != null) {
					// Reemplazamos variables plantilla
					titulo = this.literales.getLiteral(Literales.PASO_REGISTRAR, "mailFinalizacion.titulo",
							pVariablesFlujo.getIdioma());
					plantilla = StringUtils.replace(plantilla, "[#ORGANISMO.NOMBRE#]",
							UtilsSTG.obtenerLiteral(entidad.getDescripcion(), pVariablesFlujo.getIdioma(), true));
					plantilla = StringUtils.replace(plantilla, "[#URL_LOGO#]", urlLogo);
					plantilla = StringUtils.replace(plantilla, "[#TEXTO.FINALIZACION#]", mensaje);
					plantilla = StringUtils.replace(plantilla, "[#TEXTO.ACCESO_CARPETA#]", this.literales.getLiteral(
							Literales.PASO_REGISTRAR, "mailFinalizacion.accesoCarpeta", pVariablesFlujo.getIdioma()));
					plantilla = StringUtils.replace(plantilla, "[#TEXTO.NO_RESPONDER#]", this.literales.getLiteral(
							Literales.PASO_REGISTRAR, "mailFinalizacion.noResponder", pVariablesFlujo.getIdioma()));
					plantilla = StringUtils.replace(plantilla, "[#URL_ACCESO_CARPETA#]", urlCarpeta);
					mensaje = plantilla;
				}
			}

			// Enviamos mail
			final String email = detallePasoRegistrar.getAvisoFinalizar().getEmail();
			final EnvioAviso envio = new EnvioAviso();
			envio.setDestino(email);
			envio.setFechaCreacion(new Date());
			envio.setMensaje(mensaje);
			envio.setTipo(TypeEnvioAviso.EMAIL);
			envio.setTitulo(titulo);
			envioAvisoComponent.generarEnvio(envio);

		}

	}

	/**
	 * Valida si se puede registrar el trámite.
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param pDpp
	 *                               Datos persistencia paso
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 */
	private void validacionesRegistrar(final DatosInternosPasoRegistrar pDipa, final DatosPersistenciaPaso pDpp,
			final VariablesFlujo pVariablesFlujo, final DefinicionTramiteSTG pDefinicionTramite) {

		final DetallePasoRegistrar detallePasoRegistrar = (DetallePasoRegistrar) pDipa.getDetallePaso();

		// El paso debe estar en un estado valido para registrar
		if (pDipa.getEstado() != TypeEstadoPaso.PENDIENTE) {
			throw new AccionPasoNoPermitidaException("La passa no està en un estat vàlid per registrar");
		}

		// Como se ha iniciado sesión registro deberá estar en estado reintentar
		if (detallePasoRegistrar.getReintentar() == TypeSiNo.NO) {
			throw new AccionPasoNoPermitidaException(" No s'ha iniciat sessió registre i no està en estat reintentar");
		}

	}

	/**
	 * Registra el tramite.
	 *
	 * @param pDipa
	 *                               Datos internos tramite
	 * @param pDpp
	 *                               Datos persistencia paso
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 * @param reintentar
	 *                               Indica si reintentar
	 * @return Resultado registro
	 */
	private ResultadoRegistrar registrarTramite(final DatosInternosPasoRegistrar pDipa,
			final DatosPersistenciaPaso pDpp, final VariablesFlujo pVariablesFlujo,
			final DefinicionTramiteSTG pDefinicionTramite, final boolean reintentar) {
		// Obtenemos documento de asiento para obtener id sesion registro
		final DocumentoPasoPersistencia docAsientoDpp = pDpp
				.getDocumentoPasoPersistencia(ConstantesFlujo.ID_ASIENTO_REGISTRO, ConstantesNumero.N1);
		final String idSesionRegistro = docAsientoDpp.getRegistroIdSesion();

		ResultadoRegistrar resReg = null;
		if (reintentar) {
			// Reintentar registro
			resReg = reintentarRegistrar(idSesionRegistro, pDipa.getParametrosRegistro(), pVariablesFlujo);
		} else {
			// Generar asiento con info asiento
			final AsientoRegistral asiento = generarAsiento(pDipa, pVariablesFlujo, pDefinicionTramite, true);
			// Audita registro
			auditarRegistro(pDipa, pDefinicionTramite, pVariablesFlujo);
			// Invoca a registrar
			resReg = realizarRegistro(idSesionRegistro, pDipa.getParametrosRegistro(), asiento, pVariablesFlujo);
		}
		return resReg;
	}

	/**
	 * Genera evento debug (json excluyendo contenido docs).
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 */
	protected void auditarRegistro(final DatosInternosPasoRegistrar pDipa,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {
		if (pVariablesFlujo.isDebugEnabled()) {
			final AsientoRegistral asientoDebug = generarAsiento(pDipa, pVariablesFlujo, pDefinicionTramite, false);
			String asientoStr;
			try {
				asientoStr = JSONUtil.toJSON(asientoDebug);
			} catch (final JSONUtilException e) {
				asientoStr = "Error al serialitzar info seient: " + e.getMessage();
			}
			final ListaPropiedades listaPropiedades = new ListaPropiedades();
			listaPropiedades.addPropiedad(TypeParametroEvento.REGISTRO_ASIENTOREGISTRO.toString(), asientoStr);
			final EventoAuditoria evento = new EventoAuditoria();
			evento.setIdSesionTramitacion(pVariablesFlujo.getIdSesionTramitacion());
			evento.setFecha(new Date());
			evento.setTipoEvento(TypeEvento.DEBUG_SCRIPT);
			evento.setDescripcion("Debug envio valores registro");
			evento.setPropiedadesEvento(listaPropiedades);
			auditoriaComponent.auditarEventoAplicacion(evento);
		}
	}

	/**
	 * Realiza registro.
	 *
	 * @param idSesionRegistro
	 *                               id sesion
	 * @param parametrosRegistro
	 *                               Parámetros registro
	 * @param asiento
	 *                               asiento
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @return resultado registro
	 */
	protected ResultadoRegistrar realizarRegistro(final String idSesionRegistro,
			final ParametrosRegistro parametrosRegistro, final AsientoRegistral asiento,
			final VariablesFlujo pVariablesFlujo) {
		ResultadoRegistrar resReg;
		if (pVariablesFlujo.getTipoDestino() == TypeDestino.REGISTRO) {
			resReg = registroComponent.registrar(parametrosRegistro.getDatosRegistrales().getCodigoEntidad(),
					pVariablesFlujo.getIdSesionTramitacion(), idSesionRegistro, asiento,
					pVariablesFlujo.isDebugEnabled());
		} else {
			resReg = envioRemotoComponent.realizarEnvio(parametrosRegistro.getDatosRegistrales().getCodigoEntidad(),
					parametrosRegistro.getDatosRegistrales().getIdEnvioRemoto(),
					pVariablesFlujo.getIdSesionTramitacion(), idSesionRegistro, asiento,
					pVariablesFlujo.isDebugEnabled());
		}
		return resReg;
	}

	/**
	 * Reintentar registrar.
	 *
	 * @param idSesionRegistro
	 *                               id sesión
	 * @param parametrosRegistro
	 *                               parametrosRegistro
	 * @param pVariablesFlujo
	 * @return
	 */
	protected ResultadoRegistrar reintentarRegistrar(final String idSesionRegistro,
			final ParametrosRegistro parametrosRegistro, final VariablesFlujo pVariablesFlujo) {
		ResultadoRegistrar resReg;
		if (pVariablesFlujo.getTipoDestino() == TypeDestino.REGISTRO) {
			resReg = registroComponent.reintentarRegistro(parametrosRegistro.getDatosRegistrales().getCodigoEntidad(),
					idSesionRegistro, pVariablesFlujo.isDebugEnabled());
		} else {
			resReg = envioRemotoComponent.reintentarEnvio(parametrosRegistro.getDatosRegistrales().getCodigoEntidad(),
					parametrosRegistro.getDatosRegistrales().getIdEnvioRemoto(), idSesionRegistro,
					pVariablesFlujo.isDebugEnabled());
		}
		return resReg;
	}

	/**
	 * Actualizar detalle registrar.
	 *
	 * @param pDipa
	 *                            Datos internos paso
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 * @param pDpp
	 *                            Datos persistencia
	 * @param resReg
	 *                            Resultado registro
	 */
	private void actualizarDetalleRegistrar(final DatosInternosPasoRegistrar pDipa,
			final VariablesFlujo pVariablesFlujo, final DatosPersistenciaPaso pDpp, final ResultadoRegistrar resReg) {

		// Obtenemos documento de asiento para ver si es un preregistro
		final DocumentoPasoPersistencia docAsientoDpp = pDpp
				.getDocumentoPasoPersistencia(ConstantesFlujo.ID_ASIENTO_REGISTRO, ConstantesNumero.N1);
		final boolean preregistro = (docAsientoDpp.getRegistroPreregistro() == TypeSiNo.SI);

		// Actualizamos detalle
		final DetallePasoRegistrar detallePasoRegistrar = (DetallePasoRegistrar) pDipa.getDetallePaso();
		switch (resReg.getResultado()) {
		case CORRECTO:
			// Registro realizado:
			// - establecemos detalle registro
			final ResultadoRegistro rr = new ResultadoRegistro();
			rr.setPreregistro(preregistro);
			rr.setFechaRegistro(resReg.getFechaRegistro());
			rr.setNumeroRegistro(resReg.getNumeroRegistro());
			rr.setAsunto(pVariablesFlujo.getTituloTramite());
			pDipa.setResultadoRegistro(rr);
			// - resetamos info de reintentar
			detallePasoRegistrar.setRegistrar(TypeSiNo.NO);
			detallePasoRegistrar.setReintentar(TypeSiNo.NO);
			break;
		case ERROR:
			// Registro realizado con error
			// - resetamos info de reintentar
			detallePasoRegistrar.setReintentar(TypeSiNo.NO);
			break;
		case REINTENTAR:
			// Se debe reintentar registro
			// - indicamos que hay que reintentar el pago
			detallePasoRegistrar.setReintentar(TypeSiNo.SI);
			break;
		default:
			throw new TipoNoControladoException("Tipus de resultat registre no controlat: " + resReg.getResultado());
		}
	}

	/**
	 * Actualiza paso tras registrar.
	 *
	 * @param pIdSesionTramitacion
	 *                                 Id sesion tramitacion
	 * @param pDipa
	 *                                 Datos internos paso
	 * @param pDpp
	 *                                 Datos persistencia
	 * @param resReg
	 *                                 Resultado registro
	 */
	private void actualizarPersistencia(final String pIdSesionTramitacion, final DatosInternosPasoRegistrar pDipa,
			final DatosPersistenciaPaso pDpp, final ResultadoRegistrar resReg) {

		// Obtenemos documento de asientO
		final DocumentoPasoPersistencia docAsientoDpp = pDpp
				.getDocumentoPasoPersistencia(ConstantesFlujo.ID_ASIENTO_REGISTRO, ConstantesNumero.N1);

		// Actualizamos estado en funcion del resultado de registro
		// - Actualizamos persistencia
		switch (resReg.getResultado()) {
		case CORRECTO:
			// Registro realizado:
			// - updateamos estado documento asiento para indicar resultado
			// registro
			docAsientoDpp.setEstado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
			docAsientoDpp.setRegistroResultado(resReg.getResultado());
			docAsientoDpp.setRegistroNumeroRegistro(resReg.getNumeroRegistro());
			docAsientoDpp.setRegistroFechaRegistro(resReg.getFechaRegistro());
			docAsientoDpp.setRegistroNifPresentador(
					pDipa.getParametrosRegistro().getDatosPresentacion().getPresentador().getNif());
			docAsientoDpp.setRegistroNombrePresentador(
					pDipa.getParametrosRegistro().getDatosPresentacion().getPresentador().getNombreApellidos());
			dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(), pDipa.getIdPaso(), docAsientoDpp);
			break;
		case ERROR:
			// Error al registrar:
			// - updateamos estado documento asiento para resetear estado y
			// permitir nuevo registro
			docAsientoDpp.setEstado(TypeEstadoDocumento.SIN_RELLENAR);
			docAsientoDpp.setRegistroResultado(null);
			dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(), pDipa.getIdPaso(), docAsientoDpp);
			break;
		case REINTENTAR:
			// Hay que reintentar el proceso de registro
			docAsientoDpp.setEstado(TypeEstadoDocumento.RELLENADO_INCORRECTAMENTE);
			docAsientoDpp.setRegistroResultado(TypeResultadoRegistro.REINTENTAR);
			dao.establecerDatosDocumento(pDipa.getIdSesionTramitacion(), pDipa.getIdPaso(), docAsientoDpp);
			break;
		default:
			throw new TipoNoControladoException("Tipus de resultat registre no controlat: " + resReg.getResultado());
		}
	}

	/**
	 * Genera asiento registral.
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param pVariablesFlujo
	 *                               variables flujo
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 * @param pContenidoDocs
	 *                               Indica si genera o no el contenido de los
	 *                               documentos (para debug generar sin contenido)
	 * @return asiento registral
	 */
	private AsientoRegistral generarAsiento(final DatosInternosPasoRegistrar pDipa,
			final VariablesFlujo pVariablesFlujo, final DefinicionTramiteSTG pDefinicionTramite,
			final boolean pContenidoDocs) {
		final AsientoRegistral asiento = new AsientoRegistral();
		// - Datos origen
		final DatosOrigen datosOrigen = new DatosOrigen();
		datosOrigen.setCodigoEntidad(pDipa.getParametrosRegistro().getDatosRegistrales().getCodigoEntidad());
		datosOrigen.setCodigoOficinaRegistro(pDipa.getParametrosRegistro().getDatosRegistrales().getOficina());
		datosOrigen.setLibroOficinaRegistro(pDipa.getParametrosRegistro().getDatosRegistrales().getLibro());
		datosOrigen.setTipoRegistro(TypeRegistro.REGISTRO_ENTRADA);
		// - Datos asunto
		final DatosAsunto datosAsunto = new DatosAsunto();
		datosAsunto.setCodigoSiaProcedimiento(
				pVariablesFlujo.getDatosTramiteCP().getProcedimiento().getIdProcedimientoSIA());
		datosAsunto.setFechaAsunto(new Date());
		datosAsunto.setIdiomaAsunto(pVariablesFlujo.getIdioma());
		datosAsunto.setExtractoAsunto(pDipa.getParametrosRegistro().getDatosRegistrales().getExtracto());
		datosAsunto
				.setCodigoOrganoDestino(pDipa.getParametrosRegistro().getDatosRegistrales().getCodigoOrganoDestino());
		datosAsunto.setNumeroExpediente(pDipa.getParametrosRegistro().getDatosRegistrales().getNumeroExpediente());
		datosAsunto.setTextoExpone(pDipa.getParametrosRegistro().getDatosRegistrales().getTextoExpone());
		datosAsunto.setTextoSolicita(pDipa.getParametrosRegistro().getDatosRegistrales().getTextoSolicita());
		asiento.setDatosAsunto(datosAsunto);
		// - Interesados
		final List<Interesado> interesados = new ArrayList<>();
		if (pDipa.getParametrosRegistro().getDatosRepresentacion() != null) {
			interesados.add(generarInteresado(TypeInteresado.REPRESENTADO,
					pDipa.getParametrosRegistro().getDatosRepresentacion().getRepresentado()));
			interesados.add(generarInteresado(TypeInteresado.REPRESENTANTE,
					pDipa.getParametrosRegistro().getDatosRepresentacion().getRepresentante()));
		} else {
			interesados.add(generarInteresado(TypeInteresado.REPRESENTANTE,
					pDipa.getParametrosRegistro().getDatosPresentacion().getPresentador()));
		}
		asiento.setInteresados(interesados);
		// - Documentos
		final List<DocumentoAsiento> documentosRegistro = new ArrayList<>();
		final DetallePasoRegistrar dpr = (DetallePasoRegistrar) pDipa.getDetallePaso();
		documentosRegistro
				.addAll(generarDocumentosRegistro(pDipa, pVariablesFlujo, dpr.getFormularios(), pContenidoDocs));
		documentosRegistro.addAll(generarDocumentosRegistro(pDipa, pVariablesFlujo, dpr.getAnexos(), pContenidoDocs));
		documentosRegistro.addAll(generarDocumentosRegistro(pDipa, pVariablesFlujo, dpr.getPagos(), pContenidoDocs));
		asiento.setDocumentosRegistro(documentosRegistro);

		asiento.setDatosOrigen(datosOrigen);
		return asiento;
	}

	/**
	 * Genera documentos asiento.
	 *
	 * @param pDipa
	 *                                    Datos internos paso
	 * @param pVariablesFlujo
	 *                                    variables flujo
	 * @param listaDocumentosRegistro
	 *                                    documentos registro
	 * @param pContenidoDocs
	 *                                    Indica si genera o no el contenido de los
	 *                                    documentos (para debug generar sin
	 *                                    contenido)
	 * @return documentos asiento
	 */
	private List<DocumentoAsiento> generarDocumentosRegistro(final DatosInternosPasoRegistrar pDipa,
			final VariablesFlujo pVariablesFlujo, final List<DocumentoRegistro> listaDocumentosRegistro,
			final boolean pContenidoDocs) {
		final List<DocumentoAsiento> documentosRegistro = new ArrayList<>();
		if (listaDocumentosRegistro != null) {
			for (final DocumentoRegistro dr : listaDocumentosRegistro) {
				final List<DocumentoAsiento> documentosAsientoRegistral = generarDocumentosRegistro(pDipa,
						pVariablesFlujo, dr, pContenidoDocs);
				documentosRegistro.addAll(documentosAsientoRegistral);
			}
		}
		return documentosRegistro;
	}

	/**
	 * Genera documento registro.
	 *
	 * @param pDipa
	 *                            Datos internos paso
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 * @param pDocReg
	 *                            Documento registro
	 * @param pContenidoDocs
	 *                            Indica si genera o no el contenido de los
	 *                            documentos (para debug generar sin contenido)
	 * @return
	 */
	private List<DocumentoAsiento> generarDocumentosRegistro(final DatosInternosPasoRegistrar pDipa,
			final VariablesFlujo pVariablesFlujo, final DocumentoRegistro pDocReg, final boolean pContenidoDocs) {

		final List<DocumentoAsiento> res = new ArrayList<>();

		// Obtenemos datos documento del flujo
		final DatosDocumento dd = pVariablesFlujo.getDocumento(pDocReg.getId(), pDocReg.getInstancia());

		// Obtiene datos persistencia del fichero
		final DocumentoPasoPersistencia documentoPersistencia = dao.obtenerDocumentoPersistencia(
				pVariablesFlujo.getIdSesionTramitacion(), dd.getIdPaso(), pDocReg.getId(), pDocReg.getInstancia());

		// En funcion del tipo de documento generamos los documentos para el asiento
		switch (dd.getTipo()) {
		case FORMULARIO:
			// Debe ser electronico
			if (dd.getPresentacion() != TypePresentacion.ELECTRONICA) {
				throw new ErrorConfiguracionException(
						"Un formulari no pot tenir presentació presencial: " + dd.getId());
			}

			// XML formulario
			res.add(generarDocumentoRegistro(dd, dd.getFichero(), null, true, pContenidoDocs));
			// PDF formulario
			final ReferenciaFichero refPDF = ((DatosDocumentoFormulario) dd).getPdf();
			// Si tiene firmas, generamos un documento para cada firma
			if (documentoPersistencia.getFirmas() != null && documentoPersistencia.getFirmas().size() > 0) {
				final List<FirmaDocumentoPersistencia> firmas = documentoPersistencia
						.obtenerFirmasFichero(refPDF.getId());
				for (final FirmaDocumentoPersistencia f : firmas) {
					res.add(generarDocumentoRegistro(dd, refPDF, f, false, pContenidoDocs));
				}
			} else {
				// Si no tiene firmas, generamos un documento para el pdf
				res.add(generarDocumentoRegistro(dd, refPDF, null, false, pContenidoDocs));
			}
			break;
		case ANEXO:
			// Si no es presencial no se trata
			if (dd.getPresentacion() == TypePresentacion.ELECTRONICA) {
				// Si tiene firmas, generamos un documento para cada firma
				if (documentoPersistencia.getFirmas() != null && documentoPersistencia.getFirmas().size() > 0) {
					final List<FirmaDocumentoPersistencia> firmas = documentoPersistencia
							.obtenerFirmasFichero(dd.getFichero().getId());
					for (final FirmaDocumentoPersistencia f : firmas) {
						res.add(generarDocumentoRegistro(dd, dd.getFichero(), f, false, pContenidoDocs));
					}
				} else {
					// Si no tiene firmas, generamos un documento para el pdf
					res.add(generarDocumentoRegistro(dd, dd.getFichero(), null, false, pContenidoDocs));
				}
			}
			break;
		case PAGO:
			// XML Pago
			res.add(generarDocumentoRegistro(dd, dd.getFichero(), null, true, pContenidoDocs));
			// Justificante Pago (para pago electronico)
			if (dd.getPresentacion() == TypePresentacion.ELECTRONICA) {
				res.add(generarDocumentoRegistro(dd, ((DatosDocumentoPago) dd).getJustificantePago(), null, false,
						pContenidoDocs));
			}
			break;
		default:
			break;
		}

		return res;
	}

	/**
	 * Genera documento registro.
	 *
	 * @param documento
	 *                           documento
	 * @param refFichero
	 *                           fichero del documento
	 * @param firmaDocumento
	 *                           firma
	 * @param xml
	 *                           si es xml
	 * @param pContenidoDocs
	 *                           Indica si genera o no el contenido de los
	 *                           documentos (para debug generar sin contenido)
	 * @return documento registro
	 */
	private DocumentoAsiento generarDocumentoRegistro(final DatosDocumento documento,
			final ReferenciaFichero refFichero, final FirmaDocumentoPersistencia firmaDocumento, final boolean xml,
			final boolean pContenidoDocs) {

		// TODO Ver si particularizamos titulo
		byte[] contentFic = null;
		String contentFicStr = null;

		// Recuperamos fichero
		final DatosFicheroPersistencia fichero = dao.recuperarFicheroPersistencia(refFichero);
		contentFic = fichero.getContenido();
		contentFicStr = "fic:" + refFichero.getId();
		final String instancia = (documento.getTipo() == TypeDocumento.ANEXO
				? ((DatosDocumentoAnexo) documento).getInstancia() + ""
				: "1");
		final String nombreFic = documento.getId() + "-" + instancia + "."
				+ FilenameUtils.getExtension(fichero.getNombre());

		// Recuperamos firma
		DatosFicheroPersistencia firmaFichero = null;
		String firmaFicheroStr = null;
		boolean anexarFirma = false;
		if (firmaDocumento != null) {
			firmaFichero = dao.recuperarFicheroPersistencia(firmaDocumento.getFirma());
			firmaFicheroStr = "fic:" + firmaDocumento.getFirma().getId();
			anexarFirma = true;
		}

		// Si se anexa un PADES, directamente se anexa la firma
		if (firmaDocumento != null && firmaDocumento.getTipoFirma() == TypeFirmaDigital.PADES) {
			contentFic = firmaFichero.getContenido();
			contentFicStr = "fic:" + firmaDocumento.getFirma().getId();
			anexarFirma = false;
		}

		// Anexo que se ha anexado firmado
		final boolean anexoAnexadoFirmado = documento.getTipo() == TypeDocumento.ANEXO
				&& ((DatosDocumentoAnexo) documento).getAnexadoFirmado() == TypeSiNo.SI;

		// Esta firmado si tiene fichero firma o es un anexo anexado firmado
		final boolean firmado = (firmaDocumento != null || anexoAnexadoFirmado);

		// Creamos info documento asiento registral
		final DocumentoAsiento documentoAsientoRegistral = new DocumentoAsiento();
		documentoAsientoRegistral.setTituloDoc(documento.getTitulo());
		documentoAsientoRegistral.setFechaCaptura(new Date());
		documentoAsientoRegistral.setOrigenDocumento(TypeOrigenDocumento.CIUDADANO);
		documentoAsientoRegistral.setTipoDocumental(calcularTipoDocumental(documento));
		documentoAsientoRegistral.setTipoDocumento(calcularTipoDocumento(documento.getTipo(), xml));
		documentoAsientoRegistral.setValidez(calcularValidez(documento.getTipo(), firmado));
		documentoAsientoRegistral.setNombreFichero(nombreFic);
		documentoAsientoRegistral
				.setContenidoFichero(pContenidoDocs ? contentFic : UtilsFlujo.stringToBytes(contentFicStr));

		// - No firmado
		if (!firmado) {
			documentoAsientoRegistral.setModoFirma(TypeFirmaAsiento.SIN_FIRMA);
		}

		// - Firmado
		if (firmado) {
			es.caib.sistra2.commons.plugins.registro.api.types.TypeFirmaDigital tipoFirma;
			TypeFirmaAsiento modoFirma;
			if (anexoAnexadoFirmado) {
				// Anexo anexado firmado
				tipoFirma = es.caib.sistra2.commons.plugins.registro.api.types.TypeFirmaDigital.PADES;
				modoFirma = TypeFirmaAsiento.FIRMA_ATTACHED;
			} else {
				// Firmado desde asistente
				tipoFirma = es.caib.sistra2.commons.plugins.registro.api.types.TypeFirmaDigital
						.fromString(firmaDocumento.getTipoFirma().toString());
				modoFirma = calcularTipoFirmaAsiento(firmaDocumento.getTipoFirma());
			}
			documentoAsientoRegistral.setTipoFirma(tipoFirma);
			documentoAsientoRegistral.setModoFirma(modoFirma);
			if (anexarFirma) {
				documentoAsientoRegistral.setNombreFirmaAnexada(firmaFichero.getNombre());
				documentoAsientoRegistral.setContenidoFirma(
						pContenidoDocs ? firmaFichero.getContenido() : UtilsFlujo.stringToBytes(firmaFicheroStr));
			}
		}

		return documentoAsientoRegistral;
	}

	protected String calcularTipoDocumental(final DatosDocumento documento) {
		// TODO Ver de establecer configuración por STG
		String tipoDocumental = "TD99";
		if (documento.getTipo() == TypeDocumento.FORMULARIO) {
			tipoDocumental = "TD99";
		}
		return tipoDocumental;
	}

	/**
	 * Calcula tipo firma digital asiento.
	 *
	 * @param typeFirmaDigital
	 *                             tipo firma digital
	 * @return tipo firma asiento
	 */
	private TypeFirmaAsiento calcularTipoFirmaAsiento(final TypeFirmaDigital typeFirmaDigital) {
		TypeFirmaAsiento res = null;
		switch (typeFirmaDigital) {
		case PADES:
			res = TypeFirmaAsiento.FIRMA_ATTACHED;
			break;
		case XADES_DETACHED:
			res = TypeFirmaAsiento.FIRMA_DETACHED;
			break;
		case XADES_ENVELOPED:
			res = TypeFirmaAsiento.FIRMA_ATTACHED;
			break;
		case CADES_DETACHED:
			res = TypeFirmaAsiento.FIRMA_DETACHED;
			break;
		case CADES_ATTACHED:
			res = TypeFirmaAsiento.FIRMA_ATTACHED;
			break;
		default:
			throw new TipoNoControladoException("Tipus firma no controlat: " + typeFirmaDigital);
		}
		return res;
	}

	/**
	 * Calcula tipo documento asiento.
	 *
	 * @param tipoDocumento
	 *                          tipo documento flujo
	 * @param xml
	 *                          si es documento xml
	 * @return tipo documento asiento
	 */
	private TypeDocumental calcularTipoDocumento(
			final es.caib.sistramit.core.api.model.flujo.types.TypeDocumento tipoDocumento, final boolean xml) {
		TypeDocumental res;
		if (xml) {
			res = TypeDocumental.FICHERO_TECNICO;
		} else {
			if (tipoDocumento == es.caib.sistramit.core.api.model.flujo.types.TypeDocumento.FORMULARIO) {
				res = TypeDocumental.FORMULARIO;
			} else {
				res = TypeDocumental.ANEXO;
			}
		}
		return res;
	}

	/**
	 * Calcula tipo validez.
	 *
	 * @param tipoDocumento
	 *                          Tipo documento
	 * @param firmado
	 * @return Tipo validez
	 */
	private TypeValidez calcularValidez(final es.caib.sistramit.core.api.model.flujo.types.TypeDocumento tipoDocumento,
			final boolean firmado) {
		TypeValidez res = null;
		switch (tipoDocumento) {
		case FORMULARIO:
			res = TypeValidez.ORIGINAL;
			break;
		case ANEXO:
			if (firmado) {
				res = TypeValidez.ORIGINAL;
			} else {
				res = TypeValidez.COPIA;
			}
			break;
		case PAGO:
			res = TypeValidez.ORIGINAL;
			break;
		default:
			throw new TipoNoControladoException("Tipus de document no suportat per registre: " + tipoDocumento);
		}
		return res;
	}

	/**
	 * Genera datos interesado.
	 *
	 * @param tipoInteresado
	 *                            tipo
	 * @param datosInteresado
	 *                            datos usuario
	 * @return interesado
	 */
	private Interesado generarInteresado(final TypeInteresado tipoInteresado, final DatosInteresado datosInteresado) {
		TypeDocumentoIdentificacion tipoDocumento = null;
		if (NifUtils.esNie(datosInteresado.getNif())) {
			tipoDocumento = TypeDocumentoIdentificacion.ID_EXTRANJERO;
		} else if (NifUtils.esNifPersonaJuridica(datosInteresado.getNif())) {
			tipoDocumento = TypeDocumentoIdentificacion.CIF;
		} else if (NifUtils.esNifPersonaFisica(datosInteresado.getNif())) {
			tipoDocumento = TypeDocumentoIdentificacion.NIF;
		} else {
			throw new TipoNoControladoException("Tipus d'identificació no controlat");
		}

		final Interesado interesado = new Interesado();
		interesado.setActuaComo(tipoInteresado);
		interesado.setTipoDocumento(tipoDocumento);
		interesado.setDocIdentificacion(datosInteresado.getNif());
		if (tipoDocumento == TypeDocumentoIdentificacion.CIF) {
			interesado.setRazonSocial(datosInteresado.getNombre());
		} else {
			interesado.setNombre(datosInteresado.getNombre());
			interesado.setApellido1(datosInteresado.getApellido1());
			interesado.setApellido2(datosInteresado.getApellido2());
		}

		if (datosInteresado.getContacto() != null) {
			interesado.setPais(datosInteresado.getContacto().getPais());
			interesado.setProvincia(datosInteresado.getContacto().getProvincia());
			interesado.setMunicipio(datosInteresado.getContacto().getMunicipio());
			interesado.setDireccion(datosInteresado.getContacto().getDireccion());
			interesado.setCodigoPostal(datosInteresado.getContacto().getCodigoPostal());
			interesado.setEmail(datosInteresado.getContacto().getEmail());
			interesado.setTelefono(datosInteresado.getContacto().getTelefono());
		}

		return interesado;
	}

	/**
	 * Recupera plantilla customizada para finalizacion registro.
	 *
	 * @param plantillas
	 *                       Plantillas entidad
	 * @param idioma
	 *                       Idioma
	 * @return plantilla (nulo si no existe)
	 */
	private String obtenerPlantillaMailFinalizacion(final List<RPlantillaEntidad> plantillas, final String idioma) {
		// TODO VER SI CACHEAR
		String plantilla = null;
		if (plantillas != null) {
			for (final RPlantillaEntidad pe : plantillas) {
				if ("FR".equals(pe.getTipo()) && idioma.equals(pe.getIdioma())
						&& StringUtils.isNotBlank(pe.getPath())) {
					// Recuperamos plantilla de sistema de ficheros
					final String pathFicherosExternos = configuracion
							.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
					final String pathFile = pathFicherosExternos + pe.getPath();
					byte[] contenidoFic = null;
					try (final FileInputStream fis = new FileInputStream(pathFile);) {
						contenidoFic = IOUtils.toByteArray(fis);
						plantilla = new String(contenidoFic, Constantes.UTF8);
					} catch (final IOException e) {
						throw new AccionPasoNoPermitidaException(
								"Error al accedir a plantilla finalització registre " + pathFile, e);
					}
				}
			}
		}
		return plantilla;
	}

}
