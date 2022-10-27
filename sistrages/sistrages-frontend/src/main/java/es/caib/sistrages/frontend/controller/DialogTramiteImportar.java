package es.caib.sistrages.frontend.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.commons.plugins.registro.api.IRegistroPlugin;
import es.caib.sistra2.commons.plugins.registro.api.LibroOficina;
import es.caib.sistra2.commons.plugins.registro.api.OficinaRegistro;
import es.caib.sistra2.commons.plugins.registro.api.RegistroPluginException;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormulario;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.FilaImportar;
import es.caib.sistrages.core.api.model.comun.FilaImportarArea;
import es.caib.sistrages.core.api.model.comun.FilaImportarBase;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.comun.FilaImportarEntidad;
import es.caib.sistrages.core.api.model.comun.FilaImportarFormateador;
import es.caib.sistrages.core.api.model.comun.FilaImportarFormulario;
import es.caib.sistrages.core.api.model.comun.FilaImportarGestor;
import es.caib.sistrages.core.api.model.comun.FilaImportarResultado;
import es.caib.sistrages.core.api.model.comun.FilaImportarSeccion;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramite;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteRegistro;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;
import es.caib.sistrages.core.api.model.types.TypeListaValores;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.ComponenteService;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.EnvioRemotoService;
import es.caib.sistrages.core.api.service.FormateadorFormularioService;
import es.caib.sistrages.core.api.service.FormularioExternoService;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.SeccionReutilizableService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeImportarTipo;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilImportacion;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteImportar extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogTramiteImportar.class);

	/** Servicio. */
	@Inject
	private FormateadorFormularioService formateadorFormularioService;

	/** Servicio. */
	@Inject
	private ConfiguracionAutenticacionService configuracionAutenticacionService;

	/** Servicio. */
	@Inject
	private FormularioExternoService gestorExternoService;

	/** Servicio. */
	@Inject
	private SeccionReutilizableService seccionReutilizableService;

	/** Servicio. */
	@Inject
	private FormularioInternoService formularioInternoService;

	/** Servicio. */
	@Inject
	private DominioService dominioService;

	/** Servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Servicio. */
	@Inject
	private EntidadService entidadService;

	/** Servicio. */
	@Inject
	private EnvioRemotoService envioRemotoService;

	/** Servicio. */
	@Inject
	private ComponenteService componenteService;

	/** Servicio. */
	@Inject
	private ConfiguracionGlobalService configuracionGlobalService;

	/** Id elemento a tratar. */
	private String id;

	/** Tramite version. */
	private Tramite data;

	/** Contenido. **/
	private byte[] contenido;

	/** Mostrar panel info o panel upload **/
	private boolean mostrarPanelInfo = false;

	/** Version tramite. **/
	private TramiteVersion tramiteVersion;

	/** Tramite. **/
	private Tramite tramite;

	/** Area. **/
	private Area area;

	/** Dominios. **/
	Map<Long, Dominio> dominios = new HashMap<>();

	/** Formularios internos. **/
	Map<Long, DisenyoFormulario> formularios = new HashMap<>();

	/** Ficheros. **/
	Map<Long, Fichero> ficheros = new HashMap<>();

	/** Ficheros content. **/
	Map<Long, byte[]> ficherosContent = new HashMap<>();

	/** Fuente datos. **/
	Map<Long, FuenteDatos> fuentesDatos = new HashMap<>();

	/** Fuente datos content (CSV). **/
	Map<Long, byte[]> fuentesDatosContent = new HashMap<>();

	/** Formateadores. **/
	Map<Long, FormateadorFormulario> formateadores = new HashMap<>();

	/** Gestores externos **/
	Map<Long, GestorExternoFormularios> gestores = new HashMap<>();

	/** Sectores reutilizables **/
	Map<Long, SeccionReutilizable> secciones = new HashMap<>();

	/** Fila entidad. **/
	private FilaImportarEntidad filaEntidad = new FilaImportarEntidad();

	/** Linea 1. **/
	FilaImportarArea filaArea = new FilaImportarArea();

	/** Linea 2. **/
	FilaImportarTramite filaTramite = new FilaImportarTramite();

	/** Linea 3. **/
	FilaImportarTramiteVersion filaTramiteVersion = new FilaImportarTramiteVersion();

	/** Linea 3. **/
	FilaImportarTramiteRegistro filaTramiteRegistro = new FilaImportarTramiteRegistro();

	/** Fila dominios. **/
	final List<FilaImportarDominio> filasDominios = new ArrayList<>();

	/** Filas formateadores. */
	final List<FilaImportarFormateador> filasFormateador = new ArrayList<>();

	/** Filas gestores. */
	final List<FilaImportarGestor> filasGestores = new ArrayList<>();

	/** Filas gestores. */
	final List<FilaImportarSeccion> filasSecciones = new ArrayList<>();

	/** Filas formularios. **/
	List<FilaImportarFormulario> filasFormulario = new ArrayList<>();

	/**
	 * Indica si se debe mostrar el registro de oficina (si entidad es de registro
	 * centralizado o no)
	 **/
	private boolean mostrarFilasFormularios = false;

	/** Mostrar botones formateadores. **/
	private Integer posicionDominio;

	/** Mostrar botones formateadores. **/
	private Integer posicionGestor;
	private Integer posicionSeccion;

	/**
	 * Indica si se debe mostrar el registro de oficina (si entidad es de registro
	 * centralizado o no)
	 **/
	private boolean mostrarRegistroOficina;
	private boolean mostrarRegistroServicio;

	/** A true significa que no hay ninguna fila con error. **/
	private boolean todoCorrecto;

	/** Dir3 zip. **/
	private String dir3zip;
	/** Modo Zip. **/
	private String modoZip;
	/** Revision del zip **/
	private String revisionZip;
	/** Version del zip **/
	private String versionZip;
	/** Version del zip **/
	private String fechaZip;
	/** Version del zip **/
	private String usuarioZip;

	/** Literales que se usan mucho. **/
	private static final String LITERAL_GUION_BAJO = "_";
	private static final String LITERAL_PUNTO = ".";

	/** Refresca cache tramite y dominio. **/
	private boolean refrescarCacheDominio = true;

	/**
	 * Inicialización.
	 */
	public void init() {
		LOGGER.debug("DialogTramiteImportar");
		setMostrarPanelInfo(false);
		todoCorrecto = false;
	}

	/**
	 * carga de fichero.
	 *
	 * @param event el evento
	 * @throws IOException
	 *
	 */
	public void upload(final FileUploadEvent event) throws IOException {

		if (event != null && event.getFile() != null) {
			final UploadedFile file = event.getFile();
			contenido = file.getContents();
			prepararImportacion(contenido);
		} else {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofitxer"));
		}
	}

	/**
	 * Método privado que prepara toda la importación. Pasos a realizar: <br />
	 * 1. Extraer toda la info (tramite, area, tramiteVersion, etc...). <br />
	 * 2. Comprobamos que tiene lo básico: <br />
	 * 2.a. Tiene tramite, tramiteversion y area.<br />
	 * 2.b. Tiene pasos. <br />
	 * 3. Preparamos la info a mostrar.<br />
	 *
	 * @param contenido
	 * @throws IOException
	 */
	private void prepararImportacion(final byte[] contenido) throws IOException {

		todoCorrecto = false;
		final File tempFile = File.createTempFile("fichero", "zip");
		ZipFile zipFile = null;

		// Primero extraemos del zip todos las entidades (tramite, area, tramiteVersion,
		// pasos, entidad, formateadores), dominios y FD)
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(contenido);
			zipFile = new ZipFile(tempFile);

			if (!checkPropiedades(zipFile)) {
				setMostrarPanelInfo(false);
				return;
			}

			final Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				final ZipEntry ze = entries.nextElement();
				final InputStream stream = zipFile.getInputStream(ze);
				final byte[] contenidoFile = IOUtils.toByteArray(stream);
				if (!cargarDato(contenidoFile, ze.getName())) {
					setMostrarPanelInfo(false);
					return;
				}
			}
		} catch (final Exception e) {
			UtilJSF.loggearErrorFront("Error extrayendo la info del zip.", e);
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogTramiteImportar.error.fichero"));
			setMostrarPanelInfo(false);
			return;
		} finally {
			if (zipFile != null) {
				zipFile.close();
			}
		}

		// Se realiza una comprobación básica que tiene lo necesario (un trámite,
		// tramiteVersion, pasos y una area)
		if (tramite == null || tramiteVersion == null || area == null || tramiteVersion.getListaPasos() == null
				|| tramiteVersion.getListaPasos().isEmpty()) {
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteImportar.error.ficheroContenido"));
			setMostrarPanelInfo(false);
			return;
		}

		// Se muestra ya la info, otra cosa es que el botón se vea o no
		this.setMostrarPanelInfo(true);

		prepararImportacion();

	}

	/** Evento cambiar area */
	public void eventoChangeArea() {
		addMessageContext(TypeNivelGravedad.INFO, "Pendiente implementar");

	}

	/**
	 * Cuaderno de carga en los entorno PRE/PRO
	 */
	private void prepararImportacion() {

		// Paso 1. Se comprueba que la entidad por codigo dir3 sean los mismos.
		prepararFlujoEntidad();

		// Paso 2. Preparamos la info a mostrar de area
		prepararFlujoArea();

		// Paso 3. Preparamos la info a mostrar de tramite
		prepararFlujoTramite();

		// Paso 4. Preparamos la info a mostrar de tramite version
		prepararFlujoTramiteVersion();

		// Paso 5. Preparamos la info de registro
		prepararFlujoTramiteRegistro();

		// Paso 6. Preparamos la info de registro
		prepararFlujoTramiteFormulario();

		// Paso 7. Preparamos la info a mostrar de los dominios/FD
		prepararFlujoDominioFD();

		// Paso 8. Preparamos la info a mostrar de los Formateadores.
		prepararFlujoFormateadores();

		// Paso 9. Preparamos la info a mostrar de los gestores.
		prepararFlujoGestores();

		// Paso 10. Preparamos la info a mostrar de los secciones reutilizables.
		prepararFlujoSecciones();

		setMostrarPanelInfo(true);
		checkTodoCorrecto();
	}

	/**
	 * Tiene algún error
	 *
	 * @return
	 */
	public boolean isNingunError() {
		if (filaEntidad == null || filaEntidad.getResultado() == null || filaEntidad.getResultado().isError()
				|| filaArea == null || filaArea.getResultado() == null || filaArea.getResultado().isError()) {
			return false;
		}

		if (filaTramite == null || filaTramite.getResultado() == null || filaTramite.getResultado().isError()
				|| filaTramiteVersion == null || filaTramiteVersion.getResultado() == null
				|| filaTramiteVersion.getResultado().isError() || filaTramiteRegistro == null
				|| filaTramiteRegistro.getResultado() == null || filaTramiteRegistro.getResultado().isError()) {
			return false;
		}

		for (final FilaImportarDominio fila : filasDominios) {
			if (fila == null || fila.getResultado() == null || fila.getResultado().isError()) {
				return false;
			}
		}

		for (final FilaImportarFormateador fila : filasFormateador) {
			if (fila == null || fila.getResultado() == null || fila.getResultado().isError()) {
				return false;
			}
		}

		for (final FilaImportarFormulario fila : filasFormulario) {
			if (fila == null || fila.getResultado() == null || fila.getResultado().isError()) {
				return false;
			}
		}

		for (final FilaImportarSeccion fila : filasSecciones) {
			if (fila == null || fila.getResultado() == null || fila.getResultado().isError()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Comprueba si todas las filas están correctas
	 */
	private void checkTodoCorrecto() {

		if (filaEntidad == null || filaEntidad.getResultado() == null || filaEntidad.getResultado().isErrorOrWarning()
				|| filaArea == null || filaArea.getResultado().isErrorOrWarning()) {
			setTodoCorrecto(false);
			return;
		}

		if (filaTramite == null || filaTramite.getResultado() == null || filaTramite.getResultado().isErrorOrWarning()
				|| filaTramiteVersion == null || filaTramiteVersion.getResultado() == null
				|| filaTramiteVersion.getResultado().isErrorOrWarning() || filaTramiteRegistro == null
				|| filaTramiteRegistro.getResultado() == null
				|| filaTramiteRegistro.getResultado().isErrorOrWarning()) {
			setTodoCorrecto(false);
			return;
		}

		for (final FilaImportarDominio fila : filasDominios) {
			if (fila == null || fila.getResultado() == null || fila.getResultado().isErrorOrWarning()) {
				setTodoCorrecto(false);
				return;
			}
		}

		for (final FilaImportarFormateador fila : filasFormateador) {
			if (fila == null || fila.getResultado() == null || fila.getResultado().isErrorOrWarning()) {
				setTodoCorrecto(false);
				return;
			}
		}

		for (final FilaImportarGestor fila : this.filasGestores) {
			if (fila == null || fila.getResultado() == null || fila.getResultado().isErrorOrWarning()) {
				setTodoCorrecto(false);
				return;
			}
		}

		for (final FilaImportarFormulario fila : filasFormulario) {
			if (fila != null && !fila.isCorrecto()) {
				setTodoCorrecto(false);
				return;
			}
		}

		for (final FilaImportarSeccion fila : filasSecciones) {
			if (fila != null && !fila.isCorrecto()) {
				setTodoCorrecto(false);
				return;
			}
		}

		setTodoCorrecto(true);

	}

	/**
	 * Comprueba si el código DIR3 incluido en el zip coincide con el del usuario.
	 * Sólo permite info, no bloquea la importación.
	 *
	 */
	private void prepararFlujoEntidad() {

		// Comprobamos si coincide el codigo DIR3 del zip con el del usuario
		final String dir3actual = entidadService.loadEntidad(UtilJSF.getIdEntidad()).getCodigoDIR3();
		filaEntidad.setDir3Actual(dir3actual);
		boolean existeDir3ZIP = false;
		if (dir3zip != null) {
			existeDir3ZIP = entidadService.existeCodigoDIR3(dir3zip, null);
		}
		String mensaje;
		if (dir3actual == null || dir3zip == null || !dir3zip.equals(dir3actual)) {
			final Object[] propiedades = new Object[1];
			propiedades[0] = dir3actual;
			mensaje = UtilJSF.getLiteral("dialogTramiteImportar.error.dir3distinto", propiedades);
		} else {
			mensaje = "";
		}

		filaEntidad = FilaImportarEntidad.crearIT(dir3zip, dir3actual, existeDir3ZIP, mensaje);

	}

	/**
	 * Flujo de área (FLUJO_AREA). Las acciones serán:
	 * <ul>
	 * <li>Si es adm. entidad, seleccionar área y crear área.</li>
	 * <li>Si es adm. área, sólo la acción de seleccionar área.</li>
	 * </ul>
	 *
	 * Comprobaciones de area. Obtenemos el areaActual (el existente en BBDD) a
	 * partir del identificador.
	 *
	 * <ul>
	 * <li>Si no existe el área, dejamos en estado warning a la espera de que
	 * seleccione una acción.</li>
	 * <li>Si existe el área, entonces:
	 * <ul>
	 * <li>Si NO pertenece a la misma entidad, error</li>
	 * <li>Si NO tiene permisos, error</li>
	 * <li>Sino, se marca como seleccionado y como revisado.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private void prepararFlujoArea() {

		// Creamos las acciones.
		final List<TypeImportarAccion> acciones = new ArrayList<>();
		acciones.add(TypeImportarAccion.SELECCIONAR);
		if (UtilJSF.isRolAdministrador()) {
			acciones.add(TypeImportarAccion.CREAR);
		}

		final Entidad entidadPropia = entidadService.loadEntidad(UtilJSF.getIdEntidad());
		final Area areaActual = tramiteService.getAreaByIdentificador(entidadPropia.getIdentificador(),
				area.getIdentificador());
		if (areaActual == null) {

			// Lo dejamos como pendiente
			filaArea = FilaImportarArea.crearITnoExiste(area, areaActual);
		} else {

			// Comprobamos que pertenezca a la misma entidad (el area y del usuario).
			final Entidad entidad = entidadService.loadEntidadByArea(areaActual.getCodigo());
			if (entidad.getCodigo().compareTo(UtilJSF.getIdEntidad()) != 0) {

				// No pertenece al mismo area.
				filaArea = FilaImportarArea.crearITerrorEntidadIncorrecta(area, areaActual,
						UtilJSF.getLiteral("dialogTramiteImportar.error.distintaEntidad"));

			} else if (UtilJSF.isRolAdministrador()) {

				// Si es adm. entidad, puede seleccionarlo sin problema
				filaArea = FilaImportarArea.crearITseleccionOK(area, areaActual);

			} else { // Si es de tipo área.

				final List<TypeRolePermisos> permisos = UtilJSF.getSessionBean().getSecurityService()
						.getPermisosDesarrolladorEntidadByArea(areaActual.getCodigo());

				if (!permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {

					// No tiene permisos sobre el área
					filaArea = FilaImportarArea.crearITerrorSinPermisos(area, areaActual,
							UtilJSF.getLiteral("dialogTramiteImportar.error.sinpermisopromocionar"));
				} else {

					// Tiene permisos sobre el area
					filaArea = FilaImportarArea.crearITseleccionOK(area, areaActual);
				}
			}

		}
		filaArea.setAcciones(acciones);
	}

	/**
	 * Flujo de trámite (FLUJO_TRAMITE). Tiene que tener una acción en área, sino
	 * error.
	 *
	 * Comprobaciones de trámite. Obtenemos el tramiteActual (el existente en BBDD)
	 * a partir del identificador.
	 *
	 * <ul>
	 * <li>Si no existe el trámite, marcamos como que se crea el trámite (acciones
	 * crear y seleccionar)</li>
	 * <li>Si existe el trámite, entonces:
	 * <ul>
	 * <li>Marcamos como que se selecciona el trámite (accioens crear y
	 * seleccionar)</li>
	 * <li>Si el trámite pertenece a otra área, mostramos un mensaje (de info, no de
	 * error).</li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private void prepararFlujoTramite() {

		final Tramite tramiteActual;
		if (filaArea.getAccion() == null) {
			tramiteActual = null;
			// Lo dejamos a error a la espera que realice una acción con
			filaTramite = FilaImportarTramite.crearITerrorAreaSinSeleccionar(tramite, tramiteActual,
					UtilJSF.getLiteral("dialogTramiteImportar.error.areanulo"));
			return;
		}

		tramiteActual = tramiteService.getTramiteByIdentificador(tramite.getIdentificador(),
				filaArea.getArea().getCodigo(), null, null);

		if (filaArea.getResultado() == null || filaArea.getResultado() == TypeImportarResultado.WARNING) {
			// Lo dejamos a error a la espera que realice una acción con
			filaTramite = FilaImportarTramite.crearITerrorAreaSinConfirmar(tramite, tramiteActual,
					UtilJSF.getLiteral("dialogTramiteImportar.error.areasinconfirmar"));
			return;
		}

		if (tramiteActual == null) {

			// No existe.
			filaTramite = FilaImportarTramite.crearITtramiteNoExiste(tramite, tramiteActual, filaArea);

		} else {

			filaTramite = FilaImportarTramite.crearITtramiteExiste(tramite, tramiteActual, filaArea);

			// Si no coinciden las áreas, se avisa al usuario.
			if (filaArea.getAccion() == TypeImportarAccion.SELECCIONAR && filaTramite.getTramiteActual().getIdArea()
					.compareTo(filaArea.getAreaActual().getCodigo()) != 0) {
				final Entidad entidad = entidadService.loadEntidadByArea(tramiteActual.getIdArea());
				if (entidad.getCodigo().compareTo(UtilJSF.getIdEntidad()) == 0) {
					filaTramite.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.cambiaareaTramite"));
				} else {
					filaTramite.setEstado(TypeImportarEstado.ERROR);
				}
			}
		}

	}

	/**
	 * Flujo de versión (FLUJO_VERSION. Tiene que tener una acción en trámite, sino
	 * error.
	 *
	 * Comprobaciones de tramite version. Obtenemos el tramiteActual (el existente
	 * en BBDD) a partir del identificador.
	 *
	 * <ul>
	 * <li>Si no existe el trámite, marcamos como que se crea el trámite (acciones
	 * crear y seleccionar)</li>
	 * <li>Si existe el trámite, entonces:
	 * <ul>
	 * <li>Marcamos como que se selecciona el trámite (accioens crear y
	 * seleccionar)</li>
	 * <li>Si el trámite pertenece a otra área, mostramos un mensaje (de info, no de
	 * error).</li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private void prepararFlujoTramiteVersion() {

		TramiteVersion tramiteVersionActual = null;
		if (filaTramite.getAccion() == null || filaTramite.getResultado() == TypeImportarResultado.ERROR) {
			// Lo dejamos a error a la espera que realice una acción con
			filaTramiteVersion = FilaImportarTramiteVersion.crearITerrorTramiteSinSeleccionar(tramiteVersion,
					tramiteVersionActual, UtilJSF.getLiteral("dialogTramiteImportar.error.tramitemal"));
			return;
		}

		if (filaTramite.getResultado() == null || filaTramite.getResultado() == TypeImportarResultado.WARNING) {
			// Lo dejamos a error a la espera que realice una acción con
			filaTramiteVersion = FilaImportarTramiteVersion.crearITerrorTramiteSinSeleccionar(tramiteVersion,
					tramiteVersionActual, UtilJSF.getLiteral("dialogTramiteImportar.error.tramitesinconfirmar"));
			return;
		}

		if (filaTramite.getAccion() == TypeImportarAccion.CREAR) {
			filaTramiteVersion = FilaImportarTramiteVersion.crearITtramiteNuevo(tramiteVersion, tramiteVersionActual);
		} else {

			final List<TramiteVersion> versiones = tramiteService
					.listTramiteVersion(filaTramite.getTramiteActual().getCodigo(), null);

			// Obtenemos la versión del trámite.
			tramiteVersionActual = tramiteService.getTramiteVersionByNumVersion(tramiteVersion.getNumeroVersion(),
					filaTramite.getTramiteActual().getCodigo());
			if (tramiteVersionActual != null) {
				tramiteVersionActual.setListaPasos(tramiteService.getTramitePasos(tramiteVersionActual.getCodigo()));
				tramiteVersionActual.setDebug(true);
			}

			filaTramiteVersion = FilaImportarTramiteVersion.crearITtramiteSeleccionado(tramiteVersion,
					tramiteVersionActual, versiones);

		}

	}

	/**
	 * Flujo de registro (FLUJO_REGISTRO).
	 *
	 * Si tiene paso, se podrá editar los pasos
	 */
	private void prepararFlujoTramiteRegistro() {

		final Entidad entidad = entidadService.loadEntidad(UtilJSF.getIdEntidad());
		if (filaTramite.getAccion() == null || filaTramite.getResultado() == TypeImportarResultado.ERROR) {
			// Lo dejamos a error a la espera que realice una acción con
			filaTramiteRegistro = FilaImportarTramiteRegistro.crearITerrorTramiteSinSeleccionar(tramiteVersion,
					UtilJSF.getLiteral("dialogTramiteImportar.error.tramitemal"));
			if (entidad.isRegistroCentralizado()) {
				filaTramiteRegistro.setMostrarRegistro(false);
			}
			return;
		}

		TramitePaso pasoRegistro = null;
		// Paso 3.3.1 Tramite Version tiene un paso registro
		if (filaTramiteVersion != null && filaTramiteVersion.getTramiteVersion() != null
				&& filaTramiteVersion.getTramiteVersion().getListaPasos() != null) {
			for (final TramitePaso paso : tramiteVersion.getListaPasos()) {
				if (paso instanceof TramitePasoRegistrar) {
					pasoRegistro = paso;
					break;
				}
			}
		}

		if (pasoRegistro == null) {
			filaTramiteRegistro = FilaImportarTramiteRegistro.creaITsinPasoRegistro(tramiteVersion);
		} else {
			filaTramiteRegistro = FilaImportarTramiteRegistro.creaITconPasoRegistro(tramiteVersion, pasoRegistro);

			if (entidad.isRegistroCentralizado() && filaTramiteRegistro.getPasoRegistro() != null &&  ((TramitePasoRegistrar)filaTramiteRegistro.getPasoRegistro()).getEnvioRemoto() == null) {
				filaTramiteRegistro.setResultado(TypeImportarResultado.OK);
				filaTramiteRegistro.setAccion(TypeImportarAccion.NADA);
				filaTramiteRegistro.setMostrarRegistro(false);

			} else {
				try {
					if ( ((TramitePasoRegistrar)filaTramiteRegistro.getPasoRegistro()).getEnvioRemoto() == null) {
						filaTramiteRegistro.setTipoTramite(true);
						rellenarInfoRegistro();
					}
					if ( ((TramitePasoRegistrar)filaTramiteRegistro.getPasoRegistro()).getEnvioRemoto() != null) {
						filaTramiteRegistro.setTipoTramite(false);
						rellenarInfoServicio();
					}
				} catch (final Exception e) {
					UtilJSF.loggearErrorFront("Error intentando carga el plugin de registro al importar", e);
					addMessageContext(TypeNivelGravedad.ERROR,
							UtilJSF.getLiteral("dialogTramiteImportar.error.pluginregistro"));
					filaTramiteRegistro.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.pluginregistro"));
					filaTramiteRegistro.setResultado(TypeImportarResultado.ERROR);
				}
			}
		}
	}

	/**
	 * Revisa si alguno de los paso Rellenar tiene formularios y es de tipo externo
	 * y se tiene el gestor externo de formularios.
	 **/
	private void prepararFlujoTramiteFormulario() {

		filasFormulario = new ArrayList<>();
		mostrarFilasFormularios = false;

		// Paso 3.3.1 Tramite Version tiene un paso registro
		if (filaTramiteVersion != null && filaTramiteVersion.getTramiteVersion() != null
				&& filaTramiteVersion.getTramiteVersion().getListaPasos() != null) {
			for (final TramitePaso paso : tramiteVersion.getListaPasos()) {
				if (paso instanceof TramitePasoRellenar) {
					final TramitePasoRellenar pasoRellenar = (TramitePasoRellenar) paso;
					if (pasoRellenar.getFormulariosTramite() != null) {
						prepararFlujoTramiteFormulario(pasoRellenar);
					}
				}
			}
		}

	}

	/**
	 * Revisa si algun formulario que tiene es externo y tiene un gestor que no
	 * existe.
	 **/
	private void prepararFlujoTramiteFormulario(final TramitePasoRellenar pasoRellenar) {
		// for (final FormularioTramite form : pasoRellenar.getFormulariosTramite()) {
		// if (form.getTipoFormulario() == TypeFormularioGestor.EXTERNO &&
		// form.getFormularioGestorExterno() != null
		// && form.getFormularioGestorExterno().getIdentificador() != null) {
		// final boolean existe = gestorFormularioService
		// .existeFormulario(form.getFormularioGestorExterno().getIdentificador(),
		// null);
		//
		// if (!existe) {
		// mostrarFilasFormularios = true;
		// final FilaImportarFormulario fila = new FilaImportarFormulario();
		// fila.setCorrecto(false);
		// fila.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.gestorExternoInexistente",
		// new String[] { form.getFormularioGestorExterno().getIdentificador() }));
		// filasFormulario.add(fila);
		// }
		// }
		// }
	}

	/**
	 * Flujo de dominio (FLUJO_DOMINIOS). Dependiente de tener area o no. El flujo
	 * se realiza en el UtilImporacion.
	 *
	 * Recorremos los dominios del zip. <br />
	 * Obtenemos el dominio actual a partir del identificador. <br />
	 * Si el dominio tiene fuente de datos, cargamos la FD y sus datos del zip.
	 * <br />
	 * Posteriormente, si el dominio actual existe y tiene FD, la cargamos. <br />
	 * Si no tenemos FD actual pero en el zip si había, intentamos cargar la FD a
	 * partir del identificador de la FD que había en el zip. <br />
	 * <br />
	 *
	 *
	 * @return
	 */
	private void prepararFlujoDominioFD() {
		this.filasDominios.clear();
		for (final Map.Entry<Long, Dominio> entry : dominios.entrySet()) {
			final Dominio dominio = entry.getValue();
			Long idArea = null;
			if (dominio.getAmbito() == TypeAmbito.AREA && this.filaArea.getAccion() == TypeImportarAccion.SELECCIONAR) {
				idArea = this.filaArea.getAreaActual().getCodigo();
			}
			final Dominio dominioActual = dominioService.loadDominioByIdentificador(dominio.getAmbito(),
					dominio.getIdentificador(), UtilJSF.getIdEntidad(), idArea, null);
			FuenteDatos fd = null;
			byte[] fdContent = null;
			FuenteDatos fdActual = null;
			if (dominio.getIdFuenteDatos() != null && this.fuentesDatos.get(dominio.getIdFuenteDatos()) != null) {
				fd = this.fuentesDatos.get(dominio.getIdFuenteDatos());
				fdContent = this.fuentesDatosContent.get(dominio.getIdFuenteDatos());
			}
			if (dominioActual != null && dominioActual.getIdFuenteDatos() != null) {
				fdActual = dominioService.loadFuenteDato(dominioActual.getIdFuenteDatos());
			}

			if (fdActual == null && fd != null) {
				fdActual = dominioService.loadFuenteDato(fd.getAmbito(), fd.getIdentificador(), UtilJSF.getIdEntidad(),
						idArea, null);
			}

			String identificadorArea = null;
			if (this.filaArea != null && this.filaArea.getAccion() == TypeImportarAccion.SELECCIONAR) {
				identificadorArea = this.filaArea.getAreaActual().getIdentificador();
				idArea = this.filaArea.getAreaActual().getCodigo();
			}

			ConfiguracionAutenticacion configuracionAutenticacion = null;
			if (dominio.getTipo() == TypeDominio.CONSULTA_REMOTA && dominio.getConfiguracionAutenticacion() != null
					&& idArea != null) {
				configuracionAutenticacion = configuracionAutenticacionService.getConfiguracionAutenticacion(
						dominio.getAmbito(), dominio.getConfiguracionAutenticacion().getIdentificador(),
						UtilJSF.getIdEntidad(), idArea, null);
			}

			final FilaImportarDominio fila = UtilImportacion.getFilaDominio(dominio, dominioActual, fd, fdContent,
					fdActual, identificadorArea, idArea, configuracionAutenticacion);

			this.filasDominios.add(fila);
		}
	}

	/**
	 * Rellenando la info de registro. <br />
	 *
	 * Primero a carga los datos de la oficina asociados a su codigoDIR3 (su
	 * entidad). Si encuentra concordancia, intenta cargar los datos del
	 * registro.<br />
	 * Luego intenta ver si cuadra el tipo a partir de su codigoDIR (su entidad).
	 *
	 * @throws RegistroPluginException
	 *
	 */
	private void rellenarInfoRegistro() throws RegistroPluginException {
		final IRegistroPlugin iplugin = (IRegistroPlugin) componenteService.obtenerPluginEntidad(TypePlugin.REGISTRO,
				UtilJSF.getIdEntidad());
		final Entidad entidad = entidadService.loadEntidad(UtilJSF.getIdEntidad());

		mostrarRegistroOficina = !entidad.isRegistroCentralizado();

		// Cargamos los datos de oficina y registro (registro sólo si se encuentra la
		// oficina desde donde viene)
		if (!entidad.isRegistroCentralizado() && this.filaTramiteRegistro.getOficina() != null
				&& !this.filaTramiteRegistro.getOficina().isEmpty()) {
			final List<OficinaRegistro> oficinas = iplugin.obtenerOficinasRegistro(entidad.getCodigoDIR3(),
					TypeRegistro.REGISTRO_ENTRADA);
			for (final OficinaRegistro oficina : oficinas) {
				if (oficina.getCodigo().equals(this.filaTramiteRegistro.getOficina())) {
					this.filaTramiteRegistro.setOficinaText(oficina.getNombre());
					final List<LibroOficina> libros = iplugin.obtenerLibrosOficina(entidad.getCodigoDIR3(),
							oficina.getCodigo(), TypeRegistro.REGISTRO_ENTRADA);
					for (final LibroOficina libro : libros) {
						if (libro.getCodigo().equals(this.filaTramiteRegistro.getLibro())) {
							this.filaTramiteRegistro.setLibroText(libro.getNombre());
							break;
						}
					}
					break;
				}
			}

		}

	}


	/**
	 * Rellenando la info de servicio. <br />
	 *
	 * Si no existe el registro, lo crea.
	 *
	 * @throws RegistroPluginException
	 *
	 */
	private void rellenarInfoServicio() throws RegistroPluginException {

		setMostrarRegistroServicio(true);

		//Buscamos el envio si existe.
		Long idArea = null;
		if (this.filaArea != null && this.filaArea.getAccion() == TypeImportarAccion.SELECCIONAR) {
			 idArea = this.filaArea.getAreaActual().getCodigo();
		}
		EnvioRemoto envio = envioRemotoService.getEnvioByIdentificador(this.filaTramiteRegistro.getEnvioRemoto().getAmbito(), this.filaTramiteRegistro.getEnvioRemoto().getIdentificador(), UtilJSF.getIdEntidad(), idArea, null);
		this.filaTramiteRegistro.setEnvioRemoto(envio);
		if (envio == null) {
			this.filaTramiteRegistro.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.envio.noexisteenvio"));
			this.filaTramiteRegistro.setEnvioRemotoAccion(TypeImportarAccion.CREAR);
			this.filaTramiteRegistro.setResultado(TypeImportarResultado.OK);
		} else {
			this.filaTramiteRegistro.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.envio.existeenvio"));
			this.filaTramiteRegistro.setEnvioRemotoAccion(TypeImportarAccion.MANTENER);
			this.filaTramiteRegistro.setResultado(TypeImportarResultado.OK);
		}

	}

	/**
	 * Flujo de formateadores (FLUJO_FORMATEADORES). Totalmente independiente de
	 * otros flujos, depende de la entidad del usuario.
	 *
	 * Comprobaciones de formateadores. Obtenemos el formateador (el existente en
	 * BBDD) a partir del identificador y la entidad.
	 *
	 * <ul>
	 * <li>Si no existe el formateador, marcamos como info y no permitiremos hacer
	 * nada. Se desasignará al importar.</li>
	 * <li>Si existe el formateador, entonces:
	 * <ul>
	 * <li>Si está marcado como 'Desactivar personalización' , lo damos como info y
	 * no permitiremos hacer nada. Se desasignará al importar</li>
	 * <li>Si no está marcado, lo daremos por OK y se asociará al importar.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 *
	 */
	private void prepararFlujoFormateadores() {
		filasFormateador.clear();
		for (final Map.Entry<Long, FormateadorFormulario> entry : formateadores.entrySet()) {
			final FormateadorFormulario formateador = entry.getValue();

			final FormateadorFormulario formateadorActual = formateadorFormularioService
					.getFormateadorFormulario(formateador.getIdentificador());
			if (formateadorActual == null || formateadorActual.isDesactivarPersonalizacion()) {

				// Si no existe o está desactivado la personalización, dan info
				String mensaje = null;
				if (formateadorActual == null) {
					mensaje = UtilJSF.getLiteral("dialogTramiteImportar.error.noexisteformateador");
				} else {
					mensaje = UtilJSF.getLiteral("dialogTramiteImportar.error.existeformateadorBloqueado");
				}
				filasFormateador
						.add(FilaImportarFormateador.crearITformateadorError(formateador, formateadorActual, mensaje));

			} else {

				filasFormateador.add(FilaImportarFormateador.creaITformateadorExiste(formateador, formateadorActual));

			}

		}
	}

	/**
	 * Flujo de gestores (FLUJO_GESTORES). Totalmente independiente de otros flujos,
	 * depende del area.
	 *
	 * Comprobaciones de gestores. Obtenemos el gestor (el existente en BBDD) a
	 * partir del identificador y el area.
	 *
	 * <ul>
	 * <li>Si no existe el gestor formulario externos, marcamos como info y no
	 * permitiremos hacer nada. Se desasignará al importar.</li>
	 * <li>Si existe el gestor, entonces:
	 * <ul>
	 * <li>Si no está marcado, lo daremos por OK y se asociará al importar.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 *
	 */
	private void prepararFlujoGestores() {
		filasGestores.clear();
		for (final Map.Entry<Long, GestorExternoFormularios> entry : gestores.entrySet()) {
			final GestorExternoFormularios gestor = entry.getValue();
			String identificadorArea = null;
			Long idArea = null;
			if (this.filaArea != null && this.filaArea.getAccion() == TypeImportarAccion.SELECCIONAR) {
				identificadorArea = this.filaArea.getAreaActual().getIdentificador();
				idArea = this.filaArea.getAreaActual().getCodigo();
			}
			final GestorExternoFormularios gestorActual = gestorExternoService.getFormularioExternoByIdentificador(
					TypeAmbito.AREA, gestor.getIdentificador(), UtilJSF.getIdEntidad(), idArea, null);

			ConfiguracionAutenticacion configuracionAut = null;
			if (idArea != null && gestor != null && gestor.getConfiguracionAutenticacion() != null) {
				configuracionAut = configuracionAutenticacionService.getConfiguracionAutenticacion(TypeAmbito.AREA,
						gestor.getConfiguracionAutenticacion().getIdentificador(), UtilJSF.getIdEntidad(), idArea,
						null);
			}

			if (gestorActual == null) {

				// Si no existe o está desactivado la personalización, dan info
				final String mensaje = UtilJSF.getLiteral("dialogTramiteImportar.error.noexistegestor");
				filasGestores.add(FilaImportarGestor.crearITgestor(gestor, gestorActual, mensaje, configuracionAut));

			} else {
				if (gestor.getConfiguracionAutenticacion() == null) {
					filasGestores.add(FilaImportarGestor.creaITgestorExiste(gestor, gestorActual, configuracionAut));
				} else {
					if (!identificadorArea.equals(gestorActual.getAreaIdentificador())) {
						// Si no existe o está desactivado la personalización, dan info
						final String mensaje = UtilJSF.getLiteral("dialogTramiteImportar.error.existegestorOtroArea");
						filasGestores.add(FilaImportarGestor.creaITgestorExisteMalConf(gestor, gestorActual, mensaje,
								configuracionAut));
					} else {
						if (configuracionAut == null) {
							final String mensaje = UtilJSF
									.getLiteral("dialogTramiteImportar.error.existegestorsinconfig");
							filasGestores.add(FilaImportarGestor.creaITgestorExisteSinConfig(gestor, gestorActual,
									mensaje, configuracionAut));
						} else {
							filasGestores
									.add(FilaImportarGestor.creaITgestorExiste(gestor, gestorActual, configuracionAut));
						}
					}
				}
			}

		}
	}

	/**
	 * Preparar flujo secciones
	 */
	private void prepararFlujoSecciones() {
		filasSecciones.clear();
		for (final Map.Entry<Long, SeccionReutilizable> entry : secciones.entrySet()) {
			final SeccionReutilizable seccion = entry.getValue();
			Long idArea = null;
			if (this.filaArea != null && this.filaArea.getAccion() == TypeImportarAccion.SELECCIONAR) {
				idArea = this.filaArea.getAreaActual().getCodigo();
			}
			final SeccionReutilizable seccionActual = seccionReutilizableService.getSeccionReutilizableByIdentificador(
					TypeAmbito.ENTIDAD, seccion.getIdentificador(), UtilJSF.getIdEntidad(), idArea);


			if (seccionActual == null) {

				// Si no existe o está desactivado la personalización, dan info
				final String mensaje = UtilJSF.getLiteral("dialogTramiteImportar.info.noexisteseccion");
				filasSecciones.add(FilaImportarSeccion.crearITseccion(seccion, seccionActual, mensaje));

			} else {
				final String mensaje;
				if (seccionActual.getRelease() > seccion.getRelease()) {
					//Si la release es más vieja, hay que avisar (acciones reemplazar o nada)
					 mensaje = UtilJSF.getLiteral("dialogTramiteImportar.info.existeseccionActualMasNueva");
				} else {
					//Si la release es más nueva, hay que avisar  (acciones reemplazar o nada)
					 mensaje = UtilJSF.getLiteral("dialogTramiteImportar.info.existeseccionActualMenosNueva");
				}
				filasSecciones.add(FilaImportarSeccion.crearITseccionExiste(seccion, seccionActual, mensaje));
			}

		}
	}

	/**
	 * Check area.
	 */
	public void checkArea() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.filaArea);
		UtilJSF.openDialog(DialogTramiteImportarAR.class, TypeModoAcceso.EDICION, null, true, 770, 235);
	}

	/**
	 * Check tramite.
	 */
	public void checkTramite() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.filaTramite);
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR_AREA, this.filaArea);

		UtilJSF.openDialog(DialogTramiteImportarTR.class, TypeModoAcceso.EDICION, null, true, 770, 235);
	}

	/**
	 * Check tramite version.
	 */
	public void checkTramiteVersion() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.filaTramiteVersion);
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR_TRAMITE, this.filaTramite);
		UtilJSF.openDialog(DialogTramiteImportarTV.class, TypeModoAcceso.EDICION, null, true, 500, 145);
	}

	/**
	 * Check tramite version.
	 */
	public void checkTramiteRegistro() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.filaTramiteRegistro);
		UtilJSF.openDialog(DialogTramiteImportarRegistro.class, TypeModoAcceso.EDICION, null, true, 500, 170);
	}

	/**
	 * Check dominio.
	 *
	 * @param idDominio
	 */
	public void checkDominio(final String identificador) {

		posicionDominio = null;
		for (int posicion = 0; posicion < filasDominios.size(); posicion++) {
			if (filasDominios.get(posicion).getDominio() != null
					&& filasDominios.get(posicion).getDominio().getIdentificador().equals(identificador)) {
				posicionDominio = posicion;
				break;
			}
		}

		if (posicionDominio != null) {
			final FilaImportarDominio fila = this.filasDominios.get(posicionDominio);
			if(UtilJSF.getIdEntidad() != null) {
				fila.setIdEntidad(UtilJSF.getIdEntidad());
			}
			UtilJSF.getSessionBean().limpiaMochilaDatos();
			final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
			mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, fila);

			Long idArea = null;
			if (this.filaArea != null && this.filaArea.getAccion() == TypeImportarAccion.SELECCIONAR) {
				idArea = this.filaArea.getAreaActual().getCodigo();
			}

			if (idArea != null) {
				mochilaDatos.put(Constantes.AREA, idArea);
			}

			UtilJSF.openDialog(DialogTramiteImportarDominio.class, TypeModoAcceso.EDICION, null, true,
					fila.getAnchura(), fila.getAltura());
		}
	}

	/**
	 * Retorno dialogo del retorno dialogo area.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoDominio(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		final FilaImportarDominio dato = (FilaImportarDominio) respuesta.getResult();
		if (!respuesta.isCanceled()) {
			this.filasDominios.remove(this.filasDominios.get(posicionDominio));
			dato.setResultado(TypeImportarResultado.OK);
			this.filasDominios.add(posicionDominio, dato);
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds()
					.add("formTramite:dataTablaDominios");
			checkTodoCorrecto();
		}
	}

	/**
	 * Retorno dialogo del retorno dialogo area.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoArea(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {

			this.filaArea = (FilaImportarArea) respuesta.getResult();
			this.filaArea.setResultado(TypeImportarResultado.OK);

			this.filaArea.setEstado(TypeImportarEstado.REVISADO);

			// Como el area puede cambiarse o crearse, entonces hay que comprobar el
			// trámite (puede dejar de dar error), los
			// dominios y FDs (los que sean de tipo area puede dar errores)
			prepararFlujoTramite();
			prepararFlujoDominioFD();
			prepararFlujoGestores();
			checkTodoCorrecto();

		}
	}

	/**
	 * Retorno dialogo del retorno dialogo tramite.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoTramite(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			this.filaTramite = (FilaImportarTramite) respuesta.getResult();
			this.filaTramite.setEstado(TypeImportarEstado.REVISADO);
			this.filaTramite.setResultado(TypeImportarResultado.OK);
			prepararFlujoTramiteVersion();
			prepararFlujoTramiteRegistro();
			checkTodoCorrecto();
		}
	}

	/**
	 * Retorno dialogo del retorno dialogo tramite version.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoTramiteVersion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			this.filaTramiteVersion = (FilaImportarTramiteVersion) respuesta.getResult();
			this.filaTramiteVersion.setResultado(TypeImportarResultado.OK);
			checkTodoCorrecto();
		}
	}

	/**
	 * Retorno dialogo del retorno dialogo tramite version.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoRegistro(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			this.filaTramiteRegistro = (FilaImportarTramiteRegistro) respuesta.getResult();
			this.filaTramiteRegistro.setEstado(TypeImportarEstado.REVISADO);
			this.filaTramiteRegistro.setResultado(TypeImportarResultado.OK);
			checkTodoCorrecto();
		}
	}

	/**
	 * Check gestor.
	 *
	 * @param identificador
	 */
	public void checkGestor(final String identificador) {

		posicionGestor = null;
		for (int posicion = 0; posicion < filasGestores.size(); posicion++) {
			if (filasGestores.get(posicion).getGestor() != null
					&& filasGestores.get(posicion).getGestor().getIdentificador().equals(identificador)) {
				posicionGestor = posicion;
				break;
			}
		}

		if (posicionGestor != null) {
			final FilaImportarGestor fila = this.filasGestores.get(posicionGestor);
			UtilJSF.getSessionBean().limpiaMochilaDatos();
			final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
			mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, fila);

			Long idArea = null;
			if (this.filaArea != null && this.filaArea.getAccion() == TypeImportarAccion.SELECCIONAR) {
				idArea = this.filaArea.getAreaActual().getCodigo();
			}

			if (idArea != null) {
				mochilaDatos.put(Constantes.AREA, idArea);
			}
			UtilJSF.openDialog(DialogTramiteImportarGestor.class, TypeModoAcceso.EDICION, null, true, 500, 120);
		}
	}


	/**
	 * Check seccion.
	 *
	 * @param identificador
	 */
	public void checkSeccion(final String identificador) {

		posicionSeccion = null;
		for (int posicion = 0; posicion < filasSecciones.size(); posicion++) {
			if (filasSecciones.get(posicion).getSeccion() != null
					&& filasSecciones.get(posicion).getSeccion().getIdentificador().equals(identificador)) {
				posicionSeccion = posicion;
				break;
			}
		}

		if (posicionSeccion != null) {
			final FilaImportarSeccion fila = this.filasSecciones.get(posicionSeccion);
			UtilJSF.getSessionBean().limpiaMochilaDatos();
			final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
			mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, fila);

			Long idArea = null;
			if (this.filaArea != null && this.filaArea.getAccion() == TypeImportarAccion.SELECCIONAR) {
				idArea = this.filaArea.getAreaActual().getCodigo();
			}

			if (idArea != null) {
				mochilaDatos.put(Constantes.AREA, idArea);
			}
			UtilJSF.openDialog(DialogTramiteImportarSeccion.class, TypeModoAcceso.EDICION, null, true, 500, 120);
		}
	}

	/**
	 * Retorno dialogo del retorno dialogo area.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoGestor(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		final FilaImportarGestor dato = (FilaImportarGestor) respuesta.getResult();
		if (!respuesta.isCanceled()) {
			this.filasGestores.remove(this.filasGestores.get(posicionGestor));
			dato.setResultado(TypeImportarResultado.OK);
			this.filasGestores.add(posicionGestor, dato);
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds()
					.add("formTramite:dataTablaGestores");
			checkTodoCorrecto();
		}
	}

	/**
	 * Retorno dialogo del retorno dialogo area.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoSeccion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		final FilaImportarSeccion dato = (FilaImportarSeccion) respuesta.getResult();
		if (!respuesta.isCanceled()) {
			this.filasSecciones.remove(this.filasSecciones.get(posicionSeccion));
			dato.setResultado(TypeImportarResultado.OK);
			this.filasSecciones.add(posicionSeccion, dato);
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds()
					.add("formTramite:dataTablaSecciones");
			checkTodoCorrecto();
		}
	}

	/**
	 * Carga el dato donde toque.
	 *
	 * @param contenidoFile
	 * @param nombreFichero
	 * @return
	 */
	private boolean cargarDato(final byte[] contenidoFile, final String nombreFichero) {

		if (nombreFichero.equals("version.data")) {
			setTramiteVersion((TramiteVersion) UtilCoreApi.deserialize(contenidoFile));

		} else if (nombreFichero.equals("area.data")) {
			setArea((Area) UtilCoreApi.deserialize(contenidoFile));

		} else if (nombreFichero.equals("tramite.data")) {
			setTramite((Tramite) UtilCoreApi.deserialize(contenidoFile));

		} else if (nombreFichero.startsWith("dominios_")) {
			final Long codigo = obtenerId(nombreFichero);
			final Dominio dominio = (Dominio) UtilCoreApi.deserialize(contenidoFile);
			dominios.put(codigo, dominio);

		} else if (nombreFichero.startsWith("formularios_")) {
			final Long codigo = obtenerId(nombreFichero);
			final DisenyoFormulario formularioInterno = (DisenyoFormulario) UtilCoreApi.deserialize(contenidoFile);
			formularios.put(codigo, formularioInterno);

		} else if (nombreFichero.startsWith("ficheros_")) {
			final Long codigo = obtenerId(nombreFichero);
			final Fichero fichero = (Fichero) UtilCoreApi.deserialize(contenidoFile);
			ficheros.put(codigo, fichero);

		} else if (nombreFichero.startsWith("ficherosContent_")) {
			final Long codigo = obtenerId(nombreFichero);
			ficherosContent.put(codigo, contenidoFile);

		} else if (nombreFichero.startsWith("fuenteDatos_") && nombreFichero.endsWith(".data")) {
			final Long codigo = obtenerId(nombreFichero);
			final FuenteDatos fDatos = (FuenteDatos) UtilCoreApi.deserialize(contenidoFile);
			fuentesDatos.put(codigo, fDatos);

		} else if (nombreFichero.startsWith("fuenteDatos_") && nombreFichero.endsWith(".csv")) {
			final Long codigo = obtenerId(nombreFichero);
			fuentesDatosContent.put(codigo, contenidoFile);

		} else if (nombreFichero.startsWith("formateadores_")) {
			final Long codigo = obtenerId(nombreFichero);
			final FormateadorFormulario formateador = (FormateadorFormulario) UtilCoreApi.deserialize(contenidoFile);
			formateadores.put(codigo, formateador);

		} else if (nombreFichero.startsWith("gestoresExternosFormulario_")) {
			final Long codigo = obtenerId(nombreFichero);
			final GestorExternoFormularios gestor = (GestorExternoFormularios) UtilCoreApi.deserialize(contenidoFile);
			gestores.put(codigo, gestor);

		} else if (nombreFichero.startsWith("seccionesReutilizables_")) {

			final Long codigo = obtenerId(nombreFichero);
			final SeccionReutilizable seccion = (SeccionReutilizable) UtilCoreApi.deserialize(contenidoFile);
			secciones.put(codigo, seccion);

		} else if (!nombreFichero.equals("info.properties")) {
			addMessageContext(TypeNivelGravedad.ERROR, "Fichero desconocido.");
			return false;
		}
		return true;
	}

	/**
	 * Extrae la id del fichero.
	 *
	 * @param nombreFichero
	 * @return
	 */
	private Long obtenerId(final String nombreFichero) {

		final int posicionGuion = nombreFichero.indexOf(LITERAL_GUION_BAJO);
		final int posicionPunto = nombreFichero.indexOf(LITERAL_PUNTO);
		final String sCodigo = nombreFichero.substring(posicionGuion + 1, posicionPunto);
		return Long.valueOf(sCodigo);
	}

	/**
	 * Comprueba por propiedades si cumple lo mínimo. Es decir: <br />
	 * - Misma version. <br />
	 * - Entorno correcto (sólo se puede saber fichero de des a pre y de pre a pro).
	 *
	 * Si devuelve
	 *
	 * @param zipFile
	 * @throws IOException
	 *
	 */
	private boolean checkPropiedades(final ZipFile zipFile) throws IOException {

		final InputStream zipPropertiesStream = zipFile.getInputStream(new ZipEntry("info.properties"));
		final Properties prop = new Properties();
		prop.load(zipPropertiesStream);
		// Checkeamos misma versión.
		if (!prop.getProperty("version").equals(getVersion())) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogTramiteImportar.error.version"));
			return false;
		}

		// Checkeamos si se sube de los entornos que toca
		final TypeEntorno entornoActual = TypeEntorno.fromString(UtilJSF.getEntorno());
		final TypeEntorno entornoFicheroZip = TypeEntorno.fromString(prop.getProperty("entorno"));

		boolean correcto = true;
		if (entornoActual == TypeEntorno.DESARROLLO
				|| (entornoActual == TypeEntorno.PREPRODUCCION && entornoFicheroZip == TypeEntorno.DESARROLLO)
				|| (entornoActual == TypeEntorno.PRODUCCION && entornoFicheroZip == TypeEntorno.PREPRODUCCION)) {

			final TypeImportarTipo tipo = TypeImportarTipo.fromString(prop.getProperty("tipo"));
			if (tipo != TypeImportarTipo.TRAMITE) {
				addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("dialogTramiteImportar.error.tipoTramite"));
				correcto = false;
			}

		} else {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogTramiteImportar.error.entorno"));

			correcto = false;
		}

		modoZip = prop.getProperty("modo");
		dir3zip = prop.getProperty("entidad");
		revisionZip = prop.getProperty("revision");
		versionZip = prop.getProperty("version");
		usuarioZip = prop.getProperty("usuario");
		fechaZip = prop.getProperty("fecha");

		if (correcto && (modoZip == null || !modoZip.equals(Constantes.IMPORTAR_TIPO_IM))) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogTramiteImportar.error.modo"));
			correcto = false;
		}

		return correcto;
	}

	/**
	 * Importar.
	 *
	 * @throws Exception
	 */
	public void importar() throws Exception {

		checkTodoCorrecto();
		if (!todoCorrecto) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogTramiteImportar.warning.check"));
			return;
		}

		// Asociamos los datos de registro
		prepararDatosRegistro();

		// Preparamos el fichero de importar
		final FilaImportar filaImportar = new FilaImportar();
		filaImportar.setFilaArea(filaArea);
		filaImportar.setFilaTramite(filaTramite);
		filaImportar.setFilaTramiteVersion(filaTramiteVersion);
		filaImportar.setFilaTramiteRegistro(filaTramiteRegistro);
		filaImportar.setFilaSecciones(filasSecciones);
		final List<FilaImportarDominio> flsDominio = new ArrayList<>();
		for (final FilaImportarDominio filaDominio : filasDominios) {
			// Las info se ignoran y se quitan de los formularios
			if (filaDominio.getResultado() == TypeImportarResultado.INFO) {
				quitarDominio(filaDominio);
			} else {
				flsDominio.add(filaDominio);
			}
		}
		filaImportar.setFilaDominios(flsDominio);
		final List<FilaImportarFormateador> flsFormateador = new ArrayList<>();
		for (final FilaImportarFormateador filaFormateador : filasFormateador) {
			// Las info se ignoran y se quitan de los formularios
			if (filaFormateador.isCorrecto()) {
				flsFormateador.add(filaFormateador);
			} else {
				quitarFormateador(filaFormateador);
			}
		}
		filaImportar.setFilaFormateador(flsFormateador);
		final List<FilaImportarGestor> flsGestor = new ArrayList<>();
		for (final FilaImportarGestor filaGestor : filasGestores) {
			// Las info se ignoran y se quitan de los formularios
			flsGestor.add(filaGestor);
		}
		filaImportar.setFilaGestor(flsGestor);

		filaImportar.setFormularios(formularios);
		filaImportar.setFicheros(ficheros);
		filaImportar.setFicherosContent(ficherosContent);
		filaImportar.setUsuario(UtilJSF.getSessionBean().getUserName());
		filaImportar.setIdEntidad(UtilJSF.getIdEntidad());
		filaImportar.setModo(Constantes.IMPORTAR_TIPO_IM);
		final FilaImportarResultado resultado = tramiteService.importar(filaImportar);

		ResultadoError re = null;

		if (refrescarCacheDominio) {
			for (final FilaImportarDominio dominio : filasDominios) {
				if (dominio.getAccion() != TypeImportarAccion.MANTENER && dominio.getDominioActual() != null) {

					re = this.refrescar();

				}
			}
		}

		if (re == null) {
			addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.importar.ok"));
		} else if (re.getCodigo() != 1) {
			addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.importar.ok") + ". "
					+ UtilJSF.getLiteral("error.refrescarCache") + ": " + re.getMensaje());
		} else {
			addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("info.importar.ok") + ". " + UtilJSF.getLiteral("info.cache.ok"));
		}
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
		result.setResult(resultado);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Paso que busca el registro y le asocia los datos.
	 */
	private void prepararDatosRegistro() {
		if (filaTramiteVersion.getAccion() != TypeImportarAccion.NADA) {
			for (final TramitePaso paso : this.filaTramiteVersion.getTramiteVersion().getListaPasos()) {
				if (paso instanceof TramitePasoRegistrar) {
					((TramitePasoRegistrar) paso).setCodigoLibroRegistro(this.filaTramiteRegistro.getLibro());
					((TramitePasoRegistrar) paso).setCodigoOficinaRegistro(this.filaTramiteRegistro.getOficina());
					break;
				}
			}
		}
	}

	/**
	 * Quita los dominios de la lista del tramite versión y de los diseños
	 * formularios (componentes de tipo selector)
	 *
	 * @param filaDominio
	 */
	private void quitarDominio(final FilaImportarDominio filaDominio) {
		if (filaDominio.getDominioActual() != null) {
			final Long idDominio = filaDominio.getDominioActual().getCodigo();
			quitarDominiosDeTV(idDominio);
			quitarDominiosDeSelector(idDominio);
		}
	}

	/**
	 * Quita dominios de un componente de tipo selector. Recorre de los formularios,
	 * sus páginas, de ellos sus líneas y a su vez los componentes.
	 *
	 * @param idDominio
	 */
	private void quitarDominiosDeSelector(final Long idDominio) {
		if (formularios != null && !formularios.isEmpty()) {
			for (final Map.Entry<Long, DisenyoFormulario> formulario : formularios.entrySet()) {
				for (final PaginaFormulario pagina : formulario.getValue().getPaginas()) {
					for (final LineaComponentesFormulario linea : pagina.getLineas()) {
						for (final ComponenteFormulario componente : linea.getComponentes()) {
							if (esTipoComponenteSelectorDominio(componente, idDominio.toString())) {
								// Cambiamos a tipo fija, seteamos la info de dominio a nulo
								((ComponenteFormularioCampoSelector) componente)
										.setTipoListaValores(TypeListaValores.FIJA);
								((ComponenteFormularioCampoSelector) componente).setCampoDominioCodigo(null);
								((ComponenteFormularioCampoSelector) componente).setCampoDominioDescripcion(null);
							}

						}
					}
				}
			}
		}
	}

	/**
	 * Comprueba si un componente es de tipo selector, tipo lista de valores y de
	 * tipo dominio
	 *
	 * @param componente
	 * @return
	 */
	private boolean esTipoComponenteSelectorDominio(final ComponenteFormulario componente, final String idDominio) {
		return (componente.getTipo() == TypeObjetoFormulario.SELECTOR
				&& ((ComponenteFormularioCampoSelector) componente).getTipoListaValores() == TypeListaValores.DOMINIO
				&& Long.valueOf(idDominio)
						.compareTo(((ComponenteFormularioCampoSelector) componente).getCodDominio()) == 0);
	}

	/**
	 * Quita dominio de versiones de trámite.
	 *
	 * @param dominio
	 */
	private void quitarDominiosDeTV(final Long dominio) {
		if (this.filaTramiteVersion.getTramiteVersion().getListaDominios() != null) {
			final List<Long> idDominios = new ArrayList<>();
			for (final Long idDominio : this.filaTramiteVersion.getTramiteVersion().getListaDominios()) {
				if (idDominio.compareTo(dominio) == 0) {
					idDominios.add(idDominio);
				}
			}
		}
	}

	/**
	 * Quita los formateadores de las plantillas de los formularios.
	 *
	 * @param filaFormateador
	 */
	private void quitarFormateador(final FilaImportarFormateador filaFormateador) {
		if (formularios != null && !formularios.isEmpty()) {
			for (final Map.Entry<Long, DisenyoFormulario> formulario : formularios.entrySet()) {
				final List<PlantillaFormulario> plantillasBorrar = new ArrayList<>();
				for (final PlantillaFormulario plantilla : formulario.getValue().getPlantillas()) {
					if (plantilla.getIdFormateadorFormulario()
							.compareTo(filaFormateador.getFormateadorFormulario().getCodigo()) == 0) {
						plantillasBorrar.add(plantilla);
					}
				}
				formulario.getValue().getPlantillas().removeAll(plantillasBorrar);
			}
		}
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("tramiteImportarDialog");
	}

	/** Funciones get/set. **/
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public Tramite getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final Tramite data) {
		this.data = data;
	}

	/**
	 * @return the mostrarPanelInfo
	 */
	public boolean isMostrarPanelInfo() {
		return mostrarPanelInfo;
	}

	/**
	 * @param mostrarPanelInfo the mostrarPanelInfo to set
	 */
	public void setMostrarPanelInfo(final boolean mostrarPanelInfo) {
		this.mostrarPanelInfo = mostrarPanelInfo;
	}

	/**
	 * @return the tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion the tramiteVersion to set
	 */
	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * @return the tramite
	 */
	public Tramite getTramite() {
		return tramite;
	}

	/**
	 * @param tramite the tramite to set
	 */
	public void setTramite(final Tramite tramite) {
		this.tramite = tramite;
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(final Area area) {
		this.area = area;
	}

	/**
	 * @return the formularioInternoService
	 */
	public FormularioInternoService getFormularioInternoService() {
		return formularioInternoService;
	}

	/**
	 * @param formularioInternoService the formularioInternoService to set
	 */
	public void setFormularioInternoService(final FormularioInternoService formularioInternoService) {
		this.formularioInternoService = formularioInternoService;
	}

	/**
	 * @return the tramiteService
	 */
	public TramiteService getTramiteService() {
		return tramiteService;
	}

	/**
	 * @param tramiteService the tramiteService to set
	 */
	public void setTramiteService(final TramiteService tramiteService) {
		this.tramiteService = tramiteService;
	}

	/**
	 * @return the contenido
	 */
	public byte[] getContenido() {
		return contenido;
	}

	/**
	 * @param contenido the contenido to set
	 */
	public void setContenido(final byte[] contenido) {
		this.contenido = contenido;
	}

	/**
	 * @return the dominiosId
	 */
	public Map<Long, Dominio> getDominios() {
		return dominios;
	}

	/**
	 * @param dominiosId the dominiosId to set
	 */
	public void setDominios(final Map<Long, Dominio> dominios) {
		this.dominios = dominios;
	}

	/**
	 * @return the formularios
	 */
	public Map<Long, DisenyoFormulario> getFormularios() {
		return formularios;
	}

	/**
	 * @param formularios the formularios to set
	 */
	public void setFormularios(final Map<Long, DisenyoFormulario> formularios) {
		this.formularios = formularios;
	}

	/**
	 * @return the ficheros
	 */
	public Map<Long, Fichero> getFicheros() {
		return ficheros;
	}

	/**
	 * @param ficheros the ficheros to set
	 */
	public void setFicheros(final Map<Long, Fichero> ficheros) {
		this.ficheros = ficheros;
	}

	/**
	 * @return the ficherosContent
	 */
	public Map<Long, byte[]> getFicherosContent() {
		return ficherosContent;
	}

	/**
	 * @param ficherosContent the ficherosContent to set
	 */
	public void setFicherosContent(final Map<Long, byte[]> ficherosContent) {
		this.ficherosContent = ficherosContent;
	}

	/**
	 * @return the fuentesDatos
	 */
	public Map<Long, FuenteDatos> getFuentesDatos() {
		return fuentesDatos;
	}

	/**
	 * @param fuentesDatos the fuentesDatos to set
	 */
	public void setFuentesDatos(final Map<Long, FuenteDatos> fuentesDatos) {
		this.fuentesDatos = fuentesDatos;
	}

	/**
	 * @return the fuentesDatosContent
	 */
	public Map<Long, byte[]> getFuentesDatosContent() {
		return fuentesDatosContent;
	}

	/**
	 * @param fuentesDatosContent the fuentesDatosContent to set
	 */
	public void setFuentesDatosContent(final Map<Long, byte[]> fuentesDatosContent) {
		this.fuentesDatosContent = fuentesDatosContent;
	}

	/**
	 * @return the formateadores
	 */
	public Map<Long, FormateadorFormulario> getFormateadores() {
		return formateadores;
	}

	/**
	 * @param formateadores the formateadores to set
	 */
	public void setFormateadores(final Map<Long, FormateadorFormulario> formateadores) {
		this.formateadores = formateadores;
	}

	/**
	 * @return the filasDominios
	 */
	public List<FilaImportarDominio> getFilasDominios() {
		return filasDominios;
	}

	/**
	 * @return the filasFormateador
	 */
	public List<FilaImportarFormateador> getFilasFormateador() {
		return filasFormateador;
	}

	/**
	 * @return the filaArea
	 */
	public FilaImportarBase getFilaArea() {
		return filaArea;
	}

	/**
	 * @param filaArea the filaArea to set
	 */
	public void setFilaArea(final FilaImportarArea filaArea) {
		this.filaArea = filaArea;
	}

	/**
	 * @return the filaTramite
	 */
	public FilaImportarBase getFilaTramite() {
		return filaTramite;
	}

	/**
	 * @param filaTramite the filaTramite to set
	 */
	public void setFilaTramite(final FilaImportarTramite filaTramite) {
		this.filaTramite = filaTramite;
	}

	/**
	 * @return the filaTramiteVersion
	 */
	public FilaImportarBase getFilaTramiteVersion() {
		return filaTramiteVersion;
	}

	/**
	 * @param filaTramiteVersion the filaTramiteVersion to set
	 */
	public void setFilaTramiteVersion(final FilaImportarTramiteVersion filaTramiteVersion) {
		this.filaTramiteVersion = filaTramiteVersion;
	}

	/**
	 * Para obtener la versión de la configuracion global.
	 *
	 * @return
	 */
	private String getVersion() {
		final ConfiguracionGlobal confGlobal = configuracionGlobalService
				.getConfiguracionGlobal(TypePropiedadConfiguracion.VERSION);
		return confGlobal.getValor();
	}

	/**
	 * @return the filaEntidad
	 */
	public FilaImportarEntidad getFilaEntidad() {
		return filaEntidad;
	}

	/**
	 * @param filaEntidad the filaEntidad to set
	 */
	public void setFilaEntidad(final FilaImportarEntidad filaEntidad) {
		this.filaEntidad = filaEntidad;
	}

	/**
	 * @return the filaTramiteRegistro
	 */
	public final FilaImportarTramiteRegistro getFilaTramiteRegistro() {
		return filaTramiteRegistro;
	}

	/**
	 * @param filaTramiteRegistro the filaTramiteRegistro to set
	 */
	public final void setFilaTramiteRegistro(final FilaImportarTramiteRegistro filaTramiteRegistro) {
		this.filaTramiteRegistro = filaTramiteRegistro;
	}

	/**
	 * @return the todoCorrecto
	 */
	public boolean isTodoCorrecto() {
		return todoCorrecto;
	}

	/**
	 * @param todoCorrecto the todoCorrecto to set
	 */
	public void setTodoCorrecto(final boolean todoCorrecto) {
		this.todoCorrecto = todoCorrecto;
	}

	/**
	 * @return the mostrarRegistroOficina
	 */
	public boolean isMostrarRegistroOficina() {
		return mostrarRegistroOficina;
	}

	/**
	 * @param mostrarRegistroOficina the mostrarRegistroOficina to set
	 */
	public void setMostrarRegistroOficina(final boolean mostrarRegistroOficina) {
		this.mostrarRegistroOficina = mostrarRegistroOficina;
	}

	/**
	 * @return the dir3zip
	 */
	public final String getDir3zip() {
		return dir3zip;
	}

	/**
	 * @param dir3zip the dir3zip to set
	 */
	public final void setDir3zip(final String dir3zip) {
		this.dir3zip = dir3zip;
	}

	/**
	 * @return the modoZip
	 */
	public final String getModoZip() {
		return modoZip;
	}

	/**
	 * @param modoZip the modoZip to set
	 */
	public final void setModoZip(final String modoZip) {
		this.modoZip = modoZip;
	}

	/**
	 * @return the revisionZip
	 */
	public final String getRevisionZip() {
		return revisionZip;
	}

	/**
	 * @param revisionZip the revisionZip to set
	 */
	public final void setRevisionZip(final String revisionZip) {
		this.revisionZip = revisionZip;
	}

	/**
	 * @return the versionZip
	 */
	public final String getVersionZip() {
		return versionZip;
	}

	/**
	 * @param versionZip the versionZip to set
	 */
	public final void setVersionZip(final String versionZip) {
		this.versionZip = versionZip;
	}

	/**
	 * @return the fechaZip
	 */
	public final String getFechaZip() {
		return fechaZip;
	}

	/**
	 * @param fechaZip the fechaZip to set
	 */
	public final void setFechaZip(final String fechaZip) {
		this.fechaZip = fechaZip;
	}

	/**
	 * @return the usuarioZip
	 */
	public final String getUsuarioZip() {
		return usuarioZip;
	}

	/**
	 * @param usuarioZip the usuarioZip to set
	 */
	public final void setUsuarioZip(final String usuarioZip) {
		this.usuarioZip = usuarioZip;
	}

	/**
	 * @return the refrescarCacheDominio
	 */
	public boolean isRefrescarCacheDominio() {
		return refrescarCacheDominio;
	}

	/**
	 * @param refrescarCacheDominio the refrescarCacheDominio to set
	 */
	public void setRefrescarCacheDominio(final boolean refrescarCacheDominio) {
		this.refrescarCacheDominio = refrescarCacheDominio;
	}

	/**
	 * @return the mostrarFilasFormularios
	 */
	public final boolean isMostrarFilasFormularios() {
		return mostrarFilasFormularios;
	}

	/**
	 * @param mostrarFilasFormularios the mostrarFilasFormularios to set
	 */
	public final void setMostrarFilasFormularios(final boolean mostrarFilasFormularios) {
		this.mostrarFilasFormularios = mostrarFilasFormularios;
	}

	/**
	 * @return the filasFormulario
	 */
	public final List<FilaImportarFormulario> getFilasFormulario() {
		return filasFormulario;
	}

	/**
	 * @param filasFormulario the filasFormulario to set
	 */
	public final void setFilasFormulario(final List<FilaImportarFormulario> filasFormulario) {
		this.filasFormulario = filasFormulario;
	}

	/**
	 * @return the filasGestores
	 */
	public List<FilaImportarGestor> getFilasGestores() {
		return filasGestores;
	}

	/**
	 * @return the mostrarRegistroServicio
	 */
	public boolean isMostrarRegistroServicio() {
		return mostrarRegistroServicio;
	}

	/**
	 * @param mostrarRegistroServicio the mostrarRegistroServicio to set
	 */
	public void setMostrarRegistroServicio(boolean mostrarRegistroServicio) {
		this.mostrarRegistroServicio = mostrarRegistroServicio;
	}

	/**
	 * @return the filasSecciones
	 */
	public List<FilaImportarSeccion> getFilasSecciones() {
		return filasSecciones;
	}


}
