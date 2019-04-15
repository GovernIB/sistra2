package es.caib.sistrages.frontend.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
import es.caib.sistra2.commons.plugins.registro.api.TipoAsunto;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.FilaImportar;
import es.caib.sistrages.core.api.model.comun.FilaImportarArea;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.comun.FilaImportarEntidad;
import es.caib.sistrages.core.api.model.comun.FilaImportarFormateador;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramite;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.ComponenteService;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.FormateadorFormularioService;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.core.api.util.UtilImportacion;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeImportarTipo;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
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

	/** Id elemento a tratar. */
	private String id;

	/** Tramite version. */
	private Tramite data;

	/** Contenido. **/
	private byte[] contenido;

	/** Mostrar panel info o panel upload **/
	private boolean mostrarPanelInfo = false;

	/** Mostrar boton importar **/
	private boolean mostrarBotonImportar;

	/** Version tramite. **/
	private TramiteVersion tramiteVersion;
	private TramiteVersion tramiteVersionActual;

	/** Tramite. **/
	private Tramite tramite;
	private Tramite tramiteActual;

	/** Area. **/
	private Area area;
	private Area areaActual;

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

	/** Fila entidad. **/
	private FilaImportarEntidad filaEntidad = new FilaImportarEntidad();

	/** Linea 1. **/
	FilaImportarArea filaArea = new FilaImportarArea();

	/** Linea 2. **/
	FilaImportarTramite filaTramite = new FilaImportarTramite();

	/** Linea 3. **/
	FilaImportarTramiteVersion filaTramiteVersion = new FilaImportarTramiteVersion();

	/** Linea 3. **/
	FilaImportarTramiteVersion filaTramiteRegistro = new FilaImportarTramiteVersion();

	/** Fila dominios. **/
	final List<FilaImportarDominio> filasDominios = new ArrayList<>();

	/** Filas formateadores. */
	final List<FilaImportarFormateador> filasFormateador = new ArrayList<>();

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
	/** Mostrar registro info (libro / tipo / oficina). ***/
	private boolean mostrarRegistroInfo;
	/** Pasos de registro. **/
	private List<TramitePaso> pasosRegistro = new ArrayList<>();

	/** A true significa que no hay ninguna fila con error. **/
	private boolean todoCorrecto;

	/**
	 * Inicialización.
	 */
	public void init() {
		setMostrarPanelInfo(false);
		mostrarBotonImportar = false;
	}

	/**
	 * carga de fichero.
	 *
	 * @param event
	 *            el evento
	 * @throws IOException
	 *
	 */
	public void upload(final FileUploadEvent event) throws IOException {

		if (event != null && event.getFile() != null) {
			mostrarBotonImportar = false;
			final UploadedFile file = event.getFile();
			contenido = file.getContents();
			prepararImportacion(contenido);
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofitxer"));
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

		setTodoCorrecto(false);
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
			LOGGER.error("Error extrayendo la info del zip.", e);
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteImportar.error.fichero"));
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
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteImportar.error.ficheroContenido"));
			setMostrarPanelInfo(false);
			return;
		}

		// Se muestra ya la info, otra cosa es que el botón se vea o no
		this.setMostrarPanelInfo(true);
		this.setMostrarBotonImportar(false);

		// Paso 0. Se comprueba que la entidad por codigo dir3 sean los mismos.
		prepararImportacionEntidad();

		// Paso 1. Preparamos la info a mostrar de area
		prepararImportacionArea();

		// Paso 2. Preparamos la info a mostrar de tramite
		prepararImportacionTramite();

		// Paso 3. Preparamos la info a mostrar de los dominios/FD
		prepararImportacionDominioFD();

		// Paso 4. Preparamos la info a mostrar de los Formateadores.
		prepararImportacionFormateadores();

		// Seteamos si se ven los botones de area/tramite/tramiteVersion
		this.setMostrarBotonArea(filaArea.getResultado().isWarning());
		this.setMostrarBotonTramite(filaTramite.getResultado().isWarning());
		this.setMostrarBotonTramiteVersion(filaTramiteVersion.getResultado().isWarning());

		setMostrarPanelInfo(true);
		setMostrarBotonImportar(true);

		checkTodoCorrecto();
	}

	/**
	 * Comprueba si todas las filas están correctas
	 */
	private void checkTodoCorrecto() {

		if (filaEntidad == null || filaEntidad.getResultado() == TypeImportarResultado.ERROR) {
			setTodoCorrecto(false);
			return;
		}

		if (filaArea == null || filaArea.getResultado() == TypeImportarResultado.ERROR) {
			setTodoCorrecto(false);
			return;
		}

		if (filaTramite == null || filaTramite.getResultado() == TypeImportarResultado.ERROR) {
			setTodoCorrecto(false);
			return;
		}

		if (filaTramiteVersion == null || filaTramiteVersion.getResultado() == TypeImportarResultado.ERROR) {
			setTodoCorrecto(false);
			return;
		}

		if (filaTramiteRegistro == null || filaTramiteRegistro.getResultado() == TypeImportarResultado.ERROR) {
			setTodoCorrecto(false);
			return;
		}

		for (final FilaImportarDominio fila : filasDominios) {
			if (fila == null || fila.getResultado() == TypeImportarResultado.ERROR) {
				setTodoCorrecto(false);
				return;
			}
		}

		for (final FilaImportarFormateador fila : filasFormateador) {
			if (fila == null || fila.getResultado() == TypeImportarResultado.ERROR) {
				setTodoCorrecto(false);
				return;
			}
		}

		setTodoCorrecto(true);

	}

	/**
	 * Comprueba la importación de entidad.
	 *
	 * @return
	 */
	private boolean prepararImportacionEntidad() {
		final String dir3actual = entidadService.loadEntidad(UtilJSF.getIdEntidad()).getCodigoDIR3();
		filaEntidad.setDir3Actual(dir3actual);
		boolean correcto;
		if (filaEntidad.getDir3() == null || !filaEntidad.getDir3().equals(dir3actual)) {
			final Object[] propiedades = new Object[1];
			propiedades[0] = dir3actual;
			filaEntidad.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.dir3distinto", propiedades));
			filaEntidad.setResultado(TypeImportarResultado.ERROR);
			correcto = false;
		} else {
			filaEntidad.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.ok.entidadcorrecta"));
			filaEntidad.setResultado(TypeImportarResultado.OK);
			correcto = true;
		}
		return correcto;
	}

	/**
	 * Recorremos los formateadores asociados en el zip. <br />
	 * Se comprueba a partir del identificador si existe el formateador, si no
	 * existe, error y no se permite importar.
	 *
	 * Al ser el último cálculo, en caso de error (no existe el formateador) se
	 * permite seguir aunque no se podrá importar. Si existe, no se hace nada,
	 * simplemente se asocia.
	 *
	 * @return
	 */
	private void prepararImportacionFormateadores() {
		for (final Map.Entry<Long, FormateadorFormulario> entry : formateadores.entrySet()) {
			final FormateadorFormulario formateador = entry.getValue();

			final FormateadorFormulario formateadorActual = formateadorFormularioService
					.getFormateadorFormulario(formateador.getIdentificador());
			if (formateadorActual == null) {

				filasFormateador.add(new FilaImportarFormateador(formateador, formateadorActual,
						TypeImportarAccion.NADA, TypeImportarEstado.NO_EXISTE, TypeImportarResultado.ERROR,
						UtilJSF.getLiteral("dialogTramiteImportar.error.noexisteformateador")));
			} else {

				if (formateadorActual.isBloquear()) {
					filasFormateador.add(new FilaImportarFormateador(formateador, formateadorActual,
							TypeImportarAccion.NADA, TypeImportarEstado.EXISTE, TypeImportarResultado.ERROR,
							UtilJSF.getLiteral("dialogTramiteImportar.error.existeformateadorBloqueado")));
				} else {
					filasFormateador.add(new FilaImportarFormateador(formateador, formateadorActual,
							TypeImportarAccion.REEMPLAZAR, TypeImportarEstado.EXISTE, TypeImportarResultado.OK,
							UtilJSF.getLiteral("dialogTramiteImportar.error.existeformateador")));
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
	private void prepararImportacionDominioFD() {
		for (final Map.Entry<Long, Dominio> entry : dominios.entrySet()) {
			final Dominio dominio = entry.getValue();
			final Dominio dominioActual = dominioService.loadDominio(dominio.getIdentificador());
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
				fdActual = dominioService.loadFuenteDato(fd.getIdentificador());
			}

			final FilaImportarDominio fila = UtilImportacion.getFilaDominio(dominio, dominioActual, fd, fdContent,
					fdActual, checkPermisos(dominio));

			this.filasDominios.add(fila);

		}

	}

	/**
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
	 * <li>Se dará un error si no estás en el entorno de desarrollo, no eres adm.
	 * entidad y no tienes permisos de importar.</li>
	 * <li>Creamos la fila, será de tipo info o warning dependiendo de si cambia la
	 * descripcion.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 *
	 * @return
	 */
	private boolean prepararImportacionArea() {
		areaActual = tramiteService.getAreaByIdentificador(area.getIdentificador());
		if (areaActual != null) {

			final Entidad entidad = entidadService.loadEntidadByArea(areaActual.getCodigo());
			if (entidad.getCodigo().compareTo(UtilJSF.getIdEntidad()) != 0) {

				filaArea = new FilaImportarArea(TypeImportarAccion.NADA, TypeImportarEstado.EXISTE,
						TypeImportarResultado.ERROR, area, areaActual);
				filaArea.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.distintaEntidad"));
				return false;
			}
			tramiteActual = tramiteService.getTramiteByIdentificador(tramite.getIdentificador());

		}

		if (areaActual == null) {

			filaArea = new FilaImportarArea(TypeImportarAccion.CREAR, TypeImportarEstado.NO_EXISTE,
					TypeImportarResultado.WARNING, area, areaActual);

			// Si no existe el area y no se tiene permisos, hay que dar un error
			if (UtilJSF.getSessionBean().getActiveRole() != TypeRoleAcceso.ADMIN_ENT) {

				filaArea = new FilaImportarArea(TypeImportarAccion.NADA, TypeImportarEstado.EXISTE,
						TypeImportarResultado.ERROR, area, areaActual);
				filaArea.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.noexistearea"));
				return false;
			}

		} else {
			final List<TypeRolePermisos> permisos = UtilJSF.getSessionBean().getSecurityService()
					.getPermisosDesarrolladorEntidadByArea(areaActual.getCodigo());
			// Si no estamos en desarrollo, es desarrollador de entidad, es obligatorio ser
			// de tipo adm. area (sino pete)
			if (UtilJSF.getSessionBean().getActiveRole() != TypeRoleAcceso.ADMIN_ENT
					&& !permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
					&& !UtilJSF.getEntorno().equals(TypeEntorno.DESARROLLO.toString())) {

				filaArea = new FilaImportarArea(TypeImportarAccion.NADA, TypeImportarEstado.EXISTE,
						TypeImportarResultado.ERROR, area, areaActual);

				filaArea.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.sinpermisopromocionar"));
				return false;

			} else if (area.getDescripcion().equals(areaActual.getDescripcion())) {

				filaArea = new FilaImportarArea(TypeImportarAccion.NADA, TypeImportarEstado.EXISTE,
						TypeImportarResultado.OK, area, areaActual);

			} else {

				filaArea = new FilaImportarArea(TypeImportarAccion.REEMPLAZAR, TypeImportarEstado.EXISTE,
						TypeImportarResultado.WARNING, area, areaActual);

			}
		}

		return true;
	}

	/**
	 * Obtenemos el tramite actual a partir del identificador del tramite del zip.
	 * <br />
	 * Comprobaciones a realizar:
	 * <ul>
	 * <li>Si la entidad del tramite no es la misma que la del area, provoca un
	 * error. Este error se puede producir si cambias un area de entidad.</li>
	 * </ul>
	 * Posteriormente, si el tramite existe, recuperamos la version. Además,
	 * seteamos el debug a true dependiendo si el desarrollo está a true o false.
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
	private boolean prepararImportacionTramite() {
		tramiteActual = tramiteService.getTramiteByIdentificador(tramite.getIdentificador());

		// Si el area actual y el tramite actual no tienen el mismo area, provocar un
		// error.
		if (areaActual != null && tramiteActual != null
				&& tramiteActual.getIdArea().compareTo(areaActual.getCodigo()) != 0) {

			filaTramite = new FilaImportarTramite(TypeImportarAccion.NADA, TypeImportarEstado.EXISTE,
					TypeImportarResultado.ERROR, tramite, tramiteActual);
			filaTramite.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.areaDistinta"));
		}

		// Si existe el trámite en el entorno pero no el area, es que algo está mal.
		if (areaActual == null && tramiteActual != null) {

			filaTramite = new FilaImportarTramite(TypeImportarAccion.NADA, TypeImportarEstado.EXISTE,
					TypeImportarResultado.ERROR, tramite, tramiteActual);
			filaTramite.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.tramiteareaincorrecto"));
		}

		// Obtenemos la version y sus pasos si el trámite existe. Además, seteamos debug
		// a true si está en desarrollo.
		if (tramiteActual != null) {

			tramiteVersionActual = tramiteService.getTramiteVersionByNumVersion(tramiteVersion.getNumeroVersion(),
					tramiteActual.getCodigo());
			if (tramiteVersionActual != null) {
				tramiteVersionActual.setListaPasos(tramiteService.getTramitePasos(tramiteVersionActual.getCodigo()));
				if (UtilJSF.getEntorno().equals(TypeEntorno.DESARROLLO.toString())) {
					tramiteVersionActual.setDebug(true);
				} else {
					tramiteVersionActual.setDebug(false);
				}
			}
		}

		// Si no existe, creamos una fila de tipo info informando que no existe el
		// trámite.
		// Si existe, comprobamos si cambia la descripción (si cambia entonces damos
		// warning sino damos info)
		if (tramiteActual == null) {

			filaTramite = new FilaImportarTramite(TypeImportarAccion.CREAR, TypeImportarEstado.NO_EXISTE,
					TypeImportarResultado.OK, tramite, tramiteActual);

		} else {

			final Area areaTramite = tramiteService.getArea(tramiteActual.getIdArea());
			final Entidad entidadTramite = entidadService.loadEntidad(UtilJSF.getIdEntidad());

			if (!areaTramite.getCodigoDIR3Entidad().equals(entidadTramite.getCodigoDIR3())) {

				filaTramite = new FilaImportarTramite(TypeImportarAccion.ERROR, TypeImportarEstado.EXISTE,
						TypeImportarResultado.ERROR, tramite, tramiteActual);
				final Object[] parametros = new Object[1];
				parametros[0] = areaTramite.getCodigoDIR3Entidad();
				filaTramite
						.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.tramiteOtraEntidad", parametros));

			} else if (tramite.getDescripcion().equals(tramiteActual.getDescripcion())) {

				filaTramite = new FilaImportarTramite(TypeImportarAccion.NADA, TypeImportarEstado.EXISTE,
						TypeImportarResultado.OK, tramite, tramiteActual);

			} else {

				filaTramite = new FilaImportarTramite(TypeImportarAccion.REEMPLAZAR, TypeImportarEstado.EXISTE,
						TypeImportarResultado.WARNING, tramite, tramiteActual);

			}

		}

		// Paso 3.3. Tramite Version.
		if (tramiteVersionActual == null) {

			filaTramiteVersion = new FilaImportarTramiteVersion(TypeImportarAccion.CREAR, TypeImportarEstado.NO_EXISTE,
					TypeImportarResultado.OK, tramiteVersion, tramiteVersionActual);
			filaTramiteRegistro = new FilaImportarTramiteVersion(TypeImportarAccion.REEMPLAZAR,
					TypeImportarEstado.NO_EXISTE, TypeImportarResultado.WARNING, tramiteVersion, tramiteVersionActual);
		} else if (UtilJSF.getEntorno().equals(TypeEntorno.DESARROLLO.toString())) {

			if (tramiteVersionActual.getRelease() > tramiteVersion.getRelease()) {
				filaTramiteVersion = new FilaImportarTramiteVersion(TypeImportarAccion.INCREMENTAR,
						TypeImportarEstado.EXISTE, TypeImportarResultado.WARNING, tramiteVersion, tramiteVersionActual);
				filaTramiteVersion.setAcciones(
						new ArrayList<>(Arrays.asList(TypeImportarAccion.INCREMENTAR, TypeImportarAccion.REEMPLAZAR)));
			} else {
				filaTramiteVersion = new FilaImportarTramiteVersion(TypeImportarAccion.REEMPLAZAR,
						TypeImportarEstado.EXISTE, TypeImportarResultado.OK, tramiteVersion, tramiteVersionActual);
			}
			filaTramiteRegistro = new FilaImportarTramiteVersion(TypeImportarAccion.REEMPLAZAR,
					TypeImportarEstado.EXISTE, TypeImportarResultado.WARNING, tramiteVersion, tramiteVersionActual);
		} else {

			if (tramiteVersionActual.getRelease() > tramiteVersion.getRelease()) {
				filaTramiteVersion = new FilaImportarTramiteVersion(TypeImportarAccion.REEMPLAZAR,
						TypeImportarEstado.EXISTE, TypeImportarResultado.ERROR, tramiteVersion, tramiteVersionActual);
				filaTramiteVersion.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.releaseAntiguo"));
			} else {
				filaTramiteVersion = new FilaImportarTramiteVersion(TypeImportarAccion.REEMPLAZAR,
						TypeImportarEstado.EXISTE, TypeImportarResultado.OK, tramiteVersion, tramiteVersionActual);
			}
			filaTramiteRegistro = new FilaImportarTramiteVersion(TypeImportarAccion.REEMPLAZAR,
					TypeImportarEstado.EXISTE, TypeImportarResultado.WARNING, tramiteVersion, tramiteVersionActual);

		}

		// Paso 3.3.1 Tramite Version tiene un paso registro
		mostrarRegistro = false; // De momento, esta variable no se utiliza porque no hay tramites con pasos
									// personalizados.
		mostrarRegistroInfo = true;
		if (tramiteVersion != null && tramiteVersion.getListaPasos() != null) {
			for (final TramitePaso paso : tramiteVersion.getListaPasos()) {
				if (paso instanceof TramitePasoRegistrar) {
					mostrarRegistro = true;
					pasosRegistro.add(paso);
					break;
				}
			}
		}

		if (mostrarRegistro) {
			try {
				rellenarInfoRegistro();
			} catch (final Exception e) {
				LOGGER.error("Error intentando carga el plugin de registro al importar", e);
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("dialogTramiteImportar.error.pluginregistro"));
				filaTramiteVersion.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.pluginregistro"));

			}
		}

		return true;
	}

	/**
	 * Rellenando la info de registro. <br />
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

		// Cargamos los datos de oficina y registro (registro sólo si se encuentra la
		// oficina desde donde viene)
		if (this.filaTramiteRegistro.getTramiteVersionResultadoOficina() != null
				&& !this.filaTramiteRegistro.getTramiteVersionResultadoOficina().isEmpty()) {
			final List<OficinaRegistro> oficinas = iplugin.obtenerOficinasRegistro(entidad.getCodigoDIR3(),
					TypeRegistro.REGISTRO_ENTRADA);
			for (final OficinaRegistro oficina : oficinas) {
				if (oficina.getCodigo().equals(this.filaTramiteRegistro.getTramiteVersionResultadoOficina())) {
					this.filaTramiteRegistro.setTramiteVersionResultadoOficinaText(oficina.getNombre());
					final List<LibroOficina> libros = iplugin.obtenerLibrosOficina(entidad.getCodigoDIR3(),
							oficina.getCodigo(), TypeRegistro.REGISTRO_ENTRADA);
					for (final LibroOficina libro : libros) {
						if (libro.getCodigo().equals(this.filaTramiteRegistro.getTramiteVersionResultadoLibro())) {
							this.filaTramiteRegistro.setTramiteVersionResultadoLibroText(libro.getNombre());
							break;
						}
					}
					break;
				}
			}

		}

		// Cargamos los datos del tipo
		if (this.filaTramiteRegistro.getTramiteVersionResultadoTipo() != null
				&& !this.filaTramiteRegistro.getTramiteVersionResultadoTipo().isEmpty()) {
			final List<TipoAsunto> tipos = iplugin.obtenerTiposAsunto(entidad.getCodigoDIR3());
			for (final TipoAsunto tipo : tipos) {
				if (tipo.getCodigo().equals(this.filaTramiteRegistro.getTramiteVersionResultadoTipo())) {
					this.filaTramiteRegistro.setTramiteVersionResultadoTipoText(tipo.getNombre());
				}
			}
		}

	}

	/**
	 * Oculta todos los botones e info de importacion
	 */
	private void ocultarPaneles() {
		this.setMostrarPanelInfo(false);
		this.setMostrarBotonImportar(false);
		this.area = null;
		this.areaActual = null;
		this.tramite = null;
		this.tramiteActual = null;
		this.tramiteVersion = null;
		this.tramiteVersionActual = null;

	}

	/**
	 * Metodo que comprueba si tiene permisos el usuario sobre los dominios.
	 *
	 * @return
	 */
	private boolean checkPermisos(final Dominio dominio) {
		boolean tienePermisosEdicion;

		switch (dominio.getAmbito()) {
		case AREA:
			tienePermisosEdicion = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT
					|| UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR);
			break;
		case ENTIDAD:
			tienePermisosEdicion = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
			break;
		case GLOBAL:
			tienePermisosEdicion = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.SUPER_ADMIN);
			break;
		default:
			tienePermisosEdicion = false;
			break;
		}

		return tienePermisosEdicion;
	}

	/**
	 * Check area.
	 */
	public void checkArea() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.filaArea);

		UtilJSF.openDialog(DialogTramiteImportarAR.class, TypeModoAcceso.EDICION, null, true, 770, 220);
	}

	/**
	 * Check tramite.
	 */
	public void checkTramite() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.filaTramite);

		UtilJSF.openDialog(DialogTramiteImportarTR.class, TypeModoAcceso.EDICION, null, true, 770, 220);
	}

	/**
	 * Check tramite version.
	 */
	public void checkTramiteVersion() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.filaTramiteVersion);

		UtilJSF.openDialog(DialogTramiteImportarTV.class, TypeModoAcceso.EDICION, null, true, 500, 170);
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
			UtilJSF.openDialog(DialogTramiteImportarDominio.class, TypeModoAcceso.EDICION, null, true,
					fila.getAnchura(), fila.getAltura());
		}
	}

	/**
	 * Retorno dialogo del retorno dialogo area.
	 *
	 * @param event
	 *            respuesta dialogo
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
		}
	}

	/**
	 * Retorno dialogo del retorno dialogo area.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoArea(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {

			this.filaArea = (FilaImportarArea) respuesta.getResult();
			this.filaArea.setAccion(TypeImportarAccion.REVISADO);
			this.filaArea.setResultado(TypeImportarResultado.OK);
		}
	}

	/**
	 * Retorno dialogo del retorno dialogo tramite.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoTramite(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			this.filaTramite = (FilaImportarTramite) respuesta.getResult();
			this.filaTramite.setAccion(TypeImportarAccion.REVISADO);
			this.filaTramite.setResultado(TypeImportarResultado.OK);
		}
	}

	/**
	 * Retorno dialogo del retorno dialogo tramite version.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoTramiteVersion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			this.filaTramiteVersion = (FilaImportarTramiteVersion) respuesta.getResult();
			this.filaTramiteVersion.setResultado(TypeImportarResultado.OK);
			setMostrarRegistroInfo(true);
		}
	}

	/**
	 * Retorno dialogo del retorno dialogo tramite version.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoRegistro(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			this.filaTramiteRegistro = (FilaImportarTramiteVersion) respuesta.getResult();
			this.filaTramiteRegistro.setAccion(TypeImportarAccion.REVISADO);
			this.filaTramiteRegistro.setResultado(TypeImportarResultado.OK);
			setMostrarRegistroInfo(true);
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

		} else if (!nombreFichero.equals("info.properties")) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "Fichero desconocido.");
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

		final int posicionGuion = nombreFichero.indexOf("_");
		final int posicionPunto = nombreFichero.indexOf(".");
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
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteImportar.error.version"));
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
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("dialogTramiteImportar.error.tipoTramite"));
				correcto = false;
			}

		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteImportar.error.entorno"));

			correcto = false;
		}

		// Comprobamos que sean el mismo codigo dir3 (el del usuario
		filaEntidad = new FilaImportarEntidad();
		filaEntidad.setDir3(prop.getProperty("entidad"));

		return correcto;
	}

	/**
	 * Importar.
	 *
	 * @throws Exception
	 */
	public void importar() throws Exception {

		if (isNotCheckeado(this.filaEntidad.getResultado()) || isNotCheckeado(this.filaArea.getResultado())
				|| isNotCheckeado(this.filaTramite.getResultado())
				|| isNotCheckeado(this.filaTramiteRegistro.getResultado())
				|| isNotCheckeado(this.filaTramiteVersion.getResultado())) {
			return;
		}

		for (final FilaImportar linea : filasDominios) {
			if (isNotCheckeado(linea.getResultado())) {
				return;
			}
		}

		for (final FilaImportar linea : filasFormateador) {
			if (isNotCheckeado(linea.getResultado())) {
				return;
			}
		}

		if (filaTramiteVersion.getAccion() == TypeImportarAccion.INCREMENTAR) {
			filaTramiteRegistro.getTramiteVersion()
					.setRelease(filaTramiteRegistro.getTramiteVersionActual().getRelease() + 1);
		}

		tramiteService.importar(filaArea, filaTramite, filaTramiteRegistro, filasDominios, filasFormateador,
				UtilJSF.getIdEntidad(), formularios, ficheros, ficherosContent, UtilJSF.getSessionBean().getUserName());

		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.importar.ok"));
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Simplemente comprueba si no es modo error ni warning
	 *
	 * @param resultado
	 * @return
	 */
	private boolean isNotCheckeado(final TypeImportarResultado resultado) {
		final boolean noCheckeado = resultado == TypeImportarResultado.WARNING
				|| resultado == TypeImportarResultado.ERROR;
		if (noCheckeado) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteImportar.warning.check"));
		}
		return noCheckeado;
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

	/** Funciones get/set. **/
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 *            the data to set
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
	 *            the mostrarPanelInfo to set
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
	 *            the tramiteVersion to set
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
	 *            the tramite to set
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
	 *            the area to set
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
	 *            the formularioInternoService to set
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
	 *            the tramiteService to set
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
	 *            the contenido to set
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
	 *            the dominiosId to set
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
	 *            the formularios to set
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
	 *            the ficheros to set
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
	 *            the ficherosContent to set
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
	 *            the fuentesDatos to set
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
	 *            the fuentesDatosContent to set
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
	 *            the formateadores to set
	 */
	public void setFormateadores(final Map<Long, FormateadorFormulario> formateadores) {
		this.formateadores = formateadores;
	}

	/**
	 * @return the mostrarBotonImportar
	 */
	public boolean isMostrarBotonImportar() {
		return mostrarBotonImportar;
	}

	/**
	 * @param mostrarBotonImportar
	 *            the mostrarBotonImportar to set
	 */
	public void setMostrarBotonImportar(final boolean mostrarBotonImportar) {
		this.mostrarBotonImportar = mostrarBotonImportar;
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
	public FilaImportar getFilaArea() {
		return filaArea;
	}

	/**
	 * @param filaArea
	 *            the filaArea to set
	 */
	public void setFilaArea(final FilaImportarArea filaArea) {
		this.filaArea = filaArea;
	}

	/**
	 * @return the filaTramite
	 */
	public FilaImportar getFilaTramite() {
		return filaTramite;
	}

	/**
	 * @param filaTramite
	 *            the filaTramite to set
	 */
	public void setFilaTramite(final FilaImportarTramite filaTramite) {
		this.filaTramite = filaTramite;
	}

	/**
	 * @return the filaTramiteVersion
	 */
	public FilaImportar getFilaTramiteVersion() {
		return filaTramiteVersion;
	}

	/**
	 * @param filaTramiteVersion
	 *            the filaTramiteVersion to set
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
	 *            the mostrarBotonArea to set
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
	 *            the mostrarBotonTramite to set
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
	 *            the mostrarBotonTramiteVersion to set
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
	 *            the mostrarRegistro to set
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
	 *            the pasosRegistro to set
	 */
	public void setPasosRegistro(final List<TramitePaso> pasosRegistro) {
		this.pasosRegistro = pasosRegistro;
	}

	/**
	 * @return the mostrarRegistroInfo
	 */
	public boolean isMostrarRegistroInfo() {
		return mostrarRegistroInfo;
	}

	/**
	 * @param mostrarRegistroInfo
	 *            the mostrarRegistroInfo to set
	 */
	public void setMostrarRegistroInfo(final boolean mostrarRegistroInfo) {
		this.mostrarRegistroInfo = mostrarRegistroInfo;
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
	 *            the filaEntidad to set
	 */
	public void setFilaEntidad(final FilaImportarEntidad filaEntidad) {
		this.filaEntidad = filaEntidad;
	}

	/**
	 * @return the filaTramiteRegistro
	 */
	public final FilaImportarTramiteVersion getFilaTramiteRegistro() {
		return filaTramiteRegistro;
	}

	/**
	 * @param filaTramiteRegistro
	 *            the filaTramiteRegistro to set
	 */
	public final void setFilaTramiteRegistro(final FilaImportarTramiteVersion filaTramiteRegistro) {
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
	 *            the todoCorrecto to set
	 */
	public void setTodoCorrecto(final boolean todoCorrecto) {
		this.todoCorrecto = todoCorrecto;
	}
}
