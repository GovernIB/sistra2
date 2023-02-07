package es.caib.sistramit.core.service.component.flujo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.pdf.Linea;
import es.caib.sistra2.commons.pdf.LineaComponente;
import es.caib.sistra2.commons.pdf.PDFDocument;
import es.caib.sistra2.commons.pdf.Seccion;
import es.caib.sistra2.commons.plugins.autenticacion.api.AutenticacionPluginException;
import es.caib.sistra2.commons.plugins.autenticacion.api.IComponenteAutenticacionPlugin;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ArchivoCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.CatalogoPluginException;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.email.api.AnexoEmail;
import es.caib.sistra2.commons.plugins.email.api.EmailPluginException;
import es.caib.sistra2.commons.plugins.email.api.IEmailPlugin;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRegistrar;
import es.caib.sistrages.rest.api.interna.RVersionTramiteControlAcceso;
import es.caib.sistramit.core.api.exception.AutenticacionException;
import es.caib.sistramit.core.api.exception.CatalogoProcedimientosVerificacionException;
import es.caib.sistramit.core.api.exception.EmailException;
import es.caib.sistramit.core.api.exception.ErrorFormularioSoporteException;
import es.caib.sistramit.core.api.exception.FlujoInvalidoException;
import es.caib.sistramit.core.api.exception.GenerarPdfClaveException;
import es.caib.sistramit.core.api.exception.LimiteTramitacionException;
import es.caib.sistramit.core.api.exception.MetodoAutenticacionException;
import es.caib.sistramit.core.api.exception.QaaInicioTramiteException;
import es.caib.sistramit.core.api.exception.QaaRecargaTramiteException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.exception.TramiteNoExisteException;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.flujo.AnexoFichero;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.FlujoTramitacionInfo;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeDestino;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypeFlujoTramitacion;
import es.caib.sistramit.core.api.model.flujo.types.TypeTramite;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeQAA;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.integracion.CatalogoProcedimientosComponent;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosFormularioSoporte;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;
import es.caib.sistramit.core.service.model.flujo.DatosSesionTramitacion;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoTramiteDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsFormularioSoporte;
import es.caib.sistramit.core.service.util.UtilsSTG;

