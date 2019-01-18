package es.caib.sistramit.core.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.ResultadoProcesoProgramado;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.PurgaService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.integracion.PurgaComponent;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.component.system.FlujoTramitacionCacheComponent;

/**
 * Implementación proceso de purga.
 *
 * @author Indra
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PurgaServiceImpl implements PurgaService {

	/** Atributo purga component de PurgaServiceImpl. */
	@Resource(name = "purgaComponent")
	private PurgaComponent purgaComponent;

	/** Acceso configuración. */
	@Autowired
	private ConfiguracionComponent config;

	/** Acceso configuración. */
	@Autowired
	private AuditoriaComponent auditoriaComponent;

	/** Caché con con los flujos de tramitacion. */
	@Autowired
	private FlujoTramitacionCacheComponent flujoTramitacionCache;

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(PurgaServiceImpl.class);

	@Override
	@NegocioInterceptor
	public ResultadoProcesoProgramado purgarPersistencia() {

		final ListaPropiedades lp = new ListaPropiedades();

		log.info("Procés purga: inici del procés de purga...");

		final long inicio = System.currentTimeMillis();
		ResultadoProcesoProgramado res = null;

		// Marcamos para purgar los tramites finalizados, no persistentes sin
		// terminar y persistentes caducados
		log.info("Procés purga: marquem per purgar els tràmits...");
		procesoPurgarMarcarTramites(lp);

		// Purgamos los tramites marcados para purgar
		log.info("Procés purga: purgant el tràmits marcats per a purgar...");
		procesoPurgarTramitesPurga(lp);

		// Borrado definitivo de los tramites purgados
		log.info("Procés purga: eliminant definitivament els tràmites purgats...");
		procesoPurgarEliminarTramitesPurgados(lp);

		// Purgamos sesiones de formularios
		log.info("Procés purga: purgant sesions de formularis...");
		procesoPurgarSesionesFormulario(lp);

		// Purgamos pagos externos
		log.info("Procés purga: purgant pagaments externs...");
		procesoPurgarPagosExternos(lp);

		// Purgamos ficheros huerfanos
		log.info("Procés purga: purgant fitxers orfes...");
		procesoPurgarFicherosHuerfanos(lp);

		// Purgamos invalidaciones
		log.info("Procés purga: purgant invalidacions...");
		procesoPurgarInvalidaciones();

		// Purgado de errores internos (excepto ligados a tramites).
		log.info("Procés purga: purgant errors interns...");
		procesoPurgarErroresInternos(lp);

		final long duracion = (System.currentTimeMillis() - inicio) / (ConstantesNumero.N1000);

		lp.addPropiedad("Durada del procés", duracion + " segons.");

		log.info("Procés purga: fin proceso de purga (" + duracion + " segundos)");

		res = new ResultadoProcesoProgramado();
		res.setFinalizadoOk(true);
		res.setDetalles(lp);

		final EventoAuditoria evento = new EventoAuditoria();
		evento.setTipoEvento(TypeEvento.PROCESO_PURGA);
		evento.setIdSesionTramitacion(null);
		evento.setPropiedadesEvento(lp);
		evento.setFecha(new Date());
		evento.setDescripcion("Procés de purga");
		auditoriaComponent.auditarEventoAplicacion(evento);

		return res;
	}

	@Override
	@NegocioInterceptor
	public void purgarFlujosTramitacion() {
		flujoTramitacionCache.purgarFlujosTramitacion();
	}

	/**
	 * Purga de tramites marcados para purgar.
	 *
	 * @param lp
	 *            Lista propiedades
	 */
	private void procesoPurgarTramitesPurga(final ListaPropiedades lp) {
		final int num = this.purgaComponent.realizarPurgaTramitesMarcadosParaPurgar();
		lp.addPropiedad("Tramites purgados", Integer.toString(num));
	}

	/**
	 * Purga invalidaciones.
	 */
	private void procesoPurgarInvalidaciones() {
		// Borramos invalidaciones
		final Calendar calendar = Calendar.getInstance(new Locale("es", "ES"));
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR,
				Integer.parseInt(config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_INVALIDACIONES))
						* ConstantesNumero.N_1);
		this.purgaComponent.purgarInvalidaciones(calendar.getTime());
	}

	/**
	 * Purga ficheros huerfanos.
	 *
	 * @param lp
	 *            Lista propiedades
	 */
	private void procesoPurgarFicherosHuerfanos(final ListaPropiedades lp) {
		final int ficsBorrados = purgaComponent.purgarFicherosHuerfanos();
		lp.addPropiedad("Fitxers orfes esborrats", String.valueOf(ficsBorrados));
	}

	/**
	 * Purga sesiones formulario.
	 *
	 * @param lp
	 *            Lista propiedades
	 */
	private void procesoPurgarSesionesFormulario(final ListaPropiedades lp) {
		if (existeParametroPurga(config
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_SESION_FORMULARIOS_NO_FINALIZADOS))
				&& existeParametroPurga(config.obtenerPropiedadConfiguracion(
						TypePropiedadConfiguracion.PURGA_SESION_FORMULARIOS_FINALIZADOS))) {
			final Date limiteInicio = calcularFechaLimite(config.obtenerPropiedadConfiguracion(
					TypePropiedadConfiguracion.PURGA_SESION_FORMULARIOS_NO_FINALIZADOS), 'm');
			final Date limiteFin = calcularFechaLimite(config.obtenerPropiedadConfiguracion(
					TypePropiedadConfiguracion.PURGA_SESION_FORMULARIOS_FINALIZADOS), 'm');

			final int sesionesFinalizadasBorradas = this.purgaComponent.purgarSesionesFormularioFinalizadas(limiteFin);

			final int sesionesNoFinalizadasBorradas = this.purgaComponent
					.purgarSesionesFormularioNoFinalizadas(limiteInicio);

			lp.addPropiedad("Sessions formulari purgats",
					String.valueOf(sesionesFinalizadasBorradas + sesionesNoFinalizadasBorradas));
		}
	}

	/**
	 * Purga pagos externos
	 *
	 * @param lp
	 *            Lista propiedades
	 */
	private void procesoPurgarPagosExternos(final ListaPropiedades lp) {

		if (existeParametroPurga(
				config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_PAGOS_FINALIZADOS))
				&& existeParametroPurga(
						config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_PAGOS_NO_FINALIZADOS))) {
			final Date limiteInicio = calcularFechaLimite(
					config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_PAGOS_FINALIZADOS), 'm');
			final Date limiteFin = calcularFechaLimite(
					config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_PAGOS_NO_FINALIZADOS), 'm');

			final int sesionesFinalizadasBorradas = purgaComponent.purgarPagosExternosFinalizados(limiteFin);

			final int sesionesNoFinalizadasBorradas = this.purgaComponent
					.purgarPagosExternosNoFinalizados(limiteInicio);

			lp.addPropiedad("Pagaments externs purgats",
					String.valueOf(sesionesFinalizadasBorradas + sesionesNoFinalizadasBorradas));
		}

	}

	/**
	 * Elimina definitivamente los tramites purgados anteriormente.
	 *
	 * @param lp
	 *            Lista propiedades
	 */
	private void procesoPurgarEliminarTramitesPurgados(final ListaPropiedades lp) {
		if (existeParametroPurga(config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_PURGADOS))) {
			final Date fechaCaducidadPurgados = this.calcularFechaLimite(
					config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_PURGADOS), 'd');
			final int num = this.purgaComponent.eliminarTramitesPurgados(fechaCaducidadPurgados);
			lp.addPropiedad("Tràmits purgats eliminats", Integer.toString(num));
		}
	}

	/**
	 * Marca para purgar los trámites.
	 *
	 * @param lp
	 *            Lista propiedades
	 */
	private void procesoPurgarMarcarTramites(final ListaPropiedades lp) {

		Date fechaLimiteCaducados = null;
		if (existeParametroPurga(config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_CADUCADOS))) {
			fechaLimiteCaducados = this.calcularFechaLimite(
					config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_CADUCADOS), 'h');
		}

		Date fechaLimiteNPSinFinalizar = null;
		if (existeParametroPurga(
				config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_NO_PERSISTENTES))) {
			fechaLimiteNPSinFinalizar = this.calcularFechaLimite(
					config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_NO_PERSISTENTES), 'h');
		}

		Date fechaLimiteFinalizados = null;
		if (existeParametroPurga(config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_FINALIZADOS))) {
			fechaLimiteFinalizados = this.calcularFechaLimite(
					config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_FINALIZADOS), 'h');
		}

		Date fechaLimitePendientePurgaPago = null;
		if (existeParametroPurga(
				config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_PENDIENTE_PURGA_PAGO))) {
			fechaLimitePendientePurgaPago = this.calcularFechaLimite(
					config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_PENDIENTE_PURGA_PAGO), 'd');
		}

		final ListaPropiedades propsMarcar = this.purgaComponent.marcarPurgarTramites(fechaLimiteFinalizados,
				fechaLimiteNPSinFinalizar, fechaLimiteCaducados, fechaLimitePendientePurgaPago);
		lp.addPropiedad("Tràmits marcats per purgar", propsMarcar.getPropiedad("MARCADOS"));
		lp.addPropiedad("Tràmits pendents purga", propsMarcar.getPropiedad("PENDIENTES"));
		lp.addPropiedad("Tràmits pendents purga amb pagament realitzat",
				propsMarcar.getPropiedad("PENDIENTEPAGOREALIZADO"));
		lp.addPropiedad("Tràmits marcats per purgar pendent purga amb pagament realitzat",
				propsMarcar.getPropiedad("MARCADOSPENDIENTEPAGOREALIZADO"));
	}

	/**
	 * Purgado de errores internos (excepto ligados a tramites).
	 *
	 * @param lp
	 *            Lista propiedades
	 */
	private void procesoPurgarErroresInternos(final ListaPropiedades lp) {
		if (existeParametroPurga(
				config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_ERRORES_INTERNOS))) {
			final Date fechaCaducidadErrores = this.calcularFechaLimite(
					config.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PURGA_ERRORES_INTERNOS), 'd');

			final int numErroresBorrados = purgaComponent.purgaErroresInternos(fechaCaducidadErrores);
			lp.addPropiedad("Errors interns", String.valueOf(numErroresBorrados));
		}
	}

	/**
	 * Convierte a fecha restando la cantidad de tiempo especificada.
	 *
	 * @param cantidad
	 *            cantidad de tiempo en dias / horas / minutos
	 * @param tipo
	 *            indica si es dias (d) / horas (h) / minutos (m)
	 * @return fecha
	 */
	private Date calcularFechaLimite(final String cantidad, final char tipo) {
		Date regreso = null;
		long tiempo = 0;

		final long permanencia = Long.parseLong(cantidad);

		if (tipo == 'd') {
			tiempo = ConstantesNumero.N24 * ConstantesNumero.MINUTOSHORA * ConstantesNumero.MILISEGUNDOSMINUTO;
		} else if (tipo == 'h') {
			tiempo = ConstantesNumero.MINUTOSHORA * ConstantesNumero.MILISEGUNDOSMINUTO;
		} else {
			tiempo = ConstantesNumero.MILISEGUNDOSMINUTO;
		}
		final long permanenciaMilisegundos = permanencia * tiempo;

		final long milisegundos = new Date().getTime() - (permanenciaMilisegundos);

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(milisegundos);
		regreso = cal.getTime();

		return regreso;
	}

	/**
	 * Comprueba si existe el parametro de purga.
	 *
	 * @param param
	 *            Parametro
	 * @return boolean
	 */
	private boolean existeParametroPurga(final String param) {
		boolean res = true;
		if (StringUtils.isBlank(param)) {
			res = false;
		}
		try {
			final int num = Integer.parseInt(param);
			if (num <= 0) {
				res = false;
			}
		} catch (final NumberFormatException nfe) {
			res = false;
		}
		return res;
	}

}
