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
import es.caib.sistrages.core.api.model.comun.FilaImportarFormateador;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramite;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.FormateadorFormularioService;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeImportarTipo;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteImportar extends DialogControllerBase {

	/**
	 * Log.
	 */
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

	/** Linea 1. **/
	FilaImportarArea filaArea;

	/** Linea 2. **/
	FilaImportarTramite filaTramite;

	/** Linea 3. **/
	FilaImportarTramiteVersion filaTramiteVersion;

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
	/** Pasos de registro. **/
	private List<TramitePaso> pasosRegistro = new ArrayList<>();

	/**
	 * Inicialización.
	 */
	public void init() {
		setMostrarPanelInfo(false);
		mostrarBotonImportar = false;
	}

	/**
	 * Para obtener la versión de la configuracion global.
	 *
	 * @return
	 */
	private String getVersion() {
		final ConfiguracionGlobal confGlobal = configuracionGlobalService.getConfiguracionGlobal("sistrages.version");
		return confGlobal.getValor();
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

		final File tempFile = File.createTempFile("fichero", "zip");
		ZipFile zipFile = null;

		// 1. Extraer toda la info
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

		// 2. Comprobamos que tiene lo básico:
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

		// 3. Preparamos la info a mostrar de area/tramite/tramiteVersion
		areaActual = tramiteService.getAreaByIdentificador(area.getIdentificador());
		if (areaActual != null) {

			final Entidad entidad = entidadService.loadEntidadByArea(areaActual.getCodigo());
			if (entidad.getCodigo().compareTo(UtilJSF.getIdEntidad()) != 0) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("dialogTramiteImportar.error.distintaEntidad"));
				ocultarPaneles();
				return;
			}
			tramiteActual = tramiteService.getTramiteByIdentificador(tramite.getIdentificador());

		}

		if (tramiteActual != null && tramiteActual.getIdArea().compareTo(areaActual.getCodigo()) != 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "dialogTramiteImportar.error.areaDistinta");
			this.setMostrarPanelInfo(false);
			this.setMostrarBotonImportar(false);
			return;
		}

		if (tramiteActual != null) {

			tramiteVersionActual = tramiteService.getTramiteVersionByNumVersion(tramiteVersion.getNumeroVersion(),
					tramiteActual.getCodigo());
			if (tramiteVersionActual != null) {
				tramiteVersionActual.setListaPasos(tramiteService.getTramitePasos(tramiteVersionActual.getCodigo()));
			}
		}

		// Paso 3.1. Area.
		// - Si no existe area, si es administrador entidad entonces se puede crear sino
		// producir un error.
		if (areaActual == null) {

			filaArea = new FilaImportarArea(TypeImportarAccion.CREAR, TypeImportarEstado.NO_EXISTE,
					TypeImportarResultado.WARNING, area, areaActual);

			if (UtilJSF.getSessionBean().getActiveRole() != TypeRoleAcceso.ADMIN_ENT) {

				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("dialogTramiteImportar.error.noexistearea"));

				this.ocultarPaneles();
				return;
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

				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("dialogTramiteImportar.error.sinpermisopromocionar"));

				ocultarPaneles();
				return;

			} else if (area.getDescripcion().equals(areaActual.getDescripcion())) {

				filaArea = new FilaImportarArea(TypeImportarAccion.NADA, TypeImportarEstado.EXISTE,
						TypeImportarResultado.INFO, area, areaActual);

			} else {

				filaArea = new FilaImportarArea(TypeImportarAccion.REEMPLAZAR, TypeImportarEstado.EXISTE,
						TypeImportarResultado.WARNING, area, areaActual);

			}

		}

		// Paso 3.2. Tramite.
		if (tramiteActual == null) {

			filaTramite = new FilaImportarTramite(TypeImportarAccion.CREAR, TypeImportarEstado.NO_EXISTE,
					TypeImportarResultado.INFO, tramite, tramiteActual);

		} else {

			if (tramite.getDescripcion().equals(tramiteActual.getDescripcion())) {

				filaTramite = new FilaImportarTramite(TypeImportarAccion.NADA, TypeImportarEstado.EXISTE,
						TypeImportarResultado.INFO, tramite, tramiteActual);

			} else {

				filaTramite = new FilaImportarTramite(TypeImportarAccion.REEMPLAZAR, TypeImportarEstado.EXISTE,
						TypeImportarResultado.WARNING, tramite, tramiteActual);

			}

		}

		// Paso 3.3. Tramite Version.
		if (tramiteVersionActual == null) {

			filaTramiteVersion = new FilaImportarTramiteVersion(TypeImportarAccion.CREAR, TypeImportarEstado.NO_EXISTE,
					TypeImportarResultado.INFO, tramiteVersion, tramiteVersionActual);

		} else {

			filaTramiteVersion = new FilaImportarTramiteVersion(TypeImportarAccion.REEMPLAZAR,
					TypeImportarEstado.EXISTE, TypeImportarResultado.WARNING, tramiteVersion, tramiteVersionActual);

		}

		// Paso 3.3.1 Tramite Version tiene un paso registro
		mostrarRegistro = false;
		if (tramiteVersion != null && tramiteVersion.getListaPasos() != null) {
			for (final TramitePaso paso : tramiteVersion.getListaPasos()) {
				if (paso instanceof TramitePasoRegistrar) {
					mostrarRegistro = true;
					pasosRegistro.add(paso);
					break;
				}
			}
		}

		// Paso 3.4. Dominio.
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

			final FilaImportarDominio fila = new FilaImportarDominio(dominio, dominioActual, fd, fdContent, fdActual,
					checkPermisos(dominio));

			this.filasDominios.add(fila);

			if (fila.getResultado() == TypeImportarResultado.ERROR) {
				// Si hay un error paramos de seguir mirando
				return;
			}
		}

		// Paso 3.5. Formateadores.
		for (final Map.Entry<Long, FormateadorFormulario> entry : formateadores.entrySet()) {
			final FormateadorFormulario formateador = entry.getValue();

			final FormateadorFormulario formateadorActual = formateadorFormularioService
					.getFormateadorFormulario(formateador.getIdentificador());
			if (formateadorActual == null) {

				filasFormateador.add(new FilaImportarFormateador(formateador, formateadorActual,
						TypeImportarAccion.NADA, TypeImportarEstado.NO_EXISTE, TypeImportarResultado.ERROR,
						UtilJSF.getLiteral("dialogTramiteImportar.error.noexisteformateador")));
			} else {

				filasFormateador.add(new FilaImportarFormateador(formateador, formateadorActual,
						TypeImportarAccion.REEMPLAZAR, TypeImportarEstado.EXISTE, TypeImportarResultado.INFO, ""));
			}

		}

		this.setMostrarBotonArea(filaArea.getResultado().isWarning());
		this.setMostrarBotonTramite(filaTramite.getResultado().isWarning());
		this.setMostrarBotonTramiteVersion(filaTramiteVersion.getResultado().isWarning());

		setMostrarPanelInfo(true);
		setMostrarBotonImportar(true);

	}

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

		UtilJSF.openDialog(DialogTramiteImportarTV.class, TypeModoAcceso.EDICION, null, true, 770, 350);
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
			this.filaTramiteVersion.setAccion(TypeImportarAccion.REVISADO);
			this.filaTramiteVersion.setResultado(TypeImportarResultado.OK);
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

		return correcto;
	}

	/**
	 * Importar.
	 *
	 * @throws Exception
	 */
	public void importar() throws Exception {

		// Comprobamos que todas las filas están checkeadas.
		if (isNotCheckeado(this.filaArea.getResultado()) || isNotCheckeado(this.filaTramite.getResultado())
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

		tramiteService.importar(filaArea, filaTramite, filaTramiteVersion, filasDominios, filasFormateador,
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

}
