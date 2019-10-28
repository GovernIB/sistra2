package es.caib.sistramit.core.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.ROpcionFormularioSoporte;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistramit.core.api.exception.ErrorNoControladoException;
import es.caib.sistramit.core.api.exception.ErrorScriptException;
import es.caib.sistramit.core.api.exception.FormatoInvalidoFechaFrontException;
import es.caib.sistramit.core.api.exception.ParametrosEntradaIncorrectosException;
import es.caib.sistramit.core.api.exception.TamanyoMaximoAnexoException;
import es.caib.sistramit.core.api.exception.TramiteFinalizadoException;
import es.caib.sistramit.core.api.exception.UsuarioNoPermitidoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.comun.types.TypeValidacion;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.DetalleTramiteInfo;
import es.caib.sistramit.core.api.model.flujo.DocumentoRegistro;
import es.caib.sistramit.core.api.model.flujo.DocumentosRegistroPorTipo;
import es.caib.sistramit.core.api.model.flujo.Entidad;
import es.caib.sistramit.core.api.model.flujo.EntidadRedesSociales;
import es.caib.sistramit.core.api.model.flujo.EntidadSoporte;
import es.caib.sistramit.core.api.model.flujo.EntidadSoporteAnexo;
import es.caib.sistramit.core.api.model.flujo.Firma;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.SoporteOpcion;
import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoFirma;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypeObligatoriedad;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.formulario.MensajeValidacion;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoAnexo;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoFormulario;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;
import es.caib.sistramit.core.service.model.flujo.DatosSesionTramitacion;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.FirmaDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFlujo;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;

/**
 * Clase de utilidades para flujo de tramitación.
 *
 * @author Indra
 *
 */
public final class UtilsFlujo {

	/** Formato fecha en una cadena según la maneja el front. */
	public static final String FORMATO_FECHA_FRONT = "dd/MM/yyyy HH:mm";

	/** Formato fecha generica (YYYYMMDDHHMISS). */
	public static final String FORMATO_FECHA_GENERICA = "yyyyMMddHHmmss";

	/**
	 * Formatea fecha en una cadena según la maneja el front.
	 *
	 * @param fecha
	 *                  Fecha
	 * @return Fecha formateada en dd/MM/yyyy hh:mm
	 */
	public static String formateaFechaFront(final Date fecha) {
		return formateaFecha(fecha, FORMATO_FECHA_FRONT);
	}

