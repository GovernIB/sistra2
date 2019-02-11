package es.caib.sistramit.core.service.component.formulario.interno;

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
import es.caib.sistramit.core.api.model.comun.types.TypeAviso;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.AccionFormulario;
import es.caib.sistramit.core.api.model.formulario.AccionFormularioPersonalizada;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionModificadaCampo;
import es.caib.sistramit.core.api.model.formulario.MensajeAviso;
import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.PaginaFormularioData;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarPagina;
import es.caib.sistramit.core.api.model.formulario.SesionFormularioInfo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValoresPosiblesCampo;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.formulario.interno.formateadores.FormateadorPdfFormulario;
import es.caib.sistramit.core.service.component.formulario.interno.utils.CalculoDatosFormularioHelper;
import es.caib.sistramit.core.service.component.formulario.interno.utils.ConfiguracionFormularioHelper;
import es.caib.sistramit.core.service.component.formulario.interno.utils.UtilsFormularioInterno;
import es.caib.sistramit.core.service.component.formulario.interno.utils.ValidacionesFormularioHelper;
import es.caib.sistramit.core.service.component.formulario.interno.utils.ValoresPosiblesFormularioHelper;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.model.formulario.interno.DatosFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.DependenciaCampo;
import es.caib.sistramit.core.service.model.formulario.interno.InicializacionPagina;
import es.caib.sistramit.core.service.model.formulario.interno.ResultadoValidacion;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FormularioDao;
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

	@Override
	public String cargarSesion(String ticket) {

		// Obtenemos datos inicio de sesión
		final DatosInicioSesionFormulario dis = dao.obtenerDatosInicioSesionGestorFormularios(ticket);

		// Obtenemos definición del tramite
		final DefinicionTramiteSTG defTramite = configuracionComponent.recuperarDefinicionTramite(dis.getIdTramite(),
				dis.getVersionTramite(), dis.getIdioma());

		// Verificamos si ha variado la release
		if (defTramite.getDefinicionVersion().getRelease() != dis.getReleaseTramite()) {
			throw new ErrorConfiguracionException("Ha variado la release del trámite: " + dis.getReleaseTramite()
					+ " vs " + defTramite.getDefinicionVersion().getRelease());
		}

		// Generamos datos sesion en memoria
		datosSesion = new DatosSesionFormularioInterno();
		datosSesion.setIdFormulario(dis.getIdFormulario());
		datosSesion.setTicket(ticket);
		datosSesion.setDefinicionTramite(defTramite);
		datosSesion.setDatosInicioSesion(dis);
		datosSesion.setDebugEnabled(UtilsSTG.isDebugEnabled(defTramite));

		// Inicializamos datos formulario y los almacenamos en sesion
		final RFormularioTramite defFormulario = UtilsSTG.devuelveDefinicionFormulario(dis.getIdPaso(),
				dis.getIdFormulario(), defTramite);
		final DatosFormularioInterno datForm = inicializarConfiguracionFormulario(defFormulario,
				dis.getXmlDatosActuales());
		datosSesion.setDatosFormulario(datForm);

		// Devolvemos id sesion (usamos ticket)
		// TODO id sesion formulario?? separamos en campo nuevo??
		return ticket;

	}

	@Override
	public PaginaFormulario cargarPaginaActual() {

		// Obtenemos definicion pagina actual
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
		final PaginaFormulario pagAct = new PaginaFormulario(pagData);
		pagAct.setValoresPosibles(vpp);
		pagAct.setHtml(html);
		pagAct.setAcciones(acciones);
		return pagAct;
	}

	@Override
	public PaginaFormulario cargarPaginaAnterior() {
		// TODO PENDIENTE IMPLEMENTAR
		throw new RuntimeException("Pendiente implementar");
	}

	@Override
	public ResultadoEvaluarCambioCampo evaluarCambioCampoPagina(String idCampo, List<ValorCampo> valoresPagina) {
		// Calculamos como se quedan los datos tras el cambio del campo
		return calculoDatosFormularioHelper.calcularDatosPaginaCambioCampo(datosSesion, idCampo, valoresPagina);
	}

	@Override
	public ResultadoGuardarPagina guardarPagina(List<ValorCampo> valoresPagina, String accionPersonalizada) {

		// Verificamos que no este finalizada la sesion de formulario
		if (datosSesion.isFinalizada()) {
			throw new FormularioFinalizadoException("No se puede guardar pagina: sesion formulario finalizada");
		}

		// Almacenamos valores campos
		datosSesion.getDatosFormulario().getPaginaActualFormulario().actualizarValoresPagina(valoresPagina);

		// Recalcula campos ocultos
		calculoDatosFormularioHelper.calcularCamposOcultosPagina(datosSesion);

		// Realizamos validaciones
		final ResultadoValidacion rv = validarGuardarPagina(datosSesion, accionPersonalizada);

		final ResultadoGuardarPagina res = new ResultadoGuardarPagina();
		if (rv.isError()) {
			res.setError(TypeSiNo.SI);
			res.setMensaje(new MensajeAviso(TypeAviso.ERROR, rv.getMensajeError()));
		} else {
			// Mensaje aviso (solo podemos avisar de un mensaje de aviso, los juntamos
			// todos)
			if (rv.getAvisos() != null && !rv.getAvisos().isEmpty()) {
				final StringBuilder sbMensajeAviso = new StringBuilder(rv.getAvisos().size() * ConstantesNumero.N200);
				TypeAviso tipoAviso = TypeAviso.INFO;
				for (final MensajeAviso ma : rv.getAvisos()) {
					if ((tipoAviso == TypeAviso.INFO
							&& (ma.getTipo() == TypeAviso.WARNING || ma.getTipo() == TypeAviso.ERROR))
							|| (tipoAviso == TypeAviso.WARNING && ma.getTipo() == TypeAviso.ERROR)) {
						tipoAviso = ma.getTipo();
					}
					if (sbMensajeAviso.length() > 0) {
						sbMensajeAviso.append("\n");
					}
					sbMensajeAviso.append(ma.getTexto());
				}
				final String msgAviso = sbMensajeAviso.toString();
				if (StringUtils.isNotBlank(msgAviso)) {
					res.setMensaje(new MensajeAviso(tipoAviso, msgAviso));
				}
			}

			// Finalizacion formulario
			final RPaginaFormulario paginaDef = UtilsFormularioInterno.obtenerDefinicionPaginaActual(datosSesion);
			if (paginaDef.isPaginaFinal()) {
				// Si es la ultima pagina finalizamos formulario.
				finalizarFormulario(datosSesion, accionPersonalizada);
				// Marcamos sesion formulario como finalizada
				datosSesion.setFinalizada(true);
				// Devolvemos respuesta indicando que se ha finalizado
				res.setFinalizado(TypeSiNo.SI);
				// Establecemos url retorno
				res.setUrl(this.configuracionComponent
						.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL)
						+ ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_FORMULARIO_INTERNO + "?idPaso="
						+ datosSesion.getDatosInicioSesion().getIdPaso() + "&idFormulario="
						+ datosSesion.getDatosInicioSesion().getIdFormulario() + "&ticket=" + datosSesion.getTicket());
			} else {
				// Si no es la ultima pagina pasamos a la siguiente
				// TODO FASE 2: EVALUAR PARA SIGUIENTE VERSION MULTIPAGINA
				throw new ErrorConfiguracionException("No esta desarrollado formulario multipagina");
			}
		}

		return res;
	}

	@Override
	public void cancelarFormulario() {
		final DatosFinalizacionFormulario datosFinSesion = new DatosFinalizacionFormulario();
		datosFinSesion.setCancelado(true);
		dao.finalizarSesionGestorFormularios(datosSesion.getTicket(), datosFinSesion);
	}

	@Override
	public List<ValorCampo> deserializarValoresCampos(Map<String, String> valores) {
		final List<ValorCampo> lista = new ArrayList<>();
		for (final String idCampo : valores.keySet()) {
			final String valorSerializado = valores.get(idCampo);
			lista.add(UtilsFormularioInterno.deserializarValorCampo(idCampo, valorSerializado));
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

	// ---------------------------------------------------------------------------------------
	// Métodos utilidad
	// ---------------------------------------------------------------------------------------

	/**
	 * Inicializa configuración formulario.
	 *
	 * @param defForm
	 *            Definición formulario
	 * @param xmlDatosIniciales
	 *            xml datos iniciales
	 * @return Genera datos formulario interno
	 */
	private DatosFormularioInterno inicializarConfiguracionFormulario(RFormularioTramite defForm,
			byte[] xmlDatosIniciales) {

		// Parseamos datos iniciales
		final XmlFormulario xmlForm = UtilsFormulario.xmlToValores(xmlDatosIniciales);
		final List<ValorCampo> valoresCampo = xmlForm.getValores();

		// TODO Revisar inicializacion paginas:
		// - Hay que ver como se inicializan las páginas.
		// - Ver gestión dependencias entre campos de distintas páginas y campos
		// evaluables entre campos de distintas páginas
		// - Mejor inicializarlas todas y luego controlar cual se trata

		// TODO De momento solo se permite 1 sola pagina
		if (defForm.getFormularioInterno().getPaginas().size() > ConstantesNumero.N1) {
			throw new ErrorConfiguracionException("No esta implementada ejecución multipágina");
		}

		// Si formulario es personalizado, solo se permite 1 pagina
		// TODO Pendiente evaluar

		// Datos formulario interno: configuracion y datos.
		final DatosFormularioInterno df = new DatosFormularioInterno();
		df.setIndicePaginaActual(ConstantesNumero.N1);

		// Inicializamos páginas
		int index = 0;
		for (final RPaginaFormulario defPagina : defForm.getFormularioInterno().getPaginas()) {
			// Inicializamos pagina
			final InicializacionPagina ip = inicializarPagina(defForm.getIdentificador(), index, defForm, valoresCampo);
			// Añadimos pagina a datos formulario
			df.addPaginaFormulario(ip.getPagina());
			df.addDependenciasCampos(ip.getDependencias());
			index++;
		}

		// Marcar campos evaluables (aparecen como dependencias en otros campos)
		revisarCamposEvaluables(df);

		return df;
	}

	/**
	 * Marcamos en la configuracion los campos de la lista como evaluables si
	 * aparecen como dependencia de otros.
	 *
	 * @param df
	 *            Datos internos formulario
	 */
	private void revisarCamposEvaluables(final DatosFormularioInterno df) {
		for (final String idCampoEvaluable : df.getCamposEvaluables()) {
			final ConfiguracionCampo configuracionCampoEvaluable = df.getConfiguracionCampo(idCampoEvaluable);
			if (UtilsFormularioInterno.esCampoOculto(configuracionCampoEvaluable)) {
				throw new ErrorConfiguracionException("Un campo oculto no puede aparecer como dependencia de otros");
			}
			configuracionCampoEvaluable.setEvaluar(TypeSiNo.SI);
		}
	}

	/**
	 * Inicializa página del formulario (configuración y datos).
	 *
	 * @param idFormulario
	 *            id formulario
	 * @param indiceDef
	 *            indice definición
	 * @param defForm
	 *            definición formulario
	 * @param valoresCampo
	 *            valores campo
	 * @return
	 */
	private InicializacionPagina inicializarPagina(final String idFormulario, final int indiceDef,
			final RFormularioTramite defForm, final List<ValorCampo> valoresCampo) {

		// Definicion pagina
		final RPaginaFormulario defPagina = defForm.getFormularioInterno().getPaginas().get(indiceDef);

		// Añadimos pagina
		final PaginaFormularioData paginaForm = new PaginaFormularioData(idFormulario, indiceDef);
		paginaForm.setMostrarTitulo(TypeSiNo.fromBoolean(defForm.getFormularioInterno().isMostrarTitulo()));
		paginaForm.setTitulo(defForm.getFormularioInterno().getTitulo());

		// Establecemos campos página
		final List<DependenciaCampo> dependenciasPagina = new ArrayList<>();
		for (final RComponente campoDef : UtilsFormularioInterno.devuelveListaCampos(defPagina)) {

			// Calculamos valor inicial campo (si no tiene valor inicial, sera valor vacio)
			ValorCampo valorInicialCampo = UtilsFormularioInterno.buscarValorCampo(valoresCampo,
					campoDef.getIdentificador());
			if (valorInicialCampo == null) {
				valorInicialCampo = UtilsFormularioInterno.crearValorVacio(campoDef);
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

		// Devolvemos pagina inicializada y las dependencias de los campos
		final InicializacionPagina ip = new InicializacionPagina();
		ip.setPagina(paginaForm);
		ip.setDependencias(dependenciasPagina);
		return ip;
	}

	/**
	 * Realiza validaciones al guardar la pagina.
	 *
	 * @param datosSesion
	 *            Datos sesion formulario
	 * @param accionPersonalizada
	 *            Accion personalizada
	 * @return Resultado validacion
	 */
	private ResultadoValidacion validarGuardarPagina(final DatosSesionFormularioInterno datosSesion,
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

		ResultadoValidacion rv = new ResultadoValidacion();
		if (validar) {
			// Formato campos (mismas validaciones que en js)
			rv = validacionesFormularioHelper.validarConfiguracionCampos(datosSesion);
			// Script validacion campos de formulario
			// No hacemos caso de los mensajes de aviso de los campos, solo si hay error.
			// Los avisos se muestran al rellenar campo.
			if (!rv.isError()) {
				rv = validacionesFormularioHelper.validarScriptValidacionCampos(datosSesion);
			}
			// Script validacion de la pagina
			if (!rv.isError()) {
				rv = validacionesFormularioHelper.validarScriptValidacionPagina(datosSesion);
			}
		}

		return rv;
	}

	/**
	 * Finaliza formulario.
	 *
	 * @param pDatosSesion
	 *            Datos sesion
	 * @param accionPersonalizada
	 *            Accion personalizada
	 */
	private void finalizarFormulario(final DatosSesionFormularioInterno pDatosSesion,
			final String accionPersonalizada) {
		// Obtenemos valores página
		// TODO Cuando haya captchas, hay que eliminarlos
		final List<ValorCampo> valoresFormulario = pDatosSesion.getDatosFormulario().getValoresAccesiblesPaginaActual();
		// Generamos xml
		final XmlFormulario formXml = new XmlFormulario(valoresFormulario, accionPersonalizada);
		final byte[] xml = UtilsFormulario.valoresToXml(formXml);
		// Generamos pdf
		final RPlantillaFormulario plantillaPdf = configuracionFormularioHelper
				.obtenerPlantillaPdfVisualizacion(pDatosSesion);
		final byte[] pdf = generarPdfFormulario(pDatosSesion.getDefinicionTramite(),
				pDatosSesion.getDatosInicioSesion(), plantillaPdf, xml);
		// Retornamos al flujo de tramitacion
		final DatosFinalizacionFormulario datosFinSesion = new DatosFinalizacionFormulario();
		datosFinSesion.setXml(xml);
		datosFinSesion.setPdf(pdf);
		dao.finalizarSesionGestorFormularios(pDatosSesion.getTicket(), datosFinSesion);
	}

	/**
	 * Genera PDF formulario.
	 *
	 * @param definicionTramiteSTG
	 *            Definición trámite
	 * @param datosInicioSesionFormulario
	 *            Datos inicio sesión
	 *
	 * @param plantillaPdf
	 *            Plantilla PDF
	 * @param xml
	 *            XML Formulario
	 * @return PDF
	 */
	public byte[] generarPdfFormulario(DefinicionTramiteSTG definicionTramiteSTG,
			DatosInicioSesionFormulario datosInicioSesionFormulario, RPlantillaFormulario plantillaPdf, byte[] xml) {
		try {
			// Obtenemos plantilla
			byte[] plantilla = null;
			if (StringUtils.isNotBlank(plantillaPdf.getFicheroPlantilla())) {
				final String pathFicherosExternos = configuracionComponent
						.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS);
				final String pathFile = pathFicherosExternos + plantillaPdf.getFicheroPlantilla();
				plantilla = IOUtils.resourceToByteArray(pathFile);
			}
			// Obtenemos formateador
			final FormateadorPdfFormulario formateador = (FormateadorPdfFormulario) Class
					.forName(plantillaPdf.getClaseFormateador()).newInstance();
			// Formateamos a PDF
			final RFormularioTramite defFormulario = UtilsSTG.devuelveDefinicionFormulario(
					datosInicioSesionFormulario.getIdPaso(), datosInicioSesionFormulario.getIdFormulario(),
					definicionTramiteSTG);
			return formateador.formatear(xml, plantilla, definicionTramiteSTG.getDefinicionVersion().getIdioma(),
					defFormulario.getFormularioInterno(), datosInicioSesionFormulario.getTituloProcedimiento());
		} catch (final IOException e) {
			throw new ErrorPdfFormularioException("Error generando pdf formulario", e);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new ErrorPdfFormularioException(
					"Error generando pdf formulario a partir classname " + plantillaPdf.getClaseFormateador(), e);
		}

	}

}
