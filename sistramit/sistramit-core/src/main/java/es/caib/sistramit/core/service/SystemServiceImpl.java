package es.caib.sistramit.core.service;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.flujo.Entidad;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.Invalidacion;
import es.caib.sistramit.core.api.model.system.types.TypeInvalidacion;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.integracion.CatalogoProcedimientosComponent;
import es.caib.sistramit.core.service.component.integracion.DominiosComponent;
import es.caib.sistramit.core.service.component.integracion.EnvioAvisoComponent;
import es.caib.sistramit.core.service.component.integracion.SistragesComponent;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.repository.dao.InvalidacionDao;
import es.caib.sistramit.core.service.repository.dao.ProcesoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;

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

	/** Envio avisos. */
	@Autowired
	private EnvioAvisoComponent envioAvisoComponent;

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
	public ListaPropiedades revisarInvalidaciones() {
		ListaPropiedades props = null;
		final List<Invalidacion> invalidaciones = invalidacionDAO.obtenerInvalidaciones(fcRevisionInvalidaciones);
		fcRevisionInvalidaciones = new Date();
		if (!invalidaciones.isEmpty()) {
			props = new ListaPropiedades();
			try {
				props.addPropiedad("jboss-instancia", InetAddress.getLocalHost().getHostAddress());
			} catch (final Exception ex) {
				props.addPropiedad("jboss-instancia", "unknown");
			}
			for (final Invalidacion inv : invalidaciones) {
				props.addPropiedad("invalidacion", inv.getTipo().toString()
						+ (StringUtils.isNotBlank(inv.getIdentificador()) ? " - " + inv.getIdentificador() : ""));
				switch (inv.getTipo()) {
				case COMPLETA:
					sistragesComponent.evictConfiguracionGlobal();
					sistragesComponent.evictConfiguracionEntidad();
					sistragesComponent.evictDefinicionTramite();
					sistragesComponent.evictDefinicionDominio();
					break;
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
		return props;
	}

	@Override
	@NegocioInterceptor
	public void invalidar(final Invalidacion invalidacion) {

		// Validamos tipo
		if (invalidacion.getTipo() == null) {
			throw new TipoNoControladoException("Tipus d'invalidació no suportada");
		}

		// Validamos identificador obligatorio
		if ((invalidacion.getTipo() != TypeInvalidacion.CONFIGURACION
				&& invalidacion.getTipo() != TypeInvalidacion.COMPLETA)
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
		envioAvisoComponent.procesarEnviosInmediatos();
	}

	@Override
	public void procesarEnviosReintentos() {
		envioAvisoComponent.procesarEnviosReintentos();
	}

	@Override
	public Entidad obtenerInfoEntidad(final String identificador, final String idioma) {
		final RConfiguracionEntidad re = configuracionComponent.obtenerConfiguracionEntidad(identificador);
		final Entidad entidad = UtilsFlujo.detalleTramiteEntidad(re, idioma, configuracionComponent);
		return entidad;
	}

}
