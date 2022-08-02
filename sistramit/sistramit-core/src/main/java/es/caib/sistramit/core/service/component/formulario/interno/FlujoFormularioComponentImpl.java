package es.caib.sistramit.core.service.component.formulario.interno;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RPlantillaFormulario;
import es.caib.sistramit.core.api.exception.AccionPersonalizadaNoExisteException;
import es.caib.sistramit.core.api.exception.EncodeException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ErrorPdfFormularioException;
import es.caib.sistramit.core.api.exception.FormularioFinalizadoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.comun.types.TypeValidacion;
import es.caib.sistramit.core.api.model.formulario.AccionFormulario;
import es.caib.sistramit.core.api.model.formulario.AccionFormularioPersonalizada;
import es.caib.sistramit.core.api.model.formulario.Captcha;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoSelector;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionModificadaCampo;
import es.caib.sistramit.core.api.model.formulario.MensajeValidacion;
import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.RecursosFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoBuscadorDinamico;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarPagina;
import es.caib.sistramit.core.api.model.formulario.SesionFormularioInfo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValoresPosiblesCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeCaptcha;
import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.formulario.interno.formateadores.FormateadorPdfFormulario;
import es.caib.sistramit.core.service.component.formulario.interno.utils.CalculoDatosFormularioHelper;
import es.caib.sistramit.core.service.component.formulario.interno.utils.ConfiguracionFormularioHelper;
import es.caib.sistramit.core.service.component.formulario.interno.utils.UtilsFormularioInterno;
import es.caib.sistramit.core.service.component.formulario.interno.utils.ValidacionesFormularioHelper;
import es.caib.sistramit.core.service.component.formulario.interno.utils.ValoresPosiblesFormularioHelper;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.model.formulario.interno.DatosFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.DependenciaCampo;
import es.caib.sistramit.core.service.model.formulario.interno.PaginaFormularioData;
import es.caib.sistramit.core.service.model.formulario.types.TipoFinalizacionFormulario;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FormularioDao;
import es.caib.sistramit.core.service.util.UtilsCaptcha;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsFormulario;
import es.caib.sistramit.core.service.util.UtilsSTG;

