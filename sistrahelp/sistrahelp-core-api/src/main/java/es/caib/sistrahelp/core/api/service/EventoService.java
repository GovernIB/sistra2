package es.caib.sistrahelp.core.api.service;

import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;

import java.util.List;

public interface EventoService {

    List<TypeEvento> getTiposEvento(Entidad entidad);
}
