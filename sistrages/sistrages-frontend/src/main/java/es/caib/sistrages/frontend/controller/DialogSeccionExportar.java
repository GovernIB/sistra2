package es.caib.sistrages.frontend.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.ModelApi;
import es.caib.sistrages.core.api.model.ScriptSeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.SeccionReutilizableService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeImportarTipo;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogSeccionExportar extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogSeccionExportar.class);

	/** Servicio. */
	@Inject
	private SeccionReutilizableService seccionService;

	/** Servicio. */
	@Inject
	private ConfiguracionGlobalService configuracionGlobalService;

	/** Servicio. */
	@Inject
	private FormularioInternoService formularioInternoService;

	/** Id elemento a tratar. */
	private String id;

	/** Seccion reutilizable. **/
	private SeccionReutilizable data;

	/** Scripts **/
	private List<ScriptSeccionReutilizable> scripts;

	/** Mostrar botón exportar. **/
	private boolean mostrarBotonExportar;

	/**
	 * Inicialización.
	 */
	public void init() {
		LOGGER.debug("Entrando en dialogSeccionExportar.");
		data = seccionService.getSeccionReutilizable(Long.valueOf(id));
		scripts = seccionService.getScriptsByIdSeccionReutilizable(Long.valueOf(id));
		mostrarBotonExportar = true;
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
	 * Exportación de un dominio a un zip. Pasos a realizar:<br />
	 * 0. Preparar zip. <br />
	 * 1. Incluir properties. <br />
	 * 2. Incluir seccion.data <br />
	 * 3. Incluir los posibles scripts. </br />
	 * 4. Descargar
	 *
	 * @throws IOException
	 *
	 * @throws Exception
	 ***/
	public StreamedContent getFichero() throws Exception {

		byte[] content = null;

		final ByteArrayOutputStream fos = new ByteArrayOutputStream();

		// 0. Generamos el fichero zip.
		final ZipOutputStream zos = new ZipOutputStream(fos);

		// 1. Incluir el fichero de propiedades
		prepararProperties(zos);

		// 2. Incluir el dominio.data
		final DisenyoFormulario disenyoFormulario = formularioInternoService.getFormularioInternoCompleto(data.getIdFormularioAsociado());
		data.setDisenyoFormulario(disenyoFormulario);
		incluirModelApi(zos, data, "seccion.data");

		// 3. Incluir el dominio.data
		if (scripts != null) {
			for(ScriptSeccionReutilizable script : scripts) {
				incluirModelApi(zos, script, "script_tipo_"+script.getTipoScript().toString()+".data");
			}
		}
		// 4. Cerramos
		zos.closeEntry();
		zos.close();
		fos.close();

		content = fos.toByteArray();

		// 5. Descargar.
		final InputStream myInputStream = new ByteArrayInputStream(content);
		return new DefaultStreamedContent(myInputStream, "application/zip", getNombreFichero() + ".zip");

	}

	/**
	 * Obtiene el nombre del fichero.
	 *
	 * @return
	 */
	private String getNombreFichero() {
		return "SRU-" + this.data.getIdentificador() + "-" + this.data.getRelease() + "-" + UtilJSF.getEntorno();
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
	 * Perpara el fichero de properties.
	 *
	 * @return
	 * @throws IOException
	 */
	private void prepararProperties(final ZipOutputStream zos) throws IOException {

		final Properties prop = new Properties();
		prop.setProperty("entorno", UtilJSF.getEntorno());
		prop.setProperty("version", getVersion());
		prop.setProperty("fecha", Calendar.getInstance().getTime().toString());
		prop.setProperty("usuario", UtilJSF.getSessionBean().getUserName());
		prop.setProperty("tipo", TypeImportarTipo.SECCION_REUTILIZABLE.toString());
		final ByteArrayOutputStream output = new ByteArrayOutputStream();
		prop.store(output, null);

		final ZipEntry zeProperties = new ZipEntry("info.properties");
		zos.putNextEntry(zeProperties);
		zos.write(output.toByteArray());

	}

	/**
	 * Incluir version.
	 *
	 * @param zos
	 * @throws IOException
	 */
	private void incluirModelApi(final ZipOutputStream zos, final ModelApi model, final String nombreFichero)
			throws IOException {
		final ZipEntry zeTramiteVersion = new ZipEntry(nombreFichero);
		zos.putNextEntry(zeTramiteVersion);
		zos.write(UtilCoreApi.serialize(model));
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
	 * @return the mostrarBotonExportar
	 */
	public boolean isMostrarBotonExportar() {
		return mostrarBotonExportar;
	}

	/**
	 * @param mostrarBotonExportar
	 *            the mostrarBotonExportar to set
	 */
	public void setMostrarBotonExportar(final boolean mostrarBotonExportar) {
		this.mostrarBotonExportar = mostrarBotonExportar;
	}

	/**
	 * @return the data
	 */
	public SeccionReutilizable getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final SeccionReutilizable data) {
		this.data = data;
	}


}
