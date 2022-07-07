package es.caib.sistramit.core.service.component.integracion;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.DestinoEnvio;
import es.caib.sistra2.commons.plugins.registro.api.EnvioRemotoPluginException;
import es.caib.sistra2.commons.plugins.registro.api.IEnvioRemotoPlugin;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoRegistro;
import es.caib.sistra2.commons.plugins.registro.api.VerificacionRegistro;
import es.caib.sistrages.rest.api.interna.RConfiguracionAutenticacion;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.REnvioRemoto;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.RegistroSolicitudException;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeResultadoRegistro;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;

/**
 * Implementación Envio Remoto.
 *
 * @author Indra
 *
 */
@Component("envioRemotoComponent")
public final class EnvioRemotoComponentImpl implements EnvioRemotoComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Auditoria. */
	@Autowired
	private AuditoriaComponent auditoriaComponent;

	@Override
	public String iniciarSesionEnvio(final String codigoEntidad, final String idEnvioRemoto,
			final boolean debugEnabled) {
		final IEnvioRemotoPlugin plgRegistro = (IEnvioRemotoPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.ENVIO_REMOTO, codigoEntidad);
		final DestinoEnvio destinoEnvio = obtenerDestinoEnvio(codigoEntidad, idEnvioRemoto);
		try {
			final String idSesionRegistro = plgRegistro.iniciarSesionEnvio(destinoEnvio);
			return idSesionRegistro;
		} catch (final EnvioRemotoPluginException e) {
			throw new RegistroSolicitudException("Error iniciant sessió enviament per : " + idEnvioRemoto, e);
		}
	}

	@Override
	public ResultadoRegistrar realizarEnvio(final String codigoEntidad, final String idEnvioRemoto,
			final String idSesionTramitacion, final String idSesionEnvio, final AsientoRegistral asientoRegistral,
			final boolean debugEnabled) {
		final IEnvioRemotoPlugin plgRegistro = (IEnvioRemotoPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.ENVIO_REMOTO, codigoEntidad);
		final DestinoEnvio destinoEnvio = obtenerDestinoEnvio(codigoEntidad, idEnvioRemoto);
		final ResultadoRegistrar resultado = new ResultadoRegistrar();
		try {
			final ResultadoRegistro res = plgRegistro.realizarEnvio(destinoEnvio, idEnvioRemoto, asientoRegistral);
			resultado.setResultado(TypeResultadoRegistro.CORRECTO);
			resultado.setNumeroRegistro(res.getNumeroRegistro());
			resultado.setFechaRegistro(res.getFechaRegistro());
		} catch (final EnvioRemotoPluginException e) {
			// Auditamos error
			final RegistroSolicitudException rse = new RegistroSolicitudException(
					"Error realizant enviament amb id sessió: " + idSesionEnvio + " per : " + idEnvioRemoto, e);
			auditoriaComponent.auditarExcepcionNegocio(idSesionTramitacion, rse);
			resultado.setResultado(TypeResultadoRegistro.REINTENTAR);
		}
		return resultado;
	}

	@Override
	public ResultadoRegistrar reintentarEnvio(final String codigoEntidad, final String idEnvioRemoto,
			final String idSesionEnvio, final boolean debugEnabled) {
		final IEnvioRemotoPlugin plgRegistro = (IEnvioRemotoPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.ENVIO_REMOTO, codigoEntidad);
		final DestinoEnvio destinoEnvio = obtenerDestinoEnvio(codigoEntidad, idEnvioRemoto);
		try {
			final VerificacionRegistro verificacion = plgRegistro.verificarEnvio(destinoEnvio, idSesionEnvio);
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
		} catch (final EnvioRemotoPluginException e) {
			throw new RegistroSolicitudException(
					"Error verificant enviament amb id sessió: " + idSesionEnvio + " per : " + idEnvioRemoto, e);
		}
	}

	/**
	 * Obtiene destino envío.
	 *
	 * @param codigoEntidad
	 *                          Código entidad
	 * @param idEnvioRemoto
	 *                          Id envio remoto
	 * @return Destino envío
	 */
	private DestinoEnvio obtenerDestinoEnvio(final String codigoEntidad, final String idEnvioRemoto) {
		final RConfiguracionEntidad confEntidad = configuracionComponent.obtenerConfiguracionEntidad(codigoEntidad);
		REnvioRemoto confEnvioRemoto = null;
		for (final REnvioRemoto er : confEntidad.getEnviosRemoto()) {
			if (er.getIdentificador().equals(idEnvioRemoto)) {
				confEnvioRemoto = er;
				break;
			}
		}
		if (confEnvioRemoto == null) {
			throw new ErrorConfiguracionException(
					"No existeix enviament remot " + idEnvioRemoto + " a entitat " + codigoEntidad);
		}
		RConfiguracionAutenticacion confAut = null;
		if (StringUtils.isNotEmpty(confEnvioRemoto.getIdentificadorConfAutenticacion())) {
			confAut = configuracionComponent.obtenerConfiguracionAutenticacion(
					confEnvioRemoto.getIdentificadorConfAutenticacion(), codigoEntidad);
		}
		final DestinoEnvio res = new DestinoEnvio();
		res.setIdEntidad(codigoEntidad);
		res.setIdEnvioRemoto(idEnvioRemoto);
		res.setUrl(confEnvioRemoto.getUrl());
		if (StringUtils.isNotEmpty(confEnvioRemoto.getTimeout())) {
			res.setTimeoutSecs(Integer.parseInt(confEnvioRemoto.getTimeout()));
		}
		if (confAut != null) {
			res.setUsuario(confAut.getUsuario());
			res.setPassword(confAut.getPassword());
		}
		return res;
	}

}
