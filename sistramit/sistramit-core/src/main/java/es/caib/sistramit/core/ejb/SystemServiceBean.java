package es.caib.sistramit.core.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.model.comun.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.service.SystemService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SystemServiceBean implements SystemService {

    @Autowired
    private SystemService systemService;

    @Override
    public String obtenerPropiedadConfiguracion(
            final TypePropiedadConfiguracion propiedad) {
        return systemService.obtenerPropiedadConfiguracion(propiedad);
    }

    @Override
    public void auditarErrorFront(final String idSesionTramitacion,
            final ErrorFrontException error) {
        systemService.auditarErrorFront(idSesionTramitacion, error);
    }

    @Override
    public List<EventoAuditoria> recuperarLogSesionTramitacion(
            final String idSesionTramitacion, final Date fechaDesde,
            final Date fechaHasta, final boolean ordenAsc) {
        return systemService.recuperarLogSesionTramitacion(idSesionTramitacion,
                fechaDesde, fechaHasta, ordenAsc);
    }

    @Override
    public void purgar(String instancia) {
        systemService.purgar(instancia);
    }

}
