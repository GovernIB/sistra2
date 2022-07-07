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
import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeImportarTipo;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilCuadernoCarga;
import es.caib.sistrages.frontend.util.UtilImportacion;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogDominioImportar extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogDominioImportar.class);

	/** Servicio. */
	@Inject
	private DominioService dominioService;

	/** Servicio. */
	@Inject
	private ConfiguracionGlobalService configuracionGlobalService;

	/** Servicio. */
	@Inject
	private ConfiguracionAutenticacionService configuracionAutenticacionService;

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

	/** Id. **/
	private String id;

	/** Area. **/
	private Area area = null;

	/**
	 * Inicialización.
	 */
	public void init() {
		setMostrarPanelInfo(false);
		mostrarBotonImportar = false;
		if (TypeAmbito.fromString(ambito) == TypeAmbito.AREA) {
			area = new Area();
			area.setCodigo(Long.valueOf(id));
		}
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
	 * carga de fichero.
	 *
	 * @param event el evento
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
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofitxer"));
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
			UtilJSF.loggearErrorFront("Error extrayendo la info del zip.", e);
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogDominioImportar.error.cargandoFichero"));
			setMostrarPanelInfo(false);
			return;
		} finally {
			if (zipFile != null) {
				zipFile.close();
			}
		}

		// 2. Comprobamos que tiene lo básico:
		if (data == null) {
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogDominioImportar.error.dominioobligatorio"));
			setMostrarPanelInfo(false);
			return;
		}

		final Long idArea = (area == null) ? null : area.getCodigo();

		if (TypeAmbito.fromString(ambito) != data.getAmbito()) {
			filaDominio =  UtilCuadernoCarga.getFilaDominio(data, data, this.fuentesDatos, this.fuentesDatosContent,
					this.fuentesDatos, UtilJSF.getIdEntidad(), idArea);
			filaDominio.setMensaje(UtilJSF.getLiteral("dialogDominioImportar.error.dominioDiferenteAmbito"));
			filaDominio.setEstado(TypeImportarEstado.ERROR);
			filaDominio.setAccion(TypeImportarAccion.NADA);
			List<TypeImportarAccion> acciones = new ArrayList<>();
			acciones.add(TypeImportarAccion.NADA);
			filaDominio.setAcciones(acciones );
			filaDominio.setResultado(TypeImportarResultado.ERROR);
			filaDominio.setVisibleBoton(false);
			setMostrarPanelInfo(true);
			setMostrarBotonImportar(false);
			return;
		}

		final Dominio dominioActual = dominioService.loadDominioByIdentificador(TypeAmbito.fromString(ambito), data.getIdentificador(), UtilJSF.getIdEntidad(), idArea, null);
		FuenteDatos fdActual = null;
		if (dominioActual != null) {

			if (dominioActual.getTipo() == TypeDominio.FUENTE_DATOS && dominioActual.getIdFuenteDatos() != null) {
				fdActual = dominioService.loadFuenteDato(dominioActual.getIdFuenteDatos());
			}

			if (fdActual == null && this.fuentesDatos != null) {
				fdActual = dominioService.loadFuenteDato(TypeAmbito.fromString(ambito), this.fuentesDatos.getIdentificador(), UtilJSF.getIdEntidad(), area.getCodigo(), null);
			}
		}


		ConfiguracionAutenticacion configuracionAutenticacion = null;
		if (data != null && data.getTipo() == TypeDominio.CONSULTA_REMOTA && data.getConfiguracionAutenticacion() != null
				&& idArea != null) {
			configuracionAutenticacion = configuracionAutenticacionService.getConfiguracionAutenticacion(
					data.getAmbito(), data.getConfiguracionAutenticacion().getIdentificador(),
					UtilJSF.getIdEntidad(), idArea, null);
		}
		final String identificadorArea = this.area == null ? "" :  this.area.getIdentificadorCompuesto();
		filaDominio = UtilImportacion.getFilaDominioID(data, dominioActual, this.fuentesDatos, this.fuentesDatosContent,
				fdActual, identificadorArea, UtilJSF.getIdEntidad(), idArea, configuracionAutenticacion);

		if(UtilJSF.getIdEntidad() != null) {
			filaDominio.setIdEntidad(UtilJSF.getIdEntidad());
		}

		if(this.area != null) {
			filaDominio.setIdArea(this.area.getCodigo());
		}

		setMostrarPanelInfo(true);
		if (filaDominio.getResultado() != TypeImportarResultado.INFO
				&& filaDominio.getResultado() != TypeImportarResultado.ERROR) {
			// Se muestra ya la info, otra cosa es que el botón se vea o no
			setMostrarBotonImportar(true);
		}
	}

	/**
	 * Abre el dialog de dominio para seleccionar opciones.
	 */
	public void checkDominio() {
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.filaDominio);
		if (area != null) {
			mochilaDatos.put(Constantes.AREA, area.getCodigo());
		}
		UtilJSF.openDialog(DialogTramiteImportarDominio.class, TypeModoAcceso.EDICION, null, true,
				this.filaDominio.getAnchura(), this.filaDominio.getAltura());
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
			addMessageContext(TypeNivelGravedad.ERROR, "Fichero desconocido.");
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
			if (tipo != TypeImportarTipo.DOMINIO) {
				addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("dialogDominioImportar.error.tipodominio"));
				correcto = false;
			}

		} else {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogTramiteImportar.error.entorno"));

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

		// Comprobamos que el dominio está checkeado
		if (filaDominio.getResultado() == TypeImportarResultado.WARNING
				|| filaDominio.getResultado() == TypeImportarResultado.ERROR) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogTramiteImportar.warning.check"));
			return;
		}

		Long idArea = null;
		if (data.getAmbito().equals(TypeAmbito.AREA)) {
			idArea = Long.valueOf(id);
		}
		this.dominioService.importarDominio(filaDominio, UtilJSF.getIdEntidad(), idArea);

		addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.importar.ok"));
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
		UtilJSF.closeDialog(result);
		return;
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
	 * @param data the data to set
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
	 * @param contenido the contenido to set
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
	 * @param mostrarPanelInfo the mostrarPanelInfo to set
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
	 * @param mostrarBotonImportar the mostrarBotonImportar to set
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
	 * @param fuentesDatos the fuentesDatos to set
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
	 * @param fuentesDatosContent the fuentesDatosContent to set
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
	 * @param formateadores the formateadores to set
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
	 * @param filaDominio the filaDominio to set
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
	 * @param ambito the ambito to set
	 */
	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

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

}
