package es.caib.sistrages.frontend.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
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
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.ModelApi;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
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
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.GestorFicherosService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.CsvUtil;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeImportarTipo;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteExportar extends DialogControllerBase {

	/** LITERAL GUION. **/
	private static final String GUION = "-";

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

	/** Servicio. */
	@Inject
	private FormularioInternoService formularioInternoService;

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

	/** GestorExternoFormularios. **/
	List<GestorExternoFormularios> gestorExternoFormularios;

	/** SeccionesReutilizables. **/
	List<SeccionReutilizable> seccionesReutilizables;

	/** Mostrar botón exportar. **/
	private boolean mostrarBotonExportar;

	/** Nombre de los ficheros. **/
	private final Map<String, Boolean> nombreFicheros = new HashMap<>();

	/** Literal de terminacion. **/
	private static final String LITERAL_SUFIJO_DATA = ".data";

	/** Tipo (IM: Importar trámite o CC: CuadernoCarga) **/
	private String modo;

	/** Cabecera. **/
	private String cabecera;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	public void init() {

		tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(id));
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
		seccionesReutilizables = tramiteService.getSeccionesTramite(Long.valueOf(id));

		formateadores = tramiteService.getFormateadoresTramiteVersion(Long.valueOf(id));
		formularios = tramiteService.getFormulariosTramiteVersion(Long.valueOf(id));
		ficheros = tramiteService.getFicherosTramiteVersion(Long.valueOf(id));
		gestorExternoFormularios = tramiteService.getGFEByTramiteVersion(Long.valueOf(id));

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

		cabecera = UtilJSF.getLiteral("dialogTramiteExportar.header." + modo);

	}

	/**
	 * Comprueba que está correcto. Es decir, que los scripts están correctos y que
	 * los literales están rellenos en todos los idiomas del tramite version.
	 *
	 *
	 * @return
	 */
	private boolean checkTodoCorrecto() {

		boolean correcto = true;
		if (this.tramiteVersion != null && this.tramiteVersion.getBloqueada()) {
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteExportar.error.tramiteBloqueado"));
			correcto = false;
		}

		if (modo.equals("CC") && this.tramiteVersion != null) {
			List<Tasa> tasas = this.tramiteVersion.getPasoTasa().getTasas();
			for (Tasa tasa : tasas) {
				if (tasa.isSimulado()) {
					addMessageContext(TypeNivelGravedad.ERROR,
							UtilJSF.getLiteral("dialogTramiteExportar.error.simular"));
					correcto = false;
					break;
				}
			}
		}

		return correcto;

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

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("tramiteExportarDialog");
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

	private boolean generandoFichero = false;

	/**
	 * Genera un resumen y descarga el fichero.
	 *
	 * @throws IOException
	 *
	 * @throws Exception
	 ***/
	public StreamedContent getFicheroResumen()  {


		final String resumen = getResumen();
		InputStream myInputStream = new ByteArrayInputStream(resumen.getBytes(Charset.forName("UTF-8")));
		return new DefaultStreamedContent(myInputStream, "application/txt", getNombreZip() + ".txt");

	}

	/**
	 * Generacion de un resumen. Pasos a realizar:<br />
	 * 0. Preparar txt. <br />
	 * 1. Incluir properties y un miniresumen <br />
	 * 2. Incluir area <br />
	 * 3. Incluir version <br />
	 * 4. Incluir tramite <br />
	 * 5. Incluir los dominios_ID <br />
	 * 6. Incluir los formulario_ID <br />
	 * 7. Incluir los ficheros_ID  <br />
	 * 8. Incluir las fuenteDatos_ID <br />
	 * 9. Incluir los formeteadores_ID<br />
	 * 10. Incluir las gestoresExternosFormulario_ID<br />
	 * 11. Incluir las seccionesReutilizables_ID<br />
	 *
	 * @throws IOException
	 *
	 * @throws Exception
	 ***/
	public String getResumen()  {


		// 0. Preparar txt.
		final StringBuilder result = new StringBuilder();

		// 1. Incluir el fichero de propiedades y un miniresumen
		result.append(prepararResumen());
		result.append("\n\n");

		// 2. Incluir el area.data
		result.append(this.area.toString("", UtilJSF.getIdioma().toString()));
		result.append("\n\n");

		// 3. Incluir el version.data
		result.append(this.tramiteVersion.toString("", UtilJSF.getIdioma().toString()));
		result.append("\n\n");

		// 4. Incluir el tramite.data
		result.append(this.tramite.toString("", UtilJSF.getIdioma().toString()));
		result.append("\n\n");

		// 5. Incluir los dominios_ID.data
		for (final Dominio dominio : dominios) {
			result.append(dominio.toString("", UtilJSF.getIdioma().toString()));
			result.append("\n\n");
		}

		// 6. Incluir los formularios_ID.data
		for (final DisenyoFormulario formulario : formularios) {
			result.append(formulario.toString("", UtilJSF.getIdioma().toString()));
			result.append("\n\n");
		}

		// 7. Incluir los ficheros_ID.data y ficherosContent_ID.data
		for (final Fichero fichero : ficheros) {
			result.append(fichero.toString("", UtilJSF.getIdioma().toString()));
			result.append("\n\n");
		}

		// 8. Incluir los dominios_ID.data
		for (final FuenteDatos fuente : fuenteDatos) {
			result.append(fuente.toString("", UtilJSF.getIdioma().toString()));

			// 8. y fuenteDatos_ID.csv (se prepara manualmente el contenido)
			final FuenteDatosValores fd = dominioService.loadFuenteDatoValores(fuente.getCodigo());
			if (fd != null) {
				result.append(fd.toString("", UtilJSF.getIdioma().toString()));
			}
			result.append("\n\n");
		}

		// 9. Incluir los formeateadores_ID.data
		for (final FormateadorFormulario formateador : formateadores) {
			result.append(formateador.toString("", UtilJSF.getIdioma().toString()));
			result.append("\n\n");
		}

		// 10. Incluir los formulariosGestoresExternos_ID.data
		for (final GestorExternoFormularios gestores : gestorExternoFormularios) {
			result.append(gestores.toString("", UtilJSF.getIdioma().toString()));
			result.append("\n\n");
		}

		// 11. Incluir los seccionesReutilizables_ID.data
		for (final SeccionReutilizable seccion : seccionesReutilizables) {
			result.append(seccion.toString("", UtilJSF.getIdioma().toString()));
			result.append("\n\n");
		}
		return result.toString();
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
	 * 10. Incluir las gestoresExternosFormulario_ID.data<br />
	 * 11. Incluir las seccionesReutilizables_ID.data<br />
	 * 12. Descargar
	 *
	 * @throws IOException
	 *
	 * @throws Exception
	 ***/
	public StreamedContent getFichero() throws Exception {

		if (generandoFichero) {
			return null;
		}

		generandoFichero = true;

		byte[] content = null;

		final ByteArrayOutputStream fos = new ByteArrayOutputStream();

		// 0. Generamos el fichero zip.
		final ZipOutputStream zos = new ZipOutputStream(fos);

		// 1. Incluir el fichero de propiedades
		prepararProperties(zos);
		prepararResumen(zos);

		// 2. Incluir el version.data
		incluirModelApi(zos, this.tramiteVersion, "version.data");

		// 3. Incluir el area.data
		incluirModelApi(zos, this.area, "area.data");

		// 4. Incluir el tramite.data
		incluirModelApi(zos, this.tramite, "tramite.data");

		// 5. Incluir los dominios_ID.data
		for (final Dominio dominio : dominios) {
			incluirModelApi(zos, dominio, "dominios_" + dominio.getCodigo() + LITERAL_SUFIJO_DATA);
		}

		// 6. Incluir los formularios_ID.data
		for (final DisenyoFormulario formulario : formularios) {
			incluirModelApi(zos, formulario, "formularios_" + formulario.getCodigo() + LITERAL_SUFIJO_DATA);
		}

		// 7. Incluir los ficheros_ID.data y ficherosContent_ID.data
		for (final Fichero fichero : ficheros) {
			incluirModelApi(zos, fichero, "ficheros_" + fichero.getCodigo() + LITERAL_SUFIJO_DATA);

			// El contenido del fichero se hace manual
			final ContenidoFichero contenido = gestorFicherosService.obtenerContenidoFichero(fichero.getCodigo());
			final ZipEntry zeTramiteVersion = new ZipEntry(
					"ficherosContent_" + fichero.getCodigo() + LITERAL_SUFIJO_DATA);
			zos.putNextEntry(zeTramiteVersion);
			zos.write(contenido.getContent());

		}

		// 8. Incluir los dominios_ID.data
		for (final FuenteDatos fuente : fuenteDatos) {
			incluirModelApi(zos, fuente, "fuenteDatos_" + fuente.getCodigo() + LITERAL_SUFIJO_DATA);

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
			incluirModelApi(zos, formateador, "formateadores_" + formateador.getCodigo() + LITERAL_SUFIJO_DATA);
		}

		// 10. Incluir los formulariosGestoresExternos_ID.data
		for (final GestorExternoFormularios gestores : gestorExternoFormularios) {
			incluirModelApi(zos, gestores, "gestoresExternosFormulario_" + gestores.getCodigo() + LITERAL_SUFIJO_DATA);
		}

		// 11. Incluir los seccionesReutilizables_ID.data
		for (final SeccionReutilizable seccion : seccionesReutilizables) {
			// Primero hay que obtener los diseños.
			final DisenyoFormulario disenyoFormulario = formularioInternoService
					.getFormularioInterno(seccion.getIdFormularioAsociado());
			seccion.setDisenyoFormulario(disenyoFormulario);
			incluirModelApi(zos, seccion, "seccionesReutilizables_" + seccion.getCodigo() + LITERAL_SUFIJO_DATA);
		}

		zos.closeEntry();
		zos.close();
		fos.close();

		content = fos.toByteArray();

		generandoFichero = false;

		// 10. Descargar.
		final InputStream myInputStream = new ByteArrayInputStream(content);
		return new DefaultStreamedContent(myInputStream, "application/zip", getNombreZip() + ".zip");

	}

	public void expOk() {
		addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.exportar.ok"));
	}

	/**
	 * Obtiene el nombre del fichero, que es tramit-versio-release-entorn.zip
	 *
	 * @return
	 */
	private String getNombreZip() {
		String nombre = "TRA-" + this.tramite.getIdentificador() + GUION + this.tramiteVersion.getNumeroVersion()
				+ GUION + this.tramiteVersion.getRelease() + GUION + UtilJSF.getEntorno();
		if (this.modo.equals(Constantes.IMPORTAR_TIPO_CC)) {

			// Indica en que entorno se tiene que cargar (DES --> PRE y PRE --> PRO)
			String zipEntorno;
			if (TypeEntorno.fromString(UtilJSF.getEntorno()) == TypeEntorno.DESARROLLO) {
				zipEntorno = "PRE";
			} else {
				zipEntorno = "PRO";
			}
			nombre = "CC" + zipEntorno + "-" + nombre;
		}
		return nombre;
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
	 * Perpara el resumen.
	 *
	 * @return
	 * @throws IOException
	 */
	private String prepararResumen() {
		StringBuilder texto = new StringBuilder();
		texto.append("Entorno:"); texto.append(UtilJSF.getEntorno()); texto.append("\n");
		texto.append("Version:"); texto.append(getVersion());texto.append("\n");
		texto.append("Fecha:"); texto.append( Calendar.getInstance().getTime().toString());texto.append("\n");
		texto.append("Usuario:"); texto.append( UtilJSF.getSessionBean().getUserName());texto.append("\n");
		//texto.append("tipo:"); texto.append( TypeImportarTipo.TRAMITE.toString());texto.append("\n");
		final String dir3entidad = entidadService.loadEntidad(UtilJSF.getIdEntidad()).getCodigoDIR3();
		texto.append("Entidad:"); texto.append( dir3entidad);texto.append("\n");
		if (area != null) {
			texto.append("Area:"); texto.append( area.getIdentificador());texto.append("\n");
		}
		//texto.append("modo:"); texto.append( modo);texto.append("\n");
		texto.append("Revision:"); texto.append( UtilJSF.getRevision());texto.append("\n");
		if (this.tramiteVersion.getHuella() == null) {
			texto.append("Huella:"); texto.append( "SIN_HUELLA");texto.append("\n");
		} else {
			texto.append("Huella:"); texto.append( this.tramiteVersion.getHuella());texto.append("\n");
		}

		texto.append("Tramite : " + tramite.getIdentificadorCompuesto()+" Release "+ tramiteVersion.getRelease()+"\n");

		if (tramiteVersion.getListaPasos() != null && !tramiteVersion.getListaPasos() .isEmpty()) {

			StringBuilder resDebeSaber = new StringBuilder("");
			StringBuilder resRellenar = new StringBuilder("");
			StringBuilder resTasa = new StringBuilder("");
			StringBuilder resAnexar = new StringBuilder("");
			StringBuilder resRegistrar = new StringBuilder("");
			for(TramitePaso paso : tramiteVersion.getListaPasos() ) {
        		   if (paso instanceof TramitePasoDebeSaber) {
        			   resDebeSaber.append(paso.getIdPasoTramitacion()+",");

        		   } else if (paso instanceof TramitePasoRellenar) {
        			   resRellenar.append(paso.getIdPasoTramitacion());

        			   StringBuilder resForms= new StringBuilder();
    				   if (( (TramitePasoRellenar)paso ).getFormulariosTramite() != null) {
        				   for( FormularioTramite form : ( (TramitePasoRellenar)paso ).getFormulariosTramite()) {
        					   resForms.append(form.getIdentificador()+",");
        				   }
        			   }
    				   resRellenar.append(paso.getIdPasoTramitacion());
        			   if (!resForms.toString().isEmpty()) {
        				   resRellenar.append("[FORMULARIOS:"+resForms.toString()+"]");
        			   }
        			   resRellenar.append(",");

        		   } else if (paso instanceof TramitePasoTasa) {
        			   StringBuilder resTasas= new StringBuilder();
    				   if (( (TramitePasoTasa)paso ).getTasas() != null) {
        				   for(Tasa tasa : ( (TramitePasoTasa)paso ).getTasas()) {
        					   resTasas.append(tasa.getIdentificador()+",");
        				   }
        			   }
        			   resTasa.append(paso.getIdPasoTramitacion());
        			   if (!resTasas.toString().isEmpty()) {
        				   resTasa.append("[TASAS:"+resTasas.toString()+"]");
        			   }
        			   resTasa.append(",");
        		   } else if (paso instanceof TramitePasoAnexar) {
        			   StringBuilder resTasas= new StringBuilder();
    				   if (( (TramitePasoAnexar)paso ).getDocumentos() != null) {
        				   for(Documento doc : ( (TramitePasoAnexar)paso ).getDocumentos()) {
        					   resTasas.append(doc.getIdentificador()+",");
        				   }
        			   }
        			   resAnexar.append(paso.getIdPasoTramitacion());
        			   if (!resTasas.toString().isEmpty()) {
        				   resAnexar.append("[DOCS:"+resTasas.toString()+"]");
        			   }
        			   resAnexar.append(",");

        		   } else if (paso instanceof TramitePasoRegistrar) {
        			   resRegistrar.append(paso.getIdPasoTramitacion());
        			   resRegistrar.append(",");
        		   }
        	   }

			texto.append("PASOS. PASOS DEBE SABER --> " + resDebeSaber.toString()+
							   " PASOS RELLENAR -->" + resRellenar.toString() +
							   " PASOS TASA -->" + resTasa.toString() +
							   " PASOS ANEXAR -->" + resAnexar.toString() +
							   " PASOS REGISTAR -->" + resRegistrar.toString()+"\n");

		}

		if (dominios != null && !dominios.isEmpty()) {
			StringBuilder res = new StringBuilder("Dominios:");
			for (final Dominio dominio : dominios) {
				res.append(dominio.getIdentificador() + " (tipus "+dominio.getTipo()+"),");
			}
			String resultado = res.toString();
			if (resultado.endsWith(",")) {
				resultado = resultado.substring(0, resultado.length()-1)+"\n";
			}
			texto.append(resultado+"\n");
		}

		if (formateadores != null && !formateadores.isEmpty()) {

			StringBuilder res = new StringBuilder("Formateadores:");
			for (final FormateadorFormulario formateador : formateadores) {
				res.append(formateador.getIdentificador() + ",");
			}
			String resultado = res.toString();
			if (resultado.endsWith(",")) {
				resultado = resultado.substring(0, resultado.length()-1)+"\n";
			}
			texto.append(resultado+"\n");
		}
		if (gestorExternoFormularios != null && !gestorExternoFormularios.isEmpty()) {


			StringBuilder res = new StringBuilder("Gestores:");
			for (final GestorExternoFormularios gestores : gestorExternoFormularios) {
				res.append(gestores.getIdentificador() + ",");
			}
			String resultado = res.toString();
			if (resultado.endsWith(",")) {
				resultado = resultado.substring(0, resultado.length()-1)+"\n";
			}
			texto.append(resultado+"\n");
		}

		if (seccionesReutilizables != null && !seccionesReutilizables.isEmpty()) {

			StringBuilder res = new StringBuilder("SeccionesReutilizables:");
			for (final SeccionReutilizable seccion : seccionesReutilizables) {
				res.append(seccion.getIdentificador() + ",");
			}
			String resultado = res.toString();
			if (resultado.endsWith(",")) {
				resultado = resultado.substring(0, resultado.length()-1)+"\n";
			}
			texto.append(resultado+"\n");
		}


		return texto.toString();
	}

	/**
	 * Perpara el fichero de properties.
	 *
	 * @return
	 * @throws IOException
	 */
	private void prepararResumen(final ZipOutputStream zos) throws IOException {

		final String resumen = getResumen();
		final ByteArrayOutputStream output = new ByteArrayOutputStream();
		output.write(resumen.getBytes());

		final ZipEntry zeProperties = new ZipEntry("resumen.txt");
		zos.putNextEntry(zeProperties);
		zos.write(output.toByteArray());



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
		prop.setProperty("modo", modo);
		prop.setProperty("revision", UtilJSF.getRevision());
		if (this.tramiteVersion.getHuella() == null) {
			prop.setProperty("huella", "SIN_HUELLA");
		} else {
			prop.setProperty("huella", this.tramiteVersion.getHuella());
		}
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
	 *           the id to set
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
	 * @param tramiteVersion the tramiteVersion to set
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
	 * @param tramite the tramite to set
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
	 * @param area the area to set
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
	 * @param pasos the pasos to set
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
	 * @param dominiosId the dominiosId to set
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
	 * @param formateadores the formateadores to set
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
	 * @param dominios the dominios to set
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
	 * @param fuenteDatos the fuenteDatos to set
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
	 * @param formularios the formularios to set
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
	 * @param ficheros the ficheros to set
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
	 * @param mostrarBotonExportar the mostrarBotonExportar to set
	 */
	public void setMostrarBotonExportar(final boolean mostrarBotonExportar) {
		this.mostrarBotonExportar = mostrarBotonExportar;
	}

	/**
	 * @return the cabecera
	 */
	public String getCabecera() {
		return cabecera;
	}

	/**
	 * @param cabecera the cabecera to set
	 */
	public void setCabecera(final String cabecera) {
		this.cabecera = cabecera;
	}

	/**
	 * @return the modo
	 */
	public final String getModo() {
		return modo;
	}

	/**
	 * @param modo the modo to set
	 */
	public final void setModo(final String modo) {
		this.modo = modo;
	}

	/**
	 * @return the gestorExternoFormularios
	 */
	public List<GestorExternoFormularios> getGestorExternoFormularios() {
		return gestorExternoFormularios;
	}

	/**
	 * @param gestorExternoFormularios the gestorExternoFormularios to set
	 */
	public void setGestorExternoFormularios(List<GestorExternoFormularios> gestorExternoFormularios) {
		this.gestorExternoFormularios = gestorExternoFormularios;
	}

	/**
	 * @return the seccionesReutilizables
	 */
	public List<SeccionReutilizable> getSeccionesReutilizables() {
		return seccionesReutilizables;
	}

	/**
	 * @param seccionesReutilizables the seccionesReutilizables to set
	 */
	public void setSeccionesReutilizables(List<SeccionReutilizable> seccionesReutilizables) {
		this.seccionesReutilizables = seccionesReutilizables;
	}

}
