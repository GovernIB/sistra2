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

import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RFormularioInterno;
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
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoListaElementos;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoSelector;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionModificadaCampo;
import es.caib.sistramit.core.api.model.formulario.MensajeValidacion;
import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.RecursosFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoBuscadorDinamico;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarElemento;
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
import es.caib.sistramit.core.service.model.formulario.interno.DatosEdicionElemento;
import es.caib.sistramit.core.service.model.formulario.interno.DatosFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.DependenciaCampo;
import es.caib.sistramit.core.service.model.formulario.interno.PaginaData;
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
		datosSesion = new DatosSesionFormularioInterno(dis, defTramite);

		// Devolvemos id sesion (usamos ticket)
		// TODO id sesion formulario?? separamos en campo nuevo??
		return ticket;

	}

	@Override
	public void inicializarSesion() {
		// Inicializamos datos formulario y los almacenamos en sesion
		final DatosInicioSesionFormulario dis = datosSesion.getDatosInicioSesion();
		final RFormularioInterno defFormulario = datosSesion.obtenerDefinicionFormularioInterno();
		final DatosFormularioInterno datForm = inicializarConfiguracionFormulario(
				datosSesion.getDatosInicioSesion().getIdFormulario(), defFormulario, dis.getXmlDatosActuales());
		datosSesion.inicializarDatosFormulario(datForm);
	}

	@Override
	public PaginaFormulario cargarPaginaFormularioActual() {

		// Calculamos estado dinámico de los campos y actualizamos configuración campos
		actualizarEstadoCamposPagina(datosSesion, false, false);

		// Genera pagina formulario
		final PaginaFormulario pagAct = generarPaginaFormularioAsistente(false);

		return pagAct;
	}

	@Override
	public PaginaFormulario cargarPaginaFormularioAnterior(final List<ValorCampo> valores) {
		// Actualizamos valores actuales
		final PaginaData paginaActual = datosSesion.getDatosFormulario().obtenerPaginaDataActual(false);
		paginaActual.actualizarValoresPagina(valores);
		// Pop página actual
		// - Pasamos a posteriores, cargando página anterior como actual
		datosSesion.getDatosFormulario().popPaginaFormulario();
		// Carga pagina actual
		return this.cargarPaginaFormularioActual();
	}

	@Override
	public ResultadoEvaluarCambioCampo evaluarCambioCampoPaginaFormulario(final String idCampo,
			final List<ValorCampo> valoresPagina) {
		// Calculamos como se quedan los datos tras el cambio del campo
		return calculoDatosFormularioHelper.calcularDatosPaginaCambioCampo(datosSesion, idCampo, valoresPagina, false);
	}

	@Override
	public ResultadoGuardarPagina guardarPaginaFormulario(final List<ValorCampo> valoresPagina,
			final String accionPersonalizada) {
		return guardarPaginaFormularioImpl(valoresPagina, accionPersonalizada, false);
	}

	@Override
	public ResultadoGuardarPagina salirGuardandoPaginaFormulario(final List<ValorCampo> valoresPagina) {
		return guardarPaginaFormularioImpl(valoresPagina, null, true);
	}

	@Override
	public ResultadoGuardarPagina cancelarFormulario() {
		final DatosFinalizacionFormulario datosFinSesion = new DatosFinalizacionFormulario();
		datosFinSesion.setEstadoFinalizacion(TipoFinalizacionFormulario.CANCELADO);
		dao.finalizarSesionGestorFormularios(datosSesion.getDatosInicioSesion().getIdSesionFormulario(),
				datosFinSesion);

		// Retornamos indicando que esta finalizado y url redireccion
		final ResultadoGuardarPagina res = new ResultadoGuardarPagina();
		res.setFinalizado(TypeSiNo.SI);
		res.setUrl(this.configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL)
				+ ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_FORMULARIO_INTERNO + "?idPaso="
				+ datosSesion.getDatosInicioSesion().getIdPaso() + "&idFormulario="
				+ datosSesion.getDatosInicioSesion().getIdFormulario() + "&ticket="
				+ datosSesion.getDatosInicioSesion().getIdSesionFormulario());
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
	public ResultadoBuscadorDinamico buscadorDinamicoPaginaFormulario(final String idCampo, final String textoCampo,
			final List<ValorCampo> valoresPagina) {
		return calculoDatosFormularioHelper.calcularValoresPosiblesSelectorDinamico(datosSesion, idCampo, textoCampo,
				valoresPagina, false);
	}

	@Override
	public Captcha generarImagenCaptchaPaginaFormulario(final String idCampo) {
		return generarCaptchaCampoPaginaFormulario(idCampo, datosSesion.getDatosInicioSesion().getIdioma(),
				TypeCaptcha.IMAGEN);
	}

	@Override
	public Captcha generarSonidoCaptchaPaginaFormulario(final String idCampo) {
		return generarCaptchaCampoPaginaFormulario(idCampo, datosSesion.getDatosInicioSesion().getIdioma(),
				TypeCaptcha.SONIDO);
	}

	@Override
	public void regenerarCaptchaPaginaFormulario(final String idCampo) {
		// Generamos nuevo texto de captcha
		final String textCaptcha = UtilsCaptcha.generarKeyCaptcha();
		// Obtenemos datos página actual
		final PaginaData pagData = datosSesion.getDatosFormulario().obtenerPaginaDataActual(false);
		// Establecemos nuevo valor
		if (pagData.getConfiguracionCampo(idCampo).getTipo() != TypeCampo.CAPTCHA) {
			throw new ErrorConfiguracionException("Camp " + idCampo + " no es captcha");
		}
		final ValorCampoSimple vc = new ValorCampoSimple(idCampo, textCaptcha);
		pagData.reinicializarValorCampo(vc);
	}

	@Override
	public PaginaFormulario anyadirElemento(final String idCampoListaElementos, final List<ValorCampo> valoresPagina) {
		// Inicializa página elemento
		final PaginaFormulario res = inicializaPaginaElemento(datosSesion, idCampoListaElementos, null, valoresPagina,
				false);
		return res;
	}

	@Override
	public PaginaFormulario modificarElemento(final String idCampoListaElementos, final int indiceElemento,
			final List<ValorCampo> valoresPagina) {
		// Inicializa pagina elemento
		return inicializaPaginaElemento(datosSesion, idCampoListaElementos, indiceElemento, valoresPagina, false);
	}

	@Override
	public PaginaFormulario consultarElemento(final String idCampoListaElementos, final int indiceElemento,
			final List<ValorCampo> valoresPagina) {
		// Inicializa pagina elemento
		return inicializaPaginaElemento(datosSesion, idCampoListaElementos, indiceElemento, valoresPagina, true);
	}

	@Override
	public ResultadoEvaluarCambioCampo evaluarCambioCampoElemento(final String idCampoListaElementos,
			final String idCampo, final List<ValorCampo> valoresPagina) {
		checkIdListaElementos(idCampoListaElementos);
		return calculoDatosFormularioHelper.calcularDatosPaginaCambioCampo(datosSesion, idCampo, valoresPagina, true);
	}

	@Override
	public ResultadoGuardarElemento guardarElemento(final String idCampoListaElementos,
			final List<ValorCampo> valoresElemento) {

		// Verificamos si se está editando elemento
		checkIdListaElementos(idCampoListaElementos);

		// Obtenemos pagina formulario y pagina elemento
		final PaginaData pagForm = datosSesion.getDatosFormulario().obtenerPaginaDataActual(false);
		// TODO LEL CAMBIAR A PAGELE
		final PaginaData pagEle = datosSesion.getDatosFormulario().obtenerPaginaDataActual(true);

		// Obtenemos indice elemento que se esta modificando (nulo si nuevo)
		final Integer indiceElemento = datosSesion.getDatosFormulario().obtenerEdicionElementoIndiceElemento();

		// Obtenemos configuración campo elementos
		final ConfiguracionCampoListaElementos cc = (ConfiguracionCampoListaElementos) pagForm
				.getConfiguracionCampo(idCampoListaElementos);

		// Verificamos si es solo lectura
		if (cc.getSoloLectura() == TypeSiNo.SI) {
			throw new ErrorConfiguracionException("No es permet modificar element en camp " + idCampoListaElementos);
		}

		// Almacenamos valores campos elemento
		pagEle.actualizarValoresPagina(valoresElemento);

		// Actualiza estado campos (solo lectura / visible)
		actualizarEstadoCamposPagina(datosSesion, true, false);

		// Realizamos validaciones pagina
		final MensajeValidacion mv = validarGuardarPagina(datosSesion, null, true);

		// Retornamos resultado
		final ResultadoGuardarElemento res = new ResultadoGuardarElemento();
		res.setValidacion(mv);

		if (!UtilsFlujo.isErrorValidacion(mv)) {
			// Establecemos indice elemento a modificar (null si nuevo) y datos elemento
			res.setIndice(indiceElemento);
			res.setValor(pagEle.getValores());
			// Reseteamos datos edicion documento
			datosSesion.getDatosFormulario().resetearEdicionElemento();
		}

		return res;
	}

	@Override
	public ResultadoBuscadorDinamico buscadorDinamicoElemento(final String idCampoListaElementos, final String idCampo,
			final String textoCampo, final List<ValorCampo> valoresPagina) {
		// Verificamos si se está editando elemento
		checkIdListaElementos(idCampoListaElementos);
		// Calcula valores posibles
		final ResultadoBuscadorDinamico resultadoBuscadorDinamico = calculoDatosFormularioHelper
				.calcularValoresPosiblesSelectorDinamico(datosSesion, idCampo, textoCampo, valoresPagina, true);
		return resultadoBuscadorDinamico;
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
	private DatosFormularioInterno inicializarConfiguracionFormulario(final String idFormulario,
			final RFormularioInterno defForm, final byte[] xmlDatosIniciales) {

		// Parseamos datos iniciales
		final XmlFormulario xmlForm = UtilsFormulario.xmlToValores(xmlDatosIniciales);
		final List<ValorCampo> valoresIniciales = xmlForm.getValores();

		// Inicializamos página inicial (las siguiente paginas se inicializaran conforme
		// se acceda)
		final RPaginaFormulario paginaDefInicial = UtilsFormularioInterno
				.obtenerDefinicionSiguientePaginaFormulario(defForm, null);
		final PaginaData paginaDataInicial = inicializarPagina(idFormulario, defForm, paginaDefInicial,
				valoresIniciales);

		// Datos formulario interno: configuracion y datos.
		final DatosFormularioInterno df = new DatosFormularioInterno(valoresIniciales, paginaDataInicial);
		return df;
	}

	/**
	 * Inicializa página del formulario (configuración y datos).
	 *
	 * @param idFormulario
	 *                         id formulario
	 * @param defForm
	 *                         definición formulario
	 * @param valoresCampo
	 *                         valores campo
	 * @param modoConsulta
	 * @return
	 */
	private PaginaData inicializarPagina(final String idFormulario, final RFormularioInterno defForm,
			final RPaginaFormulario defPagina, final List<ValorCampo> valoresCampo) {

		// Añadimos pagina
		final PaginaData paginaForm = new PaginaData(idFormulario, defPagina.getIdentificador());
		paginaForm.setMostrarTitulo(TypeSiNo.fromBoolean(defForm.isMostrarTitulo()));
		paginaForm.setTitulo(defForm.getTitulo());

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
	 * @param elemento
	 *                                Indica si existe elemento
	 * @return Mensaje validación en caso de que exista
	 */
	private MensajeValidacion validarGuardarPagina(final DatosSesionFormularioInterno datosSesion,
			final String accionPersonalizada, final boolean elemento) {

		// Comprobamos si debemos validar página
		final boolean validar = isValidar(datosSesion, accionPersonalizada, elemento);

		// Validacion pagina
		MensajeValidacion mensaje = null;
		if (validar) {
			// Formato campos (mismas validaciones que en js). Si no pasa formato se genera
			// excepción.
			validacionesFormularioHelper.validarConfiguracionCampos(datosSesion, elemento);
			// Validacion script campos
			mensaje = validacionesFormularioHelper.validarScriptValidacionCampos(datosSesion, elemento);
			// Script validacion de la pagina
			if (mensaje == null) {
				mensaje = validacionesFormularioHelper.validarScriptValidacionPagina(datosSesion, elemento);
			}
		}

		return mensaje;
	}

	/**
	 * Verifica si se debe guardar página.
	 *
	 * @param datosSesion
	 *                                Datos sesión
	 * @param accionPersonalizada
	 *                                Acción personalizada
	 * @param elemento
	 *                                Indica si es elemento
	 * @return
	 */
	private static boolean isValidar(final DatosSesionFormularioInterno datosSesion, final String accionPersonalizada,
			final boolean elemento) {
		boolean validar = true;
		if (StringUtils.isNotBlank(accionPersonalizada)) {

			if (elemento) {
				throw new ErrorConfiguracionException(
						"No se permiten acciones personalizadas en página detalle elemento");
			}

			final AccionFormularioPersonalizada accion = datosSesion.getDatosFormulario().obtenerPaginaDataActual(false)
					.buscarAccion(accionPersonalizada);
			if (accion == null) {
				throw new AccionPersonalizadaNoExisteException(datosSesion.getDatosInicioSesion().getIdFormulario(),
						accionPersonalizada);
			}
			if (accion.getValidar() == TypeSiNo.NO) {
				validar = false;
			}
		}
		return validar;
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
		final List<ValorCampo> valoresFormulario = pDatosSesion.getDatosFormulario()
				.obtenerValoresAccesiblesPaginaFormularioActual();
		// - Si se sale sin finalizar cogemos también las páginas posteriores
		if (salirSinFinalizar) {
			// Resto pagina posteriores rellenadas
			valoresFormulario
					.addAll(pDatosSesion.getDatosFormulario().obtenerValoresPosterioresPaginaFormularioActual());
			// Resto valores que aparecen en valores iniciales y no están
			for (final ValorCampo vc : pDatosSesion.getDatosFormulario().obtenerValoresIniciales()) {
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
			final List<String> pagsRellenadas = pDatosSesion.getDatosFormulario()
					.obtenerIdsPaginasFormularioRellenadas();
			final RPlantillaFormulario plantillaPdf = configuracionFormularioHelper
					.obtenerPlantillaPdfVisualizacion(pDatosSesion);
			pdf = generarPdfFormulario(pDatosSesion.getDefinicionTramite(), pDatosSesion.getDatosInicioSesion(),
					plantillaPdf, xml, pagsRellenadas);
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
		dao.finalizarSesionGestorFormularios(pDatosSesion.getDatosInicioSesion().getIdSesionFormulario(),
				datosFinSesion);
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
	 * @param pagsRellenadas
	 *                                        Páginas rellenadas
	 * @return PDF
	 */
	private byte[] generarPdfFormulario(final DefinicionTramiteSTG definicionTramiteSTG,
			final DatosInicioSesionFormulario datosInicioSesionFormulario, final RPlantillaFormulario plantillaPdf,
			final byte[] xml, final List<String> pagsRellenadas) {

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
		final RFormularioInterno defFormulario = datosSesion.obtenerDefinicionFormularioInterno();
		return formateador.formatear(xml, pagsRellenadas, plantilla,
				definicionTramiteSTG.getDefinicionVersion().getIdioma(), defFormulario,
				datosInicioSesionFormulario.getTituloProcedimiento(), datosInicioSesionFormulario.getTituloTramite(),
				datosInicioSesionFormulario.getCodigoSiaProcedimiento(),
				datosInicioSesionFormulario.getDir3ResponsableProcedimiento());

	}

	/**
	 * Ajusta selectores (no obligatorios, lista radios,...)
	 *
	 * @param pagAct
	 *                   Datos página
	 */
	private void ajustarSelectores(final PaginaFormulario pagAct) {
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
	protected ResultadoGuardarPagina guardarPaginaFormularioImpl(final List<ValorCampo> valoresPagina,
			final String accionPersonalizada, final boolean salirSinFinalizar) {

		MensajeValidacion mensaje = null;

		// Verificamos que no este finalizada la sesion de formulario
		if (datosSesion.isFinalizada()) {
			throw new FormularioFinalizadoException("No es pot guardar pàgina: sessió formulari finalitzada");
		}

		// Definición formulario y página actual.
		final RFormularioInterno defFormulario = datosSesion.obtenerDefinicionFormularioInterno();
		final RPaginaFormulario paginaDef = datosSesion.obtenerDefinicionPaginaActual(false);

		// Datos página actual
		final PaginaData paginaActual = datosSesion.getDatosFormulario().obtenerPaginaDataActual(false);

		// Validación captchas
		if (!salirSinFinalizar) {
			mensaje = validarCapthas(paginaActual, valoresPagina);
		}

		// Guardamos valores pagina
		if (mensaje == null) {
			// Almacenamos valores campos (excepto captchas)
			paginaActual.actualizarValoresPagina(valoresPagina);
			// Realizamos validaciones
			if (!salirSinFinalizar) {
				mensaje = validarGuardarPagina(datosSesion, accionPersonalizada, false);
			}
		}

		// Devolvemos resultado
		final ResultadoGuardarPagina res = new ResultadoGuardarPagina();
		res.setFinalizado(TypeSiNo.NO);
		res.setValidacion(mensaje);

		// Si ha pasado la validacion guardamos pagina
		if (!UtilsFlujo.isErrorValidacion(mensaje)) {
			// Si no es pagina final, pasamos a la siguiente
			if (!salirSinFinalizar && !paginaDef.isPaginaFinal()) {
				// Evaluar cual es la siguiente pagina
				final String idPaginaSiguiente = configuracionFormularioHelper
						.evaluarScriptNavegacionPaginaActual(datosSesion);
				// Recuperamos definición siguiente página
				final RPaginaFormulario paginaSiguienteDef = UtilsFormularioInterno
						.obtenerDefinicionPaginaFormulario(defFormulario, idPaginaSiguiente);
				// Si pagina siguiente ya se ha rellenado antes, la obtenemos
				PaginaData paginaSiguiente = datosSesion.getDatosFormulario()
						.obtenerPaginaFormularioDataPosterior(idPaginaSiguiente);
				if (paginaSiguiente == null) {
					// Si no se ha rellenado todavía la inicializamos
					paginaSiguiente = inicializarPagina(datosSesion.getDatosInicioSesion().getIdFormulario(),
							defFormulario, paginaSiguienteDef,
							datosSesion.getDatosFormulario().obtenerValoresIniciales());
				}
				// Establecemos pagina siguietne como pagina actual
				datosSesion.getDatosFormulario().pushPaginaFormulario(paginaSiguiente);
				// Recalculo autorellenables pagina siguiente (actualiza valores)
				calculoDatosFormularioHelper.recalcularDatosPagina(datosSesion, false);
				// Retornamos indicando que no esta finalizado para que recargue pagina
				res.setRecargar(TypeSiNo.SI);
			} else {
				// Si es la ultima pagina o saliamos a mitad, finalizamos formulario
				finalizarFormulario(datosSesion, accionPersonalizada, salirSinFinalizar);
				// Marcamos sesion formulario como finalizada
				datosSesion.finalizarSesion();
				// Retornamos indicando que esta finalizado y url redireccion
				res.setFinalizado(TypeSiNo.SI);
				res.setUrl(this.configuracionComponent
						.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL)
						+ ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_FORMULARIO_INTERNO + "?idPaso="
						+ datosSesion.getDatosInicioSesion().getIdPaso() + "&idFormulario="
						+ datosSesion.getDatosInicioSesion().getIdFormulario() + "&ticket="
						+ datosSesion.getDatosInicioSesion().getIdSesionFormulario());
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
	private MensajeValidacion validarCapthas(final PaginaData paginaActual, final List<ValorCampo> valoresPagina) {

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
	private RecursosFormulario calcularRecursosEstaticos(final RPaginaFormulario paginaDef, final PaginaData pagData) {
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
	private Captcha generarCaptchaCampoPaginaFormulario(final String idCampo, final String idioma,
			final TypeCaptcha tipo) {
		// Obtenemos datos página actual
		final PaginaData pagData = datosSesion.getDatosFormulario().obtenerPaginaDataActual(false);
		// Generamos captcha a partir valor
		if (pagData.getConfiguracionCampo(idCampo).getTipo() != TypeCampo.CAPTCHA) {
			throw new ErrorConfiguracionException("Camp " + idCampo + " no es captcha");
		}
		final ValorCampoSimple vc = (ValorCampoSimple) pagData.getValorCampo(idCampo);
		final Captcha captcha = UtilsCaptcha.generaCaptcha(vc.getValor(), idioma, tipo);
		return captcha;
	}

	/**
	 * Inicializar página elemento.
	 *
	 * @param datosSesion
	 *                                  Datos sesión
	 * @param idCampoListaElementos
	 *                                  id campo lista elementos
	 * @param indiceElemento
	 *                                  indice elemento (nulo si nuevo)
	 * @param valoresPagina
	 *                                  valores actuales página principal
	 * @param forzarSoloLectura
	 *                                  Indica si se abre en modo consulta
	 * @return página elemento
	 */
	private PaginaFormulario inicializaPaginaElemento(final DatosSesionFormularioInterno datosSesion,
			final String idCampoListaElementos, final Integer indiceElemento, final List<ValorCampo> valoresPagina,
			final boolean forzarSoloLectura) {

		// Reseteamos datos elemento
		datosSesion.getDatosFormulario().resetearEdicionElemento();

		// Actualizamos valores pagina formulario
		final PaginaData pagFor = datosSesion.getDatosFormulario().obtenerPaginaDataActual(false);
		if (valoresPagina != null) {
			pagFor.actualizarValoresPagina(valoresPagina);
		}

		// Inicializar datos pagina elemento y almacena en sesion
		final DatosEdicionElemento dee = inicializarDatosEdicionElemento(datosSesion, idCampoListaElementos,
				indiceElemento, valoresPagina, forzarSoloLectura);
		datosSesion.getDatosFormulario().inicializarEdicionElemento(dee);

		// Recalcula campos autorrellenable con dependencias del formulario principal
		if (!forzarSoloLectura) {
			calculoDatosFormularioHelper.revisarCamposAutorrellenablesElemento(datosSesion);
		}

		// Calculamos estado dinámico de los campos y actualizamos configuración campos
		actualizarEstadoCamposPagina(datosSesion, true, forzarSoloLectura);

		// Genera pagina formulario
		final PaginaFormulario pagElemento = generarPaginaFormularioAsistente(true);

		return pagElemento;
	}

	/**
	 * Inicializa pagina edicion elemento.
	 *
	 * @param datosSesion
	 *                                   datos sesion formulario
	 * @param idCampoListaElementos
	 *                                   id campo lista elementos
	 * @param indiceElemento
	 *                                   indiceElemento elemento (nulo si nuevo)
	 * @param valoresPaginaPrincipal
	 *                                   valores actuales página formulario
	 * @param modoConsulta
	 *                                   Indica si el elemento se abre en modo
	 *                                   consulta
	 *
	 * @return pagina edicion elemento
	 */
	private DatosEdicionElemento inicializarDatosEdicionElemento(final DatosSesionFormularioInterno datosSesion,
			final String idCampoListaElementos, final Integer indiceElemento,
			final List<ValorCampo> valoresPaginaPrincipal, final boolean modoConsulta) {

		// Recupera pagina formulario actual
		final RPaginaFormulario paginaActualFormulario = datosSesion.obtenerDefinicionPaginaActual(false);

		// Recupera definicion formulario y pagina elemento
		final RFormularioInterno formDef = datosSesion.obtenerDefinicionFormularioInterno();
		final RPaginaFormulario paginaDef = UtilsFormularioInterno.obtenerDefinicionPaginaElemento(formDef,
				paginaActualFormulario.getIdentificador(), idCampoListaElementos);

		// Recupera valor actual elemento
		final List<ValorCampo> ve = UtilsFormularioInterno.buscarValorElemento(idCampoListaElementos, indiceElemento,
				valoresPaginaPrincipal);

		// Inicializa pagina
		final PaginaData pe = inicializarPagina(datosSesion.getDatosInicioSesion().getIdFormulario(), formDef,
				paginaDef, ve);

		// Retorna datos edicion elemento (pagina elemento y dependencias)
		final DatosEdicionElemento dee = new DatosEdicionElemento();
		dee.setIdCampoListaElementos(idCampoListaElementos);
		dee.setIndiceElemento(indiceElemento);
		dee.setPaginaElemento(pe);
		return dee;
	}

	/**
	 * Actualiza estado campos pagina.
	 *
	 * @param datosSesion
	 *                        Datos sesion
	 * @param elemento
	 *                        Si es pagina elemento
	 */
	private void actualizarEstadoCamposPagina(final DatosSesionFormularioInterno datosSesion, final boolean elemento,
			final boolean forzarSoloLectura) {
		// Obtenemos pagina actual
		final PaginaData pagAct = datosSesion.getDatosFormulario().obtenerPaginaDataActual(elemento);

		if (forzarSoloLectura) {
			// Si se fuerza solo lectura, marcamos como solo lectura
			for (final ConfiguracionCampo confCampo : pagAct.getConfiguracion()) {
				confCampo.setSoloLectura(TypeSiNo.SI);
			}
		} else {
			// Calculamos estado dinamico de los campos y actualizamos configuracion
			final List<ConfiguracionModificadaCampo> confDinamica = configuracionFormularioHelper
					.evaluarEstadoCamposPagina(datosSesion, elemento);
			for (final ConfiguracionModificadaCampo confDinamicaCampo : confDinamica) {
				final ConfiguracionCampo confCampo = pagAct.getConfiguracionCampo(confDinamicaCampo.getId());
				confCampo.setSoloLectura(confDinamicaCampo.getSoloLectura());
			}
		}
	}

	/**
	 * Genera pagina formulario para mostrar en asistente.
	 *
	 * @param elemento
	 *                     Indica si es un elemento
	 * @return Pagina formulario para mostrar en asistente
	 */
	private PaginaFormulario generarPaginaFormularioAsistente(final boolean elemento) {

		// Obtenemos definicion formulario pagina actual
		final RFormularioInterno formDef = datosSesion.obtenerDefinicionFormularioInterno();
		final RPaginaFormulario paginaDef = datosSesion.obtenerDefinicionPaginaActual(elemento);

		// Obtenemos datos página actual
		final PaginaData pagData = datosSesion.getDatosFormulario().obtenerPaginaDataActual(elemento);

		// Calculamos valores posibles campos selectores
		final List<ValoresPosiblesCampo> vpp = valoresPosiblesFormularioHelper
				.calcularValoresPosiblesPaginaActual(datosSesion, elemento);

		// Calculamos acciones a mostrar
		List<AccionFormulario> acciones = null;
		if (!elemento) {
			acciones = configuracionFormularioHelper.evaluarAccionesPaginaActual(datosSesion);
		}

		// Html pagina
		String html = null;
		try {
			html = new String(Base64.decodeBase64(paginaDef.getHtmlB64()), "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new EncodeException(e);
		}

		// Guardar sin finalizar
		TypeSiNo guardarSinFinalizar = TypeSiNo.NO;
		if (!elemento) {
			guardarSinFinalizar = TypeSiNo.fromBoolean(formDef.isPermitirGuardarSinFinalizar());
		}

		// Recursos estáticos
		final RecursosFormulario recursos = calcularRecursosEstaticos(paginaDef, pagData);

		// Devolvemos información página actual
		final PaginaFormulario pagAct = UtilsFormularioInterno.convertToPaginaFormulario(pagData);
		pagAct.setPermitirGuardar(guardarSinFinalizar);
		pagAct.setValoresPosibles(vpp);
		pagAct.setHtml(html);
		pagAct.setAcciones(acciones);
		pagAct.setRecursos(recursos);

		// Ajuste valores selectores (no obligatorios, radios,...)
		ajustarSelectores(pagAct);
		return pagAct;
	}

	/**
	 * Verifica si concuerda lista elementos.
	 *
	 * @param idCampoListaElementos
	 *                                  id lista elementos
	 */
	private void checkIdListaElementos(final String idCampoListaElementos) {
		if (!datosSesion.getDatosFormulario().obtenerEdicionElementoIdCampoListaElementos()
				.equals(idCampoListaElementos)) {
			throw new ErrorConfiguracionException("No concuerda id lista elementos");
		}
	}

}
