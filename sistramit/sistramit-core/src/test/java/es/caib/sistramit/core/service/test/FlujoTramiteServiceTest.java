package es.caib.sistramit.core.service.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.AbrirFormulario;
import es.caib.sistramit.core.api.model.flujo.AnexoFichero;
import es.caib.sistramit.core.api.model.flujo.DetallePasoAnexar;
import es.caib.sistramit.core.api.model.flujo.DetallePasoGuardar;
import es.caib.sistramit.core.api.model.flujo.DetallePasoPagar;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRellenar;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.DocumentoRegistro;
import es.caib.sistramit.core.api.model.flujo.FirmaVerificacion;
import es.caib.sistramit.core.api.model.flujo.PagoVerificacion;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;
import es.caib.sistramit.core.api.model.flujo.RetornoFormularioExterno;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoAnexar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoGuardar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoPagar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoRellenar;
import es.caib.sistramit.core.api.model.flujo.types.TypeFormulario;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.flujo.types.TypeResultadoRegistro;
import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoBuscadorDinamico;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarPagina;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.ValoresPosiblesCampo;
import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.SesionInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.service.FlujoFormularioInternoService;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;
import es.caib.sistramit.core.api.service.SecurityService;
import es.caib.sistramit.core.service.component.formulario.interno.utils.UtilsFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.test.mock.SistragesMock;
import es.caib.sistramit.core.service.util.UtilsFormulario;

// TODO Meter en test la funcionalidad que se pueda: convertir pdf,
// anexar firmado, script validacion, anexos dinamicos, opcionales,...

