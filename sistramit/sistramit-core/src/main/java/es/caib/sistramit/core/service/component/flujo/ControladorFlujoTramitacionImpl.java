package es.caib.sistramit.core.service.component.flujo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RPasoTramitacion;
import es.caib.sistrages.rest.api.interna.RVersionTramiteControlAcceso;
import es.caib.sistramit.core.api.exception.AccesoNoPermitidoException;
import es.caib.sistramit.core.api.exception.AccesoPasoRegistroCatalogoSimulado;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.CancelacionNoPermitidaException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.exception.TramiteCaducadoException;
import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.AvisoPlataforma;
import es.caib.sistramit.core.api.model.flujo.DetallePaso;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.PasoLista;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoAccion;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypeFlujoTramitacion;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.service.ApplicationContextProvider;
import es.caib.sistramit.core.service.component.flujo.reglas.ContextoReglaTramitacion;
import es.caib.sistramit.core.service.component.flujo.reglas.ReglaTramitacion;
import es.caib.sistramit.core.service.component.flujo.reglas.ReglasFlujo;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResPersonalizacionTramite;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosSesionTramitacion;
import es.caib.sistramit.core.service.model.flujo.NavegacionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeFaseEjecucion;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Lógica del flujo de tramitación.
 *
 * @author Indra
 *
 */
@Component("controladorFlujo")
@SuppressWarnings("serial")
public final class ControladorFlujoTramitacionImpl implements ControladorFlujoTramitacion, Serializable {

	/** Literales negocio. */
	@Autowired
	private Literales literales;