@Component("flujoFormularioComponent")
@Scope(value = "prototype")
public class FlujoFormularioComponentImpl implements FlujoFormularioComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Datos de sesión del formulario. */
	private DatosSesionFormularioInterno datosSesion;

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Dao para acceso a bbdd. */
	@Autowired
	private FormularioDao dao;

	/** Helper para calculo de la configuracion de un campo. */
	@Autowired
	private ConfiguracionFormularioHelper configuracionFormularioHelper;

	/** Helper para calculo de valores posibles de un campo. */
	@Autowired
	private ValoresPosiblesFormularioHelper valoresPosiblesFormularioHelper;

	/** Helper para validaciones. */
	@Autowired
	private ValidacionesFormularioHelper validacionesFormularioHelper;

	/** Helper para calculo de datos. */
	@Autowired
	private CalculoDatosFormularioHelper calculoDatosFormularioHelper;

	/** Literales negocio. */
	@Autowired
	private Literales literales;

	@Override
	public String cargarSesion(final String ticket) {

		// Obtenemos datos inicio de sesión
		final DatosInicioSesionFormulario dis = dao.obtenerDatosInicioSesionGestorFormularios(ticket, true);

		// Obtenemos definición del tramite
		final DefinicionTramiteSTG defTramite = configuracionComponent.recuperarDefinicionTramite(dis.getIdTramite(),
				dis.getVersionTramite(), dis.getIdioma());

		// Verificamos si ha variado la release
		if (defTramite.getDefinicionVersion().getRelease() != dis.getReleaseTramite()) {
			throw new ErrorConfiguracionException("Ha variat la release del tràmit: " + dis.getReleaseTramite() + " vs "
					+ defTramite.getDefinicionVersion().getRelease());
		}

		// Generamos datos sesion en memoria
		datosSesion = new DatosSesionFormularioInterno();
		datosSesion.setIdFormulario(dis.getIdFormulario());
		datosSesion.setTicket(ticket);
		datosSesion.setDefinicionTramite(defTramite);
		datosSesion.setDatosInicioSesion(dis);
		datosSesion.setDebugEnabled(UtilsSTG.isDebugEnabled(defTramite));

		// Devolvemos id sesion (usamos ticket)
		// TODO id sesion formulario?? separamos en campo nuevo??
		return ticket;

	}

	@Override
	public void inicializarSesion() {
		// Inicializamos datos formulario y los almacenamos en sesion
		final DatosInicioSesionFormulario dis = datosSesion.getDatosInicioSesion();
		final RFormularioTramite defFormulario = UtilsFormularioInterno.obtenerDefinicionFormulario(datosSesion);
		final DatosFormularioInterno datForm = inicializarConfiguracionFormulario(defFormulario,
				dis.getXmlDatosActuales());
		datosSesion.setDatosFormulario(datForm);
	}

	@Override
	public PaginaFormulario cargarPaginaActual() {

		// Obtenemos definicion formulario pagina actual
		final RFormularioTramite formDef = UtilsFormularioInterno.obtenerDefinicionFormulario(datosSesion);
		final RPaginaFormulario paginaDef = UtilsFormularioInterno.obtenerDefinicionPaginaActual(datosSesion);

		// Obtenemos datos página actual
		final PaginaFormularioData pagData = datosSesion.getDatosFormulario().getPaginaActualFormulario();

		// Calculamos valores posibles campos selectores
		final List<ValoresPosiblesCampo> vpp = valoresPosiblesFormularioHelper
				.calcularValoresPosiblesPaginaActual(datosSesion);

		// Calculamos estado dinámico de los campos y actualizamos configuración campos
		final List<ConfiguracionModificadaCampo> confDinamica = configuracionFormularioHelper
				.evaluarEstadoCamposPagina(datosSesion);
		for (final ConfiguracionModificadaCampo confDinamicaCampo : confDinamica) {
			final ConfiguracionCampo confCampo = pagData.getConfiguracionCampo(confDinamicaCampo.getId());
			confCampo.setSoloLectura(confDinamicaCampo.getSoloLectura());
		}

		// Calculamos acciones a mostrar
		final List<AccionFormulario> acciones = configuracionFormularioHelper.evaluarAccionesPaginaActual(datosSesion);

		// Html pagina
		String html = null;
		try {
			html = new String(Base64.decodeBase64(paginaDef.getHtmlB64()), "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new EncodeException(e);
		}

		// Devolvemos información página actual
		final PaginaFormulario pagAct = UtilsFormularioInterno.convertToPaginaFormulario(pagData);
		pagAct.setPermitirGuardar(TypeSiNo.fromBoolean(formDef.getFormularioInterno().isPermitirGuardarSinFinalizar()));
		pagAct.setValoresPosibles(vpp);
		pagAct.setHtml(html);
		pagAct.setAcciones(acciones);
		final RecursosFormulario recursos = calcularRecursosEstaticos(paginaDef, pagData);
		pagAct.setRecursos(recursos);

		// Ajuste valores selectores (no obligatorios, radios,...)
		ajustarSelectoresPagina(pagAct);

		return pagAct;
	}

	@Override
	public PaginaFormulario cargarPaginaAnterior(final List<ValorCampo> valores) {
		// Actualizamos valores actuales
		final PaginaFormularioData paginaActual = datosSesion.getDatosFormulario().getPaginaActualFormulario();
		paginaActual.actualizarValoresPagina(valores);
		// Pop página actual
		// - Pasamos a posteriores, cargando página anterior como actual
		datosSesion.getDatosFormulario().popPaginaFormulario();
		// Carga pagina actual
		return this.cargarPaginaActual();
	}

	@Override
	public ResultadoEvaluarCambioCampo evaluarCambioCampoPagina(final String idCampo,
			final List<ValorCampo> valoresPagina) {
		// Calculamos como se quedan los datos tras el cambio del campo
		return calculoDatosFormularioHelper.calcularDatosPaginaCambioCampo(datosSesion, idCampo, valoresPagina);
	}

	@Override
	public ResultadoGuardarPagina guardarPagina(final List<ValorCampo> valoresPagina,
			final String accionPersonalizada) {
		return guardarPaginaImpl(valoresPagina, accionPersonalizada, false);
	}

	@Override
	public ResultadoGuardarPagina guardarSalirPagina(final List<ValorCampo> valoresPagina) {
		return guardarPaginaImpl(valoresPagina, null, true);
	}

	@Override
	public ResultadoGuardarPagina cancelarFormulario() {
		final DatosFinalizacionFormulario datosFinSesion = new DatosFinalizacionFormulario();
		datosFinSesion.setEstadoFinalizacion(TipoFinalizacionFormulario.CANCELADO);
		dao.finalizarSesionGestorFormularios(datosSesion.getTicket(), datosFinSesion);

		// Retornamos indicando que esta finalizado y url redireccion
		final ResultadoGuardarPagina res = new ResultadoGuardarPagina();
		res.setFinalizado(TypeSiNo.SI);
		res.setUrl(this.configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL)
				+ ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_FORMULARIO_INTERNO + "?idPaso="
				+ datosSesion.getDatosInicioSesion().getIdPaso() + "&idFormulario="
				+ datosSesion.getDatosInicioSesion().getIdFormulario() + "&ticket=" + datosSesion.getTicket());
		return res;
	}

	@Override
	public List<ValorCampo> deserializarValoresCampos(final Map<String, String> valores) {
		final List<ValorCampo> lista = new ArrayList<>();
		for (final String idCampo : valores.keySet()) {
			final String valorSerializado = valores.get(idCampo);
			final ValorCampo vc = UtilsFormularioInterno.deserializarValorCampo(idCampo, valorSerializado);
			lista.add(vc);
		}
		return lista;
	}

	@Override
	public SesionFormularioInfo obtenerInformacionFormulario() {
		SesionFormularioInfo res = null;
		if (datosSesion != null && datosSesion.getDatosInicioSesion() != null) {
			res = new SesionFormularioInfo();
			res.setIdSesionTramitacion(datosSesion.getDatosInicioSesion().getIdSesionTramitacion());
			res.setDebugEnabled(datosSesion.isDebugEnabled());
		}
		return res;
	}

	@Override
	public ResultadoBuscadorDinamico buscadorDinamico(final String idCampo, final String textoCampo,
			final List<ValorCampo> valoresPagina) {
		return calculoDatosFormularioHelper.calcularValoresPosiblesSelectorDinamico(datosSesion, idCampo, textoCampo,
				valoresPagina);
	}

	@Override
	public Captcha generarImagenCaptcha(final String idCampo) {
		return generarCaptchaCampo(idCampo, datosSesion.getDatosInicioSesion().getIdioma(), TypeCaptcha.IMAGEN);
	}

	@Override
	public Captcha generarSonidoCaptcha(final String idCampo) {
		return generarCaptchaCampo(idCampo, datosSesion.getDatosInicioSesion().getIdioma(), TypeCaptcha.SONIDO);
	}

	@Override
	public void regenerarCaptcha(final String idCampo) {
		// Generamos nuevo texto de captcha
		final String textCaptcha = UtilsCaptcha.generarKeyCaptcha();
		// Obtenemos datos página actual
		final PaginaFormularioData pagData = datosSesion.getDatosFormulario().getPaginaActualFormulario();
		// Establecemos nuevo valor
		if (pagData.getConfiguracionCampo(idCampo).getTipo() != TypeCampo.CAPTCHA) {
			throw new ErrorConfiguracionException("Camp " + idCampo + " no es captcha");
		}
		final ValorCampoSimple vc = new ValorCampoSimple(idCampo, textCaptcha);
		pagData.reinicializarValorCampo(vc);
	}

	// ---------------------------------------------------------------------------------------
	// Métodos utilidad
	// ---------------------------------------------------------------------------------------

	/**
	 * Inicializa configuración formulario.
	 *
	 * @param defForm
	 *                              Definición formulario
	 * @param xmlDatosIniciales
	 *                              xml datos iniciales
	 * @return Genera datos formulario interno
	 */
	private DatosFormularioInterno inicializarConfiguracionFormulario(final RFormularioTramite defForm,
			final byte[] xmlDatosIniciales) {

		// Parseamos datos iniciales
		final XmlFormulario xmlForm = UtilsFormulario.xmlToValores(xmlDatosIniciales);
		final List<ValorCampo> valoresIniciales = xmlForm.getValores();

		// Datos formulario interno: configuracion y datos.
		final DatosFormularioInterno df = new DatosFormularioInterno();
		df.setValoresIniciales(valoresIniciales);

		// Inicializamos página inicial (las siguiente paginas se inicializaran conforme
		// se acceda)
		final PaginaFormularioData ip = inicializarPagina(defForm.getIdentificador(), ConstantesNumero.N1, defForm,
				valoresIniciales);

		// Añadimos pagina a datos formulario
		df.pushPaginaFormulario(ip);

		return df;
	}

	/**
	 * Inicializa página del formulario (configuración y datos).
	 *
	 * @param idFormulario
	 *                         id formulario
	 * @param indiceDef
	 *                         indice definición
	 * @param defForm
	 *                         definición formulario
	 * @param valoresCampo
	 *                         valores campo
	 * @return
	 */
	private PaginaFormularioData inicializarPagina(final String idFormulario, final int indiceDef,
			final RFormularioTramite defForm, final List<ValorCampo> valoresCampo) {

		// Definicion pagina
		final RPaginaFormulario defPagina = defForm.getFormularioInterno().getPaginas()
				.get(indiceDef - ConstantesNumero.N1);

		// Añadimos pagina
		final PaginaFormularioData paginaForm = new PaginaFormularioData(idFormulario, indiceDef);
		paginaForm.setIdentificador(defPagina.getIdentificador());
		paginaForm.setMostrarTitulo(TypeSiNo.fromBoolean(defForm.getFormularioInterno().isMostrarTitulo()));
		paginaForm.setTitulo(defForm.getFormularioInterno().getTitulo());

		// Establecemos campos página
		final List<DependenciaCampo> dependenciasPagina = new ArrayList<>();
		for (final RComponente campoDef : UtilsFormularioInterno.devuelveListaCampos(defPagina)) {

			// Calculamos valor inicial campo (si no tiene valor inicial, sera valor vacio)
			ValorCampo valorInicialCampo = UtilsFormularioInterno.buscarValorCampo(valoresCampo,
					campoDef.getIdentificador());
			if (valorInicialCampo == null) {
				// Crea valor vacío según campo
				valorInicialCampo = UtilsFormularioInterno.crearValorVacio(campoDef);
			}

			// Si es captcha, reinicia valor
			if (UtilsSTG.traduceTipoCampo(campoDef.getTipo()) == TypeCampo.CAPTCHA) {
				valorInicialCampo = new ValorCampoSimple(campoDef.getIdentificador(), UtilsCaptcha.generarKeyCaptcha());
			}

			// Calculamos dependencias con otros campos
			final DependenciaCampo dependenciasCampo = UtilsFormularioInterno.buscarDependenciasCampo(campoDef);

			// Calculamos configuracion campo
			final ConfiguracionCampo confCampo = configuracionFormularioHelper.obtenerConfiguracionCampo(campoDef);

			// Inicializamos campo: configuracion, valor inicial y dependencias
			paginaForm.inicializaCampo(confCampo, valorInicialCampo);

			// Añadimos las dependencias del campo
			dependenciasPagina.add(dependenciasCampo);

			// Recursos formulario (imagenes)
			// TODO Pendiente añadir

			// Acciones personalizadas
			// TODO Pendiente añadir

		}

		// Establece que campos son evaluables
		final List<String> camposEvaluables = new ArrayList<>();
		for (final DependenciaCampo dc : dependenciasPagina) {
			final List<String> dependenciasCampo = dc.getDependencias();
			if (!dependenciasCampo.isEmpty()) {
				camposEvaluables.addAll(dependenciasCampo);
			}
		}
		for (final String idCampoEvaluable : camposEvaluables) {
			final ConfiguracionCampo configuracionCampoEvaluable = paginaForm.getConfiguracionCampo(idCampoEvaluable);
			// Los ocultos no disparan evaluacion desde frontal
			if (configuracionCampoEvaluable != null && configuracionCampoEvaluable.getTipo() != TypeCampo.OCULTO) {
				configuracionCampoEvaluable.setEvaluar(TypeSiNo.SI);
			}
		}

		// Devolvemos pagina inicializada y las dependencias de los campos
		paginaForm.setDependencias(dependenciasPagina);
		return paginaForm;
	}

	/**
	 * Realiza validaciones al guardar la pagina.
	 *
	 * @param datosSesion
	 *                                Datos sesion formulario
	 * @param accionPersonalizada
	 *                                Accion personalizada
	 * @return Mensaje validación en caso de que exista
	 */
	private MensajeValidacion validarGuardarPagina(final DatosSesionFormularioInterno datosSesion,
			final String accionPersonalizada) {
		// - Si se ejecuta una accion personalizada comprobamos si debemos validar
		boolean validar = true;
		if (StringUtils.isNotBlank(accionPersonalizada)) {
			final AccionFormularioPersonalizada accion = datosSesion.getDatosFormulario().getPaginaActualFormulario()
					.buscarAccion(accionPersonalizada);
			if (accion == null) {
				throw new AccionPersonalizadaNoExisteException(datosSesion.getIdFormulario(), accionPersonalizada);
			}
			if (accion.getValidar() == TypeSiNo.NO) {
				validar = false;
			}
		}

		MensajeValidacion mensaje = null;
		if (validar) {
			// Formato campos (mismas validaciones que en js). Si no pasa formato se genera
			// excepción.
			validacionesFormularioHelper.validarConfiguracionCampos(datosSesion);
			// Validacion script campos
			mensaje = validacionesFormularioHelper.validarScriptValidacionCampos(datosSesion);
			// Script validacion de la pagina
			if (mensaje == null) {
				mensaje = validacionesFormularioHelper.validarScriptValidacionPagina(datosSesion);
			}
		}

		return mensaje;
	}

	/**
	 * Finaliza formulario.
	 *
	 * @param pDatosSesion
	 *                                Datos sesion
	 * @param accionPersonalizada
	 *                                Accion personalizada
	 * @param salirSinFinalizar
	 *                                Indica si se sale sin finalizar
	 */
	private void finalizarFormulario(final DatosSesionFormularioInterno pDatosSesion, final String accionPersonalizada,
			final boolean salirSinFinalizar) {

		// Obtenemos valores página
		// - Valores hasta página actual (será la final si se sale finalizando)
		final List<ValorCampo> valoresFormulario = pDatosSesion.getDatosFormulario().getValoresAccesiblesPaginaActual();
		// - Si se sale sin finalizar cogemos también las páginas posteriores
		if (salirSinFinalizar) {
			// Resto pagina posteriores rellenadas
			valoresFormulario.addAll(pDatosSesion.getDatosFormulario().getValoresPosterioresPaginaActual());
			// Resto valores que aparecen en valores iniciales y no están
			for (final ValorCampo vc : pDatosSesion.getDatosFormulario().getValoresIniciales()) {
				if (UtilsFormularioInterno.buscarValorCampo(valoresFormulario, vc.getId()) == null) {
					valoresFormulario.add(vc);
				}
			}
		}
		// TODO Cuando haya captchas, hay que eliminarlos
		// Generamos xml
		final XmlFormulario formXml = new XmlFormulario(valoresFormulario, accionPersonalizada);
		final byte[] xml = UtilsFormulario.valoresToXml(formXml);
		// Generamos pdf
		byte[] pdf = null;
		if (!salirSinFinalizar) {
			final RPlantillaFormulario plantillaPdf = configuracionFormularioHelper
					.obtenerPlantillaPdfVisualizacion(pDatosSesion);
			pdf = generarPdfFormulario(pDatosSesion.getDefinicionTramite(), pDatosSesion.getDatosInicioSesion(),
					plantillaPdf, xml);
		}
		// Retornamos al flujo de tramitacion
		final DatosFinalizacionFormulario datosFinSesion = new DatosFinalizacionFormulario();
		datosFinSesion.setXml(xml);
		datosFinSesion.setPdf(pdf);
		if (salirSinFinalizar) {
			datosFinSesion.setEstadoFinalizacion(TipoFinalizacionFormulario.SIN_FINALIZAR);
		} else {
			datosFinSesion.setEstadoFinalizacion(TipoFinalizacionFormulario.FINALIZADO);
		}
		dao.finalizarSesionGestorFormularios(pDatosSesion.getTicket(), datosFinSesion);
	}

	/**
	 * Genera PDF formulario.
	 *
	 * @param definicionTramiteSTG
	 *                                        Definición trámite
	 * @param datosInicioSesionFormulario
	 *                                        Datos inicio sesión
	 *
	 * @param plantillaPdf
	 *                                        Plantilla PDF
	 * @param xml
	 *                                        XML Formulario
	 * @return PDF
	 */
	private byte[] generarPdfFormulario(final DefinicionTramiteSTG definicionTramiteSTG,
			final DatosInicioSesionFormulario datosInicioSesionFormulario, final RPlantillaFormulario plantillaPdf,
			final byte[] xml) {

		// Obtenemos plantilla
		byte[] plantilla = null;
		if (StringUtils.isNotBlank(plantillaPdf.getFicheroPlantilla())) {
			final String pathFicherosExternos = configuracionComponent
					.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
			final String pathFile = pathFicherosExternos + plantillaPdf.getFicheroPlantilla();
			try (final FileInputStream fis = new FileInputStream(pathFile);) {
				plantilla = IOUtils.toByteArray(fis);
			} catch (final IOException e) {
				throw new ErrorPdfFormularioException(
						"Error generant pdf formulari, no es pot accedir a plantilla: " + e.getMessage(), e);
			}
		}

		// Obtenemos formateador
		FormateadorPdfFormulario formateador = null;
		try {
			formateador = (FormateadorPdfFormulario) Class.forName(plantillaPdf.getClaseFormateador()).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new ErrorPdfFormularioException(
					"Error generant pdf formulari a partir classname " + plantillaPdf.getClaseFormateador(), e);
		}

		// Formateamos a PDF
		final RFormularioTramite defFormulario = UtilsFormularioInterno.obtenerDefinicionFormulario(datosSesion);
		return formateador.formatear(xml, plantilla, definicionTramiteSTG.getDefinicionVersion().getIdioma(),
				defFormulario.getFormularioInterno(), datosInicioSesionFormulario.getTituloProcedimiento(),
				datosInicioSesionFormulario.getCodigoSiaProcedimiento(),
				datosInicioSesionFormulario.getDir3ResponsableProcedimiento());

	}

	/**
	 * Ajusta selectores (no obligatorios, lista radios,...)
	 *
	 * @param pagAct
	 *                   Datos página
	 */
	private void ajustarSelectoresPagina(final PaginaFormulario pagAct) {

		for (final ConfiguracionCampo cc : pagAct.getConfiguracion()) {
			if (cc.getTipo() == TypeCampo.SELECTOR) {
				final ConfiguracionCampoSelector ccs = (ConfiguracionCampoSelector) cc;
				ajustarSelector(pagAct, ccs);
			}
		}
	}

	/**
	 * Ajusta selector.
	 *
	 * @param pagAct
	 *                   Datos página
	 * @param ccs
	 *                   Componente selector
	 */
	private void ajustarSelector(final PaginaFormulario pagAct, final ConfiguracionCampoSelector ccs) {
		// Ajustes selector unico: si esta vacío se establece primera opción
		if (ccs.getContenido() == TypeSelector.UNICO) {
			// Si no tiene valor el campo, se establece primer valor posible
			final ValorCampo vc = UtilsFormularioInterno.buscarValorCampo(pagAct.getValores(), ccs.getId());
			if (vc != null && ((ValorCampoIndexado) vc).esVacio()) {
				final ValoresPosiblesCampo vp = UtilsFormularioInterno
						.buscarValoresPosibles(pagAct.getValoresPosibles(), ccs.getId());
				if (!vp.getValores().isEmpty()) {
					((ValorCampoIndexado) vc).setValor(vp.getValores().get(0));
				}
			}
		}
	}

	/**
	 * Implementación guardar página.
	 *
	 * @param valoresPagina
	 *                                Valores página
	 * @param accionPersonalizada
	 *                                Acción personalizada
	 * @param salirSinFinalizar
	 *                                Salir sin finalizar
	 *
	 * @return
	 */
	protected ResultadoGuardarPagina guardarPaginaImpl(final List<ValorCampo> valoresPagina,
			final String accionPersonalizada, final boolean salirSinFinalizar) {

		MensajeValidacion mensaje = null;

		// Verificamos que no este finalizada la sesion de formulario
		if (datosSesion.isFinalizada()) {
			throw new FormularioFinalizadoException("No es pot guardar pàgina: sessió formulari finalitzada");
		}

		// Validación captchas
		final PaginaFormularioData pagData = datosSesion.getDatosFormulario().getPaginaActualFormulario();
		mensaje = validarCapthas(pagData, valoresPagina);

		// Guardamos valores pagina
		if (mensaje == null) {
			// Almacenamos valores campos (excepto captchas)
			datosSesion.getDatosFormulario().getPaginaActualFormulario().actualizarValoresPagina(valoresPagina);
			// Realizamos validaciones
			if (!salirSinFinalizar) {
				mensaje = validarGuardarPagina(datosSesion, accionPersonalizada);
			}
		}

		// Devolvemos resultado
		final ResultadoGuardarPagina res = new ResultadoGuardarPagina();
		res.setFinalizado(TypeSiNo.NO);
		res.setValidacion(mensaje);

		// Si ha pasado la validacion guardamos pagina
		if (!UtilsFlujo.isErrorValidacion(mensaje)) {
			// Si no es pagina final, pasamos a la siguiente
			final RPaginaFormulario paginaDef = UtilsFormularioInterno.obtenerDefinicionPaginaActual(datosSesion);
			if (!salirSinFinalizar && !paginaDef.isPaginaFinal()) {
				// Evaluar cual es la siguiente pagina
				final String idPaginaSiguiente = configuracionFormularioHelper
						.evaluarScriptNavegacionPaginaActual(datosSesion);
				// Debe ser posterior a la actual
				final int indiceDefSiguiente = UtilsFormularioInterno.obtenerIndiceDefinicionPagina(datosSesion,
						idPaginaSiguiente);
				if (indiceDefSiguiente <= datosSesion.getDatosFormulario().getPaginaActualFormulario().getIndiceDef()) {
					throw new ErrorConfiguracionException(
							"El script de navegació de pàgina ha d'indicar una pàgina posterior (pàgina actual: "
									+ paginaDef.getIdentificador() + " - pàgina següent: " + idPaginaSiguiente);
				}
				// Si pagina siguiente ya se ha rellenado antes, la obtenemos
				PaginaFormularioData paginaSiguiente = datosSesion.getDatosFormulario()
						.getPaginaPosteriorFormulario(idPaginaSiguiente);
				if (paginaSiguiente == null) {
					// Si no se ha rellenado todavía la inicializamos
					final RFormularioTramite defFormulario = UtilsFormularioInterno
							.obtenerDefinicionFormulario(datosSesion);
					paginaSiguiente = inicializarPagina(datosSesion.getIdFormulario(), indiceDefSiguiente,
							defFormulario, datosSesion.getDatosFormulario().getValoresIniciales());
				}
				// Establecemos como pagina actual
				datosSesion.getDatosFormulario().pushPaginaFormulario(paginaSiguiente);
				// Recalculo autorellenables pagina (actualiza valores)
				calculoDatosFormularioHelper.recalcularDatosPagina(datosSesion);
				// Retornamos indicando que no esta finalizado para que recargue pagina
				res.setRecargar(TypeSiNo.SI);
			} else {
				// Si es la ultima pagina o saliamos a mitad, finalizamos formulario
				finalizarFormulario(datosSesion, accionPersonalizada, salirSinFinalizar);
				// Marcamos sesion formulario como finalizada
				datosSesion.setFinalizada(true);
				// Retornamos indicando que esta finalizado y url redireccion
				res.setFinalizado(TypeSiNo.SI);
				res.setUrl(this.configuracionComponent
						.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL)
						+ ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_FORMULARIO_INTERNO + "?idPaso="
						+ datosSesion.getDatosInicioSesion().getIdPaso() + "&idFormulario="
						+ datosSesion.getDatosInicioSesion().getIdFormulario() + "&ticket=" + datosSesion.getTicket());
			}
		}

		return res;
	}

	/**
	 * Valida campos captcha.
	 *
	 * @param paginaActual
	 *                          Datos página
	 * @param valoresPagina
	 * @return valores actuales campos captcha.
	 */
	private MensajeValidacion validarCapthas(final PaginaFormularioData paginaActual,
			final List<ValorCampo> valoresPagina) {

		MensajeValidacion mensaje = null;

		// Validamos captchas (si hay uno que da error, no pasa validación)
		boolean valido = true;
		String idCampo = null;
		for (final ConfiguracionCampo cc : paginaActual.getConfiguracion()) {
			if (cc.getTipo() == TypeCampo.CAPTCHA) {
				final ValorCampoSimple vcOld = (ValorCampoSimple) UtilsFormularioInterno
						.buscarValorCampo(paginaActual.getValores(), cc.getId());
				final ValorCampoSimple vcNew = (ValorCampoSimple) UtilsFormularioInterno.buscarValorCampo(valoresPagina,
						cc.getId());
				if (vcOld == null || vcNew == null
						|| !StringUtils.equalsIgnoreCase(vcOld.getValor(), vcNew.getValor())) {
					valido = false;
					idCampo = cc.getId();
					break;
				}
			}
		}

		// En caso de no pasar validacion, indicamos error validacion
		if (!valido) {
			final String errorMsg = literales.getLiteral(Literales.GESTOR_FORMULARIOS_INTERNO,
					"validacion.captcha.incorrecta", null, datosSesion.getDatosInicioSesion().getIdioma());
			mensaje = new MensajeValidacion(idCampo, TypeValidacion.ERROR, errorMsg);
		}

		// Reseteamos valores de todos los captchas (medida seguridad para forzar
		// reentrada captcha)
		for (final ConfiguracionCampo cc : paginaActual.getConfiguracion()) {
			if (cc.getTipo() == TypeCampo.CAPTCHA) {
				final String newCaptcha = UtilsCaptcha.generarKeyCaptcha();
				paginaActual.reinicializarValorCampo(new ValorCampoSimple(cc.getId(), newCaptcha));
			}
		}

		return mensaje;

	}

	/**
	 * Calcula recursos estáticos página (imágenes,...)
	 *
	 * @param paginaDef
	 *                      Definición página
	 * @param pagData
	 *                      Datos página
	 * @return recursos estáticos página
	 */
	private RecursosFormulario calcularRecursosEstaticos(final RPaginaFormulario paginaDef,
			final PaginaFormularioData pagData) {
		final RecursosFormulario recursos = new RecursosFormulario();
		// TODO PENDIENTE RECURSOS
		return recursos;
	}

	/**
	 * Genera captcha campo
	 *
	 * @param idCampo
	 *                    id campo
	 * @param tipo
	 *                    Tipo captcja
	 * @return captcha
	 */
	private Captcha generarCaptchaCampo(final String idCampo, final String idioma, final TypeCaptcha tipo) {
		// Obtenemos datos página actual
		final PaginaFormularioData pagData = datosSesion.getDatosFormulario().getPaginaActualFormulario();
		// Generamos captcha a partir valor
		if (pagData.getConfiguracionCampo(idCampo).getTipo() != TypeCampo.CAPTCHA) {
			throw new ErrorConfiguracionException("Camp " + idCampo + " no es captcha");
		}
		final ValorCampoSimple vc = (ValorCampoSimple) pagData.getValorCampo(idCampo);
		final Captcha captcha = UtilsCaptcha.generaCaptcha(vc.getValor(), idioma, tipo);
		return captcha;
	}

}
