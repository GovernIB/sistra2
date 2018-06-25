package es.caib.sistramit.core.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.model.comun.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.Invalidacion;
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
                sistragesComponent.evictDefinicionTramite(codigos[0],
                        Integer.parseInt(codigos[1]), codigos[2]);
            default:
                break;
            }
        }

    }

}
