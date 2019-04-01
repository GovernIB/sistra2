package es.caib.sistramit.core.service.component.flujo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistrages.rest.api.interna.RPasoTramitacion;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistrages.rest.api.interna.RVersionTramitePropiedades;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ErrorScriptException;
import es.caib.sistramit.core.api.exception.TimestampFlujoException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.flujo.DetallePaso;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.service.component.flujo.pasos.ControladorPaso;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResParametrosIniciales;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResPersonalizacionTramite;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;
import es.caib.sistramit.core.service.model.flujo.DatosSesionTramitacion;
import es.caib.sistramit.core.service.model.flujo.EstadoPersistenciaPasoTramite;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFlujo;
import es.caib.sistramit.core.service.repository.dao.FlujoTramiteDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Modificaciones del flujo. A través de este objeto se modificarán los datos de
 * sesión.
 *
 * @author Indra
 *
 */
@Component("modificacionesFlujo")
public final class ModificacionesFlujoImpl implements ModificacionesFlujo {

	/** Dao para acceso a bbdd. */
	@Autowired
	private FlujoTramiteDao dao;

	/**
	 * Motor de ejecución de scritps.
	 */
	@Autowired
	private ScriptExec scriptFlujo;

	/** Atributo constante logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ModificacionesFlujoImpl.class);

	/**
	 * Controlador paso Debe Saber.
	 */
	@Resource(name = "controladorPasoDebeSaber")
	private ControladorPaso controladorPasoDebeSaber;

	/**
	 * Controlador paso Rellenar.
	 */
	@Resource(name = "controladorPasoRellenar")
	private ControladorPaso controladorPasoRellenar;

	/**
	 * Controlador paso Anexar.
	 */
	@Resource(name = "controladorPasoAnexar")
	private ControladorPaso controladorPasoAnexar;

	/**
	 * Controlador paso Pagar.
	 */
	@Resource(name = "controladorPasoPagar")
	private ControladorPaso controladorPasoPagar;

	/**
	 * Controlador paso Registrar.
	 */
	@Resource(name = "controladorPasoRegistrar")
	private ControladorPaso controladorPasoRegistrar;

	/**
	 * Controlador paso Guardar.
	 */
	@Resource(name = "controladorPasoGuardar")
	private ControladorPaso controladorPasoGuardar;

	/**
	 * Controlador paso Captura.
	 */
	@Resource(name = "controladorPasoCapturar")
	private ControladorPaso controladorPasoCapturar;

	/**
	 * Controlador paso Información.
	 */
	@Resource(name = "controladorPasoInformacion")
	private ControladorPaso controladorPasoInformacion;

	/**
	 * Controlador paso Acción.
	 */
	@Resource(name = "controladorPasoAccion")
	private ControladorPaso controladorPasoAccion;

