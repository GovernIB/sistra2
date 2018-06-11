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
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.FormateadorFormularioService;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.FilaImportar;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeImportarAccion;
import es.caib.sistrages.frontend.model.types.TypeImportarEstado;
import es.caib.sistrages.frontend.model.types.TypeImportarResultado;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteVersionImportar extends DialogControllerBase {

	/**
	 * Log.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogTramiteVersionImportar.class);

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
	Map<Long, FormularioInterno> formularios = new HashMap<>();

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
	FilaImportar filaArea;

	/** Linea 2. **/
	FilaImportar filaTramite;

	/** Linea 3. **/
	FilaImportar filaTramiteVersion;

	/** Fila dominios. **/
	final List<FilaImportar> filasDominios = new ArrayList<>();

	/** Filas formateadores. */
	final List<FilaImportar> filasFormateador = new ArrayList<>();

	/** Mostrar area. **/
	private boolean mostrarBotonArea = false;
	/** Mostrar tramite. **/
	private boolean mostrarBotonTramite = false;
	/** Mostrar tramite version. **/
	private boolean mostrarBotonTramiteVersion = false;
	/** Mostrar botones dominios. **/
	private final List<Boolean> mostrarBotonDominio = new ArrayList<>();
	/** Mostrar botones formateadores. **/
	private Integer posicionDominio;

	/**
	 * Inicialización.
	 */
	public void init() {
		setMostrarPanelInfo(false);
		mostrarBotonImportar = false;
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "En fase BETA!");
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
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "Error cargando el fichero zip y sus datos.");
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
					"El dato debería tener tramite, tramiteVersion, area y al menos 1 paso.");
			setMostrarPanelInfo(false);
			return;
		}

		// Se muestra ya la info, otra cosa es que el botón se vea o no
		this.setMostrarPanelInfo(true);
		this.setMostrarBotonImportar(false);

		// 3. Preparamos la info a mostrar de area/tramite/tramiteVersion
		areaActual = tramiteService.getAreaByIdentificador(area.getIdentificador(), UtilJSF.getIdEntidad());
		if (areaActual != null) {

			tramiteActual = tramiteService.getTramiteByIdentificador(tramite.getIdentificador(),
					areaActual.getCodigo());

		}

		if (tramiteActual != null) {

			tramiteVersionActual = tramiteService.getTramiteVersionMaxNumVersion(tramiteActual.getCodigo());

		}

		// Paso 3.1. Area.
		// - Si no existe area, si es administrador entidad entonces se puede crear sino
		// producir un error.
		if (areaActual == null) {

			filaArea = new FilaImportar(TypeImportarAccion.CREAR, TypeImportarEstado.NO_EXISTE,
					TypeImportarResultado.WARNING);
			if (UtilJSF.getSessionBean().getActiveRole() != TypeRoleAcceso.ADMIN_ENT) {

				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						"No existe el area y tendría que crearla un administrador de entidad.");

				setMostrarBotonImportar(false);
				return;
			}
		} else {
			final List<TypeRolePermisos> permisos = UtilJSF.getSessionBean().getSecurityService()
					.getPermisosDesarrolladorEntidad(areaActual.getCodigo());
			if (!permisos.contains(TypeRolePermisos.PROMOCIONAR)) {

				filaArea = new FilaImportar(TypeImportarAccion.NADA, TypeImportarEstado.EXISTE,
						TypeImportarResultado.ERROR);

				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						"No tienes permisos de promocionar sobre este area.");

				setMostrarBotonImportar(false);
				return;

			} else if (area.getDescripcion().equals(areaActual.getDescripcion())) {

				filaArea = new FilaImportar(TypeImportarAccion.NADA, TypeImportarEstado.EXISTE,
						TypeImportarResultado.INFO);

			} else {

				filaArea = new FilaImportar(TypeImportarAccion.REEMPLAZAR, TypeImportarEstado.EXISTE,
						TypeImportarResultado.WARNING);

			}

		}

		// Paso 3.2. Tramite.
		if (tramiteActual == null) {

			filaTramite = new FilaImportar(TypeImportarAccion.CREAR, TypeImportarEstado.NO_EXISTE,
					TypeImportarResultado.INFO);

		} else {

			if (tramite.getDescripcion().equals(tramiteActual.getDescripcion())) {

				filaTramite = new FilaImportar(TypeImportarAccion.NADA, TypeImportarEstado.EXISTE,
						TypeImportarResultado.INFO);

			} else {

				filaTramite = new FilaImportar(TypeImportarAccion.REEMPLAZAR, TypeImportarEstado.EXISTE,
						TypeImportarResultado.WARNING);

			}

		}

		// Paso 3.3. Tramite Version.
		if (tramiteVersionActual == null) {

			filaTramiteVersion = new FilaImportar(TypeImportarAccion.CREAR, TypeImportarEstado.NO_EXISTE,
					TypeImportarResultado.INFO);

		} else {

			if (tramiteVersionActual.getNumeroVersion() < tramiteVersion.getNumeroVersion()) {

				filaTramiteVersion = new FilaImportar(TypeImportarAccion.CREAR, TypeImportarEstado.EXISTE,
						TypeImportarResultado.INFO);

			} else if (tramiteVersionActual.getNumeroVersion() == tramiteVersion.getNumeroVersion()) {

				filaTramiteVersion = new FilaImportar(TypeImportarAccion.REEMPLAZAR, TypeImportarEstado.EXISTE,
						TypeImportarResultado.WARNING);

			} else {

				filaTramiteVersion = new FilaImportar(TypeImportarAccion.REEMPLAZAR, TypeImportarEstado.EXISTE,
						TypeImportarResultado.WARNING);

			}
		}

		// Paso 3.4. Dominio.
		for (final Map.Entry<Long, Dominio> entry : dominios.entrySet()) {
			final Dominio dominio = entry.getValue();
			final Dominio dominioActual = dominioService.loadDominio(dominio.getIdentificador());

			if (dominioActual == null && dominio.getAmbito() == TypeAmbito.GLOBAL) {

				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No tienes permisos para crear un dominio global.");

				filasDominios.add(new FilaImportar(dominio, TypeImportarAccion.ERROR, TypeImportarEstado.NO_EXISTE,
						TypeImportarResultado.ERROR));
				setMostrarBotonImportar(false);
				this.mostrarBotonDominio.add(false);
				return;

			}

			switch (dominio.getTipo()) {
			case CONSULTA_BD:
			case CONSULTA_REMOTA:
			case LISTA_FIJA:
				if (dominioActual == null) {

					filasDominios.add(new FilaImportar(dominio, TypeImportarAccion.CREAR, TypeImportarEstado.NO_EXISTE,
							TypeImportarResultado.WARNING));
					this.mostrarBotonDominio.add(true);
				} else {

					filasDominios.add(new FilaImportar(dominio, TypeImportarAccion.REEMPLAZAR,
							TypeImportarEstado.EXISTE, TypeImportarResultado.WARNING));
					this.mostrarBotonDominio.add(true);
				}
				break;
			case FUENTE_DATOS:

				if (dominioActual == null) {

					filasDominios.add(new FilaImportar(dominio, TypeImportarAccion.CREAR, TypeImportarEstado.NO_EXISTE,
							TypeImportarResultado.WARNING));
					this.mostrarBotonDominio.add(true);
				} else {

					filasDominios.add(new FilaImportar(dominio, TypeImportarAccion.REEMPLAZAR,
							TypeImportarEstado.EXISTE, TypeImportarResultado.WARNING));
					this.mostrarBotonDominio.add(true);
				}
				break;

			}

		}

		// Paso 3.4. Dominio.
		for (final Map.Entry<Long, FormateadorFormulario> entry : formateadores.entrySet()) {
			final FormateadorFormulario formateador = entry.getValue();
			final FormateadorFormulario formateadorActual = formateadorFormularioService
					.getFormateadorFormulario(UtilJSF.getIdEntidad(), formateador.getCodigo());
			if (formateadorActual == null) {

				filasFormateador.add(new FilaImportar(formateador, TypeImportarAccion.CREAR,
						TypeImportarEstado.NO_EXISTE, TypeImportarResultado.INFO));
			} else {

				filasFormateador.add(new FilaImportar(formateador, TypeImportarAccion.REEMPLAZAR,
						TypeImportarEstado.EXISTE, TypeImportarResultado.WARNING));
			}

		}

		this.setMostrarBotonArea(filaArea.getResultado().isWarning());
		this.setMostrarBotonTramite(filaTramite.getResultado().isWarning());
		this.setMostrarBotonTramiteVersion(filaTramiteVersion.getResultado().isWarning());

		setMostrarPanelInfo(true);
		setMostrarBotonImportar(true);

	}

	/**
	 * Check area.
	 */
	public void checkArea() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_AREA, UtilJSON.toJSON(area));
		// UtilJSF.getSessionBean().setMochilaDatos(mochilaDatos);

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.area.getCodigo().toString());
		params.put(TypeParametroVentana.AREA.toString(), this.area.getCodigo().toString());
		UtilJSF.openDialog(DialogTramiteVersionImportarAR.class, TypeModoAcceso.EDICION, params, true, 770, 300);
	}

	/**
	 * Check tramite.
	 */
	public void checkTramite() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_TRAMITE, UtilJSON.toJSON(tramite));
		// UtilJSF.getSessionBean().setMochilaDatos(mochilaDatos);

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.tramite.getCodigo().toString());
		params.put(TypeParametroVentana.AREA.toString(), this.area.getCodigo().toString());
		UtilJSF.openDialog(DialogTramiteVersionImportarTR.class, TypeModoAcceso.EDICION, params, true, 770, 300);
	}

	/**
	 * Check tramite version.
	 */
	public void checkTramiteVersion() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_TRAMITE_VERSION, UtilJSON.toJSON(tramiteVersion));
		// UtilJSF.getSessionBean().setMochilaDatos(mochilaDatos);

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.tramite.getCodigo().toString());
		params.put(TypeParametroVentana.AREA.toString(), this.area.getCodigo().toString());
		UtilJSF.openDialog(DialogTramiteVersionImportarTV.class, TypeModoAcceso.EDICION, params, true, 770, 300);
	}

	/**
	 * Check dominio.
	 *
	 * @param idDominio
	 */
	public void checkDominio(final Integer posicion) {

		posicionDominio = posicion - 1;
		final FilaImportar fila = this.filasDominios.get(posicionDominio);
		final Dominio dominio = fila.getDominio();

		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_DOMINIO, UtilJSON.toJSON(dominio));

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.tramite.getCodigo().toString());
		params.put(TypeParametroVentana.AREA.toString(), this.area.getCodigo().toString());
		UtilJSF.openDialog(DialogTramiteVersionImportarDominio.class, TypeModoAcceso.EDICION, params, true, 770, 300);

	}

	/**
	 * Retorno dialogo del retorno dialogo area.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoDominio(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		final TypeImportarAccion accion = (TypeImportarAccion) respuesta.getResult();
		if (accion != TypeImportarAccion.PENDIENTE) {
			this.filasDominios.get(posicionDominio).setResultado(TypeImportarResultado.INFO);
			this.filasDominios.get(posicionDominio).setAccion(accion);
			final FilaImportar fila = this.filasDominios.get(posicionDominio);
			final Dominio dominio = fila.getDominio();
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds()
					.add("iconoDominio" + dominio.getCodigo());
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds()
					.add("labelDominio" + dominio.getCodigo());
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
		final TypeImportarAccion accion = (TypeImportarAccion) respuesta.getResult();
		if (accion != TypeImportarAccion.PENDIENTE) {
			this.filaArea.setResultado(TypeImportarResultado.INFO);
			this.filaArea.setAccion(accion);
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
		final TypeImportarAccion accion = (TypeImportarAccion) respuesta.getResult();
		if (accion != TypeImportarAccion.PENDIENTE) {
			this.filaTramite.setResultado(TypeImportarResultado.INFO);
			this.filaTramite.setAccion(accion);
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
		final TypeImportarAccion accion = (TypeImportarAccion) respuesta.getResult();
		if (accion != TypeImportarAccion.PENDIENTE) {
			this.filaTramiteVersion.setResultado(TypeImportarResultado.INFO);
			this.filaTramiteVersion.setAccion(accion);
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
			final FormularioInterno formularioInterno = (FormularioInterno) UtilCoreApi.deserialize(contenidoFile);
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
		if (!prop.getProperty("version").equals(UtilJSF.getVersion())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "Error, no es la misma version.");
			return false;
		}

		// Checkeamos si se sube de los entornos que toca
		TypeEntorno entornoActual = TypeEntorno.fromString(UtilJSF.getEntorno());
		final TypeEntorno entornoFicheroZip = TypeEntorno.fromString(prop.getProperty("entorno"));

		entornoActual = TypeEntorno.PREPRODUCCION;
		if ((entornoActual == TypeEntorno.PREPRODUCCION && entornoFicheroZip == TypeEntorno.DESARROLLO)
				|| (entornoActual == TypeEntorno.PRODUCCION && entornoFicheroZip == TypeEntorno.PREPRODUCCION)) {
			return true;
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					"Sólo se puede subir de desarrollo a preproducción y de preproducción a producción.");

			return false;
		}
	}

	/**
	 * Importar.
	 */
	public void importar() {
		if (this.filaArea.getResultado() != TypeImportarResultado.INFO
				|| this.filaTramite.getResultado() != TypeImportarResultado.INFO
				|| this.filaTramiteVersion.getResultado() != TypeImportarResultado.INFO) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "Compruebe que todas las condiciones estén revisadas .");

			return;
		}

		for (final FilaImportar linea : filasDominios) {
			if (linea.getResultado() != TypeImportarResultado.INFO) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						"Compruebe que todas las condiciones estén revisadas .");
			}
		}

		for (final FilaImportar linea : filasFormateador) {
			if (linea.getResultado() != TypeImportarResultado.INFO) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						"Compruebe que todas las condiciones estén revisadas .");
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
	public Map<Long, FormularioInterno> getFormularios() {
		return formularios;
	}

	/**
	 * @param formularios
	 *            the formularios to set
	 */
	public void setFormularios(final Map<Long, FormularioInterno> formularios) {
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
	public List<FilaImportar> getFilasDominios() {
		return filasDominios;
	}

	/**
	 * @return the filasFormateador
	 */
	public List<FilaImportar> getFilasFormateador() {
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
	public void setFilaArea(final FilaImportar filaArea) {
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
	public void setFilaTramite(final FilaImportar filaTramite) {
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
	public void setFilaTramiteVersion(final FilaImportar filaTramiteVersion) {
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
	 * @return the mostrarBotonDominio
	 */
	public List<Boolean> getMostrarBotonDominio() {
		return mostrarBotonDominio;
	}

	/**
	 * @return the mostrarBotonDominio
	 */
	public Boolean getMostrarBotonDominio(final Integer posicion) {
		posicionDominio = posicion;
		return mostrarBotonDominio.get(posicion);
	}

}