@Component("flujoTramitacionComponent")
@Scope(value = "prototype")
public class FlujoTramitacionComponentImpl implements FlujoTramitacionComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Controlador flujo tramitación. */
	@Autowired
	private ControladorFlujoTramitacion controladorFlujo;

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** CatalogoProcedimientosComponent. */
	@Autowired
	private CatalogoProcedimientosComponent catalogoProcedimientosComponent;

	/** Acceso a persistencia. */
	@Autowired
	private FlujoTramiteDao dao;

	/** Acceso a literales. */
	@Autowired
	private Literales literalesComponent;

	/**
	 * Indica si el flujo es invalido. Se marcará como inválido al generarse una
	 * excepción de tipo FATAL.
	 */
	private boolean flujoInvalido;

	/** Datos de la sesión de tramitación. */
	private DatosSesionTramitacion datosSesion;

	/** Usuario autenticado. */
	private UsuarioAutenticadoInfo usuarioAutenticadoInfo;

	/** Id sesion tramitacion. */
	private String idSesionTramitacion;

	@Override
	public String iniciarTramite(final UsuarioAutenticadoInfo pUsuarioAutenticado, final String idTramite,
			final int version, final String idioma, final String idTramiteCatalogo, final boolean servicioCatalogo,
			final String urlInicio, final Map<String, String> parametrosInicio) {

		// Establecemos info usuario
		usuarioAutenticadoInfo = pUsuarioAutenticado;
		// Generamos id de sesión
		idSesionTramitacion = dao.crearSesionTramitacion();
		// Control de si el flujo es válido
		controlFlujoInvalido();
		// Inicializa datos generales sesión
		this.datosSesion = generarDatosSesion(true, idSesionTramitacion, TypeEstadoTramite.RELLENANDO, idTramite,
				version, idioma, idTramiteCatalogo, servicioCatalogo, urlInicio, parametrosInicio,
				usuarioAutenticadoInfo, new Date(), null);
		// Realizamos operacion de iniciar
		controladorFlujo.iniciarTramite(datosSesion);
		// Retornamos id sesion
		return idSesionTramitacion;
	}

	@Override
	public void cargarTramite(final String pIdSesionTramitacion, final UsuarioAutenticadoInfo pUsuarioAutenticado) {
		usuarioAutenticadoInfo = pUsuarioAutenticado;
		idSesionTramitacion = pIdSesionTramitacion;
		cargarImpl(idSesionTramitacion, false);
	}

	@Override
	public void recargarTramite(final String pIdSesionTramitacion, final UsuarioAutenticadoInfo pUsuarioAutenticado) {
		usuarioAutenticadoInfo = pUsuarioAutenticado;
		idSesionTramitacion = pIdSesionTramitacion;
		cargarImpl(idSesionTramitacion, true);
	}

	@Override
	public DetalleTramite obtenerDetalleTramite() {
		// Control de si el flujo es válido
		controlFlujoInvalido();
		// Devuelve detalle
		return controladorFlujo.detalleTramite(datosSesion);
	}

	@Override
	public DetallePasos obtenerDetallePasos() {
		// Control de si el flujo es válido
		controlFlujoInvalido();
		// Devuelve detalle
		return controladorFlujo.detallePasos(datosSesion);
	}

	@Override
	public void invalidarFlujoTramicacion() {
		flujoInvalido = true;
	}

	@Override
	public ResultadoIrAPaso irAPaso(final String idPaso) {
		return controladorFlujo.irAPaso(datosSesion, idPaso);
	}

	@Override
	public ResultadoIrAPaso irAPasoActual() {
		return controladorFlujo.irAPaso(datosSesion, datosSesion.getDatosTramite().getIdPasoActual());
	}

	@Override
	public ResultadoAccionPaso accionPaso(final String idPaso, final TypeAccionPaso accionPaso,
			final ParametrosAccionPaso parametros) {
		// Control de si el flujo es válido
		controlFlujoInvalido();
		// Ejecuta accion paso
		final ResultadoAccionPaso respuestaAccionPaso = controladorFlujo.accionPaso(datosSesion, idPaso, accionPaso,
				parametros);
		// Devolvemos respuesta
		return respuestaAccionPaso;
	}

	@Override
	public String cancelarTramite() {
		// Obtiene detalle trámite
		final DetalleTramite detalleTramite = controladorFlujo.detalleTramite(datosSesion);
		// Control de si el flujo es válido
		controlFlujoInvalido();
		// Cancela tramite
		controladorFlujo.cancelarTramite(datosSesion);
		// Marcamos flujo como invalido
		invalidarFlujoTramicacion();
		// Devuelve url carpeta
		return detalleTramite.getEntidad().getUrlCarpeta();
	}

	@Override
	public FlujoTramitacionInfo obtenerFlujoTramitacionInfo() {
		final FlujoTramitacionInfo f = new FlujoTramitacionInfo();
		f.setIdSesionTramitacion(getIdSesionTramitacion());
		f.setDebug(isDebugEnabled());
		return f;
	}

	@Override
	public void envioFormularioSoporte(final String nif, final String nombre, final String telefono, final String email,
			final String problemaTipo, final String problemaDesc, final String horarioContacto,
			final AnexoFichero anexo) {

		// Obtenemos entidad / area
		final RConfiguracionEntidad entidad = configuracionComponent
				.obtenerConfiguracionEntidad(datosSesion.getDefinicionTramite().getDefinicionVersion().getIdEntidad());
		final String area = datosSesion.getDefinicionTramite().getDefinicionVersion().getIdArea();

		// Obtiene detalle tramite
		final DetalleTramite detalleTramite = controladorFlujo.detalleTramite(datosSesion);

		// Datos form soporte
		final DatosFormularioSoporte datosFormSoporte = new DatosFormularioSoporte();
		datosFormSoporte.setNif(nif);
		datosFormSoporte.setNombre(nombre);
		datosFormSoporte.setEmail(email);
		datosFormSoporte.setTelefono(telefono);
		datosFormSoporte.setProblemaTipo(problemaTipo);
		datosFormSoporte.setProblemaDesc(problemaDesc);
		datosFormSoporte.setHorarioContacto(horarioContacto);

		// Generamos destinatarios, asunto y mensaje
		final List<String> destinatarios = UtilsFormularioSoporte
				.obtenerDestinatariosFormularioSoporte(datosFormSoporte, entidad, area, datosSesion);
		final String asunto = UtilsFormularioSoporte.obtenerAsuntoFormularioSoporte(literalesComponent,
				datosFormSoporte, entidad, datosSesion);
		final String mensaje = UtilsFormularioSoporte.construyeMensajeSoporteIncidencias(literalesComponent,
				datosFormSoporte, entidad, datosSesion);

		// Anexo
		final List<AnexoEmail> anexos = new ArrayList<>();
		if (anexo != null) {
			// Verifica tamaño máximo
			UtilsFlujo.verificarTamanyoMaximo(detalleTramite.getEntidad().getSoporte().getAnexo().getTamanyo(),
					anexo.getFileContent().length);
			// Añade anexo
			final AnexoEmail a = new AnexoEmail();
			a.setFileName(anexo.getFileName());
			a.setContent(anexo.getFileContent());
			a.setContentType(anexo.getFileContentType());
			anexos.add(a);
		}

		// Enviamos mail
		final IEmailPlugin plgEmail = (IEmailPlugin) configuracionComponent.obtenerPluginGlobal(TypePluginGlobal.EMAIL);
		try {
			if (!plgEmail.envioEmail(destinatarios, asunto, mensaje, anexos)) {
				throw new ErrorFormularioSoporteException("Error enviant mail");
			}
		} catch (final EmailPluginException e) {
			log.error("Error al enviar email", e);
			throw new EmailException("Error al enviar mail", e);
		}

	}

	// -------------------------------------------------------
	// FUNCIONES PRIVADAS
	// -------------------------------------------------------

	/**
	 * En caso de que el flujo no sea válido genera una excepción.
	 */
	private void controlFlujoInvalido() {
		if (flujoInvalido) {
			throw new FlujoInvalidoException();
		}
	}

	/**
	 * Genera datos de sesion de tramitación.
	 *
	 * @param inicio
	 *                                    Indica si es inicio de trámite o recarga
	 *
	 * @param idSesionTramitacion
	 *                                    id sesion tramitacion
	 * @param estado
	 *                                    estado tramite
	 * @param pIdTramite
	 *                                    id tramite
	 * @param pVersion
	 *                                    version
	 * @param pIdioma
	 *                                    idioma
	 * @param pIdTramiteCP
	 *                                    id tramite CP
	 * @param servicioCP
	 *                                    Indica si procedimiento es un servicio
	 * @param pUrlInicio
	 *                                    url inicio
	 * @param pParametrosInicio
	 *                                    parametros inicio
	 * @param pUsuarioAutenticadoInfo
	 *                                    usuario autenticado
	 * @param pFechaCaducidad
	 *                                    fecha caducidad
	 * @return Datos sesion tramitacion
	 * @throws CatalogoPluginException
	 */
	private DatosSesionTramitacion generarDatosSesion(final boolean inicio, final String idSesionTramitacion,
			final TypeEstadoTramite estado, final String pIdTramite, final int pVersion, final String pIdioma,
			final String pIdTramiteCP, final boolean servicioCP, final String pUrlInicio,
			final Map<String, String> pParametrosInicio, final UsuarioAutenticadoInfo pUsuarioAutenticadoInfo,
			final Date pFechaInicio, final Date pFechaCaducidad) {

		// Obtenemos la definición del trámite(si no está el idioma
		// disponible, se coge el idioma por defecto o bien el primero
		// disponible)
		final DefinicionTramiteSTG defTramSTG = configuracionComponent.recuperarDefinicionTramite(pIdTramite, pVersion,
				pIdioma);

		// El idioma puede variar según los idiomas disponibles
		final String idiomaSesion = defTramSTG.getDefinicionVersion().getIdioma();

		// Control limitacion de tramitacion
		controlLimitacionTramitacion(defTramSTG);

		// Control QAA
		controlQAA(inicio, idSesionTramitacion, defTramSTG);

		// Obtenemos las propiedades del trámite en el Catalogo de
		// Procedimientos
		final DefinicionTramiteCP tramiteCP = catalogoProcedimientosComponent.obtenerDefinicionTramite(
				defTramSTG.getDefinicionVersion().getIdEntidad(), pIdTramiteCP, servicioCP, pIdioma);

		// Props tipo trámite, destino, tipo flujo y entorno
		final TypeTramite tipoTramite = TypeTramite
				.fromString(defTramSTG.getDefinicionVersion().getTipoTramite().toLowerCase());
		TypeDestino tipoDestino = TypeDestino.REGISTRO;
		final RPasoTramitacionRegistrar pasoReg = UtilsSTG.devuelveDefinicionPasoRegistrar(defTramSTG);
		if (pasoReg != null && "e".equals(pasoReg.getTipoDestino().toLowerCase())) {
			// Tipo destino vendrá informado si existe paso registro
			tipoDestino = TypeDestino.ENVIO;
		}
		final TypeFlujoTramitacion tipoFlujo = TypeFlujoTramitacion
				.fromString(defTramSTG.getDefinicionVersion().getTipoFlujo());
		if (tipoFlujo == null) {
			throw new TipoNoControladoException(
					"Tipus de fluix no controlat: " + defTramSTG.getDefinicionVersion().getTipoFlujo());
		}

		final String propEntorno = configuracionComponent
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.ENTORNO);
		final TypeEntorno entorno = TypeEntorno.fromString(propEntorno);
		if (entorno == null) {
			throw new TipoNoControladoException("Tipus d'entorn no controlat: " + propEntorno);
		}

		// Validar correspondencia con catalogo procedimientos
		// TODO Implementar aqui:
		// Info STG: defTramSTG
		// Info Rolsac: tramiteCP
		// Debe ser parametrizable por propiedades (sistramit.properties crear prop:
		// catalogoProcedimientos.verificarTramite = true / false)
		// Crear nueva excepcion en core-api:
		// CatalogoProcedimientosVerificacionException

		final String verificarTramiteCatalogo = configuracionComponent
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.VERIFICAR_TRAMITE_CATALOGO);

		if (verificarTramiteCatalogo == null) {
			throw new CatalogoProcedimientosVerificacionException(
					"La propietat de verificació de tràmit catàleg en sistramit.properties no està inclós.");
		}

		// Comprobamos cuando esta activo la verificación del catalogo los siguiente
		// pasos:
		// Paso1. El tramite es telematico
		// Paso2. Los datos del tramiteCP coincide con el tramite actual
		// Paso3. La plataforma coincide con SISTRA2
		if (Boolean.valueOf(verificarTramiteCatalogo)) {

			// Si el tramite del catalogo no está marcado como telematico, error!
			if (!tramiteCP.isTelematico()) {
				throw new CatalogoProcedimientosVerificacionException("El tràmit no està marcat com telemàtic.");
			}

			// Si el tramite del catalogo no está marcado como telematico, error!
			if (tramiteCP.getTramiteTelematico() == null) {
				throw new CatalogoProcedimientosVerificacionException(
						"La informació del tràmit telemàtico no està emplenat.");
			}

			// Si no cuadran el identificador y versión del catalogo con el de
			// sistragesback, error!
			if (tramiteCP.getTramiteTelematico().getTramiteVersion() == null
					|| tramiteCP.getTramiteTelematico().getTramiteIdentificador() == null
					|| tramiteCP.getTramiteTelematico().getTramiteIdentificador().isEmpty()
					|| tramiteCP.getTramiteTelematico().getTramiteVersion()
							.compareTo(defTramSTG.getDefinicionVersion().getVersion()) != 0
					|| !tramiteCP.getTramiteTelematico().getTramiteIdentificador()
							.equals(defTramSTG.getDefinicionVersion().getIdentificador())) {
				throw new CatalogoProcedimientosVerificacionException("No coincideixen la versió o el tràmit.");
			}

		}

		// Creamos sesion
		final DatosSesionTramitacion st = new DatosSesionTramitacion(defTramSTG);
		st.getDatosTramite().setEstadoTramite(estado);
		st.getDatosTramite().setIdSesionTramitacion(idSesionTramitacion);
		st.getDatosTramite().setIdTramite(pIdTramite);
		st.getDatosTramite().setVersionTramite(pVersion);
		st.getDatosTramite().setTipoTramite(tipoTramite);
		st.getDatosTramite().setTipoDestino(tipoDestino);
		st.getDatosTramite().setTipoFlujo(tipoFlujo);
		st.getDatosTramite()
			.setTituloTramite(tramiteCP.getDescripcion() + " - " + tramiteCP.getProcedimiento().getDescripcion());
		st.getDatosTramite().setUrlInicio(pUrlInicio);
		st.getDatosTramite().setParametrosInicio(pParametrosInicio);
		st.getDatosTramite().setDefinicionTramiteCP(tramiteCP);
		st.getDatosTramite().setIdioma(idiomaSesion);
		st.getDatosTramite().setEntorno(entorno);
		st.getDatosTramite().setUsuarioAutenticado(pUsuarioAutenticadoInfo);
		st.getDatosTramite().setNivelAutenticacion(pUsuarioAutenticadoInfo.getAutenticacion());
		st.getDatosTramite().setMetodoAutenticacionInicio(pUsuarioAutenticadoInfo.getMetodoAutenticacion());
		st.getDatosTramite().setIniciador(UtilsFlujo.getDatosUsuario(pUsuarioAutenticadoInfo));
		st.getDatosTramite().setVigente(tramiteCP.isVigente());
		st.getDatosTramite().setPlazoInicio(tramiteCP.getPlazoInicio());
		st.getDatosTramite().setPlazoFin(tramiteCP.getPlazoFin());
		st.getDatosTramite().setFechaCaducidad(pFechaCaducidad);
		st.getDatosTramite().setFechaInicio(pFechaInicio);

		return st;
	}

	/**
	 * Comparamos si nivel tramite es superior al del usuario autenticado.
	 *
	 * @param inicio
	 *                                Indica si es inicio o recarga
	 * @param idSesionTramitacion
	 *                                id Sesion Tramitacion
	 *
	 * @param defTramSTG
	 *                                Definición trámite
	 */
	private void controlQAA(final boolean inicio, final String idSesionTramitacion,
			final DefinicionTramiteSTG defTramSTG) {
		if (this.usuarioAutenticadoInfo.getAutenticacion() != TypeAutenticacion.ANONIMO) {
			// Control QAA
			final TypeQAA qaaTramite = TypeQAA
					.fromString(defTramSTG.getDefinicionVersion().getPropiedades().getNivelQAA() + "");
			final TypeQAA qaaUsuario = this.usuarioAutenticadoInfo.getQaa();
			if (qaaTramite.esSuperior(qaaUsuario)) {
				if (inicio) {
					throw new QaaInicioTramiteException();
				} else {
					throw new QaaRecargaTramiteException(idSesionTramitacion);
				}
			}
			// Control metodo autenticacion
			final List<TypeMetodoAutenticacion> metAut = UtilsSTG.convertMetodosAutenticado(
					defTramSTG.getDefinicionVersion().getPropiedades().getMetodosAutenticacion());
			if (!metAut.contains(this.usuarioAutenticadoInfo.getMetodoAutenticacion())) {
				throw new MetodoAutenticacionException(this.usuarioAutenticadoInfo.getMetodoAutenticacion());
			}
		}
	}

	/**
	 * Control limitación
	 *
	 * @param defTram
	 *                    Definición trámite
	 */
	private void controlLimitacionTramitacion(final DefinicionTramiteSTG defTram) {

		final RVersionTramiteControlAcceso controlAcceso = defTram.getDefinicionVersion().getControlAcceso();
		if (controlAcceso.isLimitarTramitacion()) {

			final String idTramite = defTram.getDefinicionVersion().getIdentificador();
			final int version = defTram.getDefinicionVersion().getVersion();
			final long limitNumero = controlAcceso.getLimiteTramitacionInicios();
			final int limitIntervalo = controlAcceso.getLimiteTramitacionIntervalo();

			final Long total = dao.contadorLimiteTramitacion(idTramite, version, limitIntervalo, new Date());
			if (total.longValue() >= limitNumero) {
				throw new LimiteTramitacionException(idTramite, version, limitNumero, limitIntervalo);
			}
		}

	}

	/**
	 * Devuelve id sesion tramitacion.
	 *
	 * @return id sesion tramitacion
	 */
	private String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	/**
	 * Devuelve si esta habilitado debug.
	 *
	 * @return si esta habilitado debug
	 */
	private boolean isDebugEnabled() {
		boolean res = false;
		if (datosSesion != null && datosSesion.getDefinicionTramite() != null) {
			res = datosSesion.getDefinicionTramite().getDefinicionVersion().getControlAcceso().isDebug();
		}
		return res;
	}

	/**
	 * Realiza carga tramite.
	 *
	 * @param pIdSesionTramitacion
	 *                                 Id sesion tramitacion
	 * @param recarga
	 *                                 Indica si la carga se produce tras un error
	 */
	private void cargarImpl(final String pIdSesionTramitacion, final boolean recarga) {
		// Control de si el flujo es válido
		controlFlujoInvalido();

		// Recuperamos datos de persistencia
		final DatosPersistenciaTramite tram = dao.obtenerTramitePersistencia(pIdSesionTramitacion);
		if (tram == null) {
			throw new TramiteNoExisteException(pIdSesionTramitacion);
		}

		// Verificamos si usuario puede cargar el tramite
		UtilsFlujo.controlCargaTramite(tram, usuarioAutenticadoInfo, recarga);

		// Inicializa datos de sesión
		datosSesion = generarDatosSesion(false, pIdSesionTramitacion, tram.getEstado(), tram.getIdTramite(),
				tram.getVersionTramite(), tram.getIdioma(), tram.getIdTramiteCP(), tram.isServicioCP(),
				tram.getUrlInicio(), tram.getParametrosInicio(), usuarioAutenticadoInfo, tram.getFechaInicio(),
				tram.getFechaCaducidad());

		// Lanzamos operación de cargar
		controladorFlujo.cargarTramite(datosSesion);

	}

	@Override
	public byte[] obtenerClavePdf() {

		// Literales
		final String titulo = datosSesion.getDatosTramite().getTituloTramite();
		final String descripcion = literalesComponent.getLiteral(Literales.FLUJO, "clave.explicacion",
				datosSesion.getDatosTramite().getIdioma());
		final String literalEnlaceDescrip = literalesComponent.getLiteral(Literales.FLUJO, "clave.enlace.descripcion",
				datosSesion.getDatosTramite().getIdioma());
		final String literalEnlacePalabra = literalesComponent.getLiteral(Literales.FLUJO, "clave.enlace.link",
				datosSesion.getDatosTramite().getIdioma());
		final String literalIdTramite = literalesComponent.getLiteral(Literales.FLUJO, "clave.idTramite",
				datosSesion.getDatosTramite().getIdioma());

		// Url reanudar trámite
		// TODO V0 Ver de cifrar id sesion
		final String url = configuracionComponent.obtenerPropiedadConfiguracion(
				TypePropiedadConfiguracion.SISTRAMIT_URL) + ConstantesSeguridad.PUNTOENTRADA_CARGAR_TRAMITE + "?"
				+ ConstantesSeguridad.PARAM_IDSESION + "=" + idSesionTramitacion;

		// Generación PDF
		final PDFDocument documento = new PDFDocument(titulo);

		final Vector<Seccion> lasSecciones = new Vector<>();

		final Seccion seccion = new Seccion(false);

		final List<LineaComponente> componentesDescrip = new ArrayList<>();
		componentesDescrip.add(new LineaComponente(descripcion, documento.getContext().getDefaultFont()));
		final Linea lineaDescrip = new Linea(componentesDescrip);
		lineaDescrip.setPaddingTop(1f);
		seccion.addCampo(lineaDescrip);

		final List<LineaComponente> componentesEnlace = new ArrayList<>();
		componentesEnlace.add(new LineaComponente(literalEnlaceDescrip + " ", documento.getContext().getDefaultFont()));
		componentesEnlace.add(new LineaComponente(literalEnlacePalabra, LineaComponente.getFontEnlace(documento), url));
		final Linea lineaEnlace = new Linea(componentesEnlace);
		lineaEnlace.setPaddingBottom(16f);
		seccion.addCampo(lineaEnlace);

		final List<LineaComponente> componentesIdSesion = new ArrayList<>();
		componentesIdSesion.add(new LineaComponente(literalIdTramite + ": ", documento.getContext().getDefaultFont()));
		componentesIdSesion.add(new LineaComponente(idSesionTramitacion, documento.getContext().getDefaultFont()));
		final Linea lineaIdSesion = new Linea(componentesIdSesion);
		seccion.addCampo(lineaIdSesion);

		lasSecciones.add(seccion);
		documento.setSecciones(lasSecciones);

		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			documento.generate(os, "V");
			return os.toByteArray();
		} catch (final Exception e) {
			throw new GenerarPdfClaveException("Error al generar PDF Clave: " + e.getMessage(), e);
		}

	}

	@Override
	public String logoutTramite() {
		// Obtiene detalle trámite
		final DetalleTramite detalleTramite = controladorFlujo.detalleTramite(datosSesion);

		// Preparamos logout
		String urlLogout = null;
		final IComponenteAutenticacionPlugin plgLogin = (IComponenteAutenticacionPlugin) configuracionComponent
				.obtenerPluginGlobal(TypePluginGlobal.LOGIN);
		try {
			final String urlCarpeta = detalleTramite.getEntidad().getUrlCarpeta();
			urlLogout = plgLogin.iniciarSesionLogout(detalleTramite.getEntidad().getId(),
					detalleTramite.getTramite().getIdioma(), urlCarpeta);
		} catch (final AutenticacionPluginException e) {
			throw new AutenticacionException("Error fent logout", e);
		}

		// Marcamos flujo como invalido
		invalidarFlujoTramicacion();

		return urlLogout;
	}

	@Override
	public AnexoFichero descargarArchivoCP(final String referenciaArchivo) {
		// Recupera archivo de CP
		final DetalleTramite detalleTramite = controladorFlujo.detalleTramite(datosSesion);
		final ArchivoCP archivo = catalogoProcedimientosComponent.descargarArchivo(detalleTramite.getEntidad().getId(),
				referenciaArchivo);
		// Retorna archivo
		final AnexoFichero res = new AnexoFichero();
		res.setFileName(archivo.getFilename());
		res.setFileContent(archivo.getContent());
		return res;
	}

}
