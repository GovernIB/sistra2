package es.caib.sistramit.core.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.Invalidacion;
import es.caib.sistramit.core.api.model.system.types.TypeInvalidacion;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.integracion.DominiosComponent;
import es.caib.sistramit.core.service.component.integracion.SistragesComponent;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
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
				break;
			case DOMINIO:
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
			throw new TipoNoControladoException("Tipo de invalidación no soportada");
		}

		// Validamos identificador obligatorio
		if (invalidacion.getTipo() != TypeInvalidacion.CONFIGURACION
				&& StringUtils.isBlank(invalidacion.getIdentificador())) {
			throw new ErrorConfiguracionException("Invalidación incorrecta: no existe identificador");
		}

		// Validamos identificador para tipo trámite
		if (invalidacion.getTipo() == TypeInvalidacion.TRAMITE) {
			final String[] codigos = invalidacion.getIdentificador().split("#");
			if (codigos.length != 2) {
				throw new ErrorConfiguracionException(
						"Invalidación incorrecta: no es válido identificador " + invalidacion.getIdentificador());
			}
			try {
				Integer.parseInt(codigos[1]);
			} catch (final NumberFormatException nfe) {
				throw new ErrorConfiguracionException(
						"Invalidación incorrecta: no es válido identificador " + invalidacion.getIdentificador());
			}
		}

		invalidacionDAO.addInvalidacion(invalidacion);
	}

}
