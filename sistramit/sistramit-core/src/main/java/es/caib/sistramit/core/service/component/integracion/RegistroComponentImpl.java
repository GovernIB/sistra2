package es.caib.sistramit.core.service.component.integracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.IRegistroPlugin;
import es.caib.sistra2.commons.plugins.registro.api.RegistroPluginException;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoRegistro;
import es.caib.sistramit.core.api.exception.RegistroJustificanteException;
import es.caib.sistramit.core.api.exception.RegistroSolicitudException;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeResultadoRegistro;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;

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

	@Override
	public ResultadoRegistrar registrar(String idSesionTramitacion, AsientoRegistral asiento, boolean debugEnabled) {

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
	public ResultadoRegistrar reintentarRegistro(String idSesionTramitacion, boolean debugEnabled) {
		// TODO No esta habilitado mecanismo para registro
		throw new RuntimeException("No esta habilitado mecanismo para registro");
	}

	@Override
	public byte[] obtenerJustificanteRegistro(String codigoEntidad, String numeroRegistro, boolean debugEnabled) {

		if (debugEnabled) {
			log.debug("Obteniendo justificante registro " + codigoEntidad + " - " + numeroRegistro);
		}

		// TODO Ver implicaciones gestion presencial
		final IRegistroPlugin plgRegistro = (IRegistroPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.REGISTRO, codigoEntidad);
		byte[] justificante;
		try {
			justificante = plgRegistro.obtenerJustificanteRegistro(codigoEntidad, numeroRegistro);
		} catch (final RegistroPluginException e) {
			throw new RegistroJustificanteException(
					"Error obteniendo justificante registro: " + codigoEntidad + " - " + numeroRegistro, e);
		}
		return justificante;
	}

}