	@Override
	public VariablesFlujo generarVariablesFlujo(final DatosSesionTramitacion pDatosSesion, final String idPaso) {

		final VariablesFlujo vf = new VariablesFlujo();

		// Copiamos info de datos sesion
		vf.setIdTramite(pDatosSesion.getDefinicionTramite().getDefinicionVersion().getIdentificador());
		vf.setVersionTramite(pDatosSesion.getDefinicionTramite().getDefinicionVersion().getVersion());
		vf.setIdSesionTramitacion(pDatosSesion.getDatosTramite().getIdSesionTramitacion());
		vf.setUrlInicioTramite(pDatosSesion.getDatosTramite().getUrlInicio());
		vf.setTituloTramite(pDatosSesion.getDatosTramite().getTituloTramite());
		vf.setFechaInicioPlazo(pDatosSesion.getDatosTramite().getPlazoInicio());
		vf.setFechaFinPlazo(pDatosSesion.getDatosTramite().getPlazoFin());
		vf.setIdioma(pDatosSesion.getDatosTramite().getIdioma());
		vf.setEntorno(pDatosSesion.getDatosTramite().getEntorno());
		vf.setDebugEnabled(UtilsSTG.isDebugEnabled(pDatosSesion.getDefinicionTramite()));
		vf.setDatosTramiteCP(pDatosSesion.getDatosTramite().getDefinicionTramiteCP());

		vf.setMetodoAutenticacion(pDatosSesion.getDatosTramite().getMetodoAutenticacionInicio());
		vf.setNivelAutenticacion(pDatosSesion.getDatosTramite().getNivelAutenticacion());

		DatosUsuario du = null;
		if (pDatosSesion.getDatosTramite().getNivelAutenticacion() == TypeAutenticacion.AUTENTICADO) {
			du = new DatosUsuario();
			du.setNif(pDatosSesion.getDatosTramite().getIniciador().getNif());
			du.setNombre(pDatosSesion.getDatosTramite().getIniciador().getNombre());
			du.setApellido1(pDatosSesion.getDatosTramite().getIniciador().getApellido1());
			du.setApellido2(pDatosSesion.getDatosTramite().getIniciador().getApellido2());
			du.setCorreo(pDatosSesion.getDatosTramite().getIniciador().getCorreo());
		}

		vf.setUsuario(du);
		vf.setUsuarioAutenticado(pDatosSesion.getDatosTramite().getUsuarioAutenticado());
		vf.setParametrosInicio(pDatosSesion.getDatosTramite().getParametrosInicio());

		// Se podrá acceder a los docs completados de los pasos anteriores que
		// esten completados
		if (idPaso != null) {
			// Recorremos pasos anteriores y recogemos documentos
			vf.setDocumentos(new ArrayList<DatosDocumento>());
			for (final DatosPaso dp : pDatosSesion.getDatosTramite().getDatosPasos()) {
				// Comprobamos si ya hemos llegado al paso
				if (idPaso.equals(dp.getIdPaso())) {
					break;
				} else {
					// Si es un paso anterior y esta completado recogemos su
					// docs completados
					if (dp.getEstado() == TypeEstadoPaso.COMPLETADO) {
						final ControladorPaso cp = getControladorPaso(pDatosSesion, dp.getIdPaso());
						final List<DatosDocumento> docsPaso = cp.obtenerDocumentosPaso(dp,
								pDatosSesion.getDefinicionTramite());
						vf.getDocumentos().addAll(docsPaso);
					}
				}
			}
		}

		return vf;
	}

	@Override
	public void actualizarEstadoTramite(final DatosSesionTramitacion datosSesion) {

		// Buscamos ultimo paso que se ha intentado completar
		DatosPaso dpUltimo = null;
		for (final DatosPaso dp : datosSesion.getDatosTramite().getDatosPasos()) {
			if (dp.getEstado() == TypeEstadoPaso.COMPLETADO) {
				dpUltimo = dp;
			}
		}

		// Evaluamos si debemos modificar el estado del tramite en funcion del
		// estado del paso
		TypeEstadoTramite estadoNuevo = TypeEstadoTramite.RELLENANDO;
		if (dpUltimo != null && dpUltimo.getEstado() == TypeEstadoPaso.COMPLETADO && dpUltimo.isPasoFinal()) {
			estadoNuevo = TypeEstadoTramite.FINALIZADO;
		}

		// Si hay cambio de estado actualizamos
		if (datosSesion.getDatosTramite().getEstadoTramite() != estadoNuevo
				&& datosSesion.getDatosTramite().getEstadoTramite() != TypeEstadoTramite.FINALIZADO) {
			datosSesion.getDatosTramite().setEstadoTramite(estadoNuevo);
			dao.cambiaEstadoTramite(datosSesion.getDatosTramite().getIdSesionTramitacion(), estadoNuevo);
		}
	}

	@Override
	public void personalizarTramite(final DatosSesionTramitacion pDatosSesion, final ResPersonalizacionTramite rp) {
		// Personalizacion titulo tramite
		if (StringUtils.isNotBlank(rp.getTituloTramite())) {
			pDatosSesion.getDatosTramite().setTituloTramite(rp.getTituloTramite());
		}

		// Personalizacion plazo
		final Date fcInicio = UtilsFlujo.deformateaFecha(rp.getPlazoInicio(),
				ValidacionesTipo.FORMATO_FECHA_INTERNACIONAL);
		final Date fcFin = UtilsFlujo.deformateaFecha(rp.getPlazoFin(), ValidacionesTipo.FORMATO_FECHA_INTERNACIONAL);

		if (fcInicio != null) {
			pDatosSesion.getDatosTramite().setPlazoInicio(fcInicio);
			pDatosSesion.getDatosTramite().setPlazoDinamico(true);
		}
		if (fcFin != null) {
			pDatosSesion.getDatosTramite().setPlazoFin(fcFin);
			pDatosSesion.getDatosTramite().setPlazoDinamico(true);
		}
	}

