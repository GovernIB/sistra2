package es.caib.sistrages.frontend.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
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
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.ModelApi;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.util.CsvUtil;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeImportarTipo;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogDominioExportar extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogDominioExportar.class);

	/** Servicio. */
	@Inject
	private DominioService dominioService;

	/** Servicio. */
	@Inject
	private ConfiguracionGlobalService configuracionGlobalService;

	/** Id elemento a tratar. */
	private String id;

	/** Dominio. **/
	private Dominio data;

	/** Dominio. **/
	private FuenteDatos fuente;

	/** Mostrar botón exportar. **/
	private boolean mostrarBotonExportar;

	/**
	 * Inicialización.
	 */
	public void init() {
		LOGGER.debug("Entrando en dialogDominioExportar.");
		data = dominioService.loadDominio(Long.valueOf(id));
		if (data.getIdFuenteDatos() != null && data.getTipo() == TypeDominio.FUENTE_DATOS) {
			fuente = dominioService.loadFuenteDato(data.getIdFuenteDatos());
		}
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
	 * 2. Incluir dominio.data <br />
	 * 3. Incluir las fuenteDatos_ID.data y fuenteDatos_ID.csv (si el dominio es de
	 * tipo fuente de datos) <br />
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
		incluirModelApi(zos, data, "dominio.data");

		// 3. Incluir la fuente de datos si es de ese tipo.
		if (data.getIdFuenteDatos() != null && data.getTipo() == TypeDominio.FUENTE_DATOS) {

			incluirModelApi(zos, fuente, "fuenteDatos_" + fuente.getCodigo() + ".data");

			// 8. y fuenteDatos_ID.csv (se prepara manualmente el contenido)
			final FuenteDatosValores fd = dominioService.loadFuenteDatoValores(fuente.getCodigo());
			final CsvDocumento csv = CsvUtil.getCsvDocumento(fuente, fd);

			final byte[] contentsFuenteDatosCSV = CsvUtil.exportar(csv);
			final ZipEntry zeTramite = new ZipEntry("fuenteDatos_" + fuente.getCodigo() + ".csv");
			zos.putNextEntry(zeTramite);
			zos.write(contentsFuenteDatosCSV);
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
		return "DOM-" + this.data.getIdentificador() + "-" + UtilJSF.getEntorno();
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
		prop.setProperty("tipo", TypeImportarTipo.DOMINIO.toString());
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
	 * @return the fuente
	 */
	public FuenteDatos getFuente() {
		return fuente;
	}

	/**
	 * @param fuente
	 *            the fuente to set
	 */
	public void setFuente(final FuenteDatos fuente) {
		this.fuente = fuente;
	}

}
