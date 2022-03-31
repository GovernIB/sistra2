package es.caib.sistramit.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistra2.commons.plugins.email.api.IEmailPlugin;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.Invalidacion;
import es.caib.sistramit.core.api.model.system.types.TypeInvalidacion;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.integracion.CatalogoProcedimientosComponent;
import es.caib.sistramit.core.service.component.integracion.DominiosComponent;
import es.caib.sistramit.core.service.component.integracion.SistragesComponent;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.system.Envio;
import es.caib.sistramit.core.service.repository.dao.EnvioDao;
import es.caib.sistramit.core.service.repository.dao.InvalidacionDao;
import es.caib.sistramit.core.service.repository.dao.ProcesoDao;

@Service
@Transactional
public class SystemServiceImpl implements SystemService {

	/** Componente configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Componente auditoria. */
	@Autowired
	private AuditoriaComponent auditoriaComponent;

	/** Invalidaciones DAO. */
	@Autowired
	private InvalidacionDao invalidacionDAO;

	/** Procesos DAO. */
	@Autowired
	private ProcesoDao procesosDao;

	/** Componente STG. */
	@Autowired
	private SistragesComponent sistragesComponent;

	/** Componente Dominios. */
	@Autowired
	private DominiosComponent dominiosComponent;

	/** Catalogo procedimientos. */
	@Autowired
	private CatalogoProcedimientosComponent catalogoProcedimientosComponent;

	/** Envio DAO. */
	@Autowired
	private EnvioDao envioDao;

	/** Fecha revision invalidaciones. */
	private Date fcRevisionInvalidaciones;

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

	@PostConstruct
	public void init() {
		// Establecemos fecha inicial revision invalidaciones
		fcRevisionInvalidaciones = new Date();
	}

	@Override
	@NegocioInterceptor
	public String obtenerPropiedadConfiguracion(final TypePropiedadConfiguracion propiedad) {
		return configuracionComponent.obtenerPropiedadConfiguracion(propiedad);
	}

	@Override
	@NegocioInterceptor
	public void auditarErrorFront(final String idSesionTramitacion, final ErrorFrontException error) {
		auditoriaComponent.auditarErrorFront(idSesionTramitacion, error);
	}

	@Override
	@NegocioInterceptor
	public List<EventoAuditoria> recuperarLogSesionTramitacion(final String idSesionTramitacion, final Date fechaDesde,
			final Date fechaHasta, final boolean ordenAsc) {
		return auditoriaComponent.recuperarLogSesionTramitacion(idSesionTramitacion, fechaDesde, fechaHasta, ordenAsc);
	}

	@Override
	@NegocioInterceptor
	public boolean verificarMaestro(final String instancia) {
		return procesosDao.verificarMaestro(instancia);
	}

	@Override
	@NegocioInterceptor
	public void revisarInvalidaciones() {
		final List<Invalidacion> invalidaciones = invalidacionDAO.obtenerInvalidaciones(fcRevisionInvalidaciones);
		fcRevisionInvalidaciones = new Date();
		for (final Invalidacion inv : invalidaciones) {
			switch (inv.getTipo()) {
			case CONFIGURACION:
				sistragesComponent.evictConfiguracionGlobal();
				break;
			case ENTIDAD:
				sistragesComponent.evictConfiguracionEntidad(inv.getIdentificador());
				sistragesComponent.evictAvisosEntidad(inv.getIdentificador());
				catalogoProcedimientosComponent.evictCatalogoProcedimientosEntidad(inv.getIdentificador());
				break;
			case DOMINIO:
				sistragesComponent.evictDefinicionDominio(inv.getIdentificador());
				dominiosComponent.invalidarDominio(inv.getIdentificador());
				break;
			case TRAMITE:
				final String[] codigos = inv.getIdentificador().split("#");
				final String idTramite = codigos[0];
				final int version = Integer.parseInt(codigos[1]);

				// Invalidamos para todos los idiomas posibles
				final String idiomasSoportados = configuracionComponent
						.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMAS_SOPORTADOS);
				final String[] idiomas = idiomasSoportados.split(",");
				for (final String idioma : idiomas) {
					sistragesComponent.evictDefinicionTramite(idTramite, version, idioma);
				}
				break;
			default:
				break;
			}
		}

	}

	@Override
	@NegocioInterceptor
	public void invalidar(final Invalidacion invalidacion) {

		// Validamos tipo
		if (invalidacion.getTipo() == null) {
			throw new TipoNoControladoException("Tipus d'invalidació no suportada");
		}

		// Validamos identificador obligatorio
		if (invalidacion.getTipo() != TypeInvalidacion.CONFIGURACION
				&& StringUtils.isBlank(invalidacion.getIdentificador())) {
			throw new ErrorConfiguracionException("Invalidació incorrecta: no existeix identificador");
		}

		// Validamos identificador para tipo trámite
		if (invalidacion.getTipo() == TypeInvalidacion.TRAMITE) {
			final String[] codigos = invalidacion.getIdentificador().split("#");
			if (codigos.length != 2) {
				throw new ErrorConfiguracionException(
						"Invalidació incorrecta: no és vàlid identificador " + invalidacion.getIdentificador());
			}
			try {
				Integer.parseInt(codigos[1]);
			} catch (final NumberFormatException nfe) {
				throw new ErrorConfiguracionException(
						"Invalidació incorrecta: no es vàlido identificador " + invalidacion.getIdentificador());
			}
		}

		invalidacionDAO.addInvalidacion(invalidacion);
	}

	@Override
	public void procesarEnviosInmediatos() {
		final IEmailPlugin plgEmail = (IEmailPlugin) configuracionComponent.obtenerPluginGlobal(TypePluginGlobal.EMAIL);
		final List<Envio> envios = envioDao.listaInmediatos();
		for (final Envio envio : envios) {
			enviarMensaje(plgEmail, envio);
		}
	}

	@Override
	public void procesarEnviosReintentos() {
		final IEmailPlugin plgEmail = (IEmailPlugin) configuracionComponent.obtenerPluginGlobal(TypePluginGlobal.EMAIL);
		final List<Envio> envios = envioDao.listaReintentos();
		for (final Envio envio : envios) {
			enviarMensaje(plgEmail, envio);
		}
	}

	/**
	 * Método encargado de realizar el envío de emails.
	 *
	 * @param plgEmail
	 * @param envio
	 */
	private void enviarMensaje(final IEmailPlugin plgEmail, final Envio envio) {
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

}
