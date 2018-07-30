package es.caib.sistrages.frontend.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
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

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.FilaImportarDominio;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeImportarResultado;
import es.caib.sistrages.frontend.model.types.TypeImportarTipo;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogDominioImportar extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogDominioImportar.class);

	/** Servicio. */
	@Inject
	private DominioService dominioService;

	/** Dominio. */
	private Dominio data;

	/** Contenido. **/
	private byte[] contenido;

	/** Mostrar panel info o panel upload **/
	private boolean mostrarPanelInfo = false;

	/** Mostrar boton importar **/
	private boolean mostrarBotonImportar;

	/** Fuente datos. **/
	FuenteDatos fuentesDatos;

	/** Fuente datos content (CSV). **/
	byte[] fuentesDatosContent;

	/** Formateadores. **/
	private Map<Long, FormateadorFormulario> formateadores = new HashMap<>();

	/** Fila dominios. **/
	private FilaImportarDominio filaDominio;

	/** Ambito. **/
	private String ambito;

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
	 * 2. Comprobamos que tiene lo básico que es que tiene dominio. <br />
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
		if (data == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "El dato debería tener un dominio.");
			setMostrarPanelInfo(false);
			return;
		}

		final Dominio dominioActual = dominioService.loadDominio(data.getIdentificador());
		final FuenteDatos fd = null;
		final byte[] fdContent = null;
		FuenteDatos fdActual = null;

		if (data.getAmbito() != TypeAmbito.fromString(this.ambito)) {
			filaDominio = new FilaImportarDominio();
			filaDominio.setResultado(TypeImportarResultado.ERROR);
			filaDominio.setDominio(data);
			filaDominio.setDominioActual(dominioActual);
			filaDominio.setMensaje("importar.error.distintosAmbitos");
			this.setMostrarPanelInfo(true);
			this.setMostrarBotonImportar(false);

			// Si hay un error paramos de seguir mirando
			return;
		}

		if (dominioActual != null && dominioActual.getIdFuenteDatos() != null) {
			fdActual = dominioService.loadFuenteDato(dominioActual.getIdFuenteDatos());
		}

		filaDominio = new FilaImportarDominio(data, dominioActual, fd, fdContent, fdActual);

		if (filaDominio.getResultado() == TypeImportarResultado.ERROR) {
			this.setMostrarPanelInfo(true);
			this.setMostrarBotonImportar(false);

			// Si hay un error paramos de seguir mirando
			return;
		}

		// Se muestra ya la info, otra cosa es que el botón se vea o no
		setMostrarPanelInfo(true);
		setMostrarBotonImportar(true);

	}

	/**
	 * Abre el dialog de dominio para seleccionar opciones.
	 */
	public void checkDominio() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.filaDominio);
		UtilJSF.openDialog(DialogTramiteImportarDominio.class, TypeModoAcceso.EDICION, null, true,
				this.filaDominio.getAnchura(), this.filaDominio.getAltura());
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
			this.filaDominio = dato;
			this.filaDominio.setResultado(TypeImportarResultado.OK);
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("formTramite");
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

		if (nombreFichero.equals("dominio.data")) {
			data = ((Dominio) UtilCoreApi.deserialize(contenidoFile));

		} else if (nombreFichero.startsWith("fuenteDatos_") && nombreFichero.endsWith(".data")) {
			final FuenteDatos fDatos = (FuenteDatos) UtilCoreApi.deserialize(contenidoFile);
			fuentesDatos = fDatos;

		} else if (nombreFichero.startsWith("fuenteDatos_") && nombreFichero.endsWith(".csv")) {
			fuentesDatosContent = contenidoFile;

		} else if (!nombreFichero.equals("info.properties")) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "Fichero desconocido.");
			return false;
		}
		return true;
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
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "dialogTramiteVersionImportar.error.version");
			return false;
		}

		// Checkeamos si se sube de los entornos que toca
		TypeEntorno entornoActual = TypeEntorno.fromString(UtilJSF.getEntorno());
		final TypeEntorno entornoFicheroZip = TypeEntorno.fromString(prop.getProperty("entorno"));

		entornoActual = TypeEntorno.PREPRODUCCION;
		boolean correcto = true;
		if ((entornoActual == TypeEntorno.PREPRODUCCION && entornoFicheroZip == TypeEntorno.DESARROLLO)
				|| (entornoActual == TypeEntorno.PRODUCCION && entornoFicheroZip == TypeEntorno.PREPRODUCCION)) {

			final TypeImportarTipo tipo = TypeImportarTipo.fromString(prop.getProperty("tipo"));
			if (tipo != TypeImportarTipo.DOMINIO) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "Este fichero no es de tipo dominio.");
				correcto = false;
			}

		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					"Sólo se puede subir de desarrollo a preproducción y de preproducción a producción.");

			correcto = false;
		}

		return correcto;
	}

	/**
	 * Importar.
	 */
	public void importar() {

		// Comprobamos que el dominio está checkeado
		if (filaDominio.getResultado() == TypeImportarResultado.WARNING
				|| filaDominio.getResultado() == TypeImportarResultado.ERROR) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "dialogTramiteVersionImportar.warning.check");
			return;
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

	/** Funciones get/set. **/
	/**
	 * @return the data
	 */
	public Dominio getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Dominio data) {
		this.data = data;
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
	 * @return the fuentesDatos
	 */
	public FuenteDatos getFuentesDatos() {
		return fuentesDatos;
	}

	/**
	 * @param fuentesDatos
	 *            the fuentesDatos to set
	 */
	public void setFuentesDatos(final FuenteDatos fuentesDatos) {
		this.fuentesDatos = fuentesDatos;
	}

	/**
	 * @return the fuentesDatosContent
	 */
	public byte[] getFuentesDatosContent() {
		return fuentesDatosContent;
	}

	/**
	 * @param fuentesDatosContent
	 *            the fuentesDatosContent to set
	 */
	public void setFuentesDatosContent(final byte[] fuentesDatosContent) {
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
	 * @return the filaDominio
	 */
	public FilaImportarDominio getFilaDominio() {
		return filaDominio;
	}

	/**
	 * @param filaDominio
	 *            the filaDominio to set
	 */
	public void setFilaDominio(final FilaImportarDominio filaDominio) {
		this.filaDominio = filaDominio;
	}

	/**
	 * @return the ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito
	 *            the ambito to set
	 */
	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

}
