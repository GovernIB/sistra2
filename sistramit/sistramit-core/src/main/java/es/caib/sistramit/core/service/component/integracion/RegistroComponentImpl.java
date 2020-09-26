package es.caib.sistramit.core.service.component.integracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.IRegistroPlugin;
import es.caib.sistra2.commons.plugins.registro.api.LibroOficina;
import es.caib.sistra2.commons.plugins.registro.api.RegistroPluginException;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoJustificante;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoRegistro;
import es.caib.sistra2.commons.plugins.registro.api.VerificacionRegistro;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeJustificante;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.RegistroJustificanteException;
import es.caib.sistramit.core.api.exception.RegistroSolicitudException;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeDescargaJustificante;
import es.caib.sistramit.core.api.model.flujo.types.TypeResultadoRegistro;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.system.Envio;
import es.caib.sistramit.core.service.repository.dao.EnvioDao;

/**
 * Implementación componente registro.
 *
 * @author Indra
 *
 */
@Component("registroComponent")
public final class RegistroComponentImpl implements RegistroComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Auditoria. */
	@Autowired
	private AuditoriaComponent auditoriaComponent;

	/** Envio */
	@Autowired
	private EnvioDao envioDao;

	@Override
	public ResultadoRegistrar registrar(final String codigoEntidad, final String idSesionTramitacion,
			final String idSesionRegistro, final AsientoRegistral asiento, final boolean debugEnabled) {

		final ResultadoRegistrar resultado = new ResultadoRegistrar();

		if (debugEnabled) {
			log.debug("Registrando tramite " + idSesionTramitacion + " en entidad " + codigoEntidad
					+ " con datos destino registro: entidad " + asiento.getDatosOrigen().getCodigoEntidad()
					+ " / Unidad " + asiento.getDatosAsunto().getCodigoOrganoDestino() + " / Oficina "
					+ asiento.getDatosOrigen().getCodigoOficinaRegistro() + " / Libro "
					+ asiento.getDatosOrigen().getLibroOficinaRegistro());
		}

		// Permitir que entidad sea diferente en pruebas en entornos distintos a PRO
		if (!codigoEntidad.equals(asiento.getDatosOrigen().getCodigoEntidad())
				&& TypeEntorno.fromString(configuracionComponent
						.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.ENTORNO)) == TypeEntorno.PRODUCCION) {
			throw new ErrorConfiguracionException(
					"Solo se puede especificar una entidad distinta en los datos de registro en un entorno distinto a Producción");
		}

		ResultadoRegistro res = null;
		final IRegistroPlugin plgRegistro = (IRegistroPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.REGISTRO, codigoEntidad);
		try {
			res = plgRegistro.registroEntrada(idSesionRegistro, asiento);
			resultado.setResultado(TypeResultadoRegistro.CORRECTO);
			resultado.setNumeroRegistro(res.getNumeroRegistro());
			resultado.setFechaRegistro(res.getFechaRegistro());
		} catch (final RegistroPluginException ex) {
			// Auditamos error
			final RegistroSolicitudException rse = new RegistroSolicitudException(
					"Excepcion al registrar. Marcamos para reintentar.", ex);
			auditoriaComponent.auditarExcepcionNegocio(idSesionTramitacion, rse);
			resultado.setResultado(TypeResultadoRegistro.REINTENTAR);
		}

		return resultado;
	}

	@Override
	public ResultadoJustificante obtenerJustificanteRegistro(final String codigoEntidad, final String numeroRegistro,
			final boolean debugEnabled) {

		if (debugEnabled) {
			log.debug("Obteniendo justificante registro " + codigoEntidad + " - " + numeroRegistro);
		}

		final IRegistroPlugin plgRegistro = (IRegistroPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.REGISTRO, codigoEntidad);
		ResultadoJustificante justificante;
		try {
			justificante = plgRegistro.obtenerJustificanteRegistro(codigoEntidad, numeroRegistro);
		} catch (final RegistroPluginException e) {
			throw new RegistroJustificanteException(
					"Error obteniendo justificante registro: " + codigoEntidad + " - " + numeroRegistro, e);
		}
		return justificante;
	}

	@Override
	public String obtenerLibroOrganismo(final String codigoEntidad, final String codigoOrganismo,
			final boolean debugEnabled) {

		if (debugEnabled) {
			log.debug("Obteniendo libro organismo " + codigoEntidad + " - " + codigoOrganismo);
		}

		final IRegistroPlugin plgRegistro = (IRegistroPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.REGISTRO, codigoEntidad);

		LibroOficina libro = null;
		try {
			libro = plgRegistro.obtenerLibroOrganismo(codigoEntidad, codigoOrganismo);
		} catch (final RegistroPluginException e) {
			throw new RegistroSolicitudException(
					"Error obteniendo libro asociado a organismo: " + codigoEntidad + " - " + codigoOrganismo, e);
		}
		return libro.getCodigo();
	}

	@Override
	public TypeDescargaJustificante descargaJustificantes(final String codigoEntidad) {
		final IRegistroPlugin plgRegistro = (IRegistroPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.REGISTRO, codigoEntidad);

		try {
			TypeDescargaJustificante res = TypeDescargaJustificante.FICHERO;
			final TypeJustificante justif = plgRegistro.descargaJustificantes();
			if (justif != null) {
				switch (justif) {
				case FICHERO:
					res = TypeDescargaJustificante.FICHERO;
					break;
				case URL_EXTERNA:
					res = TypeDescargaJustificante.URL_EXTERNA;
					break;
				case CARPETA_CIUDADANA:
					res = TypeDescargaJustificante.CARPETA_CIUDADANA;
					break;
				default:
					res = TypeDescargaJustificante.FICHERO;
					break;
				}
			}
			return res;
		} catch (final RegistroPluginException e) {
			throw new RegistroJustificanteException(
					"Error obteniendo como se descargan justificantes para entidad: " + codigoEntidad, e);
		}
	}

	@Override
	public void guardarEnvio(final Envio envio) {
		envioDao.addEnvio(envio);
	}

	@Override
	public String iniciarSesionRegistro(final String codigoEntidad, final boolean debugEnabled) {
		final IRegistroPlugin plgRegistro = (IRegistroPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.REGISTRO, codigoEntidad);
		try {
			final String idSesionRegistro = plgRegistro.iniciarSesionRegistroEntrada(codigoEntidad);
			return idSesionRegistro;
		} catch (final RegistroPluginException e) {
			throw new RegistroSolicitudException(
					"Error iniciando sesión registro entrada para entidad: " + codigoEntidad, e);
		}
	}

	@Override
	public ResultadoRegistrar reintentarRegistro(final String codigoEntidad, final String idSesionRegistro,
			final boolean debugEnabled) {
		final IRegistroPlugin plgRegistro = (IRegistroPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.REGISTRO, codigoEntidad);
		try {
			final VerificacionRegistro verificacion = plgRegistro.verificarRegistroEntrada(codigoEntidad,
					idSesionRegistro);

			final ResultadoRegistrar res = new ResultadoRegistrar();
			switch (verificacion.getEstado()) {
			case REALIZADO:
				res.setResultado(TypeResultadoRegistro.CORRECTO);
				res.setNumeroRegistro(verificacion.getDatosRegistro().getNumeroRegistro());
				res.setFechaRegistro(verificacion.getDatosRegistro().getFechaRegistro());
				break;
			case NO_REALIZADO:
				res.setResultado(TypeResultadoRegistro.ERROR);
				break;
			case EN_PROCESO:
				res.setResultado(TypeResultadoRegistro.REINTENTAR);
				break;
			}
			return res;
		} catch (final RegistroPluginException e) {
			throw new RegistroSolicitudException(
					"Error iniciando sesión registro entrada para entidad: " + codigoEntidad, e);
		}
	}

}
