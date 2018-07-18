package es.caib.sistra2.commons.plugins.mock.catalogoprocedimientos;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.DefinicionProcedimientoCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.ICatalogoProcedimientosPlugin;

/**
 * Plugin mock catálogo procedimientos.
 *
 * @author Indra
 *
 */
public class CatalogoProcedimientosPluginMock
        implements ICatalogoProcedimientosPlugin {

    @Override
    public DefinicionTramiteCP obtenerDefinicionTramite(String idTramiteCP,
            String idioma) {
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