	/**
	 * Lógica de modificación del flujo.
	 */
	@Autowired
	private ModificacionesFlujo modificacionesFlujo;

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Atributo constante logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ControladorFlujoTramitacionImpl.class);

	@Override
	public ResultadoIrAPaso iniciarTramite(final DatosSesionTramitacion pDatosSesion) {

		debug(pDatosSesion, "Iniciar tramite");

		// Obtenemos reglas del flujo
		final ReglasFlujo reglas = obtenerReglasFlujo(pDatosSesion.getDatosTramite().getTipoFlujo());

		// Generamos contexto ejecucion reglas
		final ContextoReglaTramitacion ctx = generarContextoReglaTramitacion(pDatosSesion);

		// Calculamos parametros iniciales tramite.
		modificacionesFlujo.calcularParametrosInicialesTramite(pDatosSesion);

		// Calculamos metodos disponibles de login (script de login)
		modificacionesFlujo.calcularMetodosLogin(pDatosSesion);

		// Ejecuta script de personalizacion del tramite: título, activo y
		// plazo.
		personalizacionTramite(pDatosSesion);

		// Realiza control de acceso: activo, plazos y filtro de certificados y
		// grupos.
		controlAcceso(pDatosSesion);

		// Creamos tramite en persistencia
		modificacionesFlujo.crearTramitePersistencia(pDatosSesion);

		// Ejecutamos reglas pre
		if (!ejecutarReglas(ctx, reglas, TypeFaseEjecucion.PRE_INICIO, null)) {
			throw new ErrorConfiguracionException("Error aplicando regla de tramitación al iniciar trámite");
		}

		// Se establece primer paso como actual y se va a dicho paso
		final String idPrimerPaso = pDatosSesion.getDatosTramite().getDatosPasos().get(0).getIdPaso();
		final ResultadoIrAPaso rp = this.irAPaso(pDatosSesion, idPrimerPaso);

		// Registramos acceso al trámite
		modificacionesFlujo.registrarAccesoTramite(pDatosSesion);

		// Ejecutamos reglas post
		if (!ejecutarReglas(ctx, reglas, TypeFaseEjecucion.POST_INICIO, null)) {
			throw new ErrorConfiguracionException("Error aplicando regla de tramitación al iniciar trámite");
		}
		return rp;
	}

	@Override
	public ResultadoIrAPaso cargarTramite(final DatosSesionTramitacion pDatosSesion) {

		debug(pDatosSesion, "Cargar tramite");

		// Obtenemos reglas del flujo
		final ReglasFlujo reglas = obtenerReglasFlujo(pDatosSesion.getDatosTramite().getTipoFlujo());
		// Generamos contexto ejecucion reglas
		final ContextoReglaTramitacion ctx = generarContextoReglaTramitacion(pDatosSesion);

		// Calculamos metodos disponibles de login (script de login)
		modificacionesFlujo.calcularMetodosLogin(pDatosSesion);

		// Ejecuta script de personalizacion del tramite: título, activo y
		// plazo.
		personalizacionTramite(pDatosSesion);

		// Realiza control de acceso: activo, plazos y filtro de certificados y
		// grupos.
		controlAcceso(pDatosSesion);

		// Carga lista de pasos y establece paso actual
		modificacionesFlujo.cargarPasosPersistencia(pDatosSesion);

		// Ejecutamos reglas pre
		if (!ejecutarReglas(ctx, reglas, TypeFaseEjecucion.PRE_CARGA, null)) {
			throw new ErrorConfiguracionException("Error aplicando regla de tramitación al cargar trámite");
		}

		// Vamos a siguiente paso
		final ResultadoIrAPaso rp = this.irAPaso(pDatosSesion, pDatosSesion.getDatosTramite().getIdPasoActual());

		// Registramos acceso al trámite
		modificacionesFlujo.registrarAccesoTramite(pDatosSesion);

		// Ejecutamos reglas post
		if (!ejecutarReglas(ctx, reglas, TypeFaseEjecucion.POST_CARGA, null)) {
			throw new ErrorConfiguracionException("Error aplicando regla de tramitación al cargar trámite");
		}

		return rp;
	}

	@Override
	public ResultadoIrAPaso irAPaso(final DatosSesionTramitacion pDatosSesion, final String pIdPaso) {

		debug(pDatosSesion, "Ir a paso " + pIdPaso);

		// Obtenemos estado inicial
		final TypeEstadoTramite estadoInicial = pDatosSesion.getDatosTramite().getEstadoTramite();

		// Verificamos paso (excepto paso simulado guardar)
		TypePaso tipoPaso = null;
		if (ConstantesFlujo.ID_PASO_GUARDAR.equals(pIdPaso)) {
			tipoPaso = TypePaso.GUARDAR;
		} else {
			final RPasoTramitacion pasoDef = UtilsSTG.devuelveDefinicionPaso(pIdPaso,
					pDatosSesion.getDefinicionTramite());
			if (pasoDef == null) {
				throw new ErrorConfiguracionException("No existe paso con id: " + pIdPaso);
			}
			tipoPaso = TypePaso.fromString(pasoDef.getTipo());
			if (tipoPaso == null) {
				throw new TipoNoControladoException("Tipo de paso no controlado: " + pasoDef.getIdentificador());
			}

			// Control bucle infinito si es un paso de tipo accion que vuelve a si
			// mismo
			if (tipoPaso == TypePaso.ACCION && pIdPaso.equals(pDatosSesion.getDatosTramite().getIdPasoActual())) {
				throw new ErrorConfiguracionException("Un paso accion no puede volver sobre si mismo");
			}

			// Si simulamos acceso catalogo servicios no podemos ir a paso registrar
			if (tipoPaso == TypePaso.REGISTRAR && pDatosSesion.getDatosTramite().getDefinicionTramiteCP()
					.getIdentificador().equals(Constantes.TRAMITE_CATALOGO_SIMULADO_ID)) {
				throw new AccesoPasoRegistroCatalogoSimulado();
			}

		}

		// Obtenemos reglas del flujo
		final ReglasFlujo reglas = obtenerReglasFlujo(pDatosSesion.getDatosTramite().getTipoFlujo());

		// Generamos contexto ejecucion reglas
		final ContextoReglaTramitacion ctx = generarContextoReglaTramitacion(pDatosSesion);

		// Pasamos como variable a las reglas cual es el siguiente paso
		final Object[] vars = { pIdPaso };

		// Ejecutamos reglas pre (si no pasan las reglas nos quedamos en el paso
		// actual)
		if (ejecutarReglas(ctx, reglas, TypeFaseEjecucion.PRE_NAVEGACION, vars)) {

			// Establecemos paso como el actual
			modificacionesFlujo.establecerPasoActual(pDatosSesion, pIdPaso);

			// Establecemos el paso siguiente
			modificacionesFlujo.calcularSiguientePaso(pDatosSesion);

			// Actualizamos estado trámite (por si cambia estado del paso)
			modificacionesFlujo.actualizarEstadoTramite(pDatosSesion);

			// Ejecutamos reglas post
			if (!ejecutarReglas(ctx, reglas, TypeFaseEjecucion.POST_NAVEGACION, vars)) {
				throw new ErrorConfiguracionException("Error aplicando regla de tramitación al ir a paso");
			}

			// Si el paso es de tipo accion ejecutamos automaticamente la accion
			if (tipoPaso == TypePaso.ACCION) {
				this.accionPaso(pDatosSesion, pIdPaso, TypeAccionPasoAccion.EJECUTAR_ACCION, null);
			}
		}

		// Devolvemos paso actual
		final ResultadoIrAPaso res = new ResultadoIrAPaso();
		res.setIdSesionTramitacion(pDatosSesion.getDatosTramite().getIdSesionTramitacion());
		res.setIdPasoActual(pDatosSesion.getDatosTramite().getIdPasoActual());
		final TypeEstadoTramite estadoFinal = pDatosSesion.getDatosTramite().getEstadoTramite();
		if (estadoFinal == TypeEstadoTramite.FINALIZADO && estadoInicial != TypeEstadoTramite.FINALIZADO) {
			res.setFinalizadoTrasIrAPaso(true);
		}

		return res;
	}

	@Override
	public ResultadoAccionPaso accionPaso(final DatosSesionTramitacion pDatosSesion, final String idPaso,
			final TypeAccionPaso accionPaso, final ParametrosAccionPaso parametros) {

		debug(pDatosSesion, "Paso " + idPaso + " accion " + accionPaso.name());

		// Controlamos si paso existe y es el inicial
		controlAccesoPaso(pDatosSesion, idPaso);

		// Obtenemos estado inicial tramite
		final TypeEstadoTramite estadoInicial = pDatosSesion.getDatosTramite().getEstadoTramite();

		// Obtenemos reglas del flujo
		final ReglasFlujo reglas = obtenerReglasFlujo(pDatosSesion.getDatosTramite().getTipoFlujo());

		// Generamos contexto ejecucion reglas
		final ContextoReglaTramitacion ctx = generarContextoReglaTramitacion(pDatosSesion);

		final Object[] vars = { accionPaso };
		// Ejecutamos reglas pre
		if (!ejecutarReglas(ctx, reglas, TypeFaseEjecucion.PRE_ACCION, vars)) {
			throw new ErrorConfiguracionException("Error aplicando regla de tramitación al ejecutar acción: "
					+ accionPaso.name() + " en paso " + idPaso);
		}

		// Invocamos paso
		final RespuestaAccionPaso rp = modificacionesFlujo.invocarAccionPaso(pDatosSesion, idPaso, accionPaso,
				parametros);

		// Establecemos siguiente paso
		modificacionesFlujo.calcularSiguientePaso(pDatosSesion);

		// Ejecutamos reglas post
		if (!ejecutarReglas(ctx, reglas, TypeFaseEjecucion.POST_ACCION, vars)) {
			throw new ErrorConfiguracionException("Error aplicando regla de tramitación al ejecutar acción: "
					+ accionPaso.name() + " en paso " + idPaso);
		}

		// Pasar automaticamente al siguiente paso si:
		// - despues de ejecutar las reglas de post lo hemos establecido
		// - se ejecuta accion
		final boolean siguientePost = (ctx.isPasarSiguientePaso()
				&& pDatosSesion.getDatosTramite().getIdPasoSiguiente() != null);
		if (siguientePost || accionPaso == TypeAccionPasoAccion.EJECUTAR_ACCION) {
			debug(pDatosSesion, "Navegacion automatica a " + pDatosSesion.getDatosTramite().getIdPasoSiguiente());
			irAPaso(pDatosSesion, pDatosSesion.getDatosTramite().getIdPasoSiguiente());
		}

		// Actualizamos estado trámite (por si cambia estado del paso)
		modificacionesFlujo.actualizarEstadoTramite(pDatosSesion);

		// Comprobamos si el tramite ha finalizado tras ejecutar la accion
		final TypeEstadoTramite estadoFinal = pDatosSesion.getDatosTramite().getEstadoTramite();

		// Devolvemos resultado paso
		final ResultadoAccionPaso res = new ResultadoAccionPaso();
		res.setParametrosRetorno(rp.getParametrosRetorno());
		res.setFinalizadoTrasAccion(
				(estadoFinal == TypeEstadoTramite.FINALIZADO && estadoInicial != TypeEstadoTramite.FINALIZADO));
		return res;
	}

	@Override
	public void cancelarTramite(final DatosSesionTramitacion pDatosSesion) {

		debug(pDatosSesion, "Cancelar tramite");

		// No puede estar finalizado
		if (pDatosSesion.getDatosTramite().getEstadoTramite() == TypeEstadoTramite.FINALIZADO) {
			throw new CancelacionNoPermitidaException("Trámite ya ha finalizado");
		}

		// Obtenemos reglas del flujo
		final ReglasFlujo reglas = obtenerReglasFlujo(pDatosSesion.getDatosTramite().getTipoFlujo());

		// Generamos contexto ejecucion reglas
		final ContextoReglaTramitacion ctx = generarContextoReglaTramitacion(pDatosSesion);

		// Ejecutamos reglas pre (si falla una regla no dejamos cancelar)
		if (!ejecutarReglas(ctx, reglas, TypeFaseEjecucion.PRE_CANCELAR, null)) {
			throw new CancelacionNoPermitidaException(
					"Cancelación del trámite no permitida debido a las reglas de tramitación");
		}

		// Cancelamos tramite (marcamos como finalizado y cancelado)
		modificacionesFlujo.cancelarTramite(pDatosSesion);

	}

	@Override
	public DetalleTramite detalleTramite(final DatosSesionTramitacion pDatosSesion) {

		// Recupera info entidad
		final RConfiguracionEntidad entidadInfo = configuracionComponent
				.obtenerConfiguracionEntidad(pDatosSesion.getDefinicionTramite().getDefinicionVersion().getIdEntidad());

		// Establece detalle tramite
		final DetalleTramite detalleTramite = UtilsFlujo.detalleTramite(pDatosSesion, entidadInfo,
				configuracionComponent);

		return detalleTramite;
	}

	@Override
	public DetallePasos detallePasos(final DatosSesionTramitacion pDatosSesion) {

		// DetallePasos (ver crear funcion DetallePasos)
		final DetallePasos detallePasos = new DetallePasos();

		// - Estado tramite
		detallePasos.setEstado(pDatosSesion.getDatosTramite().getEstadoTramite());

		// - Lista de pasos
		final List<PasoLista> listaPasos = detalleTramiteCalcularPasosLista(pDatosSesion);
		detallePasos.setPasos(listaPasos);

		// - Paso actual
		final DetallePaso dtpa = modificacionesFlujo.obtenerDetallePaso(pDatosSesion,
				pDatosSesion.getDatosTramite().getIdPasoActual());
		detallePasos.setActual(dtpa);

		// - Paso anterior y siguiente (calculamos a partir de la lista de
		// pasos)
		final NavegacionPaso np = detalleTramiteCalcularNavegacionPaso(pDatosSesion);
		detallePasos.setAnterior(np.getIdPasoAnterior());
		detallePasos.setSiguiente(np.getIdPasoSiguiente());

		return detallePasos;

	}

	// ------------------------------------------------------------------------------------------------
	// METODOS PRIVADOS
	// ------------------------------------------------------------------------------------------------

	/**
	 * Ejecuta script de personalización al iniciar el trámite.
	 *
	 * Personaliza propiedades: título, activo y plazo. En caso de marcarse con
	 * error el script de personalización se generará una excepción
	 * AccesoNoPermitidoException.
	 *
	 *
	 * @param pDatosSesion
	 *                         Datos de sesión.
	 */
	private void personalizacionTramite(final DatosSesionTramitacion pDatosSesion) {

		// Ejecutamos script de personalizacion
		final RespuestaScript rs = modificacionesFlujo.ejecutarScriptPersonalizacion(pDatosSesion);

		// Si existe personalizacion personalizamos tramite
		if (rs != null) {
			final ResPersonalizacionTramite rp = (ResPersonalizacionTramite) rs.getResultado();
			modificacionesFlujo.personalizarTramite(pDatosSesion, rp);
		}
	}

	/**
	 * Realiza control de acceso al trámite: activo, plazos y filtro de certificados
	 * y grupos. En caso de no permitir el acceso se generará una excepción
	 * AccesoNoPermitidoException.
	 *
	 * @param pDatosSesion
	 *                         Datos de sesión de tramitación
	 */
	private void controlAcceso(final DatosSesionTramitacion pDatosSesion) {

		String mensaje = null;

		// Comprobamos nivel autenticacion tramite
		if (!controlTipoAutenticacion(pDatosSesion)) {
			mensaje = literales.getLiteral(Literales.FLUJO, "acceso.tipoAutenticacionNoPermitida",
					pDatosSesion.getDatosTramite().getIdioma());
			throw new AccesoNoPermitidoException(mensaje, generarDetallesExcepcionControlAcceso(pDatosSesion));
		}

		// Recuperamos propiedades control acceso al trámite
		final RVersionTramiteControlAcceso propsControlAcceso = pDatosSesion.getDefinicionTramite()
				.getDefinicionVersion().getControlAcceso();

		// Control activo
		if (!propsControlAcceso.isActivo()) {
			mensaje = literales.getLiteral(Literales.FLUJO, "acceso.desactivado",
					pDatosSesion.getDatosTramite().getIdioma());
			throw new AccesoNoPermitidoException(mensaje, generarDetallesExcepcionControlAcceso(pDatosSesion));
		}

		// Control plazos
		final Date ahora = new Date();
		controlPlazos(propsControlAcceso, ahora, pDatosSesion);

		// Control bloqueos (los avisos se gestionaran en proceso de login, como
		// mecanismo de seguridad volvemos a verificar que no haya ningun aviso
		// bloqueante)
		controlBloqueosAvisosPlataforma(pDatosSesion);

	}

	/**
	 * Control bloqueo por avisos.
	 *
	 * @param pDatosSesion
	 *                         Datos Sesion
	 */
	private void controlBloqueosAvisosPlataforma(final DatosSesionTramitacion pDatosSesion) {

		// Obtenemos avisos plataforma
		final RAvisosEntidad avisosPlataforma = configuracionComponent
				.obtenerAvisosEntidad(pDatosSesion.getDefinicionTramite().getDefinicionVersion().getIdEntidad());

		// Obtenemos avisos bloqueantes aplicables al tramite
		final List<AvisoPlataforma> avisos = UtilsSTG.obtenerAvisosTramite(pDatosSesion.getDefinicionTramite(),
				avisosPlataforma, pDatosSesion.getDatosTramite().getIdioma(), true);

		// En caso de haber algun aviso bloqueante, mostramos primer aviso
		if (!avisos.isEmpty()) {
			throw new AccesoNoPermitidoException(avisos.get(0).getMensaje(),
					generarDetallesExcepcionControlAcceso(pDatosSesion));
		}

	}

	/**
	 * Genera detalles excepcion para control de acceso ya que no se ha genera
	 * sesión.
	 *
	 * @param pDatosSesion
	 *                         Datos sesion
	 * @return detalles excepcion
	 */
	private ListaPropiedades generarDetallesExcepcionControlAcceso(final DatosSesionTramitacion pDatosSesion) {
		final ListaPropiedades detalles = new ListaPropiedades();
		detalles.addPropiedad("Tramite Id", pDatosSesion.getDatosTramite().getIdTramite());
		detalles.addPropiedad("Tramite Vers", Integer.toString(pDatosSesion.getDatosTramite().getVersionTramite()));
		return detalles;
	}

	/**
	 * Control de plazos.
	 *
	 * @param wa
	 *                         Definicion acceso
	 * @param ahora
	 *                         Fecha actual
	 * @param pDatosSesion
	 *                         Datos sesion
	 */
	protected void controlPlazos(final RVersionTramiteControlAcceso wa, final Date ahora,
			final DatosSesionTramitacion pDatosSesion) {

		String mensaje;

		// Control de plazos tramite
		if (!UtilsFlujo.estaDentroPlazo(ahora, pDatosSesion.getDatosTramite().getPlazoInicio(),
				pDatosSesion.getDatosTramite().getPlazoFin()) || !pDatosSesion.getDatosTramite().isVigente()) {
			mensaje = literales.getLiteral(Literales.FLUJO, "acceso.fueraPlazo",
					pDatosSesion.getDatosTramite().getIdioma());
			throw new AccesoNoPermitidoException(mensaje, generarDetallesExcepcionControlAcceso(pDatosSesion));
		}

		// Control caducidad tramite
		final Date fechaCaducidad = pDatosSesion.getDatosTramite().getFechaCaducidad();
		if (fechaCaducidad != null
				&& UtilsFlujo.obtenerUltimaHora(fechaCaducidad).before(UtilsFlujo.obtenerPrimeraHora(new Date()))) {
			throw new TramiteCaducadoException(pDatosSesion.getDatosTramite().getIdSesionTramitacion());
		}
	}

	/**
	 * Controla si el usuario entra con el nivel de autenticación requerido.
	 *
	 * @param pDatosSesion
	 *                         Datos sesión
	 * @return booleano
	 */
	private boolean controlTipoAutenticacion(final DatosSesionTramitacion pDatosSesion) {
		boolean permitido = true;
		final TypeAutenticacion nivelAutenticacion = pDatosSesion.getDatosTramite().getNivelAutenticacion();
		permitido = pDatosSesion.getDatosTramite().getMetodosLogin().contains(nivelAutenticacion);

		return permitido;
	}

	/**
	 * Controla si el paso existe y es el actual.
	 *
	 * @param pDatosSesion
	 *                         Datos sesion
	 * @param pIdPaso
	 *                         Id paso
	 */
	private void controlAccesoPaso(final DatosSesionTramitacion pDatosSesion, final String pIdPaso) {
		// Obtenemos datos paso
		final DatosPaso dp = pDatosSesion.getDatosTramite().getDatosPaso(pIdPaso);

		// Control acceso paso
		// - Si es el paso actual
		if (dp == null) {
			throw new AccionPasoNoPermitidaException("Paso " + pIdPaso + " no existe. El paso actual es: "
					+ pDatosSesion.getDatosTramite().getIdPasoActual());
		}
		if (!dp.getIdPaso().equals(pDatosSesion.getDatosTramite().getIdPasoActual())) {
			throw new AccionPasoNoPermitidaException("El paso " + pIdPaso + " no es el actual. El paso actual es: "
					+ pDatosSesion.getDatosTramite().getIdPasoActual());
		}
		// - El paso no debera estar no inicializado o para revisar
		if (dp.getEstado() == TypeEstadoPaso.NO_INICIALIZADO || dp.getEstado() == TypeEstadoPaso.REVISAR) {
			throw new AccionPasoNoPermitidaException(
					"El paso " + pIdPaso + " no esta inicializado o esta pendiente de revisar");
		}
	}

	/**
	 * Calcula la lista de pasos para mostrar en el front.
	 *
	 * @param pDatosSesion
	 *                         Datos de sesión de tramitación.
	 * @return Lista de pasos
	 */
	private List<PasoLista> detalleTramiteCalcularPasosLista(final DatosSesionTramitacion pDatosSesion) {
		final List<PasoLista> listaPasos = new ArrayList<>();
		for (final DatosPaso dp : pDatosSesion.getDatosTramite().getDatosPasos()) {
			// Calculamos accesibilidad del paso
			final boolean accesible = pDatosSesion.getDatosTramite().getAccesibilidadPaso(dp.getIdPaso());
			// Creamos paso lista y lo añadimos a la lista
			final PasoLista pl = PasoLista.createNewPasoLista();
			pl.setTipo(dp.getTipo());
			pl.setId(dp.getIdPaso());
			if (accesible) {
				pl.setAccesible(TypeSiNo.SI);
			}
			if (dp.getEstado() == TypeEstadoPaso.COMPLETADO) {
				pl.setCompletado(TypeSiNo.SI);
			} else {
				pl.setCompletado(TypeSiNo.NO);
			}
			listaPasos.add(pl);
		}
		return listaPasos;
	}

	/**
	 * Calcula cuales son los ids del paso anterior y siguiente.
	 *
	 * @param pDatosSesion
	 *                         Datos sesion
	 * @return Navegación permitida entre los pasos
	 */
	private NavegacionPaso detalleTramiteCalcularNavegacionPaso(final DatosSesionTramitacion pDatosSesion) {
		final NavegacionPaso np = new NavegacionPaso();

		final int indicePasoActual = pDatosSesion.getDatosTramite().getIndiceDatosPasoActual();
		final DatosPaso datosPasoActual = pDatosSesion.getDatosTramite().getDatosPasoActual();

		// Paso anterior
		if (indicePasoActual > 0) {
			final DatosPaso datosPasoAnterior = pDatosSesion.getDatosTramite().getDatosPasos()
					.get(indicePasoActual - ConstantesNumero.N1);
			if (pDatosSesion.getDatosTramite().getAccesibilidadPaso(datosPasoAnterior.getIdPaso())) {
				np.setIdPasoAnterior(datosPasoAnterior.getIdPaso());
			}
		}

		// Paso siguiente
		if (datosPasoActual.getEstado() == TypeEstadoPaso.COMPLETADO && !datosPasoActual.isPasoFinal()) {
			final String idPasoSiguiente = pDatosSesion.getDatosTramite().getIdPasoSiguiente();
			// Si el paso siguiente es igual al anterior dejamos solo
			// anterior
			if (!idPasoSiguiente.equals(np.getIdPasoAnterior())) {
				np.setIdPasoSiguiente(idPasoSiguiente);
			}
		}

		return np;
	}

	/**
	 * Obtiene reglas para el flujo.
	 *
	 * @param tipoFlujo
	 *                      Tipo de flujo
	 * @return Reglas flujo
	 */
	private ReglasFlujo obtenerReglasFlujo(final TypeFlujoTramitacion tipoFlujo) {

		ReglasFlujo reglasFlujo = null;
		switch (tipoFlujo) {
		case NORMALIZADO:
			reglasFlujo = (ReglasFlujo) ApplicationContextProvider.getApplicationContext()
					.getBean("reglasFlujoNormalizado");
			break;
		case PERSONALIZADO:
			reglasFlujo = (ReglasFlujo) ApplicationContextProvider.getApplicationContext()
					.getBean("reglasFlujoPersonalizado");
			break;
		default:
			throw new TipoNoControladoException("Tipo de flujo " + tipoFlujo.name() + " no controlado");
		}

		return reglasFlujo;
	}

	/**
	 * Genera contexto para regla de tramitacion.
	 *
	 * @param pDatosSesion
	 *                         Datos sesion tramitacion
	 * @return Contexto regla tramitacion
	 */
	private ContextoReglaTramitacion generarContextoReglaTramitacion(final DatosSesionTramitacion pDatosSesion) {
		final ContextoReglaTramitacion ctx = new ContextoReglaTramitacion(pDatosSesion);
		return ctx;
	}

	/**
	 * Ejecuta reglas de tramitación para una fase.
	 *
	 * @param ctx
	 *                   Contexto ejecucion reglas
	 * @param reglas
	 *                   Reglas de tramitación
	 * @param fase
	 *                   Fase de ejecución
	 * @param vars
	 *                   Variables fase de ejecución
	 *
	 * @return indica si se han ejecutado correctamente las reglas
	 */
	private boolean ejecutarReglas(final ContextoReglaTramitacion ctx, final ReglasFlujo reglas,
			final TypeFaseEjecucion fase, final Object[] vars) {
		boolean res = true;
		final List<ReglaTramitacion> reglasPre = reglas.getReglasTramitacion(fase);
		if (reglasPre != null) {
			for (final ReglaTramitacion rt : reglasPre) {
				if (!rt.execute(ctx, vars)) {
					res = false;
					break;
				}
			}
		}
		return res;
	}

	/**
	 * Realiza debug.
	 *
	 * @param pDatosSesion
	 *                         Datos sesion tramitacion
	 * @param message
	 *                         Mensaje
	 */
	private void debug(final DatosSesionTramitacion pDatosSesion, final String message) {
		if (UtilsSTG.isDebugEnabled(pDatosSesion.getDefinicionTramite()) && LOGGER.isDebugEnabled()) {
			LOGGER.debug(pDatosSesion.getDatosTramite().getIdSesionTramitacion() + " - " + message);
		}
	}

}
