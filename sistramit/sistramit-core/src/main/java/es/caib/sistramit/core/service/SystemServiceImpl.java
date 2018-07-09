package es.caib.sistramit.core.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.Invalidacion;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.integracion.SistragesComponent;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.repository.dao.InvalidacionDao;

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

    /** Componente STG. */
    @Autowired
    private SistragesComponent sistragesComponent;

    /** Fecha revision invalidaciones. */
    private Date fcRevisionInvalidaciones;

    /** Log. */
    private static Logger log = LoggerFactory
            .getLogger(SystemServiceImpl.class);

    @PostConstruct
    public void init() {
        // Establecemos fecha inicial revision invalidaciones
        fcRevisionInvalidaciones = new Date();
    }

    @Override
    @NegocioInterceptor
    public String obtenerPropiedadConfiguracion(
            final TypePropiedadConfiguracion propiedad) {
        return configuracionComponent.obtenerPropiedadConfiguracion(propiedad);
    }

    @Override
    @NegocioInterceptor
    public void auditarErrorFront(final String idSesionTramitacion,
            final ErrorFrontException error) {
        auditoriaComponent.auditarErrorFront(idSesionTramitacion, error);
    }

    @Override
    @NegocioInterceptor
    public List<EventoAuditoria> recuperarLogSesionTramitacion(
            final String idSesionTramitacion, final Date fechaDesde,
            final Date fechaHasta, final boolean ordenAsc) {
        return auditoriaComponent.recuperarLogSesionTramitacion(
                idSesionTramitacion, fechaDesde, fechaHasta, ordenAsc);
    }

    @Override
    @NegocioInterceptor
    public void purgar(String instancia) {
        // TODO PENDIENTE. VER SI SE DEBE SEPARAR EN VARIOS PROCESOS POR TEMA DE
        // DURACION DE TX.

        // Purga invalidaciones
        purgarInvalidaciones();

    }

    @Override
    @NegocioInterceptor
    public void revisarInvalidaciones() {
        final List<Invalidacion> invalidaciones = invalidacionDAO
                .obtenerInvalidaciones(fcRevisionInvalidaciones);
        fcRevisionInvalidaciones = new Date();
        for (final Invalidacion inv : invalidaciones) {
            switch (inv.getTipo()) {
            case CONFIGURACION:
                sistragesComponent.evictConfiguracionGlobal();
                break;
            case ENTIDAD:
                sistragesComponent
                        .evictConfiguracionEntidad(inv.getIdentificador());
                break;
            case DOMINIO:
                // TODO PENDIENTE IMPLEMENTAR INVALIDACION DOMINIOS
                break;
            case TRAMITE:
                final String[] codigos = inv.getIdentificador().split("#");
                final String idTramite = codigos[0];
                final int version = Integer.parseInt(codigos[1]);

                // Invalidamos para todos los idiomas posibles
                final String idiomasSoportados = configuracionComponent
                        .obtenerPropiedadConfiguracion(
                                TypePropiedadConfiguracion.IDIOMAS_SOPORTADOS);
                final String[] idiomas = idiomasSoportados.split(",");
                for (final String idioma : idiomas) {
                    sistragesComponent.evictDefinicionTramite(idTramite,
                            version, idioma);
                }
            default:
                break;
            }
        }

    }

    /**
     * Purga invalidaciones.
     */
    private void purgarInvalidaciones() {
        log.debug("Purga invalidaciones: inicio...");
        // Añadimos 1 día
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, ConstantesNumero.N1);
        invalidacionDAO.purgarInvalidaciones(cal.getTime());
        log.debug("Purga invalidaciones: fin");
    }

}