	@Override
	public RespuestaAccionPaso invocarAccionPaso(final DatosSesionTramitacion pDatosSesion, final String pIdPaso,
			final TypeAccionPaso pAccionPaso, final ParametrosAccionPaso pParametros) {

		// Verificamos timestamp flujo
		if (!dao.verificaTimestampFlujo(pDatosSesion.getDatosTramite().getIdSesionTramitacion(),
				pDatosSesion.getDatosTramite().getTimestampFlujo())) {
			throw new TimestampFlujoException();
		}

		// Obtenemos datos paso
		final DatosPaso dp = pDatosSesion.getDatosTramite().getDatosPaso(pIdPaso);
		// Generamos variables flujo accesibles desde el paso
		final VariablesFlujo variablesFlujo = this.generarVariablesFlujo(pDatosSesion, pIdPaso);
		final ControladorPaso cp = this.getControladorPaso(pDatosSesion, pIdPaso);
		final RespuestaAccionPaso res = cp.accionPaso(dp, pAccionPaso, pParametros, pDatosSesion.getDefinicionTramite(),
				variablesFlujo);
		return res;
	}

	@Override
	public void crearTramitePersistencia(final DatosSesionTramitacion pDatosSesion) {
		// Establecemos datos tramite en persistencia
		final DatosPersistenciaTramite dpdt = new DatosPersistenciaTramite();
		dpdt.setParametrosInicio(pDatosSesion.getDatosTramite().getParametrosInicio());
		dpdt.setIdSesionTramitacion(pDatosSesion.getDatosTramite().getIdSesionTramitacion());
		dpdt.setEstado(pDatosSesion.getDatosTramite().getEstadoTramite());
		dpdt.setDescripcionTramite(pDatosSesion.getDatosTramite().getTituloTramite());
		dpdt.setIdioma(pDatosSesion.getDatosTramite().getIdioma());
		dpdt.setVersionTramite(pDatosSesion.getDatosTramite().getVersionTramite());
		dpdt.setIdTramite(pDatosSesion.getDatosTramite().getIdTramite());
		dpdt.setIdArea(pDatosSesion.getDefinicionTramite().getDefinicionVersion().getIdArea());
		dpdt.setIdTramiteCP(pDatosSesion.getDatosTramite().getDefinicionTramiteCP().getIdentificador());
		dpdt.setIdProcedimientoCP(pDatosSesion.getDatosTramite().getDefinicionTramiteCP().getIdentificador());
		dpdt.setServicioCP(pDatosSesion.getDatosTramite().getDefinicionTramiteCP().getProcedimiento().isServicio());
		dpdt.setIdProcedimientoSIA(
				pDatosSesion.getDatosTramite().getDefinicionTramiteCP().getProcedimiento().getIdProcedimientoSIA());
		dpdt.setAutenticacion(pDatosSesion.getDatosTramite().getNivelAutenticacion());
		dpdt.setMetodoAutenticacionInicio(pDatosSesion.getDatosTramite().getMetodoAutenticacionInicio());
		if (pDatosSesion.getDatosTramite().getIniciador() != null) {
			dpdt.setNifIniciador(pDatosSesion.getDatosTramite().getIniciador().getNif());
			dpdt.setNombreIniciador(pDatosSesion.getDatosTramite().getIniciador().getNombre());
			dpdt.setApellido1Iniciador(pDatosSesion.getDatosTramite().getIniciador().getApellido1());
			dpdt.setApellido2Iniciador(pDatosSesion.getDatosTramite().getIniciador().getApellido2());
		}
		dpdt.setTimestamp(new Date().getTime());
		dpdt.setFechaInicio(new Date());
		dpdt.setPersistente(
				pDatosSesion.getDefinicionTramite().getDefinicionVersion().getPropiedades().isPersistente());
		dpdt.setPlazoDinamico(pDatosSesion.getDatosTramite().isPlazoDinamico());
		dpdt.setUrlInicio(pDatosSesion.getDatosTramite().getUrlInicio());
		dao.crearTramitePersistencia(dpdt);

		// Indicamos que el tramite es nuevo
		pDatosSesion.getDatosTramite().setNuevo(true);
	}

