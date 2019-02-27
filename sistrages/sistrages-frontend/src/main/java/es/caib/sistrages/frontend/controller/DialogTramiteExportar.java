package es.caib.sistrages.frontend.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.ContenidoFichero;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.ModelApi;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.GestorFicherosService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.CsvUtil;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeImportarTipo;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogTramiteExportar extends DialogControllerBase {

	/** LITERAL GUION. **/
	private static final String GUION = "-";

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogTramiteExportar.class);

	/** Servicio. **/
	@Inject
	private GestorFicherosService gestorFicherosService;

	/** Servicio. **/
	@Inject
	private ConfiguracionGlobalService configuracionGlobalService;

	/** Servicio. */
	@Inject
	private DominioService dominioService;

	/** Servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Servicio. */
	@Inject
	private EntidadService entidadService;

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

	/** Dominios Id. */
	List<Long> dominiosId;

	/** Dominios. */
	List<Dominio> dominios = new ArrayList<>();

	/** Formateadores. **/
	List<FormateadorFormulario> formateadores;

	/** Fuente de datos. **/
	List<FuenteDatos> fuenteDatos = new ArrayList<>();

	/** Formularios. **/
	List<DisenyoFormulario> formularios;

	/** Formularios. **/
	List<Fichero> ficheros;

	/** Mostrar botón exportar. **/
	private boolean mostrarBotonExportar;

	/** Idiomas del tramite version. **/
	private List<String> idiomasTramiteVersion;

	/** Nombre de los ficheros. **/
	private final Map<String, Boolean> nombreFicheros = new HashMap<>();

	/**
	 * Inicialización.
	 */
	public void init() {

		tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(id));
		idiomasTramiteVersion = UtilTraducciones.getIdiomasSoportados(tramiteVersion);
		pasos = tramiteService.getTramitePasos(Long.valueOf(id));
		dominiosId = new ArrayList<>();
		final List<Dominio> dominiosSimples = tramiteService.getDominioSimpleByTramiteId(Long.valueOf(id));
		for (final Dominio dominioSimple : dominiosSimples) {
			dominiosId.add(dominioSimple.getCodigo());
		}
		tramiteVersion.setListaPasos(pasos);
		tramiteVersion.setListaDominios(dominiosId);
		tramite = tramiteService.getTramite(tramiteVersion.getIdTramite());
		area = tramiteService.getAreaTramite(tramiteVersion.getIdTramite());

		formateadores = tramiteService.getFormateadoresTramiteVersion(Long.valueOf(id));
		formularios = tramiteService.getFormulariosTramiteVersion(Long.valueOf(id));
		ficheros = tramiteService.getFicherosTramiteVersion(Long.valueOf(id));

		for (final Long dominioId : dominiosId) {
			dominios.add(dominioService.loadDominio(dominioId));
		}

		final Map<Long, Boolean> idFuenteDatos = new HashMap<>();

		for (final Dominio dominio : dominios) {
			if (dominio.getTipo() == TypeDominio.FUENTE_DATOS && dominio.getIdFuenteDatos() != null) {
				final FuenteDatos fuentesDatos = dominioService.loadFuenteDato(dominio.getIdFuenteDatos());
				if (!idFuenteDatos.containsKey(fuentesDatos.getCodigo())) {
					fuenteDatos.add(fuentesDatos);
					idFuenteDatos.put(fuentesDatos.getCodigo(), true);
				}
			}
		}

		// El modo debug no se activa al exportar.
		tramiteVersion.setDebug(false);

		if (checkTodoCorrecto()) {
			mostrarBotonExportar = true;
		} else {
			mostrarBotonExportar = false;
		}

	}

	/**
	 * Comprueba que todo está correcto. Es decir, que los scripts están correctos y
	 * que los literales están rellenos en todos los idiomas del tramite version.
	 *
	 * TODO Faltaría comprobar los scripts.
	 *
	 * @return
	 */
	private boolean checkTodoCorrecto() {

		boolean todoCorrecto = true;

		for (final TramitePaso paso : this.pasos) {

			boolean continuar = true;
			if (paso instanceof TramitePasoTasa && checkLiteralIncompleto((TramitePasoTasa) paso)) {
				continuar = false;
			} else if (paso instanceof TramitePasoAnexar && checkLiteralIncompleto((TramitePasoAnexar) paso)) {
				continuar = false;
			} else if (paso instanceof TramitePasoDebeSaber && checkLiteralIncompleto((TramitePasoDebeSaber) paso)) {
				continuar = false;
			} else if (paso instanceof TramitePasoRegistrar && checkLiteralIncompleto((TramitePasoRegistrar) paso)) {
				continuar = false;
			} else if (paso instanceof TramitePasoRellenar && checkLiteralIncompleto((TramitePasoRellenar) paso)) {
				continuar = false;
			}

			if (!continuar) {
				todoCorrecto = false;
				break;
			}
		}

		if (this.tramiteVersion != null && this.tramiteVersion.getBloqueada() && todoCorrecto) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteExportar.error.tramiteBloqueado"));
			todoCorrecto = false;
		}

		return todoCorrecto;

	}

	/**
	 * Comprueba que el literal tiene todos los idiomas del idioma indicado por el
	 * tramite version.
	 *
	 * @param literal
	 * @return
	 */
	private boolean literalIncompleto(final Literal literal) {
		if (literal != null) {
			for (final String idioma : idiomasTramiteVersion) {
				if (!literal.contains(idioma) || literal.getTraduccion(idioma).isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Comprueba si está incorrecto los literales de paso rellenar.
	 *
	 * @param paso
	 * @return
	 */
	private boolean checkLiteralIncompleto(final TramitePasoRellenar paso) {
		if (paso.getFormulariosTramite() != null) {
			for (final FormularioTramite formulario : paso.getFormulariosTramite()) {
				if (literalIncompleto(formulario.getDescripcion())) {
					UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
							UtilJSF.getLiteral("dialogTramiteExportar.error.incompleto.formulario.desc"));
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Comprueba si está incorrecto los literales de paso registrar.
	 *
	 * @param paso
	 * @return
	 */
	private boolean checkLiteralIncompleto(final TramitePasoRegistrar paso) {
		if (literalIncompleto(paso.getInstruccionesFinTramitacion())
				|| literalIncompleto(paso.getInstruccionesPresentacion())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteExportar.error.incompleto.registrar.inst"));
			return true;
		}

		return false;
	}

	/**
	 * Comprueba si está incorrecto los literales de paso debe saber.
	 *
	 * @param paso
	 * @return
	 */
	private boolean checkLiteralIncompleto(final TramitePasoDebeSaber paso) {
		if (literalIncompleto(paso.getInstruccionesIniciales())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteExportar.error.incompleto.debesaber.instr"));
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si está incorrecto los literales de paso anexar.
	 *
	 * @param paso
	 * @return
	 */
	private boolean checkLiteralIncompleto(final TramitePasoAnexar paso) {
		if (literalIncompleto(paso.getDescripcion())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteExportar.error.incompleto.anexar.desc"));
			return true;
		}

		if (paso.getDocumentos() != null) {
			for (final Documento documento : paso.getDocumentos()) {
				if (literalIncompleto(documento.getAyudaTexto()) || literalIncompleto(documento.getDescripcion())) {
					UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
							UtilJSF.getLiteral("dialogTramiteExportar.error.incompleto.anexo.lit"));
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Comprueba si está incorrecto los literales de paso tasa.
	 *
	 * @param paso
	 * @return
	 */
	private boolean checkLiteralIncompleto(final TramitePasoTasa paso) {
		if (literalIncompleto(paso.getDescripcion())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteExportar.error.incompleto.tasar.desc"));
			return true;
		}

		if (paso.getTasas() != null) {
			for (final Tasa tasa : paso.getTasas()) {
				if (literalIncompleto(tasa.getDescripcion())) {
					UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
							UtilJSF.getLiteral("dialogTramiteExportar.error.incompleto.tasa.lit"));
					return true;
				}
			}
		}

		return false;
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
			incluirModelApi(zos, dominio, "dominios_" + dominio.getCodigo() + ".data");
		}

		// 6. Incluir los formularios_ID.data
		for (final DisenyoFormulario formulario : formularios) {
			incluirModelApi(zos, formulario, "formularios_" + formulario.getCodigo() + ".data");
		}

		// 7. Incluir los ficheros_ID.data y ficherosContent_ID.data
		for (final Fichero fichero : ficheros) {
			incluirModelApi(zos, fichero, "ficheros_" + fichero.getCodigo() + ".data");

			// El contenido del fichero se hace manual
			final ContenidoFichero contenido = gestorFicherosService.obtenerContenidoFichero(fichero.getCodigo());
			final ZipEntry zeTramiteVersion = new ZipEntry("ficherosContent_" + fichero.getCodigo() + ".data");
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
			incluirModelApi(zos, formateador, "formateadores_" + formateador.getCodigo() + ".data");
		}

		zos.closeEntry();
		zos.close();
		fos.close();

		content = fos.toByteArray();

		// 10. Descargar.
		final InputStream myInputStream = new ByteArrayInputStream(content);
		return new DefaultStreamedContent(myInputStream, "application/zip", getNombreZip() + ".zip");

	}

	/**
	 * Obtiene el nombre del fichero, que es tramit-versio-release-entorn.zip
	 *
	 * @return
	 */
	private String getNombreZip() {
		return "TRA-" + this.tramite.getIdentificador() + GUION + this.tramiteVersion.getNumeroVersion() + GUION
				+ this.tramiteVersion.getRelease() + GUION + UtilJSF.getEntorno();
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
		prop.setProperty("tipo", TypeImportarTipo.TRAMITE.toString());
		final String dir3entidad = entidadService.loadEntidad(UtilJSF.getIdEntidad()).getCodigoDIR3();
		prop.setProperty("entidad", dir3entidad);
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
		final ZipEntry zipEntry = new ZipEntry(nombreFichero);
		if (!nombreFicheros.containsKey(nombreFichero)) {
			nombreFicheros.put(nombreFichero, true);
			zos.putNextEntry(zipEntry);
			zos.write(UtilCoreApi.serialize(model));
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *
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
	 * @return the dominiosId
	 */
	public List<Long> getDominiosId() {
		return dominiosId;
	}

	/**
	 * @param dominiosId
	 *            the dominiosId to set
	 */
	public void setDominiosId(final List<Long> dominios) {
		this.dominiosId = dominios;
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
	 * @return the fuenteDatos
	 */
	public List<FuenteDatos> getFuenteDatos() {
		return fuenteDatos;
	}

	/**
	 * @param fuenteDatos
	 *            the fuenteDatos to set
	 */
	public void setFuenteDatos(final List<FuenteDatos> fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	/**
	 * @return the formularios
	 */
	public List<DisenyoFormulario> getFormularios() {
		return formularios;
	}

	/**
	 * @param formularios
	 *            the formularios to set
	 */
	public void setFormularios(final List<DisenyoFormulario> formularios) {
		this.formularios = formularios;
	}

	/**
	 * @return the ficheros
	 */
	public List<Fichero> getFicheros() {
		return ficheros;
	}

	/**
	 * @param ficheros
	 *            the ficheros to set
	 */
	public void setFicheros(final List<Fichero> ficheros) {
		this.ficheros = ficheros;
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

}
