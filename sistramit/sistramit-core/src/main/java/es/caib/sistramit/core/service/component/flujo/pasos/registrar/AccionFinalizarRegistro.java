package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import es.caib.sistra2.commons.plugins.registro.api.*;
import es.caib.sistramit.core.api.exception.JsonException;
import es.caib.sistramit.core.api.model.flujo.*;
import es.caib.sistramit.core.service.model.flujo.*;
import es.caib.sistramit.core.service.model.flujo.ResultadoRegistro;
import es.caib.sistramit.core.service.repository.dao.EntregaTramiteDao;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistra2.commons.utils.ValidacionTipoException;
import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RPlantillaEntidad;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeDestino;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeResultadoRegistro;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.flujo.ConstantesFlujo;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.EnvioAvisoComponent;
import es.caib.sistramit.core.service.component.integracion.EnvioRemotoComponent;
import es.caib.sistramit.core.service.component.integracion.RegistroComponent;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
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
@Component("accionRtFinalizarRegistro")
public final class AccionFinalizarRegistro implements AccionPaso {

	// TODO PENDIENTE PREREGISTRO (ENTREGA PRESENCIAL). VER QUE PASA CON
	// JUSTIFICANTE PARA REGISTRO (NO NECESARIO?) Y PARA PRESENCIAL (NECESARIO?).

	// TODO VERIFICAR SI DOCS FIRMADOS.

	/** DAO acceso BBDD. */
	@Autowired
	private FlujoPasoDao dao;
	/** Literales negocio. */
	@Autowired
	private Literales literales;
	/** Configuración. */
	@Autowired
	private ConfiguracionComponent configuracion;
	/** Envio aviso. */
	@Autowired
	private EnvioAvisoComponent envioAvisoComponent;
	/** DAO entrega. */
	@Autowired
	private EntregaTramiteDao entregaTramiteDao;

	@Override
	public RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final TypeAccionPaso pAccionPasoObtenerAnexo, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Recuperamos parametros
		final ResultadoRegistrar resReg = (ResultadoRegistrar) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "resultadoRegistrar", true);
		final AsientoRegistral asiento = (AsientoRegistral) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "asientoRegistral", true);

		// Obtenemos datos internos del paso
		final DatosInternosPasoRegistrar pDipa = (DatosInternosPasoRegistrar) pDatosPaso.internalData();

		// Actualizamos persistencia
		actualizarPersistencia(pVariablesFlujo.getIdSesionTramitacion(), pDipa, pDpp, resReg, pDefinicionTramite, asiento);

		// Actualizamos detalle
		actualizarDetalleRegistrar(pDipa, pVariablesFlujo, pDpp, resReg);

		// Generamos aviso finalización
		generarAvisoFinalizacion(pDipa, pVariablesFlujo, resReg, pDefinicionTramite);

		// Devolvemos respuesta indicando resultado registro
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
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
	 * @param pIdSesionTramitacion Id sesion tramitacion
	 * @param pDipa                Datos internos paso
	 * @param pDpp                 Datos persistencia
	 * @param resReg               Resultado registro
	 * @param pDefinicionTramite   Definición trámite
	 * @param asientoRegitral	 Asiento registral
	 */
	private void actualizarPersistencia(final String pIdSesionTramitacion, final DatosInternosPasoRegistrar pDipa,
										final DatosPersistenciaPaso pDpp, final ResultadoRegistrar resReg, DefinicionTramiteSTG pDefinicionTramite, AsientoRegistral asientoRegitral) {

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
			// - Si es correcto y esta habilitado entrega, persistimos entrega para que se procese offline
			if (UtilsSTG.isModoEntregaHabilitado(pDefinicionTramite)) {
				entregaTramiteDao.crearEntrega(pIdSesionTramitacion, resReg.getFechaRegistro(),
						pDefinicionTramite.getDefinicionVersion().getIdEntidad(),
						asientoRegitral, UtilsSTG.isModoEntregaInmediato(pDefinicionTramite));
			}
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