	@Override
	public void addPasoTramitacion(final DatosSesionTramitacion pDatosSesion, final String idPaso,
			final TypePaso tipo) {
		final DatosPaso dp = DatosPaso.createNewDatosPaso(pDatosSesion.getDatosTramite().getIdSesionTramitacion(),
				idPaso, tipo);
		pDatosSesion.getDatosTramite().getDatosPasos().add(dp);
		dao.crearPaso(pDatosSesion.getDatosTramite().getIdSesionTramitacion(), dp.getIdPaso(), dp.getTipo(),
				pDatosSesion.getDatosTramite().getDatosPasos().size());
	}

	@Override
	public void removeUltimoPasoTramitacion(final DatosSesionTramitacion pDatosSesion) {
		final DatosPaso dp = pDatosSesion.getDatosTramite().getDatosPasoUltimo();
		pDatosSesion.getDatosTramite().removeDatosPaso(dp.getIdPaso());
		dao.eliminarPaso(pDatosSesion.getDatosTramite().getIdSesionTramitacion(), dp.getIdPaso());
	}

	@Override
	public void invalidarPasoTramitacion(final DatosSesionTramitacion pDatosSesion, final String idPaso) {
		final DatosPaso dp = pDatosSesion.getDatosTramite().getDatosPaso(idPaso);
		final ControladorPaso cp = this.getControladorPaso(pDatosSesion, idPaso);
		final VariablesFlujo variablesFlujo = this.generarVariablesFlujo(pDatosSesion, idPaso);
		cp.invalidarPaso(dp, pDatosSesion.getDefinicionTramite(), variablesFlujo);
	}

	@Override
	public void establecerAccesibilidadPaso(final DatosSesionTramitacion pDatosSesion, final String idPaso,
			final boolean accesible) {
		pDatosSesion.getDatosTramite().setAccesibilidadPaso(idPaso, accesible);
	}

	@Override
	public void establecerSoloLecturaPaso(final DatosSesionTramitacion pDatosSesion, final String idPaso,
			final boolean soloLectura) {
		final DatosPaso dp = pDatosSesion.getDatosTramite().getDatosPaso(idPaso);
		final ControladorPaso cp = this.getControladorPaso(pDatosSesion, idPaso);
		cp.establecerSoloLectura(dp, pDatosSesion.getDefinicionTramite(), soloLectura);
	}

	@Override
	public void cargarPasosPersistencia(final DatosSesionTramitacion pDatosSesion) {
		// Obtiene lista de pasos de persistencia
		final List<EstadoPersistenciaPasoTramite> pasosPersist = dao
				.obtenerListaPasos(pDatosSesion.getDatosTramite().getIdSesionTramitacion());
		String idPasoActual = null;
		for (final EstadoPersistenciaPasoTramite ept : pasosPersist) {
			// Creamos datos del paso
			pDatosSesion.getDatosTramite().getDatosPasos().add(DatosPaso.createNewDatosPaso(
					pDatosSesion.getDatosTramite().getIdSesionTramitacion(), ept.getId(), ept.getTipo()));
			// Comprobamos si esta pendiente o completado
			if (ept.getEstado() != TypeEstadoPaso.NO_INICIALIZADO && ept.getEstado() != TypeEstadoPaso.REVISAR) {
				idPasoActual = ept.getId();
			}
		}
		if (idPasoActual == null) {
			if (pasosPersist.isEmpty()) {
				throw new ErrorConfiguracionException("No se ha recuperado ningun paso de persistencia");
			}
			idPasoActual = pasosPersist.get(0).getId();
		}
		pDatosSesion.getDatosTramite().setIdPasoActual(idPasoActual);

		// Realizamos carga de cada paso
		for (final DatosPaso dp : pDatosSesion.getDatosTramite().getDatosPasos()) {
			// Cargamos paso
			final VariablesFlujo variablesFlujo = this.generarVariablesFlujo(pDatosSesion, dp.getIdPaso());
			this.getControladorPaso(pDatosSesion, dp.getIdPaso()).cargarPaso(dp, pDatosSesion.getDefinicionTramite(),
					variablesFlujo);
		}

	}

