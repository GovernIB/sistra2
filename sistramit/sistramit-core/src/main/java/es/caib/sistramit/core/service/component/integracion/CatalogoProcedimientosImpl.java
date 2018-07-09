package es.caib.sistramit.core.service.component.integracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.service.model.integracion.DefinicionProcedimientoCP;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteCP;

/**
 * Implementación acceso SISTRAGES.
 *
 * @author Indra
 *
 */
@Component("catalogoProcedimientosComponent")
public final class CatalogoProcedimientosImpl
        implements CatalogoProcedimientosComponent {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public DefinicionTramiteCP obtenerDefinicionTramite(String idTramiteCP,
            String idioma) {

        // TODO PENDIENTE
        final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
        dp.setIdentificador("PROC1");
        dp.setDescripcion("Procedimiento 1");
        dp.setIdProcedimientoSIA("SIA1");
        dp.setOrganoResponsableDir3("RespDIR3");

        final DefinicionTramiteCP dt = new DefinicionTramiteCP();
        dt.setIdentificador(idTramiteCP);
        dt.setDescripcion("Tramite 1");
        dt.setProcedimiento(dp);
        dt.setVigente(true);

        return dt;
    }

}
