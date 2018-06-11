package es.caib.sistramit.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.model.comun.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;

@Service
@Transactional
public class SystemServiceImpl implements SystemService {

    /** Componente configuracion. */
    @Autowired
    ConfiguracionComponent configuracionComponent;

    /** Componente auditoria. */
    @Autowired
    AuditoriaComponent auditoriaComponent;

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

}