	@Override
	public void registrarAccesoTramite(final DatosSesionTramitacion pDatosSesion) {
		// Calculamos fecha de caducidad
		Date fechaCaducidad = null;

		// Obtenemos los días de persistencia de la definición del trámite
		final RVersionTramitePropiedades propiedades = pDatosSesion.getDefinicionTramite().getDefinicionVersion()
				.getPropiedades();
		int diasPersistencia = 0;
		if (propiedades.isPersistente()) {
			diasPersistencia = propiedades.getDiasPersistencia();
		}
		// Si tiene dias de persistencia, se calcula teniendo en cuenta el fin
		// de plazo
		if (diasPersistencia > 0) {
			final Calendar cal = Calendar.getInstance();
			cal.setTime(UtilsFlujo.obtenerUltimaHora(new Date()));
			if (pDatosSesion.getDatosTramite().getPlazoFin() != null) {
				cal.add(Calendar.DATE, diasPersistencia);
				if (cal.getTime().after(pDatosSesion.getDatosTramite().getPlazoFin())) {
					fechaCaducidad = pDatosSesion.getDatosTramite().getPlazoFin();
				} else {
					fechaCaducidad = cal.getTime();
				}
			} else {
				cal.add(Calendar.DATE, diasPersistencia);
				fechaCaducidad = cal.getTime();
			}
		} else {
			// Si no tiene días de persistencia, se pone fecha fin plazo
			fechaCaducidad = pDatosSesion.getDatosTramite().getPlazoFin();
		}

		// Actualizamos datos sesion
		pDatosSesion.getDatosTramite().setFechaCaducidad(fechaCaducidad);

		// Registramos acceso
		final Date timestampFlujo = dao.registraAccesoTramite(pDatosSesion.getDatosTramite().getIdSesionTramitacion(),
				fechaCaducidad);

		// Establecemos timestamp flujo
		pDatosSesion.getDatosTramite().setTimestampFlujo(timestampFlujo);

	}

	@Override
	public boolean establecerPasoActual(final DatosSesionTramitacion pDatosSesion, final String pIdPaso) {

		// Si no hay ningun paso inicial implica que se esta iniciando o
		// cargando el tramite por lo que todavia no se habra establecido la
		// accesibilidad de los pasos
		if (pDatosSesion.getDatosTramite().getIdPasoActual() == null
				|| pDatosSesion.getDatosTramite().getAccesibilidadPaso(pIdPaso)) {
			// Verificamos si hay que inicializar o revisar el paso
			final DatosPaso dpDest = pDatosSesion.getDatosTramite().getDatosPaso(pIdPaso);
			if (dpDest == null) {
				throw new AccionPasoNoPermitidaException("No existen datos para paso " + pIdPaso);
			}
			if (dpDest.getEstado() == TypeEstadoPaso.NO_INICIALIZADO || dpDest.getEstado() == TypeEstadoPaso.REVISAR) {
				// Preparamos plugins paso
				final VariablesFlujo variablesPaso = this.generarVariablesFlujo(pDatosSesion, pIdPaso);
				// Inicializamos o revisamos paso
				if (dpDest.getEstado() == TypeEstadoPaso.NO_INICIALIZADO) {
					this.getControladorPaso(pDatosSesion, pIdPaso).inicializarPaso(dpDest,
							pDatosSesion.getDefinicionTramite(), variablesPaso);
				} else {
					this.getControladorPaso(pDatosSesion, pIdPaso).revisarPaso(dpDest,
							pDatosSesion.getDefinicionTramite(), variablesPaso);
				}
			}
			// Establecemos paso destino como el actual
			pDatosSesion.getDatosTramite().setIdPasoActual(pIdPaso);
		}

		// Devolvemos si se ha podido establecer el paso como actual
		return (pDatosSesion.getDatosTramite().getIdPasoActual().equals(pIdPaso));
	}

