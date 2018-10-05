package es.caib.sistra2.commons.plugins.catalogoprocedimientos.mock;


import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionProcedimientoCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ICatalogoProcedimientosPlugin;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Plugin mock catálogo procedimientos.
 *
 * @author Indra
 *
 */
public class CatalogoProcedimientosPluginMock extends AbstractPluginProperties
        implements ICatalogoProcedimientosPlugin {

    public CatalogoProcedimientosPluginMock() {
    }

    public CatalogoProcedimientosPluginMock(final String prefijoPropiedades,
            final Properties properties) {
    }

    @Override
    public DefinicionTramiteCP obtenerDefinicionTramite(
            final String idTramiteCP, final String idioma) {
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

    @Override
    public List<DefinicionProcedimientoCP> obtenerProcedimientosTramiteSistra(
            final String idTramite, final String idioma) {
        final List<DefinicionProcedimientoCP> res = new ArrayList<>();

        final DefinicionProcedimientoCP dp = new DefinicionProcedimientoCP();
        dp.setIdentificador("PROC1");
        dp.setDescripcion("Procedimiento 1");
        dp.setIdProcedimientoSIA("SIA1");
        dp.setOrganoResponsableDir3("RespDIR3");

        res.add(dp);
        return res;
    }

}
