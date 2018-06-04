package es.caib.sistrages.frontend.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.ContenidoFichero;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.ModelApi;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.GestorFicherosService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.CsvUtil;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteVersionExportar extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogTramiteVersionExportar.class);

	/** Servicio. **/
	@Inject
	private GestorFicherosService gestorFicherosService;

	/** Servicio. */
	@Inject
	private DominioService dominioService;

	/** Servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Id elemento a tratar. */
	private String id;

	/** Tramite version. */
	private TramiteVersion tramiteVersion;

	/** Tramite. **/
	private Tramite tramite;

	/** Area. **/
	private Area area;

	/** Pasos. **/
	List<TramitePaso> pasos;

	/** Dominios. */
	List<Dominio> dominios;

	/** Formateadores. **/
	List<FormateadorFormulario> formateadores;

	/** Fuente de datos. **/
	List<FuenteDatos> fuenteDatos = new ArrayList<>();

	/** Formularios. **/
	List<FormularioInterno> formularios;

	/** Formularios. **/
	List<Fichero> ficheros;

	/** Formularios. **/

	/**
	 * Inicialización.
	 */
	public void init() {

		tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(id));
		pasos = tramiteService.getTramitePasos(Long.valueOf(id));
		dominios = tramiteService.getTramiteDominios(Long.valueOf(id));
		tramiteVersion.setListaPasos(pasos);
		tramiteVersion.setListaDominios(dominios);
		tramite = tramiteService.getTramite(tramiteVersion.getIdTramite());
		area = tramiteService.getAreaTramite(tramiteVersion.getIdTramite());

		formateadores = tramiteService.getFormateadoresTramiteVersion(Long.valueOf(id));
		formularios = tramiteService.getFormulariosTramiteVersion(Long.valueOf(id));
		ficheros = tramiteService.getFicherosTramiteVersion(Long.valueOf(id));
		for (final Dominio dominio : dominios) {
			if (dominio.getTipo() == TypeDominio.FUENTE_DATOS && dominio.getFuenteDatos() != null
					&& !fuenteDatos.contains(dominio.getFuenteDatos())) {
				fuenteDatos.add(dominio.getFuenteDatos());
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
	 * Exportación de un tramite version a un zip. Pasos a realizar:<br />
	 * 0. Preparar zip. <br />
	 * 1. Incluir properties. <br />
	 * 2. Incluir version.data <br />
	 * 3. Incluir area.data <br />
	 * 4. Incluir tramite.data <br />
	 * 5. Incluir los dominios_ID.data <br />
	 * 6. Incluir los formulario_ID.data <br />
	 * 7. Incluir los ficheros_ID.data y ficherosContent_ID.data <br />
	 * 8. Incluir las fuenteDatos_ID.data y fuenteDatos_ID.csv <br />
	 * 9. Incluir los formeteadores_ID.data<br />
	 * 10. Descargar
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

		// 2. Incluir el version.data
		incluirModelApi(zos, this.tramiteVersion, "version.data");

		// 3. Incluir el area.data
		incluirModelApi(zos, this.area, "area.data");

		// 4. Incluir el tramite.data
		incluirModelApi(zos, this.tramite, "tramite.data");

		// 5. Incluir los dominios_ID.data
		for (final Dominio dominio : dominios) {
			incluirModelApi(zos, dominio, "dominios_" + dominio.getId() + ".data");
		}

		// 6. Incluir los formularios_ID.data
		for (final FormularioInterno formulario : formularios) {
			incluirModelApi(zos, formulario, "formularios_" + formulario.getId() + ".data");
		}

		// 7. Incluir los ficheros_ID.data y ficherosContent_ID.data
		for (final Fichero fichero : ficheros) {
			incluirModelApi(zos, fichero, "ficheros_" + fichero.getId() + ".data");

			// El contenido del fichero se hace manual
			final ContenidoFichero contenido = gestorFicherosService.obtenerContenidoFichero(fichero.getId());
			final ZipEntry zeTramiteVersion = new ZipEntry("ficherosContent_" + fichero.getId() + ".data");
			zos.putNextEntry(zeTramiteVersion);
			zos.write(contenido.getContent());

		}

		// 8. Incluir los dominios_ID.data
		for (final FuenteDatos fuente : fuenteDatos) {
			incluirModelApi(zos, fuente, "fuenteDatos_" + fuente.getCodigo() + ".data");

			// 8. y fuenteDatos_ID.csv (se prepara manualmente el contenido)
			final FuenteDatosValores fd = dominioService.loadFuenteDatoValores(fuente.getCodigo());
			final CsvDocumento csv = CsvUtil.getCsvDocumento(fuente, fd);

			final byte[] contentsFuenteDatosCSV = CsvUtil.exportar(csv);
			final ZipEntry zeTramite = new ZipEntry("fuenteDatos_" + fuente.getCodigo() + ".csv");
			zos.putNextEntry(zeTramite);
			zos.write(contentsFuenteDatosCSV);
		}

		// 9. Incluir los formeateadores_ID.data
		for (final FormateadorFormulario formateador : formateadores) {
			incluirModelApi(zos, formateador, "formateadores_" + formateador.getId() + ".data");
		}

		zos.closeEntry();
		zos.close();
		fos.close();

		content = fos.toByteArray();

		// 10. Descargar.
		final InputStream myInputStream = new ByteArrayInputStream(content);
		return new DefaultStreamedContent(myInputStream, "application/zip", "fichero.zip");

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
		prop.setProperty("version", UtilJSF.getVersion());
		prop.setProperty("fecha", Calendar.getInstance().getTime().toString());
		prop.setProperty("usuario", UtilJSF.getSessionBean().getUserName());
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
	 * @return the tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion
	 *            the tramiteVersion to set
	 */
	public void setTramiteVersion(final TramiteVersion data) {
		this.tramiteVersion = data;
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
	 * @return the pasos
	 */
	public List<TramitePaso> getPasos() {
		return pasos;
	}

	/**
	 * @param pasos
	 *            the pasos to set
	 */
	public void setPasos(final List<TramitePaso> pasos) {
		this.pasos = pasos;
	}

	/**
	 * @return the dominios
	 */
	public List<Dominio> getDominios() {
		return dominios;
	}

	/**
	 * @param dominios
	 *            the dominios to set
	 */
	public void setDominios(final List<Dominio> dominios) {
		this.dominios = dominios;
	}

	/**
	 * @return the formateadores
	 */
	public List<FormateadorFormulario> getFormateadores() {
		return formateadores;
	}

	/**
	 * @param formateadores
	 *            the formateadores to set
	 */
	public void setFormateadores(final List<FormateadorFormulario> formateadores) {
		this.formateadores = formateadores;
	}

}
