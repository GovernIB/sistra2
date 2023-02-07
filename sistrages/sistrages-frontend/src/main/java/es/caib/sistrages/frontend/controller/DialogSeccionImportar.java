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
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.ScriptSeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.comun.FilaImportarSeccion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.model.types.TypeScriptSeccionReutilizable;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.SeccionReutilizableService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeImportarTipo;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogSeccionImportar extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogSeccionImportar.class);

	/** Servicio. */
	@Inject
	private SeccionReutilizableService seccionService;

	/** Servicio. */
	@Inject
	private ConfiguracionGlobalService configuracionGlobalService;

	/** Dominio. */
	private SeccionReutilizable data;

	private List<ScriptSeccionReutilizable> scripts = new ArrayList<>();

	/** Contenido. **/
	private byte[] contenido;

	/** Mostrar panel info o panel upload **/
	private boolean mostrarPanelInfo = false;

	/** Mostrar boton importar **/
	private boolean mostrarBotonImportar;

	private boolean tieneScriptSRI = false;

	/** Fuente datos. **/
	FuenteDatos fuentesDatos;

	/** Fuente datos content (CSV). **/
	byte[] fuentesDatosContent;

	/** Formateadores. **/
	private Map<Long, FormateadorFormulario> formateadores = new HashMap<>();

	/** Fila dominios. **/
	private FilaImportarSeccion filaSeccion;

	/** Id. **/
	private String id;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	public void init() {
		LOGGER.debug("DialogSeccionImportar init");
		setMostrarPanelInfo(false);
		mostrarBotonImportar = false;
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

		final SeccionReutilizable seccionActual = seccionService.getSeccionReutilizableByIdentificador(
				TypeAmbito.ENTIDAD, data.getIdentificador(), UtilJSF.getIdEntidad(), null);

		if (seccionActual == null) {
			// No existe la seccion.
			final String mensaje = UtilJSF.getLiteral("dialogSeccionImportar.info.noexisteseccion");
			filaSeccion = FilaImportarSeccion.crearISnoexiste(data, seccionActual, UtilJSF.getIdEntidad(), mensaje);

		} else {
			// Exite la seccion.
			if (seccionActual.getRelease() <= data.getRelease()) {
				String mensaje = UtilJSF.getLiteral("dialogSeccionImportar.info.existeSeccionImportarMasNueva");
				filaSeccion = FilaImportarSeccion.crearISexiste(data, seccionActual, UtilJSF.getIdEntidad(), mensaje,
						true);
			} else {
				String mensaje = UtilJSF.getLiteral("dialogSeccionImportar.info.existeSeccionImportarMenosNueva");
				filaSeccion = FilaImportarSeccion.crearISexiste(data, seccionActual, UtilJSF.getIdEntidad(), mensaje,
						false);
			}
		}

		setMostrarPanelInfo(true);
		if (filaSeccion.getResultado() != TypeImportarResultado.INFO
				&& filaSeccion.getResultado() != TypeImportarResultado.ERROR) {
			// Se muestra ya la info, otra cosa es que el botón se vea o no
			setMostrarBotonImportar(true);
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

		if (nombreFichero.equals("seccion.data")) {
			data = ((SeccionReutilizable) UtilCoreApi.deserialize(contenidoFile));

		} else if (nombreFichero.startsWith("script_tipo_")) {
			final ScriptSeccionReutilizable script = (ScriptSeccionReutilizable) UtilCoreApi.deserialize(contenidoFile);
			if (nombreFichero.startsWith("script_tipo_SRI")) {
				tieneScriptSRI = true;
			}

			scripts.add(script);

		} else if (!nombreFichero.equals("info.properties")) {
			addMessageContext(TypeNivelGravedad.ERROR, "Fichero desconocido.");
			return false;
		}
		return true;
	}

	public void verScriptSRI() {

		ScriptSeccionReutilizable valorSeleccionado = getScript(TypeScriptSeccionReutilizable.CARGA_DATOS_INICIAL);
		if (valorSeleccionado != null) {
			// Muestra dialogo
			final Map<String, String> maps = new HashMap<>();
			maps.put(TypeParametroVentana.TIPO_SCRIPT_SECCION_REUTILIZABLE.toString(),
					UtilJSON.toJSON(valorSeleccionado.getTipoScript()));
			// SeccionReutilizable seccion =
			// seccionReutService.getSeccionReutilizable(valorSeleccionado.getIdSeccionReutilizable());
			// maps.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(),
			// seccion.getIdFormularioAsociado().toString());
			// maps.put(TypeParametroVentana.SECCION_REUTILIZABLE.toString(),
			// seccion.getIdentificador());
			/*
			 * maps.put(TypeParametroVentana.FORM_INTERNO_ACTUAL.toString(), this.id);
			 * maps.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);
			 */
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(valorSeleccionado.getScript()));

			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.CONSULTA, maps, true, 700);
		}
	}

	private ScriptSeccionReutilizable getScript(TypeScriptSeccionReutilizable tipo) {
		if (scripts != null) {
			for (ScriptSeccionReutilizable script : scripts) {
				if (script.getTipoScript() == tipo) {
					return script;
				}
			}
		}
		return null;
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
		if (isEntornoCorrecto(entornoActual, entornoFicheroZip)) {

			final TypeImportarTipo tipo = TypeImportarTipo.fromString(prop.getProperty("tipo"));
			if (tipo != TypeImportarTipo.SECCION_REUTILIZABLE) {
				addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("dialogSeccionImportar.error.tiposeccion"));
				correcto = false;
			}

		} else {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogTramiteImportar.error.entorno"));

			correcto = false;
		}

		return correcto;
	}

	private boolean isEntornoCorrecto(TypeEntorno entornoActual, TypeEntorno entornoFicheroZip) {
		boolean entornoCorrecto = (entornoActual == TypeEntorno.PREPRODUCCION
				&& entornoFicheroZip == TypeEntorno.DESARROLLO)
				|| (entornoActual == TypeEntorno.PRODUCCION && entornoFicheroZip == TypeEntorno.PREPRODUCCION)
				|| entornoActual == TypeEntorno.DESARROLLO;

		if (entornoActual == TypeEntorno.PRODUCCION && UtilJSF.isPromocionSe2Pro()) {
			entornoCorrecto = true;
		}
		return entornoCorrecto;
	}

	/**
	 * Importar.
	 *
	 * @throws Exception
	 */
	public void importar() throws Exception {

		// Comprobamos que el dominio está checkeado
		if (filaSeccion.getResultado() == TypeImportarResultado.WARNING
				|| filaSeccion.getResultado() == TypeImportarResultado.ERROR) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogTramiteImportar.warning.check"));
			return;
		}

		filaSeccion.setScripts(scripts);
		this.seccionService.importarSeccion(filaSeccion, UtilJSF.getIdEntidad());

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

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {

		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	/**
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * @param errorCopiar the errorCopiar to set
	 */
	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewTramites.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/** Funciones get/set. **/
	/**
	 * @return the data
	 */
	public SeccionReutilizable getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final SeccionReutilizable data) {
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
	public FilaImportarSeccion getFilaSeccion() {
		return filaSeccion;
	}

	/**
	 * @param filaDominio the filaDominio to set
	 */
	public void setFilaDominio(final FilaImportarSeccion filaDominio) {
		this.filaSeccion = filaDominio;
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

	/**
	 * @return the tieneScriptSRI
	 */
	public boolean isTieneScriptSRI() {
		return tieneScriptSRI;
	}

	/**
	 * @param tieneScriptSRI the tieneScriptSRI to set
	 */
	public void setTieneScriptSRI(boolean tieneScriptSRI) {
		this.tieneScriptSRI = tieneScriptSRI;
	}

}
