package es.caib.sistramit.core.service.component.integracion;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.email.api.IEmailPlugin;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.system.EnvioAviso;
import es.caib.sistramit.core.service.repository.dao.EnvioAvisoDao;

/**
 * Implementación componente pago.
 *
 * @author Indra
 *
 */
@Component("envioAvisoComponent")
public final class EnvioAvisoComponentImpl implements EnvioAvisoComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Envio DAO. */
	@Autowired
	private EnvioAvisoDao envioDao;

	@Override
	public void procesarEnviosInmediatos() {
		final IEmailPlugin plgEmail = (IEmailPlugin) configuracionComponent.obtenerPluginGlobal(TypePluginGlobal.EMAIL);
		final List<EnvioAviso> envios = envioDao.listaInmediatos();
		for (final EnvioAviso envio : envios) {
			enviarMensaje(plgEmail, envio);
		}
	}

	@Override
	public void procesarEnviosReintentos() {
		final IEmailPlugin plgEmail = (IEmailPlugin) configuracionComponent.obtenerPluginGlobal(TypePluginGlobal.EMAIL);
		final List<EnvioAviso> envios = envioDao.listaReintentos();
		for (final EnvioAviso envio : envios) {
			enviarMensaje(plgEmail, envio);
		}
	}

	/**
	 * Método encargado de realizar el envío de emails.
	 *
	 * @param plgEmail
	 * @param envio
	 */
	private void enviarMensaje(final IEmailPlugin plgEmail, final EnvioAviso envio) {
		try {
			final List<String> destino = new ArrayList<>();
			destino.add(envio.getDestino());

			if (plgEmail.envioEmail(destino, envio.getTitulo(), envio.getMensaje(), null)) {
				envioDao.guardarCorrecto(envio.getCodigo());
			} else {
				envioDao.errorEnvio(envio.getCodigo(), "Error enviando mail");
			}

		} catch (final Exception e) {
			final String mensajeError = ExceptionUtils.getMessage(e);
			log.error("Error al enviar email", e);
			envioDao.errorEnvio(envio.getCodigo(), mensajeError);
		}
	}

	@Override
	public void generarEnvio(final EnvioAviso envio) {
		envioDao.addEnvio(envio);
	}

}
