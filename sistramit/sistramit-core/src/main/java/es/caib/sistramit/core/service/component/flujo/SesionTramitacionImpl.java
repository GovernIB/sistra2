package es.caib.sistramit.core.service.component.flujo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.service.repository.dao.FlujoTramiteDao;

/**
 * Componente que permite generar una sesion.
 *
 * @author Indra
 *
 */
@Component("sesionTramitacion")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public final class SesionTramitacionImpl implements SesionTramitacion {

    /** Dao para acceso a bbdd. */
    @Autowired
    private FlujoTramiteDao dao;

    
    @Override
    public String generarSesionTramitacion() {
        return dao.crearSesionTramitacion();
    }

}