	@Override
	public DetallePaso obtenerDetallePaso(final DatosSesionTramitacion pDatosSesion, final String pIdPaso) {
		final DatosPaso datosPaso = pDatosSesion.getDatosTramite().getDatosPaso(pIdPaso);
		if (datosPaso == null) {
			throw new AccionPasoNoPermitidaException("No existen datos del paso " + pIdPaso);
		}
		final ControladorPaso cpa = this.getControladorPaso(pDatosSesion, pIdPaso);
		final DetallePaso dtpa = cpa.detallePaso(datosPaso, pDatosSesion.getDefinicionTramite());
		return dtpa;
	}

	@Override
	public void calcularSiguientePaso(final DatosSesionTramitacion pDatosSesion) {

		// Obtenemos paso actual
		final DatosPaso datosPasoActual = pDatosSesion.getDatosTramite().getDatosPasoActual();
		final String idPasoActual = datosPasoActual.getIdPaso();

		// Si el paso actual esta completado calculamos el siguiente paso, sino
		// no habra siguiente paso
		String idPasoSiguiente = null;
		if (datosPasoActual.getEstado() == TypeEstadoPaso.COMPLETADO) {

			// Obtenemos definición del paso
			final RPasoTramitacion defPasoActual = UtilsSTG.devuelveDefinicionPaso(idPasoActual,
					pDatosSesion.getDefinicionTramite());

			// Ejecutamos script de navegacion
			idPasoSiguiente = ejecutarScriptNavegacion(pDatosSesion, datosPasoActual.getIdPaso(), defPasoActual);

			// Si no se ha establecido idpaso el siguiente paso sera el
			// siguiente en la definición
			if (idPasoSiguiente == null && !idPasoActual.equals(ConstantesFlujo.ID_PASO_GUARDAR)) {
				// Si el paso actual es registrar pasamos a paso virtual de
				// guardar
				final TypePaso tipoPasoActual = TypePaso.fromString(defPasoActual.getTipo());
				if (tipoPasoActual == TypePaso.REGISTRAR) {
					idPasoSiguiente = ConstantesFlujo.ID_PASO_GUARDAR;
				} else if (!defPasoActual.isPasoFinal()) {
					// Si no es paso final, el siguiente paso sera el
					// siguiente en la definicion
					final int indicePasoActual = UtilsSTG.devuelveIndicePaso(idPasoActual,
							pDatosSesion.getDefinicionTramite());
					if (indicePasoActual == ConstantesNumero.N_1) {
						throw new ErrorConfiguracionException("No se encuentra paso " + idPasoActual);
					}

					final int indiceSiguiente = indicePasoActual + ConstantesNumero.N1;

					if (indiceSiguiente >= pDatosSesion.getDefinicionTramite().getDefinicionVersion().getPasos()
							.size()) {
						throw new ErrorConfiguracionException("El paso " + idPasoActual
								+ " es el último, no es final y no tiene configurado un paso siguiente en el script de navegacion");
					}
					idPasoSiguiente = pDatosSesion.getDefinicionTramite().getDefinicionVersion().getPasos()
							.get(indiceSiguiente).getIdentificador();
				}

			}
		}

		// Establecemos paso siguiente
		pDatosSesion.getDatosTramite().setIdPasoSiguiente(idPasoSiguiente);

		debug(pDatosSesion, "Calculo siguiente paso: " + idPasoSiguiente);
	}