/**
 * Testing capa de negocio de tramitación.
 *
 * @author Indra
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FlujoTramiteServiceTest extends BaseDbUnit {

	/** Flujo tramitación. */
	@Autowired
	private FlujoTramitacionService flujoTramitacionService;

	/** Flujo formulario. */
	@Autowired
	private FlujoFormularioInternoService flujoFormularioInternoService;

	/** Servicio seguridad. */
	@Autowired
	private SecurityService securityService;

	/** Url inicio. */
	private static final String URL_INICIO = "localhost:8080/sistramitfront/asistente/iniciarTramite.html?tramite="
			+ SistragesMock.ID_TRAMITE + "&version=" + SistragesMock.VERSION_TRAMITE + "&idioma=" + SistragesMock.IDIOMA
			+ "&idTramiteCatalogo=" + SistragesMock.ID_TRAMITE_CP;

	/** Publicación JNDI Datasource y creación de BBDD en memoria. */
	@BeforeClass
	public static void setUpClass() throws Exception {
		JndiBean.doSetup();
	}

	/**
	 * Verificación login autenticado: Se verifica proceso de login autenticado.
	 */
	@Test
	public void test1_loginAutenticado() {
		final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(TypeAutenticacion.AUTENTICADO);
		Assert.isTrue(
				usuarioAutenticadoInfo != null
						&& usuarioAutenticadoInfo.getAutenticacion() == TypeAutenticacion.AUTENTICADO,
				"No se ha autenticado correctamente");
		this.logger.info("Autenticado: " + usuarioAutenticadoInfo.getNif() + " - " + usuarioAutenticadoInfo.getNombre()
				+ " " + usuarioAutenticadoInfo.getApellido1() + " " + usuarioAutenticadoInfo.getApellido2());
	}

	/** Verificación login anonimo. */
	@Test
	public void test2_loginAnonimo() {
		final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(TypeAutenticacion.ANONIMO);
		Assert.isTrue(
				usuarioAutenticadoInfo != null
						&& usuarioAutenticadoInfo.getAutenticacion() == TypeAutenticacion.ANONIMO,
				"No se ha autenticado correctamente");
		this.logger.info("Autenticado anonimo");
	}

	/**
	 * Carga trámite autenticado: verificación de recarga de un trámite autenticado
	 * desde la sesión activa (p.e. tras un error) o carga de un trámite autenticado
	 * (p.e. desde carpeta ciudadana).
	 */
	@Test
	public void test3_cargaTramiteAutenticado() {

		DetalleTramite dt = null;

		// Simula login autenticado
		final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(TypeAutenticacion.AUTENTICADO);

		// Iniciar trámite
		final Map<String, String> parametrosInicio = new HashMap<>();

		final String idSesionTramitacion = flujoTramitacionService.iniciarTramite(usuarioAutenticadoInfo,
				SistragesMock.ID_TRAMITE, SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA,
				SistragesMock.ID_TRAMITE_CP, false, URL_INICIO, parametrosInicio);
		Assert.isTrue(idSesionTramitacion != null, "No se devuelve id sesion tramitacion");

		// Recargar trámite (dentro sesion)
		flujoTramitacionService.recargarTramite(idSesionTramitacion, usuarioAutenticadoInfo);
		dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
		Assert.isTrue(idSesionTramitacion.equals(dt.getTramite().getIdSesion()),
				"No se devuelve id sesion tramitacion");

		// Cargar trámite (fuera sesion)
		flujoTramitacionService.cargarTramite(idSesionTramitacion, usuarioAutenticadoInfo);
		dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
		Assert.isTrue(idSesionTramitacion.equals(dt.getTramite().getIdSesion()),
				"No se devuelve id sesion tramitacion");

	}

	/**
	 * Carga trámite anónimo: verificación de recarga de un trámite anónimo desde la
	 * sesión activa (p.e. tras un error) o carga de un trámite a partir de la clave
	 * de tramitación .
	 */
	@Test
	public void test4_cargaTramiteAnonimo() {

		DetalleTramite dt = null;

		// Simula login anonimo
		final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(TypeAutenticacion.ANONIMO);

		// Iniciar trámite
		final Map<String, String> parametrosInicio = new HashMap<>();
		final String idSesionTramitacion = flujoTramitacionService.iniciarTramite(usuarioAutenticadoInfo,
				SistragesMock.ID_TRAMITE, SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA,
				SistragesMock.ID_TRAMITE_CP, false, URL_INICIO, parametrosInicio);
		Assert.isTrue(idSesionTramitacion != null, "No se devuelve id sesion tramitacion");

		// Recargar trámite (dentro sesion)
		flujoTramitacionService.recargarTramite(idSesionTramitacion, usuarioAutenticadoInfo);
		dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
		Assert.isTrue(idSesionTramitacion.equals(dt.getTramite().getIdSesion()),
				"No se devuelve id sesion tramitacion");

		// Cargar trámite mediante clave tramitacion (fuera sesion)
		final InfoLoginTramite infoLogin = securityService
				.obtenerInfoLoginTramiteAnonimoPersistente(idSesionTramitacion);
		Assert.isTrue(infoLogin.isLoginAnonimoAuto(), "No se ha establecido login anonimo automatico");

		flujoTramitacionService.cargarTramite(idSesionTramitacion, usuarioAutenticadoInfo);
		dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
		Assert.isTrue(idSesionTramitacion.equals(dt.getTramite().getIdSesion()),
				"No se devuelve id sesion tramitacion");

	}

	/**
	 * Verificación flujo tramitación: verifica la funcionalidad básica de los
	 * diferentes pasos de tramitación a partir de la definición simulada de una
	 * versión de trámite.
	 */
	@Test
	public void test6_flujoTramitacionElectronico() throws Exception {

		final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(TypeAutenticacion.AUTENTICADO);

		// Iniciar trámite
		final String idSesionTramitacion = flujoTramitacion_iniciarTramite(usuarioAutenticadoInfo);

		// Detalle paso actual: Debe saber
		flujoTramitacion_debeSaber(idSesionTramitacion);

		// Pasamos a paso siguiente: rellenar
		flujoTramitacion_rellenar(idSesionTramitacion);

		// Pasamos a paso siguiente: anexar
		flujoTramitacion_anexar_electronico(idSesionTramitacion);

		// Pasamos a paso siguiente: pagar
		flujoTramitacion_pagar_electronico(idSesionTramitacion, usuarioAutenticadoInfo);

		// Pasamos a paso siguiente: registrar
		flujoTramitacion_registro_electronico(idSesionTramitacion, usuarioAutenticadoInfo);

	}

	/**
	 * Test paso rellenar.
	 *
	 * @param idSesionTramitacion
	 *                                id sesión
	 * @param presentacion
	 *                                presentacion
	 * @throws UnsupportedEncodingException
	 */
	private void flujoTramitacion_rellenar(final String idSesionTramitacion) throws UnsupportedEncodingException {
		DetallePasos dp;

		// Pasa al paso Rellenar
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		final ResultadoIrAPaso rp = flujoTramitacionService.irAPaso(idSesionTramitacion, dp.getSiguiente());
		Assert.isTrue(StringUtils.equals(rp.getIdPasoActual(), dp.getSiguiente()),
				"No se ha podido pasar a siguiente paso (debe saber)");
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.RELLENAR, "Paso actual no es rellenar");
		this.logger.info("Detalle paso: " + dp.print());

		// Rellenar formulario interno
		flujoTramitacion_rellenar_formInterno(idSesionTramitacion);

		// Rellenar formulario externo
		flujoTramitacion_rellenar_formExterno(idSesionTramitacion);

		// Paso terminado
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.RELLENAR, "No esta en paso rellenar");
		Assert.isTrue(dp.getActual().getCompletado() == TypeSiNo.SI, "Paso rellenar no esta completado");
		this.logger.info("Detalle paso: " + dp.print());

	}

	protected void flujoTramitacion_rellenar_formInterno(final String idSesionTramitacion)
			throws UnsupportedEncodingException {
		ParametrosAccionPaso parametros;
		ResultadoAccionPaso resPaso;
		String nombreFichero;
		byte[] datosFichero;
		XmlFormulario xmlForm;
		PaginaFormulario paginaData;
		ResultadoGuardarPagina resGuardar;
		List<ValorCampo> valoresIniciales;
		List<ValorCampo> valoresActuales;
		List<ValoresPosiblesCampo> valoresPosibles;
		DetallePasos dp;

		// Verifica que esta en paso rellenar
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.RELLENAR, "Paso actual no es rellenar");
		this.logger.info("Detalle paso: " + dp.print());

		// -- Mostramos xml inicial
		final DetallePasoRellenar dpr = (DetallePasoRellenar) dp.getActual();
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idFormulario", dpr.getFormularios().get(0).getId());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, dp.getActual().getId(),
				TypeAccionPasoRellenar.DESCARGAR_XML, parametros);
		datosFichero = (byte[]) resPaso.getParametroRetorno("xml");
		xmlForm = UtilsFormulario.xmlToValores(datosFichero);
		valoresIniciales = xmlForm.getValores();
		this.logger.info("XML formulario inicial: " + new String(datosFichero, "UTF-8"));

		// -- Abrimos formulario
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, dp.getActual().getId(),
				TypeAccionPasoRellenar.ABRIR_FORMULARIO, parametros);
		final AbrirFormulario af = (AbrirFormulario) resPaso.getParametroRetorno("referencia");
		Assert.isTrue(af.getTipo() == TypeFormulario.INTERNO, "Tipo formulario no es interno");
		Assert.isTrue(af.getTicket() != null, "No se ha devuelto ticket formulario");

		// -- Redirigimos formulario
		final String idSesionFormulario = flujoFormularioInternoService.cargarSesion(af.getTicket());
		flujoFormularioInternoService.inicializarSesion(idSesionFormulario);

		// -- Cargamos pagina actual
		paginaData = flujoFormularioInternoService.cargarPaginaActual(idSesionFormulario);
		valoresActuales = paginaData.getValores();
		valoresPosibles = paginaData.getValoresPosibles();
		// Verificamos acciones carga
		// * Dato inicial precargado
		for (final ValorCampo valorPagina : valoresActuales) {
			final ValorCampo valorInicial = UtilsFormularioInterno.buscarValorCampo(valoresIniciales,
					valorPagina.getId());
			if (valorInicial != null) {
				Assert.isTrue(valorPagina != null && valorInicial.esValorIgual(valorPagina),
						"No concuerda valor inicial campo " + valorInicial.getId());
			}
		}
		// * Estado campo
		Assert.isTrue(paginaData.getConfiguracion("TXT_CALC").getSoloLectura() == TypeSiNo.SI,
				"El campo no está como solo lectura");

		// -- Buscador dinamico
		// * Valores posibles de selector dinámico debe estar vacio de inicio
		final ValoresPosiblesCampo vpcSelDin = UtilsFormularioInterno.buscarValoresPosibles(valoresPosibles,
				"SEL_DINAMICO");
		Assert.isTrue(vpcSelDin == null, "No se deben establecer valores posibles para selector dinámico");
		// * Busca valor por texto "lor 2" -> debe devolver "Valor2"
		final ResultadoBuscadorDinamico rbd = flujoFormularioInternoService.buscadorDinamico(idSesionFormulario,
				"SEL_DINAMICO", "lor 2", valoresActuales);
		Assert.isTrue(rbd.getValores().size() == 1 && rbd.getValores().get(0).getValor().equals("V2"),
				"La búsqueda para selector dinámico debe devolver el elemento V2");

		// -- Evaluar cambio campo
		final ValorCampoSimple vcs = (ValorCampoSimple) UtilsFormularioInterno.buscarValorCampo(valoresActuales,
				"CHK_ESTADO");
		vcs.setValor("N");
		final ResultadoEvaluarCambioCampo resCambioCampo = flujoFormularioInternoService
				.evaluarCambioCampoPagina(idSesionFormulario, "CHK_ESTADO", valoresActuales);
		// * Campo calculado (cambio valor para TXT_CALC = CHK_ESTADO)
		Assert.isTrue(
				UtilsFormularioInterno.buscarValorCampo(resCambioCampo.getValores(), "TXT_CALC").esValorIgual(vcs),
				"No se ha calculado valor TXT_CALC");
		Assert.isTrue(
				UtilsFormularioInterno.buscarValorCampo(resCambioCampo.getValores(), "OCULTO_1").esValorIgual(vcs),
				"No se ha calculado valor OCULTO_1");
		// * Estado (cambio estado segun CHK_ESTADO)
		Assert.isTrue(
				UtilsFormularioInterno.buscarConfiguracionModificadaCampo(resCambioCampo.getConfiguracion(), "TXT_CALC")
						.getSoloLectura() == TypeSiNo.fromBoolean("S".equals(vcs.getValor())),
				"No se ha calculado estado TXT_CALC");
		// * Valores posibles (recalculados para SEL_CALC)
		Assert.isTrue(resCambioCampo.getValoresPosibles().get(0).getId().equals("SEL_CALC"),
				"No se ha calculado estado TXT_CALC");
		// * Actualizamos valores calculados
		for (final ValorCampo vcModif : resCambioCampo.getValores()) {
			final ValorCampo vcActual = UtilsFormularioInterno.buscarValorCampo(valoresActuales, vcModif.getId());
			vcActual.reemplazaValor(vcModif);
		}

		// -- Guardar pagina 1
		// * Metemos valores campos textos
		((ValorCampoSimple) UtilsFormularioInterno.buscarValorCampo(valoresActuales, "TXT_NORMAL"))
				.setValor("Valor modificado");
		((ValorCampoSimple) UtilsFormularioInterno.buscarValorCampo(valoresActuales, "TXT_FECHA"))
				.setValor("28/02/2019");
		((ValorCampoSimple) UtilsFormularioInterno.buscarValorCampo(valoresActuales, "TXT_HORA")).setValor("23:59");
		// * Metemos valores selectores (primero de valores posibles)
		final ValorIndexado vci = UtilsFormularioInterno.buscarValoresPosibles(valoresPosibles, "SEL_LISTA")
				.getValores().get(0);
		((ValorCampoIndexado) UtilsFormularioInterno.buscarValorCampo(valoresActuales, "SEL_LISTA")).setValor(vci);
		final ValorIndexado vci2 = UtilsFormularioInterno.buscarValoresPosibles(valoresPosibles, "SEL_MULTIPLE")
				.getValores().get(0);
		((ValorCampoListaIndexados) UtilsFormularioInterno.buscarValorCampo(valoresActuales, "SEL_MULTIPLE"))
				.addValorIndexado(vci2.getValor(), vci2.getDescripcion());
		final ValorIndexado vci3 = UtilsFormularioInterno.buscarValoresPosibles(valoresPosibles, "SEL_UNICA")
				.getValores().get(0);
		((ValorCampoIndexado) UtilsFormularioInterno.buscarValorCampo(valoresActuales, "SEL_UNICA")).setValor(vci3);
		// * Metemos valor campo validación
		((ValorCampoSimple) UtilsFormularioInterno.buscarValorCampo(valoresActuales, "TXT_VAL")).setValor("PRUEBA");

		// * Guardar pagina
		final List<ValorCampo> valoresPagina1 = duplicarListaValores(valoresActuales);
		resGuardar = flujoFormularioInternoService.guardarPagina(idSesionFormulario, valoresActuales, null);
		Assert.isTrue(resGuardar.getValidacion() == null,
				"El formulario tiene mensaje validación: " + resGuardar.getValidacion());
		Assert.isTrue(resGuardar.getFinalizado() == TypeSiNo.NO,
				"Se ha finalizado formulario tras guardar primera página");

		// -- Cargar pagina 2
		paginaData = flujoFormularioInternoService.cargarPaginaActual(idSesionFormulario);
		valoresActuales = paginaData.getValores();
		// * Verificar pagina
		Assert.isTrue(paginaData.getIdPagina().equals("P2"), "No es la pagina 2");
		// * Valor inicializado de valores iniciales
		Assert.isTrue(
				UtilsFormularioInterno.buscarValorCampo(valoresActuales, "TXT_VALINIP2")
						.esValorIgual(UtilsFormularioInterno.buscarValorCampo(valoresIniciales, "TXT_VALINIP2")),
				"No se ha calculado valor TXT_CALC");
		// * Valor calculado a partir pagina 1 (valor = TXT_CALC y estado = SOLO
		// LECTURA)
		Assert.isTrue(paginaData.getConfiguracion("TXT_CALCP2").getSoloLectura() == TypeSiNo
				.fromBoolean("S".equals(vcs.getValor())), "No se ha calculado estado TXT_CALCP1");
		Assert.isTrue(UtilsFormularioInterno.buscarValorCampo(valoresActuales, "TXT_CALCP2").esValorIgual(vcs),
				"No se ha calculado valor TXT_CALCP2");
		// * Valor autorrellenado a partir TXT_CALCP2 tras carga (valor = TXT_CALCP2)
		Assert.isTrue(
				UtilsFormularioInterno.buscarValorCampo(valoresActuales, "TXT_AUTOP2")
						.esValorIgual(UtilsFormularioInterno.buscarValorCampo(valoresActuales, "TXT_CALCP2")),
				"No se ha calculado valor TXT_AUTOP2");
		// * Cambiamos valor TXT_AUTOP2
		((ValorCampoSimple) UtilsFormularioInterno.buscarValorCampo(valoresActuales, "TXT_AUTOP2")).setValor("nuevo");

		// - Volvemos a pagina 1
		final List<ValorCampo> valoresPagina2 = duplicarListaValores(valoresActuales);
		paginaData = flujoFormularioInternoService.cargarPaginaAnterior(idSesionFormulario, valoresActuales);
		valoresActuales = paginaData.getValores();
		// * Verificar pagina
		Assert.isTrue(paginaData.getIdPagina().equals("P1"), "No es la pagina 1");
		// * Verifica valores pagina 1
		esIgualListaValores(valoresActuales, valoresPagina1);

		// - Volvemos a pagina 2
		resGuardar = flujoFormularioInternoService.guardarPagina(idSesionFormulario, valoresActuales, null);
		Assert.isTrue(resGuardar.getValidacion() == null,
				"El formulario tiene mensaje validación: " + resGuardar.getValidacion());
		Assert.isTrue(resGuardar.getFinalizado() == TypeSiNo.NO,
				"Se ha finalizado formulario tras guardar primera página");
		paginaData = flujoFormularioInternoService.cargarPaginaActual(idSesionFormulario);
		valoresActuales = paginaData.getValores();
		// * Verificar pagina
		Assert.isTrue(paginaData.getIdPagina().equals("P2"), "No es la pagina 2");
		// * Verifica valores pagina 1
		esIgualListaValores(valoresActuales, valoresPagina2);

		// - Guardar pagina 2
		resGuardar = flujoFormularioInternoService.guardarPagina(idSesionFormulario, valoresActuales, null);
		Assert.isTrue(resGuardar.getValidacion() == null,
				"El formulario tiene mensaje validación: " + resGuardar.getValidacion());
		Assert.isTrue(resGuardar.getFinalizado() == TypeSiNo.SI, "No se ha finalizado formulario tras guardar página");

		// -- Guardar formulario
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idFormulario", dpr.getFormularios().get(0).getId());
		parametros.addParametroEntrada("ticket", idSesionFormulario);
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, dp.getActual().getId(),
				TypeAccionPasoRellenar.GUARDAR_FORMULARIO, parametros);

		// -- Mostramos xml tras guardar
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idFormulario", dpr.getFormularios().get(0).getId());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, dp.getActual().getId(),
				TypeAccionPasoRellenar.DESCARGAR_XML, parametros);
		datosFichero = (byte[]) resPaso.getParametroRetorno("xml");
		this.logger.info("XML formulario guardado: " + new String(datosFichero, "UTF-8"));

		// -- Descargar pdf
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idFormulario", dpr.getFormularios().get(0).getId());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, dp.getActual().getId(),
				TypeAccionPasoRellenar.DESCARGAR_FORMULARIO, parametros);
		nombreFichero = (String) resPaso.getParametroRetorno("nombreFichero");
		datosFichero = (byte[]) resPaso.getParametroRetorno("datosFichero");
		Assert.isTrue(nombreFichero.endsWith(".pdf"), "El fichero no es pdf");
		Assert.isTrue(datosFichero.length > 0, "El fichero no tiene contenido");
	}

	protected void esIgualListaValores(final List<ValorCampo> valoresActuales, final List<ValorCampo> valoresPagina1) {
		Assert.isTrue(valoresActuales.size() == valoresPagina1.size(), "No tienen mismo numero valores");
		for (final ValorCampo vp : valoresPagina1) {
			Assert.isTrue(vp.esValorIgual(UtilsFormularioInterno.buscarValorCampo(valoresActuales, vp.getId())),
					"No es valor igual " + vp.getId());
		}
	}

	protected List<ValorCampo> duplicarListaValores(final List<ValorCampo> valoresActuales) {
		final List<ValorCampo> valoresPagina2 = new ArrayList<>();
		for (final ValorCampo vcp : valoresActuales) {
			valoresPagina2.add(vcp.duplicar());
		}
		return valoresPagina2;
	}

	protected void flujoTramitacion_rellenar_formExterno(final String idSesionTramitacion)
			throws UnsupportedEncodingException {
		ParametrosAccionPaso parametros;
		ResultadoAccionPaso resPaso;
		String nombreFichero;
		byte[] datosFichero;
		XmlFormulario xmlForm;
		List<ValorCampo> valoresIniciales;
		final List<ValorCampo> valoresActuales;
		DetallePasos dp;

		// Verifica que esta en paso rellenar
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.RELLENAR, "Paso actual no es rellenar");
		this.logger.info("Detalle paso: " + dp.print());

		// -- Mostramos xml inicial
		final DetallePasoRellenar dpr = (DetallePasoRellenar) dp.getActual();
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idFormulario", dpr.getFormularios().get(1).getId());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, dp.getActual().getId(),
				TypeAccionPasoRellenar.DESCARGAR_XML, parametros);
		datosFichero = (byte[]) resPaso.getParametroRetorno("xml");
		xmlForm = UtilsFormulario.xmlToValores(datosFichero);
		valoresIniciales = xmlForm.getValores();
		this.logger.info("XML formulario inicial: " + new String(datosFichero, "UTF-8"));

		// -- Abrimos formulario
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, dp.getActual().getId(),
				TypeAccionPasoRellenar.ABRIR_FORMULARIO, parametros);
		final AbrirFormulario af = (AbrirFormulario) resPaso.getParametroRetorno("referencia");
		Assert.isTrue(af.getTipo() == TypeFormulario.EXTERNO, "Tipo formulario no es externo");
		Assert.isTrue(af.getUrl() != null, "No se ha devuelto url redireccion formulario");

		// -- Redirigimos formulario (se plugin mock: retorna url redireccion
		// directamente el callback)
		final String ticketGFE = af.getUrl().substring(af.getUrl().indexOf("?ticket=") + "?ticket=".length());
		final SesionInfo sesionInfo = new SesionInfo();
		final UsuarioAutenticadoInfo usuInfo = securityService.validarTicketFormularioExterno(sesionInfo, ticketGFE);
		final RetornoFormularioExterno infoTicket = securityService.obtenerTicketFormularioExterno(ticketGFE);
		flujoTramitacionService.cargarTramite(idSesionTramitacion, infoTicket.getUsuario());

		// Verifica que esta en paso rellenar
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.RELLENAR, "Paso actual no es rellenar");
		this.logger.info("Detalle paso: " + dp.print());

		// Ejecutamos accion guardar formulario
		ParametrosAccionPaso pParametros;
		pParametros = new ParametrosAccionPaso();
		pParametros.addParametroEntrada("idFormulario", infoTicket.getIdFormulario());
		pParametros.addParametroEntrada("ticket", infoTicket.getTicket());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, infoTicket.getIdPaso(),
				TypeAccionPasoRellenar.GUARDAR_FORMULARIO, pParametros);

		// -- Mostramos xml tras guardar
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idFormulario", dpr.getFormularios().get(1).getId());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, dp.getActual().getId(),
				TypeAccionPasoRellenar.DESCARGAR_XML, parametros);
		datosFichero = (byte[]) resPaso.getParametroRetorno("xml");
		this.logger.info("XML formulario guardado: " + new String(datosFichero, "UTF-8"));

		// -- Descargar pdf
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idFormulario", dpr.getFormularios().get(1).getId());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, dp.getActual().getId(),
				TypeAccionPasoRellenar.DESCARGAR_FORMULARIO, parametros);
		nombreFichero = (String) resPaso.getParametroRetorno("nombreFichero");
		datosFichero = (byte[]) resPaso.getParametroRetorno("datosFichero");
		Assert.isTrue(nombreFichero.endsWith(".pdf"), "El fichero no es pdf");
		Assert.isTrue(datosFichero.length > 0, "El fichero no tiene contenido");
	}

	/**
	 * Test paso anexar.
	 *
	 * @param idSesionTramitacion
	 *                                id sesión
	 * @throws IOException
	 */
	private void flujoTramitacion_anexar_presencial(final String idSesionTramitacion) throws IOException {
		DetallePasos dp;
		ResultadoIrAPaso rp;
		ResultadoAccionPaso ra;
		ParametrosAccionPaso params;

		String idPasoAnexar;

		// - Pasamos a paso anexar
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		rp = flujoTramitacionService.irAPaso(idSesionTramitacion, dp.getSiguiente());
		Assert.isTrue(StringUtils.equals(rp.getIdPasoActual(), dp.getSiguiente()),
				"No se ha podido pasar a siguiente paso");
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.ANEXAR, "Paso actual no es anexar");
		idPasoAnexar = dp.getActual().getId();
		this.logger.info("Detalle paso: " + dp.print());

		// - Anexamos primer anexo
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idAnexo", ((DetallePasoAnexar) dp.getActual()).getAnexos().get(0).getId());
		params.addParametroEntrada("presentacion", TypePresentacion.PRESENCIAL);
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoAnexar,
				TypeAccionPasoAnexar.ANEXAR_DOCUMENTO, params);

		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		this.logger.info("Detalle paso: " + dp.print());

		// - Anexamos segundo anexo
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idAnexo", ((DetallePasoAnexar) dp.getActual()).getAnexos().get(1).getId());
		params.addParametroEntrada("presentacion", TypePresentacion.PRESENCIAL);
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoAnexar,
				TypeAccionPasoAnexar.ANEXAR_DOCUMENTO, params);

		// - Anexamos tercer anexo
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idAnexo", ((DetallePasoAnexar) dp.getActual()).getAnexos().get(2).getId());
		params.addParametroEntrada("presentacion", TypePresentacion.PRESENCIAL);
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoAnexar,
				TypeAccionPasoAnexar.ANEXAR_DOCUMENTO, params);
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		this.logger.info("Detalle paso: " + dp.print());

		// -- Paso terminado
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.ANEXAR, "No esta en paso anexar");
		Assert.isTrue(dp.getActual().getCompletado() == TypeSiNo.SI, "Paso rellenar no esta completado");
		this.logger.info("Detalle paso: " + dp.print());

	}

	/**
	 * Test paso anexar.
	 *
	 * @param idSesionTramitacion
	 *                                id sesión
	 * @throws IOException
	 */
	private void flujoTramitacion_anexar_electronico(final String idSesionTramitacion) throws IOException {
		DetallePasos dp;
		ResultadoIrAPaso rp;
		ResultadoAccionPaso ra;
		ParametrosAccionPaso params;

		String idPasoAnexar;

		// - Pasamos a paso anexar
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		rp = flujoTramitacionService.irAPaso(idSesionTramitacion, dp.getSiguiente());
		Assert.isTrue(StringUtils.equals(rp.getIdPasoActual(), dp.getSiguiente()),
				"No se ha podido pasar a siguiente paso");
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.ANEXAR, "Paso actual no es anexar");
		idPasoAnexar = dp.getActual().getId();
		this.logger.info("Detalle paso: " + dp.print());

		// - Anexamos primer anexo
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idAnexo", ((DetallePasoAnexar) dp.getActual()).getAnexos().get(0).getId());
		params.addParametroEntrada("presentacion", TypePresentacion.ELECTRONICA);
		params.addParametroEntrada("nombreFichero", "18KB.pdf");
		params.addParametroEntrada("datosFichero", readResourceFromClasspath("test-files/18KB.pdf"));
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoAnexar,
				TypeAccionPasoAnexar.ANEXAR_DOCUMENTO, params);

		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		this.logger.info("Detalle paso: " + dp.print());

		// - Descargar anexo
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idAnexo", ((DetallePasoAnexar) dp.getActual()).getAnexos().get(0).getId());
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoAnexar, TypeAccionPasoAnexar.DESCARGAR_ANEXO,
				params);
		Assert.isTrue(StringUtils.isNotBlank((String) ra.getParametroRetorno("nombreFichero")),
				"No se devuelve nombre fichero");
		Assert.isTrue(ra.getParametroRetorno("datosFichero") != null, "No se devuelve datos fichero");

		// - Anexamos segundo anexo: genérico primera instancia
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idAnexo", ((DetallePasoAnexar) dp.getActual()).getAnexos().get(1).getId());
		params.addParametroEntrada("presentacion", TypePresentacion.ELECTRONICA);
		params.addParametroEntrada("instancia", "1");
		params.addParametroEntrada("titulo", "instancia A");
		params.addParametroEntrada("nombreFichero", "18KB.pdf");
		params.addParametroEntrada("datosFichero", readResourceFromClasspath("test-files/18KB.pdf"));
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoAnexar,
				TypeAccionPasoAnexar.ANEXAR_DOCUMENTO, params);

		// - Anexamos segundo anexo: genérico segunda instancia
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idAnexo", ((DetallePasoAnexar) dp.getActual()).getAnexos().get(1).getId());
		params.addParametroEntrada("presentacion", TypePresentacion.ELECTRONICA);
		params.addParametroEntrada("instancia", "2");
		params.addParametroEntrada("titulo", "instancia B");
		params.addParametroEntrada("nombreFichero", "18KB.pdf");
		params.addParametroEntrada("datosFichero", readResourceFromClasspath("test-files/18KB.pdf"));
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoAnexar,
				TypeAccionPasoAnexar.ANEXAR_DOCUMENTO, params);

		// Borramos instancia segundo anexo
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idAnexo", ((DetallePasoAnexar) dp.getActual()).getAnexos().get(1).getId());
		params.addParametroEntrada("instancia", "1");
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoAnexar, TypeAccionPasoAnexar.BORRAR_ANEXO,
				params);

		// - Anexamos segundo anexo: genérico tercera instancia
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idAnexo", ((DetallePasoAnexar) dp.getActual()).getAnexos().get(1).getId());
		params.addParametroEntrada("presentacion", TypePresentacion.ELECTRONICA);
		params.addParametroEntrada("instancia", "2");
		params.addParametroEntrada("titulo", "instancia C");
		params.addParametroEntrada("nombreFichero", "18KB.pdf");
		params.addParametroEntrada("datosFichero", readResourceFromClasspath("test-files/18KB.pdf"));
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoAnexar,
				TypeAccionPasoAnexar.ANEXAR_DOCUMENTO, params);

		// - Anexamos anexo XML y validamos datos
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idAnexo", ((DetallePasoAnexar) dp.getActual()).getAnexos().get(2).getId());
		params.addParametroEntrada("presentacion", TypePresentacion.ELECTRONICA);
		params.addParametroEntrada("nombreFichero", "fichero.xml");
		params.addParametroEntrada("datosFichero", readResourceFromClasspath("test-files/fichero.xml"));
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoAnexar,
				TypeAccionPasoAnexar.ANEXAR_DOCUMENTO, params);

		// - Anexamos anexo PDF y validamos datos
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idAnexo", ((DetallePasoAnexar) dp.getActual()).getAnexos().get(3).getId());
		params.addParametroEntrada("presentacion", TypePresentacion.ELECTRONICA);
		params.addParametroEntrada("nombreFichero", "anexoFormulario.pdf");
		params.addParametroEntrada("datosFichero", readResourceFromClasspath("test-files/anexoFormulario.pdf"));
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoAnexar,
				TypeAccionPasoAnexar.ANEXAR_DOCUMENTO, params);

		// - Anexamos anexo DINAMICO (primera instancia)
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idAnexo", "DIN1");
		params.addParametroEntrada("presentacion", TypePresentacion.ELECTRONICA);
		params.addParametroEntrada("nombreFichero", "dinamico1.pdf");
		params.addParametroEntrada("titulo", "instancia dinamica 1");
		params.addParametroEntrada("datosFichero", readResourceFromClasspath("test-files/anexoFormulario.pdf"));
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoAnexar,
				TypeAccionPasoAnexar.ANEXAR_DOCUMENTO, params);

		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		this.logger.info("Detalle paso: " + dp.print());

		// - Anexamos anexo DINAMICO (segunda instancia)
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idAnexo", "DIN1");
		params.addParametroEntrada("presentacion", TypePresentacion.ELECTRONICA);
		params.addParametroEntrada("nombreFichero", "dinamico2.pdf");
		params.addParametroEntrada("titulo", "instancia dinamica 2");
		params.addParametroEntrada("datosFichero", readResourceFromClasspath("test-files/anexoFormulario.pdf"));
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoAnexar,
				TypeAccionPasoAnexar.ANEXAR_DOCUMENTO, params);

		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		this.logger.info("Detalle paso: " + dp.print());

		// -- Paso terminado
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.ANEXAR, "No esta en paso anexar");
		Assert.isTrue(dp.getActual().getCompletado() == TypeSiNo.SI, "Paso anexar no esta completado");
		this.logger.info("Detalle paso: " + dp.print());

	}

	/**
	 * Test paso pagar.
	 *
	 * @param idSesionTramitacion
	 *                                id sesión
	 * @throws IOException
	 */
	private void flujoTramitacion_pagar_electronico(final String idSesionTramitacion,
			final UsuarioAutenticadoInfo usuarioAutenticadoInfo) throws IOException {

		DetallePasos dp;
		ResultadoIrAPaso rp;
		ResultadoAccionPaso ra;
		ParametrosAccionPaso params;

		String idPasoPagar;

		// - Pasamos a paso pagar
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		rp = flujoTramitacionService.irAPaso(idSesionTramitacion, dp.getSiguiente());
		Assert.isTrue(StringUtils.equals(rp.getIdPasoActual(), dp.getSiguiente()),
				"No se ha podido pasar a siguiente paso");
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.PAGAR, "Paso actual no es pagar");
		idPasoPagar = dp.getActual().getId();
		this.logger.info("Detalle paso: " + dp.print());

		// - Iniciamos pago electronico
		this.logger.info("Iniciamos pago electronico...");
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idPago", ((DetallePasoPagar) dp.getActual()).getPagos().get(0).getId());
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoPagar, TypeAccionPasoPagar.INICIAR_PAGO,
				params);
		final String url = (String) ra.getParametroRetorno("url");
		Assert.isTrue(StringUtils.isNotBlank(url), "No se ha devuelto URL de redireccion a pago");
		this.logger.info("Detalle paso: " + dp.print());

		// - Simulamos retorno (carga de trámite desde fuera sesión activa)
		this.logger.info("Simulamos retorno...");
		flujoTramitacionService.cargarTramite(idSesionTramitacion, usuarioAutenticadoInfo);

		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.PAGAR, "Paso actual no es pagar");
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idPago", ((DetallePasoPagar) dp.getActual()).getPagos().get(0).getId());
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoPagar,
				TypeAccionPasoPagar.VERIFICAR_PAGO_PASARELA, params);
		final PagoVerificacion verificacionPago = (PagoVerificacion) ra.getParametroRetorno("verificacion");
		Assert.isTrue(verificacionPago.getRealizado() == TypeSiNo.SI, "Pago no se indica como pagado");
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		this.logger.info("Detalle paso: " + dp.print());

		this.logger.info("Descargamos justificante...");
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idPago", ((DetallePasoPagar) dp.getActual()).getPagos().get(0).getId());
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoPagar,
				TypeAccionPasoPagar.DESCARGAR_JUSTIFICANTE, params);
		final byte[] justif = (byte[]) ra.getParametroRetorno("datos");
		Assert.isTrue(justif.length > 0, "No se puede obtener justificante");
	}

	/**
	 * Pago presencial.
	 *
	 * @param idSesionTramitacion
	 *                                id sesión
	 * @throws IOException
	 */
	private void flujoTramitacion_pagar_presencial(final String idSesionTramitacion,
			final UsuarioAutenticadoInfo usuarioAutenticadoInfo) throws IOException {

		DetallePasos dp;
		ResultadoIrAPaso rp;
		ResultadoAccionPaso ra;
		ParametrosAccionPaso params;

		String idPasoPagar;

		// - Pasamos a paso pagar
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		rp = flujoTramitacionService.irAPaso(idSesionTramitacion, dp.getSiguiente());
		Assert.isTrue(StringUtils.equals(rp.getIdPasoActual(), dp.getSiguiente()),
				"No se ha podido pasar a siguiente paso");
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.PAGAR, "Paso actual no es pagar");
		idPasoPagar = dp.getActual().getId();
		this.logger.info("Detalle paso: " + dp.print());

		// - Iniciamos pago electronico
		this.logger.info("Iniciamos pago presencial...");
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idPago", ((DetallePasoPagar) dp.getActual()).getPagos().get(0).getId());
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoPagar,
				TypeAccionPasoPagar.CARTA_PAGO_PRESENCIAL, params);

		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		this.logger.info("Detalle paso: " + dp.print());

		// - Descargamos carta pago
		this.logger.info("Descargamos carta pago...");
		params = new ParametrosAccionPaso();
		params.addParametroEntrada("idPago", ((DetallePasoPagar) dp.getActual()).getPagos().get(0).getId());
		ra = flujoTramitacionService.accionPaso(idSesionTramitacion, idPasoPagar,
				TypeAccionPasoPagar.DESCARGAR_JUSTIFICANTE, params);
		final byte[] justif = (byte[]) ra.getParametroRetorno("datos");
		Assert.isTrue(justif.length > 0, "No se puede obtener carta pago");
	}

	/**
	 * Test paso Debe saber.
	 *
	 * @param idSesionTramitacion
	 *                                id sesión tramitación
	 */
	private void flujoTramitacion_debeSaber(final String idSesionTramitacion) {
		final DetallePasos dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.DEBESABER, "Paso inicial no es debe saber");
		this.logger.info("Detalle paso: " + dp.print());
	}

	/**
	 * Inicio de un trámite.
	 *
	 * @param usuarioAutenticadoInfo
	 *                                   id sesión tramitación
	 */
	private String flujoTramitacion_iniciarTramite(final UsuarioAutenticadoInfo usuarioAutenticadoInfo) {
		final Map<String, String> parametrosInicio = new HashMap<>();

		final String idSesionTramitacion = flujoTramitacionService.iniciarTramite(usuarioAutenticadoInfo,
				SistragesMock.ID_TRAMITE, SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA,
				SistragesMock.ID_TRAMITE_CP, false, URL_INICIO, parametrosInicio);
		Assert.isTrue(usuarioAutenticadoInfo != null, "No se devuelve id sesion tramitacion");
		this.logger.info("Tramite iniciado: " + usuarioAutenticadoInfo);

		// Detalle tramite
		final DetalleTramite dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
		Assert.isTrue(idSesionTramitacion.equals(dt.getTramite().getIdSesion()), "No coincide id sesion tramitacion");
		this.logger.info("Detalle Tramite: " + dt.print());

		return idSesionTramitacion;
	}

	/**
	 * Formulario soporte incidencias: verificación funcionamiento formulario
	 * soporte de incidencias.
	 */
	@Test
	public void test5_soporteIncidencias() {

		final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(TypeAutenticacion.AUTENTICADO);

		// Iniciar trámite
		final Map<String, String> parametrosInicio = new HashMap<>();
		final String idSesionTramitacion = flujoTramitacionService.iniciarTramite(usuarioAutenticadoInfo,
				SistragesMock.ID_TRAMITE, SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA,
				SistragesMock.ID_TRAMITE_CP, false, URL_INICIO, parametrosInicio);
		Assert.isTrue(idSesionTramitacion != null, "No se devuelve id sesion tramitacion");
		this.logger.info("Tramite iniciado: " + idSesionTramitacion);

		// Detalle tramite
		final DetalleTramite dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
		Assert.isTrue(idSesionTramitacion.equals(dt.getTramite().getIdSesion()), "No coincide id sesion tramitacion");
		this.logger.info("Detalle Tramite: " + dt.print());

		final AnexoFichero anexo = new AnexoFichero();
		anexo.setFileName("fichero.txt");
		anexo.setFileContent("Hola".getBytes());
		anexo.setFileContentType("text/plain");
		flujoTramitacionService.envioFormularioSoporte(idSesionTramitacion, usuarioAutenticadoInfo.getNif(),
				usuarioAutenticadoInfo.getNombre(), "961234567", "usu@correo.es",
				dt.getEntidad().getSoporte().getProblemas().get(0).getCodigo(), "Problema", "Mañana y tarde", anexo);

		this.logger.info("Email enviado");

	}

	private UsuarioAutenticadoInfo loginSimulado(final TypeAutenticacion auth) {

		// Recuperacion info login
		final InfoLoginTramite infoLogin = securityService.obtenerInfoLoginTramite(SistragesMock.ID_TRAMITE,
				SistragesMock.VERSION_TRAMITE, SistragesMock.ID_TRAMITE_CP, false, SistragesMock.IDIOMA, URL_INICIO);

		// Inicio sesion hacia redireccion
		// - Como se usa plugin mock, ponemos primero el metodo con el que se autentica
		final List<TypeMetodoAutenticacion> authList = new ArrayList<>();
		authList.add(TypeMetodoAutenticacion.CLAVE_CERTIFICADO);
		authList.add(TypeMetodoAutenticacion.ANONIMO);

		final String urlRedirect = securityService.iniciarSesionAutenticacion(
				SistragesMock.crearEntidad().getIdentificador(), SistragesMock.IDIOMA, authList, infoLogin.getQaa(),
				"http://localhost:8080", "http://localhost:8080", true);

		Assert.isTrue(urlRedirect != null, "No se ha devuelto url redireccion login");

		// Autenticar usuario (en modo mock, autentica segun primera letra del
		// ticket)
		final SesionInfo sesionInfo = new SesionInfo();
		sesionInfo.setIdioma(SistragesMock.IDIOMA);
		sesionInfo.setUserAgent("");
		final UsuarioAutenticadoInfo usuarioAutenticadoInfo = securityService.validarTicketAutenticacion(sesionInfo,
				auth.toString() + "123");
		return usuarioAutenticadoInfo;
	}

	/**
	 * Lee recurso de classpath.
	 *
	 * @param filePath
	 *                     path file
	 * @return contenido fichero
	 * @throws IOException
	 */
	private byte[] readResourceFromClasspath(final String filePath) throws IOException {
		try (final InputStream isFile = FlujoTramitacionService.class.getClassLoader().getResourceAsStream(filePath);) {
			final byte[] content = IOUtils.toByteArray(isFile);
			return content;
		}
	}

	/**
	 * Test paso registro.
	 *
	 * @param idSesionTramitacion
	 *                                id sesión
	 * @throws IOException
	 */
	private void flujoTramitacion_registro_electronico(final String idSesionTramitacion,
			final UsuarioAutenticadoInfo usuarioAutenticadoInfo) throws IOException {

		DetallePasos dp;
		ResultadoIrAPaso rp;
		ResultadoAccionPaso resPaso;
		ParametrosAccionPaso parametros;

		String idPaso;

		// - Pasamos a paso registrar
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		rp = flujoTramitacionService.irAPaso(idSesionTramitacion, dp.getSiguiente());
		Assert.isTrue(StringUtils.equals(rp.getIdPasoActual(), dp.getSiguiente()),
				"No se ha podido pasar a siguiente paso");
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.REGISTRAR, "Paso actual no es registrar");
		idPaso = dp.getActual().getId();
		this.logger.info("Detalle paso: " + dp.print());

		// -- Descargamos formulario
		final DocumentoRegistro formulario = ((DetallePasoRegistrar) dp.getActual()).getFormularios().get(0);
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idDocumento", formulario.getId());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.DESCARGAR_DOCUMENTO, parametros);
		Assert.isTrue(((String) resPaso.getParametroRetorno("nombreFichero")).endsWith(".pdf"), "El fichero no es pdf");
		Assert.isTrue(((byte[]) resPaso.getParametroRetorno("datosFichero")).length > 0,
				"El fichero no tiene contenido");

		// -- Descargamos anexo
		final DocumentoRegistro anexo = ((DetallePasoRegistrar) dp.getActual()).getAnexos().get(0);
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idDocumento", anexo.getId());
		parametros.addParametroEntrada("instancia", anexo.getInstancia() + "");
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.DESCARGAR_DOCUMENTO, parametros);
		Assert.isTrue(((byte[]) resPaso.getParametroRetorno("datosFichero")).length > 0,
				"El fichero no tiene contenido");

		// -- Descargamos pago
		final DocumentoRegistro pago = ((DetallePasoRegistrar) dp.getActual()).getPagos().get(0);
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idDocumento", pago.getId());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.DESCARGAR_DOCUMENTO, parametros);
		Assert.isTrue(((String) resPaso.getParametroRetorno("nombreFichero")).endsWith(".pdf"), "El fichero no es pdf");
		Assert.isTrue(((byte[]) resPaso.getParametroRetorno("datosFichero")).length > 0,
				"El fichero no tiene contenido");

		// -- Firmamos formulario
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idDocumento", formulario.getId());
		parametros.addParametroEntrada("instancia", "1");
		parametros.addParametroEntrada("firmante", usuarioAutenticadoInfo.getNif());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.INICIAR_FIRMA_DOCUMENTO, parametros);
		Assert.isTrue(resPaso.getParametroRetorno("url") != null, "No se ha retornado url inicio firma");

		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.VERIFICAR_FIRMA_DOCUMENTO, parametros);
		final FirmaVerificacion firmaVerificacion = (FirmaVerificacion) resPaso.getParametroRetorno("resultado");
		Assert.isTrue(
				firmaVerificacion.getRealizada() == TypeSiNo.SI && firmaVerificacion.getVerificada() == TypeSiNo.SI,
				"No se ha verificado la firma correctamente: " + firmaVerificacion.getDetalleError());

		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		this.logger.info("Detalle paso: " + dp.print());

		// -- Descargar firma
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idDocumento", formulario.getId());
		parametros.addParametroEntrada("instancia", "1");
		parametros.addParametroEntrada("firmante", usuarioAutenticadoInfo.getNif());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.DESCARGAR_FIRMA, parametros);
		Assert.isTrue((resPaso.getParametroRetorno("nombreFichero") != null
				&& resPaso.getParametroRetorno("datosFichero") != null), "No se ha recuperado firma");

		// -- Registrar
		parametros = new ParametrosAccionPaso();
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.INICIAR_SESION_REGISTRO, parametros);
		parametros = new ParametrosAccionPaso();
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.REGISTRAR_TRAMITE, parametros);
		Assert.isTrue(((ResultadoRegistrar) resPaso.getParametroRetorno("resultado"))
				.getResultado() == TypeResultadoRegistro.CORRECTO, "No se podido registrar");

		// -- Paso terminado, pasa automáticamente a Guardar
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		idPaso = dp.getActual().getId();
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.GUARDAR, "No esta en paso guardar");
		Assert.isTrue(dp.getActual().getCompletado() == TypeSiNo.SI, "Paso registrar no esta completado");
		this.logger.info("Detalle paso: " + dp.print());

		// -- Paso Guardar: Descargar formulario
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idDocumento", formulario.getId());
		parametros.addParametroEntrada("instancia", "1");
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoGuardar.DESCARGAR_DOCUMENTO, parametros);
		Assert.isTrue((resPaso.getParametroRetorno("nombreFichero") != null
				&& resPaso.getParametroRetorno("datosFichero") != null), "No se ha recuperado documento");

		// -- Paso Guardar: Descargar firma formulario
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idDocumento", formulario.getId());
		parametros.addParametroEntrada("instancia", "1");
		parametros.addParametroEntrada("firmante", usuarioAutenticadoInfo.getNif());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso, TypeAccionPasoGuardar.DESCARGAR_FIRMA,
				parametros);
		Assert.isTrue((resPaso.getParametroRetorno("nombreFichero") != null
				&& resPaso.getParametroRetorno("datosFichero") != null), "No se ha recuperado firma");

		// -- Paso Guardar: descargar justificante
		parametros = new ParametrosAccionPaso();
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoGuardar.DESCARGAR_JUSTIFICANTE, parametros);
		Assert.isTrue(resPaso.getParametroRetorno("nombreFichero") != null,
				"Justificante registro no tiene nombre fichero");
		Assert.isTrue(resPaso.getParametroRetorno("datosFichero") != null, "Justificante registro no tiene contenido");

		// -- Paso Guardar: valorar trámite
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("valoracion", "1");
		parametros.addParametroEntrada("problemas", "P1;P2;P3");
		parametros.addParametroEntrada("observaciones", "Observaciones valoración");
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoGuardar.VALORACION_TRAMITE, parametros);
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(((DetallePasoGuardar) dp.getActual()).getValoracion().getValorado() == TypeSiNo.SI,
				"No se ha valorado trámite");

	}

}
