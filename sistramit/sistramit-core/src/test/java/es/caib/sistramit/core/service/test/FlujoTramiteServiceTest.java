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
import es.caib.sistramit.core.api.model.flujo.DetallePasoPagar;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRellenar;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.DocumentoRegistro;
import es.caib.sistramit.core.api.model.flujo.PagoVerificacion;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoAnexar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoPagar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoRellenar;
import es.caib.sistramit.core.api.model.flujo.types.TypeFormulario;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.types.TypeValor;
import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.SesionInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;
import es.caib.sistramit.core.api.service.SecurityService;
import es.caib.sistramit.core.service.component.formulario.UtilsFormulario;
import es.caib.sistramit.core.service.model.flujo.types.TypeResultadoRegistro;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.test.mock.SistragesMock;

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

		// Generar sesion tramitacion
		final String idSesionTramitacion = flujoTramitacionService.crearSesionTramitacion(usuarioAutenticadoInfo);

		// Iniciar trámite
		final Map<String, String> parametrosInicio = new HashMap<>();

		flujoTramitacionService.iniciarTramite(idSesionTramitacion, SistragesMock.ID_TRAMITE,
				SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA, SistragesMock.ID_TRAMITE_CP, URL_INICIO,
				parametrosInicio);
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

		// Generar sesion tramitacion
		final String idSesionTramitacion = flujoTramitacionService.crearSesionTramitacion(usuarioAutenticadoInfo);

		// Iniciar trámite
		final Map<String, String> parametrosInicio = new HashMap<>();
		flujoTramitacionService.iniciarTramite(idSesionTramitacion, SistragesMock.ID_TRAMITE,
				SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA, SistragesMock.ID_TRAMITE_CP, URL_INICIO,
				parametrosInicio);
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

		// Generar sesion tramitacion
		final String idSesionTramitacion = flujoTramitacionService.crearSesionTramitacion(usuarioAutenticadoInfo);

		// Iniciar trámite
		flujoTramitacion_iniciarTramite(idSesionTramitacion);

		// Detalle paso actual: Debe saber
		flujoTramitacion_debeSaber(idSesionTramitacion);

		// Pasamos a paso siguiente: rellenar
		flujoTramitacion_rellenar(idSesionTramitacion, TypePresentacion.ELECTRONICA);

		// Pasamos a paso siguiente: anexar
		flujoTramitacion_anexar_electronico(idSesionTramitacion);

		// Pasamos a paso siguiente: pagar
		flujoTramitacion_pagar_electronico(idSesionTramitacion, usuarioAutenticadoInfo);

		// Pasamos a paso siguiente: pagar
		flujoTramitacion_registro_electronico(idSesionTramitacion, usuarioAutenticadoInfo);

	}

	/**
	 * Verificación flujo tramitación preregistro: verifica la funcionalidad básica
	 * de los diferentes pasos de tramitación a partir de la definición simulada de
	 * una versión de trámite.
	 */
	// @Test
	public void test7_flujoTramitacionPreregistro() throws Exception {

		final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(TypeAutenticacion.AUTENTICADO);

		// Generar sesion tramitacion
		final String idSesionTramitacion = flujoTramitacionService.crearSesionTramitacion(usuarioAutenticadoInfo);

		// Iniciar trámite
		flujoTramitacion_iniciarTramite(idSesionTramitacion);

		// Detalle paso actual: Debe saber
		flujoTramitacion_debeSaber(idSesionTramitacion);

		// Pasamos a paso siguiente: rellenar
		flujoTramitacion_rellenar(idSesionTramitacion, TypePresentacion.PRESENCIAL);

		// Pasamos a paso siguiente: anexar
		flujoTramitacion_anexar_presencial(idSesionTramitacion);

		// Pasamos a paso siguiente: pagar
		flujoTramitacion_pagar_presencial(idSesionTramitacion, usuarioAutenticadoInfo);

	}

	/**
	 * Test paso rellenar.
	 *
	 * @param idSesionTramitacion
	 *            id sesión
	 * @param presentacion
	 *            presentacion
	 * @throws UnsupportedEncodingException
	 */
	private void flujoTramitacion_rellenar(final String idSesionTramitacion, final TypePresentacion presentacion)
			throws UnsupportedEncodingException {
		ParametrosAccionPaso parametros;
		ResultadoAccionPaso resPaso;
		String nombreFichero;
		byte[] datosFichero;
		DetallePasos dp;

		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		final ResultadoIrAPaso rp = flujoTramitacionService.irAPaso(idSesionTramitacion, dp.getSiguiente());
		Assert.isTrue(StringUtils.equals(rp.getIdPasoActual(), dp.getSiguiente()),
				"No se ha podido pasar a siguiente paso (debe saber)");
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
		this.logger.info("XML formulario inicial: " + new String(datosFichero, "UTF-8"));

		// -- Abrimos formulario
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, dp.getActual().getId(),
				TypeAccionPasoRellenar.ABRIR_FORMULARIO, parametros);
		final AbrirFormulario af = (AbrirFormulario) resPaso.getParametroRetorno("referencia");
		Assert.isTrue(af.getTipo() == TypeFormulario.INTERNO, "Tipo formulario no es interno");
		Assert.isTrue(af.getUrl() != null, "No se ha devuelto url formulario");

		// -- Simulamos rellenar
		final String campoNuevoId = "PRESENTACION";
		final String campoNuevoValor = presentacion.toString();
		final XmlFormulario xmlForm = UtilsFormulario.xmlToValores(datosFichero);
		xmlForm.getValores().add(new ValorCampoSimple(campoNuevoId, campoNuevoValor));
		final String xmlNuevo = new String(UtilsFormulario.valoresToXml(xmlForm), "UTF-8");

		ParametrosAccionPaso pParametros;
		pParametros = new ParametrosAccionPaso();
		pParametros.addParametroEntrada("idFormulario", dpr.getFormularios().get(0).getId());
		final String ticketForm = flujoTramitacionService.simularRellenarFormulario(idSesionTramitacion,
				xmlNuevo.trim());

		// -- Guardar formulario
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idFormulario", dpr.getFormularios().get(0).getId());
		parametros.addParametroEntrada("ticket", ticketForm);
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, dp.getActual().getId(),
				TypeAccionPasoRellenar.GUARDAR_FORMULARIO, parametros);
		final TypeSiNo cancelado = (TypeSiNo) resPaso.getParametroRetorno("cancelado");
		final TypeSiNo correcto = (TypeSiNo) resPaso.getParametroRetorno("correcto");
		final String mensajeIncorrecto = (String) resPaso.getParametroRetorno("mensajeIncorrecto");
		Assert.isTrue(cancelado == TypeSiNo.NO, "El formulario se ha cancelado");
		Assert.isTrue(correcto == TypeSiNo.SI, "El formulario no es correcto");
		Assert.isTrue(StringUtils.isAllBlank(mensajeIncorrecto), "Existe mensaje incorrecto");

		// -- Mostramos xml tras guardar
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idFormulario", dpr.getFormularios().get(0).getId());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, dp.getActual().getId(),
				TypeAccionPasoRellenar.DESCARGAR_XML, parametros);
		datosFichero = (byte[]) resPaso.getParametroRetorno("xml");
		this.logger.info("XML formulario guardado: " + new String(datosFichero, "UTF-8"));

		// Verificamos que esta el campo nuevo añadido
		final XmlFormulario xmlFormNuevo = UtilsFormulario.xmlToValores(datosFichero);
		boolean correctoCampoNuevo = false;
		for (final ValorCampo vc : xmlFormNuevo.getValores()) {
			if (vc.getId().equals(campoNuevoId) && vc.getTipo() == TypeValor.SIMPLE
					&& ((ValorCampoSimple) vc).getValor().equals(campoNuevoValor)) {
				correctoCampoNuevo = true;
				break;
			}
		}
		Assert.isTrue(correctoCampoNuevo, "No existe el nuevo campo añadido al guardar");

		// -- Descargar pdf
		parametros = new ParametrosAccionPaso();
		parametros.addParametroEntrada("idFormulario", dpr.getFormularios().get(0).getId());
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, dp.getActual().getId(),
				TypeAccionPasoRellenar.DESCARGAR_FORMULARIO, parametros);
		nombreFichero = (String) resPaso.getParametroRetorno("nombreFichero");
		datosFichero = (byte[]) resPaso.getParametroRetorno("datosFichero");
		Assert.isTrue(nombreFichero.endsWith(".pdf"), "El fichero no es pdf");
		Assert.isTrue(datosFichero.length > 0, "El fichero no tiene contenido");

		// -- Paso terminado
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.RELLENAR, "No esta en paso rellenar");
		Assert.isTrue(dp.getActual().getCompletado() == TypeSiNo.SI, "Paso rellenar no esta completado");
		this.logger.info("Detalle paso: " + dp.print());

	}

	/**
	 * Test paso anexar.
	 *
	 * @param idSesionTramitacion
	 *            id sesión
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
	 *            id sesión
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

		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		this.logger.info("Detalle paso: " + dp.print());

		// -- Paso terminado
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.ANEXAR, "No esta en paso anexar");
		Assert.isTrue(dp.getActual().getCompletado() == TypeSiNo.SI, "Paso rellenar no esta completado");
		this.logger.info("Detalle paso: " + dp.print());

	}

	/**
	 * Test paso pagar.
	 *
	 * @param idSesionTramitacion
	 *            id sesión
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
	 *            id sesión
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
	 *            id sesión tramitación
	 */
	private void flujoTramitacion_debeSaber(final String idSesionTramitacion) {
		final DetallePasos dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.DEBESABER, "Paso inicial no es debe saber");
		this.logger.info("Detalle paso: " + dp.print());
	}

	/**
	 * Inicio de un trámite.
	 *
	 * @param idSesionTramitacion
	 *            id sesión tramitación
	 */
	private void flujoTramitacion_iniciarTramite(final String idSesionTramitacion) {
		final Map<String, String> parametrosInicio = new HashMap<>();
		flujoTramitacionService.iniciarTramite(idSesionTramitacion, SistragesMock.ID_TRAMITE,
				SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA, SistragesMock.ID_TRAMITE_CP, URL_INICIO,
				parametrosInicio);
		Assert.isTrue(idSesionTramitacion != null, "No se devuelve id sesion tramitacion");
		this.logger.info("Tramite iniciado: " + idSesionTramitacion);

		// Detalle tramite
		final DetalleTramite dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
		Assert.isTrue(idSesionTramitacion.equals(dt.getTramite().getIdSesion()), "No coincide id sesion tramitacion");
		this.logger.info("Detalle Tramite: " + dt.print());
	}

	/**
	 * Formulario soporte incidencias: verificación funcionamiento formulario
	 * soporte de incidencias.
	 */
	@Test
	public void test5_soporteIncidencias() {

		final UsuarioAutenticadoInfo usuarioAutenticadoInfo = loginSimulado(TypeAutenticacion.AUTENTICADO);

		// Generar sesion tramitacion
		final String idSesionTramitacion = flujoTramitacionService.crearSesionTramitacion(usuarioAutenticadoInfo);

		// Iniciar trámite
		final Map<String, String> parametrosInicio = new HashMap<>();
		flujoTramitacionService.iniciarTramite(idSesionTramitacion, SistragesMock.ID_TRAMITE,
				SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA, SistragesMock.ID_TRAMITE_CP, URL_INICIO,
				parametrosInicio);
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
				dt.getEntidad().getSoporte().getProblemas().get(0).getCodigo(), "Problema", anexo);

		this.logger.info("Email enviado");

	}

	private UsuarioAutenticadoInfo loginSimulado(final TypeAutenticacion auth) {

		// Recuperacion info login
		final InfoLoginTramite infoLogin = securityService.obtenerInfoLoginTramite(SistragesMock.ID_TRAMITE,
				SistragesMock.VERSION_TRAMITE, SistragesMock.ID_TRAMITE_CP, SistragesMock.IDIOMA, URL_INICIO);

		// Inicio sesion hacia redireccion
		final List<TypeAutenticacion> authList = new ArrayList<>();
		authList.add(TypeAutenticacion.AUTENTICADO);
		authList.add(TypeAutenticacion.ANONIMO);

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
	 *            path file
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
	 *            id sesión
	 * @throws IOException
	 */
	private void flujoTramitacion_registro_electronico(final String idSesionTramitacion,
			final UsuarioAutenticadoInfo usuarioAutenticadoInfo) throws IOException {

		DetallePasos dp;
		ResultadoIrAPaso rp;
		ResultadoAccionPaso resPaso;
		ParametrosAccionPaso parametros;

		String idPaso;

		// - Pasamos a paso pagar
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

		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		this.logger.info("Detalle paso: " + dp.print());

		// -- Registrar
		parametros = new ParametrosAccionPaso();
		resPaso = flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.REGISTRAR_TRAMITE, parametros);
		Assert.isTrue(
				((TypeResultadoRegistro) resPaso.getParametroRetorno("resultado")) == TypeResultadoRegistro.CORRECTO,
				"No se podido registrar");
		Assert.isTrue(((String) resPaso.getParametroRetorno("numeroRegistro")) != null,
				"No se devuelve numero de registro");

		// -- Paso terminado
		dp = flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
		// TODO CUANDO ESTE PASO GUARDAR, DEBERA HABER SALTADO A PASO GUARDAR
		Assert.isTrue(dp.getActual().getTipo() == TypePaso.REGISTRAR, "No esta en paso registrar");
		Assert.isTrue(dp.getActual().getCompletado() == TypeSiNo.SI, "Paso registrar no esta completado");
		this.logger.info("Detalle paso: " + dp.print());

	}

}