	@Override
	public RespuestaScript ejecutarScriptPersonalizacion(final DatosSesionTramitacion pDatosSesion) {
		RespuestaScript rs = null;
		final RVersionTramitePropiedades propiedadesTramite = pDatosSesion.getDefinicionTramite().getDefinicionVersion()
				.getPropiedades();
		if (UtilsSTG.existeScript(propiedadesTramite.getScriptPersonalizacion())) {
			final String script = propiedadesTramite.getScriptPersonalizacion().getScript();
			final Map<String, String> codigosError = UtilsSTG
					.convertLiteralesToMap(propiedadesTramite.getScriptPersonalizacion().getLiterales());

			final VariablesFlujo variablesFlujo = this.generarVariablesFlujo(pDatosSesion, null);

			rs = this.scriptFlujo.executeScriptFlujo(TypeScriptFlujo.SCRIPT_PERSONALIZACION_TRAMITE, null, script,
					variablesFlujo, null, null, codigosError, pDatosSesion.getDefinicionTramite());
		}
		return rs;
	}

	@Override
	public void calcularParametrosInicialesTramite(final DatosSesionTramitacion pDatosSesion) {
		RespuestaScript rs = null;
		final RVersionTramitePropiedades propiedadesTramite = pDatosSesion.getDefinicionTramite().getDefinicionVersion()
				.getPropiedades();
		if (UtilsSTG.existeScript(propiedadesTramite.getScriptParametrosIniciales())) {
			final RScript scriptParam = propiedadesTramite.getScriptParametrosIniciales();
			final String script = scriptParam.getScript();
			final Map<String, String> codigosError = UtilsSTG.convertLiteralesToMap(scriptParam.getLiterales());

			final VariablesFlujo variablesFlujo = this.generarVariablesFlujo(pDatosSesion, null);

			rs = this.scriptFlujo.executeScriptFlujo(TypeScriptFlujo.SCRIPT_PARAMETROS_INICIALES, null, script,
					variablesFlujo, null, null, codigosError, pDatosSesion.getDefinicionTramite());

			// Si se ha añadido parametros mediante el script los añadimos a la sesion
			final ResParametrosIniciales rp = (ResParametrosIniciales) rs.getResultado();
			for (final String codParam : rp.getParametros().keySet()) {
				pDatosSesion.getDatosTramite().getParametrosInicio().put(codParam, rp.getParametros().get(codParam));
			}

		}
	}

	@Override
	public void cancelarTramite(final DatosSesionTramitacion pDatosSesion) {
		// Marcamos el tramite como finalizado y cancelado en persistencia
		dao.cancelarTramite(pDatosSesion.getDatosTramite().getIdSesionTramitacion());
		// Actualizamos datos de sesion
		pDatosSesion.getDatosTramite().setEstadoTramite(TypeEstadoTramite.FINALIZADO);
	}

	@Override
	public void calcularMetodosLogin(final DatosSesionTramitacion pDatosSesion) {

		final List<TypeAutenticacion> metodos = new ArrayList<>();

		final RVersionTramitePropiedades propiedadesTramite = pDatosSesion.getDefinicionTramite().getDefinicionVersion()
				.getPropiedades();

		// Calculamos metodos definidos en el tramite
		if (propiedadesTramite.isNoAutenticado()) {
			metodos.add(TypeAutenticacion.ANONIMO);
		}
		if (propiedadesTramite.isAutenticado()) {
			metodos.add(TypeAutenticacion.AUTENTICADO);
		}

		// Establecemos metodos login en la sesion
		pDatosSesion.getDatosTramite().setMetodosLogin(metodos);

	}

	// -----------------------------------------------------------------------------------
	// FUNCIONES PRIVADAS
	// -----------------------------------------------------------------------------------

	/**
	 * Obtiene el controlador para el paso.
	 *
	 * @param pDatosSesion
	 *            Datos de sesión de tramitación.
	 * @param idPaso
	 *            Identificador de paso
	 * @return Controlador del paso
	 */
	private ControladorPaso getControladorPaso(final DatosSesionTramitacion pDatosSesion, final String idPaso) {
		ControladorPaso cp = null;
		for (final DatosPaso dp : pDatosSesion.getDatosTramite().getDatosPasos()) {
			if (idPaso.equals(dp.getIdPaso())) {
				switch (dp.getTipo()) {
				case DEBESABER:
					cp = controladorPasoDebeSaber;
					break;
				case RELLENAR:
					cp = controladorPasoRellenar;
					break;
				case ANEXAR:
					cp = controladorPasoAnexar;
					break;
				case CAPTURAR:
					cp = controladorPasoCapturar;
					break;
				case INFORMACION:
					cp = controladorPasoInformacion;
					break;
				case PAGAR:
					cp = controladorPasoPagar;
					break;
				case REGISTRAR:
					cp = controladorPasoRegistrar;
					break;
				case GUARDAR:
					cp = controladorPasoGuardar;
					break;
				case ACCION:
					cp = controladorPasoAccion;
					break;
				default:
					throw new TipoNoControladoException("Tipo de paso no controlado: " + dp.getTipo().name());
				}
			}
		}

		if (cp == null) {
			throw new TipoNoControladoException("Tipo de paso no controlado para paso con id: " + idPaso);
		}

		return cp;
	}

