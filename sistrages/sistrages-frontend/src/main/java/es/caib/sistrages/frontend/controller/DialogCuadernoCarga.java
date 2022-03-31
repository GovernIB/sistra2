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
import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
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
import es.caib.sistrages.core.api.model.comun.FilaImportarTramite;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteRegistro;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.ComponenteService;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.FormateadorFormularioService;
import es.caib.sistrages.core.api.service.FormularioExternoService;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeImportarTipo;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilCuadernoCarga;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogCuadernoCarga extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogCuadernoCarga.class);

	/** Servicio. */
	@Inject
	private FormateadorFormularioService formateadorFormularioService;

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

	/** Componente service. */
	@Inject
	private ComponenteService componenteService;

	/** Servicio. */
	@Inject
	private ConfiguracionGlobalService configuracionGlobalService;

	/** System servicio. **/
	@Inject
	private SystemService systemService;

	/** Servicio. */
	@Inject
	private FormularioExternoService gestorExternoService;

	/** Servicio. */
	@Inject
	private ConfiguracionAutenticacionService configuracionAutenticacionService;

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

	/** Gestores **/
	Map<Long, GestorExternoFormularios> gestores = new HashMap<>();

	/**
	 * @return the gestores
	 */
	public Map<Long, GestorExternoFormularios> getGestores() {
		return gestores;
	}

	/**
	 * @param gestores
	 *                     the gestores to set
	 */
	public void setGestores(final Map<Long, GestorExternoFormularios> gestores) {
		this.gestores = gestores;
	}

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

	/** Filas formularios. **/
	final List<FilaImportarFormulario> filasFormulario = new ArrayList<>();

	/** Mostrar botones formateadores. **/
	private Integer posicionGestor;

	/**
	 * Indica si se debe mostrar el registro de oficina (si entidad es de registro
	 * centralizado o no)
	 **/
	private boolean mostrarFilasFormularios = false;

	/** Mostrar area. **/
	private boolean mostrarBotonArea = false;
	/** Mostrar tramite. **/
	private boolean mostrarBotonTramite = false;
	/** Mostrar tramite version. **/
	private boolean mostrarBotonTramiteVersion = false;
	/** Mostrar botones formateadores. **/
	private Integer posicionDominio;
	/** Mostrar registro. ***/
	private boolean mostrarRegistro;
	/**
	 * Indica si se debe mostrar el registro de oficina (si entidad es de registro
	 * centralizado o no)
	 **/
	private boolean mostrarRegistroOficina;
	/** Pasos de registro. **/
	private List<TramitePaso> pasosRegistro = new ArrayList<>();

	/** A true significa que no hay ninguna fila con error. **/
	private boolean todoCorrecto;

	/** Literales que se usan mucho. **/
	private static final String LITERAL_GUION_BAJO = "_";
	private static final String LITERAL_PUNTO = ".";

	/** DIR3 del zip. **/
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

	/** Refresca cache tramite y dominio. **/
	private boolean refrescarCacheTramite = true;
	private boolean refrescarCacheDominio = true;

	/**
	 * Inicialización.
	 */
	public void init() {
		setMostrarPanelInfo(false);
		todoCorrecto = false;
	}

	/**
	 * carga de fichero.
	 *
	 * @param event
	 *                  el evento
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
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogCuadernoCarga.error.ficheroContenido"));
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
					UtilJSF.getLiteral("dialogCuadernoCarga.error.ficheroContenido"));
			setMostrarPanelInfo(false);
			return;
		}

		// Se muestra ya la info, otra cosa es que el botón se vea o no
		this.setMostrarPanelInfo(true);

		prepararCuadernoCarga();

	}

	/**
	 * Cuaderno de carga en los entorno PRE/PRO
	 */
	private void prepararCuadernoCarga() {

		// Paso 1. Se comprueba que la entidad por codigo dir3 sean los mismos.
		prepararFlujoEntidad();

		// Paso 2. Preparamos la info a mostrar de area
		prepararFlujoArea();

		// Paso 3. Preparamos la info a mostrar de tramite
		prepararFlujoTramite();

		// Paso 4. Preparamos la info a mostrar de tramite
		prepararFlujoTramiteVersion();

		// Paso 5. Preparamos la info a mostrar de tramite
		prepararFlujoTramiteRegistro();

		// Paso 6. Preparamos la info de registro
		prepararFlujoTramiteFormulario();

		// Paso 7. Preparamos la info a mostrar de los dominios/FD
		prepararFlujoDominioFD();

		// Paso 8. Preparamos la info a mostrar de los Formateadores.
		prepararFlujoFormateadores();

		// Paso 9. preparamos la info a mostrar de los gestores
		prepararFlujoGestores();

		// Seteamos si se ven los botones de area/tramite/tramiteVersion
		this.setMostrarBotonArea(filaArea != null && filaArea.getResultado() != null && filaArea.getResultado().isWarning() && isNingunError());
		this.setMostrarBotonTramite(filaTramite != null && filaTramite.getResultado() != null && filaTramite.getResultado().isWarning() && isNingunError());
		this.setMostrarBotonTramiteVersion(filaTramiteVersion != null && filaTramiteVersion.getResultado() != null && filaTramiteVersion.getResultado().isWarning() && isNingunError());

		setMostrarPanelInfo(true);

		checkTodoCorrecto();
	}

	/**
	 * Tiene algún error
	 *
	 * @return
	 */
	public boolean isNingunError() {
		if (filaEntidad == null || filaEntidad.getResultado() == null || filaEntidad.getResultado().isError() || filaArea == null
				|| filaArea.getResultado() == null || filaArea.getResultado().isError()) {
			return false;
		}

		if (filaTramite == null || filaTramite.getResultado() == null || filaTramite.getResultado().isError() || filaTramiteVersion == null
				|| filaTramiteVersion.getResultado() == null || filaTramiteVersion.getResultado().isError() || filaTramiteRegistro == null
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

		for (final FilaImportarGestor fila : filasGestores) {
			if (fila == null || fila.getResultado() == null || fila.getResultado().isError()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Check dominio.
	 *
	 * @param idDominio
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
	 * Retorno dialogo del retorno dialogo area.
	 *
	 * @param event
	 *                  respuesta dialogo
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
	 * Comprueba si todas las filas están correctas
	 */
	private void checkTodoCorrecto() {

		if (filaEntidad == null || filaEntidad.getResultado() == null || filaEntidad.getResultado().isErrorOrWarning() || filaArea == null
				|| filaArea.getResultado() == null || filaArea.getResultado().isErrorOrWarning()) {
			setTodoCorrecto(false);
			return;
		}

		if (filaTramite == null || filaTramite.getResultado() == null || filaTramite.getResultado().isErrorOrWarning() || filaTramiteVersion == null
				|| filaTramiteVersion.getResultado() == null || filaTramiteVersion.getResultado().isErrorOrWarning() || filaTramiteRegistro == null
						|| filaTramiteRegistro.getResultado() == null || filaTramiteRegistro.getResultado().isErrorOrWarning()) {
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
			if (fila != null && fila.getResultado() != null && !fila.isCorrecto()) {
				setTodoCorrecto(false);
				return;
			}
		}

		setTodoCorrecto(true);

	}

	/**
	 * Comprueba si el código DIR3 incluido en el zip coincide con el del usuario.
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
			mensaje = UtilJSF.getLiteral("dialogTramiteImportar.ok.entidadcorrecta");
		}

		filaEntidad = FilaImportarEntidad.crearCC(dir3zip, dir3actual, existeDir3ZIP, mensaje);
	}

	/**
	 * Flujo de area (FLUJO_AREA)
	 *
	 * Obtenemos el area actual a partir del identificador del area del zip. <br />
	 * Comprobaciones a realizar:
	 * <ul>
	 * <li>Si la entidad del area no es la misma que la actual, provoca un
	 * error.</li>
	 * </ul>
	 * Preparamos la fila del area. Teniendo en cuenta que:
	 * <ul>
	 * <li>Si no existe el area actual, habrá que crearlo y se exige tener rol de
	 * administrador de entidad (sino error!!!)</li>
	 * <li>Si existe, entonces:
	 * <ul>
	 * <li>Se dará un error si no no eres adm. entidad y no tienes permisos de
	 * importar.</li>
	 * <li>Sino, creamos la fila, será de tipo info o warning dependiendo de si
	 * cambia la descripcion.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 *
	 * @return
	 */
	private void prepararFlujoArea() {

		String idEntidad = entidadService.loadEntidad(UtilJSF.getIdEntidad()).getIdentificador();
		final Area areaActual = tramiteService.getAreaByIdentificador(idEntidad, area.getIdentificador());

		if (areaActual == null) {
			// Si no existe el área, sólo el adm. de entidad puede
			if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
				filaArea = FilaImportarArea.crearCCnoExisteAdmEntidad(area, areaActual);
			} else {
				filaArea = FilaImportarArea.crearCCerrorNoAdmEntidad(area, areaActual,
						UtilJSF.getLiteral("dialogCuadernoCarga.error.noexistearea"));
			}
		} else {

			final Entidad entidad = entidadService.loadEntidadByArea(areaActual.getCodigo());
			if (entidad.getCodigo().compareTo(UtilJSF.getIdEntidad()) != 0) {
				filaArea = FilaImportarArea.crearCCerrorEntidadIncorrecta(area, areaActual,
						UtilJSF.getLiteral("dialogCuadernoCarga.error.distintaEntidad"));

			} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {

				// Si es adm. entidad, puede seleccionarlo sin problema
				filaArea = FilaImportarArea.crearCCseleccionOK(area, areaActual);

			} else {

				final List<TypeRolePermisos> permisos = UtilJSF.getSessionBean().getSecurityService()
						.getPermisosDesarrolladorEntidadByArea(areaActual.getCodigo());

				if (!permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
						&& !permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)) {

					filaArea = FilaImportarArea.crearCCerrorSinPermisos(area, areaActual,
							UtilJSF.getLiteral("dialogTramiteImportar.error.sinpermisopromocionar"));

				} else if (area.getDescripcion().equals(areaActual.getDescripcion())) {

					// Si es adm. entidad, puede seleccionarlo sin problema
					filaArea = FilaImportarArea.crearCCseleccionOK(area, areaActual);

				}
			}

		}
	}

	/**
	 * Flujo de trámite (FLUJO_TRAMITE)
	 *
	 *
	 * Obtenemos el tramite actual a partir del identificador del tramite del zip.
	 * <br />
	 * Comprobaciones a realizar:
	 * <ul>
	 * <li>Si la entidad del tramite no es la misma que la del area, provoca un
	 * error. Este error se puede producir si cambias un area de entidad.</li>
	 * </ul>
	 * Posteriormente, si el tramite existe, recuperamos la version. Además,
	 * seteamos el debug a false.
	 *
	 * Preparamos la fila del trámite, teniendo en cuenta que:
	 * <ul>
	 * <li>Si no existe, habrá que crear la fila avisando que no existe.</li>
	 * <li>Si existe, creamos la fila, será de tipo info o warning dependiendo de si
	 * cambia la descripcion.</li>
	 * </ul>
	 *
	 * Preparamos la fila del trámite version, teniendo en cuenta que:
	 * <ul>
	 * <li>Si no existe, habrá que crear la fila avisando que no existe.</li>
	 * <li>Si existe, creamos la fila y avisamos que no existe.</li>
	 * </ul>
	 *
	 * Dependiendo de si tiene le paso de registro o no, se mostrará una ventana
	 * para rellenar los datos (como no hay personalizados, siempre se muestra).
	 *
	 * @return
	 */
	private void prepararFlujoTramite() {
		final Tramite tramiteActual = tramiteService.getTramiteByIdentificador(tramite.getIdentificador(), filaArea.getArea().getCodigo(), null, null);

		// Si el area actual y el tramite actual no tienen el mismo area, provocar un
		// error.
		if (filaArea.getAreaActual() != null && tramiteActual != null
				&& tramiteActual.getIdArea().compareTo(filaArea.getAreaActual().getCodigo()) != 0) {

			filaTramite = FilaImportarTramite.crearCCerrorDistintaArea(tramite, tramiteActual,
					UtilJSF.getLiteral("dialogCuadernoCarga.error.areaDistinta"));

		}

		// Si existe el trámite en el entorno pero no el area, es que algo está mal.
		else if (filaArea.getAreaActual() == null && tramiteActual != null) {

			filaTramite = FilaImportarTramite.crearCCerrorTramiteAreaIncorrecta(tramite, tramiteActual,
					UtilJSF.getLiteral("dialogCuadernoCarga.error.tramiteareaincorrecto"));

		}

		// Si no existe, creamos una fila de tipo info informando que no existe el
		// trámite.
		// Si existe, comprobamos si cambia la descripción (si cambia entonces damos
		// warning sino damos info)
		else if (tramiteActual == null) {

			filaTramite = FilaImportarTramite.crearCCnoExiste(tramite, tramiteActual);

		} else {

			filaTramite = FilaImportarTramite.crearCCexiste(tramite, tramiteActual);

		}

	}

	/**
	 * Flujo de versión (FLUJO_VERSION)
	 *
	 * Comprobaciones de tramite version. Obtenemos el tramiteActual (el existente
	 * en BBDD) a partir del identificador.
	 *
	 * <ul>
	 * <li>Si no existe el trámite, marcamos como que se crea el trámite (acciones
	 * crear y seleccionar)</li>
	 * <li>Si existe el trámite, entonces:
	 * <ul>
	 * <li>Si la release actual es mayor que la del zip, error</li>
	 * <li>Si la release es igual o antigua, se reemplazará.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	public void prepararFlujoTramiteVersion() {

		TramiteVersion tramiteVersionActual = null;
		// Obtenemos la version y sus pasos si el trámite existe. Además, seteamos debug
		// a false.
		if (filaTramite.getTramiteActual() != null) {

			tramiteVersionActual = tramiteService.getTramiteVersionByNumVersion(tramiteVersion.getNumeroVersion(),
					filaTramite.getTramiteActual().getCodigo());
			if (tramiteVersionActual != null) {
				tramiteVersionActual.setListaPasos(tramiteService.getTramitePasos(tramiteVersionActual.getCodigo()));
				tramiteVersionActual.setDebug(false);
			}
		}

		// Paso 3.3. Tramite Version.
		if (tramiteVersionActual == null) {
			filaTramiteVersion = FilaImportarTramiteVersion.crearCCcrearVersion(tramiteVersion, tramiteVersionActual);
		} else if (tramiteVersionActual.getRelease() > tramiteVersion.getRelease()) {
			filaTramiteVersion = FilaImportarTramiteVersion.crearCCerrorVersionAntiguo(tramiteVersion,
					tramiteVersionActual, UtilJSF.getLiteral("dialogCuadernoCarga.error.releaseAntiguo"));
		} else {
			filaTramiteVersion = FilaImportarTramiteVersion.crearCCversionReemplazar(tramiteVersion,
					tramiteVersionActual);
		}

	}

	/**
	 * Flujo de registro (FLUJO_REGISTRO).
	 *
	 * <ul>
	 * <li>Si tiene el paso de registro, se creará una fila importar registro.</li>
	 * <li>Si NO tiene le paso de registro, NO se creará una fila importar registro
	 * </li>
	 * </ul>
	 *
	 * No da error.
	 */
	private void prepararFlujoTramiteRegistro() {

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
			filaTramiteRegistro = FilaImportarTramiteRegistro.creaCCsinPasoRegistro(tramiteVersion);
		} else {
			filaTramiteRegistro = FilaImportarTramiteRegistro.creaCCconPasoRegistro(tramiteVersion, pasoRegistro);
			final Entidad entidad = entidadService.loadEntidad(UtilJSF.getIdEntidad());
			if (entidad.isRegistroCentralizado()) {
				filaTramiteRegistro.setResultado(TypeImportarResultado.OK);
				filaTramiteRegistro.setAccion(TypeImportarAccion.NADA);
				filaTramiteRegistro.setMostrarRegistro(false);
			} else {
				try {
					rellenarInfoRegistro();
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
	 *
	 * Rellenando la info de registro.
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
		if (mostrarRegistroOficina && this.filaTramiteRegistro.getOficina() != null
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
	 * Flujo de formateadores (FLUJO_FORMATEADORES)
	 *
	 *
	 * Recorremos los formateadores asociados en el zip. <br />
	 * <ul>
	 * <li>Será erróneo si NO existe o si existe pero está desactivada la
	 * personalización.</li>
	 * <li>Será correcto si existe y está desactivada la personalización.</li>
	 * </ul>
	 *
	 * @return
	 */
	private void prepararFlujoFormateadores() {
		for (final Map.Entry<Long, FormateadorFormulario> entry : formateadores.entrySet()) {
			final FormateadorFormulario formateador = entry.getValue();

			final FormateadorFormulario formateadorActual = formateadorFormularioService
					.getFormateadorFormulario(formateador.getIdentificador());
			if (formateadorActual == null || formateadorActual.isDesactivarPersonalizacion()) {
				// Si no existe o está desactivado la personalización, dar error
				String mensaje = null;
				if (formateadorActual == null) {
					mensaje = UtilJSF.getLiteral("dialogCuadernoCarga.error.noexisteformateador");
				} else {
					mensaje = UtilJSF.getLiteral("dialogCuadernoCarga.error.existeformateadorBloqueado");
				}

				filasFormateador
						.add(FilaImportarFormateador.crearCCformateadorError(formateador, formateadorActual, mensaje));

			} else {

				filasFormateador.add(FilaImportarFormateador.crearCCformateadorOk(formateador, formateadorActual));

			}
		}
	}

	/**
	 * Revisa si alguno de los paso Rellenar tiene formularios y es de tipo externo
	 * y se tiene el gestor externo de formularios.
	 **/
	private void prepararFlujoTramiteFormulario() {

		filasFormulario.clear();
		setMostrarFilasFormularios(false);

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

			final GestorExternoFormularios gestorActual = gestorExternoService
					.getFormularioExternoByIdentificador(TypeAmbito.AREA, gestor.getIdentificador(), UtilJSF.getIdEntidad(), filaArea.getArea().getCodigo(), null );

			String identificadorArea = null;
			Long idArea = null;
			if (this.filaArea != null && this.filaArea.getAccion() == TypeImportarAccion.SELECCIONAR) {
				identificadorArea = this.filaArea.getAreaActual().getIdentificador();
				idArea = this.filaArea.getAreaActual().getCodigo();
			}
			ConfiguracionAutenticacion configuracionAut = null;
			if (idArea != null && gestor != null && gestor.getConfiguracionAutenticacion() != null) {
				configuracionAut = configuracionAutenticacionService.getConfiguracionAutenticacion(TypeAmbito.AREA,
						gestor.getConfiguracionAutenticacion().getIdentificador(), UtilJSF.getIdEntidad(), idArea, null);
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
	 * Recorremos los dominios del zip. <br />
	 * Obtenemos el dominio actual a partir del identificador. <br />
	 * Si el dominio tiene fuente de datos, cargamos la FD y sus datos del zip.
	 * <br />
	 * Posteriormente, si el dominio actual existe y tiene FD, la cargamos. <br />
	 * Si no tenemos FD actual pero en el zip si había, intentamos cargar la FD a
	 * partir del identificador de la FD que había en el zip. <br />
	 * <br />
	 *
	 * A partir de ello, prepararamos la fila del dominio (llamando al util de
	 * importación que se encargará de revisar si está to_do ok y que accion/es se
	 * puede/n realizar)
	 *
	 * @return
	 */
	private void prepararFlujoDominioFD() {
		for (final Map.Entry<Long, Dominio> entry : dominios.entrySet()) {
			final Dominio dominio = entry.getValue();
			Long idArea = null;
			if (dominio.getAmbito() == TypeAmbito.AREA && filaArea.getAreaActual() != null) {
				idArea = filaArea.getAreaActual().getCodigo();
			}
			final Dominio dominioActual = dominioService.loadDominioByIdentificador(dominio.getAmbito(), dominio.getIdentificador(), UtilJSF.getIdEntidad(), idArea , null);
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
				fdActual = dominioService.loadFuenteDato(fd.getAmbito(), fd.getIdentificador(), UtilJSF.getIdEntidad(), idArea, null);
			}

			if (filaArea.getAreaActual() != null) {
				idArea = filaArea.getAreaActual().getCodigo();
			}
			final FilaImportarDominio fila = UtilCuadernoCarga.getFilaDominio(dominio, dominioActual, fd, fdContent,
					fdActual, UtilJSF.getIdEntidad(), idArea);

			this.filasDominios.add(fila);

		}

	}

	/**
	 * Check area.
	 */
	public void checkArea() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.filaArea);

		UtilJSF.openDialog(DialogCuadernoCargaAR.class, TypeModoAcceso.EDICION, null, true, 770, 220);
	}

	/**
	 * Check tramite.
	 */
	public void checkTramite() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.filaTramite);

		UtilJSF.openDialog(DialogCuadernoCargaTR.class, TypeModoAcceso.EDICION, null, true, 770, 220);
	}

	/**
	 * Check tramite version.
	 */
	public void checkTramiteVersion() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.filaTramiteVersion);

		UtilJSF.openDialog(DialogCuadernoCargaTV.class, TypeModoAcceso.EDICION, null, true, 500, 170);
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
	 * @param event
	 *                  respuesta dialogo
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
	 * @param event
	 *                  respuesta dialogo
	 */
	public void returnDialogoArea(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {

			this.filaArea = (FilaImportarArea) respuesta.getResult();
			this.filaArea.setEstado(TypeImportarEstado.REVISADO);
			this.filaArea.setResultado(TypeImportarResultado.OK);
			checkTodoCorrecto();
		}
	}

	/**
	 * Retorno dialogo del retorno dialogo tramite.
	 *
	 * @param event
	 *                  respuesta dialogo
	 */
	public void returnDialogoTramite(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			this.filaTramite = (FilaImportarTramite) respuesta.getResult();
			this.filaTramite.setEstado(TypeImportarEstado.REVISADO);
			this.filaTramite.setResultado(TypeImportarResultado.OK);
			checkTodoCorrecto();
		}
	}

	/**
	 * Retorno dialogo del retorno dialogo tramite version.
	 *
	 * @param event
	 *                  respuesta dialogo
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
	 * @param event
	 *                  respuesta dialogo
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
		if ((entornoActual == TypeEntorno.PREPRODUCCION && entornoFicheroZip == TypeEntorno.DESARROLLO)
				|| (entornoActual == TypeEntorno.PRODUCCION && entornoFicheroZip == TypeEntorno.PREPRODUCCION)) {

			final TypeImportarTipo tipo = TypeImportarTipo.fromString(prop.getProperty("tipo"));
			if (tipo != TypeImportarTipo.TRAMITE) {
				addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("dialogTramiteImportar.error.tipoTramite"));
				correcto = false;
			}

		} else {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogCuadernoCarga.error.entorno"));

			correcto = false;
		}

		if (correcto && (prop.getProperty("modo") == null
				|| !prop.getProperty("modo").equals(Constantes.IMPORTAR_TIPO_CC))) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogCuadernoCarga.error.modo"));
			correcto = false;
		}

		dir3zip = prop.getProperty("entidad");
		modoZip = prop.getProperty("modo");
		revisionZip = prop.getProperty("revision");
		versionZip = prop.getProperty("version");
		usuarioZip = prop.getProperty("usuario");
		fechaZip = prop.getProperty("fecha");

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
		}

		// Asociamos los datos de registro
		prepararDatosRegistro();

		final FilaImportar filaImportar = new FilaImportar();
		filaImportar.setFilaEntidad(filaEntidad);
		filaImportar.setFilaArea(filaArea);
		filaImportar.setFilaTramite(filaTramite);
		filaImportar.setFilaTramiteVersion(filaTramiteVersion);
		filaImportar.setFilaTramiteRegistro(filaTramiteRegistro);
		filaImportar.setFilaFormateador(filasFormateador);
		filaImportar.setFilaDominios(filasDominios);
		filaImportar.setFormularios(formularios);
		filaImportar.setFilaGestor(filasGestores);

		filaImportar.setIdEntidad(UtilJSF.getIdEntidad());
		filaImportar.setFicheros(ficheros);
		filaImportar.setFicherosContent(ficherosContent);
		filaImportar.setUsuario(UtilJSF.getSessionBean().getUserName());
		filaImportar.setModo(Constantes.IMPORTAR_TIPO_CC);

		final FilaImportarResultado resultado = tramiteService.importar(filaImportar);

		if (refrescarCacheDominio) {
			for (final FilaImportarDominio dominio : filasDominios) {
				if (dominio.getAccion() != TypeImportarAccion.MANTENER && dominio.getDominioActual() != null) {

					final String urlBase = systemService
							.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_URL.toString());
					final String usuario = systemService
							.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_USER.toString());
					final String pwd = systemService
							.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_PWD.toString());
					this.refrescarCache(urlBase, usuario, pwd, Constantes.CACHE_DOMINIO,
							dominio.getDominioActual().getIdentificador());
				}
			}
		}

		if (refrescarCacheTramite && filaTramiteVersion.getTramiteVersionActual() != null
				&& filaTramite.getTramiteActual() != null) {
			final String urlBase = systemService
					.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_URL.toString());
			final String usuario = systemService
					.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_USER.toString());
			final String pwd = systemService
					.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_PWD.toString());
			this.refrescarCache(urlBase, usuario, pwd, Constantes.CACHE_TRAMITE,
					filaTramite.getTramiteActual().getIdentificador() + "#"
							+ filaTramiteVersion.getTramiteVersionActual().getNumeroVersion());
		}
		addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.importar.ok"));
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
		result.setResult(resultado);
		UtilJSF.closeDialog(result);
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

	/** Funciones get/set. **/
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *               the id to set
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
	 * @param data
	 *                 the data to set
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
	 * @param mostrarPanelInfo
	 *                             the mostrarPanelInfo to set
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
	 * @param tramiteVersion
	 *                           the tramiteVersion to set
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
	 * @param tramite
	 *                    the tramite to set
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
	 * @param area
	 *                 the area to set
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
	 * @param formularioInternoService
	 *                                     the formularioInternoService to set
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
	 * @param tramiteService
	 *                           the tramiteService to set
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
	 * @param contenido
	 *                      the contenido to set
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
	 * @param dominiosId
	 *                       the dominiosId to set
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
	 * @param formularios
	 *                        the formularios to set
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
	 * @param ficheros
	 *                     the ficheros to set
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
	 * @param ficherosContent
	 *                            the ficherosContent to set
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
	 * @param fuentesDatos
	 *                         the fuentesDatos to set
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
	 * @param fuentesDatosContent
	 *                                the fuentesDatosContent to set
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
	 * @param formateadores
	 *                          the formateadores to set
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
	 * @param filaArea
	 *                     the filaArea to set
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
	 * @param filaTramite
	 *                        the filaTramite to set
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
	 * @param filaTramiteVersion
	 *                               the filaTramiteVersion to set
	 */
	public void setFilaTramiteVersion(final FilaImportarTramiteVersion filaTramiteVersion) {
		this.filaTramiteVersion = filaTramiteVersion;
	}

	/**
	 * @return the mostrarBotonArea
	 */
	public boolean isMostrarBotonArea() {
		return mostrarBotonArea;
	}

	/**
	 * @param mostrarBotonArea
	 *                             the mostrarBotonArea to set
	 */
	public void setMostrarBotonArea(final boolean mostrarBotonArea) {
		this.mostrarBotonArea = mostrarBotonArea;
	}

	/**
	 * @return the mostrarBotonTramite
	 */
	public boolean isMostrarBotonTramite() {
		return mostrarBotonTramite;
	}

	/**
	 * @param mostrarBotonTramite
	 *                                the mostrarBotonTramite to set
	 */
	public void setMostrarBotonTramite(final boolean mostrarBotonTramite) {
		this.mostrarBotonTramite = mostrarBotonTramite;
	}

	/**
	 * @return the mostrarBotonTramiteVersion
	 */
	public boolean isMostrarBotonTramiteVersion() {
		return mostrarBotonTramiteVersion;
	}

	/**
	 * @param mostrarBotonTramiteVersion
	 *                                       the mostrarBotonTramiteVersion to set
	 */
	public void setMostrarBotonTramiteVersion(final boolean mostrarBotonTramiteVersion) {
		this.mostrarBotonTramiteVersion = mostrarBotonTramiteVersion;
	}

	/**
	 * @return the mostrarRegistro
	 */
	public boolean isMostrarRegistro() {
		return mostrarRegistro;
	}

	/**
	 * @param mostrarRegistro
	 *                            the mostrarRegistro to set
	 */
	public void setMostrarRegistro(final boolean mostrarRegistro) {
		this.mostrarRegistro = mostrarRegistro;
	}

	/**
	 * @return the pasosRegistro
	 */
	public List<TramitePaso> getPasosRegistro() {
		return pasosRegistro;
	}

	/**
	 * @param pasosRegistro
	 *                          the pasosRegistro to set
	 */
	public void setPasosRegistro(final List<TramitePaso> pasosRegistro) {
		this.pasosRegistro = pasosRegistro;
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
	 * @param filaEntidad
	 *                        the filaEntidad to set
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
	 * @param filaTramiteRegistro
	 *                                the filaTramiteRegistro to set
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
	 * @param todoCorrecto
	 *                         the todoCorrecto to set
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
	 * @param mostrarRegistroOficina
	 *                                   the mostrarRegistroOficina to set
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
	 * @param dir3zip
	 *                    the dir3zip to set
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
	 * @param modoZip
	 *                    the modoZip to set
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
	 * @param revisionZip
	 *                        the revisionZip to set
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
	 * @param versionZip
	 *                       the versionZip to set
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
	 * @param fechaZip
	 *                     the fechaZip to set
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
	 * @param usuarioZip
	 *                       the usuarioZip to set
	 */
	public final void setUsuarioZip(final String usuarioZip) {
		this.usuarioZip = usuarioZip;
	}

	/**
	 * @return the refrescarCacheTramite
	 */
	public boolean isRefrescarCacheTramite() {
		return refrescarCacheTramite;
	}

	/**
	 * @param refrescarCacheTramite
	 *                                  the refrescarCacheTramite to set
	 */
	public void setRefrescarCacheTramite(final boolean refrescarCacheTramite) {
		this.refrescarCacheTramite = refrescarCacheTramite;
	}

	/**
	 * @return the refrescarCacheDominio
	 */
	public boolean isRefrescarCacheDominio() {
		return refrescarCacheDominio;
	}

	/**
	 * @param refrescarCacheDominio
	 *                                  the refrescarCacheDominio to set
	 */
	public void setRefrescarCacheDominio(final boolean refrescarCacheDominio) {
		this.refrescarCacheDominio = refrescarCacheDominio;
	}

	/**
	 * @return the mostrarFilasFormularios
	 */
	public boolean isMostrarFilasFormularios() {
		return mostrarFilasFormularios;
	}

	/**
	 * @param mostrarFilasFormularios
	 *                                    the mostrarFilasFormularios to set
	 */
	public void setMostrarFilasFormularios(final boolean mostrarFilasFormularios) {
		this.mostrarFilasFormularios = mostrarFilasFormularios;
	}

	/**
	 * @return the filasGestores
	 */
	public List<FilaImportarGestor> getFilasGestores() {
		return filasGestores;
	}

	/**
	 * @return the posicionGestor
	 */
	public Integer getPosicionGestor() {
		return posicionGestor;
	}

	/**
	 * @param posicionGestor
	 *                           the posicionGestor to set
	 */
	public void setPosicionGestor(final Integer posicionGestor) {
		this.posicionGestor = posicionGestor;
	}

	/**
	 * @return the filasFormulario
	 */
	public List<FilaImportarFormulario> getFilasFormulario() {
		return filasFormulario;
	}

}
