package es.caib.sistrahelp.core.ejb;

import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class EventoServiceBean implements EventoService {

    @Autowired
    private EventoService eventoService;


    /**
     * Obtiene los tipos de evento disponibles.
     *
     * @return Lista de tipos de evento.
     */
    @Override
    @PermitAll
    public List<TypeEvento> getTiposEvento(Entidad entidad) {
        return eventoService.getTiposEvento(entidad);
    }
}