	/**
	 * Ejecuta script navegacion.
	 *
	 * @param pDatosSesion
	 *            Datos sesion
	 * @param pIdPaso
	 *            Id paso
	 * @param defPasoActual
	 *            Definicion paso actual
	 * @return Id siguiente paso
	 */
	private String ejecutarScriptNavegacion(final DatosSesionTramitacion pDatosSesion, final String pIdPaso,
			final RPasoTramitacion defPasoActual) {
		String idPasoSiguiente = null;
		// Ejecutamos script de navegacion si procede
		if (!pIdPaso.equals(ConstantesFlujo.ID_PASO_GUARDAR) && !defPasoActual.isPasoFinal()
				&& UtilsSTG.existeScript(defPasoActual.getScriptNavegacion())) {

			debug(pDatosSesion, "Ejecucion script de navegacion paso " + pIdPaso);

			final String script = defPasoActual.getScriptNavegacion().getScript();
			final Map<String, String> codigosError = UtilsSTG
					.convertLiteralesToMap(defPasoActual.getScriptNavegacion().getLiterales());

			final DatosPaso dp = pDatosSesion.getDatosTramite().getDatosPaso(pIdPaso);

			final VariablesFlujo variablesFlujo = this.generarVariablesFlujo(pDatosSesion, pIdPaso);

			// Si es un paso captura, rellenar o accion tendra acceso a los docs
			// completados del paso.
			List<DatosDocumento> documentosPaso = null;
			if (dp.getTipo() == TypePaso.CAPTURAR || dp.getTipo() == TypePaso.RELLENAR
					|| dp.getTipo() == TypePaso.ACCION) {
				documentosPaso = this.getControladorPaso(pDatosSesion, pIdPaso).obtenerDocumentosPaso(dp,
						pDatosSesion.getDefinicionTramite());
			}

			final RespuestaScript rs = this.scriptFlujo.executeScriptFlujo(TypeScriptFlujo.SCRIPT_NAVEGACION_PASO,
					pIdPaso, script, variablesFlujo, null, documentosPaso, codigosError,
					pDatosSesion.getDefinicionTramite());

			idPasoSiguiente = (String) rs.getResultado();
			if (StringUtils.isBlank(idPasoSiguiente)) {
				// Si el script no devuelve nada
				idPasoSiguiente = null;
			} else {
				// Comprobamos que existe el paso siguiente en la definición
				final RPasoTramitacion defPasoSiguiente = UtilsSTG.devuelveDefinicionPaso(idPasoSiguiente,
						pDatosSesion.getDefinicionTramite());
				if (defPasoSiguiente == null) {
					throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_NAVEGACION_PASO.name(),
							pDatosSesion.getDatosTramite().getIdSesionTramitacion(), pIdPaso,
							"El script de navegacion no ha devuelto un id de paso correcto: " + idPasoSiguiente);
				}
			}

		}
		return idPasoSiguiente;
	}

	/**
	 * Realiza debug.
	 *
	 * @param pDatosSesion
	 *            Datos sesion tramitacion
	 * @param message
	 *            Mensaje
	 */
	private void debug(final DatosSesionTramitacion pDatosSesion, final String message) {
		if (UtilsSTG.isDebugEnabled(pDatosSesion.getDefinicionTramite()) && LOGGER.isDebugEnabled()) {
			LOGGER.debug(pDatosSesion.getDatosTramite().getIdSesionTramitacion() + " - " + message);
		}
	}

}
