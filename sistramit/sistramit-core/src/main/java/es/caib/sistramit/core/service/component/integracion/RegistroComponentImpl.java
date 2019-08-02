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
import es.caib.sistra2.commons.plugins.registro.api.types.TypeJustificante;
import es.caib.sistramit.core.api.exception.RegistroJustificanteException;
import es.caib.sistramit.core.api.exception.RegistroSolicitudException;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeDescargaJustificante;
import es.caib.sistramit.core.api.model.flujo.types.TypeResultadoRegistro;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
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
	public ResultadoRegistrar registrar(final String idSesionTramitacion, final AsientoRegistral asiento,
			final boolean debugEnabled) {

		final ResultadoRegistrar resultado = new ResultadoRegistrar();

		if (debugEnabled) {
			log.debug("Registrando tramite " + idSesionTramitacion);
		}

		ResultadoRegistro res = null;
		final IRegistroPlugin plgRegistro = (IRegistroPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.REGISTRO, asiento.getDatosOrigen().getCodigoEntidad());
		try {
			res = plgRegistro.registroEntrada(asiento);
			resultado.setResultado(TypeResultadoRegistro.CORRECTO);
			resultado.setNumeroRegistro(res.getNumeroRegistro());
			resultado.setFechaRegistro(res.getFechaRegistro());
		} catch (final RegistroPluginException ex) {
			// Auditamos error
			final RegistroSolicitudException rse = new RegistroSolicitudException(
					"Excepcion al registrar. Marcamos para reintentar.", ex);
			auditoriaComponent.auditarExcepcionNegocio(idSesionTramitacion, rse);

			// TODO Si estuviese preparado se podria habilitar esquema para reintentar. De
			// momento, lo tomamos como error.
			// resultado.setResultado(TypeResultadoRegistro.REINTENTAR);
			resultado.setResultado(TypeResultadoRegistro.ERROR);
		}

		return resultado;
	}

	@Override
	public ResultadoRegistrar reintentarRegistro(final String idSesionTramitacion, final boolean debugEnabled) {
		// TODO No esta habilitado mecanismo para registro
		throw new RuntimeException("No esta habilitado mecanismo para registro");
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

}
