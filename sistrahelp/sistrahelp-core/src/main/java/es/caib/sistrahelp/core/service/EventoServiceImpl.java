package es.caib.sistrahelp.core.service;

import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrahelp.core.api.service.EventoService;
import es.caib.sistrahelp.core.service.component.ConfiguracionComponent;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {




    @Autowired
    private ConfiguracionComponent configuracionComponent;

    @Override
    public List<TypeEvento> getTiposEvento(Entidad entidad) {
        List<TypeEvento> tiposEventos = new ArrayList<>();
        for (final TypeEvento ev : TypeEvento.values()) {

            tiposEventos.add(ev);

        }

        String modoEntregaHabilitar = configuracionComponent.obtenerPropiedadConfiguracionSistrages(TypePropiedadConfiguracion.SISTRAGES_MODOENTREGA_HABILITAR);


        if( ! BooleanUtils.toBoolean(modoEntregaHabilitar) || (entidad != null && !entidad.isHabilitarModoEntrega())){
            tiposEventos.remove(TypeEvento.PROCESO_ENTREGA);
        }

        return tiposEventos;
    }
}