	/**
	 * Formatea fecha en una cadena
	 *
	 * @param fecha
	 *                         Fecha
	 * @param formatoFecha
	 *                         formato fecha
	 * @return Fecha formateada
	 */
	public static String formateaFecha(final Date fecha, final String formatoFecha) {
		String res = null;
		if (fecha != null) {
			final SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);
			res = sdf.format(fecha);
		}
		return res;
	}

	/**
	 * Deformatea fecha pasada como una cadena según la maneja el front.
	 *
	 * @param fecha
	 *                  Fecha formateada en dd/MM/yyyy hh:mm
	 * @return Fecha
	 */
	public static Date deformateaFechaFront(final String fecha) {
		return deformateaFecha(fecha, FORMATO_FECHA_FRONT);
	}

	/**
	 * Deformatea fecha de una cadena
	 *
	 * @param fecha
	 *                         Fecha
	 * @param formatoFecha
	 *                         formato fecha
	 * @return Fecha deformateada
	 */
	public static Date deformateaFecha(final String fecha, final String formatoFecha) {
		Date res = null;
		if (fecha != null) {
			final SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);
			try {
				res = sdf.parse(fecha);
			} catch (final ParseException e) {
				throw new FormatoInvalidoFechaFrontException(fecha, e);
			}
		}
		return res;
	}

	/**
	 * Controla si la fecha esta dentro del plazo indicado.
	 *
	 * @param fecha
	 *                        Ahora
	 * @param plazoInicio
	 *                        Plazo desactivacion inicio
	 * @param plazoFin
	 *                        Plazo desactivacion fin
	 * @return Indica si la fecha esta dentro del plazo (si las fechas de plazo son
	 *         nulas se considera dentro del plazo).
	 */
	public static boolean estaDentroPlazo(final Date fecha, final Date plazoInicio, final Date plazoFin) {
		boolean dentroPlazo = true;
		if (plazoInicio != null || plazoFin != null) {
			if (plazoInicio != null && plazoFin == null) {
				dentroPlazo = fecha.compareTo(plazoInicio) >= 0;
			} else if (plazoInicio == null && plazoFin != null) {
				dentroPlazo = fecha.compareTo(plazoFin) <= 0;
			} else {
				dentroPlazo = fecha.compareTo(plazoInicio) >= 0 && fecha.compareTo(plazoFin) <= 0;
			}
		}
		return dentroPlazo;
	}

	/**
	 * Establece la hora a las 00:00:00 horas.
	 *
	 * @param fecha
	 *                  Fecha
	 * @return Fecha establecida a primera hora
	 **/
	public static Date obtenerPrimeraHora(final Date fecha) {
		Date respuesta = null;
		if (fecha != null) {
			final Calendar calendario2 = Calendar.getInstance();
			calendario2.setTime(fecha);
			calendario2.set(Calendar.HOUR_OF_DAY, 0);
			calendario2.set(Calendar.MINUTE, 0);
			calendario2.set(Calendar.SECOND, 0);
			calendario2.set(Calendar.MILLISECOND, 0);
			respuesta = calendario2.getTime();
		}
		return respuesta;
	}

	/**
	 * Establece la hora a las 23:59:59 horas.
	 *
	 * @param fecha
	 *                  Fecha
	 * @return Fecha establecida a primera hora
	 **/
	public static Date obtenerUltimaHora(final Date fecha) {
		Date respuesta = null;
		if (fecha != null) {
			final Calendar calendario2 = Calendar.getInstance();
			calendario2.setTime(fecha);
			calendario2.set(Calendar.HOUR_OF_DAY, ConstantesNumero.N23);
			calendario2.set(Calendar.MINUTE, ConstantesNumero.N59);
			calendario2.set(Calendar.SECOND, ConstantesNumero.N59);
			calendario2.set(Calendar.MILLISECOND, 0);
			respuesta = calendario2.getTime();
		}
		return respuesta;
	}

	/**
	 * Genera datos usuario a partir info autenticación.
	 *
	 * @param usuInfo
	 *                    info autenticación
	 * @return datos usuario
	 */
	public static DatosUsuario getDatosUsuario(final UsuarioAutenticadoInfo usuInfo) {
		DatosUsuario res = null;
		if (usuInfo != null && usuInfo.getAutenticacion() != null
				&& usuInfo.getAutenticacion() == TypeAutenticacion.AUTENTICADO) {
			res = new DatosUsuario();
			res.setNif(usuInfo.getNif());
			res.setNombre(usuInfo.getNombre());
			res.setApellido1(usuInfo.getApellido1());
			res.setApellido2(usuInfo.getApellido2());
			res.setCorreo(usuInfo.getEmail());
		}
		return res;
	}

	/**
	 * Genera datos representante a partir info autenticación.
	 *
	 * @param usuInfo
	 *                    info autenticación
	 * @return datos usuario
	 */
	public static DatosUsuario getDatosRepresentante(final UsuarioAutenticadoInfo usuInfo) {
		DatosUsuario res = null;
		if (usuInfo != null && usuInfo.getAutenticacion() != null && usuInfo.getRepresentante() != null
				&& usuInfo.getAutenticacion() == TypeAutenticacion.AUTENTICADO) {
			res = new DatosUsuario();
			res.setNif(usuInfo.getRepresentante().getNif());
			res.setNombre(usuInfo.getRepresentante().getNombre());
			res.setApellido1(usuInfo.getRepresentante().getApellido1());
			res.setApellido2(usuInfo.getRepresentante().getApellido2());
			res.setCorreo(usuInfo.getRepresentante().getEmail());
		}
		return res;
	}

	/**
	 * Verifica si el usuario puede cargar el tramite.
	 *
	 * @param datosPersistenciaTramite
	 *                                     Datos persistencia tramite
	 * @param usuarioAutenticadoInfo
	 * @param recarga
	 *                                     Indica si la carga viene de una recarga
	 */
	public static void controlCargaTramite(final DatosPersistenciaTramite datosPersistenciaTramite,
			final UsuarioAutenticadoInfo usuarioAutenticadoInfo, final boolean recarga) {

		// No dejamos cargar si se ha cancelado o purgado
		if (datosPersistenciaTramite.isCancelado() || datosPersistenciaTramite.isPurgado()
				|| datosPersistenciaTramite.isMarcadoPurgar()
				|| datosPersistenciaTramite.isPurgaPendientePorPagoRealizado()) {
			throw new TramiteFinalizadoException(datosPersistenciaTramite.getIdSesionTramitacion());
		}

		// Solo dejamos cargar un tramite finalizado tras una recarga
		if (!recarga && datosPersistenciaTramite.getEstado() == TypeEstadoTramite.FINALIZADO) {
			throw new TramiteFinalizadoException(datosPersistenciaTramite.getIdSesionTramitacion());
		}

		// Controlamos si es el usuario autenticado es el iniciador
		if (usuarioAutenticadoInfo.getAutenticacion() != datosPersistenciaTramite.getAutenticacion()
				|| (usuarioAutenticadoInfo.getAutenticacion() == TypeAutenticacion.AUTENTICADO
						&& !usuarioAutenticadoInfo.getNif().equals(datosPersistenciaTramite.getNifIniciador()))) {
			throw new UsuarioNoPermitidoException("Usuario no puede acceder al tramite",
					usuarioAutenticadoInfo.getAutenticacion(), usuarioAutenticadoInfo.getNif());
		}
	}

	/**
	 * Recupera datos entidad
	 *
	 * @param entidad
	 *                                   entidad
	 * @param idioma
	 *                                   idioma
	 * @param configuracionComponent
	 *                                   configuración component
	 * @return entidad
	 */
	public static Entidad detalleTramiteEntidad(final RConfiguracionEntidad entidad, final String idioma,
			final ConfiguracionComponent configuracionComponent) {

		final String urlResources = configuracionComponent.obtenerUrlResources();

		final Entidad e = new Entidad();
		e.setId(entidad.getIdentificador());
		e.setNombre(UtilsSTG.obtenerLiteral(entidad.getDescripcion(), idioma));
		e.setLogo(obtenerUrlPublica(urlResources, entidad.getLogo()));
		e.setCss(obtenerUrlPublica(urlResources, entidad.getCss()));
		e.setContacto(UtilsSTG.obtenerLiteral(entidad.getContactoHTML(), idioma));
		e.setUrlCarpeta(UtilsSTG.obtenerLiteral(entidad.getUrlCarpeta(), idioma));
		e.setUrlMapaWeb(UtilsSTG.obtenerLiteral(entidad.getMapaWeb(), idioma));
		e.setUrlAvisoLegal(UtilsSTG.obtenerLiteral(entidad.getAvisoLegal(), idioma));
		e.setUrlRss(UtilsSTG.obtenerLiteral(entidad.getRss(), idioma));
		final EntidadRedesSociales redes = new EntidadRedesSociales();
		redes.setFacebook(entidad.getUrlFacebook());
		redes.setInstagram(entidad.getUrlInstagram());
		redes.setTwiter(entidad.getUrlTwitter());
		redes.setYoutube(entidad.getUrlYoutube());
		e.setRedes(redes);

		final List<SoporteOpcion> soporteOpciones = new ArrayList<>();
		if (entidad.getAyudaFormulario() != null && !entidad.getAyudaFormulario().isEmpty()) {
			for (final ROpcionFormularioSoporte ro : entidad.getAyudaFormulario()) {
				final SoporteOpcion so = new SoporteOpcion();
				so.setCodigo(ro.getCodigo().toString());
				so.setTitulo(UtilsSTG.obtenerLiteral(ro.getTipo(), idioma));
				so.setDescripcion(UtilsSTG.obtenerLiteral(ro.getDescripcion(), idioma));
				soporteOpciones.add(so);
			}
		}

		final EntidadSoporte soporte = new EntidadSoporte();
		soporte.setCorreo(entidad.getEmail());
		soporte.setTelefono(entidad.getAyudaTelefono());
		soporte.setUrl(entidad.getAyudaUrl());
		soporte.setProblemas(soporteOpciones);
		// Establecemos una configuración con props globales
		// TODO Evaluar si es necesario configurarlo a nivel de entidad
		final EntidadSoporteAnexo anexo = new EntidadSoporteAnexo();
		anexo.setExtensiones(StringUtils.defaultString(configuracionComponent
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.ANEXO_SOPORTE_EXTENSIONES), "pdf"));
		anexo.setTamanyo(StringUtils.defaultString(
				configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.ANEXO_SOPORTE_TAMANYO),
				"1MB"));
		soporte.setAnexo(anexo);
		e.setSoporte(soporte);

		return e;
	}

	/**
	 * Detalle tramite info.
	 *
	 * @param pDatosSesion
	 *                         datos sesion
	 * @return Detalle tramite info.
	 */
	public static DetalleTramiteInfo detalleTramiteInfo(final DatosSesionTramitacion pDatosSesion) {
		final DetalleTramiteInfo detalleTramiteInfo = new DetalleTramiteInfo();
		detalleTramiteInfo.setIdSesion(pDatosSesion.getDatosTramite().getIdSesionTramitacion());
		detalleTramiteInfo.setIdPasoActual(pDatosSesion.getDatosTramite().getIdPasoActual());
		detalleTramiteInfo.setTipoPasoActual(pDatosSesion.getDatosTramite().getDatosPasoActual().getTipo());
		detalleTramiteInfo.setId(pDatosSesion.getDatosTramite().getIdTramite());
		detalleTramiteInfo.setVersion(pDatosSesion.getDatosTramite().getVersionTramite());
		// Indicamos si el tramite esta recien abierto (en el primer paso)
		if (esTramiteNuevo(pDatosSesion)) {
			detalleTramiteInfo.setNuevo(TypeSiNo.SI);
		} else {
			detalleTramiteInfo.setNuevo(TypeSiNo.NO);
		}
		detalleTramiteInfo.setIdioma(pDatosSesion.getDatosTramite().getIdioma());
		detalleTramiteInfo.setTitulo(pDatosSesion.getDatosTramite().getTituloTramite());
		if (pDatosSesion.getDefinicionTramite().getDefinicionVersion().getPropiedades().isPersistente()) {
			detalleTramiteInfo.setPersistente(TypeSiNo.SI);
			detalleTramiteInfo.setDiasPersistencia(
					pDatosSesion.getDefinicionTramite().getDefinicionVersion().getPropiedades().getDiasPersistencia());
		} else {
			detalleTramiteInfo.setPersistente(TypeSiNo.NO);
		}
		detalleTramiteInfo.setTipoFlujo(pDatosSesion.getDatosTramite().getTipoFlujo());
		detalleTramiteInfo.setAutenticacion(pDatosSesion.getDatosTramite().getNivelAutenticacion());
		detalleTramiteInfo.setMetodoAutenticacion(pDatosSesion.getDatosTramite().getMetodoAutenticacionInicio());
		return detalleTramiteInfo;
	}

	/**
	 * Genera detalle tramite.
	 *
	 * @param pDatosSesion
	 *                                   Datos sesion
	 * @param entidadInfo
	 *                                   entidad info
	 * @param configuracionComponent
	 *                                   Configuracion component
	 * @return detalle tramite
	 */
	public static DetalleTramite detalleTramite(final DatosSesionTramitacion pDatosSesion,
			final RConfiguracionEntidad entidadInfo, final ConfiguracionComponent configuracionComponent) {
		final DetalleTramite detalleTramite = new DetalleTramite();
		detalleTramite.setFechaDefinicion(
				UtilsFlujo.formateaFechaFront(pDatosSesion.getDefinicionTramite().getFechaRecuperacion()));
		detalleTramite.setDebug(TypeSiNo.fromBoolean(UtilsSTG.isDebugEnabled(pDatosSesion.getDefinicionTramite())));
		detalleTramite.setEntorno(pDatosSesion.getDatosTramite().getEntorno());
		detalleTramite.setIdPasoActual(pDatosSesion.getDatosTramite().getIdPasoActual());
		detalleTramite.setTramite(detalleTramiteInfo(pDatosSesion));
		detalleTramite.setUsuario(pDatosSesion.getDatosTramite().getIniciador());
		detalleTramite.setEntidad(
				detalleTramiteEntidad(entidadInfo, pDatosSesion.getDatosTramite().getIdioma(), configuracionComponent));
		return detalleTramite;
	}

	/**
	 * Ejecuta script de dependencia de un documento.
	 *
	 * @param script
	 *                                        Script
	 * @param idDocumento
	 *                                        Id documento
	 * @param pVariablesFlujo
	 *                                        Variables flujo
	 * @param pFormulariosCompletadosPaso
	 *                                        Formularios anteriores completados en
	 *                                        el paso actual
	 * @param scriptFlujo
	 *                                        Engine script
	 * @param pDefinicionTramite
	 *                                        Definicion tramite
	 *
	 * @return Obligatoriedad
	 */
	public static TypeObligatoriedad ejecutarScriptDependenciaDocumento(final RScript script, final String idDocumento,
			final VariablesFlujo pVariablesFlujo, final List<DatosDocumento> pFormulariosCompletadosPaso,
			final ScriptExec scriptFlujo, final DefinicionTramiteSTG pDefinicionTramite) {

		// Si es dependiente ejecutamos script de dependencia
		final Map<String, String> codigosError = UtilsSTG.convertLiteralesToMap(script.getLiterales());

		final RespuestaScript rs = scriptFlujo.executeScriptFlujo(TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO,
				idDocumento, script.getScript(), pVariablesFlujo, null, pFormulariosCompletadosPaso, codigosError,
				pDefinicionTramite);

		// El resultado nos tiene que devolver:
		// s (obligatorio)/n(opcional)/d(no tienes que rellenarlo)
		String dependencia = ((String) rs.getResultado());
		if (StringUtils.isBlank(dependencia)) {
			throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO.name(),
					pVariablesFlujo.getIdSesionTramitacion(), idDocumento,
					"El script de dependencia no ha devuelto valor");
		}
		dependencia = dependencia.toLowerCase();
		TypeObligatoriedad obligatorio;
		if (TypeObligatoriedad.OBLIGATORIO.toString().equals(dependencia)) {
			obligatorio = TypeObligatoriedad.OBLIGATORIO;
		} else if (TypeObligatoriedad.OPCIONAL.toString().equals(dependencia)) {
			obligatorio = TypeObligatoriedad.OPCIONAL;
		} else if (TypeObligatoriedad.DEPENDIENTE.toString().equals(dependencia)) {
			obligatorio = TypeObligatoriedad.DEPENDIENTE;
		} else {
			throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO.name(),
					pVariablesFlujo.getIdSesionTramitacion(), idDocumento,
					"El script de dependencia no ha devuelto un valor válido: " + dependencia);
		}

		return obligatorio;
	}

	/**
	 * Método para recuperar un parámetro de una acción de un paso.
	 *
	 * @param pParametros
	 *                         Parametros acción
	 * @param pParametro
	 *                         Nombre parámetro
	 * @param pObligatorio
	 *                         Obligatorio
	 * @return Valor parámetro
	 */
	public static Object recuperaParametroAccionPaso(final ParametrosAccionPaso pParametros, final String pParametro,
			final boolean pObligatorio) {
		final Object valor = pParametros.getParametroEntrada(pParametro);
		if (pObligatorio && ((valor == null) || ((valor instanceof String) && StringUtils.isEmpty((String) valor)))) {
			throw new ParametrosEntradaIncorrectosException("Falta especificar parametro " + pParametro);
		}
		return valor;
	}

	/**
	 * Convierte instancia pasada como string a int.
	 *
	 * @param instanciaStr
	 *                         Instancia como string
	 * @return Devuelve entero
	 */
	public static int instanciaStrToInt(final String instanciaStr) {
		int instancia = ConstantesNumero.N1;
		if (instanciaStr != null) {
			try {
				instancia = Integer.parseInt(instanciaStr);
			} catch (final NumberFormatException nfe) {
				throw new ParametrosEntradaIncorrectosException("El parametro instancia no es un numero", nfe);
			}
		}
		return instancia;
	}

	/**
	 * Indicamos si el tramite esta recien abierto (en el primer paso).
	 *
	 * @param pDatosSesion
	 *                         Datos sesion
	 * @return indica si es nuevo
	 */
	private static boolean esTramiteNuevo(final DatosSesionTramitacion pDatosSesion) {
		boolean nuevo = false;
		// Miramos si estamos en el primer paso
		if (pDatosSesion.getDatosTramite().getIndiceDatosPasoActual() == 0) {
			// Si no hay mas pasos, consideramos que es nuevo
			if (pDatosSesion.getDatosTramite().getDatosPasos().size() == ConstantesNumero.N1) {
				nuevo = true;
			} else {
				// Si hay mas pasos y el siguiente no esta inicializado,
				// consideramos que es nuevo
				if (pDatosSesion.getDatosTramite().getDatosPasos().get(ConstantesNumero.N1)
						.getEstado() == TypeEstadoPaso.NO_INICIALIZADO) {
					nuevo = true;
				}
			}
		}
		return nuevo;
	}

	/**
	 * Convierte datos usuario a persona.
	 *
	 * @param du
	 *               datos usuario
	 * @return persona
	 */
	public static Persona usuarioPersona(final DatosUsuario du) {
		Persona res = null;
		if (du != null) {
			res = new Persona(du.getNif(), du.getApellidosNombre());
		}
		return res;
	}

	/**
	 * Busca los documentos para registrar generados en el flujo de tramitación.
	 *
	 * @param flujoPasoDao
	 *                            DAO paso flujo
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 * @return Lista de documentos que se registrarán
	 */
	public static List<DocumentosRegistroPorTipo> buscarDocumentosParaRegistrar(final FlujoPasoDao flujoPasoDao,
			final VariablesFlujo pVariablesFlujo) {

		final List<DocumentoRegistro> listaFormularios = new ArrayList<>();
		final List<DocumentoRegistro> listaAnexos = new ArrayList<>();
		final List<DocumentoRegistro> listaPagos = new ArrayList<>();

		for (final DatosDocumento datosDocumento : pVariablesFlujo.getDocumentos()) {

			// TODO PENDIENTE DE VER COMO SE IMPLEMENTA PRESENCIAL
			if (datosDocumento.getPresentacion() == TypePresentacion.PRESENCIAL) {
				throw new ErrorNoControladoException("PENDIENTE IMPLEMENTAR ENTREGA PRESENCIAL");
			}

			// Obtenemos datos persistencia documento
			int instancia = ConstantesNumero.N1;
			if (datosDocumento.getTipo() == TypeDocumento.ANEXO) {
				instancia = ((DatosDocumentoAnexo) datosDocumento).getInstancia();
			}
			final DocumentoPasoPersistencia docPersistencia = flujoPasoDao.obtenerDocumentoPersistencia(
					pVariablesFlujo.getIdSesionTramitacion(), datosDocumento.getIdPaso(), datosDocumento.getId(),
					instancia);

			// Crea documento registro y establece datos segun tipo
			final DocumentoRegistro dr = DocumentoRegistro.createNewDocumentoRegistro();
			dr.setDescargable(TypeSiNo.SI);
			dr.setTitulo(datosDocumento.getTitulo());
			dr.setId(datosDocumento.getId());
			dr.setInstancia(ConstantesNumero.N1);
			dr.setFirmar(datosDocumento.getFirmar());

			switch (datosDocumento.getTipo()) {
			case FORMULARIO:
				// Si es un formulario de tipo captura no se registra
				final DatosDocumentoFormulario datosDocumentoFormulario = (DatosDocumentoFormulario) datosDocumento;
				if (!datosDocumentoFormulario.isFormularioCaptura()) {
					// Si el formulario no tiene pdf de visualizacion no se
					// podra descargar
					if (datosDocumentoFormulario.getPdf() == null) {
						dr.setDescargable(TypeSiNo.NO);
					}
					// Info firmas
					dr.setFirmas(
							verificarFirmantes(datosDocumento, docPersistencia, docPersistencia.getFormularioPdf()));
					// Añade a lista formularios
					listaFormularios.add(dr);
				}
				break;
			case ANEXO:
				// Establecemos instancia
				dr.setInstancia(((DatosDocumentoAnexo) datosDocumento).getInstancia());
				// Info firmas
				dr.setFirmas(verificarFirmantes(datosDocumento, docPersistencia, docPersistencia.getFichero()));
				// Añade a lista anexos
				listaAnexos.add(dr);
				break;
			case PAGO:
				// Añade a lista pagos
				listaPagos.add(dr);
				break;
			default:
				// No se debe adjuntar
			}
		}

		// Retorna diferentes listas de documentos
		final List<DocumentosRegistroPorTipo> docsRegPorTipo = new ArrayList<>();
		if (!listaFormularios.isEmpty()) {
			final DocumentosRegistroPorTipo drpt = DocumentosRegistroPorTipo.createNewDocumentosRegistroPorTipo();
			drpt.setTipo(TypeDocumento.FORMULARIO);
			drpt.getListado().addAll(listaFormularios);
			docsRegPorTipo.add(drpt);
		}
		if (!listaAnexos.isEmpty()) {
			final DocumentosRegistroPorTipo drpt = DocumentosRegistroPorTipo.createNewDocumentosRegistroPorTipo();
			drpt.setTipo(TypeDocumento.ANEXO);
			drpt.getListado().addAll(listaAnexos);
			docsRegPorTipo.add(drpt);
		}
		if (!listaPagos.isEmpty()) {
			final DocumentosRegistroPorTipo drpt = DocumentosRegistroPorTipo.createNewDocumentosRegistroPorTipo();
			drpt.setTipo(TypeDocumento.PAGO);
			drpt.getListado().addAll(listaPagos);
			docsRegPorTipo.add(drpt);
		}
		return docsRegPorTipo;
	}

	/**
	 * Verificamos lista de firmantes si han firmado
	 *
	 * @param datosDocumento
	 * @param docPersistencia
	 * @param ficheroFirmado
	 * @return estado firmas
	 */
	private static List<Firma> verificarFirmantes(final DatosDocumento datosDocumento,
			final DocumentoPasoPersistencia docPersistencia, final ReferenciaFichero ficheroFirmado) {
		final List<Firma> firmas = new ArrayList<>();
		if (datosDocumento.getFirmar() == TypeSiNo.SI) {
			for (final Persona p : datosDocumento.getFirmantes()) {
				final Firma fdr = new Firma();
				fdr.setFirmante(p);
				final FirmaDocumentoPersistencia fdp = docPersistencia.obtenerFirmaFichero(ficheroFirmado.getId(),
						p.getNif());
				if (fdp != null) {
					fdr.setEstadoFirma(TypeEstadoFirma.FIRMADO);
					fdr.setFechaFirma(UtilsFlujo.formateaFechaFront(fdp.getFecha()));
					fdr.setDescargable(TypeSiNo.SI);
					fdr.setTipoFirma(fdp.getTipoFirma());
				}
				firmas.add(fdr);
			}
		}
		return firmas;
	}

	/**
	 * Obtiene documentos del tipo.
	 *
	 * @param docsRegPorTipo
	 *                           lista de documentos por tipo
	 * @param tipoDocu
	 *                           tipo documento
	 * @return documentos del tipo
	 */
	public static List<DocumentoRegistro> obtenerDocumentosTipo(final List<DocumentosRegistroPorTipo> docsRegPorTipo,
			final TypeDocumento tipoDocu) {
		List<DocumentoRegistro> res = null;
		for (final DocumentosRegistroPorTipo drt : docsRegPorTipo) {
			if (drt.getTipo() == tipoDocu) {
				res = drt.getListado();
			}
		}
		return res;
	}

	public static boolean isErrorValidacion(final MensajeValidacion validacion) {
		return (validacion != null && validacion.getEstado() == TypeValidacion.ERROR);
	}

	/**
	 * Verifica el tamaño máximo. Genera una excepción en caso de que se sobrepase.
	 *
	 * @param tamMax
	 *                     Tamaño máximo (con sufijo MB o KB)
	 * @param numBytes
	 *                     Número de bytes del fichero
	 */
	public static void verificarTamanyoMaximo(final String tamMax, final int numBytes) {

		final String tam = tamMax.trim();

		int num = 0;
		try {
			final String numStr = tam.substring(0, tam.length() - ConstantesNumero.N2).trim();
			num = Integer.parseInt(numStr);
		} catch (final NumberFormatException nfe) {
			throw new TamanyoMaximoAnexoException(
					"No se ha podido verificar el tamaño maximo. La especificación de tamaño máximo no tiene un formato correcto: "
							+ tamMax,
					nfe);
		}

		if (tam.endsWith("MB")) {
			num = num * ConstantesNumero.N1024 * ConstantesNumero.N1024;
		} else if (tam.endsWith("KB")) {
			num = num * ConstantesNumero.N1024;
		} else {
			throw new TamanyoMaximoAnexoException(
					"No se ha podido verificar el tamaño maximo. La especificación de tamaño máximo no tiene un formato correcto: "
							+ tamMax);
		}

		if (numBytes > num) {
			throw new TamanyoMaximoAnexoException("Se ha sobrepasado el tamaño máximo");
		}

	}

	/**
	 * Calcula url recurso publico.
	 *
	 * @param urlResources
	 *                            url recurso
	 * @param resourcePublico
	 *                            recurso publico (empezando con / )
	 * @return url
	 */
	public static String obtenerUrlPublica(final String urlResources, String resourcePublico) {
		String url = "";
		if (StringUtils.isNotBlank(resourcePublico)) {
			// Eliminamos de la ruta el directorio publico
			final String directorioPublico = "/publico/";
			if (resourcePublico.startsWith(directorioPublico)) {
				resourcePublico = resourcePublico.substring(directorioPublico.length() - 1);
			}
			// Concatenamos
			url = urlResources + resourcePublico;
		}
		return url;
	}
}
